package com.docker.common.common.widget.XPopup;

import android.content.Context;
import android.support.annotation.NonNull;
import android.widget.ThemedSpinnerAdapter;

import com.docker.common.R;
import com.lxj.xpopup.animator.PopupAnimator;
import com.lxj.xpopup.core.BottomPopupView;


public class BottomPopup extends BottomPopupView {
    private Context context;
    private String style;

    //注意：自定义弹窗本质是一个自定义View，但是只需重写一个参数的构造，其他的不要重写，所有的自定义弹窗都是这样。
    public BottomPopup(@NonNull Context context, String style) {
        super(context);
        this.context = context;
        this.style = style;
    }

    // 返回自定义弹窗的布局
    @Override
    protected int getImplLayoutId() {
        if ("detail_foot_style".equals(style)) {
            return R.layout.common_xpopup_bottom_detail;
        } else if ("demo_style".equals(style)) {
            return R.layout.common_xpopup_bottom;
        }
        return 0;
    }

    // 执行初始化操作，比如：findView，设置点击，或者任何你弹窗内的业务逻辑
    @Override
    protected void onCreate() {
        super.onCreate();


//        findViewById(R.id.tv_close).setOnClickListener(new OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                dismiss(); // 关闭弹窗
//            }
//        });
    }


    // 设置最大宽度，看需要而定
    @Override
    protected int getMaxWidth() {
        return super.getMaxWidth();
    }

    // 设置最大高度，看需要而定
    @Override
    protected int getMaxHeight() {
        if ("detail_foot_style".equals(style)) {
            return super.getMaxHeight();
        }
        return super.getMaxWidth();
    }

    // 设置自定义动画器，看需要而定
    @Override
    protected PopupAnimator getPopupAnimator() {
        return super.getPopupAnimator();
    }

    /**
     * 弹窗的宽度，用来动态设定当前弹窗的宽度，受getMaxWidth()限制
     *
     * @return
     */
    protected int getPopupWidth() {
        return 0;
    }

    /**
     * 弹窗的高度，用来动态设定当前弹窗的高度，受getMaxHeight()限制
     *
     * @return
     */
    protected int getPopupHeight() {
        return 0;
    }
}
