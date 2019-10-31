package com.docker.nitsample.di;

import com.docker.core.di.netmodule.CommonService;
import com.docker.nitsample.api.SampleService;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import retrofit2.Retrofit;

/**
 * Created by zhangxindang on 2018/11/21.
 */
@Module(includes = {UIMoudle.class, ViewModelModule.class})
public class NitAppModule {


    @Singleton
    @Provides
    CommonService provideCommonService(Retrofit retrofit) {
        return retrofit.create(CommonService.class);
    }

    @Singleton
    @Provides
    SampleService provideSampleService(Retrofit retrofit) {
        return retrofit.create(SampleService.class);
    }
}
