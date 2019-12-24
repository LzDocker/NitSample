package com.docker.cirlev2.bd;
import android.databinding.BindingAdapter;
import com.docker.cirlev2.util.GlideImageLoader;
import com.docker.cirlev2.vo.card.AppBannerHeaderCardVo;
import com.docker.common.common.command.ReplyCommandParam;

import java.util.ArrayList;
import java.util.List;

public class BannerHeadBindAdapter {
    @SuppressWarnings("unchecked")
    @BindingAdapter(value = {"banner_head_items", "banne_click_command"}, requireAll = false)
    public static <T> void setAdapter(com.youth.banner.Banner banner, List<AppBannerHeaderCardVo.BannerVo> bannerVoList, ReplyCommandParam replyCommandParam) {
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
        banner.start();
    }


}
