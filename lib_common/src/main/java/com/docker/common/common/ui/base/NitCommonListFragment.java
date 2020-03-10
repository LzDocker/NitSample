package com.docker.common.common.ui.base;

import android.databinding.Observable;
import android.databinding.ObservableList;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.text.TextUtils;
import android.util.Log;
import android.util.Pair;
import android.view.View;

import com.alibaba.android.arouter.launcher.ARouter;
import com.dcbfhd.utilcode.utils.GsonUtils;
import com.dcbfhd.utilcode.utils.MapUtils;
import com.docker.common.R;
import com.docker.common.common.model.CommonListOptions;
import com.docker.common.common.vm.NitCommonListVm;
import com.docker.common.common.vo.Empty;
import com.docker.common.common.widget.recycledrag.ItemTouchHelperAdapter;
import com.docker.common.common.widget.recycledrag.ItemTouchHelperCallback;
import com.docker.common.common.widget.refresh.SmartRefreshLayout;
import com.docker.common.databinding.CommonFragmentListBinding;
import com.docker.core.util.LayoutManager;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import javax.inject.Inject;

import static com.docker.common.common.config.Constant.CommonListParam;
import static com.docker.common.common.config.Constant.KEY_RVUI_GRID2;
import static com.docker.common.common.config.Constant.KEY_RVUI_GRID3;
import static com.docker.common.common.config.Constant.KEY_RVUI_GRID5;
import static com.docker.common.common.config.Constant.KEY_RVUI_HOR;
import static com.docker.common.common.config.Constant.KEY_RVUI_LINER;

public abstract class NitCommonListFragment<VM extends NitCommonListVm> extends NitCommonFragment<VM, CommonFragmentListBinding> implements ItemTouchHelperAdapter {


    @Inject
    Empty empty;

    protected CommonListOptions commonListReq;
    private SmartRefreshLayout refreshLayout;

    private ItemTouchHelperCallback itemTouchHelperCallback;
    private ItemTouchHelper touchHelper;

    @Override
    protected int getLayoutId() {
        return R.layout.common_fragment_list;
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
        try {
            commonListReq = (CommonListOptions) getArguments().getSerializable(CommonListParam);
        }catch (Exception e){}

        if (commonListReq == null) {
            commonListReq = getArgument();
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
            case KEY_RVUI_GRID5:
                (mBinding.get()).recyclerView.setLayoutManager(LayoutManager
                        .grid(5)
                        .create((mBinding.get()).recyclerView));
                break;
            case KEY_RVUI_GRID3:
                (mBinding.get()).recyclerView.setLayoutManager(LayoutManager
                        .grid(3)
                        .create((mBinding.get()).recyclerView));
                break;
        }
        itemTouchHelperCallback = new ItemTouchHelperCallback(this);
        touchHelper = new ItemTouchHelper(itemTouchHelperCallback);
        itemTouchHelperCallback.setLongPressDragEnabled(true);
        touchHelper.attachToRecyclerView(mBinding.get().recyclerView);
    }

    protected void initRefreshUi() {
        switch (commonListReq.refreshState) {
            case 0:
                (mBinding.get()).refresh.setEnableRefresh(true);
                (mBinding.get()).refresh.setEnableLoadMore(true);
                break;
            case 1:
                (mBinding.get()).refresh.setEnableRefresh(false);
                (mBinding.get()).refresh.setEnableLoadMore(true);
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

        Log.d("sss", "initRefreshUi: ==============" + commonListReq.refreshState);
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


    /*
     * 新接口规范刷新
     *
     *
     *
     * */
    public void updateReqParamV2(boolean isAll, ArrayList<Pair<String, String>> pairArrayList) {
        (mViewModel).loadingState = false;
        if (isAll) {
            (mViewModel).mCommonListReq.ReqParam.clear();
            for (int i = 0; i < pairArrayList.size(); i++) {
                if (!TextUtils.isEmpty(pairArrayList.get(i).first) && !TextUtils.isEmpty(pairArrayList.get(i).second)) {
                    (mViewModel).mCommonListReq.ReqParam.put(pairArrayList.get(i).first, pairArrayList.get(i).second);
                }
            }
        } else {
            if (pairArrayList != null && pairArrayList.size() > 0) {
                for (int i = 0; i < pairArrayList.size(); i++)
                    if (!TextUtils.isEmpty(pairArrayList.get(i).first) && !TextUtils.isEmpty(pairArrayList.get(i).second)) {
                        if ((mViewModel).mCommonListReq.ReqParam.containsKey(pairArrayList.get(i).first)) {
                            Map<String, String> willupparam = GsonUtils.fromJson((mViewModel).mCommonListReq.ReqParam.get(pairArrayList.get(i).first), Map.class);
                            willupparam.put(pairArrayList.get(i).first, pairArrayList.get(i).second);
                        } else {
                            (mViewModel).mCommonListReq.ReqParam.put(pairArrayList.get(i).first, pairArrayList.get(i).second);
                        }
                    }
            }
        }
        (mViewModel).mPage = 1;
        (mViewModel).mItems.clear();
    }

    /*
     * 新接口规范刷新
     *
     * 只提供增量更新（追加更新）
     *
     * */
    public void updateReqParamV2(String onelevelKey, ArrayList<Pair<String, String>> pairArrayList) {
        (mViewModel).loadingState = false;
        if (TextUtils.isEmpty(onelevelKey)) {
            for (int i = 0; i < pairArrayList.size(); i++) {
                try {
                    Map<String, String> oldparam = GsonUtils.fromJson((mViewModel).mCommonListReq.ReqParam.get(pairArrayList.get(i).first), Map.class);
                    Map<String, String> newparam = GsonUtils.fromJson(pairArrayList.get(i).second, Map.class);
                    for (Map.Entry<String, String> entry : newparam.entrySet()) {
                        oldparam.put(entry.getKey(), entry.getValue());
                    }
                    mViewModel.mCommonListReq.ReqParam.put(pairArrayList.get(i).first, GsonUtils.toJson(oldparam));
                } catch (Exception e) {
                    if (!TextUtils.isEmpty(pairArrayList.get(i).first) && !TextUtils.isEmpty(pairArrayList.get(i).second)) {
                        (mViewModel).mCommonListReq.ReqParam.put(pairArrayList.get(i).first, pairArrayList.get(i).second);
                    }
                }
            }
        } else {
            Map<String, String> oldTotalparam = new HashMap<>();
            for (int i = 0; i < pairArrayList.size(); i++) {
                try {

                    oldTotalparam = GsonUtils.fromJson((mViewModel).mCommonListReq.ReqParam.get(onelevelKey), Map.class);
                    Map<String, String> oldparam = GsonUtils.fromJson(oldTotalparam.get(pairArrayList.get(i).first), Map.class);
                    Map<String, String> newparam = GsonUtils.fromJson(pairArrayList.get(i).second, Map.class);

                    for (Map.Entry<String, String> entry : newparam.entrySet()) {
                        oldparam.put(entry.getKey(), entry.getValue());
                    }
                    oldTotalparam.put(pairArrayList.get(i).first, GsonUtils.toJson(oldparam));

                } catch (Exception e) {
                    if (oldTotalparam == null) {
                        oldTotalparam = new HashMap<>();
                    }
                    oldTotalparam.put(pairArrayList.get(i).first, pairArrayList.get(i).second);
                }
            }
            mViewModel.mCommonListReq.ReqParam.put(onelevelKey, GsonUtils.toJson(oldTotalparam));
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
