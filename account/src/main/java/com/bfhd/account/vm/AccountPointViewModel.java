package com.bfhd.account.vm;

import android.arch.lifecycle.LiveData;

import com.bfhd.account.vo.index.setting.SettingItemVo;
import com.bfhd.account.vo.tygs.PointItemVo;
import com.bfhd.account.vo.tygs.PointVo;
import com.docker.common.common.vm.container.NitCommonContainerViewModel;
import com.docker.common.common.widget.empty.EmptyStatus;
import com.docker.core.di.netmodule.ApiResponse;
import com.docker.core.di.netmodule.BaseResponse;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import javax.inject.Inject;

public class AccountPointViewModel extends NitCommonContainerViewModel {


    @Inject
    public AccountPointViewModel() {
    }

    @Override
    public void loadData() {
        mEmptycommand.set(EmptyStatus.BdHiden);
        PointVo pointVo = new PointVo(0, 0);
        pointVo.setPoint("113");
        PointItemVo pointItemVo = new PointItemVo(0, 0);
        pointItemVo.setName("圣诞节快乐就发生");
        pointItemVo.setPoint("20");
        pointItemVo.setTime("2019-12-02");
        List<PointItemVo> pointItemVoList = new ArrayList<>();
        pointItemVoList.add(pointItemVo);
        pointItemVoList.add(pointItemVo);
        pointItemVoList.add(pointItemVo);
        pointItemVoList.add(pointItemVo);
        pointItemVoList.add(pointItemVo);
        pointItemVoList.add(pointItemVo);
        mItems.addAll(pointItemVoList);
    }


    @Override
    public LiveData<ApiResponse<BaseResponse>> getServicefun(String apiurl, HashMap param) {
        return null;
    }


}
