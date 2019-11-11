package com.docker.module_im.di;

import com.docker.core.di.component.BaseActComponent;
import com.docker.core.di.scope.ActivityScope;
import com.docker.module_im.init.ImLocationActivity;
import com.docker.module_im.init.ImLocationOpenActivity;

import dagger.Module;
import dagger.android.ContributesAndroidInjector;

@Module(subcomponents = {BaseActComponent.class})
public abstract class ImModule {


    @ActivityScope
    @ContributesAndroidInjector/*(modules = HomeActivityModule.class)*/
    abstract ImLocationActivity contributeImLocationActivityInjector();


    @ActivityScope
    @ContributesAndroidInjector/*(modules = HomeActivityModule.class)*/
    abstract ImLocationOpenActivity contributeImLocationOpenActivityInjector();
}
