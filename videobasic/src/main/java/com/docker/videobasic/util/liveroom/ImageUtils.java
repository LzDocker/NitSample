package com.docker.videobasic.util.liveroom;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.widget.ImageView;

import com.bumptech.glide.Glide;

/**
 * Created by Akira on 2018/5/29.
 */

public class ImageUtils {

    //加载普通图片
    @SuppressLint("NewApi")
    public static void loadImage(Context context, String url, ImageView imageView) {
        if (context != null) {
            if (context instanceof Activity && ((Activity) context).isDestroyed()) {
                return;
            }
            url = filterUrl(url);
            Glide.with(context).load(url).into(imageView);
        }
    }


    //加载圆形
    @SuppressLint("NewApi")
    public static void loadCircleImage(Context context, String url, ImageView imageView) {
        if (context != null) {
            if (context instanceof Activity && ((Activity) context).isDestroyed()) {
                return;
            }
            url = filterUrl(url);
            Glide.with(context).load(url).into(imageView);
        }
    }

    //加载圆角
    @SuppressLint("NewApi")
    public static void loadRoundImage(Context context, String url, int round, ImageView imageView) {
        if (context != null) {
            if (context instanceof Activity && ((Activity) context).isDestroyed()) {
                return;
            }
            url = filterUrl(url);
            Glide.with(context).load(url).into(imageView);
        }
    }

    private static String filterUrl(String url) {
        if (url != null && url.contains("http:") || url != null && url.contains("https:")) {
            return url;
        } else {
            return "";
        }
    }
}
