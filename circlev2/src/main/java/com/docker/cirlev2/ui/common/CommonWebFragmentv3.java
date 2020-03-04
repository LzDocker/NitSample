package com.docker.cirlev2.ui.common;

import android.arch.lifecycle.ViewModelProviders;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.docker.cirlev2.R;
import com.docker.cirlev2.databinding.Circlev2FragmentCommonWebBinding;
import com.docker.cirlev2.databinding.Circlev2FragmentCommonWebV3Binding;
import com.docker.common.common.router.AppRouter;
import com.docker.common.common.ui.base.NitCommonFragment;
import com.docker.common.common.vm.NitEmptyViewModel;
import com.docker.common.common.widget.refresh.SmartRefreshLayout;
import com.tencent.smtt.export.external.interfaces.SslError;
import com.tencent.smtt.export.external.interfaces.SslErrorHandler;
import com.tencent.smtt.sdk.ValueCallback;

/*
 * */
@Route(path = AppRouter.CIRCLEV3_COMMONH5)
public class CommonWebFragmentv3 extends NitCommonFragment<NitEmptyViewModel, Circlev2FragmentCommonWebV3Binding> {


    private String weburl;
    private WebView webView;
    private SmartRefreshLayout smartRefreshLayout;


    //    private WebView webView;

    @Override
    protected int getLayoutId() {
        return R.layout.circlev2_fragment_common_web_v3;
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

    }

    private void initWebview() {
//        IX5WebViewExtension ix5WebViewExtension = mBinding.proWebview.getX5WebViewExtension();
//        ix5WebViewExtension.setScrollBarFadingEnabled(false);
       WebSettings webSettings = webView.getSettings();
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
        webView.setWebViewClient(new WebViewClient() {
            // 设置不用系统浏览器打开,直接显示在当前 webview
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
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

//            @Override
//            public void onReceivedSslError(WebView webView, SslErrorHandler sslErrorHandler, SslError sslError) {
//                sslErrorHandler.proceed();  // 接受所有网站的证书
////                super.onReceivedSslError(webView, sslErrorHandler, sslError);
//            }
        });
        webView.setWebChromeClient(new WebChromeClient() {
            @Override
            public void onProgressChanged(WebView view, int newProgress) {
                if (newProgress > 70) {
                    if (mBinding != null && mBinding.get() != null && mBinding.get().empty != null) {
                        mBinding.get().empty.hide();
                    }
                    if (smartRefreshLayout != null) {
                        smartRefreshLayout.finishRefresh();
                    }
                }
            }

        });
    }


    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        webView = mBinding.get().proWebview;
        initWebview();
        weburl = (String) getArguments().getSerializable("weburl");
        if (!TextUtils.isEmpty(weburl)) {
            webView.loadUrl(weburl);
        } else {
            webView.loadDataWithBaseURL(null, getHtmlData((String) getArguments().getSerializable("webcontent")), "text/html", "utf-8", null);
        }
    }

    public final String getHtmlData(String bodyHTML) {
        String bob = "<div style=\"font-size:16px;line-height:25px;\"><img style=\"width: 100%;\" class=\"num_img control_img\" data-name=\"img\" src=\"https://nit-bj.oss-cn-beijing.aliyuncs.com/upload/2019.06.2019061720034436102_219x390.png\" alt=\"\" id=\"\"></div>";
        String head = "<head> <meta name=\"viewport\" content=\"width=device-width, initial-scale=1.0, user-scalable=no\"> <style>img{max-width: 100%; width:100%; height:auto;}</style> </head>";
        return "<html>" + head + "<body>" + bodyHTML + "</body></html>";
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

