package com.docker.apps.order.di;

import android.arch.lifecycle.ViewModel;

import com.docker.apps.order.vm.OrderAddressViewModel;
import com.docker.core.di.scope.ViewModelKey;

import dagger.Binds;
import dagger.Module;
import dagger.multibindings.IntoMap;

@Module
public abstract class OrderVmModule {

    //    @Binds
//    @IntoMap
//    @ViewModelKey(SampleListViewModel.class)
//    abstract ViewModel messageSampleListViewModel(SampleListViewModel model);
//
    @Binds
    @IntoMap
    @ViewModelKey(OrderAddressViewModel.class)
    abstract ViewModel OrderAddressViewModel(OrderAddressViewModel model);


}
