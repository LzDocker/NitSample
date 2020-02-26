package com.docker.cirlev2.bd;

import android.databinding.BindingAdapter;
import android.text.TextUtils;

import com.docker.cirlev2.util.BdUtils;

import java.util.ArrayList;

public class BannerBindAdapter7 {
    @SuppressWarnings("unchecked")
    @BindingAdapter(value = {"banner_items"}, requireAll = false)
    public static <T> void setAdapter(com.youth.banner.Banner banner, String bannerVoList) {
        if (TextUtils.isEmpty(bannerVoList)) {
            return;
        }
        ArrayList<String> imglist = new ArrayList<>();
        if (bannerVoList.contains(",")) {
            String[] strarr = bannerVoList.split(",");
            for (int i = 0; i < strarr.length; i++) {
                imglist.add(BdUtils.getCompleteImg(strarr[i]));
            }
        }else {
            imglist.add(BdUtils.getCompleteImg(bannerVoList));
        }
        com.docker.cirlev2.util.GlideImageLoaderv2 glideImageLoader = new com.docker.cirlev2.util.GlideImageLoaderv2();
        banner.setImageLoader(glideImageLoader);
        banner.update(imglist);
        banner.setDelayTime(2000);
        banner.start();
    }
}
