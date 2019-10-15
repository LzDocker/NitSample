package com.docker.core.di;

import android.arch.lifecycle.ViewModelProvider;
import com.docker.core.util.NitViewModelFactory;
import dagger.Binds;
import dagger.Module;

@Module
public abstract class BaseVmModule {
    @Binds
    abstract ViewModelProvider.Factory bindViewModelFactory(NitViewModelFactory factory);

}
