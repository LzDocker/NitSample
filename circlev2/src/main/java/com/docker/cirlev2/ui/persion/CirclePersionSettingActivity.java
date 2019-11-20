package com.docker.cirlev2.ui.persion;

import android.arch.lifecycle.ViewModelProviders;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.view.View;

import com.alibaba.android.arouter.launcher.ARouter;
import com.dcbfhd.utilcode.utils.ToastUtils;
import com.docker.cirlev2.R;
import com.docker.cirlev2.databinding.Circlev2ActivityCirclePersionSettingBinding;
import com.docker.cirlev2.ui.detail.CircleGroupListActivity;
import com.docker.cirlev2.vm.CirclePersionViewModel;
import com.docker.cirlev2.vo.entity.MemberGroupingVo;
import com.docker.cirlev2.vo.entity.MemberSettingsVo;
import com.docker.cirlev2.vo.entity.PersionPerssionVo;
import com.docker.cirlev2.vo.entity.TradingCommonVo;
import com.docker.cirlev2.vo.param.StaCirParam;
import com.docker.cirlev2.vo.param.StaPersionDetail;
import com.docker.common.common.command.NitContainerCommand;
import com.docker.common.common.router.AppRouter;
import com.docker.common.common.ui.base.NitCommonActivity;
import com.docker.common.common.utils.cache.CacheUtils;
import com.docker.common.common.utils.rxbus.RxBus;
import com.docker.common.common.utils.rxbus.RxEvent;
import com.docker.common.common.vo.UserInfoVo;

import java.util.HashMap;

/*
 * 设置成员
 * */
public class CirclePersionSettingActivity extends NitCommonActivity<CirclePersionViewModel, Circlev2ActivityCirclePersionSettingBinding> {

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
        return R.layout.circlev2_activity_circle_persion_setting;
    }

    @Override
    public CirclePersionViewModel getmViewModel() {
        return ViewModelProviders.of(this, factory).get(CirclePersionViewModel.class);
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
            CircleGroupListActivity.startMe(this, mStartParam, 102);
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
    public void initObserver() {

        mViewModel.mMemberSettingsLV.observe(this, memberSettingsVo -> {
            memberSettingsVo = memberSettingsVo;
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
        });
        mViewModel.mMemberSettingUpdateLV.observe(this, s -> {
            RxBus.getDefault().post(new RxEvent<>("refresh_person_list", null));
//                setResult(RESULT_OK);
            finish();
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
