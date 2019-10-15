package com.bfhd.circle.base;

import android.arch.lifecycle.Lifecycle;
import android.arch.lifecycle.MediatorLiveData;
import android.arch.lifecycle.OnLifecycleEvent;
import android.databinding.ObservableBoolean;
import android.databinding.ObservableInt;

import com.docker.common.common.command.RefreshCommand;
import com.docker.common.common.widget.empty.EmptyStatus;
import com.docker.core.base.BaseVm;
import com.docker.core.util.SingleLiveEvent;

public abstract class HivsBaseViewModel extends BaseVm {
    public final SingleLiveEvent<ViewEventResouce> mVmEventSouce = new SingleLiveEvent<>();

    /*
     * vm--->activity 传递事件
     * */
    public SingleLiveEvent<ViewEventResouce> getViewEventResouce() {
        return mVmEventSouce;
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_CREATE)
    public void create() {
        initCommand();
        mEmptycommand.set(EmptyStatus.BdLoading);
    }

    public abstract void initCommand();

    /*
     * vm 数据源
     * */
    public final MediatorLiveData<Object> mResourceLiveData = new MediatorLiveData<Object>();

    /*
     * vm smatrrefresh 禁止刷新
     * */
    public ObservableBoolean mEnableRefresh = new ObservableBoolean();

    public ObservableBoolean mEnableLoadmore = new ObservableBoolean();

    public ObservableInt mEmptycommand = new ObservableInt();

    public int mPage = 1;
    /*
  smartrefresh 是否完成加载
 * */
    public ObservableBoolean mCompleteCommand = new ObservableBoolean();


    public RefreshCommand mCommand = new RefreshCommand();

    public boolean mIsfirstLoad = true;

    public void showDialogWait(String message, boolean cancelable) {
        mVmEventSouce.setValue(new ViewEventResouce(101, message, cancelable));
    }

    public void hideDialogWait() {
        mVmEventSouce.setValue(new ViewEventResouce(102, null, null));
    }

}
