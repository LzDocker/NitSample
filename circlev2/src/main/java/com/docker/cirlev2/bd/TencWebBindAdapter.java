package com.docker.cirlev2.bd;

import android.annotation.SuppressLint;
import android.databinding.BindingAdapter;
import android.os.Build;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.webkit.JavascriptInterface;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.alibaba.android.arouter.launcher.ARouter;
import com.docker.cirlev2.vo.entity.ServiceDataBean;
import com.docker.cirlev2.vo.param.StaCirParam;
import com.docker.common.common.config.CommonApiConfig;
import com.docker.common.common.router.AppRouter;
import com.docker.common.common.utils.cache.CacheUtils;
import com.docker.common.common.vo.UserInfoVo;
import com.tencent.smtt.export.external.interfaces.SslError;
import com.tencent.smtt.export.external.interfaces.SslErrorHandler;

public class TencWebBindAdapter {


    @BindingAdapter(value = {"datasource"}, requireAll = false)
    public static void loadUrl(com.tencent.smtt.sdk.WebView webView, ServiceDataBean datasource) {
        init(webView, datasource);
    }

    @BindingAdapter(value = {"datasource"}, requireAll = false)
    public static void loadUrl(com.tencent.smtt.sdk.WebView webView, String datasource) {
        init(webView, null);
    }

    public static void init(com.tencent.smtt.sdk.WebView webView, ServiceDataBean datasource) {
        webView.setOnTouchListener(new View.OnTouchListener() { // 横向滑动事件
            private float startx;
            private float starty;
            private float offsetx;
            private float offsety;

            @Override
            public boolean onTouch(View v, MotionEvent event) {
                switch (event.getAction()) {
                    case MotionEvent.ACTION_DOWN:
                        v.getParent().requestDisallowInterceptTouchEvent(true);
                        startx = event.getX();
                        starty = event.getY();
                        break;
                    case MotionEvent.ACTION_MOVE:
                        offsetx = Math.abs(event.getX() - startx);
                        offsety = Math.abs(event.getY() - starty);
                        if (offsetx > offsety) {
                            v.getParent().requestDisallowInterceptTouchEvent(true);
                        } else {
                            v.getParent().requestDisallowInterceptTouchEvent(false);
                        }
                        break;
                    default:
                        break;
                }
                return false;
            }
        });

        com.tencent.smtt.sdk.WebSettings webSettings = webView.getSettings();
        // 特别注意：5.1以上默认禁止了https和http混用。下面代码是开启
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {// 21

        }
        webSettings.setCacheMode(WebSettings.LOAD_NO_CACHE);// 不使用缓存，直接用网络加载
        webSettings.setJavaScriptEnabled(true);// webView支持javascript
        webSettings.setJavaScriptCanOpenWindowsAutomatically(true);// 告诉js可以自动打开window

        // 两者一起使用，可以让html页面加载显示适应手机的屏幕大小
        webSettings.setUseWideViewPort(true);
        webSettings.setLoadWithOverviewMode(true);
        webSettings.setBuiltInZoomControls(true); //设置内置的缩放控件。若为false，则该WebView不可缩放
        webSettings.setAllowFileAccess(true); //设置可以访问文件
        webSettings.setLoadsImagesAutomatically(true); //支持自动加载图片
        webSettings.setDefaultTextEncodingName("utf-8");//设置编码格式
        webSettings.setSavePassword(false);// 关闭密码保存提醒；该方法在以后的版本中该方法将不被支持
        webSettings.setDomStorageEnabled(true);// 设置支持DOM storage API

        webView.addJavascriptInterface(new MyInterface(datasource), "android");

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
                super.onReceivedSslError(webView, sslErrorHandler, sslError);
            }
        });
        webView.setWebChromeClient(new com.tencent.smtt.sdk.WebChromeClient() {
            @Override
            public void onProgressChanged(com.tencent.smtt.sdk.WebView view, int newProgress) {
                if (newProgress > 75) {
                    // todo
                }
            }
        });

        if (datasource == null) {

            webView.loadUrl("http://htj.wgc360.com/index.php?m=default.goods_info&memberid=null&uuid=03665e2b62e96f4247d86e50c4a5b8fe&dynamicid=1198");
        } else {

            UserInfoVo userInfoVo = CacheUtils.getUser();
            String url = CommonApiConfig.apiBaseUrlHTJ + "index.php?m=default.goods_info" + "&memberid=" + userInfoVo.memberid + "&uuid=" + userInfoVo.uuid + "&dynamicid=" + datasource.getDynamicid();
            webView.loadUrl(url);
        }

        Log.d("sss", "init: ==============================");
    }

    public static class MyInterface {
        public ServiceDataBean datasource;

        public MyInterface() {
        }

        public MyInterface(ServiceDataBean datasource) {
            if (datasource == null) {
            } else {
                this.datasource = datasource;
            }
        }

        @SuppressLint("JavascriptInterface")
        @JavascriptInterface
        public void processHourseInfo(final String title, String weburl) {
            ARouter.getInstance().build(AppRouter.COMMONH5)
                    .withString("weburl", weburl)
                    .withString("title", title)
                    .navigation();
        }

        @SuppressLint("JavascriptInterface")
        @JavascriptInterface
        public void processCircleDetail() {
            if (datasource != null) {
                StaCirParam staCirParam = new StaCirParam(datasource.getCircleid(), datasource.getUtid(), null);
                ARouter.getInstance().build(AppRouter.CIRCLEHOME).withSerializable("mStartParam", staCirParam).navigation();
            }
        }
    }
}
