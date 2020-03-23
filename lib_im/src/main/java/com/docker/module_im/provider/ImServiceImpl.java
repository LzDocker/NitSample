package com.docker.module_im.provider;

import android.content.Context;
import android.os.Handler;
import android.text.TextUtils;
import android.util.Log;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.dcbfhd.utilcode.utils.ActivityUtils;
import com.docker.common.common.command.ReplyCommandParam;
import com.docker.common.common.provider.MessageService;
import com.docker.common.common.router.AppRouter;
import com.docker.common.common.utils.cache.CacheUtils;
import com.docker.common.common.vo.UserInfoVo;
import com.docker.module_im.DemoCache;
import com.docker.module_im.config.preference.UserPreferences;
import com.docker.module_im.login.LogoutHelper;
import com.docker.module_im.session.SessionHelper;
import com.netease.nim.uikit.api.NimUIKit;
import com.netease.nimlib.sdk.NIMClient;
import com.netease.nimlib.sdk.RequestCallback;
import com.netease.nimlib.sdk.StatusBarNotificationConfig;
import com.netease.nimlib.sdk.auth.LoginInfo;

import java.util.Set;

import cn.jpush.android.api.JPushInterface;
import cn.jpush.android.api.TagAliasCallback;

import static com.docker.video.config.AppContextAttach.getApplicationContext;


@Route(path = AppRouter.IMPROVIDER_TALK, name = "message")
public class ImServiceImpl implements MessageService {

    @Override
    public void enterToTalk(String uid) {
        SessionHelper.startP2PSession(ActivityUtils.getTopActivity(), uid);
    }

    @Override
    public void loginIm(String account, String token, ReplyCommandParam<Integer> replyCommandParam) {
        loginWithIm(account, token, replyCommandParam);
    }

    @Override
    public void setAlias(boolean isFocus) {
        if (isFocus) {
            setAlias();
        }
    }

    @Override
    public void loginOut() {
        LogoutHelper.logout();
    }

    @Override
    public void init(Context context) {

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
                    Log.d("sss", "setAlias: ==============3333");
                    logs = "Set tag and alias success";
                    Log.i(TAG, logs);
                    // 建议这里往 SharePreference 里写一个成功设置的状态。成功设置一次后，以后不必再次设置了。
                    break;
                case 6002:
                    Log.d("sss", "setAlias: ==============4444");
                    logs = "Failed to set alias and tags due to timeout. Try again after 60s.";
                    Log.i(TAG, logs);
                    // 延迟 60 秒来调用 Handler 设置别名
                    mHandler.sendMessageDelayed(mHandler.obtainMessage(MSG_SET_ALIAS, alias), 1000 * 60);
                    break;
                default:
                    logs = "Failed with errorCode = " + code;
                    Log.d("sss", "setAlias: ==============555" + logs);
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


    private void loginWithIm(String account, String token, ReplyCommandParam<Integer> replyCommandParam) {
        NimUIKit.login(new LoginInfo(account, token), new RequestCallback<LoginInfo>() {
            @Override
            public void onSuccess(LoginInfo param) {
                DemoCache.setAccount(account);
                // 初始化消息提醒配置
                initNotificationConfig();
                replyCommandParam.exectue(0);
            }

            @Override
            public void onFailed(int code) {
                replyCommandParam.exectue(1);
            }

            @Override
            public void onException(Throwable exception) {
                replyCommandParam.exectue(2);
            }
        });
    }
}
