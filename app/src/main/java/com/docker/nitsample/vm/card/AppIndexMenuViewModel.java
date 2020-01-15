package com.docker.nitsample.vm.card;

import android.arch.lifecycle.LiveData;
import android.util.Log;

import com.docker.common.common.model.BaseItemModel;
import com.docker.common.common.vm.container.NitcommonCardViewModel;
import com.docker.common.common.vo.card.BaseCardVo;
import com.docker.core.repository.NitBoundCallback;
import com.docker.core.repository.NitNetBoundObserver;
import com.docker.core.repository.Resource;
import com.docker.nitsample.api.SampleService;
import com.docker.nitsample.vo.MenuEntityVo;
import com.docker.nitsample.vo.card.AppRecycleCard2Vo;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import javax.inject.Inject;

public class AppIndexMenuViewModel extends NitcommonCardViewModel {


    public AppRecycleCard2Vo appRecycleCard2Vo;

    @Inject
    SampleService sampleService;

    @Inject
    public AppIndexMenuViewModel() {

    }

    public void fetchAppIndexMenuData(AppRecycleCard2Vo appRecycleCard2Vo) {
        if (appRecycleCard2Vo.getInnerResource().size() != 0) {
            return;
        }
        this.appRecycleCard2Vo = appRecycleCard2Vo;
        LiveData<Resource<List<MenuEntityVo>>> responseLiveData = RequestServer(sampleService.fetchIndexMenu(appRecycleCard2Vo.mRepParamMap));
        appRecycleCard2Vo.mCardVoLiveData.addSource(responseLiveData,
                new NitNetBoundObserver<>(new NitBoundCallback<List<MenuEntityVo>>() {
                    @Override
                    public void onComplete(Resource<List<MenuEntityVo>> resource) {
                        super.onComplete(resource);
                        appRecycleCard2Vo.mCardVoLiveData.setValue(resource.data);
                        appRecycleCard2Vo.setInnerResource((ArrayList<MenuEntityVo>) resource.data);
                    }

                    @Override
                    public void onNetworkError(Resource<List<MenuEntityVo>> resource) {
                        super.onNetworkError(resource);
                        appRecycleCard2Vo.mCardVoLiveData.setValue(null);
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
        if (appRecycleCard2Vo != null && appRecycleCard2Vo.getInnerResource().size() == 0) {
            fetchAppIndexMenuData(appRecycleCard2Vo);
        }
    }

    @Override
    public void loadCardData(BaseCardVo accountHeadCardVo) {
        fetchAppIndexMenuData((AppRecycleCard2Vo) accountHeadCardVo);
    }


}
