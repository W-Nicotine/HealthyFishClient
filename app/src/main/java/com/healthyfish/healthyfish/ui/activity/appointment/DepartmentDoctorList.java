package com.healthyfish.healthyfish.ui.activity.appointment;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.healthyfish.healthyfish.POJO.BeanDepartmentDoctor;
import com.healthyfish.healthyfish.POJO.BeanDoctorInfo;
import com.healthyfish.healthyfish.POJO.BeanHospDeptDoctListReq;
import com.healthyfish.healthyfish.POJO.BeanHospDeptDoctListRespItem;
import com.healthyfish.healthyfish.POJO.BeanHospRegisterReq;
import com.healthyfish.healthyfish.R;
import com.healthyfish.healthyfish.adapter.ChoiceDoctorLvAdapter;
import com.healthyfish.healthyfish.adapter.DepartmentDoctorLvAdapter;
import com.healthyfish.healthyfish.ui.activity.BaseActivity;
import com.healthyfish.healthyfish.utils.DoctorPostComparator;
import com.healthyfish.healthyfish.utils.OkHttpUtils;
import com.healthyfish.healthyfish.utils.RetrofitManagerUtils;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import okhttp3.ResponseBody;
import rx.Subscriber;

import static com.healthyfish.healthyfish.constant.Constants.HttpHealthyFishyUrl;

/**
 * 描述：挂号模块科室医生列表页面
 * 作者：LYQ on 2017/7/10.
 * 邮箱：feifanman@qq.com
 * 编辑：LYQ
 */

public class DepartmentDoctorList extends BaseActivity {

    @BindView(R.id.toolbar_title)
    TextView toolbarTitle;
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.lv_department_doctor_list)
    ListView lvDepartmentDoctorList;

    private String departmentName = "选择医生";
    private String departmentCode;//部门编号

    private Context mContext;

    private ChoiceDoctorLvAdapter adapter;

    private List<BeanDoctorInfo> mDoctorInfos = new ArrayList<>();
    private List<BeanHospDeptDoctListRespItem> DeptDoctList = new ArrayList<>();

    private BeanHospRegisterReq beanHospRegisterReq;
    private BeanDoctorInfo beanDoctorInfo;

    private String hosp = "lzzyy";//默认
    private String hospTxt = "柳州市中医院";//默认


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_department_doctor_list);
        ButterKnife.bind(this);
        initToolBar(toolbar,toolbarTitle,departmentName);
        mContext = this;
        getDepartmentName(); //从上一页面获取传递过来的参数
        initData(); //获取该科室的医生信息
        initListView(); //初始化医生列表并设置点击监听
    }

    /**
     * 初始化ListView
     */
    private void initListView() {
        adapter = new ChoiceDoctorLvAdapter(mContext, mDoctorInfos);

        lvDepartmentDoctorList.setAdapter(adapter);
        lvDepartmentDoctorList.setVerticalScrollBarEnabled(false);
        lvDepartmentDoctorList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                //进入到医生详情页面，进行预约时间的选择
                beanDoctorInfo = new BeanDoctorInfo();
                beanDoctorInfo.setHosp(hosp);
                beanDoctorInfo.setHospital(hospTxt);
                beanDoctorInfo.setDept(departmentCode);
                beanDoctorInfo.setDepartment(departmentName);
                beanDoctorInfo.setName(DeptDoctList.get(position).getDOCTOR_NAME());
                beanDoctorInfo.setDOCTOR(DeptDoctList.get(position).getDOCTOR());
                beanDoctorInfo.setIntroduce(DeptDoctList.get(position).getWEB_INTRODUCE());
                beanDoctorInfo.setWORK_TYPE(DeptDoctList.get(position).getWORK_TYPE());
                beanDoctorInfo.setSTAFF_NO(DeptDoctList.get(position).getSTAFF_NO());
                beanDoctorInfo.setCLINIQUE_CODE(DeptDoctList.get(position).getCLINIQUE_CODE());
                beanDoctorInfo.setDuties(DeptDoctList.get(position).getREISTER_NAME());
                beanDoctorInfo.setImgUrl(DeptDoctList.get(position).getZHAOPIAN());
                beanDoctorInfo.setPRE_ALLOW(DeptDoctList.get(position).getPRE_ALLOW());
                beanDoctorInfo.setPrice(String.valueOf(DeptDoctList.get(position).getPRICE()));
                beanDoctorInfo.setSchdList(DeptDoctList.get(position).getSchdList());

                Intent intent = new Intent(DepartmentDoctorList.this,DoctorDetail.class);
                intent.putExtra("BeanDoctorInfo", beanDoctorInfo); //将医生信息传递到下一页面
                startActivity(intent);
            }
        });
    }

    /**
     * 获取上一页面传过来的科室名
     */
    private void getDepartmentName() {
        beanHospRegisterReq = (BeanHospRegisterReq) getIntent().getSerializableExtra("BeanHospRegisterReq");
        if (beanHospRegisterReq!= null){
            departmentName = beanHospRegisterReq.getDeptTxt();
            departmentCode = beanHospRegisterReq.getDept();
            hosp = beanHospRegisterReq.getHosp();
            hospTxt = beanHospRegisterReq.getHospTxt();
        }
    }

    /**
     * 初始化科室医生数据
     */
    private void initData() {

        final BeanHospDeptDoctListReq beanHospDeptDoctListReq = new BeanHospDeptDoctListReq();
        beanHospDeptDoctListReq.setHosp(hosp); //医院编号id
        beanHospDeptDoctListReq.setDept(departmentCode); //部门编号

        RetrofitManagerUtils.getInstance(this, null)
                .getHealthyInfoByRetrofit(OkHttpUtils.getRequestBody(beanHospDeptDoctListReq), new Subscriber<ResponseBody>() {
                    @Override
                    public void onCompleted() {
                        adapter.notifyDataSetChanged();
                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onNext(ResponseBody responseBody) {
                        String jsonStr = null;
                        try {
                            jsonStr = responseBody.string();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                        Log.e("LYQ", jsonStr);
                        List<JSONObject> doctorList = JSONArray.parseObject(jsonStr,List.class);
                        for (JSONObject  object :doctorList) {
                            String jsonString = object.toJSONString();
                            BeanHospDeptDoctListRespItem beanHospDeptListRespItem = JSON.parseObject(jsonString, BeanHospDeptDoctListRespItem.class);
                            DeptDoctList.add(beanHospDeptListRespItem); //用于传递数据到下一页面用的list
                        }
                        Collections.sort(DeptDoctList,new DoctorPostComparator());//按职称排序
                        for (BeanHospDeptDoctListRespItem beanHospDeptListRespItem : DeptDoctList){
                            BeanDoctorInfo data = new BeanDoctorInfo();
                            data.setImgUrl(HttpHealthyFishyUrl+beanHospDeptListRespItem.getZHAOPIAN());
                            data.setName(beanHospDeptListRespItem.getDOCTOR_NAME());
                            data.setDepartment("诊室："+beanHospDeptListRespItem.getCLINIQUE_CODE());
                            data.setDuties(beanHospDeptListRespItem.getREISTER_NAME());
                            data.setHospital(hospTxt);
                            data.setIntroduce(beanHospDeptListRespItem.getWEB_INTRODUCE());
                            data.setPrice(beanHospDeptListRespItem.getPRICE()+"元");
                            mDoctorInfos.add(data);///用于展示医生信息用的list
                        }
                    }
                });

    }
}
