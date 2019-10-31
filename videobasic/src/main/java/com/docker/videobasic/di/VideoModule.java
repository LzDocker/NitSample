package com.docker.videobasic.di;


import dagger.Module;

@Module(includes = {UIModule.class, VmModule.class})
public class VideoModule {

//    @Singleton
//    @Provides
//    AccountService provideCircleService(Retrofit retrofit) {
//        return retrofit.create(AccountService.class);
//    }

}
