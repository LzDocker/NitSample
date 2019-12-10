package com.bfhd.account.vm;

import android.arch.lifecycle.LiveData;

import com.bfhd.account.api.AccountService;
import com.docker.common.common.model.BaseSampleItem;
import com.docker.common.common.vm.container.NitCommonContainerViewModel;
import com.docker.common.common.widget.empty.EmptyStatus;
import com.docker.core.di.netmodule.ApiResponse;
import com.docker.core.di.netmodule.BaseResponse;

import java.util.HashMap;

import javax.inject.Inject;

public class AccountIndexListViewModel extends NitCommonContainerViewModel {

    @Inject
    AccountService accountService;

    @Inject
    public AccountIndexListViewModel() {
    }

    @Override
    public void loadData() {
        mEmptycommand.set(EmptyStatus.BdHiden);

    }

    @Override
    public LiveData<ApiResponse<BaseResponse>> getServicefun(String apiurl, HashMap param) {
        return null;
    }

    public void addCardVo(BaseSampleItem sampleItem) {
        mEmptycommand.set(EmptyStatus.BdHiden);
        mItems.add(sampleItem);
    }


}
