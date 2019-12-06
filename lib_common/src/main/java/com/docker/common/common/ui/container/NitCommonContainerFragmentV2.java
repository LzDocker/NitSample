package com.docker.common.common.ui.container;

import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.android.arouter.launcher.ARouter;
import com.docker.common.common.command.NitContainerCommand;
import com.docker.common.common.command.NitContainerCommandV2;
import com.docker.common.common.command.NitDelegetCommand;
import com.docker.common.common.config.Constant;
import com.docker.common.common.model.CommonListOptions;
import com.docker.common.common.router.AppRouter;
import com.docker.common.common.ui.base.NitCommonActivity;
import com.docker.common.common.ui.base.NitCommonFragment;
import com.docker.common.common.ui.base.NitCommonListFragment;
import com.docker.common.common.vm.container.NitCommonContainerViewModel;
import com.docker.common.common.vm.container.NitcommonCardViewModel;
import com.docker.common.common.widget.refresh.api.RefreshLayout;
import com.docker.common.common.widget.refresh.listener.OnRefreshListener;

/*
 * 通用容器单一fragment
 * */
@Route(path = AppRouter.COMMON_CONTAINER_FRAGMENTV2)
public class NitCommonContainerFragmentV2 extends NitCommonListFragment<NitCommonContainerViewModel> {

    private NitDelegetCommand delegetCommand;
    private NitcommonCardViewModel[] nitcommonCardViewModelArr;

    public static NitCommonContainerFragmentV2 newinstance(CommonListOptions commonListReq) {
        NitCommonContainerFragmentV2 nitCommonContainerFragment = new NitCommonContainerFragmentV2();
        Bundle bundle = new Bundle();
        bundle.putSerializable(Constant.CommonListParam, commonListReq);
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
        //
        if (commonListReq.isActParent) {
            delegetCommand = ((NitCommonActivity) getHoldingActivity()).providerNitDelegetCommand(commonListReq.falg);
            Class[] inners = delegetCommand.providerInnerVm();
            nitcommonCardViewModelArr = new NitcommonCardViewModel[inners.length];
            for (int i = 0; i < inners.length; i++) {
                nitcommonCardViewModelArr[i] = (NitcommonCardViewModel) ViewModelProviders.of(this, factory).get(inners[i]);
                this.getLifecycle().addObserver(nitcommonCardViewModelArr[i]);
            }
        } else {
            delegetCommand = ((NitCommonFragment) (NitCommonContainerFragmentV2.this.getParentFragment())).providerNitDelegetCommand(commonListReq.falg);
            Class[] inners = delegetCommand.providerInnerVm();
            nitcommonCardViewModelArr = new NitcommonCardViewModel[inners.length];
            for (int i = 0; i < inners.length; i++) {
                nitcommonCardViewModelArr[i] = (NitcommonCardViewModel) ViewModelProviders.of(this, factory).get(inners[i]);
            }
        }
        mViewModel = (NitCommonContainerViewModel) ViewModelProviders.of(this, factory).get(delegetCommand.providerOuterVm());
        this.getLifecycle().addObserver(mViewModel);
        mViewModel.mContainerLiveData.observe(this, null);
        mViewModel.initParam(commonListReq);
        mViewModel.initCommand();
        mBinding.get().setViewmodel(mViewModel);
        initRvUi();
        initRefreshUi();
        initListener();
        delegetCommand.next(mViewModel, nitcommonCardViewModelArr, this);
        mViewModel.mNitcommonCardViewModelArr = nitcommonCardViewModelArr;

    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    public void initImmersionBar() {

    }

    @Override
    public void onVisible() {
        super.onVisible();
    }
}
