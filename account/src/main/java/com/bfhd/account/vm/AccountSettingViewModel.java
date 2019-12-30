package com.bfhd.account.vm;

import android.arch.lifecycle.LiveData;

import com.bfhd.account.vo.index.setting.SettingItemVo;
import com.docker.common.common.vm.container.NitCommonContainerViewModel;
import com.docker.common.common.widget.empty.EmptyStatus;
import com.docker.core.di.netmodule.ApiResponse;
import com.docker.core.di.netmodule.BaseResponse;

import java.util.HashMap;

import javax.inject.Inject;

public class AccountSettingViewModel extends NitCommonContainerViewModel {


    @Inject
    public AccountSettingViewModel() {
    }

    @Override
    public void loadData() {
        mEmptycommand.set(EmptyStatus.BdHiden);
        SettingItemVo settingItemVo = new SettingItemVo(0,0);
        mItems.add(settingItemVo);

    }


    @Override
    public LiveData<ApiResponse<BaseResponse>> getServicefun(String apiurl, HashMap param) {
        return null;
    }


}
