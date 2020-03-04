package com.docker.cirlev2.ui.pro.index;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.android.arouter.launcher.ARouter;
import com.docker.cirlev2.R;
import com.docker.cirlev2.databinding.Circlev2MutipartIndexActivityBinding;
import com.docker.cirlev2.ui.detail.index.CircleConfig;
import com.docker.cirlev2.ui.detail.index.temp.defaults.NitDefaultCircleFragment;
import com.docker.cirlev2.vm.MutipartCircleViewModel;
import com.docker.common.common.adapter.CommonpagerAdapter;
import com.docker.common.common.adapter.CommonpagerStateAdapter;
import com.docker.common.common.config.Constant;
import com.docker.common.common.model.CommonListOptions;
import com.docker.common.common.router.AppRouter;
import com.docker.common.common.ui.base.NitCommonActivity;
import com.docker.common.common.utils.cache.CacheUtils;
import com.docker.common.common.vm.NitEmptyViewModel;
import com.docker.common.common.widget.indector.CommonIndector;

import java.util.ArrayList;
import java.util.List;

import static com.docker.common.common.config.Constant.CommonListParam;

@Route(path = AppRouter.CIRCLE_DETAIL_v2_PRO_MUTIPARTINDEX)
public class MutipartIndexActivity extends NitCommonActivity<MutipartCircleViewModel, Circlev2MutipartIndexActivityBinding> {


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mViewModel.fetchMutipartTabData();
    }

    @Override
    public void initView() {
        mToolbar.setTitle("分部说");

    }

    @Override
    public void initObserver() {

        mViewModel.mMutipartTbLv.observe(this, new Observer<List<MutipartTabVo>>() {
            @Override
            public void onChanged(@Nullable List<MutipartTabVo> mutipartTabVos) {
                if (mutipartTabVos != null && mutipartTabVos.size() > 0) {
                    processTab(mutipartTabVos);
                }
            }
        });
    }

    public void processTab(List<MutipartTabVo> mutipartTabVos) {

        //{
        //            "我关注的", "七律分部", "燕山分部", "逍遥分部"
        //        } ;
        String[] titles = new String[mutipartTabVos.size() + 1];
        ArrayList<Fragment> fragments = new ArrayList<>();
        CommonListOptions commonListOptions = new CommonListOptions();
        commonListOptions.refreshState = Constant.KEY_REFRESH_OWNER;
        commonListOptions.RvUi = Constant.KEY_RVUI_LINER;
        commonListOptions.ReqParam.put("t", "idle");
        commonListOptions.ReqParam.put("act", "focus"); //act=
        if (CacheUtils.getUser() != null) {
            commonListOptions.ReqParam.put("memberid", CacheUtils.getUser().uid);
        }
        fragments.add((Fragment) ARouter.getInstance()
                .build(AppRouter.CIRCLE_DYNAMIC_LIST_FRAME)
                .withSerializable(CommonListParam, commonListOptions)
                .navigation());
        titles[0] = "我关注的";

        for (int i = 0; i < mutipartTabVos.size(); i++) {
            titles[i + 1] = mutipartTabVos.get(i).circleName;
            CircleConfig circleConfig = new CircleConfig();
            circleConfig.circleid = mutipartTabVos.get(i).circleid;
            circleConfig.utid = mutipartTabVos.get(i).utid;
            circleConfig.circleType = "1";
            circleConfig.Temple = 0;
            circleConfig.isNeedToobar = false;
            fragments.add(NitDefaultCircleFragment.getInstance(circleConfig));
        }

        // magic
        mBinding.viewpager.setAdapter(new CommonpagerStateAdapter(getSupportFragmentManager(), fragments, titles));
        CommonIndector commonIndector = new CommonIndector();
        commonIndector.initMagicIndicatorScroll(titles, mBinding.viewpager, mBinding.magic, this);
        // magic
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
    public MutipartCircleViewModel getmViewModel() {
        return ViewModelProviders.of(this, factory).get(MutipartCircleViewModel.class);
    }
}
