package com.bfhd.account.vo;

import android.databinding.BaseObservable;
import android.databinding.Bindable;
import android.text.TextUtils;

import com.bfhd.account.BR;

import java.io.Serializable;

/**
 * Created by Administrator on 2018/8/28.
 */

public class MyInfoVo extends BaseObservable implements Serializable {

    /**
     * nickname : 王老妖
     * avatar : /var/upload/image/2018/07/2018073006454615449_864x864.jpg
     * utid : 4ddd4879bcd6db6410e4c40ed9cd5272
     * job : 老王
     * circleName : 孤独的
     * viptype : 0
     * authStatus : 0
     * fansNum : 0
     * focusNum : 2
     * circleNum : 2
     */

    public String labels;

    private String nickname;

    private String avatar;
    private String utid;
    private String job;
    private String circleName;
    private String viptype;
    private String authStatus;
    private String fansNum;
    private String focusNum;
    private String circleNum;
    private String dynamicNum;

    private String invite_code;
    private String allowInvitation;
    private String nicakname;
    private String fullName;

    @Bindable
    private String notReadMsgNum;
    private String contact_us;
    private String commentNum;
    private String likeNum;
    private String systemId;
    private String reg_type;

    public String invite;
    private String point;
    public String amount;

    @Bindable
    public String inviteNum;

    @Bindable
    public String getInviteNum() {
        return inviteNum;
    }

    @Bindable
    public void setInviteNum(String inviteNum) {
        this.inviteNum = inviteNum;
        notifyPropertyChanged(BR.inviteNum);
    }

    public String getReg_type() {
        return reg_type;
    }

    public void setReg_type(String reg_type) {
        this.reg_type = reg_type;
    }

    public String getSystemId() {
        return systemId;
    }

    public void setSystemId(String systemId) {
        this.systemId = systemId;
    }

    public String getLikeNum() {
        return likeNum;
    }

    public void setLikeNum(String likeNum) {
        this.likeNum = likeNum;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getCommentNum() {
        return commentNum;
    }

    public void setCommentNum(String commentNum) {
        this.commentNum = commentNum;
    }

    public String getContact_us() {
        return contact_us;
    }

    public void setContact_us(String contact_us) {
        this.contact_us = contact_us;
    }

    @Bindable
    public String getNotReadMsgNum() {
        return notReadMsgNum;
    }

    @Bindable
    public void setNotReadMsgNum(String notReadMsgNum) {
        this.notReadMsgNum = notReadMsgNum;
        notifyPropertyChanged(BR.notReadMsgNum);
    }

    public String getNicakname() {
        if (TextUtils.isEmpty(nicakname)) {
            return fullName;
        }
        return nicakname;
    }

    public void setNicakname(String nicakname) {
        this.nicakname = nicakname;
    }

    public String getInvite_code() {
        return invite_code;
    }

    public void setInvite_code(String invite_code) {
        this.invite_code = invite_code;
    }

    public String getAllowInvitation() {
        return allowInvitation;
    }

    public void setAllowInvitation(String allowInvitation) {
        this.allowInvitation = allowInvitation;
    }

    public String getPoint() {
        return point;
    }

    public void setPoint(String point) {
        this.point = point;
    }


    public String getDynamicNum() {
        return dynamicNum;
    }

    public void setDynamicNum(String dynamicNum) {
        this.dynamicNum = dynamicNum;
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

    public String getUtid() {
        return utid;
    }

    public void setUtid(String utid) {
        this.utid = utid;
    }

    public String getJob() {
        return job;
    }

    public void setJob(String job) {
        this.job = job;
    }

    public String getCircleName() {
        return circleName;
    }

    public void setCircleName(String circleName) {
        this.circleName = circleName;
    }

    public String getViptype() {
        return viptype;
    }

    public void setViptype(String viptype) {
        this.viptype = viptype;
    }

    public String getAuthStatus() {
        return authStatus;
    }

    public void setAuthStatus(String authStatus) {
        this.authStatus = authStatus;
    }

    public String getFansNum() {
        return fansNum;
    }

    public void setFansNum(String fansNum) {
        this.fansNum = fansNum;
    }

    public String getFocusNum() {
        return focusNum;
    }

    public void setFocusNum(String focusNum) {
        this.focusNum = focusNum;
    }

    public String getCircleNum() {
        if (TextUtils.isEmpty(circleNum)) {
            return "0";
        }
        return circleNum;
    }

    public void setCircleNum(String circleNum) {
        this.circleNum = circleNum;
    }
}
