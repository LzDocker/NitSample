package com.docker.common.common.binding;

import android.content.Context;
import android.databinding.BindingAdapter;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.widget.ImageView;

import com.alibaba.android.arouter.utils.TextUtils;
import com.bumptech.glide.Glide;
import com.bumptech.glide.Priority;
import com.bumptech.glide.load.Transformation;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.engine.Resource;
import com.bumptech.glide.load.engine.bitmap_recycle.BitmapPool;
import com.bumptech.glide.load.resource.bitmap.BitmapTransformation;
import com.bumptech.glide.load.resource.bitmap.CircleCrop;
import com.bumptech.glide.request.RequestOptions;
import com.bumptech.glide.request.target.SimpleTarget;
import com.bumptech.glide.request.transition.Transition;
import com.dcbfhd.utilcode.utils.ArrayUtils;
import com.dcbfhd.utilcode.utils.FileUtils;
import com.dcbfhd.utilcode.utils.ScreenUtils;
import com.docker.common.R;
import com.docker.common.common.config.GlideApp;
import com.docker.common.common.utils.BitmapCut;
import com.docker.common.common.utils.GaussinaBlurUtil;

import java.io.File;
import java.security.MessageDigest;

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
                .centerCrop();
        GlideApp.with(imageView)
                .applyDefaultRequestOptions(options)
                .load(url).placeholder(null).transition(withCrossFade()).into(imageView);

    }

    /*
     * 圆形图片
     * */
    @BindingAdapter(value = {"roundImgUrl", "placeHolder", "errorImg"}, requireAll = false)
    public static void loadRoundimage(ImageView imageView, String url, int resHolderId, int resErrId) {
        options.skipMemoryCache(true).diskCacheStrategy(DiskCacheStrategy.ALL)
                .centerCrop();
        GlideApp.with(imageView).applyDefaultRequestOptions(options).load(url).placeholder(null).transition(withCrossFade()).into(imageView);
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
                .bitmapTransform(new CircleCrop())
                .centerCrop();
        GlideApp.with(imageView).applyDefaultRequestOptions(options).load(url).placeholder(null).transition(withCrossFade()).into(imageView);

    }

    /*
     *
     * */
    @BindingAdapter(value = {"imageUrl"}, requireAll = false)
    public static void loadcirlceimage(ImageView imageView, String url) {
        url = CommonBdUtils.getImgUrl(url);
        options.diskCacheStrategy(DiskCacheStrategy.ALL)
                .priority(Priority.HIGH)
                .centerCrop()
                .transform(new BitmapTransformation() {
                    @Override
                    protected Bitmap transform(@NonNull BitmapPool pool, @NonNull Bitmap toTransform, int outWidth, int outHeight) {
                        Bitmap endbitmap = null;
                        //调用裁剪图片工具类进行裁剪
                        endbitmap = BitmapCut.cutBitmap(toTransform);
                        return endbitmap;
                    }

                    @Override
                    public void updateDiskCacheKey(@NonNull MessageDigest messageDigest) {

                    }
                });
        GlideApp.with(imageView).applyDefaultRequestOptions(options).load(url).placeholder(null).transition(withCrossFade()).into(imageView);

    }


    /*
     *
     * */
    @BindingAdapter(value = {"imageNomUrl"}, requireAll = false)
    public static void loadceimage(ImageView imageView, String url) {
        options.skipMemoryCache(true).diskCacheStrategy(DiskCacheStrategy.ALL);
        GlideApp.with(imageView).applyDefaultRequestOptions(options).load(url).placeholder(null).transition(withCrossFade()).into(imageView);

    }


    /*
     *
     * */
    @BindingAdapter(value = {"avaterImageUrl"}, requireAll = false)
    public static void loadavaterimage(ImageView imageView, String url) {
        options.skipMemoryCache(true).diskCacheStrategy(DiskCacheStrategy.ALL)
                .centerCrop();
        GlideApp.with(imageView).applyDefaultRequestOptions(options).load(url).placeholder(R.mipmap.common_default_avatar).transition(withCrossFade()).into(imageView);

    }

    /*
     *
     * */
    @BindingAdapter(value = {"imageLocalUrl"}, requireAll = false)
    public static void loadlocalimage(ImageView imageView, String url) {
        options.dontTransform();
        GlideApp.with(imageView).applyDefaultRequestOptions(options).load(Uri.fromFile(new File(url))).transition(withCrossFade()).into(imageView);

    }

    /*
     * */
    @BindingAdapter(value = {"dontTransImg"}, requireAll = false)
    public static void donttransImg(ImageView imageView, String url) {
        options.diskCacheStrategy(DiskCacheStrategy.ALL);
        GlideApp.with(imageView).applyDefaultRequestOptions(options).load(url).placeholder(null).dontTransform().transition(withCrossFade()).into(imageView);

    }


    @BindingAdapter(value = {"rvImgUrl"}, requireAll = false)
    public static void loadrvimage(ImageView imageView, String url) {
        options.diskCacheStrategy(DiskCacheStrategy.ALL);
        GlideApp.with(imageView)
                .applyDefaultRequestOptions(options)
                .load(url).transition(withCrossFade()).placeholder(null).into(imageView);

    }


    @BindingAdapter(value = {"BlurImgUrl"}, requireAll = false)
    public static void
    BlurImgUrl(ImageView imageView, String url) {
        url = CommonBdUtils.getImgUrl("/static/var/upload/image/2019/12/2019122409334512287_750x297.png");
//        url = CommonBdUtils.getImgUrl(url);
        options.diskCacheStrategy(DiskCacheStrategy.ALL)
                .priority(Priority.HIGH)
                .transform(new BitmapTransformation() {
                    @Override
                    protected Bitmap transform(@NonNull BitmapPool pool, @NonNull Bitmap toTransform, int outWidth, int outHeight) {
                        Bitmap endbitmap = null;
                        //调用裁剪图片工具类进行裁剪
                        endbitmap = GaussinaBlurUtil.GaussianBlur(imageView.getContext(), toTransform, 10);

                        return endbitmap;
                    }

                    @Override
                    public void updateDiskCacheKey(@NonNull MessageDigest messageDigest) {

                    }
                });
        GlideApp.with(imageView).applyDefaultRequestOptions(options).load(url).placeholder(com.docker.common.R.mipmap.common_persion_head).transition(withCrossFade()).into(imageView);


    }


}
