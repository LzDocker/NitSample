package com.docker.common.common.widget.XPopup;

import android.content.Context;

import com.docker.common.R;
import com.lxj.xpopup.animator.PopupAnimator;
import com.lxj.xpopup.animator.TranslateAnimator;
import com.lxj.xpopup.core.PositionPopupView;
import com.lxj.xpopup.enums.PopupAnimation;

import io.reactivex.annotations.NonNull;

/**
 * Description:
 * Create by dance, at 2019/6/14
 */
public class QQMsgPopup extends PositionPopupView {
    public QQMsgPopup(@NonNull Context context) {
        super(context);
    }

    @Override
    protected int getImplLayoutId() {
        return R.layout.common_popup_qq_msg;
    }


    @Override
    protected PopupAnimator getPopupAnimator() {
        return new TranslateAnimator(getPopupContentView(), PopupAnimation.TranslateFromTop);
    }

    @Override
    protected void onCreate() {
        super.onCreate();

    }
}
