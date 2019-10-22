package com.docker.common.common.ui.container;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.TextUtils;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.android.arouter.launcher.ARouter;
import com.dcbfhd.utilcode.utils.FragmentUtils;
import com.docker.common.R;
import com.docker.common.common.command.NitContainerCommand;
import com.docker.common.common.command.ReponseCommand;
import com.docker.common.common.command.ReponseInterface;
import com.docker.common.common.config.Constant;
import com.docker.common.common.model.CommonContainerOptions;
import com.docker.common.common.model.CommonListOptions;
import com.docker.common.common.router.AppRouter;
import com.docker.common.common.ui.base.NitCommonActivity;
import com.docker.common.common.ui.base.NitCommonListFragment;
import com.docker.common.common.ui.base.NitCommonListInstanceFragment;
import com.docker.common.common.vm.NitEmptyVm;
import com.docker.common.common.vm.container.NitCommonContainerViewModel;
import com.docker.common.databinding.CommonActivityContainerBinding;
import com.docker.core.base.BaseVm;

import java.io.Serializable;

/*
 * 通用容器单一fragment  todo --zxd
 *
 * viewmodel  options  fragment  recycle  nomalview
 *
 * */
@Route(path = AppRouter.COMMON_CONTAINER_FRAGMENT)
public class NitCommonContainerFragment extends NitCommonListFragment<NitCommonContainerViewModel> {


    private NitContainerCommand containerCommand;
    
    public static NitCommonContainerFragment newinstance(CommonListOptions commonListReq/*, NitContainerCommand containerCommand*/) {
        NitCommonContainerFragment nitCommonContainerFragment = new NitCommonContainerFragment();
        Bundle bundle = new Bundle();
        bundle.putSerializable(Constant.ContainerParam, commonListReq);
//        bundle.putSerializable(Constant.ContainerCommand, containerCommand);
        nitCommonContainerFragment.setArguments(bundle);
        return nitCommonContainerFragment;
    }


    @Override
    protected NitCommonContainerViewModel getViewModel() {
        return ViewModelProviders.of(this, factory).get(NitCommonContainerViewModel.class);
    }

    @Override
    public CommonListOptions getArgument() {
        return null;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        ARouter.getInstance().inject(this);
        commonListReq = (CommonListOptions) getArguments().getSerializable(Constant.ContainerParam);
//        containerCommand = (NitContainerCommand) getArguments().getSerializable(Constant.ContainerCommand);
        containerCommand = ((NitCommonActivity) getHoldingActivity()).providerNitContainerCommand(commonListReq.falg);
//        mViewModel = containerCommand.exectue(this);
        mViewModel = (NitCommonContainerViewModel) ViewModelProviders.of(this, factory).get(containerCommand.exectue());
        this.getLifecycle().addObserver(mViewModel);
//        this.getLifecycle().addObserver(containerCommand);
        mViewModel.initParam(commonListReq);
        mBinding.get().setViewmodel(mViewModel);
        initRvUi();
        initRefreshUi();
        initListener();
        mViewModel.mContainerLiveData.observe(this, o -> {
        });
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
//        containerCommand.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    public void initImmersionBar() {

    }

    @Override
    public void onVisible() {
        super.onVisible();
//        containerCommand.onVisible();
    }
}
