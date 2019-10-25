package com.docker.common.di;


import com.docker.common.common.ui.ViewDocumentActivity;
import com.docker.common.common.ui.base.NitCommonListInstanceFragment;
import com.docker.common.common.ui.container.NitCommonContainerActivity;
import com.docker.common.common.ui.container.NitCommonContainerFragment;
import com.docker.core.di.component.BaseActComponent;
import com.docker.core.di.scope.ActivityScope;

import dagger.Binds;
import dagger.Module;
import dagger.android.ContributesAndroidInjector;
import dagger.android.FragmentKey;
import dagger.multibindings.IntoMap;

@Module(subcomponents = {BaseActComponent.class})
public abstract class UIModule {

    @ActivityScope
    @ContributesAndroidInjector/*(modules = HomeActivityModule.class)*/
    abstract ViewDocumentActivity viewDocumentActivity();

    @ActivityScope
    @ContributesAndroidInjector/*(modules = HomeActivityModule.class)*/
    abstract NitCommonContainerActivity NitCommonContainerActivity();

    @ActivityScope
    @ContributesAndroidInjector/*(modules = HomeActivityModule.class)*/
    abstract NitCommonListInstanceFragment NitCommonListInstanceFragment();

    @ActivityScope
    @ContributesAndroidInjector/*(modules = HomeActivityModule.class)*/
    abstract NitCommonContainerFragment NitCommonContainerFragment();


}
