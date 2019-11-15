package com.docker.cirlev2.vm;

import android.arch.lifecycle.LiveData;

import com.docker.cirlev2.api.CircleApiService;
import com.docker.cirlev2.vo.entity.CircleListVo;
import com.docker.cirlev2.vo.vo.SampleItemVo;
import com.docker.common.common.model.BaseItemModel;
import com.docker.common.common.vm.container.NitCommonContainerViewModel;
import com.docker.common.common.widget.empty.EmptyStatus;
import com.docker.core.di.netmodule.ApiResponse;
import com.docker.core.di.netmodule.BaseResponse;

import java.util.Collection;
import java.util.HashMap;

import javax.inject.Inject;

public class CircleMinesViewModel extends NitCommonContainerViewModel {


    @Inject
    public CircleMinesViewModel() {

    }

    @Inject
    CircleApiService circleApiService;

    @Override
    public LiveData<ApiResponse<BaseResponse>> getServicefun(String apiurl, HashMap param) {
        LiveData<ApiResponse<BaseResponse>> serverfun = null;
        switch (mCommonListReq.falg) {
            case 101:
                serverfun = circleApiService.fechJoinCircle(param);
                break;
            case 102:
                serverfun = circleApiService.fechCircleList(param);
                break;
        }
        return serverfun;
    }

    @Override
    public Collection<? extends BaseItemModel> formatListData(Collection data) {
        switch (mCommonListReq.falg) {
            case 101:
                CircleListVo circleCraeteVo = new CircleListVo();
                circleCraeteVo.circleName = "创建圈子";
                data.add(circleCraeteVo);
                break;
            case 102:


                break;
        }


        return data;
    }
}
