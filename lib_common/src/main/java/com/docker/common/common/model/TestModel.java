package com.docker.common.common.model;

import android.view.View;

public class TestModel implements BaseItemModel {
    @Override
    public int getItemLayout() {
        return 0;
    }

    @Override
    public OnItemClickListener getOnItemClickListener() {
        return (view, item) -> {

        };
    }

}
