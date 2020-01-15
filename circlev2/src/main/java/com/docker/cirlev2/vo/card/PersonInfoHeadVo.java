package com.docker.cirlev2.vo.card;

import android.databinding.BaseObservable;
import android.databinding.Bindable;
import android.view.View;

import com.docker.cirlev2.R;
import com.docker.common.common.vo.card.BaseCardVo;

public class PersonInfoHeadVo extends BaseObservable {


    public String focusNum;
    public String fansNum;
    public String circleNum;
    public String dynamicNum;
    public String nickname;
    public String avatar;
    public String backgroundImg;
    public String favourNum;


    public String circleid;
    public String utid;

    public String reg_type;

    public boolean isSelf = false;

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
