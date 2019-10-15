package com.bfhd.account.vo;


import android.databinding.BaseObservable;

import java.io.Serializable;

/**
 */
public class UserVO extends BaseObservable implements Serializable {

    private String uid;
    private String islock;
    private String reg_type;
    private String nickname;
    private String avatar;
    private String vipdate;
    private String viptype;
    private boolean ispay;
    private String perfectData;

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public String getIslock() {
        return islock;
    }

    public void setIslock(String islock) {
        this.islock = islock;
    }

    public String getReg_type() {
        return reg_type;
    }

    public void setReg_type(String reg_type) {
        this.reg_type = reg_type;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public String getVipdate() {
        return vipdate;
    }

    public void setVipdate(String vipdate) {
        this.vipdate = vipdate;
    }

    public String getViptype() {
        return viptype;
    }

    public void setViptype(String viptype) {
        this.viptype = viptype;
    }

    public boolean isIspay() {
        return ispay;
    }

    public void setIspay(boolean ispay) {
        this.ispay = ispay;
    }

    public String getPerfectData() {
        return perfectData;
    }

    public void setPerfectData(String perfectData) {
        this.perfectData = perfectData;
    }
}
