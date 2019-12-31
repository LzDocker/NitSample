//package com.docker.cirlev2.inter;
//
//import android.databinding.ViewDataBinding;
//import android.graphics.Color;
//import android.os.Bundle;
//import android.support.annotation.Nullable;
//import android.text.TextUtils;
//
//import com.alibaba.android.arouter.launcher.ARouter;
//import com.dcbfhd.utilcode.utils.ToastUtils;
//import com.docker.cirlev2.ui.detail.home.base.AbsCircleDetailIndexViewModel;
//import com.docker.cirlev2.ui.CircleInfoActivity;
//import com.docker.cirlev2.ui.detail.CircleEditTabActivity;
//import com.docker.cirlev2.ui.detail.CircleInviteActivity;
//import com.docker.cirlev2.ui.persion.CirclePersonListActivity;
//import com.docker.cirlev2.vo.entity.CircleDetailVo;
//import com.docker.cirlev2.vo.entity.CircleTitlesVo;
//import com.docker.cirlev2.vo.param.StaCirParam;
//import com.docker.common.common.command.NitContainerCommand;
//import com.docker.common.common.router.AppRouter;
//import com.docker.common.common.ui.base.NitCommonActivity;
//import com.docker.common.common.utils.cache.CacheUtils;
//import com.docker.common.common.utils.rxbus.RxBus;
//import com.docker.common.common.utils.rxbus.RxEvent;
//import com.docker.common.common.vo.ShareBean;
//import com.docker.common.common.vo.UserInfoVo;
//import com.docker.core.widget.BottomSheetDialog;
//import com.umeng.socialize.ShareAction;
//import com.umeng.socialize.UMShareListener;
//import com.umeng.socialize.bean.SHARE_MEDIA;
//import com.umeng.socialize.media.UMImage;
//import com.umeng.socialize.media.UMWeb;
//import com.umeng.socialize.shareboard.ShareBoardConfig;
//
//import java.util.HashMap;
//import java.util.List;
//
//import io.reactivex.disposables.Disposable;
//
///*
// * 圈子详情抽象
// * */
//public abstract class AbsCircleDetailIndexActivity<VM extends AbsCircleDetailIndexViewModel, VB extends ViewDataBinding> extends NitCommonActivity<VM, VB> {
//
//    public String utid;
//
//    public String circleid;
//
//    private Disposable disposable;
//
//    @Override
//    protected void onCreate(@Nullable Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        utid = getIntent().getStringExtra("utid");
//        circleid = getIntent().getStringExtra("circleid");
//        mViewModel.FetchCircleDetail(utid, circleid);
//        disposable = RxBus.getDefault().toObservable(RxEvent.class).subscribe(rxEvent -> {
////            if (rxEvent.getT().equals("refresh_circle_myjoin") && TextUtils.isEmpty((String) rxEvent.getR())) { // 创建/编辑圈子成功后返回
////                mViewModel.FetchCircleDetail(utid, circleid);
////            }
//        });
//    }
//
//
//    /*
//     * 刷新圈子 关注状态
//     * */
//    public abstract void UpdateCircleJoinState();
//
//
//    /*
//     * 设置vm给布局
//     * */
//    public abstract void setmViewmodel();
//
//    /*
//     * 刷新布局设置
//     * */
//    public abstract void initRefershUi();
//
//
//    public void processEditLevel1Click() {  // 编辑一级栏目
//        StaCirParam staCirParam = new StaCirParam(circleid, utid, "");
//        staCirParam.type = 2;
//        CircleEditTabActivity.startMe(this, staCirParam, CircleEditTabActivity.LEVEL_1_EDITCODE);
//    }
//
//    // 显示发布布局
//    public abstract void showPublishPop();
//
//
//    // 分享
//    public void processShare() {
//        if (mViewModel.mCircleDetailLv.getValue() != null && mViewModel.mCircleDetailLv.getValue().getShare() != null) {
//            HashMap<String, String> params = new HashMap<>();
//            UserInfoVo userInfoVo = CacheUtils.getUser();
//            params.put("type", "circle");
//            params.put("circleid", circleid);
//            params.put("memberid", userInfoVo.uid);
//            params.put("uuid", userInfoVo.uuid);
//            mViewModel.FetchShareData(params);
//        }
//    }
//
//    // 进入成员列表
//    public void processEnterPersionManager() {
//        if (mViewModel.mCircleDetailLv.getValue() != null /*&& mViewModel.detailVo.get().getRole()> 0*/) {
//            StaCirParam mStartParam = new StaCirParam();
//            mStartParam.setCircleid(circleid);
//            mStartParam.setUtid(utid);
//            mStartParam.type = Integer.parseInt(mViewModel.mCircleDetailLv.getValue().getType());
//            CirclePersonListActivity.startMe(AbsCircleDetailIndexActivity.this, mStartParam, mViewModel.mCircleDetailLv.getValue());
//        }
//    }
//
//    // 显示menu
//    public void processCircleMenu() {
//        BottomSheetDialog bottomSheetDialog = new BottomSheetDialog();
//        bottomSheetDialog.setDataCallback(new String[]{"邀请新成员", "编辑圈子", "成员列表", "圈子简介"}, position -> {
//            bottomSheetDialog.dismiss();
//            switch (position) {
//                case 0: //邀请新成员
//                    if (mViewModel.mCircleDetailLv.getValue() != null) {//"邀请新成员",
//                        StaCirParam mStartParam = new StaCirParam();
//                        mStartParam.setCircleid(circleid);
//                        mStartParam.setUtid(utid);
//                        CircleInviteActivity.startMe(AbsCircleDetailIndexActivity.this, mStartParam,
//                                mViewModel.mCircleDetailLv.getValue().getCircleName(),
//                                mViewModel.mCircleDetailLv.getValue().getLogoUrl());
//                    }
//                    break;
//                case 1: //编辑圈子
//                    if (mViewModel.mCircleDetailLv.getValue() != null /*&& mViewModel.mCircleDetailLv.getValue().getRole()> 0*/) {
//                        ARouter.getInstance().build(AppRouter.CIRCLE_CREATE_v2)
//                                .withInt("flag", Integer.parseInt(mViewModel.mCircleDetailLv.getValue().getType()))
//                                .withString("circleid", "245")
//                                .withString("utid", "98699115f2260ef14486f745fc72dbd1")
//                                .navigation();
//                    }
//                    break;
//                case 2: //成员列表
//                    if (mViewModel.mCircleDetailLv.getValue() != null /*&& mViewModel.detailVo.get().getRole()> 0*/) {
//                        StaCirParam mStartParam = new StaCirParam();
//                        mStartParam.setCircleid(circleid);
//                        mStartParam.setUtid(utid);
//                        mStartParam.type = Integer.parseInt(mViewModel.mCircleDetailLv.getValue().getType());
//                        CirclePersonListActivity.startMe(AbsCircleDetailIndexActivity.this, mStartParam, mViewModel.mCircleDetailLv.getValue());
//                    }
//                    break;
//                case 3: //圈子简介
//                    if (mViewModel.mCircleDetailLv.getValue() != null) {
//                        StaCirParam mStartParam = new StaCirParam();
//                        mStartParam.setCircleid(circleid);
//                        mStartParam.setUtid(utid);
//                        mStartParam.type = Integer.parseInt(mViewModel.mCircleDetailLv.getValue().getType());
//                        CircleInfoActivity.startMe(AbsCircleDetailIndexActivity.this, mStartParam);
//                    }
//                    break;
//            }
//        });
//        bottomSheetDialog.show(this);
//    }
//
//    public abstract void onCircleDetailFetched(CircleDetailVo circleDetailVo);
//
//    public abstract void onCircleTabFetched(List<CircleTitlesVo> circleDetailVo);
//
//    // 发布
//    private void processPublish(int type, int editType, int level1, int level2) {
//
//        // count == 1的时候就是 "全部" tab
//        StaCirParam staCirParam = null;
//        if (editType == 1) { // 设置默认值
//            if (mViewModel.mCircleClassLv.getValue() != null && mViewModel.mCircleClassLv.getValue().size() > 0 && mViewModel.mCircleDetailLv.getValue() != null) {
//                CircleTitlesVo titlesVo = mViewModel.mCircleClassLv.getValue().get(level1);
//                staCirParam = new StaCirParam(circleid, utid, mViewModel.mCircleDetailLv.getValue().getCircleName());
//                staCirParam.getExtenMap().put("classid1", titlesVo.getClassid());
//                staCirParam.getExtenMap().put("classname1", titlesVo.getName());
//                if (titlesVo.getChildClass() != null && titlesVo.getChildClass().size() > 0) {
//                    CircleTitlesVo bean = titlesVo.getChildClass().get(level2);
//                    staCirParam.getExtenMap().put("classid2", bean.getClassid());
//                    staCirParam.getExtenMap().put("classname2", bean.getName());
//                }
//            } else {
//                ToastUtils.showShort("数据问题,请稍后");
//            }
//        }
//        if (staCirParam != null) {
//            ARouter.getInstance()
//                    .build(AppRouter.CIRCLE_PUBLISH_v2_INDEX)
//                    .withInt("editType", editType)
//                    .withInt("type", type)
//                    .withSerializable("mStartParam", staCirParam)
//                    .navigation();
//        } else {
//            ToastUtils.showShort("数据问题,请稍后");
//        }
//    }
//
//    @Override
//    public void initView() {
//        mToolbar.hide();
//        setmViewmodel();
//        initRefershUi();
//    }
//
//    @Override
//    public void initObserver() {
//        mViewModel.mCircleDetailLv.observe(this, circleDetailVo -> {
//            if (circleDetailVo != null) {
//                onCircleDetailFetched(circleDetailVo);
//                mViewModel.FetchCircleClass();
//            }
//        });
//
//        mViewModel.mCircleClassLv.observe(this, circleTitlesVos -> {
//            if (circleTitlesVos != null) {
//                onCircleTabFetched(circleTitlesVos);
//            }
//        });
//
//        mViewModel.mShareLv.observe(this, shareBean -> {
//            showShare(shareBean);
//        });
//
//        mViewModel.mJoninLv.observe(this, s -> {
//            if (!TextUtils.isEmpty(s)) {
//                UpdateCircleJoinState();
//            }
//        });
//    }
//
//    @Override
//    public void initRouter() {
//    }
//
//    @Override
//    public NitContainerCommand providerNitContainerCommand(int flag) {
//        return null;
//    }
//
//    @Override
//    public void initImmersionBar() {
//
//    }
//
//    public void showShare(ShareBean shareBean) {
//        if (shareBean == null) {
//            return;
//        }
//        ShareBoardConfig config = new ShareBoardConfig();//新建ShareBoardConfig
//        config.setMenuItemBackgroundShape(ShareBoardConfig.BG_SHAPE_CIRCULAR);
//        config.setTitleVisibility(false);
//        config.setIndicatorVisibility(false);
//        config.setCancelButtonVisibility(false);
//        config.setCancelButtonVisibility(false);
//        config.setShareboardBackgroundColor(Color.WHITE);
//        UMImage image = new UMImage(this, shareBean.getShareImg());//网络图片
//        image.compressStyle = UMImage.CompressStyle.SCALE;//大小压缩，默认为大小压缩，适合普通很大的图
//        image.compressStyle = UMImage.CompressStyle.QUALITY;//质量压缩，适合长图的分
//        UMWeb web = new UMWeb(shareBean.getShareUrl());
//        web.setTitle(shareBean.getShareTit());//标题
//        web.setThumb(image);  //缩略图
//        web.setDescription(shareBean.getShareDesc());//描述
//        new ShareAction(this).withMedia(web)
//                .setDisplayList(SHARE_MEDIA.WEIXIN, SHARE_MEDIA.WEIXIN_CIRCLE, SHARE_MEDIA.WEIXIN_FAVORITE, SHARE_MEDIA.QQ, SHARE_MEDIA.QZONE)
//                .setCallback(new UMShareListener() {
//                    @Override
//                    public void onStart(SHARE_MEDIA share_media) {
//
//                    }
//
//                    @Override
//                    public void onResult(SHARE_MEDIA share_media) {
//
//                    }
//
//                    @Override
//                    public void onError(SHARE_MEDIA share_media, Throwable throwable) {
//                        ToastUtils.showShort("分享失败请重试");
//                    }
//
//                    @Override
//                    public void onCancel(SHARE_MEDIA share_media) {
//
//                    }
//                }).open(config);
//    }
//
//    @Override
//    protected void onDestroy() {
//        super.onDestroy();
//        if (disposable != null) {
//            disposable.dispose();
//        }
//    }
//}
//
