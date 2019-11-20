package com.docker.cirlev2.ui.persion;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.TextUtils;

import com.docker.cirlev2.BR;
import com.docker.cirlev2.R;
import com.docker.cirlev2.databinding.Circlev2ActivityGroupPerssionBinding;
import com.docker.cirlev2.vm.CirclePersionViewModel;
import com.docker.cirlev2.vo.entity.CircleGroupPerssionVo;
import com.docker.cirlev2.vo.param.StaCirParam;
import com.docker.common.common.adapter.NitSampleAdapter;
import com.docker.common.common.command.NitContainerCommand;
import com.docker.common.common.ui.base.NitCommonActivity;

/*
 * 圈子人员分组权限设置
 * */
public class CircleGroupPerssionActivity extends NitCommonActivity<CirclePersionViewModel, Circlev2ActivityGroupPerssionBinding> {


    //    private int type;
    private StaCirParam mStartParam;
    private CircleGroupPerssionVo circleGroupPerssionVo;
    private NitSampleAdapter mAdapter;


    public static void startMe(Context context, StaCirParam startCircleBean) {

        Intent intent = new Intent(context, CircleGroupPerssionActivity.class);
        Bundle bundle = new Bundle();
        bundle.putSerializable("mStartParam", startCircleBean);
        intent.putExtras(bundle);
        context.startActivity(intent);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.circlev2_activity_group_perssion;
    }

    @Override
    public CirclePersionViewModel getmViewModel() {
        return ViewModelProviders.of(this, factory).get(CirclePersionViewModel.class);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        mStartParam = (StaCirParam) getIntent().getSerializableExtra("mStartParam");
        super.onCreate(savedInstanceState);
    }

    @Override
    public void initView() {
        mToolbar.setTitle("设置分组权限");
        mViewModel.getCircleGrouop(mStartParam);
        mAdapter = new NitSampleAdapter(R.layout.circlev2_item_group_perssion, BR.item);
        mAdapter.setOnchildViewClickListener(new int[]{R.id.rl_title_coutainer, R.id.circle_comment_check, R.id.circle_pub_check}, (childview, position) -> {
            CircleGroupPerssionVo.Group group = (CircleGroupPerssionVo.Group) mAdapter.getItem(position);
            if (childview.getId() == R.id.rl_title_coutainer) {
                group.isExpand = !group.isExpand;
                mAdapter.notifyItemRangeChanged(position, 1);
            }
            if (childview.getId() == R.id.circle_comment_check) {
                if (group.isComment == 1) group.isComment = 0;
                else group.isComment = 1;
                mAdapter.notifyItemRangeChanged(position, 1);
                if (!TextUtils.isEmpty(group.groupid)) {
                    String key = "isComment";
                    if ("0".equals(group.groupid)) {
                        key = "ungroupedIsComment";
                    }
                    mViewModel.updateCircleGrouopPerssion(mStartParam, key, String.valueOf(group.isComment), group.groupid);
                }
            }
            if (childview.getId() == R.id.circle_pub_check) {
                if (group.isPublishDynamic == 1) group.isPublishDynamic = 0;
                else group.isPublishDynamic = 1;
                mAdapter.notifyItemRangeChanged(position, 1);
                if (!TextUtils.isEmpty(group.groupid)) {
                    String key = "isPublishDynamic";
                    if ("0".equals(group.groupid)) {
                        key = "ungroupedIsPublishDynamic";
                    }
                    mViewModel.updateCircleGrouopPerssion(mStartParam, key, String.valueOf(group.isPublishDynamic), group.groupid);
                }
            }
        });
        mBinding.recyclerView.setAdapter(mAdapter);
    }

    @Override
    public void initObserver() {

        mViewModel.mCircleGroupPerssionVoLv.observe(this, new Observer<CircleGroupPerssionVo>() {
            @Override
            public void onChanged(@Nullable CircleGroupPerssionVo circleGroupPerssionVo) {
                if (circleGroupPerssionVo != null && circleGroupPerssionVo.group.size() > 0) {
//                    mBinding.empty.hide();
                    circleGroupPerssionVo = circleGroupPerssionVo;
                    mAdapter.getmObjects().addAll(circleGroupPerssionVo.group);
                    mAdapter.notifyDataSetChanged();
                } else {
//                    mBinding.empty.showError();
                }
            }
        });
        mViewModel.mCircleGroupPerssionUpdateLv.observe(this, s -> {
        });
    }

    @Override
    public void initRouter() {

    }

    @Override
    public NitContainerCommand providerNitContainerCommand(int flag) {
        return null;
    }

}
