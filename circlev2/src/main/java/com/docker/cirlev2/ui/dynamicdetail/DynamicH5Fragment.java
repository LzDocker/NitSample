package com.docker.cirlev2.ui.dynamicdetail;

import android.arch.lifecycle.ViewModelProviders;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.widget.NestedScrollView;
import android.view.View;

import com.dcbfhd.utilcode.utils.FragmentUtils;
import com.docker.cirlev2.R;
import com.docker.cirlev2.databinding.Circlev2FragmentDetailH5V4Binding;
import com.docker.cirlev2.vm.CircleDynamicDetailViewModel;
import com.docker.cirlev2.vm.SampleListViewModel;
import com.docker.cirlev2.vo.entity.ServiceDataBean;
import com.docker.common.common.ui.base.NitCommonFragment;
import com.docker.common.common.utils.rxbus.RxBus;
import com.docker.common.common.utils.rxbus.RxEvent;

import io.reactivex.disposables.Disposable;

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

    private Disposable disposable;

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        serviceDataBean = (ServiceDataBean) getArguments().getSerializable("dataSource");
        mBinding.get().setItem(serviceDataBean);
        FragmentUtils.add(getChildFragmentManager(), DynamicBotContentFragment.getInstance(serviceDataBean), R.id.frame_bot_content);

        mBinding.get().setViewmodel(mViewModel);

        disposable = RxBus.getDefault().toObservable(RxEvent.class).subscribe(rxEvent -> {
            if (rxEvent.getT().equals("comment")) {
                mBinding.get().netSpeed.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        mBinding.get().netSpeed.fullScroll(NestedScrollView.FOCUS_DOWN);
                    }
                }, 500);
            }
        });
    }

    @Override
    public void initImmersionBar() {

    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (disposable != null) {
            disposable.dispose();
        }
    }
}
