package com.docker.apps.order.ui.logistics;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.DefaultItemAnimator;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.android.arouter.launcher.ARouter;
import com.docker.apps.R;
import com.docker.apps.databinding.ProOrderLogisticsDetialActivityBinding;
import com.docker.apps.order.ui.logistics.adapter.TimerAdapter;
import com.docker.apps.order.vm.OrderMakerViewModel;
import com.docker.apps.order.vo.LogisticeVo;
import com.docker.common.common.router.AppRouter;
import com.docker.common.common.ui.base.NitCommonActivity;

import java.util.HashMap;
import java.util.List;

@Route(path = AppRouter.ORDER_Logistics_LIST)
public class LogisticsDetialActivity extends NitCommonActivity<OrderMakerViewModel, ProOrderLogisticsDetialActivityBinding> {

    private LogisticeVo mLogisticeVo;
    private HashMap<String, String> params;
    /*
     * 入参
     * */
    private String imgurl;
    private String phone;
    private String nu;


    @Override
    protected int getLayoutId() {
        return R.layout.pro_order_logistics_detial_activity;
    }

    @Override
    public OrderMakerViewModel getmViewModel() {
        return ViewModelProviders.of(this, factory).get(OrderMakerViewModel.class);
    }

    @Override
    protected void inject() {
        super.inject();
        ARouter.getInstance().inject(this);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mBinding.setViewmodel(mViewModel);
        mToolbar.setTitle("订单跟踪");
        Intent intent = getIntent();
        phone = intent.getStringExtra("phone");
        nu = intent.getStringExtra("nu");
        imgurl = intent.getStringExtra("imgurl");

        params = new HashMap<>();
        params.put("phone", phone == null ? "" : phone);
        params.put("nu", nu == null ? "" : nu);
        mViewModel.getWuliu(params);
    }

    @Override
    public void initView() {
        initData();
    }

    @Override
    public void initObserver() {

        mViewModel.mLogisticeVoLv.observe(this, logisticeVo -> {
            mBinding.emptyLayout.hide();
            mLogisticeVo = logisticeVo;
            logisticeVo.setImgurl(imgurl);
            mBinding.setItem(logisticeVo);
            List<LogisticeVo.DataBean> dataList = logisticeVo.getData();
            if (dataList.size() == 0) {
                mBinding.empty.showNodata();
            } else {
                mBinding.empty.hide();
                //设置adapter
                mBinding.recyclerView.setAdapter(new TimerAdapter(LogisticsDetialActivity.this, dataList));
            }
        });
    }

    @Override
    public void initRouter() {

    }

    private void initData() {
        mBinding.emptyLayout.setOnretryListener(() -> {
            mViewModel.getWuliu(params);
        });

        //设置动画
        mBinding.recyclerView.setItemAnimator(new DefaultItemAnimator());


    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

    }


}
