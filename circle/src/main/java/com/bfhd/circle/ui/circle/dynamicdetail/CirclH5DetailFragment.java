package com.bfhd.circle.ui.circle.dynamicdetail;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.arch.lifecycle.ViewModelProvider;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Context;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.widget.NestedScrollView;
import android.text.TextUtils;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.JavascriptInterface;
import android.webkit.WebSettings;

import com.alibaba.android.arouter.launcher.ARouter;
import com.baidu.location.BDAbstractLocationListener;
import com.baidu.location.BDLocation;
import com.baidu.location.LocationClient;
import com.baidu.location.LocationClientOption;
import com.bfhd.circle.BR;
import com.bfhd.circle.R;
import com.bfhd.circle.base.CommentDialogFragment;
import com.bfhd.circle.base.CommonFragment;
import com.bfhd.circle.base.Constant;
import com.bfhd.circle.base.ViewEventResouce;
import com.bfhd.circle.databinding.CircleFragmentDetailH5Binding;
import com.bfhd.circle.ui.active.CircleCommentFragment;
import com.bfhd.circle.ui.circle.CircleDetailActivity;
import com.bfhd.circle.ui.circle.CircleDynamicDetailActivity;
import com.bfhd.circle.ui.circle.CircleReplayQuestionActivity;
import com.bfhd.circle.utils.UiTypeUtils;
import com.bfhd.circle.vm.CircleDynamicViewModel;
import com.bfhd.circle.vo.CommentRstVo;
import com.bfhd.circle.vo.CommentVo;
import com.bfhd.circle.vo.ServiceDataBean;
import com.bfhd.circle.vo.bean.StaCirParam;
import com.bfhd.circle.vo.bean.StaDetailParam;
import com.dcbfhd.utilcode.utils.FragmentUtils;
import com.dcbfhd.utilcode.utils.KeyboardUtils;
import com.dcbfhd.utilcode.utils.ToastUtils;
import com.docker.common.common.router.AppRouter;
import com.docker.common.common.utils.cache.CacheUtils;
import com.docker.common.common.vo.UserInfoVo;
import com.luck.picture.lib.PictureSelector;
import com.luck.picture.lib.entity.LocalMedia;
import com.luck.picture.lib.permissions.RxPermissions;
import com.tencent.smtt.export.external.interfaces.SslError;
import com.tencent.smtt.export.external.interfaces.SslErrorHandler;

import java.util.HashMap;
import java.util.List;

import javax.inject.Inject;

/*
 * 头部是H5 的详情
 * */
public class CirclH5DetailFragment extends CommonFragment<CircleDynamicViewModel, CircleFragmentDetailH5Binding> {

    @Inject
    ViewModelProvider.Factory factory;

    @Override
    protected int getLayoutId() {
        return R.layout.circle_fragment_detail_h5;
    }

    private StaDetailParam mStaParam;
    private CommentDialogFragment dialogFragment;
    private String commentContent;
    private ServiceDataBean serviceDataBean;
    private CircleCommentFragment commentFragment;
    private com.tencent.smtt.sdk.WebView proWebview;

    public static CirclH5DetailFragment newInstance(StaDetailParam mStaParam) {
        CirclH5DetailFragment circlH5DetailFragment = new CirclH5DetailFragment();
        Bundle bundle = new Bundle();
        bundle.putSerializable("mStaParam", mStaParam);
        circlH5DetailFragment.setArguments(bundle);
        return circlH5DetailFragment;
    }

    public static CirclH5DetailFragment newInstance(StaDetailParam mStaParam, ServiceDataBean serviceDataBean) {
        CirclH5DetailFragment circlH5DetailFragment = new CirclH5DetailFragment();
        Bundle bundle = new Bundle();
        bundle.putSerializable("mStaParam", mStaParam);
        bundle.putSerializable("serviceDataBean", serviceDataBean);
        circlH5DetailFragment.setArguments(bundle);
        return circlH5DetailFragment;
    }

    public static CirclH5DetailFragment newInstance() {
        CirclH5DetailFragment detailFragment = new CirclH5DetailFragment();
        return detailFragment;
    }

    @Override
    protected CircleDynamicViewModel getViewModel() {
        return ViewModelProviders.of(this, factory).get(CircleDynamicViewModel.class);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        mStaParam = (StaDetailParam) getArguments().getSerializable("mStaParam");
        serviceDataBean = (ServiceDataBean) getArguments().getSerializable("serviceDataBean");
        mViewModel.mStaDetail = mStaParam;
        super.onActivityCreated(savedInstanceState);
        // 获取activity的vm 共享数据
//        CircleDynamicViewModel parentVm = ViewModelProviders.of(this.getActivity()).get(CircleDynamicViewModel.class);
//        parentVm.mEmptycommand.set(EmptyStatus.BdHiden);


        mBinding.get().setViewmodel(mViewModel);
        commentFragment = CircleCommentFragment.newInstance(mStaParam);
        commentFragment.smartRefreshLayout = mBinding.get().refresh;
        FragmentUtils.add(getChildFragmentManager(), commentFragment, R.id.pro_frame_comment);
        mBinding.get().refresh.setOnLoadMoreListener(refreshLayout -> {
            commentFragment.OnLoadMore(mBinding.get().refresh);
        });
        proWebview = mBinding.get().proWebview;
        initWebview(proWebview);
        mBinding.get().setItem(serviceDataBean);
        mViewModel.serviceDataBean = serviceDataBean;
        if (!serviceDataBean.getUtid().equals(CacheUtils.getUser().utid)) {
            mBinding.get().llFootCoutainer.setVisibility(View.VISIBLE);
            mBinding.get().cirlceGoodsFoot.setVisibility(View.VISIBLE); // 不是自己发布的
            mBinding.get().circleJubao.setVisibility(View.VISIBLE);
        } else {
            mBinding.get().llFootCoutainer.setVisibility(View.GONE);
        }
        mBinding.get().setVariable(BR.item, serviceDataBean);
        if (mStaParam.uiType == 1) {
            mBinding.get().proWebview.loadDataWithBaseURL(null, getHtmlData(serviceDataBean.getExtData().getContent()), "text/html", "utf-8", null);
            mBinding.get().empty.hide();
            ((CircleDynamicDetailActivity) getHoldingActivity()).hideLoading();
        } else {
            if ("house".equals(serviceDataBean.getType())) {
                processLocation();
            } else {
                weburl = Constant.getWebUrlWj(serviceDataBean.getType(),
                        CacheUtils.getUser().uid,
                        CacheUtils.getUser().uuid,
                        (serviceDataBean).getDynamicid()
                );
                mBinding.get().proWebview.loadUrl(weburl);
            }
        }
        processUi(serviceDataBean.getType());
        if (serviceDataBean.getUuid().equals(CacheUtils.getUser().uuid)) { // 自己发布的
            mBinding.get().circleIvEdit.setVisibility(View.VISIBLE);
            mBinding.get().circleIvEdit.setOnClickListener(v -> {
                processEdit(serviceDataBean);
            });
        }


    }

    private void processUi(String type) {
        mBinding.get().llSafeTitle.setVisibility(View.GONE);
        UiTypeUtils.processUiType(type, () -> {
            mBinding.get().circleTvWant.setVisibility(View.VISIBLE);
            mBinding.get().circleCommonBar.setVisibility(View.GONE);
            mBinding.get().ivBack.setOnClickListener(v -> {
                getHoldingActivity().finish();
            });
            mBinding.get().ivShare.setOnClickListener(v -> {
                ((CircleDynamicDetailActivity) getHoldingActivity()).processShare();
            });
            mBinding.get().netScroll.setOnScrollChangeListener((NestedScrollView.OnScrollChangeListener) (v, scrollX, scrollY, oldScrollX, oldScrollY) -> {
                Double range = scrollY / 100.0;
                if (range > 1) {
                    mBinding.get().llBag.setAlpha((float) (1));
                    return;
                }
                mBinding.get().llBag.setAlpha((float) (range * 1));
                if (scrollY == 0) {
                    mBinding.get().circleGoodsHeaderBar.setBackgroundColor(getResources().getColor(R.color.transparent));
                }
            });
        }, () -> {
        });
        if ("project".equals(type) || "news".equals(type) /*|| "dynamic".equals(type)*/) {
            mBinding.get().circleTvWant.setVisibility(View.GONE);
            mBinding.get().circleSpace.setVisibility(View.GONE);
            mBinding.get().rlDynamicTitle.setVisibility(View.VISIBLE);
            if ("project".equals(type)) {
                mBinding.get().circleGoodsHeaderBar.setVisibility(View.VISIBLE);
            } else {
                mBinding.get().circleGoodsHeaderBar.setVisibility(View.GONE);
                ViewGroup.MarginLayoutParams layoutParams = (ViewGroup.MarginLayoutParams) mBinding.get().rlDynamicTitle.getLayoutParams();
                layoutParams.topMargin = 0;
                mBinding.get().rlDynamicTitle.setLayoutParams(layoutParams);
                mBinding.get().circleCommonBar.setVisibility(View.VISIBLE);
                mBinding.get().cirlceGoodsFoot.setVisibility(View.GONE);
            }
        } else {
            mBinding.get().circleGoodsHeaderBar.setVisibility(View.VISIBLE);
            mBinding.get().rlDynamicTitle.setVisibility(View.GONE);
        }


    }

    @Override
    public void initImmersionBar() {

    }

    @Override
    protected void initView(View var1) {
        dialogFragment = new CommentDialogFragment();
        dialogFragment.setDataCallback(new CommentDialogFragment.DialogFragmentDataCallback() {
            @Override
            public void setCommentText(String commentTextTemp) {
                KeyboardUtils.hideSoftInput(CirclH5DetailFragment.this.getHoldingActivity());
            }

            @Override
            public void sendComment(String commentTextTemp) {
                commentContent = commentTextTemp;
                KeyboardUtils.hideSoftInput(CirclH5DetailFragment.this.getHoldingActivity());
                HashMap<String, String> params = new HashMap<>();
                UserInfoVo userInfoVo = CacheUtils.getUser();
                params.put("circleid", serviceDataBean.getCircleid());
                params.put("utid", serviceDataBean.getUtid());
                params.put("dynamicid", serviceDataBean.getDynamicid());
                params.put("push_uuid", serviceDataBean.getUuid());
                params.put("push_memberid", serviceDataBean.getMemberid());
                params.put("memberid", userInfoVo.uid);
                params.put("uuid", userInfoVo.uuid);
                if (TextUtils.isEmpty(userInfoVo.avatar)) {
                    userInfoVo.avatar = "/var/upload/image/section/logo3.png";
                }
                params.put("avatar", userInfoVo.avatar);
                if (TextUtils.isEmpty(userInfoVo.nickname)) {
                    params.put("nickname", "匿名");
                } else {
                    params.put("nickname", userInfoVo.nickname);
                }
                params.put("content", commentTextTemp);
                params.put("cid", "0");
                params.put("reply_memberid", "");
                params.put("reply_uuid", "");
                params.put("reply_nickname", "");
                mViewModel.commentDynamic(params);
            }
        });

        //点击跳转到圈子详情
        mBinding.get().linFromCircle.setOnClickListener(v -> {
            CircleDetailActivity.startMe(CirclH5DetailFragment.this.getHoldingActivity(), new StaCirParam(serviceDataBean.getCircleid(), serviceDataBean.getUtid(), ""));
        });


    }


    private void initWebview(com.tencent.smtt.sdk.WebView webView) {
//        IX5WebViewExtension ix5WebViewExtension = webView.getX5WebViewExtension();
//        ix5WebViewExtension.setScrollBarFadingEnabled(false);
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
        webView.addJavascriptInterface(new MyInterface(CirclH5DetailFragment.this.getHoldingActivity()), "android");

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
                    if (mBinding != null && mBinding.get() != null && mBinding.get().empty != null) {
                        mBinding.get().empty.hide();

                    }
                    ((CircleDynamicDetailActivity) getHoldingActivity()).hideLoading();
                }
            }
        });
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
    }


    @Override
    public void onVisible() {
        super.onVisible();
    }

    String weburl;

    @Override
    public void OnVmEnentListner(ViewEventResouce viewEventResouce) {
        super.OnVmEnentListner(viewEventResouce);

        switch (viewEventResouce.eventType) {
            case 204: // 大图预览
                PictureSelector.create(this).externalPicturePreview(Integer.parseInt(viewEventResouce.message), (List<LocalMedia>) viewEventResouce.data);
                break;
            case 210: // 显示评论对话框
                if (mStaParam.uiType == 2) {
                    CircleReplayQuestionActivity.startMe(this.getHoldingActivity(), serviceDataBean);
                } else {
                    dialogFragment.setText(commentContent, "发表评论..");
                    dialogFragment.show(getChildFragmentManager(), "CommentDialogFragment");
                }
                break;
            case 211:
                CommentVo commentVo = new CommentVo();
                commentVo.setCommentid(((CommentRstVo) viewEventResouce.data).commentid);
                UserInfoVo userInfoVo = CacheUtils.getUser();
                commentVo.setMemberid(userInfoVo.uid);
                commentVo.setUuid(userInfoVo.uuid);
                commentVo.setNickname(TextUtils.isEmpty(userInfoVo.nickname) ? "匿名" : userInfoVo.nickname);
                commentVo.setAvatar(userInfoVo.avatar);
                commentVo.setContent(commentContent);
                commentVo.setPraiseNum("0");
                commentVo.setInputtime(System.currentTimeMillis() + "");
                commentFragment.addComment(commentVo);
                commentContent = "";
                break;
            case 10099:
                if (viewEventResouce.data != null) {
//                    SessionHelper.startP2PSession(CirclH5DetailFragment.this.getHoldingActivity(), ((ServiceDataBean) viewEventResouce.data).getUuid());
                }
                break;
            case 10020:
                ((CircleDynamicDetailActivity) getHoldingActivity()).processReportUi();
                break;
        }
    }

    public final String getHtmlData(String bodyHTML) {
        String bob = "<div style=\"font-size:16px;line-height:25px;\"><img style=\"width: 100%;\" class=\"num_img control_img\" data-name=\"img\" src=\"https://nit-bj.oss-cn-beijing.aliyuncs.com/upload/2019.06.2019061720034436102_219x390.png\" alt=\"\" id=\"\"></div>";
        String head = "<head> <meta name=\"viewport\" content=\"width=device-width, initial-scale=1.0, user-scalable=no\"> <style>img{max-width: 100%; width:100%; height:auto;}</style> </head>";
        return "<html>" + head + "<body>" + bodyHTML + "</body></html>";
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        if (mBinding.get().proWebview != null) {
            mBinding.get().proWebview.destroy();
        }
    }

    public ServiceDataBean getServiceDataBean() {
        return serviceDataBean;
    }

    @Override
    public void onDestroy() {
        clearWebview(proWebview);
        super.onDestroy();
    }

    private void clearWebview(com.tencent.smtt.sdk.WebView webView) {
        if (webView != null) {
            webView.loadDataWithBaseURL(null, "", "text/html", "utf-8", null);
            webView.clearHistory();
            webView.clearCache(true);// 清除缓存
            ((ViewGroup) webView.getParent()).removeView(webView);
            webView.destroy();
        }
    }

    // 编辑
    private void processEdit(ServiceDataBean serviceDataBean) {
        ((CircleDynamicDetailActivity) getHoldingActivity()).processEdit(serviceDataBean);
    }

    @Override
    public void setData(Object obj) {
        super.setData(obj);
        this.serviceDataBean = (ServiceDataBean) obj;
        mBinding.get().setVariable(BR.item, serviceDataBean);
        if (mStaParam.uiType == 1) {
            mBinding.get().proWebview.loadDataWithBaseURL(null, getHtmlData(serviceDataBean.getExtData().getContent()), "text/html", "utf-8", null);
            mBinding.get().empty.hide();
            ((CircleDynamicDetailActivity) getHoldingActivity()).hideLoading();
        } else {

            if ("house".equals(serviceDataBean.getType())) {
                processLocation();
            } else {
                weburl = Constant.getWebUrlWj(serviceDataBean.getType(),
                        CacheUtils.getUser().uid,
                        CacheUtils.getUser().uuid,
                        (serviceDataBean).getDynamicid()
                );
                mBinding.get().proWebview.loadUrl(weburl);
            }
        }
    }


    private int locationCount = 0;
    private LocationClient mLocationClient;
    private String lat;
    private String lng;

    private void processLocation() {
        locationCount = 0;
        if (mLocationClient == null) {
            mLocationClient = new LocationClient(this.getHoldingActivity());
        }
        RxPermissions rxPermissions = new RxPermissions(this.getHoldingActivity());
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

                                        lat = String.valueOf(location.getLatitude());
                                        lng = String.valueOf(location.getLongitude());
                                        mLocationClient.stop();
                                        processHouseDetail();
                                    } else {
                                        locationCount++;
                                        if (locationCount > 3) {
                                            mLocationClient.stop();
                                            processHouseDetail();
                                        }
                                    }
                                }
                            });
                            mLocationClient.start();
                        } else {
                            ToastUtils.showShort("权限被拒绝，请在设置里面开启相应权限，若无相应权限会影响使用");
                            processHouseDetail();
                        }
                    }
                });
    }

    private void processHouseDetail() {
        weburl = Constant.getWebUrlWj(serviceDataBean.getType(),
                CacheUtils.getUser().uid,
                CacheUtils.getUser().uuid,
                (serviceDataBean).getDynamicid()
        );
        mBinding.get().proWebview.loadUrl(weburl + "&lat=" + lat + "&lng=" + lng);
    }
}
