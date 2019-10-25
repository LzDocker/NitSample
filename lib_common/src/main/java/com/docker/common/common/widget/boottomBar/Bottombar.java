package com.docker.common.common.widget.boottomBar;

import com.docker.common.common.config.CommonWidgetModel;
import com.flyco.tablayout.listener.CustomTabEntity;

import java.util.ArrayList;

import static com.docker.common.common.config.CommonWidgetModel.mIconSelectIds;
import static com.docker.common.common.config.CommonWidgetModel.mIconUnselectIds;

public class Bottombar {
    private ArrayList<CustomTabEntity> mTabEntities = new ArrayList<>();

    public ArrayList<CustomTabEntity> initBotombar() {
        for (int i = 0; i < CommonWidgetModel.mMainBottomTitles.length; i++) {
            mTabEntities.add(new TabEntity(CommonWidgetModel.mMainBottomTitles[i], mIconSelectIds[i], mIconUnselectIds[i]));
        }
        return mTabEntities;
    }
}
