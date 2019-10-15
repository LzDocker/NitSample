package com.bfhd.circle.ui.circle;

import android.arch.lifecycle.ViewModelProvider;
import android.arch.lifecycle.ViewModelProviders;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Pair;
import android.view.View;

import com.bfhd.circle.R;
import com.bfhd.circle.base.EmptyVm;
import com.bfhd.circle.base.HivsBaseActivity;
import com.bfhd.circle.databinding.CircleActivityCircleSearchBinding;
import com.bfhd.circle.vo.bean.StaCircleListParam;
import com.dcbfhd.utilcode.utils.FragmentUtils;
import java.util.ArrayList;

import javax.inject.Inject;

/*
 * 圈子搜索
 * */
public class CircleSearchActivity extends HivsBaseActivity<EmptyVm, CircleActivityCircleSearchBinding> {

    @Inject
    ViewModelProvider.Factory factory;
    private CircleListFragment search;
    private String keyword;

    @Override
    protected int getLayoutId() {
        return R.layout.circle_activity_circle_search;
    }

    @Override
    public EmptyVm getmViewModel() {
        return ViewModelProviders.of(this, factory).get(EmptyVm.class);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mToolbar.hide();
    }

    @Override
    public void initView() {
        mBinding.circleTvCancel.setOnClickListener(v -> {
            finish();
        });
        mBinding.circleEditInput.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                keyword = mBinding.circleEditInput.getText().toString().trim();
                if (TextUtils.isEmpty(keyword)) {
                    mBinding.circleSerachFrame.setVisibility(View.INVISIBLE);
                } else {
                    mBinding.circleSerachFrame.setVisibility(View.VISIBLE);
                    ArrayList<Pair<String, String>> pairList = new ArrayList<>();
                    pairList.add(new Pair("keyword", keyword));
                    search.UpdateReqParam(false, pairList,false);
                    search.OnRefresh(null);
                }
            }
        });

        StaCircleListParam sta = new StaCircleListParam();
        sta.Uity = 0;
        sta.ReqType = 1;
        search = CircleListFragment.newInstance(sta);
        FragmentUtils.add(getSupportFragmentManager(), search, R.id.circle_serach_frame);

        //        UpdateReqParam/
    }


}
