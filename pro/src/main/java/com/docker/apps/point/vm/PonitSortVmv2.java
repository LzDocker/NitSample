package com.docker.apps.point.vm;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MediatorLiveData;

import com.docker.apps.point.api.PointService;
import com.docker.apps.point.vo.PointSortItemVo;
import com.docker.apps.point.vo.PointSortVo;
import com.docker.cirlev2.vm.CircleDynamicListViewModel;
import com.docker.common.common.model.BaseItemModel;
import com.docker.core.di.netmodule.ApiResponse;
import com.docker.core.di.netmodule.BaseResponse;
import com.docker.core.repository.Resource;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;

import javax.inject.Inject;

public class PonitSortVmv2 extends CircleDynamicListViewModel {


    @Inject
    public PonitSortVmv2() {

    }

    public int scope = 0;

    public String rankType;

    @Inject
    PointService pointService;


    public MediatorLiveData<List<PointSortItemVo>> mMouthHeadLv = new MediatorLiveData<>();
    public MediatorLiveData<List<PointSortItemVo>> mTotalHeadLv = new MediatorLiveData<>();


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

        switch (scope) {
            case 0:

                for (int i = 0; i < ((PointSortVo) resource.data).mouthRank.size(); i++) {
                    ((PointSortVo) resource.data).mouthRank.get(i).rankType = rankType;
                }
                if (mPage == 1) {
                    ArrayList<PointSortItemVo> mMouthIts = new ArrayList<>();
                    if (((PointSortVo) resource.data).mouthRank.size() >= 3) {
                        mMouthIts.addAll(((PointSortVo) resource.data).mouthRank.subList(0, 3));
                        ((PointSortVo) resource.data).mouthRank.removeAll(mMouthIts);
                        resource.data = ((PointSortVo) resource.data).mouthRank;
                    } else {
                        mMouthIts.addAll(((PointSortVo) resource.data).mouthRank);
                        resource.data = new ArrayList<>();
                    }
                    mMouthHeadLv.setValue(mMouthIts);
                } else {
                    resource.data = ((PointSortVo) resource.data).mouthRank;
                }
                break;
            case 1:
                for (int i = 0; i < ((PointSortVo) resource.data).totalRank.size(); i++) {
                    ((PointSortVo) resource.data).totalRank.get(i).rankType = rankType;
                }
                if (mPage == 1) {
                    ArrayList<PointSortItemVo> mTotalIts = new ArrayList<>();
                    if (((PointSortVo) resource.data).totalRank.size() >= 3) {
                        mTotalIts.addAll(((PointSortVo) resource.data).totalRank.subList(0, 3));
                        ((PointSortVo) resource.data).totalRank.removeAll(mTotalIts);
                        resource.data = ((PointSortVo) resource.data).totalRank;
                    } else {
                        mTotalIts.addAll(((PointSortVo) resource.data).totalRank);
                        resource.data = new ArrayList<>();
                    }
                    mTotalHeadLv.setValue(mTotalIts);
                } else {
                    resource.data = ((PointSortVo) resource.data).totalRank;
                }
                break;
        }
    }
}
