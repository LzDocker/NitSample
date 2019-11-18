package com.bfhd.evaluate.ui;

import android.arch.lifecycle.ViewModelProviders;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.Build;
import android.os.Bundle;
import android.os.Message;
import android.support.annotation.IntRange;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bfhd.circle.base.adapter.HivsAbsSampleAdapter;
import com.bfhd.evaluate.R;
import com.bfhd.evaluate.databinding.FragmentLessonRecyclerBinding;
import com.bfhd.evaluate.databinding.ItemAudioLessonDetailBinding;
import com.bfhd.evaluate.view.ScrollLinearLayoutManager;
import com.bfhd.evaluate.vm.EnStudyViewModel;
import com.bfhd.evaluate.vo.LessonDetailVo;
import com.dcbfhd.utilcode.utils.LogUtils;
import com.dcbfhd.utilcode.utils.ToastUtils;
import com.docker.common.common.ui.base.NitCommonFragment;
import com.docker.core.util.AppExecutors;
import com.docker.video.AlivcLiveRoom.TimeFormater;

import java.io.IOException;
import java.util.Timer;
import java.util.TimerTask;

import javax.inject.Inject;

import timber.log.Timber;

/**
 * 新概念课程详情 数据列表
 * {@link com.bfhd.evaluate.vo.RadioLessonVo}
 */
public class AudioLessonDetailFragment extends NitCommonFragment<EnStudyViewModel, FragmentLessonRecyclerBinding> {
    @Inject
    AppExecutors appExecutors;

    public static AudioLessonDetailFragment newInstance() {
        AudioLessonDetailFragment welcomeFragment = new AudioLessonDetailFragment();
//        Bundle bundle = new Bundle();
//        bundle.putString("id", id);
//        bundle.putBoolean("isPhoto", isPhoto);
//        welcomeFragment.setArguments(bundle);
        return welcomeFragment;
    }

    private ScrollLinearLayoutManager scrollLinearLayoutManager;


    private MediaPlayer followMediaPlayer;

    private boolean isStart = false, isPlay = false, isPhoto;

    private boolean isLast = false;

    public int nowChoose = 0, prePostion = 0;
    Double lastEndTime = 0d, startTime = 0d;

    public void setLast(boolean last) {
        isLast = last;
    }


    private LessonDetailVo lessonDetailVo = null;

    public void setLessonDetailVo(LessonDetailVo lessonDetailVo) {
        this.lessonDetailVo = lessonDetailVo;
        initFolloWlMedia();
    }

    public LessonDetailVo getLessonDetailVo() {
        return lessonDetailVo;
    }

    public void notiRecycler() {
        if (lessonDetailVo == null)
            return;
        if (simpleCommonRecyclerAdapter.getmObjects() != null)
            simpleCommonRecyclerAdapter.getmObjects().clear();
//        if (loopModel == 2 || loopModel == 1)
        clearNowChoose();
//        int integer = CacheUtils.getLessonCatch("l_" + lessonDetailVo.getId());
//        if (integer > 0)
//            nowChoose = integer - 1;
        simpleCommonRecyclerAdapter.getmObjects().addAll(lessonDetailVo.getSort_content());
        simpleCommonRecyclerAdapter.notifyDataSetChanged();
    }

    private HivsAbsSampleAdapter simpleCommonRecyclerAdapter;

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_lesson_recycler;
    }

    @Override
    public EnStudyViewModel getViewModel() {
        return ViewModelProviders.of(this, factory).get(EnStudyViewModel.class);
    }


    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }

    @Override
    protected void initView(View var1) {
        simpleCommonRecyclerAdapter = new HivsAbsSampleAdapter(R.layout.item_audio_lesson_detail, com.bfhd.evaluate.BR.item) {
            @Override
            public void onRealviewBind(ViewHolder holder, int position) {
                ItemAudioLessonDetailBinding readBinding = (ItemAudioLessonDetailBinding) holder.getBinding();
                readBinding.studyReadShow.setOnClickListener(view -> {
                    readBinding.getItem().setCH(true);
                    readBinding.studyReadShow.setVisibility(View.GONE);
                    readBinding.studyReadCh.setVisibility(View.VISIBLE);
                });
                readBinding.studyReadCh.setOnClickListener(v -> {
                    v.setVisibility(View.GONE);
                    readBinding.studyReadShow.setVisibility(View.VISIBLE);
                });
                readBinding.studyReadEn.setOnClickListener(v -> {
                    if (prePostion == position) {
                        clickNow();
                    } else {
                        LinearLayout linearLayout = (LinearLayout) v.getParent();
                        clickItemView(position, linearLayout);
                    }
                });
            }
        };
        scrollLinearLayoutManager = new ScrollLinearLayoutManager(getHoldingActivity());
        mBinding.get().fragmentLessonRecycler.setLayoutManager(scrollLinearLayoutManager);
        mBinding.get().fragmentLessonRecycler.setAdapter(simpleCommonRecyclerAdapter);
        mBinding.get().fragmentLessonRecycler.setItemViewCacheSize(30);
        timerTask = new TimerTask() {
            @Override
            public void run() {
                try {
                    if (followMediaPlayer == null)
                        return;
                    if ((getHoldingActivity()) == null) {
                        followMediaPlayer.stop();
                        followMediaPlayer.release();
                        return;
                    }
                    if ((getHoldingActivity()).isFinishing()) {
                        followMediaPlayer.stop();
                        followMediaPlayer.release();
                        return;
                    }

                    boolean isp = followMediaPlayer.isPlaying();
                    if (isp != isTPlay) {
                        isTPlay = isp;
                        notiActivity(!isp);
                    }
                    int nowCur = followMediaPlayer.getCurrentPosition();
                    int timeBean = nowCur;/// 1000;
                    //取出下一段开始时间，后台会存在跳
                    if (isp)
                        if (loopModel == 3) {
                            if (lastEndTime <= (timeBean + 300)) {
                                followMediaPlayer.pause();
                                followMediaPlayer.seekTo(startTime.intValue() - 200);
                            }
                        }
                    if (isp)
                        getHoldingActivity().runOnUiThread(() -> {
                            changeRecycler(timeBean);
                        });

                    if (nowDuration == 0)
                        return;

                    Float d = ((float) nowCur / (float) nowDuration * 100);//Double.valueOf((nowCur / nowDuration) * 100);

                    Timber.i(String.format("lastEndTime=%s,timeBean=%s,nowCur=%s,nowDuration=%s,d=%s,isp=%s,", lastEndTime, timeBean, nowCur, nowDuration, d, isp));

                    Message message = Message.obtain();
                    message.arg1 = d.intValue() + 3;
                    message.what = 0x11;///###¥¥¥¥¥¥¥¥¥¥¥31313
                    if (((AudioLessonDetailActivity) getHoldingActivity()).lessonAudioDetailHandler != null) {
                        ((AudioLessonDetailActivity) getHoldingActivity()).lessonAudioDetailHandler.sendMessage(message);
                    }
                } catch (IllegalStateException e) {
                    e.printStackTrace();
                    if ((getHoldingActivity()) == null || getHoldingActivity().isFinishing()) {
                        this.cancel();
                    }
                }
            }
        };
        timer = new Timer();
        timer.schedule(timerTask, 0, 200);
    }


    @Override
    public void initImmersionBar() {
    }


    private boolean isTPlay = false;
    private boolean isStop = false;

    @Override
    public void onStop() {
        super.onStop();
        LogUtils.e("ddssdds   stop");
        isStop = true;
        if (timer != null)
            timer.cancel();

    }

    private Timer timer;

    private TimerTask timerTask;

    private int nowDuration = 0;

    //初始化列表播放器
    public void initFolloWlMedia() {
        if (lessonDetailVo == null)
            return;
        appExecutors.diskIO().execute(() -> {
            try {
                followMediaPlayer = new MediaPlayer();
                followMediaPlayer.setDataSource(lessonDetailVo.getRadio());
                if (followMediaPlayer == null)
                    followMediaPlayer = new MediaPlayer();
                followMediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);
                followMediaPlayer.setOnErrorListener(new MediaPlayer.OnErrorListener() {
                    @Override
                    public boolean onError(MediaPlayer mp, int what, int extra) {
                        LogUtils.e(what, extra);
                        return false;
                    }
                });
                followMediaPlayer.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
                    @Override
                    public void onPrepared(MediaPlayer mp) {
                        try {
                            isCompleate = false;
                            LogUtils.i("setOnPreparedListener :  " + loopModel + "  nowDuration:" + nowDuration + "  isNoClass:" + isNoClass);
                            if (loopModel == 1 || loopModel == 2) {//单篇
                                if (!isNoClass)
                                    followMediaPlayer.start();
                            }
                            nowDuration = followMediaPlayer.getDuration();
                            isPlay = true;
                            isStart = true;
                            if (!isNoClass) {
                                Message message = Message.obtain();
                                message.arg1 = 1;
                                message.what = 0x12;
                                if (getHoldingActivity() != null)
                                    ((AudioLessonDetailActivity) getHoldingActivity()).lessonAudioDetailHandler.sendMessage(message);
                            }
                        } catch (IllegalStateException e) {
                        }
                    }
                });
                followMediaPlayer.setOnSeekCompleteListener(new MediaPlayer.OnSeekCompleteListener() {
                    @Override
                    public void onSeekComplete(MediaPlayer mp) {
                        mp.start();
                    }
                });
                followMediaPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
                    @Override
                    public void onCompletion(MediaPlayer mp) {
                        if (getHoldingActivity() != null) {
                            if (!getHoldingActivity().isFinishing()) {
                                isCompleate = true;
                                LogUtils.i("setOnCompleteListener:isNextPre=" + ((AudioLessonDetailActivity) getHoldingActivity()).isNextPre);
                                // CacheUtils.putLessonCatch("l_" + lessonDetailVo.getId(), 0);
                                if (loopModel == 1) {
                                    getHoldingActivity().runOnUiThread(() -> {
                                        mBinding.get().fragmentLessonRecycler.smoothScrollToPosition(0);
                                        LessonDetailVo.SortContentBean studyReadVo = (LessonDetailVo.SortContentBean) simpleCommonRecyclerAdapter.getmObjects().get(prePostion);
                                        studyReadVo.setChoose(false);
                                        simpleCommonRecyclerAdapter.notifyItemChanged(prePostion);
                                        nowChoose = 0;
                                        prePostion = 0;
                                        lastEndTime = 0d;
                                        startTime = 0d;
                                    });
                                    try {
                                        followMediaPlayer.release();
                                        followMediaPlayer = null;
                                    } catch (IllegalStateException e) {
                                        e.printStackTrace();
                                    }
                                    initFolloWlMedia();
                                } else if (loopModel == 2) {
                                    if (isLast) {
                                        ToastUtils.showShort("没有下一篇了");
                                        try {
                                            followMediaPlayer.pause();
//                                            followMediaPlayer.reset();
                                        } catch (IllegalStateException e) {
                                            e.printStackTrace();
                                        }

                                    } else {
                                        ToastUtils.showShort("即将播放下一篇");
                                        mediaStop();
                                        isPlay = false;
                                        Message message = Message.obtain();
                                        message.arg1 = 0;
                                        message.what = 0x121;
                                        ((AudioLessonDetailActivity) getHoldingActivity()).lessonAudioDetailHandler.sendMessage(message);
                                    }
                                }
                            }
                        }
                    }
                });
                followMediaPlayer.prepareAsync();
            } catch (IllegalStateException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
        });

    }

    @Override
    public void onResume() {
        super.onResume();
        try {
            if (isStop) {
                timerTask = new TimerTask() {
                    @Override
                    public void run() {
                        //  isTPlay = false;
                        boolean isp = followMediaPlayer.isPlaying();
                        if (isp != isTPlay) {
                            isTPlay = isp;
                            notiActivity(!isp);
                        }
                        int nowCur = followMediaPlayer.getCurrentPosition();
                        int timeBean = nowCur;/// 1000;
                        //取出下一段开始时间，后台会存在跳

                        if (isp)
                            if (loopModel == 3) {
                                if (lastEndTime <= (timeBean + 300)) {
                                    followMediaPlayer.pause();
                                    followMediaPlayer.seekTo(startTime.intValue() - 200);
                                }
                            }
                        if (isp)
                            getHoldingActivity().runOnUiThread(() -> {
                                changeRecycler(timeBean);
                            });
                        if (nowDuration == 0)
                            return;
                        Float d = ((float) nowCur / (float) nowDuration * 100);//Double.valueOf((nowCur / nowDuration) * 100);
                        LogUtils.i(" resume " + lastEndTime + "----dddddddd-----" + timeBean + "  " + nowCur + "   " + nowDuration + "  " + d + "   isp:" + isp);
                        Message message = Message.obtain();
                        message.arg1 = d.intValue() + 3;
                        message.what = 0x11;///###¥¥¥¥¥¥¥¥¥¥¥31313
                        if (((AudioLessonDetailActivity) getHoldingActivity()).lessonAudioDetailHandler != null) {
                            ((AudioLessonDetailActivity) getHoldingActivity()).lessonAudioDetailHandler.sendMessage(message);
                        }
                    }
                };
//
                timer = new Timer();
                timer.schedule(timerTask, 0, 200);

            }
        } catch (IllegalStateException e) {
            e.printStackTrace();
//            if ((getHoldingActivity()) == null || getHoldingActivity().isFinishing()) {
//                timer.cancel();
//            }
        }

    }


    private boolean isNoClass = false;

    public void noPlayNext() {
        isNoClass = true;
//        followMediaPlayer.setSource(lessonDetailVo.getRadio());
//        folloWlMedia.prepared();
        initFolloWlMedia();
    }

    public void playNext() {
        appExecutors.diskIO().execute(() -> {
            try {
                if (followMediaPlayer != null)
                    followMediaPlayer.stop();
            } catch (IllegalStateException e) {
                e.printStackTrace();
            }
            initFolloWlMedia();
        });

    }

    private boolean isCompleate = false;//

    //播放
    public boolean readPlay() {
        LogUtils.e("isplay:" + isPlay);
        if (followMediaPlayer == null)
            return false;
        if (isCompleate && isLast) {
            if (prePostion >= 0) {
                LessonDetailVo.SortContentBean studyReadVo = (LessonDetailVo.SortContentBean) simpleCommonRecyclerAdapter.getmObjects().get(prePostion);
                studyReadVo.setChoose(false);
                simpleCommonRecyclerAdapter.notifyItemChanged(prePostion);
            }
            clearNowChoose();
            followMediaPlayer.start();
        } else {
            if (isPlay)
                followMediaPlayer.pause();
            else
                followMediaPlayer.start();
            isPlay = !isPlay;
        }
        return isPlay;
    }


    ///自动滚动下一个item,屏幕最后一个 在滚下一屏第一个
    private void changeRecycler(double nowtime) {
        try {
            if (mBinding.get() == null)
                return;
            if (nowChoose == simpleCommonRecyclerAdapter.getmObjects().size() || prePostion == simpleCommonRecyclerAdapter.getmObjects().size()) {
            } else if (lastEndTime > (nowtime)) {
            } else {
                if (nowChoose >= 0) {
                    LessonDetailVo.SortContentBean studyReadVo = (LessonDetailVo.SortContentBean) simpleCommonRecyclerAdapter.getmObjects().get(prePostion);
                    studyReadVo.setChoose(false);
                    simpleCommonRecyclerAdapter.notifyItemChanged(prePostion);
                }
                prePostion = nowChoose;
                LessonDetailVo.SortContentBean studyReadVo = (LessonDetailVo.SortContentBean) simpleCommonRecyclerAdapter.getmObjects().get(nowChoose);
                lastEndTime = TimeFormater.timeToSecond(studyReadVo.getEnd_time()) * 1000;
                startTime = TimeFormater.timeToSecond(studyReadVo.getStart_time()) * 1000;
                if (lastEndTime > nowtime) {
                    studyReadVo.setChoose(true);
                    simpleCommonRecyclerAdapter.notifyItemChanged(nowChoose);
                }
                followMediaPlayer.seekTo(startTime.intValue());
                nowChoose++;
                int dee = scrollLinearLayoutManager.findLastVisibleItemPosition();
                int bee = scrollLinearLayoutManager.findFirstVisibleItemPosition();
                try {
                    int d22 = nowChoose - 1;
                    if (d22 < 0)
                        d22 = 0;
                    int dw = nowChoose + (dee - bee - 1) - 1;
                    if (d22 > bee && d22 <= dee) {
                        if (dee == d22) {
                            if (dw < 0)
                                dw = 0;
                            mBinding.get().fragmentLessonRecycler.smoothScrollToPosition(dw);

                        }
                    } else {
                        mBinding.get().fragmentLessonRecycler.smoothScrollToPosition(d22);
                    }
                } catch (Exception e) {//有异常 收一下
                    e.printStackTrace();
                }

            }
        } catch (IllegalStateException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onPause() {
        super.onPause();

        if (isPlay) {
            try {
                if (followMediaPlayer != null)
                    if (followMediaPlayer.isPlaying()) {
                        followMediaPlayer.pause();
                    }
            } catch (IllegalStateException e) {
                e.printStackTrace();
            }
            isPlay = false;
        }
    }

    //点击单项item
    private void clickItemView(int p, View v) {
        Log.e(this.getClass().getName(), String.format("clickItemView--%s,---nowDuration:%s,nowChoose:%s,prePostion:%s,isNoClass:%s", p, nowDuration, nowChoose, prePostion, isNoClass));
        if (followMediaPlayer != null && followMediaPlayer.isPlaying())

            followMediaPlayer.pause();
        if (prePostion >= 0) {
            LessonDetailVo.SortContentBean studyReadVo = (LessonDetailVo.SortContentBean) simpleCommonRecyclerAdapter.getmObjects().get(prePostion);
            studyReadVo.setChoose(false);
            simpleCommonRecyclerAdapter.notifyItemChanged(prePostion);
        }
        nowChoose = p + 1;
        prePostion = p;
        if (v == null)
            v = mBinding.get().fragmentLessonRecycler.getChildAt(p);
        if (v == null) {
            Log.e(this.getClass().getName(), String.format("clickItemView--v == null"));
            return;
        }
        TextView txt = v.findViewById(R.id.study_read_en);
        txt.setTextColor(getResources().getColor(R.color.alivc_blue));
        LessonDetailVo.SortContentBean studyReadVo = (LessonDetailVo.SortContentBean) simpleCommonRecyclerAdapter.getmObjects().get(p);
        Double begin = TimeFormater.timeToSecond(studyReadVo.getStart_time()) * 1000;
        lastEndTime = TimeFormater.timeToSecond(studyReadVo.getEnd_time()) * 1000;
        startTime = begin;
        if (isNoClass) {
            followMediaPlayer.start();
            isNoClass = false;
        }
        if (followMediaPlayer != null)
            followMediaPlayer.seekTo(begin.intValue());
//        fo.resume();
    }

    private void clickNow() {
        if (followMediaPlayer != null) {
            followMediaPlayer.pause();
            followMediaPlayer.seekTo(startTime.intValue());
        }
//        folloWlMedia.pause();
//        folloWlMedia.seek(startTime);
//        folloWlMedia.resume();
    }

    /**
     * 1 单篇 2顺序 3单句
     */
    private int loopModel = 1;
    private float mediaSpeed = (float) 1.0;

    public float getMediaSpeed() {
        return mediaSpeed;
    }

    public void setMediaSpeed(float mediaSpeed) {
        this.mediaSpeed = mediaSpeed;
        try {
            if (followMediaPlayer != null)
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                    followMediaPlayer.setPlaybackParams(followMediaPlayer.getPlaybackParams().setSpeed(mediaSpeed));
                }
        } catch (IllegalStateException e) {
            e.printStackTrace();
        }

    }

    public int getLoopModel() {
        return loopModel;
    }

    public void setLoopModel(@IntRange(from = 1, to = 3) int i) {
        loopModel = i;
    }

    public void setPlayPause() {
        appExecutors.diskIO().execute(() -> {

            followMediaPlayer.pause();
            notiActivity(true);
        });
    }

    public void mediaStop() {
        appExecutors.diskIO().execute(() -> {
            try {
                if (followMediaPlayer != null) {
                    followMediaPlayer.stop();
                    followMediaPlayer.release();
                    followMediaPlayer = null;
                }
            } catch (IllegalStateException e) {
                e.printStackTrace();
            }
        });
    }

    private void clearNowChoose() {
        getHoldingActivity().runOnUiThread(() -> {
            mBinding.get().fragmentLessonRecycler.smoothScrollToPosition(0);
        });
        nowChoose = 0;
        prePostion = 0;
        lastEndTime = 0d;
        startTime = 0d;
    }


    private static final String STATE_SAVE_IS_HIDDEN = "STATE_SAVE_IS_HIDDEN";

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (savedInstanceState != null) {
            boolean isSupportHidden = savedInstanceState.getBoolean(STATE_SAVE_IS_HIDDEN);
            FragmentTransaction ft = getFragmentManager().beginTransaction();
            if (isSupportHidden) {
                ft.hide(this);
            } else {
                ft.show(this);
            }
            ft.commit();
        }
    }

    private void notiActivity(boolean pause) {
        if (getHoldingActivity() != null && !getHoldingActivity().isFinishing()) {
            Message message = Message.obtain();
            message.arg1 = pause ? 0 : 1;
            message.what = 0x12;
            ((AudioLessonDetailActivity) getHoldingActivity()).lessonAudioDetailHandler.sendMessage(message);
        }
    }
}
