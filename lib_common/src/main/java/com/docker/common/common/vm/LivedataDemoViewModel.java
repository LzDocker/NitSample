package com.docker.common.common.vm;

import android.arch.core.util.Function;
import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MediatorLiveData;
import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.Observer;
import android.arch.lifecycle.Transformations;
import android.provider.Contacts;
import android.support.annotation.Nullable;

import com.baidubce.model.User;
import com.docker.common.api.OpenService;
import com.docker.common.common.router.RoutersServerVo;
import com.docker.common.common.vm.container.NitCommonContainerViewModel;
import com.docker.common.common.vo.UpdateInfo;
import com.docker.common.common.vo.UserInfoVo;
import com.docker.core.di.netmodule.ApiResponse;
import com.docker.core.di.netmodule.BaseResponse;
import com.docker.core.repository.Resource;

import java.util.HashMap;

import javax.inject.Inject;

/**
 * Created by zhangxindang on 2018/10/19.
 */

public class LivedataDemoViewModel extends NitCommonContainerViewModel {

    @Inject
    OpenService openService;

    @Inject
    public LivedataDemoViewModel() {

    }


    public final LiveData<String> UidLivedata = new MutableLiveData<>();

    public final LiveData<Resource<UpdateInfo>> upDateLV = Transformations.switchMap(UidLivedata, new Function<String, LiveData<Resource<UpdateInfo>>>() {
        @Override
        public LiveData<Resource<UpdateInfo>> apply(String id) {
            return RequestServer(openService.systemUpdate("1", "1", id));
        }
    });

    public final MutableLiveData<HashMap<String, String>> loginParamLV = new MutableLiveData<>();

    public final LiveData<Resource<RoutersServerVo>> userInfoLV = Transformations.switchMap(loginParamLV, new Function<HashMap<String, String>, LiveData<Resource<RoutersServerVo>>>() {
        @Override
        public LiveData<Resource<RoutersServerVo>> apply(HashMap<String, String> input) {
            return RequestServer(openService.fetchRouter(input));
        }
    });


    public void login(HashMap<String, String> params) {
        loginParamLV.setValue(params);
    }


    public final MutableLiveData<RoutersServerVo> routersServerVoMutableLiveData = new MutableLiveData<>();
    public final MutableLiveData<UpdateInfo> updateInfoMutableLiveData = new MutableLiveData<>();

    public final MediatorLiveData mediatorLiveData = new MediatorLiveData<>();

    // 串行接口
    public void mutipartApitest() {


    }

    // 并行接口
    public void onTimeApitest() {
        mediatorLiveData.addSource(routersServerVoMutableLiveData,
                (Observer<RoutersServerVo>) routersServerVo ->
                        mediatorLiveData.setValue(mediatorLiveData));
        mediatorLiveData.addSource(updateInfoMutableLiveData,
                (Observer<UpdateInfo>) updateInfo ->
                        mediatorLiveData.setValue(updateInfo));
    }


}
