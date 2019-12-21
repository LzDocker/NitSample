package com.docker.cirlev2.inter.frame;

import android.databinding.ViewDataBinding;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;

import com.alibaba.android.arouter.launcher.ARouter;
import com.dcbfhd.utilcode.utils.CollectionUtils;
import com.dcbfhd.utilcode.utils.ToastUtils;
import com.docker.cirlev2.ui.CircleInfoActivity;
import com.docker.cirlev2.ui.detail.CircleEditTabActivity;
import com.docker.cirlev2.ui.detail.CircleInviteActivity;
import com.docker.cirlev2.ui.persion.CirclePersonListActivity;
import com.docker.cirlev2.vo.entity.CircleDetailVo;
import com.docker.cirlev2.vo.entity.CircleTitlesVo;
import com.docker.cirlev2.vo.param.StaCirParam;
import com.docker.cirlev2.widget.popmen.SuperPopmenu;
import com.docker.common.common.router.AppRouter;
import com.docker.common.common.ui.base.NitCommonFragment;
import com.docker.common.common.utils.cache.CacheUtils;
import com.docker.common.common.vo.ShareBean;
import com.docker.common.common.vo.UserInfoVo;
import com.docker.core.widget.BottomSheetDialog;
import com.umeng.socialize.ShareAction;
import com.umeng.socialize.UMShareListener;
import com.umeng.socialize.bean.SHARE_MEDIA;
import com.umeng.socialize.media.UMImage;
import com.umeng.socialize.media.UMWeb;
import com.umeng.socialize.shareboard.ShareBoardConfig;

import java.util.HashMap;
import java.util.List;

public abstract class NitAbsCircleFragment<VM extends AbsCircleDetailIndexViewModel, VB extends ViewDataBinding> extends NitCommonFragment<VM, VB> {

    public String circleid;
    public String utid;
    public String circletype;
    public SuperPopmenu mPublishMenu;

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mViewModel.FetchCircleDetail(utid, circleid);
        initObserver();
    }

    public void initObserver() {
        mViewModel.mCircleDetailLv.observe(this, circleDetailVo -> {
            if (circleDetailVo != null) {
                onCircleDetailFetched(circleDetailVo);
                mViewModel.FetchCircleClass();
            }
        });

        mViewModel.mCircleClassLv.observe(this, circleTitlesVos -> {
            if (!CollectionUtils.isEmpty(circleTitlesVos)) {
                onCircleTabFetched(circleTitlesVos);
            }
        });

        mViewModel.mShareLv.observe(this, shareBean -> {
            if (shareBean != null) {
                showShare(shareBean);
            }
        });

        mViewModel.mJoninLv.observe(this, s -> {
        });
    }


    @Override
    protected void initView(View var1) {

    }


    @Override
    public void initImmersionBar() {

    }


    public abstract void onCircleDetailFetched(CircleDetailVo circleDetailVo);

    public abstract void onCircleTabFetched(List<CircleTitlesVo> circleTitlesVos);

    public void onShareClick() {
        if (mViewModel.mCircleDetailLv.getValue() != null) {
            HashMap<String, String> params = new HashMap<>();
            UserInfoVo userInfoVo = CacheUtils.getUser();
            params.put("type", "circle");
            params.put("circleid", circleid);
            params.put("memberid", userInfoVo.uid);
            params.put("uuid", userInfoVo.uuid);
            mViewModel.FetchShareData(params);
        }
    }

    private void showShare(ShareBean shareBean) {
        if (shareBean == null) {
            return;
        }
        ShareBoardConfig config = new ShareBoardConfig();//新建ShareBoardConfig
        config.setMenuItemBackgroundShape(ShareBoardConfig.BG_SHAPE_CIRCULAR);
        config.setTitleVisibility(false);
        config.setIndicatorVisibility(false);
        config.setCancelButtonVisibility(false);
        config.setCancelButtonVisibility(false);
        config.setShareboardBackgroundColor(Color.WHITE);
        UMImage image = new UMImage(this.getHoldingActivity(), shareBean.getShareImg());//网络图片
        image.compressStyle = UMImage.CompressStyle.SCALE;//大小压缩，默认为大小压缩，适合普通很大的图
        image.compressStyle = UMImage.CompressStyle.QUALITY;//质量压缩，适合长图的分
        UMWeb web = new UMWeb(shareBean.getShareUrl());
        web.setTitle(shareBean.getShareTit());//标题
        web.setThumb(image);  //缩略图
        web.setDescription(shareBean.getShareDesc());//描述
        new ShareAction(this.getHoldingActivity()).withMedia(web)
                .setDisplayList(SHARE_MEDIA.WEIXIN, SHARE_MEDIA.WEIXIN_CIRCLE, SHARE_MEDIA.WEIXIN_FAVORITE, SHARE_MEDIA.QQ, SHARE_MEDIA.QZONE)
                .setCallback(new UMShareListener() {
                    @Override
                    public void onStart(SHARE_MEDIA share_media) {

                    }

                    @Override
                    public void onResult(SHARE_MEDIA share_media) {

                    }

                    @Override
                    public void onError(SHARE_MEDIA share_media, Throwable throwable) {
                        ToastUtils.showShort("分享失败请重试");
                    }

                    @Override
                    public void onCancel(SHARE_MEDIA share_media) {

                    }
                }).open(config);
    }

    // 显示menu
    public void onCircleMenuClick() {
        BottomSheetDialog bottomSheetDialog = new BottomSheetDialog();
        bottomSheetDialog.setDataCallback(new String[]{"邀请新成员", "编辑圈子", "成员列表", "圈子简介"}, position -> {
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
                case 1: //编辑圈子
                    if (mViewModel.mCircleDetailLv.getValue() != null /*&& mViewModel.mCircleDetailLv.getValue().getRole()> 0*/) {
                        ARouter.getInstance().build(AppRouter.CIRCLE_CREATE_v2)
                                .withInt("flag", Integer.parseInt(mViewModel.mCircleDetailLv.getValue().getType()))
                                .withString("circleid", "245")
                                .withString("utid", "98699115f2260ef14486f745fc72dbd1")
                                .navigation();
                    }
                    break;
                case 2: //成员列表
                    if (mViewModel.mCircleDetailLv.getValue() != null /*&& mViewModel.detailVo.get().getRole()> 0*/) {
                        StaCirParam mStartParam = new StaCirParam();
                        mStartParam.setCircleid(circleid);
                        mStartParam.setUtid(utid);
                        mStartParam.type = Integer.parseInt(mViewModel.mCircleDetailLv.getValue().getType());
                        CirclePersonListActivity.startMe(this.getHoldingActivity(), mStartParam, mViewModel.mCircleDetailLv.getValue());
                    }
                    break;
                case 3: //圈子简介
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

    // 成员管理
    public void onCirclePersionManagerClick() {
        if (mViewModel.mCircleDetailLv.getValue() != null) {
            StaCirParam mStartParam = new StaCirParam();
            mStartParam.setCircleid(circleid);
            mStartParam.setUtid(utid);
            mStartParam.type = Integer.parseInt(mViewModel.mCircleDetailLv.getValue().getType());
            CirclePersonListActivity.startMe(this.getHoldingActivity(), mStartParam, mViewModel.mCircleDetailLv.getValue());
        }
    }


    public void onLevel1EditClick() {
        StaCirParam staCirParam = new StaCirParam(circleid, utid, "");
        staCirParam.type = 2;
        CircleEditTabActivity.startMe(this.getHoldingActivity(), staCirParam, CircleEditTabActivity.LEVEL_1_EDITCODE);
    }

    public void onpublishClick() {
        if (mPublishMenu == null) {
            mPublishMenu = new SuperPopmenu(this.getHoldingActivity());
        }
        mPublishMenu.init(mBinding.get().getRoot());
        mPublishMenu.showMoreWindow(mBinding.get().getRoot());
    }

    public void onPublish() {

    }

}

