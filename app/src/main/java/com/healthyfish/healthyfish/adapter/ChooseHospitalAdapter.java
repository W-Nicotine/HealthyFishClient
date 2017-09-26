package com.healthyfish.healthyfish.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.healthyfish.healthyfish.POJO.BeanHospitalListRespItem;
import com.healthyfish.healthyfish.R;
import com.zhy.autolayout.utils.AutoUtils;

import java.util.List;

import static com.healthyfish.healthyfish.constant.Constants.HttpHealthyFishyUrl;

/**
 * 描述：医院列表适配器
 * 作者：WKJ on 2017/7/10.
 * 邮箱：
 * 编辑：WKJ
 */

public class ChooseHospitalAdapter extends RecyclerView.Adapter<ChooseHospitalAdapter.ViewHolder> {
    private List<BeanHospitalListRespItem> imgUrls;//测试数据list
    private Context mContext;

    public ChooseHospitalAdapter(List<BeanHospitalListRespItem> imgUrls, Context mContext) {
        this.imgUrls = imgUrls;
        this.mContext = mContext;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View rootView = LayoutInflater.from(mContext).inflate(R.layout.item_choose_hospital, parent, false);
        return new ViewHolder(rootView);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        String url = HttpHealthyFishyUrl + imgUrls.get(position).getImg();
        Glide.with(mContext).load(url).error(R.mipmap.error).centerCrop().into(holder.hospital_pic);
        holder.hospital_name.setText(imgUrls.get(position).getName());
        holder.hospital_address.setText(imgUrls.get(position).getAddress());
    }

    @Override
    public int getItemCount() {
        return imgUrls.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private ImageView hospital_pic;
        private TextView hospital_name;
        private TextView hospital_address;

        public ViewHolder(View itemView) {
            super(itemView);
            AutoUtils.auto(itemView);
            hospital_pic = (ImageView) itemView.findViewById(R.id.iv_hospital_img);
            hospital_name = (TextView) itemView.findViewById(R.id.tv_hospital_name);
            hospital_address = (TextView) itemView.findViewById(R.id.tv_hospital_address);
        }
    }
}
