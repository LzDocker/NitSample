package com.docker.common.common.vm;

import android.arch.lifecycle.Lifecycle;
import android.arch.lifecycle.OnLifecycleEvent;
import android.databinding.ObservableBoolean;
import android.databinding.ObservableInt;
import com.dcbfhd.utilcode.utils.ActivityUtils;
import com.docker.common.common.command.RefreshCommand;
import com.docker.common.common.utils.annotation.CustomModeParser;
import com.docker.common.common.widget.dialog.DialogWait;
import com.docker.common.common.widget.empty.EmptyStatus;
import com.docker.core.base.BaseVm;

public abstract class NitCommonBaseVm extends BaseVm {

    private DialogWait dialogWait;

    @OnLifecycleEvent(Lifecycle.Event.ON_CREATE)
    public void create() {
        initCommand();
        mEmptycommand.set(EmptyStatus.BdLoading);
        CustomModeParser.inject(this);
    }



    public abstract void initCommand();

    public ObservableInt mEmptycommand = new ObservableInt();

    public boolean mIsfirstLoad = true;

    public ObservableBoolean mCompleteCommand = new ObservableBoolean();

    public RefreshCommand mCommand = new RefreshCommand();

    public boolean loadingState = false;

    public void showDialogWait(String message, boolean cancelable) {
        if (dialogWait == null) {
            dialogWait = new DialogWait(ActivityUtils.getTopActivity());
        }
        dialogWait.setMessage(message);
        dialogWait.show();
    }

    public void hideDialogWait() {
        if (dialogWait != null) {
            dialogWait.dismiss();
        }
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_DESTROY)
    public void destory() {
        if (dialogWait != null) {
            dialogWait.dismiss();
        }
    }
}
