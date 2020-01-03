package com.docker.common.common.widget.XPopup;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.support.annotation.NonNull;
import android.view.Gravity;
import android.view.View;
import android.view.WindowManager;
import android.widget.FrameLayout;

import com.dcbfhd.utilcode.utils.ActivityUtils;
import com.dcbfhd.utilcode.utils.ImageUtils;
import com.docker.common.R;
import com.docker.common.common.vo.ShareBean;
import com.lxj.xpopup.XPopup;
import com.lxj.xpopup.animator.PopupAnimator;
import com.lxj.xpopup.animator.TranslateAnimator;
import com.lxj.xpopup.core.CenterPopupView;
import com.lxj.xpopup.enums.PopupAnimation;
import com.lxj.xpopup.util.XPopupUtils;
import com.umeng.socialize.ShareAction;
import com.umeng.socialize.UMShareListener;
import com.umeng.socialize.bean.SHARE_MEDIA;
import com.umeng.socialize.media.UMImage;
import com.umeng.socialize.media.UMWeb;

public class FullScreenSharePopView extends CenterPopupView {

    private int style = 0;

    public FullScreenSharePopView(@NonNull Context context) {
        super(context);
    }

    public FullScreenSharePopView setStyle(int style) {
        this.style = style;
        return this;
    }

    @Override
    protected int getMaxWidth() {
        return 0;
    }

    @Override
    protected void initPopupContent() {
        super.initPopupContent();
        popupInfo.hasShadowBg = false;
    }

    @Override
    public void onNavigationBarChange(boolean show) {
        if (!show) {
            applyFull();
            getPopupContentView().setPadding(0, 0, 0, 0);
        } else {
            applySize(true);
        }
    }

    @Override
    protected void applySize(boolean isShowNavBar) {
        int rotation = ((WindowManager) getContext().getSystemService(Context.WINDOW_SERVICE)).getDefaultDisplay().getRotation();
        View contentView = getPopupContentView();
        FrameLayout.LayoutParams params = (LayoutParams) contentView.getLayoutParams();
        params.gravity = Gravity.TOP;
        contentView.setLayoutParams(params);

        int actualNabBarHeight = isShowNavBar || XPopupUtils.isNavBarVisible(getContext()) ? XPopupUtils.getNavBarHeight() : 0;
        if (rotation == 0) {
            contentView.setPadding(contentView.getPaddingLeft(), contentView.getPaddingTop(), contentView.getPaddingRight(),
                    actualNabBarHeight);
        } else if (rotation == 1 || rotation == 3) {
            contentView.setPadding(contentView.getPaddingLeft(), contentView.getPaddingTop(), contentView.getPaddingRight(), 0);
        }
    }

    Paint paint = new Paint();
    Rect shadowRect;

    @Override
    protected void dispatchDraw(Canvas canvas) {
        super.dispatchDraw(canvas);
        if (popupInfo.hasStatusBarShadow) {
            paint.setColor(XPopup.statusBarShadowColor);
            shadowRect = new Rect(0, 0, XPopupUtils.getWindowWidth(getContext()), XPopupUtils.getStatusBarHeight());
            canvas.drawRect(shadowRect, paint);
        }
    }

    @Override
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        paint = null;
    }

    @Override
    protected PopupAnimator getPopupAnimator() {
        return new TranslateAnimator(getPopupContentView(), PopupAnimation.TranslateFromBottom);
    }

    @Override
    protected int getImplLayoutId() {

        return R.layout.common_full_screen_share_pop;

    }

    @Override
    protected void onCreate() {
        super.onCreate();
        if (style == 0) { // img
            findViewById(R.id.ll_share_img).setVisibility(VISIBLE);

        } else {      // link
            findViewById(R.id.tv_link).setVisibility(VISIBLE);
        }
        findViewById(R.id.tv_cancel).setOnClickListener(v -> {
            this.dismiss();
        });

        findViewById(R.id.ll_wx).setOnClickListener(v -> {
            if (style == 0) {
                shareMediaImg(SHARE_MEDIA.WEIXIN);
            } else {
                shareLink(null, SHARE_MEDIA.WEIXIN);
            }
        });
        findViewById(R.id.ll_wx_circle).setOnClickListener(v -> {
            if (style == 0) {
                shareMediaImg(SHARE_MEDIA.WEIXIN_CIRCLE);
            } else {
                shareLink(null, SHARE_MEDIA.WEIXIN_CIRCLE);
            }
        });
        findViewById(R.id.ll_qq).setOnClickListener(v -> {
            if (style == 0) {
                shareMediaImg(SHARE_MEDIA.QQ);
            } else {
                shareLink(null, SHARE_MEDIA.QQ);
            }
        });
    }

    private UMShareListener umShareListener = new UMShareListener() {
        @Override
        public void onStart(SHARE_MEDIA share_media) {

        }

        @Override
        public void onResult(SHARE_MEDIA share_media) {
            FullScreenSharePopView.this.dismiss();
        }

        @Override
        public void onError(SHARE_MEDIA share_media, Throwable throwable) {

        }

        @Override
        public void onCancel(SHARE_MEDIA share_media) {

        }
    };

    private void shareMediaImg(SHARE_MEDIA share_media) {
        UMImage umImage = new UMImage(ActivityUtils.getTopActivity(), ImageUtils.view2Bitmap(findViewById(R.id.ll_share_img)));
        new ShareAction(ActivityUtils.getTopActivity())
                .setPlatform(share_media)//传入平台
                .withMedias(umImage)
                .setCallback(umShareListener)//回调监听器
                .share();
        FullScreenSharePopView.this.dismiss();
    }

    private void shareLink(ShareBean shareBean, SHARE_MEDIA share_media) {

//        UMWeb web = new UMWeb(shareBean.getShareUrl());
        UMWeb web = new UMWeb("http://www.baidu.com");
//        web.setTitle(shareBean.getShareTit());//标题
        web.setTitle("test");//标题
//        web.setDescription(shareBean.getShareDesc());//描述
        web.setDescription("desc");//描述
        new ShareAction(ActivityUtils.getTopActivity())
                .setPlatform(share_media)//传入平台
                .withMedia(web)
                .setCallback(umShareListener)//回调监听器
                .share();
        FullScreenSharePopView.this.dismiss();
    }
}
