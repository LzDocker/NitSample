package com.docker.apps.order.di;

import com.docker.apps.order.api.OrderService;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import retrofit2.Retrofit;

@Module(includes = {OrderUIModule.class, OrderVmModule.class})
public class OrderModule {
    @Singleton
    @Provides
    OrderService provideOrderService(Retrofit retrofit) {
        return retrofit.create(OrderService.class);
    }

}
