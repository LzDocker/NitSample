package com.docker.common.common.vo.servervo.vo;


import android.databinding.BaseObservable;
import com.alibaba.android.arouter.launcher.ARouter;
import com.docker.common.common.model.OnItemClickListener;
import com.docker.common.common.router.AppRouter;

public class DynamicDataBase extends BaseObservable {


    public OnItemClickListener getOnItemClickListener() {
        return (item, view) -> ARouter.getInstance().build(AppRouter.CIRCLE_dynamic_v2_detail).withString("dynamicId", this.getDynamicid()).navigation();
    }

    public String type;

    public ExtDataBase extData;


    public String dynamicid;  // "27",
    public String memberid;  // "3",
    public String uuid;  // "b9bf86035e086493575dba1e79ebda92",
    public String circleid;  // "5",
    public String utid;  // "fcb19cb4771ad2deb7255128c16d3bfe",
    public String istop;  // "0",
    public String inputtime;  // "1566790229",
    public String classid;  // "3412",
    public String classid2;  // "0",
    public String dataid;  // "8",
    public String dis;  // "18.718626256207717",
    public String commentNum;  // "0",
    public String favourNum;  // "0",
    public String seeNum;  // 495,
    public String avatar;  // "",
    public String circleName;  // "官方圈",
    public String nickname;  // "",
    public String country;  // "中国",
    public String province;  // "北京市",
    public String city;  // "北京市",
    public String district;  // "海淀区",
    public String new_content;


    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public ExtDataBase getExtData() {
        return extData;
    }

    public void setExtData(ExtDataBase extData) {
        this.extData = extData;
    }

    public String getDynamicid() {
        return dynamicid;
    }

    public void setDynamicid(String dynamicid) {
        this.dynamicid = dynamicid;
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

    public String getCircleid() {
        return circleid;
    }

    public void setCircleid(String circleid) {
        this.circleid = circleid;
    }

    public String getUtid() {
        return utid;
    }

    public void setUtid(String utid) {
        this.utid = utid;
    }

    public String getIstop() {
        return istop;
    }

    public void setIstop(String istop) {
        this.istop = istop;
    }

    public String getInputtime() {
        return inputtime;
    }

    public void setInputtime(String inputtime) {
        this.inputtime = inputtime;
    }

    public String getClassid() {
        return classid;
    }

    public void setClassid(String classid) {
        this.classid = classid;
    }

    public String getClassid2() {
        return classid2;
    }

    public void setClassid2(String classid2) {
        this.classid2 = classid2;
    }

    public String getDataid() {
        return dataid;
    }

    public void setDataid(String dataid) {
        this.dataid = dataid;
    }

    public String getDis() {
        return dis;
    }

    public void setDis(String dis) {
        this.dis = dis;
    }

    public String getCommentNum() {
        return commentNum;
    }

    public void setCommentNum(String commentNum) {
        this.commentNum = commentNum;
    }

    public String getFavourNum() {
        return favourNum;
    }

    public void setFavourNum(String favourNum) {
        this.favourNum = favourNum;
    }

    public String getSeeNum() {
        return seeNum;
    }

    public void setSeeNum(String seeNum) {
        this.seeNum = seeNum;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public String getCircleName() {
        return circleName;
    }

    public void setCircleName(String circleName) {
        this.circleName = circleName;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getProvince() {
        return province;
    }

    public void setProvince(String province) {
        this.province = province;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getDistrict() {
        return district;
    }

    public void setDistrict(String district) {
        this.district = district;
    }

    public String getNew_content() {
        return new_content;
    }

    public void setNew_content(String new_content) {
        this.new_content = new_content;
    }
}
