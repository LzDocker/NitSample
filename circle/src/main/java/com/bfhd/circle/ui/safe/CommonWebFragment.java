package com.bfhd.circle.ui.safe;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.arch.lifecycle.ViewModelProvider;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.media.MediaMetadataRetriever;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.JavascriptInterface;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.TextView;

import com.alibaba.android.arouter.launcher.ARouter;
import com.baidu.location.BDAbstractLocationListener;
import com.baidu.location.BDLocation;
import com.baidu.location.LocationClient;
import com.baidu.location.LocationClientOption;
import com.baidu.mapapi.model.LatLng;
import com.baidu.mapapi.search.core.SearchResult;
import com.baidu.mapapi.search.geocode.GeoCodeResult;
import com.baidu.mapapi.search.geocode.GeoCoder;
import com.baidu.mapapi.search.geocode.OnGetGeoCoderResultListener;
import com.baidu.mapapi.search.geocode.ReverseGeoCodeOption;
import com.baidu.mapapi.search.geocode.ReverseGeoCodeResult;
import com.bfhd.circle.R;
import com.bfhd.circle.base.CommonFragment;
import com.bfhd.circle.base.Constant;
import com.bfhd.circle.base.ViewEventResouce;
import com.bfhd.circle.databinding.CircleFragmentCommonWebBinding;
import com.bfhd.circle.utils.mFileUtils;
import com.bfhd.circle.vm.CircleTypeViewModel;
import com.bfhd.circle.vo.ServiceDataBean;
import com.bfhd.circle.vo.bean.GoodsTypeListParam;
import com.bfhd.circle.vo.bean.ReleaseDyamicBean;
import com.bfhd.circle.vo.bean.SourceUpParam;
import com.dcbfhd.utilcode.utils.FileUtils;
import com.dcbfhd.utilcode.utils.GsonUtils;
import com.dcbfhd.utilcode.utils.LogUtils;
import com.dcbfhd.utilcode.utils.NetworkUtils;
import com.dcbfhd.utilcode.utils.ToastUtils;
import com.docker.common.common.router.AppRouter;
import com.docker.common.common.utils.cache.CacheUtils;
import com.docker.common.common.utils.oss.MyOSSUtils;
import com.docker.common.common.utils.rxbus.RxBus;
import com.docker.common.common.utils.rxbus.RxEvent;
import com.docker.common.common.vo.UserInfoVo;
import com.docker.common.common.vo.WorldNumList;
import com.docker.core.util.AppExecutors;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import com.luck.picture.lib.PictureSelector;
import com.luck.picture.lib.config.PictureConfig;
import com.luck.picture.lib.config.PictureMimeType;
import com.luck.picture.lib.entity.LocalMedia;
import com.luck.picture.lib.permissions.RxPermissions;
import com.tencent.smtt.export.external.interfaces.SslError;
import com.tencent.smtt.export.external.interfaces.SslErrorHandler;
import com.tencent.smtt.sdk.ValueCallback;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;

import io.reactivex.disposables.Disposable;

import static android.app.Activity.RESULT_OK;

/*
 * */
public class CommonWebFragment extends CommonFragment<CircleTypeViewModel, CircleFragmentCommonWebBinding> {

    @Inject
    ViewModelProvider.Factory factory;
    private String weburl;
    private com.tencent.smtt.sdk.WebView webView;
    private GoodsTypeListParam goodsTypeListParam;
    private SourceUpParam mSourceUpParam;

    @Inject
    AppExecutors appExecutors;

    public final static int FILECHOOSER_RESULTCODE = 1;
    public final static int FILECHOOSER_RESULTCODE_FOR_ANDROID_5 = 2;
    private Map<String, String> map;
    private Disposable disposable;
    private GeoCoder geocode;


    //    private WebView webView;

    @Override
    protected int getLayoutId() {
        return R.layout.circle_fragment_common_web;
    }

    public static CommonWebFragment newInstance(String weburl) {
        CommonWebFragment dynamicFragment = new CommonWebFragment();
        Bundle bundle = new Bundle();
        bundle.putSerializable("weburl", weburl);
        dynamicFragment.setArguments(bundle);
        return dynamicFragment;
    }

    public static CommonWebFragment newInstance(String type, String weburl) {
        CommonWebFragment dynamicFragment = new CommonWebFragment();
        Bundle bundle = new Bundle();
        bundle.putSerializable("weburl", weburl);
        bundle.putSerializable("type", type);
        dynamicFragment.setArguments(bundle);
        return dynamicFragment;
    }

    @Override
    protected CircleTypeViewModel getViewModel() {
        return ViewModelProviders.of(this, factory).get(CircleTypeViewModel.class);
    }

    @Override
    protected void initView(View var1) {
        webView = mBinding.get().proWebview;
        initWebview();
    }

    protected void initCityCode() {
        //新建编码查询对象
        geocode = GeoCoder.newInstance();
        OnGetGeoCoderResultListener listener = new OnGetGeoCoderResultListener() {
            /**
             * 反地理编码查询结果回调函数
             * @param result  反地理编码查询结果
             */
            @Override
            public void onGetReverseGeoCodeResult(ReverseGeoCodeResult result) {
                if (result == null || result.error != SearchResult.ERRORNO.NO_ERROR) {
                    cityCode = "";
                }
                if (result != null && result.error == SearchResult.ERRORNO.NO_ERROR) {
                    //得到位置
                    cityCode = result.getAddressDetail().countryCode + "";
                }
                UserInfoVo userInfoVo = CacheUtils.getUser();
                myWeburl = Constant.BaseServeTest + "index.php?m=publish.push_dynamic" +
                        "&t=" + type + "&memberid=" + userInfoVo.uid + "&uuid=" + userInfoVo.uuid + "" +
                        "&lat=" + lat + "&lng=" + lng + "&area1=" + province + "&area2=" + city + "&area3=" + district + "&cityCode=" + cityCode;
                Log.i("gjw", "myWeburl: " + myWeburl);
                webView.loadUrl(myWeburl);
            }

            /**
             * 地理编码查询结果回调函数
             * @param result  地理编码查询结果
             */
            @Override
            public void onGetGeoCodeResult(GeoCodeResult result) {
                Log.i("gjw", "myWeburl: ");
            }
        };
        //设置查询结果监听者
        geocode.setOnGetGeoCodeResultListener(listener);
        //新建查询对象要查询的条件
        LatLng latLng = new LatLng(Float.parseFloat(lat), Float.parseFloat(lng));
        ReverseGeoCodeOption options = new ReverseGeoCodeOption().location(latLng);
        // 发起反地理编码请求
        geocode.reverseGeoCode(options);
    }

    @Override
    public void OnVmEnentListner(ViewEventResouce viewEventResouce) {
        super.OnVmEnentListner(viewEventResouce);
        switch (viewEventResouce.eventType) {
            case 105:
                break;
        }
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
        webView.addJavascriptInterface(new MyInterface(CommonWebFragment.this.getHoldingActivity()), "android");
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

        // 选择地址
        @SuppressLint("JavascriptInterface")
        @JavascriptInterface
        public void selectAddress() {
            ((Activity) context).runOnUiThread(() -> ARouter.getInstance().build(AppRouter.ACCOUNT_COUNTRY).navigation(CommonWebFragment.this.getHoldingActivity(), 1012));
        }

        // 选择分类
        @SuppressLint("JavascriptInterface")
        @JavascriptInterface
        public void selectType() {
            ((Activity) context).runOnUiThread(() -> {
                goodsTypeListParam = new GoodsTypeListParam(null, null);
//                CircleGoodsTypeListActivity.startMe(CommonWebFragment.this.getHoldingActivity(), goodsTypeListParam, "speical", 1011);
            });
        }// 选择分类

        @SuppressLint("JavascriptInterface")
        @JavascriptInterface
        public void previewResource(String resource, String position) {
            ((Activity) context).runOnUiThread(() -> {
                List<ServiceDataBean.ResourceBean> resourceBeans = getResultList(resource, ServiceDataBean.ResourceBean.class);
                if (resourceBeans != null && !TextUtils.isEmpty(position) && resourceBeans.size() >= Integer.parseInt(position)) {
                    if (resourceBeans.get(Integer.parseInt(position)).getT() == 2) {
                        String videoUrl = Constant.getCompleteImageUrl(resourceBeans.get(Integer.parseInt(position)).getUrl());
                        String thumbUrl = Constant.getCompleteImageUrl(resourceBeans.get(Integer.parseInt(position)).getImg());
                        ARouter.getInstance().build(AppRouter.Video_Player).withString("videoUrl", videoUrl).withString("thumbUrl", thumbUrl).navigation();
                    } else {
                        List<LocalMedia> localMediaList = new ArrayList<>();
                        for (int i = 0; i < resourceBeans.size(); i++) {
                            LocalMedia localMedia = new LocalMedia();
                            localMedia.setPictureType("1");
                            if (!TextUtils.isEmpty(resourceBeans.get(i).getImg())) {
                                localMedia.setPath(Constant.getCompleteImageUrl(resourceBeans.get(i).getImg()));
                            } else {
                                localMedia.setPath(Constant.getCompleteImageUrl(resourceBeans.get(i).getUrl()));
                            }
                            localMediaList.add(localMedia);
                        }
                        PictureSelector
                                .create(CommonWebFragment.this)
                                .themeStyle(R.style.picture_default_style)
                                .openExternalPreview(Integer.parseInt(position), localMediaList);
                    }
                }
            });
        }

        // 选择图片
        @SuppressLint("JavascriptInterface")
        @JavascriptInterface
        public void selectMedia() {
            ((Activity) context).runOnUiThread(() -> {
                mSourceUpParam = new SourceUpParam();
                enterToSelect();
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
                    CommonWebFragment.this.getHoldingActivity().finish();
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
            if (requestCode == 1011) {
                goodsTypeListParam = (GoodsTypeListParam) intent.getSerializableExtra("mStaparam");
                final int version = Build.VERSION.SDK_INT;
                String parentid = goodsTypeListParam.parent.linkageid;
                String chilied = "-1";
                String name = goodsTypeListParam.parent.name;
                if (goodsTypeListParam.children != null) {
                    chilied = goodsTypeListParam.children.linkageid;
                    name = name + goodsTypeListParam.children.name;
                }
                if (version < 18) {
                    webView.loadUrl("javascript:getTypeByApp('" + parentid + "','" + chilied + "','" + name + "')");
                } else {
                    webView.evaluateJavascript("javascript:getTypeByApp('" + parentid + "','" + chilied + "','" + name + "')", new ValueCallback<String>() {
                        @Override
                        public void onReceiveValue(String value) {

                        }
                    });
                }
            }

        }
        if (requestCode == 10099) {
            processSelectMedia(intent);
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
        } else {
            type = (String) getArguments().getSerializable("type");
            processLocation();
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


    private int locationCount = 0;
    private String mCountryStr;
    private LocationClient mLocationClient;
    private String lat;
    private String lng;
    private String type;
    private String province;
    private String city;
    private String district;
    private String cityCode = "";
    private String codeIso = "";
    private String isCHN;
    private String lat_lng = "";
    private String myWeburl;

    private void processLocation() {
        locationCount = 0;
        if (mLocationClient == null) {
            mLocationClient = new LocationClient(CommonWebFragment.this.getHoldingActivity());
        }
        RxPermissions rxPermissions = new RxPermissions(CommonWebFragment.this.getHoldingActivity());
        rxPermissions.requestEach(Manifest.permission.READ_EXTERNAL_STORAGE,
                Manifest.permission.ACCESS_NETWORK_STATE,
                Manifest.permission.WRITE_EXTERNAL_STORAGE,
                Manifest.permission.READ_EXTERNAL_STORAGE,
                Manifest.permission.ACCESS_WIFI_STATE,
                Manifest.permission.WRITE_SETTINGS,
                Manifest.permission.ACCESS_COARSE_LOCATION,
                Manifest.permission.ACCESS_FINE_LOCATION,
                Manifest.permission.READ_PHONE_STATE)
                .subscribe(permission -> {
                    if (permission.name.equals(Manifest.permission.ACCESS_FINE_LOCATION)) {
                        if (permission.granted) {
                            // 用户已经同意该权限
                            LocationClientOption option = new LocationClientOption();
                            option.setIsNeedAddress(true);
                            option.setOpenGps(true); // 打开gps
                            option.setCoorType("bd09ll"); // 设置坐标类型
                            option.setScanSpan(1000);
                            mLocationClient.setLocOption(option);
                            mLocationClient.registerLocationListener(new BDAbstractLocationListener() {
                                @Override
                                public void onReceiveLocation(BDLocation location) {
                                    if (location.getAddress() != null) {
                                        province = location.getAddress().province;
                                        city = location.getAddress().city;
                                        district = location.getAddress().district;
                                        isCHN = location.getCountry();
                                        mCountryStr = location.getCity();
                                        cityCode = location.getCityCode();
                                        lat = String.valueOf(location.getLatitude());
                                        lng = String.valueOf(location.getLongitude());
                                        lat_lng = lat + "," + lng;
                                        mLocationClient.stop();
                                        processPuhlish();
                                    } else {
                                        locationCount++;
                                        if (locationCount > 3) {
                                            mLocationClient.stop();
                                            processPuhlish();
                                        }
                                    }
                                }
                            });
                            mLocationClient.start();
                        } else {
                            ToastUtils.showShort("权限被拒绝，请在设置里面开启相应权限，若无相应权限会影响使用");
                            processPuhlish();
                        }
                    }
                });
    }

    private void processPuhlish() {
        UserInfoVo userInfoVo = CacheUtils.getUser();
        myWeburl = Constant.BaseServeTest + "index.php?m=publish.push_dynamic" +
                "&t=" + type + "&memberid=" + userInfoVo.uid + "&uuid=" + userInfoVo.uuid + "" +
                "&lat=" + lat + "&lng=" + lng + "&area1=" + province + "&area2=" + city + "&area3=" + district + "&cityCode=";
        if (TextUtils.isEmpty(lat) || TextUtils.isEmpty(lat)) {
            cityCode = "";
            webView.loadUrl(myWeburl + cityCode);
        } else {
            if (TextUtils.isEmpty(cityCode)) {
                initCityCode();
            } else {
                webView.loadUrl(myWeburl + cityCode);
            }
        }

        Log.i("gjw", "processPuhlish: " + myWeburl + cityCode);

    }


    @Override
    public void onDetach() {
        super.onDetach();
        if (mLocationClient != null) {
            mLocationClient.stop();
            mLocationClient = null;
        }
    }

    public void enterToSelect() {
        PictureSelector.create(CommonWebFragment.this.getHoldingActivity())
                .openGallery(PictureMimeType.ofAll())//全部.PictureMimeType.ofAll()、图片.ofImage()、视频.ofVideo()、音频.ofAudio()
                .theme(R.style.picture_default_style)//主题样式(不设置为默认样式) 也可参考demo values/styles下 例如：R.style.picture.white.style
                .maxSelectNum(9)// 最大图片选择数量 int
                .minSelectNum(1)// 最小选择数量 int
                .imageSpanCount(4)// 每行显示个数 int
                .selectionMode(PictureConfig.MULTIPLE)// 多选 or 单选 PictureConfig.MULTIPLE or PictureConfig.SINGLE
                .previewImage(true)// 是否可预览图片 true or false
                .previewVideo(true)// 是否可预览视频 true or false
                .enablePreviewAudio(true) // 是否可播放音频 true or false
                .isCamera(true)// 是否显示拍照按钮 true or false
                .imageFormat(PictureMimeType.PNG)// 拍照保存图片格式后缀,默认jpeg
                .isZoomAnim(true)// 图片列表点击 缩放效果 默认true
                .sizeMultiplier(0.5f)// glide 加载图片大小 0~1之间 如设置 .glideOverride()无效
                .setOutputCameraPath("/CustomPath")// 自定义拍照保存路径,可不填
//                                            .enableCrop()// 是否裁剪 true or false
                .compress(true)// 是否压缩 true or false
                .glideOverride(160, 160)// int glide 加载宽高，越小图片列表越流畅，但会影响列表图片浏览的清晰度
//                                            .withAspectRatio()// int 裁剪比例 如16:9 3:2 3:4 1:1 可自定义
                .hideBottomControls(false)// 是否显示uCrop工具栏，默认不显示 true or false
                .isGif(true)// 是否显示gif图片 true or false
//                                            .compressSavePath()//压缩图片保存地址
                .freeStyleCropEnabled(true)// 裁剪框是否可拖拽 true or false
                .circleDimmedLayer(true)// 是否圆形裁剪 true or false
                .showCropFrame(false)// 是否显示裁剪矩形边框 圆形裁剪时建议设为false   true or false
                .showCropGrid(true)// 是否显示裁剪矩形网格 圆形裁剪时建议设为false    true or false
                .openClickSound(true)// 是否开启点击声音 true or false
//                .selectionMedia(selectList)// 是否传入已选图片 List<LocalMedia> list
                .previewEggs(true)// 预览图片时 是否增强左右滑动图片体验(图片滑动一半即可看到上一张是否选中) true or false
                .cropCompressQuality(90)// 裁剪压缩质量 默认90 int
                .minimumCompressSize(100)// 小于100kb的图片不压缩
                .synOrAsy(true)//同步true或异步false 压缩 默认同步
//                                            .cropWH()// 裁剪宽高比，设置如果大于图片本身宽高则无效 int
                .rotateEnabled(true) // 裁剪是否可旋转图片 true or false
                .scaleEnabled(true)// 裁剪是否可放大缩小图片 true or false
                .videoQuality(1)// 视频录制质量 0 or 1 int
                .videoMaxSecond(30)// 显示多少秒以内的视频or音频也可适用 int
                .videoMinSecond(30)// 显示多少秒以内的视频or音频也可适用 int
                .recordVideoSecond(30)//视频秒数录制 默认60s int
                .isDragFrame(false)// 是否可拖动裁剪框(固定)
                .forResult(10099);//结果回调onActivityResult code
    }

    private void processSelectMedia(Intent data) {
        List<LocalMedia> selectList = PictureSelector.obtainMultipleResult(data);
        processUpload(selectList);
    }

    public void processUpload(List<LocalMedia> selectList) {
        showWaitDialog("上传中...");
        appExecutors.diskIO().execute(() -> {
            if (selectList.size() > 0) {
                List<ReleaseDyamicBean> releaseDyamicBeanList = new ArrayList<>();
                for (int i = 0; i < selectList.size(); i++) {
                    LocalMedia localMedia = selectList.get(i);
                    ReleaseDyamicBean releaseDyamicBean = new ReleaseDyamicBean();
                    switch (PictureMimeType.pictureToVideo(localMedia.getPictureType())) {
                        case 1:
                            LogUtils.e("图片-----》");
                            releaseDyamicBean.setT("1");
                            if (!TextUtils.isEmpty(localMedia.getCompressPath())) {
                                releaseDyamicBean.setImgPath(localMedia.getCompressPath());
                            } else {
                                releaseDyamicBean.setImgPath(localMedia.getPath());
                            }
                            releaseDyamicBean.setSort(i);
                            break;
                        case 2:
                            LogUtils.e("视频-----》");
                            releaseDyamicBean.setT("2");
                            releaseDyamicBean.setVideoUrl(localMedia.getPath().substring(localMedia.getPath().lastIndexOf(Constant.mBaseImageUrl) + Constant.mBaseImageUrl.length()));
                            releaseDyamicBean.setLocVideoPath(localMedia.getPath());

                            try {
                                MediaMetadataRetriever mmr = new MediaMetadataRetriever();
                                mmr.setDataSource(localMedia.getPath());
                                Bitmap bitmap = mmr.getFrameAtTime((long) (1) * 1000 * 1000, MediaMetadataRetriever.OPTION_CLOSEST);
                                String vfname = String.valueOf(System.currentTimeMillis()) + "video" + i;
                                if (bitmap != null) {
                                    mFileUtils.saveBitmapPng(bitmap, vfname);
                                    releaseDyamicBean.setVideoImgPath(mFileUtils.SDPATH + vfname + ".png");
                                } else {
                                    releaseDyamicBean.setVideoImgPath("");
                                }
                                releaseDyamicBean.setSort(i);
                            } catch (Exception e) {
                                releaseDyamicBean.setVideoImgPath("");
                            }
                            break;
                        case 3:
                            // 音频
                            LogUtils.e("音频-----》");
                            break;
                    }
                    releaseDyamicBeanList.add(releaseDyamicBean);
                }
                upLoad(releaseDyamicBeanList);
            } else {
                appExecutors.mainThread().execute(() ->
                        {
                            hidWaitDialog();
                            ToastUtils.showShort("请先选择资源");
                        }
                );
                return;
            }
        });
    }

    public void upLoad(List<ReleaseDyamicBean> releaseDyamicBeanList) {
        if (releaseDyamicBeanList.size() > 0) {
            if ("1".equals(releaseDyamicBeanList.get(0).getT())) { // 图片上传
                upImg(releaseDyamicBeanList);
            } else {
                // 视频上传
//                zipVideo(releaseDyamicBeanList);

            }
        } else {
            appExecutors.mainThread().execute(() -> {
                ToastUtils.showShort("请先选择资源");
            });
        }
    }

//    private void zipVideo(List<ReleaseDyamicBean> releaseDyamicBeanList) {
//        appExecutors.diskIO().execute(() -> {
//            for (int i = 0; i < releaseDyamicBeanList.size(); i++) {
//                try {
//                    Log.d("sss", "zipVideo: =====================" + releaseDyamicBeanList.get(i).getLocVideoPath());
////                    String path = SiliCompressor.with(CommonWebFragment.this.getHoldingActivity()).compressVideo(releaseDyamicBeanList.get(i).getLocVideoPath(),
//                            Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES).getPath());
//                    if (!TextUtils.isEmpty(path)) {
//                        releaseDyamicBeanList.get(i).setLocVideoPath(path);
//                        Log.d("sss", "zipVideo: =========path============" + path);
//                        Log.d("sss", "zipVideo: =========path============" + releaseDyamicBeanList.get(i).getLocVideoPath());
//                    }
//                } catch (URISyntaxException e) {
//                    e.printStackTrace();
//                    Log.d("sss", "zipVideo: =========URISyntaxException============");
//                }
//            }
//            Log.d("sss", "zipVideo: =====================" + releaseDyamicBeanList.get(0).getLocVideoPath());
//            upVideo(releaseDyamicBeanList);
//        });
//    }

    private void upImg(List<ReleaseDyamicBean> releaseDyamicBeanList) {

        if (!NetworkUtils.isAvailableByPing()) {
            appExecutors.mainThread().execute(() -> {
                hidWaitDialog();
                ToastUtils.showShort("网络未连接，请设置网络后重试！");
            });
        } else {
            for (int i = 0; i < releaseDyamicBeanList.size(); i++) {

                if (releaseDyamicBeanList.get(i).isUpLoaded()) {

                    if (i == releaseDyamicBeanList.size()) {
                        appExecutors.mainThread().execute(new Runnable() {
                            @Override
                            public void run() {
                                hidWaitDialog();
                                imgSuccess();
                            }
                        });
                    }
                    continue;
                }
//                int finalI = i;
                if (releaseDyamicBeanList.get(i).getImgPath().startsWith("http")) {
                    releaseDyamicBeanList.get(i).setUpLoaded(true);
                    mSourceUpParam.imgList.add(releaseDyamicBeanList.get(i).getImgPath());
                    if (mSourceUpParam.imgList.size() == releaseDyamicBeanList.size()) {
                        hidWaitDialog();
                    }
                } else {
//                    FileUtils.getFileSize(new File(releaseDyamicBeanList.get(i).getImgPath()));
                    BitmapFactory.Options options = new BitmapFactory.Options();
                    BitmapFactory.decodeFile(releaseDyamicBeanList.get(i).getImgPath(), options);
                    options.inJustDecodeBounds = true;
                    //获取图片的宽高
                    int height = options.outHeight;
                    int width = options.outWidth;
                    upImgTooss(width, height, releaseDyamicBeanList, i);
//                    upImgToServer(width, height, releaseDyamicBeanList, i);
                }
            }

        }

    }

    private void upVideo(List<ReleaseDyamicBean> releaseDyamicBeanList) {
        if (!NetworkUtils.isAvailableByPing()) {
            appExecutors.mainThread().execute(() -> {
                hidWaitDialog();
                ToastUtils.showShort("网络未连接，请设置网络后重试！");
            });
        } else {
            for (int i = 0; i < releaseDyamicBeanList.size(); i++) {
                if (releaseDyamicBeanList.get(i).isUpLoaded()) {
                    if (i == releaseDyamicBeanList.size()) {
                        appExecutors.mainThread().execute(() -> {
                            hidWaitDialog();
//                            videoSuccess();
                        });
                    }
                    continue;
                }
                int finalI = i;
                MyOSSUtils.getInstance().upVideo(this.getHoldingActivity(), FileUtils.getFileName(releaseDyamicBeanList.get(i).getLocVideoPath()), releaseDyamicBeanList.get(i).getLocVideoPath(), new MyOSSUtils.OssUpCallback() {
                    @Override
                    public void successImg(String img_url) {

                    }

                    @Override
                    public void successVideo(String video_url) {
                        if (video_url != null) {
                            ReleaseDyamicBean releaseDyamicBean = releaseDyamicBeanList.get(finalI);
                            String videourl[] = video_url.split("com");
                            releaseDyamicBean.setVideoUrl(videourl[1]);
                            if (TextUtils.isEmpty(releaseDyamicBeanList.get(finalI).getVideoImgPath())) {
                                releaseDyamicBean.setUpLoaded(true);
                                releaseDyamicBean.setVideoImgUrl("");
                                mSourceUpParam.upLoadVideoList.add(releaseDyamicBean);
                                if (mSourceUpParam.upLoadVideoList.size() == releaseDyamicBeanList.size()) {
                                    hidWaitDialog();
                                    videoSuccess();
                                }
                            } else {
                                BitmapFactory.Options options = new BitmapFactory.Options();
                                BitmapFactory.decodeFile(releaseDyamicBeanList.get(finalI).getVideoImgPath(), options);
                                options.inJustDecodeBounds = true;
                                //获取图片的宽高
                                int height = options.outHeight;
                                int width = options.outWidth;
                                MyOSSUtils.getInstance().upImage(CommonWebFragment.this.getHoldingActivity(), getImageUrl(String.valueOf(width), String.valueOf(height)), releaseDyamicBeanList.get(finalI).getVideoImgPath(), new MyOSSUtils.OssUpCallback() {
                                    @Override
                                    public void successImg(String img_url) {
                                        appExecutors.mainThread().execute(() -> {
                                            if (img_url != null) {
//                                            mSourceUpParam.imgList.add(img_url);
//                                            releaseDyamicBeanList.get(finalI).setUpLoaded(true);
                                                mSourceUpParam.upLoadVideoList.add(releaseDyamicBean);
                                                String imgurl[] = img_url.split("com");
                                                releaseDyamicBean.setUpLoaded(true);
                                                releaseDyamicBean.setVideoImgUrl(imgurl[1]);
                                                if (mSourceUpParam.upLoadVideoList.size() == releaseDyamicBeanList.size()) {
                                                    hidWaitDialog();
                                                    videoSuccess();
                                                }
                                            } else {
                                                hidWaitDialog();
                                                ToastUtils.showShort("视频图片上传失败请重试！");
                                            }
                                        });
                                    }

                                    @Override
                                    public void successVideo(String video_url) {

                                    }

                                    @Override
                                    public void inProgress(long progress, long zong) {

                                    }
                                });
                            }

                        } else {
                            appExecutors.mainThread().execute(() -> {
                                hidWaitDialog();
                                ToastUtils.showShort("视频上传失败请重试！");
                            });
                        }
                    }

                    @Override
                    public void inProgress(long progress, long zong) {
                    }
                });
            }
        }
    }

    private void upImgTooss(int width, int height, List<ReleaseDyamicBean> releaseDyamicBeanList, int i) {
        MyOSSUtils.getInstance().upImage(this.getHoldingActivity(), getImageUrl(String.valueOf(width), String.valueOf(height)), releaseDyamicBeanList.get(i).getImgPath(), new MyOSSUtils.OssUpCallback() {
            @Override
            public void successImg(String img_url) {
                if (img_url != null) {
                    releaseDyamicBeanList.get(i).setUpLoaded(true);
                    String imgurl[] = img_url.split("com");
                    mSourceUpParam.imgList.add(imgurl[1]);
                    appExecutors.mainThread().execute(() -> {
                        if (mSourceUpParam.imgList.size() == releaseDyamicBeanList.size()) {
                            hidWaitDialog();
                            imgSuccess();
                        }
                    });
                } else {
                    appExecutors.mainThread().execute(() -> {
                        hidWaitDialog();
                        ToastUtils.showShort("上传失败请重新选择后上传！");
                    });
                }
            }

            @Override
            public void successVideo(String video_url) {

            }

            @Override
            public void inProgress(long progress, long zong) {

            }
        });

    }

    private String getImageUrl(String with, String height) {
        String mpath = "";
        mpath = "upload/image/" + System.currentTimeMillis() + "_" + with + "x" + height + ".png";
        return mpath;
    }

    private void videoSuccess() {
        if (mSourceUpParam.upLoadVideoList.size() > 0) {
            List<ServiceDataBean.ResourceBean> resourceBeans = new ArrayList<>();
            for (int i = 0; i < mSourceUpParam.upLoadVideoList.size(); i++) {
                ServiceDataBean.ResourceBean resourceBean = new ServiceDataBean.ResourceBean();
                resourceBean.setT(2);
                resourceBean.setUrl(mSourceUpParam.upLoadVideoList.get(i).getVideoUrl());
                resourceBean.setImg(mSourceUpParam.upLoadVideoList.get(i).getVideoImgUrl());
                resourceBean.setSort(String.valueOf(i + 1));
                resourceBeans.add(resourceBean);
            }
            String json = GsonUtils.toJson(resourceBeans);
            final int version = Build.VERSION.SDK_INT;
            Log.d("sss", "videoSuccess: ==============" + json);
            if (version < 18) {
                webView.loadUrl("javascript:getImgAndVideoByApp('" + json + "')");
            } else {
                webView.evaluateJavascript("javascript:getImgAndVideoByApp('" + json + "')", value -> {
                });
            }
        }
    }

    private void imgSuccess() {
        if (mSourceUpParam.imgList.size() > 0) {
            List<ServiceDataBean.ResourceBean> resourceBeans = new ArrayList<>();
            for (int i = 0; i < mSourceUpParam.imgList.size(); i++) {
                ServiceDataBean.ResourceBean resourceBean = new ServiceDataBean.ResourceBean();
                resourceBean.setT(1);
                resourceBean.setUrl(mSourceUpParam.imgList.get(i));
                resourceBean.setImg(mSourceUpParam.imgList.get(i));
                resourceBean.setSort(String.valueOf(i + 1));
                resourceBeans.add(resourceBean);
            }
            String json = GsonUtils.toJson(resourceBeans);
            Log.d("sss", "imgSuccess: ==============" + json);
            final int version = Build.VERSION.SDK_INT;
            if (version < 18) {
                webView.loadUrl("javascript:getImgAndVideoByApp('" + json + "')");
            } else {
                webView.evaluateJavascript("javascript:getImgAndVideoByApp('" + json + "')", value -> {
                });
            }
        }
    }


    public <T> List<T> getResultList(String data, Class<T> classtype) {
        if (TextUtils.isEmpty(data)) return null;
        List<T> members = null;
        try {
            Gson gson = new Gson();
            JsonParser parser = new JsonParser();
            JsonArray Jarray = parser.parse(data).getAsJsonArray();
            members = new ArrayList<>();
            for (JsonElement obj : Jarray) {
                T member = gson.fromJson(obj, classtype);
                members.add(member);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return members;
    }

}

