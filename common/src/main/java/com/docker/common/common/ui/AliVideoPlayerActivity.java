package com.docker.common.common.ui;

import android.arch.lifecycle.ViewModelProvider;
import android.arch.lifecycle.ViewModelProviders;
import android.content.res.Configuration;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.LinearLayout;

import com.alibaba.android.arouter.facade.annotation.Autowired;
import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.android.arouter.launcher.ARouter;
import com.alivc.player.VcPlayerLog;
import com.aliyun.vodplayer.media.AliyunLocalSource;
import com.dcbfhd.utilcode.utils.LogUtils;
import com.dcbfhd.utilcode.utils.SDCardUtils;
import com.dcbfhd.utilcode.utils.StringUtils;
import com.dcbfhd.utilcode.utils.ToastUtils;
import com.docker.common.R;
import com.docker.common.common.router.AppRouter;
import com.docker.common.common.video.utils.ScreenUtils;
import com.docker.common.common.video.vm.AliPlayerViewModel;
import com.docker.common.common.video.widget.AliyunVodPlayerView;
import com.docker.common.databinding.OpenAlivideoLayoutBinding;
import com.docker.core.base.BaseActivity;

import javax.inject.Inject;

// 全屏显示视频
@Route(path = AppRouter.Video_Player)
public class AliVideoPlayerActivity extends BaseActivity<AliPlayerViewModel, OpenAlivideoLayoutBinding> {


    @Inject
    ViewModelProvider.Factory factory;


    @Override
    protected int getLayoutId() {
        return R.layout.open_alivideo_layout;
    }

    @Override
    public AliPlayerViewModel getmViewModel() {
        return ViewModelProviders.of(this, factory).get(AliPlayerViewModel.class);
    }


    @Autowired()
    public String videoUrl, thumbUrl;

    @Override
    protected void inject() {
        super.inject();
        ARouter.getInstance().inject(this);
    }

    private AliyunVodPlayerView aliyunVodPlayerView;


    @Override
    public void onCreate(@Nullable Bundle savedInstanceStates) {
        super.onCreate(savedInstanceStates);
        mToolbar.hide();
        initView();
//        FragmentUtils.add(getSupportFragmentManager(),AliVideoPlayerFragment.newInstance(videoUrl,thumbUrl),R.id.alivideo_frame);
    }


    public void initView() {
        aliyunVodPlayerView = mBinding.alivideoPlayerview;
        initPlayer();
        String videoUrl =
                "https://zaijiaxue.oss-cn-beijing.aliyuncs.com/static2/var/upload/cn/language/6%20%E9%BB%84%E5%B1%B1%E5%A5%87%E6%9D%BE_L%20CN.mp4";
        String thumbUrl = "http://app.zjxk12.com/var/upload/2019/05/2019050508195443760_600x400.jpg";

    }

    private void initPlayer() {
        aliyunVodPlayerView.setKeepScreenOn(true);
        aliyunVodPlayerView.disableNativeLog();
        aliyunVodPlayerView.setPlayingCache(false, SDCardUtils.getSDCardPathByEnvironment() + "/safe/", 60 * 60 /*时长, s */, 300 /*大小，MB*/);
        aliyunVodPlayerView.setTheme(AliyunVodPlayerView.Theme.Blue);
        aliyunVodPlayerView.setCirclePlay(true);
        aliyunVodPlayerView.setAutoPlay(true);
//        aliyunVodPlayerView.changeScreenMode(AliyunScreenMode.Full);
//        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
//                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        paleyVideoUrl();
    }


    private void paleyVideoUrl() {
        AliyunLocalSource.AliyunLocalSourceBuilder asb = new AliyunLocalSource.AliyunLocalSourceBuilder();
        if (StringUtils.isEmpty(videoUrl)) {
            ToastUtils.showShort(R.string.alivc_err_invalid_inutfile);
            this.finish();
            return;
        }
        if (!StringUtils.isEmpty(thumbUrl)) {
            asb.setCoverPath(thumbUrl);
        }
        asb.setSource(videoUrl);
        AliyunLocalSource mLocalSource = asb.build();
        aliyunVodPlayerView.setLocalSource(mLocalSource);
    }

    @Override
    protected void onPause() {
        super.onPause();
        if (aliyunVodPlayerView != null)
            aliyunVodPlayerView.pause();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (aliyunVodPlayerView != null)
            aliyunVodPlayerView.onDestroy();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        if (aliyunVodPlayerView != null) {
            aliyunVodPlayerView.onStop();
        }
    }


    ///全屏
    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        updatePlayerViewMode();

    }

    private void updatePlayerViewMode() {
        if (aliyunVodPlayerView != null) {
            int orientation = getResources().getConfiguration().orientation;
            if (orientation == Configuration.ORIENTATION_PORTRAIT) {

                this.getWindow().clearFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);
                aliyunVodPlayerView.setSystemUiVisibility(View.SYSTEM_UI_FLAG_VISIBLE);
                LinearLayout.LayoutParams aliVcVideoViewLayoutParams = (LinearLayout.LayoutParams) aliyunVodPlayerView
                        .getLayoutParams();
                aliVcVideoViewLayoutParams.height = (int) (ScreenUtils.getWidth(this) * 9.0f / 16);
                aliVcVideoViewLayoutParams.width = ViewGroup.LayoutParams.MATCH_PARENT;
                LogUtils.e(aliVcVideoViewLayoutParams.height + "   " + aliVcVideoViewLayoutParams.width);
            } else if (orientation == Configuration.ORIENTATION_LANDSCAPE) {
//                if (!isStrangePhone()) {
                this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                        WindowManager.LayoutParams.FLAG_FULLSCREEN);
                aliyunVodPlayerView.setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                        | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                        | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                        | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                        | View.SYSTEM_UI_FLAG_FULLSCREEN
                        | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY);
//                }
                LinearLayout.LayoutParams aliVcVideoViewLayoutParams = (LinearLayout.LayoutParams) aliyunVodPlayerView
                        .getLayoutParams();
                aliVcVideoViewLayoutParams.height = ViewGroup.LayoutParams.MATCH_PARENT;
                aliVcVideoViewLayoutParams.width = ViewGroup.LayoutParams.MATCH_PARENT;
//                aliVcVideoViewLayoutParams.width = (int) (ScreenUtils.getWidth(this) + 1500);
                LogUtils.e(aliVcVideoViewLayoutParams.height + "   " + aliVcVideoViewLayoutParams.width);
            }
        }
    }

    protected boolean isStrangePhone() {
        boolean strangePhone = "mx5".equalsIgnoreCase(Build.DEVICE)
                || "Redmi Note2".equalsIgnoreCase(Build.DEVICE)
                || "Z00A_1".equalsIgnoreCase(Build.DEVICE)
                || "hwH60-L02".equalsIgnoreCase(Build.DEVICE)
                || "hermes".equalsIgnoreCase(Build.DEVICE)
                || ("V4".equalsIgnoreCase(Build.DEVICE) && "Meitu".equalsIgnoreCase(Build.MANUFACTURER))
                || ("m1metal".equalsIgnoreCase(Build.DEVICE) && "Meizu".equalsIgnoreCase(Build.MANUFACTURER));

        VcPlayerLog.e("lfj1115 ", " Build.Device = " + Build.DEVICE + " , isStrange = " + strangePhone);
        return strangePhone;
    }

}
