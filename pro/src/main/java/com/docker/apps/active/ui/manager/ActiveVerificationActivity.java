package com.docker.apps.active.ui.manager;

import android.Manifest;
import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.android.arouter.launcher.ARouter;
import com.dcbfhd.utilcode.utils.ToastUtils;
import com.docker.apps.R;
import com.docker.apps.active.vm.ActiveCommonViewModel;
import com.docker.apps.active.vo.card.ActiveInfoCard;
import com.docker.apps.active.vo.card.ActiveManagerCard;
import com.docker.apps.databinding.ProActiveManagerDetailBinding;
import com.docker.apps.databinding.ProActiveVerificationBinding;
import com.docker.common.common.command.NitDelegetCommand;
import com.docker.common.common.config.Constant;
import com.docker.common.common.model.CommonListOptions;
import com.docker.common.common.router.AppRouter;
import com.docker.common.common.ui.base.NitCommonActivity;
import com.docker.common.common.ui.base.NitCommonFragment;
import com.docker.common.common.vm.NitCommonListVm;
import com.docker.common.common.vm.container.NitCommonContainerViewModel;
import com.docker.common.common.widget.card.NitBaseProviderCard;
import com.flyco.tablayout.listener.OnTabSelectListener;
import com.luck.picture.lib.permissions.RxPermissions;

import java.util.HashMap;

import cn.bingoogolapple.qrcode.core.BarcodeType;
import cn.bingoogolapple.qrcode.core.QRCodeView;

/*
 * 活动管理详情
 **/

//
@Route(path = AppRouter.ACTIVE_MANAGE_VERFIC)
public class ActiveVerificationActivity extends NitCommonActivity<ActiveCommonViewModel, ProActiveVerificationBinding> {

    public String activityid;

    @Override
    public ActiveCommonViewModel getmViewModel() {
        return ViewModelProviders.of(this, factory).get(ActiveCommonViewModel.class);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.pro_active_verification;
    }

    @Override
    protected void inject() {
        super.inject();
        ARouter.getInstance().inject(this);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mToolbar.hide();
        activityid = getIntent().getStringExtra("activityid");
    }

    @Override
    public void initView() {
        QRCodeView.Delegate delegate = new QRCodeView.Delegate() {
            @Override
            public void onScanQRCodeSuccess(String result) {
                Log.d("sss", "onScanQRCodeSuccess: ============" + result);
                HashMap<String, String> param = new HashMap<>();
                param.put("activityid", activityid);
                param.put("evoucherNo", result);
                mViewModel.evoucherValidate(param);
            }

            @Override
            public void onCameraAmbientBrightnessChanged(boolean isDark) {

            }

            @Override
            public void onScanQRCodeOpenCameraError() {

            }
        };
        mBinding.zxingview.setType(BarcodeType.ONLY_QR_CODE, null);
        mBinding.zxingview.setDelegate(delegate);
        RxPermissions rxPermissions = new RxPermissions(ActiveVerificationActivity.this);
        rxPermissions.requestEach(
                Manifest.permission.CAMERA,
                Manifest.permission.READ_EXTERNAL_STORAGE)
                .subscribe(permission -> {
                    if (permission.name.equals(Manifest.permission.CAMERA)) {
                        if (permission.granted) {
                            // 用户已经同意该权限
                            mBinding.zxingview.startCamera();
                            mBinding.zxingview.startSpot();
                        } else if (permission.shouldShowRequestPermissionRationale) {
                            // 用户拒绝了该权限，没有选中『不再询问』（Never ask again）,那么下次再次启动时，还会提示请求权限的对话框
                        } else {
                            // 用户拒绝了该权限，并且选中『不再询问』
                        }
                    }
                });

        mBinding.segment.setTabData(new String[]{"扫码核销", "密码核销"});
        mBinding.segment.setOnTabSelectListener(new OnTabSelectListener() {
            @Override
            public void onTabSelect(int position) {
                if (position == 0) {
                    mBinding.frameBarScan.setVisibility(View.VISIBLE);
                    mBinding.llPwdVerfi.setVisibility(View.GONE);
                    mBinding.zxingview.setType(BarcodeType.ONLY_QR_CODE, null);
                    mBinding.zxingview.setDelegate(delegate);
                    RxPermissions rxPermissions = new RxPermissions(ActiveVerificationActivity.this);
                    rxPermissions.requestEach(
                            Manifest.permission.CAMERA,
                            Manifest.permission.READ_EXTERNAL_STORAGE)
                            .subscribe(permission -> {
                                if (permission.name.equals(Manifest.permission.CAMERA)) {
                                    if (permission.granted) {
                                        // 用户已经同意该权限
                                        mBinding.zxingview.startSpot();
                                    } else if (permission.shouldShowRequestPermissionRationale) {
                                        // 用户拒绝了该权限，没有选中『不再询问』（Never ask again）,那么下次再次启动时，还会提示请求权限的对话框
                                    } else {
                                        // 用户拒绝了该权限，并且选中『不再询问』
                                    }
                                }
                            });
                } else {
                    mBinding.zxingview.stopSpot();
                    mBinding.frameBarScan.setVisibility(View.GONE);
                    mBinding.llPwdVerfi.setVisibility(View.VISIBLE);
                }
            }

            @Override
            public void onTabReselect(int position) {

            }
        });
        mBinding.segment.setCurrentTab(0);

        mBinding.ivBack.setOnClickListener(v -> {
            finish();
        });

        mBinding.tvSure.setOnClickListener(v -> {
            if (!TextUtils.isEmpty(mBinding.edCode.getText().toString().trim())) {
                // todo
                HashMap<String, String> param = new HashMap<>();
                param.put("activityid", activityid);
                param.put("evoucherNo", mBinding.edCode.getText().toString().trim());
                mViewModel.evoucherValidate(param);
            } else {
                ToastUtils.showShort(mBinding.edCode.getHint());
            }
        });

    }

    @Override
    public void initObserver() {
        mViewModel.mValidateLv.observe(this, s -> {
            finish();
        });
    }


    @Override
    public void initRouter() {

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mBinding.zxingview.stopCamera();
    }
}
