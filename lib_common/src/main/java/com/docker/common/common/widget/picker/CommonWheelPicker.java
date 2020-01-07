package com.docker.common.common.widget.picker;

import android.app.Activity;
import android.view.Gravity;
import android.view.View;

import com.docker.common.R;
import com.docker.common.common.command.ReplyCommandParam;

import java.util.ArrayList;
import java.util.List;

import cn.qqtheme.framework.picker.DatePicker;
import cn.qqtheme.framework.picker.DateTimePicker;
import cn.qqtheme.framework.picker.OptionPicker;
import cn.qqtheme.framework.picker.SinglePicker;
import cn.qqtheme.framework.picker.TimePicker;
import cn.qqtheme.framework.widget.WheelView;

import static cn.qqtheme.framework.picker.DateTimePicker.YEAR_MONTH;

public class CommonWheelPicker {

    public ArrayList<String> keyArray;
    public ArrayList<String> valueArr;

    public ArrayList<String> getKey() {
        return keyArray;
    }

    public ArrayList<String> getValue() {
        return valueArr;
    }

    public void showCommonPicker(Activity context, ArrayList<String> keyArray, ArrayList<String> valueArr, ReplyCommandParam replyCommandParam) {
        this.keyArray = keyArray;
        this.valueArr = valueArr;
        SinglePicker picker = new SinglePicker(context, valueArr);
        picker.setOffset(3);//显示的条目的偏移量，条数为（offset*2+1）
        picker.setGravity(Gravity.BOTTOM);//居中
        picker.setPressedTextColor(context.getResources().getColor(R.color.common_black_666));
        picker.setCancelTextColor(context.getResources().getColor(R.color.common_black_666));
        picker.setSubmitTextColor(context.getResources().getColor(R.color.common_black_666));
        picker.setTopHeight(45);
        picker.setTopLineVisible(false);
        picker.setContentPadding(0, 10);
        picker.setDividerColor(context.getResources().getColor(R.color.common_black_ce));
        picker.setTextColor(context.getResources().getColor(R.color.black));
        picker.setUseWeight(true);
        picker.setDividerRatio(WheelView.DividerConfig.FILL);
        picker.setLineSpaceMultiplier(3);
        picker.setCycleDisable(false);
        picker.setOnItemPickListener(new OptionPicker.OnOptionPickListener() {
            @Override
            public void onOptionPicked(int index, String item) {
                replyCommandParam.exectue(index);
            }
        });
        picker.show();
    }


    public static void showMouthPicker(Activity context, List<String> datelist, OptionPicker.OnOptionPickListener onOptionPickListener) {
        SinglePicker picker = new SinglePicker(context, datelist);
        picker.setOffset(3);//显示的条目的偏移量，条数为（offset*2+1）
        picker.setGravity(Gravity.BOTTOM);//居中
        picker.setPressedTextColor(context.getResources().getColor(R.color.common_black_666));
        picker.setCancelTextColor(context.getResources().getColor(R.color.common_black_666));
        picker.setSubmitTextColor(context.getResources().getColor(R.color.common_black_666));
        picker.setTopHeight(45);
        picker.setTopLineVisible(false);
        picker.setContentPadding(0, 10);
        picker.setDividerColor(context.getResources().getColor(R.color.common_black_ce));
        picker.setTextColor(context.getResources().getColor(R.color.black));
        picker.setUseWeight(true);
        picker.setDividerRatio(WheelView.DividerConfig.FILL);
        picker.setLineSpaceMultiplier(3);
        picker.setCycleDisable(false);
        picker.setOnItemPickListener(onOptionPickListener);
        picker.show();
    }

    public static void showSexPicker(Activity context, OptionPicker.OnOptionPickListener onOptionPickListener) {
        List<String> list = new ArrayList<>();
        list.add("保密");
        list.add("男");
        list.add("女");
        SinglePicker picker = new SinglePicker(context, list);
        picker.setOffset(2);//显示的条目的偏移量，条数为（offset*2+1）
        picker.setGravity(Gravity.BOTTOM);//居中
        picker.setCancelTextColor(context.getResources().getColor(R.color.common_black_666));
        picker.setSubmitTextColor(context.getResources().getColor(R.color.common_black_666));
        picker.setPressedTextColor(context.getResources().getColor(R.color.common_black_666));
        picker.setTopHeight(45);
        picker.setTopLineVisible(false);
        picker.setContentPadding(0, 10);
        picker.setDividerColor(context.getResources().getColor(R.color.common_black_ce));
        picker.setTextColor(context.getResources().getColor(R.color.black));
        picker.setUseWeight(false);
        picker.setDividerRatio(WheelView.DividerConfig.FILL);
        picker.setLineSpaceMultiplier(3);
        picker.setCycleDisable(true);
        picker.setOnItemPickListener(onOptionPickListener);
        picker.show();
    }

    public static void showBloodPicker(Activity context, OptionPicker.OnOptionPickListener onOptionPickListener) {
        List<String> list = new ArrayList<>();
        list.add("A型");
        list.add("B型");
        list.add("AB型");
        list.add("O型");
        SinglePicker picker = new SinglePicker(context, list);
        picker.setOffset(2);//显示的条目的偏移量，条数为（offset*2+1）
        picker.setGravity(Gravity.BOTTOM);//居中
        picker.setCancelTextColor(context.getResources().getColor(R.color.common_black_666));
        picker.setSubmitTextColor(context.getResources().getColor(R.color.common_black_666));
        picker.setPressedTextColor(context.getResources().getColor(R.color.common_black_666));
        picker.setTopHeight(45);
        picker.setTopLineVisible(false);
        picker.setContentPadding(0, 10);
        picker.setDividerColor(context.getResources().getColor(R.color.common_black_ce));
        picker.setTextColor(context.getResources().getColor(R.color.black));
        picker.setUseWeight(false);
        picker.setDividerRatio(WheelView.DividerConfig.FILL);
        picker.setLineSpaceMultiplier(3);
        picker.setCycleDisable(true);
        picker.setOnItemPickListener(onOptionPickListener);
        picker.show();
    }

    public static void showYearMouth(Activity context, DatePicker.OnYearMonthPickListener onOptionPickListener) {

        DatePicker picker = new DatePicker(context, YEAR_MONTH);
        picker.setRangeStart(2000, 1);
        picker.setRangeEnd(2090, 12);
        picker.setLabel("    ", "    ", "");
        picker.setOffset(2);//显示的条目的偏移量，条数为（offset*2+1）
        picker.setGravity(Gravity.BOTTOM);//居中
        picker.setCancelTextColor(context.getResources().getColor(R.color.common_black));
        picker.setSubmitTextColor(context.getResources().getColor(R.color.colorPrimary));
        picker.setDividerConfig(new WheelView.DividerConfig().setThick(8f));
        picker.setPressedTextColor(context.getResources().getColor(R.color.common_black_666));
        picker.setTopHeight(45);
        picker.setTopLineVisible(false);
        picker.setContentPadding(0, 10);
        picker.setDividerColor(context.getResources().getColor(R.color.colorPrimary));
        picker.setTextColor(context.getResources().getColor(R.color.black));
        picker.setUseWeight(false);
        picker.setDividerRatio(WheelView.DividerConfig.FILL);
        picker.setLineSpaceMultiplier(3);
        picker.setCycleDisable(true);
        picker.setOnDatePickListener(onOptionPickListener);
        picker.show();
    }

    public static void showYearMouthDay(Activity context, DatePicker.OnYearMonthDayPickListener onOptionPickListener) {
        DatePicker picker = new DatePicker(context, DateTimePicker.YEAR_MONTH_DAY);
        picker.setRangeStart(2020, 1, 1);
        picker.setRangeEnd(2090, 12, 31);
        picker.setLabel("    ", "    ", "");
        picker.setOffset(2);//显示的条目的偏移量，条数为（offset*2+1）
        picker.setGravity(Gravity.BOTTOM);//居中
        picker.setCancelTextColor(context.getResources().getColor(R.color.common_black));
        picker.setSubmitTextColor(context.getResources().getColor(R.color.colorPrimary));
        picker.setDividerConfig(new WheelView.DividerConfig().setThick(8f));
        picker.setPressedTextColor(context.getResources().getColor(R.color.common_black_666));
        picker.setTopHeight(45);
        picker.setTopLineVisible(false);
        picker.setContentPadding(0, 10);
        picker.setDividerColor(context.getResources().getColor(R.color.colorPrimary));
        picker.setTextColor(context.getResources().getColor(R.color.black));
        picker.setUseWeight(false);
        picker.setDividerRatio(WheelView.DividerConfig.FILL);
        picker.setLineSpaceMultiplier(3);
        picker.setCycleDisable(true);
        picker.setOnDatePickListener(onOptionPickListener);
        picker.show();
    }

    public static void showYearMouthDayHourSecond(Activity context, DatePicker.OnYearMonthDayPickListener onOptionPickListener) {
        DatePicker picker = new DatePicker(context, DateTimePicker.YEAR_MONTH_DAY);
        picker.setRangeStart(2020, 1, 1);
        picker.setRangeEnd(2090, 12, 31);
        picker.setLabel("    ", "    ", "");
        picker.setOffset(2);//显示的条目的偏移量，条数为（offset*2+1）
        picker.setGravity(Gravity.BOTTOM);//居中
        picker.setCancelTextColor(context.getResources().getColor(R.color.common_black));
        picker.setSubmitTextColor(context.getResources().getColor(R.color.colorPrimary));
        picker.setDividerConfig(new WheelView.DividerConfig().setThick(8f));
        picker.setPressedTextColor(context.getResources().getColor(R.color.common_black_666));
        picker.setTopHeight(45);
        picker.setTopLineVisible(false);
        picker.setContentPadding(0, 10);
        picker.setDividerColor(context.getResources().getColor(R.color.colorPrimary));
        picker.setTextColor(context.getResources().getColor(R.color.black));
        picker.setUseWeight(false);
        picker.setDividerRatio(WheelView.DividerConfig.FILL);
        picker.setLineSpaceMultiplier(3);
        picker.setCycleDisable(true);
        picker.setOnDatePickListener(onOptionPickListener);
        picker.show();
    }

    public static void showTimePicker(Activity context, DateTimePicker.OnYearMonthDayTimePickListener onOptionPickListener) {

        DateTimePicker picker = new DateTimePicker(context, DateTimePicker.HOUR_24);
        picker.setDateRangeStart(2020, 1, 1);
        picker.setDateRangeEnd(2050, 12, 31);
        picker.setTimeRangeStart(1, 0);
        picker.setTimeRangeEnd(23, 0);
        picker.setOffset(2);//显示的条目的偏移量，条数为（offset*2+1）
        picker.setGravity(Gravity.BOTTOM);//居中
        picker.setCancelTextColor(context.getResources().getColor(R.color.common_black));
        picker.setSubmitTextColor(context.getResources().getColor(R.color.colorPrimaryDark));
        picker.setDividerConfig(new WheelView.DividerConfig().setThick(8f));
        picker.setPressedTextColor(context.getResources().getColor(R.color.common_black_666));
        picker.setTopHeight(45);
        picker.setTopLineVisible(false);
        picker.setContentPadding(0, 10);
        picker.setDividerColor(context.getResources().getColor(R.color.colorPrimary));
        picker.setTextColor(context.getResources().getColor(R.color.black));
        picker.setUseWeight(false);
        picker.setDividerRatio(WheelView.DividerConfig.FILL);
        picker.setLineSpaceMultiplier(3);
        picker.setCycleDisable(true);
        picker.setOnDateTimePickListener(onOptionPickListener);
        picker.show();
    }

    public static void showTimePicker(Activity context, int[] ints, DateTimePicker.OnYearMonthDayTimePickListener onOptionPickListener) {
        DateTimePicker picker = new DateTimePicker(context, DateTimePicker.HOUR_24);
        picker.setDateRangeStart(ints[0], ints[1], ints[2]);
        picker.setDateRangeEnd(2050, 12, 31);
        picker.setTimeRangeStart(ints[3], ints[4]);
        picker.setTimeRangeEnd(23, 0);
        picker.setOffset(2);//显示的条目的偏移量，条数为（offset*2+1）
        picker.setGravity(Gravity.BOTTOM);//居中
        picker.setCancelTextColor(context.getResources().getColor(R.color.common_black));
        picker.setSubmitTextColor(context.getResources().getColor(R.color.colorPrimaryDark));
        picker.setDividerConfig(new WheelView.DividerConfig().setThick(8f));
        picker.setPressedTextColor(context.getResources().getColor(R.color.common_black_666));
        picker.setTopHeight(45);
        picker.setTopLineVisible(false);
        picker.setContentPadding(0, 10);
        picker.setDividerColor(context.getResources().getColor(R.color.colorPrimary));
        picker.setTextColor(context.getResources().getColor(R.color.black));
        picker.setUseWeight(false);
        picker.setDividerRatio(WheelView.DividerConfig.FILL);
        picker.setLineSpaceMultiplier(3);
        picker.setCycleDisable(true);
        picker.setOnDateTimePickListener(onOptionPickListener);
        picker.show();
    }


}
