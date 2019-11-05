package com.docker.videobasic.ui;

import android.arch.lifecycle.ViewModelProviders;
import android.content.pm.ActivityInfo;
import android.content.res.Configuration;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.docker.common.common.model.CommonListOptions;
import com.docker.common.common.ui.base.NitCommonListFragment;
import com.docker.core.util.AppExecutors;
import com.docker.video.assist.DataInter;
import com.docker.video.assist.OnVideoViewEventHandler;
import com.docker.video.assist.ReceiverGroupManager;
import com.docker.video.entity.DataSource;
import com.docker.video.event.OnPlayerEventListener;
import com.docker.video.provider.MonitorDataProvider;
import com.docker.video.provider.VideoBean;
import com.docker.video.receiver.OnReceiverEventListener;
import com.docker.video.receiver.ReceiverGroup;
import com.docker.video.widget.BaseVideoView;
import com.docker.videobasic.util.ImgUrlUtil;
import com.docker.videobasic.util.videolist.PUtil;
import com.docker.videobasic.vm.VideoListViewModel;
import com.docker.videobasic.vo.VideoFullEntityVo;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

/*
 *
 * 点击播放
 * */
public class VideoFullListFragment extends NitCommonListFragment<VideoListViewModel> implements OnPlayerEventListener, OnReceiverEventListener {

    CommonListOptions commonListReq = new CommonListOptions();


    private boolean isLandScape;
    private BaseVideoView mVideoView;
    private ReceiverGroup mReceiverGroup;
    private long mDataSourceId;
    private boolean userPause;
    private VideoFullEntityVo videoFullEntityVo;
    private LinearLayout llplay;
    private int margin = 0;

    @Inject
    AppExecutors appExecutors;

    public static VideoFullListFragment newInstance() {
        return new VideoFullListFragment();
    }

    @Override
    public VideoListViewModel getViewModel() {
        return ViewModelProviders.of(this, factory).get(VideoListViewModel.class);
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceStates) {
        super.onCreate(savedInstanceStates);
        mViewModel.scope = 2;
        mVideoView = new BaseVideoView(this.getHoldingActivity());
        mViewModel.onrefresh.observe(this, aBoolean -> {
//            stopPlayer();
//            resetPlayUi();
        });
        mViewModel.fullData.observe(this, viewVideoFullEntityVoHashMap -> {
            if (llplay != null) {
                llplay.removeAllViews();
            }
            llplay = (LinearLayout) viewVideoFullEntityVoHashMap.get("view");
            videoFullEntityVo = (VideoFullEntityVo) viewVideoFullEntityVoHashMap.get("item");
            llplay.addView(mVideoView);
            updateVideo(true);
            processVideoPlayer();
        });
    }


    private void processVideoPlayer() {
        mVideoView.stop();
        mVideoView.seekTo(-1);
        mVideoView.setOnPlayerEventListener(this);
        mVideoView.setOnReceiverEventListener(this);
        mVideoView.setEventHandler(mOnEventAssistHandler);
        mReceiverGroup = ReceiverGroupManager.get().getReceiverGroup(this.getHoldingActivity(),
                null,
                ImgUrlUtil.getCompleteImageUrl(videoFullEntityVo));
        mReceiverGroup.getGroupValue().putBoolean(DataInter.Key.KEY_NETWORK_RESOURCE, true);
        mReceiverGroup.getGroupValue().putBoolean(DataInter.Key.KEY_IS_HAS_NEXT, false);
        mVideoView.setReceiverGroup(mReceiverGroup);

        ReadyData();
        mVideoView.start();
    }

    private void ReadyData() {

        MonitorDataProvider dataProvider = new MonitorDataProvider();
        List<VideoBean> videoBeans = new ArrayList<>();
        videoBeans.add(new VideoBean(
                "你欠缺的也许并不是能力",
                "http://open-image.nosdn.127.net/image/snapshot_movie/2016/11/b/a/c36e048e284c459686133e66a79e2eba.jpg",
                ImgUrlUtil.getCompleteVideoUrl(videoFullEntityVo)));
        /*"https://mov.bn.netease.com/open-movie/nos/mp4/2016/06/22/SBP8G92E3_hd.mp4"*/
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

    private OnVideoViewEventHandler mOnEventAssistHandler = new OnVideoViewEventHandler() {
        @Override
        public void onAssistHandle(BaseVideoView assist, int eventCode, Bundle bundle) {
            super.onAssistHandle(assist, eventCode, bundle);
            switch (eventCode) {
                case DataInter.Event.CODE_REQUEST_PAUSE:
                    userPause = true;
                    break;
                case DataInter.Event.EVENT_CODE_REQUEST_BACK:
                    if (isLandScape) {
                        getHoldingActivity().setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
                    } else {
                        getHoldingActivity().finish();
                    }
                    break;
                case DataInter.Event.EVENT_CODE_REQUEST_NEXT:
//                    mDataSourceId++;
//                    mVideoView.setDataSource(generatorDataSource(mDataSourceId));
//                    mVideoView.start();
                    break;
                case DataInter.Event.EVENT_CODE_REQUEST_TOGGLE_SCREEN:
                    getHoldingActivity().setRequestedOrientation(isLandScape ?
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
    protected void initView(View var1) {


        mBinding.get().recyclerView.addOnChildAttachStateChangeListener(new RecyclerView.OnChildAttachStateChangeListener() {
            @Override
            public void onChildViewAttachedToWindow(@NonNull View view) {

            }

            @Override
            public void onChildViewDetachedFromWindow(@NonNull View view) {

            }
        });
    }


    @Override
    public void onInvisible() {
        super.onInvisible();
        if (llplay != null) {
            if (mVideoView.isInPlaybackState()) {
                mVideoView.pause();
            } else {
                mVideoView.stop();
            }
        }
    }

    @Override
    public void onVisible() {
        super.onVisible();
        if (llplay != null) {
            if (mVideoView.isInPlaybackState()) {
                if (!userPause)
                    mVideoView.resume();
            } else {
                mVideoView.rePlay(0);
            }
        }

    }

    @Override
    public CommonListOptions getArgument() {
        commonListReq.refreshState = 2;
        commonListReq.RvUi = 1;
        commonListReq.ReqParam.put("t", "video");
        commonListReq.ApiUrl = "https://www.jinxitime.com/api.php?m=dynamic.getList";
        return commonListReq;
    }

    @Override
    public void initImmersionBar() {

    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        if (newConfig.orientation == Configuration.ORIENTATION_LANDSCAPE) {
            isLandScape = true;
//            updateVideo(true);
//            llplay.removeAllViews();
//            mBinding.get().empty.addView(mVideoView);

        } else {
            isLandScape = false;
//            updateVideo(false);
//            mBinding.get().empty.removeView(mVideoView);
//            llplay.addView(mVideoView);

        }
//        mReceiverGroup.getGroupValue().putBoolean(DataInter.Key.KEY_IS_LANDSCAPE, isLandScape);
    }


    @Override
    public void onDestroy() {
        super.onDestroy();
        mVideoView.stopPlayback();
    }

    public void onBackPressed() {
        if (isLandScape) {
            getHoldingActivity().setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
            return;
        } else {
            getHoldingActivity().finish();
        }
    }

    @Override
    public void onPlayerEvent(int eventCode, Bundle bundle) {

    }

    private void updateVideo(boolean landscape) {
        LinearLayout.LayoutParams layoutParams = (LinearLayout.LayoutParams) mVideoView.getLayoutParams();
        layoutParams.width = ViewGroup.LayoutParams.MATCH_PARENT;
        layoutParams.height = ViewGroup.LayoutParams.MATCH_PARENT;
        layoutParams.setMargins(0, 0, 0, 0);
//        if (landscape) {
//            layoutParams.width = ViewGroup.LayoutParams.MATCH_PARENT;
//            layoutParams.height = ViewGroup.LayoutParams.MATCH_PARENT;
//            layoutParams.setMargins(0, 0, 0, 0);
//        } else {
//            layoutParams.width = PUtil.getScreenW(this.getHoldingActivity()) - (margin * 2);
//            layoutParams.height = layoutParams.width * 9 / 16;
//            layoutParams.setMargins(margin, margin, margin, margin);
//        }
        mVideoView.setLayoutParams(layoutParams);
    }

    @Override
    public void onReceiverEvent(int eventCode, Bundle bundle) {
        switch (eventCode) {
            case DataInter.Event.EVENT_CODE_REQUEST_BACK:
                onBackPressed();
                break;
            case DataInter.Event.EVENT_CODE_REQUEST_TOGGLE_SCREEN:
                getActivity().setRequestedOrientation(isLandScape ?
                        ActivityInfo.SCREEN_ORIENTATION_PORTRAIT :
                        ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
                if (isLandScape) {
                    mBinding.get().empty.removeView(mVideoView);
                    llplay.addView(mVideoView);
                } else {
                    llplay.removeView(mVideoView);
                    mBinding.get().empty.addView(mVideoView);
                }
                mReceiverGroup.getGroupValue().putBoolean(DataInter.Key.KEY_IS_LANDSCAPE, isLandScape);
                break;
            case DataInter.Event.CODE_REQUEST_RESUME:

                break;

            case DataInter.Event.CODE_REQUEST_PAUSE:

                break;
        }
    }
}
