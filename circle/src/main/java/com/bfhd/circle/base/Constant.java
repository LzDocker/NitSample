package com.bfhd.circle.base;


import android.os.Environment;
import android.text.TextUtils;

import com.dcbfhd.utilcode.utils.AppUtils;
import com.dcbfhd.utilcode.utils.EncryptUtils;

/*
 *
 * 统一协议
 * */
public class Constant {


    // 微信
//    public static final String WxAppid = "wx83f069fcdea26ca4";
    public static final String WxAppid = "wxaa486294063f057d";
    public static final String Wxsecret = "d7c817b7a302ce6aad7217922cef2be2";
    public static final String QQID = "101780103";
    public static final String QQKey = "42113368dbc1de3b734f0a96956369fe";
    // 讯飞ID
    public static final String IFLAYID = "=5afbf83a";
    public static final String Umeng = "5d0300984ca3579a45000b17";

    public static final String BaseServeTest = "http://tygs.wgc360.com/";
//    public static final String BaseServeTest = " http://app.cosri.org.cn/";

    public static final String endpoint = "http://tygsapp.oss-cn-beijing.aliyuncs.com";
    public static final String mBaseImageUrl = endpoint;//测试地址
    public static final String mOSSBaseImageUrl = endpoint;

    public static String getCompleteImageUrl(String url) {//云端的图片
        if (TextUtils.isEmpty(url)) {
            return "";
        } else if (url.contains("http")) {
            return url;
        } else {
            return endpoint + url;
        }
    }


    public static String getAudioUrl(String url) {

        String utl = getCompleteImageUrl(url);
        if (utl.contains("https")) {
            String newUrl = utl.substring(0, 4) + utl.substring(5, utl.length());
            return newUrl;
        }
        return utl;

    }


    public static String getCompletePdf(String url) {
        if (TextUtils.isEmpty(url)) {
            return "";
        } else if (url.contains("http")) {
            return url;
        } else {
            return BaseServeTest + url;
        }
    }

    /*
     * circle
     * */

    public static String BaseFileFloder = Environment.getExternalStorageDirectory() + "/" + AppUtils.getAppName() + "/chache/";
    public static String URL_DANGROUS = BaseServeTest + "index.php?m=default.country_risk_detail&id="; //国家风险详情

    public static String getWebUrlWj(String type, String memberid, String uuid, String dynamicid) {
//        String sign = EncryptUtils.encryptMD5ToString(type + Dataid).toLowerCase();
        String url = "";
        switch (type) {
            case "car":
            case "goods":
            case "time":
            case "datetime":
            case "product":
            case "project":
            case "house": //todo
            case "dynamic":
                url = BaseServeTest + "index.php?m=default.goods_info" + "&memberid=" + memberid + "&uuid=" + uuid + "&dynamicid=" + dynamicid;
                break;
        }
        return url;
    }

    public static String getWebUrl(String type, String Dataid) {
        String sign = EncryptUtils.encryptMD5ToString(type + Dataid).toLowerCase();
        String url = BaseServeTest + "api.php?m=h5.app_detail" + "&type=" + type + "&dataid=" + Dataid + "&sign=" + sign;
//        switch (type) {
//            case "event":
//                url = URL_EVENT_REPORT;
//                break;
//            case "alarm":
//                url = URL_PUSH;
//                break;
//            case "report":
//                url = URL_CUSTOM;
//                break;
//            case "sentiment":
//                url = URL_MONINTER;
//                break;
//            case "risk":
//                url = URL_DANGROUS;
//                break;
//        }
        return url;
    }


}
