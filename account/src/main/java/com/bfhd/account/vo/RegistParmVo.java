package com.bfhd.account.vo;

import android.databinding.BaseObservable;

import java.io.Serializable;

public class RegistParmVo extends BaseObservable implements Serializable {

    private String phone;
    private String smscode;
    private String pwd;

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getSmscode() {
        return smscode;
    }

    public void setSmscode(String smscode) {
        this.smscode = smscode;
    }

    public String getPwd() {
        return pwd;
    }

    public void setPwd(String pwd) {
        this.pwd = pwd;
    }
}
