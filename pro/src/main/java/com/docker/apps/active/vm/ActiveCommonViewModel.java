package com.docker.apps.active.vm;

import android.arch.lifecycle.LiveData;
import android.view.View;

import com.docker.apps.active.api.ActiveService;
import com.docker.apps.active.vo.LinkageVo;
import com.docker.apps.active.vo.PersionVo;
import com.docker.common.common.vm.container.NitCommonContainerViewModel;
import com.docker.common.common.widget.empty.EmptyStatus;
import com.docker.core.di.netmodule.ApiResponse;
import com.docker.core.di.netmodule.BaseResponse;

import java.util.HashMap;

import javax.inject.Inject;

public class ActiveCommonViewModel extends NitCommonContainerViewModel {


    public int apiType = 0;

    @Inject
    public ActiveCommonViewModel() {

    }


    @Inject
    ActiveService activeService;

    @Override
    public LiveData<ApiResponse<BaseResponse>> getServicefun(String apiurl, HashMap param) {

        LiveData<ApiResponse<BaseResponse>> serverFun = null;
        switch (apiType) {
            case 0:
                serverFun = activeService.fetchLinkType(param);
                break;
        }
        return serverFun;
    }


    public void onTypeSelectClick(LinkageVo item, View view) {
        for (int i = 0; i < mItems.size(); i++) {
            if (!((LinkageVo) mItems.get(i)).linkageid.equals(item.linkageid)) {
                ((LinkageVo) mItems.get(i)).setChecked(false);
            } else {
                ((LinkageVo) mItems.get(i)).setChecked(!((LinkageVo) mItems.get(i)).isChecked());
            }
        }
    }

}
