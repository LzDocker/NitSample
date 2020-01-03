package com.docker.apps.intvite.db;

import android.databinding.BindingAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;
import android.widget.ViewFlipper;

import com.docker.apps.R;

public class FliperDataBindAdapter {

    @BindingAdapter(value = {"flipper"}, requireAll = false)
    public static void loadflipper(ViewFlipper viewFlipper, String flipper) {

        for (int i = 0; i < 10; i++) {
            View view = LayoutInflater.from(viewFlipper.getContext()).inflate(R.layout.pro_item_fliper_notice, null);
            TextView tv_show = view.findViewById(R.id.tv_notice);
            tv_show.setText("test" + flipper + i);
            viewFlipper.addView(view);
        }
        viewFlipper.setInAnimation(viewFlipper.getContext(), R.anim.fliper_in);
        viewFlipper.setOutAnimation(viewFlipper.getContext(), R.anim.fliper_out);
        viewFlipper.setFlipInterval(5000);
        viewFlipper.setAutoStart(true);
        viewFlipper.isAutoStart();
    }
}
