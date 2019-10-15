package com.bfhd.circle.ui.circle;

import android.arch.lifecycle.ViewModelProvider;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;

import com.bfhd.circle.BR;
import com.bfhd.circle.R;
import com.bfhd.circle.base.HivsBaseViewModel;
import com.bfhd.circle.base.OpenBaseListActivity;
import com.bfhd.circle.base.ViewEventResouce;
import com.bfhd.circle.base.adapter.HivsSampleAdapter;
import com.bfhd.circle.vm.CircleViewModel;
import com.bfhd.circle.vo.CircleGroupPerssionVo;
import com.bfhd.circle.vo.bean.StaCirParam;

import javax.inject.Inject;

/*
 * 圈子人员分组权限设置
 * */
public class CircleGroupPerssionActivity extends OpenBaseListActivity<CircleViewModel> {

    @Inject
    ViewModelProvider.Factory factory;

    @Override
    public CircleViewModel getmViewModel() {
        return ViewModelProviders.of(this, factory).get(CircleViewModel.class);
    }

    //    private int type;
    private StaCirParam mStartParam;
    private CircleGroupPerssionVo circleGroupPerssionVo;
    private HivsSampleAdapter mAdapter;


    public static void startMe(Context context, StaCirParam startCircleBean) {

        Intent intent = new Intent(context, CircleGroupPerssionActivity.class);
        Bundle bundle = new Bundle();
        bundle.putSerializable("mStartParam", startCircleBean);
        intent.putExtras(bundle);
        context.startActivity(intent);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        mStartParam = (StaCirParam) getIntent().getSerializableExtra("mStartParam");
        super.onCreate(savedInstanceState);
    }

    @Override
    public HivsBaseViewModel setViewmodel() {
        return null;
    }

    @Override
    public void initView() {
        mToolbar.setTitle("设置分组权限");
        mBinding.refresh.setEnablePureScrollMode(true);
        mViewModel.getCircleGrouop(mStartParam);
        mAdapter = new HivsSampleAdapter(R.layout.circle_item_group_perssion, BR.item);
        mAdapter.setOnchildViewClickListener(new int[]{R.id.rl_title_coutainer,R.id.circle_comment_check,R.id.circle_pub_check}, (childview, position) -> {

            CircleGroupPerssionVo.Group group = (CircleGroupPerssionVo.Group) mAdapter.getItem(position);
            if (childview.getId() == R.id.rl_title_coutainer) {
                group.isExpand = !group.isExpand;
                mAdapter.notifyItemRangeChanged(position,1);
            }
            if (childview.getId() == R.id.circle_comment_check) {
                if (group.isComment == 1) group.isComment = 0;else group.isComment = 1;
                mAdapter.notifyItemRangeChanged(position,1);
                if(!TextUtils.isEmpty(group.groupid)){
                    String key = "isComment";
                    if("0".equals(group.groupid)){
                        key = "ungroupedIsComment";
                    }
                    mViewModel.updateCircleGrouopPerssion(mStartParam,key,String.valueOf(group.isComment),group.groupid);
                }
            }
            if (childview.getId() == R.id.circle_pub_check) {
                if (group.isPublishDynamic == 1) group.isPublishDynamic = 0;else group.isPublishDynamic = 1;
                mAdapter.notifyItemRangeChanged(position,1);
                if(!TextUtils.isEmpty(group.groupid)){
                    String key = "isPublishDynamic";
                    if("0".equals(group.groupid)){
                        key = "ungroupedIsPublishDynamic";
                    }
                    mViewModel.updateCircleGrouopPerssion(mStartParam,key,String.valueOf(group.isPublishDynamic),group.groupid);
                }
            }
        });
        mBinding.recyclerView.setAdapter(mAdapter);
    }

    @Override
    public void OnVmEnentListner(ViewEventResouce viewEventResouce) {
        super.OnVmEnentListner(viewEventResouce);
        switch (viewEventResouce.eventType) {
            case 109: // 获取分组数据成功
                if (viewEventResouce.data != null && ((CircleGroupPerssionVo) viewEventResouce.data).group.size() > 0) {
                    mBinding.empty.hide();
                    circleGroupPerssionVo = (CircleGroupPerssionVo) viewEventResouce.data;
                    mAdapter.getmObjects().addAll(((CircleGroupPerssionVo) viewEventResouce.data).group);
                    mAdapter.notifyDataSetChanged();
                } else {
                    mBinding.empty.showError();
                }
                break;
        }
    }
}
