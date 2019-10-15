package com.bfhd.account.vo;

import android.databinding.BaseObservable;

import java.io.Serializable;

public class NoSeeVo extends BaseObservable implements Serializable {


    /**
     * id : 1
     * black_memberid : 2
     * memberid : 1
     * inputtime : 1567737415
     * avatar :
     * fullName :
     */

    private String id;
    private String black_memberid;
    private String memberid;
    private String inputtime;
    private String avatar;
    private String fullName;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getBlack_memberid() {
        return black_memberid;
    }

    public void setBlack_memberid(String black_memberid) {
        this.black_memberid = black_memberid;
    }

    public String getMemberid() {
        return memberid;
    }

    public void setMemberid(String memberid) {
        this.memberid = memberid;
    }

    public String getInputtime() {
        return inputtime;
    }

    public void setInputtime(String inputtime) {
        this.inputtime = inputtime;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }
}
