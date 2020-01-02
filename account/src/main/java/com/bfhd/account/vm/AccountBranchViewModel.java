package com.bfhd.account.vm;

import android.arch.lifecycle.LiveData;

import com.bfhd.account.vo.tygs.BranchVo;
import com.bfhd.account.vo.tygs.FansVo;
import com.docker.common.common.vm.container.NitCommonContainerViewModel;
import com.docker.common.common.widget.empty.EmptyStatus;
import com.docker.core.di.netmodule.ApiResponse;
import com.docker.core.di.netmodule.BaseResponse;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import javax.inject.Inject;

public class AccountBranchViewModel extends NitCommonContainerViewModel {


    @Inject
    public AccountBranchViewModel() {
    }

    @Override
    public void loadData() {
        mEmptycommand.set(EmptyStatus.BdHiden);
        BranchVo branchVo = new BranchVo(0, 0);
        branchVo.setCircleName("1111");
        branchVo.setNickname("1111");
        branchVo.setIsFocus(0);

        BranchVo branchVo1 = new BranchVo(0, 0);
        branchVo1.setCircleName("222");
        branchVo1.setNickname("222");
        branchVo1.setIsFocus(2);

        List<BranchVo> fansVoList = new ArrayList<>();
        fansVoList.add(branchVo);
        fansVoList.add(branchVo1);

        mItems.addAll(fansVoList);
    }


    @Override
    public LiveData<ApiResponse<BaseResponse>> getServicefun(String apiurl, HashMap param) {
        return null;
    }


}
