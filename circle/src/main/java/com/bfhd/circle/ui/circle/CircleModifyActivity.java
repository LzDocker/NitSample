package com.bfhd.circle.ui.circle;

import android.app.Activity;
import android.arch.lifecycle.ViewModelProvider;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;

import com.bfhd.circle.R;
import com.bfhd.circle.base.HivsBaseActivity;
import com.bfhd.circle.databinding.CircleActivityCircleModifyBinding;
import com.bfhd.circle.vm.CircleViewModel;
import com.dcbfhd.utilcode.utils.ToastUtils;

import javax.inject.Inject;

/*
 * 修改名称
 * */
public class CircleModifyActivity extends HivsBaseActivity<CircleViewModel, CircleActivityCircleModifyBinding> {

    @Inject
    ViewModelProvider.Factory factory;
    //    private int type;
    private String fullname;

    public static void startMe(Context context, String fullname, int reqcode) {
        Intent intent = new Intent(context, CircleModifyActivity.class);
        Bundle bundle = new Bundle();
        bundle.putSerializable("fullname", fullname);
        intent.putExtras(bundle);
        ((Activity) context).startActivityForResult(intent, reqcode);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.circle_activity_circle_modify;
    }

    @Override
    public CircleViewModel getmViewModel() {
        return ViewModelProviders.of(this, factory).get(CircleViewModel.class);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        fullname = getIntent().getStringExtra("fullname");
        super.onCreate(savedInstanceState);
        mBinding.etNickname.setText(fullname);
    }

    @Override
    public void initView() {
        mToolbar.setTitle("修改备注");
        mToolbar.setTvRight("保存", v -> {
            if (TextUtils.isEmpty(mBinding.etNickname.getText())) {
                ToastUtils.showShort("备注不能为空！");
                return;
            }
            Intent intent = new Intent();
            intent.putExtra("fullname", mBinding.etNickname.getText().toString());
            setResult(RESULT_OK, intent);
            finish();
        });
        mBinding.imgDelete.setOnClickListener(v -> {
            mBinding.etNickname.setText("");
        });
        mBinding.etNickname.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (TextUtils.isEmpty(mBinding.etNickname.getText())) {
                    mBinding.imgDelete.setVisibility(View.GONE);
                } else {
                    mBinding.imgDelete.setVisibility(View.VISIBLE);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

    }


}
