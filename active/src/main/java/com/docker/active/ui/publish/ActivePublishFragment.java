package com.docker.active.ui.publish;

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
import com.baidu.location.BDLocation;
import com.baidu.mapapi.search.geocode.ReverseGeoCodeResult;
import com.dcbfhd.utilcode.utils.FragmentUtils;
import com.dcbfhd.utilcode.utils.ToastUtils;
import com.docker.active.R;
import com.docker.active.databinding.ProActivePubFragmentBinding;
import com.docker.active.vm.ActiveCommonViewModel;
import com.docker.active.vo.ActivePubVo;
import com.docker.active.vo.LinkageVo;
import com.docker.cirlev2.ui.common.CircleSourceUpFragment;
import com.docker.cirlev2.ui.publish.CirclePublishActivity;
import com.docker.cirlev2.util.GlideImageLoader;
import com.docker.cirlev2.vo.entity.ServiceDataBean;
import com.docker.cirlev2.vo.param.SourceUpParam;
import com.docker.cirlev2.vo.param.StaCirParam;
import com.docker.common.common.binding.CommonBdUtils;
import com.docker.common.common.router.AppRouter;
import com.docker.common.common.ui.base.NitCommonFragment;
import com.docker.common.common.utils.TimerUtils;
import com.docker.common.common.utils.cache.CacheUtils;
import com.docker.common.common.utils.location.LocationManager;
import com.docker.common.common.utils.rxbus.RxBus;
import com.docker.common.common.utils.rxbus.RxEvent;
import com.docker.common.common.vo.UserInfoVo;
import com.docker.common.common.widget.picker.CommonWheelPicker;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;

import javax.inject.Inject;

import io.reactivex.disposables.Disposable;

import static android.app.Activity.RESULT_OK;
import static com.docker.common.common.router.AppRouter.ACTIVE_PUBLISH;

/*
 * 活动类型的动态
 * */
@Route(path = ACTIVE_PUBLISH)
public class ActivePublishFragment extends NitCommonFragment<ActiveCommonViewModel, ProActivePubFragmentBinding> {

    private SourceUpParam mSourceUpParam;
    private CircleSourceUpFragment sourceUpFragment;

    private String content;
    private Disposable disposable;
    private ArrayList<String> selectSurfImgs = new ArrayList<>();
    public LinkageVo linkageVo;
    private ActivePubVo activePubVo;

    public String activitytitle;
    public String activityid;


    @Inject
    LocationManager locationManager;

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
    protected ActiveCommonViewModel getViewModel() {
        return ViewModelProviders.of(this, factory).get(ActiveCommonViewModel.class);
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

        Bundle bundle = getArguments().getBundle("bundle");
        if (bundle != null) {
            activityid = bundle.getString("activityid");
            activitytitle = bundle.getString("activitytitle");
        }


        mViewModel.mDynamicPublishLV.observe(this, s -> {
            ToastUtils.showShort("发布成功");
            RxBus.getDefault().post(new RxEvent<>("active_refresh", ""));
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
            mViewModel.mActiveDetailLv.observe(this, activeVo -> {
                activePubVo = new ActivePubVo();
                activePubVo.utid = activeVo.utid;
                activePubVo.actType = activeVo.actType;
                activePubVo.memberid = activeVo.memberid;
                activePubVo.uuid = activeVo.uuid;
                activePubVo.title = activeVo.title;

                if (activeVo.banner != null && activeVo.banner.size() > 0) {
                    for (int i = 0; i < activeVo.banner.size(); i++) {
                        if (!TextUtils.isEmpty(activeVo.banner.get(i).getImg())) {
                            selectSurfImgs.add(CommonBdUtils.getCompleteImageUrl(activeVo.banner.get(i).getImg()));
                        } else {
                            selectSurfImgs.add(CommonBdUtils.getCompleteImageUrl(activeVo.banner.get(i).getUrl()));
                        }
                    }
                    initBanner(selectSurfImgs);
                }

                activePubVo.isDate = activeVo.isDate;
                if ("1".equals(activePubVo.isDate)) {
                    mBinding.get().rbLimitTime.setChecked(true);
                } else {
                    mBinding.get().rbNlimTime.setChecked(true);
                }

                activePubVo.dataid = activeVo.dataid;

                activePubVo.startDate = activeVo.startDate;


                mBinding.get().tvStartTime.setText(activePubVo.startDate);


                yearStart = String.valueOf(TimerUtils.getYear(activePubVo.startDate));
                mouthStart = String.valueOf(TimerUtils.getMonth(activePubVo.startDate));
                dayStart = String.valueOf(TimerUtils.getDay(activePubVo.startDate));
                housStart = String.valueOf(TimerUtils.getHour(activePubVo.startDate));
                secondStart = String.valueOf(TimerUtils.getMinute(activePubVo.startDate));


                activePubVo.endDate = activeVo.endDate;
                mBinding.get().tvEndTime.setText(activePubVo.endDate);
                activePubVo.num = activeVo.limitNum;
                activePubVo.situs = activeVo.situs;

                activePubVo.location = activeVo.location;
                activePubVo.address = activeVo.address;
                activePubVo.cityCode = activeVo.cityCode;
                activePubVo.lng = activeVo.lng;
                activePubVo.lat = activeVo.lat;
                activePubVo.sponsorName = activeVo.sponsorName;
                activePubVo.contact = activeVo.contact;
                activePubVo.content = activeVo.content;
                activePubVo.contentshow = "已编辑";

                activePubVo.signAudit = activeVo.signAudit;
                if ("0".equals(activePubVo.signAudit)) {
                    mBinding.get().activeHeCheck.setChecked(false);
                } else {
                    mBinding.get().activeHeCheck.setChecked(true);
                }

                activePubVo.circleid = activeVo.circleid;
                activePubVo.circleidshow = activeVo.circlename;
                activePubVo.actTypeshow = activeVo.actTypeName;

                mBinding.get().llSelCircle.setVisibility(View.GONE);
                activePubVo.point = activeVo.point;
                mBinding.get().setPub(activePubVo);


            });
            HashMap<String, String> parm = new HashMap<>();
            parm.put("activityid", activityid);
            mViewModel.fetchActiveVo(parm);

        } else {
            initBanner(null);
            activePubVo = new ActivePubVo();
            if (mHandParam != null) {  // 带有圈子信息进来的
                mBinding.get().llSelCircle.setVisibility(View.GONE);
                activePubVo.circleid = mHandParam.getCircleid();
                activePubVo.utid = mHandParam.getUtid();
            } else {
                UserInfoVo userInfoVo = CacheUtils.getUser();
                if ("2".equals(userInfoVo.reg_type)) { // 股东
                    mBinding.get().llSelCircle.setVisibility(View.VISIBLE);
                } else { // 默认圈子
                    activePubVo.circleid = userInfoVo.circleid;
                    activePubVo.utid = userInfoVo.utid;
                }
            }
            mBinding.get().setPub(activePubVo);
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

//        if (mEditType == 2) {
//            mHandParam = processStaParam(mHandParam.serviceDataBean);
//        }
//        processStaparam();
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

        mBinding.get().llSelCircle.setOnClickListener(v -> { // 选择分部
            //ACCOUNT_BRANCH_LIST
            ARouter.getInstance().build(AppRouter.ACCOUNT_BRANCH_LIST).withInt("flag", 1).navigation(ActivePublishFragment.this.getHoldingActivity(), 10067);
        });

        mBinding.get().rbLimitTime.setOnCheckedChangeListener((buttonView, isChecked) -> {
            mBinding.get().getPub().isDate = "1";
        });
        mBinding.get().rbNlimTime.setOnCheckedChangeListener((buttonView, isChecked) -> {
            mBinding.get().getPub().isDate = "0";
        });

        mBinding.get().llStartTime.setOnClickListener(v -> {
            CommonWheelPicker.showTimePicker(ActivePublishFragment.this.getHoldingActivity(), (year, month, day, hous, second) -> {
                yearStart = year;
                mouthStart = month;
                dayStart = day;
                housStart = hous;
                secondStart = second;

                String str = year + "-" + month + "-" + day + " " + hous + ":" + second + ":00";
                SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                Date date = null;
                try {
                    date = simpleDateFormat.parse(str);
                } catch (ParseException e) {
                    e.printStackTrace();
                }
                long ts = date.getTime();

                mBinding.get().getPub().startDate = String.valueOf(str);
                mBinding.get().tvStartTime.setText(str);

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
                        String str = year + "-" + month + "-" + day + " " + hous + ":" + second + ":00";
                        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                        Date date = null;
                        try {
                            date = simpleDateFormat.parse(str);
                        } catch (ParseException e) {
                            e.printStackTrace();
                        }
                        long ts = date.getTime();
                        mBinding.get().getPub().endDate = String.valueOf(str);
                        mBinding.get().tvEndTime.setText(str);
                    });
        });

        mBinding.get().llLoaction.setOnClickListener(v -> {
//            ARouter.getInstance().build(AppRouter.COMMON_LOCATION_ACTIVITY).navigation(getHoldingActivity(), 10099);
            ToastUtils.showShort("定位中...");
            locationManager.processLocation(this.getHoldingActivity(),
                    (o) -> {
                        if (o != null) {
                            mBinding.get().getPub().lat = String.valueOf(((BDLocation) o).getLatitude());
                            mBinding.get().getPub().lng = String.valueOf(((BDLocation) o).getLongitude());
                            mBinding.get().getPub().cityCode = ((BDLocation) o).getCityCode();
                            mBinding.get().getPub().location = ((BDLocation) o).getAddrStr();
                            mBinding.get().tvLocation.setText(((BDLocation) o).getAddrStr());
                        } else {
                            Log.d("sss", "onCreate: =======定位失败========");

                        }
                    },
                    (geo) -> {
                        if (geo != null) {
                            Log.d("sss", "==city==" + ((ReverseGeoCodeResult) geo).getAddress());
                        } else {
                            Log.d("sss", "==citycode=failed=");
                        }
                    }
            );


        });

        // 活动内容
        mBinding.get().llContent.setOnClickListener(v -> {
            ARouter.getInstance().build(AppRouter.ACTIVE_CONTENT_EDIT).withString("data", mBinding.get().getPub().content).navigation(this.getHoldingActivity(), 10080);
        });

        // 活动类型
        mBinding.get().llType.setOnClickListener(v -> {
            ARouter.getInstance().build(AppRouter.ACTIVE_TYPE_SELECT).navigation(getHoldingActivity(), 10097);
        });

        mBinding.get().activeHeCheck.setOnCheckedChangeListener((buttonView, isChecked) -> {
            if (mBinding.get().getPub() == null) {
                return;
            }
            if (isChecked) {
                mBinding.get().getPub().signAudit = "1";
            } else {
                mBinding.get().getPub().signAudit = "0";
            }
        });

        mBinding.get().publish.setOnClickListener(v -> {
            publish();
        });

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
        if (resultCode == RESULT_OK) {
            if (requestCode == 10090) {
                selectSurfImgs = (ArrayList<String>) data.getSerializableExtra("imgList");
                updateBanner(selectSurfImgs);
            } else if (requestCode == 10080) {
                content = data.getStringExtra("data");
                if (!TextUtils.isEmpty(content)) {
                    mBinding.get().tvContent.setText("已编辑");
                    mBinding.get().getPub().content = content;
                } else {
                    mBinding.get().tvContent.setText("");
                }
            } else if (requestCode == 10097) {
                linkageVo = (LinkageVo) data.getSerializableExtra("linkageVo");
                mBinding.get().tvType.setText(linkageVo.name);
                mBinding.get().getPub().actType = linkageVo.linkageid;

            } else if (requestCode == 10099) {
                mBinding.get().getPub().lat = data.getStringExtra("lat");
                mBinding.get().getPub().lng = data.getStringExtra("lng");
                mBinding.get().getPub().cityCode = data.getStringExtra("citycode");
                mBinding.get().getPub().location = data.getStringExtra("address");
            } else if (requestCode == 10067) {
                mBinding.get().getPub().circleid = data.getStringExtra("circleid");
                mBinding.get().getPub().utid = data.getStringExtra("utid");
                mBinding.get().tvCircleName.setText(data.getStringExtra("circlename"));
            } else {
                sourceUpFragment.onActivityResult(requestCode, resultCode, data);
            }
        }
    }


    // 发布
    public void publish() {

        if (selectSurfImgs == null || selectSurfImgs.size() == 0) {
            ToastUtils.showShort("请选择活动封面图");
            return;
        }

        if (TextUtils.isEmpty(mBinding.get().tvContent.getText().toString())) {
            ToastUtils.showShort("请输入要发表的内容");
            return;
        }

        if (TextUtils.isEmpty(mBinding.get().getPub().title)) {
            ToastUtils.showShort("活动名称不能为空");
            return;
        }

        if (TextUtils.isEmpty(mBinding.get().getPub().startDate)) {
            ToastUtils.showShort("活动开时间不能为空");
            return;
        }

        if (TextUtils.isEmpty(mBinding.get().getPub().endDate)) {
            ToastUtils.showShort("活动结束时间不能为空");
            return;
        }

        if (TextUtils.isEmpty(mBinding.get().getPub().location)) {
            ToastUtils.showShort("活动定位不能为空");
            return;
        }
        if (TextUtils.isEmpty(mBinding.get().getPub().address)) {
            ToastUtils.showShort("活动具体地址不能为空");
            return;
        }

        if (TextUtils.isEmpty(mBinding.get().getPub().num)) {
            ToastUtils.showShort("活动人数不能为空");
            return;
        }
        if (TextUtils.isEmpty(mBinding.get().getPub().content)) {
            ToastUtils.showShort("活动内容不能为空");
            return;
        }
        if (TextUtils.isEmpty(mBinding.get().getPub().actType)) {
            ToastUtils.showShort("活动类型不能为空");
            return;
        }
        if (TextUtils.isEmpty(mBinding.get().getPub().sponsorName)) {
            ToastUtils.showShort("活动主办方名称不能为空");
            return;
        }
        if (TextUtils.isEmpty(mBinding.get().getPub().contact)) {
            ToastUtils.showShort("活动主办方电话不能为空");
            return;
        }
//        if (TextUtils.isEmpty(mBinding.get().getPub().circleidshow)) {
//            ToastUtils.showShort("分舵不能为空");
//            return;
//        }


//        if (TextUtils.isEmpty(mHandParam.getCircleid()) || TextUtils.isEmpty(mHandParam.getUtid())) {
//            ToastUtils.showShort("请选择要发布的圈子");
//            return;
//        }

        if (sourceUpFragment.selectList != null && sourceUpFragment.selectList.size() > 0) {
            sourceUpFragment.processUpload();
        } else {
            realPublish();
        }

    }

    private void realPublish() {

        if (TextUtils.isEmpty(mBinding.get().getPub().utid)) {
            ToastUtils.showShort("请选择要同步的分舵");
            return;
        }

        UserInfoVo userInfoVo = CacheUtils.getUser();
        HashMap<String, String> paramMap = new HashMap();
        paramMap.put("memberid", userInfoVo.uid);
        paramMap.put("uuid", userInfoVo.uuid);

//        paramMap.put("actType", mBinding.get().getPub().actType);
        paramMap.put("actType", mBinding.get().getPub().actType);

        paramMap.put("utid", mBinding.get().getPub().utid);
        paramMap.put("circleid", mBinding.get().getPub().circleid);

        paramMap.put("point", mBinding.get().getPub().point);
//        paramMap.put("title", mBinding.get().getPub().title);
        paramMap.put("title", mBinding.get().getPub().title);
//        if (mSourceUpParam.imgList.size() > 0) {
//            StringBuilder stringBuilder = new StringBuilder();
//            for (int i = 0; i < mSourceUpParam.imgList.size(); i++) {
//                stringBuilder.append(mSourceUpParam.imgList.get(i)).append(",");
//            }
//            paramMap.put("banner", stringBuilder.substring(0, stringBuilder.length() - 1));
//        }


        if (selectSurfImgs.size() > 0) {
            for (int i = 0; i < selectSurfImgs.size(); i++) {
                paramMap.put("banner[" + i + "][t]", "1");
                paramMap.put("banner[" + i + "][url]", selectSurfImgs.get(i));
                paramMap.put("banner[" + i + "][img]", selectSurfImgs.get(i));
                paramMap.put("banner[" + i + "][sort]", i + 1 + "");
            }
        }
//        if (mSourceUpParam.upLoadVideoList.size() > 0) {
//            for (int i = 0; i < mSourceUpParam.upLoadVideoList.size(); i++) {
//                paramMap.put("banner[" + i + "][t]", "2");
//                paramMap.put("banner[" + i + "][url]", mSourceUpParam.upLoadVideoList.get(i).getVideoUrl());
//                paramMap.put("banner[" + i + "][img]", mSourceUpParam.upLoadVideoList.get(i).getVideoImgUrl());
//                paramMap.put("banner[" + i + "][sort]", i + 1 + "");
//            }
//        }

        paramMap.put("isDate", mBinding.get().getPub().isDate);
        paramMap.put("startDate", mBinding.get().getPub().startDate);
        paramMap.put("endDate", mBinding.get().getPub().endDate);
        paramMap.put("situs", "2");
        paramMap.put("location", mBinding.get().getPub().location);
        paramMap.put("address", mBinding.get().getPub().address);
        paramMap.put("lat", mBinding.get().getPub().lat);
        paramMap.put("lng", mBinding.get().getPub().lng);
        paramMap.put("cityCode", mBinding.get().getPub().cityCode);
        paramMap.put("sponsorName", mBinding.get().getPub().sponsorName);
        paramMap.put("contact", mBinding.get().getPub().contact);
        paramMap.put("content", mBinding.get().getPub().content);
        paramMap.put("signAudit", mBinding.get().getPub().signAudit);
        paramMap.put("limitNum", mBinding.get().getPub().num);
        paramMap.put("type", "activity");

        if (!TextUtils.isEmpty(mBinding.get().getPub().dataid)) {
            paramMap.put("dataid", mBinding.get().getPub().dataid);
        }
        mViewModel.publishActive(paramMap);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (disposable != null) {
            disposable.dispose();
        }
    }


}
