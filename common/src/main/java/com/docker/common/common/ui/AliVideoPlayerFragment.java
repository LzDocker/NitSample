package com.docker.common.common.ui;

import android.arch.lifecycle.ViewModelProvider;
import android.arch.lifecycle.ViewModelProviders;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;

import com.aliyun.vodplayer.media.AliyunLocalSource;
import com.dcbfhd.utilcode.utils.SDCardUtils;
import com.dcbfhd.utilcode.utils.StringUtils;
import com.dcbfhd.utilcode.utils.ToastUtils;
import com.docker.common.R;
import com.docker.common.common.ui.base.NitCommonFragment;
import com.docker.common.common.video.vm.AliPlayerViewModel;
import com.docker.common.common.video.widget.AliyunScreenMode;
import com.docker.common.common.video.widget.AliyunVodPlayerView;
import com.docker.common.databinding.OpenAlivideoFrameLayoutBinding;

import javax.inject.Inject;

public class AliVideoPlayerFragment extends NitCommonFragment<AliPlayerViewModel, OpenAlivideoFrameLayoutBinding> {
    @Inject
    ViewModelProvider.Factory factory;
    private String videoUrl = null, thumbUrl = null;

    public static AliVideoPlayerFragment newInstance(String videourl, String thumburl) {
        AliVideoPlayerFragment detailFragment = new AliVideoPlayerFragment();
        Bundle bundle = new Bundle();
        bundle.putString("videoUrl", videourl);
        bundle.putString("thumbUrl", thumburl);
        detailFragment.setArguments(bundle);
        return detailFragment;
    }

    private AliyunVodPlayerView aliyunVodPlayerView;

    @Override
    protected int getLayoutId() {
        return R.layout.open_alivideo_frame_layout;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle bundle = getArguments();
        if (bundle != null) {
            videoUrl = bundle.getString("videoUrl");
            thumbUrl = bundle.getString("thumbUrl");
        }
    }

    @Override
    protected AliPlayerViewModel getViewModel() {
        return ViewModelProviders.of(this, factory).get(AliPlayerViewModel.class);
    }

    @Override
    protected void initView(View var1) {
        aliyunVodPlayerView = mBinding.get().alivideoPlayerview;
        initPlayer();
    }

    @Override
    public void initImmersionBar() {

    }


    private void initPlayer() {
        aliyunVodPlayerView.setKeepScreenOn(true);
        aliyunVodPlayerView.disableNativeLog();
        aliyunVodPlayerView.setPlayingCache(false, SDCardUtils.getSDCardPathByEnvironment() + "/safe/", 60 * 60 /*时长, s */, 300 /*大小，MB*/);
        aliyunVodPlayerView.setTheme(AliyunVodPlayerView.Theme.Blue);
        aliyunVodPlayerView.setCirclePlay(true);
        aliyunVodPlayerView.setAutoPlay(true);
        aliyunVodPlayerView.changeScreenMode(AliyunScreenMode.Full);
//        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
//                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        paleyVideoUrl();
    }


    private void paleyVideoUrl() {
        AliyunLocalSource.AliyunLocalSourceBuilder asb = new AliyunLocalSource.AliyunLocalSourceBuilder();
        if (StringUtils.isEmpty(videoUrl)) {
            ToastUtils.showShort(R.string.alivc_err_invalid_inutfile);
            return;
        }
        asb.setSource(videoUrl);
        if (!StringUtils.isEmpty(thumbUrl))
            asb.setCoverPath(thumbUrl);
        AliyunLocalSource mLocalSource = asb.build();
        aliyunVodPlayerView.setLocalSource(mLocalSource);
    }

//    private void updatePlayerViewMode() {
//        if (aliyunVodPlayerView != null) {
//            int orientation = getResources().getConfiguration().orientation;
//            if (orientation == Configuration.ORIENTATION_PORTRAIT) {
//
//                getActivity().getWindow().clearFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);
//                aliyunVodPlayerView.setSystemUiVisibility(View.SYSTEM_UI_FLAG_VISIBLE);
//                LinearLayout.LayoutParams aliVcVideoViewLayoutParams = (LinearLayout.LayoutParams) aliyunVodPlayerView
//                        .getLayoutParams();
//                aliVcVideoViewLayoutParams.height = (int) (ScreenUtils.getWidth(getActivity()) * 9.0f / 16);
//                aliVcVideoViewLayoutParams.width = ViewGroup.LayoutParams.MATCH_PARENT;
//
//
//                LogUtils.e(aliVcVideoViewLayoutParams.height + "   " + aliVcVideoViewLayoutParams.width);
//            } else if (orientation == Configuration.ORIENTATION_LANDSCAPE) {
////                if (!isStrangePhone()) {
//                getActivity().getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
//                        WindowManager.LayoutParams.FLAG_FULLSCREEN);
//                aliyunVodPlayerView.setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_STABLE
//                        | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
//                        | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
//                        | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
//                        | View.SYSTEM_UI_FLAG_FULLSCREEN
//                        | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY);
////                }
//                LinearLayout.LayoutParams aliVcVideoViewLayoutParams = (LinearLayout.LayoutParams) aliyunVodPlayerView
//                        .getLayoutParams();
//                aliVcVideoViewLayoutParams.height = ViewGroup.LayoutParams.MATCH_PARENT;
//                aliVcVideoViewLayoutParams.width = ViewGroup.LayoutParams.MATCH_PARENT;
////                aliVcVideoViewLayoutParams.width = (int) (ScreenUtils.getWidth(this) + 1500);
//                LogUtils.e(aliVcVideoViewLayoutParams.height + "   " + aliVcVideoViewLayoutParams.width);
//            }
//
//        }
//        initImmersionBar();
//    }
//
//    ///全屏
//    @Override
//    public void onConfigurationChanged(Configuration newConfig) {
//        super.onConfigurationChanged(newConfig);
//        updatePlayerViewMode();
//    }


    @Override
    public void onPause() {
        super.onPause();
        if (aliyunVodPlayerView != null)
            aliyunVodPlayerView.pause();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (aliyunVodPlayerView != null)
            aliyunVodPlayerView.onDestroy();
    }

}
