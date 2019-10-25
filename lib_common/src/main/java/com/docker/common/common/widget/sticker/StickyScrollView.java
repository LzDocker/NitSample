package com.docker.common.common.widget.sticker;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.support.v4.widget.NestedScrollView;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AlphaAnimation;

import com.docker.common.R;

import java.util.ArrayList;

public class StickyScrollView extends NestedScrollView {

    /**
     * Tag for views that should stick and have constant drawing. e.g. TextViews, ImageViews etc
     */
    public static final String STICKY_TAG = "sticky";

    /**
     * Flag for views that should stick and have non-constant drawing. e.g. Buttons, ProgressBars etc
     */
    public static final String FLAG_NONCONSTANT = "-nonconstant";

    /**
     * Flag for views that have aren't fully opaque
     */
    public static final String FLAG_HASTRANSPARANCY = "-hastransparancy";

    /**
     * Default height of the shadow peeking out below the stuck view.
     */
    private static final int DEFAULT_SHADOW_HEIGHT = 10; // dp;

    private ArrayList<View> mStickyViews;
    private View mCurrentlyStickingView;
    private float mStickyViewTopOffset;
    private int mStickyViewLeftOffset;
    private boolean mRedirectTouchesToStickyView;
    private boolean mClippingToPadding;
    private boolean mClipToPaddingHasBeenSet;

    private int mShadowHeight;
    private Drawable mShadowDrawable;

    private final Runnable mInvalidateRunnable = new Runnable() {

        @Override
        public void run() {
            if (mCurrentlyStickingView != null) {
                int l = getLeftForViewRelativeOnlyChild(mCurrentlyStickingView);
                int t = getBottomForViewRelativeOnlyChild(mCurrentlyStickingView);
                int r = getRightForViewRelativeOnlyChild(mCurrentlyStickingView);
                int b = (int) (getScrollY() + (mCurrentlyStickingView.getHeight() + mStickyViewTopOffset));
                invalidate(l, t, r, b);
            }
            postDelayed(this, 16);
        }
    };

    public StickyScrollView(Context context) {
        this(context, null);
    }

    public StickyScrollView(Context context, AttributeSet attrs) {
        this(context, attrs, android.R.attr.scrollViewStyle);
    }

    public StickyScrollView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        setup();


        TypedArray a = context.obtainStyledAttributes(attrs,
                R.styleable.StickyScrollView, defStyle, 0);

        final float density = context.getResources().getDisplayMetrics().density;
        int defaultShadowHeightInPix = (int) (DEFAULT_SHADOW_HEIGHT * density + 0.5f);

        mShadowHeight = a.getDimensionPixelSize(
                R.styleable.StickyScrollView_stuckShadowHeight,
                defaultShadowHeightInPix);

        int shadowDrawableRes = a.getResourceId(
                R.styleable.StickyScrollView_stuckShadowDrawable, -1);

        if (shadowDrawableRes != -1) {
            mShadowDrawable = context.getResources().getDrawable(
                    shadowDrawableRes);
        }

        a.recycle();
    }

    /**
     * Sets the height of the shadow drawable in pixels.
     *
     * @param height
     */
    public void setShadowHeight(int height) {
        mShadowHeight = height;
    }


    public void setup() {
        mStickyViews = new ArrayList<View>();
    }

    private int getLeftForViewRelativeOnlyChild(View v) {
        int left = v.getLeft();
        while (v.getParent() != getChildAt(0)) {
            v = (View) v.getParent();
            left += v.getLeft();
        }
        return left;
    }

    private int getTopForViewRelativeOnlyChild(View v) {
        int top = v.getTop();
        while (v.getParent() != getChildAt(0)) {
            v = (View) v.getParent();
            top += v.getTop();
        }
        return top;
    }

    private int getRightForViewRelativeOnlyChild(View v) {
        int right = v.getRight();
        while (v.getParent() != getChildAt(0)) {
            v = (View) v.getParent();
            right += v.getRight();
        }
        return right;
    }

    private int getBottomForViewRelativeOnlyChild(View v) {
        int bottom = v.getBottom();
        while (v.getParent() != getChildAt(0)) {
            v = (View) v.getParent();
            bottom += v.getBottom();
        }
        return bottom;
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        super.onLayout(changed, l, t, r, b);
        if (!mClipToPaddingHasBeenSet) {
            mClippingToPadding = true;
        }
        notifyHierarchyChanged();
    }

    @Override
    public void setClipToPadding(boolean clipToPadding) {
        super.setClipToPadding(clipToPadding);
        mClippingToPadding = clipToPadding;
        mClipToPaddingHasBeenSet = true;
    }

    @Override
    public void addView(View child) {
        super.addView(child);
        findStickyViews(child);
    }

    @Override
    public void addView(View child, int index) {
        super.addView(child, index);
        findStickyViews(child);
    }

    @Override
    public void addView(View child, int index, ViewGroup.LayoutParams params) {
        super.addView(child, index, params);
        findStickyViews(child);
    }

    @Override
    public void addView(View child, int width, int height) {
        super.addView(child, width, height);
        findStickyViews(child);
    }

    @Override
    public void addView(View child, ViewGroup.LayoutParams params) {
        super.addView(child, params);
        findStickyViews(child);
    }

    @Override
    protected void dispatchDraw(Canvas canvas) {
        super.dispatchDraw(canvas);
        if (mCurrentlyStickingView != null) {
            canvas.save();
            canvas.translate(getPaddingLeft() + mStickyViewLeftOffset, getScrollY() + mStickyViewTopOffset + (mClippingToPadding ? getPaddingTop() : 0));

            canvas.clipRect(0, (mClippingToPadding ? -mStickyViewTopOffset : 0), getWidth() - mStickyViewLeftOffset, mCurrentlyStickingView.getHeight() + mShadowHeight + 1);

            if (mShadowDrawable != null) {
                int left = 0;
                int right = mCurrentlyStickingView.getWidth();
                int top = mCurrentlyStickingView.getHeight();
                int bottom = mCurrentlyStickingView.getHeight() + mShadowHeight;
                mShadowDrawable.setBounds(left, top, right, bottom);
                mShadowDrawable.draw(canvas);
            }

            canvas.clipRect(0, (mClippingToPadding ? -mStickyViewTopOffset : 0), getWidth(), mCurrentlyStickingView.getHeight());
            if (getStringTagForView(mCurrentlyStickingView).contains(FLAG_HASTRANSPARANCY)) {
                showView(mCurrentlyStickingView);
                mCurrentlyStickingView.draw(canvas);
                hideView(mCurrentlyStickingView);
            } else {
                mCurrentlyStickingView.draw(canvas);
            }
            canvas.restore();
        }
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        if (ev.getAction() == MotionEvent.ACTION_DOWN) {
            mRedirectTouchesToStickyView = true;
        }

        if (mRedirectTouchesToStickyView) {
            mRedirectTouchesToStickyView = mCurrentlyStickingView != null;
            if (mRedirectTouchesToStickyView) {
                mRedirectTouchesToStickyView =
                        ev.getY() <= (mCurrentlyStickingView.getHeight() + mStickyViewTopOffset) &&
                                ev.getX() >= getLeftForViewRelativeOnlyChild(mCurrentlyStickingView) &&
                                ev.getX() <= getRightForViewRelativeOnlyChild(mCurrentlyStickingView);
            }
        } else if (mCurrentlyStickingView == null) {
            mRedirectTouchesToStickyView = false;
        }
        if (mRedirectTouchesToStickyView) {
            ev.offsetLocation(0, -1 * ((getScrollY() + mStickyViewTopOffset) - getTopForViewRelativeOnlyChild(mCurrentlyStickingView)));
        }
        return super.dispatchTouchEvent(ev);
    }

    private boolean hasNotDoneActionDown = true;

    @Override
    public boolean onTouchEvent(MotionEvent ev) {
        if (mRedirectTouchesToStickyView) {
            ev.offsetLocation(0, ((getScrollY() + mStickyViewTopOffset) - getTopForViewRelativeOnlyChild(mCurrentlyStickingView)));
        }

        if (ev.getAction() == MotionEvent.ACTION_DOWN) {
            hasNotDoneActionDown = false;
        }

        if (hasNotDoneActionDown) {
            MotionEvent down = MotionEvent.obtain(ev);
            down.setAction(MotionEvent.ACTION_DOWN);
            super.onTouchEvent(down);
            hasNotDoneActionDown = false;
        }

        if (ev.getAction() == MotionEvent.ACTION_UP || ev.getAction() == MotionEvent.ACTION_CANCEL) {
            hasNotDoneActionDown = true;
        }

        return super.onTouchEvent(ev);
    }

    @Override
    protected void onScrollChanged(int l, int t, int oldl, int oldt) {
        super.onScrollChanged(l, t, oldl, oldt);
        doTheStickyThing();
    }

    private void doTheStickyThing() {
        View viewThatShouldStick = null;
        View approachingStickyView = null;
        for (View v : mStickyViews) {
            int viewTop = getTopForViewRelativeOnlyChild(v) - getScrollY() + (mClippingToPadding ? 0 : getPaddingTop());
            if (viewTop <= 0) {
                if (viewThatShouldStick == null || viewTop > (getTopForViewRelativeOnlyChild(viewThatShouldStick) - getScrollY() + (mClippingToPadding ? 0 : getPaddingTop()))) {
                    viewThatShouldStick = v;
                }
            } else {
                if (approachingStickyView == null || viewTop < (getTopForViewRelativeOnlyChild(approachingStickyView) - getScrollY() + (mClippingToPadding ? 0 : getPaddingTop()))) {
                    approachingStickyView = v;
                }
            }
        }
        if (viewThatShouldStick != null) {
            mStickyViewTopOffset = approachingStickyView == null ? 0 : Math.min(0, getTopForViewRelativeOnlyChild(approachingStickyView) - getScrollY() + (mClippingToPadding ? 0 : getPaddingTop()) - viewThatShouldStick.getHeight());
            if (viewThatShouldStick != mCurrentlyStickingView) {
                if (mCurrentlyStickingView != null) {
                    stopStickingCurrentlyStickingView();
                }
                // only compute the left offset when we start sticking.
                mStickyViewLeftOffset = getLeftForViewRelativeOnlyChild(viewThatShouldStick);
                startStickingView(viewThatShouldStick);
            }
        } else if (mCurrentlyStickingView != null) {
            stopStickingCurrentlyStickingView();
        }
    }

    private void startStickingView(View viewThatShouldStick) {
        mCurrentlyStickingView = viewThatShouldStick;
        if (getStringTagForView(mCurrentlyStickingView).contains(FLAG_HASTRANSPARANCY)) {
            hideView(mCurrentlyStickingView);
        }
        if (((String) mCurrentlyStickingView.getTag()).contains(FLAG_NONCONSTANT)) {
            post(mInvalidateRunnable);
        }
    }

    private void stopStickingCurrentlyStickingView() {
        if (getStringTagForView(mCurrentlyStickingView).contains(FLAG_HASTRANSPARANCY)) {
            showView(mCurrentlyStickingView);
        }
        mCurrentlyStickingView = null;
        removeCallbacks(mInvalidateRunnable);
    }

    /**
     * Notify that the sticky attribute has been added or removed from one or more views in the View hierarchy
     */
    public void notifyStickyAttributeChanged() {
        notifyHierarchyChanged();
    }

    private void notifyHierarchyChanged() {
        if (mCurrentlyStickingView != null) {
            stopStickingCurrentlyStickingView();
        }
        mStickyViews.clear();
        findStickyViews(getChildAt(0));
        doTheStickyThing();
        invalidate();
    }

    private void findStickyViews(View v) {
        if (v instanceof ViewGroup) {
            ViewGroup vg = (ViewGroup) v;
            for (int i = 0; i < vg.getChildCount(); i++) {
                String tag = getStringTagForView(vg.getChildAt(i));
                if (tag != null && tag.contains(STICKY_TAG)) {
                    mStickyViews.add(vg.getChildAt(i));
                } else if (vg.getChildAt(i) instanceof ViewGroup) {
                    findStickyViews(vg.getChildAt(i));
                }
            }
        } else {
            String tag = (String) v.getTag();
            if (tag != null && tag.contains(STICKY_TAG)) {
                mStickyViews.add(v);
            }
        }
    }

    private String getStringTagForView(View v) {
        Object tagObject = v.getTag();
        return String.valueOf(tagObject);
    }

    private void hideView(View v) {
        if (Build.VERSION.SDK_INT >= 11) {
            v.setAlpha(0);
        } else {
            AlphaAnimation anim = new AlphaAnimation(1, 0);
            anim.setDuration(0);
            anim.setFillAfter(true);
            v.startAnimation(anim);
        }
    }

    private void showView(View v) {
        if (Build.VERSION.SDK_INT >= 11) {
            v.setAlpha(1);
        } else {
            AlphaAnimation anim = new AlphaAnimation(0, 1);
            anim.setDuration(0);
            anim.setFillAfter(true);
            v.startAnimation(anim);
        }
    }

}
