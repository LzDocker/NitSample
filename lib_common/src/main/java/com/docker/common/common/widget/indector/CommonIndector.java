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
import net.lucode.hackware.magicindicator.buildins.UIUtil;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.CommonNavigator;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.abs.CommonNavigatorAdapter;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.abs.IPagerIndicator;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.abs.IPagerTitleView;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.indicators.LinePagerIndicator;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.titles.SimplePagerTitleView;

import java.util.ArrayList;

public class CommonIndector {
    public CommonNavigator initMagicIndicator(String mTitleList[], ViewPager viewPager, MagicIndicator magicIndicator, Activity activity) {
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
                ((ColorFlipPagerTitleView) simplePagerTitleView).setNomalTextSize(15f);
                ((ColorFlipPagerTitleView) simplePagerTitleView).setSelectTextSize(16f);
                simplePagerTitleView.setSelectedColor(Color.BLACK);
                simplePagerTitleView.setNormalColor(Color.GRAY);
                simplePagerTitleView.setOnClickListener(v -> viewPager.setCurrentItem(index, false));
                return simplePagerTitleView;
            }

            @Override
            public IPagerIndicator getIndicator(Context context) {
                LinePagerIndicator indicator = new LinePagerIndicator(context);
                indicator.setMode(LinePagerIndicator.MODE_WRAP_CONTENT);
                indicator.setStartInterpolator(new AccelerateInterpolator());
                indicator.setEndInterpolator(new DecelerateInterpolator(2.0f));
                indicator.setColors(activity.getResources().getColor(R.color.colorPrimaryDark));
                indicator.setLineHeight(UIUtil.dip2px(context, 1));
                indicator.setLineWidth(UIUtil.dip2px(context, 45));
                indicator.setRoundRadius(UIUtil.dip2px(context, 3));
                return indicator;
            }
        });
        magicIndicator.setNavigator(mCommonNavigator);
        ViewPagerHelper.bind(magicIndicator, viewPager);
        return mCommonNavigator;
    }

    public CommonNavigator initMagicIndicatorScroll(String mTitleList[], ViewPager viewPager, MagicIndicator magicIndicator, Activity activity) {
        CommonNavigator mCommonNavigator = new CommonNavigator(activity);
        mCommonNavigator.setAdjustMode(false);
        mCommonNavigator.setAdapter(new CommonNavigatorAdapter() {
            @Override
            public int getCount() {
                return mTitleList == null ? 0 : mTitleList.length;
            }

            @Override
            public IPagerTitleView getTitleView(Context context, final int index) {
                SimplePagerTitleView simplePagerTitleView = new ColorFlipPagerTitleView(context);
                simplePagerTitleView.setText(mTitleList[index]);
                ((ColorFlipPagerTitleView) simplePagerTitleView).setNomalTextSize(15f);
                ((ColorFlipPagerTitleView) simplePagerTitleView).setSelectTextSize(16f);
                simplePagerTitleView.setSelectedColor(Color.BLACK);
                simplePagerTitleView.setNormalColor(Color.GRAY);
                simplePagerTitleView.setOnClickListener(v -> viewPager.setCurrentItem(index, false));
                return simplePagerTitleView;
            }

            @Override
            public IPagerIndicator getIndicator(Context context) {
                LinePagerIndicator indicator = new LinePagerIndicator(context);
                indicator.setMode(LinePagerIndicator.MODE_WRAP_CONTENT);
                indicator.setStartInterpolator(new AccelerateInterpolator());
                indicator.setEndInterpolator(new DecelerateInterpolator(2.0f));
                indicator.setColors(activity.getResources().getColor(R.color.colorPrimaryDark));
                indicator.setLineHeight(UIUtil.dip2px(context, 1));
                indicator.setLineWidth(UIUtil.dip2px(context, 45));
                indicator.setRoundRadius(UIUtil.dip2px(context, 3));
                return indicator;
            }
        });
        magicIndicator.setNavigator(mCommonNavigator);
        ViewPagerHelper.bind(magicIndicator, viewPager);
        return mCommonNavigator;
    }


    public ArrayList<String> mTitleList;

    public CommonNavigator initMagicIndicatorScrollSpac(ViewPager viewPager, MagicIndicator magicIndicator, Activity activity) {


        CommonNavigator mCommonNavigator = new CommonNavigator(activity);
        mCommonNavigator.setAdjustMode(false);
        CommonNavigatorAdapter commonNavigatorAdapter = new CommonNavigatorAdapter() {
            @Override
            public int getCount() {
                return mTitleList == null ? 0 : mTitleList.size();
            }

            @Override
            public IPagerTitleView getTitleView(Context context, final int index) {
                SimplePagerTitleView simplePagerTitleView = new ColorFlipPagerTitleView(context);
                simplePagerTitleView.setText(mTitleList.get(index));
                ((ColorFlipPagerTitleView) simplePagerTitleView).setNomalTextSize(15f);
                ((ColorFlipPagerTitleView) simplePagerTitleView).setSelectTextSize(16f);
                simplePagerTitleView.setSelectedColor(Color.BLACK);
                simplePagerTitleView.setNormalColor(Color.GRAY);
                simplePagerTitleView.setOnClickListener(v -> viewPager.setCurrentItem(index, false));
                return simplePagerTitleView;
            }

            @Override
            public IPagerIndicator getIndicator(Context context) {
                LinePagerIndicator indicator = new LinePagerIndicator(context);
                indicator.setMode(LinePagerIndicator.MODE_WRAP_CONTENT);
                indicator.setStartInterpolator(new AccelerateInterpolator());
                indicator.setEndInterpolator(new DecelerateInterpolator(2.0f));
                indicator.setColors(activity.getResources().getColor(R.color.colorPrimaryDark));
                indicator.setLineHeight(UIUtil.dip2px(context, 1));
                indicator.setLineWidth(UIUtil.dip2px(context, 45));
                indicator.setRoundRadius(UIUtil.dip2px(context, 3));
                return indicator;
            }
        };
        mCommonNavigator.setAdapter(commonNavigatorAdapter);
        magicIndicator.setNavigator(mCommonNavigator);
        ViewPagerHelper.bind(magicIndicator, viewPager);
        return mCommonNavigator;
    }
}
