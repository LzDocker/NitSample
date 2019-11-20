package com.bfhd.circle.ui.circle;

import android.annotation.SuppressLint;
import android.arch.lifecycle.ViewModelProvider;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Context;
import android.content.Intent;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.support.design.widget.AppBarLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.text.TextUtils;
import android.view.View;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.android.arouter.launcher.ARouter;
import com.bfhd.circle.R;
import com.bfhd.circle.base.HivsBaseActivity;
import com.bfhd.circle.base.ViewEventResouce;
import com.bfhd.circle.databinding.CircleActivityCircleDetailBinding;
import com.bfhd.circle.vm.CircleViewModel;
import com.bfhd.circle.vo.CircleTitlesVo;
import com.bfhd.circle.vo.bean.StaCirParam;
import com.bfhd.circle.widget.popmenu.Popmenu;
import com.dcbfhd.utilcode.utils.ScreenUtils;
import com.dcbfhd.utilcode.utils.ToastUtils;
import com.docker.common.common.adapter.CommonpagerAdapter;
import com.docker.common.common.router.AppRouter;
import com.docker.common.common.utils.cache.CacheUtils;
import com.docker.common.common.utils.rxbus.RxBus;
import com.docker.common.common.utils.rxbus.RxEvent;
import com.docker.common.common.vo.ShareBean;
import com.docker.common.common.vo.UserInfoVo;
import com.docker.common.common.widget.appbar.AppBarStateChangeListener;
import com.docker.common.common.widget.refresh.api.RefreshHeader;
import com.docker.common.common.widget.refresh.listener.SimpleMultiPurposeListener;
import com.docker.core.widget.BottomSheetDialog;
import com.gyf.immersionbar.ImmersionBar;
import com.umeng.socialize.ShareAction;
import com.umeng.socialize.UMShareAPI;
import com.umeng.socialize.UMShareListener;
import com.umeng.socialize.bean.SHARE_MEDIA;
import com.umeng.socialize.media.UMImage;
import com.umeng.socialize.media.UMWeb;
import com.umeng.socialize.shareboard.ShareBoardConfig;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import javax.inject.Inject;

import io.reactivex.disposables.Disposable;

/*
 * 圈子详情
 * */
@Route(path = AppRouter.CIRCLEHOME)
public class CircleDetailActivity extends HivsBaseActivity<CircleViewModel, CircleActivityCircleDetailBinding> {

    @Inject
    ViewModelProvider.Factory factory;
    public StaCirParam mStartParam;
    private List<Fragment> fragments;
    private CommonpagerAdapter commonpagerAdapter;
    private List<CircleTitlesVo> circleTitlesVos;
    private Disposable disposable;
    private Popmenu mpopMenu;
    private int mScreenWidth = 0;

    public static void startMe(Context context, StaCirParam startCircleBean) {
        Intent intent = new Intent(context, CircleDetailActivity.class);
        Bundle bundle = new Bundle();
        bundle.putSerializable("mStartParam", startCircleBean);
        intent.putExtras(bundle);
        context.startActivity(intent);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.circle_activity_circle_detail;
    }


    @Override
    public CircleViewModel getmViewModel() {
        return ViewModelProviders.of(this, factory).get(CircleViewModel.class);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        isOverrideContentView = true;
        super.onCreate(savedInstanceState);
        initIntent();
        //获得屏幕宽度
        mScreenWidth = ScreenUtils.getScreenWidth();
        mBinding.setViewmodel(mViewModel);
        disposable = RxBus.getDefault().toObservable(RxEvent.class).subscribe(rxEvent -> {
            if (rxEvent.getT().equals("refresh_circle_myjoin") && TextUtils.isEmpty((String) rxEvent.getR())) { // 创建圈子成功后返回
                mViewModel.getCircleDetail();
            }
            if (rxEvent.getR() != null && mBinding != null) {
                mBinding.circleTvPersonnum.setText(mViewModel.detailVo.get().getEmployeeNum() + "人");
//                if ("1".equals(mViewModel.detailVo.get().getIsJoin())) {
//                    mBinding.viewBot.setVisibility(View.VISIBLE);
//                    mBinding.circleIvAdd.setVisibility(View.VISIBLE);
//                } else {
//                    mBinding.viewBot.setVisibility(View.GONE);
//                    mBinding.circleIvAdd.setVisibility(View.GONE);
//                }
            }

            if (rxEvent.getT().equals("login_state_change")) {
                finish();
//                ARouter.getInstance().build(AppRouter.CIRCLEHOME).withSerializable("mStartParam", mStartParam).navigation();
            }
        });
//        UserInfoVo userInfoVo = CacheUtils.getUser();
//        Glide.with(this).load(Constant.getCompleteImageUrl(userInfoVo.avatar)).into(mBinding.circleLlUserHead);
//        mBinding.circleLlUserName.setText(userInfoVo.fullName);
    }

    private void initIntent() {
        mStartParam = (StaCirParam) getIntent().getExtras().getSerializable("mStartParam");
        mViewModel.initCircleParam(mStartParam);
        mViewModel.getData();
    }

    public void initView() {
        mBinding.refresh.setEnableLoadMore(false);
        mBinding.ivShare.setOnClickListener(v -> { // 分享
            if (mViewModel.detailVo.get() != null && mViewModel.detailVo.get().getShare() != null) {
//                showShare(mViewModel.detailVo.get().getShare());
                HashMap<String, String> params = new HashMap<>();
                UserInfoVo userInfoVo = CacheUtils.getUser();
                params.put("type", "circle");
                params.put("memberid", userInfoVo.uid);
                params.put("uuid", userInfoVo.uuid);
                mViewModel.getShareData(params);

            }
        });

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
                            mBinding.ivBack.setBackgroundDrawable(getResources().getDrawable(R.drawable.circle_ov_shape_back));
                            mBinding.ivShare.setBackgroundDrawable(getResources().getDrawable(R.drawable.circle_ov_shape_back));
                            mBinding.ivMenuImg.setBackgroundDrawable(getResources().getDrawable(R.drawable.circle_ov_shape_back));
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
                        if (mViewModel.detailVo.get() != null) {
                            mBinding.title.setText(mViewModel.detailVo.get().getCircleName());
                        }
                        break;
                }
            }
        });
        mBinding.refresh.setOnLoadMoreListener(v -> {
            if (commonpagerAdapter != null && commonpagerAdapter.getCount() > 0) {
                ((CircleDetailFragment) fragments.get(mBinding.viewPager.getCurrentItem())).OnLoadMore(mBinding.refresh);
            }
        });
        mBinding.refresh.setOnMultiPurposeListener(new SimpleMultiPurposeListener() {
            @Override
            public void onHeaderMoving(RefreshHeader header, boolean isDragging, float percent, int offset, int headerHeight, int maxDragHeight) {
                //设置图片向下移动
//                mBinding.parallax.setTranslationY(offset);
                //设置title渐变效果
                mBinding.rlTitleCoutainer.setAlpha(1 - Math.min(percent, 1));
                //设置图片宽度变化   当达到指定设置的指定值后  宽度停止  只向下移动
//                if (offset <= 100) {
//                    ViewGroup.LayoutParams layoutParams = mBinding.parallax.getLayoutParams();
//                    layoutParams.width = (mScreenWidth + offset);
//                    ((ViewGroup.MarginLayoutParams) layoutParams).setMargins(-(layoutParams.width - mScreenWidth) / 2, -ConvertUtils.dp2px(200), 0, 0);
//                    mBinding.parallax.requestLayout();
//                }
            }
        });
        mBinding.appBar.addOnOffsetChangedListener((appBarLayout, verticalOffset) -> {
            int scrollRangle = appBarLayout.getTotalScrollRange();
            /**
             * 如果是verticalOffset改成负数   有不一样的效果，可以模拟试试
             */
//            mBinding.parallax.setTranslationY(verticalOffset);
            int mAlpha = (int) Math.abs(255f / scrollRangle * verticalOffset);
            //顶部title渐变效果
            mBinding.toolbar.setBackgroundColor(Color.argb(mAlpha, 255, 255, 255));
        });
        mBinding.refresh.setOnRefreshListener(v -> {
            mViewModel.getData();
            if (circleTitlesVos == null || circleTitlesVos.size() == 0) {
                mViewModel.getCircleClass();
            }
            if (commonpagerAdapter != null && commonpagerAdapter.getCount() > 0) {
                ((CircleDetailFragment) fragments.get(mBinding.viewPager.getCurrentItem())).OnRefresh(mBinding.refresh);
            }
        });
        //
        mBinding.circleLlPerLiner.setOnClickListener(v -> {
            mStartParam.type = Integer.parseInt(mViewModel.detailVo.get().getType());
            CirclePersonListActivity.startMe(CircleDetailActivity.this, mStartParam, mViewModel.detailVo.get());
        });
        mBinding.ivMenuMore.setOnClickListener(v -> {
            BottomSheetDialog bottomSheetDialog = new BottomSheetDialog();
            bottomSheetDialog.setDataCallback(new String[]{"邀请新成员", "编辑店铺", "店铺成员列表", "店铺简介"}, position -> {
                bottomSheetDialog.dismiss();
                switch (position) {
                    case 0:
                        if (mViewModel.detailVo.get() != null) {//"邀请新成员",
                            mStartParam.type = Integer.parseInt(mViewModel.detailVo.get().getType());
                            CircleFriendShareActivity.startMe(CircleDetailActivity.this, mStartParam, mViewModel.detailVo.get().getCircleName(), mViewModel.detailVo.get().getLogoUrl());
                        }

                        break;
                    case 1:
                        if (mViewModel.detailVo.get() != null /*&& mViewModel.detailVo.get().getRole()> 0*/) {
                            mStartParam.type = Integer.parseInt(mViewModel.detailVo.get().getType());
//                            CircleCreateActivity.startMe(CircleDetailActivity.this, mStartParam.type, mStartParam.getUtid(), mStartParam.getCircleid());
//                            CirclePersonListActivity.startMe(CircleDetailActivity.this, mStartParam, mViewModel.detailVo.get());

                            /*
                            *     mCreateType = (int) getIntent().getExtras().get("TYPE");
        mUtid = getIntent().getStringExtra("mUtid");
        mCircleID = getIntent().getStringExtra("circleID");
                            * */

//                            ARouter.getInstance().build(AppRouter.CIRCLE_CREATE_HWJ).withInt("TYPE", 1).withString("mUtid", mStartParam.getUtid()).withString("circleID", mStartParam.getCircleid()).navigation();
                        }
                        break;
                    case 2:
                        if (mViewModel.detailVo.get() != null /*&& mViewModel.detailVo.get().getRole()> 0*/) {
                            mStartParam.type = Integer.parseInt(mViewModel.detailVo.get().getType());
                            CirclePersonListActivity.startMe(CircleDetailActivity.this, mStartParam, mViewModel.detailVo.get());
                        }
                        break;
                    case 3:
                        if (mViewModel.detailVo.get() != null) {
                            mStartParam.type = Integer.parseInt(mViewModel.detailVo.get().getType());
                            CircleInfoActivity.startMe(CircleDetailActivity.this, mStartParam);
                        }
//                        if (mViewModel.detailVo.get() != null) {//圈子权限设置
//                            mStartParam.type = Integer.parseInt(mViewModel.detailVo.get().getType());
//                            CircleperssionActivity.startMe(CircleDetailActivity.this, mStartParam);
//                        }
                        break;
                }
            });
            bottomSheetDialog.show(this);
        });

        //
        mBinding.circleIvAdd.setOnClickListener(vs -> {
//            CommonDialog.newInstance()
//                    .setLayoutId(R.layout.circle_dialog_publish)
//                    .setConvertListener((ViewConvertListener) (holder, dialog) -> {
//                        holder.setOnClickListener(R.id.circle_active, v -> {
//                            dialog.dismiss();
//                            // 动态
//                            processCirclePublish(CirclePublishActivity.PUBLISH_TYPE_ACTIVE);
//                        });
//                        holder.setOnClickListener(R.id.circle_news, v -> {
//                            dialog.dismiss();
//                            // 新闻
//                            processCirclePublish(CirclePublishActivity.PUBLISH_TYPE_NEWS);
//                        });
//                        holder.setOnClickListener(R.id.circle_queastion, v -> {
//                            dialog.dismiss();
//                            // 问答
//                            processCirclePublish(CirclePublishActivity.PUBLISH_TYPE_QREQUESTION);
//                        });
//                        holder.setOnClickListener(R.id.circle_close, v -> dialog.dismiss());
//                    })
//                    .setOutCancel(true)
//                    .setShowBottom(true)
//                    .show(getSupportFragmentManager());
//            showPopMenu();
        });

        mBinding.ivBack.setOnClickListener(v -> {
            finish();
        });
        mBinding.circleLlInfo.setOnClickListener(v -> { // 圈子简介
            if (mViewModel.detailVo.get() != null) {
                mStartParam.type = Integer.parseInt(mViewModel.detailVo.get().getType());
                CircleInfoActivity.startMe(CircleDetailActivity.this, mStartParam);
            }
        });
//        mBinding.circleTvPersonnum.setOnClickListener(v -> { // 圈子成员列表  管理员以上能进 todo
//
//            //取消关注
//            if (mViewModel.detailVo.get() != null /*&& mViewModel.detailVo.get().getRole()> 0*/) {
//                mStartParam.type = Integer.parseInt(mViewModel.detailVo.get().getType());
//                CirclePersonListActivity.startMe(CircleDetailActivity.this, mStartParam, mViewModel.detailVo.get());
//            }
//        });
    }


    private void showPopMenu() {
        if (fragments == null) {
            return;
        }
        if (null == mpopMenu) {
            mpopMenu = new Popmenu(this);
            mpopMenu.init(mBinding.empty);
        }
        mpopMenu.showMoreWindow(mBinding.empty);
        mpopMenu.setReplyCommand(o -> {
            int type = (int) o;
            switch (type) {
                case 1:
                    processCirclePublish(CirclePublishActivity.PUBLISH_TYPE_ACTIVE, 1);
                    break;
                case 2:
                    processCirclePublish(CirclePublishActivity.PUBLISH_TYPE_NEWS, 1);
                    break;
                case 3:
                    processCirclePublish(CirclePublishActivity.PUBLISH_TYPE_QREQUESTION, 1);
                    break;
            }
        });
    }

    // 发布 默认当前栏目
    private void processCirclePublish(int type, int editType) {
        int level1 = mBinding.viewPager.getCurrentItem();  // classid
        CircleDetailFragment circleDetailFragment = (CircleDetailFragment) fragments.get(level1);
        int level2 = circleDetailFragment.getCurrenTab();
        int count = circleDetailFragment.getFrameCount();
        // count == 1的时候就是 "全部" tab
        StaCirParam staCirParam = null;
        if (editType == 1) { // 设置默认值
            if (circleTitlesVos != null && circleTitlesVos.size() > 0 && mViewModel.detailVo.get() != null) {
                CircleTitlesVo titlesVo = circleTitlesVos.get(level1);
                staCirParam = new StaCirParam(mStartParam.getCircleid(), mStartParam.getUtid(), mViewModel.detailVo.get().getCircleName());
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
            CirclePublishActivity.startMe(this, type, staCirParam, editType);
        } else {
            ToastUtils.showShort("数据问题,请稍后");
        }
    }


    @Override
    public void initImmersionBar() {
        ImmersionBar.with(this)
                .titleBar(mBinding.toolbar)
                .statusBarDarkFont(true)   //状态栏字体是深色，不写默认为亮色
                .autoDarkModeEnable(true) //自动状态栏字体和导航栏图标变色，必须指定状态栏颜色和导航栏颜色才可以自动变色哦
                .navigationBarColor("#ffffff")
                .init();
    }


    private void initTab(List<CircleTitlesVo> circleTitlesVos) {

//        if (mViewModel.detailVo.get().getRole() > 0 && !"-1".equals(CacheUtils.getUser().memberid)) {
//            mBinding.ivMenuMore.setVisibility(View.VISIBLE);
//        } else {
//            mBinding.ivMenuMore.setVisibility(View.GONE);
//        }
//        mBinding.ivMenuMore.setVisibility(View.VISIBLE);

//        if ("1".equals(mViewModel.detailVo.get().getIsJoin()) && "1".equals(mViewModel.detailVo.get().getIsPublishDynamic())) {
//            mBinding.viewBot.setVisibility(View.VISIBLE);
//            mBinding.circleIvAdd.setVisibility(View.VISIBLE);
//        } else {
//            mBinding.viewBot.setVisibility(View.GONE);
//            mBinding.circleIvAdd.setVisibility(View.GONE);
//        }

        //wj
        if (mViewModel.detailVo.get().getUuid().equals(CacheUtils.getUser().uuid)) {
            mBinding.ivMenuMore.setVisibility(View.VISIBLE);
        } else {
            mBinding.ivMenuMore.setVisibility(View.GONE);
        }


        //wj


//        commonpagerAdapter.notifyDataSetChanged();
//        mBinding.tabCircleTitle.notifyDataSetChanged();

        if (circleTitlesVos != null && circleTitlesVos.size() > 0) {
            fragments = new ArrayList<>();
            String[] titles = new String[circleTitlesVos.size()];//circleTitlesVos  动态/问答
            for (int i = 0; i < circleTitlesVos.size(); i++) {
                mStartParam.role = mViewModel.detailVo.get().getRole();
                fragments.add(CircleDetailFragment.newInstance(circleTitlesVos, i, mStartParam));
                titles[i] = circleTitlesVos.get(i).getName();
            }
            commonpagerAdapter = new CommonpagerAdapter(getSupportFragmentManager(), fragments);
            mBinding.viewPager.setAdapter(commonpagerAdapter);
            mBinding.tabCircleTitle.setViewPager(mBinding.viewPager, titles);
            mBinding.viewPager.setOffscreenPageLimit(fragments.size());
//            if (mViewModel.detailVo.get().getRole() > 0) {
//                TabLabelTextLayout tabLabel = new TabLabelTextLayout(this);
//                tabLabel.setOnClickListener(v -> {
//                    onEditClick(0);
//                });
//                FrameLayout.LayoutParams layoutParams = new FrameLayout.LayoutParams(FrameLayout.LayoutParams.WRAP_CONTENT, FrameLayout.LayoutParams.MATCH_PARENT);
//                layoutParams.gravity = Gravity.CENTER_VERTICAL;
//                ((ViewGroup) mBinding.tabCircleTitle.getChildAt(0)).addView(tabLabel, layoutParams);
//            }
            mBinding.viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
                @Override
                public void onPageScrolled(int i, float v, int i1) {

                }

                @Override
                public void onPageSelected(int position) {
                    mBinding.refresh.finishLoadMore();
                    mBinding.refresh.finishRefresh();

                }

                @Override
                public void onPageScrollStateChanged(int i) {

                }
            });
        }
    }

    /*
     * 0 一级菜单点击
     * */
    private void onEditClick(int type) {
        StaCirParam staCirParam = new StaCirParam(circleTitlesVos.get(0).getCircleid(), circleTitlesVos.get(0).getUtid(), "");
        staCirParam.type = 2;
        CircleEditClassActivity.startMe(this, staCirParam, CircleEditClassActivity.LEVEL_1_EDITCODE);
    }

    @Override
    public void OnVmEnentListner(ViewEventResouce viewEventResouce) {
        super.OnVmEnentListner(viewEventResouce);
        switch (viewEventResouce.eventType) {
            case 103:
                mBinding.refresh.finishRefresh();
                mBinding.refresh.finishLoadMore();
                if (viewEventResouce.data != null && ((List<CircleTitlesVo>) viewEventResouce.data).size() > 0) {
                    if (circleTitlesVos != null) {
                        circleTitlesVos.clear();
                    }
                    circleTitlesVos = (List<CircleTitlesVo>) viewEventResouce.data;
                    initTab(circleTitlesVos);
                } else {
                    // 没有一级栏目的数据 那就写死吧
                }
                break;
            case 113:
                if (mBinding.viewPager.getChildCount() == 0) {
                    mViewModel.getCircleClass();
                }
                break;

            case 211:
                showShare((ShareBean) viewEventResouce.data);
                break;
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        UMShareAPI.get(this).onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
            case CircleEditClassActivity.LEVEL_2_EDITCODE:
            case CircleEditClassActivity.LEVEL_1_EDITCODE:
                if (resultCode == RESULT_OK) {
                    finish();
                    startMe(CircleDetailActivity.this, CircleDetailActivity.this.mStartParam);
//                    initTab(circleTitlesVos);
//                    Intent intent = new Intent();
//                    intent.putExtra("mStartParam",mStartParam);
//                    fragments.get(mBinding.viewPager.getCurrentItem()).onActivityResult(requestCode,resultCode,intent);
                }
                break;
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (disposable != null) {
            disposable.dispose();
        }
    }


//    public void showShare(ShareVo shareBean) {
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
//                .setDisplayList(SHARE_MEDIA.WEIXIN, SHARE_MEDIA.WEIXIN_CIRCLE, SHARE_MEDIA.QQ)
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


}
