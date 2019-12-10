package com.docker.nitsample.ui.edit;

import android.content.Context;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;

import com.docker.common.R;
import com.docker.common.common.adapter.CommonpagerAdapter;
import com.docker.common.common.ui.base.NitCommonActivity;
import com.lxj.xpopup.core.DrawerPopupView;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.annotations.NonNull;

/**
 * Description:
 * Create by dance, at 2019/5/5
 */
public class EditPagerDrawerPopup extends DrawerPopupView {

    NitCommonActivity nitCommonActivity;

    public EditPagerDrawerPopup(@NonNull Context context) {
        super(context);
    }

    @Override
    protected int getImplLayoutId() {
        return R.layout.common_custom_pager_drawer;
    }

    TabLayout tabLayout;
    ViewPager pager;

    @Override
    protected void onCreate() {
        super.onCreate();
        tabLayout = findViewById(R.id.tabLayout);
        pager = findViewById(R.id.pager);
        nitCommonActivity = (NitCommonActivity) getContext();

        List<Fragment> fragments = new ArrayList<>();

        EditCoutainerFragment editCoutainerFragment = EditCoutainerFragment.getInstance();
        fragments.add(editCoutainerFragment);
        String[] titles = new String[]{"card"};
        pager.setAdapter(new CommonpagerAdapter(nitCommonActivity.getSupportFragmentManager(), fragments, titles));
        tabLayout.setupWithViewPager(pager);
    }

    @Override
    protected void onShow() {
        super.onShow();
    }

    @Override
    protected void onDismiss() {
        super.onDismiss();
    }

}
