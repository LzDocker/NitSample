package com.docker.apps.point.vm;

import android.arch.lifecycle.LiveData;

import com.docker.apps.point.api.PointService;
import com.docker.cirlev2.vm.CircleDynamicListViewModel;
import com.docker.cirlev2.vm.PointSortViewModel;
import com.docker.common.common.model.BaseItemModel;
import com.docker.common.common.vm.NitCommonListVm;
import com.docker.common.common.vm.container.NitCommonContainerViewModel;
import com.docker.core.di.netmodule.ApiResponse;
import com.docker.core.di.netmodule.BaseResponse;
import java.util.Collection;
import java.util.HashMap;

import javax.inject.Inject;

public class PonitSortVm extends CircleDynamicListViewModel {


    @Inject
    public PonitSortVm(){

    }

    @Inject
    PointService pointService;

    @Override
    public Collection<? extends BaseItemModel> formatListData(Collection data) {
        return data;
    }

    @Override
    public BaseItemModel formatData(BaseItemModel data) {
        return data;
    }


    @Override
    public LiveData<ApiResponse<BaseResponse>> getServicefun(String apiurl, HashMap param) {
        return pointService.fechCircleInfoList(param);
    }

}
