package com.docker.nitsample.vm;

import android.arch.lifecycle.LiveData;
import android.support.annotation.NonNull;

import com.bfhd.circle.api.CircleService;
import com.bfhd.circle.base.HivsNetBoundObserver;
import com.bfhd.circle.base.NetBoundCallback;
import com.dcbfhd.utilcode.utils.AppUtils;
import com.docker.common.api.OpenService;
import com.docker.common.common.vm.NitCommonVm;
import com.docker.common.common.vo.ServiceDataBean;
import com.docker.common.common.vo.UpdateInfo;
import com.docker.common.common.widget.empty.EmptyStatus;
import com.docker.core.di.netmodule.ApiResponse;
import com.docker.core.di.netmodule.BaseResponse;
import com.docker.core.repository.NetworkBoundResourceAuto;
import com.docker.core.repository.Resource;

import java.util.List;

import javax.inject.Inject;

public class MainViewModel extends NitCommonVm {

    @Inject
    OpenService openService;

    @Inject
    public MainViewModel() {

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
