package com.docker.cirlev2.ui.publish.select;

import android.arch.lifecycle.ViewModelProvider;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import com.docker.cirlev2.vm.CirlcleSelectViewModel;
import com.docker.cirlev2.vo.param.StaCirParam;
import com.docker.common.common.command.NitContainerCommand;
import com.docker.common.common.config.Constant;
import com.docker.common.common.model.CommonListOptions;
import com.docker.common.common.ui.base.NitCommonListActivity;
import com.docker.common.common.utils.rxbus.RxBus;
import com.docker.common.common.utils.rxbus.RxEvent;

import javax.inject.Inject;

import io.reactivex.disposables.Disposable;

/*
 * 选择分享圈子
 *
 * */
public class CircleShareSelectActivity extends NitCommonListActivity<CirlcleSelectViewModel> {

    private Disposable disposable;
    private StaCirParam mStaCirParam;

    @Inject
    ViewModelProvider.Factory factory;

    public static void startMe(Context context, StaCirParam mStaCirParam) {
        Intent intent = new Intent(context, CircleShareSelectActivity.class);
        intent.putExtra("StaCirParam", mStaCirParam);
        context.startActivity(intent);
    }


    @Override
    public CirlcleSelectViewModel getmViewModel() {
        return ViewModelProviders.of(this, factory).get(CirlcleSelectViewModel.class);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        mStaCirParam = (StaCirParam) getIntent().getExtras().getSerializable("StaCirParam");
        commonListReq = new CommonListOptions();
        commonListReq.refreshState = Constant.KEY_REFRESH_OWNER;
        commonListReq.ReqParam.put("uuid", "3c29a4eed44db285468df3443790e64a");
        commonListReq.ReqParam.put("memberid", "3");
        if (mStaCirParam != null) {
            commonListReq.externs.put("default", mStaCirParam.getCircleid());
        } else {
            mStaCirParam = new StaCirParam();
        }
        super.onCreate(savedInstanceState);
        disposable = RxBus.getDefault().toObservable(RxEvent.class).subscribe(rxEvent -> {
            if (rxEvent.getT().equals("GroupSelect")) {
                finish();
            }
        });
    }

    @Override
    public void initView() {
        mToolbar.setTitle("选择");
        mViewModel.selectVoMediatorLiveData.observe(this, circlePubSelectVo -> {
            mStaCirParam.setCircleid(circlePubSelectVo.getCircleid());
            mStaCirParam.setUtid(circlePubSelectVo.getUtid());
            mStaCirParam.setExtentron(circlePubSelectVo.getCircleName());
            CircleShareGroupSelectActivity.startMe(this, mStaCirParam);
        });
    }


    @Override
    public void initObserver() {

    }

    @Override
    public NitContainerCommand providerNitContainerCommand(int flag) {
        return null;
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (disposable != null) {
            disposable.dispose();
        }
    }
}
