package com.docker.nitsample.di;

import com.bfhd.account.di.AccountModule;
import com.bfhd.circle.di.CircleModule;
import com.bfhd.evaluate.di.EvaluateModule;
import com.docker.active.di.ActiveModule;
import com.docker.apps.intvite.di.InviteModule;
import com.docker.apps.point.di.PointModule;
import com.docker.cirlev2.di.CircleV2Module;
import com.docker.common.di.CommonModule;
import com.docker.core.base.BaseApp;
import com.docker.core.di.netmodule.GlobalConfigModule;
import com.docker.message.di.MessageModule;
import com.docker.module_im.di.ImModule;
import com.docker.order.di.OrderModule;
import com.docker.videobasic.di.VideoModule;
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
        GlobalConfigModule.class,

        /*app*/
        NitAppModule.class,

        /* account*/
        AccountModule.class,
        /*
         * circle*/
        CircleModule.class,

        /*common*/
        CommonModule.class,

        /*VideoModule
         * */
        VideoModule.class,

        /*message*/
        MessageModule.class,

        /*circle*/
        CircleV2Module.class,

//        ImModule.class,

        EvaluateModule.class,

        PointModule.class,

        ActiveModule.class,

        InviteModule.class,

        OrderModule.class,

})
public interface AppComponent {

    Gson gson();

    OkHttpClient okHttpClient();

    BaseApp baseApplication();

    void inject(BaseApp application);

}
