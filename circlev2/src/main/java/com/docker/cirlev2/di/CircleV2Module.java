package com.docker.cirlev2.di;


import com.docker.cirlev2.api.CircleApiService;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import retrofit2.Retrofit;

@Module(includes = {UIModule.class, VmModule.class})
public class CircleV2Module {
    @Singleton
    @Provides
    CircleApiService provideCircleApiService(Retrofit retrofit) {
        return retrofit.create(CircleApiService.class);
    }

}
