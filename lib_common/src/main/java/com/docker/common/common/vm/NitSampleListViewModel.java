package com.docker.common.common.vm;

import android.arch.lifecycle.LiveData;

import com.docker.common.common.model.BaseItemModel;
import com.docker.common.common.widget.empty.EmptyStatus;
import com.docker.core.di.netmodule.ApiResponse;
import com.docker.core.di.netmodule.BaseResponse;

import java.util.Collection;
import java.util.HashMap;

import javax.inject.Inject;

@Deprecated
public class NitSampleListViewModel extends NitCommonListVm {


    @Inject
    public NitSampleListViewModel() {

    }

    @Override
    public void loadData() {
        mEmptycommand.set(EmptyStatus.BdHiden);


    }

    @Override
    public BaseItemModel formatData(BaseItemModel data) {
        return data;
    }

    @Override
    public Collection<? extends BaseItemModel> formatListData(Collection data) {
        return data;
    }

    @Override
    public LiveData<ApiResponse<BaseResponse>> getServicefun(String apiurl, HashMap param) {
        return null;
    }


}
