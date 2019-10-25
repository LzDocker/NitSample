package com.docker.common.common.vo;

import java.io.Serializable;

public class AddressVo implements Serializable {

    private String id;
    private String name;
    private String phone;
    private String address;//详细地址
    private String is_moren;//是否默认 1 默认 0 非默认
    private String location;//所在地区
    private boolean isCheck;
    private String region1;
    private String region2;
    private String region3;

    public String getRegion1() {
        return region1;
    }

    public void setRegion1(String region1) {
        this.region1 = region1;
    }

    public String getRegion2() {
        return region2;
    }

    public void setRegion2(String region2) {
        this.region2 = region2;
    }

    public String getRegion3() {
        return region3;
    }

    public void setRegion3(String region3) {
        this.region3 = region3;
    }

    ;

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public boolean isCheck() {
        return isCheck;
    }

    public void setCheck(boolean check) {
        isCheck = check;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getIs_moren() {
        return is_moren;
    }

    public void setIs_moren(String is_moren) {
        this.is_moren = is_moren;
    }
}
