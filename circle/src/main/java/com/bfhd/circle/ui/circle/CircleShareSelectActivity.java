package com.bfhd.circle.ui.circle;

import android.arch.lifecycle.ViewModelProvider;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;

import com.bfhd.circle.BR;
import com.bfhd.circle.R;
import com.bfhd.circle.base.HivsBaseViewModel;
import com.bfhd.circle.base.OpenBaseListActivity;
import com.bfhd.circle.base.ViewEventResouce;
import com.bfhd.circle.vm.CircleViewModel;
import com.bfhd.circle.vo.CircleListVo;
import com.bfhd.circle.vo.bean.StaCirParam;
import com.docker.common.common.utils.rxbus.RxBus;
import com.docker.common.common.utils.rxbus.RxEvent;
import com.docker.core.util.adapter.SimpleCommonRecyclerAdapter;

import java.util.Collection;

import javax.inject.Inject;

import io.reactivex.disposables.Disposable;

/*
 * 选择分享圈子
 *
 * */
public class CircleShareSelectActivity extends OpenBaseListActivity<CircleViewModel> {

    private SimpleCommonRecyclerAdapter mAdapter;
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
    public CircleViewModel getmViewModel() {
        return ViewModelProviders.of(this, factory).get(CircleViewModel.class);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        mStaCirParam = (StaCirParam) getIntent().getExtras().getSerializable("StaCirParam");
        super.onCreate(savedInstanceState);
        disposable = RxBus.getDefault().toObservable(RxEvent.class).subscribe(rxEvent -> {
            if (rxEvent.getT().equals("GroupSelect")) {
                finish();
            }
        });
    }

    @Override
    public HivsBaseViewModel setViewmodel() {
        return null;
    }

    @Override
    public void initView() {
        mToolbar.setTitle("选择圈子");
        mAdapter = new SimpleCommonRecyclerAdapter(R.layout.circle_item_select, BR.item);
        mBinding.refresh.setEnablePureScrollMode(true);
        mBinding.recyclerView.setAdapter(mAdapter);
        mViewModel.getCircleJoinList();
        mAdapter.setOnItemClickListener((v, p) -> {
            CircleListVo circleListVo = (CircleListVo) mAdapter.getmObjects().get(p);
//            StaCirParam staCirParam = new StaCirParam(circleListVo.getCircleid(), circleListVo.getUtid(), circleListVo.getCircleName());
            mStaCirParam.setCircleid(circleListVo.getCircleid());
            mStaCirParam.setUtid(circleListVo.getUtid());
            mStaCirParam.setExtentron(circleListVo.getCircleName());
            CircleShareGroupSelectActivity.startMe(this, mStaCirParam);
            for (int i = 0; i < mAdapter.getmObjects().size(); i++) {
                ((CircleListVo) mAdapter.getmObjects().get(i)).isCheck = false;
            }
            circleListVo.isCheck = true;
            mAdapter.notifyItemRangeChanged(p, 1);
        });

    }

    @Override
    public void OnVmEnentListner(ViewEventResouce viewEventResouce) {
        super.OnVmEnentListner(viewEventResouce);
        if (viewEventResouce.eventType == 107 && (Collection) viewEventResouce.data != null) {
            mAdapter.getmObjects().addAll((Collection) viewEventResouce.data);
            if (mStaCirParam.getCircleid() != null && !TextUtils.isEmpty(mStaCirParam.getCircleid())) {
                for (int i = 0; i < mAdapter.getmObjects().size(); i++) {
                    if (mStaCirParam.getCircleid().equals(((CircleListVo) mAdapter.getmObjects().get(i)).getCircleid())) {
                        ((CircleListVo) mAdapter.getmObjects().get(i)).isCheck = true;
                    }
                }
            }
            if (mAdapter.getmObjects().size() == 0) {
                mBinding.empty.showNodata();
                return;
            }
            mBinding.empty.hide();
            mAdapter.notifyDataSetChanged();
        } else {
            mBinding.empty.showError();
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (mAdapter != null) {
            mAdapter.notifyDataSetChanged();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (disposable != null) {
            disposable.dispose();
        }
    }
}
