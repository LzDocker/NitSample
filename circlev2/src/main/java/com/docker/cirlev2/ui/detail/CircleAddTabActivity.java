package com.docker.cirlev2.ui.detail;

import android.app.Activity;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;

import com.dcbfhd.utilcode.utils.CollectionUtils;
import com.dcbfhd.utilcode.utils.ToastUtils;
import com.docker.cirlev2.R;
import com.docker.cirlev2.databinding.Circlev2ActivityCircleAddClassBinding;
import com.docker.cirlev2.databinding.Circlev2ActivityCircleEditClassBinding;
import com.docker.cirlev2.util.CircleEditClassAdapter;
import com.docker.cirlev2.vm.CircleEditTabViewModel;
import com.docker.cirlev2.vo.entity.CircleTitlesVo;
import com.docker.cirlev2.vo.param.StaCirParam;
import com.docker.cirlev2.vo.pro.AppVo;
import com.docker.common.common.adapter.NitAbsSampleAdapter;
import com.docker.common.common.ui.base.NitCommonActivity;
import com.docker.common.common.widget.recycledrag.ItemTouchHelperCallback;
import com.docker.core.BR;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/*
 * 添加分类
 * */
public class CircleAddTabActivity extends NitCommonActivity<CircleEditTabViewModel, Circlev2ActivityCircleAddClassBinding> {

    private NitAbsSampleAdapter mAddTabAdapter;

    private CircleTitlesVo circleTitlesVo;

//    private int type;

    public static void startMe(Context context, CircleTitlesVo circleTitlesVo, int code) {
        Intent intent = new Intent(context, CircleAddTabActivity.class);
        Bundle bundle = new Bundle();
        bundle.putSerializable("circletitle", circleTitlesVo);
        intent.putExtras(bundle);
        ((Activity) context).startActivityForResult(intent, code);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.circlev2_activity_circle_add_class;
    }

    @Override
    public CircleEditTabViewModel getmViewModel() {
        return ViewModelProviders.of(this, factory).get(CircleEditTabViewModel.class);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        circleTitlesVo = (CircleTitlesVo) getIntent().getSerializableExtra("circletitle");
        super.onCreate(savedInstanceState);
    }

    @Override
    public void initView() {
        mToolbar.setTitle("添加分类");
        if (circleTitlesVo != null) {
            mBinding.edTabName.setText(circleTitlesVo.getName());
        }
        if (!TextUtils.isEmpty(mBinding.edTabName.getText().toString().trim())) {
            mBinding.ivDel.setVisibility(View.VISIBLE);
        } else {
            mBinding.ivDel.setVisibility(View.GONE);
        }
        mToolbar.setTvRight("保存", v -> {

        });

        //CircleTitlesVo
        mAddTabAdapter = new NitAbsSampleAdapter(R.layout.circlev2_item_pro, BR.item) {
            @Override
            public void onRealviewBind(ViewHolder holder, int position) {

            }
        };

        mBinding.ivDel.setOnClickListener(v -> {
            mBinding.edTabName.setText("");
        });

        mBinding.edTabName.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (!TextUtils.isEmpty(mBinding.edTabName.getText().toString().trim())) {
                    mBinding.ivDel.setVisibility(View.VISIBLE);
                } else {
                    mBinding.ivDel.setVisibility(View.GONE);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        AppVo appVo = new AppVo();
        appVo.name = "动态";
        appVo.id = "0";
        mAddTabAdapter.add(appVo);

        AppVo appVo1 = new AppVo();
        appVo1.name = "新闻";
        appVo1.setChecked(true);
        appVo1.id = "1";
        mAddTabAdapter.add(appVo1);

        AppVo appVo2 = new AppVo();
        appVo2.name = "问答";
        appVo2.id = "2";
        mAddTabAdapter.add(appVo2);

        mBinding.recyclerView.setLayoutManager(new LinearLayoutManager(this));
        mBinding.recyclerView.setAdapter(mAddTabAdapter);

    }

    @Override
    public void initObserver() {

    }

    @Override
    public void initRouter() {

    }

    private boolean checkData() {
        if (TextUtils.isEmpty(mBinding.edTabName.getText().toString().trim())) {
            ToastUtils.showShort(mBinding.edTabName.getHint());
            return false;
        }

        return true;
    }
}
