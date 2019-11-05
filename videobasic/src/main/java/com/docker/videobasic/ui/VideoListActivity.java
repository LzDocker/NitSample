package com.docker.videobasic.ui;

import android.arch.lifecycle.ViewModelProviders;
import android.content.pm.ActivityInfo;
import android.content.res.Configuration;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.FrameLayout;
import android.widget.LinearLayout;

import com.aliyun.vodplayer.media.AliyunVodPlayer;
import com.aliyun.vodplayer.media.IAliyunVodPlayer;
import com.dcbfhd.utilcode.utils.FragmentUtils;
import com.docker.common.common.adapter.CommonpagerAdapter;
import com.docker.common.common.command.NitContainerCommand;
import com.docker.common.common.ui.base.NitCommonActivity;
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
import com.docker.videobasic.databinding.VideoListActivityBinding;
import com.docker.videobasic.util.videolist.BaseVideoListFragment;
import com.docker.videobasic.vm.SingleVideoVm;

import java.util.ArrayList;
import java.util.List;

public class VideoListActivity extends NitCommonActivity<SingleVideoVm, VideoListActivityBinding> {
    public List<Fragment> fragments = new ArrayList<>();

    @Override
    protected int getLayoutId() {
        return R.layout.video_list_activity;
    }

    @Override
    public SingleVideoVm getmViewModel() {
        return ViewModelProviders.of(this, factory).get(SingleVideoVm.class);
    }

    @Override
    public void initView() {
        mToolbar.hide();
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
        fragments.add(VideoListFragment.newInstance());
        fragments.add(VideoDyListFragment.newInstance());
//        fragments.add(VideoFullListFragment.newInstance());
//        fragments.add(VideoFullListFragment.newInstance());
        mBinding.viewpager.setAdapter(new CommonpagerAdapter(getSupportFragmentManager(), fragments));
        mBinding.viewpager.setCurrentItem(0);

    }

    @Override
    public void initObserver() {

    }

    @Override
    public void initRouter() {

    }

    @Override
    public NitContainerCommand providerNitContainerCommand(int flag) {
        return null;
    }


    @Override
    public void onBackPressed() {
        if (fragments.get(mBinding.viewpager.getCurrentItem()) instanceof VideoListFragment) {
            ((VideoListFragment) fragments.get(mBinding.viewpager.getCurrentItem())).onBackPressed();
        } else {
            ((VideoDyListFragment) fragments.get(mBinding.viewpager.getCurrentItem())).onBackPressed();
        }

//        if(fragment!=null){
//            fragment.onBackPressed();
//        }else {
//            super.onBackPressed();
//        }
    }


}
