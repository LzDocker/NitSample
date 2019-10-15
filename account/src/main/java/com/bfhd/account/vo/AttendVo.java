package com.bfhd.account.vo;

public class AttendVo {
    /**
     * id : 3
     * inputtime : 1558944625
     * memberid : 3
     * integral : 5
     * lat :
     * lng :
     * attend_date : 2019-05-27
     */

    private String id;
    private String inputtime;
    private String memberid;
    private String integral;
    private String lat;
    private String lng;
    private String attend_date;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getInputtime() {
        return inputtime;
    }

    public void setInputtime(String inputtime) {
        this.inputtime = inputtime;
    }

    public String getMemberid() {
        return memberid;
    }

    public void setMemberid(String memberid) {
        this.memberid = memberid;
    }

    public String getIntegral() {
        return integral;
    }

    public void setIntegral(String integral) {
        this.integral = integral;
    }

    public String getLat() {
        return lat;
    }

    public void setLat(String lat) {
        this.lat = lat;
    }

    public String getLng() {
        return lng;
    }

    public void setLng(String lng) {
        this.lng = lng;
    }

    public String getAttend_date() {
        return attend_date;
    }

    public void setAttend_date(String attend_date) {
        this.attend_date = attend_date;
    }

    //{"id":"3","inputtime":"1558944625","memberid":"3","integral":"5","lat":"","lng":"","attend_date":"2019-05-27"}
}
