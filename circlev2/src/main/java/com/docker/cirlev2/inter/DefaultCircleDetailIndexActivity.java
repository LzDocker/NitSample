//package com.docker.cirlev2.inter;
//
//import android.annotation.SuppressLint;
//import android.arch.lifecycle.ViewModelProviders;
//import android.content.Context;
//import android.content.res.ColorStateList;
//import android.graphics.Color;
//import android.os.Build;
//import android.support.design.widget.AppBarLayout;
//import android.support.v4.app.Fragment;
//import android.text.TextUtils;
//import android.view.View;
//import android.widget.ImageView;
//
//import com.alibaba.android.arouter.facade.annotation.Route;
//import com.alibaba.android.arouter.launcher.ARouter;
//import com.bumptech.glide.Glide;
//import com.bumptech.glide.load.resource.bitmap.CenterCrop;
//import com.bumptech.glide.request.RequestOptions;
//import com.dcbfhd.utilcode.utils.CollectionUtils;
//import com.dcbfhd.utilcode.utils.ToastUtils;
//import com.docker.cirlev2.R;
//import com.docker.cirlev2.databinding.Circlev2DefaultDetailIndexActivityBinding;
//import com.docker.cirlev2.ui.detail.home.temp.defaults.DefaultDetailIndexViewModel;
//import com.docker.cirlev2.ui.list.CircleDynamicCoutainerFragment;
//import com.docker.cirlev2.vo.entity.CircleDetailVo;
//import com.docker.cirlev2.vo.entity.CircleTitlesVo;
//import com.docker.cirlev2.vo.param.StaCirParam;
//import com.docker.cirlev2.widget.popmen.SuperPopmenu;
//import com.docker.common.common.adapter.CommonpagerAdapter;
//import com.docker.common.common.binding.CommonBdUtils;
//import com.docker.common.common.command.NitContainerCommand;
//import com.docker.common.common.router.AppRouter;
//import com.docker.common.common.ui.base.NitCommonFragment;
//import com.docker.common.common.widget.appbar.AppBarStateChangeListener;
//import com.docker.common.common.widget.indector.CommonIndector;
//import com.docker.common.common.widget.refresh.api.RefreshHeader;
//import com.docker.common.common.widget.refresh.listener.SimpleMultiPurposeListener;
//import com.gyf.immersionbar.ImmersionBar;
//import com.youth.banner.loader.ImageLoader;
//
//import java.io.Serializable;
//import java.util.ArrayList;
//import java.util.List;
//
///*
// * 默认圈子详情
// * */
//@Route(path = AppRouter.CIRCLE_DETAIL_v2_INDEX_default)
//public class DefaultCircleDetailIndexActivity extends AbsCircleDetailIndexActivity<DefaultDetailIndexViewModel, Circlev2DefaultDetailIndexActivityBinding> {
//
//
//    private ArrayList<Fragment> fragments = new ArrayList<>();
//    private SuperPopmenu myPubMenu;
//
//    @Override
//    public void UpdateCircleJoinState() {
//        mBinding.circleTvPersonnum.setText(mViewModel.mCircleDetailLv.getValue().getEmployeeNum() + "人");
//        if ("1".equals(mViewModel.mCircleDetailLv.getValue().getIsJoin())) {
//            mBinding.circlev2IvPublish.setVisibility(View.VISIBLE);
//        } else {
//            mBinding.circlev2IvPublish.setVisibility(View.GONE);
//        }
//    }
//
//    @Override
//    public void setmViewmodel() {
//        mBinding.setViewmodel(mViewModel);
//    }
//
//
//    @Override
//    public void initRefershUi() {
//        mBinding.refresh.setEnableLoadMore(false);
//        mBinding.refresh.setOnRefreshListener(refreshLayout -> {
//            if (CollectionUtils.isEmpty(fragments)) {
//                mBinding.refresh.finishRefresh();
//                return;
//            }
//            ((NitCommonFragment) fragments.get(mBinding.viewPager.getCurrentItem())).onReFresh(mBinding.refresh);
//        });
//
//
//    }
//
//    @Override
//    public void showPublishPop() {
//
//        if (fragments.size() == 0) {
//            return;
//        }
//
//        if (myPubMenu == null) {
//            myPubMenu = new SuperPopmenu(this);
//            myPubMenu.init(mBinding.getRoot());
//        }
//        myPubMenu.showMoreWindow(mBinding.getRoot());
//
////            processPublish(mPubLishType, 1);
//    }
//
//    @Override
//    public void initView() {
//        super.initView();
//
//        initAppBar();
//        /*
//         * 编辑 todo
//         * */
//        mBinding.circlev2Edit.setOnClickListener(v -> {
//            processEditLevel1Click();
//        });
//
//        // 发布
//        mBinding.circlev2IvPublish.setOnClickListener(v -> {
//            showPublishPop();
//        });
//
//        // 分享
//        mBinding.ivShare.setOnClickListener(v -> {
//            processShare();
//        });
//
//        mBinding.ivBack.setOnClickListener(v -> {
//            finish();
//        });
//
//        // 进入成员管理
//        mBinding.circleLlPerLiner.setOnClickListener(v -> {
//            processEnterPersionManager();
//        });
//
//        // 菜单
//        mBinding.ivMenuMore.setOnClickListener(v -> {
//            processCircleMenu();
//        });
//    }
//
//
//    private void processView(CircleDetailVo circleDetailVo) {
////        if (circleDetailVo.getRole() > 0) {
////            mBinding.ivMenuMore.setVisibility(View.VISIBLE);
////        } else {
////            mBinding.ivMenuMore.setVisibility(View.GONE);
////        }
//
//    }
//
//
//    @Override
//    public void onCircleDetailFetched(CircleDetailVo circleDetailVo) {
//        mBinding.setCircleDetail(circleDetailVo);
//        processBanner(circleDetailVo);
//    }
//
//    @Override
//    public void onCircleTabFetched(List<CircleTitlesVo> circleDetailVo) {
//        processTab(circleDetailVo);
//    }
//
//    private void processPublish(int type, int editType) {
//
//        int level1 = mBinding.viewPager.getCurrentItem();  // classid
//        CircleDynamicCoutainerFragment circleDetailFragment = (CircleDynamicCoutainerFragment) fragments.get(level1);
//        int level2 = circleDetailFragment.getCurrenTab();
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
//    private void initAppBar() {
////        int mScreenWidth = ScreenUtils.getScreenWidth();
//        mBinding.appBar.addOnOffsetChangedListener(new AppBarStateChangeListener() {
//            @SuppressLint("ResourceAsColor")
//            @Override
//            public void onStateChanged(AppBarLayout appBarLayout, State state) {
//                switch (state) {
//                    case IDLE:
//                        mBinding.title.setText("");
//                        break;
//                    case EXPANDED:
//                        mBinding.title.setText("");
//                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
//                            mBinding.ivBack.setImageTintList(null);
//                            mBinding.ivShare.setImageTintList(null);
//                            mBinding.ivMenuImg.setImageTintList(null);
//                            mBinding.ivBack.setBackgroundDrawable(getResources().getDrawable(R.drawable.circlev2_ov_shape_back));
//                            mBinding.ivShare.setBackgroundDrawable(getResources().getDrawable(R.drawable.circlev2_ov_shape_back));
//                            mBinding.ivMenuImg.setBackgroundDrawable(getResources().getDrawable(R.drawable.circlev2_ov_shape_back));
//                        }
//                        break;
//                    case COLLAPSED:
//                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
//                            mBinding.ivBack.setImageTintList(ColorStateList.valueOf(R.color.black));
//                            mBinding.ivShare.setImageTintList(ColorStateList.valueOf(R.color.black));
//                            mBinding.ivMenuImg.setImageTintList(ColorStateList.valueOf(R.color.black));
//                            mBinding.ivBack.setBackgroundDrawable(null);
//                            mBinding.ivShare.setBackgroundDrawable(null);
//                            mBinding.ivMenuImg.setBackgroundDrawable(null);
//                        }
//                        if (mViewModel.mCircleDetailLv.getValue() != null) {
//                            mBinding.title.setText(mViewModel.mCircleDetailLv.getValue().getCircleName());
//                        }
//                        break;
//                }
//            }
//        });
//
//        mBinding.refresh.setOnMultiPurposeListener(new SimpleMultiPurposeListener() {
//            @Override
//            public void onHeaderMoving(RefreshHeader header, boolean isDragging, float percent, int offset, int headerHeight, int maxDragHeight) {
//                //设置图片向下移动
////                mBinding.banner.setTranslationY(offset);
//                //设置title渐变效果
//                mBinding.rlTitleCoutainer.setAlpha(1 - Math.min(percent, 1));
//                //设置图片宽度变化   当达到指定设置的指定值后  宽度停止  只向下移动
////                if (offset <= 100) {
////                    ViewGroup.LayoutParams layoutParams = mBinding.banner.getLayoutParams();
////                    layoutParams.width = (mScreenWidth + offset);
////                    ((ViewGroup.MarginLayoutParams) layoutParams).setMargins(-(layoutParams.width - mScreenWidth) / 2, -ConvertUtils.dp2px(200), 0, 0);
////                    mBinding.banner.requestLayout();
////                }
//            }
//        });
//        mBinding.appBar.addOnOffsetChangedListener((appBarLayout, verticalOffset) -> {
//            int scrollRangle = appBarLayout.getTotalScrollRange();
//            /**
//             * 如果是verticalOffset改成负数   有不一样的效果，
//             */
////            mBinding.banner.setTranslationY(verticalOffset);
//            int mAlpha = (int) Math.abs(255f / scrollRangle * verticalOffset);
//            //顶部title渐变效果
//            mBinding.vd_toolbar.setBackgroundColor(Color.argb(mAlpha, 255, 255, 255));
//        });
//
//    }
//
//
//    @Override
//    public void initRouter() {
//        ARouter.getInstance().inject(this);
//    }
//
//    @Override
//    public NitContainerCommand providerNitContainerCommand(int flag) {
//        return null;
//    }
//
//    @Override
//    protected int getLayoutId() {
//        return R.layout.circlev2_default_detail_index_activity;
//    }
//
//    @Override
//    public DefaultDetailIndexViewModel getmViewModel() {
//        return ViewModelProviders.of(this, factory).get(DefaultDetailIndexViewModel.class);
//    }
//
//    private void processBanner(CircleDetailVo circleDetailVo) {
//        ArrayList<String> imglist = new ArrayList<>();
//        if (circleDetailVo.getSurfaceImg().contains(",")) {
//            String[] img = circleDetailVo.getSurfaceImg().split(",");
//            for (int i = 0; i < img.length; i++) {
//                if (!TextUtils.isEmpty(img[i])) {
//                    imglist.add(CommonBdUtils.getCompleteImageUrl(img[i]));
//                }
//            }
//        } else {
//            imglist.add(CommonBdUtils.getCompleteImageUrl(circleDetailVo.getSurfaceImg()));
//        }
//        imglist.clear();
//        imglist.add(CommonBdUtils.getCompleteImageUrl("/static/var/upload/img20191029/upload/image/1572354265340_536x451.png"));
//        imglist.add(CommonBdUtils.getCompleteImageUrl("/static/var/upload/img20191029/upload/image/1572354265340_536x451.png"));
//        imglist.add(CommonBdUtils.getCompleteImageUrl("/static/var/upload/img20191029/upload/image/1572354265340_536x451.png"));
//        startBanner(imglist);
//    }
//
//    private void processTab(List<CircleTitlesVo> circleTitlesVos) {
//        String[] titles = new String[circleTitlesVos.size()];
//        for (int i = 0; i < circleTitlesVos.size(); i++) {
//            titles[i] = circleTitlesVos.get(i).getName();
//            fragments.add((Fragment) ARouter.getInstance()
//                    .build(AppRouter.CIRCLE_DYNAMIC_LIST_FRAME_COUTAINER)
//                    .withSerializable("tabVo", (Serializable) circleTitlesVos)
//                    .withInt("pos", i)
//                    .withInt("role", mViewModel.mCircleDetailLv.getValue().getRole())
//                    .navigation());
//        }
//        // magic
//        mBinding.viewPager.setAdapter(new CommonpagerAdapter(getSupportFragmentManager(), fragments, titles));
//        CommonIndector commonIndector = new CommonIndector();
//        commonIndector.initMagicIndicator(titles, mBinding.viewPager, mBinding.magicIndicator, this);
//        // magic
//    }
//
//    private void startBanner(List<String> imglist) {
//        mBinding.banner.setImageLoader(new ImageLoader() {
//            @Override
//            public void displayImage(Context context, Object path, ImageView imageView) {
//                RequestOptions options = new RequestOptions();
//                options.transforms(new CenterCrop());
//                Glide.with(DefaultCircleDetailIndexActivity.this)
//                        .load(CommonBdUtils.getCompleteImageUrl((String) path))
//                        .apply(options)
//                        .into(imageView);
//            }
//        });
//        mBinding.banner.update(imglist);
//        mBinding.banner.setDelayTime(3000);
//        mBinding.banner.setOnBannerListener(position -> {
//        });
//        mBinding.banner.start();
//    }
//
//
//    @Override
//    public void initImmersionBar() {
//        ImmersionBar.with(DefaultCircleDetailIndexActivity.this)
//                .navigationBarColor("#ffffff")
//                .statusBarDarkFont(true)
//                .titleBarMarginTop(mBinding.vd_toolbar)
//                .init();
//    }
//
//
//    @Override
//    protected void onDestroy() {
//        super.onDestroy();
//    }
//}
//
