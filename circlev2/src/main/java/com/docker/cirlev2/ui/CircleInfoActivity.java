package com.docker.cirlev2.ui;

import android.arch.lifecycle.ViewModelProvider;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.android.arouter.launcher.ARouter;
import com.docker.cirlev2.R;
import com.docker.cirlev2.databinding.Circlev2ActivityCircleInfoBinding;
import com.docker.cirlev2.databinding.Circlev2SampleActivityBinding;
import com.docker.cirlev2.ui.create.CircleCreateActivity;
import com.docker.cirlev2.vm.CircleCreateViewModel;
import com.docker.cirlev2.vm.SampleListViewModel;
import com.docker.cirlev2.vo.param.StaCirParam;
import com.docker.cirlev2.vo.vo.CircleCreateVo;
import com.docker.common.common.router.AppRouter;
import com.docker.common.common.ui.base.NitCommonActivity;
import com.docker.common.common.utils.cache.CacheUtils;
import com.docker.common.common.utils.rxbus.RxBus;
import com.docker.common.common.utils.rxbus.RxEvent;

import javax.inject.Inject;

import io.reactivex.disposables.Disposable;

/*
 * 圈子介绍 简介
 * */
@Route(path = AppRouter.CIRCLE_INFO_v2)
public class CircleInfoActivity extends NitCommonActivity<CircleCreateViewModel, Circlev2ActivityCircleInfoBinding> {

    @Inject
    ViewModelProvider.Factory factory;
    //    private int type;
    private StaCirParam mStartParam;

    private Disposable disposable;

    public static void startMe(Context context, StaCirParam startCircleBean) {

        Intent intent = new Intent(context, CircleInfoActivity.class);
        Bundle bundle = new Bundle();
        bundle.putSerializable("mStartParam", startCircleBean);
        intent.putExtras(bundle);
        context.startActivity(intent);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.circlev2_activity_circle_info;
    }

    @Override
    public CircleCreateViewModel getmViewModel() {
        return ViewModelProviders.of(this, factory).get(CircleCreateViewModel.class);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        mStartParam = (StaCirParam) getIntent().getSerializableExtra("mStartParam");
        super.onCreate(savedInstanceState);
        disposable = RxBus.getDefault().toObservable(RxEvent.class).subscribe(rxEvent -> {
            if (rxEvent.getT().equals("refresh_circle_myjoin")) {
//                mViewModel.getCircleDetailVo(mStartParam.getUtid(), mStartParam.getCircleid());
            }
        });


    }

    @Override
    public void initView() {
        mToolbar.setTitle("圈子简介");
        if (mStartParam.role == 1) {
            mToolbar.setTvRight("编辑", v -> {
                ARouter.getInstance().build(AppRouter.CIRCLE_CREATE_v2).withInt("flag", 1).withString("circleid", mStartParam.getCircleid()).withString("utid", mStartParam.getUtid()).navigation();
            });
        }
        mViewModel.getCircleDetailVo(mStartParam.getUtid(), mStartParam.getCircleid());
        mBinding.circleInfoQuit.setOnClickListener(v -> {
            CircleCreateVo circleCreateVo = mBinding.getVo();
            mViewModel.quitCircle(mStartParam);
        });
    }


    @Override
    public void initObserver() {

    }

    @Override
    public void initRouter() {

    }

}
