package com.docker.apps.point.di;


import android.arch.lifecycle.ViewModel;

import com.docker.apps.point.vm.PonitSortVm;
import com.docker.core.di.scope.ViewModelKey;
import dagger.Binds;
import dagger.Module;
import dagger.multibindings.IntoMap;

@Module
public abstract class PointVmModule {

    //    @Binds
//    @IntoMap
//    @ViewModelKey(SampleListViewModel.class)
//    abstract ViewModel messageSampleListViewModel(SampleListViewModel model);
//
    @Binds
    @IntoMap
    @ViewModelKey(PonitSortVm.class)
    abstract ViewModel PonitSortVm(PonitSortVm model);

}
