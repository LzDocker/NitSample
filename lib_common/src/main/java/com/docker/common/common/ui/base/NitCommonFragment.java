package com.docker.common.common.ui.base;

import android.arch.lifecycle.ViewModelProvider;
import android.databinding.ViewDataBinding;
import com.docker.common.common.vm.NitCommonVm;
import com.docker.common.common.widget.refresh.SmartRefreshLayout;
import com.docker.core.base.BaseFragment;

import javax.inject.Inject;

public abstract class NitCommonFragment<VM extends NitCommonVm, VB extends ViewDataBinding> extends BaseFragment<VM, VB> {
    @Inject
    public ViewModelProvider.Factory factory;

    public void onLoadMore(SmartRefreshLayout refreshLayout) {
    }

    public void onReFresh(SmartRefreshLayout refreshLayout) {
    }

    public void setData(Object o) {
    }


}
