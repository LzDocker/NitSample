package com.docker.message.di;


import android.arch.lifecycle.ViewModel;

import com.docker.core.di.scope.ViewModelKey;
import com.docker.message.vm.MessageViewModel;
import com.docker.message.vm.SampleListViewModel;

import dagger.Binds;
import dagger.Module;
import dagger.multibindings.IntoMap;

@Module
public abstract class VmModule {

    @Binds
    @IntoMap
    @ViewModelKey(SampleListViewModel.class)
    abstract ViewModel messageSampleListViewModel(SampleListViewModel model);

    @Binds
    @IntoMap
    @ViewModelKey(MessageViewModel.class)
    abstract ViewModel MessageViewModel(MessageViewModel model);


}
