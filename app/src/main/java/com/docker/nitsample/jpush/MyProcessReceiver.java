package com.docker.nitsample.jpush;

import android.app.NotificationManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import com.docker.core.util.AppExecutors;

import cn.jpush.android.api.JPushInterface;
import cn.jpush.android.helper.Logger;

import static android.content.Context.NOTIFICATION_SERVICE;

public class MyProcessReceiver extends BroadcastReceiver {

    private static final String TAG = "MyReceiver";

    private NotificationManager nm;

    AppExecutors appExecutors = new AppExecutors();

    @Override
    public void onReceive(Context context, Intent intent) {
        if (null == nm) {
            nm = (NotificationManager) context.getSystemService(NOTIFICATION_SERVICE);
        }

        Bundle bundle = intent.getExtras();
        Logger.d(TAG, "onReceive - " + intent.getAction() + ", extras: ");

        if (JPushInterface.ACTION_REGISTRATION_ID.equals(intent.getAction())) {
            Logger.d(TAG, "JPush 用户注册成功");

        } else if (JPushInterface.ACTION_MESSAGE_RECEIVED.equals(intent.getAction())) {
            Logger.d(TAG, "接受到推送下来的自定义消息");

            String extras = bundle.getString(JPushInterface.EXTRA_EXTRA);
//            CollectVo.ParamsBean paramsBean = GsonUtils.fromJson(extras, CollectVo.ParamsBean.class);
//            if ("quit".equals(paramsBean.getAct())) {
//                ToastUtils.showShort("您的账号在其它设备登录，您已被迫下线！！");
//                JPushInterface.deleteAlias(context, AppUtils.getAppVersionCode());
//                JPushInterface.clearAllNotifications(context);
//                ShortcutBadger.removeCount(context);
//                ActivityUtils.finishAllActivities();
//                if (AppUtils.isAppForeground()) {
//                    ARouter.getInstance().build(AppRouter.ACCOUNT_LOGIN).navigation();
//                }
//            }
//            if ("store".equals(paramsBean.getAct())) {
//                UserInfoVo userInfoVo = CacheUtils.getUser();
//                userInfoVo.reg_type = "2";
//                CacheUtils.saveUser(userInfoVo);
//            }
//            RouterUtils.Jump(paramsBean, true);

        } else if (JPushInterface.ACTION_NOTIFICATION_RECEIVED.equals(intent.getAction())) {
            Logger.d(TAG, "接受到推送下来的通知");

            receivingNotification(context, bundle);

        } else if (JPushInterface.ACTION_NOTIFICATION_OPENED.equals(intent.getAction())) {
            Logger.d(TAG, "用户点击打开了通知");

//            openNotification(context, bundle);

        } else {
            Logger.d(TAG, "Unhandled intent - " + intent.getAction());
        }
    }

    private void receivingNotification(Context context, Bundle bundle) {
        String title = bundle.getString(JPushInterface.EXTRA_NOTIFICATION_TITLE);
        Logger.d(TAG, " title : " + title);
        String message = bundle.getString(JPushInterface.EXTRA_ALERT);
        Logger.d(TAG, "message : " + message);
        String extras = bundle.getString(JPushInterface.EXTRA_EXTRA);
        Logger.d(TAG, "extras : " + extras);
//        RxBus.getDefault().post(new RxEvent<>("Badger", 1));


//        processCustomMessage(context,bundle);
    }

//    private void openNotification(Context context, Bundle bundle) {
//        String extras = bundle.getString(JPushInterface.EXTRA_EXTRA);
//        CommentVo.ParamsBean paramsBean = GsonUtils.fromJson(extras, CommentVo.ParamsBean.class);
//        RouterUtils.Jump(paramsBean, true);
//    }

    // 自定义通知栏
//    private void processCustomMessage(Context context, Bundle bundle) {
//        String channelID = "1";
//        String channelName = "channel_name";
//
//        // 跳转的Activity
//        Intent intent = new Intent(context, HomeActivity.class);
//        intent.putExtras(bundle);
//        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
//        PendingIntent pendingIntent = PendingIntent.getActivity(context, 0, intent, 0);
//
//        // 获得系统推送的自定义消息
//        String message = bundle.getString(JPushInterface.EXTRA_MESSAGE);
//
//        NotificationManager notificationManager = (NotificationManager) context.getSystemService(NOTIFICATION_SERVICE);
//
//        //适配安卓8.0的消息渠道
//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
//            NotificationChannel channel = new NotificationChannel(channelID, channelName, NotificationManager.IMPORTANCE_HIGH);
//            notificationManager.createNotificationChannel(channel);
//        }
//
//        NotificationCompat.Builder notification =
//                new NotificationCompat.Builder(context, channelID);
//        notification.setAutoCancel(true)
//                .setContentText(message)
//                .setContentTitle("我是Title")
//                .setSmallIcon(R.mipmap.yingyongguanli)
//                .setDefaults(Notification.DEFAULT_ALL)
//                .setContentIntent(pendingIntent);
//
//        notificationManager.notify((int) (System.currentTimeMillis() / 1000), notification.build());
//
//    }
}
