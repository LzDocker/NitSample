package com.docker.common.common.vo;

import java.io.Serializable;

/**
 * 用于分享的bean
 * Created by Administrator on 2017/5/12 0012.
 */

public class ShareBean implements Serializable {

    /**
     * shareUrl :
     * shareTit :
     * shareDesc :
     * shareImg :
     */
    private String activityid;
    private String shortUrl;

    public String getActivityid() {
        return activityid;
    }

    public void setActivityid(String activityid) {
        this.activityid = activityid;
    }

    public String getShortUrl() {
        return shortUrl;
    }

    public void setShortUrl(String shortUrl) {
        this.shortUrl = shortUrl;
    }

    private String shareUrl;
    private String shareTit;
    private String shareDesc;
    private String shareImg;


    public String getShareUrl() {
        return shareUrl;
    }

    public void setShareUrl(String shareUrl) {
        this.shareUrl = shareUrl;
    }

    public String getShareTit() {
        return shareTit;
    }

    public void setShareTit(String shareTit) {
        this.shareTit = shareTit;
    }

    public String getShareDesc() {
        return shareDesc;
    }

    public void setShareDesc(String shareDesc) {
        this.shareDesc = shareDesc;
    }

    public String getShareImg() {
        return shareImg;
    }

    public void setShareImg(String shareImg) {
        this.shareImg = shareImg;
    }

    private String infoid;

    public String getInfoid() {
        return infoid;
    }

    public void setInfoid(String infoid) {
        this.infoid = infoid;
    }
}