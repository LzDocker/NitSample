package com.bfhd.circle.ui.circle.circlecreate;

import android.arch.lifecycle.ViewModelProvider;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Context;
import android.content.Intent;
import android.databinding.Observable;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;

import com.bfhd.circle.R;
import com.bfhd.circle.base.CommonFragment;
import com.bfhd.circle.base.Constant;
import com.bfhd.circle.base.ViewEventResouce;
import com.bfhd.circle.databinding.CircleFragmentCircleCreateHomeBinding;
import com.bfhd.circle.ui.circle.CircleAddSurfActivity;
import com.bfhd.circle.ui.common.CircleSourceUpFragment;
import com.bfhd.circle.vm.CircleViewModel;
import com.bfhd.circle.vo.CircleCreateVo;
import com.bfhd.circle.vo.bean.SourceUpParam;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.CenterCrop;
import com.bumptech.glide.request.RequestOptions;
import com.dcbfhd.utilcode.utils.FragmentUtils;
import com.dcbfhd.utilcode.utils.ToastUtils;
import com.docker.common.common.utils.rxbus.RxBus;
import com.docker.common.common.utils.rxbus.RxEvent;
import com.youth.banner.loader.ImageLoader;

import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.inject.Inject;

import static android.app.Activity.RESULT_OK;

/*
 * 创建和太极家
 * */
public class CircleHomeFragment extends CommonFragment<CircleViewModel, CircleFragmentCircleCreateHomeBinding> {

    private SourceUpParam mSourceUpParam;
    private SourceUpParam mSurfSourceUpParam;
    private CircleSourceUpFragment sourceUpFragment;
    //    private CircleSourceUpFragment sourceDurfUpFragment;
    private boolean isSurf = true;
    private CircleCreateVo mCircleCreateVo;
    private String mUtid;
    private String mCircleID;
    private ArrayList<String> imglist;

    @Inject
    ViewModelProvider.Factory factory;

    @Override
    protected int getLayoutId() {
        return R.layout.circle_fragment_circle_create_home;
    }

    public static CircleHomeFragment newInstance(String mutid, String mCircleID) {
        CircleHomeFragment detailFragment = new CircleHomeFragment();
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
//
//        sourceDurfUpFragment = CircleSourceUpFragment.newInstance(mSurfSourceUpParam);
//        sourceDurfUpFragment.setmSingleImageView(mBinding.get().activityCreatcircleCover);
//        FragmentUtils.add(getChildFragmentManager(), sourceDurfUpFragment, R.id.frame_surf);

        mBinding.get().activityCreatcircleCover.setOnClickListener(v -> {
//            isSurf = true;
//            sourceDurfUpFragment.enterToSelect(2);
            Intent intent = new Intent(CircleHomeFragment.this.getHoldingActivity(), CircleAddSurfActivity.class);
            intent.putStringArrayListExtra("imglist", imglist);
            startActivityForResult(intent, 10099);

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
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
//        if (isSurf) {
//            sourceDurfUpFragment.onActivityResult(requestCode, resultCode, data);
//        } else {
//
//        }


        if (requestCode == 10099) {
            if (resultCode == RESULT_OK) {
                imglist = data.getStringArrayListExtra("imglist");
                mBinding.get().banner.setVisibility(View.VISIBLE);
                mBinding.get().changMenuLlPhoto.setVisibility(View.GONE);
                mBinding.get().banner.setVisibility(View.VISIBLE);
                mBinding.get().banner.setImageLoader(new GlideImageLoader());
                mBinding.get().banner.update(imglist);
                mBinding.get().banner.setDelayTime(3000);
                mBinding.get().banner.setOnBannerListener(position -> {
                    Intent intent = new Intent(CircleHomeFragment.this.getHoldingActivity(), CircleAddSurfActivity.class);
                    intent.putStringArrayListExtra("imglist", imglist);
                    startActivityForResult(intent, 10099);
                });
                mBinding.get().banner.start();
            }
        } else {
            sourceUpFragment.onActivityResult(requestCode, resultCode, data);
        }


    }


    public class GlideImageLoader extends ImageLoader {
        @Override
        public void displayImage(Context context, Object path, ImageView imageView) {
            RequestOptions options = new RequestOptions();
            options.transforms(new CenterCrop());
            Glide.with(CircleHomeFragment.this)
                    .load(Constant.getCompleteImageUrl((String) path))
                    .apply(options)
                    .into(imageView);
        }

    }

    // 创建圈子
    public void create() {

        mCircleCreateVo = mBinding.get().getCreateVo();
        if (TextUtils.isEmpty(mCircleCreateVo.surfaceImg)) {
            ToastUtils.showShort("和太极家封面图不能为空！");
            return;
        }
        if (TextUtils.isEmpty(mCircleCreateVo.circleName)) {
            ToastUtils.showShort("和太极家名称不能为空！");
            return;
        }
        if (TextUtils.isEmpty(mCircleCreateVo.logoUrl)) {
            ToastUtils.showShort("和太极家logo不能为空！");
            return;
        }
        if (TextUtils.isEmpty(mCircleCreateVo.companyName)) {
            ToastUtils.showShort("公司名称不能为空！");
            return;
        }
        if (TextUtils.isEmpty(mCircleCreateVo.address)) {
            ToastUtils.showShort("和太极家地址不能为空！");
            return;
        }
        if (TextUtils.isEmpty(mCircleCreateVo.contact)) {
            ToastUtils.showShort("联系电话不能为空！");
            return;
        }

        if (TextUtils.isEmpty(mCircleCreateVo.card)) {
            ToastUtils.showShort("身份证号码不能为空！");
            return;
        }

        if (!TextUtils.isEmpty(mCircleCreateVo.card)) {
            if (!IsIDcard(mCircleCreateVo.card)) {
                ToastUtils.showShort("请输入正确的身份证号码！");
                return;
            }
        }


        if (TextUtils.isEmpty(mCircleCreateVo.companyName)) {
            ToastUtils.showShort("请填写公司全称！");
            return;
        }

        if (TextUtils.isEmpty(mCircleCreateVo.job)) {
            ToastUtils.showShort("请输入您的职位名称！");
            return;
        }

        if (TextUtils.isEmpty(mCircleCreateVo.goodsname)) {
            ToastUtils.showShort("请输入您的主营产品名称！");
            return;
        }


        if (TextUtils.isEmpty(mUtid)) { // 创建
            mViewModel.createCircle(mCircleCreateVo, "1");
        } else { // 编辑
            mCircleCreateVo.utid = mUtid;
            mViewModel.editCircle(mCircleCreateVo, "1", mCircleID);
        }
    }

    /**
     * 验证输入身份证号
     *
     * @return 如果是符合格式的字符串, 返回 <b>true </b>,否则为 <b>false </b>
     */
    public static boolean IsIDcard(String str) {
        String regex = "(^\\d{18}$)|(^\\d{15}$)";
        return match(regex, str);
    }

    /**
     * @param regex 正则表达式字符串
     * @param str   要匹配的字符串
     * @return 如果str 符合 regex的正则表达式格式,返回true, 否则返回 false;
     */
    private static boolean match(String regex, String str) {
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(str);
        return matcher.matches();
    }

    @Override
    public void OnVmEnentListner(ViewEventResouce viewEventResouce) {
        super.OnVmEnentListner(viewEventResouce);
        switch (viewEventResouce.eventType) {
            case 105:
                RxBus.getDefault().post(new RxEvent<>("refresh_circle_myjoin", null));
//                CircleCreateRst circleCreateRst = (CircleCreateRst) viewEventResouce.data;
//                UserInfoVo userInfoVo = CacheUtils.getUser();
//                userInfoVo.circleid = circleCreateRst.circleid;
//                userInfoVo.utid = circleCreateRst.utid;
//                userInfoVo.reg_type = "2";
//                CacheUtils.saveUser(userInfoVo);
//                StaCirParam staCirParam = new StaCirParam(circleCreateRst.circleid, circleCreateRst.utid, null);
//                CircleHomeActivity.startMe(getActivity(), staCirParam);
//                ARouter.getInstance().build(AppRouter.CIRCLEHOME).navigation();
                getHoldingActivity().finish();
                break;
            case 108:
                if (viewEventResouce.data != null) {
                    mCircleCreateVo = (CircleCreateVo) viewEventResouce.data;
                    mBinding.get().setCreateVo(mCircleCreateVo);
                    mBinding.get().activityCreatcircleCoverhint.setVisibility(View.INVISIBLE);
                    Glide.with(this.getHoldingActivity()).load(Constant.getCompleteImageUrl(mCircleCreateVo.logoUrl)).into(mBinding.get().circleIvTeamlogo);
                    Glide.with(this.getHoldingActivity()).load(Constant.getCompleteImageUrl(mCircleCreateVo.surfaceImg)).into(mBinding.get().activityCreatcircleCover);
                }
                break;
        }
    }
}
