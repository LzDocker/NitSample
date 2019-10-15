package com.bfhd.circle.base;

import android.os.Bundle;
import android.view.View;

import com.bfhd.circle.R;
import com.bfhd.circle.databinding.OpenActivityBaseListBinding;

import javax.inject.Inject;

/*
 * Base 列表
 **/
public abstract class OpenBaseListActivity<VM extends HivsBaseViewModel> extends HivsBaseActivity<VM, OpenActivityBaseListBinding> {



    @Override
    protected int getLayoutId() {
        return R.layout.open_activity_base_list;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (setViewmodel() != null) {
            mBinding.setViewmodel(setViewmodel());
            mBinding.refresh.setVisibility(View.GONE);
        } else {
            mBinding.refreshAuto.setVisibility(View.GONE);
        }


    } // STOPSHIP: 2019/5/17

    public abstract HivsBaseViewModel setViewmodel();
}
