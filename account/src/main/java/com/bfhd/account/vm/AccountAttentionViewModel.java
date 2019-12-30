package com.bfhd.account.vm;

import android.arch.lifecycle.LiveData;

import com.bfhd.account.vo.index.setting.SettingPersonInfoVo;
import com.bfhd.account.vo.tygs.FansVo;
import com.docker.common.common.vm.container.NitCommonContainerViewModel;
import com.docker.common.common.widget.empty.EmptyStatus;
import com.docker.core.di.netmodule.ApiResponse;
import com.docker.core.di.netmodule.BaseResponse;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import javax.inject.Inject;

public class AccountAttentionViewModel extends NitCommonContainerViewModel {


    @Inject
    public AccountAttentionViewModel() {
    }

    @Override
    public void loadData() {
        mEmptycommand.set(EmptyStatus.BdHiden);
        FansVo fansVo = new FansVo(0, 0);
        fansVo.setCircleName("1111");
        fansVo.setNickname("1111");
        fansVo.setIsFocus(0);

        FansVo fansVo1 = new FansVo(0, 0);
        fansVo1.setCircleName("222");
        fansVo1.setNickname("222");
        fansVo1.setIsFocus(1);

        FansVo fansVo2 = new FansVo(0, 0);
        fansVo2.setCircleName("222");
        fansVo2.setNickname("222");
        fansVo2.setIsFocus(2);


        List<FansVo> fansVoList = new ArrayList<>();
        fansVoList.add(fansVo);
        fansVoList.add(fansVo1);
        fansVoList.add(fansVo2);
        mItems.addAll(fansVoList);
    }


    @Override
    public LiveData<ApiResponse<BaseResponse>> getServicefun(String apiurl, HashMap param) {
        return null;
    }


}
