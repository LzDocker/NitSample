package com.bfhd.evaluate.di;

import android.arch.lifecycle.ViewModel;

import com.bfhd.evaluate.vm.EnStudyRxViewModel;
import com.bfhd.evaluate.vm.EnStudyViewModel;
import com.docker.core.di.scope.ViewModelKey;

import dagger.Binds;
import dagger.Module;
import dagger.multibindings.IntoMap;

@Module
public abstract class VmModule {


    @Binds
    @IntoMap
    @ViewModelKey(EnStudyViewModel.class)
    abstract ViewModel enStudyViewModel(EnStudyViewModel model);

    @Binds
    @IntoMap
    @ViewModelKey(EnStudyRxViewModel.class)
    abstract ViewModel enStudyRxViewModel(EnStudyRxViewModel model);


}
