package com.docker.cirlev2.ui.detail;

import android.app.Activity;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import com.docker.cirlev2.BR;
import com.docker.cirlev2.R;
import com.docker.cirlev2.databinding.Circlev2ActivityGroupListBinding;
import com.docker.cirlev2.vm.CircleDetailIndexViewModel;
import com.docker.cirlev2.vo.entity.MemberGroupingVo;
import com.docker.cirlev2.vo.param.StaCirParam;
import com.docker.common.common.command.NitContainerCommand;
import com.docker.common.common.ui.base.NitCommonActivity;
import com.docker.core.util.adapter.SimpleCommonRecyclerAdapter;

/*
 * 成员分组列表
 * */
public class CircleGroupListActivity extends NitCommonActivity<CircleDetailIndexViewModel, Circlev2ActivityGroupListBinding> {


    private StaCirParam mStartParam;
    private SimpleCommonRecyclerAdapter mAdapter;
    public static void startMe(Context context, StaCirParam startCircleBean, int code) {
        Intent intent = new Intent(context, CircleGroupListActivity.class);
        Bundle bundle = new Bundle();
        bundle.putSerializable("mStartParam", startCircleBean);
        intent.putExtras(bundle);
        ((Activity) context).startActivityForResult(intent, code);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.circlev2_activity_group_list;
    }

    @Override
    public CircleDetailIndexViewModel getmViewModel() {
        return ViewModelProviders.of(this, factory).get(CircleDetailIndexViewModel.class);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        mStartParam = (StaCirParam) getIntent().getSerializableExtra("mStartParam");
        super.onCreate(savedInstanceState);
        mViewModel.getMemberGroup(mStartParam);
    }

    @Override
    public void initView() {
        mToolbar.setTitle("分组");
        mAdapter = new SimpleCommonRecyclerAdapter(R.layout.circlev2_item_circle_group, BR.item);
        mBinding.recyclerView.setAdapter(mAdapter);
        mBinding.circleTvNew.setOnClickListener(v -> {
            CircleEditMemberGroupActivity.startMe(this, mStartParam);
        });
        mAdapter.setOnItemClickListener((v, p) -> {
            Intent intent = new Intent();
            intent.putExtra("group", (MemberGroupingVo) mAdapter.getItem(p));
            setResult(RESULT_OK, intent);
            finish();
        });
    }

    @Override
    public void initObserver() {
        mViewModel.mMemberGroupLv.observe(this, memberGroupingVos -> {
            mAdapter.getmObjects().clear();
            mAdapter.getmObjects().addAll(memberGroupingVos);
            MemberGroupingVo memberGroupingVo = new MemberGroupingVo();
            memberGroupingVo.setGroupName("内部成员");
            memberGroupingVo.setGroupid("-1");
            mAdapter.getmObjects().add(0, memberGroupingVo);
            mAdapter.notifyDataSetChanged();
        });
    }

    @Override
    public void initRouter() {

    }

    @Override
    public NitContainerCommand providerNitContainerCommand(int flag) {
        return null;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK && requestCode == 101) {
            mViewModel.getMemberGroup(mStartParam);
        }
    }
}
