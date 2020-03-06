package com.docker.cirlev2.ui.create;

import android.arch.lifecycle.ViewModelProvider;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.databinding.Observable;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.view.ViewPager;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;

import com.alibaba.android.arouter.launcher.ARouter;
import com.bumptech.glide.Glide;
import com.dcbfhd.utilcode.utils.FragmentUtils;
import com.dcbfhd.utilcode.utils.ToastUtils;
import com.docker.cirlev2.R;
import com.docker.cirlev2.databinding.Circlev2ItemCreateCompanyBinding;
import com.docker.cirlev2.ui.common.CircleSourceUpFragment;
import com.docker.cirlev2.util.GlideImageLoader;
import com.docker.cirlev2.vm.CircleCreateViewModel;
import com.docker.cirlev2.vo.param.SourceUpParam;
import com.docker.cirlev2.vo.vo.CircleCreateVo;
import com.docker.common.common.binding.CommonBdUtils;
import com.docker.common.common.router.AppRouter;
import com.docker.common.common.ui.base.NitCommonFragment;
import com.docker.common.common.utils.rxbus.RxBus;
import com.docker.common.common.utils.rxbus.RxEvent;

import java.util.ArrayList;

import javax.inject.Inject;

import static android.app.Activity.RESULT_OK;

/*
 * 企业圈
 * */
public class CircleCompanyFragment extends NitCommonFragment<CircleCreateViewModel, Circlev2ItemCreateCompanyBinding> {

    private SourceUpParam mSourceUpParam;
    private SourceUpParam mSurfSourceUpParam;
    private CircleSourceUpFragment sourceUpFragment;
    private CircleSourceUpFragment sourceDurfUpFragment;
    private boolean isSurf = true;
    private CircleCreateVo mCircleCreateVo;
    private String mUtid;
    private String mCircleID;
    private ArrayList<String> selectSurfImgs = new ArrayList<>();
    @Inject
    ViewModelProvider.Factory factory;

    @Override
    protected int getLayoutId() {
        return R.layout.circlev2_item_create_company;
    }

    public static CircleCompanyFragment newInstance(String mutid, String mCircleID) {
        CircleCompanyFragment detailFragment = new CircleCompanyFragment();
        Bundle bundle = new Bundle();
        bundle.putString("mUtid", mutid);
        bundle.putString("mCircleID", mCircleID);
        detailFragment.setArguments(bundle);
        return detailFragment;
    }

    @Override
    protected CircleCreateViewModel getViewModel() {
        return ViewModelProviders.of(this, factory).get(CircleCreateViewModel.class);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mUtid = getArguments().getString("mUtid");
        mCircleID = getArguments().getString("mCircleID");

        Log.d("sss", mUtid + "onActivityCreated: ==============" + mCircleID);
        if (TextUtils.isEmpty(mUtid)) { // 新建
            mCircleCreateVo = new CircleCreateVo();
            mBinding.get().setItem(mCircleCreateVo);
        } else {  // 编辑
            mViewModel.getCircleDetailVo(mUtid, mCircleID);
        }

        mViewModel.mCircleCreateLv.observe(this, circleCreateRst -> {
            RxBus.getDefault().post(new RxEvent<>("refresh_circle_myjoin", null));
            getHoldingActivity().finish();
        });

        mViewModel.mCircleDetailLv.observe(this, circleCreateRst -> {
            mCircleCreateVo = circleCreateRst;
            mBinding.get().setItem(mCircleCreateVo);
//            mBinding.get().activityCreatcircleCoverhint.setVisibility(View.INVISIBLE);
            Glide.with(this.getHoldingActivity()).load(CommonBdUtils.getImgUrl(mCircleCreateVo.logoUrl)).into(mBinding.get().circleIvTeamlogo);
//            Glide.with(this.getHoldingActivity()).load(mCircleCreateVo.surfaceImg).into(mBinding.get().activityCreatcircleCover);
            if (!TextUtils.isEmpty(circleCreateRst.surfaceImg)) {
                String pic[] = circleCreateRst.surfaceImg.split(",");
                for (int i = 0; i < pic.length; i++) {
                    selectSurfImgs.add(CommonBdUtils.getCompleteImageUrl(pic[i]));
                }
                initBanner(selectSurfImgs);
            }

        });

        mViewModel.mCircleEditLv.observe(this, circleCreateRst -> {
            RxBus.getDefault().post(new RxEvent<>("refresh_circle_myjoin", null));
            getHoldingActivity().finish();
        });
    }


    private void initBanner(ArrayList<String> imgs) {

        GlideImageLoader glideImageLoader = new GlideImageLoader();
        mBinding.get().banner.setImageLoader(glideImageLoader);
        updateBanner(imgs);
        mBinding.get().banner.setDelayTime(2000);
        mBinding.get().banner.setOnBannerListener(position -> {
            ARouter.getInstance().build(AppRouter.ACTIVE_PUBLISH_BANNER_PREVIEW).withInt("position", position).withSerializable("imgList", selectSurfImgs).navigation(this.getHoldingActivity(), 10090);
        });
        ViewPager viewPager = mBinding.get().banner.getRootView().findViewById(R.id.bannerViewPager);
        if (viewPager != null) {
            viewPager.setPageMargin(20);
        }
        mBinding.get().banner.start();
    }

    private void updateBanner(ArrayList<String> imgs) {
        if (imgs != null) {
            ArrayList<String> realImg = new ArrayList<>();
            for (int i = 0; i < imgs.size(); i++) {
                realImg.add(CommonBdUtils.getImgUrl(imgs.get(i)));
            }
            mBinding.get().banner.update(realImg);
        }
    }


    @Override
    public void initImmersionBar() {

    }

    @Override
    protected void initView(View var1) {
        mSourceUpParam = new SourceUpParam(SourceUpParam.SOURCE_TYPE_IMG_ONLY, 1);
        mSourceUpParam.needCrop = true;
        sourceUpFragment = CircleSourceUpFragment.newInstance(mSourceUpParam);
        sourceUpFragment.setmSingleImageView(mBinding.get().circleIvTeamlogo);
        FragmentUtils.add(getChildFragmentManager(), sourceUpFragment, R.id.frame_logo);
        mSourceUpParam.status.addOnPropertyChangedCallback(new Observable.OnPropertyChangedCallback() {
            @Override
            public void onPropertyChanged(Observable sender, int propertyId) {
                // 根据状态回调 来判断是否能发布了
                if (mSourceUpParam.status.get() == 2) {
                    mCircleCreateVo.logoUrl = mSourceUpParam.imgList.get(0);
                }
            }
        });

        mBinding.get().circleIvTeamlogo.setOnClickListener(v -> {
            isSurf = false;
            sourceUpFragment.enterToSelect(2);
        });
        mSurfSourceUpParam = new SourceUpParam(SourceUpParam.SOURCE_TYPE_IMG_ONLY, 1);
        mSurfSourceUpParam.needCrop = true;
        sourceDurfUpFragment = CircleSourceUpFragment.newInstance(mSurfSourceUpParam);
        sourceDurfUpFragment.setmSingleImageView(mBinding.get().imgPlaceholder);

        FragmentUtils.add(getChildFragmentManager(), sourceDurfUpFragment, R.id.fr_plac);
        mBinding.get().frImgadd.setOnClickListener(v -> {
            isSurf = true;
            sourceDurfUpFragment.enterToSelect(2);
        });
        mSurfSourceUpParam.status.addOnPropertyChangedCallback(new Observable.OnPropertyChangedCallback() {
            @Override
            public void onPropertyChanged(Observable sender, int propertyId) {
                // 根据状态回调 来判断是否能发布了
                if (mSurfSourceUpParam.status.get() == 2) {
                    selectSurfImgs.add(mSurfSourceUpParam.imgList.get(0));
                    updateBanner(selectSurfImgs);
                } else if (mSurfSourceUpParam.status.get() == 3) {
                    hidWaitDialog();
                    ToastUtils.showShort("上传资源失败请重试！");
                }
            }
        });
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == RESULT_OK) {
            if (requestCode == 10090) {
                selectSurfImgs = (ArrayList<String>) data.getSerializableExtra("imgList");
                updateBanner(selectSurfImgs);
                return;
            }

            if (isSurf) {
                sourceDurfUpFragment.onActivityResult(requestCode, resultCode, data);
            } else {
                sourceUpFragment.onActivityResult(requestCode, resultCode, data);
            }
        }


    }


    // 创建圈子
    public void create() {

        mCircleCreateVo = mBinding.get().getItem();
        StringBuilder stringBuilder = new StringBuilder();
        if (selectSurfImgs.size() > 0) {
            for (int i = 0; i < selectSurfImgs.size(); i++) {
                stringBuilder.append(selectSurfImgs.get(i)).append(",");
            }
        }
        String sucs = stringBuilder.toString();
        if (sucs != null && sucs.length() > 0) {
            if (sucs.contains(",")) {
                sucs = sucs.substring(0, sucs.length() - 1);
            }
        } else {
            sucs = "";
        }
        mCircleCreateVo.surfaceImg = sucs;

        if (TextUtils.isEmpty(mCircleCreateVo.surfaceImg)) {
            ToastUtils.showShort("分舵封面图不能为空！");
            return;
        }
        if (TextUtils.isEmpty(mCircleCreateVo.circleName)) {
            ToastUtils.showShort("分舵名称不能为空！");
            return;
        }
        if (TextUtils.isEmpty(mCircleCreateVo.logoUrl)) {
            ToastUtils.showShort("分舵logo不能为空！");
            return;
        }
        if (TextUtils.isEmpty(mCircleCreateVo.companyName)) {
            ToastUtils.showShort("分舵名称不能为空！");
            return;
        }
        if (TextUtils.isEmpty(mCircleCreateVo.address)) {
            ToastUtils.showShort("分舵地址不能为空！");
            return;
        }
        if (TextUtils.isEmpty(mCircleCreateVo.contact)) {
            ToastUtils.showShort("联系电话不能为空！");
            return;
        }

        if (TextUtils.isEmpty(mUtid)) { // 创建
            mViewModel.createCircle(mCircleCreateVo, "1");
        } else { // 编辑
            mCircleCreateVo.utid = mUtid;
            mViewModel.editCircle(mCircleCreateVo, "1", mCircleID);
        }

    }

}
