package com.docker.apps.point.vm;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MediatorLiveData;

import com.docker.apps.point.api.PointService;
import com.docker.apps.point.vo.PointSortItemVo;
import com.docker.apps.point.vo.PointSortVo;
import com.docker.apps.point.vo.PointTabVo;
import com.docker.cirlev2.vm.CircleDynamicListViewModel;
import com.docker.cirlev2.vm.PointSortViewModel;
import com.docker.common.common.model.BaseItemModel;
import com.docker.common.common.vm.NitCommonListVm;
import com.docker.common.common.vm.container.NitCommonContainerViewModel;
import com.docker.core.di.netmodule.ApiResponse;
import com.docker.core.di.netmodule.BaseResponse;
import com.docker.core.repository.NitBoundCallback;
import com.docker.core.repository.NitNetBoundObserver;
import com.docker.core.repository.Resource;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;

import javax.inject.Inject;

public class PonitSortVm extends CircleDynamicListViewModel {


    @Inject
    public PonitSortVm() {

    }

    public int scope = 0;

    public String rankType;

    @Inject
    PointService pointService;


    public MediatorLiveData<List<PointSortItemVo>> mHeadDataLv = new MediatorLiveData<>();


    public MediatorLiveData<List<PointTabVo>> mPointTabLv = new MediatorLiveData<>();


    public void fechPointTabdata() {
        mPointTabLv.addSource(RequestServer(pointService.fechPointTabdata()),
                new NitNetBoundObserver<List<PointTabVo>>(new NitBoundCallback<List<PointTabVo>>() {
                    @Override
                    public void onComplete(Resource<List<PointTabVo>> resource) {
                        super.onComplete(resource);
                        mPointTabLv.setValue(resource.data);
                    }

                    @Override
                    public void onBusinessError(Resource<List<PointTabVo>> resource) {
                        super.onBusinessError(resource);
                        mPointTabLv.setValue(null);
                    }

                    @Override
                    public void onNetworkError(Resource<List<PointTabVo>> resource) {
                        super.onNetworkError(resource);
                        mPointTabLv.setValue(null);
                    }
                }));
    }

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
        return pointService.fechPointSortList(param);
    }

    @Override
    public void formartData(Resource resource) {
//        super.formartData(resource);

        for (int i = 0; i < ((List<PointSortItemVo>) resource.data).size(); i++) {
            ((List<PointSortItemVo>) resource.data).get(i).rankType = rankType;
        }
        if (mPage == 1) {
            ArrayList<PointSortItemVo> mMouthIts = new ArrayList<>();
            if (((List<PointSortItemVo>) resource.data).size() >= 3) {
                mMouthIts.addAll(((List<PointSortItemVo>) resource.data).subList(0, 3));
                ((List<PointSortItemVo>) resource.data).removeAll(mMouthIts);
            } else {
                mMouthIts.addAll(((List<PointSortItemVo>) resource.data));
                resource.data = new ArrayList<>();
            }
            mHeadDataLv.setValue(mMouthIts);
        }
    }
}
