package com.docker.cirlev2.inter.frame.example;

import android.annotation.SuppressLint;
import android.arch.lifecycle.ViewModelProviders;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.AppBarLayout;
import android.support.v4.app.Fragment;
import android.view.View;

import com.alibaba.android.arouter.launcher.ARouter;
import com.dcbfhd.utilcode.utils.CollectionUtils;
import com.docker.cirlev2.R;
import com.docker.cirlev2.databinding.Circlev2DefaultDetailIndexFragmentBinding;
import com.docker.cirlev2.inter.frame.DefaultDetailIndexViewModel;
import com.docker.cirlev2.inter.frame.NitAbsCircleFragment;
import com.docker.cirlev2.vo.card.AppBannerHeaderCardVo;
import com.docker.cirlev2.vo.entity.CircleDetailVo;
import com.docker.cirlev2.vo.entity.CircleTitlesVo;
import com.docker.common.common.adapter.CommonpagerAdapter;
import com.docker.common.common.command.NitDelegetCommand;
import com.docker.common.common.config.Constant;
import com.docker.common.common.model.CommonListOptions;
import com.docker.common.common.router.AppRouter;
import com.docker.common.common.ui.base.NitCommonFragment;
import com.docker.common.common.vm.NitCommonListVm;
import com.docker.common.common.widget.appbar.AppBarStateChangeListener;
import com.docker.common.common.widget.card.NitBaseProviderCard;
import com.docker.common.common.widget.indector.CommonIndector;
import com.docker.common.common.widget.refresh.api.RefreshHeader;
import com.docker.common.common.widget.refresh.listener.SimpleMultiPurposeListener;
import com.gyf.immersionbar.ImmersionBar;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class NitDefaultCircleFragment extends NitAbsCircleFragment<DefaultDetailIndexViewModel, Circlev2DefaultDetailIndexFragmentBinding> {


    public static int temple = 2;


    public ArrayList<Fragment> fragments = new ArrayList<>();

    public static NitAbsCircleFragment getInstance(String circleid, String utid, String circletype) {
        NitDefaultCircleFragment nitDefaultCircleFragment = null;
        switch (temple) {
            case 0:
                nitDefaultCircleFragment = new NitDefaultCircleFragment();
                break;
            case 1:
                nitDefaultCircleFragment = new CircleDetailFragmentTemple_HeaderImg();
                break;
            case 2:
                nitDefaultCircleFragment = new CircleDetailFragmentTemple_HeaderNone();
                break;
        }
        Bundle bundle = new Bundle();
        bundle.putString("circleid", circleid);
        bundle.putString("utid", utid);
        bundle.putString("circletype", circletype);
        nitDefaultCircleFragment.setArguments(bundle);
        return nitDefaultCircleFragment;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        circleid = getArguments().getString("circleid");
        utid = getArguments().getString("utid");
        circletype = getArguments().getString("circletype");
        super.onActivityCreated(savedInstanceState);
        mBinding.get().setViewmodel(mViewModel);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.circlev2_default_detail_index_fragment;
    }

    @Override
    protected DefaultDetailIndexViewModel getViewModel() {
        return ViewModelProviders.of(this, factory).get(DefaultDetailIndexViewModel.class);
    }

    @Override
    protected void initView(View var1) {
        initAppBar();
        initRefresh();

        mBinding.get().circlev2Edit.setOnClickListener(v -> {
            onLevel1EditClick();
        });

        mBinding.get().ivShare.setOnClickListener(v -> {
            onShareClick();
        });
        mBinding.get().ivBack.setOnClickListener(v -> getHoldingActivity().finish());

//        mBinding.get().circleLlPerLiner.setOnClickListener(v -> {
//            onCirclePersionManagerClick();
//        });


        mBinding.get().ivMenuMore.setOnClickListener(v -> onCircleMenuClick());

        mBinding.get().circlev2IvPublish.setOnClickListener(v -> {
            onpublishClick();
        });

        CommonListOptions commonListOptions = new CommonListOptions();
        commonListOptions.RvUi = Constant.KEY_RVUI_LINER;
        NitBaseProviderCard.providerCardNoRefreshForFrame(getChildFragmentManager(), R.id.frame_header, commonListOptions);
    }

    @Override
    public void initImmersionBar() {
        ImmersionBar.with(this)
                .navigationBarColor("#ffffff")
                .statusBarDarkFont(true)
                .titleBarMarginTop(mBinding.get().toolbar)
                .init();
    }

    @Override
    public void onCircleDetailFetched(CircleDetailVo circleDetailVo) {
        mBinding.get().setCircleDetail(circleDetailVo);
//        mBinding.get().circleTvPersonnum.setText(mViewModel.mCircleDetailLv.getValue().getEmployeeNum() + "人");
        if ("1".equals(mViewModel.mCircleDetailLv.getValue().getIsJoin())) {
            mBinding.get().circlev2IvPublish.setVisibility(View.VISIBLE);
        } else {
            mBinding.get().circlev2IvPublish.setVisibility(View.GONE);
        }
        processHeader(circleDetailVo);
    }

    @Override
    public void onCircleTabFetched(List<CircleTitlesVo> list) {
        peocessTab(list);
    }

    public void peocessTab(List<CircleTitlesVo> circleTitlesVos) {
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
        mBinding.get().viewPager.setAdapter(new CommonpagerAdapter(getChildFragmentManager(), fragments, titles));
        CommonIndector commonIndector = new CommonIndector();
        commonIndector.initMagicIndicator(titles, mBinding.get().viewPager, mBinding.get().magicIndicator, this.getHoldingActivity());
        // magic
    }

    public void processHeader(CircleDetailVo circleDetailVo) {
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
//        mBinding.get().banner.setImageLoader(new ImageLoader() {
//            @Override
//            public void displayImage(Context context, Object path, ImageView imageView) {
//                RequestOptions options = new RequestOptions();
//                options.transforms(new CenterCrop());
//                Glide.with(NitDefaultCircleFragment.this)
//                        .load(CommonBdUtils.getCompleteImageUrl((String) path))
//                        .apply(options)
//                        .into(imageView);
//            }
//        });
//        mBinding.get().banner.update(imglist);
//        mBinding.get().banner.setDelayTime(3000);
//        mBinding.get().banner.setOnBannerListener(position -> {
//        });
//        mBinding.get().banner.start();
    }


    public void initAppBar() {
        mBinding.get().appBar.addOnOffsetChangedListener(new AppBarStateChangeListener() {
            @SuppressLint("ResourceAsColor")
            @Override
            public void onStateChanged(AppBarLayout appBarLayout, State state) {
                switch (state) {
                    case IDLE:
                        mBinding.get().title.setText("");
                        break;
                    case EXPANDED:
                        mBinding.get().title.setText("");
                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                            mBinding.get().ivBack.setImageTintList(null);
                            mBinding.get().ivShare.setImageTintList(null);
                            mBinding.get().ivMenuImg.setImageTintList(null);
                            mBinding.get().ivBack.setBackgroundDrawable(getResources().getDrawable(R.drawable.circlev2_ov_shape_back));
                            mBinding.get().ivShare.setBackgroundDrawable(getResources().getDrawable(R.drawable.circlev2_ov_shape_back));
                            mBinding.get().ivMenuImg.setBackgroundDrawable(getResources().getDrawable(R.drawable.circlev2_ov_shape_back));
                        }
                        break;
                    case COLLAPSED:
                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                            mBinding.get().ivBack.setImageTintList(ColorStateList.valueOf(R.color.black));
                            mBinding.get().ivShare.setImageTintList(ColorStateList.valueOf(R.color.black));
                            mBinding.get().ivMenuImg.setImageTintList(ColorStateList.valueOf(R.color.black));
                            mBinding.get().ivBack.setBackgroundDrawable(null);
                            mBinding.get().ivShare.setBackgroundDrawable(null);
                            mBinding.get().ivMenuImg.setBackgroundDrawable(null);
                        }
                        if (mViewModel.mCircleDetailLv.getValue() != null) {
                            mBinding.get().title.setText(mViewModel.mCircleDetailLv.getValue().getCircleName());
                        }
                        break;
                }
            }
        });

        mBinding.get().refresh.setOnMultiPurposeListener(new SimpleMultiPurposeListener() {
            @Override
            public void onHeaderMoving(RefreshHeader header, boolean isDragging, float percent, int offset, int headerHeight, int maxDragHeight) {
                mBinding.get().rlTitleCoutainer.setAlpha(1 - Math.min(percent, 1));
            }
        });
        mBinding.get().appBar.addOnOffsetChangedListener((appBarLayout, verticalOffset) -> {
            int scrollRangle = appBarLayout.getTotalScrollRange();
            int mAlpha = (int) Math.abs(255f / scrollRangle * verticalOffset);
            mBinding.get().toolbar.setBackgroundColor(Color.argb(mAlpha, 255, 255, 255));
        });

    }

    public void initRefresh() {
        mBinding.get().refresh.setEnableLoadMore(false);
        mBinding.get().refresh.setOnRefreshListener(refreshLayout -> {
            if (CollectionUtils.isEmpty(fragments)) {
                mBinding.get().refresh.finishRefresh();
                return;
            }
            ((NitCommonFragment) fragments.get(mBinding.get().viewPager.getCurrentItem())).onReFresh(mBinding.get().refresh);
            onRefreshIng();
        });
    }

    public void onRefreshIng() {

    }

    @Override
    public NitDelegetCommand providerNitDelegetCommand(int flag) {
        NitDelegetCommand nitDelegetCommand = new NitDelegetCommand() {
            @Override
            public Class providerOuterVm() {
                return null;
            }

            @Override
            public void next(NitCommonListVm commonListVm, NitCommonFragment nitCommonFragment) {
                AppBannerHeaderCardVo appBannerHeaderCardVo = new AppBannerHeaderCardVo(0, 0);
                ArrayList<AppBannerHeaderCardVo.BannerVo> arrayList = new ArrayList<>();
                for (int i = 0; i < 4; i++) {
                    arrayList.add(new AppBannerHeaderCardVo.BannerVo());
                }
                appBannerHeaderCardVo.bannerVos.set(arrayList);
                NitBaseProviderCard.providerCard(commonListVm, appBannerHeaderCardVo, nitCommonFragment);
            }
        };
        return nitDelegetCommand;
    }

}

