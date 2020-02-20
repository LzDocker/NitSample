package com.docker.apps.active.ui.publish;

import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.view.ViewPager;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.android.arouter.launcher.ARouter;
import com.docker.apps.R;
import com.docker.apps.active.widget.CardActivePopup;
import com.docker.apps.databinding.ProActiveBannerPreviewActivityBinding;
import com.docker.apps.databinding.ProActiveSuccActivityBinding;
import com.docker.cirlev2.util.GlideImageLoader;
import com.docker.common.common.router.AppRouter;
import com.docker.common.common.ui.base.NitCommonActivity;
import com.docker.common.common.vm.NitEmptyViewModel;
import com.lxj.xpopup.XPopup;
import com.lxj.xpopup.core.BasePopupView;
import com.lxj.xpopup.interfaces.XPopupCallback;
import com.youth.banner.BannerConfig;

import java.util.ArrayList;

@Route(path = AppRouter.ACTIVE_PUBLISH_BANNER_PREVIEW)
public class ActiveBannerPreviewActivity extends NitCommonActivity<NitEmptyViewModel, ProActiveBannerPreviewActivityBinding> {


    private ArrayList<String> imgList = new ArrayList<>();
    private int position;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        imgList = (ArrayList<String>) getIntent().getSerializableExtra("imgList");
        position = getIntent().getIntExtra("position", 0);
        mBinding.tvPosition.setText(position + 1 + "/" + imgList.size());

        initBanner();
    }

    private void initBanner() {

        GlideImageLoader glideImageLoader = new GlideImageLoader();
        mBinding.preBenner.setBannerStyle(BannerConfig.NOT_INDICATOR);
        mBinding.preBenner.setImageLoader(glideImageLoader);
        mBinding.preBenner.update(imgList);
        mBinding.preBenner.isAutoPlay(false);
        mBinding.preBenner.start();
        mBinding.preBenner.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int i, float v, int i1) {

            }

            @Override
            public void onPageSelected(int i) {
                position = i;
                mBinding.tvPosition.setText((i + 1) + "/" + imgList.size());
            }

            @Override
            public void onPageScrollStateChanged(int i) {

            }
        });

        mBinding.tvDel.setOnClickListener(v -> {
            if (imgList.size() > 0) {
                imgList.remove(position);
                mBinding.preBenner.update(imgList);
            }
        });
    }

    @Override
    public void initView() {
        mToolbar.hide();
        mBinding.ivBack.setOnClickListener(v -> finish());
        mBinding.tvComplete.setOnClickListener(v -> {
            Intent intent = new Intent();
            intent.putExtra("imgList", imgList);
            setResult(RESULT_OK, intent);
            finish();
        });
    }

    @Override
    public void initObserver() {

    }

    @Override
    public void initRouter() {
        ARouter.getInstance().inject(this);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.pro_active_banner_preview_activity;
    }

    @Override
    public NitEmptyViewModel getmViewModel() {
        return ViewModelProviders.of(this, factory).get(NitEmptyViewModel.class);
    }

}

