package com.docker.cirlev2.di;


import android.arch.lifecycle.ViewModel;

import com.docker.cirlev2.inter.frame.DefaultDetailIndexViewModel;
import com.docker.cirlev2.vm.CircleCommentListViewModel;
import com.docker.cirlev2.vm.CircleCreateViewModel;
import com.docker.cirlev2.vm.CircleDetailIndexViewModel;
import com.docker.cirlev2.vm.CircleDynamicDetailViewModel;
import com.docker.cirlev2.vm.CircleDynamicListViewModel;
import com.docker.cirlev2.vm.CircleEditTabViewModel;
import com.docker.cirlev2.vm.CircleIndexViewModel;
import com.docker.cirlev2.vm.CircleMinesViewModel;
import com.docker.cirlev2.vm.CirclePersionViewModel;
import com.docker.cirlev2.vm.CircleShoppingCarViewModel;
import com.docker.cirlev2.vm.CirlcleSelectViewModel;
import com.docker.cirlev2.vm.CreateListViewModel;
import com.docker.cirlev2.vm.PublishViewModel;
import com.docker.cirlev2.vm.SampleListViewModel;
import com.docker.cirlev2.vm.card.CircleDynamicDetailCardVm;
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

    @Binds
    @IntoMap
    @ViewModelKey(PublishViewModel.class)
    abstract ViewModel PublishViewModel(PublishViewModel model);

    @Binds
    @IntoMap
    @ViewModelKey(CirlcleSelectViewModel.class)
    abstract ViewModel CirlcleSelectViewModel(CirlcleSelectViewModel model);

    @Binds
    @IntoMap
    @ViewModelKey(CirclePersionViewModel.class)
    abstract ViewModel CirclePersionViewModel(CirclePersionViewModel model);

    @Binds
    @IntoMap
    @ViewModelKey(CircleDynamicDetailCardVm.class)
    abstract ViewModel CircleDynamicDetailCardVm(CircleDynamicDetailCardVm model);

    @Binds
    @IntoMap
    @ViewModelKey(CircleDynamicDetailViewModel.class)
    abstract ViewModel CircleDynamicDetailViewModel(CircleDynamicDetailViewModel model);

    @Binds
    @IntoMap
    @ViewModelKey(CircleCommentListViewModel.class)
    abstract ViewModel CircleCommentListViewModel(CircleCommentListViewModel model);

    @Binds
    @IntoMap
    @ViewModelKey(CircleEditTabViewModel.class)
    abstract ViewModel CircleEditTabViewModel(CircleEditTabViewModel model);

    @Binds
    @IntoMap
    @ViewModelKey(DefaultDetailIndexViewModel.class)
    abstract ViewModel DefaultDetailIndexViewModel(DefaultDetailIndexViewModel model);


    @Binds
    @IntoMap
    @ViewModelKey(CircleShoppingCarViewModel.class)
    abstract ViewModel CircleShoppingCarViewModel(CircleShoppingCarViewModel model);






}
