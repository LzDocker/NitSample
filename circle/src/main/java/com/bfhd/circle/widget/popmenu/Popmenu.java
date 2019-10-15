package com.bfhd.circle.widget.popmenu;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.app.Activity;
import android.content.res.Resources;
import android.graphics.Rect;
import android.os.Handler;
import android.util.DisplayMetrics;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewAnimationUtils;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;

import com.bfhd.circle.R;
import com.docker.common.common.command.ReplyCommandParam;

public class Popmenu extends PopupWindow implements OnClickListener {

    private Activity mContext;
    private RelativeLayout layout;
    private ImageView close;
    private View bgView;
    private int mWidth;
    private int mHeight;
    private int statusBarHeight;
    private Handler mHandler = new Handler();

    public Popmenu(Activity context) {
        mContext = context;
    }

    /**
     * 初始化
     *
     * @param view 要显示的模糊背景View,一般选择跟布局layout
     */
    public void init(View view) {
        Rect frame = new Rect();
        mContext.getWindow().getDecorView().getWindowVisibleDisplayFrame(frame);
        statusBarHeight = frame.top;
        DisplayMetrics metrics = new DisplayMetrics();
        mContext.getWindowManager().getDefaultDisplay()
                .getMetrics(metrics);
        mWidth = metrics.widthPixels;
        mHeight = metrics.heightPixels;

        setWidth(mWidth);
        setHeight(mHeight);

        layout = (RelativeLayout) LayoutInflater.from(mContext).inflate(R.layout.circle_publish_menu, null);

        setContentView(layout);

        close = (ImageView) layout.findViewById(R.id.iv_close);
        close.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                if (isShowing()) {
                    closeAnimation();
                }
            }

        });

        bgView = layout.findViewById(R.id.rel);
        setOutsideTouchable(true);
        setFocusable(true);
        setClippingEnabled(false);//使popupwindow全屏显示
    }

    public int getNavigationBarHeight(Activity activity) {
        Resources resources = activity.getResources();
        int resourceId = resources.getIdentifier("navigation_bar_height", "dimen", "android");
        //获取NavigationBar的高度
        int height = resources.getDimensionPixelSize(resourceId);
        return height;
    }

    /**
     * 显示window动画
     *
     * @param anchor
     */
    public void showMoreWindow(View anchor) {

        showAtLocation(anchor, Gravity.TOP | Gravity.START, 0, 0);
        mHandler.post(new Runnable() {
            @Override
            public void run() {
                try {
                    //圆形扩展的动画
                    if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.LOLLIPOP) {
                        int x = mWidth / 2;
                        int y = (int) (mHeight - fromDpToPx(25));
                        Animator animator = ViewAnimationUtils.createCircularReveal(bgView, x,
                                y, 0, bgView.getHeight());
                        animator.addListener(new AnimatorListenerAdapter() {
                            @Override
                            public void onAnimationStart(Animator animation) {
//                                bgView.setVisibility(View.VISIBLE);
                            }

                            @Override
                            public void onAnimationEnd(Animator animation) {
                                //							layout.setVisibility(View.VISIBLE);
                            }
                        });
                        animator.setDuration(300);
                        animator.start();
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });


        showAnimation(layout);

    }

    private void showAnimation(ViewGroup layout) {
        try {
            LinearLayout linearLayout = layout.findViewById(R.id.lin);
            mHandler.post(new Runnable() {
                @Override
                public void run() {
                    //＋ 旋转动画
                    close.animate().rotation(90).setDuration(400);
                }
            });
            //菜单项弹出动画
            for (int i = 0; i < linearLayout.getChildCount(); i++) {
                final View child = linearLayout.getChildAt(i);
                child.setOnClickListener(this);
                child.setVisibility(View.INVISIBLE);
                mHandler.postDelayed(new Runnable() {

                    @Override
                    public void run() {
                        child.setVisibility(View.VISIBLE);
                        ValueAnimator fadeAnim = ObjectAnimator.ofFloat(child, "translationY", 600, 0);
                        fadeAnim.setDuration(200);
                        KickBackAnimator kickAnimator = new KickBackAnimator();
                        kickAnimator.setDuration(150);
                        fadeAnim.setEvaluator(kickAnimator);
                        fadeAnim.start();
                    }
                }, i * 100 + 300);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    /**
     * 关闭window动画
     */
    private void closeAnimation() {
        mHandler.post(new Runnable() {
            @Override
            public void run() {
                close.animate().rotation(-90).setDuration(400);
            }
        });

        try {
            if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.LOLLIPOP) {

                int x = mWidth / 2;
                int y = (int) (mHeight - fromDpToPx(25));
                Animator animator = ViewAnimationUtils.createCircularReveal(bgView, x,
                        y, bgView.getHeight(), 0);
                animator.addListener(new AnimatorListenerAdapter() {
                    @Override
                    public void onAnimationStart(Animator animation) {
                        //							layout.setVisibility(View.GONE);
                    }

                    @Override
                    public void onAnimationEnd(Animator animation) {
//                        bgView.setVisibility(View.GONE);
                        dismiss();
                    }
                });
                animator.setDuration(200);
                animator.start();
            }
        } catch (Exception e) {
        }
    }

    /**
     * 点击事件处理
     *
     * @param v
     */
    @Override
    public void onClick(View v) {

        if (isShowing()) {
            closeAnimation();
        }
        if (replyCommand != null) {
            if (v.getId() == R.id.circle_active) {
                replyCommand.exectue(1);
            }
            if (v.getId() == R.id.circle_news) {
                replyCommand.exectue(2);
            }
            if (v.getId() == R.id.circle_queastion) {
                replyCommand.exectue(3);
            }
        }
    }

    float fromDpToPx(float dp) {
        return dp * Resources.getSystem().getDisplayMetrics().density;
    }

    private ReplyCommandParam replyCommand;

    public ReplyCommandParam getReplyCommand() {
        return replyCommand;
    }

    public void setReplyCommand(ReplyCommandParam replyCommand) {
        this.replyCommand = replyCommand;
    }
}
