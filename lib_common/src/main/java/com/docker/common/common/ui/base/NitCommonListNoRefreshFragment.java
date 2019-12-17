package com.docker.common.common.ui.base;

import android.databinding.Observable;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.text.TextUtils;
import android.util.Pair;
import android.view.View;

import com.alibaba.android.arouter.launcher.ARouter;
import com.docker.common.R;
import com.docker.common.common.model.CommonListOptions;
import com.docker.common.common.vm.NitCommonListVm;
import com.docker.common.common.vo.Empty;
import com.docker.common.common.widget.recycledrag.ItemTouchHelperAdapter;
import com.docker.common.common.widget.recycledrag.ItemTouchHelperCallback;
import com.docker.common.common.widget.refresh.SmartRefreshLayout;
import com.docker.common.databinding.CommonFragmentListBinding;
import com.docker.common.databinding.CommonFragmentListNoRefreshBinding;
import com.docker.core.util.LayoutManager;

import java.util.ArrayList;
import java.util.Collections;

import javax.inject.Inject;

import static com.docker.common.common.config.Constant.CommonListParam;
import static com.docker.common.common.config.Constant.KEY_RVUI_GRID2;
import static com.docker.common.common.config.Constant.KEY_RVUI_HOR;
import static com.docker.common.common.config.Constant.KEY_RVUI_LINER;

public abstract class NitCommonListNoRefreshFragment<VM extends NitCommonListVm> extends NitCommonFragment<VM, CommonFragmentListNoRefreshBinding> implements ItemTouchHelperAdapter {


    @Inject
    Empty empty;

    protected CommonListOptions commonListReq;
    private SmartRefreshLayout refreshLayout;

    private ItemTouchHelperCallback itemTouchHelperCallback;
    private ItemTouchHelper touchHelper;

    @Override
    protected int getLayoutId() {
        return R.layout.common_fragment_list_no_refresh;
    }

    @Override
    protected void initView(View var1) {
        (mBinding.get()).setViewmodel(mViewModel);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        commonListReq = getArgument();
        if (commonListReq == null) {
            commonListReq = (CommonListOptions) getArguments().getSerializable(CommonListParam);
        }
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
        try {
            mViewModel.mServerLiveData.observe(this, o -> {
            });
        } catch (Exception e) {

        }
        mViewModel.loadingOV.addOnPropertyChangedCallback(new Observable.OnPropertyChangedCallback() {
            @Override
            public void onPropertyChanged(Observable sender, int propertyId) {
                if (refreshLayout != null) {
                    refreshLayout.finishRefresh();
                    refreshLayout.finishLoadMore();
                }
            }
        });
    }

    public CommonListOptions getArgument() {
        return null;
    }

    protected void initRvUi() {
        switch (commonListReq.RvUi) {
            case KEY_RVUI_LINER:
                (mBinding.get()).recyclerView.setLayoutManager(LayoutManager.linear().create((mBinding.get()).recyclerView));
                ((LinearLayoutManager) mBinding.get().recyclerView.getLayoutManager()).setSmoothScrollbarEnabled(true);
                ((LinearLayoutManager) mBinding.get().recyclerView.getLayoutManager()).setAutoMeasureEnabled(true);
                mBinding.get().recyclerView.setNestedScrollingEnabled(false);

                break;

            case KEY_RVUI_GRID2:
                (mBinding.get()).recyclerView.setLayoutManager(LayoutManager
                        .grid(2)
                        .create((mBinding.get()).recyclerView));
                break;
            case KEY_RVUI_HOR:
                (mBinding.get()).recyclerView.setLayoutManager(LayoutManager
                        .linear(LinearLayoutManager.HORIZONTAL, false)
                        .create((mBinding.get()).recyclerView));
                break;
        }
        itemTouchHelperCallback = new ItemTouchHelperCallback(this);
        touchHelper = new ItemTouchHelper(itemTouchHelperCallback);
//        itemTouchHelperCallback.setLongPressDragEnabled(true);
//        touchHelper.attachToRecyclerView(mBinding.get().recyclerView);
    }

    protected void initRefreshUi() {

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
        mViewModel.loadData();
    }

    @Override
    public void onLoadMore(SmartRefreshLayout refreshLayout) {
        super.onLoadMore(refreshLayout);
        this.refreshLayout = refreshLayout;
        mViewModel.loadData();
    }

    @Override
    public boolean onItemMove(int fromPosition, int toPosition) {
        Collections.swap(mViewModel.mItems, fromPosition, toPosition);
        mBinding.get().recyclerView.getAdapter().notifyItemMoved(fromPosition, toPosition);
        int rang = 0;
        if (fromPosition > toPosition) {
            rang = fromPosition;
        } else {
            rang = toPosition;
        }
        mBinding.get().recyclerView.getAdapter().notifyItemRangeChanged(0, mViewModel.mItems.size());
        return true;
    }

    @Override
    public void onItemDismiss(int position) {
        mViewModel.mItems.remove(position);
    }
}
