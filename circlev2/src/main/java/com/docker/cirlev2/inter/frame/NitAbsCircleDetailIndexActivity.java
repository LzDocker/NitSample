package com.docker.cirlev2.inter.frame;

import android.arch.lifecycle.ViewModelProviders;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.android.arouter.launcher.ARouter;
import com.dcbfhd.utilcode.utils.FragmentUtils;
import com.docker.cirlev2.R;
import com.docker.cirlev2.databinding.Circlev2NitAbsDetailIndexActivityBinding;
import com.docker.cirlev2.inter.CircleConfig;
import com.docker.cirlev2.inter.frame.example.NitDefaultCircleFragment;
import com.docker.common.common.router.AppRouter;
import com.docker.common.common.ui.base.NitCommonActivity;
import com.docker.common.common.vm.NitEmptyViewModel;

/*
 * 圈子详情抽象
 * */
@Route(path = AppRouter.CIRCLE_DETAIL_v2_INDEX_NEW_default)
public class NitAbsCircleDetailIndexActivity extends NitCommonActivity<NitEmptyViewModel, Circlev2NitAbsDetailIndexActivityBinding> {

    public String utid;

    /*
     * 圈子id
     * */
    public String circleid;

    /*
     * 圈子类型
     * */
    public String circleType;


    public Fragment fragment;


    public CircleConfig circleConfig;

    public int temp = 0;

    @Override
    protected int getLayoutId() {
        return R.layout.circlev2_nit_abs_detail_index_activity;
    }

    @Override
    public NitEmptyViewModel getmViewModel() {
        return ViewModelProviders.of(this, factory).get(NitEmptyViewModel.class);
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        circleConfig = (CircleConfig) getIntent().getSerializableExtra("circleConfig");
        if (circleConfig == null) {
            utid = getIntent().getStringExtra("utid");
            circleid = getIntent().getStringExtra("circleid");
            circleType = getIntent().getStringExtra("circletype");
            circleConfig = new CircleConfig();
            circleConfig.utid = utid;
            circleConfig.circleid = circleid;
            circleConfig.circleType = circleType;

        } else {
            utid = circleConfig.utid;
            circleid = circleConfig.circleid;
            circleType = circleConfig.circleType;
        }

        switch (circleType) {
            case "0":// 官方圈0
            case "1":// 兴趣圈1
            case "2":// 企业圈2
                fragment = NitDefaultCircleFragment.getInstance(circleConfig);
                break;
            default:
                fragment = NitDefaultCircleFragment.getInstance(circleConfig);
                break;
        }
        FragmentUtils.add(getSupportFragmentManager(), fragment, R.id.frame_circle_detail);

//        官方圈0 兴趣圈1 企业圈|2
//        fragment = NitDefaultCircleFragment.getInstance(circleid, utid, circleType);
    }

    @Override
    public void initView() {
        mToolbar.hide();

    }

    @Override
    public void initObserver() {

    }

    @Override
    public void initRouter() {
        ARouter.getInstance().inject(this);
    }

    @Override
    public void initImmersionBar() {

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

}

