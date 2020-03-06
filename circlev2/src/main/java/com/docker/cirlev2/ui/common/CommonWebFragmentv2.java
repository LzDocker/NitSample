package com.docker.cirlev2.ui.common;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.arch.lifecycle.ViewModelProvider;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.JavascriptInterface;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.FrameLayout;
import android.widget.LinearLayout;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.android.arouter.launcher.ARouter;
import com.baidu.mapapi.search.geocode.GeoCoder;
import com.dcbfhd.utilcode.utils.ConvertUtils;
import com.dcbfhd.utilcode.utils.ToastUtils;
import com.docker.cirlev2.R;
import com.docker.cirlev2.databinding.Circlev2FragmentCommonWebBinding;
import com.docker.cirlev2.vo.param.SourceUpParam;
import com.docker.common.common.router.AppRouter;
import com.docker.common.common.ui.base.NitCommonFragment;
import com.docker.common.common.utils.rxbus.RxBus;
import com.docker.common.common.utils.rxbus.RxEvent;
import com.docker.common.common.vm.NitEmptyViewModel;
import com.docker.common.common.vo.WorldNumList;
import com.docker.common.common.widget.refresh.SmartRefreshLayout;
import com.docker.core.util.AppExecutors;
import com.tencent.smtt.export.external.interfaces.SslError;
import com.tencent.smtt.export.external.interfaces.SslErrorHandler;
import com.tencent.smtt.sdk.ValueCallback;

import java.util.Map;

import javax.inject.Inject;

import io.reactivex.disposables.Disposable;

import static android.app.Activity.RESULT_OK;
import static com.dcbfhd.utilcode.utils.ScreenUtils.getScreenHeight;
import static com.dcbfhd.utilcode.utils.ScreenUtils.getScreenWidth;

/*
 * */
@Route(path = AppRouter.CIRCLEV2_COMMONH5)
public class CommonWebFragmentv2 extends NitCommonFragment<NitEmptyViewModel, Circlev2FragmentCommonWebBinding> {

    @Inject
    ViewModelProvider.Factory factory;
    private String weburl;
    private com.tencent.smtt.sdk.WebView webView;
    private SourceUpParam mSourceUpParam;

    @Inject
    AppExecutors appExecutors;

    public final static int FILECHOOSER_RESULTCODE = 1;
    public final static int FILECHOOSER_RESULTCODE_FOR_ANDROID_5 = 2;
    private Map<String, String> map;
    private Disposable disposable;
    private GeoCoder geocode;
    private SmartRefreshLayout smartRefreshLayout;


    //    private WebView webView;

    @Override
    protected int getLayoutId() {
        return R.layout.circlev2_fragment_common_web;
    }

    public static CommonWebFragmentv2 newInstance(String weburl) {
        CommonWebFragmentv2 dynamicFragment = new CommonWebFragmentv2();
        Bundle bundle = new Bundle();
        bundle.putSerializable("weburl", weburl);
        dynamicFragment.setArguments(bundle);
        return dynamicFragment;
    }

    public static CommonWebFragmentv2 newInstance(String type, String weburl) {
        CommonWebFragmentv2 dynamicFragment = new CommonWebFragmentv2();
        Bundle bundle = new Bundle();
        bundle.putSerializable("weburl", weburl);
        bundle.putSerializable("type", type);
        dynamicFragment.setArguments(bundle);
        return dynamicFragment;
    }


    @Override
    public void onReFresh(SmartRefreshLayout refreshLayout) {
        super.onReFresh(refreshLayout);
        smartRefreshLayout = refreshLayout;
        if (mBinding.get().proWebview != null && !TextUtils.isEmpty(weburl)) {
            mBinding.get().proWebview.reload();
        } else {
            refreshLayout.finishRefresh();
        }
    }

    @Override
    protected NitEmptyViewModel getViewModel() {
        return ViewModelProviders.of(this, factory).get(NitEmptyViewModel.class);
    }

    @Override
    protected void initView(View var1) {
        webView = mBinding.get().proWebview;
        initWebview();
    }

    private void initWebview() {
//        IX5WebViewExtension ix5WebViewExtension = mBinding.proWebview.getX5WebViewExtension();
//        ix5WebViewExtension.setScrollBarFadingEnabled(false);
        com.tencent.smtt.sdk.WebSettings webSettings = webView.getSettings();
//      WebSettings webSettings = webView.getSettings();
        // 特别注意：5.1以上默认禁止了https和http混用。下面代码是开启
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {// 21
            webView.getSettings().setMixedContentMode(WebSettings.MIXED_CONTENT_ALWAYS_ALLOW);
        }
//        webSettings.setCacheMode(WebSettings.LOAD_NO_CACHE);// 不使用缓存，直接用网络加载
        webSettings.setCacheMode(WebSettings.LOAD_CACHE_ELSE_NETWORK);// 不使用缓存，直接用网络加载
        webSettings.setJavaScriptEnabled(true);// webView支持javascript
        webSettings.setJavaScriptCanOpenWindowsAutomatically(true);// 告诉js可以自动打开window
        webSettings.setUseWideViewPort(true);
        webSettings.setLoadWithOverviewMode(true);
        webSettings.setBuiltInZoomControls(true); //设置内置的缩放控件。若为false，则该WebView不可缩放
        webSettings.setAllowFileAccess(true); //设置可以访问文件
        webSettings.setLoadsImagesAutomatically(true); //支持自动加载图片
        webSettings.setDefaultTextEncodingName("utf-8");//设置编码格式
        webSettings.setSavePassword(false);// 关闭密码保存提醒；该方法在以后的版本中该方法将不被支持
        webSettings.setDomStorageEnabled(true);// 设置支持DOM storage API
        webView.addJavascriptInterface(new MyInterface(CommonWebFragmentv2.this.getHoldingActivity()), "android");
        webView.setWebViewClient(new com.tencent.smtt.sdk.WebViewClient() {
            // 设置不用系统浏览器打开,直接显示在当前 webview
            @Override
            public boolean shouldOverrideUrlLoading(com.tencent.smtt.sdk.WebView view, String url) {
                // 如果不是http或者https开头的url，那么使用手机自带的浏览器打开
                if (!url.startsWith("http://") && !url.startsWith("https://")) {
                    try {
                        return true;
                    } catch (Exception e) {
                        e.printStackTrace();
                        return true;
                    }
                }
                view.loadUrl(url);
                return true;
            }

            @Override
            public void onReceivedSslError(com.tencent.smtt.sdk.WebView webView, SslErrorHandler sslErrorHandler, SslError sslError) {
                sslErrorHandler.proceed();  // 接受所有网站的证书
//                super.onReceivedSslError(webView, sslErrorHandler, sslError);
            }
            
        });
        webView.setWebChromeClient(new com.tencent.smtt.sdk.WebChromeClient() {
            @Override
            public void onProgressChanged(com.tencent.smtt.sdk.WebView view, int newProgress) {
                if (newProgress > 70) {
                    if (mBinding != null && mBinding.get() != null && mBinding.get().empty != null) {
                        mBinding.get().empty.hide();
                    }
                    if (smartRefreshLayout != null) {
                        smartRefreshLayout.finishRefresh();
                    }
                }
            }

            public void openFileChooser(ValueCallback<Uri> valueCallback, String s, String s1) {
                openFileChooserImpl(valueCallback);
            }

            public void openFileChooser(ValueCallback<Uri> uploadMsg) {
                openFileChooserImpl(uploadMsg);
            }

            public void openFileChooser(ValueCallback<Uri> uploadMsg, String acceptType) {
                openFileChooserImpl(uploadMsg);
            }

            public boolean onShowFileChooser(WebView webView, ValueCallback<Uri[]> filePathCallback, FileChooserParams fileChooserParams) {
                onenFileChooseImpleForAndroid(filePathCallback);
                return true;
            }
        });
    }

    public ValueCallback<Uri> mUploadMessage;

    private void openFileChooserImpl(ValueCallback<Uri> uploadMsg) {
        mUploadMessage = uploadMsg;
        Intent i = new Intent(Intent.ACTION_GET_CONTENT);
        i.addCategory(Intent.CATEGORY_OPENABLE);
        i.setType("image/*");
        startActivityForResult(Intent.createChooser(i, "File Chooser"), FILECHOOSER_RESULTCODE);
    }

    public ValueCallback<Uri[]> mUploadMessageForAndroid5;

    private void onenFileChooseImpleForAndroid(ValueCallback<Uri[]> filePathCallback) {
        mUploadMessageForAndroid5 = filePathCallback;
        Intent contentSelectionIntent = new Intent(Intent.ACTION_GET_CONTENT);
        contentSelectionIntent.addCategory(Intent.CATEGORY_OPENABLE);
        contentSelectionIntent.setType("image/*");
        Intent chooserIntent = new Intent(Intent.ACTION_CHOOSER);
        chooserIntent.putExtra(Intent.EXTRA_INTENT, contentSelectionIntent);
        chooserIntent.putExtra(Intent.EXTRA_TITLE, "Image Chooser");
        startActivityForResult(chooserIntent, FILECHOOSER_RESULTCODE_FOR_ANDROID_5);
    }

    public class MyInterface {

        private Context context;

        public MyInterface(Context context) {
            this.context = context;

        }

        @SuppressLint("JavascriptInterface")
        @JavascriptInterface
        public void processHourseInfo(final String title, String weburl) {
            ((Activity) context).runOnUiThread(() -> {
                ARouter.getInstance().build(AppRouter.COMMONH5)
                        .withString("weburl", weburl)
                        .withString("title", title)
                        .navigation((Activity) context);
            });
        }

        // 支付
        @SuppressLint("JavascriptInterface")
        @JavascriptInterface
        public void processPoint(final String[] parmarr) {
            ((Activity) context).runOnUiThread(() -> {
                String app_id = parmarr[0];
                String pointaccount = parmarr[1];
                String price = parmarr[2];
                String id = parmarr[3];
                if (TextUtils.isEmpty(price) || TextUtils.isEmpty(pointaccount) || TextUtils.isEmpty(app_id) || TextUtils.isEmpty(id)) {
                    return;
                }
//                ARouter.getInstance().build(AppRouter.Pro_Country_select)
//                        .withString("price", price)
//                        .withString("pointaccount", pointaccount)
//                        .withString("app_id", app_id)
//                        .withString("id", id)
//                        .navigation((Activity) context, 105);
            });
        }


        // 选择图片
        @SuppressLint("JavascriptInterface")
        @JavascriptInterface
        public void selectMedia() {
            ((Activity) context).runOnUiThread(() -> {
                mSourceUpParam = new SourceUpParam();

            });
        }

//        // 联系客服小蜜
//        @SuppressLint("JavascriptInterface")
//        @JavascriptInterface
//        public void contactPhone(String phone) {
//            ((Activity) context).runOnUiThread(() -> {
//                CommonDialog.newInstance().setLayoutId(R.layout.open_dialog_confirm).setConvertListener((ViewConvertListener) (holder, dialog) -> {
//                    TextView title = holder.getView(R.id.title);
//                    TextView message = holder.getView(R.id.message);
//                    TextView cancel = holder.getView(R.id.cancel);
//                    TextView confirm = holder.getView(R.id.confirm);
//                    title.setTextSize(16);
//                    title.setText(phone);
//                    message.setText("在线专职客服为您解疑答惑");
//                    cancel.setText("取消");
//                    confirm.setText("呼叫");
//                    cancel.setTextColor(Color.parseColor("#E60012"));
//                    confirm.setTextColor(Color.parseColor("#E60012"));
//                    cancel.setOnClickListener(v1 -> {
//                        dialog.dismiss();
//                    });
//
//                    confirm.setOnClickListener(v1 -> {
//                        //跳转到拨号界面，同时传递电话号码
//                        Intent dialIntent = new Intent(Intent.ACTION_DIAL, Uri.parse("tel:" + phone));
//                        startActivity(dialIntent);
//                        dialog.dismiss();
//                    });
//                }).setMargin(40).show(getFragmentManager());
//            });
//        }

        // 发布成功
        @SuppressLint("JavascriptInterface")
        @JavascriptInterface
        public void publishSuccess() {
            ((Activity) context).runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    ToastUtils.showShort("发布成功！");
                    CommonWebFragmentv2.this.getHoldingActivity().finish();
                    RxBus.getDefault().post(new RxEvent<>("dynamic_refresh", ""));
                }
            });
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent intent) {
        super.onActivityResult(requestCode, resultCode, intent);
        /// 选择文件-----
        if (requestCode == FILECHOOSER_RESULTCODE) {
            if (null == mUploadMessage)
                return;
            Uri result = intent == null || resultCode != RESULT_OK ? null : intent.getData();
            mUploadMessage.onReceiveValue(result);
            mUploadMessage = null;
        } else if (requestCode == FILECHOOSER_RESULTCODE_FOR_ANDROID_5) {
            if (null == mUploadMessageForAndroid5)
                return;
            Uri result = (intent == null || resultCode != RESULT_OK) ? null : intent.getData();
            if (result != null) {
                mUploadMessageForAndroid5.onReceiveValue(new Uri[]{result});
            } else {
                mUploadMessageForAndroid5.onReceiveValue(new Uri[]{});
            }
            mUploadMessageForAndroid5 = null;
        }
        if (resultCode == RESULT_OK) {
            if (requestCode == 1012) {
                WorldNumList.WorldEnty worldEnty = (WorldNumList.WorldEnty) intent.getSerializableExtra("datasource");
                final int version = Build.VERSION.SDK_INT;
                if (version < 18) {
                    webView.loadUrl("javascript:getAreaByApp('" + worldEnty.id + "','" + worldEnty.name + "','" + worldEnty.cityCode + "')");
                } else {
                    webView.evaluateJavascript("javascript:getAreaByApp('" + worldEnty.id + "','" + worldEnty.name + "','" + worldEnty.cityCode + "')", new ValueCallback<String>() {
                        @Override
                        public void onReceiveValue(String value) {

                        }
                    });
                }
            }
        }
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        weburl = (String) getArguments().getSerializable("weburl");
        if (!TextUtils.isEmpty(weburl)) {
            webView.loadUrl(weburl);
            Log.d("sss", "onActivityCreated: ==================" + weburl);
        }
    }

    @Override
    public void onVisible() {
        super.onVisible();

    }


    @Override
    public void initImmersionBar() {
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
    }

    @Override
    public void onDestroy() {
        clearWebview();
        if (geocode != null) {
            // 释放地理编码检索实例
            geocode.destroy();
        }
        super.onDestroy();
    }

    private void clearWebview() {
        if (webView != null) {
            webView.loadDataWithBaseURL(null, "", "text/html", "utf-8", null);
            webView.clearHistory();
            webView.clearCache(true);// 清除缓存
            ((ViewGroup) webView.getParent()).removeView(webView);
            webView.destroy();
            //webView = null;
        }
    }


    @Override
    public void onDetach() {
        super.onDetach();

    }


}

