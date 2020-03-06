package com.docker.cirlev2.ui.dynamicdetail;

import android.arch.lifecycle.ViewModelProviders;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;

import com.docker.cirlev2.R;
import com.docker.cirlev2.databinding.Circlev2FragmentDetailBotContentBinding;
import com.docker.cirlev2.databinding.Circlev2FragmentDetailContentBinding;
import com.docker.cirlev2.vm.CircleCommentListViewModel;
import com.docker.cirlev2.vm.CircleDynamicDetailViewModel;
import com.docker.cirlev2.vm.CircleDynamicListViewModel;
import com.docker.cirlev2.vo.entity.ServiceDataBean;
import com.docker.common.common.command.NitDelegetCommand;
import com.docker.common.common.config.Constant;
import com.docker.common.common.model.CommonListOptions;
import com.docker.common.common.ui.base.NitCommonFragment;
import com.docker.common.common.utils.rxbus.RxBus;
import com.docker.common.common.utils.rxbus.RxEvent;
import com.docker.common.common.vm.NitCommonListVm;
import com.docker.common.common.widget.card.NitBaseProviderCard;

import io.reactivex.disposables.Disposable;

public class DynamicContentFragment extends NitCommonFragment<CircleDynamicDetailViewModel, Circlev2FragmentDetailContentBinding> {

    public ServiceDataBean serviceDataBean;

    public static DynamicContentFragment getInstance(ServiceDataBean serviceDataBean) {
        DynamicContentFragment dynamicH5Fragment = new DynamicContentFragment();
        Bundle bundle = new Bundle();
        bundle.putSerializable("dataSource", serviceDataBean);
        dynamicH5Fragment.setArguments(bundle);
        return dynamicH5Fragment;
    }

    @Override
    protected int getLayoutId() {
        return R.layout.circlev2_fragment_detail_content;
    }

    @Override
    protected CircleDynamicDetailViewModel getViewModel() {
        return ViewModelProviders.of(this, factory).get(CircleDynamicDetailViewModel.class);
    }

    @Override
    protected void initView(View var1) {
        mBinding.get().setViewmodel(mViewModel);
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
