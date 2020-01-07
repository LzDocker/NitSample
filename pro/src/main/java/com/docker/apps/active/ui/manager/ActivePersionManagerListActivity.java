package com.docker.apps.active.ui.manager;

import android.arch.lifecycle.ViewModelProviders;
import android.os.Bundle;
import android.support.v4.app.Fragment;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.android.arouter.launcher.ARouter;
import com.docker.apps.R;
import com.docker.apps.active.vm.ActivePersionListViewModel;
import com.docker.apps.databinding.ProActiveManagerBinding;
import com.docker.apps.databinding.ProActivePersionManagerBinding;
import com.docker.cirlev2.vm.SampleListViewModel;
import com.docker.cirlev2.vo.entity.CircleTitlesVo;
import com.docker.common.common.adapter.CommonpagerAdapter;
import com.docker.common.common.command.NitDelegetCommand;
import com.docker.common.common.config.Constant;
import com.docker.common.common.model.CommonListOptions;
import com.docker.common.common.router.AppRouter;
import com.docker.common.common.ui.base.NitCommonActivity;
import com.docker.common.common.ui.base.NitCommonFragment;
import com.docker.common.common.ui.container.NitCommonContainerFragmentV2;
import com.docker.common.common.vm.NitCommonListVm;
import com.docker.common.common.vm.container.NitCommonContainerViewModel;
import com.docker.common.common.widget.indector.CommonIndector;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/*
 * 活动名单管理列表
 **/

@Route(path = AppRouter.ACTIVE_MANAGER_PERSION_LIST)
public class ActivePersionManagerListActivity extends NitCommonActivity<NitCommonContainerViewModel, ProActivePersionManagerBinding> {

    public ArrayList<Fragment> fragments = new ArrayList<>();

    @Override
    public NitCommonContainerViewModel getmViewModel() {
        return ViewModelProviders.of(this, factory).get(NitCommonContainerViewModel.class);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.pro_active_persion_manager;
    }

    @Override
    protected void inject() {
        super.inject();
        ARouter.getInstance().inject(this);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mToolbar.setTitle("名单管理");

        mToolbar.getTvTitle().setOnClickListener(v -> {

        });
    }

    @Override
    public void initView() {
        peocessTab();
    }

    @Override
    public void initObserver() {


    }

    public void peocessTab() {

        CommonListOptions commonListOptions = new CommonListOptions();
        commonListOptions.isActParent = true;
        commonListOptions.RvUi = Constant.KEY_RVUI_LINER;
        commonListOptions.refreshState = Constant.KEY_REFRESH_OWNER;
        commonListOptions.ReqParam.put("", "");
        NitCommonContainerFragmentV2 activeRegistFragment = NitCommonContainerFragmentV2.newinstance(commonListOptions);
        fragments.add(activeRegistFragment);


        CommonListOptions commonListOptions1 = new CommonListOptions();
        commonListOptions1.isActParent = true;
        commonListOptions1.RvUi = Constant.KEY_RVUI_LINER;
        commonListOptions1.refreshState = Constant.KEY_REFRESH_OWNER;
        commonListOptions1.ReqParam.put("", "");
        NitCommonContainerFragmentV2 activeWaitVifirFragment = NitCommonContainerFragmentV2.newinstance(commonListOptions1);
        fragments.add(activeWaitVifirFragment);

        CommonListOptions commonListOptions3 = new CommonListOptions();
        commonListOptions3.isActParent = true;
        commonListOptions3.RvUi = Constant.KEY_RVUI_LINER;
        commonListOptions3.refreshState = Constant.KEY_REFRESH_OWNER;
        commonListOptions3.ReqParam.put("", "");
        NitCommonContainerFragmentV2 activeAleaVifirFragment = NitCommonContainerFragmentV2.newinstance(commonListOptions3);
        fragments.add(activeAleaVifirFragment);

        String[] titles = new String[]{"报名审核", "待核销", "已核销"};
        // magic
        mBinding.viewPager.setAdapter(new CommonpagerAdapter(getSupportFragmentManager(), fragments, titles));
        CommonIndector commonIndector = new CommonIndector();
        commonIndector.initMagicIndicator(titles, mBinding.viewPager, mBinding.magicIndicator, this);
    }

    @Override
    public NitDelegetCommand providerNitDelegetCommand(int flag) {
        NitDelegetCommand nitDelegetCommand = new NitDelegetCommand() {
            @Override
            public Class providerOuterVm() {
                return ActivePersionListViewModel.class;
            }

            @Override
            public void next(NitCommonListVm commonListVm, NitCommonFragment nitCommonFragment) {

            }
        };
        return nitDelegetCommand;
    }

    @Override
    public void initRouter() {

    }
}
