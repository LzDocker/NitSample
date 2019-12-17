package com.docker.cirlev2.vm;

import android.arch.lifecycle.LiveData;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;

import com.alibaba.android.arouter.launcher.ARouter;
import com.dcbfhd.utilcode.utils.ActivityUtils;
import com.docker.cirlev2.R;
import com.docker.cirlev2.api.CircleApiService;
import com.docker.cirlev2.util.BdUtils;
import com.docker.cirlev2.vo.entity.ServiceDataBean;
import com.docker.cirlev2.vo.param.StaPersionDetail;
import com.docker.cirlev2.vo.vo.SampleItemVo;
import com.docker.common.common.model.OnItemClickListener;
import com.docker.common.common.router.AppRouter;
import com.docker.common.common.utils.cache.CacheUtils;
import com.docker.common.common.utils.rxbus.RxBus;
import com.docker.common.common.utils.rxbus.RxEvent;
import com.docker.common.common.vm.container.NitCommonContainerViewModel;
import com.docker.common.common.vo.UserInfoVo;
import com.docker.common.common.widget.empty.EmptyStatus;
import com.docker.core.di.netmodule.ApiResponse;
import com.docker.core.di.netmodule.BaseResponse;
import com.docker.core.repository.NitBoundCallback;
import com.docker.core.repository.NitNetBoundObserver;
import com.docker.core.repository.Resource;
import com.luck.picture.lib.PictureSelector;
import com.luck.picture.lib.entity.LocalMedia;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import javax.inject.Inject;

public class CircleDynamicListViewModel extends NitCommonContainerViewModel {


    @Inject
    CircleApiService circleApiService;

    @Inject
    public CircleDynamicListViewModel() {

    }

    @Override
    public LiveData<ApiResponse<BaseResponse>> getServicefun(String apiurl, HashMap param) {
        if (!TextUtils.isEmpty(apiurl)) {
            return circleApiService.fechCircleInfoList(apiurl, param);
        }
        return circleApiService.fechCircleInfoList(param);
    }

    // 点赞
    public OnItemClickListener onItemDz() {
        return (item, view) -> {

        };
    }


    // 动态点击
    public void ItemDynamicClick(ServiceDataBean item, View view) {

        ARouter.getInstance().build(AppRouter.CIRCLE_dynamic_v2_detail).withString("dynamicId", item.getDynamicid()).navigation();
//        ARouter.getInstance().build(AppRouter.CIRCLE_DETAIL).withString("dynamicid", item.getDynamicid()).navigation();
    }

    // 头像点击
    public void ItemAvaterClick(ServiceDataBean item, View view) {
        if (item != null) {
            StaPersionDetail staPersionDetail = new StaPersionDetail();
            staPersionDetail.name = item.getExtData().linkman;
            staPersionDetail.uuid = item.getUuid();
            staPersionDetail.uid = item.getMemberid();
            ARouter.getInstance().build(AppRouter.CIRCLE_persion_v2_detail).withSerializable("mStartParam", staPersionDetail).navigation();
        }

    }


    // 关注
    public void attention(ServiceDataBean item, View view) {

    }


    // 单一图片或视频点击事件
    public void singleVideoOrImgClick(ServiceDataBean serviceDataBean, View view) {
        if (serviceDataBean.getExtData() != null && serviceDataBean.getExtData().getResource() != null && serviceDataBean.getExtData().getResource().get(0) != null) {
            if (serviceDataBean.getExtData().getResource().get(0).getT() == 2) {
                String videoUrl = BdUtils.getImgUrl(serviceDataBean.getExtData().getResource().get(0).getUrl());
                String thumbUrl = BdUtils.getImgUrl(serviceDataBean.getExtData().getResource().get(0).getImg());
                ARouter.getInstance().build(AppRouter.VIDEOSINGLE).withString("videoUrl", videoUrl).withString("thumbUrl", thumbUrl).navigation();
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
                            localMedia.setPath(BdUtils.getImgUrl(resourceBeans.get(i).getImg()));
                        } else {
                            localMedia.setPath(BdUtils.getImgUrl(resourceBeans.get(i).getUrl()));
                        }
                        localMediaList.add(localMedia);
                    }
                }
                PictureSelector
                        .create(ActivityUtils.getTopActivity())
                        .themeStyle(R.style.picture_default_style)
                        .openExternalPreview(position, localMediaList);
            }
        }
    }

    //
    public void ItemZFClick(ServiceDataBean item, View view) {
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

    public void getShareData(HashMap<String, String> params) {

        showDialogWait("加载中...", false);
        mServerLiveData.addSource(
                RequestServer(circleApiService.share(params))
                , new NitNetBoundObserver(new NitBoundCallback<ServiceDataBean.ShareBean>() {
                    @Override
                    public void onComplete(Resource<ServiceDataBean.ShareBean> resource) {
                        super.onComplete(resource);
                        hideDialogWait();
                        RxBus.getDefault().post(new RxEvent<>("show_share", resource.data));
                    }

                    @Override
                    public void onComplete() {
                        super.onComplete();
                        hideDialogWait();
                    }
                }));

    }

    //
    public void ItemPLClick(ServiceDataBean item, View view) {

    }

    //
    public void ItemDZClick(ServiceDataBean item, View view) {

    }

    // 内部九宫格点击
    public static void imgclick(ServiceDataBean.ResourceBean item, View view, ServiceDataBean serviceDataBean) {
        if (serviceDataBean.getExtData() != null && serviceDataBean.getExtData().getResource() != null) {
            int index = serviceDataBean.getInnerResource().get().indexOf(item);
            if (serviceDataBean.getExtData().getResource().get(index).getT() == 2) {
                String videoUrl = BdUtils.getImgUrl(serviceDataBean.getExtData().getResource().get(index).getUrl());
                String thumbUrl = BdUtils.getImgUrl(serviceDataBean.getExtData().getResource().get(index).getImg());
                ARouter.getInstance().build(AppRouter.VIDEOSINGLE).withString("videoUrl", videoUrl).withString("thumbUrl", thumbUrl).navigation();
            } else {
                List<ServiceDataBean.ResourceBean> resourceBeans = null;
                List<LocalMedia> localMediaList = new ArrayList<>();
                resourceBeans = serviceDataBean.getExtData().getResource();
                if (resourceBeans != null && resourceBeans.size() > 0) {
                    for (int i = 0; i < resourceBeans.size(); i++) {
                        LocalMedia localMedia = new LocalMedia();
                        localMedia.setPictureType("1");
                        if (!TextUtils.isEmpty(resourceBeans.get(i).getImg())) {
                            localMedia.setPath(BdUtils.getImgUrl(resourceBeans.get(i).getImg()));
                        } else {
                            localMedia.setPath(BdUtils.getImgUrl(resourceBeans.get(i).getUrl()));
                        }
                        localMediaList.add(localMedia);
                    }
                }
                PictureSelector
                        .create(ActivityUtils.getTopActivity())
                        .themeStyle(R.style.picture_default_style)
                        .openExternalPreview(index, localMediaList);
            }
        }
    }

}
