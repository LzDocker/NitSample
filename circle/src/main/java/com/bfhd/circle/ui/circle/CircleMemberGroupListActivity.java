package com.bfhd.circle.ui.circle;

import android.app.Activity;
import android.arch.lifecycle.ViewModelProvider;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;

import com.bfhd.circle.BR;
import com.bfhd.circle.R;
import com.bfhd.circle.base.HivsBaseActivity;
import com.bfhd.circle.base.ViewEventResouce;
import com.bfhd.circle.databinding.CircleActivityCircleMemberGroupBinding;
import com.bfhd.circle.vm.CircleViewModel;
import com.bfhd.circle.vo.MemberGroupingVo;
import com.bfhd.circle.vo.bean.StaCirParam;
import com.docker.core.util.adapter.SimpleCommonRecyclerAdapter;
import java.util.List;
import javax.inject.Inject;

/*
 * 成员分组列表
 * */
public class CircleMemberGroupListActivity extends HivsBaseActivity<CircleViewModel, CircleActivityCircleMemberGroupBinding> {

    @Inject
    ViewModelProvider.Factory factory;

    private StaCirParam mStartParam;
    private SimpleCommonRecyclerAdapter mAdapter;


//    private int type;

    public static void startMe(Context context, StaCirParam startCircleBean, int code) {

        Intent intent = new Intent(context, CircleMemberGroupListActivity.class);
        Bundle bundle = new Bundle();
        bundle.putSerializable("mStartParam", startCircleBean);
        intent.putExtras(bundle);
        ((Activity) context).startActivityForResult(intent, code);

    }

    @Override
    protected int getLayoutId() {
        return R.layout.circle_activity_circle_member_group;
    }

    @Override
    public CircleViewModel getmViewModel() {
        return ViewModelProviders.of(this, factory).get(CircleViewModel.class);
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
        mAdapter = new SimpleCommonRecyclerAdapter(R.layout.circle_item_circle_group, BR.item);
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
    public void OnVmEnentListner(ViewEventResouce viewEventResouce) {
        super.OnVmEnentListner(viewEventResouce);
        switch (viewEventResouce.eventType) {
            case 112: // 获取分类数据成功
                mAdapter.getmObjects().clear();
                mAdapter.getmObjects().addAll(((List<MemberGroupingVo>) viewEventResouce.data));
                MemberGroupingVo memberGroupingVo = new MemberGroupingVo();
                memberGroupingVo.setGroupName("内部成员");
                memberGroupingVo.setGroupid("-1");
                mAdapter.getmObjects().add(0, memberGroupingVo);
                mAdapter.notifyDataSetChanged();
                break;
        }
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK && requestCode == 101) {
            mViewModel.getMemberGroup(mStartParam);
        }
    }
}
