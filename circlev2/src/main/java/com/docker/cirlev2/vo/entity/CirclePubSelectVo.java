package com.docker.cirlev2.vo.entity;

import android.databinding.Bindable;
import android.view.View;

import com.docker.cirlev2.BR;
import com.docker.cirlev2.R;
import com.docker.cirlev2.vm.CirlcleSelectViewModel;

import timber.log.Timber;

public class CirclePubSelectVo extends CircleListVo {

    @Override
    public int getItemLayout() {
        return R.layout.circlev2_item_select_circle;
    }


    /*

    R.mipmap.class_check_true :
    */

    @Bindable
    private int selectsource = R.mipmap.class_check_false;

    @Bindable
    public int getSelectsource() {
        return selectsource;
    }

    public void setSelectsource(int selectsource) {
        this.selectsource = selectsource;
        notifyPropertyChanged(BR.selectsource);
    }
}
