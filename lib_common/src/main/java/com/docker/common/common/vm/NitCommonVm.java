package com.docker.common.common.vm;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MediatorLiveData;

import com.docker.common.api.OpenService;
import com.docker.core.di.netmodule.ApiResponse;
import com.docker.core.di.netmodule.BaseResponse;
import com.docker.core.repository.CommonRepository;
import com.docker.core.repository.Resource;

import javax.inject.Inject;

import timber.log.Timber;

public abstract class NitCommonVm<T> extends NitCommonBaseVm {

    @Inject
    CommonRepository commonRepository;


    @Inject
    public OpenService openService;

    @Override
    public void initCommand() {

    }

    public MediatorLiveData<Resource<T>> RequestServer(LiveData<ApiResponse<BaseResponse<T>>> servicefun) {
        if (servicefun == null) {
            Timber.e("servicefun == null");
            return null;
        }
        return commonRepository.noneChache(servicefun);
    }

    public MediatorLiveData<Resource<T>> RequestSpeicalServer(LiveData<ApiResponse<T>> servicefun) {
        if (servicefun == null) {
            Timber.e("servicefun == null");
            return null;
        }
        return commonRepository.SpecialFeatch(servicefun);
    }

}
