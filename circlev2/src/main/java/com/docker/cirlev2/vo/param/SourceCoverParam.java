package com.docker.cirlev2.vo.param;

import android.databinding.ObservableField;

import com.dcbfhd.utilcode.utils.ScreenUtils;

import java.io.Serializable;
import java.util.List;

/*
选择net 图片 入参
* */
public class SourceCoverParam implements Serializable {


    public static final int UI_TYPE_TOP_HIDEN = 101;
    public static final int UI_TYPE_TOP_SHOW = 102;


    // 资源类型
    public int uiType = UI_TYPE_TOP_HIDEN;

    // 最多选择个数
    public int sourceMaxNum = 9;

    // 是否需要剪裁
    public boolean needCrop = false;

    // 裁剪完的宽度 默认屏幕宽度一致
    public int width = ScreenUtils.getScreenWidth();

    public int height = 400;

    // 状态  0 初始化状态  1 选择完成
    public ObservableField<Integer> status = new ObservableField<>();

    private List<String> imgList;//选择的图片

    public SourceCoverParam(int uiType, int sourceMaxNum, List<String> imgList) {
        this.uiType = uiType;
        this.sourceMaxNum = sourceMaxNum;
        this.status.set(0);
        this.imgList = imgList;
    }

    public List<String> getImgList() {
        return imgList;
    }

}
