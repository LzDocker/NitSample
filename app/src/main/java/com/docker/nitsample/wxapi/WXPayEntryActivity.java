package com.docker.nitsample.wxapi;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

import com.docker.common.common.config.ThiredPartModel;
import com.tencent.mm.opensdk.modelbase.BaseReq;
import com.tencent.mm.opensdk.modelbase.BaseResp;
import com.tencent.mm.opensdk.openapi.IWXAPI;
import com.tencent.mm.opensdk.openapi.IWXAPIEventHandler;
import com.tencent.mm.opensdk.openapi.WXAPIFactory;

public class WXPayEntryActivity extends Activity implements IWXAPIEventHandler {


    private IWXAPI api;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        setContentView(R.layout.wxpay_result);
        api = WXAPIFactory.createWXAPI(this, ThiredPartModel.WxAppid);
        api.handleIntent(getIntent(), this);
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        setIntent(intent);
        api.handleIntent(intent, this);
    }

    @Override
    public void onReq(BaseReq req) {
    }

    @Override
    public void onResp(BaseResp resp) {
//        Log.d(TAG, "onPayFinish, errCode = " + resp.errCode);
//        if (resp.getType() == ConstantsAPI.COMMAND_PAY_BY_WX) {
//            if (resp.errCode == 0) { //支付成功
//                com.docker.core.rxbus.RxBus.getDefault().post(new RxEvent<>("refresh_buy", ""));
//                Toast.makeText(this, "支付成功", Toast.LENGTH_LONG).show();
//
//            } else {
//                Toast.makeText(this, "支付失败，请重试", Toast.LENGTH_SHORT).show();
//            }
//            finish();
//        }
    }
}