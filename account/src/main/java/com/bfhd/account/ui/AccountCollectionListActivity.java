package com.bfhd.account.ui;

import android.arch.lifecycle.ViewModelProvider;
import android.arch.lifecycle.ViewModelProviders;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.text.TextUtils;
import android.view.View;

import com.alibaba.android.arouter.launcher.ARouter;
import com.bfhd.account.BR;
import com.bfhd.account.R;
import com.bfhd.account.vm.AccountViewModel;
import com.bfhd.account.vo.CollectVo;
import com.bfhd.circle.base.HivsBaseViewModel;
import com.bfhd.circle.base.OpenBaseListActivity;
import com.bfhd.circle.base.ViewEventResouce;
import com.bfhd.circle.base.adapter.HivsSampleAdapter;
import com.bfhd.circle.vo.bean.StaDetailParam;
import com.bfhd.circle.vo.bean.StaPersionDetail;
import com.docker.common.common.router.AppRouter;
import com.docker.common.common.widget.empty.EmptyLayout;
import com.docker.common.common.widget.refresh.api.RefreshLayout;
import com.docker.common.common.widget.refresh.listener.OnLoadMoreListener;
import com.docker.common.common.widget.refresh.listener.OnRefreshListener;
import com.docker.core.util.adapter.OnchildViewClickListener;
import java.util.List;

import javax.inject.Inject;

/*
 * 收藏列表
 **/
public class AccountCollectionListActivity extends OpenBaseListActivity<AccountViewModel> {

    private HivsSampleAdapter hivsSampleAdapter;

    private String type = "4";
    @Inject
    ViewModelProvider.Factory factory;


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

        String flag = getIntent().getStringExtra("flag");
        if (!TextUtils.isEmpty(flag)) {
            type = "5";
            mToolbar.setTitle("关注");
        } else {
            mToolbar.setTitle("收藏");
            type = "4";
        }
        mViewModel.getCollectList(type);
        mBinding.refresh.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(@NonNull RefreshLayout refreshLayout) {
                mViewModel.mPage = 1;
                mViewModel.getCollectList(type);

            }
        });

        mBinding.refresh.setOnLoadMoreListener(new OnLoadMoreListener() {
            @Override
            public void onLoadMore(@NonNull RefreshLayout refreshLayout) {
                mViewModel.getCollectList(type);
            }
        });

        mBinding.empty.setOnretryListener(new EmptyLayout.OnretryListener() {
            @Override
            public void onretry() {
                mViewModel.mPage = 1;
                mViewModel.getCollectList(type);
            }
        });
    }

    @Override
    public HivsBaseViewModel setViewmodel() {
        return null;
    }

    @Override
    public void initView() {

        mBinding.empty.hide();
        hivsSampleAdapter = new HivsSampleAdapter(R.layout.collection_item, BR.item);
        mBinding.recyclerView.setAdapter(hivsSampleAdapter);
        hivsSampleAdapter.notifyDataSetChanged();

        hivsSampleAdapter.setOnchildViewClickListener(new int[]{R.id.rl_item, R.id.iv_avater}, new OnchildViewClickListener() {
            @Override
            public void onChildCiewCilck(View childview, int position) {
                int id = childview.getId();
                CollectVo favVo = (CollectVo) hivsSampleAdapter.getmObjects().get(position);
                if (id == R.id.rl_item) {
                    StaDetailParam staDetailParam = new StaDetailParam();
                    staDetailParam.dynamicId = favVo.getParams().getDynamicid();
                    staDetailParam.isRecomend = false;
                    staDetailParam.speical = -1;
                    staDetailParam.uiType = 3;
                    ARouter.getInstance().build(AppRouter.CIRCLE_DETAIL).withSerializable("mStaparam", staDetailParam).navigation();
//                    RouterUtils.Jump(((CollectVo) hivsSampleAdapter.getmObjects().get(position)).getParams(), false);
                } else if (id == R.id.iv_avater) {
                    StaPersionDetail staPersionDetail = new StaPersionDetail();
                    staPersionDetail.name = favVo.getNickname();
                    staPersionDetail.uuid = favVo.getParams().getUuid();
                    staPersionDetail.uid = favVo.getUid();
                    ARouter.getInstance().build(AppRouter.CIRCLE_persion_detail).withSerializable("mStartParam", staPersionDetail).navigation();
                }

            }
        });

    }


    @Override
    public void OnVmEnentListner(ViewEventResouce viewEventResouce) {
        super.OnVmEnentListner(viewEventResouce);

        switch (viewEventResouce.eventType) {
            case 520:
                mBinding.refresh.finishRefresh();
                mBinding.refresh.finishLoadMore();
                if (viewEventResouce.data != null) {
                    if (viewEventResouce.data != null && ((List<CollectVo>) viewEventResouce.data).size() > 0) {
                        List<CollectVo> commentVoList = (List<CollectVo>) viewEventResouce.data;
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
            case 521:
                mBinding.refresh.finishRefresh();
                mBinding.empty.showError();
                break;

        }
    }

}
