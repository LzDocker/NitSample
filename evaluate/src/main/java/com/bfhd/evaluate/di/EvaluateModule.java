package com.bfhd.evaluate.di;

import com.bfhd.evaluate.api.EnStudyService;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import retrofit2.Retrofit;

@Module(includes = {UIModule.class, VmModule.class})
public class EvaluateModule {

    @Singleton
    @Provides
    EnStudyService provideEnStudyService(Retrofit retrofit) {
        return retrofit.create(EnStudyService.class);
    }

}
