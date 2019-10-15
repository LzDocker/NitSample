package com.docker.common.common.video.theme;


/*
 * Copyright (C) 2010-2018 Alibaba Group Holding Limited.
 */

import com.docker.common.common.video.widget.AliyunVodPlayerView;

/**
 * 主题的接口。用于变换UI的主题。
 * {@link AliyunVodPlayerView}
 */

public interface ITheme {
    /**
     * 设置主题
     * @param theme 支持的主题
     */
    void setTheme(AliyunVodPlayerView.Theme theme);
}
