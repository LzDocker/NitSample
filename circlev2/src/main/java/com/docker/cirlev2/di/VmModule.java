package com.docker.cirlev2.di;


import android.arch.lifecycle.ViewModel;

import com.docker.cirlev2.vm.CircleCreateViewModel;
import com.docker.cirlev2.vm.CircleDetailIndexViewModel;
import com.docker.cirlev2.vm.CircleDynamicListViewModel;
import com.docker.cirlev2.vm.CircleIndexViewModel;
import com.docker.cirlev2.vm.CircleMinesViewModel;
import com.docker.cirlev2.vm.CreateListViewModel;
import com.docker.cirlev2.vm.SampleListViewModel;
import com.docker.core.di.scope.ViewModelKey;

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
    @ViewModelKey(CircleIndexViewModel.class)
    abstract ViewModel CircleIndexViewModel(CircleIndexViewModel model);

    @Binds
    @IntoMap
    @ViewModelKey(CircleMinesViewModel.class)
    abstract ViewModel CircleMinesViewModel(CircleMinesViewModel model);

    @Binds
    @IntoMap
    @ViewModelKey(CreateListViewModel.class)
    abstract ViewModel CreateV2ListViewModel(CreateListViewModel model);

    @Binds
    @IntoMap
    @ViewModelKey(CircleCreateViewModel.class)
    abstract ViewModel CircleCreateViewModel(CircleCreateViewModel model);

    @Binds
    @IntoMap
    @ViewModelKey(CircleDetailIndexViewModel.class)
    abstract ViewModel CircleDetailIndexViewModel(CircleDetailIndexViewModel model);


    @Binds
    @IntoMap
    @ViewModelKey(CircleDynamicListViewModel.class)
    abstract ViewModel CircleDynamicListViewModel(CircleDynamicListViewModel model);



}
