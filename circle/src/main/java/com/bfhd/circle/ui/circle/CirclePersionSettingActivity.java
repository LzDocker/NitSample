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
import com.bfhd.circle.R;
import com.bfhd.circle.base.HivsBaseActivity;
import com.bfhd.circle.base.ViewEventResouce;
import com.bfhd.circle.databinding.CircleActivityCirclePersionSettingBinding;
import com.bfhd.circle.vm.CircleViewModel;
import com.bfhd.circle.vo.MemberGroupingVo;
import com.bfhd.circle.vo.MemberSettingsVo;
import com.bfhd.circle.vo.PersionPerssionVo;
import com.bfhd.circle.vo.TradingCommonVo;
import com.bfhd.circle.vo.bean.StaCirParam;
import com.bfhd.circle.vo.bean.StaPersionDetail;
import com.dcbfhd.utilcode.utils.ToastUtils;
import com.docker.common.common.router.AppRouter;
import com.docker.common.common.utils.cache.CacheUtils;
import com.docker.common.common.utils.rxbus.RxBus;
import com.docker.common.common.utils.rxbus.RxEvent;
import com.docker.common.common.vo.UserInfoVo;

import java.util.HashMap;

import javax.inject.Inject;

/*
 * 设置成员
 * */
public class CirclePersionSettingActivity extends HivsBaseActivity<CircleViewModel, CircleActivityCirclePersionSettingBinding> {

    @Inject
    ViewModelProvider.Factory factory;
    //    private int type;
    private StaCirParam mStartParam;
    private TradingCommonVo mCommonVo;
    private MemberSettingsVo memberSettingsVo;
    private int mRole;
    private PersionPerssionVo mvo;
    private MemberGroupingVo modifyGroupVo;
    private UserInfoVo userInfoVo;


    public static void startMe(Context context, StaCirParam startCircleBean, TradingCommonVo commonVo, int mRole) {

        Intent intent = new Intent(context, CirclePersionSettingActivity.class);
        Bundle bundle = new Bundle();
        bundle.putSerializable("mStartParam", startCircleBean);
        bundle.putSerializable("TradingCommonVo", commonVo);
        bundle.putSerializable("mRole", mRole);
        intent.putExtras(bundle);
        context.startActivity(intent);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.circle_activity_circle_persion_setting;
    }

    @Override
    public CircleViewModel getmViewModel() {
        return ViewModelProviders.of(this, factory).get(CircleViewModel.class);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        mStartParam = (StaCirParam) getIntent().getSerializableExtra("mStartParam");
        mCommonVo = (TradingCommonVo) getIntent().getSerializableExtra("TradingCommonVo");
        mRole = getIntent().getIntExtra("mRole", 0);
        super.onCreate(savedInstanceState);
        userInfoVo = CacheUtils.getUser();
        mBinding.roundIcon.setOnClickListener(v -> {
            if (memberSettingsVo != null) {
                StaPersionDetail msta = new StaPersionDetail();
                msta.uuid = memberSettingsVo.getUuid();
                msta.uid = memberSettingsVo.getMemberid();
                msta.name = memberSettingsVo.getNickname();
//                CirclePersonDetailActivity.startMe(CirclePersionSettingActivity.this, msta);
                ARouter.getInstance().build(AppRouter.CIRCLE_persion_detail).withSerializable("mStartParam", msta).navigation();
            }
        });
        mViewModel.getSettingMember(mStartParam, mCommonVo.getMemberid(), mCommonVo.getUuid());
        mBinding.llFullname.setOnClickListener(v -> {
            // 修改备注
            CircleModifyActivity.startMe(this, mBinding.tvFullname.getText().toString(), 101);
        });

        if (mRole == 2) { // 圈主
            if (mCommonVo.getMemberid().equals(userInfoVo.uid)) {        //是否是自己
                mBinding.llPersLiner.setVisibility(View.GONE);
                mBinding.tvDel.setVisibility(View.GONE);
            } else {
                mBinding.llPersLiner.setVisibility(View.VISIBLE);
                mBinding.tvDel.setVisibility(View.VISIBLE);
            }
        } else if (mRole == 1) { // 管理员
            if (mCommonVo.getMemberid().equals(userInfoVo.uid)) {        //是否是自己
                mBinding.llPersLiner.setVisibility(View.GONE);
                mBinding.tvDel.setVisibility(View.GONE);
            } else {
                mBinding.llPersLiner.setVisibility(View.VISIBLE);
                mBinding.tvDel.setVisibility(View.VISIBLE);
            }

            if (mCommonVo.getRole() == 2) { // 管理员--进入圈主 只能设置备注
                mBinding.llPersLiner.setVisibility(View.GONE);
                mBinding.tvDel.setVisibility(View.GONE);
            }
        }

        mBinding.tvSubmit.setOnClickListener(v -> {
            // 保存设置
            if (TextUtils.isEmpty(mBinding.tvFullname.getText())) {
                ToastUtils.showShort("备注名称不能为空！");
                return;
            }


            HashMap<String, String> params = new HashMap<>();
            params.put("role", mvo.isManager ? "1" : "0");
            params.put("isComment", mvo.isComment ? "1" : "0");
            params.put("isPublishDynamic", mvo.isPublish ? "1" : "0");
            if (modifyGroupVo != null && !TextUtils.isEmpty(modifyGroupVo.getGroupid())) {
                if (TextUtils.isEmpty(modifyGroupVo.getGroupid())) {
                    params.put("groupid", "-1");
                } else {
                    params.put("groupid", modifyGroupVo.getGroupid());

                }

            } else {

                if (TextUtils.isEmpty(memberSettingsVo.getGroupid())) {
                    params.put("groupid", "-1");
                } else {
                    params.put("groupid", memberSettingsVo.getGroupid());

                }
            }
            mViewModel.updateSettingMember(mStartParam, mCommonVo.getMemberid(), mCommonVo.getUuid(), mBinding.tvFullname.getText().toString().trim(), params);
        });

        mBinding.tvDel.setOnClickListener(v -> {
            // del
            if (mCommonVo.getMemberid().equals(userInfoVo.uid)) {
                ToastUtils.showShort("不能删除自己！");
                return;
            }
            mViewModel.quitCircle(mStartParam, mCommonVo.getMemberid(), mCommonVo.getUuid());
        });

        if (mRole > 0) {
            mBinding.llManagerCoutainer.setVisibility(View.VISIBLE);
        } else {
            mBinding.llManagerCoutainer.setVisibility(View.GONE);
        }

        mBinding.llGroup.setOnClickListener(v -> { // 分组
            CircleMemberGroupListActivity.startMe(this, mStartParam, 102);
        });

        mBinding.llPerssion.setOnClickListener(v -> { // 权限

            CirclePersonPerssionActivity.startMe(this, mStartParam, 103, mvo);
        });
    }

    @Override
    public void initView() {
        mToolbar.setTitle("设置成员");

    }

    @Override
    public void OnVmEnentListner(ViewEventResouce viewEventResouce) {
        super.OnVmEnentListner(viewEventResouce);
        switch (viewEventResouce.eventType) {
            case 110: // 获取数据成功
                memberSettingsVo = (MemberSettingsVo) viewEventResouce.data;
                mBinding.setPersonVo(memberSettingsVo);

                if (memberSettingsVo != null) {
                    boolean ismanager = false;
                    boolean iscomment = false;
                    boolean ispublish = false;
                    if ("1".equals(memberSettingsVo.getRole())) {
                        ismanager = true;
                    }
                    if ("1".equals(memberSettingsVo.getIsComment())) {
                        iscomment = true;
                    }
                    if ("1".equals(memberSettingsVo.getIsPublishDynamic())) {
                        ispublish = true;
                    }
                    mvo = new PersionPerssionVo(ismanager, iscomment, ispublish);
                }

                break;
            case 111:
                RxBus.getDefault().post(new RxEvent<>("refresh_person_list", null));
//                setResult(RESULT_OK);
                finish();
                break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK && requestCode == 101) {
            mBinding.tvFullname.setText(data.getStringExtra("fullname"));
        }
        if (resultCode == RESULT_OK && requestCode == 102) {
            modifyGroupVo = (MemberGroupingVo) data.getSerializableExtra("group");
            mBinding.tvGroup.setText(modifyGroupVo.getGroupName());
        }
        if (resultCode == RESULT_OK && requestCode == 103) {
            mvo = (PersionPerssionVo) data.getSerializableExtra("perssion");
        }

    }
}
