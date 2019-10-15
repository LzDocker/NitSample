package com.bfhd.account.ui;

import android.arch.lifecycle.ViewModelProvider;
import android.arch.lifecycle.ViewModelProviders;
import android.os.Bundle;

import com.alibaba.android.arouter.launcher.ARouter;
import com.bfhd.account.BR;
import com.bfhd.account.R;
import com.bfhd.account.databinding.AccountActivitySgininListBinding;
import com.bfhd.account.vm.AccountViewModel;
import com.bfhd.circle.base.HivsBaseActivity;
import com.bfhd.circle.base.ViewEventResouce;
import com.docker.core.util.adapter.SimpleCommonRecyclerAdapter;
import java.util.Collection;

import javax.inject.Inject;

/*
 * 签到列表
 **/
public class AccountSigninListActivity extends HivsBaseActivity<AccountViewModel, AccountActivitySgininListBinding> {


    @Inject
    ViewModelProvider.Factory factory;
//    private HivsSampleAdapter<AttendVo> attendVoHivsSampleAdapter;

    private SimpleCommonRecyclerAdapter simpleCommonRecyclerAdapter;

    @Override
    public AccountViewModel getmViewModel() {
        return ViewModelProviders.of(this, factory).get(AccountViewModel.class);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.account_activity_sginin_list;
    }

    @Override
    protected void inject() {
        super.inject();
        ARouter.getInstance().inject(this);
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }


    @Override
    public void initView() {
        mToolbar.setTitle("签到记录");
        mBinding.empty.hide();
        mViewModel.getAttendList();
        mBinding.sgininRefresh.setEnableLoadMore(false);
        simpleCommonRecyclerAdapter = new SimpleCommonRecyclerAdapter(R.layout.account_item_sign, BR.attendvo);
        mBinding.recyclerView.setAdapter(simpleCommonRecyclerAdapter);

        mBinding.empty.setOnretryListener(() -> {
            mViewModel.getAttendList();
        });
        mBinding.sgininRefresh.setOnRefreshListener(refreshLayout -> {
            simpleCommonRecyclerAdapter.clear();
            mViewModel.getAttendList();
        });

    }

    @Override
    public void OnVmEnentListner(ViewEventResouce viewEventResouce) {
        super.OnVmEnentListner(viewEventResouce);
        mBinding.sgininRefresh.finishRefresh();
        switch (viewEventResouce.eventType) {
            case 110:
                mBinding.empty.hide();
                if (viewEventResouce.data != null) {
                    simpleCommonRecyclerAdapter.getmObjects().addAll((Collection) viewEventResouce.data);
                    simpleCommonRecyclerAdapter.notifyDataSetChanged();
                } else {

                }
                break;

        }
    }


}
