package com.docker.videobasic.ui;

import android.annotation.SuppressLint;
import android.arch.lifecycle.ViewModelProviders;
import android.content.pm.ActivityInfo;
import android.content.res.Configuration;
import android.databinding.Observable;
import android.databinding.ObservableList;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.PagerSnapHelper;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.view.ViewTreeObserver;
import android.widget.Toast;

import com.dcbfhd.utilcode.utils.ScreenUtils;
import com.dcbfhd.utilcode.utils.ToastUtils;
import com.docker.common.common.model.BaseItemModel;
import com.docker.common.common.model.CommonListOptions;
import com.docker.common.common.ui.base.NitCommonListFragment;
import com.docker.core.util.AppExecutors;
import com.docker.video.assist.AssistPlayer;
import com.docker.video.assist.DataInter;
import com.docker.video.assist.ReceiverGroupManager;
import com.docker.video.cover.GestureCover;
import com.docker.video.event.OnPlayerEventListener;
import com.docker.video.player.IPlayer;
import com.docker.video.receiver.OnReceiverEventListener;
import com.docker.video.receiver.ReceiverGroup;
import com.docker.videobasic.R;
import com.docker.videobasic.util.ImgUrlUtil;
import com.docker.videobasic.util.videolist.PUtil;
import com.docker.videobasic.util.videolist.TestActivity;
import com.docker.videobasic.vm.VideoListViewModel;
import com.docker.videobasic.vo.VideoDyEntityVo;

import javax.inject.Inject;

import timber.log.Timber;

/*
 *
 * 仿抖音翻页视频界面
 * */
public class VideoDyListFragment extends NitCommonListFragment<VideoListViewModel> implements OnReceiverEventListener, OnPlayerEventListener {

    CommonListOptions commonListReq = new CommonListOptions();

    private ReceiverGroup mReceiverGroup;

    private boolean isLandScape;

    private boolean toDetail;

    private int mVerticalRecyclerStart;

    private int mScreenH;

    private int targetPos = 0;

    private PagerSnapHelper pagerSnapHelper;

    private boolean isFirst = true;

    @Inject
    AppExecutors appExecutors;

    public static VideoDyListFragment newInstance() {
        return new VideoDyListFragment();
    }

    @Override
    public VideoListViewModel getViewModel() {
        return ViewModelProviders.of(this, factory).get(VideoListViewModel.class);
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceStates) {
        super.onCreate(savedInstanceStates);
        mViewModel.scope = 1;
        mViewModel.playPosLv.observe(this, integer -> {
            targetPos = integer.intValue();
        });
        mViewModel.onrefresh.observe(this, aBoolean -> {
            stopPlayer();
            resetPlayUi();
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
        pagerSnapHelper = new PagerSnapHelper();
        pagerSnapHelper.attachToRecyclerView(mBinding.get().recyclerView);
        mBinding.get().recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                int itemVisibleRectHeight = getItemVisibleRectHeight(targetPos);
                if (Math.abs(itemVisibleRectHeight) >= ScreenUtils.getAppScreenHeight() - 100) {
                    ((VideoDyEntityVo) mViewModel.mItems.get(targetPos)).isPlayer = false;
                    stopPlayer();
                }
            }

            @Override
            public void onScrollStateChanged(@NonNull RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                if (newState == RecyclerView.SCROLL_STATE_IDLE) {
                    if (!((VideoDyEntityVo) mViewModel.mItems.get(targetPos)).isPlayer) {
                        ((VideoDyEntityVo) mViewModel.mItems.get(targetPos)).isPlayer = true;
                        targetPos = recyclerView.getLayoutManager().getPosition(pagerSnapHelper.findSnapView(recyclerView.getLayoutManager()));
                        if (mViewModel.mItems.size() >= targetPos + 1) {
                            resetReceiveGroup();
                            mViewModel.ItemVideoClick((BaseItemModel) mViewModel.mItems.get(targetPos),
                                    recyclerView.getLayoutManager().findViewByPosition(targetPos));
                        }
                        if (targetPos > mViewModel.mItems.size() - 10 && !mViewModel.loadingState) {
                            mViewModel.loadData();
                        }
                    }
                }
            }
        });

        mBinding.get().recyclerView.addOnChildAttachStateChangeListener(new RecyclerView.OnChildAttachStateChangeListener() {
            @Override
            public void onChildViewAttachedToWindow(@NonNull View view) {
                targetPos = mBinding.get().recyclerView.getLayoutManager().getPosition(view);
                Log.d("sss", "onChildViewAttachedToWindow: ====" + targetPos);
                if (targetPos == 0) { // 第一个
                    if (!((VideoDyEntityVo) mViewModel.mItems.get(0)).isPlayer && isFirst) {
                        isFirst = false;
                        ((VideoDyEntityVo) mViewModel.mItems.get(0)).isPlayer = true;
                        if (mViewModel.mItems.size() >= targetPos + 1) {
                            resetReceiveGroup();
                            view.postDelayed(() -> mViewModel.ItemVideoClick((BaseItemModel) mViewModel.mItems.get(0),
                                    mBinding.get().recyclerView.getLayoutManager().findViewByPosition(0)), 300);
                        }
                    }
                }
            }

            @Override
            public void onChildViewDetachedFromWindow(@NonNull View view) {

            }
        });
    }

    private void stopPlayer() {
        AssistPlayer.get().destroy();
        ((VideoDyEntityVo) mViewModel.mItems.get(targetPos)).isPlayer = false;
    }

    @Override
    public void onInvisible() {
        super.onInvisible();
        AssistPlayer.get().destroy();
    }

    @Override
    public void onVisible() {
        super.onVisible();
        resetPlayUi();
    }

    private void resetPlayUi() {
        toDetail = false;
        AssistPlayer.get().addOnPlayerEventListener(this);
        mReceiverGroup.removeReceiver(DataInter.ReceiverKey.KEY_GESTURE_COVER);
        mReceiverGroup.getGroupValue().putBoolean(DataInter.Key.KEY_CONTROLLER_TOP_ENABLE, false);
        mReceiverGroup.getGroupValue().putBoolean(DataInter.Key.KEY_COMPLETE_SHOW, false);
        AssistPlayer.get().setReceiverGroup(mReceiverGroup);
    }

    @Override
    public void onStart() {
        super.onStart();
        AssistPlayer.get().addOnReceiverEventListener(this);
        AssistPlayer.get().addOnPlayerEventListener(this);
//        mReceiverGroup = ReceiverGroupManager.get().getLiteReceiverGroup(getContext());
        mReceiverGroup = ReceiverGroupManager.get().getReceiverGroup(this.getHoldingActivity(),
                null);
        mReceiverGroup.getGroupValue().putBoolean(DataInter.Key.KEY_NETWORK_RESOURCE, false);
        mReceiverGroup.getGroupValue().putBoolean(DataInter.Key.KEY_CONTROLLER_SCREEN_SWITCH_ENABLE, false);
        mReceiverGroup.getGroupValue().putBoolean(DataInter.Key.KEY_COMPLETE_SHOW, false);
    }

    public void resetReceiveGroup() {
        mReceiverGroup = ReceiverGroupManager.get().getReceiverGroup(this.getHoldingActivity(),
                null, ImgUrlUtil.getCompleteImageUrl((VideoDyEntityVo) mViewModel.mItems.get(targetPos)));
        mReceiverGroup.getGroupValue().putBoolean(DataInter.Key.KEY_NETWORK_RESOURCE, false);
        mReceiverGroup.getGroupValue().putBoolean(DataInter.Key.KEY_CONTROLLER_SCREEN_SWITCH_ENABLE, false);
        mReceiverGroup.getGroupValue().putBoolean(DataInter.Key.KEY_COMPLETE_SHOW, false);
        AssistPlayer.get().addOnPlayerEventListener(this);
    }

    @Override
    public CommonListOptions getArgument() {
        commonListReq.refreshState = 2;
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
        mReceiverGroup.getGroupValue().putBoolean(DataInter.Key.KEY_COMPLETE_SHOW, false);
    }

    private void attachFullScreen() {
        mReceiverGroup.addReceiver(DataInter.ReceiverKey.KEY_GESTURE_COVER, new GestureCover(getContext()));
        mReceiverGroup.getGroupValue().putBoolean(DataInter.Key.KEY_CONTROLLER_TOP_ENABLE, true);
        mReceiverGroup.getGroupValue().putBoolean(DataInter.Key.KEY_COMPLETE_SHOW, false);
        mBinding.get().empty.setVisibility(View.VISIBLE);
        AssistPlayer.get().play(mBinding.get().empty, null);
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
                getActivity().setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
                attachList();

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
                if (mViewModel.mItems.size() > targetPos + 1) {
                    mBinding.get().recyclerView.smoothScrollToPosition(targetPos + 1);
                }
                break;
        }
    }

    private void attachList() {
        if (mViewModel != null) {
            mReceiverGroup.removeReceiver(DataInter.ReceiverKey.KEY_GESTURE_COVER);
            mReceiverGroup.getGroupValue().putBoolean(DataInter.Key.KEY_COMPLETE_SHOW, false);
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
        AssistPlayer.get().destroy();
        getActivity().finish();
    }

    @Override
    public void onLowMemory() {
        super.onLowMemory();
        AssistPlayer.get().destroy();
        Log.d("sss", "onLowMemory: ====================------===");
    }
}
