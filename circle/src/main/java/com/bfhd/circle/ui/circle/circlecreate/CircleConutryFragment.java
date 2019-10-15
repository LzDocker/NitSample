package com.bfhd.circle.ui.circle.circlecreate;

import android.arch.lifecycle.ViewModelProvider;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.databinding.Observable;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;

import com.alibaba.android.arouter.launcher.ARouter;
import com.bfhd.circle.R;
import com.bfhd.circle.base.CommonFragment;
import com.bfhd.circle.base.ViewEventResouce;
import com.bfhd.circle.databinding.CircleFragmentCircleCreateCountryBinding;
import com.bfhd.circle.ui.common.CircleSourceUpFragment;
import com.bfhd.circle.vm.CircleViewModel;
import com.bfhd.circle.vo.CircleCreateVo;
import com.bfhd.circle.vo.bean.SourceUpParam;
import com.dcbfhd.utilcode.utils.FragmentUtils;
import com.dcbfhd.utilcode.utils.ToastUtils;
import com.bumptech.glide.Glide;
import com.docker.common.common.router.AppRouter;
import com.docker.common.common.utils.rxbus.RxBus;
import com.docker.common.common.utils.rxbus.RxEvent;
import com.docker.common.common.vo.WorldNumList;

import javax.inject.Inject;

import static android.app.Activity.RESULT_OK;

/*
 * 兴趣圈
 * */
public class CircleConutryFragment extends CommonFragment<CircleViewModel, CircleFragmentCircleCreateCountryBinding> {

    private SourceUpParam mSourceUpParam;
    private SourceUpParam mSurfSourceUpParam;
    private CircleSourceUpFragment sourceUpFragment;
    private CircleSourceUpFragment sourceDurfUpFragment;
    private boolean isSurf = true;
    private CircleCreateVo mCircleCreateVo;
    private String mUtid;
    private String mCircleID;
    private WorldNumList.WorldEnty worldEnty;

    @Inject
    ViewModelProvider.Factory factory;

    @Override
    protected int getLayoutId() {
        return R.layout.circle_fragment_circle_create_country;
    }

    public static CircleConutryFragment newInstance(String mutid, String mCircleID) {
        CircleConutryFragment detailFragment = new CircleConutryFragment();
        Bundle bundle = new Bundle();
        bundle.putString("mUtid", mutid);
        bundle.putString("mCircleID", mCircleID);
        detailFragment.setArguments(bundle);
        return detailFragment;
    }

    @Override
    protected CircleViewModel getViewModel() {
        return ViewModelProviders.of(this, factory).get(CircleViewModel.class);
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
        if (TextUtils.isEmpty(mUtid)) { // 新建
            mCircleCreateVo = new CircleCreateVo();
            mBinding.get().setCreateVo(mCircleCreateVo);
        } else {  // 编辑
            mViewModel.getCircleDetailVo(mUtid, mCircleID);
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
        sourceDurfUpFragment.setmSingleImageView(mBinding.get().activityCreatcircleCover);
        FragmentUtils.add(getChildFragmentManager(), sourceDurfUpFragment, R.id.frame_surf);
        mBinding.get().activityCreatcircleCover.setOnClickListener(v -> {
            isSurf = true;
            sourceDurfUpFragment.enterToSelect(2);
        });
        mSurfSourceUpParam.status.addOnPropertyChangedCallback(new Observable.OnPropertyChangedCallback() {
            @Override
            public void onPropertyChanged(Observable sender, int propertyId) {
                // 根据状态回调 来判断是否能发布了
                if (mSurfSourceUpParam.status.get() == 2) {
                    mCircleCreateVo.surfaceImg = mSurfSourceUpParam.imgList.get(0);
                    mBinding.get().activityCreatcircleCoverhint.setVisibility(View.INVISIBLE);
                } else {
                    mBinding.get().activityCreatcircleCoverhint.setVisibility(View.VISIBLE);
                }
            }
        });
        mBinding.get().activityCreatcircleBtn.setOnClickListener(v -> {
            create();
        });
        mBinding.get().tvCreateCircleAddress.setOnClickListener(v -> {
            ARouter.getInstance().build(AppRouter.ACCOUNT_COUNTRY).navigation(this.getHoldingActivity(), 3001);
        });
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 3001 && resultCode == RESULT_OK ) {
            worldEnty = (WorldNumList.WorldEnty) data.getSerializableExtra("datasource");
            mBinding.get().tvCreateCircleAddress.setText(worldEnty.name);
            mCircleCreateVo.address = worldEnty.name;
//            mCircleCreateVo.country_id = worldEnty.id;
        }

        if (isSurf) {
            sourceDurfUpFragment.onActivityResult(requestCode, resultCode, data);
        } else {
            sourceUpFragment.onActivityResult(requestCode, resultCode, data);
        }
    }


    // 创建圈子
    public void create() {

        mCircleCreateVo = mBinding.get().getCreateVo();
        if (TextUtils.isEmpty(mCircleCreateVo.surfaceImg)) {
            ToastUtils.showShort("圈子封面图不能为空！");
            return;
        }
        if (TextUtils.isEmpty(mCircleCreateVo.circleName)) {
            ToastUtils.showShort("圈子名称不能为空！");
            return;
        }
        if (TextUtils.isEmpty(mCircleCreateVo.logoUrl)) {
            ToastUtils.showShort("圈子logo不能为空！");
            return;
        }

        if (TextUtils.isEmpty(mCircleCreateVo.contact)) {
            ToastUtils.showShort("联系电话不能为空！");
            return;
        }

//        if (TextUtils.isEmpty(mCircleCreateVo.country_id)) {
//            ToastUtils.showShort("所在国家不能为空！");
//            return;
//        }
        if (TextUtils.isEmpty(mUtid)) { // 创建
            mViewModel.createCircle(mCircleCreateVo, "3");
        } else { // 编辑
            mCircleCreateVo.utid = mUtid;
            mViewModel.editCircle(mCircleCreateVo, "3", mCircleID);
        }
    }

    @Override
    public void OnVmEnentListner(ViewEventResouce viewEventResouce) {
        super.OnVmEnentListner(viewEventResouce);
        switch (viewEventResouce.eventType) {
            case 105:
                Log.d("sss", "OnVmEnentListner: --------------------");
                RxBus.getDefault().post(new RxEvent<>("refresh_circle_myjoin", null));
                getHoldingActivity().finish();
                break;
            case 108:
                if (viewEventResouce.data != null) {
                    mCircleCreateVo = (CircleCreateVo) viewEventResouce.data;
                    mBinding.get().setCreateVo(mCircleCreateVo);
                    mBinding.get().activityCreatcircleCoverhint.setVisibility(View.INVISIBLE);
                    Glide.with(this.getHoldingActivity()).load(mCircleCreateVo.logoUrl).into(mBinding.get().circleIvTeamlogo);
                    Glide.with(this.getHoldingActivity()).load(mCircleCreateVo.surfaceImg).into(mBinding.get().activityCreatcircleCover);
                }
                break;
        }
    }


}
