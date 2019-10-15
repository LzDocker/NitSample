package com.docker.common.common.widget.empty;

import android.content.Context;
import android.content.res.TypedArray;
import android.databinding.BindingAdapter;
import android.databinding.DataBindingUtil;
import android.databinding.InverseBindingAdapter;
import android.databinding.InverseBindingListener;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.FrameLayout;

import com.docker.common.R;
import com.docker.common.databinding.CommonEmptyLayoutBinding;
import com.docker.common.databinding.CommonEmptyLayoutErrorBinding;
import com.docker.common.databinding.CommonEmptyLayoutLoadingBinding;
import com.docker.common.databinding.CommonEmptyLayoutNodataBinding;

import timber.log.Timber;

public class EmptyLayout extends FrameLayout {

    private int status;
    private CommonEmptyLayoutLoadingBinding loadingBinding;
    private CommonEmptyLayoutErrorBinding errorBinding;
    private CommonEmptyLayoutNodataBinding nodataBinding;
    private CommonEmptyLayoutBinding emptyLayoutBinding;

    public EmptyLayout(Context context) {
        super(context);
    }

    public EmptyLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context, attrs);
    }

    public EmptyLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context, attrs);
    }


    private void init(Context context, AttributeSet attrs) {
        TypedArray ta = context.obtainStyledAttributes(attrs, R.styleable.empty_layout);
        status = ta.getInt(R.styleable.empty_layout_status, EmptyStatus.BdHiden);
        loadingBinding = DataBindingUtil.inflate(LayoutInflater.from(context), R.layout.common_empty_layout_loading, this, false);
        errorBinding = DataBindingUtil.inflate(LayoutInflater.from(context), R.layout.common_empty_layout_error, this, false);
        nodataBinding = DataBindingUtil.inflate(LayoutInflater.from(context), R.layout.common_empty_layout_nodata, this, false);
        FrameLayout.LayoutParams layoutParams = new FrameLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        this.addView(loadingBinding.getRoot(), layoutParams);
        this.addView(nodataBinding.getRoot(), layoutParams);
        this.addView(errorBinding.getRoot(), layoutParams);
    }


    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        showLoading();
    }

    public void setGone() {
        loadingBinding.getRoot().setVisibility(GONE);
        errorBinding.getRoot().setVisibility(GONE);
        nodataBinding.getRoot().setVisibility(GONE);
    }

    public void showLoading() {
        loadingBinding.getRoot().setVisibility(VISIBLE);
        errorBinding.getRoot().setVisibility(GONE);
        nodataBinding.getRoot().setVisibility(GONE);
        loadingBinding.getRoot().bringToFront();
    }

    public void showError() {
        errorBinding.getRoot().bringToFront();
        loadingBinding.getRoot().setVisibility(GONE);
        errorBinding.getRoot().setVisibility(VISIBLE);
        nodataBinding.getRoot().setVisibility(GONE);
        errorBinding.commonTvError.setOnClickListener(v -> {
            if (onretryListener != null) {
                onretryListener.onretry();
            }
        });
    }

    public void showNodata() {
        nodataBinding.getRoot().bringToFront();
        loadingBinding.getRoot().setVisibility(GONE);
        errorBinding.getRoot().setVisibility(GONE);
        nodataBinding.getRoot().setVisibility(VISIBLE);
    }

    public int getStatus() {
        return status;
    }
    public void setStatus(int status) {
        this.status = status;
        switch (status) {
            case EmptyStatus.BdError:
                this.showError();
                break;
            case EmptyStatus.BdEmpty:
                this.showNodata();
                break;
            case EmptyStatus.BdLoading:
                this.showLoading();
                break;
            case EmptyStatus.BdHiden:
                this.setGone();
                break;
        }
    }

    public void hide() {
        this.setGone();
    }

    @BindingAdapter("statusAttrChanged")
    public static void setStatusAttrChanged(EmptyLayout view, InverseBindingListener inverseBindingListener) {
        if (inverseBindingListener == null) {
            view.setListener(null);
        } else {
            view.setListener(inverseBindingListener::onChange);
        }
    }

    @BindingAdapter(value = "status", requireAll = false)
    public static void setstatus(EmptyLayout view, int status) {
        view.setStatus(status);
    }

    @InverseBindingAdapter(attribute = "status", event = "statusAttrChanged")
    public static int getStatus(EmptyLayout view) {
        return view.getStatus();
    }

    private OnStatusChangeListener listener;

    public interface OnStatusChangeListener {
        void onStatusChanged();
    }

    public void setListener(OnStatusChangeListener listener) {
        this.listener = listener;
    }

    public interface OnretryListener {
        void onretry();
    }

    private OnretryListener onretryListener;

    public void setOnretryListener(OnretryListener onretryListener) {
        this.onretryListener = onretryListener;
    }
}
