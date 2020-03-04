package com.docker.common.common.vo.entity;

public class ParamsBean {
    /**
     * act : dynamicCollect
     * type : dynamic
     * memberid : 2
     * uuid : 5f0fb1927a454ff311a46b0d2e20c453
     * dynamicid : 10585
     * dataid : 41
     */

    private String act;
    private String type;
    private String memberid;
    private String uuid;
    private String dynamicid;
    private String dataid;
    private String push_memberid;
    private String push_uuid;
    private String goodsid;
    public String orderid;
    public String status;

    public String utid;
    public String circleid;
    public String fullName;


    public String getPush_memberid() {
        return push_memberid;
    }

    public void setPush_memberid(String push_memberid) {
        this.push_memberid = push_memberid;
    }

    public String getPush_uuid() {
        return push_uuid;
    }

    public void setPush_uuid(String push_uuid) {
        this.push_uuid = push_uuid;
    }

    public String getGoodsid() {
        return goodsid;
    }

    public void setGoodsid(String goodsid) {
        this.goodsid = goodsid;
    }

    public String getAct() {
        return act;
    }

    public void setAct(String act) {
        this.act = act;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
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

    public String getDynamicid() {
        return dynamicid;
    }

    public void setDynamicid(String dynamicid) {
        this.dynamicid = dynamicid;
    }

    public String getDataid() {
        return dataid;
    }

    public void setDataid(String dataid) {
        this.dataid = dataid;
    }
}