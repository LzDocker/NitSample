package com.docker.nitsample.vm.card;

import android.arch.lifecycle.LiveData;
import android.util.Log;

import com.docker.apps.active.api.ActiveService;
import com.docker.apps.active.vo.ActiveWraperVo;
import com.docker.common.common.model.BaseItemModel;
import com.docker.common.common.vm.container.NitcommonCardViewModel;
import com.docker.common.common.vo.card.BaseCardVo;
import com.docker.core.repository.NitBoundCallback;
import com.docker.core.repository.NitNetBoundObserver;
import com.docker.core.repository.Resource;
import com.docker.nitsample.vo.card.AppRecycleHorizontalCardVo;

import java.util.Collection;

import javax.inject.Inject;

public class AppRecycleHorizontalVm extends NitcommonCardViewModel {


    public AppRecycleHorizontalCardVo appRecycleCard2Vo;

    @Inject
    ActiveService activeService;

    @Inject
    public AppRecycleHorizontalVm() {

    }

    public void fetchAppIndexMenuData(AppRecycleHorizontalCardVo appRecycleCard2Vo) {
//        if (appRecycleCard2Vo.getActiveVos().get().size() != 0) {
//            return;
//        }
        this.appRecycleCard2Vo = appRecycleCard2Vo;
        LiveData<Resource<ActiveWraperVo>> responseLiveData = RequestServer(activeService.fetchActiveList(appRecycleCard2Vo.mRepParamMap));
        appRecycleCard2Vo.mCardVoLiveData.addSource(responseLiveData,
                new NitNetBoundObserver<>(new NitBoundCallback<ActiveWraperVo>() {
                    @Override
                    public void onComplete(Resource<ActiveWraperVo> resource) {
                        super.onComplete(resource);
                        appRecycleCard2Vo.mCardVoLiveData.setValue(resource.data);
                        appRecycleCard2Vo.setActiveVos(((ActiveWraperVo) resource.data).indexList);
                    }

                    @Override
                    public void onNetworkError(Resource<ActiveWraperVo> resource) {
                        super.onNetworkError(resource);
                        appRecycleCard2Vo.mCardVoLiveData.setValue(null);
                    }
                }));
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
        if (appRecycleCard2Vo != null && appRecycleCard2Vo.getActiveVos().get().size() == 0) {
            fetchAppIndexMenuData(appRecycleCard2Vo);
        }
    }

    @Override
    public void loadCardData(BaseCardVo accountHeadCardVo) {
        if (((AppRecycleHorizontalCardVo) accountHeadCardVo).getActiveVos() != null && ((AppRecycleHorizontalCardVo) accountHeadCardVo).getActiveVos().get() != null) {
            ((AppRecycleHorizontalCardVo) accountHeadCardVo).getActiveVos().get().clear();
        }
        fetchAppIndexMenuData((AppRecycleHorizontalCardVo) accountHeadCardVo);
    }
}
