package com.bfhd.account.vm;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MediatorLiveData;

import com.bfhd.account.vo.MyInfoVo;
import com.bfhd.account.vo.index.AccountHeadVo;
import com.bfhd.account.vo.index.AccountIndexItemVo;
import com.bfhd.account.vo.index.setting.SettingItemVo;
import com.docker.common.common.vm.NitCommonListVm;
import com.docker.common.common.vm.container.NitCommonContainerViewModel;
import com.docker.common.common.widget.empty.EmptyStatus;
import com.docker.core.di.httpmodule.ApiResponse;
import com.docker.core.di.httpmodule.BaseResponse;

import java.util.HashMap;

import javax.inject.Inject;

public class AccountSettingViewModel extends NitCommonContainerViewModel {


    @Inject
    public AccountSettingViewModel() {
    }

    @Override
    public void loadData() {
        mEmptycommand.set(EmptyStatus.BdHiden);
        SettingItemVo settingItemVo = new SettingItemVo();
        mItems.add(settingItemVo);

    }


    @Override
    public LiveData<ApiResponse<BaseResponse>> getServicefun(String apiurl, HashMap param) {
        return null;
    }


}
