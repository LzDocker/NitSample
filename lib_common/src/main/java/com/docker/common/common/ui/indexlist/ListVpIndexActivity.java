package com.docker.common.common.ui.indexlist;

import android.arch.lifecycle.ViewModelProviders;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.docker.common.R;
import com.docker.common.common.router.AppRouter;
import com.docker.common.common.ui.base.NitCommonActivity;
import com.docker.common.common.vm.NitEmptyViewModel;
import com.docker.common.databinding.CommonIndexListMainBinding;

@Route(path = AppRouter.COMMON_LIST_INDEX)
public class ListVpIndexActivity extends NitCommonActivity<NitEmptyViewModel, CommonIndexListMainBinding> {


    @Override
    public void initView() {

    }

    @Override
    public void initObserver() {

    }

    @Override
    public void initRouter() {

    }

    @Override
    protected int getLayoutId() {
        return R.layout.common_index_list_main;
    }

    @Override
    public NitEmptyViewModel getmViewModel() {
        return ViewModelProviders.of(this, factory).get(NitEmptyViewModel.class);
    }



}

