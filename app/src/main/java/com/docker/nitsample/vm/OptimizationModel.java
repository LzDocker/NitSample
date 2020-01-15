package com.docker.nitsample.vm;

import android.arch.lifecycle.MediatorLiveData;

import com.docker.common.common.vm.NitCommonVm;
import com.docker.core.repository.NitBoundCallback;
import com.docker.core.repository.NitNetBoundObserver;
import com.docker.core.repository.Resource;
import com.docker.nitsample.api.SampleService;
import com.docker.nitsample.vo.BannerEntityVo;

import java.util.HashMap;
import java.util.List;

import javax.inject.Inject;

public class OptimizationModel extends NitCommonVm {


    @Inject
    SampleService sampleService;

    @Inject
    public OptimizationModel() {
    }


    public final MediatorLiveData<HashMap<String, List<BannerEntityVo>>> mapMediatorLiveData = new MediatorLiveData<>();


    public void loadBannerGoodsData() {
        mapMediatorLiveData.addSource(RequestServer(sampleService.fetchGoodsBannervo()), new NitNetBoundObserver<HashMap<String, List<BannerEntityVo>>>(new NitBoundCallback<HashMap<String, List<BannerEntityVo>>>() {
            @Override
            public void onComplete(Resource<HashMap<String, List<BannerEntityVo>>> resource) {
                super.onComplete(resource);
                mapMediatorLiveData.setValue(resource.data);
            }

            @Override
            public void onBusinessError(Resource<HashMap<String, List<BannerEntityVo>>> resource) {
                super.onBusinessError(resource);
                mapMediatorLiveData.setValue(null);
            }

            @Override
            public void onNetworkError(Resource<HashMap<String, List<BannerEntityVo>>> resource) {
                super.onNetworkError(resource);
                mapMediatorLiveData.setValue(null);
            }
        }

        ));
    }

}
