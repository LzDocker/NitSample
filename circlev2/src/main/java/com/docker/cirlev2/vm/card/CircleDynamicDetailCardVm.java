package com.docker.cirlev2.vm.card;

import android.arch.lifecycle.LiveData;
import android.util.Log;

import com.docker.cirlev2.api.CircleApiService;
import com.docker.cirlev2.vo.card.CircleDynamicDetailCardVo;
import com.docker.cirlev2.vo.entity.ServiceDataBean;
import com.docker.common.common.model.BaseItemModel;
import com.docker.common.common.vm.container.NitcommonCardViewModel;
import com.docker.common.common.vo.card.BaseCardVo;
import com.docker.core.repository.NitBoundCallback;
import com.docker.core.repository.NitNetBoundObserver;
import com.docker.core.repository.Resource;

import java.util.Collection;

import javax.inject.Inject;

public class CircleDynamicDetailCardVm extends NitcommonCardViewModel {

    public CircleDynamicDetailCardVo circleDynamicDetailCardVo;

    @Inject
    CircleApiService circleApiService;

    @Inject
    public CircleDynamicDetailCardVm() {

    }

    // 动态详情
    public void fechDynamicDetail(CircleDynamicDetailCardVo circleDynamicDetailCardVo) {
        this.circleDynamicDetailCardVo = circleDynamicDetailCardVo;
        LiveData<Resource<ServiceDataBean>> responseLiveData = RequestServer(circleApiService.fechDynamicDetail(circleDynamicDetailCardVo.mRepParamMap));
        circleDynamicDetailCardVo.mCardVoLiveData.addSource(responseLiveData,
                new NitNetBoundObserver<>(new NitBoundCallback<ServiceDataBean>() {
                    @Override
                    public void onComplete(Resource<ServiceDataBean> resource) {
                        super.onComplete(resource);
                        circleDynamicDetailCardVo.mCardVoLiveData.setValue(resource.data);
                        circleDynamicDetailCardVo.mCardVoLiveData.removeSource(responseLiveData);
                        circleDynamicDetailCardVo.setServerData(resource.data);
                    }

                    @Override
                    public void onNetworkError(Resource<ServiceDataBean> resource) {
                        super.onNetworkError(resource);
                        circleDynamicDetailCardVo.mCardVoLiveData.setValue(null);
                    }
                }));
    }

    public void process() {
        Log.d("sss", "process: ===============================");
    }


    @Override
    public BaseItemModel formatData(BaseItemModel data) {
        return null;
    }

    @Override
    public Collection<? extends BaseItemModel> formatListData(Collection data) {
        return null;
    }

    @Override
    public void loadData() {
        if (circleDynamicDetailCardVo != null) {
            fechDynamicDetail(circleDynamicDetailCardVo);
        }
    }

    @Override
    public void loadCardData(BaseCardVo accountHeadCardVo) {
        if (((CircleDynamicDetailCardVo) accountHeadCardVo).serviceDataBean == null) {
            fechDynamicDetail((CircleDynamicDetailCardVo) accountHeadCardVo);
        }
    }

}
