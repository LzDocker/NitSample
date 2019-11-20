package com.docker.cirlev2.vm;

import android.arch.lifecycle.MediatorLiveData;
import android.text.TextUtils;

import com.docker.cirlev2.api.CircleApiService;
import com.docker.cirlev2.vo.entity.CircleCreateRst;
import com.docker.cirlev2.vo.entity.CircleDetailVo;
import com.docker.cirlev2.vo.vo.CircleCreateVo;
import com.docker.common.common.utils.ParamUtils;
import com.docker.common.common.utils.cache.CacheUtils;
import com.docker.common.common.vm.NitCommonVm;
import com.docker.common.common.vm.container.NitCommonContainerViewModel;
import com.docker.common.common.vo.UserInfoVo;
import com.docker.common.common.widget.empty.EmptyStatus;
import com.docker.core.repository.NitBoundCallback;
import com.docker.core.repository.NitNetBoundObserver;
import com.docker.core.repository.Resource;

import java.util.HashMap;

import javax.inject.Inject;

import timber.log.Timber;

public class CircleCreateViewModel extends NitCommonVm {


    @Inject
    public CircleCreateViewModel() {

    }

    /*
     * 圈子详情信息
     * */
    public final MediatorLiveData<CircleCreateVo> mCircleDetailLv = new MediatorLiveData<>();
    public final MediatorLiveData<CircleCreateRst> mCircleCreateLv = new MediatorLiveData<>();
    public final MediatorLiveData<CircleCreateRst> mCircleEditLv = new MediatorLiveData<>();

    @Inject
    CircleApiService circleApiService;

    public void getCircleDetailVo(String utid, String circleid) {
        mCircleDetailLv.addSource(RequestServer(circleApiService.fechCircleDetailVo(utid, circleid)),
                new NitNetBoundObserver<>(new NitBoundCallback<CircleCreateVo>() {
                    @Override
                    public void onNetworkError(Resource<CircleCreateVo> resource) {
                        super.onNetworkError(resource);
                    }
                    @Override
                    public void onComplete(Resource<CircleCreateVo> resource) {
                        super.onComplete(resource);
                        mCircleDetailLv.setValue(resource.data);
                    }
                }));
    }


    public void createCircle(CircleCreateVo circleCreateVo, String type) {
        showDialogWait("创建中...", false);
        UserInfoVo userInfoVo = CacheUtils.getUser();
        circleCreateVo.memberid = userInfoVo.uid;
        circleCreateVo.uuid = userInfoVo.uuid;
        circleCreateVo.fullName = circleCreateVo.circleName;
        HashMap<String, String> params = (HashMap<String, String>) ParamUtils.objectToMap(circleCreateVo, false);
        params.put("memberid", userInfoVo.uid);
        params.put("uuid", userInfoVo.uuid);
        params.put("type", type);//官方圈0|兴趣圈1|企业圈2
        if (!TextUtils.isEmpty(userInfoVo.lng) && !TextUtils.isEmpty(userInfoVo.lat)) {
            params.put("lng", userInfoVo.lng);
            params.put("lat", userInfoVo.lat);
        }
        if (TextUtils.isEmpty(userInfoVo.nickname)) {
            params.put("fullName", "匿名");
        } else {
            params.put("fullName", userInfoVo.nickname);
        }
        mCircleCreateLv.addSource(RequestServer(circleApiService.createCircle(params)),
                new NitNetBoundObserver<>(new NitBoundCallback<CircleCreateRst>() {
                    @Override
                    public void onNetworkError(Resource<CircleCreateRst> resource) {
                        super.onNetworkError(resource);
                    }

                    @Override
                    public void onComplete(Resource<CircleCreateRst> resource) {
                        super.onComplete(resource);
                        mCircleCreateLv.setValue(resource.data);
                    }
                }));
    }

    public void editCircle(CircleCreateVo circleCreateVo, String type, String circleid) {
        showDialogWait("保存中...", false);
        UserInfoVo userInfoVo = CacheUtils.getUser();
        circleCreateVo.memberid = userInfoVo.uid;
        circleCreateVo.uuid = userInfoVo.uuid;
        circleCreateVo.fullName = circleCreateVo.circleName;
        HashMap<String, String> params = (HashMap<String, String>) ParamUtils.objectToMap(circleCreateVo, false);
        params.put("memberid", userInfoVo.uid);
        params.put("uuid", userInfoVo.uuid);
        params.put("type", type);//官方圈0|兴趣圈1|企业圈2
        params.put("circleid", circleid);
        if (!TextUtils.isEmpty(userInfoVo.lng) && !TextUtils.isEmpty(userInfoVo.lat)) {
            params.put("lng", userInfoVo.lng);
            params.put("lat", userInfoVo.lat);
        }
        if (TextUtils.isEmpty(userInfoVo.nickname)) {
            params.put("fullName", "匿名");
        } else {
            params.put("fullName", userInfoVo.nickname);
        }
        mCircleEditLv.addSource(RequestServer(circleApiService.createCircle(params)),
                new NitNetBoundObserver<>(new NitBoundCallback<CircleCreateRst>() {
                    @Override
                    public void onNetworkError(Resource<CircleCreateRst> resource) {
                        super.onNetworkError(resource);
                    }

                    @Override
                    public void onComplete(Resource<CircleCreateRst> resource) {
                        super.onComplete(resource);
                        mCircleEditLv.setValue(resource.data);
                    }
                }));
    }
}
