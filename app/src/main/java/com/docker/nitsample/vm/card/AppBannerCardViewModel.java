package com.docker.nitsample.vm.card;

import android.arch.lifecycle.LiveData;
import android.util.Log;

import com.bfhd.account.api.AccountService;
import com.bfhd.account.vo.MyInfoDispatcherVo;
import com.bfhd.account.vo.card.AccountHeadCardVo;
import com.docker.common.common.model.BaseItemModel;
import com.docker.common.common.vm.container.NitcommonCardViewModel;
import com.docker.common.common.vo.card.BaseCardVo;
import com.docker.core.repository.NitBoundCallback;
import com.docker.core.repository.NitNetBoundObserver;
import com.docker.core.repository.Resource;
import com.docker.nitsample.api.SampleService;
import com.docker.nitsample.vo.BannerEntityVo;
import com.docker.nitsample.vo.card.AppBannerCardVo;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import javax.inject.Inject;

public class AppBannerCardViewModel extends NitcommonCardViewModel {


    public AppBannerCardVo appBannerCardVo;

    @Inject
    SampleService sampleService;

    @Inject
    public AppBannerCardViewModel() {

    }

    public void fetchAppBannerData(AppBannerCardVo appBannerCardVo) {
        this.appBannerCardVo = appBannerCardVo;
        LiveData<Resource<List<BannerEntityVo>>> responseLiveData = RequestServer(sampleService.fetchBannerAd(appBannerCardVo.mRepParamMap));
        appBannerCardVo.mCardVoLiveData.addSource(responseLiveData,
                new NitNetBoundObserver<>(new NitBoundCallback<List<BannerEntityVo>>() {
                    @Override
                    public void onComplete(Resource<List<BannerEntityVo>> resource) {
                        super.onComplete(resource);
                        appBannerCardVo.mCardVoLiveData.setValue(resource.data);
                        ArrayList<BannerEntityVo> appBannerCardVos = new ArrayList<>();
                        appBannerCardVos.addAll((ArrayList<BannerEntityVo>) resource.data);
                        appBannerCardVos.addAll((ArrayList<BannerEntityVo>) resource.data);
                        appBannerCardVos.addAll((ArrayList<BannerEntityVo>) resource.data);
                        appBannerCardVos.addAll((ArrayList<BannerEntityVo>) resource.data);
                        appBannerCardVos.addAll((ArrayList<BannerEntityVo>) resource.data);
                        appBannerCardVos.addAll((ArrayList<BannerEntityVo>) resource.data);
                        appBannerCardVos.addAll((ArrayList<BannerEntityVo>) resource.data);
                        appBannerCardVo.setBannerList(appBannerCardVos);
                    }
                    @Override
                    public void onNetworkError(Resource<List<BannerEntityVo>> resource) {
                        super.onNetworkError(resource);
                        appBannerCardVo.mCardVoLiveData.setValue(null);
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
        if (appBannerCardVo != null) {
            fetchAppBannerData(appBannerCardVo);
        }
    }

    @Override
    public void loadCardData(BaseCardVo accountHeadCardVo) {
        fetchAppBannerData((AppBannerCardVo) accountHeadCardVo);
    }


}
