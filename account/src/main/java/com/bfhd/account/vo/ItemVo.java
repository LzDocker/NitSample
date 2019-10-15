package com.bfhd.account.vo;



public class ItemVo {
    public long id;
    public String name;
    public String phone;
    public String imgUrl;
    public String remark ;

    public ItemVo(String name, String phone, String remark) {
        this.name = name;
        this.phone = phone;
        this.remark = remark;
    }
}