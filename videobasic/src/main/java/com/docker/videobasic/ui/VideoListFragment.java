package com.docker.videobasic.ui;

import android.annotation.SuppressLint;
import android.arch.lifecycle.ViewModelProviders;
import android.content.pm.ActivityInfo;
import android.content.res.Configuration;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.widget.FrameLayout;

import com.docker.common.common.model.CommonListOptions;
import com.docker.common.common.ui.base.NitCommonListFragment;
import com.docker.video.assist.AssistPlayer;
import com.docker.video.assist.DataInter;
import com.docker.video.assist.ReceiverGroupManager;
import com.docker.video.cover.GestureCover;
import com.docker.video.event.OnPlayerEventListener;
import com.docker.video.player.IPlayer;
import com.docker.video.receiver.OnReceiverEventListener;
import com.docker.video.receiver.ReceiverGroup;
import com.docker.videobasic.R;
import com.docker.videobasic.util.videolist.PUtil;
import com.docker.videobasic.vm.VideoListViewModel;
import com.docker.videobasic.vo.VideoEntityVo;

import java.util.zip.Inflater;

public class VideoListFragment extends NitCommonListFragment<VideoListViewModel> implements OnReceiverEventListener, OnPlayerEventListener {

    CommonListOptions commonListReq = new CommonListOptions();

    private ReceiverGroup mReceiverGroup;

    private boolean isLandScape;

    private boolean toDetail;

    private int mPlayPosition = -1;

    private int mVerticalRecyclerStart;

    private int mScreenH;

    private FrameLayout frameLayout;

    private FrameLayout fullscreenframe;

    public static VideoListFragment newInstance() {
        return new VideoListFragment();
    }

    @Override
    public VideoListViewModel getViewModel() {
        return ViewModelProviders.of(this, factory).get(VideoListViewModel.class);
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceStates) {
        super.onCreate(savedInstanceStates);
        mViewModel.playPosLv.observe(this, integer -> {
            mPlayPosition = integer.intValue();
        });
        mViewModel.onrefresh.observe(this, aBoolean -> {
            stopPlayer();
        });
    }

    @Override
    protected void initView(View var1) {
        mScreenH = PUtil.getScreenH(mBinding.get().recyclerView.getContext());
        mBinding.get().recyclerView.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @SuppressLint("NewApi")
            @Override
            public void onGlobalLayout() {
                int[] location = new int[2];
                mBinding.get().recyclerView.getLocationOnScreen(location);
                mVerticalRecyclerStart = location[1];
                mBinding.get().recyclerView.getViewTreeObserver().removeOnGlobalLayoutListener(this);
            }
        });
        mBinding.get().recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                if (mPlayPosition != -1) {
                    int itemVisibleRectHeight = getItemVisibleRectHeight(mPlayPosition);
                    if (itemVisibleRectHeight <= 0) {
                        stopPlayer();
                    }
                }
            }
        });
    }

    @Override
    public void onInvisible() {
        super.onInvisible();
        stopPlayer();
    }

    private void stopPlayer() {
        if (mPlayPosition != -1) {
            AssistPlayer.get().play(frameLayout, null);
            AssistPlayer.get().stop();
            ((VideoEntityVo) mViewModel.mItems.get(mPlayPosition)).setPlayer(false);
            mPlayPosition = -1;
        }
    }

    @Override
    public void onStart() {
        super.onStart();
        AssistPlayer.get().addOnReceiverEventListener(this);
        AssistPlayer.get().addOnPlayerEventListener(this);
        mReceiverGroup = ReceiverGroupManager.get().getLiteReceiverGroup(getContext());
        mReceiverGroup.getGroupValue().putBoolean(DataInter.Key.KEY_NETWORK_RESOURCE, false);

//        mReceiverGroup.getGroupValue().putBoolean(DataInter.Key.KEY_CONTROLLER_SCREEN_SWITCH_ENABLE, false);

    }

    @Override
    public CommonListOptions getArgument() {
        commonListReq.refreshState = 0;
        commonListReq.RvUi = 0;
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
        isLandScape = newConfig.orientation == Configuration.ORIENTATION_LANDSCAPE;
        if (newConfig.orientation == Configuration.ORIENTATION_LANDSCAPE) {
            attachFullScreen();
        } else if (newConfig.orientation == Configuration.ORIENTATION_PORTRAIT) {
            attachList();
        }
        mReceiverGroup.getGroupValue().putBoolean(DataInter.Key.KEY_IS_LANDSCAPE, isLandScape);
    }

    private void attachFullScreen() {
        mReceiverGroup.addReceiver(DataInter.ReceiverKey.KEY_GESTURE_COVER, new GestureCover(getContext()));
        mReceiverGroup.getGroupValue().putBoolean(DataInter.Key.KEY_CONTROLLER_TOP_ENABLE, true);
        fullscreenframe = new FrameLayout(this.getContext());
        ((ViewGroup) mBinding.get().empty).addView(fullscreenframe);
        AssistPlayer.get().play(fullscreenframe, null);
    }

    @Override
    public void onResume() {
        super.onResume();
        toDetail = false;
        mReceiverGroup.removeReceiver(DataInter.ReceiverKey.KEY_GESTURE_COVER);
        mReceiverGroup.getGroupValue().putBoolean(DataInter.Key.KEY_CONTROLLER_TOP_ENABLE, false);
        AssistPlayer.get().setReceiverGroup(mReceiverGroup);
        if (isLandScape) {
            attachFullScreen();
        } else {
            attachList();
        }
        int state = AssistPlayer.get().getState();
        if (state != IPlayer.STATE_PLAYBACK_COMPLETE) {
            AssistPlayer.get().resume();
        }
    }

    @Override
    public void onPause() {
        super.onPause();
        if (!toDetail) {
            AssistPlayer.get().pause();
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        AssistPlayer.get().removeReceiverEventListener(this);
        AssistPlayer.get().removePlayerEventListener(this);
        AssistPlayer.get().destroy();
    }

    @Override
    public void onReceiverEvent(int eventCode, Bundle bundle) {
        switch (eventCode) {
            case DataInter.Event.EVENT_CODE_REQUEST_BACK:
                getActivity().setRequestedOrientation(isLandScape ?
                        ActivityInfo.SCREEN_ORIENTATION_PORTRAIT :
                        ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
                if (isLandScape) {
                    attachList();
                } else {
                    onBackPressed();
                }
                break;
            case DataInter.Event.EVENT_CODE_REQUEST_TOGGLE_SCREEN:
                getActivity().setRequestedOrientation(isLandScape ?
                        ActivityInfo.SCREEN_ORIENTATION_PORTRAIT :
                        ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
                if (isLandScape) {
                    attachList();
                }
                break;
            case DataInter.Event.CODE_REQUEST_RESUME:

                break;

            case DataInter.Event.CODE_REQUEST_PAUSE:

                break;
        }
    }

    @Override
    public void onPlayerEvent(int eventCode, Bundle bundle) {
        switch (eventCode) {
            case OnPlayerEventListener.PLAYER_EVENT_ON_PLAY_COMPLETE:
                AssistPlayer.get().stop();
                break;
        }
    }

    private void attachList() {
        if (mViewModel != null) {
            mReceiverGroup.removeReceiver(DataInter.ReceiverKey.KEY_GESTURE_COVER);
            mReceiverGroup.getGroupValue().putBoolean(DataInter.Key.KEY_CONTROLLER_TOP_ENABLE, false);
            if (mViewModel.view != null) {
                AssistPlayer.get().play(mViewModel.view.findViewById(R.id.layoutContainer), null);
            }
        }
    }

    /**
     * 获取Item中渲染视图的可见高度
     *
     * @param position
     * @return
     */
    private int getItemVisibleRectHeight(int position) {
        RecyclerView.ViewHolder itemHolder = getItemHolder(position);
        if (itemHolder == null)
            return 0;
        int[] location = new int[2];
        itemHolder.itemView.findViewById(R.id.layoutContainer).getLocationOnScreen(location);
        int height = itemHolder.itemView.findViewById(R.id.layoutContainer).getHeight();

        int visibleRect;
        if (location[1] <= mVerticalRecyclerStart) {
            visibleRect = location[1] - mVerticalRecyclerStart + height;
        } else {
            if (location[1] + height >= mScreenH) {
                visibleRect = mScreenH - location[1];
            } else {
                visibleRect = height;
            }
        }
        return visibleRect;
    }

    private RecyclerView.ViewHolder getItemHolder(int position) {
        RecyclerView.ViewHolder viewHolder = mBinding.get().recyclerView.findViewHolderForLayoutPosition(position);
        return viewHolder;
    }

    public void onBackPressed() {
        if (isLandScape) {
            getActivity().setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
            attachList();
            return;
        }
        getActivity().finish();
    }
}
