package com.bfhd.account.ui;

import android.arch.lifecycle.ViewModelProvider;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.databinding.Observable;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.TextView;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.android.arouter.launcher.ARouter;
import com.bfhd.account.R;
import com.bfhd.account.databinding.AccountActivityCompleteInfoBinding;
import com.bfhd.account.databinding.AccountActivityRegistBinding;
import com.bfhd.account.utils.AccountConstant;
import com.bfhd.account.vm.AccountViewModel;
import com.bfhd.account.vo.RegistParmVo;
import com.bfhd.circle.base.HivsBaseActivity;
import com.bfhd.circle.base.ViewEventResouce;
import com.bfhd.circle.ui.common.CircleSourceUpFragment;
import com.bfhd.circle.ui.common.CommonH5Activity;
import com.bfhd.circle.vo.bean.SourceUpParam;
import com.dcbfhd.utilcode.utils.FragmentUtils;
import com.dcbfhd.utilcode.utils.ToastUtils;
import com.docker.cirlev2.util.BdUtils;
import com.docker.common.common.config.GlideApp;
import com.docker.common.common.router.AppRouter;
import com.docker.common.common.utils.cache.CacheUtils;
import com.docker.common.common.vo.UserInfoVo;
import com.docker.common.common.vo.VisitingCardVo;
import com.gyf.immersionbar.ImmersionBar;

import java.util.HashMap;
import java.util.Set;
import java.util.concurrent.TimeUnit;

import javax.inject.Inject;

import cn.jpush.android.api.JPushInterface;
import cn.jpush.android.api.TagAliasCallback;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;

/*
 * 完善信息
 * */
@Route(path = AppRouter.ACCOUNT_COMPLETE_INFO)
public class CompleteInfoActivity extends HivsBaseActivity<AccountViewModel, AccountActivityCompleteInfoBinding> {


    SourceUpParam mHeadSourceUpParam;
    private CircleSourceUpFragment sourceHeadUpFragment;


    @Inject
    ViewModelProvider.Factory factory;

    @Override
    public AccountViewModel getmViewModel() {
        return ViewModelProviders.of(this, factory).get(AccountViewModel.class);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.account_activity_complete_info;
    }

    @Override
    protected void inject() {
        super.inject();
        ARouter.getInstance().inject(this);
    }

    @Override
    public void initImmersionBar() {
        super.initImmersionBar();
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

        mHeadSourceUpParam = new SourceUpParam(SourceUpParam.SOURCE_TYPE_AUTO, 1);
        mHeadSourceUpParam.needCrop = true;
        sourceHeadUpFragment = CircleSourceUpFragment.newInstance(mHeadSourceUpParam);
        sourceHeadUpFragment.setmSingleImageView(mBinding.ivHeader);
        FragmentUtils.add(getSupportFragmentManager(), sourceHeadUpFragment, R.id.frame);

        mHeadSourceUpParam.status.addOnPropertyChangedCallback(new android.databinding.Observable.OnPropertyChangedCallback() {
            @Override
            public void onPropertyChanged(Observable sender, int propertyId) {
                // 根据状态回调 来判断是否图片已经上传
                if (mHeadSourceUpParam.status.get() == 2) {
                    //获取头像照片的路径信息
                    GlideApp.with(mBinding.ivHeader).load(BdUtils.getImgUrl(mHeadSourceUpParam.imgList.get(0))).into(mBinding.ivHeader);
                }
            }
        });

        //点击选择头像
        mBinding.frClick.setOnClickListener(v -> {
            sourceHeadUpFragment.enterToSelect(2);
        });

        mBinding.tvSubmit.setOnClickListener(v -> {
            if (checkParam()) {

                HashMap<String, String> param = new HashMap<>();
                param.put("memberid", CacheUtils.getUser().uid);
                param.put("uuid", CacheUtils.getUser().uuid);
                if (mHeadSourceUpParam.imgList.size() > 0) {
                    param.put("avatar", mHeadSourceUpParam.imgList.get(0));
                }
                param.put("fullName", mBinding.edName.getText().toString().trim());
                param.put("labels", mBinding.edQianming.getText().toString().trim());
                mViewModel.saveUserInfo(param);
            }


        });

        //saveUserInfo


    }

    public boolean checkParam() {

        if (TextUtils.isEmpty(mBinding.edName.getText().toString())) {
            ToastUtils.showShort("请输入昵称");
            return false;
        }
        if (TextUtils.isEmpty(mBinding.edQianming.getText().toString())) {
            ToastUtils.showShort("请输入个性签名");
            return false;
        }

        return true;
    }


    @Override
    public void OnVmEnentListner(ViewEventResouce viewEventResouce) {
        super.OnVmEnentListner(viewEventResouce);
        switch (viewEventResouce.eventType) {
            case 538:
                ARouter.getInstance().build(AppRouter.HOME).navigation();
                finish();
                break;
        }
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            sourceHeadUpFragment.onActivityResult(requestCode, resultCode, data);
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }
}
