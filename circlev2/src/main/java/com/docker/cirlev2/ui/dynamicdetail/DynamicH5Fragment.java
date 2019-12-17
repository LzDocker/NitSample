package com.docker.cirlev2.ui.dynamicdetail;

import android.arch.lifecycle.ViewModelProviders;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;

import com.dcbfhd.utilcode.utils.FragmentUtils;
import com.docker.cirlev2.R;
import com.docker.cirlev2.databinding.Circlev2FragmentDetailH5Binding;
import com.docker.cirlev2.databinding.Circlev2FragmentDetailH5V4Binding;
import com.docker.cirlev2.vm.CircleDynamicDetailViewModel;
import com.docker.cirlev2.vm.SampleListViewModel;
import com.docker.cirlev2.vo.entity.ServiceDataBean;
import com.docker.common.common.ui.base.NitCommonFragment;

public class DynamicH5Fragment extends NitCommonFragment<CircleDynamicDetailViewModel, Circlev2FragmentDetailH5V4Binding> {

    public ServiceDataBean serviceDataBean;

    public static DynamicH5Fragment getInstance(ServiceDataBean serviceDataBean) {
        DynamicH5Fragment dynamicH5Fragment = new DynamicH5Fragment();
        Bundle bundle = new Bundle();
        bundle.putSerializable("dataSource", serviceDataBean);
        dynamicH5Fragment.setArguments(bundle);
        return dynamicH5Fragment;
    }

    @Override
    protected int getLayoutId() {
        return R.layout.circlev2_fragment_detail_h5_v4;
    }

    @Override
    protected CircleDynamicDetailViewModel getViewModel() {
        return ViewModelProviders.of(this, factory).get(CircleDynamicDetailViewModel.class);
    }

    @Override
    protected void initView(View var1) {

    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        serviceDataBean = (ServiceDataBean) getArguments().getSerializable("dataSource");
        mBinding.get().setItem(serviceDataBean);
        FragmentUtils.add(getChildFragmentManager(), DynamicBotContentFragment.getInstance(serviceDataBean), R.id.frame_bot_content);

        mBinding.get().setViewmodel(mViewModel);
    }

    @Override
    public void initImmersionBar() {

    }

}
