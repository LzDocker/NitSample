package com.docker.cirlev2.vm;

import android.arch.lifecycle.MediatorLiveData;

import com.docker.cirlev2.api.CircleApiService;
import com.docker.cirlev2.vo.entity.ServiceDataBean;
import com.docker.core.repository.NitBoundCallback;
import com.docker.core.repository.NitNetBoundObserver;
import com.docker.core.repository.Resource;

import java.util.HashMap;

import javax.inject.Inject;

public class CircleDynamicDetailViewModel extends CircleDynamicListViewModel {

    public final MediatorLiveData<ServiceDataBean> mDynamicDetailLv = new MediatorLiveData<>();

    @Inject
    CircleApiService circleApiService;

    @Inject
    public CircleDynamicDetailViewModel() {

    }

    // 动态详情
    public MediatorLiveData<ServiceDataBean> fechDynamicDetail(HashMap<String, String> param) {
        mDynamicDetailLv.addSource(RequestServer(circleApiService.fechDynamicDetail(param)), new NitNetBoundObserver<ServiceDataBean>(new NitBoundCallback<ServiceDataBean>() {
            @Override
            public void onComplete(Resource<ServiceDataBean> resource) {
                super.onComplete(resource);
                mDynamicDetailLv.setValue(resource.data);
            }

            @Override
            public void onNetworkError(Resource resource) {
                super.onNetworkError(resource);
                mDynamicDetailLv.setValue(null);
            }

            @Override
            public void onBusinessError(Resource resource) {
                super.onBusinessError(resource);
                mDynamicDetailLv.setValue(null);
            }
        }));
        return mDynamicDetailLv;
    }

}
