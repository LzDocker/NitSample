package com.bfhd.circle.ui.circle;

import android.arch.lifecycle.ViewModelProvider;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.android.arouter.launcher.ARouter;
import com.bfhd.circle.R;
import com.bfhd.circle.base.HivsBaseActivity;
import com.bfhd.circle.databinding.CircleActivityCircleCreateHwjBinding;
import com.bfhd.circle.ui.circle.circlecreate.CircleHomeFragment;
import com.bfhd.circle.vm.CircleViewModel;
import com.dcbfhd.utilcode.utils.FragmentUtils;
import com.docker.common.common.router.AppRouter;
import javax.inject.Inject;

/*
 * 新建圈子
 * 编辑圈子
 * */
//@Route(path = AppRouter.CIRCLE_CREATE_HWJ)
public class CircleCreateHwjActivity extends HivsBaseActivity<CircleViewModel, CircleActivityCircleCreateHwjBinding> {


    public static final int CIRCLE_TYPE_COMPANY = 1;    // 企业圈
    public static final int CIRCLE_TYPE_ACTIVE = 2;    // 兴趣圈
    public static final int CIRCLE_TYPE_COUNTRY = 3;    // 国别圈
    private int mCreateType = CIRCLE_TYPE_COMPANY;
    private String mUtid;
    private String mCircleID;

    public static void startMe(Context context, int type, String utid, String circleID) {
        Intent intent = new Intent(context, CircleCreateHwjActivity.class);
        Bundle bundle = new Bundle();
        bundle.putSerializable("TYPE", type);
        bundle.putSerializable("mUtid", utid);
        bundle.putSerializable("circleID", circleID);
        intent.putExtras(bundle);
        context.startActivity(intent);
    }

    @Inject
    ViewModelProvider.Factory factory;

    @Override
    protected int getLayoutId() {
        return R.layout.circle_activity_circle_create_hwj;
    }


    @Override
    public CircleViewModel getmViewModel() {
        return ViewModelProviders.of(this, factory).get(CircleViewModel.class);
    }

    @Override
    protected void inject() {
        super.inject();
        ARouter.getInstance().inject(this);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        mCreateType = (int) getIntent().getExtras().get("TYPE");
        mUtid = getIntent().getStringExtra("mUtid");
        mCircleID = getIntent().getStringExtra("circleID");
        super.onCreate(savedInstanceState);
        mBinding.setViewmodel(mViewModel);
        if (TextUtils.isEmpty(mUtid)) {
            mToolbar.setTitle("创建和太极家");
        } else {
            mToolbar.setTitle("编辑和太极家");

        }
    }

    @Override
    public void initView() {
        switch (mCreateType) {
            case CIRCLE_TYPE_COMPANY:
                FragmentUtils.add(getSupportFragmentManager(),
                        CircleHomeFragment.newInstance(mUtid, mCircleID), R.id.circle_publish_frame);
                break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (mCreateType) {
            case CIRCLE_TYPE_COMPANY:
                FragmentUtils.findFragment(getSupportFragmentManager(), CircleHomeFragment.class).onActivityResult(requestCode, resultCode, data);
                break;
        }
    }

    public boolean dispatchTouchEvent(MotionEvent event) {
        if (event.getAction() == MotionEvent.ACTION_DOWN  &&
                getCurrentFocus()!=null &&
                getCurrentFocus().getWindowToken()!=null) {

            View v = getCurrentFocus();
            if (isShouldHideKeyboard(v, event)) {
                hideKeyboard(v.getWindowToken());
            }
        }
        return super.dispatchTouchEvent(event);
    }

    /**
     * 根据EditText所在坐标和用户点击的坐标相对比，来判断是否隐藏键盘，因为当用户点击EditText时则不能隐藏
     */
    private boolean isShouldHideKeyboard(View v, MotionEvent event) {
        if (v != null && (v instanceof EditText)) {
            int[] l = {0, 0};
            v.getLocationOnScreen(l);
            int left = l[0],
                    top = l[1],
                    bottom = top + v.getHeight(),
                    right = left + v.getWidth();
            if (event.getRawX() > left && event.getRawX() < right
                    && event.getRawY() > top && event.getRawY() < bottom) {
                // 点击EditText的事件，忽略它。
                return false;
            } else {
                return true;
            }
        }
        // 如果焦点不是EditText则忽略，这个发生在视图刚绘制完，第一个焦点不在EditText上，和用户用轨迹球选择其他的焦点
        return false;
    }

    /**
     * 获取InputMethodManager，隐藏软键盘
     */
    private void hideKeyboard(IBinder token) {
        if (token != null) {
            InputMethodManager mInputMethodManager = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
            mInputMethodManager.hideSoftInputFromWindow(token, InputMethodManager.HIDE_NOT_ALWAYS);
        }
    }

}
