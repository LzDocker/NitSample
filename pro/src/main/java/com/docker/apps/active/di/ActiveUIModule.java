package com.docker.apps.active.di;


import com.docker.apps.active.ui.detail.ActiveDetailFragment;
import com.docker.apps.active.ui.detail.ActiveResultActivity;
import com.docker.apps.active.ui.detail.ActiveSuccActivity;
import com.docker.apps.active.ui.index.ActiveContainerFragment;
import com.docker.apps.active.ui.index.ActiveIndexActivity;
import com.docker.core.di.component.BaseActComponent;

import dagger.Module;
import dagger.android.ContributesAndroidInjector;

@Module(subcomponents = {BaseActComponent.class})
public abstract class ActiveUIModule {

//    @ActivityScope
//    @ContributesAndroidInjector/*(modules = HomeActivityModule.class)*/
//    abstract PointSortActivity contributePointSortActivityInjector();


    @ContributesAndroidInjector/*(modules = HomeActivityModule.class)*/
    abstract ActiveContainerFragment contributeActiveContainerFragmentInjector();


    @ContributesAndroidInjector/*(modules = HomeActivityModule.class)*/
    abstract ActiveIndexActivity contributeActiveIndexActivityInjector();

    @ContributesAndroidInjector/*(modules = HomeActivityModule.class)*/
    abstract ActiveDetailFragment contributeActiveDetailFragmentInjector();

    @ContributesAndroidInjector/*(modules = HomeActivityModule.class)*/
    abstract ActiveSuccActivity contributeActiveSuccActivityInjector();

    @ContributesAndroidInjector/*(modules = HomeActivityModule.class)*/
    abstract ActiveResultActivity contributeActiveResultActivityInjector();
}

