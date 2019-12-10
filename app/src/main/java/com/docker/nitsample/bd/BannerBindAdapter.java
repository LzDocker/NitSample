package com.docker.nitsample.bd;

import android.databinding.BindingAdapter;
import android.support.v4.view.ViewPager;

import com.docker.common.common.command.ReplyCommandParam;
import com.docker.nitsample.R;
import com.docker.nitsample.util.GlideImageLoader;
import com.docker.nitsample.vo.card.AppBannerCardVo;

import java.util.ArrayList;
import java.util.List;

public class BannerBindAdapter {
    @SuppressWarnings("unchecked")
    @BindingAdapter(value = {"banner_items", "clickCommand"}, requireAll = false)
    public static <T> void setAdapter(com.youth.banner.Banner banner, List<AppBannerCardVo.BannerVo> bannerVoList, ReplyCommandParam replyCommandParam) {
        ArrayList<String> imglist = new ArrayList<>();
        for (int i = 0; i < bannerVoList.size(); i++) {
            imglist.add(bannerVoList.get(i).img);
        }
        GlideImageLoader glideImageLoader = new GlideImageLoader();
        banner.setImageLoader(glideImageLoader);
        banner.update(imglist);
        banner.setDelayTime(2000);
        banner.setOnBannerListener(position -> {
            replyCommandParam.exectue(position);
        });
        ViewPager viewPager = banner.getRootView().findViewById(R.id.bannerViewPager);
        if (viewPager != null) {
            viewPager.setPageMargin(20);
        }
        banner.start();
    }
}
