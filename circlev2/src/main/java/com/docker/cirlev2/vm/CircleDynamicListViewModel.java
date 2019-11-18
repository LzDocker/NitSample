package com.docker.cirlev2.vm;

import android.arch.lifecycle.LiveData;
import android.text.TextUtils;

import com.docker.cirlev2.api.CircleApiService;
import com.docker.cirlev2.vo.vo.SampleItemVo;
import com.docker.common.common.vm.container.NitCommonContainerViewModel;
import com.docker.common.common.widget.empty.EmptyStatus;
import com.docker.core.di.netmodule.ApiResponse;
import com.docker.core.di.netmodule.BaseResponse;

import java.util.HashMap;

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


}
