package com.docker.common.common.vm.container;

import android.arch.lifecycle.MediatorLiveData;

import com.docker.common.common.model.BaseItemModel;
import com.docker.common.common.vm.NitCommonListVm;

import java.util.Collection;

import javax.inject.Inject;

/*
 *
 * mark
 *
 * 只做继承使用
 * */
public class NitCommonContainerViewModel extends NitCommonListVm {

    /*
     * vm 数据源
     * */
    public final MediatorLiveData<Object> mContainerLiveData = new MediatorLiveData<Object>();


    @Inject
    public NitCommonContainerViewModel() {

    }

    @Override
    public Collection<? extends BaseItemModel> formatListData(Collection data) {
        return data;
    }

    @Override
    public BaseItemModel formatData(BaseItemModel data) {
        return data;
    }
}
