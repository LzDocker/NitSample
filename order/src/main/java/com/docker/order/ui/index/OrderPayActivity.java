package com.docker.order.ui.index;

import android.annotation.SuppressLint;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.widget.Toast;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.android.arouter.launcher.ARouter;
import com.alipay.sdk.app.PayTask;
import com.dcbfhd.utilcode.utils.ToastUtils;
import com.docker.common.common.config.ThiredPartConfig;
import com.docker.common.common.router.AppRouter;
import com.docker.common.common.ui.base.NitCommonActivity;
import com.docker.common.common.utils.PayResult;
import com.docker.common.common.utils.cache.CacheUtils;
import com.docker.common.common.utils.rxbus.RxBus;
import com.docker.common.common.utils.rxbus.RxEvent;
import com.docker.common.common.vo.PayOrederVo;
import com.docker.common.common.vo.UserInfoVo;
import com.docker.order.R;
import com.docker.order.databinding.ProOrderPayActivityBinding;
import com.docker.order.vm.OrderMakerViewModel;
import com.tencent.mm.opensdk.modelpay.PayReq;
import com.tencent.mm.opensdk.openapi.IWXAPI;
import com.tencent.mm.opensdk.openapi.WXAPIFactory;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;

/*
 * 订单支付
 **/
@Route(path = AppRouter.ORDER_PAY)
public class OrderPayActivity extends NitCommonActivity<OrderMakerViewModel, ProOrderPayActivityBinding> {

    private int payType = 0;

    private Disposable mdisposable;

    private Disposable disposable;

    private PayOrederVo payOrederVo;

    private static final int SDK_PAY_FLAG = 1;

    @Override
    protected int getLayoutId() {
        return R.layout.pro_order_pay_activity;
    }

    @Override
    public OrderMakerViewModel getmViewModel() {
        return ViewModelProviders.of(this, factory).get(OrderMakerViewModel.class);
    }

    @Override
    protected void inject() {
        super.inject();
        ARouter.getInstance().inject(this);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        payOrederVo = (PayOrederVo) getIntent().getSerializableExtra("payOrederVo");
        mBinding.tvTotalmoney.setText(payOrederVo.payTotal);
        disposable = RxBus.getDefault().toObservable(RxEvent.class).subscribe(rxEvent -> {
            if (rxEvent.getT().equals("refresh_buy")) {
                ARouter.getInstance().build(AppRouter.ORDER_PAY_SUCC).navigation();
                finish();
            }
        });
    }

    @Override
    public void initView() {
        mToolbar.setTitle("支付订单");
        mBinding.rlWx.setOnClickListener(v -> {
            mBinding.checkboxWx.setChecked(true);
            mBinding.checkboxZfb.setChecked(false);
            payType = 1;
        });
        mBinding.rlZfb.setOnClickListener(v -> {
            mBinding.checkboxWx.setChecked(false);
            mBinding.checkboxZfb.setChecked(true);
            payType = 0;
        });
        timer();
        mBinding.orderpay.setOnClickListener(v -> {
            if (payType == 1) { // wx
                HashMap<String, String> param = new HashMap<>();
                UserInfoVo userInfoVo = CacheUtils.getUser();
                UUID uuid = UUID.randomUUID();
                param.put("sysSn", payOrederVo.sysSn);
                param.put("uuid", uuid.toString());
                param.put("memberid", userInfoVo.uid);
                mViewModel.fechWxPreOrder(param);
            } else { // zfb
                processAliPay(payOrederVo.alipayStr);
            }
        });
    }

    private void processAliPay(String orderinfo) {  // 支付宝
        Runnable payRunnable = () -> {
            PayTask alipay = new PayTask(OrderPayActivity.this);
            Map<String, String> result = alipay.payV2(orderinfo, true);
            Message msg = new Message();
            msg.what = SDK_PAY_FLAG;
            msg.obj = result;
            mHandler.sendMessage(msg);
        };
        Thread payThread = new Thread(payRunnable);
        payThread.start();
    }

    @SuppressLint("HandlerLeak")
    private Handler mHandler = new Handler() {
        @SuppressWarnings("unused")
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case SDK_PAY_FLAG: {
                    PayResult payResult = new PayResult((Map<String, String>) msg.obj);
                    String resultInfo = payResult.getResult();
                    String resultStatus = payResult.getResultStatus();
                    if (TextUtils.equals(resultStatus, "9000")) {
                        Toast.makeText(OrderPayActivity.this, "支付成功", Toast.LENGTH_SHORT).show();
                        RxBus.getDefault().post(new RxEvent<>("refresh_buy", ""));
                    } else {
                        if (TextUtils.equals(resultStatus, "8000")) {
                            Toast.makeText(OrderPayActivity.this, "支付结果确认中", Toast.LENGTH_SHORT).show();
                        } else {
                            Toast.makeText(OrderPayActivity.this, "支付失败", Toast.LENGTH_SHORT).show();
                        }
                    }
                    break;
                }
                default:
                    break;
            }
        }

    };

    private void timer() {
        final int count = 1800;
        Observable.interval(0, 1, TimeUnit.SECONDS)
                .take(count)
                .map(aLong -> count - aLong)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<Long>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                        mdisposable = d;
                    }

                    @Override
                    public void onNext(Long number) {
                        int s = (int) (number / 60);
                        int m = (int) (number % 60);
                        String ss;
                        if (String.valueOf(s).length() != 2) {
                            ss = "0" + s;
                        } else {
                            ss = String.valueOf(s);
                        }
                        String mm;
                        if (String.valueOf(m).length() != 2) {
                            mm = "0" + m;
                        } else {
                            mm = String.valueOf(m);
                        }
                        mBinding.tvTimer.setText("支付剩余时间:  00:" + ss + ":" + mm);

                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onComplete() {
                        ToastUtils.showShort("超时未支付");
                        finish();
                    }
                });
    }


    @Override
    public void initObserver() {

        mViewModel.mWxPrepayLv.observe(this, wxOrderVo -> {
            if (wxOrderVo != null) {
                IWXAPI wxapi = WXAPIFactory.createWXAPI(this, ThiredPartConfig.WxAppid);  //应用ID 即微信开放平台审核通过的应用APPID
                wxapi.registerApp(ThiredPartConfig.WxAppid);
                PayReq req = new PayReq();
                req.appId = wxOrderVo.appid;
                req.partnerId = wxOrderVo.partnerid;
                req.prepayId = wxOrderVo.prepayid;
                req.nonceStr = wxOrderVo.noncestr;
                req.timeStamp = wxOrderVo.timestamp;
                req.packageValue = "Sign=WXPay";
                req.sign = wxOrderVo.sign;
                wxapi.sendReq(req);
            }
        });
    }

    @Override
    public void initRouter() {

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mdisposable != null) {
            mdisposable.dispose();
        }
        if (disposable != null) {
            disposable.dispose();
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {

        }
    }
}
