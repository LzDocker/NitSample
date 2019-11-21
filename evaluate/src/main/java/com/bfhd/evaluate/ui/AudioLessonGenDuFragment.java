package com.bfhd.evaluate.ui;

import android.Manifest;
import android.arch.lifecycle.ViewModelProviders;
import android.graphics.Color;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Environment;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentTransaction;
import android.text.Spannable;
import android.text.SpannableStringBuilder;
import android.text.TextUtils;
import android.text.style.ForegroundColorSpan;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.alibaba.android.arouter.facade.annotation.Autowired;
import com.alibaba.android.arouter.launcher.ARouter;
import com.bfhd.circle.base.adapter.HivsAbsSampleAdapter;
import com.bfhd.evaluate.BR;
import com.bfhd.evaluate.R;
import com.bfhd.evaluate.databinding.FragmentLessonRecyclerBinding;
import com.bfhd.evaluate.databinding.ItemGenduCompareBinding;
import com.bfhd.evaluate.view.WaveLineView;
import com.bfhd.evaluate.vm.EnStudyViewModel;
import com.bfhd.evaluate.vo.LessonDetailVo;
import com.chivox.ChivoxUtils;
import com.dcbfhd.utilcode.utils.FileUtils;
import com.dcbfhd.utilcode.utils.LogUtils;
import com.dcbfhd.utilcode.utils.ToastUtils;
import com.docker.common.common.model.CommonListOptions;
import com.docker.common.common.ui.base.NitCommonFragment;
import com.docker.common.common.widget.refresh.api.RefreshLayout;
import com.docker.common.common.widget.refresh.listener.OnRefreshLoadMoreListener;
import com.docker.core.util.AppExecutors;
import com.docker.video.AlivcLiveRoom.TimeFormater;
import com.luck.picture.lib.permissions.RxPermissions;

import java.io.IOException;
import java.util.Locale;
import java.util.Timer;
import java.util.TimerTask;

import javax.inject.Inject;

/**
 * 新概念课程列表
 * {@link com.bfhd.evaluate.vo.RadioLessonVo}
 */
public class AudioLessonGenDuFragment extends NitCommonFragment<EnStudyViewModel, FragmentLessonRecyclerBinding> {

    CommonListOptions commonListReq = new CommonListOptions();

    public static AudioLessonGenDuFragment newInstance() {
        return new AudioLessonGenDuFragment();
    }

    private Timer timer;

    private TimerTask timerTask;
    private int preClickPosition = 0;
    private View preView;


    @Inject
    AppExecutors appExecutor;

    public String id;
    private boolean isFirst = true;
    private Double lastEndTime = 0d, lastBeginTime = 0d;


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
//        mBinding.get()
//                .recyclerView
//                .setLayoutManager(new LinearLayoutManager(getHoldingActivity()));
    }

    @Override
    protected void initView(View var1) {
        mBinding.get().fragmentLessonRecycler.setBackgroundColor(Color.parseColor("#F0F0F0"));
        mBinding.get().fragmentLessonRecycler.setItemViewCacheSize(30);
        simpleCommonRecyclerAdapter = new HivsAbsSampleAdapter(R.layout.item_gendu_compare, com.bfhd.evaluate.BR.item) {
            @Override
            public void onRealviewBind(ViewHolder holder, int position) {
                ItemGenduCompareBinding binding = (ItemGenduCompareBinding) holder.getBinding();
                binding.readComparePlay.setOnClickListener(view -> {//播放原声
                    LogUtils.i("recorderCallbackp:-----" + position);

                    view.setVisibility(View.GONE);
                    binding.readComparePause.setVisibility(View.VISIBLE);
                    playClick(position);
                });
                binding.readComparePause.setOnClickListener(v -> {
                    v.setVisibility(View.GONE);
                    binding.readComparePlay.setVisibility(View.VISIBLE);
                    pauseFollow();
                });
                binding.readCompareRecording.setOnClickListener(v -> {//录音
                    LogUtils.i("recorderCallbackp:-----" + position);
                    if (ChivoxUtils.recorderApp == null) {
                        ((AudioLessonDetailActivity) getHoldingActivity()).showNotifi("评测环境异常，请重新打开软件");
                    } else {
                        try {
                            followMedia.pause();
                            localMedia.stop();
                        } catch (IllegalStateException e) {
                            e.printStackTrace();
                        }
                        checkAudioPer(position);
                        binding.readCompareWave.setVisibility(View.VISIBLE);
                        binding.readCompareWave.postDelayed(() -> {
                            binding.readCompareWave.startAnim();
                        }, 400);
//                    binding.readCompareCancel.setVisibility(View.VISIBLE);
                        binding.readComparePause.setVisibility(View.GONE);
                        binding.readComparePlay.setVisibility(View.GONE);
                        binding.readCompareRecording.setVisibility(View.GONE);
                        binding.readComparePlayback.setVisibility(View.GONE);
                    }
                });
                binding.readComparePlayback.setOnClickListener(view -> {//播放录音
                    playLocalMedia(position);
                });
                binding.readCompareCancel.setOnClickListener(v -> {//取消播放
                    binding.readCompareCancel.setVisibility(View.GONE);
                    binding.readComparePlay.setVisibility(View.VISIBLE);
                    binding.readCompareRecording.setVisibility(View.VISIBLE);
                    binding.readComparePlayback.setVisibility(View.VISIBLE);
                    if (ChivoxUtils.recorderApp != null)
                        appExecutor.networkIO().execute(() -> {
                            ChivoxUtils.recorderApp.stop();
                        });
                });
                binding.readCompareWave.setOnClickListener(v -> {
                    binding.readCompareWave.stopAnim();
                    binding.readCompareWave.setVisibility(View.GONE);
                    binding.readComparePlay.setVisibility(View.VISIBLE);
                    binding.readCompareRecording.setVisibility(View.VISIBLE);
                    binding.readComparePlayback.setVisibility(View.VISIBLE);
                    if (ChivoxUtils.recorderApp != null)
                        appExecutor.networkIO().execute(() -> {
                            ChivoxUtils.recorderApp.stop();
                        });
                });


            }
        };
        simpleCommonRecyclerAdapter.setOnItemClickListener((v, p) -> {
            if (preView != null) {
                if (preClickPosition == p)
                    return;
            }
            if (ChivoxUtils.recorderApp != null && ChivoxUtils.recorderApp.isRunning())
                return;
            isFirst = true;
            nowStudyReadVo = lessonDetailVo.getSort_content().get(p);
            LogUtils.i("recorderCallbackp:" + p + "   " + preClickPosition + "   :" + nowStudyReadVo.getEn());
            LessonDetailVo.SortContentBean studyReadVo = (LessonDetailVo.SortContentBean) simpleCommonRecyclerAdapter.getmObjects().get(p);
            lastEndTime = TimeFormater.timeToSecond(studyReadVo.getEnd_time()) * 1000;
            lastBeginTime = TimeFormater.timeToSecond(studyReadVo.getStart_time()) * 1000;
//            lastEndTime = TimeFormater.timeToSecond(lessonDetailVo.get);
//            lastBeginTime = times[0];//开始结束时间
            v.findViewById(R.id.read_compare_relative).setVisibility(View.VISIBLE);
            v.findViewById(R.id.gendu_item_relative).setBackgroundColor(Color.WHITE);
            if (preView != null) {
                preView.findViewById(R.id.read_compare_relative).setVisibility(View.GONE);
                preView.findViewById(R.id.gendu_item_relative).setBackgroundColor(Color.parseColor("#F0F0F0"));
                pauseFollow();
                preView.findViewById(R.id.read_compare_play).setVisibility(View.VISIBLE);
                preView.findViewById(R.id.read_compare_pause).setVisibility(View.GONE);
            }
            preClickPosition = p;
            preView = v;

        });
        mBinding.get().fragmentLessonRecycler.setAdapter(simpleCommonRecyclerAdapter);

        timerTask = new TimerTask() {
            @Override
            public void run() {
                try {
                    if (followMedia != null && !isStop) {
                        int now = followMedia.getCurrentPosition();
//                        LogUtils.i(lastEndTime + "----dddddddd-----" + now);
                        if (lastEndTime <= (now + 300) && followMedia.isPlaying()) {
                            followMedia.pause();
                            if (getHoldingActivity() != null) {
                                getHoldingActivity().runOnUiThread(() -> {
                                    if (preView != null) {
                                        preView.findViewById(R.id.read_compare_play).setVisibility(View.VISIBLE);
                                        preView.findViewById(R.id.read_compare_pause).setVisibility(View.GONE);
                                    }
                                });
                            }
                        }
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

    private LessonDetailVo lessonDetailVo = null;

    public void setLessonDetailVo(LessonDetailVo lessonDetailVo) {
        this.lessonDetailVo = lessonDetailVo;
        setRadio();
    }

    public LessonDetailVo getLessonDetailVo() {
        return lessonDetailVo;
    }

    private HivsAbsSampleAdapter simpleCommonRecyclerAdapter;

    private void playClick(int p) {
        Log.i("readDetail", String.format("playClick--lastBeginTime:%s ,lastEndTime:%s", lastBeginTime, lastEndTime) + Thread.currentThread().getName());
        try {
            if (isFirst) {
                followMedia.start();
                isFirst = false;
            }
            int dede = lastBeginTime.intValue();
            followMedia.seekTo(dede);
        } catch (IllegalStateException e) {
            e.printStackTrace();
        }
    }

    public void notiRecycler() {
        if (lessonDetailVo == null)
            return;
        if (simpleCommonRecyclerAdapter.getmObjects() != null) {
            simpleCommonRecyclerAdapter.getmObjects().clear();
            mBinding.get().fragmentLessonRecycler.removeAllViews();
        }
        simpleCommonRecyclerAdapter.getmObjects().addAll(lessonDetailVo.getSort_content());
        simpleCommonRecyclerAdapter.notifyDataSetChanged();
    }

    private void checkAudioPer(final int p) {
        RxPermissions rxPermissions = new RxPermissions(getHoldingActivity());
        rxPermissions
                .request(
                        Manifest.permission.READ_EXTERNAL_STORAGE,
                        Manifest.permission.RECORD_AUDIO,
                        Manifest.permission.WRITE_EXTERNAL_STORAGE)
                .subscribe(granted -> {
                    if (granted) {
                        beginSpeech(p);
                        //startAudio(p);
                    } else {
                        ToastUtils.showShort("权限被拒绝，请在设置里面开启相应权限，若无相应权限会影响使用");
                    }
                });
    }

    //本地播放器,列表播放器
//    private WlMedia localWlMedia, folloWlMedia;

    private MediaPlayer localMedia, followMedia;

    private void initFolloWlMedia() {
        appExecutor.diskIO().execute(() -> {
            try {
                followMedia = new MediaPlayer();
                followMedia.setAudioStreamType(AudioManager.STREAM_MUSIC);
                followMedia.setOnErrorListener(new MediaPlayer.OnErrorListener() {
                    @Override
                    public boolean onError(MediaPlayer mp, int what, int extra) {
//                        ToastUtils.showShort("错误,错误信息");
                        return false;
                    }
                });
                followMedia.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
                    @Override
                    public void onPrepared(MediaPlayer mp) {
                        if (!getHoldingActivity().isFinishing()) {
//                        mp.start();
//                            runOnUiThread(() -> {
//                                mBinding.followPhotoPlay.setClickable(true);
//                            });
                        }
                    }
                });
                followMedia.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
                    @Override
                    public void onCompletion(MediaPlayer mp) {
                        LogUtils.e("setOnCompleteListener---》");
                        if (!getHoldingActivity().isFinishing()) {
                            LogUtils.e("setOnCompleteListener---》22222");
                            if (lessonDetailVo != null)
                                appExecutor.diskIO().execute(() -> {
                                    try {
                                        followMedia.reset();
                                        followMedia.setDataSource(lessonDetailVo.getRadio());
                                        followMedia.prepareAsync();
                                    } catch (IOException e) {
                                        e.printStackTrace();
                                    }
                                });
                        }
                    }
                });
                followMedia.setOnSeekCompleteListener(new MediaPlayer.OnSeekCompleteListener() {
                    @Override
                    public void onSeekComplete(MediaPlayer mp) {
                        mp.start();
                    }
                });

                followMedia.setDataSource(lessonDetailVo.getRadio());
                followMedia.prepareAsync();
            } catch (IllegalStateException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
        });

    }


    private void pauseFollow() {
        try {
            if (followMedia != null)
                if (followMedia.isPlaying())
                    followMedia.pause();
        } catch (IllegalStateException e) {
            e.printStackTrace();
        }
    }

    private void setRadio() {
        initFolloWlMedia();
        initLocalMedia();

    }

    //本地播放器初始化
    private void initLocalMedia() {
        localMedia = new MediaPlayer();
        localMedia.setAudioStreamType(AudioManager.STREAM_MUSIC);
        localMedia.setOnCompletionListener(mp -> {
            LogUtils.e("setOnCompleteListener---》");
            if (!getHoldingActivity().isFinishing()) {
                LogUtils.e("setOnCompleteListener---》22222");
                if (lessonDetailVo != null)
                    appExecutor.diskIO().execute(() -> {
                        mp.reset();
                    });

            }
        });
        localMedia.setOnPreparedListener(mp -> {
            mp.start();
        });

    }

    @Override
    public void onResume() {
        super.onResume();
        try {
            if (isStop) {
                timer = new Timer();
                timerTask = new TimerTask() {
                    @Override
                    public void run() {
                        if (followMedia == null) {
                            timerTask.cancel();
                        } else {
                            int now = followMedia.getCurrentPosition();
//                            LogUtils.i(lastEndTime + "----dddddddd-----" + now);
                            if (lastEndTime <= (now + 300) && followMedia.isPlaying()) {
                                followMedia.pause();
                                if (getHoldingActivity() != null) {
                                    getHoldingActivity().runOnUiThread(() -> {
                                        if (preView != null) {
                                            preView.findViewById(R.id.read_compare_play).setVisibility(View.VISIBLE);
                                            preView.findViewById(R.id.read_compare_pause).setVisibility(View.GONE);
                                        }
                                    });
                                }
                            }
                        }
                    }
                };
                timer.schedule(timerTask, 0, 200);
            }
        } catch (IllegalStateException e) {
            e.printStackTrace();
        }
    }

    //播放本地朗读音频
    private void playLocalMedia(int p) {
        try {
            String fileDir = String.format(Locale.getDefault(), "%s/enstu/pei/", Environment.getExternalStorageDirectory().getAbsolutePath()) + "/" + lessonDetailVo.getId();
            if (FileUtils.isDir(fileDir)) {
                fileDir = fileDir + "/" + p + ".wav";
//            localWlMedia.setSource(fileDir);
//            localWlMedia.prepared();
                if (!localMedia.isPlaying()) {
                    localMedia.setDataSource(fileDir);
                    localMedia.prepareAsync();

                }
            }
        } catch (IllegalStateException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private String fileDir = Environment.getExternalStorageDirectory().getAbsolutePath() + "/enstu/pei/";

    private void beginSpeech(int p) {
        if (!FileUtils.createOrExistsDir(fileDir)) {
            return;
        }
        String filePath = fileDir + lessonDetailVo.getId() + "/" + p + ".wav";
        if (ChivoxUtils.recorderApp != null) {
            ChivoxUtils.recorderApp.start(filePath, ((AudioLessonDetailActivity) getHoldingActivity()).recorderCallback);
        }
    }

    public LessonDetailVo.SortContentBean nowStudyReadVo;

    public void updateRecycleItem(String result) {
//        View view = mBinding.get().fragmentLessonRecycler.getChildAt(preClickPosition);
//        LogUtils.i("preClickPosition:" + preClickPosition + "   " + result);
        if (preView == null)
            return;
        if (TextUtils.isEmpty(result) || result.contains("errId\":")) {
            ToastUtils.showLong(result);
            WaveLineView waveLineView = preView.findViewById(R.id.read_compare_wave);
            waveLineView.stopAnim();
            waveLineView.setVisibility(View.GONE);
            ImageView recordingimg = preView.findViewById(R.id.read_compare_recording);
            ImageView playimg = preView.findViewById(R.id.read_compare_play);
            recordingimg.setVisibility(View.VISIBLE);
            playimg.setVisibility(View.VISIBLE);
            ImageView backimg = preView.findViewById(R.id.read_compare_playback);
            backimg.setVisibility(View.VISIBLE);
        } else {
            TextView textView = preView.findViewById(R.id.read_compare_en);
            String en = nowStudyReadVo.getEn();
            TextView textScore = preView.findViewById(R.id.read_compare_score);
            com.alibaba.fastjson.JSONObject jsonObject = com.alibaba.fastjson.JSONObject.parseObject(result);
            com.alibaba.fastjson.JSONObject jsonResult = jsonObject.getJSONObject("result");
            String overall = jsonResult.getString("overall");
            com.alibaba.fastjson.JSONArray jsonDetails = jsonResult.getJSONArray("details");
            SpannableStringBuilder style = new SpannableStringBuilder(en);
            for (int i = 0; i < jsonDetails.size(); i++) {
                com.alibaba.fastjson.JSONObject jsonDetail = (com.alibaba.fastjson.JSONObject) jsonDetails.get(i);
//            jsonDetail.getString("char");
                int beginindex = jsonDetail.getInteger("beginindex");
                int endindex = jsonDetail.getInteger("endindex") + 1;
                try {
                    if (endindex > en.length()) {
                        style.setSpan(new ForegroundColorSpan(Color.RED), beginindex, en.length(), Spannable.SPAN_EXCLUSIVE_INCLUSIVE);
                        break;
                    } else {
                        int score = jsonDetail.getInteger("score");
                        if (score < 80) {
                            style.setSpan(new ForegroundColorSpan(Color.RED), beginindex, endindex, Spannable.SPAN_EXCLUSIVE_INCLUSIVE);
                        } else {
                            style.setSpan(new ForegroundColorSpan(Color.parseColor("#57AB44")), beginindex, endindex, Spannable.SPAN_EXCLUSIVE_INCLUSIVE);
                        }
                    }
                } catch (IndexOutOfBoundsException e) {
                    e.printStackTrace();
                }
            }
            textScore.setText(overall + "分");
            int iscore = Integer.parseInt(overall);
            if (iscore > 69)
                textScore.setTextColor(Color.parseColor("#57AB44"));
            else {
                textScore.setTextColor(Color.RED);
            }
            textScore.setVisibility(View.VISIBLE);
            textView.setText(style);
        }
    }

    @Override
    public void onPause() {
        super.onPause();
        LogUtils.i("onPause----");
        try {
            if (localMedia != null)
                if (localMedia.isPlaying())
                    localMedia.pause();
            if (followMedia != null)
                if (followMedia.isPlaying())
                    followMedia.pause();
        } catch (IllegalStateException e) {
            e.printStackTrace();
        }
    }

    private boolean isStop = false;

    @Override
    public void onStop() {
        super.onStop();
        isStop = true;
        if (timer != null)
            timer.cancel();

    }


    @Override
    public void onDestroy() {
        super.onDestroy();
        LogUtils.e("audio Destroy");
        try {
            if (localMedia != null) {
                localMedia.stop();
                localMedia.release();
                localMedia = null;
            }
            if (followMedia != null) {
                followMedia.stop();
                followMedia.release();
                followMedia = null;
            }
        } catch (IllegalStateException e) {
            e.printStackTrace();
        }
//        folloWlMedia.onDestroy();
//        localWlMedia.onDestroy();
    }


    private static final String STATE_SAVE_IS_HIDDEN = "STATE_SAVE_IS_HIDDEN";

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ARouter.getInstance().inject(this);
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

    @Override
    public void onSaveInstanceState(Bundle outState) {
        outState.putBoolean(STATE_SAVE_IS_HIDDEN, isHidden());
    }

    public void setPause() {
        if (preView != null) {

            if (preView.findViewById(R.id.read_compare_pause).getVisibility() == View.VISIBLE) {
//                preView.findViewById(R.id.gendu_item_relative).setBackgroundColor(Color.parseColor("#F0F0F0"));
                pauseFollow();
                preView.findViewById(R.id.read_compare_play).setVisibility(View.VISIBLE);
                preView.findViewById(R.id.read_compare_pause).setVisibility(View.GONE);
            }
        }
        try {
            if (localMedia != null)
                if (localMedia.isPlaying())
                    localMedia.pause();
        } catch (IllegalStateException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void initImmersionBar() {
    }


}
