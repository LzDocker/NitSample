package com.docker.common.common.binding;

import android.databinding.BindingAdapter;
import android.graphics.Bitmap;
import android.widget.ImageView;

import com.alibaba.android.arouter.utils.TextUtils;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.resource.bitmap.CircleCrop;
import com.bumptech.glide.request.RequestOptions;
import com.dcbfhd.utilcode.utils.ArrayUtils;
import com.dcbfhd.utilcode.utils.FileUtils;
import com.dcbfhd.utilcode.utils.ScreenUtils;
import com.docker.common.common.config.GlideApp;

import static com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions.withCrossFade;

/**
 * Created by zhangxindang on 2018/12/18.
 */

public class ImgBindingAdapter {

    private static RequestOptions options = new RequestOptions();

    @BindingAdapter("android:src")
    public static void setSrc(ImageView view, Bitmap bitmap) {
        view.setImageBitmap(bitmap);
    }

    @BindingAdapter("android:src")
    public static void setSrc(ImageView view, int resId) {
        view.setImageResource(resId);
    }

    @BindingAdapter(value = {"ImgUrl", "placeHolder", "errorImg"}, requireAll = false)
    public static void loadimage(ImageView imageView, String url, int resHolderId, int resErrId) {
        options.skipMemoryCache(true).diskCacheStrategy(DiskCacheStrategy.ALL)
                .placeholder(resHolderId)
                .error(resErrId)
                .centerCrop();
        GlideApp.with(imageView).applyDefaultRequestOptions(options).load(url).transition(withCrossFade()).into(imageView);
    }

    /*
     * 圆形图片
     * */
    @BindingAdapter(value = {"roundImgUrl", "placeHolder", "errorImg"}, requireAll = false)
    public static void loadRoundimage(ImageView imageView, String url, int resHolderId, int resErrId) {
        options.skipMemoryCache(true).diskCacheStrategy(DiskCacheStrategy.ALL)
                .placeholder(resHolderId)
                .error(resErrId)
                .centerCrop();
        GlideApp.with(imageView).applyDefaultRequestOptions(options).load(url).transition(withCrossFade()).into(imageView);
    }

    /*
     * 圆形图片
     * */
    @BindingAdapter(value = {"gifImgUrl", "placeHolder", "errorImg"}, requireAll = false)
    public static void loadSrcimage(ImageView imageView, int url, int resHolderId, int resErrId) {
        options.diskCacheStrategy(DiskCacheStrategy.RESOURCE).fitCenter();
        GlideApp.with(imageView).asGif().load(url).apply(options).into(imageView);
    }

    /*
     * auto 图片
     * */
    @BindingAdapter(value = {"autoSizeImgUrl", "placeHolder", "errorImg"}, requireAll = false)
    public static void loadAutoSizeimage(ImageView imageView, String url, int resHolderId, int resErrId) {
        options.skipMemoryCache(true).diskCacheStrategy(DiskCacheStrategy.ALL)
                .placeholder(resHolderId)
                .error(resErrId);
        int with = 0;
        int height = 0;
        if (!TextUtils.isEmpty(url) && url.contains("_")) {
            String szie = url.split("_")[1];
            if (!TextUtils.isEmpty(szie)) {
                //720x1280.png
                String wh = FileUtils.getFileNameNoExtension(szie);
                if (!TextUtils.isEmpty(wh)) {
                    String[] whstr = wh.split("x");
                    if (!ArrayUtils.isEmpty(whstr) && whstr.length == 2) {
//                        with = Integer.parseInt(whstr[0]);
//                        height = Integer.parseInt(whstr[1]);
//
                        with = ScreenUtils.getAppScreenWidth();
                        height = with / ScreenUtils.getAppScreenWidth() * height;
                        options.override(with, height);
                    }
                }
            }
        }
        GlideApp.with(imageView).applyDefaultRequestOptions(options).load(url).transition(withCrossFade()).into(imageView);
    }


//    /*
//     * 圆形图片
//     * */
//    @BindingAdapter(value = {"autoImgUrl", "placeHolder", "errorImg"}, requireAll = false)
//    public static void loadAutoimage(ImageView imageView, String url, int resHolderId, int resErrId) {
//        options.skipMemoryCache(true).diskCacheStrategy(DiskCacheStrategy.ALL)
//                .placeholder(resHolderId)
//                .error(resErrId)
//                .centerCrop().dontAnimate();
//
//        if (imageView.getScaleType() != ImageView.ScaleType.FIT_XY) {
//            imageView.setScaleType(ImageView.ScaleType.FIT_XY);
//        }
//        ViewGroup.LayoutParams params = imageView.getLayoutParams();
//        int vw = imageView.getWidth() - imageView.getPaddingLeft() - imageView.getPaddingRight();
//        //float scale = (float) vw / (float) resource.getIntrinsicWidth();
//        int vh = (int) ((float) vw / (float) 1.78);
//        params.height = vh + imageView.getPaddingTop() + imageView.getPaddingBottom();
//        imageView.setLayoutParams(params);
//        Glide.with(imageView.getContext()).load(url).apply(options).into(imageView);
//    }


    /*
     * 圆形图片
     * */
    @BindingAdapter(value = {"openImgUrl", "placeHolder", "errorImg"}, requireAll = false)
    public static void loadcirlceRoundimage(ImageView imageView, String url, int resHolderId, int resErrId) {
        options.skipMemoryCache(true).diskCacheStrategy(DiskCacheStrategy.ALL)
                .placeholder(resHolderId)
                .error(resErrId)
                .bitmapTransform(new CircleCrop())
                .centerCrop();
        GlideApp.with(imageView).applyDefaultRequestOptions(options).load(url).transition(withCrossFade()).into(imageView);

    }

    /*
     * 圆形图片
     * */
    @BindingAdapter(value = {"imageUrl"}, requireAll = false)
    public static void loadcirlceimage(ImageView imageView, String url) {
        options.skipMemoryCache(true).diskCacheStrategy(DiskCacheStrategy.ALL)
                .centerCrop();
        GlideApp.with(imageView).applyDefaultRequestOptions(options).load(url).transition(withCrossFade()).into(imageView);

    }

    /*
     * 圆形图片
     * */
    @BindingAdapter(value = {"dontTransImg"}, requireAll = false)
    public static void donttransImg(ImageView imageView, String url) {
        options.diskCacheStrategy(DiskCacheStrategy.ALL).dontTransform();
        GlideApp.with(imageView).applyDefaultRequestOptions(options).load(url).into(imageView);
        /*.transition(withCrossFade())*/

    }


}
