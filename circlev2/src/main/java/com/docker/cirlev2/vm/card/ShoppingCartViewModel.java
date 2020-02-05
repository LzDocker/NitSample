package com.docker.cirlev2.vm.card;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MediatorLiveData;
import android.databinding.ObservableField;
import android.view.View;

import com.docker.cirlev2.api.CircleApiService;
import com.docker.cirlev2.vo.card.PersonInfoHeadCardVo;
import com.docker.cirlev2.vo.card.PersonInfoHeadVo;
import com.docker.cirlev2.vo.entity.ServiceDataBean;
import com.docker.cirlev2.vo.vo.ShoppingCarItemVo;
import com.docker.cirlev2.vo.vo.ShoppingCarVo;
import com.docker.common.common.vm.container.NitCommonContainerViewModel;
import com.docker.common.common.vm.container.NitcommonCardViewModel;
import com.docker.common.common.vo.card.BaseCardVo;
import com.docker.common.common.widget.empty.EmptyStatus;
import com.docker.core.di.netmodule.ApiResponse;
import com.docker.core.di.netmodule.BaseResponse;
import com.docker.core.repository.NitBoundCallback;
import com.docker.core.repository.NitNetBoundObserver;
import com.docker.core.repository.Resource;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import javax.inject.Inject;

public class ShoppingCartViewModel extends NitcommonCardViewModel {


    public ShoppingCarVo shoppingCarVo;

    @Inject
    CircleApiService circleApiService;


    @Inject
    public ShoppingCartViewModel() {

    }


    @Override
    public void loadData() {
        if (shoppingCarVo != null) {
            loadCardData(shoppingCarVo);
        }
    }


    @Override
    public LiveData<ApiResponse<BaseResponse>> getServicefun(String apiurl, HashMap param) {

        return null;
    }


    @Override
    public void loadCardData(BaseCardVo accountHeadCardVo) {
        this.shoppingCarVo = (ShoppingCarVo) accountHeadCardVo;
        LiveData<Resource<List<ShoppingCarItemVo>>> responseLiveData = RequestServer(circleApiService.getGoodsTrueData(shoppingCarVo.mRepParamMap));
        shoppingCarVo.mCardVoLiveData.addSource(responseLiveData,
                new NitNetBoundObserver<>(new NitBoundCallback<List<ShoppingCarItemVo>>() {
                    @Override
                    public void onComplete(Resource<List<ShoppingCarItemVo>> resource) {
                        super.onComplete(resource);
                        shoppingCarVo.mCardVoLiveData.setValue(resource.data);
                        shoppingCarVo.mCardVoLiveData.removeSource(responseLiveData);
                        shoppingCarVo.setShopCardListOb(resource.data);
                    }

                    @Override
                    public void onNetworkError(Resource<List<ShoppingCarItemVo>> resource) {
                        super.onNetworkError(resource);
                        shoppingCarVo.mCardVoLiveData.setValue(null);
                    }
                }));

    }
}
