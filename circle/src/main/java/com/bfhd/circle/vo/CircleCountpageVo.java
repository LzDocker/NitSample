package com.bfhd.circle.vo;

import android.databinding.BaseObservable;
import android.databinding.Bindable;

import java.io.Serializable;

public class CircleCountpageVo extends BaseObservable implements Serializable {


    /*"focusNum": "1",
		"fansNum": "0",
		"circleNum": "7",
		"dynamicNum": "13",
		"nickname": "\u8d75\u6c38\u7131",
		"avatar": "",
		"backgroundImg": "",
		"isFocus": "0"*/


    public String focusNum;
    public String fansNum;
    public String circleNum;
    public String dynamicNum;
    public String nickname;
    public String avatar;
    public String backgroundImg;

    public String circleid;
    public String utid;

    public String reg_type;

    @Bindable
    public int isFocus;

    @Bindable
    public int getIsFocus() {
        return isFocus;
    }

    @Bindable
    public void setIsFocus(int isFocus) {
        this.isFocus = isFocus;
    }
}
