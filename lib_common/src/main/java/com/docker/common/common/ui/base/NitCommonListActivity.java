package com.docker.common.common.ui.base;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.util.Pair;

import com.alibaba.android.arouter.facade.annotation.Autowired;
import com.alibaba.android.arouter.launcher.ARouter;
import com.docker.common.R;
import com.docker.common.common.command.NitContainerCommand;
import com.docker.common.common.model.CommonListOptions;
import com.docker.common.common.vm.NitCommonListVm;
import com.docker.common.common.vo.Empty;
import com.docker.common.common.widget.refresh.SmartRefreshLayout;
import com.docker.common.databinding.CommonFragmentListBinding;
import com.docker.core.util.LayoutManager;

import java.util.ArrayList;

import javax.inject.Inject;

import static com.docker.common.common.config.Constant.CommonListParam;

public abstract class NitCommonListActivity<VM extends NitCommonListVm> extends NitCommonActivity<VM, CommonFragmentListBinding> {

    @Inject
    Empty empty;

    @Autowired
    String title;

    protected CommonListOptions commonListReq;

    private SmartRefreshLayout refreshLayout;

    @Override
    protected int getLayoutId() {
        return R.layout.common_fragment_list;
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (commonListReq == null) {
            commonListReq = (CommonListOptions) getIntent().getSerializableExtra(CommonListParam);
        }
        if (commonListReq != null) {
            (mViewModel).initParam(commonListReq);
            mBinding.setViewmodel(mViewModel);
            initRvUi();
            initRefreshUi();
            initListener();
        }
    }

    @Override
    public void initRouter() {
        ARouter.getInstance().inject(this);
    }

    public void initListener() {
        mViewModel.mServerLiveData.observe(this, o -> {

        });
    }

    protected void initRvUi() {
        if (commonListReq.RvUi == 0) {
            (mBinding).recyclerView.setLayoutManager(LayoutManager.linear().create((mBinding).recyclerView));
        } else {
            (mBinding).recyclerView.setLayoutManager(LayoutManager
                    .grid(2)
                    .create((mBinding).recyclerView));
        }
    }

    protected void initRefreshUi() {
        switch (commonListReq.refreshState) {
            case 0:
                (mBinding).refresh.setEnableRefresh(true);
                (mBinding).refresh.setEnableLoadMore(true);
                break;
            case 1:
                (mBinding).refresh.setEnableRefresh(false);
                (mBinding).refresh.setEnableLoadMore(true);
                break;
            case 2:
                (mBinding).refresh.setEnableRefresh(false);
                (mBinding).refresh.setEnableLoadMore(false);
                break;
            case 3:
                (mBinding).refresh.setEnableRefresh(true);
                (mBinding).refresh.setEnableLoadMore(true);
                (mBinding).refresh.setEnablePureScrollMode(true);
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
    protected void onResume() {
        super.onResume();
        if ((mViewModel).mItems.size() == 0 && (mViewModel).mPage == 1 && !(mViewModel).loadingState) {
            (mViewModel).loadData();
        }
    }
}
