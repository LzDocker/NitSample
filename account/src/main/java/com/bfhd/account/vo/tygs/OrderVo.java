package com.bfhd.account.vo.tygs;

import android.view.View;

import com.bfhd.account.R;
import com.bfhd.account.vo.MyInfoVo;
import com.docker.common.common.vo.card.BaseCardVo;

public class OrderVo extends BaseCardVo<MyInfoVo> {

    public OrderVo(int style, int position) {
        super(style, position);
    }

    @Override
    public void onItemClick(BaseCardVo item, View view) {

    }

    @Override
    public int getItemLayout() {
        return R.layout.account_tygs_item_order;
    }

    /**
     * app_id : 4
     * memberid : 3
     * circleid : 27
     * real_price : 0.01
     * status : 1
     * pay_time : 0
     * payStatus : 0
     * effect_time : 0
     * shop_name : 安全培训
     * img : /var/upload/image/2019/06/2019061809491565159_290x179.png
     */

    private String app_id;
    private String memberid;
    private String circleid;
    private String real_price;
    private int status;
    private String pay_time;
    private String payStatus;
    private String effect_time;
    private String shop_name;
    private String img;
    private String payment;  //支付类型微信|1 支付宝|2 银联|3 积分|4 线下转账|5

    private String integral;

    public String getIntegral() {
        return integral;
    }

    public void setIntegral(String integral) {
        this.integral = integral;
    }

    public String getPayment() {
        return payment;
    }

    public void setPayment(String payment) {
        this.payment = payment;
    }

    public String getApp_id() {
        return app_id;
    }

    public void setApp_id(String app_id) {
        this.app_id = app_id;
    }

    public String getMemberid() {
        return memberid;
    }

    public void setMemberid(String memberid) {
        this.memberid = memberid;
    }

    public String getCircleid() {
        return circleid;
    }

    public void setCircleid(String circleid) {
        this.circleid = circleid;
    }

    public String getReal_price() {
        return real_price;
    }

    public void setReal_price(String real_price) {
        this.real_price = real_price;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getPay_time() {
        return pay_time;
    }

    public void setPay_time(String pay_time) {
        this.pay_time = pay_time;
    }

    public String getPayStatus() {
        return payStatus;
    }

    public void setPayStatus(String payStatus) {
        this.payStatus = payStatus;
    }

    public String getEffect_time() {
        return effect_time;
    }

    public void setEffect_time(String effect_time) {
        this.effect_time = effect_time;
    }

    public String getShop_name() {
        return shop_name;
    }

    public void setShop_name(String shop_name) {
        this.shop_name = shop_name;
    }

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
    }


}
