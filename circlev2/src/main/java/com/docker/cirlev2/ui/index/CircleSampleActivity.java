package com.docker.cirlev2.ui.index;

import android.arch.lifecycle.ViewModelProviders;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.android.arouter.launcher.ARouter;
import com.dcbfhd.utilcode.utils.FragmentUtils;
import com.docker.cirlev2.R;
import com.docker.cirlev2.databinding.Circlev2SampleActivityBinding;
import com.docker.cirlev2.vm.SampleListViewModel;
import com.docker.common.common.command.NitContainerCommand;
import com.docker.common.common.model.CommonListOptions;
import com.docker.common.common.router.AppRouter;
import com.docker.common.common.ui.base.NitCommonActivity;
import com.docker.common.common.ui.container.NitCommonContainerFragment;

@Route(path = AppRouter.CIRCLE_SAMPLE_INDEX)
public class CircleSampleActivity extends NitCommonActivity<SampleListViewModel, Circlev2SampleActivityBinding> {


    @Override
    protected int getLayoutId() {
        return R.layout.circlev2_sample_activity;
    }

    @Override
    public SampleListViewModel getmViewModel() {
        return ViewModelProviders.of(this, factory).get(SampleListViewModel.class);
    }

    @Override
    public void initView() {
        mToolbar.hide();
        CommonListOptions commonListOptions = new CommonListOptions();
        commonListOptions.falg = 0;
        NitCommonContainerFragment containerFragment = NitCommonContainerFragment.newinstance(commonListOptions);
        FragmentUtils.add(getSupportFragmentManager(), containerFragment, R.id.circlev2_frame);
    }

    @Override
    public void initObserver() {

    }

    @Override
    public void initRouter() {
        ARouter.getInstance().inject(this);
    }

    @Override
    public NitContainerCommand providerNitContainerCommand(int flag) {
        return (NitContainerCommand) () -> (SampleListViewModel.class);
    }

}
