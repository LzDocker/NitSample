package com.bfhd.account.ui.tygs;

import android.arch.lifecycle.ViewModelProvider;
import android.arch.lifecycle.ViewModelProviders;
import android.os.Bundle;
import android.support.v4.app.Fragment;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.android.arouter.launcher.ARouter;
import com.bfhd.account.R;
import com.bfhd.account.databinding.AccountActivityActManagerBinding;
import com.bfhd.account.vm.AccountOrderViewModel;
import com.bfhd.account.vm.AccountRewardViewModel;
import com.bfhd.account.vo.tygs.AccountRewardHeadVo;
import com.bfhd.circle.base.adapter.HivsSampleAdapter;
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
import com.docker.common.common.widget.card.NitBaseProviderCard;
import com.docker.common.common.widget.indector.CommonIndector;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

/*
 * 我的订单列表
 **/

@Route(path = AppRouter.ACCOUNT_ORDER_LIST)
public class AccounOrderListActivity extends NitCommonActivity<NitCommonContainerViewModel, AccountActivityActManagerBinding> {


    @Inject
    ViewModelProvider.Factory factory;
    private HivsSampleAdapter mAdapter;

    public ArrayList<Fragment> fragments = new ArrayList<>();


    @Override
    public NitCommonContainerViewModel getmViewModel() {
        return ViewModelProviders.of(this, factory).get(NitCommonContainerViewModel.class);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.account_activity_act_manager;
    }

    @Override
    protected void inject() {
        super.inject();
        ARouter.getInstance().inject(this);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mToolbar.setTitle("活动报名");

    }

    @Override
    public void initView() {

        List<CircleTitlesVo> circleTitlesVos = new ArrayList<>();
        CircleTitlesVo circleTitlesVo = new CircleTitlesVo();
        circleTitlesVo.setName("全部");
        CircleTitlesVo circleTitlesVo1 = new CircleTitlesVo();
        circleTitlesVo1.setName("待付款");
        CircleTitlesVo circleTitlesVo2 = new CircleTitlesVo();
        circleTitlesVo2.setName("待收货");
        CircleTitlesVo circleTitlesVo3 = new CircleTitlesVo();
        circleTitlesVo3.setName("已完成");
        CircleTitlesVo circleTitlesVo4 = new CircleTitlesVo();
        circleTitlesVo4.setName("已取消");

        circleTitlesVos.add(circleTitlesVo);
        circleTitlesVos.add(circleTitlesVo1);
        circleTitlesVos.add(circleTitlesVo2);
        circleTitlesVos.add(circleTitlesVo3);
        circleTitlesVos.add(circleTitlesVo4);
        peocessTab(circleTitlesVos);

    }

    @Override
    public NitDelegetCommand providerNitDelegetCommand(int flag) {
        NitDelegetCommand nitDelegetCommand = null;
        switch (flag) {
            case 0:
            case 1:
            case 2:
            case 3:
            case 4:
                nitDelegetCommand = new NitDelegetCommand() {
                    @Override
                    public Class providerOuterVm() {
                        return AccountOrderViewModel.class;
                    }

                    @Override
                    public void next(NitCommonListVm commonListVm, NitCommonFragment nitCommonFragment) {
                    }
                };
                break;

        }


        return nitDelegetCommand;
    }


    @Override
    public void initObserver() {

    }

    public void peocessTab(List<CircleTitlesVo> circleTitlesVos) {
        String[] titles = new String[circleTitlesVos.size()];
        for (int i = 0; i < circleTitlesVos.size(); i++) {
            titles[i] = circleTitlesVos.get(i).getName();
            CommonListOptions commonListOptions = new CommonListOptions();
            commonListOptions.refreshState = Constant.KEY_REFRESH_ONLY_LOADMORE;
            commonListOptions.isActParent = true;
            commonListOptions.falg = i;
            NitCommonContainerFragmentV2 nitCommonContainerFragmentV2 = NitCommonContainerFragmentV2.newinstance(commonListOptions);
            fragments.add(nitCommonContainerFragmentV2);
        }

        // magic
        mBinding.viewPager.setAdapter(new CommonpagerAdapter(getSupportFragmentManager(), fragments, titles));
        CommonIndector commonIndector = new CommonIndector();
        commonIndector.initMagicIndicator(titles, mBinding.viewPager, mBinding.magicIndicator, this);

    }

    @Override
    public void initRouter() {

    }


}
