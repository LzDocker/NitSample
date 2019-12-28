package com.bfhd.account.vm;

import android.arch.lifecycle.LiveData;

import com.bfhd.account.vo.index.setting.SettingItemVo;
import com.bfhd.account.vo.index.setting.SettingPersonInfoVo;
import com.docker.common.common.vm.container.NitCommonContainerViewModel;
import com.docker.common.common.vm.container.NitcommonCardViewModel;
import com.docker.common.common.widget.empty.EmptyStatus;
import com.docker.core.di.netmodule.ApiResponse;
import com.docker.core.di.netmodule.BaseResponse;

import java.util.HashMap;

import javax.inject.Inject;

public class AccountPersonInfoViewModel extends NitCommonContainerViewModel {


    @Inject
    public AccountPersonInfoViewModel() {
    }

    @Override
    public void loadData() {
        mEmptycommand.set(EmptyStatus.BdHiden);
        SettingPersonInfoVo settingPersonInfoVo = new SettingPersonInfoVo(0,0);
        mItems.add(settingPersonInfoVo);
    }


    @Override
    public LiveData<ApiResponse<BaseResponse>> getServicefun(String apiurl, HashMap param) {
        return null;
    }


}
