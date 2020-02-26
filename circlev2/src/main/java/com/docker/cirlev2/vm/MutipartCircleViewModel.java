package com.docker.cirlev2.vm;

import android.arch.lifecycle.MediatorLiveData;

import com.docker.cirlev2.api.CircleApiService;
import com.docker.cirlev2.ui.pro.index.MutipartTabVo;
import com.docker.common.common.vm.NitCommonVm;
import com.docker.core.repository.NitBoundCallback;
import com.docker.core.repository.NitNetBoundObserver;
import com.docker.core.repository.Resource;

import java.util.List;

import javax.inject.Inject;

public class MutipartCircleViewModel extends NitCommonVm {


    @Inject
    public MutipartCircleViewModel() {

    }

    @Inject
    CircleApiService circleApiService;


    public MediatorLiveData<List<MutipartTabVo>> mMutipartTbLv = new MediatorLiveData<>();


    public void fetchMutipartTabData() {
        mMutipartTbLv.addSource(RequestServer(circleApiService.getCircleTab()), new NitNetBoundObserver<List<MutipartTabVo>>(new NitBoundCallback<List<MutipartTabVo>>() {
            @Override
            public void onComplete(Resource<List<MutipartTabVo>> resource) {
                super.onComplete(resource);
                mMutipartTbLv.setValue(resource.data);
            }
        }));
    }


}
