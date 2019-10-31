package com.bfhd.circle.di;

import com.bfhd.circle.api.CircleService;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import retrofit2.Retrofit;

@Module(includes = {UIModule.class, VmModule.class})
public class CircleModule {

    @Singleton
    @Provides
    CircleService provideCircleService(Retrofit retrofit) {
        return retrofit.create(CircleService.class);
    }
}
