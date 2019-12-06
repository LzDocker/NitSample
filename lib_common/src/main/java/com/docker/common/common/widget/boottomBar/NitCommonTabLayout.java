package com.docker.common.common.widget.boottomBar;

import android.content.Context;
import android.util.AttributeSet;

import com.flyco.tablayout.CommonTabLayout;

public class NitCommonTabLayout extends CommonTabLayout {

//    public int Speicaltab = 2;

    public NitCommonTabLayout(Context context) {
        super(context);
    }

    public NitCommonTabLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public NitCommonTabLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    public void setCurrentTab(int currentTab) {
//        if (currentTab != Speicaltab) {
        super.setCurrentTab(currentTab);
//        }
    }
}
