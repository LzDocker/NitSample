package com.bfhd.circle.ui.circle;

import android.Manifest;
import android.arch.lifecycle.ViewModelProvider;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.android.arouter.launcher.ARouter;
import com.baidu.location.BDAbstractLocationListener;
import com.baidu.location.BDLocation;
import com.baidu.location.LocationClient;
import com.baidu.location.LocationClientOption;
import com.bfhd.circle.R;
import com.bfhd.circle.base.CommonFragment;
import com.bfhd.circle.base.Constant;
import com.bfhd.circle.base.HivsBaseActivity;
import com.bfhd.circle.base.ViewEventResouce;
import com.bfhd.circle.databinding.CircleActivityDynamicDetailBinding;
import com.bfhd.circle.ui.circle.dynamicdetail.CirclH5DetailFragment;
import com.bfhd.circle.ui.circle.dynamicdetail.CircleDynamicDetailFragment;
import com.bfhd.circle.vm.CircleDynamicViewModel;
import com.bfhd.circle.vo.ServiceDataBean;
import com.bfhd.circle.vo.bean.StaDetailParam;
import com.dcbfhd.utilcode.utils.FragmentUtils;
import com.dcbfhd.utilcode.utils.ToastUtils;
import com.docker.common.common.router.AppRouter;
import com.docker.common.common.utils.cache.CacheUtils;
import com.docker.common.common.utils.rxbus.RxBus;
import com.docker.common.common.utils.rxbus.RxEvent;
import com.docker.common.common.vo.ShareBean;
import com.docker.common.common.vo.UserInfoVo;
import com.docker.core.widget.BottomSheetDialog;
import com.luck.picture.lib.permissions.RxPermissions;
import com.umeng.socialize.ShareAction;
import com.umeng.socialize.UMShareAPI;
import com.umeng.socialize.UMShareListener;
import com.umeng.socialize.bean.SHARE_MEDIA;
import com.umeng.socialize.media.UMImage;
import com.umeng.socialize.media.UMWeb;
import com.umeng.socialize.shareboard.ShareBoardConfig;

import java.util.HashMap;

import javax.inject.Inject;

import io.reactivex.disposables.Disposable;

/*
 * 动态详情界面
 *
 *
 * */
@Route(path = AppRouter.CIRCLE_DETAIL)
public class CircleDynamicDetailActivity extends HivsBaseActivity<CircleDynamicViewModel, CircleActivityDynamicDetailBinding> {
    @Inject
    ViewModelProvider.Factory factory;
    private StaDetailParam mStaparam;
    public ServiceDataBean serviceDataBean;
    private Disposable disposable;
    private boolean isInit = false;
    private CommonFragment curCommonFragment;
    private String dynamicid;

    //    private int type;
    public static void startMe(Context context, StaDetailParam mStaparam) {
        Intent intent = new Intent(context, CircleDynamicDetailActivity.class);
        Bundle bundle = new Bundle();
        bundle.putSerializable("mStaparam", mStaparam);
        intent.putExtras(bundle);
        context.startActivity(intent);
    }

    public static void startMe(Context context, String dynamicid) {
        Intent intent = new Intent(context, CircleDynamicDetailActivity.class);
        Bundle bundle = new Bundle();
        bundle.putSerializable("dynamicid", dynamicid);
        intent.putExtras(bundle);
        context.startActivity(intent);
    }

    protected void inject() {
        super.inject();
        ARouter.getInstance().inject(this);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.circle_activity_dynamic_detail;
    }

    @Override
    public CircleDynamicViewModel getmViewModel() {
        return ViewModelProviders.of(this, factory).get(CircleDynamicViewModel.class);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        mStaparam = (StaDetailParam) getIntent().getSerializableExtra("mStaparam");
        dynamicid = getIntent().getStringExtra("dynamicid");
        if (mStaparam == null) {
            mStaparam = new StaDetailParam();
            mStaparam.dynamicId = dynamicid;
        }

        super.onCreate(savedInstanceState);
        mToolbar.hide();
        mBinding.setViewmodel(mViewModel);
        disposable = RxBus.getDefault().toObservable(RxEvent.class).subscribe(rxEvent -> {
            if (rxEvent.getT().equals("login_state_change")) {
                finish();
                CircleDynamicDetailActivity.startMe(CircleDynamicDetailActivity.this, mStaparam);
            }
            if (rxEvent.getT().equals("dynamic_refresh") && TextUtils.isEmpty((String) rxEvent.getR())) { // 编辑成功后后返回
                UserInfoVo userInfoVo = CacheUtils.getUser();
                HashMap<String, String> param = new HashMap();
                param.put("dynamicid", mStaparam.dynamicId);
                if (!"-1".equals(userInfoVo.memberid)) {
                    param.put("memberid", userInfoVo.uid);
                    param.put("uuid", userInfoVo.uuid);
                }
                mViewModel.fechDynamicDetail(param);
            }
        });

        UserInfoVo userInfoVo = CacheUtils.getUser();
        HashMap<String, String> param = new HashMap();
        param.put("dynamicid", mStaparam.dynamicId);
        if (!"-1".equals(userInfoVo.memberid)) {
            param.put("memberid", userInfoVo.uid);
            param.put("uuid", userInfoVo.uuid);
        }
        mViewModel.fechDynamicDetail(param);

        mBinding.empty.setOnretryListener(() -> {
            UserInfoVo userInfoVo1 = CacheUtils.getUser();
            HashMap<String, String> param1 = new HashMap();
            param1.put("dynamicid", mStaparam.dynamicId);
            if (!"-1".equals(userInfoVo1.memberid)) {
                param1.put("memberid", userInfoVo1.uid);
                param1.put("uuid", userInfoVo1.uuid);
            }
            mViewModel.fechDynamicDetail(param);
        });
    }

    private void processToolbar() {
        switch (serviceDataBean.getType()) {
            case "car":
            case "time":
            case "datetime":
            case "product":
            case "house":
            case "goods":
                mStaparam.uiType = 3;
                break;
            case "project":
            case "dynamic":
                mStaparam.uiType = 0;
                break;
            case "news":
                mStaparam.uiType = 1;
                mStaparam.speical = 1;
                break;
        }

        if ("answer".equals(mStaparam.type)) {
            mBinding.title.setText("问答详情");
        } else {
            mBinding.title.setText("详情");
        }
        if ("news".equals(mStaparam.type) || "dynamic".equals(mStaparam.type) || ("project").equals(mStaparam.type)) {
            mBinding.circleHeaderBar.setVisibility(View.VISIBLE);
        } else {
            mBinding.circleHeaderBar.setVisibility(View.GONE);
        }
        if (!serviceDataBean.getUuid().equals(CacheUtils.getUser().uuid)) {
            mBinding.circleJubao.setVisibility(View.VISIBLE);
        }
        mBinding.ivBack.setOnClickListener(v -> {
            finish();
        });

        switch (mStaparam.uiType) {
            case 2:
            case 0:
                curCommonFragment = CircleDynamicDetailFragment.newInstance(mStaparam, serviceDataBean);
                break;
            case 3:
            case 1:
                curCommonFragment = CirclH5DetailFragment.newInstance(mStaparam, serviceDataBean);
                break;
        }
        FragmentUtils.add(getSupportFragmentManager(), curCommonFragment, R.id.detail_frame);
        mBinding.empty.hide();
    }

    @Override
    public void initView() {

        mBinding.ivShare.setOnClickListener(v -> {
            processShare();
        });

    }


    public void processShare() {
        if (serviceDataBean != null) {
            HashMap<String, String> params = new HashMap<>();
            UserInfoVo userInfoVo = CacheUtils.getUser();
            params.put("type", serviceDataBean.getType());
            params.put("dynamicid", serviceDataBean.getDynamicid());
            params.put("circleid", serviceDataBean.getCircleid());
            params.put("utid", serviceDataBean.getUtid());
            params.put("memberid", userInfoVo.uid);
            params.put("uuid", userInfoVo.uuid);
            params.put("dataid", serviceDataBean.getDataid());
            mViewModel.getShareData(params);
        }
    }

    @Override
    public void OnVmEnentListner(ViewEventResouce viewEventResouce) {
        super.OnVmEnentListner(viewEventResouce);
        switch (viewEventResouce.eventType) {
            case 205:
            case 206:
            case 207:
                if (viewEventResouce.data != null) {
                    serviceDataBean = (ServiceDataBean) viewEventResouce.data;
                    if (curCommonFragment == null) {
                        setServiceDataBean((ServiceDataBean) viewEventResouce.data);
                    } else {
                        mBinding.empty.hide();
                        curCommonFragment.setData(serviceDataBean);
                    }
                } else {
                    mBinding.empty.showError();
                }
                break;
            case 211:
                showShare((ShareBean) viewEventResouce.data);
                break;
            case 10090: // 删除该动态
                finish();
                break;
            case 10020: //举报
                processReportUi();
                break;

        }
    }


    public void showShare(ShareBean shareBean) {
        if (shareBean == null) {
            return;
        }
        ShareBoardConfig config = new ShareBoardConfig();//新建ShareBoardConfig
        config.setMenuItemBackgroundShape(ShareBoardConfig.BG_SHAPE_CIRCULAR);
        config.setTitleVisibility(false);
        config.setIndicatorVisibility(false);
        config.setCancelButtonVisibility(false);
        config.setCancelButtonVisibility(false);
        config.setShareboardBackgroundColor(Color.WHITE);
        UMImage image = new UMImage(CircleDynamicDetailActivity.this, shareBean.getShareImg());//网络图片
        image.compressStyle = UMImage.CompressStyle.SCALE;//大小压缩，默认为大小压缩，适合普通很大的图
        image.compressStyle = UMImage.CompressStyle.QUALITY;//质量压缩，适合长图的分
        UMWeb web = new UMWeb(shareBean.getShareUrl());
        web.setTitle(shareBean.getShareTit());//标题
        web.setThumb(image);  //缩略图
        web.setDescription(shareBean.getShareDesc());//描述
        new ShareAction(CircleDynamicDetailActivity.this).withMedia(web)
                .setDisplayList(SHARE_MEDIA.WEIXIN, SHARE_MEDIA.WEIXIN_CIRCLE, SHARE_MEDIA.WEIXIN_FAVORITE, SHARE_MEDIA.QQ, SHARE_MEDIA.QZONE)
                .setCallback(new UMShareListener() {
                    @Override
                    public void onStart(SHARE_MEDIA share_media) {

                    }

                    @Override
                    public void onResult(SHARE_MEDIA share_media) {

                    }

                    @Override
                    public void onError(SHARE_MEDIA share_media, Throwable throwable) {
                        ToastUtils.showShort("分享失败请重试");
                    }

                    @Override
                    public void onCancel(SHARE_MEDIA share_media) {

                    }
                }).open(config);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        UMShareAPI.get(this).onActivityResult(requestCode, resultCode, data);
    }


    public void setServiceDataBean(ServiceDataBean serviceDataBean) {
        this.serviceDataBean = serviceDataBean;
        UserInfoVo userInfoVo = CacheUtils.getUser();
        if (serviceDataBean != null && serviceDataBean.getUuid().equals(userInfoVo.uuid)) { //
            if ("news".equals(mStaparam.type) || "answer".equals(mStaparam.type) || "dynamic".equals(mStaparam.type) || "project".equals(mStaparam.type)) {
                mBinding.ivMenuMore.setVisibility(View.VISIBLE);
                mBinding.ivMenuMore.setOnClickListener(v -> {
                    showCircleMenu();
                });
            }
        }
        mStaparam.type = serviceDataBean.getType();
        processToolbar();
    }

    public void showCircleMenu() {
        BottomSheetDialog bottomSheetDialog = new BottomSheetDialog();
        bottomSheetDialog.setDataCallback(new String[]{"编辑", "删除"}, position -> {
            bottomSheetDialog.dismiss();
            switch (position) {
//                case 0:
//                    mViewModel.dynamicTop(serviceDataBean.getCircleid(), serviceDataBean.getDynamicid(), serviceDataBean.getUtid());
//                    RxBus.getDefault().post(new RxEvent<>("dynamic_refresh", ""));
//                    break;
                case 0:
                    processLocation();
                    break;
                case 1:
                    showConfirmdialog();
                    break;
            }
        });
        bottomSheetDialog.show(this);
    }

    private void showConfirmdialog() {
        String tip = "";
        if ("dynamic".equals(serviceDataBean.getType())) {
            tip = "确定删除该动态?";
        } else {
            tip = "确定删除该商品?";
        }
//        ConfirmDialog.newInstance(tip, "删除后无法恢复，请谨慎删除").setConfimLietener(new ConfirmDialog.ConfimLietener() {
//            @Override
//            public void onCancle() {
//
//            }
//
//            @Override
//            public void onConfim() {
//                mViewModel.dynamicDel(serviceDataBean.getCircleid(), serviceDataBean.getDynamicid(), serviceDataBean.getUtid());
//                RxBus.getDefault().post(new RxEvent<>("dynamic_refresh", ""));
//            }
//        }).setMargin(24).show(getSupportFragmentManager());
    }

    /*
    *
    *      case 1:
                    processCirclePublish(CirclePublishActivity.PUBLISH_TYPE_ACTIVE, 1);
                    break;
                case 2:
                    processCirclePublish(CirclePublishActivity.PUBLISH_TYPE_NEWS, 1);
                    break;
                case 3:
                    processCirclePublish(CirclePublishActivity.PUBLISH_TYPE_QREQUESTION, 1);
                    break;
    * */


//    public void editDynamic() {
//        StaCirParam staCirParam = new StaCirParam(serviceDataBean.getCircleid(), serviceDataBean.getUtid(), serviceDataBean.getCircleName());
//        staCirParam.serviceDataBean = serviceDataBean;
//        switch (serviceDataBean.getType()) {
//            case "news":
//                CirclePublishActivity.startMe(this, CirclePublishActivity.PUBLISH_TYPE_NEWS, staCirParam, 2);
//                break;
//            case "answer":
//                CirclePublishActivity.startMe(this, CirclePublishActivity.PUBLISH_TYPE_QREQUESTION, staCirParam, 2);
//                break;
//            case "dynamic":
//                CirclePublishActivity.startMe(this, CirclePublishActivity.PUBLISH_TYPE_ACTIVE, staCirParam, 2);
//                break;
//        }
//    }

//    public void editDynamic() {
//        StaCirParam staCirParam = new StaCirParam(serviceDataBean.getCircleid(), serviceDataBean.getUtid(), serviceDataBean.getCircleName());
//        staCirParam.serviceDataBean = serviceDataBean;
//        switch (serviceDataBean.getType()) {
//            case "news":
//                CirclePublishActivity.startMe(this, CirclePublishActivity.PUBLISH_TYPE_NEWS, staCirParam, 2);
//                break;
//            case "answer":
//                CirclePublishActivity.startMe(this, CirclePublishActivity.PUBLISH_TYPE_QREQUESTION, staCirParam, 2);
//                break;
//            case "dynamic":
//                CirclePublishActivity.startMe(this, CirclePublishActivity.PUBLISH_TYPE_ACTIVE, staCirParam, 2);
//                break;
//        }
//    }


    private int locationCount = 0;
    private LocationClient mLocationClient;
    private String lat;
    private String lng;
    private String province;
    private String city;
    private String district;

    private void processLocation() {
        locationCount = 0;
        mLocationClient = new LocationClient(this);
        RxPermissions rxPermissions = new RxPermissions(this);
        rxPermissions.requestEach(Manifest.permission.READ_EXTERNAL_STORAGE,
                Manifest.permission.ACCESS_NETWORK_STATE,
                Manifest.permission.WRITE_EXTERNAL_STORAGE,
                Manifest.permission.READ_EXTERNAL_STORAGE,
                Manifest.permission.ACCESS_WIFI_STATE,
                Manifest.permission.WRITE_SETTINGS,
                Manifest.permission.ACCESS_COARSE_LOCATION,
                Manifest.permission.ACCESS_FINE_LOCATION,
                Manifest.permission.READ_PHONE_STATE)
                .subscribe(permission -> {
                    if (permission.name.equals(Manifest.permission.ACCESS_FINE_LOCATION)) {
                        if (permission.granted) {
                            // 用户已经同意该权限
                            LocationClientOption option = new LocationClientOption();
                            option.setIsNeedAddress(true);
                            option.setOpenGps(true); // 打开gps
                            option.setCoorType("bd09ll"); // 设置坐标类型
                            option.setScanSpan(1000);
                            mLocationClient.setLocOption(option);
                            mLocationClient.registerLocationListener(new BDAbstractLocationListener() {
                                @Override
                                public void onReceiveLocation(BDLocation location) {
                                    if (location.getAddress() != null) {
                                        province = location.getAddress().province;
                                        city = location.getAddress().city;
                                        district = location.getAddress().district;
                                        location.getCityCode();
                                        lat = String.valueOf(location.getLatitude());
                                        lng = String.valueOf(location.getLongitude());
                                        processEdit();
                                        mLocationClient.stop();

                                    } else {
                                        locationCount++;
                                        if (locationCount > 3) {
                                            mLocationClient.stop();
                                            processEdit();
                                        }
                                    }
                                }
                            });
                            mLocationClient.start();
                        } else {
                            ToastUtils.showShort("权限被拒绝，请在设置里面开启相应权限，若无相应权限会影响使用");
                            processEdit();
                        }
                    }
                });
    }

    private void processEdit() {
        UserInfoVo userInfoVo = CacheUtils.getUser();
        String weburl = Constant.BaseServeTest + "index.php?m=publish.push_dynamic" +
                "&t=" + serviceDataBean.getType() + "&memberid=" + userInfoVo.uid + "&uuid=" + userInfoVo.uuid + "" +
                "&lat=" + lat + "&lng=" + lng + "&area1=" + province + "&area2=" + city + "&area3=" + district + "&id=" + serviceDataBean.getDynamicid();
        ARouter.getInstance().build(AppRouter.COMMONH5).withString("weburl", weburl).withString("title", "编辑").navigation();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (disposable != null) {
            disposable.dispose();
        }
    }

    public void processEdit(ServiceDataBean serviceDataBean) {
        this.serviceDataBean = serviceDataBean;
        showCircleMenu();
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
                    mViewModel.circleBlackList(serviceDataBean.getMemberid());
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
            mViewModel.circlePersionReport(serviceDataBean.getMemberid(), title[position]);
        });
        bottomSheetDialog.show(this);
    }

    public void hideLoading() {
        if (mBinding != null && mBinding.empty != null) {
            mBinding.empty.hide();
        }
    }
}
