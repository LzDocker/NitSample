package com.bfhd.circle.vm;


import android.arch.lifecycle.Lifecycle;
import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.OnLifecycleEvent;
import android.databinding.ObservableArrayList;
import android.databinding.ObservableBoolean;
import android.databinding.ObservableInt;
import android.databinding.ObservableList;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;

import com.alibaba.android.arouter.launcher.ARouter;
import com.bfhd.circle.BR;
import com.bfhd.circle.R;
import com.bfhd.circle.api.CircleService;
import com.bfhd.circle.base.Constant;
import com.bfhd.circle.base.HivsBaseViewModel;
import com.bfhd.circle.base.HivsNetBoundObserver;
import com.bfhd.circle.base.NetBoundCallback;
import com.bfhd.circle.base.ViewEventResouce;
import com.bfhd.circle.utils.AudioPlayerUtils;
import com.bfhd.circle.vo.CircleDynamicVo;
import com.bfhd.circle.vo.CircleUserVo;
import com.bfhd.circle.vo.CommentRstVo;
import com.bfhd.circle.vo.PerssionVo;
import com.bfhd.circle.vo.RstVo;
import com.bfhd.circle.vo.ScreenVo;
import com.bfhd.circle.vo.ServiceDataBean;
import com.bfhd.circle.vo.StaDynaVo;
import com.bfhd.circle.vo.TypeVo;
import com.bfhd.circle.vo.bean.OrderPayParam;
import com.bfhd.circle.vo.bean.StaCirParam;
import com.bfhd.circle.vo.bean.StaDetailParam;
import com.bfhd.circle.vo.bean.StaPersionDetail;
import com.dcbfhd.utilcode.utils.EncryptUtils;
import com.dcbfhd.utilcode.utils.FileUtils;
import com.dcbfhd.utilcode.utils.ToastUtils;
import com.docker.common.common.router.AppRouter;
import com.docker.common.common.utils.cache.CacheUtils;
import com.docker.common.common.utils.rxbus.RxBus;
import com.docker.common.common.utils.rxbus.RxEvent;
import com.docker.common.common.vo.RstServerVo;
import com.docker.common.common.vo.ShareBean;
import com.docker.common.common.vo.UserInfoVo;
import com.docker.common.common.widget.empty.EmptyCommand;
import com.docker.common.common.widget.empty.EmptyStatus;
import com.docker.core.di.netmodule.ApiResponse;
import com.docker.core.di.netmodule.BaseResponse;
import com.docker.core.repository.CommonRepository;
import com.docker.core.repository.Resource;
import com.docker.core.util.AppExecutors;
import com.luck.picture.lib.entity.LocalMedia;
import com.wx.goodview.GoodView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;

import io.reactivex.disposables.Disposable;
import me.tatarka.bindingcollectionadapter2.ItemBinding;
import me.tatarka.bindingcollectionadapter2.OnItemBind;

/**
 * Created by zhangxindang on 2018/10/19.
 * 动态 / 问答 圈子相关的
 */

public class CircleDynamicViewModel extends HivsBaseViewModel {
    @Inject
    CommonRepository commonRepository;

    @Inject
    public CircleDynamicViewModel() {
    }

    @Inject
    CircleService circleService;
    public StaDynaVo mStaDy;
    public StaCirParam mStaCir;
    public StaDetailParam mStaDetail;
    public ServiceDataBean serviceDataBean;
    private String isComment; // 评论权限
    private AudioPlayerUtils playerUtils;
    private Disposable disposable;
    public boolean isLoadState = false;
    public boolean isControlrefenable = false; // 是否控制刷新可用
    public ObservableBoolean bdenable = new ObservableBoolean(false);
    public ObservableBoolean bdenablemore = new ObservableBoolean(false);
    public boolean isAddAttenPersion = false;

    @Inject
    AppExecutors appExecutors;
    public ObservableInt mPerisonAttenEmptycommand = new ObservableInt(2);

    public EmptyCommand mPerisonAttenretrycommand = () -> getUserVoList();

    /*
     * 初始化参数
     * */
    public void initVMParam(StaDynaVo staDynaVo, StaCirParam staCirParam) {
        this.mStaCir = staCirParam;
        this.mStaDy = staDynaVo;
    }

    @Override
    public void initCommand() {
        mCommand.OnRefresh(() -> {
            mPage = 1;
            mIsfirstLoad = false;
            getInfoData();
        });
        mCommand.OnLoadMore(() -> {
            mIsfirstLoad = false;
            getInfoData();
        });
        mCommand.OnRetryLoad(() -> {
            mEmptycommand.set(EmptyStatus.BdLoading);
            mIsfirstLoad = true;
            getInfoData();
        });
        disposable = RxBus.getDefault().toObservable(RxEvent.class).subscribe(rxEvent -> {
            if (rxEvent.getT().equals("dz")) {
                ServiceDataBean item = (ServiceDataBean) rxEvent.getR();
                if (item == null) {
                    return;
                }
                if (items != null && items.size() > 0) {
                    for (int i = 0; i < items.size(); i++) {
                        if (item.getDynamicid().equals(items.get(i).getDynamicid())) {
                            items.get(i).setIsFav(item.getIsFav());
                            items.get(i).setFavourNum(item.getFavourNum());
                            items.get(i).notifyPropertyChanged(BR.isFav);
                            items.get(i).notifyPropertyChanged(BR.favourNum);
                            break;
                        }
                    }
                }
            }

            if (rxEvent.getT().equals("refresh_collect")) {
                ServiceDataBean item = (ServiceDataBean) rxEvent.getR();
                if (item == null) {
                    return;
                }
                if (mStaDy != null && mStaDy.ReqParam != null && mStaDy.ReqParam.containsKey("act") && "collect".equals(mStaDy.ReqParam.get("act"))) {
                    if (item.getIsCollect() == 1) {
                        items.add(0, item);
                    } else {
                        for (int i = 0; i < items.size(); i++) {
                            if (item.getDataid().equals(items.get(i).getDataid())) {
                                items.remove(items.get(i));
                                break;
                            }
                        }
                        if (items.size() == 0) {
                            mEmptycommand.set(EmptyStatus.BdEmpty);
                        }
                    }
                }
            }
        });
    }

    private void setLoadControl(boolean enable) {
        if (isControlrefenable) {  // ui
            bdenablemore.set(enable);
            bdenablemore.notifyChange();
        } else {
            bdenable.set(enable);
            bdenablemore.set(enable);
            bdenable.notifyChange();
            bdenablemore.notifyChange();
        }
    }

    /*
     * 获取数据
     * */
    public void getInfoData() {

        LiveData<ApiResponse<BaseResponse<List<ServiceDataBean>>>> servicefun = null;
        mStaDy.ReqParam.put("page", String.valueOf(mPage));
//        UserInfoVo userInfoVo = CacheUtils.getUser();
//        mStaDy.ReqParam.put("memberid", userInfoVo.uid);
//        mStaDy.ReqParam.put("uuid", userInfoVo.uuid);

//        if (mStaDy.ReqParam.containsKey("memberid") && "-1".equals(mStaDy.ReqParam.get("memberid"))) {
//            mStaDy.ReqParam.put("memberid", userInfoVo.uid);
//        }
//        if (mStaDy.ReqParam.containsKey("uuid") && "-1".equals(mStaDy.ReqParam.get("uuid"))) {
//            mStaDy.ReqParam.put("uuid", userInfoVo.uuid);
//        }

        if (mStaDy.ReqType == 1) { // 固定url
            servicefun = circleService.fechCircleInfoList(mStaDy.ReqParam);
        } else {
            servicefun = circleService.fechCircleInfoList(mStaDy.ApiUrl, mStaDy.ReqParam);
        }
        mResourceLiveData.addSource(
                commonRepository.noneChache(
                        servicefun), new HivsNetBoundObserver<>(new NetBoundCallback<List<ServiceDataBean>>() {
                    @Override
                    public void onLoading() {
                        super.onLoading();
                        isLoadState = true;
                        if (mPage == 1) {
                            if (mIsfirstLoad) {
                                mEmptycommand.set(EmptyStatus.BdLoading);
                                setLoadControl(false);
                            } else {
                                setLoadControl(true);
                            }
                        }
                    }

                    @Override
                    public void onComplete(Resource<List<ServiceDataBean>> resource) {
                        super.onComplete(resource);
                        isLoadState = false;
                        mIsfirstLoad = false;
                        if (mPage == 1) {
                            items.clear();
                        }
                        if (resource.data != null && resource.data.size() > 0) {
                            mVmEventSouce.setValue(new ViewEventResouce(201, "", resource.data));
                            mEmptycommand.set(EmptyStatus.BdHiden);
                            items.addAll(resource.data);
                            if (isAddAttenPersion) { //todo
                                // 关注
                                if (mPage == 1) {
                                    getUserVoList();
                                    ServiceDataBean serverPersion = new ServiceDataBean();
                                    serverPersion.setType("persion");
                                    items.add(1, serverPersion);
                                }
                                // 关注
                            }
                            mPage++;
                            setLoadControl(true);
                        } else {
                            if (items.size() == 0) { // 暂无数据
                                mEmptycommand.set(EmptyStatus.BdEmpty);
                                setLoadControl(false);
                            }
                            mVmEventSouce.setValue(new ViewEventResouce(202, "", null));
                        }
                        mCompleteCommand.set(true);
                        mCompleteCommand.notifyChange();
                    }

                    @Override
                    public void onComplete() {
                        super.onComplete();
                        isLoadState = false;
                        mVmEventSouce.setValue(new ViewEventResouce(202, "", null));
                        mCompleteCommand.set(true);
                        mCompleteCommand.notifyChange();
                    }

                    @Override
                    public void onBusinessError(Resource<List<ServiceDataBean>> resource) {
                        super.onBusinessError(resource);
                        setLoadControl(false);
                        mVmEventSouce.setValue(new ViewEventResouce(202, "", null));
                    }

                    @Override
                    public void onNetworkError(Resource<List<ServiceDataBean>> resource) {
//                        super.onNetworkError(resource);
                        ToastUtils.showShort("网络问题，请重试");
                        mCompleteCommand.set(true);
                        mCompleteCommand.notifyChange();
                        setLoadControl(false);
                        if (items.size() == 0) {
                            mEmptycommand.set(EmptyStatus.BdError);
                        }
                        mVmEventSouce.setValue(new ViewEventResouce(203, "", null));
                    }
                }));
    }


    public void getUserVoList() {
        mPerisonAttenEmptycommand.set(EmptyStatus.BdLoading);
        UserInfoVo userInfoVo = CacheUtils.getUser();
        mResourceLiveData.addSource(
                commonRepository.noneChache(
                        circleService.fetchUserVo(userInfoVo.uid, userInfoVo.uuid)), new HivsNetBoundObserver<>(new NetBoundCallback<List<CircleUserVo>>() {
                    @Override
                    public void onComplete(Resource<List<CircleUserVo>> resource) {
                        super.onComplete(resource);
                        if (resource.data != null &&
                                items != null
                                && items.size() > 1
                                && "persion".equals(items.get(1).getType())) {
                            items.get(1).observableList.addAll(resource.data);
                            mPerisonAttenEmptycommand.set(EmptyStatus.BdHiden);
                        } else {
                            items.remove(1);
                        }
                    }

                    @Override
                    public void onNetworkError(Resource<List<CircleUserVo>> resource) {
//                        super.onNetworkError(resource);
                        ToastUtils.showShort("网络问题，请重试");
                        if (items.size() > 1) {
                            items.remove(1);
                        }
                    }

                    @Override
                    public void onBusinessError(Resource<List<CircleUserVo>> resource) {
                        super.onBusinessError(resource);
//                        if (resource.data != null &&
//                                items != null
//                                && items.size() > 1
//                                && "persion".equals(items.get(1).getType())) {
//                            items.get(1).observableList.addAll(resource.data);
//                            mPerisonAttenEmptycommand.set(EmptyStatus.BdError);
//                        }
                        if (items.size() > 1) {
                            items.remove(1);
                        }
                    }
                }));
    }


    //-----------------------------------------------------------------------------------------------------------------------------
    // 列表数据源
    public final ObservableList<ServiceDataBean> items = new ObservableArrayList<>();

    // product 闲置物品 商品  车辆

    //project  项目

    // house  住房

    // car  车辆

    // time 时间

    // 多类型条目适配
    public final OnItemBind<ServiceDataBean> mutipartItemsBinding = (itemBinding, position, item) -> {
        switch (item.getType()) {
            case "persion":
                itemBinding.set(BR.item, R.layout.circle_item_dynamic_persion);
                break;
            case "car":
            case "datetime":
            case "product":
            case "goods":
                itemBinding.set(BR.item, R.layout.circle_item_dynamic_goods);
                break;
            case "project":
//                itemBinding.set(BR.item, R.layout.circle_item_dynamic_safe);
                itemBinding.set(BR.item, R.layout.circle_item_dynamic_project);
                break;
            case "house":
                itemBinding.set(BR.item, R.layout.circle_item_dynamic_hourse);
                break;
            //--------------------------------------------------------------------
            case "answer":
                itemBinding.set(BR.item, R.layout.circle_item_dynamic_answer);
                break;
            case "dynamic":
                itemBinding.set(BR.item, R.layout.circle_item_dynamic_dynamic);
                break;
            case "news":
                itemBinding.set(BR.item, R.layout.circle_item_dynamic_news);
                break;
            case "event":
                itemBinding.set(BR.item, R.layout.circle_item_dynamic_event);
                break;
            case "shared":
                itemBinding.set(BR.item, R.layout.circle_item_dynamic_share);
                break;
            case "alarm":
                // todo
                itemBinding.set(BR.item, R.layout.circle_item_dynamic_dangrous);
                break;
            case "report":
                itemBinding.set(BR.item, R.layout.circle_item_dynamic_report);
                break;
            case "sentiment":
                itemBinding.set(BR.item, R.layout.circle_item_dynamic_sentiment);
                break;
            case "emergency": // 应急管理
            case "management": // 管理要求
                itemBinding.set(BR.item, R.layout.circle_item_dynamic_file_manager);
                break;
            default:
                itemBinding.set(BR.item, R.layout.circle_item_dynamic_answer);
                break;
        }
    };
    // itembinding
    public final ItemBinding<ServiceDataBean> itemBinding = ItemBinding.of(mutipartItemsBinding)
            .bindExtra(BR.viewmodel, this);

//--------------------------------------------------------------------------------------------------------------------------------

    // 嵌套rv的数据源
    public final ObservableList<ServiceDataBean.ResourceBean> getResouceData(ServiceDataBean serviceDataBean) {
        ObservableList<ServiceDataBean.ResourceBean> observableList = new ObservableArrayList<>();
        if (serviceDataBean != null && serviceDataBean.getExtData().getResource() != null && serviceDataBean.getExtData().getResource().size() > 0) {
            for (int i = 0; i < serviceDataBean.getExtData().getResource().size(); i++) {
                serviceDataBean.getExtData().getResource().get(i).setParentid(serviceDataBean.getDynamicid());
            }
            observableList.addAll(serviceDataBean.getExtData().getResource());
        }
        return observableList;
    }


    // 嵌套rv的itembinding
    public final ItemBinding<ServiceDataBean.ResourceBean> itemImgBinding = ItemBinding.<ServiceDataBean.ResourceBean>of(BR.item, R.layout.circle_item_dynamic_img_fin) // 单一view 有点击事件
            .bindExtra(BR.viewmodel, this);


    // 详情rv的
    public final ItemBinding<ServiceDataBean.ResourceBean> itemDetailBinding = ItemBinding.<ServiceDataBean.ResourceBean>of(BR.item, R.layout.circle_item_dynamic_img_detail) // 单一view 有点击事件
            .bindExtra(BR.viewmodel, this);


    //商品筛选
    public final ItemBinding<ScreenVo> itemBindingScreenDetai = ItemBinding.<ScreenVo>of(BR.item, R.layout.circle_screen_item)
            .bindExtra(BR.viewmodel, this);

    // 关闭
    public void ItemCloseClick(CircleUserVo persion, View view) {
        items.get(1).observableList.remove(persion);
    }

    // 进入persion详情
    public void ItemPersionIconClick(CircleUserVo persion, View view) {
        if (persion != null) {
            StaPersionDetail staPersionDetail = new StaPersionDetail();
            staPersionDetail.name = persion.nickname;
            staPersionDetail.uuid = persion.uuid;
            staPersionDetail.uid = persion.memberid;
//            ARouter.getInstance().build(AppRouter.CIRCLE_persion_detail).withSerializable("mStartParam", staPersionDetail).navigation();
            ARouter.getInstance().build(AppRouter.CIRCLE_person_info).withString("memberid2", staPersionDetail.uid).withString("uuid2", staPersionDetail.uuid).navigation();

        }
    }

    // 关注persion
    public void ItemAttenClick(CircleUserVo persion, View view) {
        if (checkLoginState()) {
            return;
        }
        if (persion == null) {
            return;
        }
        showDialogWait("关注中...", false);
        HashMap<String, String> params = new HashMap<>();
        UserInfoVo userInfoVo = CacheUtils.getUser();
        params.put("memberid", userInfoVo.uid);
        params.put("memberid2", persion.memberid);
        params.put("uuid2", persion.uuid);
        params.put("uuid", userInfoVo.uuid);
        if (!TextUtils.isEmpty(userInfoVo.nickname)) {
            params.put("nickname", userInfoVo.nickname);
        } else {
            params.put("nickname", "匿名");
        }
        params.put("status", "1");
        mResourceLiveData.addSource(
                commonRepository.noneChache(
                        circleService.attention(params)), new HivsNetBoundObserver<>(new NetBoundCallback<String>() {
                    @Override
                    public void onComplete(Resource<String> resource) {
                        super.onComplete(resource);
                        hideDialogWait();
                        if (items != null && items.size() > 1) {
                            items.get(1).observableList.remove(persion);
                        }
                    }

                    @Override
                    public void onComplete() {
                        super.onComplete();
                        hideDialogWait();
                    }
                }));
    }


    // 嵌套rv的itembinding
    public final ItemBinding<CircleUserVo> itemPersionBinding = ItemBinding.<CircleUserVo>of(BR.item, R.layout.circle_item_dynamic_persion_item) // 单一view 有点击事件
            .bindExtra(BR.viewmodel, this);


    //-----------------------------------------------------------------------------------------------------------------------------------------
    /*
     * 条目点击事件  // 使用router 跳转    应该放到opensource  todo
     * */
    public void ItemDynamicClick(ServiceDataBean item, View view) {
        StaDetailParam staDetailParam = new StaDetailParam();
        switch (item.getType()) {
            case "car":
            case "time":
            case "datetime":
            case "product":
            case "house":
            case "goods":
                staDetailParam.uiType = 3;
                break;
            case "safe":
                staDetailParam.uiType = 3;
                staDetailParam.isRecomend = true;
                break;
            case "answer":
                staDetailParam.uiType = 2;
                staDetailParam.speical = 1;
                break;
            case "project":
            case "dynamic":
                staDetailParam.uiType = 0;
//                staDetailParam.speical = 1;
                break;
            case "news":
                staDetailParam.uiType = 1;
                staDetailParam.speical = 1;
                break;
            case "event":
                staDetailParam.uiType = 3;
                staDetailParam.isRecomend = true;
                break;
            case "shared":
                staDetailParam.isRecomend = true;
                staDetailParam.uiType = 0;
                break;
            case "alarm":
                staDetailParam.isRecomend = true;
                staDetailParam.uiType = 3;
                break;
            case "report":
                staDetailParam.isRecomend = true;
                staDetailParam.uiType = 3;
                break;
            case "sentiment": // 舆情监控
                staDetailParam.isRecomend = true;
                staDetailParam.uiType = 3;
                break;
            case "emergency": // 应急管理  详情是commonh5
            case "management": // 管理要求
//                staDetailParam.isRecomend = true;
//                staDetailParam.uiType = 3;
                if (1 == 1) {
                    String weburl = Constant.getCompletePdf(item.getExtData().getFile_path());
                    String extension = FileUtils.getFileExtension(item.getExtData().getFile_path());
                    String filename = EncryptUtils.encryptMD5ToString(weburl) + "." + extension;
                    ARouter.getInstance().build(AppRouter.ViewDoc_Document).withString("fileURL", weburl)
                            .withString("fileName", filename)
                            .navigation();
                    return;
                }
                break;
            default:
                staDetailParam.uiType = 1;
                break;
        }
        staDetailParam.dynamicId = item.getDynamicid();
        staDetailParam.type = item.getType();
        ARouter.getInstance().build(AppRouter.CIRCLE_DETAIL).withSerializable("mStaparam", staDetailParam).navigation();

    }


    // 点击头像
    public void ItemAvaterClick(ServiceDataBean serviceDataBean, View v) {
        if (serviceDataBean != null) {
            StaPersionDetail staPersionDetail = new StaPersionDetail();
            staPersionDetail.name = serviceDataBean.getExtData().linkman;
            staPersionDetail.uuid = serviceDataBean.getUuid();
            staPersionDetail.uid = serviceDataBean.getMemberid();

//            ARouter.getInstance().build(AppRouter.CIRCLE_persion_detail).withSerializable("mStartParam", staPersionDetail).navigation();
            ARouter.getInstance().build(AppRouter.CIRCLE_person_info).withString("memberid2", serviceDataBean.getMemberid()).withString("uuid2", serviceDataBean.getUuid()).navigation();

        }
    }

    // 单个视频点击事件
    public void singleVideoClick(ServiceDataBean serviceDataBean, View v) {
        if (serviceDataBean.getExtData() != null && serviceDataBean.getExtData().getResource() != null && serviceDataBean.getExtData().getResource().get(0) != null) {
            if (serviceDataBean.getExtData().getResource().get(0).getT() == 2) {
                String videoUrl = Constant.getCompleteImageUrl(serviceDataBean.getExtData().getResource().get(0).getUrl());
                String thumbUrl = Constant.getCompleteImageUrl(serviceDataBean.getExtData().getResource().get(0).getImg());
//                ARouter.getInstance().build(AppRouter.Video_Player).withString("videoUrl", videoUrl).withString("thumbUrl", thumbUrl).navigation();
            }
        }
    }

    public void singleVideoOrImgClick(ServiceDataBean serviceDataBean, View v) {
        if (serviceDataBean.getExtData() != null && serviceDataBean.getExtData().getResource() != null && serviceDataBean.getExtData().getResource().get(0) != null) {
            if (serviceDataBean.getExtData().getResource().get(0).getT() == 2) {
                String videoUrl = Constant.getCompleteImageUrl(serviceDataBean.getExtData().getResource().get(0).getUrl());
                String thumbUrl = Constant.getCompleteImageUrl(serviceDataBean.getExtData().getResource().get(0).getImg());
//                ARouter.getInstance().build(AppRouter.Video_Player).withString("videoUrl", videoUrl).withString("thumbUrl", thumbUrl).navigation();
            } else {
                List<ServiceDataBean.ResourceBean> resourceBeans = null;
                int position = 0;
                List<LocalMedia> localMediaList = new ArrayList<>();
                resourceBeans = serviceDataBean.getExtData().getResource();
                if (resourceBeans != null && resourceBeans.size() > 0) {
                    for (int i = 0; i < resourceBeans.size(); i++) {
                        LocalMedia localMedia = new LocalMedia();
                        localMedia.setPictureType("1");
                        if (!TextUtils.isEmpty(resourceBeans.get(i).getImg())) {
                            localMedia.setPath(Constant.getCompleteImageUrl(resourceBeans.get(i).getImg()));
                        } else {
                            localMedia.setPath(Constant.getCompleteImageUrl(resourceBeans.get(i).getUrl()));
                        }
                        localMediaList.add(localMedia);
                    }
                }
                mVmEventSouce.setValue(new ViewEventResouce(204, String.valueOf(position), localMediaList));
            }
        }

    }


    /*
     * 图片点击事件
     * */
    public void imgclick(ServiceDataBean.ResourceBean resourceBean, View view, String imgurl) {
        if (resourceBean == null || resourceBean.getParentid() == null) {
            return;
        }
        if (resourceBean.getT() == 2) {
            String videoUrl = Constant.getCompleteImageUrl(resourceBean.getUrl());
            String thumbUrl = Constant.getCompleteImageUrl(resourceBean.getImg());
//            ARouter.getInstance().build(AppRouter.Video_Player).withString("videoUrl", videoUrl).withString("thumbUrl", thumbUrl).navigation();
        } else {
            List<ServiceDataBean.ResourceBean> resourceBeans = null;
            int position = 0;
            List<LocalMedia> localMediaList = new ArrayList<>();
            for (int i = 0; i < items.size(); i++) {
                if (items.get(i).getDynamicid() != null && resourceBean.getParentid().equals(items.get(i).getDynamicid())) {
                    resourceBeans = items.get(i).getExtData().getResource();
                    break;
                }
            }
            String imgsource = "";
            if (!TextUtils.isEmpty(resourceBean.getImg())) {
                imgsource = resourceBean.getImg();
            } else {
                imgsource = resourceBean.getUrl();
            }
            if (resourceBeans != null && resourceBeans.size() > 0) {
                for (int i = 0; i < resourceBeans.size(); i++) {
                    if (resourceBeans.get(i).getImg().equals(imgsource) || resourceBeans.get(i).getUrl().equals(imgsource)) {
                        position = i;
                    }
                    LocalMedia localMedia = new LocalMedia();
                    localMedia.setPictureType("1");
                    if (!TextUtils.isEmpty(resourceBeans.get(i).getImg())) {
                        localMedia.setPath(Constant.getCompleteImageUrl(resourceBeans.get(i).getImg()));
                    } else {
                        localMedia.setPath(Constant.getCompleteImageUrl(resourceBeans.get(i).getUrl()));
                    }
                    localMediaList.add(localMedia);
                }
            }
            mVmEventSouce.setValue(new ViewEventResouce(204, String.valueOf(position), localMediaList));
        }
    }

    /*
     * 条目点击事件 转发
     * */
    public void ItemZFClick(ServiceDataBean item, View view) {
//        ToastUtils.showShort("--------转发----------");

        if (checkLoginState()) {
            return;
        }
        if (item != null) {
            HashMap<String, String> params = new HashMap<>();
            UserInfoVo userInfoVo = CacheUtils.getUser();
            params.put("type", item.getType());
            params.put("dynamicid", item.getDynamicid());
            params.put("circleid", item.getCircleid());
            params.put("utid", item.getUtid());
            params.put("memberid", userInfoVo.uid);
            params.put("uuid", userInfoVo.uuid);
            params.put("dataid", item.getDataid());
            getShareData(params);
        }
    }


    public boolean checkLoginState() {
        if (("-1".equals(CacheUtils.getUser().memberid))) {
            ARouter.getInstance().build(AppRouter.ACCOUNT_LOGIN).withBoolean("isFoceLogin", true).navigation();
            return true;
        } else {
            return false;
        }
    }


    /*
     *  收藏
     * */
    public void ItemStoreClick(ServiceDataBean item, View view) {

        if (checkLoginState()) {
            return;
        }
        if (item == null) {
            return;
        }
        showDialogWait("加载中...", false);
        UserInfoVo userInfoVo = CacheUtils.getUser();
        HashMap<String, String> paramap = new HashMap<>();
        paramap.put("memberid", userInfoVo.uid);
        paramap.put("uuid", userInfoVo.uuid);
        paramap.put("dataid", item.getDataid());
        paramap.put("dynamicid", item.getDynamicid());
        paramap.put("type", item.getType());
        paramap.put("push_uuid", item.getUuid());
        paramap.put("push_memberid", item.getMemberid());
        paramap.put("nickname", userInfoVo.nickname);
        if (item.getIsCollect() == 1) {
            paramap.put("status", "0");
        } else {
            paramap.put("status", "1");
        }

        mResourceLiveData.addSource(commonRepository.noneChache(circleService.collect(paramap)),
                new HivsNetBoundObserver<>(new NetBoundCallback<String>() {
                    @Override
                    public void onComplete(Resource<String> resource) {
                        super.onComplete(resource);
                        this.onComplete();
                        hideDialogWait();
                        if (item.getIsCollect() == 1) {
                            item.setIsCollect(0);
                        } else {
                            item.setIsCollect(1);
                        }
                        item.notifyPropertyChanged(BR.isCollect);
                        RxBus.getDefault().post(new RxEvent<>("refresh_collect", item));
                    }

                    @Override
                    public void onComplete() {
                        super.onComplete();
                        hideDialogWait();
                    }

                    @Override
                    public void onNetworkError(Resource<String> resource) {
//                        super.onNetworkError(resource);
                        hideDialogWait();
                        if (item.getIsCollect() == 1) {
                            item.setIsCollect(0);
                        } else {
                            item.setIsCollect(1);
                        }
                        item.notifyPropertyChanged(BR.isCollect);
                    }
                }));
    }

    /*
     * 条目点击事件 评论
     * */
    public void ItemPLClick(ServiceDataBean item, View view) {
        if (checkLoginState()) {
            return;
        }
        if (item == null) {
            return;
        }
        if (TextUtils.isEmpty(isComment)) {
            getCommentPerssion(item);
        } else {
            if (isComment.equals("1")) {
                if ("safe".equals(item.getType())) {
                    if (serviceDataBean == null) {
                        return;
                    }
                    ARouter.getInstance().build(AppRouter.COMMENTLIST).withSerializable("staDetailParam", mStaDetail).withSerializable("serviceDataBean", serviceDataBean).navigation();
                } else {
                    mVmEventSouce.setValue(new ViewEventResouce(210, "", item));
                }
            } else {
                ToastUtils.showShort("暂无评论权限！");
            }
        }
    }

    /*
     * 条目点击事件 点赞
     * */
    public void ItemDZClick(ServiceDataBean item, View view) {
        if (checkLoginState()) {
            return;
        }
        if (item == null) {
            return;
        }
        UserInfoVo userInfoVo = CacheUtils.getUser();
        Map<String, String> params = new HashMap<>();
        params.put("memberid", userInfoVo.uid);
        params.put("uuid", userInfoVo.uuid);
        params.put("dynamicid", item.getDynamicid());
        params.put("status", item.getIsFav() == 1 ? "0" : "1");
        params.put("push_uuid", item.getUuid());
        params.put("push_memberid", item.getMemberid());
        if (!TextUtils.isEmpty(userInfoVo.nickname)) {
            params.put("nickname", "匿名");
        }
        mResourceLiveData.addSource(
                commonRepository.noneChache(
                        circleService.dynamicParise(params)), new HivsNetBoundObserver<>(new NetBoundCallback<RstVo>() {
                    @Override
                    public void onComplete(Resource<RstVo> resource) {
                        super.onComplete(resource);
                        item.notifyPropertyChanged(BR.isFav);
                        item.notifyPropertyChanged(BR.favourNum);
                        if (item.getIsFav() == 1) { //1赞 0取消赞
                            item.setIsFav(0);
                            item.setFavourNum((item.getFavourNum()) - 1);
                        } else {
                            showDzView(view);
                            item.setIsFav(1);
                            item.setFavourNum(item.getFavourNum() + 1);
                        }
                        RxBus.getDefault().post(new RxEvent<>("dz", item));
                    }
                }));
    } /*
     * 条目点击事件 点赞
     * */

    public void ItemDZGoodsClick(ServiceDataBean item, View view) {
        if (checkLoginState()) {
            return;
        }
        if (item == null) {
            return;
        }
        UserInfoVo userInfoVo = CacheUtils.getUser();
        Map<String, String> params = new HashMap<>();
        params.put("memberid", userInfoVo.uid);
        params.put("uuid", userInfoVo.uuid);
        params.put("dynamicid", item.getDynamicid());
        params.put("status", item.getIsFav() == 1 ? "0" : "1");
        params.put("push_uuid", item.getUuid());
        params.put("push_memberid", item.getMemberid());
        if (!TextUtils.isEmpty(userInfoVo.nickname)) {
            params.put("nickname", "匿名");
        }
        mResourceLiveData.addSource(
                commonRepository.noneChache(
                        circleService.dynamicParise(params)), new HivsNetBoundObserver<>(new NetBoundCallback<RstVo>() {
                    @Override
                    public void onComplete(Resource<RstVo> resource) {
                        super.onComplete(resource);
                        item.notifyPropertyChanged(BR.isFav);
                        item.notifyPropertyChanged(BR.favourNum);
                        if (item.getIsFav() == 1) { //1赞 0取消赞
                            item.setIsFav(0);
                            item.setFavourNum((item.getFavourNum()) - 1);
                        } else {
                            showDzGoodsView(view);
                            item.setIsFav(1);
                            item.setFavourNum(item.getFavourNum() + 1);
                        }
                        RxBus.getDefault().post(new RxEvent<>("dz", item));
                    }
                }));
    }


    private void showDzView(View view) {
        GoodView goodView = new GoodView(view.getContext());
        goodView.setDistance(100);
        goodView.setImage(R.mipmap.circle_icon_hd_dz2);
        GoodView goodView1 = new GoodView(view.getContext());
        goodView1.setImage(R.mipmap.circle_icon_hd_dz2);
        GoodView goodView2 = new GoodView(view.getContext());
        goodView2.setImage(R.mipmap.circle_icon_hd_dz2);
        goodView.show(view.findViewById(R.id.iv_dz));
        goodView1.show(view.findViewById(R.id.iv_dz));
        goodView2.show(view.findViewById(R.id.iv_dz));
    }

    private void showDzGoodsView(View view) {
        GoodView goodView = new GoodView(view.getContext());
        goodView.setDistance(100);
        goodView.setImage(R.mipmap.circle_icon_dz2);
        GoodView goodView1 = new GoodView(view.getContext());
        goodView1.setImage(R.mipmap.circle_icon_dz2);
        GoodView goodView2 = new GoodView(view.getContext());
        goodView2.setImage(R.mipmap.circle_icon_dz2);
        goodView.show(view);
        goodView1.show(view);
        goodView2.show(view);
    }


    // 聊一聊
    public void ItemTalkClick(ServiceDataBean item, View view) {
        if (checkLoginState()) {
            return;
        }
        if (item == null) {
            return;
        }
        mVmEventSouce.setValue(new ViewEventResouce(10099, "", item));
    }


    // 去购买界面
    public void ItemBuyClick(ServiceDataBean item, View view) {
        if (checkLoginState()) {
            return;
        }
        if (item == null) {
            return;
        }
        OrderPayParam orderPayParam = new OrderPayParam();
        orderPayParam.dynamicid = item.getDynamicid();
        orderPayParam.t = "1";
        orderPayParam.shoptype = item.getType();
//        ARouter.getInstance().build(AppRouter.CHOOSEPAY).withSerializable("OrderPayParam", orderPayParam).navigation();

        Log.d("sss", "ItemBuyClick: ======去购买界面=====");
//        UserInfoVo userInfoVo = CacheUtils.getUser();
//        Map<String, String> params = new HashMap<>();
//        params.put("memberid", userInfoVo.uid);
//        params.put("uuid", userInfoVo.uuid);
//        params.put("dynamicid", item.getDynamicid());
//        params.put("status", item.getIsFav() == 1 ? "0" : "1");
//        params.put("push_uuid", item.getUuid());
//        params.put("push_memberid", item.getMemberid());
//        params.put("nickname", userInfoVo.nickname);
//        mResourceLiveData.addSource(
//                commonRepository.noneChache(
//                        circleService.dynamicParise(params)), new HivsNetBoundObserver<>(new NetBoundCallback<RstVo>() {
//                    @Override
//                    public void onComplete(Resource<RstVo> resource) {
//                        super.onComplete(resource);
//                        item.notifyPropertyChanged(BR.isFav);
//                        item.notifyPropertyChanged(BR.favourNum);
//                        if (item.getIsFav() == 1) { //1赞 0取消赞
//                            item.setIsFav(0);
//                            item.setFavourNum((item.getFavourNum()) - 1);
//                        } else {
//                            showDzView(view);
//                            item.setIsFav(1);
//                            item.setFavourNum(item.getFavourNum() + 1);
//                        }
//                        RxBus.getDefault().post(new RxEvent<>("dz", item));
//                    }
//                }));
    }


//------------------------------------------------------------------------------------------------------------------------------------------------

    /*
     * 图片点击事件 -- 不再使用了
     * */
    public static void imgclick(String imgurl, View view) {
        ToastUtils.showShort("---" + imgurl);
    }

    /*
     * 条目点击事件  // 使用router 跳转    应该放到opensource  todo
     * */
    public void ItemDynamicClick(CircleDynamicVo item, View view) {
        ToastUtils.showShort("---" + item.getName() + "---------------");

    }
//------------------------------------------------------------------------------------------------------------------------------------------

    public void getData() {
        getInfoData();
    }


    // 动态详情数据 调用固定接口的方式
    public void fechDynamicDetail(HashMap<String, String> param) {
        mResourceLiveData.addSource(
                commonRepository.noneChache(
                        circleService.fechDynamicDetail(param)), new HivsNetBoundObserver<>(new NetBoundCallback<ServiceDataBean>() {
                    @Override
                    public void onComplete(Resource<ServiceDataBean> resource) {
                        super.onComplete(resource);
                        mVmEventSouce.setValue(new ViewEventResouce(205, "", resource.data));
                        items.add(resource.data);
                    }

                    @Override
                    public void onBusinessError(Resource<ServiceDataBean> resource) {
                        super.onBusinessError(resource);
                        mVmEventSouce.setValue(new ViewEventResouce(206, "", null));
                    }

                    @Override
                    public void onNetworkError(Resource<ServiceDataBean> resource) {
                        super.onNetworkError(resource);
                        mVmEventSouce.setValue(new ViewEventResouce(207, "", null));
                    }
                }));
    }

//-----------------------------------------------------------------------------------------------------------------------------------

    // 评论
    public void commentDynamic(HashMap<String, String> params) {
        if (checkLoginState()) {
            return;
        }
        showDialogWait("发送中...", false);
        mResourceLiveData.addSource(
                commonRepository.noneChache(
                        circleService.commentDynamic(params)), new HivsNetBoundObserver<>(new NetBoundCallback<CommentRstVo>() {
                    @Override
                    public void onComplete(Resource<CommentRstVo> resource) {
                        super.onComplete(resource);
                        hideDialogWait();
                        if (resource.data != null) {
                            mVmEventSouce.setValue(new ViewEventResouce(211, "", resource.data));
                        }
                    }

                    @Override
                    public void onComplete() {
                        super.onComplete();
                        hideDialogWait();
                    }
                }));
    }


    // 获取权限
    public void getCommentPerssion(ServiceDataBean item) {
        if (checkLoginState()) {
            return;
        }
        //getAuthorization
        showDialogWait("加载中...", false);
        UserInfoVo userInfoVo = CacheUtils.getUser();
        Map<String, String> params = new HashMap<>();
        params.put("circleid", item.getCircleid());
        params.put("utid", item.getUtid());
        params.put("memberid", userInfoVo.uid);
        params.put("uuid", userInfoVo.uuid);

        mResourceLiveData.addSource(
                commonRepository.noneChache(
                        circleService.getAuthorization(params)), new HivsNetBoundObserver<>(new NetBoundCallback<PerssionVo>() {
                    @Override
                    public void onComplete(Resource<PerssionVo> resource) {
                        super.onComplete(resource);
                        hideDialogWait();
                        if (resource.data != null) {
                            isComment = resource.data.isComment;
                            if (isComment.equals("1")) {
                                if ("safe".equals(item.getType())) {
                                    if (serviceDataBean == null) {
                                        return;
                                    }
                                    ARouter.getInstance().build(AppRouter.COMMENTLIST).withSerializable("staDetailParam", mStaDetail).withSerializable("serviceDataBean", serviceDataBean).navigation();
                                } else {
                                    mVmEventSouce.setValue(new ViewEventResouce(210, "", item));
                                }
                            } else {
                                ToastUtils.showShort("暂无评论权限！");
                            }
                        }
                    }

                    @Override
                    public void onComplete() {
                        super.onComplete();
                        hideDialogWait();
                    }
                }));

    }


    // 详情中的语音点击
    public void AudioDetailClick(String audiourl, View view) {
        if (playerUtils == null) {
            playerUtils = new AudioPlayerUtils();
        }
        playerUtils.AudioDetailClick(audiourl, view);
    }


    public void getShareData(HashMap<String, String> params) {
        if (checkLoginState()) {
            return;
        }
        showDialogWait("加载中...", false);
        mResourceLiveData.addSource(
                commonRepository.noneChache(
                        circleService.share(params)), new HivsNetBoundObserver<>(new NetBoundCallback<ShareBean>() {
                    @Override
                    public void onComplete(Resource<ShareBean> resource) {
                        super.onComplete(resource);
                        hideDialogWait();
                        mVmEventSouce.setValue(new ViewEventResouce(211, "", resource.data));
                    }

                    @Override
                    public void onComplete() {
                        super.onComplete();
                        hideDialogWait();
                    }
                }));

    }

    @OnLifecycleEvent(Lifecycle.Event.ON_DESTROY)
    public void destory() {
        if (playerUtils != null) {
            playerUtils.ondestory();
        }
        if (disposable != null) {
            disposable.dispose();
        }
    }


    public void attention(ServiceDataBean serviceDataBean, View view) {
        if (checkLoginState()) {
            return;
        }
        if (serviceDataBean == null) {
            return;
        }
        //attention
        showDialogWait("关注中...", false);
        HashMap<String, String> params = new HashMap<>();
        UserInfoVo userInfoVo = CacheUtils.getUser();
        params.put("memberid", userInfoVo.uid);
        params.put("memberid2", serviceDataBean.getMemberid());
        params.put("uuid2", serviceDataBean.getUuid());
        params.put("uuid", userInfoVo.uuid);
        if (!TextUtils.isEmpty(userInfoVo.nickname)) {
            params.put("nickname", userInfoVo.nickname);
        } else {
            params.put("nickname", "匿名");
        }
        if (serviceDataBean.getIsFocus() == 1) {
            params.put("status", "0");
        } else {
            params.put("status", "1");
        }

        mResourceLiveData.addSource(
                commonRepository.noneChache(
                        circleService.attention(params)), new HivsNetBoundObserver<>(new NetBoundCallback<String>() {
                    @Override
                    public void onComplete(Resource<String> resource) {
                        super.onComplete(resource);
                        hideDialogWait();
                        if (serviceDataBean.getIsFocus() == 1) {
                            serviceDataBean.setIsFocus(0);
                        } else {
                            serviceDataBean.setIsFocus(1);
                        }
                        serviceDataBean.notifyPropertyChanged(BR.isFocus);
                        RxBus.getDefault().post(new RxEvent<>("dynamic_refresh", ""));
                    }

                    @Override
                    public void onComplete() {
                        super.onComplete();
                        hideDialogWait();
                    }
                }));
    }

    public void dynamicTop(String circleid, String dynamicid, String utid) {
        showDialogWait("置顶中...", false);
        HashMap<String, String> params = new HashMap<>();
        UserInfoVo userInfoVo = CacheUtils.getUser();
        params.put("circleid", circleid);
        params.put("dynamicid", dynamicid);
        params.put("utid", utid);
        params.put("memberid", userInfoVo.uid);
        params.put("status", "1");
        params.put("uuid", userInfoVo.uuid);
        mResourceLiveData.addSource(
                commonRepository.noneChache(
                        circleService.dynamicTop(params)), new HivsNetBoundObserver<>(new NetBoundCallback<String>() {
                    @Override
                    public void onComplete(Resource<String> resource) {
                        super.onComplete(resource);
                        hideDialogWait();
                        ToastUtils.showShort("置顶成功");
                    }

                    @Override
                    public void onComplete() {
                        super.onComplete();
                        hideDialogWait();
                    }

                    @Override
                    public void onNetworkError(Resource<String> resource) {
                        super.onNetworkError(resource);
                        ToastUtils.showShort("网络原因请重试");
                    }
                }));
    }

    public void dynamicDel(String circleid, String dynamicid, String utid) {
        showDialogWait("删除中...", false);
        HashMap<String, String> params = new HashMap<>();
        UserInfoVo userInfoVo = CacheUtils.getUser();
        params.put("circleid", circleid);
        params.put("dynamicid", dynamicid);
        params.put("utid", utid);
        params.put("memberid", userInfoVo.uid);
        params.put("uuid", userInfoVo.uuid);
        mResourceLiveData.addSource(
                commonRepository.noneChache(
                        circleService.dynamicDel(params)), new HivsNetBoundObserver<>(new NetBoundCallback<String>() {
                    @Override
                    public void onComplete(Resource<String> resource) {
                        super.onComplete(resource);
                        hideDialogWait();
                        ToastUtils.showShort("删除成功");
                        mVmEventSouce.setValue(new ViewEventResouce(10090, "", null));
                    }

                    @Override
                    public void onComplete() {
                        super.onComplete();
                        hideDialogWait();
                    }

                    @Override
                    public void onNetworkError(Resource<String> resource) {
                        super.onNetworkError(resource);
                        ToastUtils.showShort("网络原因请重试");
                    }
                }));
    }


    //  获取热门搜索关键词
    public void fetchHotSearch() {
        mResourceLiveData.addSource(
                commonRepository.noneChache(
                        circleService.hotwords("2")), new HivsNetBoundObserver<>(new NetBoundCallback<List<RstServerVo>>(this) {
                    @Override
                    public void onComplete(Resource<List<RstServerVo>> resource) {
                        super.onComplete(resource);
                        mVmEventSouce.setValue(new ViewEventResouce(1003, "", resource.data));
                    }

                    @Override
                    public void onNetworkError(Resource<List<RstServerVo>> resource) {
                        super.onNetworkError(resource);
                        mVmEventSouce.setValue(new ViewEventResouce(1003, "", null));
                    }
                }));
    }


    // 分类接口
    public void linkage(String keyid, String parentid) {
        mResourceLiveData.addSource(
                commonRepository.noneChache(
                        circleService.linkage(keyid, parentid)), new HivsNetBoundObserver<>(new NetBoundCallback<List<TypeVo>>(this) {
                    @Override
                    public void onComplete(Resource<List<TypeVo>> resource) {
                        super.onComplete(resource);
                        mVmEventSouce.setValue(new ViewEventResouce(1004, "", resource.data));
                    }

                    @Override
                    public void onNetworkError(Resource<List<TypeVo>> resource) {
                        super.onNetworkError(resource);
                        mVmEventSouce.setValue(new ViewEventResouce(1004, "", null));
                    }
                }));
    }


    // 获取筛选条件
    public void getScreenData(String type) {
        showDialogWait("加载中......", false);
        UserInfoVo userInfoVo = CacheUtils.getUser();
        HashMap<String, String> params = new HashMap<>();
        params.put("t", type);
        params.put("memberid", userInfoVo.uid);
        params.put("uuid", userInfoVo.uuid);
        mResourceLiveData.addSource(
                commonRepository.noneChache(
                        circleService.screenData(params)), new HivsNetBoundObserver<>(new NetBoundCallback<List<ScreenVo>>(this) {
                    @Override
                    public void onComplete(Resource<List<ScreenVo>> resource) {
                        super.onComplete(resource);
                        hideDialogWait();
//                        itemsScreen.addAll(resource.data);
                        mVmEventSouce.setValue(new ViewEventResouce(1005, "", resource.data));
                    }

                    @Override
                    public void onBusinessError(Resource<List<ScreenVo>> resource) {
                        super.onBusinessError(resource);
                        hideDialogWait();
                        ToastUtils.showShort("获取数据失败");
                    }

                    @Override
                    public void onNetworkError(Resource<List<ScreenVo>> resource) {
                        super.onNetworkError(resource);
                        hideDialogWait();
                        mVmEventSouce.setValue(new ViewEventResouce(1006, "", null));
                        ToastUtils.showShort("网络原因，获取数据失败");
                    }
                }));
    }


    // 获取筛选条件
    public void getScreenGoodsData(String type) {
        showDialogWait("请稍后......", false);
        UserInfoVo userInfoVo = CacheUtils.getUser();
        HashMap<String, String> params = new HashMap<>();
        params.put("t", type);
        params.put("memberid", userInfoVo.uid);
        params.put("uuid", userInfoVo.uuid);
        mResourceLiveData.addSource(
                commonRepository.noneChache(
                        circleService.screenData(params)), new HivsNetBoundObserver<>(new NetBoundCallback<List<ScreenVo>>(this) {
                    @Override
                    public void onComplete(Resource<List<ScreenVo>> resource) {
                        super.onComplete(resource);
                        hideDialogWait();
//                        itemsScreen.addAll(resource.data);
                        mVmEventSouce.setValue(new ViewEventResouce(1005, "", resource.data));
                    }

                    @Override
                    public void onBusinessError(Resource<List<ScreenVo>> resource) {
                        super.onBusinessError(resource);
                        hideDialogWait();
                        ToastUtils.showShort("获取数据失败");
                    }

                    @Override
                    public void onNetworkError(Resource<List<ScreenVo>> resource) {
                        super.onNetworkError(resource);
                        hideDialogWait();
                        mVmEventSouce.setValue(new ViewEventResouce(1006, "", null));
                        ToastUtils.showShort("网络原因，获取数据失败");
                    }
                }));
    }


    // 举报
    public void circleReport(ServiceDataBean serviceDataBean, View view) {
        mVmEventSouce.setValue(new ViewEventResouce(10020, "", serviceDataBean));
    }


    // 举报个人
    public void circlePersionReport(String memberid, String content) {
        showDialogWait("举报中...", false);
        mResourceLiveData.addSource(
                commonRepository.noneChache(
                        circleService.CircleReport(memberid, content)), new HivsNetBoundObserver<>(new NetBoundCallback<String>() {
                    @Override
                    public void onComplete(Resource<String> resource) {
                        super.onComplete(resource);
                        hideDialogWait();
                        ToastUtils.showShort("举报成功");
                    }

                    @Override
                    public void onComplete() {
                        super.onComplete();
                        hideDialogWait();
                    }

                    @Override
                    public void onNetworkError(Resource<String> resource) {
                        super.onNetworkError(resource);
                        ToastUtils.showShort("举报失败请重试...");
                    }
                }));
    }


    public void circleBlackList(String memberid) {
        if (checkLoginState()) {
            return;
        }
        showDialogWait("拉黑中...", false);
        UserInfoVo userInfoVo = CacheUtils.getUser();
        mResourceLiveData.addSource(
                commonRepository.noneChache(
                        circleService.pullBlack(userInfoVo.memberid, memberid)), new HivsNetBoundObserver<>(new NetBoundCallback<String>() {
                    @Override
                    public void onComplete(Resource<String> resource) {
                        super.onComplete(resource);
                        hideDialogWait();
                        ToastUtils.showShort("拉黑成功");
                    }

                    @Override
                    public void onComplete() {
                        super.onComplete();
                        hideDialogWait();
                    }

                    @Override
                    public void onNetworkError(Resource<String> resource) {
                        super.onNetworkError(resource);
                        ToastUtils.showShort("拉黑失败请重试...");
                    }
                }));
    }
}
