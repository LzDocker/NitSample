package com.bfhd.account.ui;

import android.arch.lifecycle.ViewModelProvider;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.android.arouter.launcher.ARouter;
import com.bfhd.account.R;
import com.bfhd.account.databinding.AccountActivitySettingBinding;
import com.bfhd.account.vm.AccountViewModel;
import com.bfhd.circle.base.HivsBaseActivity;
import com.docker.common.common.router.AppRouter;
import com.docker.common.common.utils.versionmanager.AppVersionManager;
import com.docker.core.widget.BottomSheetDialog;

import javax.inject.Inject;

/*
 * 设置
 **/
@Route(path = AppRouter.ACCOUNT_ATTEN_SETTING)
@Deprecated
public class AccounSettingActivity extends HivsBaseActivity<AccountViewModel, AccountActivitySettingBinding> {

    @Inject
    ViewModelProvider.Factory factory;

    private BottomSheetDialog bottomSheetDialog = new BottomSheetDialog();
    private String tel;
    @Inject
    AppVersionManager versionManager;

    @Override
    public AccountViewModel getmViewModel() {
        return ViewModelProviders.of(this, factory).get(AccountViewModel.class);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.account_activity_setting;
    }

    @Override
    protected void inject() {
        super.inject();
        ARouter.getInstance().inject(this);
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        tel = getIntent().getStringExtra("tel");
//        mBinding.tvConstUs.setText(tel);
    }

    @Override
    public void initView() {

        // @Deprecated ----
//        CommonListOptions commonListReq = new CommonListOptions();
//        commonListReq.refreshState = 3;
//        NitCommonListInstanceFragment nitCommonListInstanceFragment = NitCommonListInstanceFragment.newinstance(commonListReq, new ReponseInterface() {
//            @Override
//            public Object exectue(Object o) {
//                return ViewModelProviders.of(((NitCommonFragment) o),
//                        ((NitCommonFragment) o).factory).get(AccountIndexListViewModel.class);
//            }
//
//            @Override
//            public void next(Object o) {
//                ((AccountIndexListViewModel) ((NitCommonListInstanceFragment) o).mViewModel).infoVoMutableLiveData.observe((NitCommonListInstanceFragment) o, new Observer<MyInfoVo>() {
//                    @Override
//                    public void onChanged(@Nullable MyInfoVo myInfoVo) {
//
//                    }
//                });
//                ((AccountIndexListViewModel) ((NitCommonListInstanceFragment) o).mViewModel).fetchMyInfo();
//            }
//        });
//        FragmentUtils.add(getSupportFragmentManager(), nitCommonListInstanceFragment, R.id.frame);

    }

//    @Override
//    public void initView() {
//        mToolbar.setTitle("设置");
//        mBinding.tvChacheClear.setText(GlideCacheUtil.getInstance().getCacheSize(this) + "");
//
//        // 修改密码
//        mBinding.llModifyPwd.setOnClickListener(v -> {
//            Intent intent = new Intent(AccounSettingActivity.this, FindPwdActivity.class);
//            intent.putExtra("title", "修改密码");
//            startActivity(intent);
//
//        });
//        mBinding.llPersonInfo.setOnClickListener(v -> {
//            Intent intent = new Intent(AccounSettingActivity.this, AccountPersonInfoActivity.class);
//            startActivity(intent);
//        });
//
//        //地址管理
//        mBinding.llAddress.setOnClickListener(v -> {
//            Intent intent = new Intent(AccounSettingActivity.this, AccounAddressListActivity.class);
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
//            CommonH5Activity.startMe(AccounSettingActivity.this, Constant.HelpCenterWeb, "帮助中心");
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
//            Intent intent = new Intent(AccounSettingActivity.this, AccounPrivacySettingActivity.class);
//            startActivity(intent);
//        });
//        //
//        mBinding.llAboutUs.setOnClickListener(v -> {
//            CommonH5Activity.startMe(AccounSettingActivity.this, Constant.AboutUsWeb, "关于我们");
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
//            bottomSheetDialog.show(AccounSettingActivity.this);
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
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            if (requestCode == 10086 && resultCode == RESULT_OK) {
                versionManager.install();
            }
        }
    }
}
