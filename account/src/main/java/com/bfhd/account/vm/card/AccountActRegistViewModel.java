package com.bfhd.account.vm.card;

import android.arch.lifecycle.LiveData;

import com.bfhd.account.vo.index.setting.SettingPersonInfoVo;
import com.docker.common.common.vm.container.NitCommonContainerViewModel;
import com.docker.common.common.widget.empty.EmptyStatus;
import com.docker.core.di.netmodule.ApiResponse;
import com.docker.core.di.netmodule.BaseResponse;

import java.util.HashMap;

import javax.inject.Inject;

public class AccountActRegistViewModel extends NitCommonContainerViewModel {


    @Inject
    public AccountActRegistViewModel() {
    }

    @Override
    public void loadData() {
        mEmptycommand.set(EmptyStatus.BdHiden);

    }


    @Override
    public LiveData<ApiResponse<BaseResponse>> getServicefun(String apiurl, HashMap param) {
        return null;
    }


}
