package com.bfhd.circle.ui.circle.circlepublish;

import android.arch.lifecycle.ViewModelProvider;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.databinding.Observable;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.view.View;

import com.bfhd.circle.R;
import com.bfhd.circle.base.CommonFragment;
import com.bfhd.circle.base.Constant;
import com.bfhd.circle.base.ViewEventResouce;
import com.bfhd.circle.databinding.CircleFragmentCirclePubActiveBinding;
import com.bfhd.circle.ui.circle.CirclePerssionSelectActivity;
import com.bfhd.circle.ui.circle.CirclePublishActivity;
import com.bfhd.circle.ui.circle.CircleShareSelectActivity;
import com.bfhd.circle.ui.common.CircleSourceUpFragment;
import com.bfhd.circle.vm.CirclePublishViewModel;
import com.bfhd.circle.vo.ServiceDataBean;
import com.bfhd.circle.vo.bean.SourceUpParam;
import com.bfhd.circle.vo.bean.StaCirParam;
import com.dcbfhd.utilcode.utils.FragmentUtils;
import com.dcbfhd.utilcode.utils.ToastUtils;
import com.docker.common.common.utils.cache.CacheUtils;
import com.docker.common.common.utils.rxbus.RxBus;
import com.docker.common.common.utils.rxbus.RxEvent;
import com.docker.common.common.vo.UserInfoVo;
import com.luck.picture.lib.entity.LocalMedia;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import javax.inject.Inject;

import io.reactivex.disposables.Disposable;

/*
 * 动态类型的动态
 * */
public class CirclePubActiveFragment extends CommonFragment<CirclePublishViewModel, CircleFragmentCirclePubActiveBinding> {

    private SourceUpParam mSourceUpParam;
    private CircleSourceUpFragment sourceUpFragment;

    private Disposable disposable;

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
        return R.layout.circle_fragment_circle_pub_active;
    }

    private int mEditType;

    public static CirclePubActiveFragment newInstance() {
        CirclePubActiveFragment detailFragment = new CirclePubActiveFragment();
        return detailFragment;
    }


    @Override
    protected CirclePublishViewModel getViewModel() {
        return ViewModelProviders.of(this, factory).get(CirclePublishViewModel.class);
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
        disposable = RxBus.getDefault().toObservable(RxEvent.class).subscribe(rxEvent -> {
            if (rxEvent.getT().equals("GroupSelect")) {
                mHandParam = (StaCirParam) rxEvent.getR();
                processStaparam();
            }
            if (rxEvent.getT().equals("circle_perrsion_update")) { // 权限
                mHandParam = (StaCirParam) rxEvent.getR();
                if (mHandParam != null) {
                    mBinding.get().tvPerssionSelect.setText(mHandParam.extentron2);
                }
            }
        });
        mSourceUpParam = new SourceUpParam(SourceUpParam.SOURCE_TYPE_AUTO, 9);
        sourceUpFragment = CircleSourceUpFragment.newInstance(mSourceUpParam);
        if (mEditType == 2) {
            if (mHandParam.serviceDataBean.getExtData().getResource() != null && mHandParam.serviceDataBean.getExtData().getResource().size() > 0) {
                ArrayList<LocalMedia> localMediaList = new ArrayList();
                List<ServiceDataBean.ResourceBean> resourceBeans = mHandParam.serviceDataBean.getExtData().getResource();
                if (resourceBeans != null && resourceBeans.size() > 0) {
                    for (int i = 0; i < resourceBeans.size(); i++) {
                        LocalMedia localMedia = new LocalMedia();

                        if (2 == resourceBeans.get(i).getT()) {
                            localMedia.setPictureType("video/mp4");
                            localMedia.setPath(Constant.getCompleteImageUrl(resourceBeans.get(i).getUrl()));
                        } else {
                            localMedia.setPictureType("image/jpeg");
                            localMedia.setPath(Constant.getCompleteImageUrl(resourceBeans.get(i).getImg()));
                        }

//                        localMedia.setPictureType("1");
//                        if (!TextUtils.isEmpty(resourceBeans.get(i).getImg())) {
//                            localMedia.setPath(Constant.getCompleteImageUrl(resourceBeans.get(i).getImg()));
//                        } else {
//                            localMedia.setPath(Constant.getCompleteImageUrl(resourceBeans.get(i).getUrl()));
//                        }
                        localMediaList.add(localMedia);
                    }
                }
                sourceUpFragment.processDataRep(localMediaList);
            }
        }
        mBinding.get().perssionSelect.setOnClickListener(v -> {
            if (mHandParam == null) {
                ToastUtils.showShort("请先选择要同步的圈子");
                return;
            }
            CirclePerssionSelectActivity.startMe(CirclePubActiveFragment.this.getHoldingActivity(), mHandParam);
        });
        mBinding.get().circleSelect.setOnClickListener(v -> {

            CircleShareSelectActivity.startMe(CirclePubActiveFragment.this.getHoldingActivity(), mHandParam);
        });

        FragmentUtils.add(getChildFragmentManager(), sourceUpFragment, R.id.souce_up_frame);
        mSourceUpParam.status.addOnPropertyChangedCallback(new Observable.OnPropertyChangedCallback() {
            @Override
            public void onPropertyChanged(Observable sender, int propertyId) {
                // 根据状态回调 来判断是否能发布了
                switch (mSourceUpParam.status.get()) {
                    case 2:
                        realPublish();
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

    public void processStaparam() {

        if (mHandParam != null) {

            mBinding.get().circleSelect.setVisibility(View.VISIBLE);
            if (!TextUtils.isEmpty(mHandParam.getCircleid())) {
                mBinding.get().perssionSelect.setVisibility(View.VISIBLE);
            }

//            if (mPerssionParam == null) {
            if (TextUtils.isEmpty(mHandParam.extentron2)) {
                mBinding.get().tvPerssionSelect.setText("全部");
            } else {
                // 编辑逻辑后面再写
            }
            switch (mHandParam.getExtenMap().size()) { // 圈子 分类
                case 0:    // 没有一级栏目 只有圈子
                    mBinding.get().tvCircleSelect.setText(mHandParam.getExtentron());
                    break;
                case 2:   // 没有选择二级栏目
                    mBinding.get().tvCircleSelect.setText(mHandParam.getExtentron() + " / " + mHandParam.getExtenMap().get("classname1"));
                    break;
                case 4:  // 都有
                    mBinding.get().tvCircleSelect.setText(mHandParam.getExtentron() + " / " + mHandParam.getExtenMap().get("classname1") + " / " + mHandParam.getExtenMap().get("classname2"));
                    break;
            }
        }

    }


    private StaCirParam processStaParam(ServiceDataBean serviceDataBean) {
        mHandParam.getExtenMap().put("classid1", serviceDataBean.getClassid());
        mHandParam.getExtenMap().put("classname1", "");
        mHandParam.getExtenMap().put("classid2", serviceDataBean.getClassid2());
        mHandParam.getExtenMap().put("classname2", "");
        if (serviceDataBean.getExtData() != null) {
            mBinding.get().proEventDesc.setText(serviceDataBean.getExtData().getContent());
        }
        return mHandParam;
    }


    @Override
    public void initImmersionBar() {

    }

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

    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        sourceUpFragment.onActivityResult(requestCode, resultCode, data);
    }


    // 发布
    public void publish() {

        if (TextUtils.isEmpty(mBinding.get().proEventDesc.getText().toString())) {
            ToastUtils.showShort("请输入要发表的内容");
            return;
        }

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
        paramMap.put("content", mBinding.get().proEventDesc.getText().toString().trim());
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
    public void OnVmEnentListner(ViewEventResouce viewEventResouce) {
        super.OnVmEnentListner(viewEventResouce);

        if (viewEventResouce.eventType == 104) {
            ToastUtils.showShort("发布成功");
            RxBus.getDefault().post(new RxEvent<>("dynamic_refresh", ""));
            getHoldingActivity().finish();
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (disposable != null) {
            disposable.dispose();
        }
    }


}
