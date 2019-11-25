package com.docker.common.common.ui.container;

import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.android.arouter.launcher.ARouter;
import com.docker.common.R;
import com.docker.common.common.adapter.CommonpagerAdapter;
import com.docker.common.common.command.NitContainerCommand;
import com.docker.common.common.config.Constant;
import com.docker.common.common.model.CommonListOptions;
import com.docker.common.common.router.AppRouter;
import com.docker.common.common.ui.base.NitCommonActivity;
import com.docker.common.common.ui.base.NitCommonFragment;
import com.docker.common.common.ui.base.NitCommonListFragment;
import com.docker.common.common.vm.NitCommonVm;
import com.docker.common.common.vm.container.NitCommonContainerViewModel;
import com.docker.common.common.widget.indector.CommonIndector;
import com.docker.common.databinding.CommonTabCoutainerFragmentBinding;

/*
 * 通用容器单一fragment  包含tab
 *
 *
 *
 * */
@Route(path = AppRouter.COMMON_TAB_CONTAINER_FRAGMENT)
public class NitTabContainerFragment extends NitCommonFragment<NitCommonVm, CommonTabCoutainerFragmentBinding> {


    private String[] titles;
    private boolean scrollFlag;

    public static NitTabContainerFragment newinstance(String[] titles, boolean scrollFlag) {
        NitTabContainerFragment nitCommonContainerFragment = new NitTabContainerFragment();
        Bundle bundle = new Bundle();
        bundle.putStringArray(Constant.ContainerParam, titles);
        bundle.putBoolean("scrollFlag", scrollFlag);
        nitCommonContainerFragment.setArguments(bundle);
        return nitCommonContainerFragment;
    }

    @Override
    protected int getLayoutId() {
        return R.layout.common_tab_frame_layout;
    }

    @Override
    protected NitCommonVm getViewModel() {
        return ViewModelProviders.of(this, factory).get(NitCommonVm.class);
    }

    @Override
    protected void initView(View var1) {

    }
    
    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        ARouter.getInstance().inject(this);
        titles = getArguments().getStringArray(Constant.ContainerParam);
        scrollFlag = getArguments().getBoolean("scrollFlag");
        mBinding.get().empty.hide();
        // magic
        mBinding.get().viewpager.setAdapter(new CommonpagerAdapter(getChildFragmentManager(), ((NitCommonActivity) getHoldingActivity()).providerNitContainerFragment(), titles));
        CommonIndector commonIndector = new CommonIndector();
        if (scrollFlag) {
            commonIndector.initMagicIndicatorScroll(titles, mBinding.get().viewpager, mBinding.get().magicIndicator, this.getHoldingActivity());
        } else {
            commonIndector.initMagicIndicator(titles, mBinding.get().viewpager, mBinding.get().magicIndicator, this.getHoldingActivity());
        }
        // magic

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
