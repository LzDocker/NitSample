package com.docker.message.ui.index;

import android.arch.lifecycle.ViewModelProviders;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.android.arouter.launcher.ARouter;
import com.dcbfhd.utilcode.utils.FragmentUtils;
import com.docker.common.common.command.NitContainerCommand;
import com.docker.common.common.model.CommonListOptions;
import com.docker.common.common.router.AppRouter;
import com.docker.common.common.ui.base.NitCommonActivity;
import com.docker.common.common.ui.container.NitCommonContainerFragment;
import com.docker.message.R;
import com.docker.message.databinding.MessageSampleActivityBinding;
import com.docker.message.vm.SampleListViewModel;

@Route(path = AppRouter.MESSAGEINDEX)
public class MessageSampleActivity extends NitCommonActivity<SampleListViewModel, MessageSampleActivityBinding> {


    @Override
    protected int getLayoutId() {
        return R.layout.message_sample_activity;
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
        FragmentUtils.add(getSupportFragmentManager(), containerFragment, R.id.message_frame);
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
