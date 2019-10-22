package com.bfhd.account.ui.index.setting;

import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.bfhd.account.vm.AccountSettingViewModel;
import com.docker.common.common.model.CommonListOptions;
import com.docker.common.common.router.AppRouter;
import com.docker.common.common.ui.base.NitCommonListFragment;
import com.docker.common.common.utils.versionmanager.AppVersionManager;
import com.docker.core.widget.BottomSheetDialog;

import javax.inject.Inject;

import static android.app.Activity.RESULT_OK;

/*
 * 设置
 **/
@Route(path = AppRouter.ACCOUNT_ATTEN_SETTING_FRAG)
public class AccounSettingFragment extends NitCommonListFragment<AccountSettingViewModel> {

    CommonListOptions commonListReq = new CommonListOptions();

    private BottomSheetDialog bottomSheetDialog = new BottomSheetDialog();
    private String tel;


    @Inject
    AppVersionManager versionManager;

    @Override
    public AccountSettingViewModel getViewModel() {
        return ViewModelProviders.of(this, factory).get(AccountSettingViewModel.class);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
    }

    @Override
    public CommonListOptions getArgument() {
        commonListReq.refreshState = 3;
        commonListReq.RvUi = 0;
        return commonListReq;
    }


//    @Override
//    public void initView() {
//        mToolbar.setTitle("设置");
//        mBinding.tvChacheClear.setText(GlideCacheUtil.getInstance().getCacheSize(this) + "");
//
//        // 修改密码
//        mBinding.llModifyPwd.setOnClickListener(v -> {
//            Intent intent = new Intent(AccounSettingFragment.this, FindPwdActivity.class);
//            intent.putExtra("title", "修改密码");
//            startActivity(intent);
//
//        });
//        mBinding.llPersonInfo.setOnClickListener(v -> {
//            Intent intent = new Intent(AccounSettingFragment.this, AccountPersonInfoActivity.class);
//            startActivity(intent);
//        });
//
//        //地址管理
//        mBinding.llAddress.setOnClickListener(v -> {
//            Intent intent = new Intent(AccounSettingFragment.this, AccounAddressListActivity.class);
//            startActivity(intent);
//        });
//        mBinding.tvVersionvode.setText("版本号： " + AppUtils.getAppVersionName());
//        mBinding.tvVersionvode.setOnClickListener(v -> {
//            this.getLifecycle().addObserver(versionManager.Bind(this, this, mViewModel.checkUpData(), true, versionManager.TYPE_DIALOG, "com.docker.NitSample"));
//        });
//
//        // 帮助中心
//        mBinding.llHelpCenter.setOnClickListener(v -> {
////            Intent intent = new Intent(AccounSettingActivity.this, AccountHelpCenterActivity.class);
////            startActivity(intent);
//            CommonH5Activity.startMe(AccounSettingFragment.this, Constant.HelpCenterWeb, "帮助中心");
//        });
//
//        // 联系我们
//        mBinding.tvConstUs.setOnClickListener(v -> {
//            if (TextUtils.isEmpty(tel)) {
//                return;
//            }
//            //跳转到拨号界面，同时传递电话号码
//            Intent dialIntent = new Intent(Intent.ACTION_DIAL, Uri.parse("tel:" + tel));
//            startActivity(dialIntent);
//        });
//        mBinding.llPrivacySetting.setOnClickListener(v -> {
//            Intent intent = new Intent(AccounSettingFragment.this, AccounPrivacySettingActivity.class);
//            startActivity(intent);
//        });
//        //
//        mBinding.llAboutUs.setOnClickListener(v -> {
//            CommonH5Activity.startMe(AccounSettingFragment.this, Constant.AboutUsWeb, "关于我们");
//        });
//
//        mBinding.tvChacheClear.setOnClickListener(v -> {
//
//            bottomSheetDialog.setDataCallback(new String[]{"清除缓存"}, position -> {
//                bottomSheetDialog.dismiss();
//                switch (position) {
//                    case 0:
//                        showWaitDialog("清除中...");
//                        clearChahe();
//                        break;
//                }
//            });
//            bottomSheetDialog.show(AccounSettingFragment.this);
//
//        });
//
//        mBinding.tvGoOut.setOnClickListener(v -> {
//            ARouter.getInstance().build(AppRouter.ACCOUNT_LOGIN).navigation();
//            CacheUtils.clearUser();
////            LogoutHelper.logout();
//            JPushInterface.deleteAlias(this, AppUtils.getAppVersionCode());
//            JPushInterface.clearAllNotifications(this);
////            ActivityUtils.finishAllActivities();
//            ActivityUtils.finishAllActivities(true);
//
//        });
//    }
//
//
//    private void clearChahe() {
//
//        GlideCacheUtil.getInstance().clearImageDiskCache(this, () -> {
//            hidWaitDialog();
//            mBinding.tvChacheClear.setText(GlideCacheUtil.getInstance().getCacheSize(this) + "");
//        });
//
//    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            if (requestCode == 10086 && resultCode == RESULT_OK) {
                versionManager.install();
            }
        }
    }

    @Override
    public void initImmersionBar() {

    }

    @Override
    public void onVisible() {
        super.onVisible();

    }
}
