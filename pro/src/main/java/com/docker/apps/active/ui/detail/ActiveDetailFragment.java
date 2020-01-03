package com.docker.apps.active.ui.detail;

import android.arch.lifecycle.ViewModelProviders;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.View;

import com.alibaba.android.arouter.facade.annotation.Autowired;
import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.android.arouter.launcher.ARouter;
import com.docker.apps.active.ui.index.ActiveContainerFragment;
import com.docker.apps.active.vo.card.ActiveDetailHeadCard;
import com.docker.cirlev2.vm.CircleDynamicDetailViewModel;
import com.docker.cirlev2.vo.entity.ServiceDataBean;
import com.docker.common.common.adapter.CommonpagerAdapter;
import com.docker.common.common.command.NitDelegetCommand;
import com.docker.common.common.config.Constant;
import com.docker.common.common.model.CommonListOptions;
import com.docker.common.common.router.AppRouter;
import com.docker.common.common.ui.base.NitCommonFragment;
import com.docker.common.common.vm.NitCommonListVm;
import com.docker.common.common.widget.card.NitBaseProviderCard;
import com.docker.common.common.widget.indector.CommonIndector;
import com.docker.common.databinding.CommonDetailCoutainerLayoutBinding;

import java.util.ArrayList;

/*
 * 活动详情
 * */
@Route(path = AppRouter.ACTIVE_DEATIL)
public class ActiveDetailFragment extends NitCommonFragment<CircleDynamicDetailViewModel, CommonDetailCoutainerLayoutBinding> {


    private ServiceDataBean serviceDataBean;

    @Override
    protected int getLayoutId() {
        return com.docker.common.R.layout.common_detail_coutainer_layout;
    }

    @Override
    protected CircleDynamicDetailViewModel getViewModel() {
        return ViewModelProviders.of(this, factory).get(CircleDynamicDetailViewModel.class);
    }

    @Override
    protected void initView(View var1) {
        CommonListOptions commonListOptions = new CommonListOptions();
        commonListOptions.RvUi = Constant.KEY_RVUI_LINER;
        NitBaseProviderCard.providerCardNoRefreshForFrame(getChildFragmentManager(), com.docker.common.R.id.frame_head, commonListOptions);


        mBinding.get().refresh.setEnableLoadMore(true);

        ArrayList<Fragment> fragments = new ArrayList<>();
        String[] titles = new String[]{"活动介绍", "活动动态"};
        fragments.add((Fragment) ARouter.getInstance()
                .build(AppRouter.CIRCLE_DYNAMIC_LIST_FRAME)
                .navigation());
        fragments.add((Fragment) ARouter.getInstance()
                .build(AppRouter.CIRCLE_DYNAMIC_LIST_FRAME)
                .navigation());
        // magic
        mBinding.get().viewPager.setAdapter(new CommonpagerAdapter(getChildFragmentManager(), fragments));
        CommonIndector commonIndector = new CommonIndector();
        commonIndector.initMagicIndicatorScroll(titles, mBinding.get().viewPager, mBinding.get().magicIndicator, this.getHoldingActivity());
        // magic

    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        serviceDataBean = (ServiceDataBean) getArguments().getSerializable("config");
        super.onActivityCreated(savedInstanceState);


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
                ActiveDetailHeadCard activeDetailHeadCard = new ActiveDetailHeadCard(0, 0);
                NitBaseProviderCard.providerCard(commonListVm, activeDetailHeadCard, nitCommonFragment);
            }
        };
        return nitDelegetCommand;
    }

    @Override
    public void initImmersionBar() {

    }

}
