package com.docker.cirlev2.ui.index;

import android.arch.lifecycle.ViewModelProviders;
import android.support.v4.app.Fragment;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.android.arouter.launcher.ARouter;
import com.dcbfhd.utilcode.utils.FragmentUtils;
import com.docker.cirlev2.R;
import com.docker.cirlev2.databinding.Circlev2SampleActivityBinding;
import com.docker.cirlev2.vm.CircleMinesViewModel;
import com.docker.cirlev2.vm.SampleListViewModel;
import com.docker.common.common.command.NitContainerCommand;
import com.docker.common.common.model.CommonListOptions;
import com.docker.common.common.router.AppRouter;
import com.docker.common.common.ui.base.NitCommonActivity;
import com.docker.common.common.ui.container.NitCommonContainerFragment;

@Route(path = AppRouter.CIRCLE_INDEX)
public class CircleIndexActivity extends NitCommonActivity<SampleListViewModel, Circlev2SampleActivityBinding> {


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
        mToolbar.setTitle("加入公社");
//        FragmentUtils.add(getSupportFragmentManager(), (Fragment) ARouter.getInstance().build(AppRouter.CIRCLE_INDEX_FRAME).navigation(), R.id.circlev2_frame);
        FragmentUtils.add(getSupportFragmentManager(), (Fragment) ARouter.getInstance().build(AppRouter.CIRCLE_INDEX_FRAMEV2).navigation(), R.id.circlev2_frame);
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
        NitContainerCommand nitContainerCommand = null;
        switch (flag) {
            case 101:
                nitContainerCommand = (NitContainerCommand) () -> CircleMinesViewModel.class;
                break;
            case 102:
                nitContainerCommand = (NitContainerCommand) () -> CircleMinesViewModel.class;
                break;
        }

        return nitContainerCommand;
    }

}
