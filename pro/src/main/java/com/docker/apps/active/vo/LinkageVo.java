package com.docker.apps.active.vo;

import android.databinding.Bindable;

import com.docker.apps.BR;
import com.docker.apps.R;
import com.docker.common.common.model.BaseSampleItem;
import com.docker.common.common.model.OnItemClickListener;

public class LinkageVo extends BaseSampleItem {

/*
*  "linkageid": "3472",
        "parentid": "0",
        "name": "闲置物品",
        "img": "\/var\/upload\/image\/2019\/08\/2019082318080725276_80x80.png",
        "recommend": "0"
* */

    public String linkageid;
    public String parentid;
    public String name;
    public String img;
    public String recommend;

    public String getName() {
        return name;
    }

    @Bindable
    private boolean checked;

    @Bindable
    public boolean isChecked() {
        return checked;
    }

    @Bindable
    public void setChecked(boolean checked) {
        this.checked = checked;
        notifyPropertyChanged(BR.checked);
    }

    @Override
    public int getItemLayout() {
        return R.layout.pro_active_link_type;
    }

    @Override
    public OnItemClickListener getOnItemClickListener() {
        return (item, view) -> {

        };
    }
}
