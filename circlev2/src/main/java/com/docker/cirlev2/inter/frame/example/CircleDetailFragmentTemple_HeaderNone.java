package com.docker.cirlev2.inter.frame.example;

import android.view.View;

import com.docker.cirlev2.ui.CircleInfoActivity;
import com.docker.cirlev2.ui.detail.CircleInviteActivity;
import com.docker.cirlev2.vo.param.StaCirParam;
import com.docker.common.common.command.NitDelegetCommand;
import com.docker.core.widget.BottomSheetDialog;
import com.gyf.immersionbar.ImmersionBar;

public class CircleDetailFragmentTemple_HeaderNone extends NitDefaultCircleFragment {

    @Override
    public NitDelegetCommand providerNitDelegetCommand(int flag) {
        return null;
    }

    @Override
    protected void initView(View var1) {
        super.initView(var1);
        mBinding.get().ivShare.setVisibility(View.GONE);
        mBinding.get().circlev2Edit.setVisibility(View.GONE);
    }

    @Override
    public void initAppBar() {
        mBinding.get().title.setText("桃源志");
        mBinding.get().ivBack.setImageResource(com.docker.core.R.mipmap.ic_back);
        mBinding.get().ivBack.setBackgroundDrawable(null);

        mBinding.get().frameHeader.setVisibility(View.GONE);
        mBinding.get().toolbar.setVisibility(View.GONE);
    }

    @Override
    public void onRefreshIng() {
        super.onRefreshIng();
    }

    @Override
    public void initRefresh() {
        mBinding.get().refresh.setEnableLoadMore(false);
        mBinding.get().refresh.setEnableRefresh(false);
    }

    @Override
    public void onCircleMenuClick() {
//        super.onCircleMenuClick();
        BottomSheetDialog bottomSheetDialog = new BottomSheetDialog();
        bottomSheetDialog.setDataCallback(new String[]{"邀请新成员", "圈子简介"}, position -> {
            bottomSheetDialog.dismiss();
            switch (position) {
                case 0: //邀请新成员
                    if (mViewModel.mCircleDetailLv.getValue() != null) {//"邀请新成员",
                        StaCirParam mStartParam = new StaCirParam();
                        mStartParam.setCircleid(circleid);
                        mStartParam.setUtid(utid);
                        CircleInviteActivity.startMe(this.getHoldingActivity(), mStartParam,
                                mViewModel.mCircleDetailLv.getValue().getCircleName(),
                                mViewModel.mCircleDetailLv.getValue().getLogoUrl());
                    }
                    break;
                case 1: //圈子简介
                    if (mViewModel.mCircleDetailLv.getValue() != null) {
                        StaCirParam mStartParam = new StaCirParam();
                        mStartParam.setCircleid(circleid);
                        mStartParam.setUtid(utid);
                        mStartParam.type = Integer.parseInt(mViewModel.mCircleDetailLv.getValue().getType());
                        CircleInfoActivity.startMe(this.getHoldingActivity(), mStartParam);
                    }
                    break;
            }
        });
        bottomSheetDialog.show(this.getHoldingActivity());
    }

    @Override
    public void initImmersionBar() {
        ImmersionBar.with(this)
                .navigationBarColor("#ffffff")
                .statusBarColor("#ffffff")
                .statusBarDarkFont(true)
                .titleBarMarginTop(mBinding.get().llStickCoutainer)
                .init();
    }
}
