package com.docker.message.ui.index;

import android.arch.lifecycle.ViewModelProviders;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.View;

import com.alibaba.android.arouter.facade.annotation.Autowired;
import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.android.arouter.launcher.ARouter;
import com.dcbfhd.utilcode.utils.FragmentUtils;
import com.docker.common.common.command.NitContainerCommand;
import com.docker.common.common.router.AppRouter;
import com.docker.common.common.ui.base.NitCommonActivity;
import com.docker.message.R;
import com.docker.message.databinding.MessageActivityBinding;
import com.docker.message.databinding.MessageSampleActivityBinding;
import com.docker.message.vm.SampleListViewModel;

@Route(path = AppRouter.MESSAGMAIN)
public class MessageActivity extends NitCommonActivity<SampleListViewModel, MessageActivityBinding> {

    @Autowired
    int style;

    @Override
    protected int getLayoutId() {
        return R.layout.message_activity;
    }

    @Override
    public SampleListViewModel getmViewModel() {
        return ViewModelProviders.of(this, factory).get(SampleListViewModel.class);
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        switch (style) {
            case 0:
                mBinding.messageFrameList.setVisibility(View.GONE);
                FragmentUtils.add(getSupportFragmentManager(),
                        (Fragment) ARouter.getInstance().build(AppRouter.MESSAGELIST).withInt("style", 0).navigation(),
                        R.id.message_frame);
                break;
            case 1:
                mBinding.messageCard.setVisibility(View.GONE);
                FragmentUtils.add(getSupportFragmentManager(),
                        (Fragment) ARouter.getInstance().build(AppRouter.MESSAGELIST).withInt("style", 1).navigation(),
                        R.id.message_frame_list);
                break;
        }
    }

    @Override
    public void initView() {
        mToolbar.hide();

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
