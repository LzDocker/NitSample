package com.docker.apps.order.di;

import android.arch.lifecycle.ViewModel;

import com.docker.apps.order.vm.OrderAddressViewModel;
import com.docker.apps.order.vm.OrderCommonViewModel;
import com.docker.apps.order.vm.OrderMakerViewModel;
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

    @Binds
    @IntoMap
    @ViewModelKey(OrderMakerViewModel.class)
    abstract ViewModel OrderMakerViewModel(OrderMakerViewModel model);

    @Binds
    @IntoMap
    @ViewModelKey(OrderCommonViewModel.class)
    abstract ViewModel OrderCommonViewModel(OrderCommonViewModel model);


}
