package com.bfhd.circle.ui.active;

import android.arch.lifecycle.ViewModelProvider;
import android.arch.lifecycle.ViewModelProviders;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.View;

import com.bfhd.circle.BR;
import com.bfhd.circle.R;
import com.bfhd.circle.base.CommonFragment;
import com.bfhd.circle.databinding.CircleFragmentRecommendBinding;
import com.bfhd.circle.vm.CircleRecommendViewModel;
import com.bfhd.circle.vo.CircleInfo;
import com.docker.common.common.widget.refresh.SmartRefreshLayout;
import com.docker.core.util.adapter.SimpleCommonRecyclerAdapter;
import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

/*
 *  推荐 fragment  通用布局
 * */
public class CircleRecommendFragment extends CommonFragment<CircleRecommendViewModel, CircleFragmentRecommendBinding> {


    @Inject
    ViewModelProvider.Factory factory;

    private SimpleCommonRecyclerAdapter adapter = new SimpleCommonRecyclerAdapter(R.layout.circle_item_circle_list, BR.item);

    @Override
    protected int getLayoutId() {
        return R.layout.circle_fragment_recommend;
    }

    public static CircleRecommendFragment newInstance() {
        return new CircleRecommendFragment();
    }

    @Override
    protected CircleRecommendViewModel getViewModel() {
        return ViewModelProviders.of(this, factory).get(CircleRecommendViewModel.class);
    }

    @Override
    protected void initView(View var1) {
        getData();
    }
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mBinding.get().setViewmodel(mViewModel);
//        mBinding.get().recyclerView.setNestedScrollingEnabled(true);
//        mBinding.get().recyclerView.setAdapter(adapter);


    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

    }

    @Override
    public void OnRefresh(SmartRefreshLayout smartRefreshLayout) {
        super.OnRefresh(smartRefreshLayout);
        getData();
        smartRefreshLayout.finishRefresh();
    }

    @Override
    public void OnLoadMore(SmartRefreshLayout smartRefreshLayout) {
        super.OnLoadMore(smartRefreshLayout);
        getData();
        smartRefreshLayout.finishLoadMore();
    }

    @Override
    public void initImmersionBar() {

    }

    @Override
    public void onVisible() {
        super.onVisible();
    }

    private void getData() {
        List<CircleInfo> circleInfos = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            CircleInfo info = new CircleInfo();
            info.setInfo("----" + i);
            info.setName("----" + i);
            circleInfos.add(info);
        }
        adapter.add(circleInfos);
    }
}
