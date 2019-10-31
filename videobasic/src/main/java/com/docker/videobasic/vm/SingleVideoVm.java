package com.docker.videobasic.vm;

import android.arch.lifecycle.LiveData;
import android.support.annotation.NonNull;

import com.dcbfhd.utilcode.utils.AppUtils;
import com.docker.common.api.OpenService;
import com.docker.common.common.vm.NitCommonVm;
import com.docker.common.common.vo.UpdateInfo;
import com.docker.core.di.netmodule.ApiResponse;
import com.docker.core.di.netmodule.BaseResponse;
import com.docker.core.repository.NetworkBoundResourceAuto;
import com.docker.core.repository.Resource;

import javax.inject.Inject;

public class SingleVideoVm extends NitCommonVm {

    @Inject
    OpenService openService;


    @Inject
    public SingleVideoVm() {

    }

    public LiveData<Resource<UpdateInfo>> checkUpData() {
        return new NetworkBoundResourceAuto<UpdateInfo>() {
            @NonNull
            @Override
            protected LiveData<ApiResponse<BaseResponse<UpdateInfo>>> createCall() {
                return openService.systemUpdate("2", "1", AppUtils.getAppVersionCode() + "");
            }
        }.asLiveData();
    }


}
