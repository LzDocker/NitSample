package com.docker.cirlev2.di;


import android.arch.lifecycle.ViewModel;

import com.docker.cirlev2.ui.detail.index.temp.defaults.DefaultDetailIndexViewModel;
import com.docker.cirlev2.vm.CircleCommentListViewModel;
import com.docker.cirlev2.vm.CircleCreateViewModel;
import com.docker.cirlev2.vm.CircleDetailIndexViewModel;
import com.docker.cirlev2.vm.CircleDynamicDetailViewModel;
import com.docker.cirlev2.vm.CircleDynamicListViewModel;
import com.docker.cirlev2.vm.CircleEditTabViewModel;
import com.docker.cirlev2.vm.CircleIndexViewModel;
import com.docker.cirlev2.vm.CircleMinesViewModel;
import com.docker.cirlev2.vm.CirclePersionViewModel;
import com.docker.cirlev2.vm.CirclePersonInfoViewModel;
import com.docker.cirlev2.vm.CircleShoppingViewModel;
import com.docker.cirlev2.vm.CircleShoppingViewModelv2;
import com.docker.cirlev2.vm.CirlcleSelectViewModel;
import com.docker.cirlev2.vm.CreateListViewModel;
import com.docker.cirlev2.vm.MutipartCircleViewModel;
import com.docker.cirlev2.vm.PublishViewModel;
import com.docker.cirlev2.vm.SampleListViewModel;
import com.docker.cirlev2.vm.card.CircleDynamicDetailCardVm;
import com.docker.cirlev2.vm.card.CirclePersonInfoHeadCardVm;
import com.docker.cirlev2.vm.card.ShoppingCartViewModel;
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
    @ViewModelKey(CircleShoppingViewModel.class)
    abstract ViewModel CircleShoppingCarViewModel(CircleShoppingViewModel model);

    @Binds
    @IntoMap
    @ViewModelKey(CirclePersonInfoViewModel.class)
    abstract ViewModel CirclePersonInfoViewModel(CirclePersonInfoViewModel model);

    @Binds
    @IntoMap
    @ViewModelKey(CirclePersonInfoHeadCardVm.class)
    abstract ViewModel CirclePersonInfoHeadCardVm(CirclePersonInfoHeadCardVm model);

    @Binds
    @IntoMap
    @ViewModelKey(ShoppingCartViewModel.class)
    abstract ViewModel ShoppingCartViewModel(ShoppingCartViewModel model);

    @Binds
    @IntoMap
    @ViewModelKey(CircleShoppingViewModelv2.class)
    abstract ViewModel CircleShoppingViewModelv2(CircleShoppingViewModelv2 model);

    @Binds
    @IntoMap
    @ViewModelKey(MutipartCircleViewModel.class)
    abstract ViewModel MutipartCircleViewModel(MutipartCircleViewModel model);




}
