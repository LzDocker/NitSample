package com.docker.apps.active.vm;

import android.arch.lifecycle.LiveData;
import android.util.Log;

import com.docker.apps.active.api.ActiveService;
import com.docker.apps.active.vo.ActiveVo;
import com.docker.apps.active.vo.card.ActiveDetailHeadCard;
import com.docker.common.common.model.BaseItemModel;
import com.docker.common.common.vm.container.NitCommonContainerViewModel;
import com.docker.common.common.vm.container.NitcommonCardViewModel;
import com.docker.common.common.vo.card.BaseCardVo;
import com.docker.core.repository.NitBoundCallback;
import com.docker.core.repository.NitNetBoundObserver;
import com.docker.core.repository.Resource;

import java.util.ArrayList;
import java.util.Collection;

import javax.inject.Inject;

public class ActiveHeadCardViewModel extends NitcommonCardViewModel {


    public ActiveDetailHeadCard activeDetailHeadCard;


    @Inject
    public ActiveHeadCardViewModel() {

    }

    @Inject
    ActiveService activeService;


    public void ActiveHeadCardData(ActiveDetailHeadCard activeDetailHeadCard) {
        this.activeDetailHeadCard = activeDetailHeadCard;
        LiveData<Resource<ActiveVo>> responseLiveData = RequestServer(activeService.fetchActivedetail(activeDetailHeadCard.mRepParamMap));
        activeDetailHeadCard.mCardVoLiveData.addSource(responseLiveData,
                new NitNetBoundObserver<>(new NitBoundCallback<ActiveVo>() {
                    @Override
                    public void onComplete(Resource<ActiveVo> resource) {
                        super.onComplete(resource);
                        activeDetailHeadCard.mCardVoLiveData.setValue(resource.data);
                        activeDetailHeadCard.setVoObservableField(resource.data);
                    }

                    @Override
                    public void onNetworkError(Resource<ActiveVo> resource) {
                        super.onNetworkError(resource);
                        activeDetailHeadCard.setVoObservableField(null);
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
        if (activeDetailHeadCard != null) {
            ActiveHeadCardData(activeDetailHeadCard);
        }
    }

    @Override
    public void loadCardData(BaseCardVo accountHeadCardVo) {
        ActiveHeadCardData((ActiveDetailHeadCard) accountHeadCardVo);
    }


}
