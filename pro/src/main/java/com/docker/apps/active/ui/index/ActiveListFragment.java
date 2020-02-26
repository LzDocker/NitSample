package com.docker.apps.active.ui.index;

import android.arch.lifecycle.ViewModelProviders;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.view.View;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.android.arouter.launcher.ARouter;
import com.docker.apps.active.vm.ActiveCommonViewModel;
import com.docker.cirlev2.vm.CircleDynamicListViewModel;
import com.docker.cirlev2.vo.entity.CircleTitlesVo;
import com.docker.common.common.config.Constant;
import com.docker.common.common.model.CommonListOptions;
import com.docker.common.common.router.AppRouter;
import com.docker.common.common.ui.base.NitCommonListFragment;
import com.docker.common.common.utils.rxbus.RxBus;
import com.docker.common.common.utils.rxbus.RxEvent;

import io.reactivex.disposables.Disposable;

import static com.docker.common.common.config.Constant.KEY_RVUI_GRID2;

@Route(path = AppRouter.ACTIVE_FRAME_LIST)
public class ActiveListFragment extends NitCommonListFragment<ActiveCommonViewModel> {

    CommonListOptions commonListReq = new CommonListOptions();

    public CircleTitlesVo mCircleTitlesVo;
    public int mChildPos = 0;

    private int refresh = -1;

    Disposable disposable;

    @Override
    public ActiveCommonViewModel getViewModel() {
        return ViewModelProviders.of(this, factory).get(ActiveCommonViewModel.class);
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceStates) {
        super.onCreate(savedInstanceStates);
        ARouter.getInstance().inject(this);
    }

    @Override
    protected void initView(View var1) {

    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        refresh = getArguments().getInt("refresh");
        super.onActivityCreated(savedInstanceState);
        //
        disposable = RxBus.getDefault().toObservable(RxEvent.class).subscribe(rxEvent -> {
            if (rxEvent.getT().equals("dynamic_refresh")) {
                mViewModel.mPage = 1;
                if (mViewModel.mItems != null) {
                    mViewModel.mItems.clear();
                }
                mViewModel.loadData();
            }
        });

    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

    }

    @Override
    public CommonListOptions getArgument() {
        mCircleTitlesVo = (CircleTitlesVo) getArguments().getSerializable("param");
        if (mCircleTitlesVo != null) { // 圈子内
            commonListReq.refreshState = Constant.KEY_REFRESH_ONLY_LOADMORE;
            mChildPos = getArguments().getInt("childPosition");

            commonListReq.ReqParam.put("utid", mCircleTitlesVo.getUtid());
            commonListReq.ReqParam.put("circleid", mCircleTitlesVo.getCircleid());
            commonListReq.ReqParam.put("classid", mCircleTitlesVo.getClassid());
            if (mChildPos != -1) {
                commonListReq.ReqParam.put("circleid2", mCircleTitlesVo.getChildClass().get(mChildPos).getCircleid());
                commonListReq.ReqParam.put("t", mCircleTitlesVo.getChildClass().get(mChildPos).getDataType());
            } else {
                commonListReq.ReqParam.put("t", mCircleTitlesVo.getDataType());
            }
            if (TextUtils.isEmpty(commonListReq.ReqParam.get("t"))) {
                commonListReq.ReqParam.remove("t");
            }
        }

        if (refresh == -1) {
            commonListReq.refreshState = Constant.KEY_REFRESH_ONLY_LOADMORE;
        } else {
            commonListReq.refreshState = refresh;
        }

        mViewModel.apiType = 2;
        return commonListReq;
    }

    @Override
    public void initImmersionBar() {

    }

    @Override
    public void onVisible() {
        super.onVisible();

    }
}
