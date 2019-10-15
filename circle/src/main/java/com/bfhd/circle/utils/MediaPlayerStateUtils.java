package com.bfhd.circle.utils;

import android.graphics.drawable.AnimationDrawable;
import android.media.MediaPlayer;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;

import com.bfhd.circle.R;

public class MediaPlayerStateUtils {


    public static void processState(MediaPlayer mp, View view) {
        if (mp == null) {
            return;
        }
        ImageView imageView = view.findViewById(R.id.activity_answer_iv_spinner);
        ImageView idelView = view.findViewById(R.id.activity_answer_iv_play1);
        if (!mp.isPlaying()) {
            imageView.setVisibility(View.GONE);
            idelView.setVisibility(View.VISIBLE);
            return;
        }

        imageView.setVisibility(View.VISIBLE);
        idelView.setVisibility(View.GONE);

        mp.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
            @Override
            public void onPrepared(MediaPlayer mp) {
                // 准备就绪
                imageView.setBackground(view.getContext().getResources().getDrawable(R.drawable.circle_audio_spinner));
                ((AnimationDrawable) imageView.getBackground()).start();
            }
        });

        mp.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            @Override
            public void onCompletion(MediaPlayer mp) {
                // 播放完成
                imageView.setVisibility(View.GONE);
                idelView.setVisibility(View.VISIBLE);
            }
        });

        mp.setOnErrorListener(new MediaPlayer.OnErrorListener() {
            @Override
            public boolean onError(MediaPlayer mp, int what, int extra) {
                imageView.setVisibility(View.GONE);
                idelView.setVisibility(View.VISIBLE);
                return false;
            }
        });

        mp.setOnBufferingUpdateListener(new MediaPlayer.OnBufferingUpdateListener() {
            @Override
            public void onBufferingUpdate(MediaPlayer mediaPlayer, int i) {
                // i 0~100
                Log.d("Progress:", "缓存进度" + i + "%");
                imageView.setBackground(view.getContext().getResources().getDrawable(R.drawable.circle_audio_spinner));
                ((AnimationDrawable) imageView.getBackground()).start();
            }
        });
    }




}
