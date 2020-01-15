package com.docker.apps.intvite.db;

import android.databinding.BindingAdapter;
import android.databinding.ObservableList;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;
import android.widget.ViewFlipper;

import com.docker.apps.R;

public class FliperDataBindAdapter {

    @BindingAdapter(value = {"flipper"}, requireAll = false)
    public static void loadflipper(ViewFlipper viewFlipper, ObservableList<String> fliperDataList) {

        if (fliperDataList != null && fliperDataList.size() > 0) {
            for (int i = 0; i < fliperDataList.size(); i++) {
                View view = LayoutInflater.from(viewFlipper.getContext()).inflate(R.layout.pro_item_fliper_notice, null);
                TextView tv_show = view.findViewById(R.id.tv_notice);
                tv_show.setText(fliperDataList.get(i));
                viewFlipper.addView(view);
            }
            viewFlipper.setInAnimation(viewFlipper.getContext(), R.anim.fliper_in);
            viewFlipper.setOutAnimation(viewFlipper.getContext(), R.anim.fliper_out);
            viewFlipper.setFlipInterval(5000);
            viewFlipper.setAutoStart(true);
            viewFlipper.isAutoStart();
        }


    }
}
