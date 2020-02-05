package com.bfhd.circle.ui.circle;

import android.arch.lifecycle.ViewModelProvider;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.view.View;

import com.alibaba.android.arouter.launcher.ARouter;
import com.bfhd.circle.BR;
import com.bfhd.circle.R;
import com.bfhd.circle.base.HivsBaseActivity;
import com.bfhd.circle.base.ViewEventResouce;
import com.bfhd.circle.base.adapter.HivsAbsSampleAdapter;
import com.bfhd.circle.databinding.CircleActivityCirclePersonListBinding;
import com.bfhd.circle.databinding.CircleItemCircleInnerPersionBinding;
import com.bfhd.circle.ui.adapter.CirclePersionListAdapter;
import com.bfhd.circle.vm.CirclePersonViewModel;
import com.bfhd.circle.vo.CircleDetailVo;
import com.bfhd.circle.vo.TradingCommonVo;
import com.bfhd.circle.vo.bean.StaCirParam;
import com.bfhd.circle.vo.bean.StaPersionDetail;
import com.bumptech.glide.Glide;
import com.docker.common.common.router.AppRouter;
import com.docker.common.common.utils.cache.CacheUtils;
import com.docker.common.common.utils.rxbus.RxBus;
import com.docker.common.common.utils.rxbus.RxEvent;
import com.docker.common.common.vo.UserInfoVo;
import com.docker.core.widget.BottomSheetDialog;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import javax.inject.Inject;

import io.reactivex.disposables.Disposable;

/*
 * 圈子成员
 * */
public class CirclePersonListActivity extends HivsBaseActivity<CirclePersonViewModel, CircleActivityCirclePersonListBinding> {

    private HivsAbsSampleAdapter mInnerAdapter;

    private CirclePersionListAdapter mOuterAdapter;

    private StaCirParam mStartParam;

    private CircleDetailVo mCircleDetailVo; // 圈子详情传过来的数据 后端真给力 emmm

    private boolean innerVisb = true;

    private Disposable disposable;

    private boolean isMyCircle = false;

    @Inject
    ViewModelProvider.Factory factory;

    @Override
    protected int getLayoutId() {
        return R.layout.circle_activity_circle_person_list;
    }

    public static void startMe(Context context, StaCirParam startCircleBean, CircleDetailVo circleDetailVo) {
        Intent intent = new Intent(context, CirclePersonListActivity.class);
        Bundle bundle = new Bundle();
        bundle.putSerializable("mStartParam", startCircleBean);
        bundle.putSerializable("mCircleDetailVo", circleDetailVo);
        intent.putExtras(bundle);
        context.startActivity(intent);
    }

    @Override
    public CirclePersonViewModel getmViewModel() {
        return ViewModelProviders.of(this, factory).get(CirclePersonViewModel.class);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        mStartParam = (StaCirParam) getIntent().getSerializableExtra("mStartParam");
        mCircleDetailVo = (CircleDetailVo) getIntent().getSerializableExtra("mCircleDetailVo");
        super.onCreate(savedInstanceState);
        mBinding.setViewmodel(mViewModel);
        mBinding.refresh.setEnablePureScrollMode(true);
        mViewModel.getInnerPersonList(mStartParam);
        disposable = RxBus.getDefault().toObservable(RxEvent.class).subscribe(rxEvent -> {
            if (rxEvent.getT().equals("refresh_person_list")) {
                mViewModel.getInnerPersonList(mStartParam);
            }
        });
    }

    @Override
    public void initView() {
        mToolbar.setTitle("圈子成员");
        if (mStartParam.role > 0) {
            mToolbar.setIvRight(R.mipmap.editor_image1, v -> {
                BottomSheetDialog bottomSheetDialog = new BottomSheetDialog();
                String[] bar;
                bar = new String[]{"邀请新成员", "设置分组权限", "分组管理"};
                bottomSheetDialog.setDataCallback(bar, position -> {
                    bottomSheetDialog.dismiss();
                    switch (position) {
                        case 0:
                            CircleFriendShareActivity.startMe(CirclePersonListActivity.this, mStartParam,mCircleDetailVo.getCircleName(),mCircleDetailVo.getLogoUrl());

                            break;
                        case 1:
                            CircleGroupPerssionActivity.startMe(CirclePersonListActivity.this, mStartParam);
                            break;
                        case 2:
                            CircleEditMemberGroupActivity.startMe(this, mStartParam);
                            //分组管理 todo
                            break;
                    }
                });
                bottomSheetDialog.show(this);
            });
        }

        ArrayList<Integer> darbles = new ArrayList<Integer>();
        darbles.add(R.drawable.circle_ov_shape);
        darbles.add(R.drawable.circle_ov_shape1);
        darbles.add(R.drawable.circle_ov_shape3);
        darbles.add(R.drawable.circle_ov_shape4);

        mInnerAdapter = new HivsAbsSampleAdapter(R.layout.circle_item_circle_inner_persion, BR.item) {
            @Override
            public void onRealviewBind(ViewHolder holder, int position) {
                CircleItemCircleInnerPersionBinding binding = (CircleItemCircleInnerPersionBinding) holder.getBinding();
                Random random = new Random();
                int i = random.nextInt(darbles.size());
                binding.roundIcon.setBackgroundDrawable(getResources().getDrawable(darbles.get(i)));
            }
        };
        mBinding.recyclerinner.setAdapter(mInnerAdapter);

        mInnerAdapter.setOnItemClickListener((v, p) -> {
            if(mInnerAdapter.getmObjects().size()>=p){
                processSettingPersion((TradingCommonVo) mInnerAdapter.getItem(p));
            }
        });

        mOuterAdapter = new CirclePersionListAdapter(this);
        mBinding.recyclerout.setAdapter(mOuterAdapter);
        mOuterAdapter.setOnItemCilckListener((section, position) -> {
            TradingCommonVo commonVo = mOuterAdapter.getData().get(section).getEmployee().get(position);
            processSettingPersion(commonVo);
        });

        mBinding.llInnerTitleCoutainer.setOnClickListener(v -> {
            innerVisb = !innerVisb;
            if (innerVisb) {
                mBinding.recyclerinner.setVisibility(View.VISIBLE);
                Glide.with(this).load(R.mipmap.circle_arrow_down_icon).into(mBinding.ivOutArrow);
            } else {
                mBinding.recyclerinner.setVisibility(View.GONE);
//                Glide.with(this).load(R.mipmap.arrow_right).into(mBinding.ivOutArrow);
            }
        });
    }

    /*
    0 普通成员
    1 管理员          只有圈住可以设置管理员
    2 圈主
     * 设置成员*/
    private void processSettingPersion(TradingCommonVo commonVo) {

        if (isMyCircle) {
            CirclePersionSettingActivity.startMe(CirclePersonListActivity.this, mStartParam, commonVo, 2);
        } else {
            if (mCircleDetailVo.getRole() > 0) {
                CirclePersionSettingActivity.startMe(CirclePersonListActivity.this, mStartParam, commonVo, mCircleDetailVo.getRole());
            } else {
                StaPersionDetail msta = new StaPersionDetail();
                msta.uuid = commonVo.getUuid();
                msta.uid = commonVo.getMemberid();
                msta.name = commonVo.getNickname();
//                CirclePersonDetailActivity.startMe(CirclePersonListActivity.this, msta);
//                ARouter.getInstance().build(AppRouter.CIRCLE_persion_detail).withSerializable("mStartParam", msta).navigation();
                ARouter.getInstance().build(AppRouter.CIRCLE_person_info).withString("memberid2", msta.uid).withString("uuid2", msta.uuid).navigation();


            }
        }
    }

    @Override
    public void OnVmEnentListner(ViewEventResouce viewEventResouce) {
        super.OnVmEnentListner(viewEventResouce);
        switch (viewEventResouce.eventType) {
            case 103: //
                mInnerAdapter.getmObjects().clear();
                UserInfoVo userInfoVo = CacheUtils.getUser();
                if (viewEventResouce.data == null || ((List<TradingCommonVo>) viewEventResouce.data).size() == 0) {
                    mBinding.empty.showNodata();
                    return;
                }
                TradingCommonVo createBean = null;
                for (int i = 0; i < ((List<TradingCommonVo>) viewEventResouce.data).size(); i++) {
                    TradingCommonVo commonVo = ((List<TradingCommonVo>) viewEventResouce.data).get(i);
                    if (!TextUtils.isEmpty(commonVo.getMemberid())
                            && !TextUtils.isEmpty(mCircleDetailVo.getMemberid()) && commonVo.getMemberid().equals(mCircleDetailVo.getMemberid())) { // 圈主
                        createBean = commonVo;
                        createBean.setRole(2);
                    } else {
                        mInnerAdapter.getmObjects().add(commonVo);
                    }
                }
                if (createBean != null) {
                    mInnerAdapter.getmObjects().add(0, createBean);
                    if (createBean.getUuid().equals(userInfoVo.uuid) && createBean.getMemberid().equals(userInfoVo.uid)) { // 自己是圈主
                        isMyCircle = true;
                    }
                }
                mInnerAdapter.notifyDataSetChanged();
                mBinding.tvInnerNum.setText(String.valueOf(mInnerAdapter.getmObjects().size()));
                if (mCircleDetailVo.getRole() > 0) { // 管理员可以查看全部成员
                    mViewModel.getOuterPersonList(mStartParam);
                }
                break;
            case 104:
                mOuterAdapter.getData().clear();
                mOuterAdapter.setData((List<TradingCommonVo>) viewEventResouce.data);
                break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            if (mCircleDetailVo.getRole() > 0) { // 管理员可以查看全部成员
                mViewModel.getOuterPersonList(mStartParam);
            }
        }
    }
}
