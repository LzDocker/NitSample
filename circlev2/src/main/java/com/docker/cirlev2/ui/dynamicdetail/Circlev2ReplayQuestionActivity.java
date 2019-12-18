package com.docker.cirlev2.ui.dynamicdetail;

import android.Manifest;
import android.animation.ObjectAnimator;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Context;
import android.content.Intent;
import android.databinding.Observable;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.animation.LinearInterpolator;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.dcbfhd.utilcode.utils.FileUtils;
import com.dcbfhd.utilcode.utils.FragmentUtils;
import com.dcbfhd.utilcode.utils.LogUtils;
import com.dcbfhd.utilcode.utils.ToastUtils;
import com.docker.cirlev2.R;
import com.docker.cirlev2.databinding.Circlev2ActivityCircleReplayQuestionBinding;
import com.docker.cirlev2.ui.common.CircleSourceUpFragment;
import com.docker.cirlev2.vm.CircleDynamicDetailViewModel;
import com.docker.cirlev2.vo.entity.ServiceDataBean;
import com.docker.cirlev2.vo.param.SourceUpParam;
import com.docker.common.common.config.Constant;
import com.docker.common.common.ui.base.NitCommonActivity;
import com.docker.common.common.utils.cache.CacheUtils;
import com.docker.common.common.utils.oss.MyOSSUtils;
import com.docker.common.common.vo.UserInfoVo;
import com.docker.core.di.netmodule.progress.ProgressManager;
import com.docker.core.util.AppExecutors;
import com.iflytek.cloud.ErrorCode;
import com.iflytek.cloud.InitListener;
import com.iflytek.cloud.RecognizerListener;
import com.iflytek.cloud.RecognizerResult;
import com.iflytek.cloud.SpeechConstant;
import com.iflytek.cloud.SpeechError;
import com.iflytek.cloud.SpeechRecognizer;
import com.iflytek.cloud.SpeechUtility;
import com.luck.picture.lib.permissions.RxPermissions;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONTokener;

import java.io.IOException;
import java.util.HashMap;
import java.util.LinkedHashMap;

import javax.inject.Inject;

import static com.docker.common.common.router.AppRouter.CIRCLE_comment_v2_ANSWER;

/*
 * 回复问答
 * */

@Route(path = CIRCLE_comment_v2_ANSWER)
public class Circlev2ReplayQuestionActivity extends NitCommonActivity<CircleDynamicDetailViewModel, Circlev2ActivityCircleReplayQuestionBinding> {

    private ServiceDataBean serviceDataBean;
    private SourceUpParam mSourceUpParam;
    private CircleSourceUpFragment sourceUpFragment;
    private String audioUrl;
    @Inject
    AppExecutors appExecutors;

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
    @Inject
    ProgressManager progressManager;

    private Handler progressUpdateTimer = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            showVideoProgressInfo();
        }
    };

    public static void startMe(Context context, ServiceDataBean serviceDataBean) {

        Intent intent = new Intent(context, Circlev2ReplayQuestionActivity.class);
        Bundle bundle = new Bundle();
        bundle.putSerializable("datasource", serviceDataBean);
        intent.putExtras(bundle);
        context.startActivity(intent);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.circlev2_activity_circle_replay_question;
    }

    @Override
    public CircleDynamicDetailViewModel getmViewModel() {
        return ViewModelProviders.of(this, factory).get(CircleDynamicDetailViewModel.class);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        serviceDataBean = (ServiceDataBean) getIntent().getSerializableExtra("ServiceDataBean");
        super.onCreate(savedInstanceState);
        mBinding.setViewmodel(mViewModel);
        mBinding.setItem(serviceDataBean);
        // 初始化讯飞
        SpeechUtility.createUtility(this, SpeechConstant.APPID + "=5afbf83a");
        initIfly();
    }

    // 上传语音
    private void realPublish() {
        showWaitDialog("上传音频中...");
        appExecutors.networkIO().execute(() -> {
            if (FileUtils.isFileExists(uploadFilePath) && FileUtils.getFileLength(uploadFilePath) > 0) {
                MyOSSUtils.getInstance().upVideo(this, FileUtils.getFileName(uploadFilePath), uploadFilePath, new MyOSSUtils.OssUpCallback() {
                    @Override
                    public void successImg(String img_url) {

                    }

                    @Override
                    public void successVideo(String video_url) {
                        if (video_url != null) {
                            Log.d("sss", "successVideo: " + video_url);
                            appExecutors.mainThread().execute(() -> {
                                hidWaitDialog();
                                String[] audiopubUrl = video_url.split("com");
                                answer(audiopubUrl[1]);
                                audioUrl = video_url;
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
                    answer("");
                });

            }
        });
    }

    public void answer(String audioUrl) {

        HashMap<String, String> params = new HashMap<>();
        UserInfoVo userInfoVo = CacheUtils.getUser();
        params.put("circleid", serviceDataBean.getCircleid());
        params.put("utid", serviceDataBean.getUtid());
        params.put("dynamicid", serviceDataBean.getDynamicid());
        params.put("push_uuid", serviceDataBean.getUuid());
        params.put("push_memberid", serviceDataBean.getMemberid());
        params.put("memberid", userInfoVo.uid);
        params.put("uuid", userInfoVo.uuid);
        if (TextUtils.isEmpty(userInfoVo.avatar)) {
            userInfoVo.avatar = "/var/upload/image/section/logo3.png";
        }
        params.put("avatar", userInfoVo.avatar);
        if (TextUtils.isEmpty(userInfoVo.nickname)) {
            params.put("nickname", "匿名");
        } else {
            params.put("nickname", userInfoVo.nickname);
        }
        params.put("content", mBinding.activityAnswerEt.getText().toString());
        params.put("cid", "0");
        params.put("reply_memberid", "");
        params.put("reply_uuid", "");
        params.put("reply_nickname", "");
        if (mBinding.activityQuizTvReRecord.getVisibility() == View.VISIBLE && !TextUtils.isEmpty(audioUrl)) {
            params.put("audio", audioUrl);
            params.put("audio_duration", mBinding.activityQuizTvTime.getText().toString().substring(0, mBinding.activityQuizTvTime.getText().toString().indexOf("'")));
        }
        mViewModel.commentDynamic(params);

    }


    @Override
    public void initView() {
        mToolbar.setTitle("回复问题");
        mToolbar.setTvRight("提交", v -> {
            if (TextUtils.isEmpty(mBinding.activityAnswerEt.getText().toString())) {
                ToastUtils.showShort("请输入回答内容！");
                return;
            }
            sourceUpFragment.processUpload();
        });
        mSourceUpParam = new SourceUpParam(SourceUpParam.SOURCE_TYPE_IMG_ONLY, 9);
        sourceUpFragment = CircleSourceUpFragment.newInstance(mSourceUpParam);
        FragmentUtils.add(getSupportFragmentManager(), sourceUpFragment, R.id.souce_up_frame);
        mSourceUpParam.status.addOnPropertyChangedCallback(new Observable.OnPropertyChangedCallback() {
            @Override
            public void onPropertyChanged(Observable sender, int propertyId) {
                if (mSourceUpParam.status.get() == 2) {
                    // 上传
                    realPublish();
                }
            }
        });

        mBinding.activityQuizIvRecord.setOnClickListener(v -> {
            record();
        });

        mBinding.activityQuizIvPlay.setOnClickListener(v -> {
            mp.reset();//将mp重置为创建状态

            initMP();//初始化mp，这样保证按下stop按钮后再按play按钮可以播放
            if (!mp.isPlaying()) {
                mp.start();//播放音频
            }
        });
        //
        mBinding.activityQuizTvReRecord.setOnClickListener(v -> {
            mBinding.activityQuizPb.setProgress(0);
            mBinding.activityQuizTvReRecord.setVisibility(View.GONE);
            mBinding.activityQuizIvPlay.setVisibility(View.GONE);
            mBinding.activityQuizIvPlay2.setVisibility(View.GONE);
            mBinding.activityQuizIvPause.setVisibility(View.GONE);
            mBinding.activityQuizTvTime.setText("0''");
//            mBinding.get().activityQuizTvTime.setVisibility(View.GONE);
            mBinding.activityQuizIvRecord.setVisibility(View.VISIBLE);
            audioTime = 1;
            mBinding.activityAnswerEt.setText("");
        });

        mBinding.activityQuizIvPause.setOnClickListener(v -> {
            if (mIat.isListening()) {
                isProgress = false;
                mIat.stopListening();
                objectAnimatorBottom.cancel();
                mBinding.activityQuizTvReRecord.setVisibility(View.VISIBLE);
                mBinding.activityQuizIvPause.setVisibility(View.GONE);
                mBinding.activityQuizIvPlay.setVisibility(View.VISIBLE);
                mBinding.activityQuizPb.setVisibility(View.GONE);
                mBinding.activityQuizPb.setProgress(0);
            }
        });
    }

    @Override
    public void initObserver() {

        mViewModel.mCommentVoMLiveData.observe(this, commentRstVo -> {
            finish();
        });
    }

    @Override
    public void initRouter() {

    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        sourceUpFragment.onActivityResult(requestCode, resultCode, data);
    }


    private void initIfly() {
        mIat = SpeechRecognizer.createRecognizer(this, new InitListener() {
            @Override
            public void onInit(int code) {
                if (code != ErrorCode.SUCCESS) {
                    ToastUtils.showShort("初始化失败，错误码：" + code);
                }
            }
        });
        setParam();
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

    /**
     * 听写监听器。
     */
    private RecognizerListener mRecognizerListener = new RecognizerListener() {

        @Override
        public void onBeginOfSpeech() {
            // 此回调表示：sdk内部录音机已经准备好了，用户可以开始语音输入
            LogUtils.e("========", "开始说话");
            // 当前进度
            mBinding.activityQuizPb.setMax(MAX_PROGRESS);
            mBinding.activityQuizPb.setProgress(0);

            //初始化动画
            objectAnimatorBottom = ObjectAnimator
                    .ofInt(mBinding.activityQuizPb, PROGRESS_PROPERTY, mBinding.activityQuizPb.getMax())
                    .setDuration(60000);
            objectAnimatorBottom.setInterpolator(new LinearInterpolator());
            objectAnimatorBottom.start();
            mBinding.activityQuizTvTime.setVisibility(View.VISIBLE);
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

            mBinding.activityQuizIvPlay.setVisibility(View.VISIBLE);
            mBinding.activityQuizIvRecord.setVisibility(View.GONE);
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

        mBinding.activityAnswerEt.setText(resultBuffer.toString());
        mBinding.activityAnswerEt.setSelection(mBinding.activityAnswerEt.length());

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

    private void startUpdateTimer() {
        if (progressUpdateTimer != null) {
            progressUpdateTimer.removeMessages(0);
            progressUpdateTimer.sendEmptyMessageDelayed(0, 1000);
        }
    }

    private void showVideoProgressInfo() {
        if (isProgress && audioTime <= 60) {
            mBinding.activityQuizTvTime.setVisibility(View.VISIBLE);
            mBinding.activityQuizTvTime.setText(audioTime + "''");
            audioTime++;
            startUpdateTimer();
        } else {
            isProgress = false;
            mIat.stopListening();
            mBinding.activityQuizTvReRecord.setVisibility(View.VISIBLE);
            mBinding.activityQuizIvPause.setVisibility(View.GONE);
            mBinding.activityQuizIvPlay.setVisibility(View.VISIBLE);
            mBinding.activityQuizPb.setVisibility(View.GONE);
            mBinding.activityQuizPb.setProgress(0);
        }
    }

    /**
     * 开启录音
     */
    private void record() {
        mBinding.activityAnswerEt.setText(null);
        mIatResults.clear();// 清空显示内容
        isProgress = true;
        mBinding.activityQuizIvRecord.setVisibility(View.GONE);
        mBinding.activityQuizIvPause.setVisibility(View.VISIBLE);
        mBinding.activityQuizPb.setVisibility(View.VISIBLE);
        mBinding.activityQuizTvTime.setVisibility(View.VISIBLE);
        RxPermissions rxPermissions = new RxPermissions(this);
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

    //初始化mp
    private void initMP() {
        try {
            mp = new MediaPlayer();//新建一个的实例

            mp.setDataSource(this, Uri.parse(uploadFilePath));
            mp.prepare();//播放 准备完成，开始播放前要调用
            mp.setOnPreparedListener(mp -> {
                mBinding.activityQuizIvPlay.setVisibility(View.GONE);
                mBinding.activityQuizIvPause.setVisibility(View.VISIBLE);
                mBinding.activityQuizPb.setVisibility(View.VISIBLE);
                mBinding.activityQuizTvReRecord.setVisibility(View.GONE);
                // 当前进度
                mBinding.activityQuizPb.setMax(MAX_PROGRESS);
                mBinding.activityQuizPb.setProgress(0);
                //初始化动画
                objectAnimatorBottom = ObjectAnimator
                        .ofInt(mBinding.activityQuizPb, PROGRESS_PROPERTY, mBinding.activityQuizPb.getMax())
                        .setDuration(mp.getDuration());
                objectAnimatorBottom.setInterpolator(new LinearInterpolator());
                objectAnimatorBottom.start();

                mBinding.activityQuizTvTime.setText("0''");
                audioTime = 1;
                isProgress = true;
                startUpdateTimer();//记录播放时间
            });
            mp.setOnCompletionListener(mp -> {
                isProgress = false;
                mBinding.activityQuizIvPlay.setVisibility(View.VISIBLE);
                mBinding.activityQuizIvPause.setVisibility(View.GONE);
                mBinding.activityQuizPb.setVisibility(View.GONE);
                mBinding.activityQuizPb.setProgress(0);
                mBinding.activityQuizTvReRecord.setVisibility(View.VISIBLE);

            });
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
