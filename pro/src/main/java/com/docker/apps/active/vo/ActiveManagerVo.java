package com.docker.apps.active.vo;

import android.databinding.BaseObservable;
import android.databinding.Bindable;

import com.docker.apps.BR;

public class ActiveManagerVo extends BaseObservable {

    public ActiveManagerVo(int icon, String title, int id) {
        this.icon = icon;
        this.title = title;
        this.id = id;
    }

    public int icon;

    @Bindable
    public String title;
    public int id;

    @Bindable
    public int rotation = 0;

    @Bindable
    public int getRotation() {
        return rotation;
    }

    @Bindable
    public void setRotation(int rotation) {
        this.rotation = rotation;
        notifyPropertyChanged(BR.rotation);
    }

    @Bindable
    public String getTitle() {
        return title;
    }

    @Bindable
    public void setTitle(String title) {
        this.title = title;
        notifyPropertyChanged(BR.title);
    }
}
