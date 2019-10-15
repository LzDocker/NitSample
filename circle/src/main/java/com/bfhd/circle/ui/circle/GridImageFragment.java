package com.bfhd.circle.ui.circle;

import android.arch.lifecycle.ViewModelProvider;
import android.arch.lifecycle.ViewModelProviders;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.View;

import com.bfhd.circle.R;
import com.bfhd.circle.base.CommonFragment;
import com.bfhd.circle.databinding.CircleFragmentGridImageBinding;
import com.bfhd.circle.vm.CircleGridImageViewModel;
import javax.inject.Inject;

/*
 * 选择 图片 / 视频 通用布局 选择后上传
 *
 * 有时间再写吧 先赶进度了。。。
 *
 *
 * */
public class GridImageFragment extends CommonFragment<CircleGridImageViewModel, CircleFragmentGridImageBinding> {

    // todo
    @Inject
    ViewModelProvider.Factory factory;

    @Override
    protected int getLayoutId() {
        return R.layout.circle_fragment_grid_image;
    }

    public static GridImageFragment newInstance() {
        return new GridImageFragment();
    }

    @Override
    protected CircleGridImageViewModel getViewModel() {
        return ViewModelProviders.of(this, factory).get(CircleGridImageViewModel.class);
    }

    @Override
    protected void initView(View var1) {

    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mBinding.get().setViewmodel(mViewModel);

    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

    }

    @Override
    public void initImmersionBar() {

    }
}
