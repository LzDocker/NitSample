package com.bfhd.circle.ui.safe;

import android.arch.lifecycle.ViewModelProvider;
import android.arch.lifecycle.ViewModelProviders;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.View;

import com.bfhd.circle.BR;
import com.bfhd.circle.R;
import com.bfhd.circle.base.HivsBaseFragment;
import com.bfhd.circle.databinding.CircleFragmentCircleInfoBinding;
import com.bfhd.circle.vm.CircleViewModel;
import com.bfhd.circle.vo.CircleInfo;
import com.docker.core.util.adapter.SimpleCommonRecyclerAdapter;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

/*
* 我的关注
* */
public class MineAttentionFragment extends HivsBaseFragment<CircleViewModel, CircleFragmentCircleInfoBinding> {


    @Inject
    ViewModelProvider.Factory factory;

    @Override
    protected int getLayoutId() {
        return R.layout.circle_fragment_circle_info;
    }

    public static MineAttentionFragment newInstance() {
        return new MineAttentionFragment();
    }

    @Override
    protected CircleViewModel getViewModel() {
        return ViewModelProviders.of(this, factory).get(CircleViewModel.class);
    }

    @Override
    protected void initView(View var1) {

    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mBinding.get().setViewmodel(mViewModel);

        SimpleCommonRecyclerAdapter adapter = new SimpleCommonRecyclerAdapter(R.layout.circle_item_circle_list, BR.item);
        mBinding.get().recyclerView.setAdapter(adapter);


        List<CircleInfo> circleInfos = new ArrayList<>();

        for (int i = 0; i < 50; i++) {
            CircleInfo info = new CircleInfo();
            info.setInfo("----" + i);
            info.setName("----" + i);
            circleInfos.add(info);
        }

        adapter.add(circleInfos);

    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);


    }


    @Override
    public void initImmersionBar() {

    }
}
