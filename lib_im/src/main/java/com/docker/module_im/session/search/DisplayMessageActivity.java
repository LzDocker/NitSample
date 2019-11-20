package com.docker.module_im.session.search;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;

import com.docker.module_im.R;
import com.gyf.immersionbar.ImmersionBar;
import com.netease.nim.uikit.api.wrapper.NimToolBarOptions;
import com.netease.nim.uikit.business.session.module.Container;
import com.netease.nim.uikit.business.session.module.ModuleProxy;
import com.netease.nim.uikit.business.session.module.list.MessageListPanelEx;
import com.netease.nim.uikit.business.uinfo.UserInfoHelper;
import com.netease.nim.uikit.common.activity.ToolBarOptions;
import com.netease.nim.uikit.common.activity.UI;
import com.netease.nimlib.sdk.msg.constant.SessionTypeEnum;
import com.netease.nimlib.sdk.msg.model.IMMessage;

/**
 * 搜索结果消息列表界面
 * <p>
 * Created by huangjun on 2017/1/11.
 */
public class DisplayMessageActivity extends UI implements ModuleProxy {

    private static String EXTRA_ANCHOR = "anchor";

    public static void start(Context context, IMMessage anchor) {
        Intent intent = new Intent();
        intent.setClass(context, DisplayMessageActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);

        //search extra
        intent.putExtra(EXTRA_ANCHOR, anchor);

        context.startActivity(intent);
    }

    // context
    private SessionTypeEnum sessionType;
    private String account; // 对方帐号
    private IMMessage anchor;

    private MessageListPanelEx messageListPanel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        View rootView = LayoutInflater.from(this).inflate(R.layout.message_history_activity, null);
        setContentView(rootView);

        ToolBarOptions options = new NimToolBarOptions();
        setToolBar(R.id.toolbar, options);

        onParseIntent();

        Container container = new Container(this, account, sessionType, this);
        messageListPanel = new MessageListPanelEx(container, rootView, anchor, true, false);
        initImmersionBar();
    }

    protected void initImmersionBar() {
        ImmersionBar.with(this)
                .statusBarColor(com.netease.nim.uikit.R.color.common_white)
                .statusBarDarkFont(true)   //状态栏字体是深色，不写默认为亮色
                .autoDarkModeEnable(true) //自动状态栏字体和导航栏图标变色，必须指定状态栏颜色和导航栏颜色才可以自动变色哦
                .autoStatusBarDarkModeEnable(true, 0.2f) //自动状态栏字体变色，必须指定状态栏颜色才可以自动变色哦
                .autoNavigationBarDarkModeEnable(true, 0.2f) //自动导航栏图标变色，必须指定导航栏颜色才可以自动变色哦
                .flymeOSStatusBarFontColor("#000000")  //修改flyme OS状态栏字体颜色
                .navigationBarColor(com.netease.nim.uikit.R.color.transparent_white)
                .init();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        messageListPanel.onDestroy();
//        ImmersionBar.with(this).destroy();
    }

    protected void onParseIntent() {
        anchor = (IMMessage) getIntent().getSerializableExtra(EXTRA_ANCHOR);
        account = anchor.getSessionId();
        sessionType = anchor.getSessionType();

        setTitle(UserInfoHelper.getUserTitleName(account, sessionType));
    }

    @Override
    public boolean sendMessage(IMMessage msg) {
        return false;
    }

    @Override
    public void onInputPanelExpand() {

    }

    @Override
    public void onItemFooterClick(IMMessage message) {

    }

    @Override
    public void shouldCollapseInputPanel() {

    }

    @Override
    public boolean isLongClickEnabled() {
        return true;
    }

}
