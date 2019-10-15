package com.bfhd.account.ui;

import android.arch.lifecycle.ViewModelProvider;
import android.arch.lifecycle.ViewModelProviders;
import android.os.Bundle;

import com.alibaba.android.arouter.launcher.ARouter;
import com.bfhd.account.BR;
import com.bfhd.account.R;
import com.bfhd.account.vm.AccountViewModel;
import com.bfhd.account.vo.FansVo;
import com.bfhd.circle.base.HivsBaseViewModel;
import com.bfhd.circle.base.OpenBaseListActivity;
import com.bfhd.circle.base.ViewEventResouce;
import com.bfhd.circle.base.adapter.HivsSampleAdapter;
import com.bfhd.circle.vo.bean.StaPersionDetail;
import com.docker.common.common.router.AppRouter;

import java.util.List;

import javax.inject.Inject;

/*
 * 关注列表  wj
 **/
public class AccountAttenListActivity extends OpenBaseListActivity<AccountViewModel> {


    private String type = "4";
    @Inject
    ViewModelProvider.Factory factory;
    private HivsSampleAdapter hivsSampleAdapter;


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
        mToolbar.setTitle("关注");
        mViewModel.getFocusList();
        mBinding.refresh.setOnRefreshListener(refreshLayout -> {
            mViewModel.mPage = 1;
            mViewModel.getFocusList();
        });
        mBinding.refresh.setOnLoadMoreListener(refreshLayout -> mViewModel.getFocusList());
        mBinding.empty.setOnretryListener(() -> {
            mViewModel.mPage = 1;
            mViewModel.getFocusList();
        });
    }

    @Override
    public HivsBaseViewModel setViewmodel() {
        return null;
    }

    @Override
    public void initView() {
        mBinding.empty.hide();
        hivsSampleAdapter = new HivsSampleAdapter(R.layout.account_attenwj_item, BR.item);
        hivsSampleAdapter.setOnchildViewClickListener(new int[]{R.id.tv_focus, R.id.tv_focusme, R.id.lin_focus}, (childview, position) -> {
            int id = childview.getId();
            if (R.id.tv_focus == id) {//“已关注” 按钮
                FansVo fansVo = (FansVo) hivsSampleAdapter.getItem(position);
                mViewModel.focusClick(fansVo);
            }
            if (R.id.tv_focusme == id) {//“+关注”按钮
                FansVo fansVo = (FansVo) hivsSampleAdapter.getItem(position);
                mViewModel.focusClick(fansVo);
            }
            if (R.id.lin_focus == id) {
                FansVo fansVo = (FansVo) hivsSampleAdapter.getItem(position);
                StaPersionDetail staPersionDetail = new StaPersionDetail();
                staPersionDetail.name = fansVo.getNickname();
                staPersionDetail.uuid = fansVo.getUuid();
                staPersionDetail.uid = fansVo.getMemberid();
                ARouter.getInstance().build(AppRouter.CIRCLE_persion_detail).withSerializable("mStartParam", staPersionDetail).navigation();
            }
        });
        mBinding.recyclerView.setAdapter(hivsSampleAdapter);
        hivsSampleAdapter.notifyDataSetChanged();
    }

    @Override
    public void OnVmEnentListner(ViewEventResouce viewEventResouce) {
        super.OnVmEnentListner(viewEventResouce);
        switch (viewEventResouce.eventType) {
            case 113:
                mBinding.refresh.finishRefresh();
                mBinding.refresh.finishLoadMore();
                if (viewEventResouce.data != null) {
                    if (viewEventResouce.data != null && ((List<FansVo>) viewEventResouce.data).size() > 0) {
                        List<FansVo> commentVoList = (List<FansVo>) viewEventResouce.data;
                        if (mViewModel.mPage == 1) {
                            hivsSampleAdapter.clear();
                        }
                        hivsSampleAdapter.getmObjects().addAll(commentVoList);
                        hivsSampleAdapter.notifyDataSetChanged();
                    }
                    if (hivsSampleAdapter.getmObjects().size() == 0) {
                        mBinding.empty.showNodata();
                    }
                    mBinding.refresh.finishRefresh();
                    mBinding.refresh.finishLoadMore();
                }
                break;
            case 114:
                mBinding.empty.showError();
                mBinding.refresh.finishRefresh();
                mBinding.empty.showError();
                break;
        }
    }
}
