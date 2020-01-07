package com.docker.apps.active.ui.manager;

import android.arch.lifecycle.ViewModelProviders;
import android.os.Bundle;
import android.support.v4.app.Fragment;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.android.arouter.launcher.ARouter;
import com.docker.apps.R;
import com.docker.apps.active.vo.ActiveManagerVo;
import com.docker.apps.active.vo.card.ActiveInfoCard;
import com.docker.apps.active.vo.card.ActiveManagerCard;
import com.docker.apps.databinding.ProActiveManagerBinding;
import com.docker.apps.databinding.ProActiveManagerDetailBinding;
import com.docker.cirlev2.vo.entity.CircleTitlesVo;
import com.docker.common.common.adapter.CommonpagerAdapter;
import com.docker.common.common.command.NitDelegetCommand;
import com.docker.common.common.config.Constant;
import com.docker.common.common.model.CommonListOptions;
import com.docker.common.common.router.AppRouter;
import com.docker.common.common.ui.base.NitCommonActivity;
import com.docker.common.common.ui.base.NitCommonFragment;
import com.docker.common.common.vm.NitCommonListVm;
import com.docker.common.common.vm.container.NitCommonContainerViewModel;
import com.docker.common.common.widget.card.NitBaseProviderCard;
import com.docker.common.common.widget.indector.CommonIndector;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/*
 * 活动管理详情
 **/

@Route(path = AppRouter.ACTIVE_MANAGER_DETAIL)
public class ActiveManagerDetailActivity extends NitCommonActivity<NitCommonContainerViewModel, ProActiveManagerDetailBinding> {

    @Override
    public NitCommonContainerViewModel getmViewModel() {
        return ViewModelProviders.of(this, factory).get(NitCommonContainerViewModel.class);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.pro_active_manager_detail;
    }

    @Override
    protected void inject() {
        super.inject();
        ARouter.getInstance().inject(this);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mToolbar.setTitle("活动管理");

        CommonListOptions commonListOptions = new CommonListOptions();
        commonListOptions.isActParent = true;
        commonListOptions.RvUi = Constant.KEY_RVUI_LINER;
        commonListOptions.falg = 0;
        NitBaseProviderCard.providerCardNoRefreshForFrame(getSupportFragmentManager(), R.id.frame, commonListOptions);

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
                ActiveInfoCard activeInfoCard = new ActiveInfoCard(0, 0);
                NitBaseProviderCard.providerCard(commonListVm, activeInfoCard, nitCommonFragment);

                ActiveManagerCard activeManagerCard = new ActiveManagerCard(0, 1);
                NitBaseProviderCard.providerCard(commonListVm, activeManagerCard, nitCommonFragment);

            }
        };
        return nitDelegetCommand;
    }

    @Override
    public void initView() {

        // 扫码核销
        mBinding.tvScanerTicker.setOnClickListener(v -> {
            ARouter.getInstance().build(AppRouter.ACTIVE_MANAGE_VERFIC).navigation();
        });

        // 名单管理
        mBinding.tvPersionManager.setOnClickListener(v -> {
            ARouter.getInstance().build(AppRouter.ACTIVE_MANAGER_PERSION_LIST).navigation();
        });
    }

    @Override
    public void initObserver() {

    }


    @Override
    public void initRouter() {

    }


}
