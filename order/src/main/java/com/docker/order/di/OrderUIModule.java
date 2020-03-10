package com.docker.order.di;
import com.docker.core.di.component.BaseActComponent;
import com.docker.core.di.scope.ActivityScope;
import com.docker.order.ui.address.OrderAddAddressActivity;
import com.docker.order.ui.address.OrderAddressManagerActivity;
import com.docker.order.ui.comment.OrderCommentSuccessActivity;
import com.docker.order.ui.comment.OrderGoodsCommentActivity;
import com.docker.order.ui.index.OrderDetailActivity;
import com.docker.order.ui.index.OrderGoodsListActivity;
import com.docker.order.ui.index.OrderListActivity;
import com.docker.order.ui.index.OrderMakeActivity;
import com.docker.order.ui.index.OrderPayActivity;
import com.docker.order.ui.index.OrderPaySuccActivity;
import com.docker.order.ui.logistics.LogisticsDetialActivity;

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

    @ActivityScope
    @ContributesAndroidInjector/*(modules = HomeActivityModule.class)*/
    abstract OrderGoodsListActivity contributeOrderGoodsListActivityInjector();

    @ActivityScope
    @ContributesAndroidInjector/*(modules = HomeActivityModule.class)*/
    abstract OrderGoodsCommentActivity contributeOrderGoodsCommentActivityInjector();

    @ActivityScope
    @ContributesAndroidInjector/*(modules = HomeActivityModule.class)*/
    abstract OrderCommentSuccessActivity contributeOrderCommentSuccessActivityInjector();

    @ActivityScope
    @ContributesAndroidInjector/*(modules = HomeActivityModule.class)*/
    abstract OrderDetailActivity contributeOrderDetailActivityInjector();

    @ActivityScope
    @ContributesAndroidInjector/*(modules = HomeActivityModule.class)*/
    abstract OrderPaySuccActivity contributeOrderPaySuccActivityInjector();


}

