package com.docker.common.common.ui;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.widget.Button;
import android.widget.SeekBar;
import android.widget.TextView;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.dcbfhd.utilcode.utils.ToastUtils;
import com.docker.common.R;
import com.docker.common.common.router.AppRouter;
import com.ywl5320.wlmedia.WlMedia;
import com.ywl5320.wlmedia.enums.WlPlayModel;
import com.ywl5320.wlmedia.util.WlTimeUtil;

import java.lang.ref.WeakReference;

/**
 * kxf -> 2019-10-17
 **/

@Route(path = AppRouter.AUDIO_DEMO)
public class AudioDemoActivity extends AppCompatActivity {

    private WlMedia folloWlMedia;
    private double allDuration = 0;
    private SeekBar seekBar;
    private TextView textTime;
    private Button pauseBtn, resumeBtn, stopBtn, s1Btn, s2Btn, s3Btn;
    private double seekT;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.common_activity_audio_demo);
        seekBar = findViewById(R.id.audio_demo_timebar);
        textTime = findViewById(R.id.audio_demo_time);
        pauseBtn = findViewById(R.id.audio_demo_pause);
        stopBtn = findViewById(R.id.audio_demo_stop);
        resumeBtn = findViewById(R.id.audio_demo_resume);
        s1Btn = findViewById(R.id.audio_demo_s1);
        s2Btn = findViewById(R.id.audio_demo_s2);
        s3Btn = findViewById(R.id.audio_demo_s3);
        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
                seekT = (seekBar.getProgress() * allDuration / 100);
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
                folloWlMedia.seekStart(false);
            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                folloWlMedia.seek(seekT);
            }
        });
        pauseBtn.setOnClickListener(v -> {
            if (folloWlMedia != null)
                folloWlMedia.pause();
        });

        stopBtn.setOnClickListener(v -> {
            if (folloWlMedia != null)
                folloWlMedia.stop();
        });

        resumeBtn.setOnClickListener(v -> {
            if (folloWlMedia != null)
                folloWlMedia.resume();
        });

        s1Btn.setOnClickListener(v -> {
            if (folloWlMedia != null)
                folloWlMedia.setPlaySpeed(0.8f);
        });

        s2Btn.setOnClickListener(v -> {
            if (folloWlMedia != null)
                folloWlMedia.setPlaySpeed(1.0f);
        });

        s3Btn.setOnClickListener(v -> {
            if (folloWlMedia != null)
                folloWlMedia.setPlaySpeed(1.2f);
        });
        lessonAudioDetailHandler = new LessonAudioDetailHandler(this);
        initFolloWlMedia();


    }

    public LessonAudioDetailHandler lessonAudioDetailHandler;

    //初始化列表播放器
    public void initFolloWlMedia() {
        folloWlMedia = new WlMedia();
        folloWlMedia.setPlayModel(WlPlayModel.PLAYMODEL_ONLY_AUDIO);
        folloWlMedia.setVolume(100);//音量
        folloWlMedia.setPlayPitch(1.0f);
        folloWlMedia.setPlaySpeed(1.0f);//速度
        folloWlMedia.setRtspTimeOut(30);
        folloWlMedia.setOnPauseListener(pause -> {//播放状态监听
            Message message = Message.obtain();
            message.arg1 = pause ? 0 : 1;
            message.what = 0x12;
            lessonAudioDetailHandler.sendMessage(message);
        });
        folloWlMedia.setOnErrorListener((i, s) -> {
            ToastUtils.showShort(String.format("错误code%s,错误信息:%s", i, s));

        });
        folloWlMedia.setOnTimeInfoListener((timeBean) -> {//播放进度监听
            Double d = (timeBean * 100 / allDuration);
            Message message = Message.obtain();
            message.arg1 = d.intValue();
            message.what = 0x11;
            lessonAudioDetailHandler.sendMessage(message);

        });
        folloWlMedia.setOnPreparedListener(() -> {//初始化完成
            folloWlMedia.start();
            allDuration = folloWlMedia.getDuration();
            Message message = Message.obtain();
            message.arg1 = 1;
            message.what = 0x12;
            lessonAudioDetailHandler.sendMessage(message);

        });
        folloWlMedia.setOnCompleteListener(() -> {
            ToastUtils.showShort("播放完成");
        });
        folloWlMedia.setSource("https://chaoyangtiyuju.oss-cn-beijing.aliyuncs.com/20190626/A/test1/player.mp3");
        folloWlMedia.prepared();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        folloWlMedia.onDestroy();
    }

    public static class LessonAudioDetailHandler extends Handler {
        private WeakReference<AudioDemoActivity> studyReadActivityWeakReference;

        public LessonAudioDetailHandler(AudioDemoActivity activityWeakReference) {
            this.studyReadActivityWeakReference = new WeakReference<>(activityWeakReference);
        }

        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case 0x11:
                    studyReadActivityWeakReference.get().seekBar.setProgress(msg.arg1);
                    studyReadActivityWeakReference.get().textTime.setText(WlTimeUtil.secdsToDateFormat(msg.arg1) + "/" + WlTimeUtil.secdsToDateFormat((int) studyReadActivityWeakReference.get().allDuration));
                    break;

            }
        }
    }
}
