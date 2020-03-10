package com.docker.common.di;


import com.docker.common.common.ui.CommonAddressListv2Activity;
import com.docker.common.common.ui.RxDemoActivity;
import com.docker.common.common.ui.ViewDocumentActivity;
import com.docker.common.common.ui.base.NitCommonListInstanceFragment;
import com.docker.common.common.ui.container.NitCommonCardFragment;
import com.docker.common.common.ui.container.NitCommonCardNoRefreshFragment;
import com.docker.common.common.ui.container.NitCommonContainerActivity;
import com.docker.common.common.ui.container.NitCommonContainerFragment;
import com.docker.common.common.ui.container.NitCommonContainerFragmentV2;
import com.docker.common.common.ui.container.NitCommonContainerNoRefreshFragmentV2;
import com.docker.common.common.ui.container.NitCommonRichContainerFragment;
import com.docker.common.common.ui.container.NitTabContainerFragment;
import com.docker.common.common.ui.indexlist.ListVpIndexActivity;
import com.docker.common.common.ui.location.MapLocationActivity;
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

    @ActivityScope
    @ContributesAndroidInjector/*(modules = HomeActivityModule.class)*/
    abstract NitCommonCardNoRefreshFragment NitCommonCardNoRefreshFragment();

    @ActivityScope
    @ContributesAndroidInjector/*(modules = HomeActivityModule.class)*/
    abstract NitCommonContainerNoRefreshFragmentV2 NitCommonContainerNoRefreshFragmentV2();

    @ActivityScope
    @ContributesAndroidInjector/*(modules = HomeActivityModule.class)*/
    abstract CommonAddressListv2Activity CommonAddressListv2Activity();

    @ActivityScope
    @ContributesAndroidInjector/*(modules = HomeActivityModule.class)*/
    abstract MapLocationActivity MapLocationActivity();

    @ActivityScope
    @ContributesAndroidInjector/*(modules = HomeActivityModule.class)*/
    abstract NitCommonRichContainerFragment NitCommonRichContainerFragment();

    @ActivityScope
    @ContributesAndroidInjector/*(modules = HomeActivityModule.class)*/
    abstract ListVpIndexActivity ListVpIndexActivity();



}
