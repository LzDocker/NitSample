package com.docker.nitsample.di;

import com.docker.core.base.BaseApp;
import com.docker.core.di.AppModule;
import com.docker.core.di.BaseVmModule;
import com.docker.core.di.httpmodule.CommonServiceModule;
import com.docker.core.di.httpmodule.GlobalConfigModule;
import com.docker.core.di.httpmodule.HttpClientModule;
import com.docker.core.di.module.cachemodule.CacheModule;
import com.google.gson.Gson;

import javax.inject.Singleton;

import dagger.Component;
import dagger.android.AndroidInjectionModule;
import dagger.android.support.AndroidSupportInjectionModule;
import okhttp3.OkHttpClient;

/**
 * Created by zhangxindang on 2018/11/21.
 */
@Singleton
@Component(modules = {
        AndroidInjectionModule.class,
        AndroidSupportInjectionModule.class,
        AppModule.class,
        HttpClientModule.class,
        GlobalConfigModule.class,

        ServiceModule.class,
        CommonServiceModule.class,
        CacheModule.class,

        BaseVmModule.class,
        UIMoudle.class,
        ViewModelModule.class,


        /* account*/
        com.bfhd.account.di.ServiceModule.class,
        com.bfhd.account.di.UIModule.class,
        com.bfhd.account.di.VmModule.class,

        /*
         * circle*/
        com.bfhd.circle.di.ServiceModule.class,
        com.bfhd.circle.di.UIModule.class,
        com.bfhd.circle.di.VmModule.class,

        /*common*/
        com.docker.common.di.ServiceModule.class,
        com.docker.common.di.UIModule.class,
        com.docker.common.di.VmModule.class,

})
public interface AppComponent {

    Gson gson();

    OkHttpClient okHttpClient();

    BaseApp baseApplication();

    void inject(BaseApp application);

}
