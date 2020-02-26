package com.docker.nitsample.vm.circle;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MediatorLiveData;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;

import com.alibaba.android.arouter.launcher.ARouter;
import com.dcbfhd.utilcode.utils.ToastUtils;
import com.docker.cirlev2.api.CircleApiService;
import com.docker.cirlev2.vo.entity.CircleListNomalVo;
import com.docker.cirlev2.vo.entity.CircleListVo;
import com.docker.common.common.model.BaseItemModel;
import com.docker.common.common.router.AppRouter;
import com.docker.common.common.utils.cache.CacheUtils;
import com.docker.common.common.vm.container.NitCommonContainerViewModel;
import com.docker.common.common.vm.container.NitcommonCardViewModel;
import com.docker.common.common.vo.UserInfoVo;
import com.docker.common.common.vo.card.BaseCardVo;
import com.docker.core.di.netmodule.ApiResponse;
import com.docker.core.di.netmodule.BaseResponse;
import com.docker.core.repository.NitBoundCallback;
import com.docker.core.repository.NitNetBoundObserver;
import com.docker.core.repository.Resource;
import com.docker.nitsample.api.SampleService;
import com.docker.nitsample.vo.card.AppRecycleHorizontalCardVo2;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;

import javax.inject.Inject;

public class CircleJoinListVm extends NitCommonContainerViewModel {

    public int mApitype = 0;

    @Inject
    CircleApiService circleApiService;


    @Inject
    SampleService sampleService;

    @Inject
    public CircleJoinListVm() {

    }


    @Override
    public LiveData<ApiResponse<BaseResponse>> getServicefun(String apiurl, HashMap param) {

        LiveData<ApiResponse<BaseResponse>> serverfun = null;

        switch (mApitype) {
            case 0:
                serverfun = sampleService.fechCircleList(param);
                break;
            case 1:
                serverfun = sampleService.fechCircleListNomal(param);
                break;
        }
        return serverfun;


    }


    public MediatorLiveData<String> mJoinSucLv = new MediatorLiveData<>();

    public void joinCircle(CircleListVo circleListNomalVo, View view) {

        if (CacheUtils.getUser() == null) {
            return;
        }
        ARouter.getInstance().build(AppRouter.HOME_JOIN_ACTION).withString("circleid", circleListNomalVo.circleid)
                .withString("utid", circleListNomalVo.getUtid()).navigation();

//        UserInfoVo userInfoVo = CacheUtils.getUser();
//        HashMap<String, String> params = new HashMap<>();
//        params.put("memberid", userInfoVo.uid);
//        params.put("uuid", userInfoVo.uuid);
//        params.put("utid", circleListNomalVo.getUtid());
//        if (TextUtils.isEmpty(userInfoVo.nickname)) {
//            params.put("fullName", "匿名");
//        } else {
//            params.put("fullName", userInfoVo.nickname);
//        }
//        params.put("circleid", circleListNomalVo.circleid);
//        mJoinSucLv.addSource(RequestServer(circleApiService.joinCircle(params)), new NitNetBoundObserver<String>(new NitBoundCallback<String>() {
//            @Override
//            public void onComplete(Resource<String> resource) {
//                super.onComplete(resource);
//                ToastUtils.showShort("加入成功");
//                mJoinSucLv.setValue(resource.data);
//                mItems.remove(circleListNomalVo);
//            }
//
//            @Override
//            public void onBusinessError(Resource resource) {
//                super.onBusinessError(resource);
//                ToastUtils.showShort("加入失败请重试");
//            }
//
//            @Override
//            public void onNetworkError(Resource resource) {
//                super.onNetworkError(resource);
//                ToastUtils.showShort("加入失败请重试");
//            }
//        }));
    }


    public void closeCircle(CircleListVo circleListNomalVo, View view) {
        mItems.remove(circleListNomalVo);
    }


    public final MediatorLiveData<String> mCheckLv = new MediatorLiveData<>();


    public void sendCheckMesg(HashMap<String, String> param) {
        mCheckLv.addSource(RequestServer(sampleService.sendCheckMsg(param)), new NitNetBoundObserver<String>(new NitBoundCallback<String>() {
            @Override
            public void onComplete(Resource<String> resource) {
                super.onComplete(resource);
                mCheckLv.setValue("succ");
            }
        }));
    }

}
