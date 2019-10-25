package com.docker.common.common.ui.base;

import android.databinding.Observable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.util.Pair;
import android.view.View;

import com.alibaba.android.arouter.launcher.ARouter;
import com.docker.common.R;
import com.docker.common.common.model.CommonListOptions;
import com.docker.common.common.vm.NitCommonListVm;
import com.docker.common.common.widget.refresh.SmartRefreshLayout;
import com.docker.common.databinding.CommonFragmentListBinding;
import com.docker.core.util.LayoutManager;

import java.util.ArrayList;
import java.util.Collection;

public abstract class NitCommonListFragment<VM extends NitCommonListVm> extends NitCommonFragment<VM, CommonFragmentListBinding> {

    protected CommonListOptions commonListReq;
    private SmartRefreshLayout refreshLayout;

    @Override
    protected int getLayoutId() {
        return R.layout.common_fragment_list;
    }

    @Override
    protected void initView(View var1) {
        (mBinding.get()).setViewmodel(mViewModel);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        commonListReq = getArgument();
        if (commonListReq != null) {
            ARouter.getInstance().inject(this);
            (mViewModel).initParam(commonListReq);
            mBinding.get().setViewmodel(mViewModel);
            initRvUi();
            initRefreshUi();
            initListener();
        }
    }

    public void initListener() {
        mViewModel.mServerLiveData.observe(this, o -> {
            if ((mBinding.get()).refresh != null) {
                if (o != null && ((Collection) o).size() < (mViewModel).mPageSize) {
                    (mBinding.get()).refresh.setNoMoreData(true);
                } else if (o == null && (mViewModel).mItems.size() > 0) {
                    (mBinding.get()).refresh.setNoMoreData(true);
                } else {
                    (mBinding.get()).refresh.setNoMoreData(false);
                }
            }
        });
        mViewModel.mRefreshStateLiveData.addOnPropertyChangedCallback(new Observable.OnPropertyChangedCallback() {
            @Override
            public void onPropertyChanged(Observable sender, int propertyId) {
                if (mViewModel.mRefreshStateLiveData.get()) {
                    if (refreshLayout != null) {
                        refreshLayout.finishRefresh();
                    }
                }
            }
        });
        (mViewModel).mRefreshStateNodataLiveData.addOnPropertyChangedCallback(new Observable.OnPropertyChangedCallback() {
            @Override
            public void onPropertyChanged(Observable sender, int propertyId) {
                if ((mViewModel).mRefreshStateNodataLiveData.get()) {
                    mBinding.get().refresh.setNoMoreData((mViewModel).mRefreshStateNodataLiveData.get());
                    if (refreshLayout != null) {
                        refreshLayout.setNoMoreData((mViewModel).mRefreshStateNodataLiveData.get());
                    }
                }
            }
        });
    }

    public abstract CommonListOptions getArgument();

    protected void initRvUi() {
        if (commonListReq.RvUi == 0) {
            (mBinding.get()).recyclerView.setLayoutManager(LayoutManager.linear().create((mBinding.get()).recyclerView));
        } else {
            (mBinding.get()).recyclerView.setLayoutManager(LayoutManager
                    .grid(2)
                    .create((mBinding.get()).recyclerView));
        }
    }

    protected void initRefreshUi() {
        switch (commonListReq.refreshState) {
            case 0:
                (mBinding.get()).refresh.setEnableRefresh(true);
                (mBinding.get()).refresh.setEnableLoadMore(true);
                break;
            case 1:
                (mBinding.get()).refresh.setEnableRefresh(true);
                (mBinding.get()).refresh.setEnableLoadMore(false);
                break;
            case 2:
                (mBinding.get()).refresh.setEnableRefresh(false);
                (mBinding.get()).refresh.setEnableLoadMore(false);
                break;
            case 3:
                (mBinding.get()).refresh.setEnableRefresh(true);
                (mBinding.get()).refresh.setEnableLoadMore(true);
                (mBinding.get()).refresh.setEnablePureScrollMode(true);
                break;
        }
    }

    // 外部更改请求接口的参数
    // isall 是否全量更改
    public void UpdateReqParam(boolean isAll, Pair<String, String> pair) {
        mViewModel.loadingState = false;
        if (isAll) {
            (mViewModel).mCommonListReq.ReqParam.clear();
            if (!TextUtils.isEmpty(pair.first) && !TextUtils.isEmpty(pair.second)) {
                (mViewModel).mCommonListReq.ReqParam.put(pair.first, pair.second);
            }
        } else {
            if (!TextUtils.isEmpty(pair.first) && !TextUtils.isEmpty(pair.second)) {
                (mViewModel).mCommonListReq.ReqParam.put(pair.first, pair.second);
            }
            if (!TextUtils.isEmpty(pair.first) && TextUtils.isEmpty(pair.second)) {
                (mViewModel).mCommonListReq.ReqParam.remove(pair.first);
            }
        }
        (mViewModel).mPage = 1;
        (mViewModel).mItems.clear();
    }


    // 外部更改请求接口的参数
    // isall 是否全量更改
    public void UpdateReqParam(boolean isAll, ArrayList<Pair<String, String>> pairList) {
        (mViewModel).loadingState = false;
        if (isAll) {
            (mViewModel).mCommonListReq.ReqParam.clear();
            for (int i = 0; i < pairList.size(); i++) {
                if (!TextUtils.isEmpty(pairList.get(i).first) && !TextUtils.isEmpty(pairList.get(i).second)) {
                    (mViewModel).mCommonListReq.ReqParam.put(pairList.get(i).first, pairList.get(i).second);
                }
            }
        } else {
            if (pairList != null && pairList.size() > 0) {
                for (int i = 0; i < pairList.size(); i++) {
                    if (!TextUtils.isEmpty(pairList.get(i).first) && !TextUtils.isEmpty(pairList.get(i).second)) {
                        (mViewModel).mCommonListReq.ReqParam.put(pairList.get(i).first, pairList.get(i).second);
                    }
                }
            }

        }
        (mViewModel).mPage = 1;
        (mViewModel).mItems.clear();
    }

    @Override
    public void onVisible() {
        super.onVisible();
        if ((mViewModel).mItems.size() == 0 && (mViewModel).mPage == 1 && !(mViewModel).loadingState) {
            (mViewModel).loadData();
        }
    }

    @Override
    public void onReFresh(SmartRefreshLayout refreshLayout) {
        super.onReFresh(refreshLayout);
        this.refreshLayout = refreshLayout;
        (mViewModel).mPage = 1;
        (mViewModel).mItems.clear();
    }
}
