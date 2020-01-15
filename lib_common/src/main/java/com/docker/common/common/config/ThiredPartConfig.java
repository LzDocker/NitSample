package com.docker.common.common.config;

import android.app.Notification;

import com.alibaba.android.arouter.launcher.ARouter;
import com.alivc.player.AliVcMediaPlayer;
import com.alivc.player.VcPlayerLog;
import com.baidu.mapapi.CoordType;
import com.baidu.mapapi.SDKInitializer;
import com.dcbfhd.utilcode.utils.Utils;
import com.docker.common.R;
import com.docker.core.base.BaseApp;
import com.docker.video.AlivcPlayer.AlivcPlayer;
import com.docker.video.config.PlayerConfig;
import com.docker.video.config.PlayerLibrary;
import com.docker.video.entity.DecoderPlan;
import com.tencent.smtt.sdk.QbSdk;
import com.umeng.commonsdk.UMConfigure;
import com.umeng.socialize.PlatformConfig;

import cn.jpush.android.api.BasicPushNotificationBuilder;
import cn.jpush.android.api.JPushInterface;
import timber.log.Timber;

public class ThiredPartConfig {
    // 微信
    public static final String WxAppid = "wxaa486294063f057d";
    public static final String Wxsecret = "d7c817b7a302ce6aad7217922cef2be2";
    // qq
    public static final String QQID = "101780103";
    public static final String QQKey = "42113368dbc1de3b734f0a96956369fe";
    // 讯飞ID
    public static final String IFLAYID = "=5afbf83a";
    //友盟
    public static final String Umeng = "5d0300984ca3579a45000b17";

    // oss
//    public static final String BaseImageUrl = "http://taijistar.oss-cn-beijing.aliyuncs.com";
    public static final String BaseImageUrl = "http://tygsapp.oss-cn-beijing.aliyuncs.com";

    public static final String endpoint = "https://oss-cn-beijing.aliyuncs.com";
    //    public static final String accessKeyId = "LTAIWijAvXa7BmAt";
    public static final String accessKeyId = "LTAI4FxpjPKZocMo4X8RdYYS";
    //    public static final String accessKeySecret = "RYDaCj2um1nlhYgfyVH6JRceq4ypwO";
    public static final String accessKeySecret = "WmFNwtSR6du990Umh9Gg0klvZGZXc6";
    //    public static final String P_BUCKETNAME = "taijistar";
    public static final String P_BUCKETNAME = "tygsapp";

    public static final String FLODER = "static/var/upload/img";
    public static final String FLODERVIDEO = "static/var/upload/video";
    //    public static final String BaseServeUrl = "http://htj.wgc360.com/";
    public static final String BaseServeUrl = "http://tygs.wgc360.com/";

    public static final String ServeUrl = "http://tygs.wgc360.com/";


    public static void init(BaseApp app) {
        Utils.init(app);
        // 友盟
        UMConfigure.init(app, Umeng
                , "umeng", UMConfigure.DEVICE_TYPE_PHONE, "");//58edcfeb310c93091c000be2 5965ee00734be40b580001a0
        UMConfigure.setLogEnabled(true);
        // 微信
        PlatformConfig.setWeixin(WxAppid, Wxsecret);
        PlatformConfig.setQQZone(QQID, QQKey);
        // 百度地图
        SDKInitializer.initialize(app);
        SDKInitializer.setCoordType(CoordType.BD09LL);
        //非wifi情况下，主动下载x5内核
        QbSdk.setDownloadWithoutWifi(true);
        //搜集本地tbs内核信息并上报服务器，服务器返回结果决定使用哪个内核。
        QbSdk.PreInitCallback cb = new QbSdk.PreInitCallback() {
            @Override
            public void onViewInitFinished(boolean arg0) {
                //x5內核初始化完成的回调，为true表示x5内核加载成功，否则表示x5内核加载失败，会自动切换到系统内核。
                Timber.d("开启TBS===X5加速成功");
            }

            @Override
            public void onCoreInitFinished() {
            }
        };
        //x5内核初始化接口
        QbSdk.initX5Environment(app, cb);
        // 极光推送
        JPushInterface.setDebugMode(false);
        JPushInterface.init(app);
        BasicPushNotificationBuilder builder = new BasicPushNotificationBuilder(app);
        builder.statusBarDrawable = R.mipmap.ic_toolbar_back;
        builder.notificationFlags = Notification.FLAG_AUTO_CANCEL
                | Notification.FLAG_SHOW_LIGHTS;  //设置为自动消失和呼吸灯闪烁
        builder.notificationDefaults = Notification.DEFAULT_SOUND
                | Notification.DEFAULT_VIBRATE
                | Notification.DEFAULT_LIGHTS;  // 设置为铃声、震动、呼吸灯闪烁都要
        JPushInterface.setDefaultPushNotificationBuilder(builder);

        Timber.plant(new Timber.DebugTree());

        ARouter.init(app);

        // -------------video----------------------------
        //查看log
        VcPlayerLog.enableLog();
        //初始化播放器
        AliVcMediaPlayer.init(app);
        PlayerConfig.addDecoderPlan(new DecoderPlan(1, AlivcPlayer.class.getName(), "AlivcPlayer"));
        PlayerConfig.setDefaultPlanId(1);
        //use default NetworkEventProducer.
        PlayerConfig.setUseDefaultNetworkEventProducer(true);
        PlayerLibrary.init(app);
        //-----------video---------------------------------

    }
}
