package com.docker.cirlev2.ui.detail;

import android.annotation.SuppressLint;
import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Context;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.AppBarLayout;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.PopupMenu;

import com.alibaba.android.arouter.facade.annotation.Autowired;
import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.android.arouter.launcher.ARouter;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.CenterCrop;
import com.bumptech.glide.request.RequestOptions;
import com.dcbfhd.utilcode.utils.CollectionUtils;
import com.dcbfhd.utilcode.utils.ToastUtils;
import com.docker.cirlev2.R;
import com.docker.cirlev2.databinding.Circlev2DetailIndexActivityBinding;
import com.docker.cirlev2.ui.CircleInfoActivity;
import com.docker.cirlev2.ui.list.CircleDynamicCoutainerFragment;
import com.docker.cirlev2.ui.persion.CirclePersonListActivity;
import com.docker.cirlev2.ui.publish.CirclePublishActivity;
import com.docker.cirlev2.vm.CircleDetailIndexViewModel;
import com.docker.cirlev2.vo.entity.CircleDetailVo;
import com.docker.cirlev2.vo.entity.CircleTitlesVo;
import com.docker.cirlev2.vo.entity.ServiceDataBean;
import com.docker.cirlev2.vo.param.StaCirParam;
import com.docker.cirlev2.vo.vo.CircleCreateCardVo;
import com.docker.cirlev2.widget.popmen.Popmenu;
import com.docker.common.common.adapter.CommonpagerAdapter;
import com.docker.common.common.binding.CommonBdUtils;
import com.docker.common.common.command.NitContainerCommand;
import com.docker.common.common.command.ReplyCommandParam;
import com.docker.common.common.router.AppRouter;
import com.docker.common.common.ui.base.NitCommonActivity;
import com.docker.common.common.ui.base.NitCommonFragment;
import com.docker.common.common.utils.cache.CacheUtils;
import com.docker.common.common.utils.rxbus.RxBus;
import com.docker.common.common.utils.rxbus.RxEvent;
import com.docker.common.common.vo.ShareBean;
import com.docker.common.common.vo.UserInfoVo;
import com.docker.common.common.widget.appbar.AppBarStateChangeListener;
import com.docker.common.common.widget.indector.CommonIndector;
import com.docker.common.common.widget.refresh.api.RefreshHeader;
import com.docker.common.common.widget.refresh.listener.SimpleMultiPurposeListener;
import com.docker.core.widget.BottomSheetDialog;
import com.gyf.immersionbar.ImmersionBar;
import com.umeng.socialize.ShareAction;
import com.umeng.socialize.UMShareListener;
import com.umeng.socialize.bean.SHARE_MEDIA;
import com.umeng.socialize.media.UMImage;
import com.umeng.socialize.media.UMWeb;
import com.umeng.socialize.shareboard.ShareBoardConfig;
import com.youth.banner.loader.ImageLoader;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import io.reactivex.disposables.Disposable;

/*/
 * 圈子详情
 * */
@Route(path = AppRouter.CIRCLE_DETAIL_v2_INDEX)
public class CircleDetailIndexActivity extends NitCommonActivity<CircleDetailIndexViewModel, Circlev2DetailIndexActivityBinding> {


    @Autowired
    String utid;

    @Autowired
    String circleid;

    private ArrayList<Fragment> fragments = new ArrayList<>();
    private Popmenu myPubMenu;
    private Disposable disposable;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mViewModel.FetchCircleDetail(utid, circleid);
        disposable = RxBus.getDefault().toObservable(RxEvent.class).subscribe(rxEvent -> {
            if (rxEvent.getT().equals("refresh_circle_myjoin") && TextUtils.isEmpty((String) rxEvent.getR())) { // 创建圈子成功后返回
                mViewModel.FetchCircleDetail(utid, circleid);
            }
            if (rxEvent.getR() != null && mBinding != null) {
                mBinding.circleTvPersonnum.setText(mViewModel.mCircleDetailLv.getValue().getEmployeeNum() + "人");
                if ("1".equals(mViewModel.mCircleDetailLv.getValue().getIsJoin())) {
                    mBinding.circlev2IvPublish.setVisibility(View.VISIBLE);
                } else {
                    mBinding.circlev2IvPublish.setVisibility(View.GONE);
                }
            }
//            if (rxEvent.getT().equals("login_state_change")) {
//                finish();
//                ARouter.getInstance().build(AppRouter.CIRCLEHOME).withSerializable("mStartParam", mStartParam).navigation();
//            }
        });
    }

    @Override
    public void initView() {
        mToolbar.hide();
        mBinding.setViewmodel(mViewModel);
        mBinding.refresh.setEnableLoadMore(false);
        mBinding.refresh.setOnRefreshListener(refreshLayout -> {
            if (CollectionUtils.isEmpty(fragments)) {
                mBinding.refresh.finishRefresh();
                return;
            }
            ((NitCommonFragment) fragments.get(mBinding.viewPager.getCurrentItem())).onReFresh(mBinding.refresh);
        });

        /*
         * 编辑 todo
         * */
        mBinding.circlev2Edit.setOnClickListener(v -> {

        });
        initAppBar();

        // 发布
        mBinding.circlev2IvPublish.setOnClickListener(v -> {
            showPublishPop();
        });

        // 分享
        mBinding.ivShare.setOnClickListener(v -> {
            if (mViewModel.mCircleDetailLv.getValue() != null && mViewModel.mCircleDetailLv.getValue().getShare() != null) {
//                showShare(mViewModel.detailVo.get().getShare());
                HashMap<String, String> params = new HashMap<>();
                UserInfoVo userInfoVo = CacheUtils.getUser();
                params.put("type", "circle");
                params.put("circleid", circleid);
                params.put("memberid", userInfoVo.uid);
                params.put("uuid", userInfoVo.uuid);
                mViewModel.FetchShareData(params);
            }
        });

        mBinding.ivBack.setOnClickListener(v -> {
            finish();
        });

        // 进入成员管理
        mBinding.circleLlPerLiner.setOnClickListener(v -> {
            if (mViewModel.mCircleDetailLv.getValue() != null /*&& mViewModel.detailVo.get().getRole()> 0*/) {
                StaCirParam mStartParam = new StaCirParam();
                mStartParam.setCircleid(circleid);
                mStartParam.setUtid(utid);
                mStartParam.type = Integer.parseInt(mViewModel.mCircleDetailLv.getValue().getType());
                CirclePersonListActivity.startMe(CircleDetailIndexActivity.this, mStartParam, mViewModel.mCircleDetailLv.getValue());
            }
        });

        // 菜单
        mBinding.ivMenuMore.setOnClickListener(v -> {
            BottomSheetDialog bottomSheetDialog = new BottomSheetDialog();
            bottomSheetDialog.setDataCallback(new String[]{"邀请新成员", "编辑圈子", "成员列表", "圈子简介"}, position -> {
                bottomSheetDialog.dismiss();
                switch (position) {
                    case 0: //邀请新成员
                        if (mViewModel.mCircleDetailLv.getValue() != null) {//"邀请新成员",
                            StaCirParam mStartParam = new StaCirParam();
                            mStartParam.setCircleid(circleid);
                            mStartParam.setUtid(utid);
                            CircleInviteActivity.startMe(CircleDetailIndexActivity.this, mStartParam,
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
                            CirclePersonListActivity.startMe(CircleDetailIndexActivity.this, mStartParam, mViewModel.mCircleDetailLv.getValue());
                        }
                        break;
                    case 3: //圈子简介
                        if (mViewModel.mCircleDetailLv.getValue() != null) {
                            StaCirParam mStartParam = new StaCirParam();
                            mStartParam.setCircleid(circleid);
                            mStartParam.setUtid(utid);
                            mStartParam.type = Integer.parseInt(mViewModel.mCircleDetailLv.getValue().getType());
                            CircleInfoActivity.startMe(CircleDetailIndexActivity.this, mStartParam);
                        }
                        break;
                }
            });
            bottomSheetDialog.show(this);
        });
    }


    @Override
    public void initObserver() {
        mViewModel.mCircleDetailLv.observe(this, circleDetailVo -> {
            if (circleDetailVo != null) {
                mViewModel.FetchCircleClass();
                mBinding.setCircleDetail(circleDetailVo);
                processBanner(circleDetailVo);
                processView(circleDetailVo);
            }
        });

        mViewModel.mCircleClassLv.observe(this, circleTitlesVos -> {
            if (circleTitlesVos != null) {
                processTab(circleTitlesVos);
            }
        });

        mViewModel.mShareLv.observe(this, shareBean -> {
            showShare(shareBean);
        });

        mViewModel.mJoninLv.observe(this, s -> {

        });
    }


    private void processView(CircleDetailVo circleDetailVo) {
//        if (circleDetailVo.getRole() > 0) {
//            mBinding.ivMenuMore.setVisibility(View.VISIBLE);
//        } else {
//            mBinding.ivMenuMore.setVisibility(View.GONE);
//        }
    }


    private void showPublishPop() {

        if (fragments.size() == 0) {
            return;
        }

        if (myPubMenu == null) {
            myPubMenu = new Popmenu(this);
            myPubMenu.init(mBinding.getRoot());
        }
        myPubMenu.showMoreWindow(mBinding.getRoot());
        myPubMenu.setReplyCommand(o -> {
            int type = (int) o;
            int mPubLishType = CirclePublishActivity.PUBLISH_TYPE_NEWS;
            switch (type) {
                case 1:
                    mPubLishType = CirclePublishActivity.PUBLISH_TYPE_ACTIVE;
                    break;
                case 2:
                    mPubLishType = CirclePublishActivity.PUBLISH_TYPE_NEWS;
                    break;
                case 3:
                    mPubLishType = CirclePublishActivity.PUBLISH_TYPE_QREQUESTION;
                    break;
            }
            processPublish(mPubLishType, 1);
        });
    }

    private void processPublish(int type, int editType) {

        int level1 = mBinding.viewPager.getCurrentItem();  // classid
        CircleDynamicCoutainerFragment circleDetailFragment = (CircleDynamicCoutainerFragment) fragments.get(level1);
        int level2 = circleDetailFragment.getCurrenTab();
        // count == 1的时候就是 "全部" tab
        StaCirParam staCirParam = null;
        if (editType == 1) { // 设置默认值
            if (mViewModel.mCircleClassLv.getValue() != null && mViewModel.mCircleClassLv.getValue().size() > 0 && mViewModel.mCircleDetailLv.getValue() != null) {
                CircleTitlesVo titlesVo = mViewModel.mCircleClassLv.getValue().get(level1);
                staCirParam = new StaCirParam(circleid, utid, mViewModel.mCircleDetailLv.getValue().getCircleName());
                staCirParam.getExtenMap().put("classid1", titlesVo.getClassid());
                staCirParam.getExtenMap().put("classname1", titlesVo.getName());
                if (titlesVo.getChildClass() != null && titlesVo.getChildClass().size() > 0) {
                    CircleTitlesVo bean = titlesVo.getChildClass().get(level2);
                    staCirParam.getExtenMap().put("classid2", bean.getClassid());
                    staCirParam.getExtenMap().put("classname2", bean.getName());
                }
            } else {
                ToastUtils.showShort("数据问题,请稍后");
            }
        }
        if (staCirParam != null) {
            ARouter.getInstance()
                    .build(AppRouter.CIRCLE_PUBLISH_v2_INDEX)
                    .withInt("editType", editType)
                    .withInt("type", type)
                    .withSerializable("mStartParam", staCirParam)
                    .navigation();
        } else {
            ToastUtils.showShort("数据问题,请稍后");
        }
    }

    private void initAppBar() {
//        int mScreenWidth = ScreenUtils.getScreenWidth();
        mBinding.appBar.addOnOffsetChangedListener(new AppBarStateChangeListener() {
            @SuppressLint("ResourceAsColor")
            @Override
            public void onStateChanged(AppBarLayout appBarLayout, State state) {
                switch (state) {
                    case IDLE:
                        mBinding.title.setText("");
                        break;
                    case EXPANDED:
                        mBinding.title.setText("");
                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                            mBinding.ivBack.setImageTintList(null);
                            mBinding.ivShare.setImageTintList(null);
                            mBinding.ivMenuImg.setImageTintList(null);
                            mBinding.ivBack.setBackgroundDrawable(getResources().getDrawable(R.drawable.circlev2_ov_shape_back));
                            mBinding.ivShare.setBackgroundDrawable(getResources().getDrawable(R.drawable.circlev2_ov_shape_back));
                            mBinding.ivMenuImg.setBackgroundDrawable(getResources().getDrawable(R.drawable.circlev2_ov_shape_back));
                        }
                        break;
                    case COLLAPSED:
                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                            mBinding.ivBack.setImageTintList(ColorStateList.valueOf(R.color.black));
                            mBinding.ivShare.setImageTintList(ColorStateList.valueOf(R.color.black));
                            mBinding.ivMenuImg.setImageTintList(ColorStateList.valueOf(R.color.black));
                            mBinding.ivBack.setBackgroundDrawable(null);
                            mBinding.ivShare.setBackgroundDrawable(null);
                            mBinding.ivMenuImg.setBackgroundDrawable(null);
                        }
                        if (mViewModel.mCircleDetailLv.getValue() != null) {
                            mBinding.title.setText(mViewModel.mCircleDetailLv.getValue().getCircleName());
                        }
                        break;
                }
            }
        });

        mBinding.refresh.setOnMultiPurposeListener(new SimpleMultiPurposeListener() {
            @Override
            public void onHeaderMoving(RefreshHeader header, boolean isDragging, float percent, int offset, int headerHeight, int maxDragHeight) {
                //设置图片向下移动
//                mBinding.banner.setTranslationY(offset);
                //设置title渐变效果
                mBinding.rlTitleCoutainer.setAlpha(1 - Math.min(percent, 1));
                //设置图片宽度变化   当达到指定设置的指定值后  宽度停止  只向下移动
//                if (offset <= 100) {
//                    ViewGroup.LayoutParams layoutParams = mBinding.banner.getLayoutParams();
//                    layoutParams.width = (mScreenWidth + offset);
//                    ((ViewGroup.MarginLayoutParams) layoutParams).setMargins(-(layoutParams.width - mScreenWidth) / 2, -ConvertUtils.dp2px(200), 0, 0);
//                    mBinding.banner.requestLayout();
//                }
            }
        });
        mBinding.appBar.addOnOffsetChangedListener((appBarLayout, verticalOffset) -> {
            int scrollRangle = appBarLayout.getTotalScrollRange();
            /**
             * 如果是verticalOffset改成负数   有不一样的效果，
             */
//            mBinding.banner.setTranslationY(verticalOffset);
            int mAlpha = (int) Math.abs(255f / scrollRangle * verticalOffset);
            //顶部title渐变效果
            mBinding.toolbar.setBackgroundColor(Color.argb(mAlpha, 255, 255, 255));
        });

    }


    @Override
    public void initRouter() {
        ARouter.getInstance().inject(this);
    }

    @Override
    public NitContainerCommand providerNitContainerCommand(int flag) {
        return null;
    }

    @Override
    protected int getLayoutId() {
        return R.layout.circlev2_detail_index_activity;
    }

    @Override
    public CircleDetailIndexViewModel getmViewModel() {
        return ViewModelProviders.of(this, factory).get(CircleDetailIndexViewModel.class);
    }

    private void processBanner(CircleDetailVo circleDetailVo) {
        ArrayList<String> imglist = new ArrayList<>();
        if (circleDetailVo.getSurfaceImg().contains(",")) {
            String[] img = circleDetailVo.getSurfaceImg().split(",");
            for (int i = 0; i < img.length; i++) {
                if (!TextUtils.isEmpty(img[i])) {
                    imglist.add(CommonBdUtils.getCompleteImageUrl(img[i]));
                }
            }
        } else {
            imglist.add(CommonBdUtils.getCompleteImageUrl(circleDetailVo.getSurfaceImg()));
        }
        imglist.clear();
        imglist.add(CommonBdUtils.getCompleteImageUrl("/static/var/upload/img20191029/upload/image/1572354265340_536x451.png"));
        imglist.add(CommonBdUtils.getCompleteImageUrl("/static/var/upload/img20191029/upload/image/1572354265340_536x451.png"));
        imglist.add(CommonBdUtils.getCompleteImageUrl("/static/var/upload/img20191029/upload/image/1572354265340_536x451.png"));
        startBanner(imglist);
    }

    private void processTab(List<CircleTitlesVo> circleTitlesVos) {
        String[] titles = new String[circleTitlesVos.size()];
        for (int i = 0; i < circleTitlesVos.size(); i++) {
            titles[i] = circleTitlesVos.get(i).getName();
            fragments.add((Fragment) ARouter.getInstance()
                    .build(AppRouter.CIRCLE_DYNAMIC_LIST_FRAME_COUTAINER)
                    .withSerializable("tabVo", (Serializable) circleTitlesVos)
                    .withInt("pos", i)
                    .withInt("role", mViewModel.mCircleDetailLv.getValue().getRole())
                    .navigation());
        }
        // magic
        mBinding.viewPager.setAdapter(new CommonpagerAdapter(getSupportFragmentManager(), fragments, titles));
        CommonIndector commonIndector = new CommonIndector();
        commonIndector.initMagicIndicator(titles, mBinding.viewPager, mBinding.magicIndicator, this);
        // magic
    }

    private void startBanner(List<String> imglist) {
        mBinding.banner.setImageLoader(new ImageLoader() {
            @Override
            public void displayImage(Context context, Object path, ImageView imageView) {
                RequestOptions options = new RequestOptions();
                options.transforms(new CenterCrop());
                Glide.with(CircleDetailIndexActivity.this)
                        .load(CommonBdUtils.getCompleteImageUrl((String) path))
                        .apply(options)
                        .into(imageView);
            }
        });
        mBinding.banner.update(imglist);
        mBinding.banner.setDelayTime(3000);
        mBinding.banner.setOnBannerListener(position -> {
        });
        mBinding.banner.start();
    }


    @Override
    public void initImmersionBar() {
        ImmersionBar.with(CircleDetailIndexActivity.this)
                .statusBarDarkFont(true)
                .titleBarMarginTop(mBinding.toolbar)
                .init();
    }

    public void showShare(ShareBean shareBean) {
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
        UMImage image = new UMImage(this, shareBean.getShareImg());//网络图片
        image.compressStyle = UMImage.CompressStyle.SCALE;//大小压缩，默认为大小压缩，适合普通很大的图
        image.compressStyle = UMImage.CompressStyle.QUALITY;//质量压缩，适合长图的分
        UMWeb web = new UMWeb(shareBean.getShareUrl());
        web.setTitle(shareBean.getShareTit());//标题
        web.setThumb(image);  //缩略图
        web.setDescription(shareBean.getShareDesc());//描述
        new ShareAction(this).withMedia(web)
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

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (disposable != null) {
            disposable.dispose();
        }
    }
}

