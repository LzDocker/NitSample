package com.docker.apps.active.vo.card;

import android.databinding.BindingAdapter;
import android.support.v4.view.ViewPager;

import com.docker.common.common.command.ReplyCommandParam;

import java.util.ArrayList;
import java.util.List;

public class BannerBindAdapter3 {

    @SuppressWarnings("unchecked")
    @BindingAdapter(value = {"banner_items", "banne_click_command"}, requireAll = false)
    public static <T> void setAdapter(com.youth.banner.Banner banner,
                                      List<String> bannerVoList,
                                      ReplyCommandParam replyCommandParam
    ) {

        if (bannerVoList == null || bannerVoList.size() == 0) {
            return;
        }
        ArrayList<String> imglist = new ArrayList<>();
        for (int i = 0; i < bannerVoList.size(); i++) {
            imglist.add(bannerVoList.get(i));
        }
        com.docker.cirlev2.util.GlideImageLoaderv2 glideImageLoader = new com.docker.cirlev2.util.GlideImageLoaderv2();
        banner.setImageLoader(glideImageLoader);
        banner.update(imglist);
        banner.setDelayTime(2000);
        replyCommandParam.exectue("1/" + bannerVoList.size());
        banner.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int i, float v, int i1) {

            }

            @Override
            public void onPageSelected(int i) {
                replyCommandParam.exectue(i + 1 + "/" + bannerVoList.size());
            }

            @Override
            public void onPageScrollStateChanged(int i) {

            }
        });
        banner.start();
    }
}
