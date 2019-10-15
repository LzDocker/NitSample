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
import com.dcbfhd.utilcode.utils.ScreenUtils;
import com.docker.common.common.command.ReplyCommandParam;
import com.docker.common.common.utils.cache.CacheUtils;

// 和万家发布
public class PopmenuWj extends PopupWindow implements OnClickListener {

    private Activity mContext;
    private RelativeLayout layout;
    private ImageView close;
    private ImageView back;
    private View view_divier;
    private View bgView;
    private LinearLayout gradle2;

    private int mWidth;
    private int mHeight;
    private int statusBarHeight;
    private Handler mHandler = new Handler();

    public PopmenuWj(Activity context) {
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
        mHeight = ScreenUtils.getScreenHeight();
        setWidth(mWidth);
        setHeight(mHeight);

        layout = (RelativeLayout) LayoutInflater.from(mContext).inflate(R.layout.circle_publish_menu_wj, null);
        setContentView(layout);

        close = (ImageView) layout.findViewById(R.id.iv_close);
        back = layout.findViewById(R.id.iv_back);
        view_divier = layout.findViewById(R.id.view_divier);
        gradle2 = layout.findViewById(R.id.ll_gradle_2);

        back.setOnClickListener(v -> {
            view_divier.setVisibility(View.GONE);
            gradle2.setVisibility(View.GONE);
            layout.findViewById(R.id.lin).setVisibility(View.VISIBLE);
            back.setVisibility(View.GONE);
        });

        close.setOnClickListener(v -> {
            if (isShowing()) {
                closeAnimation();
                reset();
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
        mHandler.post(() -> {
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
        });
        showAnimation(layout);

    }

    private void showAnimation(ViewGroup layout) {
        try {
            LinearLayout linearLayout = layout.findViewById(R.id.lin);
            mHandler.post(() -> {//＋ 旋转动画
                close.animate().rotation(90).setDuration(400);
            });
            if ("2".equals(CacheUtils.getUser().reg_type)) {
                //菜单项弹出动画
                for (int i = 0; i < linearLayout.getChildCount(); i++) {
                    final View child = linearLayout.getChildAt(i);
                    child.setOnClickListener(this);
                    child.setVisibility(View.INVISIBLE);
                    mHandler.postDelayed(() -> {
                        child.setVisibility(View.VISIBLE);
                        ValueAnimator fadeAnim = ObjectAnimator.ofFloat(child, "translationY", 600, 0);
                        fadeAnim.setDuration(200);
                        KickBackAnimator kickAnimator = new KickBackAnimator();
                        kickAnimator.setDuration(150);
                        fadeAnim.setEvaluator(kickAnimator);
                        fadeAnim.start();
                    }, i * 100 + 300);
                }
            } else {
                for (int i = 0; i < linearLayout.getChildCount(); i++) {
                    final View child = linearLayout.getChildAt(i);
                    if (child.getId() == R.id.circle_active) {
                        LinearLayout.LayoutParams layoutParams = (LinearLayout.LayoutParams) child.getLayoutParams();
                        layoutParams.width = layoutParams.WRAP_CONTENT;
                        layoutParams.leftMargin = 24;
                        layoutParams.weight = 0;
                        child.setLayoutParams(layoutParams);
                        child.setOnClickListener(this);
                        child.setVisibility(View.INVISIBLE);
                        mHandler.postDelayed(() -> {
                            child.setVisibility(View.VISIBLE);
                            ValueAnimator fadeAnim = ObjectAnimator.ofFloat(child, "translationY", 600, 0);
                            fadeAnim.setDuration(100);
                            KickBackAnimator kickAnimator = new KickBackAnimator();
                            kickAnimator.setDuration(150);
                            fadeAnim.setEvaluator(kickAnimator);
                            fadeAnim.start();
                        }, 100 + 300);
                    } else {
                        child.setVisibility(View.GONE);
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    /**
     * 关闭window动画
     */
    private void closeAnimation() {
        mHandler.post(() -> close.animate().rotation(-90).setDuration(400));

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
            if (v.getId() == R.id.circle_items) {
                LinearLayout linearLayout = layout.findViewById(R.id.lin);
                linearLayout.setVisibility(View.GONE);
                gradle2.setVisibility(View.VISIBLE);
                view_divier.setVisibility(View.VISIBLE);
                back.setVisibility(View.VISIBLE);
                for (int i = 0; i < gradle2.getChildCount(); i++) {
                    final View child = gradle2.getChildAt(i);
                    child.setOnClickListener(this);
                }
                return;
            }
//            closeAnimation();
        }
        if (replyCommand != null) {
            if (v.getId() == R.id.circle_goods_store) { // 发布商品
                replyCommand.exectue("goods");
            }
            if (v.getId() == R.id.circle_pro) { // 发布项目
                replyCommand.exectue("project");
            }
            if (v.getId() == R.id.circle_active) { // 发布动态
                replyCommand.exectue("dynamic");
            }
            if (v.getId() == R.id.circle_goods) { // 发布闲置物品
                replyCommand.exectue("product");
            }
            if (v.getId() == R.id.circle_hourse) { // 闲置住房
                replyCommand.exectue("house");
            }
            if (v.getId() == R.id.circle_car) { // 闲置车辆
                replyCommand.exectue("car");
            }
            if (v.getId() == R.id.circle_time) { // 闲置时间
                replyCommand.exectue("datetime");
            }
//            reset();
        }
    }


    private void reset() {
        view_divier.setVisibility(View.GONE);
        gradle2.setVisibility(View.GONE);
        layout.findViewById(R.id.lin).setVisibility(View.VISIBLE);
        back.setVisibility(View.GONE);
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
