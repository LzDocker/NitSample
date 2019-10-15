package com.bfhd.circle.di;

import com.bfhd.circle.api.CircleService;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import retrofit2.Retrofit;

@Module
public class ServiceModule {

    @Singleton
    @Provides
    CircleService provideCircleService(Retrofit retrofit) {
        return retrofit.create(CircleService.class);
    }
}
