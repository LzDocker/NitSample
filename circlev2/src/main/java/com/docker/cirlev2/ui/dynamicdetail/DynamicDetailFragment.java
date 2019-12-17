package com.docker.cirlev2.ui.dynamicdetail;

import android.arch.lifecycle.ViewModelProviders;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;

import com.docker.cirlev2.R;
import com.docker.cirlev2.databinding.Circlev2FragmentDetailH5Binding;
import com.docker.cirlev2.vm.SampleListViewModel;
import com.docker.cirlev2.vo.entity.ServiceDataBean;
import com.docker.common.common.ui.base.NitCommonFragment;

public class DynamicDetailFragment extends NitCommonFragment<SampleListViewModel, Circlev2FragmentDetailH5Binding> {

    public ServiceDataBean serviceDataBean;

    public static DynamicDetailFragment getInstance(ServiceDataBean serviceDataBean) {
        DynamicDetailFragment dynamicH5Fragment = new DynamicDetailFragment();
        Bundle bundle = new Bundle();
        bundle.putSerializable("dataSource", serviceDataBean);
        dynamicH5Fragment.setArguments(bundle);
        return dynamicH5Fragment;
    }

    @Override
    protected int getLayoutId() {
        return R.layout.circlev2_fragment_detail_h5;
    }

    @Override
    protected SampleListViewModel getViewModel() {
        return ViewModelProviders.of(this, factory).get(SampleListViewModel.class);
    }

    @Override
    protected void initView(View var1) {

    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        serviceDataBean = (ServiceDataBean) getArguments().getSerializable("dataSource");
        mBinding.get().setItem(serviceDataBean);
    }

    @Override
    public void initImmersionBar() {

    }

}
