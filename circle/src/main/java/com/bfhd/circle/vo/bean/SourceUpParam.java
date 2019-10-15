package com.bfhd.circle.vo.bean;

import android.databinding.ObservableField;
import android.widget.ImageView;

import com.bfhd.circle.R;
import com.dcbfhd.utilcode.utils.ScreenUtils;
import com.luck.picture.lib.config.PictureConfig;
import com.yalantis.ucrop.UCrop;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/*
选择资源入参
* */
public class SourceUpParam implements Serializable {


    public static final int SOURCE_TYPE_IMG_ONLY = 101;   // 图片
    public static final int SOURCE_TYPE_VIDEO_ONLY = 102; // 视频
    public static final int SOURCE_TYPE_AUTO = 103;    // 两者可同时
    public static final int SOURCE_TYPE_ONE_TYPE = 104; // 只能一种根据第一次选的类型决定之后的类型

    public ImageView mSingleImgview;

    // 资源类型
    public int sourceType = SOURCE_TYPE_IMG_ONLY;

    public boolean needCrop = false;

    // 裁剪完的宽度 默认屏幕宽度一致
    public int width = ScreenUtils.getScreenWidth();

    public int height = 400;

    // 最多选择个数
    public int sourceMaxNum = 9;

    // 状态  0 初始化状态  1 正在上传  2 上传成功  3上传失败
    public ObservableField<Integer> status = new ObservableField<>();

    public List<String> urlList = new ArrayList<>();//上传后的资源路径

    public List<String> imgList = new ArrayList<>();//上传后的图片路径

    public List<ReleaseDyamicBean> upLoadVideoList = new ArrayList<>();


    // 图片and 视频
    public SourceUpParam(int sourceType, int sourceMaxNum, List<String> urlList, List<String> imgList, List<ReleaseDyamicBean> upLoadVideoList) {
        this.sourceType = sourceType;
        this.sourceMaxNum = sourceMaxNum;
        this.status.set(0);
        this.urlList = urlList;
        this.imgList = imgList;
        this.upLoadVideoList = upLoadVideoList;
    }


    public SourceUpParam(int sourceType, int sourceMaxNum) {
        this.sourceType = sourceType;
        this.sourceMaxNum = sourceMaxNum;
        this.status.set(0);
    }


    public SourceUpParam() {
        this.status.set(0);
    }

    public int resultcode_local = PictureConfig.CHOOSE_REQUEST;
    public int resultcode_net = 101;

//    // 图片and 视频
//    public SourceUpParam(int sourceType, int sourceMaxNum, List<String> urlList, List<String> imgList, List<ReleaseDyamicBean> upLoadVideoList) {
//        this.sourceType = sourceType;
//        this.sourceMaxNum = sourceMaxNum;
//        this.status.set(0);
//        this.urlList = urlList;
//        this.imgList = imgList;
//        this.upLoadVideoList = upLoadVideoList;
//    }
//
//
//    // 图片
//    public SourceUpParam(int sourceType, int sourceMaxNum, ObservableField<Integer> status, List<String> imgList) {
//        this.sourceType = sourceType;
//        this.sourceMaxNum = sourceMaxNum;
//        this.status = status;
//        this.imgList = imgList;
//    }
}
