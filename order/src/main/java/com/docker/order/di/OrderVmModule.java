package com.docker.order.di;

import android.arch.lifecycle.ViewModel;
import com.docker.core.di.scope.ViewModelKey;
import com.docker.order.vm.OrderAddressViewModel;
import com.docker.order.vm.OrderCommentViewModel;
import com.docker.order.vm.OrderCommonViewModel;
import com.docker.order.vm.OrderMakerViewModel;

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

    @Binds
    @IntoMap
    @ViewModelKey(OrderCommentViewModel.class)
    abstract ViewModel OrderCommentViewModel(OrderCommentViewModel model);


}
