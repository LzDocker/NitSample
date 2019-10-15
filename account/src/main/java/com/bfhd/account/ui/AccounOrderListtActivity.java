package com.bfhd.account.ui;

import android.arch.lifecycle.ViewModelProvider;
import android.arch.lifecycle.ViewModelProviders;
import android.os.Bundle;
import android.support.annotation.NonNull;

import com.alibaba.android.arouter.launcher.ARouter;
import com.bfhd.account.BR;
import com.bfhd.account.R;
import com.bfhd.account.vm.AccountViewModel;
import com.bfhd.account.vo.OrderVo;
import com.bfhd.circle.base.HivsBaseViewModel;
import com.bfhd.circle.base.OpenBaseListActivity;
import com.bfhd.circle.base.ViewEventResouce;
import com.docker.common.common.widget.empty.EmptyLayout;
import com.docker.common.common.widget.refresh.api.RefreshLayout;
import com.docker.common.common.widget.refresh.listener.OnLoadMoreListener;
import com.docker.common.common.widget.refresh.listener.OnRefreshListener;
import com.docker.core.util.adapter.SimpleCommonRecyclerAdapter;

import java.util.List;

import javax.inject.Inject;

/*
 * 订单列表
 **/
public class AccounOrderListtActivity extends OpenBaseListActivity<AccountViewModel> {


    @Inject
    ViewModelProvider.Factory factory;
    private SimpleCommonRecyclerAdapter simpleCommonRecyclerAdapter;


    @Override
    public AccountViewModel getmViewModel() {
        return ViewModelProviders.of(this, factory).get(AccountViewModel.class);
    }


    @Override
    protected void inject() {
        super.inject();
        ARouter.getInstance().inject(this);
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        showWaitDialog("加载中......");
        mViewModel.getOrderList();
        mBinding.refresh.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(@NonNull RefreshLayout refreshLayout) {
                mViewModel.mPage = 1;
                mViewModel.getOrderList();
            }
        });

        mBinding.refresh.setOnLoadMoreListener(new OnLoadMoreListener() {
            @Override
            public void onLoadMore(@NonNull RefreshLayout refreshLayout) {
                mViewModel.getOrderList();
            }
        });

        mBinding.empty.setOnretryListener(new EmptyLayout.OnretryListener() {
            @Override
            public void onretry() {
                mViewModel.mPage = 1;
                mViewModel.getOrderList();
            }
        });

    }

    @Override
    public HivsBaseViewModel setViewmodel() {
        return null;
    }

    @Override
    public void initView() {
        mToolbar.setTitle("我的订单");
        mBinding.empty.hide();
        simpleCommonRecyclerAdapter = new SimpleCommonRecyclerAdapter(R.layout.account_item_order, BR.item);
        mBinding.recyclerView.setAdapter(simpleCommonRecyclerAdapter);
        simpleCommonRecyclerAdapter.notifyDataSetChanged();
        simpleCommonRecyclerAdapter.setOnItemClickListener((v, p) -> {
            //跳转到订单详情页面
        });
    }

    @Override
    public void OnVmEnentListner(ViewEventResouce viewEventResouce) {
        super.OnVmEnentListner(viewEventResouce);
        switch (viewEventResouce.eventType) {
            case 530:
                mBinding.empty.hide();
                if (viewEventResouce.data != null && ((List<OrderVo>) viewEventResouce.data).size() > 0) {
                    List<OrderVo> orderVoList = (List<OrderVo>) viewEventResouce.data;
                    if (mViewModel.mPage == 1) {
                        simpleCommonRecyclerAdapter.clear();
                    }
                    simpleCommonRecyclerAdapter.getmObjects().addAll(orderVoList);
                    simpleCommonRecyclerAdapter.notifyDataSetChanged();
                }
                if (simpleCommonRecyclerAdapter.getmObjects().size() == 0) {
                    mBinding.empty.showNodata();
                }
                mBinding.refresh.finishRefresh();
                mBinding.refresh.finishLoadMore();
                break;
            case 531:
                mBinding.refresh.finishLoadMore();
                mBinding.refresh.finishRefresh();
                mBinding.empty.showError();
                break;
        }
    }
}
