package com.bfhd.circle.di;

import com.bfhd.circle.ui.active.CircleCommentFragment;
import com.bfhd.circle.ui.active.CircleCommentListActivity;
import com.bfhd.circle.ui.active.CircleRecommendFragment;
import com.bfhd.circle.ui.active.CircleReplyListActivity;
import com.bfhd.circle.ui.circle.CircleAddSurfActivity;
import com.bfhd.circle.ui.circle.CircleCoverActivity;
import com.bfhd.circle.ui.circle.CircleCovertFragment;
import com.bfhd.circle.ui.circle.CircleCreateActivity;
import com.bfhd.circle.ui.circle.CircleCreateHwjActivity;
import com.bfhd.circle.ui.circle.CircleDynamicDetailActivity;
import com.bfhd.circle.ui.circle.CircleEditMemberGroupActivity;
import com.bfhd.circle.ui.circle.CircleFriendShareActivity;
import com.bfhd.circle.ui.circle.CircleGroupPerssionActivity;
import com.bfhd.circle.ui.circle.CircleHome2Activity;
import com.bfhd.circle.ui.circle.CircleHomeActivity;
import com.bfhd.circle.ui.circle.CircleModifyActivity;
import com.bfhd.circle.ui.circle.CirclePersionSettingActivity;
import com.bfhd.circle.ui.circle.CirclePersonDetailWjActivity;
import com.bfhd.circle.ui.circle.CirclePersonPerssionActivity;
import com.bfhd.circle.ui.circle.CirclePerssionSelectActivity;
import com.bfhd.circle.ui.circle.CirclePublishActivity;
import com.bfhd.circle.ui.circle.CircleCreateListActivity;
import com.bfhd.circle.ui.circle.CircleDetailActivity;
import com.bfhd.circle.ui.circle.CircleDetailFragment;
import com.bfhd.circle.ui.circle.CircleEditClassActivity;
import com.bfhd.circle.ui.circle.CircleMemberGroupListActivity;
import com.bfhd.circle.ui.circle.CircleInfoActivity;
import com.bfhd.circle.ui.circle.CircleInfoListFragment;
import com.bfhd.circle.ui.circle.CircleListActivity;
import com.bfhd.circle.ui.circle.CircleListFragment;
import com.bfhd.circle.ui.circle.CirclePersonDetailActivity;
import com.bfhd.circle.ui.circle.CirclePersonListActivity;
import com.bfhd.circle.ui.circle.CircleRecommandPersionListFragment;
import com.bfhd.circle.ui.circle.CircleReplayQuestionActivity;
import com.bfhd.circle.ui.circle.CircleSearchActivity;
import com.bfhd.circle.ui.circle.CircleShareGroupSelectActivity;
import com.bfhd.circle.ui.circle.CircleShareSelectActivity;
import com.bfhd.circle.ui.circle.CircleTypeListActivity;
import com.bfhd.circle.ui.circle.CircleperssionActivity;
import com.bfhd.circle.ui.circle.GridImageFragment;
import com.bfhd.circle.ui.circle.circlecreate.CircleActiveFragment;
import com.bfhd.circle.ui.circle.circlecreate.CircleCompanyFragment;
import com.bfhd.circle.ui.circle.circlecreate.CircleConutryFragment;
import com.bfhd.circle.ui.circle.circlecreate.CircleHomeFragment;
import com.bfhd.circle.ui.circle.circlepublish.CirclePubActiveFragment;
import com.bfhd.circle.ui.circle.circlepublish.CirclePubNewsFragment;
import com.bfhd.circle.ui.circle.circlepublish.CirclePubRequestionFragment;
import com.bfhd.circle.ui.circle.dynamicdetail.CirclH5DetailFragment;
import com.bfhd.circle.ui.circle.dynamicdetail.CircleDynamicDetailFragment;
import com.bfhd.circle.ui.common.CircleSourceUpFragment;
import com.bfhd.circle.ui.common.CommonH5Activity;
import com.bfhd.circle.ui.safe.CircleFragment;
import com.bfhd.circle.ui.safe.CommonWebFragment;
import com.bfhd.circle.ui.safe.DynamicFragment;
import com.bfhd.circle.ui.safe.MineAttentionFragment;
import com.docker.core.di.component.BaseActComponent;
import com.docker.core.di.scope.ActivityScope;

import dagger.Module;
import dagger.android.ContributesAndroidInjector;

@Module(subcomponents = {BaseActComponent.class})
public abstract class UIModule {

    //    @ActivityScope
//    @ContributesAndroidInjector/*(modules = HomeActivityModule.class)*/
//    abstract MainActivity contributeMainActivitytInjector();

//    @ActivityScope
//    @ContributesAndroidInjector/*(modules = HomeActivityModule.class)*/
//    abstract SafeNewsFragment contributeCircleListFragmenttInjector();

    @ActivityScope
    @ContributesAndroidInjector/*(modules = HomeActivityModule.class)*/
    abstract MineAttentionFragment contributeCircleInfoFragmentInjector();

//    @ActivityScope
//    @ContributesAndroidInjector/*(modules = HomeActivityModule.class)*/
//    abstract DangrousPushFragment contributeCirclePushListInjector();

    @ActivityScope
    @ContributesAndroidInjector/*(modules = HomeActivityModule.class)*/
    abstract CircleFragment contributeCircleFragmentInjector();

    @ActivityScope
    @ContributesAndroidInjector/*(modules = HomeActivityModule.class)*/
    abstract CircleInfoListFragment contributeCircleInfoListFragmentInjector();

    @ActivityScope
    @ContributesAndroidInjector/*(modules = HomeActivityModule.class)*/
    abstract CircleDetailActivity contributeCircleDetailActivityInjector();

    @ActivityScope
    @ContributesAndroidInjector/*(modules = HomeActivityModule.class)*/
    abstract CirclePublishActivity contributeCirclePublishActivityInjector();

    @ActivityScope
    @ContributesAndroidInjector/*(modules = HomeActivityModule.class)*/
    abstract CircleCreateActivity contributeCircleCreateActivityInjector();

    @ActivityScope
    @ContributesAndroidInjector/*(modules = HomeActivityModule.class)*/
    abstract CircleRecommendFragment contributeCircleRecommendFragmentInjector();

    @ActivityScope
    @ContributesAndroidInjector/*(modules = HomeActivityModule.class)*/
    abstract CircleCommentFragment contributeCircleCommentFragmentInjector();

//    @ActivityScope
//    @ContributesAndroidInjector/*(modules = HomeActivityModule.class)*/
//    abstract MutipartItemsFragment contributeMutipartItemsFragmentInjector();

    @ActivityScope
    @ContributesAndroidInjector/*(modules = HomeActivityModule.class)*/
    abstract GridImageFragment contributeGridImageFragmentInjector();

    @ActivityScope
    @ContributesAndroidInjector/*(modules = HomeActivityModule.class)*/
    abstract CircleDetailFragment contributeCircleDetailFragmentInjector();

    @ActivityScope
    @ContributesAndroidInjector/*(modules = HomeActivityModule.class)*/
    abstract CircleEditClassActivity contributeCircleEditClassActivityInjector();

    @ActivityScope
    @ContributesAndroidInjector/*(modules = HomeActivityModule.class)*/
    abstract CircleListActivity contributeCircleListActivityInjector();

    @ActivityScope
    @ContributesAndroidInjector/*(modules = HomeActivityModule.class)*/
    abstract CircleListFragment contributeCircleListFragmentInjector();

    @ActivityScope
    @ContributesAndroidInjector/*(modules = HomeActivityModule.class)*/
    abstract CirclePersonListActivity contributeCirclePersonListActivityInjector();

    @ActivityScope
    @ContributesAndroidInjector/*(modules = HomeActivityModule.class)*/
    abstract CircleMemberGroupListActivity contributeCircleEditGroupActivityyInjector();

    @ActivityScope
    @ContributesAndroidInjector/*(modules = HomeActivityModule.class)*/
    abstract CircleTypeListActivity contributeCircleTypeListActivityyInjector();

    @ActivityScope
    @ContributesAndroidInjector/*(modules = HomeActivityModule.class)*/
    abstract CircleInfoActivity contributeCircleInfoActivityInjector();

    @ActivityScope
    @ContributesAndroidInjector/*(modules = HomeActivityModule.class)*/
    abstract CircleSearchActivity contributeCircleSearchActivityInjector();

    @ActivityScope
    @ContributesAndroidInjector/*(modules = HomeActivityModule.class)*/
    abstract CirclePubNewsFragment contributeCirclePubNewsFragmentInjector();

    @ActivityScope
    @ContributesAndroidInjector/*(modules = HomeActivityModule.class)*/
    abstract CircleCoverActivity contributeCircleCoverActivityInjector();

    @ActivityScope
    @ContributesAndroidInjector/*(modules = HomeActivityModule.class)*/
    abstract CircleCovertFragment contributeCircleCovertFragmentInjector();

    @ActivityScope
    @ContributesAndroidInjector/*(modules = HomeActivityModule.class)*/
    abstract CirclePersonDetailActivity contributeCirclePersonDetailActivityInjector();

    @ActivityScope
    @ContributesAndroidInjector/*(modules = HomeActivityModule.class)*/
    abstract DynamicFragment contributeDynamicFragmentInjector();

    @ActivityScope
    @ContributesAndroidInjector/*(modules = HomeActivityModule.class)*/
    abstract CircleSourceUpFragment contributeCircleSourceUpFragmentInjector();

    @ActivityScope
    @ContributesAndroidInjector/*(modules = HomeActivityModule.class)*/
    abstract CirclePubActiveFragment contributeCirclePubActiveFragmentInjector();

    @ActivityScope
    @ContributesAndroidInjector/*(modules = HomeActivityModule.class)*/
    abstract CirclePubRequestionFragment contributeCirclePubRequestionFragmentInjector();

//    @ActivityScope
//    @ContributesAndroidInjector/*(modules = HomeActivityModule.class)*/
//    abstract PayFragment contributePayFragmentInjector();

    @ActivityScope
    @ContributesAndroidInjector/*(modules = HomeActivityModule.class)*/
    abstract CircleCreateListActivity contributeCircleCreateListActivityInjector();

    @ActivityScope
    @ContributesAndroidInjector/*(modules = HomeActivityModule.class)*/
    abstract CircleCompanyFragment contributeCircleCompanyFragmentInjector();

    @ActivityScope
    @ContributesAndroidInjector/*(modules = HomeActivityModule.class)*/
    abstract CircleActiveFragment contributeCircleActiveFragmentInjector();

    @ActivityScope
    @ContributesAndroidInjector/*(modules = HomeActivityModule.class)*/
    abstract CircleConutryFragment contributeCircleConutryFragmentInjector();

    @ActivityScope
    @ContributesAndroidInjector/*(modules = HomeActivityModule.class)*/
    abstract CircleperssionActivity contributeCircleperssionActivityInjector();

    @ActivityScope
    @ContributesAndroidInjector/*(modules = HomeActivityModule.class)*/
    abstract CircleFriendShareActivity contributeCircleFriendShareActivityInjector();

    @ActivityScope
    @ContributesAndroidInjector/*(modules = HomeActivityModule.class)*/
    abstract CircleGroupPerssionActivity contributeCircleGroupPerssionActivityInjector();

    @ActivityScope
    @ContributesAndroidInjector/*(modules = HomeActivityModule.class)*/
    abstract CirclePersionSettingActivity contributeCirclePersionSettingActivityInjector();

    @ActivityScope
    @ContributesAndroidInjector/*(modules = HomeActivityModule.class)*/
    abstract CircleModifyActivity contributeCircleModifyActivityInjector();

    @ActivityScope
    @ContributesAndroidInjector/*(modules = HomeActivityModule.class)*/
    abstract CircleEditMemberGroupActivity contributeCircleEditMemberGroupActivityInjector();

    @ActivityScope
    @ContributesAndroidInjector/*(modules = HomeActivityModule.class)*/
    abstract CirclePersonPerssionActivity contributeCirclePersonPerssionActivityyInjector();

    @ActivityScope
    @ContributesAndroidInjector/*(modules = HomeActivityModule.class)*/
    abstract CircleShareSelectActivity contributeCircleShareSelectActivityInjector();

    @ActivityScope
    @ContributesAndroidInjector/*(modules = HomeActivityModule.class)*/
    abstract CircleShareGroupSelectActivity contributeCircleShareGroupSelectActivityInjector();

    @ActivityScope
    @ContributesAndroidInjector/*(modules = HomeActivityModule.class)*/
    abstract CirclePerssionSelectActivity contributeCirclePerssionSelectActivityInjector();

    @ActivityScope
    @ContributesAndroidInjector/*(modules = HomeActivityModule.class)*/
    abstract CircleDynamicDetailActivity contributeCircleDynamicDetailActivityInjector();

    @ActivityScope
    @ContributesAndroidInjector/*(modules = HomeActivityModule.class)*/
    abstract CirclH5DetailFragment contributeCirclH5DetailFragmentInjector();

    @ActivityScope
    @ContributesAndroidInjector/*(modules = HomeActivityModule.class)*/
    abstract CircleDynamicDetailFragment contributeCircleDynamicDetailFragmentInjector();

    @ActivityScope
    @ContributesAndroidInjector/*(modules = HomeActivityModule.class)*/
    abstract CircleReplayQuestionActivity contributeCircleReplayQuestionActivityInjector();

    @ActivityScope
    @ContributesAndroidInjector/*(modules = HomeActivityModule.class)*/
    abstract CommonWebFragment contributeCommonWebFragmentInjector();

    @ActivityScope
    @ContributesAndroidInjector/*(modules = HomeActivityModule.class)*/
    abstract CommonH5Activity contributeCommonH5ActivityInjector();

    @ActivityScope
    @ContributesAndroidInjector/*(modules = HomeActivityModule.class)*/
    abstract CircleReplyListActivity contributeCircleReplyListActivityInjector();

    @ActivityScope
    @ContributesAndroidInjector/*(modules = HomeActivityModule.class)*/
    abstract CircleCommentListActivity contributeCircleCommentListActivityInjector();

    @ActivityScope
    @ContributesAndroidInjector/*(modules = HomeActivityModule.class)*/
    abstract CircleRecommandPersionListFragment contributeCircleTestFragmentInjector();


    @ActivityScope
    @ContributesAndroidInjector/*(modules = HomeActivityModule.class)*/
    abstract CircleHomeActivity contributeCircleHomeActivityInjector();

    @ActivityScope
    @ContributesAndroidInjector/*(modules = HomeActivityModule.class)*/
    abstract CircleHome2Activity contributeCircleHomeActivity2Injector();


    @ActivityScope
    @ContributesAndroidInjector/*(modules = HomeActivityModule.class)*/
    abstract CircleCreateHwjActivity contributeCircleCreateHwjActivityInjector();


    @ActivityScope
    @ContributesAndroidInjector/*(modules = HomeActivityModule.class)*/
    abstract CircleHomeFragment contributeCircleHomeFragmentInjector();

    @ActivityScope
    @ContributesAndroidInjector/*(modules = HomeActivityModule.class)*/
    abstract CirclePersonDetailWjActivity contributeCirclePersonDetailWjActivityInjector();

    @ActivityScope
    @ContributesAndroidInjector/*(modules = HomeActivityModule.class)*/
    abstract CircleAddSurfActivity contributeCircleAddSurfActivityInjector();
//    @ActivityScope
//    @ContributesAndroidInjector/*(modules = HomeActivityModule.class)*/
//    abstract DynamicFragmentV2 contributeDynamicFragmentV2Injector();


}
