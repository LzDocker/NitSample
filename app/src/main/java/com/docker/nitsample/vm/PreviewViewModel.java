package com.docker.nitsample.vm;

import android.arch.lifecycle.LifecycleOwner;
import android.arch.lifecycle.Observer;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.View;

import com.dcbfhd.utilcode.utils.ActivityUtils;
import com.dcbfhd.utilcode.utils.ToastUtils;
import com.docker.common.api.OpenService;
import com.docker.common.common.model.BaseItemModel;
import com.docker.common.common.utils.cache.DbCacheUtils;
import com.docker.common.common.utils.rxbus.RxBus;
import com.docker.common.common.utils.rxbus.RxEvent;
import com.docker.common.common.vm.NitCommonListVm;
import com.docker.common.common.vm.NitCommonVm;
import com.docker.nitsample.vo.SamplEditVo;

import java.util.ArrayList;
import java.util.Collection;

import javax.inject.Inject;

public class PreviewViewModel extends NitCommonListVm {

    @Inject
    OpenService openService;

    @Inject
    DbCacheUtils dbCacheUtils;

    @Inject
    public PreviewViewModel() {

    }

    @Override
    public void loadData() {

    }

    @Override
    public Collection<? extends BaseItemModel> formatListData(Collection data) {
        return null;
    }

    @Override
    public BaseItemModel formatData(BaseItemModel data) {
        return null;
    }


}
