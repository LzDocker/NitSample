package com.docker.common.common.vm.container;

import android.arch.lifecycle.MediatorLiveData;
import android.databinding.ObservableArrayList;
import android.databinding.ObservableList;
import android.util.Log;

import com.docker.common.BR;
import com.docker.common.common.command.ReplyCommand;
import com.docker.common.common.model.BaseItemModel;
import com.docker.common.common.vm.NitCommonListVm;
import com.docker.common.common.vo.ServiceDataBean;

import java.util.ArrayList;
import java.util.Collection;

import javax.inject.Inject;

import me.tatarka.bindingcollectionadapter2.ItemBinding;
import me.tatarka.bindingcollectionadapter2.collections.MergeObservableList;

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

    @Override
    public void refresh() {
        super.refresh();
    }
}
