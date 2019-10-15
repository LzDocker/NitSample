package com.bfhd.circle.vo;

import android.databinding.BaseObservable;
import android.databinding.Bindable;

import java.io.Serializable;
import java.util.List;

public class CircleDetailVo extends BaseObservable implements Serializable {


    /**
     * teamName : 明媚半永久
     * surfaceImg :
     * intro :
     * memberid : 1320
     * nickname : 汪斌
     * avatar : /var/upload/image/2017/10/2017102618493823650.jpg
     * employee : [{"avatar":""},{"avatar":""}]
     * visitTotal : 0
     * yesterdayVisit : 0
     * employeeNum :
     * isJoin : 1
     * role : 1
     * share : {"shareUrl":"","shareTit":"","shareDesc":"","shareImg":""}
     */

    //safe
    private String uuid;
    private String seeNum;

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    public String getSeeNum() {
        return seeNum;
    }

    public void setSeeNum(String seeNum) {
        this.seeNum = seeNum;
    }


    private String circleName;
    private String surfaceImg;
    private String intro;
    private String memberid;
    private String nickname;
    private String avatar;
    private String visitTotal;
    private String yesterdayVisit;

    @Bindable
    private String employeeNum;


    @Bindable
    private String isJoin;

    private String type;
    private int role;
    private ShareVo share;
    private List<EmployeeBean> employee;
    //圈子logo
    private String logoUrl;
    private String isPublishDynamic;//是否允许发布动态
    private String isComment;//是否允许评论
    /**
     * companyName :
     * contact :
     * address :
     * industry1 : 3440
     * industry2 : 3546
     * industryStr : 运动健身 - 健身中心
     */

    private String companyName;
    private String contact;
    private String address;
    private String industry1;
    private String industry2;
    private String industryStr;
    private String classidStr;

    public String getClassidStr() {
        return classidStr;
    }

    public void setClassidStr(String classidStr) {
        this.classidStr = classidStr;
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

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getCircleName() {
        return circleName;
    }

    public void setCircleName(String circleName) {
        this.circleName = circleName;
    }

    public String getSurfaceImg() {
        return surfaceImg;
    }

    public void setSurfaceImg(String surfaceImg) {
        this.surfaceImg = surfaceImg;
    }

    public String getIntro() {
        return intro;
    }

    public void setIntro(String intro) {
        this.intro = intro;
    }

    public String getMemberid() {
        return memberid;
    }

    public void setMemberid(String memberid) {
        this.memberid = memberid;
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

    public String getVisitTotal() {
        return visitTotal;
    }

    public void setVisitTotal(String visitTotal) {
        this.visitTotal = visitTotal;
    }

    public String getYesterdayVisit() {
        return yesterdayVisit;
    }

    public void setYesterdayVisit(String yesterdayVisit) {
        this.yesterdayVisit = yesterdayVisit;
    }

    @Bindable
    public String getEmployeeNum() {
        return employeeNum;
    }

    @Bindable
    public void setEmployeeNum(String employeeNum) {
        this.employeeNum = employeeNum;
    }

    @Bindable
    public String getIsJoin() {
        return isJoin;
    }

    @Bindable
    public void setIsJoin(String isJoin) {
        this.isJoin = isJoin;
    }

    public int getRole() {
        return role;
    }

    public void setRole(int role) {
        this.role = role;
    }

    public ShareVo getShare() {
        return share;
    }

    public void setShare(ShareVo share) {
        this.share = share;
    }

    public List<EmployeeBean> getEmployee() {
        return employee;
    }

    public void setEmployee(List<EmployeeBean> employee) {
        this.employee = employee;
    }

    public String getLogoUrl() {
        return logoUrl;
    }

    public void setLogoUrl(String logoUrl) {
        this.logoUrl = logoUrl;
    }

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public String getContact() {
        return contact;
    }

    public void setContact(String contact) {
        this.contact = contact;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getIndustry1() {
        return industry1;
    }

    public void setIndustry1(String industry1) {
        this.industry1 = industry1;
    }

    public String getIndustry2() {
        return industry2;
    }

    public void setIndustry2(String industry2) {
        this.industry2 = industry2;
    }

    public String getIndustryStr() {
        return industryStr;
    }

    public void setIndustryStr(String industryStr) {
        this.industryStr = industryStr;
    }

    public static class EmployeeBean implements Serializable {
        /**
         * avatar :
         */

        private String avatar;

        public String getAvatar() {
            return avatar;
        }

        public void setAvatar(String avatar) {
            this.avatar = avatar;
        }
    }


}
