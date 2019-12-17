package com.docker.cirlev2.util;

import android.graphics.drawable.AnimationDrawable;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import com.dcbfhd.utilcode.utils.ToastUtils;
import com.docker.cirlev2.R;
import com.docker.core.util.AppExecutors;
import com.ywl5320.wlmedia.WlMedia;
import com.ywl5320.wlmedia.enums.WlMute;
import com.ywl5320.wlmedia.enums.WlPlayModel;

public class AudioPlayerUtils {
    private WlMedia wlMedia;
    boolean ispause = false;
    AppExecutors appExecutors = new AppExecutors();
    private ImageView imageView;
    private ImageView idelView;
    private String audiourl = "-1";

    public void AudioDetailClick(String audiourl, View view) {
        if (imageView != null) {
            if (imageView.getBackground() != null) {
                ((AnimationDrawable) imageView.getBackground()).stop();
            }
            imageView.setVisibility(View.GONE);
            idelView.setVisibility(View.VISIBLE);
        }
        imageView = view.findViewById(R.id.activity_answer_iv_spinner);
        idelView = view.findViewById(R.id.activity_answer_iv_play1);
        if (!TextUtils.isEmpty((String) audiourl)) {
            if (wlMedia == null) {
                wlMedia = new WlMedia();//新建一个的实例
                wlMedia.setPlayModel(WlPlayModel.PLAYMODEL_ONLY_AUDIO);
                wlMedia.setMute(WlMute.MUTE_CENTER);//立体声
                wlMedia.setVolume(80);//80%音量
                wlMedia.setPlayPitch(1.0f);//正常速度
                wlMedia.setPlaySpeed(1.0f);//正常音调
                wlMedia.setRtspTimeOut(30);//网络流超时时间
                wlMedia.setSource(BdUtils.getAudioUrl(audiourl));
                wlMedia.prepared();//播放
                this.audiourl = audiourl;
            } else {
                if (this.audiourl.equals(audiourl)) {
                    if (!ispause) {
                        if (wlMedia.isPlaying()) {
                            wlMedia.pause();
                        } else {
                            wlMedia.setSource(BdUtils.getAudioUrl(audiourl));
                            wlMedia.prepared();//播放
                            this.audiourl = audiourl;
                        }
                    } else {
                        if (wlMedia.isPlaying()) {
                            wlMedia.resume();
                        } else {
                            wlMedia.setSource(BdUtils.getAudioUrl(audiourl));
                            wlMedia.prepared();//播放
                            this.audiourl = audiourl;
                        }
                    }
                } else {
                    wlMedia.stop();
                    wlMedia = new WlMedia();//新建一个的实例
                    wlMedia.setPlayModel(WlPlayModel.PLAYMODEL_ONLY_AUDIO);
                    wlMedia.setMute(WlMute.MUTE_CENTER);//立体声
                    wlMedia.setVolume(80);//80%音量
                    wlMedia.setPlayPitch(1.0f);//正常速度
                    wlMedia.setPlaySpeed(1.0f);//正常音调
                    wlMedia.setRtspTimeOut(30);//网络流超时时间
                    wlMedia.setSource(BdUtils.getAudioUrl(audiourl));
                    wlMedia.prepared();//播放
                    this.audiourl = audiourl;
                }
            }
            wlMedia.setOnPreparedListener(() -> {
                wlMedia.start();//开始播放
            });
            wlMedia.setOnErrorListener((code, msg) -> {
                Log.d("sss", "onError: " + msg);
                ToastUtils.showShort("找不到资源了!");
                imageView.setVisibility(View.GONE);
                idelView.setVisibility(View.VISIBLE);
            });
            wlMedia.setOnLoadListener(load -> {
                if (load) {
                    //Log.d("ywl5320", "加载中");
                    appExecutors.mainThread().execute(() -> {
                        imageView.setVisibility(View.VISIBLE);
                        idelView.setVisibility(View.GONE);
                        imageView.setBackground(view.getContext().getResources().getDrawable(R.drawable.list_spinner));
                        ((AnimationDrawable) imageView.getBackground()).start();
                    });
                } else {
                    Log.d("ywl5320", "播放中");

                    appExecutors.mainThread().execute(new Runnable() {
                        @Override
                        public void run() {
                            imageView.setVisibility(View.VISIBLE);
                            idelView.setVisibility(View.GONE);
                            imageView.setBackground(view.getContext().getResources().getDrawable(R.drawable.circlev2_audio_spinner));
                            ((AnimationDrawable) imageView.getBackground()).start();
                        }
                    });
                }
            });

            wlMedia.setOnCompleteListener(() -> {
                Log.d("ywl5320", "播放完成");
                appExecutors.mainThread().execute(() -> {
                    imageView.setVisibility(View.GONE);
                    idelView.setVisibility(View.VISIBLE);
                });
            });

            wlMedia.setOnPauseListener(pause -> {
                ispause = pause;
                if (pause) {
                    Log.d("ywl5320", "暂停中");
                    appExecutors.mainThread().execute(() -> {
                        imageView.setVisibility(View.GONE);
                        idelView.setVisibility(View.VISIBLE);
                    });
                } else {
                    Log.d("ywl5320", "继续播放");
                    appExecutors.mainThread().execute(() -> {
                        idelView.setVisibility(View.GONE);
                        imageView.setVisibility(View.VISIBLE);
                        imageView.setBackground(view.getContext().getResources().getDrawable(R.drawable.circlev2_audio_spinner));
                        ((AnimationDrawable) imageView.getBackground()).start();
                    });
                }
            });

        } else {
            ToastUtils.showShort("找不到资源了!");
        }
    }


    public void ondestory() {
        if (wlMedia != null) {
            wlMedia.onDestroy();
        }
    }
}
