package com.docker.cirlev2.inter.frame.example;

import android.view.View;

import com.alibaba.android.arouter.launcher.ARouter;
import com.docker.cirlev2.ui.CircleInfoActivity;
import com.docker.cirlev2.ui.detail.CircleInviteActivity;
import com.docker.cirlev2.vo.card.AppImgHeaderCardVo;
import com.docker.cirlev2.vo.param.StaCirParam;
import com.docker.cirlev2.vo.pro.AppVo;
import com.docker.common.common.adapter.NitAbsSampleAdapter;
import com.docker.common.common.command.NitDelegetCommand;
import com.docker.common.common.router.AppRouter;
import com.docker.common.common.ui.base.NitCommonFragment;
import com.docker.common.common.vm.NitCommonListVm;
import com.docker.common.common.widget.card.NitBaseProviderCard;
import com.docker.core.widget.BottomSheetDialog;

import static com.docker.cirlev2.ui.publish.CirclePublishActivity.PUBLISH_TYPE_NEWS;

public class CircleDetailFragmentTemple_HeaderImg extends NitDefaultCircleFragment {

    private NitCommonListVm outerVm;

    @Override
    public NitDelegetCommand providerNitDelegetCommand(int flag) {
        NitDelegetCommand nitDelegetCommand = new NitDelegetCommand() {
            @Override
            public Class providerOuterVm() {
                return null;
            }

            @Override
            public void next(NitCommonListVm commonListVm, NitCommonFragment nitCommonFragment) {
                outerVm = commonListVm;
                AppImgHeaderCardVo appImgHeaderCardVo = new AppImgHeaderCardVo(0, 1);

//                AppBannerHeaderCardVo appBannerHeaderCardVo = new AppBannerHeaderCardVo(0, 0);

                NitBaseProviderCard.providerCard(commonListVm, appImgHeaderCardVo, nitCommonFragment);
//                NitBaseProviderCard.providerCard(commonListVm, appBannerHeaderCardVo, nitCommonFragment);
            }
        };
        return nitDelegetCommand;
    }

    @Override
    protected void initView(View var1) {
        super.initView(var1);
        mBinding.get().ivShare.setVisibility(View.GONE);
        mBinding.get().circlev2Edit.setVisibility(View.GONE);
    }

    @Override
    public void onRefreshIng() {
        super.onRefreshIng();
        outerVm.onJustRefresh();
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
    public void processPro(NitAbsSampleAdapter mAdapter) {
        super.processPro(mAdapter);

        AppVo appVo = new AppVo();
        appVo.name = "文章";
        appVo.id = "1";
        mAdapter.add(appVo);
    }

    @Override
    public void onAppClick(AppVo appVo) {
        super.onAppClick(appVo);
    }
}
