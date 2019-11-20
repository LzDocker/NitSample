package com.docker.cirlev2.ui.create;

import android.arch.lifecycle.ViewModelProviders;
import android.support.v4.app.Fragment;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.android.arouter.launcher.ARouter;
import com.dcbfhd.utilcode.utils.FragmentUtils;
import com.docker.cirlev2.R;
import com.docker.cirlev2.databinding.Circlev2SampleActivityBinding;
import com.docker.cirlev2.vm.CircleMinesViewModel;
import com.docker.cirlev2.vm.CreateListViewModel;
import com.docker.cirlev2.vm.SampleListViewModel;
import com.docker.common.common.command.NitContainerCommand;
import com.docker.common.common.router.AppRouter;
import com.docker.common.common.ui.base.NitCommonActivity;
import com.docker.common.common.ui.base.NitCommonListActivity;

@Route(path = AppRouter.CIRCLE_CREATE_v2_INDEX)
public class CircleCreateIndexActivity extends NitCommonListActivity<CreateListViewModel> {


    @Override
    public CreateListViewModel getmViewModel() {
        return ViewModelProviders.of(this, factory).get(CreateListViewModel.class);
    }

    @Override
    public void initView() {
        mToolbar.setTitle("创建圈子");
    }

    @Override
    public void initObserver() { }

    @Override
    public NitContainerCommand providerNitContainerCommand(int flag) {
        return null;
    }
}
