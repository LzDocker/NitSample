package com.bfhd.circle.ui.circle;

import android.arch.lifecycle.ViewModelProvider;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import com.alibaba.android.arouter.launcher.ARouter;
import com.bfhd.circle.R;
import com.bfhd.circle.base.HivsBaseActivity;
import com.bfhd.circle.base.ViewEventResouce;
import com.bfhd.circle.databinding.CircleActivityCircleInfoBinding;
import com.bfhd.circle.vm.CircleViewModel;
import com.bfhd.circle.vo.CircleCreateVo;
import com.bfhd.circle.vo.bean.StaCirParam;
import com.docker.common.common.router.AppRouter;
import com.docker.common.common.utils.rxbus.RxBus;
import com.docker.common.common.utils.rxbus.RxEvent;
import javax.inject.Inject;

import io.reactivex.disposables.Disposable;

/*
 * 圈子介绍 简介
 * */
public class CircleInfoActivity extends HivsBaseActivity<CircleViewModel, CircleActivityCircleInfoBinding> {

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
        return R.layout.circle_activity_circle_info;
    }

    @Override
    public CircleViewModel getmViewModel() {
        return ViewModelProviders.of(this, factory).get(CircleViewModel.class);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        mStartParam = (StaCirParam) getIntent().getSerializableExtra("mStartParam");
        super.onCreate(savedInstanceState);
        disposable = RxBus.getDefault().toObservable(RxEvent.class).subscribe(rxEvent -> {
            if (rxEvent.getT().equals("refresh_circle_myjoin")) {
                mViewModel.getCircleDetailVo(mStartParam.getUtid(), mStartParam.getCircleid());
            }
        });


    }

    @Override
    public void initView() {
        mToolbar.setTitle("圈子简介");
        mToolbar.setTvRight("编辑", v -> {
            CircleCreateActivity.startMe(CircleInfoActivity.this, mStartParam.type, mStartParam.getUtid(), mStartParam.getCircleid());
//            ARouter.getInstance().build(AppRouter.CIRCLE_CREATE_HWJ).withInt("TYPE",1).withString("mUtid",mStartParam.getUtid()).withString("circleID",mStartParam.getCircleid()).navigation();

        });
        mViewModel.getCircleDetailVo(mStartParam.getUtid(), mStartParam.getCircleid());
        mBinding.circleInfoQuit.setOnClickListener(v -> {
            CircleCreateVo circleCreateVo = mBinding.getVo();
            mViewModel.quitCircle(mStartParam);

        });
    }

    @Override
    public void OnVmEnentListner(ViewEventResouce viewEventResouce) {
        super.OnVmEnentListner(viewEventResouce);
        switch (viewEventResouce.eventType) {
            case 108: //
                CircleCreateVo circleCreateVo = (CircleCreateVo) viewEventResouce.data;
                mBinding.setVo(circleCreateVo);
                break;
            case 111:
                RxBus.getDefault().post(new RxEvent<>("refresh_circle_myjoin", "111")); // 刷新我的圈子
                this.finish();
                break;
        }
    }


}
