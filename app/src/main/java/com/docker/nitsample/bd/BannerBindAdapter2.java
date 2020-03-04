package com.docker.nitsample.bd;

import android.databinding.BindingAdapter;
import android.support.v4.view.ViewPager;

import com.docker.common.common.command.ReplyCommandParam;
import com.docker.nitsample.util.GlideImageLoader;
import com.docker.nitsample.vo.OptimizationVo;
import com.docker.nitsample.vo.card.AppBannerCardVo;

import java.util.ArrayList;
import java.util.List;

public class BannerBindAdapter2 {
    @SuppressWarnings("unchecked")
    @BindingAdapter(value = {"banner_items", "banne_click_command"}, requireAll = false)
    public static <T> void setAdapter(com.youth.banner.Banner banner, List<OptimizationVo.BannerVo> bannerVoList, ReplyCommandParam replyCommandParam) {
        ArrayList<String> imglist = new ArrayList<>();
        for (int i = 0; i < bannerVoList.size(); i++) {
            imglist.add(bannerVoList.get(i).img);
        }
        com.docker.cirlev2.util.GlideImageLoader glideImageLoader = new com.docker.cirlev2.util.GlideImageLoader();
        banner.setImageLoader(glideImageLoader);
        banner.update(imglist);
        banner.setDelayTime(2000);
        banner.setOnBannerListener(position -> {
            replyCommandParam.exectue(position);
        });
        banner.start();
    }
}
