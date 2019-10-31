package com.docker.common.common.widget.indector;

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.support.v4.view.ViewPager;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.DecelerateInterpolator;

import com.docker.common.R;
import com.docker.common.common.widget.ColorFlipPagerTitleView;
import net.lucode.hackware.magicindicator.MagicIndicator;
import net.lucode.hackware.magicindicator.ViewPagerHelper;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.CommonNavigator;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.abs.CommonNavigatorAdapter;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.abs.IPagerIndicator;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.abs.IPagerTitleView;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.indicators.LinePagerIndicator;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.titles.SimplePagerTitleView;

public class CommonIndector {
    public void initMagicIndicator(String mTitleList[], ViewPager viewPager, MagicIndicator magicIndicator, Activity activity) {
        CommonNavigator mCommonNavigator = new CommonNavigator(activity);
        mCommonNavigator.setAdjustMode(true);
        mCommonNavigator.setAdapter(new CommonNavigatorAdapter() {
            @Override
            public int getCount() {
                return mTitleList == null ? 0 : mTitleList.length;
            }

            @Override
            public IPagerTitleView getTitleView(Context context, final int index) {
                SimplePagerTitleView simplePagerTitleView = new ColorFlipPagerTitleView(context);
                simplePagerTitleView.setText(mTitleList[index]);
                ((ColorFlipPagerTitleView) simplePagerTitleView).setNomalTextSize(13f);
                ((ColorFlipPagerTitleView) simplePagerTitleView).setSelectTextSize(15f);
                simplePagerTitleView.setSelectedColor(Color.BLACK);
                simplePagerTitleView.setNormalColor(Color.GRAY);
                simplePagerTitleView.setOnClickListener(v -> viewPager.setCurrentItem(index));
                return simplePagerTitleView;
            }
            @Override
            public IPagerIndicator getIndicator(Context context) {
                LinePagerIndicator indicator = new LinePagerIndicator(context);
                indicator.setMode(LinePagerIndicator.MODE_WRAP_CONTENT);
                indicator.setStartInterpolator(new AccelerateInterpolator());
                indicator.setEndInterpolator(new DecelerateInterpolator(2.0f));
                indicator.setColors(activity.getResources().getColor(R.color.colorPrimaryDark));
                return indicator;
            }
        });
        magicIndicator.setNavigator(mCommonNavigator);
        ViewPagerHelper.bind(magicIndicator, viewPager);
    }

}
