package com.docker.cirlev2.ui.publish.select;

import android.arch.lifecycle.ViewModelProvider;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.view.View;

import com.docker.cirlev2.BR;
import com.docker.cirlev2.R;
import com.docker.cirlev2.databinding.Circlev2ActivityPerssionSelectBinding;
import com.docker.cirlev2.databinding.Circlev2ItemCircleGroupSelectBinding;
import com.docker.cirlev2.vm.PublishViewModel;
import com.docker.cirlev2.vo.entity.MemberGroupingVo;
import com.docker.cirlev2.vo.param.StaCirParam;
import com.docker.common.common.adapter.NitAbsSampleAdapter;
import com.docker.common.common.command.NitContainerCommand;
import com.docker.common.common.ui.base.NitCommonActivity;
import com.docker.common.common.utils.rxbus.RxBus;
import com.docker.common.common.utils.rxbus.RxEvent;

import java.util.Collection;

import javax.inject.Inject;

/*
 * 谁可以看
 * */
public class CirclePerssionSelectActivity extends NitCommonActivity<PublishViewModel, Circlev2ActivityPerssionSelectBinding> {

    @Inject
    ViewModelProvider.Factory factory;
    /*
     * 1,2,3
     * */
    private int type = 1;
    private boolean isClose = true;
    private StaCirParam mStaparam;
    private NitAbsSampleAdapter mAdapter;

    @Override
    protected int getLayoutId() {
        return R.layout.circlev2_activity_perssion_select;
    }

    @Override
    public PublishViewModel getmViewModel() {
        return ViewModelProviders.of(this, factory).get(PublishViewModel.class);
    }

    public static void startMe(Context context, StaCirParam staCirParam) {
        Intent intent = new Intent(context, CirclePerssionSelectActivity.class);
        intent.putExtra("mStaparam", staCirParam);
        context.startActivity(intent);
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

        mViewModel.mMemberGroupLV.observe(this, memberGroupingVos -> {
            if (memberGroupingVos != null && ((Collection) memberGroupingVos).size() > 0) {
                mAdapter.getmObjects().addAll((Collection) memberGroupingVos);
                mAdapter.notifyDataSetChanged();
            } else {
                // 没有分组 不存在吧？
            }
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

        mAdapter = new NitAbsSampleAdapter<MemberGroupingVo>(R.layout.circlev2_item_circle_group_select, BR.item) {
            @Override
            public void onRealviewBind(ViewHolder holder, int position) {
                Circlev2ItemCircleGroupSelectBinding itembinding = (Circlev2ItemCircleGroupSelectBinding) holder.getBinding();
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

    @Override
    public void initObserver() {

    }

    @Override
    public void initRouter() {

    }

    @Override
    public NitContainerCommand providerNitContainerCommand(int flag) {
        return null;
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

}
