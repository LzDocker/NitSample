package com.docker.cirlev2.vo.card;

import android.databinding.BaseObservable;
import android.databinding.Bindable;
import android.view.View;

import com.docker.cirlev2.BR;
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

    public String labels;
    public String content;

    public String memberid;

    public String uuid;

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

    public String circleid;
    public String utid;

    public String reg_type;

    public boolean isSelf = false;

    @Bindable
    public boolean getIsSelf() {
        return isSelf;
    }

    @Bindable
    public void setIsSelf(boolean self) {
        isSelf = self;
        notifyPropertyChanged(BR.isSelf);
    }

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
