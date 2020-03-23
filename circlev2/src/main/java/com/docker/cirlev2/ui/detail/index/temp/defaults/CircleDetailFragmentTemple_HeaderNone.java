package com.docker.cirlev2.ui.detail.index.temp.defaults;

import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.view.View;

import com.docker.cirlev2.ui.CircleInfoActivity;
import com.docker.cirlev2.ui.detail.CircleInviteActivity;
import com.docker.cirlev2.vo.entity.CircleTitlesVo;
import com.docker.cirlev2.vo.param.StaCirParam;
import com.docker.common.common.command.NitDelegetCommand;
import com.docker.core.widget.BottomSheetDialog;
import com.gyf.immersionbar.ImmersionBar;

import java.util.List;

public class CircleDetailFragmentTemple_HeaderNone extends NitDefaultCircleFragment {

    @Override
    public NitDelegetCommand providerNitDelegetCommand(int flag) {
        return null;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        if (!TextUtils.isEmpty(circleConfig.extens.get("title"))) {
            mBinding.get().title.setText(circleConfig.extens.get("title"));
            mBinding.get().toolbar.setBackgroundColor(Color.WHITE);
        } else {
            mBinding.get().toolbar.setVisibility(View.GONE);
        }

        if (!circleConfig.isNeedToobar) {
            mBinding.get().toolbarLayout.setVisibility(View.GONE);
        }
    }

    @Override
    protected void initView(View var1) {
        super.initView(var1);
        mBinding.get().ivShare.setVisibility(View.GONE);
        mBinding.get().circlev2Edit.setVisibility(View.GONE);

    }

    @Override
    public void initAppBar() {

        mBinding.get().ivBack.setImageResource(com.docker.core.R.mipmap.ic_back);
        mBinding.get().ivBack.setBackgroundDrawable(null);
        mBinding.get().frameHeader.setVisibility(View.GONE);


//        mBinding.get().ivMenuMore.setVisibility(View.GONE);
//        mBinding.get().vd_toolbar.setVisibility(View.GONE);
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
    public void onCircleTabFetched(List<CircleTitlesVo> list) {
        super.onCircleTabFetched(list);
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
        super.initImmersionBar();
//        View view = null;
//        if (!TextUtils.isEmpty(circleConfig.extens.get("title"))) {
//            view = mBinding.get().vd_toolbar;
//            mBinding.get().toolbarLayout.setVisibility(View.VISIBLE);
//            ImmersionBar.with(this)
//                    .navigationBarColor("#ffffff")
//                    .statusBarColor("#ffffff")
//                    .statusBarDarkFont(true)
//                    .titleBarMarginTop(view)
//                    .init();
//        } else {
////            view = mBinding.get().llStickCoutainer;
//        }
//

    }
}
