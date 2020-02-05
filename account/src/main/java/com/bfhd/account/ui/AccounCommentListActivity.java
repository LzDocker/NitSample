package com.bfhd.account.ui;

import android.arch.lifecycle.ViewModelProvider;
import android.arch.lifecycle.ViewModelProviders;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.view.View;

import com.alibaba.android.arouter.launcher.ARouter;
import com.bfhd.account.BR;
import com.bfhd.account.R;
import com.bfhd.account.vm.AccountViewModel;
import com.bfhd.account.vo.CommentVo;
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
 * 评论列表
 **/
public class AccounCommentListActivity extends OpenBaseListActivity<AccountViewModel> {


    @Inject
    ViewModelProvider.Factory factory;
    private List<CommentVo> commentVoList;
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
        mViewModel.getCommenList();

        mBinding.refresh.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(@NonNull RefreshLayout refreshLayout) {
                mViewModel.mPage = 1;
                mViewModel.getCommenList();
            }
        });


        mBinding.refresh.setOnLoadMoreListener(new OnLoadMoreListener() {
            @Override
            public void onLoadMore(@NonNull RefreshLayout refreshLayout) {
                mViewModel.getCommenList();
            }
        });

        mBinding.empty.setOnretryListener(new EmptyLayout.OnretryListener() {
            @Override
            public void onretry() {
                mViewModel.mPage = 1;
                mViewModel.getCommenList();
            }
        });


    }

    @Override
    public HivsBaseViewModel setViewmodel() {
        return null;
    }

    @Override
    public void initView() {
        mToolbar.setTitle("评论");
        hivsSampleAdapter = new HivsSampleAdapter(R.layout.account_item_comment, BR.item);
        mBinding.recyclerView.setAdapter(hivsSampleAdapter);
        hivsSampleAdapter.notifyDataSetChanged();

        hivsSampleAdapter.setOnchildViewClickListener(new int[]{R.id.rl_item, R.id.iv_avater}, new OnchildViewClickListener() {
            @Override
            public void onChildCiewCilck(View childview, int position) {
                int id = childview.getId();
                CommentVo commentVo = (CommentVo) hivsSampleAdapter.getmObjects().get(position);
                if (id == R.id.rl_item) {
                    StaDetailParam staDetailParam = new StaDetailParam();
                    staDetailParam.dynamicId = commentVo.getParams().getDynamicid();
                    staDetailParam.isRecomend = false;
                    staDetailParam.speical = -1;
                    staDetailParam.uiType = 3;
                    ARouter.getInstance().build(AppRouter.CIRCLE_DETAIL).withSerializable("mStaparam", staDetailParam).navigation();
                } else if (id == R.id.iv_avater) {
                    StaPersionDetail staPersionDetail = new StaPersionDetail();
                    staPersionDetail.name = commentVo.getNickname();
                    staPersionDetail.uuid = commentVo.getParams().getUuid();
                    staPersionDetail.uid = commentVo.getUid();
//                    ARouter.getInstance().build(AppRouter.CIRCLE_persion_detail).withSerializable("mStartParam", staPersionDetail).navigation();
                    ARouter.getInstance().build(AppRouter.CIRCLE_person_info).withString("memberid2", staPersionDetail.uid).withString("uuid2", staPersionDetail.uuid).navigation();

                }
            }
        });

    }

    @Override
    public void OnVmEnentListner(ViewEventResouce viewEventResouce) {
        super.OnVmEnentListner(viewEventResouce);
        switch (viewEventResouce.eventType) {
            case 516:
                if (viewEventResouce.data != null) {
                    mBinding.empty.hide();
                    commentVoList = (List<CommentVo>) viewEventResouce.data;
                    if (commentVoList.size() > 0) {
                        if (mViewModel.mPage == 1) {
                            hivsSampleAdapter.clear();
                        }
                        hivsSampleAdapter.getmObjects().addAll(commentVoList);
                        hivsSampleAdapter.notifyDataSetChanged();
                    } else {
                        if (mViewModel.mPage == 1) {
                            mBinding.empty.showNodata();
                        }
                    }

                }
                if (hivsSampleAdapter.getmObjects().size() == 0) {
                    mBinding.empty.showNodata();
                }
                mBinding.refresh.finishRefresh();
                mBinding.refresh.finishLoadMore();

                break;
            case 517:
                mBinding.refresh.finishRefresh();
                mBinding.refresh.finishLoadMore();
                mBinding.empty.showError();
                break;

        }

    }
}
