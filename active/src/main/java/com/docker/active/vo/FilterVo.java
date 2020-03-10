package com.docker.active.vo;

import android.databinding.BaseObservable;
import android.databinding.Bindable;

import com.docker.active.BR;

public class FilterVo extends BaseObservable {

    public String title;

    public int state;

    @Bindable
    public boolean checked = false;

    @Bindable
    public boolean getChecked() {
        return checked;
    }

    public void setChecked(boolean checked) {
        this.checked = checked;
        notifyPropertyChanged(BR.checked);
    }
}
