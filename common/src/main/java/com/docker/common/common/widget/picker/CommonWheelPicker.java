package com.docker.common.common.widget.picker;

import android.app.Activity;
import android.view.Gravity;
import com.docker.common.R;
import com.docker.common.common.command.ReplyCommandParam;
import java.util.ArrayList;
import java.util.List;

import cn.qqtheme.framework.picker.OptionPicker;
import cn.qqtheme.framework.picker.SinglePicker;
import cn.qqtheme.framework.widget.WheelView;

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

}
