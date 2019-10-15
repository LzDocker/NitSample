package com.bfhd.circle.vo;

import android.databinding.BaseObservable;
import android.databinding.Bindable;

public class CirlceTypeVo extends BaseObservable {
    public String title;
    public boolean isCheck;

    public CirlceTypeVo(String title, boolean isCheck) {
        this.title = title;
        this.isCheck = isCheck;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public boolean isCheck() {
        return isCheck;
    }

    public void setCheck(boolean check) {
        isCheck = check;
    }
}
