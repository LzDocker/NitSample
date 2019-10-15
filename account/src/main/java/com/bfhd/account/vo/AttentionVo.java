package com.bfhd.account.vo;

import android.databinding.BaseObservable;
import android.databinding.Bindable;
import android.databinding.ObservableInt;

import java.io.Serializable;

public class AttentionVo extends BaseObservable implements Serializable {

 /*
 * "memberid": "73",
        "uuid": "9109c584ae0fd9cdcc72b5fd4d358e8d",
        "nickname": "陈小海☀️",
        "avatar": "\/var\/upload\/image\/2017\/08\/2017080314412683391.jpg",
        "isFocus": "1",
        "isFocusMe": "1",
        "fansNum": "3"*/

    public String memberid;
    public String uuid;
    public String nickname;
    public String avatar;

    @Bindable
    public int isFocus;
    public int isFocusMe;
    public String fansNum;

    @Bindable
    public int getIsFocus() {
        return isFocus;
    }

    @Bindable
    public void setIsFocus(int isFocus) {
        this.isFocus = isFocus;
    }
}
