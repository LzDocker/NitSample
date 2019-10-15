package com.bfhd.circle.ui.circle;

import android.arch.lifecycle.ViewModelProvider;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Context;
import android.content.Intent;
import android.databinding.Observable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.view.View;

import com.alibaba.android.arouter.launcher.ARouter;
import com.bfhd.circle.R;
import com.bfhd.circle.base.HivsBaseActivity;
import com.bfhd.circle.databinding.CircleActivityCircleSurfEditBinding;
import com.bfhd.circle.ui.common.CircleSourceUpFragment;
import com.bfhd.circle.vm.CircleViewModel;
import com.bfhd.circle.vo.bean.SourceUpParam;
import com.dcbfhd.utilcode.utils.FragmentUtils;
import com.dcbfhd.utilcode.utils.ToastUtils;
import com.yalantis.ucrop.UCrop;

import java.util.ArrayList;

import javax.inject.Inject;

/*
 * 圈子封面图
 * */
public class CircleAddSurfActivity extends HivsBaseActivity<CircleViewModel, CircleActivityCircleSurfEditBinding> {


    private CircleSourceUpFragment sourceDurfUpFragment1;
    private CircleSourceUpFragment sourceDurfUpFragment2;
    private CircleSourceUpFragment sourceDurfUpFragment3;
    private int step = 1;

    private ArrayList<String> imglist;

    private String img1;
    private String img2;
    private String img3;
    @Inject
    ViewModelProvider.Factory factory;

    @Override
    protected int getLayoutId() {
        return R.layout.circle_activity_circle_surf_edit;
    }

    @Override
    public CircleViewModel getmViewModel() {
        return ViewModelProviders.of(this, factory).get(CircleViewModel.class);
    }

    @Override
    protected void inject() {
        super.inject();
        ARouter.getInstance().inject(this);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mBinding.setViewmodel(mViewModel);
        mToolbar.setTitle("编辑封面");
        mToolbar.setTvRight("完成", v -> {
            imglist.clear();
            if (!TextUtils.isEmpty(img1)) {
                imglist.add(img1);
            }
            if (!TextUtils.isEmpty(img2)) {
                imglist.add(img2);
            }
            if (!TextUtils.isEmpty(img3)) {
                imglist.add(img3);
            }
            if (imglist.size() > 0) {
                Intent intent = new Intent();
                intent.putStringArrayListExtra("imglist", imglist);
                setResult(RESULT_OK, intent);
                finish();
            } else {
                ToastUtils.showShort("请选择封面图");
            }
        });

        imglist = getIntent().getStringArrayListExtra("imglist");
        if (imglist != null) { //todo
            if (imglist.size() == 1) {
                img1 = imglist.get(0);
                mBinding.activityCreatcircleCoverhint1.setVisibility(View.GONE);
                mBinding.llEdit1.setVisibility(View.VISIBLE);
            }
            if (imglist.size() == 2) {
                img1 = imglist.get(0);
                img2 = imglist.get(1);
                mBinding.activityCreatcircleCoverhint1.setVisibility(View.GONE);
                mBinding.llEdit1.setVisibility(View.VISIBLE);
                mBinding.activityCreatcircleCoverhint2.setVisibility(View.GONE);
                mBinding.llEdit2.setVisibility(View.VISIBLE);
                mBinding.changMenuLlPhoto2.setVisibility(View.VISIBLE);
            }
            if (imglist.size() == 3) {
                img1 = imglist.get(0);
                img2 = imglist.get(1);
                img3 = imglist.get(2);
                mBinding.activityCreatcircleCoverhint1.setVisibility(View.GONE);
                mBinding.llEdit1.setVisibility(View.VISIBLE);
                mBinding.activityCreatcircleCoverhint2.setVisibility(View.GONE);
                mBinding.llEdit2.setVisibility(View.VISIBLE);
                mBinding.changMenuLlPhoto2.setVisibility(View.VISIBLE);
                mBinding.activityCreatcircleCoverhint3.setVisibility(View.GONE);
                mBinding.llEdit3.setVisibility(View.VISIBLE);
                mBinding.changMenuLlPhoto3.setVisibility(View.VISIBLE);
            }
            mBinding.setImg1(img1);
            mBinding.setImg2(img2);
            mBinding.setImg3(img3);
        } else {
            imglist = new ArrayList<>();
        }
    }

    @Override
    public void initView() {

        UCrop.Options options = new UCrop.Options();
        options.setToolbarColor(getResources().getColor(R.color.colorPrimary));
        options.setToolbarTitle("图片剪裁");
        options.setStatusBarColor(getResources().getColor(R.color.colorPrimary));
        options.setFreeStyleCropEnabled(true);
        options.setHideBottomControls(false);
        options.withAspectRatio(16, 9);
        options.setScaleEnabled(true);

        SourceUpParam mSurfSourceUpParam = new SourceUpParam(SourceUpParam.SOURCE_TYPE_IMG_ONLY, 1);
        mSurfSourceUpParam.resultcode_local = 10011;
        mSurfSourceUpParam.needCrop = true;
        sourceDurfUpFragment1 = CircleSourceUpFragment.newInstance(mSurfSourceUpParam);
        sourceDurfUpFragment1.options = options;
        sourceDurfUpFragment1.setmSingleImageView(mBinding.activityCreatcircleCover1);
        FragmentUtils.add(getSupportFragmentManager(), sourceDurfUpFragment1, R.id.frame_surf1);


        SourceUpParam mSurfSourceUpParam2 = new SourceUpParam(SourceUpParam.SOURCE_TYPE_IMG_ONLY, 1);
        mSurfSourceUpParam2.resultcode_local = 10012;
        mSurfSourceUpParam2.needCrop = true;
        sourceDurfUpFragment2 = CircleSourceUpFragment.newInstance(mSurfSourceUpParam2);
        sourceDurfUpFragment2.options = options;
        sourceDurfUpFragment2.setmSingleImageView(mBinding.activityCreatcircleCover2);
        FragmentUtils.add(getSupportFragmentManager(), sourceDurfUpFragment2, R.id.frame_surf2);


        SourceUpParam mSurfSourceUpParam3 = new SourceUpParam(SourceUpParam.SOURCE_TYPE_IMG_ONLY, 1);
        mSurfSourceUpParam3.resultcode_local = 10013;
        mSurfSourceUpParam3.needCrop = true;
        sourceDurfUpFragment3 = CircleSourceUpFragment.newInstance(mSurfSourceUpParam3);
        sourceDurfUpFragment3.options = options;
        sourceDurfUpFragment3.setmSingleImageView(mBinding.activityCreatcircleCover3);
        FragmentUtils.add(getSupportFragmentManager(), sourceDurfUpFragment3, R.id.frame_surf3);


        mBinding.activityCreatcircleCover1.setOnClickListener(v -> {
            sourceDurfUpFragment1.enterToSelect(2);
            step = 1;
        });
        mBinding.activityCreatcircleCover2.setOnClickListener(v -> {
            sourceDurfUpFragment2.enterToSelect(2);
            step = 2;
        });
        mBinding.activityCreatcircleCover3.setOnClickListener(v -> {
            sourceDurfUpFragment3.enterToSelect(2);
            step = 3;
        });

        mBinding.tvAdd1.setOnClickListener(v -> {
            sourceDurfUpFragment2.enterToSelect(2);
            step = 2;
        });
        mBinding.tvAdd2.setOnClickListener(v -> {
            sourceDurfUpFragment3.enterToSelect(2);
            step = 3;
        });
        mBinding.tvDel2.setOnClickListener(v -> {
            img2 = "";
            mBinding.changMenuLlPhoto2.setVisibility(View.GONE);
        });
        mBinding.tvDel3.setOnClickListener(v -> {
            img3 = "";
            mBinding.changMenuLlPhoto3.setVisibility(View.GONE);
        });


        mSurfSourceUpParam.status.addOnPropertyChangedCallback(new Observable.OnPropertyChangedCallback() {
            @Override
            public void onPropertyChanged(Observable sender, int propertyId) {
                // 根据状态回调 来判断是否能发布了
                if (mSurfSourceUpParam.status.get() == 2) {
                    // mSurfSourceUpParam.imgList.get(0);
                    mBinding.activityCreatcircleCoverhint1.setVisibility(View.INVISIBLE);
                    mBinding.llEdit1.setVisibility(View.VISIBLE);
                    mBinding.tvAdd1.setVisibility(View.VISIBLE);
                    img1 = mSurfSourceUpParam.imgList.get(0);
                } else {
                    mBinding.activityCreatcircleCoverhint1.setVisibility(View.VISIBLE);
                }
            }
        });

        mSurfSourceUpParam2.status.addOnPropertyChangedCallback(new Observable.OnPropertyChangedCallback() {
            @Override
            public void onPropertyChanged(Observable sender, int propertyId) {
                // 根据状态回调 来判断是否能发布了
                if (mSurfSourceUpParam2.status.get() == 2) {
                    // mSurfSourceUpParam.imgList.get(0);
                    mBinding.changMenuLlPhoto2.setVisibility(View.VISIBLE);
                    mBinding.activityCreatcircleCoverhint2.setVisibility(View.INVISIBLE);
                    mBinding.llEdit2.setVisibility(View.VISIBLE);
                    img2 = mSurfSourceUpParam2.imgList.get(0);
                } else {
                    mBinding.activityCreatcircleCoverhint2.setVisibility(View.VISIBLE);
                }
            }
        });

        mSurfSourceUpParam3.status.addOnPropertyChangedCallback(new Observable.OnPropertyChangedCallback() {
            @Override
            public void onPropertyChanged(Observable sender, int propertyId) {
                // 根据状态回调 来判断是否能发布了
                if (mSurfSourceUpParam3.status.get() == 2) {
                    // mSurfSourceUpParam.imgList.get(0);
                    mBinding.changMenuLlPhoto3.setVisibility(View.VISIBLE);
                    mBinding.activityCreatcircleCoverhint3.setVisibility(View.INVISIBLE);
                    mBinding.llEdit3.setVisibility(View.VISIBLE);
                    img3 = mSurfSourceUpParam3.imgList.get(0);
                } else {
                    mBinding.activityCreatcircleCoverhint3.setVisibility(View.VISIBLE);
                }
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (step) {
            case 1:
                sourceDurfUpFragment1.onActivityResult(requestCode, resultCode, data);
                break;
            case 2:
                sourceDurfUpFragment2.onActivityResult(requestCode, resultCode, data);
                break;
            case 3:
                sourceDurfUpFragment3.onActivityResult(requestCode, resultCode, data);
                break;
        }
    }
}
