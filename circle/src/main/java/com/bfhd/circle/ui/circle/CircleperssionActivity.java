package com.bfhd.circle.ui.circle;

import android.arch.lifecycle.ViewModelProvider;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import com.bfhd.circle.R;
import com.bfhd.circle.base.HivsBaseActivity;
import com.bfhd.circle.base.ViewEventResouce;
import com.bfhd.circle.databinding.CircleActivityCircleInfoBinding;
import com.bfhd.circle.databinding.CircleActivityCirclePerssionBinding;
import com.bfhd.circle.vm.CircleViewModel;
import com.bfhd.circle.vo.bean.StaCirParam;
import javax.inject.Inject;

/*
 * 圈子权限设置
 * */
public class CircleperssionActivity extends HivsBaseActivity<CircleViewModel, CircleActivityCirclePerssionBinding> {

    @Inject
    ViewModelProvider.Factory factory;
    //    private int type;
    private StaCirParam mStartParam;

    public static void startMe(Context context, StaCirParam startCircleBean) {

        Intent intent = new Intent(context, CircleperssionActivity.class);
        Bundle bundle = new Bundle();
        bundle.putSerializable("mStartParam", startCircleBean);
        intent.putExtras(bundle);
        context.startActivity(intent);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.circle_activity_circle_perssion;
    }

    @Override
    public CircleViewModel getmViewModel() {
        return ViewModelProviders.of(this, factory).get(CircleViewModel.class);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        mStartParam = (StaCirParam) getIntent().getSerializableExtra("mStartParam");
        super.onCreate(savedInstanceState);

    }

    @Override
    public void initView() {
        mToolbar.setTitle("圈子权限设置");
    }

    @Override
    public void OnVmEnentListner(ViewEventResouce viewEventResouce) {
        super.OnVmEnentListner(viewEventResouce);
        switch (viewEventResouce.eventType) {
            case 103: // 获取分类数据成功

                break;
            case 104: // 保存成功
                setResult(RESULT_OK);
                finish();
                break;
        }
    }


}
