package com.docker.nitsample.vm.card;

import android.arch.lifecycle.LiveData;
import android.databinding.ObservableField;
import android.util.Log;

import com.docker.cirlev2.vo.entity.CircleListNomalVo;
import com.docker.cirlev2.vo.entity.ServerGoodsDataBean;
import com.docker.cirlev2.vo.entity.ServiceDataBean;
import com.docker.common.common.vm.container.NitcommonCardViewModel;
import com.docker.common.common.vo.card.BaseCardVo;
import com.docker.core.repository.NitBoundCallback;
import com.docker.core.repository.NitNetBoundObserver;
import com.docker.core.repository.Resource;
import com.docker.nitsample.api.SampleService;
import com.docker.nitsample.vo.OptimizationVo;
import com.docker.nitsample.vo.card.AppGoodsOptimizationCardVo;
import com.docker.nitsample.vo.card.AppRecycleHorizontalCardVo2;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

public class OptimizationCardViewModel extends NitcommonCardViewModel {


    public AppGoodsOptimizationCardVo appGoodsOptimizationCardVo;

    @Inject
    SampleService sampleService;

    @Inject
    public OptimizationCardViewModel() {

    }

    @Override
    public void loadData() {
        if (appGoodsOptimizationCardVo != null) {
            loadCardData(appGoodsOptimizationCardVo);
        }
    }

    @Override
    public void loadCardData(BaseCardVo accountHeadCardVo) {
        this.appGoodsOptimizationCardVo = (AppGoodsOptimizationCardVo) accountHeadCardVo;
        LiveData<Resource<List<ServerGoodsDataBean>>> responseLiveData = RequestServer(sampleService.fechCircleGoodsList(appGoodsOptimizationCardVo.mRepParamMap));
        appGoodsOptimizationCardVo.mCardVoLiveData.addSource(responseLiveData,
                new NitNetBoundObserver<>(new NitBoundCallback<List<ServerGoodsDataBean>>() {
                    @Override
                    public void onComplete(Resource<List<ServerGoodsDataBean>> resource) {
                        super.onComplete(resource);
                        ((AppGoodsOptimizationCardVo) accountHeadCardVo).setRecycleGoodsList((ArrayList<ServerGoodsDataBean>) resource.data);
                    }

                    @Override
                    public void onNetworkError(Resource<List<ServerGoodsDataBean>> resource) {
                        super.onNetworkError(resource);
                        appGoodsOptimizationCardVo.mCardVoLiveData.setValue(null);
                    }
                }));
    }
}
