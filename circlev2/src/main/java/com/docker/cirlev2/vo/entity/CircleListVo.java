package com.docker.cirlev2.vo.entity;

import android.databinding.Bindable;
import android.text.TextUtils;

import com.alibaba.android.arouter.launcher.ARouter;
import com.docker.cirlev2.BR;
import com.docker.cirlev2.R;
import com.docker.cirlev2.inter.CircleConfig;
import com.docker.common.common.config.Constant;
import com.docker.common.common.model.BaseSampleItem;
import com.docker.common.common.model.CommonListOptions;
import com.docker.common.common.model.OnItemClickListener;
import com.docker.common.common.router.AppRouter;

import java.io.Serializable;

import timber.log.Timber;

public class CircleListVo extends BaseSampleItem implements Serializable {

    public int style = 0;

    @Override
    public OnItemClickListener getOnItemClickListener() {
        return (item, view) -> {
            Timber.e("=========================");

            if (TextUtils.isEmpty(((CircleListVo) item).circleid)) { // 创建

                ARouter.getInstance().build(AppRouter.CIRCLE_CREATE_v2_INDEX)
                        .navigation();
            } else {  // 进入详情
//                ARouter.getInstance().build(AppRouter.CIRCLE_DETAIL_v2_INDEX_default)
//                ARouter.getInstance().build(AppRouter.CIRCLE_DETAIL_v2_INDEX_NEW_default)
//                        .withString("circleid", ((CircleListVo) item).circleid)
//                        .withString("utid", ((CircleListVo) item).utid)
//                        .withString("circletype", ((CircleListVo) item).type)
//                        .navigation();

                CircleConfig circleConfig = new CircleConfig();
                circleConfig.circleid = ((CircleListVo) item).circleid;
                circleConfig.utid = ((CircleListVo) item).utid;
                circleConfig.circleType = ((CircleListVo) item).type;
                circleConfig.Temple = 0;
                ARouter.getInstance()
                        .build(AppRouter.CIRCLE_DETAIL_v2_INDEX_NEW_default)
                        .withSerializable("circleConfig", circleConfig)
                        .navigation();
            }
        };
    }

    @Override
    public int getItemLayout() {
        int lay = R.layout.circlev2_item_mycircle;
        switch (style) {
            case 1:
                lay = R.layout.circlev2_item_nomal_circle;
                break;
        }
        return lay;
    }

    //        * teamid : 1
//            * utid :
//            * teamName : 壹哥
//     * type:官方圈0 兴趣圈1 企业圈|2
//            */
    private String utid;
    private String type;
    private int addImageRes;
    /**
     * surfaceImg :
     * isJoin : 1
     */
    private String surfaceImg;

    @Bindable
    private String isJoin;

    private String logoUrl;
    private boolean checked;


    public String is_allow_join; // 1 允许加入

    public String getIs_allow_join() {
        return is_allow_join;
    }

    public void setIs_allow_join(String is_allow_join) {
        this.is_allow_join = is_allow_join;
    }

    public String memberid;
    public String uuid;
    public String isSelect;
    public String circleid;//团队ID
    public String circleName;//	团队名称
    public String industry1;//	一级行业ID
    public String industry2;//	二级行业ID
    public String industryStr;//	行业名称
    public String role;//角色 0普通成员 1管理员
    public String isReg;//是否注册
    public String vipdate;//会员期限
    public String viptype;//会员类型
    public String id;//同teamid

    public String classid1;
    public String classid2;
    public String classStr;
    public String classStr1;
    public String authStatus;

    public String intro;

    public String getIntro() {
        return intro;
    }

    public void setIntro(String intro) {
        this.intro = intro;
    }

    @Bindable
    public boolean isCheck = false; // 前段使用字段

    @Bindable
    public boolean isCheck() {
        return isCheck;
    }

    public void setCheck(boolean check) {
        isCheck = check;
        notifyPropertyChanged(BR.isCheck);
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

    public String getIsSelect() {
        return isSelect;
    }

    public void setIsSelect(String isSelect) {
        this.isSelect = isSelect;
    }

    public String getCircleid() {
        return circleid;
    }

    public void setCircleid(String circleid) {
        this.circleid = circleid;
    }

    public String getCircleName() {
        return circleName;
    }

    public void setCircleName(String circleName) {
        this.circleName = circleName;
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

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public String getIsReg() {
        return isReg;
    }

    public void setIsReg(String isReg) {
        this.isReg = isReg;
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

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public boolean isChecked() {
        return checked;
    }

    public void setChecked(boolean checked) {
        this.checked = checked;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getUtid() {
        return utid;
    }

    public void setUtid(String utid) {
        this.utid = utid;
    }


    public int getAddImageRes() {
        return addImageRes;
    }

    public void setAddImageRes(int addImageRes) {
        this.addImageRes = addImageRes;
    }

    public String getSurfaceImg() {
        return surfaceImg;
    }

    public void setSurfaceImg(String surfaceImg) {
        this.surfaceImg = surfaceImg;
    }

    @Bindable
    public String getIsJoin() {
        return isJoin;
    }

    @Bindable
    public void setIsJoin(String isJoin) {
        this.isJoin = isJoin;
    }

    public String getLogoUrl() {
        return logoUrl;
    }

    public void setLogoUrl(String logoUrl) {
        this.logoUrl = logoUrl;
    }

}
