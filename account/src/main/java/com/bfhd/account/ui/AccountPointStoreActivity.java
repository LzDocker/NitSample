package com.bfhd.account.ui;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.arch.lifecycle.ViewModelProvider;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.webkit.JavascriptInterface;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.alibaba.android.arouter.launcher.ARouter;
import com.bfhd.account.R;
import com.bfhd.account.databinding.AccountPointStoreBinding;
import com.bfhd.account.vm.AccountViewModel;
import com.bfhd.circle.base.HivsBaseActivity;
import com.bfhd.circle.base.ViewEventResouce;
import com.dcbfhd.utilcode.utils.ToastUtils;
import com.docker.common.common.utils.cache.CacheUtils;
import com.docker.common.common.vo.UserInfoVo;
import com.google.gson.internal.LinkedTreeMap;

import java.util.HashMap;

import javax.inject.Inject;

public class AccountPointStoreActivity extends HivsBaseActivity<AccountViewModel, AccountPointStoreBinding> {
    @Inject
    ViewModelProvider.Factory factory;

    @Override
    public AccountViewModel getmViewModel() {
        return ViewModelProviders.of(this, factory).get(AccountViewModel.class);
    }


    @Override
    protected int getLayoutId() {
        return R.layout.account_point_store;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    protected void inject() {
        super.inject();
        ARouter.getInstance().inject(this);
    }

    @Override
    public void initView() {
        initWebview();
        mToolbar.getTvTitle().setOnClickListener(v -> {
//            ARouter.getInstance().build(AppRouter.Pro.Pro_Country_select)
//                    .withString("price", "10")
//                    .withString("pointaccount", "102")
//                    .withString("app_id", "1")
//                    .withString("id", "2")
//                    .navigation();

        });
    }


    private void initWebview() {
        if (Build.VERSION.SDK_INT > Build.VERSION_CODES.LOLLIPOP) {
            mBinding.pointStroeWebview.getSettings().setMixedContentMode(WebSettings.MIXED_CONTENT_ALWAYS_ALLOW);
        }
        mBinding.pointStroeWebview.getSettings().setJavaScriptEnabled(true);
        // 设置图片适合webView大小
        mBinding.pointStroeWebview.getSettings().setUseWideViewPort(false);
        WebSettings settings = mBinding.pointStroeWebview.getSettings();
        settings.setLayoutAlgorithm(WebSettings.LayoutAlgorithm.SINGLE_COLUMN);
        mBinding.pointStroeWebview.getSettings().setBlockNetworkImage(false);
        mBinding.pointStroeWebview.getSettings().setJavaScriptEnabled(true);
        // 设置图片适合webView大小
        mBinding.pointStroeWebview.getSettings().setUseWideViewPort(false);
        mBinding.pointStroeWebview.getSettings().setJavaScriptCanOpenWindowsAutomatically(true);
        mBinding.pointStroeWebview.getSettings().setUseWideViewPort(true);//关键点
        mBinding.pointStroeWebview.getSettings().setLayoutAlgorithm(WebSettings.LayoutAlgorithm.SINGLE_COLUMN);
        mBinding.pointStroeWebview.getSettings().setDisplayZoomControls(false);
        mBinding.pointStroeWebview.getSettings().setJavaScriptEnabled(true); // 设置支持javascript脚本
        mBinding.pointStroeWebview.getSettings().setAllowFileAccess(true); // 允许访问文件
        mBinding.pointStroeWebview.getSettings().setBuiltInZoomControls(false); // 设置显示缩放按钮
        mBinding.pointStroeWebview.getSettings().setLoadWithOverviewMode(true);
        mBinding.pointStroeWebview.getSettings().setUseWideViewPort(true);
        mBinding.pointStroeWebview.getSettings().setLayoutAlgorithm(WebSettings.LayoutAlgorithm.SINGLE_COLUMN);
        mBinding.pointStroeWebview.getSettings().setLoadWithOverviewMode(true);
        mBinding.pointStroeWebview.getSettings().setTextZoom(100);
        mBinding.pointStroeWebview.setWebChromeClient(new WebChromeClient() {
        });
//        http://app.cosri.org.cn/index.php?m=default.shopping_mall  积分商城页面
//        http://app.cosri.org.cn/index.php?m=default.shoppin_suc  (购买成功后的页面)
        UserInfoVo userInfoVo = CacheUtils.getUser();
        String url = "http://htj.wgc360.com/index.php?m=default.shopping_mall&uuid=" + userInfoVo.uuid;
//        String url = "http://app.cosri.org.cn/index.php?m=default.safe&id=21";
        mBinding.pointStroeWebview.loadUrl(url);
        mBinding.pointStroeWebview.getSettings().setJavaScriptEnabled(true);
        mBinding.pointStroeWebview.addJavascriptInterface(new MyInterface(this), "android");
        mBinding.pointStroeWebview.setWebViewClient(new WebViewClient() {
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                view.loadUrl(url);
                return true;
            }

            @Override
            public void onPageStarted(WebView view, String url, Bitmap favicon) {
                super.onPageStarted(view, url, favicon);
                //加载时
//                mBinding.get().empty.showLoading();
            }

            @Override
            public void onPageFinished(WebView view, String url) {
                super.onPageFinished(view, url);
                //加载完成
//                mBinding.empty.hide();
            }
        });
    }

    public class MyInterface {

        private Context context;

        public MyInterface(Context context) {
            this.context = context;
        }

        // 支付
        @SuppressLint("JavascriptInterface")
        @JavascriptInterface
        public void processPoint(final String[] parmarr) {
            ((Activity) context).runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    String app_id = parmarr[0];
                    String pointaccount = parmarr[1];//个人积分总和
                    String price = parmarr[2];//商品价格
                    String id = parmarr[3];

                    if (Float.parseFloat(pointaccount) < Float.parseFloat(price)) {
                        ToastUtils.showShort("您的积分不足，不能兑换");
                    } else {
//                        ConfirmDialog.newInstance("提示", "确认使用" + price + "积分兑换？").setConfimLietener(new ConfirmDialog.ConfimLietener() {
//                            @Override
//                            public void onCancle() {
//                            }
//
//                            @Override
//                            public void onConfim() {
//                                HashMap<String, String> hashMap = new HashMap();
//                                UserInfoVo userInfoVo = CacheUtils.getUser();
//                                hashMap.put("memberid", userInfoVo.uid);
//                                hashMap.put("goods_id", id);
//                                hashMap.put("uuid", userInfoVo.uuid);
//                                mViewModel.pointPay(hashMap);
//                            }
//                        }).setMargin(50).show(getSupportFragmentManager());
                    }


//                    if (TextUtils.isEmpty(price) || TextUtils.isEmpty(pointaccount) || TextUtils.isEmpty(app_id) || TextUtils.isEmpty(id)) {
//                        return;
//                    }
//                    ARouter.getInstance().build(AppRouter.Pro_Country_select)
//                            .withString("price", price)
//                            .withString("pointaccount", pointaccount)
//                            .withString("app_id", app_id)
//                            .withString("id", id)
//                            .navigation((Activity) context, 105);
                }
            });
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK && requestCode == 105) {
            mBinding.pointStroeWebview.loadUrl(data.getStringExtra("url"));
        }
    }

    public boolean onKeyDown(int keyCode, KeyEvent keyEvent) {
        if (keyCode == keyEvent.KEYCODE_BACK) {//监听返回键，如果可以后退就后退
            if (mBinding.pointStroeWebview.canGoBack()) {
                mBinding.pointStroeWebview.goBack();
                return true;
            }
        }
        return super.onKeyDown(keyCode, keyEvent);

    }


    public void OnVmEnentListner(ViewEventResouce viewEventResouce) {
        super.OnVmEnentListner(viewEventResouce);
        switch (viewEventResouce.eventType) {
            case 430:
                if (viewEventResouce.data != null) {
                    LinkedTreeMap<String, String> map = (LinkedTreeMap<String, String>) viewEventResouce.data;
                    if (!TextUtils.isEmpty(map.get("url"))) {
                        mBinding.pointStroeWebview.loadUrl(map.get("url"));
//                        Intent intent = new Intent();
//                        intent.putExtra("url", map.get("url"));
//                        setResult(RESULT_OK, intent);

                    } else {
                        ToastUtils.showShort("兑换失败请重试");
                    }
                } else {
                    ToastUtils.showShort("兑换失败请重试");
                }
                break;
        }
    }


}
