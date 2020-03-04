package com.docker.message.ui;

import android.arch.lifecycle.ViewModelProviders;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.view.View;

import com.alibaba.android.arouter.facade.annotation.Autowired;
import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.android.arouter.launcher.ARouter;
import com.docker.common.common.config.Constant;
import com.docker.common.common.model.CommonListOptions;
import com.docker.common.common.router.AppRouter;
import com.docker.common.common.ui.base.NitCommonListFragment;
import com.docker.common.common.utils.cache.CacheUtils;
import com.docker.message.vm.MessageViewModel;

@Route(path = AppRouter.MESSAGELIST)
public class MessageFragment extends NitCommonListFragment<MessageViewModel> {

    CommonListOptions commonListReq = new CommonListOptions();

    @Autowired
    public int style;

    public static MessageFragment newInstance() {
        return new MessageFragment();
    }

    @Override
    public MessageViewModel getViewModel() {
        return ViewModelProviders.of(this, factory).get(MessageViewModel.class);
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceStates) {
        super.onCreate(savedInstanceStates);
        ARouter.getInstance().inject(this);
        mViewModel.setServerType(0);
    }

    @Override
    protected void initView(View var1) {
        mViewModel.style = style;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

    }

    @Override
    protected void initRvUi() {
        if (style == 0) {
            mBinding.get()
                    .recyclerView
                    .setLayoutManager(com.docker.core.util.LayoutManager.grid(5)
                            .create(mBinding.get().recyclerView));
        } else {
            super.initRvUi();
        }
    }

    @Override
    public CommonListOptions getArgument() {
        if (style == 0) {
            commonListReq.refreshState = Constant.KEY_REFRESH_NOUSE;
        } else {
            commonListReq.refreshState = Constant.KEY_REFRESH_OWNER;
        }
        if (CacheUtils.getUser() != null) {
            commonListReq.ReqParam.put("uuid", CacheUtils.getUser().uuid);
            commonListReq.ReqParam.put("memberid", CacheUtils.getUser().uid);
        }
        return commonListReq;
    }

    @Override
    public void initImmersionBar() {

    }

    @Override
    public void onVisible() {
        super.onVisible();
        if (mViewModel != null && CacheUtils.getUser() != null && style == 0 && TextUtils.isEmpty(mViewModel.mCommonListReq.ReqParam.get("uuid"))) {
            mViewModel.mCommonListReq.ReqParam.put("uuid", CacheUtils.getUser().uuid);
            mViewModel.mCommonListReq.ReqParam.put("memberid", CacheUtils.getUser().uid);
            mViewModel.mPage = 1;
            mViewModel.loadData();
        }
    }
}
