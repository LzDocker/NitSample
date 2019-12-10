package com.docker.nitsample.util;

import android.content.Context;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.CenterCrop;
import com.bumptech.glide.load.resource.bitmap.RoundedCorners;
import com.bumptech.glide.request.RequestOptions;
import com.docker.common.common.binding.CommonBdUtils;
import com.youth.banner.loader.ImageLoader;

public class GlideImageLoader extends ImageLoader {
    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
    @Override
    public void displayImage(Context context, Object path, ImageView imageView) {
        imageView.setCropToPadding(true);
        RequestOptions options = new RequestOptions();
        options.transforms(new CenterCrop(), new RoundedCorners(18));
        Glide.with(context)
                .load(CommonBdUtils.getCompleteImageUrl((String) path))
                .apply(options)
                .into(imageView);
    }
}