package com.docker.apps.order.di;


import com.docker.apps.order.ui.address.OrderAddAddressActivity;
import com.docker.apps.order.ui.address.OrderAddressManagerActivity;
import com.docker.apps.order.ui.index.OrderListActivity;
import com.docker.apps.order.ui.index.OrderMakeActivity;
import com.docker.apps.order.ui.index.OrderPayActivity;
import com.docker.apps.order.ui.logistics.LogisticsDetialActivity;
import com.docker.core.di.component.BaseActComponent;
import com.docker.core.di.scope.ActivityScope;

import dagger.Module;
import dagger.android.ContributesAndroidInjector;

@Module(subcomponents = {BaseActComponent.class})
public abstract class OrderUIModule {

//    @ActivityScope
//    @ContributesAndroidInjector/*(modules = HomeActivityModule.class)*/
//    abstract PointSortActivity contributePointSortActivityInjector();
//

    @ActivityScope
    @ContributesAndroidInjector/*(modules = HomeActivityModule.class)*/
    abstract OrderAddressManagerActivity contributeOrderAddressManagerActivityInjector();

    @ActivityScope
    @ContributesAndroidInjector/*(modules = HomeActivityModule.class)*/
    abstract OrderAddAddressActivity contributeOrderAddAddressActivityInjector();

    @ActivityScope
    @ContributesAndroidInjector/*(modules = HomeActivityModule.class)*/
    abstract OrderMakeActivity contributeOrderMakeActivityInjector();

    @ActivityScope
    @ContributesAndroidInjector/*(modules = HomeActivityModule.class)*/
    abstract OrderPayActivity contributeOrderPayActivityInjector();

    @ActivityScope
    @ContributesAndroidInjector/*(modules = HomeActivityModule.class)*/
    abstract OrderListActivity contributeOrderListActivityInjector();

    @ActivityScope
    @ContributesAndroidInjector/*(modules = HomeActivityModule.class)*/
    abstract LogisticsDetialActivity contributeLogisticsDetialActivityInjector();


}

