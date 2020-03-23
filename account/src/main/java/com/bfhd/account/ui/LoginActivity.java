package com.bfhd.account.ui;

import android.Manifest;
import android.arch.lifecycle.ViewModelProvider;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.os.Bundle;
import android.os.IBinder;
import android.text.TextUtils;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;

import com.alibaba.android.arouter.facade.annotation.Autowired;
import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.android.arouter.launcher.ARouter;
import com.bfhd.account.R;
import com.bfhd.account.databinding.AccountActivityLoginBinding;
import com.bfhd.account.vm.AccountViewModel;
import com.bfhd.circle.base.HivsBaseActivity;
import com.bfhd.circle.base.ViewEventResouce;
import com.dcbfhd.utilcode.utils.ToastUtils;
import com.docker.common.common.command.ReplyCommandParam;
import com.docker.common.common.provider.MessageService;
import com.docker.common.common.router.AppRouter;
import com.docker.common.common.utils.cache.CacheUtils;
import com.docker.common.common.utils.cache.DbCacheUtils;
import com.docker.common.common.utils.rxbus.RxBus;
import com.docker.common.common.utils.rxbus.RxEvent;
import com.docker.common.common.utils.tool.MD5Util;
import com.docker.common.common.vo.UserInfoVo;
import com.gyf.immersionbar.ImmersionBar;
import com.luck.picture.lib.permissions.RxPermissions;
import com.umeng.socialize.UMAuthListener;
import com.umeng.socialize.UMShareAPI;
import com.umeng.socialize.bean.SHARE_MEDIA;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

import javax.inject.Inject;

@Route(path = AppRouter.ACCOUNT_LOGIN)
public class LoginActivity extends HivsBaseActivity<AccountViewModel, AccountActivityLoginBinding> {

    private String area_code = "+86";
    private HashMap<String, String> wechatInfo = null;

    @Autowired
    MessageService imService;


    @Autowired
    boolean isFoceLogin = false;

    @Inject
    ViewModelProvider.Factory factory;


    @Inject
    DbCacheUtils dbCacheUtils;


    @Inject
    UserInfoVo userInfoVo;

    @Override
    public AccountViewModel getmViewModel() {
        return ViewModelProviders.of(this, factory).get(AccountViewModel.class);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.account_activity_login;
    }

    @Override
    protected void inject() {
        super.inject();
        ARouter.getInstance().inject(this);
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mToolbar.setNavigationIndicator(R.mipmap.account_icon_close);
        mToolbar.setBackgroundColor(getResources().getColor(R.color.account_white));
        mToolbar.setTitle("登录", getResources().getColor(R.color.account_black));
        initperssion();
    }

    public void loginWithIm(String account, String token) {
//        imService = (MessageService) ARouter.getInstance().build(AppRouter.IMPROVIDER_TALK).navigation();
        imService.loginIm(account, token, (ReplyCommandParam<Integer>) integer -> {
            if ("1".equals(CacheUtils.getUser().perfectData)) {
                ARouter.getInstance().build(AppRouter.ACCOUNT_COMPLETE_INFO).navigation();
                finish();
                return;
            }
            switch (integer) {
                case 0:
                case 1:
                case 2:
                    imService.setAlias(isFoceLogin);
                    if (!isFoceLogin) {
                        ARouter.getInstance().build(AppRouter.HOME).navigation(LoginActivity.this);
                    } else {
                        RxBus.getDefault().post(new RxEvent<>("login_state_change", ""));
                    }
                    finish();
                    break;
            }
        });
    }


    private void initperssion() {
        RxPermissions rxPermissions = new RxPermissions(this);
        rxPermissions.requestEach(
                Manifest.permission.WRITE_EXTERNAL_STORAGE,
                Manifest.permission.READ_EXTERNAL_STORAGE)
                .subscribe(permission -> {
                    if (permission.name.equals(Manifest.permission.WRITE_EXTERNAL_STORAGE)) {
                        if (permission.granted) {
                            // 用户已经同意该权限


                        } else if (permission.shouldShowRequestPermissionRationale) {
                            // 用户拒绝了该权限，没有选中『不再询问』（Never ask again）,那么下次再次启动时，还会提示请求权限的对话框

                        } else {

                            // 用户拒绝了该权限，并且选中『不再询问』
//
                        }
                    }
                });
    }


    @Override
    public void initImmersionBar() {

        ImmersionBar.with(this)
                .fitsSystemWindows(true)  //使用该属性,必须指定状态栏颜色
                .statusBarColor("#ffffff")
                .statusBarDarkFont(true)   //状态栏字体是深色，不写默认为亮色
                .autoDarkModeEnable(true) //自动状态栏字体和导航栏图标变色，必须指定状态栏颜色和导航栏颜色才可以自动变色哦
                .autoStatusBarDarkModeEnable(true, 0.2f) //自动状态栏字体变色，必须指定状态栏颜色才可以自动变色哦
                .autoNavigationBarDarkModeEnable(true, 0.2f) //自动导航栏图标变色，必须指定导航栏颜色才可以自动变色哦
                .flymeOSStatusBarFontColor("#000000")  //修改flyme OS状态栏字体颜色
                .navigationBarColor("#ffffff")
                .fullScreen(true)
                .addTag("PicAndColor")
                .init();
    }

    public void initView() {
        mBinding.accountWechatLogin.setOnClickListener(v -> {
            processWeChartLogin();
        });
        mBinding.accountQqLogin.setOnClickListener(v -> {
            processQQLogin();
        });
        mBinding.accountToRegister.setOnClickListener(v -> {
            Intent intent = new Intent(LoginActivity.this, RegistActivity.class);
            startActivity(intent);
        });
        mBinding.accountLoginButton.setOnClickListener(v -> {
//            dbCacheUtils.save("routerdb", "111", new ReplyCommand() {
//                @Override
//                public void exectue() {
//                    ToastUtils.showShort("success");
//                }
//            });

            if (TextUtils.isEmpty(mBinding.edUsername.getText().toString().trim())) {
                ToastUtils.showShort("用户名不能为空");
                return;
            }
            if (TextUtils.isEmpty(mBinding.edPassword.getText().toString().trim())) {
                ToastUtils.showShort("密码不能为空");
                return;
            }
            mViewModel.Login(mBinding.edUsername.getText().toString().trim(), mBinding.edPassword.getText().toString().trim(), area_code);
        });
        // 选择
        mBinding.rlNumSelect.setOnClickListener(v -> {
            Intent intent = new Intent(LoginActivity.this, AccounSelectCountryNumActivity.class);
            startActivityForResult(intent, 101);
        });

        // 忘记密码
        mBinding.accountFoggetPwd.setOnClickListener(v -> {
            Intent intent = new Intent(LoginActivity.this, FindPwdActivity.class);
            startActivity(intent);
//            routerControl.jump(RouterInfo.App_SEARCH,o -> ARouter.getInstance().build(String.valueOf(o)).navigation());
        });
    }

    public boolean dispatchTouchEvent(MotionEvent event) {
        if (event.getAction() == MotionEvent.ACTION_DOWN &&
                getCurrentFocus() != null &&
                getCurrentFocus().getWindowToken() != null) {

            View v = getCurrentFocus();
            if (isShouldHideKeyboard(v, event)) {
                hideKeyboard(v.getWindowToken());
            }
        }
        return super.dispatchTouchEvent(event);
    }

    /**
     * 根据EditText所在坐标和用户点击的坐标相对比，来判断是否隐藏键盘，因为当用户点击EditText时则不能隐藏
     */
    private boolean isShouldHideKeyboard(View v, MotionEvent event) {
        if (v != null && (v instanceof EditText)) {
            int[] l = {0, 0};
            v.getLocationOnScreen(l);
            int left = l[0],
                    top = l[1],
                    bottom = top + v.getHeight(),
                    right = left + v.getWidth();
            if (event.getRawX() > left && event.getRawX() < right
                    && event.getRawY() > top && event.getRawY() < bottom) {
                // 点击EditText的事件，忽略它。
                return false;
            } else {
                return true;
            }
        }
        // 如果焦点不是EditText则忽略，这个发生在视图刚绘制完，第一个焦点不在EditText上，和用户用轨迹球选择其他的焦点
        return false;
    }

    /**
     * 获取InputMethodManager，隐藏软键盘
     */
    private void hideKeyboard(IBinder token) {
        if (token != null) {
            InputMethodManager mInputMethodManager = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
            mInputMethodManager.hideSoftInputFromWindow(token, InputMethodManager.HIDE_NOT_ALWAYS);
        }
    }

    @Override
    public void OnVmEnentListner(ViewEventResouce viewEventResouce) {
        super.OnVmEnentListner(viewEventResouce);
        switch (viewEventResouce.eventType) {
            case 104:
                if (viewEventResouce.data != null) {
                    UserInfoVo userInfoVo = (UserInfoVo) viewEventResouce.data;
                    CacheUtils.saveUser(userInfoVo);
                    imService.setAlias(true);
                    loginWithIm(userInfoVo.uuid, MD5Util.toMD5_32(userInfoVo.uuid));
                }
                break;
            case 109:
                Intent intent = new Intent(LoginActivity.this, RegistActivity.class);
                intent.putExtra("wechatInfo", (Serializable) wechatInfo);
                intent.putExtra("bindType", "1");
                startActivity(intent);
                break;
            case 110:
                Intent intent1 = new Intent(LoginActivity.this, RegistActivity.class);
                intent1.putExtra("wechatInfo", (Serializable) wechatInfo);
                intent1.putExtra("bindType", "2");
                startActivity(intent1);
                break;
            case 204:

                break;
        }
    }

    private void processWeChartLogin() {
        UMShareAPI.get(this).getPlatformInfo(this, SHARE_MEDIA.WEIXIN, new UMAuthListener() {
            @Override
            public void onStart(SHARE_MEDIA share_media) {
                showWaitDialog("加载中...");
            }

            @Override
            public void onComplete(SHARE_MEDIA share_media, int i, Map<String, String> map) {
                hidWaitDialog();
                Log.d("sss", "onComplete: ============================");
//                String uid = map.get("uid");
//                String openid = map.get("openid");//微博没有
//                String unionid = map.get("unionid");//微博没有
//                String access_token = map.get("access_token");
//                String refresh_token = map.get("refresh_token");//微信,qq,微博都没有获取到
//                String expires_in = map.get("expires_in");
//                String name = map.get("name");
//                String gender = map.get("gender");
//                String iconurl = map.get("iconurl");

//                for (Map.Entry<String, String> entry : map.entrySet()) {
//                    String mapKey = entry.getKey();
//                    String mapValue = entry.getValue();
//                    Log.d(TAG, "onComplete: =============" + (mapKey + ":" + mapValue));
//                }

                wechatInfo = (HashMap<String, String>) map;
                HashMap<String, String> param = new HashMap<>();
                param.put("wxUid", map.get("unionid"));
                param.put("nickname", map.get("name"));
                param.put("avatar", map.get("iconurl"));
                mViewModel.ThiredLogin(param);
            }

            @Override
            public void onError(SHARE_MEDIA share_media, int i, Throwable throwable) {
                hidWaitDialog();
                Log.d("sss", "onError: ======onError======================");
            }

            @Override
            public void onCancel(SHARE_MEDIA share_media, int i) {
                hidWaitDialog();
                Log.d("sss", "onCancel: ======onCancel======================");
            }
        });
    }

    private void processQQLogin() {
        UMShareAPI.get(this).getPlatformInfo(this, SHARE_MEDIA.QQ, new UMAuthListener() {
            @Override
            public void onStart(SHARE_MEDIA share_media) {
                showWaitDialog("加载中...");
            }

            @Override
            public void onComplete(SHARE_MEDIA share_media, int i, Map<String, String> map) {
                hidWaitDialog();
//                String uid = map.get("uid");
//                String openid = map.get("openid");//微博没有
//                String unionid = map.get("unionid");//微博没有
//                String access_token = map.get("access_token");
//                String refresh_token = map.get("refresh_token");//微信,qq,微博都没有获取到
//                String expires_in = map.get("expires_in");
//                String name = map.get("name");
//                String gender = map.get("gender");
//                String iconurl = map.get("iconurl");
                wechatInfo = (HashMap<String, String>) map;
                HashMap<String, String> param = new HashMap<>();
                param.put("nickname", map.get("name"));
                param.put("avatar", map.get("iconurl"));
                param.put("qqUid", map.get("uid"));
                mViewModel.ThiredLoginQQ(param);
            }

            @Override
            public void onError(SHARE_MEDIA share_media, int i, Throwable throwable) {
                hidWaitDialog();
            }

            @Override
            public void onCancel(SHARE_MEDIA share_media, int i) {
                hidWaitDialog();
            }

        });
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode != 101) {
            UMShareAPI.get(this).onActivityResult(requestCode, resultCode, data);
        }
        if (resultCode == RESULT_OK) {
            if (requestCode == 101) {
                area_code = data.getStringExtra("num");
                mBinding.tvNum.setText(data.getStringExtra("num"));
            }
        }
    }

}