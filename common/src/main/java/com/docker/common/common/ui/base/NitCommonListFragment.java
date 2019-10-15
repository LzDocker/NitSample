package com.docker.common.common.ui.base;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.util.Pair;
import android.view.View;

import com.docker.common.R;
import com.docker.common.common.model.CommonListReq;
import com.docker.common.common.vm.NitCommonListVm;
import com.docker.common.databinding.CommonFragmentListBinding;
import com.docker.common.common.widget.refresh.SmartRefreshLayout;
import com.docker.core.util.LayoutManager;

import java.util.ArrayList;
import java.util.Collection;

public abstract class NitCommonListFragment<VM extends NitCommonListVm> extends NitCommonFragment<VM, CommonFragmentListBinding> {

    protected CommonListReq commonListReq;
    private SmartRefreshLayout refreshLayout;

    @Override
    protected int getLayoutId() {
        return R.layout.common_fragment_list;
    }

    @Override
    protected void initView(View var1) {
        ((CommonFragmentListBinding) mBinding.get()).setViewmodel((NitCommonListVm) mViewModel);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        commonListReq = getArgument();
        ((NitCommonListVm) mViewModel).initParam(commonListReq);
        initRvUi();
        initRefreshUi();
        initListener();
    }

    public void initListener() {
        ((NitCommonListVm) mViewModel).mServerLiveData.observe(this, o -> {
            if (((CommonFragmentListBinding) mBinding.get()).refresh != null) {
                if (o != null && ((Collection) o).size() < ((NitCommonListVm) mViewModel).mPageSize) {
                    ((CommonFragmentListBinding) mBinding.get()).refresh.setNoMoreData(true);
                } else if (o == null && ((NitCommonListVm) mViewModel).mItems.size() > 0) {
                    ((CommonFragmentListBinding) mBinding.get()).refresh.setNoMoreData(true);
                } else {
                    ((CommonFragmentListBinding) mBinding.get()).refresh.setNoMoreData(false);
                }
            }
        });
        ((NitCommonListVm) mViewModel).mRefreshStateLiveData.observe(this, o -> {
            if (o.booleanValue()) {
                if (refreshLayout != null) {
                    refreshLayout.finishRefresh();
                }
            }
        });
    }

    public abstract CommonListReq getArgument();

    protected void initRvUi() {
        if (commonListReq.RvUi == 0) {
            ((CommonFragmentListBinding) mBinding.get()).recyclerView.setLayoutManager(LayoutManager.linear().create(((CommonFragmentListBinding) mBinding.get()).recyclerView));
        } else {
            ((CommonFragmentListBinding) mBinding.get()).recyclerView.setLayoutManager(LayoutManager
                    .grid(2)
                    .create(((CommonFragmentListBinding) mBinding.get()).recyclerView));
        }
    }

    protected void initRefreshUi() {
        switch (commonListReq.refreshState) {
            case 0:
                ((CommonFragmentListBinding) mBinding.get()).refresh.setEnableRefresh(true);
                ((CommonFragmentListBinding) mBinding.get()).refresh.setEnableLoadMore(true);
                break;
            case 1:
                ((CommonFragmentListBinding) mBinding.get()).refresh.setEnableRefresh(true);
                ((CommonFragmentListBinding) mBinding.get()).refresh.setEnableLoadMore(false);
                break;
            case 2:
                ((CommonFragmentListBinding) mBinding.get()).refresh.setEnableRefresh(false);
                ((CommonFragmentListBinding) mBinding.get()).refresh.setEnableLoadMore(false);
                break;
            case 3:
                ((CommonFragmentListBinding) mBinding.get()).refresh.setEnableRefresh(false);
                ((CommonFragmentListBinding) mBinding.get()).refresh.setEnableLoadMore(false);
                ((CommonFragmentListBinding) mBinding.get()).refresh.setEnablePureScrollMode(true);
                break;
        }
    }

    // 外部更改请求接口的参数
    // isall 是否全量更改
    public void UpdateReqParam(boolean isAll, Pair<String, String> pair) {
        ((NitCommonListVm) mViewModel).loadingState = false;
        if (isAll) {
            ((NitCommonListVm) mViewModel).mCommonListReq.ReqParam.clear();
            if (!TextUtils.isEmpty(pair.first) && !TextUtils.isEmpty(pair.second)) {
                ((NitCommonListVm) mViewModel).mCommonListReq.ReqParam.put(pair.first, pair.second);
            }
        } else {
            if (!TextUtils.isEmpty(pair.first) && !TextUtils.isEmpty(pair.second)) {
                ((NitCommonListVm) mViewModel).mCommonListReq.ReqParam.put(pair.first, pair.second);
            }
            if (!TextUtils.isEmpty(pair.first) && TextUtils.isEmpty(pair.second)) {
                ((NitCommonListVm) mViewModel).mCommonListReq.ReqParam.remove(pair.first);
            }
        }
        ((NitCommonListVm) mViewModel).mPage = 1;
        ((NitCommonListVm) mViewModel).mItems.clear();
    }


    // 外部更改请求接口的参数
    // isall 是否全量更改
    public void UpdateReqParam(boolean isAll, ArrayList<Pair<String, String>> pairList) {
        ((NitCommonListVm) mViewModel).loadingState = false;
        if (isAll) {
            ((NitCommonListVm) mViewModel).mCommonListReq.ReqParam.clear();
            for (int i = 0; i < pairList.size(); i++) {
                if (!TextUtils.isEmpty(pairList.get(i).first) && !TextUtils.isEmpty(pairList.get(i).second)) {
                    ((NitCommonListVm) mViewModel).mCommonListReq.ReqParam.put(pairList.get(i).first, pairList.get(i).second);
                }
            }
        } else {
            if (pairList != null && pairList.size() > 0) {
                for (int i = 0; i < pairList.size(); i++) {
                    if (!TextUtils.isEmpty(pairList.get(i).first) && !TextUtils.isEmpty(pairList.get(i).second)) {
                        ((NitCommonListVm) mViewModel).mCommonListReq.ReqParam.put(pairList.get(i).first, pairList.get(i).second);
                    }
                }
            }

        }
        ((NitCommonListVm) mViewModel).mPage = 1;
        ((NitCommonListVm) mViewModel).mItems.clear();
    }

    @Override
    public void onVisible() {
        super.onVisible();
        if (((NitCommonListVm) mViewModel).mItems.size() == 0 && ((NitCommonListVm) mViewModel).mPage == 1 && !((NitCommonListVm) mViewModel).loadingState) {
            ((NitCommonListVm) mViewModel).loadData();
        }
    }

    @Override
    public void onReFresh(SmartRefreshLayout refreshLayout) {
        super.onReFresh(refreshLayout);
        this.refreshLayout = refreshLayout;
        ((NitCommonListVm) mViewModel).mPage = 1;
        ((NitCommonListVm) mViewModel).mItems.clear();
    }
}
