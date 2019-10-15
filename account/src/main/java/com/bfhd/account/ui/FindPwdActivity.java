package com.bfhd.account.ui;

import android.arch.lifecycle.ViewModelProvider;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.widget.TextView;

import com.alibaba.android.arouter.launcher.ARouter;
import com.bfhd.account.R;
import com.bfhd.account.databinding.AccountActivityFindpwdBinding;
import com.bfhd.account.vm.AccountViewModel;
import com.bfhd.circle.base.HivsBaseActivity;
import com.bfhd.circle.base.ViewEventResouce;
import com.dcbfhd.utilcode.utils.ToastUtils;
import com.gyf.immersionbar.ImmersionBar;
import java.util.concurrent.TimeUnit;

import javax.inject.Inject;

import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;

public class FindPwdActivity extends HivsBaseActivity<AccountViewModel, AccountActivityFindpwdBinding> {


    @Inject
    ViewModelProvider.Factory factory;

    private String title;

    @Override
    public AccountViewModel getmViewModel() {
        return ViewModelProviders.of(this, factory).get(AccountViewModel.class);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.account_activity_findpwd;
    }

    @Override
    protected void inject() {
        super.inject();
        ARouter.getInstance().inject(this);
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        title = getIntent().getStringExtra("title");
        super.onCreate(savedInstanceState);
        mToolbar.setNavigationIndicator(R.mipmap.account_icon_close);
        mToolbar.setBackgroundColor(getResources().getColor(R.color.account_white));
        if (TextUtils.isEmpty(title)) {
            mToolbar.setTitle("找回密码", getResources().getColor(R.color.account_black));
        } else {
            mToolbar.setTitle("修改密码", getResources().getColor(R.color.account_black));
        }
    }

    @Override
    public void initImmersionBar() {
        ImmersionBar.with(this)
                .fitsSystemWindows(true)  //使用该属性,必须指定状态栏颜色
                .statusBarColor("#ffffff")
                .statusBarDarkFont(true)   //状态栏字体是深色，不写默认为亮色
                .autoDarkModeEnable(true) //自动状态栏字体和导航栏图标变色，必须指定状态栏颜色和导航栏颜色才可以自动变色哦
                .autoStatusBarDarkModeEnable(true, 0.2f) //自动状态栏字体变色，必须指定状态栏颜色才可以自动变色哦
                .autoNavigationBarDarkModeEnable(true, 0.2f) //自动导航栏图标变色，必须指定导航栏颜色才可以自动变色哦
                .flymeOSStatusBarFontColor("#000000")  //修改flyme OS状态栏字体颜色
                .navigationBarColor("#ffffff")
                .fullScreen(true)
                .addTag("PicAndColor")
                .init();
    }

    public void initView() {

        mBinding.accountSubmint.setOnClickListener(v -> {
            if (TextUtils.isEmpty(mBinding.edUsername.getText().toString().trim())) {
                ToastUtils.showShort("用户名不能为空");
                return;
            }
            if (TextUtils.isEmpty(mBinding.edPassword.getText().toString().trim())) {
                ToastUtils.showShort("密码不能为空");
                return;
            }
            if (TextUtils.isEmpty(mBinding.edSmsCode.getText().toString().trim())) {
                ToastUtils.showShort("验证码不能为空");
                return;
            }
            mViewModel.resetPwd(mBinding.edUsername.getText().toString().trim(), mBinding.edSmsCode.getText().toString().trim(), mBinding.edPassword.getText().toString().trim());
        });
        // 选择
        mBinding.rlNumSelect.setOnClickListener(v -> {
            Intent intent = new Intent(FindPwdActivity.this, AccounSelectCountryNumActivity.class);
            startActivityForResult(intent, 101);
        });
        // 发送信息
        mBinding.tvSendCode.setOnClickListener(v -> {
            if (TextUtils.isEmpty(mBinding.edUsername.getText().toString())) {
                ToastUtils.showShort("手机号不能为空！");
                return;
            }
//            if (mBinding.edUsername.getText().toString().length() != 11) {
//                ToastUtils.showShort("手机号格式不正确！");
//                return;
//            }
            mViewModel.sendSms(mBinding.edUsername.getText().toString(), mBinding.tvNum.getText().toString().trim());
        });

    }

    @Override
    public void OnVmEnentListner(ViewEventResouce viewEventResouce) {
        super.OnVmEnentListner(viewEventResouce);
        switch (viewEventResouce.eventType) {
            case 103:
                verfi();
                break;
            case 104:
                ToastUtils.showShort("密码重置成功");
                finish();
                break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            if (requestCode == 101) {
                mBinding.tvNum.setText(data.getStringExtra("data"));
            }
        }
    }

    private void verfi() {
        final int count = 60;
        Observable.interval(0, 1, TimeUnit.SECONDS)
                .take(count)
                .map(aLong -> count - aLong)
                .observeOn(AndroidSchedulers.mainThread())
                .doOnSubscribe(disposable -> mBinding.tvSendCode.setEnabled(false))
                .subscribe(new Observer<Long>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(Long aLong) {
                        ((TextView) mBinding.tvSendCode).setText(aLong + "s");
                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onComplete() {
                        mBinding.tvSendCode.setEnabled(true);
                        ((TextView) mBinding.tvSendCode).setText("发送验证码");
                    }
                });
    }

}
