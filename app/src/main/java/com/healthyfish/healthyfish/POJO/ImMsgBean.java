package com.healthyfish.healthyfish.POJO;

import org.litepal.crud.DataSupport;

public class ImMsgBean extends DataSupport {

    public final static int CHAT_SENDER_OTHER= 0;
    public final static int CHAT_SENDER_ME = 1;

    public final static int CHAT_MSGTYPE_TEXT_RECEIVER = 10;
    public final static int CHAT_MSGTYPE_IMG_RECEIVER = 11;
    public final static int CHAT_MSGTYPE_TEXT_SENDER = 12;
    public final static int CHAT_MSGTYPE_IMG_SENDER = 13;

    public ImMsgBean() {
    }

    public ImMsgBean(String time) {
        this.time = time;
    }

    private int id;
    // 界面UI的发送类型：图片，文字
    private int msgType;
    // MQTT判断发送类型：t(text) i(image) v s(system)
    private String type;
    // 发送时间
    private String time;
    // 图片
    private String image;
    // 姓名
    private String name;
    // 发送内容
    private String content;
    // 发送主题
    private String topic;
    // 是发送者还是接收者
    private boolean isSender = false;
    // 发送状态
    private boolean isSuccess = false;
    // 是否在loading
    private boolean isLoading = false;

    public boolean isSuccess() {
        return isSuccess;
    }

    public void setSuccess(boolean success) {
        isSuccess = success;
    }

    public boolean isSender() {
        return isSender;
    }

    public void setSender(boolean sender) {
        isSender = sender;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getTopic() {
        return topic;
    }

    public void setTopic(String topic) {
        this.topic = topic;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getMsgType() {
        return msgType;
    }

    public void setMsgType(int msgType) {
        this.msgType = msgType;
    }

    public boolean isLoading() {
        return isLoading;
    }

    public void setLoading(boolean loading) {
        isLoading = loading;
    }
}