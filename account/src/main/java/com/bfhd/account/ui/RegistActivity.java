package com.bfhd.account.ui;

import android.arch.lifecycle.ViewModelProvider;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.TextView;

import com.alibaba.android.arouter.launcher.ARouter;
import com.bfhd.account.R;
import com.bfhd.account.databinding.AccountActivityRegistBinding;
import com.bfhd.account.utils.AccountConstant;
import com.bfhd.account.vm.AccountViewModel;
import com.bfhd.account.vo.RegistParmVo;
import com.bfhd.circle.base.Constant;
import com.bfhd.circle.base.HivsBaseActivity;
import com.bfhd.circle.base.ViewEventResouce;
import com.bfhd.circle.ui.common.CommonH5Activity;
import com.dcbfhd.utilcode.utils.ToastUtils;
import com.docker.common.common.router.AppRouter;
import com.docker.common.common.utils.cache.CacheUtils;
import com.docker.common.common.vo.UserInfoVo;
import com.gyf.immersionbar.ImmersionBar;

import java.util.HashMap;
import java.util.Set;
import java.util.concurrent.TimeUnit;

import javax.inject.Inject;

import cn.jpush.android.api.JPushInterface;
import cn.jpush.android.api.TagAliasCallback;
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
//            if (mBinding.getRegistParm().getPhone().length() != 11) {
//                ToastUtils.showShort("手机号格式不正确！");
//                return;
//            }
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


    // 激光设置
    private void setAlias() {
        UserInfoVo userInfoVo = CacheUtils.getUser();
        if (!"-1".equals(userInfoVo.uuid)) {
            String alias = userInfoVo.uuid;
            if (TextUtils.isEmpty(alias)) {
                return;
            }
            if (!TextUtils.isEmpty(CacheUtils.getJpAlias())) {
                return;
            }
            mHandler.sendMessage(mHandler.obtainMessage(MSG_SET_ALIAS, alias));
        }
    }

    private String TAG = "jiguang";
    private final TagAliasCallback mAliasCallback = new TagAliasCallback() {
        @Override
        public void gotResult(int code, String alias, Set<String> tags) {
            String logs;
            switch (code) {
                case 0:
                    logs = "Set tag and alias success";
                    Log.i(TAG, logs);
                    // 建议这里往 SharePreference 里写一个成功设置的状态。成功设置一次后，以后不必再次设置了。
                    break;
                case 6002:
                    logs = "Failed to set alias and tags due to timeout. Try again after 60s.";
                    Log.i(TAG, logs);
                    // 延迟 60 秒来调用 Handler 设置别名
                    mHandler.sendMessageDelayed(mHandler.obtainMessage(MSG_SET_ALIAS, alias), 1000 * 60);
                    break;
                default:
                    logs = "Failed with errorCode = " + code;
                    Log.e(TAG, logs);
            }
        }
    };
    private static final int MSG_SET_ALIAS = 1001;
    private final Handler mHandler = new Handler() {
        @Override
        public void handleMessage(android.os.Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case MSG_SET_ALIAS:
                    Log.d(TAG, "Set alias in handler.");
                    // 调用 JPush 接口来设置别名。
                    JPushInterface.setAliasAndTags(getApplicationContext(),
                            (String) msg.obj,
                            null,
                            mAliasCallback);
                    break;
                default:
                    Log.i(TAG, "Unhandled msg - " + msg.what);
            }
        }
    };

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
                setAlias();
                ARouter.getInstance().build(AppRouter.ACCOUNT_COMPLETE_INFO).navigation();

//                loginWithIm(userInfoVo.uuid, MD5Util.toMD5_32(userInfoVo.uuid));
                break;
            case 516:
                if (viewEventResouce.data != null) {
                    Log.d("sss", "OnVmEnentListner: " + viewEventResouce.data);
                }
                break;
        }
    }

//    public void loginWithIm(String account, String token) {
//        NimUIKit.login(new LoginInfo(account, token), new RequestCallback<LoginInfo>() {
//            @Override
//            public void onSuccess(LoginInfo param) {
//                LogUtil.i("login success");
//                DemoCache.setAccount(account);
//                // 初始化消息提醒配置
//                initNotificationConfig();
////                // 进入主界面
////                MainActivity.start(LoginActivity.this, null);
//                ARouter.getInstance().build(AppRouter.HOME).navigation(RegistActivity.this);
//                finish();
//            }
//
//            @Override
//            public void onFailed(int code) {
//                if (code == 302 || code == 404) {
//                    ToastHelper.showToast(RegistActivity.this, R.string.login_failed);
//                } else {
//                    ToastHelper.showToast(RegistActivity.this, "im登录失败: " + code);
//                }
//                ARouter.getInstance().build(AppRouter.HOME).navigation(RegistActivity.this);
//                finish();
//            }
//
//            @Override
//            public void onException(Throwable exception) {
//                ToastHelper.showToast(RegistActivity.this, R.string.login_exception);
//                ARouter.getInstance().build(AppRouter.HOME).navigation(RegistActivity.this);
//                finish();
//            }
//        });
//    }

//    private void initNotificationConfig() {
//        // 初始化消息提醒
//        NIMClient.toggleNotification(UserPreferences.getNotificationToggle());
//        // 加载状态栏配置
//        StatusBarNotificationConfig statusBarNotificationConfig = UserPreferences.getStatusConfig();
//        if (statusBarNotificationConfig == null) {
//            statusBarNotificationConfig = DemoCache.getNotificationConfig();
//            UserPreferences.setStatusConfig(statusBarNotificationConfig);
//        }
//        // 更新配置
//        NIMClient.updateStatusBarNotificationConfig(statusBarNotificationConfig);
//    }


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
