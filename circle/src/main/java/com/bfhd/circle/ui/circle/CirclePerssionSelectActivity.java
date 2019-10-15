package com.bfhd.circle.ui.circle;

import android.arch.lifecycle.ViewModelProvider;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.view.View;

import com.bfhd.circle.BR;
import com.bfhd.circle.R;
import com.bfhd.circle.base.HivsBaseActivity;
import com.bfhd.circle.base.ViewEventResouce;
import com.bfhd.circle.base.adapter.HivsAbsSampleAdapter;
import com.bfhd.circle.databinding.CircleActivityPerssionSelectBinding;
import com.bfhd.circle.databinding.CircleItemCircleGroupSelectBinding;
import com.bfhd.circle.vm.CircleViewModel;
import com.bfhd.circle.vo.MemberGroupingVo;
import com.bfhd.circle.vo.bean.StaCirParam;
import com.docker.common.common.utils.rxbus.RxBus;
import com.docker.common.common.utils.rxbus.RxEvent;
import java.util.Collection;

import javax.inject.Inject;

/*
 * 谁可以看
 * */
public class CirclePerssionSelectActivity extends HivsBaseActivity<CircleViewModel, CircleActivityPerssionSelectBinding> {

    @Inject
    ViewModelProvider.Factory factory;
    /*
     * 1,2,3
     * */
    private int type = 1;
    private boolean isClose = true;
    private StaCirParam mStaparam;
    private HivsAbsSampleAdapter mAdapter;

    @Override
    protected int getLayoutId() {
        return R.layout.circle_activity_perssion_select;
    }

    public static void startMe(Context context, StaCirParam staCirParam) {
        Intent intent = new Intent(context, CirclePerssionSelectActivity.class);
        intent.putExtra("mStaparam", staCirParam);
        context.startActivity(intent);
    }

    @Override
    public CircleViewModel getmViewModel() {
        return ViewModelProviders.of(this, factory).get(CircleViewModel.class);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        mStaparam = (StaCirParam) getIntent().getSerializableExtra("mStaparam");
        super.onCreate(savedInstanceState);
        mToolbar.setTitle("谁可以看");
        mToolbar.setTvRight("确定", (View.OnClickListener) v -> {

            switch (type) {
                case 1:
                    mStaparam.extentron2 = "全部";
                    break;
                case 2:
                    mStaparam.extentron2 = "内部成员";
                    break;
                case 3:
                    mStaparam.extentron2 = "外部成员";
                    for (int i = 0; i < mAdapter.getmObjects().size(); i++) {
                        MemberGroupingVo vo = (MemberGroupingVo) mAdapter.getmObjects().get(i);
                        if (vo.isClicked()) {
                            mStaparam.extentronList.add(vo.getGroupid());
                        }
                    }
                    break;
            }

            mStaparam.type = type;
            RxBus.getDefault().post(new RxEvent<>("circle_perrsion_update", mStaparam));
            finish();
        });
    }

    @Override
    public void initView() {

        mBinding.llPublic.setOnClickListener(v -> {
            type = 1;
            processType();
        });

        mBinding.llMember.setOnClickListener(v -> {
            type = 2;
            processType();
        });

        mBinding.llOther.setOnClickListener(v -> {
            type = 3;
            processType();
        });

        mAdapter = new HivsAbsSampleAdapter<MemberGroupingVo>(R.layout.circle_item_circle_group_select, BR.item) {
            @Override
            public void onRealviewBind(ViewHolder holder, int position) {
                CircleItemCircleGroupSelectBinding itembinding = (CircleItemCircleGroupSelectBinding) holder.getBinding();
                itembinding.itemAudienceIv.setSelected(getItem(position).isClicked());
            }
        };
        mBinding.recycleGroup.setLayoutManager(new LinearLayoutManager(this));
        mBinding.recycleGroup.setAdapter(mAdapter);
        mViewModel.getMemberGroup(mStaparam);
        mAdapter.setOnItemClickListener((v, p) -> {
            MemberGroupingVo groupingVo = (MemberGroupingVo) mAdapter.getItem(p);
            groupingVo.setClicked(!groupingVo.isClicked());
            mAdapter.notifyItemRangeChanged(p, 1);
        });
    }

    private void processType() {

        if (type == 3) {
            isClose = !isClose;
            mBinding.recycleGroup.setVisibility(isClose ? View.GONE : View.VISIBLE);
            mBinding.activityReleaseDynamicAudienceIvIsOpen.setImageResource(isClose ? R.mipmap.circle_arrow_down_icon : R.mipmap.circle_arrow_up_icon);
        } else {
            isClose = true;
            mBinding.recycleGroup.setVisibility(isClose ? View.GONE : View.VISIBLE);
            mBinding.activityReleaseDynamicAudienceIvIsOpen.setImageResource(isClose ? R.mipmap.circle_arrow_down_icon : R.mipmap.circle_arrow_up_icon);
            for (int i = 0; i < mAdapter.getmObjects().size(); i++) {
                MemberGroupingVo vo = (MemberGroupingVo) mAdapter.getmObjects().get(i);
                vo.setClicked(false);
            }
            mAdapter.notifyDataSetChanged();
        }
        mBinding.activityReleaseDynamicAudienceIvPublic.setVisibility(type == 1 ? View.VISIBLE : View.INVISIBLE);
        mBinding.activityReleaseDynamicAudienceIvPublic.setSelected(type == 1 ? true : false);

        mBinding.activityReleaseDynamicAudienceIvMember.setVisibility(type == 2 ? View.VISIBLE : View.INVISIBLE);
        mBinding.activityReleaseDynamicAudienceIvMember.setSelected(type == 2 ? true : false);

        mBinding.activityReleaseDynamicAudienceIvOther.setVisibility(type == 3 ? View.VISIBLE : View.INVISIBLE);
        mBinding.activityReleaseDynamicAudienceIvOther.setSelected(type == 3 ? true : false);

    }

    @Override
    public void OnVmEnentListner(ViewEventResouce viewEventResouce) {
        super.OnVmEnentListner(viewEventResouce);
        if (viewEventResouce.eventType == 112) {
            if (viewEventResouce.data != null && ((Collection) viewEventResouce.data).size() > 0) {
                mAdapter.getmObjects().addAll((Collection) viewEventResouce.data);
                mAdapter.notifyDataSetChanged();
            } else {
                // 没有分组 不存在吧？
            }
        }
    }
}
