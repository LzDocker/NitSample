package com.docker.nitsample.vm;

import android.arch.lifecycle.LiveData;

import com.docker.cirlev2.api.CircleApiService;
import com.docker.common.common.vm.container.NitCommonContainerViewModel;
import com.docker.core.di.netmodule.ApiResponse;
import com.docker.core.di.netmodule.BaseResponse;

import java.util.HashMap;

import javax.inject.Inject;

public class CircleJoinListViewModel extends NitCommonContainerViewModel {

    @Inject
    CircleApiService circleApiService;

    @Inject
    public CircleJoinListViewModel() {

    }


    @Override
    public LiveData<ApiResponse<BaseResponse>> getServicefun(String apiurl, HashMap param) {
        LiveData<ApiResponse<BaseResponse>> serverfun = null;
        switch (mCommonListReq.falg) {
            case 101:
            case 103:
                serverfun = circleApiService.fechJoinCircle(param);
                break;
            case 102:
                serverfun = circleApiService.fechCircleList(param);
                break;
        }
        return serverfun;
    }


}
