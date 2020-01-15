package com.docker.cirlev2.widget.popmen;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.app.Activity;
import android.content.res.Resources;
import android.graphics.Rect;
import android.os.Handler;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
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

import com.dcbfhd.utilcode.utils.ScreenUtils;
import com.docker.cirlev2.R;
import com.docker.cirlev2.vo.pro.AppVo;
import com.docker.common.BR;
import com.docker.common.common.adapter.NitAbsSampleAdapter;
import com.docker.common.common.command.ReplyCommandParam;

public class SuperPopmenu extends PopupWindow implements OnClickListener {

    private Activity mContext;
    private RelativeLayout layout;
    private ImageView close;
    private View bgView;
    private int mWidth;
    private int mHeight;
    private int statusBarHeight;
    RecyclerView recyclerView;
    private Handler mHandler = new Handler();

    private NitAbsSampleAdapter absSampleAdapter;

    public SuperPopmenu(Activity context) {
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

        layout = (RelativeLayout) LayoutInflater.from(mContext).inflate(R.layout.circlev2_super_publish_menu, null);

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


        recyclerView = layout.findViewById(R.id.recycle);
        absSampleAdapter = new NitAbsSampleAdapter(R.layout.circlev2_pro_sample, BR.item) {
            @Override
            public void onRealviewBind(ViewHolder holder, int position) {

            }
        };
        recyclerView.setLayoutManager(new GridLayoutManager(recyclerView.getContext(), 4));
        recyclerView.setAdapter(absSampleAdapter);

        AppVo appManagerVo = new AppVo();
        appManagerVo.name = "应用管理";
        appManagerVo.id = "-1";
        absSampleAdapter.add(appManagerVo);

        AppVo appVo = new AppVo();
        appVo.name = "动态";
        appVo.id = "0";
        absSampleAdapter.add(appVo);

        AppVo appVo1 = new AppVo();
        appVo1.name = "问答";
        appVo1.id = "1";
        absSampleAdapter.add(appVo1);

        AppVo appVo2 = new AppVo();
        appVo2.name = "新闻";
        appVo2.id = "2";
        absSampleAdapter.add(appVo2);


        absSampleAdapter.notifyDataSetChanged();
        absSampleAdapter.setOnItemClickListener((view1, position) -> {
            if (replyCommandParam != null) {
                replyCommandParam.exectue(absSampleAdapter.getItem(position));
            }
        });
    }


    private ReplyCommandParam replyCommandParam;

    public void setReplyCommandParam(ReplyCommandParam replyCommandParam) {
        this.replyCommandParam = replyCommandParam;
    }

    public NitAbsSampleAdapter getAdapter() {
        return absSampleAdapter;
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
                        }

                        @Override
                        public void onAnimationEnd(Animator animation) {
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

//    private void findRecycle(ViewGroup viewGroup) {
//        for (int i = 0; i < viewGroup.getChildCount(); i++) {
//            if (viewGroup.getChildAt(i) instanceof ViewGroup) {
//                if (viewGroup.getChildAt(i) instanceof RecyclerView) {
//                    recyclerView = (RecyclerView) viewGroup.getChildAt(i);
//                } else {
//                    findRecycle((ViewGroup) viewGroup.getChildAt(i));
//                }
//            }
//        }
//    }

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
            if (recyclerView != null) {
                for (int i = 0; i < recyclerView.getChildCount(); i++) {
                    final View child = recyclerView.getChildAt(i);
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
    }

    float fromDpToPx(float dp) {
        return dp * Resources.getSystem().getDisplayMetrics().density;
    }
}
