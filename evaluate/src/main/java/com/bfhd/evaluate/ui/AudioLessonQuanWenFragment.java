package com.bfhd.evaluate.ui;

import android.Manifest;
import android.arch.lifecycle.ViewModelProvider;
import android.arch.lifecycle.ViewModelProviders;
import android.graphics.Color;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Environment;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentTransaction;
import android.text.Spannable;
import android.text.SpannableStringBuilder;
import android.text.TextUtils;
import android.text.style.ForegroundColorSpan;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.bfhd.circle.base.adapter.HivsAbsSampleAdapter;
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
import com.docker.common.common.ui.base.NitCommonFragment;
import com.docker.core.util.AppExecutors;
import com.docker.video.AlivcLiveRoom.TimeFormater;
import com.luck.picture.lib.permissions.RxPermissions;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;

import javax.inject.Inject;

/**
 * 新概念课程列表
 * {@link com.bfhd.evaluate.vo.RadioLessonVo}
 */
public class AudioLessonQuanWenFragment extends NitCommonFragment<EnStudyViewModel, FragmentLessonRecyclerBinding> {
    private int preClickPosition = 0;
    private View preView;

    public static AudioLessonQuanWenFragment newInstance() {
        AudioLessonQuanWenFragment welcomeFragment = new AudioLessonQuanWenFragment();
        Bundle bundle = new Bundle();
        welcomeFragment.setArguments(bundle);
        return welcomeFragment;
    }


    @Inject
    ViewModelProvider.Factory factory;

    @Inject
    AppExecutors appExecutors;
    boolean isPhoto;

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_lesson_recycler;
    }

    @Override
    public EnStudyViewModel getViewModel() {
        return ViewModelProviders.of(this, factory).get(EnStudyViewModel.class);
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

    public void notiRecycler() {
        if (lessonDetailVo == null)
            return;
        notiSort();

    }

    List<LessonDetailVo.SortContentBean> sortContentBeanList = new ArrayList<>();

    private void notiSort() {
        if (sortContentBeanList != null) {
            sortContentBeanList.clear();
        }
        List<LessonDetailVo.SortContentBean> lessonDetailVoSort_content = lessonDetailVo.getSort_content();
        StringBuilder cn = new StringBuilder(), en = new StringBuilder();
        double sTime = 0;
        titleStr = lessonDetailVoSort_content.get(0).getEn();
//        lessonDetailVoSort_content.remove(0);
        LessonDetailVo.SortContentBean lalalaaBean = new LessonDetailVo.SortContentBean();
        for (int i = 1; i < lessonDetailVoSort_content.size(); i++) {
            LessonDetailVo.SortContentBean lessonDetaile = lessonDetailVoSort_content.get(i);
//            if (i == 0) {
//                titleStr = lessonDetaile.getEn();
//                continue;
//            }
            double statrd = TimeFormater.timeToSecond(lessonDetaile.getStart_time());
            double endD = TimeFormater.timeToSecond(lessonDetaile.getEnd_time());
//            if (etime > stime) {
//                etime = TimeFormater.timeToSecond(lessonDetailVoSort_content.get(i - 1).getEnd_time());
//            }
            if (i == 1) {
                en.append(lessonDetaile.getEn());
                cn.append(lessonDetaile.getText());
                sTime = statrd;
                lalalaaBean.setStart_time(lessonDetaile.getStart_time());
                lalalaaBean.setOrder(lessonDetaile.getOrder());
            } else if (i == lessonDetailVoSort_content.size() - 1) {
                en.append(lessonDetaile.getEn());
                lalalaaBean.setEn(en.toString());
                lalalaaBean.setText(cn.toString());
                lalalaaBean.setEnd_time(lessonDetaile.getEnd_time());
                sortContentBeanList.add(lalalaaBean);
                System.err.println("完：" + JSON.toJSONString(sortContentBeanList));
            } else {
                if (endD > (sTime + 40)) {
                    /// 新的
                    lalalaaBean.setEn(en.toString());
                    lalalaaBean.setText(cn.toString());
                    lalalaaBean.setEnd_time(lessonDetailVoSort_content.get(i - 1).getEnd_time());
                    sTime = endD;
                    sortContentBeanList.add(lalalaaBean);
//					System.err.println("新的："+en);
                    en = new StringBuilder();
                    lalalaaBean = new LessonDetailVo.SortContentBean();
                    lalalaaBean.setStart_time(lessonDetaile.getStart_time());
                    en.append(lessonDetaile.getEn());
                    cn.append(lessonDetaile.getText());
                } else {
                    en.append(lessonDetaile.getEn());
                    cn.append(lessonDetaile.getText());
                }
            }
//
        }


        if (simpleCommonRecyclerAdapter.getmObjects() != null) {
            simpleCommonRecyclerAdapter.getmObjects().clear();
            mBinding.get().fragmentLessonRecycler.removeAllViews();
        }
        simpleCommonRecyclerAdapter.getmObjects().addAll(sortContentBeanList);
        simpleCommonRecyclerAdapter.notifyDataSetChanged();

        mBinding.get().fragmentLessonRecycler.postDelayed(() -> {
            View view = mBinding.get().fragmentLessonRecycler.getChildAt(0);
            if (view != null) {
                clickItemView(view, 0);
//                view.findViewById(R.id.read_compare_relative).setVisibility(View.VISIBLE);
//                view.findViewById(R.id.gendu_item_relative).setBackgroundColor(Color.WHITE);
            }
        }, 300);

    }

    private String titleStr;

    @Override
    protected void initView(View var1) {
        isPhoto = getArguments().getBoolean("isPhoto");
        mBinding.get().fragmentLessonRecycler.setBackgroundColor(Color.parseColor("#F0F0F0"));
        mBinding.get().fragmentLessonRecycler.setItemViewCacheSize(30);
        simpleCommonRecyclerAdapter = new HivsAbsSampleAdapter(R.layout.item_gendu_compare, com.bfhd.evaluate.BR.item) {
            @Override
            public void onRealviewBind(ViewHolder holder, int position) {
                ItemGenduCompareBinding binding = (ItemGenduCompareBinding) holder.getBinding();
                binding.readCompareCh.setVisibility(View.GONE);
                binding.readComparePlay.setOnClickListener(view -> {//播放原声
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
                    if (ChivoxUtils.recorderApp == null) {
                        ((AudioLessonDetailActivity) getHoldingActivity()).showNotifi("评测环境异常，请重新打开软件");
                    } else {
                        try {
                            followMediaPlayer.pause();
                            localMediaPlayer.stop();
                        } catch (IllegalStateException e) {
                            e.printStackTrace();
                        }
                        checkAudioPer(position);
                        binding.readCompareWave.setVisibility(View.VISIBLE);
                        binding.readCompareWave.postDelayed(() -> {
                            binding.readCompareWave.startAnim();
                        }, 400);
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
                        appExecutors.networkIO().execute(() -> {
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
                        appExecutors.networkIO().execute(() -> {
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
            clickItemView(v, p);

        });
        mBinding.get().fragmentLessonRecycler.setAdapter(simpleCommonRecyclerAdapter);

        timerTask = new TimerTask() {
            @Override
            public void run() {
                try {
                    if (followMediaPlayer != null && !isStop) {
                        int now = followMediaPlayer.getCurrentPosition() / 1000;
                        if (lastEndTime <= (now + 0.02) && followMediaPlayer.isPlaying()) {
                            followMediaPlayer.pause();
                            if (getHoldingActivity() != null) {
                                getHoldingActivity().runOnUiThread(() -> {
                                    if (preView != null) {
//                                        LogUtils.i(lastEndTime + "----dddddddd-----" + now);

                                        preView.findViewById(R.id.read_compare_play).setVisibility(View.VISIBLE);
                                        preView.findViewById(R.id.read_compare_pause).setVisibility(View.GONE);
                                    }
                                });
                            }
                        }
                    }
                } catch (IllegalStateException e) {
                    e.printStackTrace();
                }
            }
        };
        timer = new Timer();
        timer.schedule(timerTask, 0, 200);
    }

    private boolean isStop = false;

    private Timer timer;

    private TimerTask timerTask;


    private void clickItemView(View v, int p) {
        if (ChivoxUtils.recorderApp != null && ChivoxUtils.recorderApp.isRunning())
            return;
        nowStudyReadVo = sortContentBeanList.get(p);
        LessonDetailVo.SortContentBean studyReadVo = (LessonDetailVo.SortContentBean) simpleCommonRecyclerAdapter.getmObjects().get(p);
        lastEndTime = TimeFormater.timeToSecond(studyReadVo.getEnd_time());
        lastBeginTime = TimeFormater.timeToSecond(studyReadVo.getStart_time());
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
    }


    private boolean isFirst = true;
    private Double lastEndTime = 0d, lastBeginTime = 0d;

    private void playClick(int p) {
        try {
            if (isFirst) {
                followMediaPlayer.start();
                isFirst = false;
            }
            int dede = lastBeginTime.intValue() * 1000;

            followMediaPlayer.seekTo(dede);
        } catch (IllegalStateException e) {
            e.printStackTrace();
        }
    }

    private void pauseFollow() {
//        folloWlMedia.pause();
        try {
            if (followMediaPlayer != null)
                if (followMediaPlayer.isPlaying()) {
                    followMediaPlayer.pause();
                }
        } catch (IllegalStateException e) {
            e.printStackTrace();
        }
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
//    private WlMedia folloWlMedia;
    private MediaPlayer localMediaPlayer;// = new MediaPlayer();
    private MediaPlayer followMediaPlayer;//= new MediaPlayer();


    private void initFolloWlMedia() {
        appExecutors.diskIO().execute(() -> {
            try {
                followMediaPlayer = new MediaPlayer();
                followMediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);
                followMediaPlayer.setOnErrorListener(new MediaPlayer.OnErrorListener() {
                    @Override
                    public boolean onError(MediaPlayer mp, int what, int extra) {
//                        ToastUtils.showShort("错误,错误信息");
                        return false;
                    }
                });
                followMediaPlayer.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
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
                followMediaPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
                    @Override
                    public void onCompletion(MediaPlayer mp) {
                        LogUtils.e("setOnCompleteListener---》");
                        if (!getHoldingActivity().isFinishing()) {
                            LogUtils.e("setOnCompleteListener---》22222");
                            if (lessonDetailVo != null) {
                                getHoldingActivity().runOnUiThread(() -> {
                                    if (preView != null) {
                                        if (preView.findViewById(R.id.read_compare_pause).getVisibility() == View.VISIBLE) {
                                            preView.findViewById(R.id.read_compare_play).setVisibility(View.VISIBLE);
                                            preView.findViewById(R.id.read_compare_pause).setVisibility(View.GONE);
                                        }

                                    }
                                });
                                appExecutors.diskIO().execute(() -> {
                                    try {
                                        followMediaPlayer.reset();
                                        followMediaPlayer.setDataSource(lessonDetailVo.getRadio());
                                        followMediaPlayer.prepareAsync();
                                    } catch (IOException e) {
                                        e.printStackTrace();
                                    }
                                });
                            }
                        }
                    }
                });
                followMediaPlayer.setOnSeekCompleteListener(new MediaPlayer.OnSeekCompleteListener() {
                    @Override
                    public void onSeekComplete(MediaPlayer mp) {
                        mp.start();
                    }
                });

                followMediaPlayer.setDataSource(lessonDetailVo.getRadio());
                followMediaPlayer.prepareAsync();
            } catch (IllegalStateException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
//                    followMedia.prepare();
//                } catch (IOException e) {
//                    e.printStackTrace();
//                }
        });

    }

    private void setRadio() {
//        folloWlMedia.setSource(lessonDetailVo.getRadio());
//        folloWlMedia.prepared();
        initFolloWlMedia();
        initLocalMedia();

    }

    //本地播放器初始化
    private void initLocalMedia() {
        try {
            localMediaPlayer = new MediaPlayer();
            localMediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);
            localMediaPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
                @Override
                public void onCompletion(MediaPlayer mp) {
                    LogUtils.e("setOnCompleteListener---》");
                    if (!getHoldingActivity().isFinishing()) {
                        LogUtils.e("setOnCompleteListener---》22222");
                        if (lessonDetailVo != null)
                            appExecutors.diskIO().execute(() -> {
                                mp.reset();
                            });
                    }

                }
            });
            localMediaPlayer.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
                @Override
                public void onPrepared(MediaPlayer mp) {
                    mp.start();
                }
            });
        } catch (IllegalStateException e) {
            e.printStackTrace();
        }

    }

    //播放本地朗读音频
    private void playLocalMedia(int p) {
        String fileDir = String.format(Locale.getDefault(), "%s/enstu/pei_quan/", Environment.getExternalStorageDirectory().getAbsolutePath()) + "/" + lessonDetailVo.getId();
        try {
            if (FileUtils.isDir(fileDir)) {
                fileDir = fileDir + "/" + p + ".wav";
//            localWlMedia.setSource(fileDir);
//            localWlMedia.prepared();
                if (!localMediaPlayer.isPlaying()) {
                    localMediaPlayer.setDataSource(fileDir);
                    localMediaPlayer.prepareAsync();

                }
            }
        } catch (IllegalStateException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    private String fileDir = Environment.getExternalStorageDirectory().getAbsolutePath() + "/enstu/pei_quan/";

    private void beginSpeech(int p) {
        if (!FileUtils.createOrExistsDir(fileDir)) {
            return;
        }
        String filePath = fileDir + lessonDetailVo.getId() + "/" + p + ".wav";
        if (ChivoxUtils.recorderApp != null) {
            ChivoxUtils.recorderApp.start(filePath, ((AudioLessonDetailActivity) getHoldingActivity()).recorderCallback);
        } else {
            getHoldingActivity().runOnUiThread(() -> {
                ToastUtils.showShort("请回到首页,引擎还未初始化");
            });
        }
    }

    public LessonDetailVo.SortContentBean nowStudyReadVo;
    private Map<String, Integer> scoreLists = new HashMap<>();
    private int allScore = 0;

    public void updateRecycleItem(String result) {
        View view = mBinding.get().fragmentLessonRecycler.getChildAt(preClickPosition);
        if (view == null)
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
            TextView textView = view.findViewById(R.id.read_compare_en);
            TextView textScore = view.findViewById(R.id.read_compare_score);
            String en = nowStudyReadVo.getEn();
            LogUtils.i("dsddd----:" + en + en.length());
            com.alibaba.fastjson.JSONObject jsonObject = com.alibaba.fastjson.JSONObject.parseObject(result);
            com.alibaba.fastjson.JSONObject jsonResult = jsonObject.getJSONObject("result");
            String overall = jsonResult.getString("overall");
            com.alibaba.fastjson.JSONArray jsonDetails = jsonResult.getJSONArray("details");
            SpannableStringBuilder style = new SpannableStringBuilder(en);
            boolean isPass = true;
            for (int i = 0; i < jsonDetails.size(); i++) {
                com.alibaba.fastjson.JSONObject jsonDetail = (com.alibaba.fastjson.JSONObject) jsonDetails.get(i);
                int beginindex = jsonDetail.getInteger("beginindex");
                int endindex = jsonDetail.getInteger("endindex") + 1;
                int score = jsonDetail.getInteger("score");

                try {
                    if (endindex > en.length()) {
                        style.setSpan(new ForegroundColorSpan(Color.RED), beginindex, en.length() - 1, Spannable.SPAN_EXCLUSIVE_INCLUSIVE);
                        break;
                    }
                    if (score < 80) {
                        if (i == (jsonDetail.size() - 1))
                            style.setSpan(new ForegroundColorSpan(Color.RED), beginindex, en.length() - 1, Spannable.SPAN_EXCLUSIVE_INCLUSIVE);
                        else
                            style.setSpan(new ForegroundColorSpan(Color.RED), beginindex, endindex, Spannable.SPAN_EXCLUSIVE_INCLUSIVE);
                    } else {
                        if (i == (jsonDetail.size() - 1))
                            style.setSpan(new ForegroundColorSpan(Color.parseColor("#57AB44")), beginindex, en.length() - 1, Spannable.SPAN_EXCLUSIVE_INCLUSIVE);
                        else
                            style.setSpan(new ForegroundColorSpan(Color.parseColor("#57AB44")), beginindex, endindex, Spannable.SPAN_EXCLUSIVE_INCLUSIVE);
                    }
                } catch (IndexOutOfBoundsException e) {
                    e.printStackTrace();
                }
            }
            textView.setText(style);

            if (!TextUtils.isEmpty(overall)) {
                //多次配音 存放最后一个评分
                textScore.setText(overall + "分");
                int iscore = Integer.parseInt(overall);
                scoreLists.put(nowStudyReadVo.getStart_time(), iscore);

//            allScore += iscore;
                if (iscore > 79)
                    textScore.setTextColor(Color.parseColor("#57AB44"));
                else {
                    textScore.setTextColor(Color.RED);
                    isPass = false;
                }
                textScore.setVisibility(View.VISIBLE);
                if (scoreLists.size() == sortContentBeanList.size()) {
                    allScore = 0;
                    scoreLists.entrySet().iterator();
                    for (Object isd : scoreLists.values()) {
                        int d = (int) isd;
                        allScore += d;
                    }
                    int sdd = allScore / scoreLists.size();
                    if (isPass) {
                    }


                    showNotifi(sdd);

                }

            }
        }
    }


    private void showNotifi(int score) {
        if (score > 69) {
            ToastUtils
                    .showShort(String.format("恭喜你,平均得分%s", score));
        } else {
            ToastUtils
                    .showShort((String.format("平均得分%s,还需要继续努力哦！", score)));
        }

    }

    @Override
    public void onPause() {
        super.onPause();
        LogUtils.i("onPause----");
        try {

            if (localMediaPlayer != null)
                if (localMediaPlayer.isPlaying())
                    localMediaPlayer.pause();
            if (followMediaPlayer != null)
                if (followMediaPlayer.isPlaying())
                    followMediaPlayer.pause();
        } catch (IllegalStateException e) {
            e.printStackTrace();
        }
    }


    @Override
    public void onStop() {
        super.onStop();
        appExecutors.diskIO().execute(() -> {
            isStop = true;
            if (timer != null)
                timer.cancel();
//            followMediaPlayer.stop();
//            localMediaPlayer.stop();
        });

    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        LogUtils.i(" AudioLessonQuanWenFragment onDestroy ");
        appExecutors.diskIO().execute(() -> {
//            folloWlMedia.onDestroy();
//            localWlMedia.onDestroy();
            try {
                if (localMediaPlayer != null) {
                    localMediaPlayer.stop();
                    localMediaPlayer.release();
                    localMediaPlayer = null;
                }
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

    @Override
    public void onHiddenChanged(boolean hidden) {
        super.onHiddenChanged(hidden);
        LogUtils.i(" AudioLessonQuanWenFragment  " + hidden);
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

    @Override
    public void onResume() {
        super.onResume();
        if (isStop) {
            timer = new Timer();
            timerTask = new TimerTask() {
                @Override
                public void run() {
                    if (followMediaPlayer == null) {
                        timerTask.cancel();
                    } else {
                        int now = followMediaPlayer.getCurrentPosition() / 1000;
                        LogUtils.i(lastEndTime + "----dddddddd-----" + now);
                        if (lastEndTime <= (now + 0.02) && followMediaPlayer.isPlaying()) {
                            followMediaPlayer.pause();
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
    }


    @Override
    public void onSaveInstanceState(Bundle outState) {
        outState.putBoolean(STATE_SAVE_IS_HIDDEN, isHidden());
    }


    public void setPause() {
        if (preView != null) {

            if (preView.findViewById(R.id.read_compare_pause).getVisibility() == View.VISIBLE) {
                pauseFollow();
                preView.findViewById(R.id.read_compare_play).setVisibility(View.VISIBLE);
                preView.findViewById(R.id.read_compare_pause).setVisibility(View.GONE);

            }
        }
        try {
            if (localMediaPlayer != null && localMediaPlayer.isPlaying())
                localMediaPlayer.pause();
        } catch (IllegalStateException e) {
            e.printStackTrace();
        }

    }

    @Override
    public void initImmersionBar() {

    }
}


