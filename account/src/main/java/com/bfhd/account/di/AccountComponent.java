package com.bfhd.account.di;


import javax.inject.Singleton;

import dagger.Component;
import dagger.android.AndroidInjectionModule;
import dagger.android.support.AndroidSupportInjectionModule;

@Singleton
@Component(modules = {
        AndroidInjectionModule.class,
        AndroidSupportInjectionModule.class,

        com.bfhd.account.di.ServiceModule.class,
        com.bfhd.account.di.UIModule.class,
        com.bfhd.account.di.VmModule.class,
})
public interface AccountComponent {


}
