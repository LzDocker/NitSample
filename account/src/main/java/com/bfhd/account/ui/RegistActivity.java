package com.bfhd.account.ui;

import android.arch.lifecycle.ViewModelProvider;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.os.Bundle;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.TextView;

import com.alibaba.android.arouter.facade.annotation.Autowired;
import com.alibaba.android.arouter.launcher.ARouter;
import com.bfhd.account.R;
import com.bfhd.account.databinding.AccountActivityRegistBinding;
import com.bfhd.account.utils.AccountConstant;
import com.bfhd.account.vm.AccountViewModel;
import com.bfhd.account.vo.RegistParmVo;
import com.bfhd.circle.base.HivsBaseActivity;
import com.bfhd.circle.base.ViewEventResouce;
import com.bfhd.circle.ui.common.CommonH5Activity;
import com.dcbfhd.utilcode.utils.ToastUtils;
import com.docker.common.common.provider.MessageService;
import com.docker.common.common.router.AppRouter;
import com.docker.common.common.utils.cache.CacheUtils;
import com.docker.common.common.vo.UserInfoVo;
import com.gyf.immersionbar.ImmersionBar;

import java.util.HashMap;
import java.util.concurrent.TimeUnit;

import javax.inject.Inject;

import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;

public class RegistActivity extends HivsBaseActivity<AccountViewModel, AccountActivityRegistBinding> {


    @Inject
    ViewModelProvider.Factory factory;

    private int mType = 1;  //个人
    private RegistParmVo registParmVo = new RegistParmVo();
    private String testsmsCode = "940418";
    private Observable timer;
    private String area_code = "+86";
    private HashMap<String, String> wechatInfo = null;
    private String isCheckbox = "";
    private String bindType;

    @Autowired
    MessageService imService;

    @Override
    public AccountViewModel getmViewModel() {
        return ViewModelProviders.of(this, factory).get(AccountViewModel.class);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.account_activity_regist;
    }

    @Override
    protected void inject() {
        super.inject();
        ARouter.getInstance().inject(this);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        wechatInfo = (HashMap<String, String>) getIntent().getSerializableExtra("wechatInfo");
        bindType = getIntent().getStringExtra("bindType");
        super.onCreate(savedInstanceState);
        mToolbar.setNavigationIndicator(R.mipmap.account_icon_close);
        mToolbar.setBackgroundColor(getResources().getColor(R.color.account_white));
        if (wechatInfo == null) {
            mToolbar.setTitle("注册", getResources().getColor(R.color.account_black));
        } else {
            mToolbar.setTitle("绑定手机号", getResources().getColor(R.color.account_black));
            mBinding.accountRegistNext.setText("绑定");
        }
        isCheckbox = "1";
    }

    @Override
    public void initImmersionBar() {
        super.initImmersionBar();
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
        mBinding.setRegistParm(registParmVo);
        mBinding.accountRegistNext.setOnClickListener(v -> {
            if (TextUtils.isEmpty(isCheckbox)) {
                ToastUtils.showShort("请选择同意用户协议");
                return;
            }
            doRegister();
        });

        mBinding.linCheckbox.setOnClickListener(v -> {
            if (mBinding.checkboxAgreement.isChecked()) {
                mBinding.checkboxAgreement.setChecked(false);
                isCheckbox = "1";
            } else {
                isCheckbox = "";
                mBinding.checkboxAgreement.setChecked(true);
            }

        });


        // 发送信息
        mBinding.tvSendCode.setOnClickListener(v -> {
            if (TextUtils.isEmpty(mBinding.getRegistParm().getPhone())) {
                ToastUtils.showShort("手机号不能为空！");
                return;
            }
            mViewModel.sendSms(mBinding.getRegistParm().getPhone(), mBinding.tvNum.getText().toString().trim());
        });

        mBinding.accountToLogin.setOnClickListener(v -> {
            finish();
        });
        // 选择
        mBinding.rlNumSelect.setOnClickListener(v -> {
            Intent intent = new Intent(RegistActivity.this, AccounSelectCountryNumActivity.class);
            startActivityForResult(intent, 101);
        });
        mBinding.accountToUse.setOnClickListener(v -> {
            CommonH5Activity.startMe(RegistActivity.this, AccountConstant.UseContantWeb, "使用协议");
        });
        mBinding.accountToPrivate.setOnClickListener(v -> {
            CommonH5Activity.startMe(RegistActivity.this, AccountConstant.UseUseWeb, "隐私协议"); // todo
        });
    }

    public boolean dispatchTouchEvent(MotionEvent event) {
        if (event.getAction() == MotionEvent.ACTION_DOWN &&
                getCurrentFocus() != null &&
                getCurrentFocus().getWindowToken() != null) {

            View v = getCurrentFocus();
            if (isShouldHideKeyboard(v, event)) {
                hideKeyboard(v.getWindowToken());
            }
        }
        return super.dispatchTouchEvent(event);
    }

    /**
     * 根据EditText所在坐标和用户点击的坐标相对比，来判断是否隐藏键盘，因为当用户点击EditText时则不能隐藏
     */
    private boolean isShouldHideKeyboard(View v, MotionEvent event) {
        if (v != null && (v instanceof EditText)) {
            int[] l = {0, 0};
            v.getLocationOnScreen(l);
            int left = l[0],
                    top = l[1],
                    bottom = top + v.getHeight(),
                    right = left + v.getWidth();
            if (event.getRawX() > left && event.getRawX() < right
                    && event.getRawY() > top && event.getRawY() < bottom) {
                // 点击EditText的事件，忽略它。
                return false;
            } else {
                return true;
            }
        }
        // 如果焦点不是EditText则忽略，这个发生在视图刚绘制完，第一个焦点不在EditText上，和用户用轨迹球选择其他的焦点
        return false;
    }

    /**
     * 获取InputMethodManager，隐藏软键盘
     */
    private void hideKeyboard(IBinder token) {
        if (token != null) {
            InputMethodManager mInputMethodManager = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
            mInputMethodManager.hideSoftInputFromWindow(token, InputMethodManager.HIDE_NOT_ALWAYS);
        }
    }


    @Override
    public void OnVmEnentListner(ViewEventResouce viewEventResouce) {
        super.OnVmEnentListner(viewEventResouce);
        switch (viewEventResouce.eventType) {
            case 103:
                verfi();
                break;
            case 105:
                ToastUtils.showShort("注册成功");
                UserInfoVo userInfoVo = CacheUtils.getUser();
                imService.setAlias(true);
                ARouter.getInstance().build(AppRouter.ACCOUNT_COMPLETE_INFO).navigation();

                //loginWithIm(userInfoVo.uuid, MD5Util.toMD5_32(userInfoVo.uuid));

                break;
            case 516:
                if (viewEventResouce.data != null) {
                    Log.d("sss", "OnVmEnentListner: " + viewEventResouce.data);
                }
                break;
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

    private void doRegister() {
        if (TextUtils.isEmpty(mBinding.getRegistParm().getPhone())) {
            ToastUtils.showShort("手机号不能为空");
            return;
        }
        if (TextUtils.isEmpty(mBinding.getRegistParm().getSmscode())) {
            ToastUtils.showShort("验证码不能为空");
            return;
        }
        if (TextUtils.isEmpty(mBinding.getRegistParm().getPwd())) {
            ToastUtils.showShort("密码不能为空");
            return;
        }
        mViewModel.register(mBinding.getRegistParm(), mType, area_code, wechatInfo, mBinding.getRegistParm().getPhone(), bindType);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            if (requestCode == 101) {
                area_code = data.getStringExtra("data");
                mBinding.tvNum.setText(data.getStringExtra("data"));
            }
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }
}
