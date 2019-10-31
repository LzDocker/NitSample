package com.docker.videobasic.ui;

import android.arch.lifecycle.ViewModelProviders;
import android.content.pm.ActivityInfo;
import android.content.res.Configuration;
import android.os.Bundle;
import android.view.ViewGroup;
import android.widget.LinearLayout;

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
import com.docker.videobasic.vm.SingleVideoVm;

import java.util.ArrayList;
import java.util.List;

public class VideoListActivity extends NitCommonActivity<SingleVideoVm, SingleVideoActivityBinding> implements OnPlayerEventListener {

    private BaseVideoView mVideoView;
    private ReceiverGroup mReceiverGroup;
    private boolean isLandscape;
    private long mDataSourceId;
    private boolean userPause;

    @Override
    protected int getLayoutId() {
        return R.layout.single_video_activity;
    }

    @Override
    public SingleVideoVm getmViewModel() {
        return ViewModelProviders.of(this, factory).get(SingleVideoVm.class);
    }

    @Override
    public void initView() {
        mToolbar.hide();
        mVideoView = mBinding.videoView;
        initPlay();
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


    private void ReadyData() {

        MonitorDataProvider dataProvider = new MonitorDataProvider();
        List<VideoBean> videoBeans = new ArrayList<>();
        videoBeans.add(new VideoBean(
                "你欠缺的也许并不是能力",
                "http://open-image.nosdn.127.net/image/snapshot_movie/2016/11/b/a/c36e048e284c459686133e66a79e2eba.jpg",
                "https://mov.bn.netease.com/open-movie/nos/mp4/2016/06/22/SBP8G92E3_hd.mp4"));
        dataProvider.setTestData(videoBeans);
        mVideoView.setDataProvider(dataProvider);
        mVideoView.setDataSource(generatorDataSource(mDataSourceId));

//        DataSource dataSource = new DataSource();
//        dataSource.setData("https://oimryzjfe.qnssl.com/content/1F3D7F815F2C6870FB512B8CA2C3D2C1.mp4");
//        dataSource.setTitle("你欠缺的也许并不是能力");
//        mVideoView.setDataSource(dataSource);
    }


    private DataSource generatorDataSource(long id) {
        DataSource dataSource = new DataSource();
        dataSource.setId(id);
        return dataSource;
    }

    private void initPlay() {
        updateVideo(false);

        mVideoView.setOnPlayerEventListener(this);
        mVideoView.setEventHandler(mOnEventAssistHandler);
        mReceiverGroup = ReceiverGroupManager.get().getReceiverGroup(this,
                null,
                "http://open-image.nosdn.127.net/image/snapshot_movie/2016/11/b/a/c36e048e284c459686133e66a79e2eba.jpg");
        mReceiverGroup.getGroupValue().putBoolean(DataInter.Key.KEY_NETWORK_RESOURCE, true);
        mReceiverGroup.getGroupValue().putBoolean(DataInter.Key.KEY_IS_HAS_NEXT, false);
        mVideoView.setReceiverGroup(mReceiverGroup);

        ReadyData();
        mVideoView.start();


        /*
        * "你欠缺的也许并不是能力",
                "http://open-image.nosdn.127.net/image/snapshot_movie/2016/11/b/a/c36e048e284c459686133e66a79e2eba.jpg",
                "https://mov.bn.netease.com/open-movie/nos/mp4/2016/06/22/SBP8G92E3_hd.mp4"
        * */


        // If you want to start play at a specified time,
        // please set this method.
        //mVideoView.start(30*1000);
//        testbut.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                int i = mVideoView.getCurrentPosition();
//                int d = mVideoView.getDuration();
//                Log.e("getCurrentPosition---", i + "");
//                Log.e("getDuration---", d + "");
//            }
//        });

    }


    @Override
    protected void onPause() {
        super.onPause();
        if (mVideoView.isInPlaybackState()) {
            mVideoView.pause();
        } else {
            mVideoView.stop();
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (mVideoView.isInPlaybackState()) {
            if (!userPause)
                mVideoView.resume();
        } else {
            mVideoView.rePlay(0);
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mVideoView.stopPlayback();
    }

    @Override
    public void onBackPressed() {
        if (isLandscape) {
            setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
            return;
        }
        super.onBackPressed();
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        if (newConfig.orientation == Configuration.ORIENTATION_LANDSCAPE) {
            isLandscape = true;
            updateVideo(true);
        } else {
            isLandscape = false;
            updateVideo(false);
        }
        mReceiverGroup.getGroupValue().putBoolean(DataInter.Key.KEY_IS_LANDSCAPE, isLandscape);
    }

    private OnVideoViewEventHandler mOnEventAssistHandler = new OnVideoViewEventHandler() {
        @Override
        public void onAssistHandle(BaseVideoView assist, int eventCode, Bundle bundle) {
            super.onAssistHandle(assist, eventCode, bundle);
            switch (eventCode) {
                case DataInter.Event.CODE_REQUEST_PAUSE:
                    userPause = true;
                    break;
                case DataInter.Event.EVENT_CODE_REQUEST_BACK:
                    if (isLandscape) {
                        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
                    } else {
                        finish();
                    }
                    break;
                case DataInter.Event.EVENT_CODE_REQUEST_NEXT:
                    mDataSourceId++;
                    mVideoView.setDataSource(generatorDataSource(mDataSourceId));
                    mVideoView.start();
                    break;
                case DataInter.Event.EVENT_CODE_REQUEST_TOGGLE_SCREEN:
                    setRequestedOrientation(isLandscape ?
                            ActivityInfo.SCREEN_ORIENTATION_PORTRAIT :
                            ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
                    break;
                case DataInter.Event.EVENT_CODE_ERROR_SHOW:
                    mVideoView.stop();
                    break;
            }
        }
    };

    @Override
    public void onPlayerEvent(int eventCode, Bundle bundle) {
        switch (eventCode) {
            case OnPlayerEventListener.PLAYER_EVENT_ON_VIDEO_RENDER_START:

                break;
            case OnPlayerEventListener.PLAYER_EVENT_ON_PLAY_COMPLETE:

                break;
            case OnPlayerEventListener.PLAYER_EVENT_ON_STATUS_CHANGE:
                break;
            case OnPlayerEventListener.PLAYER_EVENT_ON_RESUME:
                userPause = false;
                break;
        }
    }

    private void updateVideo(boolean landscape) {
        LinearLayout.LayoutParams layoutParams = (LinearLayout.LayoutParams) mVideoView.getLayoutParams();
        layoutParams.width = ViewGroup.LayoutParams.MATCH_PARENT;
        layoutParams.height = ViewGroup.LayoutParams.MATCH_PARENT;
        layoutParams.setMargins(0, 0, 0, 0);
//        if(landscape){
//            layoutParams.width = ViewGroup.LayoutParams.MATCH_PARENT;
//            layoutParams.height = ViewGroup.LayoutParams.MATCH_PARENT;
//            layoutParams.setMargins(0, 0, 0, 0);
//        }else{
//            layoutParams.width = PUtil.getScreenW(this) - (margin*2);
//            layoutParams.height = layoutParams.width * 9/16;
//            layoutParams.setMargins(margin, margin, margin, margin);
//        }
        mVideoView.setLayoutParams(layoutParams);
    }
}
