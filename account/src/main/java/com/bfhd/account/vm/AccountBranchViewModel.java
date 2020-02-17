package com.bfhd.account.vm;

import android.arch.lifecycle.LiveData;

import com.bfhd.account.api.AccountService;
import com.bfhd.account.vo.tygs.BranchVo;
import com.bfhd.account.vo.tygs.BranchVoV2;
import com.bfhd.account.vo.tygs.FansVo;
import com.docker.common.common.vm.container.NitCommonContainerViewModel;
import com.docker.common.common.widget.empty.EmptyStatus;
import com.docker.core.di.netmodule.ApiResponse;
import com.docker.core.di.netmodule.BaseResponse;
import com.docker.core.repository.Resource;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import javax.inject.Inject;

public class AccountBranchViewModel extends NitCommonContainerViewModel {

    public int flag = 0;

    @Inject
    AccountService accountService;

    @Inject
    public AccountBranchViewModel() {
    }


    @Override
    public LiveData<ApiResponse<BaseResponse>> getServicefun(String apiurl, HashMap param) {
        LiveData<ApiResponse<BaseResponse>> serverfun = null;
        serverfun = accountService.fechJoinCircle(param);
        return serverfun;
    }

    @Override
    public void formartData(Resource resource) {
        super.formartData(resource);
        if (flag == 1) {
            for (int i = 0; i < ((List<BranchVoV2>) resource.data).size(); i++) {
                ((List<BranchVoV2>) resource.data).get(i).style = flag;
            }
        }
    }
}
