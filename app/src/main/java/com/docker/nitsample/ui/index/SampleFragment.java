package com.docker.nitsample.ui.index;

import android.arch.lifecycle.ViewModelProviders;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;

import com.docker.common.common.model.CommonListReq;
import com.docker.common.common.ui.base.NitCommonListFragment;
import com.docker.nitsample.vm.SampleListViewModel;

public class SampleFragment extends NitCommonListFragment<SampleListViewModel> {

    CommonListReq commonListReq = new CommonListReq();

    public static SampleFragment newInstance() {
        return new SampleFragment();
    }

    @Override
    public SampleListViewModel getViewModel() {
        return ViewModelProviders.of(this, factory).get(SampleListViewModel.class);
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceStates) {
        super.onCreate(savedInstanceStates);

    }

    @Override
    protected void initView(View var1) {

    }

    @Override
    public CommonListReq getArgument() {
        commonListReq.refreshState = 3;
        commonListReq.RvUi = 0;
        return commonListReq;
    }

    @Override
    public void initImmersionBar() {
    }
}
