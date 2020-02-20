package com.docker.nitsample.vm;

import android.arch.lifecycle.MediatorLiveData;

import com.docker.common.api.OpenService;
import com.docker.common.common.vm.NitCommonVm;
import com.docker.core.repository.NitBoundCallback;
import com.docker.core.repository.NitNetBoundObserver;
import com.docker.core.repository.Resource;
import com.docker.nitsample.api.SampleService;
import com.docker.nitsample.vo.Tabvo;

import java.util.List;

import javax.inject.Inject;

public class IndexTygsViewModel extends NitCommonVm {

    @Inject
    SampleService sampleService;

    public MediatorLiveData<List<Tabvo>> mTabvoLiveData = new MediatorLiveData<>();

    @Inject
    public IndexTygsViewModel() {

    }


    public void fetchIndexTab() {
        mTabvoLiveData.addSource(RequestServer(sampleService.fetchIndexTabvo()), new NitNetBoundObserver<List<Tabvo>>(new NitBoundCallback<List<Tabvo>>() {
            @Override
            public void onComplete(Resource<List<Tabvo>> resource) {
                super.onComplete(resource);
                mTabvoLiveData.setValue(resource.data);
            }

            @Override
            public void onBusinessError(Resource<List<Tabvo>> resource) {
                super.onBusinessError(resource);
                mTabvoLiveData.setValue(null);
            }

            @Override
            public void onNetworkError(Resource<List<Tabvo>> resource) {
                super.onNetworkError(resource);
                mTabvoLiveData.setValue(null);
            }
        }));

    }


}
