package com.docker.nitsample.vm;

import android.arch.lifecycle.MediatorLiveData;

import com.docker.common.api.OpenService;
import com.docker.common.common.vm.NitCommonVm;
import com.docker.common.common.vo.RstServerVo;
import com.docker.core.repository.NitBoundCallback;
import com.docker.core.repository.NitNetBoundObserver;
import com.docker.core.repository.Resource;
import com.docker.nitsample.api.SampleService;

import java.util.List;

import javax.inject.Inject;

public class SearchViewModel extends NitCommonVm {

    @Inject
    SampleService service;


    public final MediatorLiveData<List<RstServerVo>> hotLableLv = new MediatorLiveData<>();

    @Inject
    public SearchViewModel() {

    }


    public void fetchHotSearchLab() {
        hotLableLv.addSource(RequestServer(service.hotwords("2")), new NitNetBoundObserver<>(new NitBoundCallback<List<RstServerVo>>() {
            @Override
            public void onComplete(Resource<List<RstServerVo>> resource) {
                super.onComplete(resource);
                hotLableLv.setValue(resource.data);
            }
        }));
    }

}
