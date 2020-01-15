package com.docker.nitsample.bd;

import android.databinding.BindingAdapter;
import android.databinding.ObservableList;
import android.support.v4.view.ViewPager;
import android.view.ViewGroup;

import com.docker.common.common.binding.CommonBdUtils;
import com.docker.common.common.command.ReplyCommandParam;
import com.docker.nitsample.R;
import com.docker.nitsample.util.GlideImageLoader;
import com.docker.nitsample.vo.BannerEntityVo;
import com.docker.nitsample.vo.OptimizationVo;
import com.docker.nitsample.vo.card.AppBannerCardVo;

import java.util.ArrayList;
import java.util.List;

public class BannerBindAdapter {
    @SuppressWarnings("unchecked")
    @BindingAdapter(value = {"banner_items", "clickCommand"}, requireAll = false)
    public static <T> void setAdapter(com.youth.banner.Banner banner, ObservableList<BannerEntityVo> bannerVoList, ReplyCommandParam replyCommandParam) {
        if (bannerVoList.size() > 0) {
            ArrayList<String> imglist = new ArrayList<>();
            for (int i = 0; i < bannerVoList.size(); i++) {
                imglist.add(CommonBdUtils.getCompleteImageUrl(bannerVoList.get(i).imgurl));
            }
            GlideImageLoader glideImageLoader = new GlideImageLoader();
            banner.setImageLoader(glideImageLoader);
            banner.update(imglist);
            banner.setDelayTime(2000);
            banner.setOnBannerListener(position -> {
                replyCommandParam.exectue(bannerVoList.get(position));
            });
            ViewPager viewPager = banner.getRootView().findViewById(R.id.bannerViewPager);
            if (viewPager != null) {
                viewPager.setPageMargin(80);
            }
            banner.start();
        }
    }
}
