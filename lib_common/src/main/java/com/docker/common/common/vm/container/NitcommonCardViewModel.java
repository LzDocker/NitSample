package com.docker.common.common.vm.container;

import android.arch.lifecycle.LiveData;
import android.text.TextUtils;

import com.docker.common.common.model.BaseItemModel;
import com.docker.common.common.vm.NitCommonListVm;
import com.docker.common.common.widget.empty.EmptyStatus;
import com.docker.core.di.netmodule.ApiResponse;
import com.docker.core.di.netmodule.BaseResponse;

import java.util.Collection;
import java.util.HashMap;

import javax.inject.Inject;
public class NitcommonCardViewModel extends NitCommonListVm {


    @Inject
    public NitcommonCardViewModel() {
    }

    @Override
    public void loadData() {
        mItems.clear();
        mEmptycommand.set(EmptyStatus.BdHiden);
        mCompleteCommand.set(true);
        mCompleteCommand.notifyChange();
    }

    @Override
    public BaseItemModel formatData(BaseItemModel data) {
        return null;
    }

    @Override
    public Collection<? extends BaseItemModel> formatListData(Collection data) {
        return null;
    }

    @Override
    public LiveData<ApiResponse<BaseResponse>> getServicefun(String apiurl, HashMap param) {
        return null;
    }

    public void onCardFrameVisible() {
        if (!TextUtils.isEmpty((CharSequence) mCommonListReq.externs.get("NeedVisibleRefresh"))) {
            onJustRefresh();
        }
    }

}
