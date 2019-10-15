package com.bfhd.account.ui;

import android.arch.lifecycle.ViewModelProvider;
import android.arch.lifecycle.ViewModelProviders;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.alibaba.android.arouter.launcher.ARouter;
import com.bfhd.account.BR;
import com.bfhd.account.R;
import com.bfhd.account.vm.AccountViewModel;
import com.bfhd.account.vo.PointVo;
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

/**
 * 积分记录列表
 */
public class AccounPointRecordListActivity extends OpenBaseListActivity<AccountViewModel> {

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
        mViewModel.getPointDetail(false);
        mBinding.refresh.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(@NonNull RefreshLayout refreshLayout) {
                mViewModel.mPage = 1;
                mViewModel.getPointDetail(false);
            }
        });

        mBinding.refresh.setOnLoadMoreListener(new OnLoadMoreListener() {
            @Override
            public void onLoadMore(@NonNull RefreshLayout refreshLayout) {
                mViewModel.getPointDetail(false);
            }
        });

        mBinding.empty.setOnretryListener(new EmptyLayout.OnretryListener() {
            @Override
            public void onretry() {
                mViewModel.getPointDetail(false);
            }
        });
    }

    @Override
    public void initView() {
        mToolbar.setTitle("积分明细");
        mBinding.empty.hide();
        simpleCommonRecyclerAdapter = new SimpleCommonRecyclerAdapter(R.layout.account_item_point, BR.item);
        mBinding.recyclerView.setAdapter(simpleCommonRecyclerAdapter);
        simpleCommonRecyclerAdapter.notifyDataSetChanged();
    }

    @Override
    public HivsBaseViewModel setViewmodel() {
        return null;
    }

    @Override
    public void OnVmEnentListner(ViewEventResouce viewEventResouce) {
        super.OnVmEnentListner(viewEventResouce);
        switch (viewEventResouce.eventType) {
            case 536:
                if (viewEventResouce.data != null&&((List<PointVo>)viewEventResouce.data).size()>0) {
                    List<PointVo> pointVoList = (List<PointVo>) viewEventResouce.data;
                    mBinding.empty.hide();
                    if (mViewModel.mPage == 1) {
                        simpleCommonRecyclerAdapter.clear();
                    }
                    simpleCommonRecyclerAdapter.getmObjects().addAll(pointVoList);
                    simpleCommonRecyclerAdapter.notifyDataSetChanged();
                }
                if ( simpleCommonRecyclerAdapter.getmObjects().size()==0){
                    mBinding.empty.showNodata();
                }
                mBinding.refresh.finishRefresh();
                mBinding.refresh.finishLoadMore();
                break;
            case 537:
                mBinding.refresh.finishRefresh();
                mBinding.refresh.finishLoadMore();
                mBinding.empty.showError();
                break;
        }
    }
}
