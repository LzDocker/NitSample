package com.docker.cirlev2.ui.pro;

import android.arch.lifecycle.ViewModelProviders;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.android.arouter.launcher.ARouter;
import com.docker.cirlev2.R;
import com.docker.cirlev2.databinding.Circlev2ProManagerActivityBinding;
import com.docker.cirlev2.vm.SampleListViewModel;
import com.docker.cirlev2.vo.card.ProRecycleCardVo;
import com.docker.cirlev2.vo.pro.AppVo;
import com.docker.common.common.command.NitDelegetCommand;
import com.docker.common.common.model.CommonListOptions;
import com.docker.common.common.router.AppRouter;
import com.docker.common.common.ui.base.NitCommonActivity;
import com.docker.common.common.ui.base.NitCommonFragment;
import com.docker.common.common.vm.NitCommonListVm;
import com.docker.common.common.widget.card.NitBaseProviderCard;
import com.docker.common.common.widget.empty.EmptyStatus;

/*
 * 圈子应用管理
 * */
@Route(path = AppRouter.CIRCLE_DETAIL_v2_PRO_MANAGER)
public class CircleProManagerActivity extends NitCommonActivity<SampleListViewModel, Circlev2ProManagerActivityBinding> {


    @Override
    protected int getLayoutId() {
        return R.layout.circlev2_pro_manager_activity;
    }

    @Override
    public SampleListViewModel getmViewModel() {
        return ViewModelProviders.of(this, factory).get(SampleListViewModel.class);
    }


    @Override
    public void initView() {
        mToolbar.setTitle("应用管理");
        CommonListOptions commonListOptions = new CommonListOptions();
        commonListOptions.isActParent = true;
        NitBaseProviderCard.providerCardForFrame(getSupportFragmentManager(), R.id.frame_pro, commonListOptions);

    }

    @Override
    public void initObserver() {

    }

    @Override
    public void initRouter() {
        ARouter.getInstance().inject(this);
    }

    @Override
    public NitDelegetCommand providerNitDelegetCommand(int flag) {
        NitDelegetCommand nitDelegetCommand = new NitDelegetCommand() {
            @Override
            public Class providerOuterVm() {
                return null;
            }

            @Override
            public void next(NitCommonListVm commonListVm, NitCommonFragment nitCommonFragment) {


                ProRecycleCardVo proRecycleCardVo = new ProRecycleCardVo(0, 0);
                NitBaseProviderCard.providerCard(commonListVm, proRecycleCardVo, nitCommonFragment);

                ProRecycleCardVo proRecycleCardVo1 = new ProRecycleCardVo(1, 1);
                NitBaseProviderCard.providerCard(commonListVm, proRecycleCardVo1, nitCommonFragment);


            }
        };
        return nitDelegetCommand;
    }
}
