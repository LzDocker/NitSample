package com.bfhd.account.utils;

import android.text.TextUtils;

import com.bfhd.account.vo.CommentVo;

import java.util.List;


public class Utils {

    public static final String endpoint = "http://taijistar.oss-cn-beijing.aliyuncs.com";
    public static final String mOSSBaseImageUrl = endpoint + "/static";

    public static String getCompleteImageUrl(List<CommentVo.ImgsBean> imgList) {//云端的图片

        if (imgList != null && imgList.size() > 0) {
            CommentVo.ImgsBean imgsBean = imgList.get(0);
            String url = imgsBean.getImg();
            if (TextUtils.isEmpty(url)) {
                return "";
            } else if (url.contains("http")) {
                return url;
            } else {
                if (url.startsWith("/var")) {
                    return mOSSBaseImageUrl + url;
                } else {
                    return endpoint + url;
                }
            }
        } else {
            return "";
        }

    }
}
