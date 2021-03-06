package com.docker.nitsample.jpush;

import android.content.Context;
import android.util.Log;

import com.alibaba.android.arouter.launcher.ARouter;
import com.bfhd.account.utils.RouterUtils;
import com.bfhd.account.vo.CommentVo;
import com.dcbfhd.utilcode.utils.ActivityUtils;
import com.dcbfhd.utilcode.utils.AppUtils;
import com.dcbfhd.utilcode.utils.GsonUtils;
import com.dcbfhd.utilcode.utils.ToastUtils;
import com.docker.common.common.router.AppRouter;
import com.docker.common.common.utils.rxbus.RxBus;
import com.docker.common.common.utils.rxbus.RxEvent;
import com.docker.common.common.vo.entity.ParamsBean;
import com.docker.message.util.MessageRouterUtils;

import cn.jpush.android.api.JPushInterface;
import cn.jpush.android.api.NotificationMessage;
import cn.jpush.android.service.JPushMessageReceiver;
import me.leolin.shortcutbadger.ShortcutBadger;

public class MyJpushReceiver extends JPushMessageReceiver {
    @Override
    public void onNotifyMessageArrived(Context context, NotificationMessage notificationMessage) {
        super.onNotifyMessageArrived(context, notificationMessage);
        RxBus.getDefault().post(new RxEvent<>("Badger", 1));

        Log.d("sss", "onNotifyMessageArrived: ========onNotifyMessageArrived============" + notificationMessage.toString());
    }

    @Override
    public void onNotifyMessageOpened(Context context, NotificationMessage notificationMessage) {
        super.onNotifyMessageOpened(context, notificationMessage);

        Log.d("sss", "onNotifyMessageOpened: ====================" + notificationMessage.toString());
        ParamsBean paramsBean = GsonUtils.fromJson(notificationMessage.notificationExtras, ParamsBean.class);

        if ("quit".equals(paramsBean.getAct())) {
            ToastUtils.showShort("您的账号在其它设备登录，您已被迫下线！！");
            JPushInterface.deleteAlias(context, AppUtils.getAppVersionCode());
            JPushInterface.clearAllNotifications(context);
            ShortcutBadger.removeCount(context);
            ActivityUtils.finishAllActivities();
            if (AppUtils.isAppForeground()) {
                ARouter.getInstance().build(AppRouter.ACCOUNT_LOGIN).navigation();
            }
            return;
        }
//        if ("store".equals(paramsBean.getAct())) {
//            UserInfoVo userInfoVo = CacheUtils.getUser();
//            userInfoVo.reg_type = "2";
//            CacheUtils.saveUser(userInfoVo);
//        }
        MessageRouterUtils.Jump(paramsBean, true);
    }
}
