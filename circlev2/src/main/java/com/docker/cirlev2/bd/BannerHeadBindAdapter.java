package com.docker.cirlev2.bd;

import android.databinding.BindingAdapter;
import android.support.v4.view.ViewPager;
import android.view.ViewGroup;

import com.docker.cirlev2.R;
import com.docker.cirlev2.util.GlideImageLoader;
import com.docker.cirlev2.vo.card.AppBannerHeaderCardVo;
import com.docker.common.common.command.ReplyCommandParam;
import com.youth.banner.Transformer;
import com.youth.banner.view.BannerViewPager;

import java.util.ArrayList;
import java.util.List;

public class BannerHeadBindAdapter {
    @SuppressWarnings("unchecked")
    @BindingAdapter(value = {"banner_head_items", "banne_click_command"}, requireAll = false)
    public static <T> void setAdapter(com.youth.banner.Banner banner, List<AppBannerHeaderCardVo.BannerVo> bannerVoList, ReplyCommandParam replyCommandParam) {

        ViewPager viewPager = banner.findViewById(R.id.bannerViewPager);
        viewPager.setPageMargin(32);
//        banner.setBannerAnimation(Transformer.ScaleInOut);
//        banner.setBannerAnimation(Transformer.BackgroundToForeground);
//        banner.setBannerAnimation(Transformer.FlipVertical);

        ArrayList<String> imglist = new ArrayList<>();
        for (int i = 0; i < bannerVoList.size(); i++) {
            imglist.add(bannerVoList.get(i).img);
        }
        GlideImageLoader glideImageLoader = new GlideImageLoader();
        banner.setImageLoader(glideImageLoader);
        banner.update(imglist);
        banner.setDelayTime(3000);
        banner.setOnBannerListener(position -> {
            replyCommandParam.exectue(position);
        });
        banner.start();
    }


}
