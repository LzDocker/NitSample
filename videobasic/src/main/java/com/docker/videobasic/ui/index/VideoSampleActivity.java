package com.docker.videobasic.ui.index;

import android.arch.lifecycle.ViewModelProviders;
import android.content.pm.ActivityInfo;
import android.content.res.Configuration;
import android.os.Bundle;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.LinearLayout;
import android.widget.RemoteViews;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.android.arouter.launcher.ARouter;
import com.dcbfhd.utilcode.utils.FragmentUtils;
import com.docker.common.common.command.NitContainerCommand;
import com.docker.common.common.model.CommonListOptions;
import com.docker.common.common.router.AppRouter;
import com.docker.common.common.ui.base.NitCommonActivity;
import com.docker.common.common.ui.container.NitCommonContainerFragment;
import com.docker.video.assist.DataInter;
import com.docker.video.assist.OnVideoViewEventHandler;
import com.docker.video.assist.ReceiverGroupManager;
import com.docker.video.entity.DataSource;
import com.docker.video.event.OnPlayerEventListener;
import com.docker.video.provider.MonitorDataProvider;
import com.docker.video.provider.VideoBean;
import com.docker.video.receiver.ReceiverGroup;
import com.docker.video.widget.BaseVideoView;
import com.docker.videobasic.R;
import com.docker.videobasic.databinding.SingleVideoActivityBinding;
import com.docker.videobasic.databinding.VideoSampleActivityBinding;
import com.docker.videobasic.vm.SampleListViewModel;
import com.docker.videobasic.vm.SingleVideoVm;

import java.util.ArrayList;
import java.util.List;

@Route(path = AppRouter.VIDEOINDEX)
public class VideoSampleActivity extends NitCommonActivity<SingleVideoVm, VideoSampleActivityBinding> {


    @Override
    protected int getLayoutId() {
        return R.layout.video_sample_activity;
    }

    @Override
    public SingleVideoVm getmViewModel() {
        return ViewModelProviders.of(this, factory).get(SingleVideoVm.class);
    }

    @Override
    public void initView() {
        mToolbar.hide();
        CommonListOptions commonListOptions = new CommonListOptions();
        commonListOptions.falg = 0;
//        fragments.add(SampleFragment.newInstance());
        NitCommonContainerFragment containerFragment = NitCommonContainerFragment.newinstance(commonListOptions);
        FragmentUtils.add(getSupportFragmentManager(), containerFragment, R.id.video_frame);
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
