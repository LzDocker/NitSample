package com.docker.common.common.ui.base;

import android.arch.lifecycle.ViewModelProvider;
import android.databinding.ViewDataBinding;
import android.support.v4.app.Fragment;

import com.docker.common.common.command.NitContainerCommand;
import com.docker.common.common.command.NitDelegetCommand;
import com.docker.common.common.vm.NitCommonVm;
import com.docker.common.common.widget.dialog.DialogWait;
import com.docker.common.common.widget.refresh.SmartRefreshLayout;
import com.docker.core.base.BaseFragment;

import java.util.ArrayList;

import javax.inject.Inject;

public abstract class NitCommonFragment<VM extends NitCommonVm, VB extends ViewDataBinding> extends BaseFragment<VM, VB> {
    public DialogWait dialogWait;
    @Inject
    public ViewModelProvider.Factory factory;

    public void onLoadMore(SmartRefreshLayout refreshLayout) {
    }

    public void onReFresh(SmartRefreshLayout refreshLayout) {
    }

    public void setData(Object o) {
    }

    public void showWaitDialog(String message) {
        if (dialogWait == null) {
            dialogWait = new DialogWait(getHoldingActivity());
        }
        dialogWait.setMessage(message);
        dialogWait.show();
    }

    public void hidWaitDialog() {
        if (getHoldingActivity() != null && dialogWait != null) {
            dialogWait.dismiss();
        }
    }

    public NitContainerCommand providerNitContainerCommand(int flag) {
        return null;
    }

    public NitDelegetCommand providerNitDelegetCommand(int flag) {
        return null;
    }
}
