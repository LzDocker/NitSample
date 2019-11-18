package com.bfhd.evaluate.di;

import android.arch.lifecycle.ViewModel;

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


}
