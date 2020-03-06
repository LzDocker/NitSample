package com.docker.module_im.init;

import android.content.Context;

import com.docker.common.common.utils.cache.CacheUtils;
import com.docker.common.common.utils.tool.MD5Util;
import com.docker.common.common.vo.UserInfoVo;
import com.docker.module_im.DemoCache;
import com.docker.module_im.NIMInitManager;
import com.docker.module_im.common.util.crash.AppCrashHandler;
import com.docker.module_im.config.preference.UserPreferences;
import com.docker.module_im.contact.ContactHelper;
import com.docker.module_im.event.DemoOnlineStateContentProvider;
import com.docker.module_im.mixpush.DemoPushContentProvider;
import com.docker.module_im.session.SessionHelper;
import com.netease.nim.uikit.api.NimUIKit;
import com.netease.nim.uikit.api.UIKitOptions;
import com.netease.nim.uikit.business.contact.core.query.PinYin;
import com.netease.nimlib.sdk.NIMClient;
import com.netease.nimlib.sdk.auth.LoginInfo;
import com.netease.nimlib.sdk.util.NIMUtil;

//import com.netease.nimlib.sdk.mixpush.NIMPushClient;

public class NimInitManagerCore {


    public  void init(Context context){

        DemoCache.setContext(context);
        // 4.6.0 开始，第三方推送配置入口改为 SDKOption#mixPushConfig，旧版配置方式依旧支持。
        NIMClient.init(context, getLoginInfo(), NimSDKOptionConfig.getSDKOptions(context));

        // crash handler
        AppCrashHandler.getInstance(context);

        // 以下逻辑只在主进程初始化时执行
        if (NIMUtil.isMainProcess(context)) {

            // 注册自定义推送消息处理，这个是可选项
//            NIMPushClient.registerMixPushMessageHandler(new DemoMixPushMessageHandler());

            // 初始化红包模块，在初始化UIKit模块之前执行
//            NIMRedPacketClient.init(this);
            // init pinyin
            PinYin.init(context);
            PinYin.validate();
            // 初始化UIKit模块
            initUIKit(context);
            // 初始化消息提醒
            NIMClient.toggleNotification(UserPreferences.getNotificationToggle());
            //关闭撤回消息提醒
            NIMClient.toggleRevokeMessageNotification(false);
            // 云信sdk相关业务初始化
            NIMInitManager.getInstance().init(true);
            // 初始化音视频模块
//            initAVChatKit();
            // 初始化rts模块
//            initRTSKit();
        }
    }

    /**
     * 注意：每个进程都会创建自己的Application 然后调用onCreate() 方法，
     * 如果用户有自己的逻辑需要写在Application#onCreate()（还有Application的其他方法）中，一定要注意判断进程，不能把业务逻辑写在core进程，
     * 理论上，core进程的Application#onCreate()（还有Application的其他方法）只能做与im sdk 相关的工作
     */


    private LoginInfo getLoginInfo() {

        UserInfoVo userInfoVo = CacheUtils.getUser();
        if(userInfoVo!=null){
            return new LoginInfo(userInfoVo.uuid, MD5Util.toMD5_32(userInfoVo.uuid));
        }else {
            return null;
        }
    }

    private void initUIKit(Context context) {
        // 初始化
        NimUIKit.init(context, buildUIKitOptions(context));

        // 设置地理位置提供者。如果需要发送地理位置消息，该参数必须提供。如果不需要，可以忽略。
//        NimUIKit.setLocationProvider(new ImLocationProvider());

        // IM 会话窗口的定制初始化。
        SessionHelper.init();

        // 聊天室聊天窗口的定制初始化。
//        ChatRoomSessionHelper.init();

        // 通讯录列表定制初始化
        ContactHelper.init();

        // 添加自定义推送文案以及选项，请开发者在各端（Android、IOS、PC、Web）消息发送时保持一致，以免出现通知不一致的情况
        NimUIKit.setCustomPushContentProvider(new DemoPushContentProvider());

        NimUIKit.setOnlineStateContentProvider(new DemoOnlineStateContentProvider());
    }

    private UIKitOptions buildUIKitOptions(Context context) {
        UIKitOptions options = new UIKitOptions();
        // 设置app图片/音频/日志等缓存目录
        options.appCacheDir = NimSDKOptionConfig.getAppCacheDir(context) + "/app";
        return options;
    }

//    private void initAVChatKit() {
//        AVChatOptions avChatOptions = new AVChatOptions() {
//            @Override
//            public void logout(Context context) {
//                MainActivity.logout(context, true);
//            }
//        };
//        avChatOptions.entranceActivity = WelcomeActivity.class;
//        avChatOptions.notificationIconRes = R.drawable.ic_stat_notify_msg;
//        AVChatKit.init(avChatOptions);
//
//        // 初始化日志系统
//        LogHelper.init();
//        // 设置用户相关资料提供者
//        AVChatKit.setUserInfoProvider(new IUserInfoProvider() {
//            @Override
//            public UserInfo getUserInfo(String account) {
//                return NimUIKit.getUserInfoProvider().getUserInfo(account);
//            }
//
//            @Override
//            public String getUserDisplayName(String account) {
//                return UserInfoHelper.getUserDisplayName(account);
//            }
//        });
//        // 设置群组数据提供者
//        AVChatKit.setTeamDataProvider(new ITeamDataProvider() {
//            @Override
//            public String getDisplayNameWithoutMe(String teamId, String account) {
//                return TeamHelper.getDisplayNameWithoutMe(teamId, account);
//            }
//
//            @Override
//            public String getTeamMemberDisplayName(String teamId, String account) {
//                return TeamHelper.getTeamMemberDisplayName(teamId, account);
//            }
//        });
//    }
//
//    private void initRTSKit() {
//        RTSOptions rtsOptions = new RTSOptions() {
//            @Override
//            public void logout(Context context) {
//                MainActivity.logout(context, true);
//            }
//        };
//        RTSKit.init(rtsOptions);
//        RTSHelper.init();
//    }
}
