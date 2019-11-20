package com.docker.module_im.main.activity;

import android.os.Bundle;
import android.widget.TextView;

import com.docker.module_im.BuildConfig;
import com.docker.module_im.R;
import com.netease.nim.uikit.api.wrapper.NimToolBarOptions;
import com.netease.nim.uikit.common.activity.ToolBarOptions;
import com.netease.nim.uikit.common.activity.UI;

public class AboutActivity extends UI {

    private TextView version;
    private TextView versionGit;
    private TextView versionDate;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.about_layout);

        ToolBarOptions options = new NimToolBarOptions();
        setToolBar(R.id.toolbar, options);

        findViews();
        initViewData();
    }

    @Override
    protected void onPause() {
        super.onPause();
    }

    private void findViews() {
        version = findViewById(R.id.version_detail);
        versionGit = findViewById(R.id.version_detail_git);
        versionDate = findViewById(R.id.version_detail_date);
    }

    private void initViewData() {

    }
}
