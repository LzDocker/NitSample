package com.docker.common.common.ui.base;

import android.arch.lifecycle.ViewModelProvider;
import android.databinding.ViewDataBinding;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;

import com.docker.common.common.command.NitContainerCommand;
import com.docker.common.common.command.NitDelegetCommand;
import com.docker.common.common.widget.dialog.DialogWait;
import com.docker.core.base.BaseActivity;
import com.docker.core.base.BaseVm;

import java.util.ArrayList;

import javax.inject.Inject;

public abstract class NitCommonActivity<VM extends BaseVm, VB extends ViewDataBinding> extends BaseActivity<VM, VB> {

    private DialogWait dialogWait;
    @Inject
    public ViewModelProvider.Factory factory;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initView();
        initObserver();
        initRouter();
    }

    public abstract void initView();

    public abstract void initObserver();

    public abstract void initRouter();

    public NitContainerCommand providerNitContainerCommand(int flag) {
        return null;
    }

    public ArrayList<Fragment> providerNitContainerFragment() {
        return null;
    }


    public void showWaitDialog(String message) {
        if (dialogWait == null) {
            dialogWait = new DialogWait(this);
        }
        dialogWait.setMessage(message);
        dialogWait.show();
    }

    public void hidWaitDialog() {
        if (this != null && dialogWait != null) {
            dialogWait.dismiss();
        }
    }

    /*
     *
     * 提供给内层 listfragment vm代理
     * */
    public NitDelegetCommand providerNitDelegetCommand(int flag) {
        return null;
    }
}
