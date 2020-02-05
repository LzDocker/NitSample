package com.docker.nitsample.ui;

import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.os.Bundle;

import com.alibaba.android.arouter.launcher.ARouter;
import com.bfhd.account.vm.AccountViewModel;
import com.docker.common.common.router.AppRouter;
import com.docker.common.common.ui.base.NitCommonActivity;
import com.docker.common.common.utils.cache.CacheUtils;
import com.docker.common.common.utils.tool.MD5Util;
import com.docker.common.common.vo.UserInfoVo;
import com.docker.module_im.DemoCache;
import com.docker.module_im.config.preference.UserPreferences;
import com.docker.module_im.session.SessionHelper;
import com.docker.nitsample.R;
import com.docker.nitsample.databinding.ActivitySplashBinding;
import com.gyf.immersionbar.ImmersionBar;
import com.netease.nim.uikit.api.NimUIKit;
import com.netease.nim.uikit.common.ToastHelper;
import com.netease.nimlib.sdk.NIMClient;
import com.netease.nimlib.sdk.NimIntent;
import com.netease.nimlib.sdk.RequestCallback;
import com.netease.nimlib.sdk.StatusBarNotificationConfig;
import com.netease.nimlib.sdk.auth.LoginInfo;
import com.netease.nimlib.sdk.msg.model.IMMessage;

import java.util.ArrayList;
import java.util.concurrent.TimeUnit;

import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;

/*
 * 闪屏界面
 * */
public class SplashActivity extends NitCommonActivity<AccountViewModel, ActivitySplashBinding> {

    private Disposable mdisposable;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_splash;
    }

    @Override
    public AccountViewModel getmViewModel() {
        return ViewModelProviders.of(this, factory).get(AccountViewModel.class);
    }

    @Override
    protected void inject() {
        super.inject();
        ARouter.getInstance().inject(this);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        isOverrideContentView = true;
        super.onCreate(savedInstanceState);
        // im 由于没接入推送，只存在app 运行状态可以拿到消息
        if (!isTaskRoot()) {
            // 拿到消息 处理im的消息，和激光无关
            Intent intent = getIntent();
            if (intent != null) {
                parseNotifyIntent(intent);
            }
            finish();
            return;
        }
//        if (!CacheUtils.getWelcomeFlag()) {//欢迎页
//            Intent intent = new Intent(this, WelocomeActivity.class);
//            startActivity(intent);
//            finish();
//            return;
//        }
        UserInfoVo userInfoVo = CacheUtils.getUser();
        timer(userInfoVo);
        mBinding.tvJump.setOnClickListener(v -> {
            if (mdisposable != null) {
                mdisposable.dispose();
            }
            if ("-1".equals(userInfoVo.uuid)) { // 未登录
                enterSystem();
                finish();
            } else { // 登录过
                mViewModel.AutoLoginV2();
            }
        });

        mViewModel.mUserAutoLoginLv.observe(this, userInfoVo1 -> {
            if (userInfoVo1 != null) {
                loginWithIm(userInfoVo1.uuid, MD5Util.toMD5_32(userInfoVo1.uuid));
            } else {
                enterSystem();
                finish();
            }
        });
    }

    private void timer(UserInfoVo userInfoVo) {
        final int count = 3;
        Observable.interval(0, 1, TimeUnit.SECONDS)
                .take(count)
                .map(aLong -> count - aLong)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<Long>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                        mdisposable = d;
                    }

                    @Override
                    public void onNext(Long number) {
                        mBinding.tvJump.setText("跳过 " + (number));
                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onComplete() {
                        if (userInfoVo == null) { // 未登录
                            ARouter.getInstance().build(AppRouter.ACCOUNT_LOGIN).navigation();
                        } else { // 登录过
                            mViewModel.AutoLoginV2();
                        }
                    }
                });
    }


    private void parseNotifyIntent(Intent intent) {
        if (intent.hasExtra(NimIntent.EXTRA_NOTIFY_CONTENT)) {
            ArrayList<IMMessage> messages = (ArrayList<IMMessage>) intent.getSerializableExtra(NimIntent.EXTRA_NOTIFY_CONTENT);
            if (messages == null || messages.size() > 1) {
                ARouter.getInstance().build(AppRouter.HOME).navigation(SplashActivity.this);
            } else {
                IMMessage message = (IMMessage) messages.get(0);
                intent.removeExtra(NimIntent.EXTRA_NOTIFY_CONTENT);
                switch (message.getSessionType()) {
                    case P2P:
                        SessionHelper.startP2PSession(this, message.getSessionId());
                        break;
//                case Team:
//                    SessionHelper.startTeamSession(this, message.getSessionId());
//                    break;
                }
            }
            return;
        }
    }

    private void initNotificationConfig() {
        // 初始化消息提醒
        NIMClient.toggleNotification(UserPreferences.getNotificationToggle());
        // 加载状态栏配置
        StatusBarNotificationConfig statusBarNotificationConfig = UserPreferences.getStatusConfig();
        if (statusBarNotificationConfig == null) {
            statusBarNotificationConfig = DemoCache.getNotificationConfig();
            UserPreferences.setStatusConfig(statusBarNotificationConfig);
        }
        // 更新配置
        NIMClient.updateStatusBarNotificationConfig(statusBarNotificationConfig);
    }


    public void enterSystem() {
        ARouter.getInstance().build(AppRouter.HOME).navigation(SplashActivity.this);
    }

    public void loginWithIm(String account, String token) {
        NimUIKit.login(new LoginInfo(account, token), new RequestCallback<LoginInfo>() {
            @Override
            public void onSuccess(LoginInfo param) {
                DemoCache.setAccount(account);
                // 初始化消息提醒配置
                initNotificationConfig();
                enterSystem();
                finish();
            }

            @Override
            public void onFailed(int code) {
                if (code == 302 || code == 404) {
                    ToastHelper.showToast(SplashActivity.this, "登录失败");
                } else {
                    ToastHelper.showToast(SplashActivity.this, "登录失败: " + code);
                }
                enterSystem();
                finish();
            }

            @Override
            public void onException(Throwable exception) {
                enterSystem();
                finish();
            }
        });
    }


    @Override
    public void initImmersionBar() {
        ImmersionBar.with(this)
                .transparentBar()
                .init();
    }

    @Override
    public void initView() {

    }

    @Override
    public void initObserver() {

    }

    @Override
    public void initRouter() {

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mdisposable != null) {
            mdisposable.dispose();
        }
    }
}
