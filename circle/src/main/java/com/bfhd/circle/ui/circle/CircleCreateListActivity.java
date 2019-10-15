package com.bfhd.circle.ui.circle;

import android.arch.lifecycle.ViewModelProvider;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;

import com.bfhd.circle.BR;
import com.bfhd.circle.R;
import com.bfhd.circle.base.HivsBaseActivity;
import com.bfhd.circle.databinding.CircleActivityCircleCreateListBinding;
import com.bfhd.circle.vm.CircleViewModel;
import com.bfhd.circle.vo.CircleCreateCardVo;
import com.docker.core.util.adapter.SimpleCommonRecyclerAdapter;

import javax.inject.Inject;

/*
 * 圈子创建
 * */
public class CircleCreateListActivity extends HivsBaseActivity<CircleViewModel, CircleActivityCircleCreateListBinding> {

    @Inject
    ViewModelProvider.Factory factory;

    @Override
    protected int getLayoutId() {
        return R.layout.circle_activity_circle_create_list;
    }

    @Override
    public CircleViewModel getmViewModel() {
        return ViewModelProviders.of(this, factory).get(CircleViewModel.class);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mBinding.setViewmodel(mViewModel);
    }

    @Override
    public void initView() {
        mToolbar.setTitle("创建圈子");
        SimpleCommonRecyclerAdapter adapter = new SimpleCommonRecyclerAdapter(R.layout.circle_item_createcircle, BR.item);
        mBinding.recycle.setLayoutManager(new LinearLayoutManager(this));
        mBinding.recycle.setAdapter(adapter);
        adapter.add(new CircleCreateCardVo(R.mipmap.circle_qiye_icon, "创建企业圈", "企业展示"));
        adapter.add(new CircleCreateCardVo(R.mipmap.circle_xingqu_icon, "创建兴趣圈", "志同道合"));
        adapter.add(new CircleCreateCardVo(R.mipmap.circle_guobie_icon, "创建国别圈", "志同道合"));
        adapter.setOnItemClickListener((v, p) -> {
            processCreate(p);
        });
    }

    private void processCreate(int position) {
        int type = CircleCreateActivity.CIRCLE_TYPE_COMPANY;
        switch (position) {
            case 0: // 企业
                type = CircleCreateActivity.CIRCLE_TYPE_COMPANY;
                break;
            case 1:// 兴趣
                type = CircleCreateActivity.CIRCLE_TYPE_ACTIVE;
                break;
            case 2:// 国别
                type = CircleCreateActivity.CIRCLE_TYPE_COUNTRY;
                break;
        }
        CircleCreateActivity.startMe(this, type,null,null);
    }
}
