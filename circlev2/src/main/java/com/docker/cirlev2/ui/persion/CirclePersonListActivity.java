package com.docker.cirlev2.ui.persion;

import android.arch.lifecycle.ViewModelProviders;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.view.View;

import com.alibaba.android.arouter.launcher.ARouter;
import com.bumptech.glide.Glide;
import com.docker.cirlev2.BR;
import com.docker.cirlev2.R;
import com.docker.cirlev2.databinding.Circlev2ActivityPersonListBinding;
import com.docker.cirlev2.databinding.Circlev2ItemCircleInnerPersionBinding;
import com.docker.cirlev2.ui.detail.CircleEditMemberGroupActivity;
import com.docker.cirlev2.ui.detail.CircleInviteActivity;
import com.docker.cirlev2.util.CirclePersionListAdapter;
import com.docker.cirlev2.vm.CirclePersionViewModel;
import com.docker.cirlev2.vo.entity.CircleDetailVo;
import com.docker.cirlev2.vo.entity.TradingCommonVo;
import com.docker.cirlev2.vo.param.StaCirParam;
import com.docker.cirlev2.vo.param.StaPersionDetail;
import com.docker.common.common.adapter.NitAbsSampleAdapter;
import com.docker.common.common.command.NitContainerCommand;
import com.docker.common.common.ui.base.NitCommonActivity;
import com.docker.common.common.utils.cache.CacheUtils;
import com.docker.common.common.utils.rxbus.RxBus;
import com.docker.common.common.utils.rxbus.RxEvent;
import com.docker.common.common.vo.UserInfoVo;
import com.docker.core.widget.BottomSheetDialog;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import io.reactivex.disposables.Disposable;

/*
 * 圈子成员
 * */
public class CirclePersonListActivity extends NitCommonActivity<CirclePersionViewModel, Circlev2ActivityPersonListBinding> {

    private NitAbsSampleAdapter mInnerAdapter;

    private CirclePersionListAdapter mOuterAdapter;

    private StaCirParam mStartParam;

    private CircleDetailVo mCircleDetailVo; // 圈子详情传过来的数据 后端真给力 emmm

    private boolean innerVisb = true;

    private Disposable disposable;

    private boolean isMyCircle = false;

    @Override
    protected int getLayoutId() {
        return R.layout.circlev2_activity_person_list;
    }

    @Override
    public CirclePersionViewModel getmViewModel() {
        return ViewModelProviders.of(this, factory).get(CirclePersionViewModel.class);
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

        }

        mToolbar.setIvRight(R.mipmap.open_meun, v -> {
            BottomSheetDialog bottomSheetDialog = new BottomSheetDialog();
            String[] bar;
            bar = new String[]{"邀请新成员", "设置分组权限", "分组管理"};
            bottomSheetDialog.setDataCallback(bar, position -> {
                bottomSheetDialog.dismiss();
                switch (position) {
                    case 0:
                        CircleInviteActivity.startMe(CirclePersonListActivity.this,
                                mStartParam, mCircleDetailVo.getCircleName(),
                                mCircleDetailVo.getLogoUrl());
                        break;
                    case 1://设置分组权限
                            CircleGroupPerssionActivity.startMe(CirclePersonListActivity.this, mStartParam);
                        break;
                    case 2: //分组管理
                        CircleEditMemberGroupActivity.startMe(this, mStartParam);
                        break;
                }
            });
            bottomSheetDialog.show(this);
        });

        ArrayList<Integer> darbles = new ArrayList<Integer>();
        darbles.add(R.drawable.circle_ov_shape);
        darbles.add(R.drawable.circle_ov_shape1);
        darbles.add(R.drawable.circle_ov_shape3);
        darbles.add(R.drawable.circle_ov_shape4);

        mInnerAdapter = new NitAbsSampleAdapter(R.layout.circlev2_item_circle_inner_persion, BR.item) {
            @Override
            public void onRealviewBind(ViewHolder holder, int position) {
                Circlev2ItemCircleInnerPersionBinding binding = (Circlev2ItemCircleInnerPersionBinding) holder.getBinding();
                Random random = new Random();
                int i = random.nextInt(darbles.size());
                binding.roundIcon.setBackgroundDrawable(getResources().getDrawable(darbles.get(i)));
            }
        };
        mBinding.recyclerinner.setAdapter(mInnerAdapter);

        mInnerAdapter.setOnItemClickListener((v, p) -> {
            if (mInnerAdapter.getmObjects().size() >= p) {
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
                Glide.with(this).load(R.mipmap.arrow_right).into(mBinding.ivOutArrow);
            }
        });
    }

    @Override
    public void initObserver() {

        mViewModel.mInnerTradingLV.observe(this, tradingCommonVos -> {
            mInnerAdapter.getmObjects().clear();
            UserInfoVo userInfoVo = CacheUtils.getUser();
            if (tradingCommonVos == null || ((List<TradingCommonVo>) tradingCommonVos).size() == 0) {
                mBinding.empty.showNodata();
                return;
            }
            TradingCommonVo createBean = null;
            for (int i = 0; i < ((List<TradingCommonVo>) tradingCommonVos).size(); i++) {
                TradingCommonVo commonVo = ((List<TradingCommonVo>) tradingCommonVos).get(i);
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
            mBinding.empty.hide();
        });

        mViewModel.mOuterTradingLV.observe(this, tradingCommonVos -> {
            mOuterAdapter.getData().clear();
            mOuterAdapter.setData(tradingCommonVos);
        });

    }

    @Override
    public void initRouter() {

    }

    @Override
    public NitContainerCommand providerNitContainerCommand(int flag) {
        return null;
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
                CirclePersonDetailActivity.startMe(CirclePersonListActivity.this, msta);
//                ARouter.getInstance().build(AppRouter.CIRCLE_persion_detail).withSerializable("mStartParam", msta).navigation();
            }
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

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (disposable != null) {
            disposable.dispose();
        }
    }
}
