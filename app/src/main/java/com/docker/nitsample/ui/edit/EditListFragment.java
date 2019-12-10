package com.docker.nitsample.ui.edit;

import android.arch.lifecycle.ViewModelProviders;
import android.os.Bundle;
import android.support.annotation.Nullable;

import com.bfhd.account.vm.AccountIndexListViewModel;
import com.docker.common.common.model.CommonListOptions;
import com.docker.common.common.ui.base.NitCommonListFragment;
import com.docker.common.common.utils.rxbus.RxBus;
import com.docker.common.common.utils.rxbus.RxEvent;
import com.docker.nitsample.vm.EditListViewModel;

import io.reactivex.disposables.Disposable;

public class EditListFragment extends NitCommonListFragment<EditListViewModel> {

    CommonListOptions commonListReq = new CommonListOptions();

    public static EditListFragment newInstance() {
        return new EditListFragment();
    }

    private Disposable disposable;
    
    @Override
    public EditListViewModel getViewModel() {
        return ViewModelProviders.of(this, factory).get(EditListViewModel.class);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
//        disposable = RxBus.getDefault().toObservable(RxEvent.class).subscribe(rxEvent -> {
//            if (rxEvent.getT().equals("refresh_spa")) {
//                mViewModel.loadData();
//            }
//        });


    }

    @Override
    public CommonListOptions getArgument() {
        commonListReq.refreshState = 3;
        commonListReq.RvUi = 0;
        return commonListReq;
    }


    @Override
    public void onVisible() {
        super.onVisible();
        mViewModel.loadData();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (disposable != null) {
            disposable.dispose();
        }
    }

    @Override
    public void initImmersionBar() {

    }
}
