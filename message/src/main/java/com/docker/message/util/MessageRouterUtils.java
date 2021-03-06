package com.docker.message.util;

import android.text.TextUtils;

import com.alibaba.android.arouter.launcher.ARouter;
import com.docker.cirlev2.vo.param.StaDetailParam;
import com.docker.common.common.config.Constant;
import com.docker.common.common.model.CommonListOptions;
import com.docker.common.common.router.AppRouter;
import com.docker.common.common.utils.cache.CacheUtils;
import com.docker.common.common.vo.UserInfoVo;
import com.docker.common.common.vo.entity.ParamsBean;
import com.docker.message.vo.MessageListVoV2;

public class MessageRouterUtils {


    public static void Jump(ParamsBean paramsBean, boolean isPush) {

        if (CacheUtils.getUser() == null) {
            return;
        }

        if ("quit".equals(paramsBean.getAct())) {
//            ARouter.getInstance().build(AppRouter.Account.ACCOUNT_LOGIN).navigation();
            return;
        }

        if ("dynamicFavour".equals(paramsBean.getAct())
                || "dynamicCollect".equals(paramsBean.getAct())
                || "dynamicComment".equals(paramsBean.getAct())
                || "dynamicCommentReply".equals(paramsBean.getAct())
        ) { // 点赞 收藏 评论 回复
            StaDetailParam staDetailParam = new StaDetailParam();
            staDetailParam.dynamicId = paramsBean.getDynamicid();
            ARouter.getInstance().build(AppRouter.CIRCLE_dynamic_v2_detail).withString("dynamicId", paramsBean.getDynamicid()).navigation();
//            ARouter.getInstance().build(AppRouter.CIRCLE_DETAIL).withSerializable("mStaparam", staDetailParam).navigation();
            return;
        }


        // 关注
        if ("userFocus".equals(paramsBean.getAct()) && isPush) {
            //
            CommonListOptions options = new CommonListOptions();
            options.refreshState = 0;
            options.ReqParam.put("type", "1");
            options.ReqParam.put("memberid", CacheUtils.getUser().uid);
            ARouter.getInstance().build(AppRouter.MESSAGELISTACT)
                    .withSerializable(Constant.CommonListParam, options)
                    .withString("title", "关注")
                    .navigation();
//            ARouter.getInstance().build(AppRouter.ACCOUNT_SYSTEM_sMESSAGE).withString("type", "1").navigation();
            return;
        }

        if ("message".equals(paramsBean.getType()) && isPush) {
            //

            CommonListOptions options = new CommonListOptions();
            options.refreshState = 0;
            options.ReqParam.put("type", "1");
            options.ReqParam.put("memberid", CacheUtils.getUser().uid);
            ARouter.getInstance().build(AppRouter.MESSAGELISTACT)
                    .withSerializable(Constant.CommonListParam, options)
                    .withString("title", "系统通知")
                    .navigation();

//            ARouter.getInstance().build(AppRouter.ACCOUNT_SYSTEM_sMESSAGE).withString("type", "1").navigation();
            return;
        }

        // 发货
        if ("order".equals(paramsBean.getAct())) {
            //
            if ("-1".equals(CacheUtils.getUser().memberid)) {
                return;
            }
            String ispush = "1";
            if (paramsBean.getPush_uuid().equals(CacheUtils.getUser().uuid)) {
                ispush = "1";
            } else {
                ispush = "0";
            }
            ARouter.getInstance().build(AppRouter.ORDER_DETAIL).withString("orderid", paramsBean.orderid).navigation();
//            ARouter.getInstance().build(AppRouter.Pro_order_detail)
//                    .withString("status", paramsBean.status)
//                    .withString("is_push", ispush)
//                    .withString("orderId", paramsBean.orderid)
//                    .navigation();
            return;
        }

        // 审核通过
        if ("store".equals(paramsBean.getAct()) && isPush) {
            //
            UserInfoVo userInfoVo = CacheUtils.getUser();
            userInfoVo.reg_type = "2";
            CacheUtils.saveUser(userInfoVo);
            ARouter.getInstance().build(AppRouter.ACCOUNT_SYSTEM_sMESSAGE).withString("type", "1").navigation();
            return;
        }
        // 活动
        if ("activity".equals(paramsBean.getAct())) {
            ARouter.getInstance().build(AppRouter.ACTIVE_DEATIL_ACTIVITY).withString("activityid", paramsBean.getDataid()).navigation();
            return;
        }

        if ("message".equals(paramsBean.getType())) {
            //
            ARouter.getInstance().build(AppRouter.ACCOUNT_SYSTEM_sMESSAGE).withString("type", "1").navigation();
            return;
        }

        if (TextUtils.isEmpty(paramsBean.getType()) && isPush) {
            ARouter.getInstance().build(AppRouter.HOME).navigation();
            return;
        }
        if (TextUtils.isEmpty(paramsBean.getType()) || TextUtils.isEmpty(paramsBean.getDynamicid())) {
            return;
        }
    }

}
