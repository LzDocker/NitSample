package com.docker.common.common.ui.container;

import android.arch.lifecycle.ViewModelProviders;
import android.os.Bundle;
import android.support.annotation.Nullable;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.docker.common.common.command.NitDelegetCommand;
import com.docker.common.common.model.CommonListOptions;
import com.docker.common.common.router.AppRouter;
import com.docker.common.common.ui.base.NitCommonActivity;
import com.docker.common.common.ui.base.NitCommonFragment;
import com.docker.common.common.ui.base.NitCommonListFragment;
import com.docker.common.common.ui.base.NitCommonListNoRefreshFragment;
import com.docker.common.common.vm.container.NitcommonCardViewModel;

import static com.docker.common.common.config.Constant.CommonListParam;

/*
 * 通用card容器
 *
 *
 * */
@Route(path = AppRouter.COMMON_CARD_FRAGMENT_NO_REFRESH)
public class NitCommonCardNoRefreshFragment extends NitCommonListNoRefreshFragment<NitcommonCardViewModel> {
    private NitDelegetCommand delegetCommand;


    public static NitCommonCardNoRefreshFragment newinstance(CommonListOptions commonListOptions) {
        NitCommonCardNoRefreshFragment nitCommonCardFragment = new NitCommonCardNoRefreshFragment();
        Bundle bundle = new Bundle();
        bundle.putSerializable(CommonListParam, commonListOptions);
        nitCommonCardFragment.setArguments(bundle);
        return nitCommonCardFragment;
    }

    @Override
    public NitcommonCardViewModel getViewModel() {
        return ViewModelProviders.of(this, factory).get(NitcommonCardViewModel.class);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        if (commonListReq.isActParent) {
            delegetCommand = ((NitCommonActivity) getHoldingActivity()).providerNitDelegetCommand(commonListReq.falg);
        } else {
            delegetCommand = ((NitCommonFragment) (NitCommonCardNoRefreshFragment.this.getParentFragment())).providerNitDelegetCommand(commonListReq.falg);
        }
        delegetCommand.next(mViewModel, this);
    }


    @Override
    public void initImmersionBar() {

    }

    @Override
    public void onVisible() {
        super.onVisible();
    }
}