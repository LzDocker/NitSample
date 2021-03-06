package com.docker.cirlev2.ui.detail.index.type;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;

import com.alibaba.android.arouter.facade.Postcard;
import com.docker.cirlev2.R;
import com.docker.cirlev2.ui.detail.index.temp.defaults.NitDefaultCircleFragment;
import com.docker.cirlev2.ui.persion.CirclePersonListActivity;
import com.docker.cirlev2.vo.card.AppImgHeaderCardVo;
import com.docker.cirlev2.vo.entity.CircleDetailVo;
import com.docker.cirlev2.vo.entity.CircleTitlesVo;
import com.docker.cirlev2.vo.param.StaCirParam;
import com.docker.cirlev2.vo.pro.AppVo;
import com.docker.common.common.adapter.NitAbsSampleAdapter;
import com.docker.common.common.command.NitDelegetCommand;
import com.docker.common.common.ui.base.NitCommonFragment;
import com.docker.common.common.utils.cache.CacheUtils;
import com.docker.common.common.vm.NitCommonListVm;
import com.docker.common.common.widget.card.NitBaseProviderCard;
import com.docker.core.widget.BottomSheetDialog;
import com.gyf.immersionbar.ImmersionBar;

import java.util.HashMap;
import java.util.List;

public class CircleDetailFragment_gdh extends NitDefaultCircleFragment {
    AppImgHeaderCardVo appImgHeaderCardVo;

    @Override
    public NitDelegetCommand providerNitDelegetCommand(int flag) {
        NitDelegetCommand nitDelegetCommand = new NitDelegetCommand() {
            @Override
            public Class providerOuterVm() {
                return null;
            }

            @Override
            public void next(NitCommonListVm commonListVm, NitCommonFragment nitCommonFragment) {
                NitBaseProviderCard.providerCard(commonListVm, appImgHeaderCardVo, nitCommonFragment);
            }
        };
        return nitDelegetCommand;
    }


    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mBinding.get().title.setText(circleConfig.extens.get("title"));
        appImgHeaderCardVo = new AppImgHeaderCardVo(0, 1);
    }

    @Override
    protected void initView(View var1) {
        super.initView(var1);
        mBinding.get().ivShare.setVisibility(View.GONE);
        mBinding.get().circlev2Edit.setVisibility(View.GONE);

    }

    @Override
    public void onCircleDetailFetched(CircleDetailVo circleDetailVo) {
        super.onCircleDetailFetched(circleDetailVo);
//        mBinding.get().circlev2IvPublish.setVisibility(View.VISIBLE);
        appImgHeaderCardVo.imgurl.set(circleDetailVo.getSurfaceImg());
        appImgHeaderCardVo.logourl.set(circleDetailVo.getLogoUrl());
    }

    @Override
    public void initAppBar() {
        super.initAppBar();
//        mBinding.get().frameHeader.setVisibility(View.GONE);

    }

    @Override
    public void onCircleTabFetched(List<CircleTitlesVo> list) {
        super.onCircleTabFetched(list);
    }

    @Override
    public void onCircleMenuClick() {
//        super.onCircleMenuClick();
        onShareClick();

//        BottomSheetDialog bottomSheetDialog = new BottomSheetDialog();
//        bottomSheetDialog.setDataCallback(new String[]{"成员管理", "编辑分类"}, position -> {
//            bottomSheetDialog.dismiss();
//            switch (position) {
//                case 0: //成员管理
//                    if (mViewModel.mCircleDetailLv.getValue() != null /*&& mViewModel.detailVo.get().getRole()> 0*/) {
//                        StaCirParam mStartParam = new StaCirParam();
//                        mStartParam.setCircleid(circleid);
//                        mStartParam.setUtid(utid);
//                        mStartParam.type = Integer.parseInt(mViewModel.mCircleDetailLv.getValue().getType());
//                        CirclePersonListActivity.startMe(this.getHoldingActivity(), mStartParam, mViewModel.mCircleDetailLv.getValue());
//                    }
//                    break;
//                case 1: //编辑分类
//                    onLevel1EditClick();
//                    break;
//            }
//        });
//        bottomSheetDialog.show(this.getHoldingActivity());
    }

    @Override
    public void initImmersionBar() {
        ImmersionBar.with(this)
                .titleBar(mBinding.get().toolbar)
                .navigationBarColor("#ffffff")
                .statusBarDarkFont(false)
                .init();
    }

    @Override
    public void processPro(NitAbsSampleAdapter mAdapter) {
        super.processPro(mAdapter);
        AppVo appVo = new AppVo();
        appVo.id = "4";
        appVo.name = "活动";
        appVo.icon = R.mipmap.publish_act;
        mAdapter.add(appVo);
    }

    @Override
    public void processPushSHow(CircleDetailVo circleDetailVo) {
        super.processPushSHow(circleDetailVo);
        if (CacheUtils.getUser() != null) {
            if ("1".equals(circleDetailVo.getIsJoin())) {
                mBinding.get().circlev2IvPublish.setVisibility(View.VISIBLE);
            } else {
                mBinding.get().circlev2IvPublish.setVisibility(View.GONE);
            }
        } else {
            mBinding.get().circlev2IvPublish.setVisibility(View.GONE);
        }
    }


    @Override
    public void processPubRouterNext(Postcard postcard) {
//        super.processPubRouterNext(postcard);
        HashMap<String, String> param = new HashMap<>();
        param.put("isShowBot", "1");
        postcard.withSerializable("extens", param).navigation();
    }
}
