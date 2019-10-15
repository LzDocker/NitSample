package com.bfhd.account.utils;

import android.text.TextUtils;

import com.alibaba.android.arouter.launcher.ARouter;
import com.bfhd.account.vo.CommentVo;
import com.bfhd.circle.vo.bean.StaDetailParam;
import com.docker.common.common.router.AppRouter;
import com.docker.common.common.utils.cache.CacheUtils;
import com.docker.common.common.vo.UserInfoVo;

public class RouterUtils {


    public static void Jump(CommentVo.ParamsBean paramsBean, boolean isPush) {


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
            ARouter.getInstance().build(AppRouter.CIRCLE_DETAIL).withSerializable("mStaparam", staDetailParam).navigation();
            return;
        }


        // 关注
        if ("userFocus".equals(paramsBean.getAct()) && isPush) {
            //
            ARouter.getInstance().build(AppRouter.ACCOUNT_SYSTEM_sMESSAGE).navigation();
            return;
        }

        if ("message".equals(paramsBean.getType()) && isPush) {
            //
            ARouter.getInstance().build(AppRouter.ACCOUNT_SYSTEM_sMESSAGE).navigation();
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
            ARouter.getInstance().build(AppRouter.ACCOUNT_SYSTEM_sMESSAGE).navigation();
            return;
        }

        if ("message".equals(paramsBean.getType())) {
            //
            ARouter.getInstance().build(AppRouter.ACCOUNT_SYSTEM_sMESSAGE).navigation();
            return;
        }

        if (TextUtils.isEmpty(paramsBean.getType()) && isPush) {
            ARouter.getInstance().build(AppRouter.HOME).navigation();
            return;
        }
        if (TextUtils.isEmpty(paramsBean.getType()) || TextUtils.isEmpty(paramsBean.getDynamicid())) {
            return;
        }
        StaDetailParam staDetailParam = new StaDetailParam();
        switch (paramsBean.getType()) {
            case "safe":
                staDetailParam.uiType = 1;
                staDetailParam.isRecomend = true;
                break;
            case "answer":
                staDetailParam.uiType = 2;
                staDetailParam.speical = 1;
                break;
            case "dynamic":
                staDetailParam.uiType = 0;
                staDetailParam.speical = 1;
                break;
            case "news":
                staDetailParam.uiType = 1;
                staDetailParam.speical = 1;
                break;
            case "event":
                staDetailParam.uiType = 3;
                staDetailParam.isRecomend = true;
                break;
            case "shared":
                staDetailParam.isRecomend = true;
                staDetailParam.uiType = 0;
                break;
            case "alarm":
                staDetailParam.isRecomend = true;
                staDetailParam.uiType = 3;
                break;
            case "report":
                staDetailParam.isRecomend = true;
                staDetailParam.uiType = 3;
                break;
            case "sentiment": // 舆情监控
                staDetailParam.isRecomend = true;
                staDetailParam.uiType = 3;
                break;
            case "emergency": // 应急管理  详情是commonh5
            case "management": // 管理要求
//                staDetailParam.isRecomend = true;
//                staDetailParam.uiType = 3;
//                if (1 == 1) {
//                    String weburl = Constant.getCompletePdf(item.getExtData().getFile_path());
//                    String extension = FileUtils.getFileExtension(item.getExtData().getFile_path());
//                    String filename = EncryptUtils.encryptMD5ToString(weburl) + "." + extension;
//                    ARouter.getInstance().build(AppRouter.ViewDoc.ViewDoc_Document).withString("fileURL", weburl)
//                            .withString("fileName", filename)
//                            .navigation();
//                    return;
//                }
                break;
            default:
                staDetailParam.uiType = 1;
                break;
        }
        staDetailParam.dynamicId = paramsBean.getDynamicid();
        staDetailParam.type = paramsBean.getType();
        ARouter.getInstance().build(AppRouter.CIRCLE_DETAIL).withSerializable("mStaparam", staDetailParam).navigation();
    }
}
