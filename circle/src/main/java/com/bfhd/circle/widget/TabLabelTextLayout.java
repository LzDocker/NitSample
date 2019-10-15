package com.bfhd.circle.widget;

import android.content.Context;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bfhd.circle.R;

public class TabLabelTextLayout extends LinearLayout {

    private Context context;
    public TabLabelTextLayout(Context context) {
        super(context);
        this.context = context;
        initview();
    }
    private void initview(){
        this.setOrientation(LinearLayout.HORIZONTAL);
        View view =  LayoutInflater.from(context).inflate(R.layout.circle_tab_item_lable,this);
        this.setGravity(Gravity.CENTER_VERTICAL);
    }
}
