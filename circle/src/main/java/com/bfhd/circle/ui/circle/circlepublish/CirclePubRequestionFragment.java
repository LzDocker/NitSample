package com.bfhd.circle.ui.circle.circlepublish;

import android.Manifest;
import android.animation.ObjectAnimator;
import android.arch.lifecycle.ViewModelProvider;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.databinding.Observable;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.animation.LinearInterpolator;

import com.bfhd.circle.R;
import com.bfhd.circle.base.CommonFragment;
import com.bfhd.circle.base.Constant;
import com.bfhd.circle.base.ViewEventResouce;
import com.bfhd.circle.databinding.CircleFragmentCirclePubRequestionBinding;
import com.bfhd.circle.ui.circle.CirclePerssionSelectActivity;
import com.bfhd.circle.ui.circle.CirclePublishActivity;
import com.bfhd.circle.ui.circle.CircleShareSelectActivity;
import com.bfhd.circle.ui.common.CircleSourceUpFragment;
import com.bfhd.circle.vm.CirclePublishViewModel;
import com.bfhd.circle.vo.ServiceDataBean;
import com.bfhd.circle.vo.bean.ReleaseDyamicBean;
import com.bfhd.circle.vo.bean.SourceUpParam;
import com.bfhd.circle.vo.bean.StaCirParam;
import com.dcbfhd.utilcode.utils.FileUtils;
import com.dcbfhd.utilcode.utils.FragmentUtils;
import com.dcbfhd.utilcode.utils.LogUtils;
import com.dcbfhd.utilcode.utils.ToastUtils;
import com.docker.common.common.config.ThiredPartConfig;
import com.docker.common.common.utils.cache.CacheUtils;
import com.docker.common.common.utils.oss.MyOSSUtils;
import com.docker.common.common.utils.rxbus.RxBus;
import com.docker.common.common.utils.rxbus.RxEvent;
import com.docker.common.common.vo.UserInfoVo;
import com.docker.core.util.AppExecutors;
import com.iflytek.cloud.ErrorCode;
import com.iflytek.cloud.InitListener;
import com.iflytek.cloud.RecognizerListener;
import com.iflytek.cloud.RecognizerResult;
import com.iflytek.cloud.SpeechConstant;
import com.iflytek.cloud.SpeechError;
import com.iflytek.cloud.SpeechRecognizer;
import com.iflytek.cloud.SpeechUtility;
import com.luck.picture.lib.entity.LocalMedia;
import com.luck.picture.lib.permissions.RxPermissions;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONTokener;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;

import javax.inject.Inject;

import io.reactivex.disposables.Disposable;

/*
 * 问答类型的动态  复制之前的项目的 整的头疼
 * */
public class CirclePubRequestionFragment extends CommonFragment<CirclePublishViewModel, CircleFragmentCirclePubRequestionBinding> {

    private SourceUpParam mSourceUpParam;
    private List<String> urlList = new ArrayList<>();//上传后的资源路径
    private List<String> imgList = new ArrayList<>();//上传后的图片路径
    private List<ReleaseDyamicBean> upLoadVideoList = new ArrayList<>();
    private CircleSourceUpFragment sourceUpFragment;
    // 语音听写对象
    private SpeechRecognizer mIat;
    // 用HashMap存储听写结果
    private HashMap<String, String> mIatResults = new LinkedHashMap<>();
    private String uploadFilePath = Constant.BaseFileFloder + System.currentTimeMillis() + "iat.wav";//需要上传的音频文件
    // 总共的时间是30s.
    public static final int MAX_PROGRESS = 100;
    private ObjectAnimator objectAnimatorBottom;
    public static final String PROGRESS_PROPERTY = "progress";
    private boolean isProgress;
    private int audioTime = 1;//录制的时间或指播放时间
    MediaPlayer mp = new MediaPlayer();

    private Disposable disposable;

    private int mEditType;
    /*
     *
     * 选择圈子 选择 分类 子栏目
     *
     * extenMap size 来区分选了多少级
     *
     * */
    private StaCirParam mHandParam; // 接收到的分组数据

//    private StaCirParam mPerssionParam; // 权限数据

    private Handler progressUpdateTimer = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            showVideoProgressInfo();
        }
    };

    @Inject
    ViewModelProvider.Factory factory;

    @Override
    protected int getLayoutId() {
        return R.layout.circle_fragment_circle_pub_requestion;
    }

    public static CirclePubRequestionFragment newInstance() {
        CirclePubRequestionFragment detailFragment = new CirclePubRequestionFragment();
        return detailFragment;
    }

    @Override
    protected CirclePublishViewModel getViewModel() {
        return ViewModelProviders.of(this, factory).get(CirclePublishViewModel.class);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mEditType = ((CirclePublishActivity) getHoldingActivity()).getmEditType();
        mHandParam = ((CirclePublishActivity) getHoldingActivity()).getmStartParam();

        // 初始化讯飞
        SpeechUtility.createUtility(this.getHoldingActivity(), SpeechConstant.APPID + ThiredPartConfig.IFLAYID);
        initIfly();
        disposable = RxBus.getDefault().toObservable(RxEvent.class).subscribe(rxEvent -> {
            if (rxEvent.getT().equals("GroupSelect")) {
                mHandParam = (StaCirParam) rxEvent.getR();
                processStaparam();
            }
            if (rxEvent.getT().equals("circle_perrsion_update")) { // 权限
                mHandParam = (StaCirParam) rxEvent.getR();
                if (mHandParam != null) {
                    mBinding.get().tvPerssionSelect.setText(mHandParam.extentron2);
                }
            }
        });
        processNext();
    }


    public void processStaparam() {
        if (mHandParam != null) {
            mBinding.get().circleSelect.setVisibility(View.VISIBLE);
            if (!TextUtils.isEmpty(mHandParam.getCircleid())) {
                mBinding.get().perssionSelect.setVisibility(View.VISIBLE);
            }
            if (TextUtils.isEmpty(mHandParam.extentron2)) {
                mBinding.get().tvPerssionSelect.setText("全部");
            } else {
                // 编辑逻辑后面再写
            }
            switch (mHandParam.getExtenMap().size()) { // 圈子 分类
                case 0:    // 没有一级栏目 只有圈子
                    mBinding.get().tvCircleSelect.setText(mHandParam.getExtentron());
                    break;
                case 2:   // 没有选择二级栏目
                    mBinding.get().tvCircleSelect.setText(mHandParam.getExtentron() + " / " + mHandParam.getExtenMap().get("classname1"));
                    break;
                case 4:  // 都有
                    mBinding.get().tvCircleSelect.setText(mHandParam.getExtentron() + " / " + mHandParam.getExtenMap().get("classname1") + " / " + mHandParam.getExtenMap().get("classname2"));
                    break;
            }
        }
    }

    @Override
    public void initImmersionBar() {
    }

    private void initIfly() {
        mIat = SpeechRecognizer.createRecognizer(this.getHoldingActivity(), new InitListener() {
            @Override
            public void onInit(int code) {
                if (code != ErrorCode.SUCCESS) {
                    ToastUtils.showShort("初始化失败，错误码：" + code);
                }
            }
        });
        setParam();
    }

    private void showVideoProgressInfo() {
        if (isProgress && audioTime <= 60) {
            mBinding.get().activityQuizTvTime.setVisibility(View.VISIBLE);
            mBinding.get().activityQuizTvTime.setText(audioTime + "''");
            audioTime++;
            startUpdateTimer();
        } else {
            isProgress = false;
            mIat.stopListening();
            mBinding.get().activityQuizTvReRecord.setVisibility(View.VISIBLE);
            mBinding.get().activityQuizIvPause.setVisibility(View.GONE);
            mBinding.get().activityQuizIvPlay.setVisibility(View.VISIBLE);
            mBinding.get().activityQuizPb.setVisibility(View.GONE);
            mBinding.get().activityQuizPb.setProgress(0);
        }
    }

    private void startUpdateTimer() {
        if (progressUpdateTimer != null) {
            progressUpdateTimer.removeMessages(0);
            progressUpdateTimer.sendEmptyMessageDelayed(0, 1000);
        }
    }


    /**
     * 听写监听器。
     */
    private RecognizerListener mRecognizerListener = new RecognizerListener() {

        @Override
        public void onBeginOfSpeech() {
            // 此回调表示：sdk内部录音机已经准备好了，用户可以开始语音输入
            LogUtils.e("========", "开始说话");
            // 当前进度
            mBinding.get().activityQuizPb.setMax(MAX_PROGRESS);
            mBinding.get().activityQuizPb.setProgress(0);

            //初始化动画
            objectAnimatorBottom = ObjectAnimator
                    .ofInt(mBinding.get().activityQuizPb, PROGRESS_PROPERTY, mBinding.get().activityQuizPb.getMax())
                    .setDuration(60000);
            objectAnimatorBottom.setInterpolator(new LinearInterpolator());
            objectAnimatorBottom.start();
            mBinding.get().activityQuizTvTime.setVisibility(View.VISIBLE);
            startUpdateTimer();//记录播放时间
        }

        @Override
        public void onError(SpeechError error) {
            // Tips：
            // 错误码：10118(您没有说话)，可能是录音机权限被禁，需要提示用户打开应用的录音权限。
            // 如果使用本地功能（语记）需要提示用户开启语记的录音权限。
            LogUtils.e("========", error.getPlainDescription(true));

        }

        @Override
        public void onEndOfSpeech() {
            // 此回调表示：检测到了语音的尾端点，已经进入识别过程，不再接受语音输入
            LogUtils.e("========", "结束说话");

            mBinding.get().activityQuizIvPlay.setVisibility(View.VISIBLE);
            mBinding.get().activityQuizIvRecord.setVisibility(View.GONE);
        }

        @Override
        public void onResult(RecognizerResult results, boolean isLast) {
            LogUtils.e("========", results.getResultString());
            printResult(results);
        }

        @Override
        public void onVolumeChanged(int volume, byte[] data) {
            LogUtils.e("========", "当前正在说话，音量大小：" + volume);
            LogUtils.e("========", "返回音频数据：" + data.length);


        }

        @Override
        public void onEvent(int eventType, int arg1, int arg2, Bundle obj) {
            // 以下代码用于获取与云端的会话id，当业务出错时将会话id提供给技术支持人员，可用于查询会话日志，定位出错原因
            // 若使用本地能力，会话id为null
            //	if (SpeechEvent.EVENT_SESSION_ID == eventType) {
            //		String sid = obj.getString(SpeechEvent.KEY_EVENT_SESSION_ID);
            //		Log.d(TAG, "session id =" + sid);
            //	}
        }
    };

    /**
     * 打印解析音频的文本
     *
     * @param results
     */
    private void printResult(RecognizerResult results) {
        String text = parseIatResult(results.getResultString());

        String sn = null;
        // 读取json结果中的sn字段
        try {
            JSONObject resultJson = new JSONObject(results.getResultString());
            sn = resultJson.optString("sn");
        } catch (JSONException e) {
            e.printStackTrace();
        }

        mIatResults.put(sn, text);

        StringBuffer resultBuffer = new StringBuffer();
        for (String key : mIatResults.keySet()) {
            resultBuffer.append(mIatResults.get(key));
        }

        mBinding.get().activityAnswerEt.setText(resultBuffer.toString());
        mBinding.get().activityAnswerEt.setSelection(mBinding.get().activityAnswerEt.length());

    }


    public static String parseIatResult(String json) {
        StringBuffer ret = new StringBuffer();
        try {
            JSONTokener tokener = new JSONTokener(json);
            JSONObject joResult = new JSONObject(tokener);

            JSONArray words = joResult.getJSONArray("ws");
            for (int i = 0; i < words.length(); i++) {
                // 转写结果词，默认使用第一个结果
                JSONArray items = words.getJSONObject(i).getJSONArray("cw");
                JSONObject obj = items.getJSONObject(0);
                ret.append(obj.getString("w"));
//				如果需要多候选结果，解析数组其他字段
//				for(int j = 0; j < items.length(); j++)
//				{
//					JSONObject obj = items.getJSONObject(j);
//					ret.append(obj.getString("w"));
//				}
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return ret.toString();
    }


    private void processNext() {

        mSourceUpParam = new SourceUpParam(SourceUpParam.SOURCE_TYPE_AUTO, 9, urlList, imgList, upLoadVideoList);
        sourceUpFragment = CircleSourceUpFragment.newInstance(mSourceUpParam);

        if (mEditType == 2) {
            if (mHandParam.serviceDataBean.getExtData().getResource() != null && mHandParam.serviceDataBean.getExtData().getResource().size() > 0) {
                ArrayList<LocalMedia> localMediaList = new ArrayList();
                List<ServiceDataBean.ResourceBean> resourceBeans = mHandParam.serviceDataBean.getExtData().getResource();
                if (resourceBeans != null && resourceBeans.size() > 0) {
                    for (int i = 0; i < resourceBeans.size(); i++) {
                        LocalMedia localMedia = new LocalMedia();

                        if (2 == resourceBeans.get(i).getT()) {
                            localMedia.setPictureType("video/mp4");
                            localMedia.setPath(Constant.getCompleteImageUrl(resourceBeans.get(i).getUrl()));
                        } else {
                            localMedia.setPictureType("image/jpeg");
                            localMedia.setPath(Constant.getCompleteImageUrl(resourceBeans.get(i).getImg()));
                        }


//                        localMedia.setPictureType("1");
//                        if (!TextUtils.isEmpty(resourceBeans.get(i).getImg())) {
//                            localMedia.setPath(Constant.getCompleteImageUrl(resourceBeans.get(i).getImg()));
//                        } else {
//                            localMedia.setPath(Constant.getCompleteImageUrl(resourceBeans.get(i).getUrl()));
//                        }
                        localMediaList.add(localMedia);
                    }
                }
                sourceUpFragment.processDataRep(localMediaList);
            }
        }


//        mBinding.get().checkIsRelay.setOnCheckedChangeListener((buttonView, isChecked) -> {
//            if (!isChecked) {
//                if (mHandParam != null) {
//                    mHandParam = null;
//                }
//                if (mPerssionParam != null) {
//                    mPerssionParam = null;
//                }
//                mBinding.get().perssionSelect.setVisibility(View.GONE);
//                mBinding.get().circleSelect.setVisibility(View.GONE);
//                mBinding.get().tvCircleSelect.setText("");
//            } else {
//                mBinding.get().circleSelect.setVisibility(View.VISIBLE);
//            }
//        });
        mBinding.get().perssionSelect.setOnClickListener(v -> {
            if (mHandParam == null) {
                ToastUtils.showShort("请先选择要同步的圈子");
                return;
            }
            CirclePerssionSelectActivity.startMe(CirclePubRequestionFragment.this.getHoldingActivity(), mHandParam);
        });
        mBinding.get().circleSelect.setOnClickListener(v -> {

            CircleShareSelectActivity.startMe(CirclePubRequestionFragment.this.getHoldingActivity(), mHandParam);
        });

        FragmentUtils.add(getChildFragmentManager(), sourceUpFragment, R.id.souce_up_frame);
        mSourceUpParam.status.addOnPropertyChangedCallback(new Observable.OnPropertyChangedCallback() {
            @Override
            public void onPropertyChanged(Observable sender, int propertyId) {
                // 根据状态回调 来判断是否能发布了
                switch (mSourceUpParam.status.get()) {
                    case 2:
                        realPublish();
                        break;
                    case 3:
                        hidWaitDialog();
                        ToastUtils.showShort("上传资源失败请重试！");
                        break;
                }
            }
        });

        mBinding.get().activityQuizIvRecord.setOnClickListener(v -> {
            record();
        });

        mBinding.get().activityQuizIvPlay.setOnClickListener(v -> {
            mp.reset();//将mp重置为创建状态

            initMP();//初始化mp，这样保证按下stop按钮后再按play按钮可以播放
            if (!mp.isPlaying()) {
                mp.start();//播放音频
            }
        });
        //
        mBinding.get().activityQuizTvReRecord.setOnClickListener(v -> {
            mBinding.get().activityQuizPb.setProgress(0);
            mBinding.get().activityQuizTvReRecord.setVisibility(View.GONE);
            mBinding.get().activityQuizIvPlay.setVisibility(View.GONE);
            mBinding.get().activityQuizIvPlay2.setVisibility(View.GONE);
            mBinding.get().activityQuizIvPause.setVisibility(View.GONE);
            mBinding.get().activityQuizTvTime.setText("0''");
//            mBinding.get().activityQuizTvTime.setVisibility(View.GONE);
            mBinding.get().activityQuizIvRecord.setVisibility(View.VISIBLE);
            audioTime = 1;
            mBinding.get().activityAnswerEt.setText("");
        });

        mBinding.get().activityQuizIvPause.setOnClickListener(v -> {
            if (mIat.isListening()) {
                isProgress = false;
                mIat.stopListening();
                objectAnimatorBottom.cancel();

                mBinding.get().activityQuizTvReRecord.setVisibility(View.VISIBLE);
                mBinding.get().activityQuizIvPause.setVisibility(View.GONE);
                mBinding.get().activityQuizIvPlay.setVisibility(View.VISIBLE);
                mBinding.get().activityQuizPb.setVisibility(View.GONE);
                mBinding.get().activityQuizPb.setProgress(0);
            }
        });

        if (mEditType == 2) {
            mHandParam = processStaParam(mHandParam.serviceDataBean);
        }
        processStaparam();
    }


    private StaCirParam processStaParam(ServiceDataBean serviceDataBean) {
        mHandParam.getExtenMap().put("classid1", serviceDataBean.getClassid());
        mHandParam.getExtenMap().put("classname1", "");
        mHandParam.getExtenMap().put("classid2", serviceDataBean.getClassid2());
        mHandParam.getExtenMap().put("classname2", "");
        if (serviceDataBean.getExtData() != null) {
            mBinding.get().activityAnswerEt.setText(serviceDataBean.getExtData().getContent());
        }
        return mHandParam;
    }

    //初始化mp
    private void initMP() {
        try {
            mp = new MediaPlayer();//新建一个的实例
            mp.setDataSource(this.getHoldingActivity(), Uri.parse(uploadFilePath));
            mp.prepare();//播放 准备完成，开始播放前要调用
            mp.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
                @Override
                public void onPrepared(MediaPlayer mp) {
                    mBinding.get().activityQuizIvPlay.setVisibility(View.GONE);
                    mBinding.get().activityQuizIvPause.setVisibility(View.VISIBLE);
                    mBinding.get().activityQuizPb.setVisibility(View.VISIBLE);
                    mBinding.get().activityQuizTvReRecord.setVisibility(View.GONE);
                    // 当前进度
                    mBinding.get().activityQuizPb.setMax(MAX_PROGRESS);
                    mBinding.get().activityQuizPb.setProgress(0);
                    //初始化动画
                    objectAnimatorBottom = ObjectAnimator
                            .ofInt(mBinding.get().activityQuizPb, PROGRESS_PROPERTY, mBinding.get().activityQuizPb.getMax())
                            .setDuration(mp.getDuration());
                    objectAnimatorBottom.setInterpolator(new LinearInterpolator());
                    objectAnimatorBottom.start();

                    mBinding.get().activityQuizTvTime.setText("0''");
                    audioTime = 1;
                    isProgress = true;
                    startUpdateTimer();//记录播放时间
                }
            });
            mp.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
                @Override
                public void onCompletion(MediaPlayer mp) {
                    isProgress = false;
                    mBinding.get().activityQuizIvPlay.setVisibility(View.VISIBLE);
                    mBinding.get().activityQuizIvPause.setVisibility(View.GONE);
                    mBinding.get().activityQuizPb.setVisibility(View.GONE);
                    mBinding.get().activityQuizPb.setProgress(0);
                    mBinding.get().activityQuizTvReRecord.setVisibility(View.VISIBLE);

                }
            });
        } catch (IOException e) {
            e.printStackTrace();
        }

    }


    @Override
    protected void initView(View var1) {

    }


    /**
     * 开启录音
     */
    private void record() {
        mBinding.get().activityAnswerEt.setText(null);
        mIatResults.clear();// 清空显示内容
        isProgress = true;
        mBinding.get().activityQuizIvRecord.setVisibility(View.GONE);
        mBinding.get().activityQuizIvPause.setVisibility(View.VISIBLE);
        mBinding.get().activityQuizPb.setVisibility(View.VISIBLE);
        mBinding.get().activityQuizTvTime.setVisibility(View.VISIBLE);
        RxPermissions rxPermissions = new RxPermissions(this.getHoldingActivity());
        rxPermissions
                .request(
                        Manifest.permission.READ_EXTERNAL_STORAGE,
                        Manifest.permission.RECORD_AUDIO,
                        Manifest.permission.CAMERA)
                .subscribe(granted -> {
                    if (granted) {
                        int ret = mIat.startListening(mRecognizerListener);
                        if (ret != ErrorCode.SUCCESS) {
                            LogUtils.e("==========", "听写失败,错误码：" + ret);
                        } else {
                            LogUtils.e("==========", "开始说话");
                        }
                    } else {
                        ToastUtils.showShort("权限被拒绝，请在设置里面开启相应权限，若无相应权限会影响使用");
                    }
                });
    }


    @Override
    public void onPause() {
        super.onPause();
        if (mIat.isListening()) {
            isProgress = false;
            mIat.stopListening();
            objectAnimatorBottom.cancel();

            mBinding.get().activityQuizTvReRecord.setVisibility(View.VISIBLE);
            mBinding.get().activityQuizIvPause.setVisibility(View.GONE);
            mBinding.get().activityQuizIvPlay.setVisibility(View.VISIBLE);
            mBinding.get().activityQuizPb.setVisibility(View.GONE);
            mBinding.get().activityQuizPb.setProgress(0);
        }
        if (mp.isPlaying()) {
            isProgress = false;
            mp.pause();//暂停播放
            mBinding.get().activityQuizTvTime.setText(mp.getDuration() > 59 * 1000 ? "60''" : mp.getDuration() / 1000 + "''");
            mBinding.get().activityQuizIvPause.setVisibility(View.GONE);
            mBinding.get().activityQuizIvPlay.setVisibility(View.VISIBLE);
            mBinding.get().activityQuizTvReRecord.setVisibility(View.VISIBLE);
            mBinding.get().activityQuizPb.setVisibility(View.GONE);
            mBinding.get().activityQuizPb.setProgress(0);

        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (progressUpdateTimer != null) {
            progressUpdateTimer.removeCallbacksAndMessages(this);
        }
        progressUpdateTimer = null;
        if (mp != null) {
            mp.release();
            mp = null;
        }
        if (mIat.isListening()) {
            isProgress = false;
            mIat.stopListening();
            objectAnimatorBottom.cancel();
        }
        if (disposable != null) {
            disposable.dispose();
        }
    }


    /**
     * 参数设置
     *
     * @return
     */
    public void setParam() {
        // 清空参数
        mIat.setParameter(SpeechConstant.PARAMS, null);
        // 设置听写引擎
        mIat.setParameter(SpeechConstant.ENGINE_TYPE, SpeechConstant.TYPE_CLOUD);
        // 设置返回结果格式
        mIat.setParameter(SpeechConstant.RESULT_TYPE, "json");
        // 设置语言
        mIat.setParameter(SpeechConstant.LANGUAGE, "zh_cn");
        // 设置语言区域
        mIat.setParameter(SpeechConstant.ACCENT, null);
        // 设置语音前端点:静音超时时间，即用户多长时间不说话则当做超时处理
        mIat.setParameter(SpeechConstant.VAD_BOS, "60000");
        // 设置语音后端点:后端点静音检测时间，即用户停止说话多长时间内即认为不再输入， 自动停止录音
        mIat.setParameter(SpeechConstant.VAD_EOS, "60000");
        // 设置标点符号,设置为"0"返回结果无标点,设置为"1"返回结果有标点
        mIat.setParameter(SpeechConstant.ASR_PTT, "1");
        // 设置音频保存路径，保存音频格式支持pcm、wav，设置路径为sd卡请注意WRITE_EXTERNAL_STORAGE权限
        // 注：AUDIO_FORMAT参数语记需要更新版本才能生效
        mIat.setParameter(SpeechConstant.AUDIO_FORMAT, "wav");
        mIat.setParameter(SpeechConstant.ASR_AUDIO_PATH, uploadFilePath);
    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        sourceUpFragment.onActivityResult(requestCode, resultCode, data);
    }


    // 发布
    public void publish() {

        if (TextUtils.isEmpty(mBinding.get().activityAnswerEt.getText().toString())) {
            ToastUtils.showShort("请输入要发表的内容");
            return;
        }
        if (TextUtils.isEmpty(mHandParam.getCircleid()) || TextUtils.isEmpty(mHandParam.getUtid())) {
            ToastUtils.showShort("请选择要发布的圈子");
            return;
        }
        if (sourceUpFragment.selectList != null && sourceUpFragment.selectList.size() > 0) {
            sourceUpFragment.processUpload();
        } else {
            realPublish();
        }

    }

    @Inject
    AppExecutors appExecutors;

    public void realPublish() {
        appExecutors.mainThread().execute(() -> showWaitDialog("上传音频中..."));
        appExecutors.networkIO().execute(() -> {
            if (FileUtils.isFileExists(uploadFilePath) && FileUtils.getFileLength(uploadFilePath) > 0) {
                MyOSSUtils.getInstance().upVideo(this.getHoldingActivity(), FileUtils.getFileName(uploadFilePath), uploadFilePath, new MyOSSUtils.OssUpCallback() {
                    @Override
                    public void successImg(String img_url) {

                    }

                    @Override
                    public void successVideo(String video_url) {
                        if (video_url != null) {
                            Log.d("sss", "successVideo: " + video_url);
                            appExecutors.mainThread().execute(() -> {
                                hidWaitDialog();
                                String[] audiourl = video_url.split("com");
                                answer(audiourl[1]);
                            });
                        } else {
                            appExecutors.mainThread().execute(() -> {
                                hidWaitDialog();
                                ToastUtils.showShort("音频上传失败请重试！");
                            });
                        }
                    }

                    @Override
                    public void inProgress(long progress, long zong) {
                    }
                });
            } else {
                appExecutors.mainThread().execute(() -> {
                    hidWaitDialog();
                });
                answer("");
            }
        });

    }

    public void answer(String audioUrl) {

        UserInfoVo userInfoVo = CacheUtils.getUser();
        HashMap<String, String> paramMap = new HashMap();
        paramMap.put("memberid", userInfoVo.uid);
        paramMap.put("uuid", userInfoVo.uuid);
        paramMap.put("type", "answer");
        paramMap.put("content", mBinding.get().activityAnswerEt.getText().toString().trim());
        paramMap.put("circleid", mHandParam.getCircleid());
        paramMap.put("utid", mHandParam.getUtid());

        if (!TextUtils.isEmpty(mHandParam.getExtenMap().get("classid1"))) {
            paramMap.put("classid", mHandParam.getExtenMap().get("classid1"));
        }
        if (!TextUtils.isEmpty(mHandParam.getExtenMap().get("classid2"))) {
            paramMap.put("classid2", mHandParam.getExtenMap().get("classid2"));
        }

        if (mEditType == 2) {
            paramMap.put("dataid", mHandParam.serviceDataBean.getDataid());
        }
        if (TextUtils.isEmpty(mHandParam.extentron2) && mHandParam.extentronList.size() == 0) {
            paramMap.put("visibleType", "0");   // extentronList
        } else {
            paramMap.put("visibleType", "1");

            StringBuilder stringBuilder = new StringBuilder();
            for (int i = 0; i < mHandParam.extentronList.size(); i++) {
                String id = (String) mHandParam.extentronList.get(i);
                stringBuilder.append(id).append(",");
            }
            if (stringBuilder.length() > 1) {
                String ids = stringBuilder.substring(0, stringBuilder.length() - 1);
                paramMap.put("groupids", ids);
            }
        }

        if (mBinding.get().activityQuizTvReRecord.getVisibility() == View.VISIBLE && !TextUtils.isEmpty(audioUrl)) {
            paramMap.put("audio", audioUrl);
            paramMap.put("audio_duration", mBinding.get().activityQuizTvTime.getText().toString().substring(0, mBinding.get().activityQuizTvTime.getText().toString().indexOf("'")));
        }


        if (mSourceUpParam.imgList.size() > 0) {
            for (int i = 0; i < mSourceUpParam.imgList.size(); i++) {
                paramMap.put("resource[" + i + "][t]", "1");
                paramMap.put("resource[" + i + "][url]", mSourceUpParam.imgList.get(i));
                paramMap.put("resource[" + i + "][img]", mSourceUpParam.imgList.get(i));
                paramMap.put("resource[" + i + "][sort]", i + 1 + "");
            }
        }
        if (mSourceUpParam.upLoadVideoList.size() > 0) {
            for (int i = 0; i < mSourceUpParam.upLoadVideoList.size(); i++) {
                paramMap.put("resource[" + i + "][t]", "2");
                paramMap.put("resource[" + i + "][url]", mSourceUpParam.upLoadVideoList.get(i).getVideoUrl());
                paramMap.put("resource[" + i + "][img]", mSourceUpParam.upLoadVideoList.get(i).getVideoImgUrl());
                paramMap.put("resource[" + i + "][sort]", i + 1 + "");
            }
        }
        this.getHoldingActivity().runOnUiThread(() -> mViewModel.publishNews(paramMap));

    }


    @Override
    public void OnVmEnentListner(ViewEventResouce viewEventResouce) {
        super.OnVmEnentListner(viewEventResouce);
        if (viewEventResouce.eventType == 104) {
            ToastUtils.showShort("发布成功");
            RxBus.getDefault().post(new RxEvent<>("dynamic_refresh", ""));
            getHoldingActivity().finish();
        }
    }
}
