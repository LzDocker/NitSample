package com.bfhd.account.vo;

import android.databinding.BaseObservable;
import android.databinding.Bindable;

import java.io.Serializable;

public class FansVo extends BaseObservable implements Serializable {


    private String memberid;
    private String uuid;
    private String nickname;
    private String avatar;
    @Bindable
    private int isFocus;
    @Bindable
    private int isFocusMe;
    private String fansNum;
    private String circleName;

    public String getCircleName() {
        return circleName;
    }

    public void setCircleName(String circleName) {
        this.circleName = circleName;
    }

    public String getMemberid() {
        return memberid;
    }

    public void setMemberid(String memberid) {
        this.memberid = memberid;
    }

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
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
    @Bindable
    public int getIsFocus() {
        return isFocus;
    }
    @Bindable
    public void setIsFocus(int isFocus) {
        this.isFocus = isFocus;
    }
    @Bindable
    public int getIsFocusMe() {
        return isFocusMe;
    }
    @Bindable
    public void setIsFocusMe(int isFocusMe) {
        this.isFocusMe = isFocusMe;
    }

    public String getFansNum() {
        return fansNum;
    }

    public void setFansNum(String fansNum) {
        this.fansNum = fansNum;
    }
}
