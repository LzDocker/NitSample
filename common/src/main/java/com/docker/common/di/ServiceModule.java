package com.docker.common.di;

import com.docker.common.api.OpenService;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import retrofit2.Retrofit;

@Module
public class ServiceModule {

    @Singleton
    @Provides
    OpenService provideOpenService(Retrofit retrofit) {
        return retrofit.create(OpenService.class);
    }

}
