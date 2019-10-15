package com.bfhd.circle.ui.circle;

import android.arch.lifecycle.ViewModelProvider;
import android.arch.lifecycle.ViewModelProviders;
import android.os.Bundle;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.bfhd.circle.R;
import com.bfhd.circle.base.EmptyVm;
import com.bfhd.circle.base.HivsBaseActivity;
import com.bfhd.circle.databinding.CircleActivityCircleListBinding;
import com.bfhd.circle.vo.bean.StaCircleListParam;
import com.dcbfhd.utilcode.utils.FragmentUtils;
import com.docker.common.common.router.AppRouter;
import javax.inject.Inject;

/*
 * 圈子列表  我的圈子
 * */
@Route(path = AppRouter.MINECIRCLELIST)
public class CircleListActivity extends HivsBaseActivity<EmptyVm, CircleActivityCircleListBinding> {

    @Inject
    ViewModelProvider.Factory factory;

    @Override
    protected int getLayoutId() {
        return R.layout.circle_activity_circle_list;
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
        StaCircleListParam sta = new StaCircleListParam();
        sta.Uity = 1;
        sta.ReqType = 0;
        FragmentUtils.add(getSupportFragmentManager(), CircleListFragment.newInstance(sta), R.id.circle_list_frame);
    }

}
