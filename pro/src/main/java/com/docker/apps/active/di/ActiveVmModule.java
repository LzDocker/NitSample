package com.docker.apps.active.di;

import android.arch.lifecycle.ViewModel;

import com.docker.apps.active.vm.ActivePersionListViewModel;
import com.docker.core.di.scope.ViewModelKey;

import dagger.Binds;
import dagger.Module;
import dagger.multibindings.IntoMap;

@Module
public abstract class ActiveVmModule {

    //    @Binds
//    @IntoMap
//    @ViewModelKey(SampleListViewModel.class)
//    abstract ViewModel messageSampleListViewModel(SampleListViewModel model);
    @Binds
    @IntoMap
    @ViewModelKey(ActivePersionListViewModel.class)
    abstract ViewModel ActivePersionListViewModel(ActivePersionListViewModel model);
//


}
