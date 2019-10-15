package com.docker.nitsample.ui.index;

import android.arch.lifecycle.ViewModelProviders;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;

import com.docker.common.common.model.CommonListReq;
import com.docker.common.common.ui.base.NitCommonListFragment;
import com.docker.nitsample.vm.SampleListViewModel;
import com.docker.nitsample.vm.SampleNetListViewModel;

public class SampleListFragment extends NitCommonListFragment<SampleNetListViewModel> {

    CommonListReq commonListReq = new CommonListReq();

    public static SampleListFragment newInstance() {
        return new SampleListFragment();
    }

    @Override
    public SampleNetListViewModel getViewModel() {
        return ViewModelProviders.of(this, factory).get(SampleNetListViewModel.class);
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
        commonListReq.refreshState = 0;
        commonListReq.RvUi = 0;
        commonListReq.ReqParam.put("t", "dynamic");
        commonListReq.ReqParam.put("uuid", "8621e837a2a1579710a95143e5862424");
        commonListReq.ReqParam.put("memberid", "64");
        return commonListReq;
    }

    @Override
    public void initImmersionBar() {
    }
}
