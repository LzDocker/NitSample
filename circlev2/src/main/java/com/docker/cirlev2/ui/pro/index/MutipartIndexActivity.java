package com.docker.cirlev2.ui.pro.index;

import android.arch.lifecycle.ViewModelProviders;
import android.support.v4.app.Fragment;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.android.arouter.launcher.ARouter;
import com.docker.cirlev2.R;
import com.docker.cirlev2.databinding.Circlev2MutipartIndexActivityBinding;
import com.docker.cirlev2.ui.detail.index.CircleConfig;
import com.docker.cirlev2.ui.detail.index.temp.defaults.NitDefaultCircleFragment;
import com.docker.common.common.adapter.CommonpagerAdapter;
import com.docker.common.common.router.AppRouter;
import com.docker.common.common.ui.base.NitCommonActivity;
import com.docker.common.common.vm.NitEmptyViewModel;
import com.docker.common.common.widget.indector.CommonIndector;

import java.util.ArrayList;

@Route(path = AppRouter.CIRCLE_DETAIL_v2_PRO_MUTIPARTINDEX)
public class MutipartIndexActivity extends NitCommonActivity<NitEmptyViewModel, Circlev2MutipartIndexActivityBinding> {


    @Override
    public void initView() {

        mToolbar.setTitle("分部说");

        String[] titles = new String[]{"七律分部", "燕山分部", "逍遥分部"};
        ArrayList<Fragment> fragments = new ArrayList<>();

        CircleConfig circleConfig = new CircleConfig();
        circleConfig.circleid = "255";
        circleConfig.utid = "62fe4a4647e39d823677c40fa8fff5f1";
        circleConfig.circleType = "1";
        circleConfig.Temple = 0;
        fragments.add(NitDefaultCircleFragment.getInstance(circleConfig));

        CircleConfig circleConfig1 = new CircleConfig();
        circleConfig1.circleid = "255";
        circleConfig1.utid = "62fe4a4647e39d823677c40fa8fff5f1";
        circleConfig1.circleType = "1";
        circleConfig1.Temple = 0;
        fragments.add(NitDefaultCircleFragment.getInstance(circleConfig1));


        CircleConfig circleConfig2 = new CircleConfig();
        circleConfig2.circleid = "255";
        circleConfig2.utid = "62fe4a4647e39d823677c40fa8fff5f1";
        circleConfig2.circleType = "1";
        circleConfig2.Temple = 0;
        fragments.add(NitDefaultCircleFragment.getInstance(circleConfig2));


        mBinding.viewpager.setOffscreenPageLimit(3);

        // magic
        mBinding.viewpager.setAdapter(new CommonpagerAdapter(getSupportFragmentManager(), fragments, titles));
        CommonIndector commonIndector = new CommonIndector();
        commonIndector.initMagicIndicatorScroll(titles, mBinding.viewpager, mBinding.magic, this);
        // magic
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
        return R.layout.circlev2_mutipart_index_activity;
    }

    @Override
    public NitEmptyViewModel getmViewModel() {
        return ViewModelProviders.of(this, factory).get(NitEmptyViewModel.class);
    }
}
