package com.docker.common.common.config;

import com.docker.common.R;
import com.docker.common.common.widget.refresh.SmartRefreshLayout;
import com.docker.common.common.widget.refresh.footer.ClassicsFooter;
import com.docker.common.common.widget.refresh.header.ClassicsHeader;

public class CommonWidgetModel {
    public static final String[] mMainBottomTitles = {
            "公社首页",
            "公社优选",
            "",
            "江湖消息",
            "个人中心",
    };
    public static final int[] mIconUnselectIds = {
            R.mipmap.index_un,
            R.mipmap.gsyx,
            R.mipmap.dot,
            R.mipmap.jhxx,
            R.mipmap.grzx,
    };
    public static final int[] mIconSelectIds = {
            R.mipmap.index,
            R.mipmap.gsyx_sel,
            R.mipmap.dot,
            R.mipmap.jhxx_sel,
            R.mipmap.grzx_sel,
    };

    public static void initrefresh() {
        //设置全局的Header构建器
        SmartRefreshLayout.setDefaultRefreshHeaderCreator((context, layout) -> {
            ClassicsHeader classicsHeader = new ClassicsHeader(context);
            classicsHeader.setDrawableArrowSize(13);
            classicsHeader.setDrawableProgressSize(16);
            classicsHeader.setDrawableSize(16);
            classicsHeader.setFinishDuration(200);
            classicsHeader.setTextSizeTitle(13);
            classicsHeader.setAccentColorId(R.color.black);
            return classicsHeader;
        });
        //设置全局的Footer构建器
        SmartRefreshLayout.setDefaultRefreshFooterCreator((context, layout) -> {
            //指定为经典Footer，默认是 BallPulseFooter
            ClassicsFooter classicsFooter = new ClassicsFooter(context);
            classicsFooter.setDrawableArrowSize(13);
            classicsFooter.setDrawableProgressSize(16);
            classicsFooter.setDrawableSize(16);
            classicsFooter.setFinishDuration(200);
            classicsFooter.setTextSizeTitle(13);
            classicsFooter.setAccentColorId(R.color.black);
            return classicsFooter;
        });
    }
}
