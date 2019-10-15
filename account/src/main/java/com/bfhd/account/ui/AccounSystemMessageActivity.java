package com.bfhd.account.ui;
import android.arch.lifecycle.ViewModelProvider;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.android.arouter.launcher.ARouter;
import com.bfhd.account.BR;
import com.bfhd.account.R;
import com.bfhd.account.utils.RouterUtils;
import com.bfhd.account.vm.AccountViewModel;
import com.bfhd.account.vo.SystemMessageVo;
import com.bfhd.circle.base.HivsBaseViewModel;
import com.bfhd.circle.base.OpenBaseListActivity;
import com.bfhd.circle.base.ViewEventResouce;
import com.docker.common.common.router.AppRouter;
import com.docker.core.util.adapter.SimpleCommonRecyclerAdapter;
import java.util.Collection;
import java.util.List;

import javax.inject.Inject;

/*
 * 系统通知
 **/

@Route(path = AppRouter.ACCOUNT_SYSTEM_sMESSAGE)
public class AccounSystemMessageActivity extends OpenBaseListActivity<AccountViewModel> {


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
        mViewModel.getSystemMessageList();

    }

    @Override
    public HivsBaseViewModel setViewmodel() {
        return null;
    }

    @Override
    public void initView() {
        mToolbar.setTitle("系统通知");
        simpleCommonRecyclerAdapter = new SimpleCommonRecyclerAdapter(R.layout.account_item_sys_message, BR.item);
        mBinding.recyclerView.setAdapter(simpleCommonRecyclerAdapter);
        simpleCommonRecyclerAdapter.setOnItemClickListener((v, p) -> {
            Intent intent = new Intent(AccounSystemMessageActivity.this, AccounMessageDetailActivity.class);
            startActivity(intent);
        });
        mBinding.refresh.setOnRefreshListener(refreshLayout -> {
            mViewModel.mPage = 1;
            mViewModel.getSystemMessageList();
        });
        mBinding.refresh.setOnLoadMoreListener(refreshLayout -> mViewModel.getSystemMessageList());
        //加载失败的时候
        mBinding.empty.setOnretryListener(() -> {
            mViewModel.mPage = 1;
            mViewModel.getSystemMessageList();
        });
        simpleCommonRecyclerAdapter.setOnItemClickListener((view, position) -> {
            if (((SystemMessageVo) simpleCommonRecyclerAdapter.getmObjects().get(position)).getParams() != null) {
                if ("message".equals(((SystemMessageVo) simpleCommonRecyclerAdapter.getmObjects().get(position)).getParams().getType())) {
                    return;
                }
                RouterUtils.Jump(((SystemMessageVo) simpleCommonRecyclerAdapter.getmObjects().get(position)).getParams(), false);
            }
        });
    }

    @Override
    public void OnVmEnentListner(ViewEventResouce viewEventResouce) {
        super.OnVmEnentListner(viewEventResouce);
        switch (viewEventResouce.eventType) {
            case 514:
                mBinding.refresh.finishRefresh();
                mBinding.refresh.finishLoadMore();
                if (viewEventResouce.data != null) {
//                    mBinding.frame.setBackgroundColor(Color.TRANSPARENT);
                    if (mViewModel.mPage == 1) {
                        simpleCommonRecyclerAdapter.clear();
                    }
                    List<SystemMessageVo> systemMessageVos = (List<SystemMessageVo>) viewEventResouce.data;
                    simpleCommonRecyclerAdapter.getmObjects().addAll((Collection) viewEventResouce.data);
                    simpleCommonRecyclerAdapter.notifyDataSetChanged();
                    if (simpleCommonRecyclerAdapter.getmObjects().size() > 0) {
                        mBinding.empty.hide();
                    } else {
                        mBinding.empty.showNodata();
                    }
                    break;
                } else {
                    mBinding.empty.showNodata();
                }
            case 515:
                mBinding.refresh.finishRefresh();
                mBinding.empty.showError();
                break;
        }

    }

}
