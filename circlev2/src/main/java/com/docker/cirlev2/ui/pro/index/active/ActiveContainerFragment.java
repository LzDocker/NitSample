package com.docker.cirlev2.ui.pro.index.active;

import android.arch.lifecycle.ViewModelProviders;
import android.support.v4.app.Fragment;
import android.view.View;

import com.alibaba.android.arouter.launcher.ARouter;
import com.dcbfhd.utilcode.utils.FragmentUtils;
import com.docker.cirlev2.R;
import com.docker.cirlev2.databinding.Circlev2ActiveCoutainerFragmentBinding;
import com.docker.common.common.router.AppRouter;
import com.docker.common.common.ui.base.NitCommonFragment;
import com.docker.common.common.vm.NitEmptyViewModel;

public class ActiveContainerFragment extends NitCommonFragment<NitEmptyViewModel, Circlev2ActiveCoutainerFragmentBinding> {


    public static ActiveContainerFragment getInstance() {
        ActiveContainerFragment fragment = new ActiveContainerFragment();
        return fragment;
    }

    @Override
    protected int getLayoutId() {
        return R.layout.circlev2_active_coutainer_fragment;
    }

    @Override
    protected NitEmptyViewModel getViewModel() {
        return ViewModelProviders.of(this, factory).get(NitEmptyViewModel.class);
    }

    @Override
    protected void initView(View var1) {

        Fragment fragment = (Fragment) ARouter.getInstance()
                .build(AppRouter.CIRCLE_DYNAMIC_LIST_FRAME)
                .navigation();

        FragmentUtils.add(getChildFragmentManager(), fragment, R.id.frame_active);
    }

    @Override
    public void initImmersionBar() {

    }


}
