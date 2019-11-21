package com.bfhd.evaluate.ui;

import android.arch.lifecycle.ViewModelProvider;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.android.arouter.launcher.ARouter;
import com.alibaba.fastjson.JSON;
import com.bfhd.evaluate.R;
import com.bfhd.evaluate.api.EnStudyService;
import com.bfhd.evaluate.databinding.ActivityAudioLessonDetailBinding;
import com.bfhd.evaluate.view.popwindow.HorizontalPosition;
import com.bfhd.evaluate.view.popwindow.SmartPopupWindow;
import com.bfhd.evaluate.view.popwindow.VerticalPosition;
import com.bfhd.evaluate.vm.EnStudyViewModel;
import com.bfhd.evaluate.vo.LessonDetailVo;
import com.chivox.AIEngine;
import com.chivox.AIRecorder;
import com.chivox.ChivoxUtils;
import com.dcbfhd.utilcode.utils.LogUtils;
import com.dcbfhd.utilcode.utils.StringUtils;
import com.dcbfhd.utilcode.utils.ToastUtils;
import com.docker.common.common.command.NitContainerCommand;
import com.docker.common.common.router.AppRouter;
import com.docker.common.common.ui.base.NitCommonActivity;
import com.docker.core.util.AppExecutors;
import com.gyf.immersionbar.ImmersionBar;

import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

/**
 * 新概念课程详情
 */
@Route(path = AppRouter.EVALUATE_DETAIL)
public class AudioLessonDetailActivity extends NitCommonActivity<EnStudyViewModel, ActivityAudioLessonDetailBinding> {

    @Inject
    ViewModelProvider.Factory factory;
    @Inject
    AppExecutors appExecutors;

    private ArrayList<String> lessonIds = new ArrayList<String>();//当前目录列表中课程的id
    private int nowIndex = 0;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_audio_lesson_detail;
    }

    @Override
    public EnStudyViewModel getmViewModel() {
        return ViewModelProviders.of(this, factory).get(EnStudyViewModel.class);
    }

    public LessonDetailVo lessonDetailVo;

    private long startPTime = 0;
    public boolean isDownload = false, isPhoto;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Intent preIntent = getIntent();
        String id = preIntent.getStringExtra("data");
        lessonIds = preIntent.getStringArrayListExtra("ids");
        String lessonId = preIntent.getStringExtra("id");
        nowIndex = preIntent.getIntExtra("nowIndex", 0);
        isPhoto = preIntent.getBooleanExtra("isPhoto", false);//是否是图片转换的
        isDownload = preIntent.getBooleanExtra("isDownload", false);//下载
        lessonDetailVo = JSON.parseObject(id, LessonDetailVo.class);
        lessonDetailVo = new LessonDetailVo();
        lessonDetailVo.setId("ddsdds1122");
        mViewModel.getAudioLessonDetail("44", "37c0fc402ba039c78b07065bfd4d114c", lessonId);
        audioLessonDetailFragment = AudioLessonDetailFragment.newInstance();
        audioDetailGenDuFragment = AudioLessonGenDuFragment.newInstance();
        audioDetailQuanWenFragment = AudioLessonQuanWenFragment.newInstance();
        lessonAudioDetailHandler = new AudioLessonDetailHandler(this);

        if (lessonIds == null || lessonIds.size() == 1) {
            mBinding.studyReadNext.setVisibility(View.INVISIBLE);
            mBinding.studyReadShang.setVisibility(View.INVISIBLE);
            mBinding.studyReadKcsp.setVisibility(View.INVISIBLE);
            audioLessonDetailFragment.setLast(true);
        }
        addFragemnt();
        chooseFragment();
    }

    AudioLessonDetailFragment audioLessonDetailFragment;
    AudioLessonGenDuFragment audioDetailGenDuFragment;
    AudioLessonQuanWenFragment audioDetailQuanWenFragment;
    private int nowChoose = 1;

    Fragment nowShowFragment = null;


    private void addFragemnt() {
        addFragment(audioDetailQuanWenFragment);
        addFragment(audioDetailGenDuFragment);
        hideFragment(audioDetailQuanWenFragment);
        hideFragment(audioDetailGenDuFragment);
        addFragment(audioLessonDetailFragment);
        hideFragment(audioLessonDetailFragment);
    }


    private void chooseFragment() {
        if (nowChoose == 1) {
            if (nowShowFragment instanceof AudioLessonDetailFragment) {

                return;//当前切换 当前
            }
            LogUtils.e("chooseFragment: " + nowShowFragment);

            if (nowShowFragment == null) {
                showFragment(audioLessonDetailFragment);
            } else
                showHideFragment(audioLessonDetailFragment, nowShowFragment);
            nowShowFragment = audioLessonDetailFragment;

        } else if (nowChoose == 2) {
            if (nowShowFragment instanceof AudioLessonGenDuFragment) {
                return;
            }
            showHideFragment(audioDetailGenDuFragment, nowShowFragment);
            if (audioDetailGenDuFragment.getLessonDetailVo() == null) {
                audioDetailGenDuFragment.setLessonDetailVo(lessonDetailVo);
            }
            nowShowFragment = audioDetailGenDuFragment;

        } else if (nowChoose == 3) {
            if (nowShowFragment instanceof AudioLessonQuanWenFragment) {
                return;
            }
//            FragmentUtils.showHide(audioDetailQuanWenFragment, nowShowFragment);
            showHideFragment(audioDetailQuanWenFragment, nowShowFragment);
            if (audioDetailQuanWenFragment.getLessonDetailVo() == null) {
                audioDetailQuanWenFragment.setLessonDetailVo(lessonDetailVo);
            }
            nowShowFragment = audioDetailQuanWenFragment;

        }

    }

    private void addFragment(Fragment add) {
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.add(mBinding.audioDetailFrame.getId(), add);
        transaction.commitNowAllowingStateLoss();//commitAllowingStateLoss

    }

    private void showFragment(Fragment show) {
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.show(show);
        transaction.commitNowAllowingStateLoss();//commitAllowingStateLoss
    }

    private void hideFragment(Fragment hide) {
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.hide(hide);
        transaction.commitNowAllowingStateLoss();//commitAllowingStateLoss

    }

    private void showHideFragment(Fragment show, Fragment hide) {
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.hide(hide);
        transaction.show(show);
        transaction.commitNowAllowingStateLoss();
//        LogUtils.i(" hide.isHidden()==  " + hide.isHidden());
    }


    @Override
    public void initView() {
        mToolbar.setTitle("课程详情");
        mToolbar.setBackgroundColor(Color.parseColor("#29BBFF"));
        mToolbar.setNavigationOnClickListener(v -> {
            stopThis();
        });
        mBinding.studyReadXunhuan.setOnClickListener(this::onClick);
        mBinding.studyReadGendu.setOnClickListener(this::onClick);
        mBinding.studyReadBeisu.setOnClickListener(this::onClick);
        mBinding.studyReadHide.setOnClickListener(this::onClick);
        mBinding.studyReadQwkh.setOnClickListener(this::onClick);
        mBinding.studyReadNext.setOnClickListener(this::onClick);
        mBinding.studyReadShang.setOnClickListener(this::onClick);
        mBinding.studyReadKcsp.setOnClickListener(this::onClick);
        mBinding.studyReadQwzs.setOnClickListener(this::onClick);
        mBinding.studyReadShangLin.setOnClickListener(this::onClick);
        mBinding.studyReadNextLin.setOnClickListener(this::onClick);
        mBinding.studyReadResume.setOnClickListener(v -> {
            if (nowShowFragment instanceof AudioLessonDetailFragment)
                audioLessonDetailFragment.readPlay();
        });
    }

    public void onClick(View view) {
        int id = view.getId();
        if (id == R.id.study_read_xunhuan) {
            if (ChivoxUtils.recorderApp.isRunning()) {
                ToastUtils.showShort("请先关闭评测");
            } else {
                audioDetailGenDuFragment.setPause();
                audioDetailQuanWenFragment.setPause();
                showSortPop();
                nowChoose = 1;
                chooseFragment();
            }
        } else if (id == R.id.study_read_gendu) {
            if (ChivoxUtils.recorderApp.isRunning()) {
                ToastUtils.showShort("请先关闭评测");

            } else {
                audioLessonDetailFragment.setPlayPause();
                audioDetailQuanWenFragment.setPause();

                nowChoose = 2;
                chooseFragment();
            }
        } else if (id == R.id.study_read_qwkh) {
            if (ChivoxUtils.recorderApp.isRunning()) {
                ToastUtils.showShort("请先关闭评测");

            } else {
                audioLessonDetailFragment.setPlayPause();
                audioDetailGenDuFragment.setPause();
                nowChoose = 3;
                chooseFragment();
            }
        } else if (id == R.id.study_read_beisu) {
            if (ChivoxUtils.recorderApp.isRunning()) {
                ToastUtils.showShort("请先关闭评测");

            } else {
                nowChoose = 1;
                chooseFragment();
                showSpeedPop();
            }
        } else if (id == R.id.study_read_hide) {
            mBinding.studyReadBliner.setVisibility(mBinding.studyReadBliner.getVisibility() == View.GONE ? View.VISIBLE : View.GONE);
        } else if (id == R.id.study_read_qwzs) {
            if (ChivoxUtils.recorderApp.isRunning()) {
                ToastUtils.showShort("请先关闭评测");

            } else {
                if (TextUtils.isEmpty(lessonDetailVo.getNotes()))
                    ToastUtils.showShort("没有数据");
                else
                    ARouter.getInstance().build(AppRouter.COMMONH5).withString("weburl", null).withString("title", "全文注释").withString("contentHtml", lessonDetailVo.getNotes()).navigation();
            }
        } else if (id == R.id.study_read_shang_lin || id == R.id.study_read_shang) {
            if (nowShowFragment instanceof AudioLessonDetailFragment) {
                if (audioLessonDetailFragment != null) {
                    mBinding.studyReadBeisu.setText("1.0倍速");
                    audioLessonDetailFragment.setMediaSpeed((float) 1.0);
                    if (audioLessonDetailFragment.getLoopModel() == 3) {
                        mBinding.studyReadXunhuan.setText("单篇循环");
                        audioLessonDetailFragment.setLoopModel(1);
                    }
                    isNextPre = true;
                    palyPre();
                }
            }
        } else if (id == R.id.study_read_next_lin || id == R.id.study_read_next) {
            if (nowShowFragment instanceof AudioLessonDetailFragment) {
                if (audioLessonDetailFragment != null) {
                    mBinding.studyReadBeisu.setText("1.0倍速");
                    audioLessonDetailFragment.setMediaSpeed((float) 1.0);
                    if (audioLessonDetailFragment.getLoopModel() == 3) {
                        audioLessonDetailFragment.setLoopModel(1);
                        mBinding.studyReadXunhuan.setText("单篇循环");
                    }
//                            audioLessonDetailFragment.setLoopModel(1);
                    isNextPre = true;
                    palyNext();
                }
            }
        } else if (id == R.id.study_read_kcsp) {
        }

    }

    @Override
    public void initObserver() {
        mViewModel.mContainerLiveData.observe(this, datas -> {

            lessonDetailVo = (LessonDetailVo) datas;
            List<LessonDetailVo.SortContentBean> sort_content = lessonDetailVo.getSort_content();
            for (int i = 0; i < sort_content.size(); i++) {
                LessonDetailVo.SortContentBean sentText = sort_content.get(i);
                sentText.setEn(StringUtils.full2Half(sentText.getEn()));//还有双引号
            }
            lessonDetailVo.setRadio(EnStudyService.OSS_URL + lessonDetailVo.getRadio());
            audioLessonDetailFragment.setLessonDetailVo(lessonDetailVo);
            audioLessonDetailFragment.notiRecycler();
            if (nowShowFragment instanceof AudioLessonDetailFragment) {
//                audioLessonDetailFragment.playNext();
                audioDetailGenDuFragment.setLessonDetailVo(lessonDetailVo);
                audioDetailQuanWenFragment.setLessonDetailVo(lessonDetailVo);
                audioDetailGenDuFragment.notiRecycler();
                audioDetailQuanWenFragment.notiRecycler();

            }
            if (nowShowFragment instanceof AudioLessonGenDuFragment) {
                audioDetailGenDuFragment.setLessonDetailVo(lessonDetailVo);
            }
            if (nowShowFragment instanceof AudioLessonQuanWenFragment) {
                audioDetailQuanWenFragment.setLessonDetailVo(lessonDetailVo);
            }

        });

    }

    @Override
    public void initRouter() {
        ARouter.getInstance().inject(this);
    }

    public AudioLessonDetailHandler lessonAudioDetailHandler;

    @Override
    public NitContainerCommand providerNitContainerCommand(int flag) {
        return (NitContainerCommand) () -> (EnStudyViewModel.class);
    }


    public static class AudioLessonDetailHandler extends Handler {
        private WeakReference<AudioLessonDetailActivity> studyReadActivityWeakReference;

        public AudioLessonDetailHandler(AudioLessonDetailActivity activityWeakReference) {
            this.studyReadActivityWeakReference = new WeakReference<>(activityWeakReference);
        }

        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case 0x11:
                    studyReadActivityWeakReference.get().mBinding.studyReadProgress.setProgress(msg.arg1);
                    break;
                case 0x12:
                    if (msg.arg1 == 0)
                        studyReadActivityWeakReference.get().mBinding.studyReadResume.pause();
                    else
                        studyReadActivityWeakReference.get().mBinding.studyReadResume.play();
                    break;
                case 0x121:
                    studyReadActivityWeakReference.get().palyNext();
                    break;

            }
        }
    }

    public boolean isNextPre = false;//点击了上下
    AIRecorder.Callback recorderCallback = new AIRecorder.Callback() {
        public void onStarted() {
            if (AudioLessonDetailActivity.this.isFinishing())
                return;
            byte[] id = new byte[64];
            String sentText;
            if (nowShowFragment instanceof AudioLessonGenDuFragment) {
                sentText = audioDetailGenDuFragment.nowStudyReadVo.getEn();
                LogUtils.i("recorderCallbackp:" + sentText);

            } else
                sentText = audioDetailQuanWenFragment.nowStudyReadVo.getEn();
//            String param = String.format("{\"coreProvideType\": \"native\", \"serialNumber\": \"%s\", \"app\": {\"userId\": \"" + userId + "\"}, \"audio\": {\"audioType\": \"wav\", \"channel\": 1, \"sampleBytes\": 2, \"sampleRate\": 16000}, \"request\": {\"coreType\": \"en.pred.exam\", \"rank\": 100, \"precision\": 0.5, \"client_params\":{ \"ext_subitem_rank4\": 0 ,\"ext_word_details\":1}, \"refText\":{\"lm\":\"%s\"}}}", serialNumber, sentText);
            String param = String.format("{\"coreProvideType\": \"native\",\"serialNumber\": \"%s\", \"app\": {\"userId\": \"" + "dddd" + "\"}, \"audio\": {\"audioType\": \"wav\", \"channel\": 1, \"sampleBytes\": 2, \"sampleRate\": 16000}, \"request\": {\"coreType\": \"en.sent.score\", \"refText\":\"%s\", \"rank\": 100}}", ChivoxUtils.serialNumber, sentText);
            LogUtils.e(param);

            int rv = AIEngine.aiengine_start(ChivoxUtils.engine, param, id, callback, AudioLessonDetailActivity.this);
            LogUtils.e("recorderCallback: rv-" + rv);
        }

        public void onStopped() {
            AIEngine.aiengine_stop(ChivoxUtils.engine);
        }

        public void onData(byte[] data, int size) {
            AIEngine.aiengine_feed(ChivoxUtils.engine, data, size);
        }
    };
    private AIEngine.aiengine_callback callback = new AIEngine.aiengine_callback() {

        @Override
        public int run(byte[] id, int type, byte[] data, int size) {
            if (!AudioLessonDetailActivity.this.isFinishing()) {
                if (!isStop) {
                    if (type == AIEngine.AIENGINE_MESSAGE_TYPE_JSON) {
                        final String result = new String(data, 0, size).trim(); /* must trim the end '\0' */
                        LogUtils.e("callback: " + result);

                        runOnUiThread(() -> {
                            if (nowShowFragment instanceof AudioLessonGenDuFragment)
                                audioDetailGenDuFragment.updateRecycleItem(result);
                            else
                                audioDetailQuanWenFragment.updateRecycleItem(result);
                        });

                        if (ChivoxUtils.recorderApp.isRunning()) {
                            appExecutors.networkIO().execute(() -> {
                                ChivoxUtils.recorderApp.stop();
                            });
                        }

                    }
                }
            }
            return 0;
        }
    };

    private void palyNext() {
        if (lessonIds.size() == nowIndex + 1) {
            ToastUtils.showShort("没有下一篇了");
            return;
        }
        audioLessonDetailFragment.mediaStop();
        nowIndex = nowIndex + 1;
        String dd = lessonIds.get(nowIndex);
        if (lessonIds.size() == nowIndex + 1) {
            audioLessonDetailFragment.setLast(true);
            mBinding.studyReadNextLin.setVisibility(View.INVISIBLE);
        } else {
            mBinding.studyReadNextLin.setVisibility(View.VISIBLE);
        }
        mViewModel.getAudioLessonDetail("44", "37c0fc402ba039c78b07065bfd4d114c", dd);
    }

    private void palyPre() {
        if (nowIndex == 0) {
            ToastUtils.showShort("没有上一篇了");
            return;
        }
        audioLessonDetailFragment.mediaStop();
        nowIndex = nowIndex - 1;
        if (mBinding.studyReadNextLin.getVisibility() == View.INVISIBLE)
            mBinding.studyReadNextLin.setVisibility(View.VISIBLE);

        mViewModel.getAudioLessonDetail("44", "37c0fc402ba039c78b07065bfd4d114c", lessonIds.get(nowIndex));
    }

    //排序 播放速度

    private void showSpeedPop() {
        float speed = 0;
        if (audioLessonDetailFragment != null) {
            speed = audioLessonDetailFragment.getMediaSpeed();
        }
        View view = LayoutInflater.from(this).inflate(R.layout.popwindow_speed, null);
        SmartPopupWindow popupWindow = SmartPopupWindow.Builder
                .build(this, mBinding.studyReadBeisu)
//                .setAlpha(0.4f)                   //背景灰度     默认全透明
                .setOutsideTouchDismiss(true)    //点击外部消失  默认true（消失）
                .createPopupWindow();             //创建PopupWindow
        popupWindow.setContentView(view);
        popupWindow.setWidth(390);
        popupWindow.showAtAnchorView(mBinding.studyReadBeisu, VerticalPosition.ABOVE, HorizontalPosition.CENTER);
        TextView speed1 = view.findViewById(R.id.pw_speed1);
        TextView speed2 = view.findViewById(R.id.pw_speed2);
        TextView speed3 = view.findViewById(R.id.pw_speed3);
        if (speed == (float) 0.8)
            view.findViewById(R.id.pw_speedi1).setVisibility(View.VISIBLE);
        else if (speed == (float) 1.0)
            view.findViewById(R.id.pw_speedi2).setVisibility(View.VISIBLE);
        else if (speed == (float) 1.2)
            view.findViewById(R.id.pw_speedi3).setVisibility(View.VISIBLE);
        speed1.setOnClickListener(v -> {
            speed1.setTextColor(getResources().getColor(R.color.alivc_blue));
            speed2.setTextColor(getResources().getColor(R.color.common_ff333333));
            speed3.setTextColor(getResources().getColor(R.color.common_ff333333));
            view.findViewById(R.id.pw_speedi1).setVisibility(View.VISIBLE);
            view.findViewById(R.id.pw_speedi2).setVisibility(View.GONE);
            view.findViewById(R.id.pw_speedi3).setVisibility(View.GONE);
            mBinding.studyReadBeisu.setText("0.8倍速");
            audioLessonDetailFragment.setMediaSpeed((float) 0.8);
            if (popupWindow.isShowing())
                popupWindow.dismiss();
        });
        speed2.setOnClickListener(v -> {
            speed2.setTextColor(getResources().getColor(R.color.alivc_blue));
            speed1.setTextColor(getResources().getColor(R.color.common_ff333333));
            speed3.setTextColor(getResources().getColor(R.color.common_ff333333));
            view.findViewById(R.id.pw_speedi2).setVisibility(View.VISIBLE);
            view.findViewById(R.id.pw_speedi1).setVisibility(View.GONE);
            view.findViewById(R.id.pw_speedi3).setVisibility(View.GONE);
            mBinding.studyReadBeisu.setText("1.0倍速");
            audioLessonDetailFragment.setMediaSpeed((float) 1.0);
            if (popupWindow.isShowing())
                popupWindow.dismiss();
        });
        speed3.setOnClickListener(v -> {
            speed3.setTextColor(getResources().getColor(R.color.alivc_blue));
            speed2.setTextColor(getResources().getColor(R.color.common_ff333333));
            speed1.setTextColor(getResources().getColor(R.color.common_ff333333));
            view.findViewById(R.id.pw_speedi3).setVisibility(View.VISIBLE);
            view.findViewById(R.id.pw_speedi2).setVisibility(View.GONE);
            view.findViewById(R.id.pw_speedi1).setVisibility(View.GONE);
            mBinding.studyReadBeisu.setText("1.2倍速");
            audioLessonDetailFragment.setMediaSpeed((float) 1.2);
            if (popupWindow.isShowing())
                popupWindow.dismiss();
        });
    }

    //排序 SmartPopupWindow
    private void showSortPop() {
        int loop = 1;
        if (audioLessonDetailFragment != null) {
            loop = audioLessonDetailFragment.getLoopModel();
        }
        View view = LayoutInflater.from(this).inflate(R.layout.popwindow_sort, null);
        SmartPopupWindow popupWindow = SmartPopupWindow.Builder
                .build(this, mBinding.studyReadXunhuan)
//                .setAlpha(0.4f)                   //背景灰度     默认全透明
                .setOutsideTouchDismiss(true)    //点击外部消失  默认true（消失）
                .createPopupWindow();             //创建PopupWindow
        popupWindow.setContentView(view);
        popupWindow.setWidth(300);
        popupWindow.showAtAnchorView(mBinding.studyReadXunhuan, VerticalPosition.ABOVE, HorizontalPosition.CENTER);
        TextView speed1 = view.findViewById(R.id.pw_sort1);
        TextView speed2 = view.findViewById(R.id.pw_sort2);
        TextView speed3 = view.findViewById(R.id.pw_sort3);
        if (loop == 1) view.findViewById(R.id.pw_sorti1).setVisibility(View.VISIBLE);
        else if (loop == 2)
            view.findViewById(R.id.pw_sorti2).setVisibility(View.VISIBLE);
        else if (loop == 3)
            view.findViewById(R.id.pw_sorti3).setVisibility(View.VISIBLE);
        speed1.setOnClickListener(v -> {
            speed1.setTextColor(getResources().getColor(R.color.alivc_blue));
            speed2.setTextColor(getResources().getColor(R.color.common_ff333333));
            speed3.setTextColor(getResources().getColor(R.color.common_ff333333));
            view.findViewById(R.id.pw_sorti1).setVisibility(View.VISIBLE);
            view.findViewById(R.id.pw_sorti2).setVisibility(View.GONE);
            view.findViewById(R.id.pw_sorti3).setVisibility(View.GONE);
            mBinding.studyReadXunhuan.setText("单篇循环");
            audioLessonDetailFragment.setLoopModel(1);
            if (popupWindow.isShowing())
                popupWindow.dismiss();
        });
        speed2.setOnClickListener(v -> {
            speed2.setTextColor(getResources().getColor(R.color.alivc_blue));
            speed1.setTextColor(getResources().getColor(R.color.common_ff333333));
            speed3.setTextColor(getResources().getColor(R.color.common_ff333333));
            view.findViewById(R.id.pw_sorti2).setVisibility(View.VISIBLE);
            view.findViewById(R.id.pw_sorti1).setVisibility(View.GONE);
            view.findViewById(R.id.pw_sorti3).setVisibility(View.GONE);
            mBinding.studyReadXunhuan.setText("顺序播放");
            audioLessonDetailFragment.setLoopModel(2);
            if (popupWindow.isShowing())
                popupWindow.dismiss();
        });
        speed3.setOnClickListener(v -> {
            speed3.setTextColor(getResources().getColor(R.color.alivc_blue));
            speed2.setTextColor(getResources().getColor(R.color.common_ff333333));
            speed1.setTextColor(getResources().getColor(R.color.common_ff333333));
            view.findViewById(R.id.pw_sorti3).setVisibility(View.VISIBLE);
            view.findViewById(R.id.pw_sorti2).setVisibility(View.GONE);
            view.findViewById(R.id.pw_sorti1).setVisibility(View.GONE);
            mBinding.studyReadXunhuan.setText("单句循环");
            audioLessonDetailFragment.setLoopModel(3);
            if (popupWindow.isShowing())
                popupWindow.dismiss();

        });


    }

    public void showNotifi(String msg) {
        ToastUtils.showShort(msg);
    }


    @Override
    protected void onResume() {
        super.onResume();
        if (isStop)
            isStop = false;
    }

    private boolean isStop = false;

    @Override
    protected void onStop() {
        super.onStop();
        isStop = true;
        LogUtils.e("onStop------");
        if (ChivoxUtils.recorderApp != null)
            if (ChivoxUtils.recorderApp.isRunning())
                appExecutors.networkIO().execute(() -> {
                    ChivoxUtils.recorderApp.stop();
                });
    }

    // * 双击返回键退出
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            stopThis();
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

    //关闭  台电平板 反应有时候痴呆
    private void stopThis() {
        LogUtils.e("stopThis------");
        if (ChivoxUtils.recorderApp != null)
            if (ChivoxUtils.recorderApp.isRunning())
                appExecutors.networkIO().execute(() -> {
                    LogUtils.e("recorderApp--stopThis------");
                    ChivoxUtils.recorderApp.stop();
                });
        this.finish();
    }

    @Override
    public void initImmersionBar() {
        ImmersionBar.with(this)
                .fitsSystemWindows(true)  //使用该属性,必须指定状态栏颜色
                .statusBarColor("#29BBFF")
//                .statusBarDarkFont(true)   //状态栏字体是深色，不写默认为亮色
//                .autoDarkModeEnable(true) //自动状态栏字体和导航栏图标变色，必须指定状态栏颜色和导航栏颜色才可以自动变色哦
//                .autoStatusBarDarkModeEnable(true, 0.2f) //自动状态栏字体变色，必须指定状态栏颜色才可以自动变色哦
//                .autoNavigationBarDarkModeEnable(true, 0.2f) //自动导航栏图标变色，必须指定导航栏颜色才可以自动变色哦
//                .navigationBarColor(R.color.white)
//                .fullScreen(true)
                .init();
    }
}
