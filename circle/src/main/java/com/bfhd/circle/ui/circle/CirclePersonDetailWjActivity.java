package com.bfhd.circle.ui.circle;

import android.arch.lifecycle.ViewModelProvider;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.design.widget.AppBarLayout;
import android.support.v4.app.Fragment;
import android.view.Gravity;
import android.view.View;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.DecelerateInterpolator;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.android.arouter.launcher.ARouter;
import com.bfhd.circle.R;
import com.bfhd.circle.base.CommonFragment;
import com.bfhd.circle.base.HivsBaseActivity;
import com.bfhd.circle.base.ViewEventResouce;
import com.bfhd.circle.databinding.CircleActivityCirclePersionDetailWjBinding;
import com.bfhd.circle.ui.safe.DynamicFragment;
import com.bfhd.circle.vm.CircleViewModel;
import com.bfhd.circle.vo.CircleCountpageVo;
import com.bfhd.circle.vo.CircleTitlesVo;
import com.bfhd.circle.vo.StaDynaVo;
import com.bfhd.circle.vo.bean.StaCirParam;
import com.bfhd.circle.vo.bean.StaPersionDetail;
import com.dcbfhd.utilcode.utils.ScreenUtils;
import com.docker.common.common.adapter.CommonpagerAdapter;
import com.docker.common.common.router.AppRouter;
import com.docker.common.common.utils.cache.CacheUtils;
import com.docker.common.common.utils.rxbus.RxBus;
import com.docker.common.common.utils.rxbus.RxEvent;
import com.docker.common.common.vo.UserInfoVo;
import com.docker.common.common.widget.ColorFlipPagerTitleView;
import com.docker.common.common.widget.appbar.AppBarStateChangeListener;
import com.docker.core.widget.BottomSheetDialog;
import com.gyf.immersionbar.ImmersionBar;
import net.lucode.hackware.magicindicator.ViewPagerHelper;
import net.lucode.hackware.magicindicator.buildins.UIUtil;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.CommonNavigator;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.abs.CommonNavigatorAdapter;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.abs.IPagerIndicator;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.abs.IPagerTitleView;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.indicators.LinePagerIndicator;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.titles.SimplePagerTitleView;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import io.reactivex.disposables.Disposable;

/*
 * 成员主页 wj
 * */
@Route(path = AppRouter.CIRCLE_persion_detail)
public class CirclePersonDetailWjActivity extends HivsBaseActivity<CircleViewModel, CircleActivityCirclePersionDetailWjBinding> {


    @Inject
    ViewModelProvider.Factory factory;
    public StaPersionDetail mStartParam;
    private CircleCountpageVo vo;
    private UserInfoVo userInfoVo;
    private int mScreenWidth = 0;
    private Disposable disposable;

    public static void startMe(Context context, StaPersionDetail staPersionDetail) {
        Intent intent = new Intent(context, CirclePersonDetailWjActivity.class);
        Bundle bundle = new Bundle();
        bundle.putSerializable("mStartParam", staPersionDetail);
        intent.putExtras(bundle);
        context.startActivity(intent);
    }

    @Override
    protected void inject() {
        super.inject();
        ARouter.getInstance().inject(this);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.circle_activity_circle_persion_detail_wj;
    }


    @Override
    public CircleViewModel getmViewModel() {
        return ViewModelProviders.of(this, factory).get(CircleViewModel.class);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        isOverrideContentView = true;
        super.onCreate(savedInstanceState);
        //获得屏幕宽度
        mScreenWidth = ScreenUtils.getScreenWidth();
        initIntent();
        mBinding.setViewmodel(mViewModel);
        mBinding.refresh.setEnableLoadMore(false);
        disposable = RxBus.getDefault().toObservable(RxEvent.class).subscribe(rxEvent -> {
            if (rxEvent.getT().equals("login_state_change")) {
                finish();
//                ARouter.getInstance().build(AppRouter.CIRCLE_persion_detail).withSerializable("mStartParam", mStartParam).navigation();

                ARouter.getInstance().build(AppRouter.CIRCLE_person_info).withString("memberid2", mStartParam.uid).withString("uuid2", mStartParam.uuid).navigation();

            }
        });
    }

    private void initIntent() {
        mStartParam = (StaPersionDetail) getIntent().getSerializableExtra("mStartParam");
        mViewModel.circlePersionDetail(mStartParam.uid, mStartParam.uuid);
        userInfoVo = CacheUtils.getUser();
        if (!mStartParam.uuid.equals(userInfoVo.uuid)) { //
            mBinding.circleJubao.setVisibility(View.VISIBLE);
            mBinding.circleJubao.setOnClickListener(v -> {
                processReportUi();
            });
        }
        if (mStartParam.uuid.equals(userInfoVo.uuid)) {
            mBinding.llFocuCou.setVisibility(View.GONE);
        } else {
            mBinding.llFocuCou.setVisibility(View.VISIBLE);
        }
        mBinding.circleTvHome.setOnClickListener(v -> {
            if (vo != null) {
                StaCirParam staCirParam = new StaCirParam(vo.circleid, vo.utid, null);
                ARouter.getInstance().build(AppRouter.CIRCLEHOME).withSerializable("mStartParam", staCirParam).navigation();
            }
        });
    }

    List<Fragment> fragmentList;
    String[] title;

    private void processViewPager(List<CircleTitlesVo> titlesVoList) {
        fragmentList = new ArrayList<>();
        if ("2".equals(vo.reg_type)) { // 家族成员
            title = new String[titlesVoList.size()];
            for (int i = 0; i < titlesVoList.size(); i++) {
                title[i] = titlesVoList.get(i).getName();
                StaDynaVo staDynaVo = new StaDynaVo();
                staDynaVo.UiType = 1;
                staDynaVo.ReqType = 1;
//                staDynaVo.ReqParam.put("memberid", mStartParam.uid);
//                staDynaVo.ReqParam.put("uuid", mStartParam.uuid);
                staDynaVo.ReqParam.put("utid", vo.utid);
                staDynaVo.ReqParam.put("memberid2",mStartParam.uid);
                staDynaVo.ReqParam.put("uuid2",mStartParam.uuid);

                if (mStartParam.uuid.equals(userInfoVo.uuid)) { // 自己
                    staDynaVo.ReqParam.put("self", userInfoVo.uid);
                }
                if ("dynamic".equals(titlesVoList.get(i).getDataType())) {
//                    staDynaVo.ReqParam.put("classid", titlesVoList.get(i).getClassid());
                    staDynaVo.ReqParam.put("t", titlesVoList.get(i).getDataType());
                } else {
                    staDynaVo.ReqParam.put("goodsui", "goods");
                    staDynaVo.ReqParam.put("t", titlesVoList.get(i).getDataType());
                }
                fragmentList.add(DynamicFragment.newInstance(staDynaVo, null));
            }
        } else {
            title = new String[]{"动态"};
            for (int i = 0; i < titlesVoList.size(); i++) {
                if ("dynamic".equals(titlesVoList.get(i).getDataType())) {
                    StaDynaVo staDynaVo = new StaDynaVo();
                    staDynaVo.UiType = 1;
                    staDynaVo.ReqType = 1;
                    staDynaVo.ReqParam.put("memberid", mStartParam.uid);
                    staDynaVo.ReqParam.put("uuid", mStartParam.uuid);
                    staDynaVo.ReqParam.put("utid", vo.utid);
                    if (mStartParam.uuid.equals(userInfoVo.uuid)) { // 自己
                        staDynaVo.ReqParam.put("self", userInfoVo.uid);
                    }
                    staDynaVo.ReqParam.put("memberid2",mStartParam.uid);
                    staDynaVo.ReqParam.put("uuid2",mStartParam.uuid);
//                    staDynaVo.ReqParam.put("classid", titlesVoList.get(i).getClassid());
                    staDynaVo.ReqParam.put("t", titlesVoList.get(i).getDataType());
                    fragmentList.add(DynamicFragment.newInstance(staDynaVo, null));
                }
            }
        }

//        mBinding.tabCircleTitle.setViewPager(mBinding.viewpager);
//        mBinding.tabCircleTitle.setCurrentTab(0);
        initMagicIndicator();
        mBinding.refresh.setOnRefreshListener(refreshLayout -> {
            ((CommonFragment) fragmentList.get(mBinding.viewpager.getCurrentItem())).OnRefresh(mBinding.refresh);
        });
    }


    private void initMagicIndicator() {
        mBinding.viewpager.setAdapter(new CommonpagerAdapter(getSupportFragmentManager(), fragmentList, title));
        mBinding.magicIndicator.setBackgroundColor(Color.WHITE);
        CommonNavigator mCommonNavigator = new CommonNavigator(this);
        if (fragmentList.size() > 2) {
            mCommonNavigator.setAdjustMode(true);
        }
        mCommonNavigator.setAdapter(new CommonNavigatorAdapter() {
            @Override
            public int getCount() {
                return fragmentList == null ? 0 : fragmentList.size();
            }

            @Override
            public IPagerTitleView getTitleView(Context context, final int index) {
                SimplePagerTitleView simplePagerTitleView = new ColorFlipPagerTitleView(context);
                if (fragmentList.size() < 2) {
                    simplePagerTitleView.setGravity(Gravity.CENTER_VERTICAL);
                }
                simplePagerTitleView.setText(title[index]);
                simplePagerTitleView.setTextSize(14);
                simplePagerTitleView.setNormalColor(Color.BLACK);
                simplePagerTitleView.setSelectedColor(getResources().getColor(R.color.colorPrimaryDark));
                simplePagerTitleView.setNormalColor(Color.GRAY);
                simplePagerTitleView.setOnClickListener(v -> mBinding.viewpager.setCurrentItem(index));
                return simplePagerTitleView;
            }

            @Override
            public IPagerIndicator getIndicator(Context context) {
                LinePagerIndicator indicator = new LinePagerIndicator(context);
                indicator.setMode(LinePagerIndicator.MODE_EXACTLY);
                indicator.setLineHeight(UIUtil.dip2px(context, 2));
                indicator.setLineWidth(UIUtil.dip2px(context, 30));
                indicator.setRoundRadius(UIUtil.dip2px(context, 3));
                indicator.setStartInterpolator(new AccelerateInterpolator());
                indicator.setEndInterpolator(new DecelerateInterpolator(2.0f));
                indicator.setColors(getResources().getColor(R.color.colorPrimaryDark));
                return indicator;
            }
        });
        mBinding.magicIndicator.setNavigator(mCommonNavigator);
        ViewPagerHelper.bind(mBinding.magicIndicator, mBinding.viewpager);
    }


    public void initView() {
        mBinding.appbar.addOnOffsetChangedListener(new AppBarStateChangeListener() {
            @Override
            public void onStateChanged(AppBarLayout appBarLayout, State state) {
                switch (state) {
                    case IDLE:
                        mBinding.title.setText("");
                        break;
                    case EXPANDED:
                        mBinding.title.setText("");
                        mBinding.ivBack.setImageResource(R.mipmap.ic_toolbar_back);
                        mBinding.ivBack.setBackgroundDrawable(getResources().getDrawable(R.drawable.circle_ov_shape_back));
                        mBinding.ivReport.setBackgroundDrawable(getResources().getDrawable(R.drawable.circle_ov_shape_back));
                        break;
                    case COLLAPSED:
                        mBinding.ivBack.setImageResource(R.mipmap.ic_back);
                        mBinding.ivBack.setBackgroundDrawable(null);
                        mBinding.ivReport.setBackgroundDrawable(null);
                        break;
                }
            }
        });
        mBinding.tvAlerFocus.setOnClickListener(v -> {
            if (vo != null) {
                mViewModel.focus(mStartParam.uid, mStartParam.uuid, vo);
            }
        });
        mBinding.tvFocu.setOnClickListener(v -> {
            if (vo != null) {
                mViewModel.focus(mStartParam.uid, mStartParam.uuid, vo);
            }
        });
        mBinding.ivBack.setOnClickListener(v -> {
            finish();
        });
//        mBinding.refresh.setOnMultiPurposeListener(new SimpleMultiPurposeListener() {
//            @Override
//            public void onHeaderMoving(RefreshHeader header, boolean isDragging, float percent, int offset, int headerHeight, int maxDragHeight) {
//                //设置图片向下移动
//                mBinding.parallax.setTranslationY(offset);
//                //设置title渐变效果
//                mBinding.rlTitleCoutainer.setAlpha(1 - Math.min(percent, 1));
//                //设置图片宽度变化   当达到指定设置的指定值后  宽度停止  只向下移动
////                if (offset <= 100) {
////                    ViewGroup.LayoutParams layoutParams = mBinding.parallax.getLayoutParams();
////                    layoutParams.width = (mScreenWidth + offset);
////                    ((ViewGroup.MarginLayoutParams) layoutParams).setMargins(-(layoutParams.width - mScreenWidth) / 2, -ConvertUtils.dp2px(200), 0, 0);
////                    mBinding.parallax.requestLayout();
////                }
//            }
//        });

//        mBinding.appbar.addOnOffsetChangedListener((appBarLayout, verticalOffset) -> {
//            int scrollRangle = appBarLayout.getTotalScrollRange();
//            /**
//             * 如果是verticalOffset改成负数   有不一样的效果，可以模拟试试
//             */
//            mBinding.parallax.setTranslationY(verticalOffset);
//            int mAlpha = (int) Math.abs(255f / scrollRangle * verticalOffset);
//            //顶部title渐变效果
////            mBinding.toolbar.setBackgroundColor(Color.argb(mAlpha, 255, 255, 255));
//            mBinding.rlcoutainer.setBackgroundColor(Color.argb(mAlpha, 255, 255, 255));
//        });
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

    @Override
    public void OnVmEnentListner(ViewEventResouce viewEventResouce) {
        super.OnVmEnentListner(viewEventResouce);
        switch (viewEventResouce.eventType) {
            case 213:
                vo = (CircleCountpageVo) viewEventResouce.data;
                if (vo != null) {
                    mBinding.setItem(vo);
                    if ("2".equals(vo.reg_type)) {
                        mBinding.circleTvHome.setVisibility(View.VISIBLE);
                    }

                    mViewModel.getCircleClass(vo.circleid, vo.utid);

                }
                break;
            case 103:
                if (viewEventResouce.data != null && ((List<CircleTitlesVo>) viewEventResouce.data).size() > 0) {
                    processViewPager((List<CircleTitlesVo>) viewEventResouce.data);
                }
                break;
        }
    }


    public void processReportUi() {
        String[] title = new String[]{"举报", "拉黑"};
        BottomSheetDialog bottomSheetDialog = new BottomSheetDialog();
        bottomSheetDialog.setDataCallback(title, position -> {
            bottomSheetDialog.dismiss();
            switch (position) {
                case 0:
                    processReportUiStep2();
                    break;
                case 1:
                    mViewModel.circleBlackList(mStartParam.uid);
                    break;
            }
        });
        bottomSheetDialog.show(this);
    }

    public void processReportUiStep2() {
        String[] title = new String[]{"色情、赌博、毒品", "谣言、社会负面、诈骗", "邪教、非法集会、传销", "医药、整型、虚假广告", "有奖集赞和关注转发", "违反国家政策和法律"};
        BottomSheetDialog bottomSheetDialog = new BottomSheetDialog();
        bottomSheetDialog.setDataCallback(title, position -> {
            bottomSheetDialog.dismiss();
            mViewModel.circlePersionReport(mStartParam.uid, title[position]);
        });
        bottomSheetDialog.show(this);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (disposable != null) {
            disposable.dispose();
        }
    }
}
