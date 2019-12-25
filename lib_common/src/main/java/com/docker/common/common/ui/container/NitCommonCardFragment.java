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
import com.docker.common.common.vm.container.NitcommonCardViewModel;

import static com.docker.common.common.config.Constant.CommonListParam;

/*
 * 通用card容器
 *
 *
 * */
@Route(path = AppRouter.COMMON_CARD_FRAGMENT)
public class NitCommonCardFragment extends NitCommonListFragment<NitcommonCardViewModel> {
    private NitDelegetCommand delegetCommand;


    public static NitCommonCardFragment newinstance(CommonListOptions commonListOptions) {
        NitCommonCardFragment nitCommonCardFragment = new NitCommonCardFragment();
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
            delegetCommand = ((NitCommonFragment) (NitCommonCardFragment.this.getParentFragment())).providerNitDelegetCommand(commonListReq.falg);
        }
        if (delegetCommand != null) {
            delegetCommand.next(mViewModel, this);
        }
    }


    @Override
    public void initImmersionBar() {

    }

    @Override
    public void onVisible() {
        super.onVisible();
        mViewModel.onCardFrameVisible();
    }
}
