package com.docker.videobasic.util;

import android.text.TextUtils;

import com.docker.videobasic.vo.VideoEntityVo;

public class ImgUrlUtil {


    public static String getCompleteImageUrl(VideoEntityVo videoEntityVo) {//云端的图片
        if (videoEntityVo == null) {
            return "";
        }
        if (videoEntityVo.getExtData() == null) {
            return "";
        }
        if (videoEntityVo.getExtData().getResource() == null) {
            return "";
        }
        if (videoEntityVo.getExtData().getResource().size() == 0) {
            return "";
        }
        String url = videoEntityVo.getExtData().getResource().get(0).getImg();

        if (TextUtils.isEmpty(url)) {
            return "";
        } else if (url.contains("http")) {
            return url;
        } else {
            return "https://jinxi6.oss-cn-beijing.aliyuncs.com/static" + url;
        }
    }

    public static String getCompleteVideoUrl(VideoEntityVo videoEntityVo) {//云端的图片
        if (videoEntityVo == null) {
            return "";
        }
        if (videoEntityVo.getExtData() == null) {
            return "";
        }
        if (videoEntityVo.getExtData().getResource() == null) {
            return "";
        }
        if (videoEntityVo.getExtData().getResource().size() == 0) {
            return "";
        }
        String url = videoEntityVo.getExtData().getResource().get(0).getUrl();

        if (TextUtils.isEmpty(url)) {
            return "";
        } else if (url.contains("http")) {
            return url;
        } else {
            return "https://jinxi6.oss-cn-beijing.aliyuncs.com/static" + url;
        }
    }
}
