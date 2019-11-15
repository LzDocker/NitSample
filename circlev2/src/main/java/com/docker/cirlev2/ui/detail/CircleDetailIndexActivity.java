package com.docker.cirlev2.ui.detail;

import android.annotation.SuppressLint;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Context;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.AppBarLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.text.TextUtils;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.alibaba.android.arouter.facade.annotation.Autowired;
import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.android.arouter.launcher.ARouter;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.CenterCrop;
import com.bumptech.glide.request.RequestOptions;
import com.dcbfhd.utilcode.utils.CollectionUtils;
import com.dcbfhd.utilcode.utils.ConvertUtils;
import com.dcbfhd.utilcode.utils.ScreenUtils;
import com.docker.cirlev2.R;
import com.docker.cirlev2.databinding.Circlev2DetailIndexActivityBinding;
import com.docker.cirlev2.vm.CircleDetailIndexViewModel;
import com.docker.cirlev2.vo.entity.CircleDetailVo;
import com.docker.cirlev2.vo.entity.CircleTitlesVo;
import com.docker.common.common.adapter.CommonpagerAdapter;
import com.docker.common.common.binding.CommonBdUtils;
import com.docker.common.common.command.NitContainerCommand;
import com.docker.common.common.config.Constant;
import com.docker.common.common.router.AppRouter;
import com.docker.common.common.ui.base.NitCommonActivity;
import com.docker.common.common.ui.base.NitCommonFragment;
import com.docker.common.common.widget.appbar.AppBarStateChangeListener;
import com.docker.common.common.widget.indector.CommonIndector;
import com.docker.common.common.widget.refresh.api.RefreshHeader;
import com.docker.common.common.widget.refresh.api.RefreshLayout;
import com.docker.common.common.widget.refresh.listener.OnRefreshListener;
import com.docker.common.common.widget.refresh.listener.SimpleMultiPurposeListener;
import com.gyf.immersionbar.ImmersionBar;
import com.youth.banner.loader.ImageLoader;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import timber.log.Timber;

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

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mViewModel.FetchCircleDetail(utid, circleid);
    }

    @Override
    public void initView() {
        mToolbar.hide();
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
    }

    private void initAppBar() {
        int mScreenWidth = ScreenUtils.getScreenWidth();
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
                mBinding.banner.setTranslationY(offset);
                //设置title渐变效果
                mBinding.rlTitleCoutainer.setAlpha(1 - Math.min(percent, 1));
                //设置图片宽度变化   当达到指定设置的指定值后  宽度停止  只向下移动
                if (offset <= 100) {
                    ViewGroup.LayoutParams layoutParams = mBinding.banner.getLayoutParams();
                    layoutParams.width = (mScreenWidth + offset);
                    ((ViewGroup.MarginLayoutParams) layoutParams).setMargins(-(layoutParams.width - mScreenWidth) / 2, -ConvertUtils.dp2px(200), 0, 0);
                    mBinding.banner.requestLayout();
                }
            }
        });
        mBinding.appBar.addOnOffsetChangedListener((appBarLayout, verticalOffset) -> {
            int scrollRangle = appBarLayout.getTotalScrollRange();
            /**
             * 如果是verticalOffset改成负数   有不一样的效果，可以模拟试试
             */
            mBinding.banner.setTranslationY(verticalOffset);
            int mAlpha = (int) Math.abs(255f / scrollRangle * verticalOffset);
            //顶部title渐变效果
            mBinding.toolbar.setBackgroundColor(Color.argb(mAlpha, 255, 255, 255));
        });

    }

    @Override
    public void initObserver() {
        mViewModel.mCircleDetailLv.observe(this, circleDetailVo -> {
            if (circleDetailVo != null) {
                mViewModel.FetchCircleClass();
                mBinding.setCircleDetail(circleDetailVo);
                processBanner(circleDetailVo);
            }
        });

        mViewModel.mCircleClassLv.observe(this, circleTitlesVos -> {
            if (circleTitlesVos != null) {
                processTab(circleTitlesVos);
            }
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
}

