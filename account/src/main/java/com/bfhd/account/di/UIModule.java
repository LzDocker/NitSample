package com.bfhd.account.di;


import com.bfhd.account.ui.AccounAddAddressActivity;
import com.bfhd.account.ui.AccounAddressListActivity;
import com.bfhd.account.ui.AccounAttentionListActivity;
import com.bfhd.account.ui.AccounCommentListActivity;
import com.bfhd.account.ui.AccounFansListActivity;
import com.bfhd.account.ui.AccounMessageDetailActivity;
import com.bfhd.account.ui.AccounMessageListtActivity;
import com.bfhd.account.ui.AccounModifyPwdActivity;
import com.bfhd.account.ui.AccounMoneyDetailListActivity;
import com.bfhd.account.ui.AccounNoSeeActivity;
import com.bfhd.account.ui.AccounOrderListtActivity;
import com.bfhd.account.ui.AccounParseListActivity;
import com.bfhd.account.ui.AccounPointRecordListActivity;
import com.bfhd.account.ui.AccounPrivacySettingActivity;
import com.bfhd.account.ui.AccounSelectCountryNumActivity;
import com.bfhd.account.ui.AccounSettingActivity;
import com.bfhd.account.ui.AccounSuggestionActivity;
import com.bfhd.account.ui.AccounSystemMessageActivity;
import com.bfhd.account.ui.AccountAddConstActivity;
import com.bfhd.account.ui.AccountAttenListActivity;
import com.bfhd.account.ui.AccountCityCoutainerFragment;
import com.bfhd.account.ui.AccountCollectionListActivity;
import com.bfhd.account.ui.AccountCountryNumListFragment;
import com.bfhd.account.ui.AccountCountrySearchFragment;
import com.bfhd.account.ui.AccountHelpCenterActivity;
import com.bfhd.account.ui.AccountInfoCompleteActivity;
import com.bfhd.account.ui.AccountMineActiveActivity;
import com.bfhd.account.ui.AccountOneKeySosActivity;
import com.bfhd.account.ui.AccountPersonInfoActivity;
import com.bfhd.account.ui.AccountPointStoreActivity;
import com.bfhd.account.ui.AccountSafeCollectionListActivity;
import com.bfhd.account.ui.AccountSigninListActivity;
import com.bfhd.account.ui.AccountWjBuyActivity;
import com.bfhd.account.ui.AccountWjSenttActivity;
import com.bfhd.account.ui.AccoutMoneyBoxActivity;
import com.bfhd.account.ui.AccoutMoneyTXActivity;
import com.bfhd.account.ui.AccoutMoneyTXActivityV2;
import com.bfhd.account.ui.CompleteInfoActivity;
import com.bfhd.account.ui.FindPwdActivity;
import com.bfhd.account.ui.LoginActivity;
import com.bfhd.account.ui.MineFragment;
import com.bfhd.account.ui.MineFragmentWj;
import com.bfhd.account.ui.RegistActivity;
import com.bfhd.account.ui.RegisteFragment;
import com.bfhd.account.ui.index.FragmentMineIndex;
import com.bfhd.account.ui.tygs.AccounCollectActivity;
import com.bfhd.account.ui.tygs.AccounOrderListActivity;
import com.bfhd.account.ui.tygs.AccounPointActivity;
import com.bfhd.account.ui.tygs.AccounRewardActivity;
import com.bfhd.account.ui.tygs.AccounTygsAttentionListActivity;
import com.bfhd.account.ui.tygs.AccounTygsBranchListActivity;
import com.bfhd.account.ui.tygs.fragment.AccountInvitationCoutainerFragment;
import com.bfhd.account.ui.tygs.setting.AccounSettingFragment;
import com.docker.core.di.component.BaseActComponent;
import com.docker.core.di.scope.ActivityScope;

import dagger.Module;
import dagger.android.ContributesAndroidInjector;

@Module(subcomponents = {BaseActComponent.class})
public abstract class UIModule {

    //    @ActivityScope
//    @ContributesAndroidInjector/*(modules = HomeActivityModule.class)*/
//    abstract MainActivity contributeMainActivitytInjector();


    @ActivityScope
    @ContributesAndroidInjector(modules = LoginModule.class)
    abstract LoginActivity contributeLoginActivityInjector();

    @ActivityScope
    @ContributesAndroidInjector/*(modules = HomeActivityMoe.class)*/
    abstract RegistActivity contributeRegistActivityInjector();

    @ActivityScope
    @ContributesAndroidInjector/*(modules = HomeActivityMoe.class)*/
    abstract MineFragment contributeMineFragmentInjector();

    @ActivityScope
    @ContributesAndroidInjector/*(modules = HomeActivityMoe.class)*/
    abstract AccountPersonInfoActivity contributeAccountPersonInfoActivityInjector();

    @ActivityScope
    @ContributesAndroidInjector/*(modules = HomeActivityMoe.class)*/
    abstract AccountMineActiveActivity contributeAccountMineActiveActivityInjector();

    @ActivityScope
    @ContributesAndroidInjector/*(modules = HomeActivityMoe.class)*/
    abstract AccounTygsAttentionListActivity contributeAccounAttentionListActivityInjector();

    @ActivityScope
    @ContributesAndroidInjector/*(modules = HomeActivityMoe.class)*/
    abstract AccounFansListActivity contributeAccounFansListActivityInjector();

    @ActivityScope
    @ContributesAndroidInjector/*(modules = HomeActivityMoe.class)*/
    abstract AccounMessageListtActivity contributeAccounMessageListtActivityInjector();

    @ActivityScope
    @ContributesAndroidInjector/*(modules = HomeActivityMoe.class)*/
    abstract AccounOrderListtActivity contributeAccounOrderListtActivityInjector();

    @ActivityScope
    @ContributesAndroidInjector/*(modules = HomeActivityMoe.class)*/
    abstract AccountCollectionListActivity contributeAccountCollectionListActivityInjector();

    @ActivityScope
    @ContributesAndroidInjector/*(modules = HomeActivityMoe.class)*/
    abstract AccountOneKeySosActivity contributeAccountOneKeySosActivityInjector();

    @ActivityScope
    @ContributesAndroidInjector/*(modules = HomeActivityMoe.class)*/
    abstract AccountSigninListActivity contributeAccountAccountSigninListActivityInjector();

    @ActivityScope
    @ContributesAndroidInjector/*(modules = HomeActivityMoe.class)*/
    abstract AccounSuggestionActivity contributAccounSuggestionActivityInjector();

    @ActivityScope
    @ContributesAndroidInjector/*(modules = HomeActivityMoe.class)*/
    abstract AccounSettingActivity contributAccounSettingActivityInjector();


    @ActivityScope
    @ContributesAndroidInjector/*(modules = HomeActivityMoe.class)*/
    abstract RegisteFragment contributRegisteFragmentInjector();


    @ActivityScope
    @ContributesAndroidInjector/*(modules = HomeActivityMoe.class)*/
    abstract AccountInfoCompleteActivity contributAccountInfoCompleteActivityInjector();

    @ActivityScope
    @ContributesAndroidInjector/*(modules = HomeActivityMoe.class)*/
    abstract AccounModifyPwdActivity contributAccounModifyPwdActivityInjector();

    @ActivityScope
    @ContributesAndroidInjector/*(modules = HomeActivityMoe.class)*/
    abstract AccountHelpCenterActivity contributAccountHelpCenterActivityInjector();

    @ActivityScope
    @ContributesAndroidInjector/*(modules = HomeActivityMoe.class)*/
    abstract AccountAddConstActivity contributAccountAddConstActivityInjector();

    @ActivityScope
    @ContributesAndroidInjector/*(modules = HomeActivityMoe.class)*/
    abstract AccounSystemMessageActivity contributAccounSystemMessageActivityInjector();

    @ActivityScope
    @ContributesAndroidInjector/*(modules = HomeActivityMoe.class)*/
    abstract AccounCommentListActivity contributAccounCommentListActivityInjector();

    @ActivityScope
    @ContributesAndroidInjector/*(modules = HomeActivityMoe.class)*/
    abstract AccounParseListActivity contributAccounParseListActivityInjector();

    @ActivityScope
    @ContributesAndroidInjector/*(modules = HomeActivityMoe.class)*/
    abstract AccounMessageDetailActivity contributAccounMessageDetailActivityInjector();

    @ActivityScope
    @ContributesAndroidInjector/*(modules = HomeActivityMoe.class)*/
    abstract AccounSelectCountryNumActivity contributAccounSelectCountryNumActivityInjector();

    @ActivityScope
    @ContributesAndroidInjector/*(modules = HomeActivityMoe.class)*/
    abstract AccountCountrySearchFragment contributAccountCountrySearchFragmentInjector();

    @ActivityScope
    @ContributesAndroidInjector/*(modules = HomeActivityMoe.class)*/
    abstract AccountCountryNumListFragment contributAccountCountryNumListFragmentInjector();

    @ActivityScope
    @ContributesAndroidInjector/*(modules = HomeActivityMoe.class)*/
    abstract FindPwdActivity contributFindPwdActivityInjector();

    @ActivityScope
    @ContributesAndroidInjector/*(modules = HomeActivityMoe.class)*/
    abstract AccounPointRecordListActivity contributPointRecordListActivityInjector();

    @ActivityScope
    @ContributesAndroidInjector/*(modules = HomeActivityMoe.class)*/
    abstract AccountSafeCollectionListActivity contributAccountSafeCollectionListActivityInjector();

    @ActivityScope
    @ContributesAndroidInjector/*(modules = HomeActivityMoe.class)*/
    abstract AccountPointStoreActivity contributAccountPointStoreActivityInjector();

    @ActivityScope
    @ContributesAndroidInjector/*(modules = HomeActivityMoe.class)*/
    abstract MineFragmentWj contributMineFragmentWjInjector();

    @ActivityScope
    @ContributesAndroidInjector/*(modules = HomeActivityMoe.class)*/
    abstract AccountCityCoutainerFragment contributAccountCityCoutainerFragmentInjector();

    @ActivityScope
    @ContributesAndroidInjector/*(modules = HomeActivityMoe.class)*/
    abstract AccounAddressListActivity contributAccounAddressListActivityInjector();

    @ActivityScope
    @ContributesAndroidInjector/*(modules = HomeActivityMoe.class)*/
    abstract AccounAddAddressActivity contributAccounAddAddressActivityInjector();

    @ActivityScope
    @ContributesAndroidInjector/*(modules = HomeActivityMoe.class)*/
    abstract AccountWjSenttActivity contributAccountWjSenttActivityInjector();

    @ActivityScope
    @ContributesAndroidInjector/*(modules = HomeActivityMoe.class)*/
    abstract AccountAttenListActivity contributAccountAttenListActivityInjector();

    @ActivityScope
    @ContributesAndroidInjector/*(modules = HomeActivityMoe.class)*/
    abstract AccoutMoneyBoxActivity contributAccoutMoneyBoxActivityInjector();


    @ActivityScope
    @ContributesAndroidInjector/*(modules = HomeActivityMoe.class)*/
    abstract AccountWjBuyActivity contributAccountWjBuyActivityInjector();


    @ActivityScope
    @ContributesAndroidInjector/*(modules = HomeActivityMoe.class)*/
    abstract AccounMoneyDetailListActivity contributAccounMoneyDetailListActivityInjector();


    @ActivityScope
    @ContributesAndroidInjector/*(modules = HomeActivityMoe.class)*/
    abstract AccoutMoneyTXActivity contributAccoutMoneyTXActivityInjector();

    @ActivityScope
    @ContributesAndroidInjector/*(modules = HomeActivityMoe.class)*/
    abstract AccounPrivacySettingActivity contributAccounPrivacySettingActivityInjector();

    @ActivityScope
    @ContributesAndroidInjector/*(modules = HomeActivityMoe.class)*/
    abstract AccounNoSeeActivity contributAccounNoSeeActivityInjector();

    @ActivityScope
    @ContributesAndroidInjector/*(modules = HomeActivityMoe.class)*/
    abstract FragmentMineIndex contributFragmentMineIndexInjector();

    @ActivityScope
    @ContributesAndroidInjector/*(modules = HomeActivityMoe.class)*/
    abstract AccounSettingFragment contributAccounSettingFragmentInjector();

    @ActivityScope
    @ContributesAndroidInjector/*(modules = HomeActivityMoe.class)*/
    abstract AccounAttentionListActivity contributAccounAttentionListActivityInjector();

    @ActivityScope
    @ContributesAndroidInjector/*(modules = HomeActivityMoe.class)*/
    abstract AccounRewardActivity contributAccounRewardActivityInjector();


    @ActivityScope
    @ContributesAndroidInjector/*(modules = HomeActivityMoe.class)*/
    abstract AccounPointActivity contributAccounPointActivityInjector();

    @ActivityScope
    @ContributesAndroidInjector/*(modules = HomeActivityMoe.class)*/
    abstract AccountInvitationCoutainerFragment contributAccountInvitationCoutainerFragmentInjector();

    @ActivityScope
    @ContributesAndroidInjector/*(modules = HomeActivityMoe.class)*/
    abstract AccounTygsBranchListActivity contributAccounTygsBranchListActivityInjector();


    @ActivityScope
    @ContributesAndroidInjector/*(modules = HomeActivityMoe.class)*/
    abstract AccounOrderListActivity contributAccounOrderListActivityInjector();

    @ActivityScope
    @ContributesAndroidInjector/*(modules = HomeActivityMoe.class)*/
    abstract AccounCollectActivity contributAccounCollectActivityInjector();

    @ActivityScope
    @ContributesAndroidInjector/*(modules = HomeActivityMoe.class)*/
    abstract AccoutMoneyTXActivityV2 contributAccoutMoneyTXActivityV2Injector();

    @ActivityScope
    @ContributesAndroidInjector/*(modules = HomeActivityMoe.class)*/
    abstract CompleteInfoActivity contributCompleteInfoActivityInjector();

}

