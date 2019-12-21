package com.docker.cirlev2.ui.dynamicdetail;

import android.annotation.SuppressLint;
import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.widget.NestedScrollView;
import android.view.MotionEvent;
import android.view.View;
import android.webkit.JavascriptInterface;
import android.webkit.WebSettings;

import com.alibaba.android.arouter.launcher.ARouter;
import com.dcbfhd.utilcode.utils.FragmentUtils;
import com.docker.cirlev2.R;
import com.docker.cirlev2.databinding.Circlev2FragmentDetailH5V4Binding;
import com.docker.cirlev2.vm.CircleDynamicDetailViewModel;
import com.docker.cirlev2.vm.SampleListViewModel;
import com.docker.cirlev2.vo.entity.ServiceDataBean;
import com.docker.cirlev2.vo.param.StaCirParam;
import com.docker.common.common.config.CommonApiConfig;
import com.docker.common.common.router.AppRouter;
import com.docker.common.common.ui.base.NitCommonFragment;
import com.docker.common.common.utils.cache.CacheUtils;
import com.docker.common.common.utils.rxbus.RxBus;
import com.docker.common.common.utils.rxbus.RxEvent;
import com.docker.common.common.vo.UserInfoVo;
import com.docker.common.common.widget.empty.EmptyLayout;
import com.tencent.smtt.export.external.interfaces.SslError;
import com.tencent.smtt.export.external.interfaces.SslErrorHandler;

import io.reactivex.disposables.Disposable;

public class DynamicH5Fragment extends NitCommonFragment<CircleDynamicDetailViewModel, Circlev2FragmentDetailH5V4Binding> {

    public ServiceDataBean serviceDataBean;
    private com.tencent.smtt.sdk.WebView webView;

    public static DynamicH5Fragment getInstance(ServiceDataBean serviceDataBean) {
        DynamicH5Fragment dynamicH5Fragment = new DynamicH5Fragment();
        Bundle bundle = new Bundle();
        bundle.putSerializable("dataSource", serviceDataBean);
        dynamicH5Fragment.setArguments(bundle);
        return dynamicH5Fragment;
    }

    @Override
    protected int getLayoutId() {
        return R.layout.circlev2_fragment_detail_h5_v4;
    }

    @Override
    protected CircleDynamicDetailViewModel getViewModel() {
        return ViewModelProviders.of(this, factory).get(CircleDynamicDetailViewModel.class);
    }

    @Override
    protected void initView(View var1) {
        mViewModel.mAttenLv.observe(this, s -> {
        });
    }

    private Disposable disposable;

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        serviceDataBean = (ServiceDataBean) getArguments().getSerializable("dataSource");
        mBinding.get().setItem(serviceDataBean);
        FragmentUtils.add(getChildFragmentManager(), DynamicBotContentFragment.getInstance(serviceDataBean), R.id.frame_bot_content);
        mBinding.get().setViewmodel(mViewModel);
        disposable = RxBus.getDefault().toObservable(RxEvent.class).subscribe(rxEvent -> {
            if (rxEvent.getT().equals("comment")) {
                mBinding.get().netSpeed.postDelayed(() -> mBinding.get().netSpeed.fullScroll(NestedScrollView.FOCUS_DOWN), 500);
            }
        });
        initWebView();
    }

    @Override
    public void initImmersionBar() {

    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (disposable != null) {
            disposable.dispose();
        }
    }

    private void initWebView() {
        webView = mBinding.get().proWebview;
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
        webView.addJavascriptInterface(new MyInterface(), "android");

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
                mBinding.get().empty.showError();
                mBinding.get().empty.setOnretryListener(() -> {
                    mBinding.get().empty.showLoading();
                    webView.reload();
                });
            }
        });
        webView.setWebChromeClient(new com.tencent.smtt.sdk.WebChromeClient() {
            @Override
            public void onProgressChanged(com.tencent.smtt.sdk.WebView view, int newProgress) {
                if (newProgress > 75) {
                    if (mBinding.get() != null) {
                        mBinding.get().empty.hide();
                    }
                }
            }
        });
        processLoad();
//        if (serviceDataBean == null) {
//
//            webView.loadUrl("http://htj.wgc360.com/index.php?m=default.goods_info&memberid=null&uuid=03665e2b62e96f4247d86e50c4a5b8fe&dynamicid=1198");
//        } else {
//            UserInfoVo userInfoVo = CacheUtils.getUser();
//            String url = CommonApiConfig.apiBaseUrlHTJ + "index.php?m=default.goods_info" + "&memberid=" + userInfoVo.memberid + "&uuid=" + userInfoVo.uuid + "&dynamicid=" + serviceDataBean.getDynamicid();
//            webView.loadUrl(url);
//        }
    }


    private void processLoad() {
        if (serviceDataBean != null) {
            switch (serviceDataBean.getType()) {
                case "news":
                    mBinding.get().proWebview.loadDataWithBaseURL(null, getHtmlData(serviceDataBean.getExtData().getContent()), "text/html", "utf-8", null);
                    break;
                case "goods":
                    UserInfoVo userInfoVo = CacheUtils.getUser();
                    String url = CommonApiConfig.apiBaseUrlHTJ + "index.php?m=default.goods_info" + "&memberid=" + userInfoVo.memberid + "&uuid=" + userInfoVo.uuid + "&dynamicid=" + serviceDataBean.getDynamicid();
                    webView.loadUrl(url);
                    break;
            }
        }
    }

    public final String getHtmlData(String bodyHTML) {
        String bob = "<div style=\"font-size:16px;line-height:25px;\"><img style=\"width: 100%;\" class=\"num_img control_img\" data-name=\"img\" src=\"https://nit-bj.oss-cn-beijing.aliyuncs.com/upload/2019.06.2019061720034436102_219x390.png\" alt=\"\" id=\"\"></div>";
        String head = "<head> <meta name=\"viewport\" content=\"width=device-width, initial-scale=1.0, user-scalable=no\"> <style>img{max-width: 100%; width:100%; height:auto;}</style> </head>";
        return "<html>" + head + "<body>" + bodyHTML + "</body></html>";
    }

    public class MyInterface {

        public MyInterface() {
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
            if (serviceDataBean != null) {
                StaCirParam staCirParam = new StaCirParam(serviceDataBean.getCircleid(), serviceDataBean.getUtid(), null);
                ARouter.getInstance().build(AppRouter.CIRCLEHOME).withSerializable("mStartParam", staCirParam).navigation();
            }
        }
    }
}
