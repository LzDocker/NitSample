package com.docker.core.di.netmodule;

import android.text.TextUtils;

import com.docker.core.di.AppModule;
import com.docker.core.di.BaseVmModule;
import com.docker.core.di.module.cachemodule.CacheModule;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import okhttp3.HttpUrl;
import okhttp3.Interceptor;

/**
 * Created by zhangxindang on 2018/11/21.
 */
@Module(includes = {AppModule.class,
        HttpClientModule.class,
        BaseVmModule.class,
        CommonServiceModule.class,
        CacheModule.class})
public class GlobalConfigModule {

    private HttpUrl mApiUrl;
    private List<Interceptor> mInterceptors;
    private File mCacheFile;
    private HttpRequestHandler mHandler;
    private ConverterHoledr converterHoled;

    private GlobalConfigModule(Buidler buidler) {
        this.mApiUrl = buidler.apiUrl;
        this.mInterceptors = buidler.interceptors;
        this.mHandler = buidler.handler;
        this.mCacheFile = buidler.cacheFile;
        this.converterHoled = buidler.converterHoled;
    }

    public static Buidler buidler() {
        return new Buidler();
    }

    @Singleton
    @Provides
    List<Interceptor> provideInterceptors() {
        return mInterceptors;
    }

    @Singleton
    @Provides
    HttpUrl provideBaseUrl() {
        return mApiUrl;
    }


    @Singleton
    @Provides
    HttpRequestHandler provideHttpRequestHandler() {
        return mHandler == null ? HttpRequestHandler.EMPTY : mHandler;//打印请求信息
    }

    @Singleton
    @Provides
    public ConverterHoledr provideConverterHoledr() {
        return converterHoled;
    }

    public static final class Buidler {
        private HttpUrl apiUrl;
        private List<Interceptor> interceptors = new ArrayList<>();
        private File cacheFile;
        private HttpRequestHandler handler;
        private ConverterHoledr converterHoled;

        private Buidler() {

        }

        public Buidler baseurl(String baseurl) {
            if (TextUtils.isEmpty(baseurl)) {
                throw new IllegalArgumentException("baseurl can not be empty");
            }
            this.apiUrl = HttpUrl.parse(baseurl);
            return this;
        }

        public Buidler globeHttpHandler(HttpRequestHandler handler) {// handle the http response before displaying it to users
            this.handler = handler;
            return this;
        }

        public Buidler addInterceptor(Interceptor interceptor) {
            this.interceptors.add(interceptor);
            return this;
        }

        public Buidler cacheFile(File cacheFile) {
            this.cacheFile = cacheFile;
            return this;
        }

        public Buidler converterHoled(ConverterHoledr converterHoled) {
            this.converterHoled = converterHoled;
            return this;
        }

        public GlobalConfigModule build() {
            return new GlobalConfigModule(this);
        }
    }


}
