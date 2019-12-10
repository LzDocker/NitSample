package com.docker.common.di;


import com.docker.common.common.ui.RxDemoActivity;
import com.docker.common.common.ui.ViewDocumentActivity;
import com.docker.common.common.ui.base.NitCommonListInstanceFragment;
import com.docker.common.common.ui.container.NitCommonCardFragment;
import com.docker.common.common.ui.container.NitCommonContainerActivity;
import com.docker.common.common.ui.container.NitCommonContainerFragment;
import com.docker.common.common.ui.container.NitCommonContainerFragmentV2;
import com.docker.common.common.ui.container.NitTabContainerFragment;
import com.docker.core.di.component.BaseActComponent;
import com.docker.core.di.scope.ActivityScope;

import dagger.Module;
import dagger.android.ContributesAndroidInjector;

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

    @ActivityScope
    @ContributesAndroidInjector/*(modules = HomeActivityModule.class)*/
    abstract RxDemoActivity rxDemoActivity();

    @ActivityScope
    @ContributesAndroidInjector/*(modules = HomeActivityModule.class)*/
    abstract NitTabContainerFragment NitTabContainerFragment();

    @ActivityScope
    @ContributesAndroidInjector/*(modules = HomeActivityModule.class)*/
    abstract NitCommonCardFragment NitCommonCardFragment();

    @ActivityScope
    @ContributesAndroidInjector/*(modules = HomeActivityModule.class)*/
    abstract NitCommonContainerFragmentV2 NitCommonContainerFragmentV2();

}
