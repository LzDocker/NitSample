package com.bfhd.circle.vo;

import android.text.TextUtils;

import java.io.Serializable;

public class MemberSettingsVo implements Serializable {


    private String fullName;
    private String role;
    private String isPublishDynamic;
    private String isComment;
    private String groupid;
    private String nickname;
    private String avatar;
    private String groupName;
    private String fansNum;
    private String circleNum;
    private String dynamicNum;
    private String favourNum;
    private String commentNum;
    private String mobile;
    private String inviter_uuid;
    private String inviter;
    private String inputtime;
    private String uuid;
    private String memberid;

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    public String getMemberid() {
        return memberid;
    }

    public void setMemberid(String memberid) {
        this.memberid = memberid;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public String getIsPublishDynamic() {
        return isPublishDynamic;
    }

    public void setIsPublishDynamic(String isPublishDynamic) {
        this.isPublishDynamic = isPublishDynamic;
    }

    public String getIsComment() {
        return isComment;
    }

    public void setIsComment(String isComment) {
        this.isComment = isComment;
    }

    public String getGroupid() {
        return groupid;
    }

    public void setGroupid(String groupid) {
        this.groupid = groupid;
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

    public String getGroupName() {
        return groupName;
    }

    public void setGroupName(String groupName) {
        this.groupName = groupName;
    }

    public String getFansNum() {
        if (TextUtils.isEmpty(fansNum)) {
            return "0";
        }
        return fansNum;
    }

    public void setFansNum(String fansNum) {
        this.fansNum = fansNum;
    }

    public String getCircleNum() {
        return circleNum;
    }

    public void setCircleNum(String circleNum) {
        this.circleNum = circleNum;
    }

    public String getDynamicNum() {
        return dynamicNum;
    }

    public void setDynamicNum(String dynamicNum) {
        this.dynamicNum = dynamicNum;
    }

    public String getFavourNum() {
        return favourNum;
    }

    public void setFavourNum(String favourNum) {
        this.favourNum = favourNum;
    }

    public String getCommentNum() {
        return commentNum;
    }

    public void setCommentNum(String commentNum) {
        this.commentNum = commentNum;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getInviter_uuid() {
        return inviter_uuid;
    }

    public void setInviter_uuid(String inviter_uuid) {
        this.inviter_uuid = inviter_uuid;
    }

    public String getInviter() {
        return inviter;
    }

    public void setInviter(String inviter) {
        this.inviter = inviter;
    }

    public String getInputtime() {
        return inputtime;
    }

    public void setInputtime(String inputtime) {
        this.inputtime = inputtime;
    }

}
