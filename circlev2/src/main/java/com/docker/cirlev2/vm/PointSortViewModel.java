package com.docker.cirlev2.vm;

import com.docker.common.common.model.BaseItemModel;
import com.docker.common.common.vm.NitCommonListVm;

import java.util.Collection;

public class PointSortViewModel extends NitCommonListVm {

    @Override
    public Collection<? extends BaseItemModel> formatListData(Collection data) {
        return data;
    }

    @Override
    public BaseItemModel formatData(BaseItemModel data) {
        return data;
    }


//    @Override
//    public LiveData<ApiResponse<BaseResponse>> getServicefun(String apiurl, HashMap param) {
//        return super.getServicefun(apiurl, param);
//    }


    @Override
    public void loadData() {
//        super.loadData();


    }
}
