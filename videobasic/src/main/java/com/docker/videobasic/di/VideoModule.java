package com.docker.videobasic.di;


import com.docker.videobasic.api.VideoService;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import retrofit2.Retrofit;

@Module(includes = {UIModule.class, VmModule.class})
public class VideoModule {

    @Singleton
    @Provides
    VideoService providevideoService(Retrofit retrofit) {
        return retrofit.create(VideoService.class);
    }

}
