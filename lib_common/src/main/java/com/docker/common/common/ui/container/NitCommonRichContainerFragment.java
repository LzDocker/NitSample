package com.docker.common.common.ui.container;

import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.android.arouter.launcher.ARouter;
import com.docker.common.common.command.NitDelegetCommand;
import com.docker.common.common.config.Constant;
import com.docker.common.common.model.CommonListOptions;
import com.docker.common.common.router.AppRouter;
import com.docker.common.common.ui.base.NitCommonActivity;
import com.docker.common.common.ui.base.NitCommonFragment;
import com.docker.common.common.ui.base.NitCommonListFragment;
import com.docker.common.common.ui.base.NitCommonRichListFragment;
import com.docker.common.common.vm.container.NitCommonContainerViewModel;

/*
 * 通用容器单一fragment
 * */
@Route(path = AppRouter.COMMON_RICH_CONTAINER_FRAGMENTV2)
public class NitCommonRichContainerFragment extends NitCommonRichListFragment<NitCommonContainerViewModel> {

    private NitDelegetCommand delegetCommand;

    public static NitCommonRichContainerFragment newinstance(CommonListOptions commonListReq) {
        NitCommonRichContainerFragment nitCommonContainerFragment = new NitCommonRichContainerFragment();
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
        } else {
            delegetCommand = ((NitCommonFragment) (NitCommonRichContainerFragment.this.getParentFragment())).providerNitDelegetCommand(commonListReq.falg);
        }
        mViewModel = (NitCommonContainerViewModel) ViewModelProviders.of(this, factory).get(delegetCommand.providerOuterVm());
        this.getLifecycle().addObserver(mViewModel);
        mViewModel.mContainerLiveData.observe(this, o -> {

        });
        mViewModel.initParam(commonListReq);
        mViewModel.initCommand();
        mBinding.get().setViewmodel(mViewModel);
        initRvUi();
        initRefreshUi();
        initListener();
        if (delegetCommand != null) {
            delegetCommand.next(mViewModel, this);
        }
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
