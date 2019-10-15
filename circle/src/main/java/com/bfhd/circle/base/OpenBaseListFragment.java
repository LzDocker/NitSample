package com.bfhd.circle.base;

import android.os.Bundle;
import android.view.View;
import com.bfhd.circle.R;
import com.bfhd.circle.databinding.OpenActivityBaseListBinding;

import javax.inject.Inject;

/*
 * Base 列表
 **/
public abstract class OpenBaseListFragment<VM extends HivsBaseViewModel> extends CommonFragment<VM, OpenActivityBaseListBinding> {

    @Override
    protected int getLayoutId() {
        return R.layout.open_activity_base_list;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    protected void initView(View var1) {

        if (setViewmodel() == null) {
        } else {
            mBinding.get().setViewmodel(setViewmodel());
        }
    }

    public abstract HivsBaseViewModel setViewmodel();
}
