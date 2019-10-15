package com.bfhd.circle.ui.circle;

import android.arch.lifecycle.ViewModelProvider;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;

import com.bfhd.circle.R;
import com.bfhd.circle.BR;
import com.bfhd.circle.base.HivsBaseActivity;
import com.bfhd.circle.base.adapter.HivsAbsSampleAdapter;
import com.bfhd.circle.databinding.CircleActivityCircleTypeListBinding;
import com.bfhd.circle.databinding.CircleItemCircleTypeTitleBinding;
import com.bfhd.circle.vm.CircleTypeViewModel;
import com.bfhd.circle.vo.CirlceTypeVo;
import com.bfhd.circle.vo.bean.StaCircleListParam;
import com.docker.common.common.adapter.CommonpagerAdapter;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

/*
 * 圈子列表  分类列表
 * */
public class CircleTypeListActivity extends HivsBaseActivity<CircleTypeViewModel, CircleActivityCircleTypeListBinding> {

    @Inject
    ViewModelProvider.Factory factory;

    @Override
    protected int getLayoutId() {
        return R.layout.circle_activity_circle_type_list;
    }

    @Override
    public CircleTypeViewModel getmViewModel() {
        return ViewModelProviders.of(this, factory).get(CircleTypeViewModel.class);
    }
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mToolbar.setTitle("更多");
        mToolbar.setIvRight(R.mipmap.ic_back, v -> {
            Intent intent = new Intent(CircleTypeListActivity.this, CircleSearchActivity.class);
            startActivity(intent);
        });
    }
    @Override
    public void initView() {
        HivsAbsSampleAdapter mAdapter = new HivsAbsSampleAdapter(R.layout.circle_item_circle_type_title, BR.item) {
            @Override
            public void onRealviewBind(ViewHolder holder, int position) {
                CircleItemCircleTypeTitleBinding itembind = (CircleItemCircleTypeTitleBinding) holder.getBinding();
                if (((CirlceTypeVo) getItem(position)).isCheck) {
                    itembind.tvTitle.setBackgroundColor(getResources().getColor(R.color.circle_white));
                } else {
                    itembind.tvTitle.setBackgroundColor(getResources().getColor(R.color.circle_gray_common));
                }
            }
        };
        List<CirlceTypeVo> titleList = new ArrayList<>();
        titleList.add(new CirlceTypeVo("企业圈", true));
        titleList.add(new CirlceTypeVo("兴趣圈", false));
        titleList.add(new CirlceTypeVo("国别圈", false));

        mBinding.circleRecycleType.setAdapter(mAdapter);
        mAdapter.add(titleList);

        List<Fragment> fragmentList = new ArrayList<>();
        for (int i = 0; i < mAdapter.getmObjects().size(); i++) {
            StaCircleListParam sta = new StaCircleListParam();
            sta.Uity = 0;
            sta.ReqType = 1;
            int ty = i + 1;
            sta.paramMap.put("type", ty + "");
            fragmentList.add(CircleListFragment.newInstance(sta));
        }
        CommonpagerAdapter pagerAdapter = new CommonpagerAdapter(getSupportFragmentManager(), fragmentList);
        mBinding.circleViewpager.setAdapter(pagerAdapter);
        mBinding.circleViewpager.setOffscreenPageLimit(fragmentList.size());
        mAdapter.setOnItemClickListener((v, po) -> {
            mBinding.circleViewpager.setCurrentItem(po, false);
            for (int i = 0; i < mAdapter.getmObjects().size(); i++) {
                ((CirlceTypeVo) mAdapter.getmObjects().get(i)).isCheck = false;
            }
            ((CirlceTypeVo) mAdapter.getmObjects().get(po)).isCheck = true;
            mAdapter.notifyDataSetChanged();
        });
    }
}
