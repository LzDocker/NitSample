package com.docker.cirlev2.vm.card;

import android.arch.lifecycle.LiveData;
import android.util.Log;

import com.docker.cirlev2.api.CircleApiService;
import com.docker.cirlev2.vo.card.PersonInfoHeadCardVo;
import com.docker.cirlev2.vo.card.PersonInfoHeadVo;
import com.docker.cirlev2.vo.entity.CircleCountpageVo;
import com.docker.cirlev2.vo.entity.ServiceDataBean;
import com.docker.common.common.model.BaseItemModel;
import com.docker.common.common.vm.container.NitcommonCardViewModel;
import com.docker.common.common.vo.card.BaseCardVo;
import com.docker.core.repository.NitBoundCallback;
import com.docker.core.repository.NitNetBoundObserver;
import com.docker.core.repository.Resource;

import java.util.Collection;

import javax.inject.Inject;

public class CirclePersonInfoHeadCardVm extends NitcommonCardViewModel {

    public PersonInfoHeadCardVo personInfoHeadVo;

    @Inject
    CircleApiService circleApiService;

    @Inject
    public CirclePersonInfoHeadCardVm() {

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
        if (personInfoHeadVo != null) {
            loadCardData(personInfoHeadVo);
        }
    }

    @Override
    public void loadCardData(BaseCardVo accountHeadCardVo) {
        this.personInfoHeadVo = (PersonInfoHeadCardVo) accountHeadCardVo;
        LiveData<Resource<PersonInfoHeadVo>> responseLiveData = RequestServer(circleApiService.circlePersionCount(personInfoHeadVo.mRepParamMap));
        personInfoHeadVo.mCardVoLiveData.addSource(responseLiveData,
                new NitNetBoundObserver<>(new NitBoundCallback<PersonInfoHeadVo>() {
                    @Override
                    public void onComplete(Resource<PersonInfoHeadVo> resource) {
                        super.onComplete(resource);
                        personInfoHeadVo.mCardVoLiveData.setValue(resource.data);
                        personInfoHeadVo.mCardVoLiveData.removeSource(responseLiveData);
                        personInfoHeadVo.setDatasource(resource.data);
                    }

                    @Override
                    public void onNetworkError(Resource<PersonInfoHeadVo> resource) {
                        super.onNetworkError(resource);
                        personInfoHeadVo.mCardVoLiveData.setValue(null);
                    }
                }));

    }

}
