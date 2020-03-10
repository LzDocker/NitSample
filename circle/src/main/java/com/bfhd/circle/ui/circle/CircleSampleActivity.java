package com.bfhd.circle.ui.circle;

import android.arch.lifecycle.ViewModelProvider;
import android.arch.lifecycle.ViewModelProviders;
import android.os.Bundle;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.bfhd.circle.R;
import com.bfhd.circle.base.EmptyVm;
import com.bfhd.circle.base.HivsBaseActivity;
import com.bfhd.circle.databinding.CircleActivityCircleListBinding;
import com.bfhd.circle.databinding.CircleStickTestBinding;
import com.bfhd.circle.vo.bean.StaCircleListParam;
import com.dcbfhd.utilcode.utils.FragmentUtils;
import com.docker.common.common.router.AppRouter;

import javax.inject.Inject;

/*
 * 圈子列表  我的圈子
 * */
@Route(path = AppRouter.CIRCLE_SAMPLE)
public class CircleSampleActivity extends HivsBaseActivity<EmptyVm, CircleStickTestBinding> {

    @Inject
    ViewModelProvider.Factory factory;

    @Override
    protected int getLayoutId() {
        return R.layout.circle_stick_test;
    }

    @Override
    public EmptyVm getmViewModel() {
        return ViewModelProviders.of(this, factory).get(EmptyVm.class);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mToolbar.setTitle("我的圈子");
    }

    @Override
    public void initView() {
    }

}
