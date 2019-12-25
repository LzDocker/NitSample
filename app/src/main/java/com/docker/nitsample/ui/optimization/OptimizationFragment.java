package com.docker.nitsample.ui.optimization;

import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;

import com.alibaba.android.arouter.launcher.ARouter;
import com.docker.cirlev2.vo.card.AppBannerHeaderCardVo;
import com.docker.common.common.command.NitDelegetCommand;
import com.docker.common.common.config.Constant;
import com.docker.common.common.model.CommonListOptions;
import com.docker.common.common.ui.base.NitCommonActivity;
import com.docker.common.common.ui.base.NitCommonFragment;
import com.docker.common.common.ui.base.NitCommonListFragment;
import com.docker.common.common.ui.container.NitCommonContainerFragmentV2;
import com.docker.common.common.vm.NitCommonListVm;
import com.docker.common.common.vm.container.NitCommonContainerViewModel;
import com.docker.common.common.widget.card.NitBaseProviderCard;
import com.docker.nitsample.R;
import com.docker.nitsample.databinding.IndexTygsFragmentBinding;
import com.docker.nitsample.databinding.OptimizationFragmentBinding;
import com.docker.nitsample.ui.index.IndexTygsFragment;
import com.docker.nitsample.vm.MainViewModel;
import com.docker.nitsample.vm.OptimizationModel;
import com.docker.nitsample.vo.LayoutManagerVo;
import com.docker.nitsample.vo.RecycleTopLayout;
import com.docker.nitsample.vo.card.AppRecycleCard2Vo;
import com.docker.nitsample.vo.card.AppRecycleHorizontalCardVo;
import com.docker.nitsample.vo.card.AppRecycleHorizontalCardVo2;

/**
 * 公社优选
 */
public class OptimizationFragment extends NitCommonFragment<OptimizationModel, OptimizationFragmentBinding> {
    private NitDelegetCommand delegetCommand;
    private NitCommonListVm outerVm;
    private CommonListOptions commonListReq;

    public static OptimizationFragment newinstance(CommonListOptions commonListReq) {
        OptimizationFragment optimizationFragment = new OptimizationFragment();
        Bundle bundle = new Bundle();
        bundle.putSerializable(Constant.ContainerParam, commonListReq);
        optimizationFragment.setArguments(bundle);
        return optimizationFragment;
    }

    @Override
    protected int getLayoutId() {
        return R.layout.optimization_fragment;
    }


    @Override
    protected OptimizationModel getViewModel() {
        return ViewModelProviders.of(this, factory).get(OptimizationModel.class);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        ARouter.getInstance().inject(this);
        Bundle bundle = getArguments();
        commonListReq = (CommonListOptions) bundle.getSerializable(Constant.ContainerParam);
        NitBaseProviderCard.providerCoutainerForFrame(getChildFragmentManager(), R.id.frame, commonListReq);
    }

    @Override
    protected void initView(View var1) {


    }

    @Override
    public NitDelegetCommand providerNitDelegetCommand(int flag) {
        NitDelegetCommand nitDelegetCommand = new NitDelegetCommand() {
            @Override
            public Class providerOuterVm() {
                return OptimizationModel.class;
            }

            @Override
            public void next(NitCommonListVm commonListVm, NitCommonFragment nitCommonFragment) {
                outerVm = commonListVm;


            }
        };
        return nitDelegetCommand;
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
