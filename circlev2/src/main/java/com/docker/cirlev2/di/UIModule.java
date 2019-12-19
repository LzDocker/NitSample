package com.docker.cirlev2.di;


import com.docker.cirlev2.ui.CircleInfoActivity;
import com.docker.cirlev2.ui.common.CircleCoverActivity;
import com.docker.cirlev2.ui.common.CircleCovertFragment;
import com.docker.cirlev2.ui.common.CircleSourceUpFragment;
import com.docker.cirlev2.ui.create.CircleActiveFragment;
import com.docker.cirlev2.ui.create.CircleCompanyFragment;
import com.docker.cirlev2.ui.create.CircleConutryFragment;
import com.docker.cirlev2.ui.create.CircleCreateActivity;
import com.docker.cirlev2.ui.create.CircleCreateIndexActivity;
import com.docker.cirlev2.ui.detail.CircleDetailIndexActivity;
import com.docker.cirlev2.ui.detail.CircleEditMemberGroupActivity;
import com.docker.cirlev2.ui.detail.CircleGroupListActivity;
import com.docker.cirlev2.ui.detail.CircleInviteActivity;
import com.docker.cirlev2.ui.dynamicdetail.CircleDynamicDetailActivity;
import com.docker.cirlev2.ui.dynamicdetail.CircleMoreReplyListActivity;
import com.docker.cirlev2.ui.dynamicdetail.CircleReplyListActivity;
import com.docker.cirlev2.ui.dynamicdetail.Circlev2ReplayQuestionActivity;
import com.docker.cirlev2.ui.dynamicdetail.DynamicBotContentFragment;
import com.docker.cirlev2.ui.dynamicdetail.DynamicDetailFragment;
import com.docker.cirlev2.ui.dynamicdetail.DynamicH5Fragment;
import com.docker.cirlev2.ui.dynamicdetail.DynamicH5Fragmentv2;
import com.docker.cirlev2.ui.index.CircleIndexActivity;
import com.docker.cirlev2.ui.index.CircleIndexFragment;
import com.docker.cirlev2.ui.index.CircleSampleActivity;
import com.docker.cirlev2.ui.list.CircleDynamicCoutainerFragment;
import com.docker.cirlev2.ui.list.CircleDynamicListFragment;
import com.docker.cirlev2.ui.persion.CircleGroupPerssionActivity;
import com.docker.cirlev2.ui.persion.CircleModifyActivity;
import com.docker.cirlev2.ui.persion.CirclePersionSettingActivity;
import com.docker.cirlev2.ui.persion.CirclePersonDetailActivity;
import com.docker.cirlev2.ui.persion.CirclePersonListActivity;
import com.docker.cirlev2.ui.persion.CirclePersonPerssionActivity;
import com.docker.cirlev2.ui.publish.CirclePubActiveFragment;
import com.docker.cirlev2.ui.publish.CirclePubNewsFragment;
import com.docker.cirlev2.ui.publish.CirclePubRequestionFragment;
import com.docker.cirlev2.ui.publish.CirclePublishActivity;
import com.docker.cirlev2.ui.publish.select.CirclePerssionSelectActivity;
import com.docker.cirlev2.ui.publish.select.CircleShareGroupSelectActivity;
import com.docker.cirlev2.ui.publish.select.CircleShareSelectActivity;
import com.docker.core.di.component.BaseActComponent;
import com.docker.core.di.scope.ActivityScope;

import dagger.Module;
import dagger.android.ContributesAndroidInjector;

@Module(subcomponents = {BaseActComponent.class})
public abstract class UIModule {

    @ActivityScope
    @ContributesAndroidInjector/*(modules = HomeActivityModule.class)*/
    abstract CircleSampleActivity contributeCircleSampleActivityInjector();


    @ActivityScope
    @ContributesAndroidInjector/*(modules = HomeActivityModule.class)*/
    abstract CircleIndexFragment contributeCircleIndexFragmentInjector();

    @ActivityScope
    @ContributesAndroidInjector/*(modules = HomeActivityModule.class)*/
    abstract DynamicDetailFragment contributeDynamicDetailFragmentInjector();


    @ActivityScope
    @ContributesAndroidInjector/*(modules = HomeActivityModule.class)*/
    abstract CircleIndexActivity contributeCircleIndexActivityInjector();

    @ActivityScope
    @ContributesAndroidInjector/*(modules = HomeActivityModule.class)*/
    abstract CircleDynamicListFragment contributeCircleDynamicListFragmentInjector();


    @ActivityScope
    @ContributesAndroidInjector/*(modules = HomeActivityModule.class)*/
    abstract CircleCreateActivity contributeCircleCreateActivityInjector();


    @ActivityScope
    @ContributesAndroidInjector/*(modules = HomeActivityModule.class)*/
    abstract CircleCreateIndexActivity contributeCircleCreateIndexActivityInjector();

    @ActivityScope
    @ContributesAndroidInjector/*(modules = HomeActivityModule.class)*/
    abstract CircleDetailIndexActivity contributeCircleDetailIndexActivityInjector();


    @ActivityScope
    @ContributesAndroidInjector/*(modules = HomeActivityModule.class)*/
    abstract CircleDynamicCoutainerFragment contributeCircleDynamicCoutainerFragmentInjector();

    @ActivityScope
    @ContributesAndroidInjector/*(modules = HomeActivityModule.class)*/
    abstract CirclePublishActivity contributeCirclePublishActivityInjector();

    @ActivityScope
    @ContributesAndroidInjector/*(modules = HomeActivityModule.class)*/
    abstract CirclePubNewsFragment contributeCirclePubNewsFragmentInjector();


    @ActivityScope
    @ContributesAndroidInjector/*(modules = HomeActivityModule.class)*/
    abstract CircleCovertFragment contributeCircleCovertFragmentInjector();

    @ActivityScope
    @ContributesAndroidInjector/*(modules = HomeActivityModule.class)*/
    abstract CircleSourceUpFragment contributeCircleSourceUpFragmentInjector();


    @ActivityScope
    @ContributesAndroidInjector/*(modules = HomeActivityModule.class)*/
    abstract CircleCoverActivity contributeCircleCoverActivityInjector();


    @ActivityScope
    @ContributesAndroidInjector/*(modules = HomeActivityModule.class)*/
    abstract CirclePubRequestionFragment contributeCirclePubRequestionFragmentInjector();

    @ActivityScope
    @ContributesAndroidInjector/*(modules = HomeActivityModule.class)*/
    abstract CirclePubActiveFragment contributeCirclePubActiveFragmentInjector();

    @ActivityScope
    @ContributesAndroidInjector/*(modules = HomeActivityModule.class)*/
    abstract CirclePerssionSelectActivity contributeCirclePerssionSelectActivityInjector();

    @ActivityScope
    @ContributesAndroidInjector/*(modules = HomeActivityModule.class)*/
    abstract CircleShareSelectActivity contributeCircleShareSelectActivityInjector();

    @ActivityScope
    @ContributesAndroidInjector/*(modules = HomeActivityModule.class)*/
    abstract CircleShareGroupSelectActivity contributeCircleShareGroupSelectActivityInjector();

    @ActivityScope
    @ContributesAndroidInjector/*(modules = HomeActivityModule.class)*/
    abstract CircleInviteActivity contributeCircleInviteActivityInjector();

    @ActivityScope
    @ContributesAndroidInjector/*(modules = HomeActivityModule.class)*/
    abstract CircleGroupListActivity contributeCircleGroupListActivityInjector();

    @ActivityScope
    @ContributesAndroidInjector/*(modules = HomeActivityModule.class)*/
    abstract CircleEditMemberGroupActivity contributeCircleEditMemberGroupActivityInjector();

    @ActivityScope
    @ContributesAndroidInjector/*(modules = HomeActivityModule.class)*/
    abstract CircleActiveFragment contributeCircleCircleActiveFragmentInjector();

    @ActivityScope
    @ContributesAndroidInjector/*(modules = HomeActivityModule.class)*/
    abstract CircleCompanyFragment contributeCircleCompanyFragmentInjector();

    @ActivityScope
    @ContributesAndroidInjector/*(modules = HomeActivityModule.class)*/
    abstract CircleConutryFragment contributeCircleConutryFragmentInjector();

    @ActivityScope
    @ContributesAndroidInjector/*(modules = HomeActivityModule.class)*/
    abstract CirclePersonListActivity contributeCirclePersonListActivityInjector();

    @ActivityScope
    @ContributesAndroidInjector/*(modules = HomeActivityModule.class)*/
    abstract CircleModifyActivity contributeCircleModifyActivityInjector();

    @ActivityScope
    @ContributesAndroidInjector/*(modules = HomeActivityModule.class)*/
    abstract CirclePersionSettingActivity contributeCirclePersionSettingActivityInjector();

    @ActivityScope
    @ContributesAndroidInjector/*(modules = HomeActivityModule.class)*/
    abstract CirclePersonDetailActivity contributeCirclePersonDetailActivityInjector();

    @ActivityScope
    @ContributesAndroidInjector/*(modules = HomeActivityModule.class)*/
    abstract CirclePersonPerssionActivity contributeCirclePersonPerssionActivityInjector();

    @ActivityScope
    @ContributesAndroidInjector/*(modules = HomeActivityModule.class)*/
    abstract CircleGroupPerssionActivity contributeCircleGroupPerssionActivityInjector();

    @ActivityScope
    @ContributesAndroidInjector/*(modules = HomeActivityModule.class)*/
    abstract CircleDynamicDetailActivity contributeCircleDynamicDetailActivityInjector();

    @ActivityScope
    @ContributesAndroidInjector/*(modules = HomeActivityModule.class)*/
    abstract DynamicH5Fragment contributeDynamicH5FragmentInjector();

    @ActivityScope
    @ContributesAndroidInjector/*(modules = HomeActivityModule.class)*/
    abstract DynamicH5Fragmentv2 contributeDynamicH5Fragmentv2Injector();

    @ActivityScope
    @ContributesAndroidInjector/*(modules = HomeActivityModule.class)*/
    abstract DynamicBotContentFragment contributeDynamicBotContentFragmentInjector();

    @ActivityScope
    @ContributesAndroidInjector/*(modules = HomeActivityModule.class)*/
    abstract CircleReplyListActivity contributeCircleReplyListActivityInjector();


    @ActivityScope
    @ContributesAndroidInjector/*(modules = HomeActivityModule.class)*/
    abstract CircleMoreReplyListActivity contributeCircleMoreReplyListActivityInjector();

    @ActivityScope
    @ContributesAndroidInjector/*(modules = HomeActivityModule.class)*/
    abstract Circlev2ReplayQuestionActivity contributeCirclev2ReplayQuestionActivityInjector();



    @ActivityScope
    @ContributesAndroidInjector/*(modules = HomeActivityModule.class)*/
    abstract CircleInfoActivity contributeCircleInfoActivityInjector();
}

