package com.bfhd.account.ui;

import android.arch.lifecycle.ViewModelProvider;
import android.arch.lifecycle.ViewModelProviders;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.view.View;

import com.alibaba.android.arouter.launcher.ARouter;
import com.bfhd.account.BR;
import com.bfhd.account.R;
import com.bfhd.account.databinding.AccountActivityMoneyDetailListBinding;
import com.bfhd.account.databinding.AccountItemMoneyDetailBinding;
import com.bfhd.account.vm.AccountViewModel;
import com.bfhd.circle.base.HivsBaseActivity;
import com.bfhd.circle.base.ViewEventResouce;
import com.bfhd.circle.base.adapter.HivsAbsSampleAdapter;
import com.bfhd.circle.utils.BdUtils;
import com.docker.common.common.utils.rxbus.RxBus;
import com.docker.common.common.utils.rxbus.RxEvent;
import com.docker.common.common.vo.MoneyDetailVo;
import com.docker.common.common.widget.empty.EmptyLayout;
import com.docker.common.common.widget.refresh.api.RefreshLayout;
import com.docker.common.common.widget.refresh.listener.OnRefreshListener;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.inject.Inject;

import io.reactivex.disposables.Disposable;

/*
 * 金额明细
 **/
public class AccounMoneyDetailListActivity extends HivsBaseActivity<AccountViewModel, AccountActivityMoneyDetailListBinding> {


    @Inject
    ViewModelProvider.Factory factory;

    private List<MoneyDetailVo> moneyDetailVoList;
    private Disposable disposable;
//    private TimePickerView pvTime;
    private String timeStamp;
    private HivsAbsSampleAdapter hivsAbsSampleAdapter;

    @Override
    protected int getLayoutId() {
        return R.layout.account_activity_money_detail_list;
    }

    @Override
    public AccountViewModel getmViewModel() {
        return ViewModelProviders.of(this, factory).get(AccountViewModel.class);
    }

    @Override
    protected void inject() {
        super.inject();
        ARouter.getInstance().inject(this);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        disposable = RxBus.getDefault().toObservable(RxEvent.class).subscribe(rxEvent -> {
            if (rxEvent.getT().equals("refresh_focus")) {

            }
        });
        mToolbar.setTitle("明细");
        mBinding.tvTime.setText(getCurrentTime());
    }


    public void initView() {
        mBinding.empty.hide();
        timeStamp = BdUtils.DateTimeStamp(getCurrentTime(), "yyyy.MM");
        mViewModel.getMoneyDetailList(timeStamp);

        hivsAbsSampleAdapter = new HivsAbsSampleAdapter(R.layout.account_item_money_detail, BR.item) {
            @Override
            public void onRealviewBind(ViewHolder holder, int position) {
                MoneyDetailVo moneyDetailVo = moneyDetailVoList.get(position);
                String type = moneyDetailVo.getDotype();
                AccountItemMoneyDetailBinding itembinding = (AccountItemMoneyDetailBinding) holder.getBinding();
                if ("1".equals(type)) {
                    itembinding.tvOperation.setTextColor(Color.parseColor("#BD4952"));
                } else if ("2".equals(type)) {
                    itembinding.tvOperation.setTextColor(Color.parseColor("#3F9A7C"));
                }
            }

        };

        mBinding.recyclerView.setAdapter(hivsAbsSampleAdapter);
        hivsAbsSampleAdapter.notifyDataSetChanged();

        mBinding.tvTime.setOnClickListener(v -> {
            showCancler();
        });


        mBinding.refresh.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(@NonNull RefreshLayout refreshLayout) {
                mViewModel.mPage = 1;
                mViewModel.getMoneyDetailList(timeStamp);

            }
        });

        mBinding.refresh.setOnLoadMoreListener(refreshLayout -> {
            mViewModel.getMoneyDetailList(timeStamp);
        });

        mBinding.empty.setOnretryListener(new EmptyLayout.OnretryListener() {
            @Override
            public void onretry() {
                mViewModel.mPage = 1;
                mViewModel.getMoneyDetailList(timeStamp);
            }
        });


    }

    private void showCancler() {
        Calendar selectedDate = Calendar.getInstance();
        Calendar startDate = Calendar.getInstance();
        //startDate.set(2013,1,1);
        Calendar endDate = Calendar.getInstance();
        //endDate.set(2020,1,1);

        //正确设置方式 原因：注意事项有说明
        startDate.set(2013, 0, 1);
        endDate.set(2080, 11, 31);

//        pvTime = new TimePickerBuilder(this, new OnTimeSelectListener() {
//            @Override
//            public void onTimeSelect(Date date, View v) {//选中事件回调
//                mBinding.tvTime.setText(getTime(date));
//                timeStamp = BdUtils.DateTimeStamp(getTime(date), "yyyy.MM");
//                mViewModel.mPage = 1;
//                mViewModel.getMoneyDetailList(timeStamp);
//            }
//        })
//                .setType(new boolean[]{true, true, false, false, false, false})// 默认全部显示
//                .setCancelText("取消")//取消按钮文字
//                .setSubmitText("确定")//确认按钮文字
//                .setContentTextSize(18)//滚轮文字大小
//                .setTitleSize(20)//标题文字大小
//                .setTitleText("")//标题文字
//                .setOutSideCancelable(true)//点击屏幕，点在控件外部范围时，是否取消显示
//                .isCyclic(true)//是否循环滚动
//                .setTitleColor(Color.BLACK)//标题文字颜色
//                .setSubmitColor(Color.BLACK)//确定按钮文字颜色
//                .setCancelColor(Color.BLACK)//取消按钮文字颜色
//                .setTitleBgColor(Color.WHITE)//标题背景颜色 Night mode
//                .setBgColor(Color.WHITE)//滚轮背景颜色 Night mode
//                .setDate(selectedDate)// 如果不设置的话，默认是系统时间*/
//                .setRangDate(startDate, endDate)//起始终止年月日设定
//                .setLabel("年", "月", "日", "时", "分", "秒")//默认设置为年月日时分秒
//                .isCenterLabel(false) //是否只显示中间选中项的label文字，false则每项item全部都带有label。
//                .isDialog(false)//是否显示为对话框样式
//                .build();
//        pvTime.show();
    }

    private String getTime(Date date) {
        String time = "";
//        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");//设置日期格式
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy.MM");
        time = simpleDateFormat.format(date);
        return time;
    }

    //获取当前时间
    private String getCurrentTime() {
        String currentTime = "";
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy.MM");
        currentTime = simpleDateFormat.format(new Date());
        return currentTime;
    }

    @Override
    public void OnVmEnentListner(ViewEventResouce viewEventResouce) {
        super.OnVmEnentListner(viewEventResouce);
        switch (viewEventResouce.eventType) {
            case 113:
                if (viewEventResouce.data != null) {
                    mBinding.empty.hide();
                    moneyDetailVoList = (List<MoneyDetailVo>) viewEventResouce.data;
                    if (moneyDetailVoList.size() > 0) {
                        if (mViewModel.mPage == 1) {
                            hivsAbsSampleAdapter.clear();
                        }
                        hivsAbsSampleAdapter.getmObjects().addAll(moneyDetailVoList);
                        hivsAbsSampleAdapter.notifyDataSetChanged();
                    } else {
                        if (mViewModel.mPage == 1) {
                            mBinding.empty.showNodata();
                        }
                    }
                }
                if (hivsAbsSampleAdapter.getmObjects().size() == 0) {
                    mBinding.empty.showNodata();
                }
                mBinding.refresh.finishRefresh();
                mBinding.refresh.finishLoadMore();
                break;
            case 114:
                mBinding.empty.showError();
                mBinding.refresh.finishRefresh();
                mBinding.refresh.finishLoadMore();
                break;
            case 542:
                break;
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (disposable != null) {
            disposable.dispose();
        }
    }
}
