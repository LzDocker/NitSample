package com.bfhd.circle.ui.circle;

import android.arch.lifecycle.ViewModelProvider;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearSnapHelper;
import android.view.View;

import com.bfhd.circle.R;
import com.bfhd.circle.base.CommonFragment;
import com.bfhd.circle.base.ViewEventResouce;
import com.bfhd.circle.databinding.CircleFragmentRecommandPersionListBinding;
import com.bfhd.circle.ui.adapter.CircleJoinAdapter;
import com.bfhd.circle.vm.CircleFrameViewModel;
import com.bfhd.circle.vo.CircleListVo;
import com.bfhd.circle.vo.bean.StaCirParam;
import java.util.List;

import javax.inject.Inject;

/* 推荐关注列表
 *
 * */
public class CircleRecommandPersionListFragment extends CommonFragment<CircleFrameViewModel, CircleFragmentRecommandPersionListBinding> {


    @Inject
    ViewModelProvider.Factory factory;

    @Override
    protected int getLayoutId() {
        return R.layout.circle_fragment_recommand_persion_list;
    }


    public static CircleRecommandPersionListFragment newInstance() {
        return new CircleRecommandPersionListFragment();
    }

    @Override
    protected CircleFrameViewModel getViewModel() {
        return ViewModelProviders.of(this, factory).get(CircleFrameViewModel.class);
    }

    CircleJoinAdapter adapter;

    @Override
    protected void initView(View var1) {
        mBinding.get().recyclerView.setNestedScrollingEnabled(true);

        LinearSnapHelper snapHelper = new LinearSnapHelper();
        snapHelper.attachToRecyclerView(mBinding.get().recyclerView);
        mBinding.get().recyclerView.setItemAnimator(new DefaultItemAnimator());
        adapter = new CircleJoinAdapter();
        mBinding.get().recyclerView.setAdapter(adapter);

        adapter.setOnItemClickListener((view1, position) -> {
            if (position == adapter.getmObjects().size() - 1) {
                Intent intent = new Intent(this.getHoldingActivity(), CircleCreateListActivity.class);
                startActivity(intent);
            } else {
                CircleListVo circleListVo = adapter.getmObjects().get(position);
                CircleDetailActivity.startMe(this.getHoldingActivity(), new StaCirParam(circleListVo.circleid, circleListVo.getUtid(), ""));
            }
        });
    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

    }


    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mBinding.get().setViewmodel(mViewModel);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
    }


    @Override
    public void initImmersionBar() {

    }

    //    /*
//     *
//     * 加载更多的时候调用
//     * */
//    @Override
//    public void OnLoadMore(SmartRefreshLayout smartRefreshLayout) {
//        super.OnLoadMore(smartRefreshLayout);
//        this.smartRefreshLayout = smartRefreshLayout;
//        if (mImgtype == IMGTYPE_BAIDU) {
//            mViewModel.getBaiduImgList(keyWords);
//        }
//    }
//
//    @Override
//    public void OnRefresh(SmartRefreshLayout smartRefreshLayout) {
//        super.OnRefresh(smartRefreshLayout);
//        this.smartRefreshLayout = smartRefreshLayout;
//        if (mImgtype == IMGTYPE_BAIDU) {
//            mViewModel.mBaiduImgPage = 0;
//            mViewModel.getBaiduImgList(keyWords);
//        }
//    }
//
    @Override
    public void onVisible() {
        super.onVisible();
        mViewModel.getData();
    }

    @Override
    public void OnVmEnentListner(ViewEventResouce viewEventResouce) {
        super.OnVmEnentListner(viewEventResouce);
        switch (viewEventResouce.eventType) {
            case 103:
                mBinding.get().empty.hide();
                if (viewEventResouce.data != null) {
                    adapter.clear();
                    adapter.setNotifyOnChange(false);
                    adapter.add((List<CircleListVo>) viewEventResouce.data);
                    adapter.add(new CircleListVo());
                    adapter.notifyItemRangeChanged(0, adapter.getItemCount());
                } else {
                    if (adapter.getmObjects().size() == 0) {
                        adapter.add(new CircleListVo());
                        adapter.notifyDataSetChanged();
                    }
                }
                break;
        }
    }
}
