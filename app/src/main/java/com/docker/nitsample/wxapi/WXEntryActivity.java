package com.docker.nitsample.wxapi;

import android.os.Bundle;

import com.docker.common.common.config.ThiredPartModel;
import com.tencent.mm.opensdk.openapi.IWXAPI;
import com.tencent.mm.opensdk.openapi.IWXAPIEventHandler;
import com.tencent.mm.opensdk.openapi.WXAPIFactory;
import com.umeng.socialize.weixin.view.WXCallbackActivity;

public class WXEntryActivity extends WXCallbackActivity implements IWXAPIEventHandler {

    private IWXAPI api;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        api = WXAPIFactory.createWXAPI(this, ThiredPartModel.WxAppid, false);
        api.registerApp(ThiredPartModel.WxAppid);
        handleIntent(getIntent());
    }


}
