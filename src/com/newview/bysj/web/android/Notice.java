package com.newview.bysj.web.android;

/**
 * 和发送邮件有关的类
 * Created 2016/5/3,15:01.
 * Author 张战.
 */
public class Notice {

    private Integer id;
    private String title;
    private String content;
    private String noticeDate;
    private Integer version;
    private Integer addressor_id;
    private String addressor_name;
    private Integer serverId;
    private String addresseeStrId;

    public String getAddresseeStrId() {
        return addresseeStrId;
    }

    public void setAddresseeStrId(String addresseeStrId) {
        this.addresseeStrId = addresseeStrId;
    }

    public Integer getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public String getContent() {
        return content;
    }

    public String getNoticeDate() {
        return noticeDate;
    }

    public Integer getVersion() {
        return version;
    }

    public Integer getAddressor_id() {
        return addressor_id;
    }

    public String getAddressor_name() {
        return addressor_name;
    }

    public Integer getServerId() {
        return serverId;
    }
}
