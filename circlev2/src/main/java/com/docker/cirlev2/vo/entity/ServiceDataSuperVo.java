package com.docker.cirlev2.vo.entity;

import android.databinding.Bindable;
import android.databinding.ObservableField;

import com.alibaba.android.arouter.launcher.ARouter;
import com.docker.cirlev2.BR;
import com.docker.cirlev2.R;
import com.docker.common.common.model.BaseSampleItem;
import com.docker.common.common.model.OnItemClickListener;
import com.docker.common.common.router.AppRouter;

import java.io.Serializable;
import java.util.List;

import me.tatarka.bindingcollectionadapter2.ItemBinding;

public abstract class ServiceDataSuperVo extends BaseSampleItem implements Serializable {

    public int flag = 0;
    public boolean isSelect;
    public String country;
    public String province;
    public String city;
    public String district;
    public String autotrophy;   //1 自营

    @Bindable
    public int num;

    @Bindable
    public int getNum() {
        return num;
    }

    @Bindable
    public void setNum(int num) {
        this.num = num;
    }

    public boolean isSelect() {
        return isSelect;
    }

    public void setSelect(boolean select) {
        isSelect = select;
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

    public String dynamicid;
    public String memberid;
    public String uuid;
    public String circleid;
    public String utid;
    public String type;


    @Bindable
    public int favourNum = 0;
    public String commentNum;
    public String viewNum;
    public String istop;
    public String inputtime;
    public String classid;
    public String classid2;
    public String dataid;
    public String avatar;
    public String nickname;
    public String circleName;


    public String area1;
    public String area2;
    public String area3;

    public String employeeNum;

    public String getEmployeeNum() {
        return employeeNum;
    }

    public void setEmployeeNum(String employeeNum) {
        this.employeeNum = employeeNum;
    }

    @Bindable
    public int isFav;

    public String content;


    /**
     * 图书列表
     * <p>
     * bookid : 1
     * name : 好的好多话没有
     * thumb : /var/upload/image/2018/08/2018081516114554050_250x149.png
     * publishing : jfjdj
     * publish_time : 1534320658.251608
     * pricing : 100.00
     * discount : 9.9
     * companyName : 好的好的好
     */

    public String bookid;
    public String name;
    public String thumb;
    public String publishing;
    public String publish_time;
    public String pricing;
    public String discount;
    public String companyName;


    // 动态详情 -------------------------------------------
    public String seeNum = "";//浏览量
    public String cityName;

    @Bindable
    public int isFocus;


    @Bindable
    public int isCollect;


    public String getCityName() {
        return cityName;
    }

    public void setCityName(String cityName) {
        this.cityName = cityName;
    }

    @Bindable
    public int getIsCollect() {

        return isCollect;
    }

    @Bindable
    public void setIsCollect(int isCollect) {
        this.isCollect = isCollect;
    }

    public String getSeeNum() {
        return seeNum;
    }

    public void setSeeNum(String seeNum) {
        this.seeNum = seeNum;
    }


    @Bindable
    public int getIsFocus() {
        return isFocus;
    }

    @Bindable
    public void setIsFocus(int isFocus) {
        this.isFocus = isFocus;
    }
    //------------------------------------------------------


    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
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

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    @Bindable
    public int getFavourNum() {
        return favourNum;
    }

    @Bindable
    public void setFavourNum(int favourNum) {
        this.favourNum = favourNum;
    }

    public String getCommentNum() {
        return commentNum;
    }

    public void setCommentNum(String commentNum) {
        this.commentNum = commentNum;
    }

    public String getViewNum() {
        return viewNum;
    }

    public void setViewNum(String viewNum) {
        this.viewNum = viewNum;
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

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public String getCircleName() {
        return circleName;
    }

    public void setCircleName(String circleName) {
        this.circleName = circleName;
    }

    public int getIsFav() {
        return isFav;
    }

    @Bindable
    public void setIsFav(int isFav) {
        this.isFav = isFav;
        notifyPropertyChanged(BR.isFav);
    }


    public String getBookid() {
        return bookid;
    }

    public void setBookid(String bookid) {
        this.bookid = bookid;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getThumb() {
        return thumb;
    }

    public void setThumb(String thumb) {
        this.thumb = thumb;
    }

    public String getPublishing() {
        return publishing;
    }

    public void setPublishing(String publishing) {
        this.publishing = publishing;
    }

    public String getPublish_time() {
        return publish_time;
    }

    public void setPublish_time(String publish_time) {
        this.publish_time = publish_time;
    }

    public String getPricing() {
        return pricing;
    }

    public void setPricing(String pricing) {
        this.pricing = pricing;
    }

    public String getDiscount() {
        return discount;
    }

    public void setDiscount(String discount) {
        this.discount = discount;
    }

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }


}
