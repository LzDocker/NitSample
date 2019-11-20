package com.bfhd.evaluate.di;


import com.bfhd.evaluate.ui.AudioLessonActivity;
import com.bfhd.evaluate.ui.AudioLessonDetailActivity;
import com.bfhd.evaluate.ui.AudioLessonDetailFragment;
import com.bfhd.evaluate.ui.AudioLessonFragment;
import com.bfhd.evaluate.ui.AudioLessonGenDuFragment;
import com.bfhd.evaluate.ui.AudioLessonQuanWenFragment;
import com.docker.core.di.component.BaseActComponent;
import com.docker.core.di.scope.ActivityScope;

import dagger.Module;
import dagger.android.ContributesAndroidInjector;

@Module(subcomponents = {BaseActComponent.class})
public abstract class UIModule {
    /**
     * 新概念目录册列表页面
     *
     * @return
     */
    @ActivityScope
    @ContributesAndroidInjector//(modules = LoginModule.class)
    abstract AudioLessonActivity audioLessonActivity();

    /**
     * 新概念目录册列表页面
     *
     * @return
     */
    @ActivityScope
    @ContributesAndroidInjector//(modules = LoginModule.class)
    abstract AudioLessonFragment audioLessonFragment();

    /**
     * 新概念详情: 跟读 /全文/ 内容 页面
     *
     * @return
     */
    @ActivityScope
    @ContributesAndroidInjector//(modules = LoginModule.class)
    abstract AudioLessonDetailActivity audioLessonDetailActivity();

    /**
     * 新概念详情 内容页面
     *
     * @return
     */
    @ActivityScope
    @ContributesAndroidInjector//(modules = LoginModule.class)
    abstract AudioLessonDetailFragment audioLessonDetailFragment();

    /**
     * 新概念详情 全文页面
     *
     * @return
     */
    @ActivityScope
    @ContributesAndroidInjector//(modules = LoginModule.class)
    abstract AudioLessonQuanWenFragment audioLessonQuanWenFragment();

    /**
     * 新概念详情 跟读页面
     *
     * @return
     */
    @ActivityScope
    @ContributesAndroidInjector//(modules = LoginModule.class)
    abstract AudioLessonGenDuFragment audioLessonGenDuFragment();


}

