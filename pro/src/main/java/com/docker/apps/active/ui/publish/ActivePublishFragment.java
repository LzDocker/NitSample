package com.docker.apps.active.ui.publish;

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

import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.android.arouter.launcher.ARouter;
import com.dcbfhd.utilcode.utils.FragmentUtils;
import com.dcbfhd.utilcode.utils.ToastUtils;
import com.docker.apps.R;
import com.docker.apps.databinding.ProActivePubFragmentBinding;
import com.docker.cirlev2.ui.common.CircleSourceUpFragment;
import com.docker.cirlev2.ui.publish.CirclePublishActivity;
import com.docker.cirlev2.util.GlideImageLoader;
import com.docker.cirlev2.vm.PublishViewModel;
import com.docker.cirlev2.vo.entity.ServiceDataBean;
import com.docker.cirlev2.vo.param.SourceUpParam;
import com.docker.cirlev2.vo.param.StaCirParam;
import com.docker.common.common.binding.CommonBdUtils;
import com.docker.common.common.router.AppRouter;
import com.docker.common.common.ui.base.NitCommonFragment;
import com.docker.common.common.utils.cache.CacheUtils;
import com.docker.common.common.utils.rxbus.RxBus;
import com.docker.common.common.utils.rxbus.RxEvent;
import com.docker.common.common.vo.UserInfoVo;
import com.docker.common.common.widget.picker.CommonWheelPicker;
import com.luck.picture.lib.entity.LocalMedia;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import javax.inject.Inject;

import io.reactivex.disposables.Disposable;

import static android.app.Activity.RESULT_OK;
import static com.docker.common.common.router.AppRouter.ACTIVE_PUBLISH;

/*
 * 活动类型的动态
 * */
@Route(path = ACTIVE_PUBLISH)
public class ActivePublishFragment extends NitCommonFragment<PublishViewModel, ProActivePubFragmentBinding> {

    private SourceUpParam mSourceUpParam;
    private CircleSourceUpFragment sourceUpFragment;

    private Disposable disposable;
    private ArrayList<String> selectSurfImgs = new ArrayList<>();

    /*
     *
     * 选择圈子 选择 分类 子栏目
     *
     * extenMap size 来区分选了多少级
     *
     * */
    private StaCirParam mHandParam; // 接收到的分组数据

//    private StaCirParam mPerssionParam; // 权限数据

    @Inject
    ViewModelProvider.Factory factory;

    @Override
    protected int getLayoutId() {
        return R.layout.pro_active_pub_fragment;
    }

    private int mEditType;

    public static ActivePublishFragment newInstance() {
        ActivePublishFragment detailFragment = new ActivePublishFragment();
        return detailFragment;
    }


    @Override
    protected PublishViewModel getViewModel() {
        return ViewModelProviders.of(this, factory).get(PublishViewModel.class);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mEditType = ((CirclePublishActivity) getHoldingActivity()).getmEditType();
        mHandParam = ((CirclePublishActivity) getHoldingActivity()).getmStartParam();

        initBanner(null);
        mViewModel.mDynamicPublishLV.observe(this, s -> {
            ToastUtils.showShort("发布成功");
            RxBus.getDefault().post(new RxEvent<>("dynamic_refresh", ""));
            getHoldingActivity().finish();
        });
        disposable = RxBus.getDefault().toObservable(RxEvent.class).subscribe(rxEvent -> {
            if (rxEvent.getT().equals("GroupSelect")) {
                mHandParam = (StaCirParam) rxEvent.getR();
                processStaparam();
            }
        });
        mSourceUpParam = new SourceUpParam(SourceUpParam.SOURCE_TYPE_AUTO, 1);
        mSourceUpParam.needCrop = true;
        sourceUpFragment = CircleSourceUpFragment.newInstance(mSourceUpParam);
        sourceUpFragment.setmSingleImageView(mBinding.get().imgPlaceholder);
        mBinding.get().frImgadd.setOnClickListener(v -> {
            sourceUpFragment.enterToSelect(1);
        });

        if (mEditType == 2) {
            if (mHandParam.serviceDataBean.getExtData().getResource() != null && mHandParam.serviceDataBean.getExtData().getResource().size() > 0) {
                ArrayList<LocalMedia> localMediaList = new ArrayList();
                List<ServiceDataBean.ResourceBean> resourceBeans = mHandParam.serviceDataBean.getExtData().getResource();
                if (resourceBeans != null && resourceBeans.size() > 0) {
                    for (int i = 0; i < resourceBeans.size(); i++) {
                        LocalMedia localMedia = new LocalMedia();
                        localMedia.setPictureType("1");
                        if (!TextUtils.isEmpty(resourceBeans.get(i).getImg())) {
                            localMedia.setPath(CommonBdUtils.getCompleteImageUrl(resourceBeans.get(i).getImg()));
                        } else {
                            localMedia.setPath(CommonBdUtils.getCompleteImageUrl(resourceBeans.get(i).getUrl()));
                        }
                        localMediaList.add(localMedia);
                    }
                }
                sourceUpFragment.processDataRep(localMediaList);
            }
        }
//        mBinding.get().perssionSelect.setOnClickListener(v -> {
//            if (mHandParam == null) {
//                ToastUtils.showShort("请先选择要同步的圈子");
//                return;
//            }
//            CirclePerssionSelectActivity.startMe(ActivePublishFragment.this.getHoldingActivity(), mHandParam);
//        });
//        mBinding.get().circleSelect.setOnClickListener(v -> {
//
//            CircleShareSelectActivity.startMe(ActivePublishFragment.this.getHoldingActivity(), mHandParam);
//        });

        FragmentUtils.add(getChildFragmentManager(), sourceUpFragment, R.id.souce_up_frame);
        mSourceUpParam.status.addOnPropertyChangedCallback(new Observable.OnPropertyChangedCallback() {
            @Override
            public void onPropertyChanged(Observable sender, int propertyId) {
                // 根据状态回调 来判断是否能发布了
                switch (mSourceUpParam.status.get()) {
                    case 2:
//                        realPublish();
                        selectSurfImgs.add(mSourceUpParam.imgList.get(0));
                        updateBanner(selectSurfImgs);

                        for (int i = 0; i < selectSurfImgs.size(); i++) {
                            Log.d("sss", "onPropertyChanged: ==================" + selectSurfImgs.get(i));

                        }
                        break;
                    case 3:
                        hidWaitDialog();
                        ToastUtils.showShort("上传资源失败请重试！");
                        break;
                }
            }
        });

        if (mEditType == 2) {
            mHandParam = processStaParam(mHandParam.serviceDataBean);
        }
        processStaparam();
    }


    private void initBanner(ArrayList<String> imgs) {

        GlideImageLoader glideImageLoader = new GlideImageLoader();
        mBinding.get().banner.setImageLoader(glideImageLoader);
        updateBanner(imgs);
        mBinding.get().banner.setDelayTime(2000);
        mBinding.get().banner.setOnBannerListener(position -> {
            ARouter.getInstance().build(AppRouter.ACTIVE_PUBLISH_BANNER_PREVIEW).withInt("position", position).withSerializable("imgList", selectSurfImgs).navigation(ActivePublishFragment.this.getHoldingActivity(), 10090);
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

    public void processStaparam() {

//        if (mHandParam != null) {
//
//            mBinding.get().circleSelect.setVisibility(View.VISIBLE);
//            if (!TextUtils.isEmpty(mHandParam.getCircleid())) {
//                mBinding.get().perssionSelect.setVisibility(View.VISIBLE);
//            }
//
////            if (mPerssionParam == null) {
//            if (TextUtils.isEmpty(mHandParam.extentron2)) {
//                mBinding.get().tvPerssionSelect.setText("全部");
//            } else {
//                // 编辑逻辑后面再写
//            }
//            switch (mHandParam.getExtenMap().size()) { // 圈子 分类
//                case 0:    // 没有一级栏目 只有圈子
//                    mBinding.get().tvCircleSelect.setText(mHandParam.getExtentron());
//                    break;
//                case 2:   // 没有选择二级栏目
//                    mBinding.get().tvCircleSelect.setText(mHandParam.getExtentron() + " / " + mHandParam.getExtenMap().get("classname1"));
//                    break;
//                case 4:  // 都有
//                    mBinding.get().tvCircleSelect.setText(mHandParam.getExtentron() + " / " + mHandParam.getExtenMap().get("classname1") + " / " + mHandParam.getExtenMap().get("classname2"));
//                    break;
//            }
//        }

    }


    private StaCirParam processStaParam(ServiceDataBean serviceDataBean) {
        mHandParam.getExtenMap().put("classid1", serviceDataBean.getClassid());
        mHandParam.getExtenMap().put("classname1", "");
        mHandParam.getExtenMap().put("classid2", serviceDataBean.getClassid2());
        mHandParam.getExtenMap().put("classname2", "");
//        if (serviceDataBean.getExtData() != null) {
//            mBinding.get().proEventDesc.setText(serviceDataBean.getExtData().getContent());
//        }
        return mHandParam;
    }


    @Override
    public void initImmersionBar() {

    }


    private String yearStart;
    private String mouthStart;
    private String dayStart;
    private String housStart;
    private String secondStart;

    private String yearEnd;
    private String mouthEnd;
    private String dayEnd;

    @Override
    protected void initView(View var1) {

//        mBinding.get().checkIsRelay.setOnCheckedChangeListener((buttonView, isChecked) -> {
//            if (!isChecked) {
//                if (mHandParam != null) {
//                    mHandParam = null;
//                }
//                if (mPerssionParam != null) {
//                    mPerssionParam = null;
//                }
//                mBinding.get().perssionSelect.setVisibility(View.GONE);
//                mBinding.get().circleSelect.setVisibility(View.GONE);
//                mBinding.get().tvCircleSelect.setText("");
//            } else {
//                mBinding.get().circleSelect.setVisibility(View.VISIBLE);
//            }
//        });

        mBinding.get().llStartTime.setOnClickListener(v -> {
            CommonWheelPicker.showTimePicker(ActivePublishFragment.this.getHoldingActivity(), (year, month, day, hous, second) -> {
                yearStart = year;
                mouthStart = month;
                dayStart = day;
                housStart = hous;
                secondStart = second;
                mBinding.get().tvStartTime.setText(year + month + day + hous + second);
                mBinding.get().tvEndTime.setText("");
            });
        });

        mBinding.get().llEndTime.setOnClickListener(v -> {
            if (TextUtils.isEmpty(yearStart)) {
                ToastUtils.showShort("请先选择开始时间");
                return;
            }
            CommonWheelPicker.showTimePicker(ActivePublishFragment.this.getHoldingActivity(),
                    new int[]{Integer.parseInt(yearStart), Integer.parseInt(mouthStart), Integer.parseInt(dayStart),
                            Integer.parseInt(housStart), Integer.parseInt(secondStart)},
                    (year, month, day, hous, second) -> {
                        mBinding.get().tvEndTime.setText(year + month + day + hous + second);
                    });
        });
        mBinding.get().llLoaction.setOnClickListener(v -> {
            ARouter.getInstance().build(AppRouter.COMMON_LOCATION_ACTIVITY).navigation();
        });
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            if (requestCode == 10090) {
                selectSurfImgs = (ArrayList<String>) data.getSerializableExtra("imgList");
                updateBanner(selectSurfImgs);
            } else {
                sourceUpFragment.onActivityResult(requestCode, resultCode, data);
            }
        }
    }


    // 发布
    public void publish() {

//        if (TextUtils.isEmpty(mBinding.get().proEventDesc.getText().toString())) {
//            ToastUtils.showShort("请输入要发表的内容");
//            return;
//        }

        if (TextUtils.isEmpty(mHandParam.getCircleid()) || TextUtils.isEmpty(mHandParam.getUtid())) {
            ToastUtils.showShort("请选择要发布的圈子");
            return;
        }

        if (sourceUpFragment.selectList != null && sourceUpFragment.selectList.size() > 0) {
            sourceUpFragment.processUpload();
        } else {
            realPublish();
        }

    }

    private void realPublish() {

        UserInfoVo userInfoVo = CacheUtils.getUser();
        HashMap<String, String> paramMap = new HashMap();
        paramMap.put("memberid", userInfoVo.uid);
        paramMap.put("uuid", userInfoVo.uuid);
        paramMap.put("type", "dynamic");
//        paramMap.put("content", mBinding.get().proEventDesc.getText().toString().trim());
        paramMap.put("circleid", mHandParam.getCircleid());
        paramMap.put("utid", mHandParam.getUtid());
        if (!TextUtils.isEmpty(mHandParam.getExtenMap().get("classid1"))) {
            paramMap.put("classid", mHandParam.getExtenMap().get("classid1"));
        }
        if (!TextUtils.isEmpty(mHandParam.getExtenMap().get("classid2"))) {
            paramMap.put("classid2", mHandParam.getExtenMap().get("classid2"));
        }
        if (mEditType == 2) {
            paramMap.put("dataid", mHandParam.serviceDataBean.getDataid());
        }
        if (TextUtils.isEmpty(mHandParam.extentron2) && mHandParam.extentronList.size() == 0) {
            paramMap.put("visibleType", "0");   // extentronList
        } else {
            paramMap.put("visibleType", "1");

            StringBuilder stringBuilder = new StringBuilder();
            for (int i = 0; i < mHandParam.extentronList.size(); i++) {
                String id = (String) mHandParam.extentronList.get(i);
                stringBuilder.append(id).append(",");
            }
            if (stringBuilder.length() > 1) {
                String ids = stringBuilder.substring(0, stringBuilder.length() - 1);
                paramMap.put("groupids", ids);
            }
        }

        if (mSourceUpParam.imgList.size() > 0) {
            for (int i = 0; i < mSourceUpParam.imgList.size(); i++) {
                paramMap.put("resource[" + i + "][t]", "1");
                paramMap.put("resource[" + i + "][url]", mSourceUpParam.imgList.get(i));
                paramMap.put("resource[" + i + "][img]", mSourceUpParam.imgList.get(i));
                paramMap.put("resource[" + i + "][sort]", i + 1 + "");
            }
        }
        if (mSourceUpParam.upLoadVideoList.size() > 0) {
            for (int i = 0; i < mSourceUpParam.upLoadVideoList.size(); i++) {
                paramMap.put("resource[" + i + "][t]", "2");
                paramMap.put("resource[" + i + "][url]", mSourceUpParam.upLoadVideoList.get(i).getVideoUrl());
                paramMap.put("resource[" + i + "][img]", mSourceUpParam.upLoadVideoList.get(i).getVideoImgUrl());
                paramMap.put("resource[" + i + "][sort]", i + 1 + "");
            }
        }
        this.getHoldingActivity().runOnUiThread(() -> mViewModel.publishNews(paramMap));
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (disposable != null) {
            disposable.dispose();
        }
    }


}
