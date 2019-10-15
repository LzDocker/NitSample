package com.bfhd.account.ui;

import android.arch.lifecycle.ViewModelProvider;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.view.View;

import com.alibaba.android.arouter.launcher.ARouter;
import com.bfhd.account.R;
import com.bfhd.account.databinding.AccountActivityInfoCompleteBinding;
import com.bfhd.account.vm.AccountViewModel;
import com.bfhd.circle.base.Constant;
import com.bfhd.circle.base.HivsBaseActivity;
import com.bfhd.circle.base.ViewEventResouce;
import com.bfhd.circle.ui.common.CommonH5Activity;
import com.dcbfhd.utilcode.utils.ToastUtils;
import com.docker.common.common.router.AppRouter;
import com.docker.common.common.utils.cache.CacheUtils;
import com.docker.common.common.vo.UserInfoVo;
import com.gyf.immersionbar.ImmersionBar;
import java.util.HashMap;
import javax.inject.Inject;

/*
 * 完善信息
 **/
public class AccountInfoCompleteActivity extends HivsBaseActivity<AccountViewModel, AccountActivityInfoCompleteBinding> {


    @Inject
    ViewModelProvider.Factory factory;
    private int type;
    private String area_code;
    private UserInfoVo userInfoVo;


    @Override
    public AccountViewModel getmViewModel() {
        return ViewModelProviders.of(this, factory).get(AccountViewModel.class);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.account_activity_info_complete;
    }

    @Override
    protected void inject() {
        super.inject();
        ARouter.getInstance().inject(this);
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        userInfoVo = CacheUtils.getUser();
        if (!TextUtils.isEmpty(getIntent().getExtras().getString("mType"))) {
            type = Integer.parseInt(getIntent().getExtras().getString("mType"));
        } else {
            type = 1;
        }
        mToolbar.setNavigationIndicator(R.mipmap.account_icon_close);
        mToolbar.setBackgroundColor(getResources().getColor(R.color.account_white));
        mToolbar.setTitle("完善信息", getResources().getColor(R.color.account_black));
        if (type == 2) { // 企业
            mBinding.llPersonCoutainer.setVisibility(View.GONE);
            mBinding.llCompanyCoutainer.setVisibility(View.VISIBLE);
            mBinding.tvJump.setVisibility(View.GONE);
            mBinding.edCompanyNum.setText(userInfoVo.mobile);
        } else {
            mBinding.llPersonCoutainer.setVisibility(View.VISIBLE);
            mBinding.llCompanyCoutainer.setVisibility(View.GONE);
            mBinding.tvJump.setVisibility(View.VISIBLE);
        }
        mBinding.tvNum.setOnClickListener(v -> { // 点击
            Intent intent = new Intent(this, AccounSelectCountryNumActivity.class);
            startActivityForResult(intent, 101);
        });
        mBinding.accountComplete.setOnClickListener(v -> {
            if (checkParam()) {
                processNext();
            }
        });
        // 用户协议
        mBinding.accountToUserConst.setOnClickListener(v -> {
            CommonH5Activity.startMe(AccountInfoCompleteActivity.this, Constant.UseContantWeb, "使用协议");
        });
        mBinding.accountUserConst.setOnClickListener(v -> {
            CommonH5Activity.startMe(AccountInfoCompleteActivity.this, Constant.UseUseWeb, "隐私协议"); // todo
        });

//        mBinding.tvSelectCompanyNum.setOnClickListener(v -> {
//            String[] options = new String[]{"50人一下", "50-100人", "100-200人", "500人以上"};
//            BottomSheetDialog bottomSheetDialog = new BottomSheetDialog();
//            bottomSheetDialog.setDataCallback(options, position -> {
//                bottomSheetDialog.dismiss();
//                mBinding.tvSelectCompanyNum.setText(options[position]);
//            });
//            bottomSheetDialog.show(this);
//        });
        mBinding.tvJump.setOnClickListener(v -> {
            finish();
            ARouter.getInstance().build(AppRouter.HOME).navigation();
        });
        mBinding.accountToUserConst.setOnClickListener(v -> {
            CommonH5Activity.startMe(AccountInfoCompleteActivity.this, Constant.UseContantWeb, "使用协议");
        });
    }

    private boolean checkParam() {
        if (type == 2) {

            //企业
            if (TextUtils.isEmpty(mBinding.edCompanyName.getText().toString().trim())) {
                ToastUtils.showShort("企业名称不能为空");
                return false;
            }
            if (TextUtils.isEmpty(mBinding.edCompanyContant.getText().toString().trim())) {
                ToastUtils.showShort("联系人姓名不能为空");
                return false;
            }
            if (TextUtils.isEmpty(mBinding.edCompanyJob.getText().toString().trim())) {
                ToastUtils.showShort("联系人职务不能为空");
                return false;
            }
            if (TextUtils.isEmpty(mBinding.edCompanyNum.getText().toString().trim())) {
                ToastUtils.showShort("联系人手机号不能为空");
                return false;
            }

            if (TextUtils.isEmpty(mBinding.edCompanyPhone.getText().toString().trim())) {
                ToastUtils.showShort("联系人电话不能为空");
                return false;
            }
            if (TextUtils.isEmpty(mBinding.edCompanyEmail.getText().toString().trim())) {
                ToastUtils.showShort("联系人邮箱不能为空");
                return false;
            }

            return true;
        } else {

            // 个人
            if (TextUtils.isEmpty(mBinding.edPersionName.getText().toString().trim())) {
                ToastUtils.showShort("姓名不能为空");
                return false;
            }
            if (TextUtils.isEmpty(mBinding.edPersionPassport.getText().toString().trim())) {
                ToastUtils.showShort("护照号不能为空");
                return false;
            }
            if (TextUtils.isEmpty(mBinding.edPersionName.getText().toString().trim())) {
                ToastUtils.showShort("邮箱不能为空");
                return false;
            }

            return true;
        }
    }

    @Override
    public void initView() {


    }


    // 完善个人/企业信息
    private void processNext() {
        //todo 企业完善信息的时候，传递参数的key值和后台不一致
        HashMap<String, String> param = new HashMap<>();
        if (type == 1) { // 个人
            param.put("fullname", mBinding.edPersionName.getText().toString().trim());
            param.put("passportNo", mBinding.edPersionPassport.getText().toString().trim());
            param.put("email", mBinding.edPersionEmail.getText().toString().trim());
        } else {//企业
            param.put("companyName", mBinding.edCompanyName.getText().toString().trim());
            if (!TextUtils.isEmpty(mBinding.tvSelectCompanyNum.getText().toString().trim())) {
                param.put("companySize", mBinding.tvSelectCompanyNum.getText().toString().trim());
            }
            param.put("fullname", mBinding.edCompanyContant.getText().toString().trim());
            param.put("job", mBinding.edCompanyJob.getText().toString().trim());
//            param.put("area_code", mBinding.tvNum.getText().toString().trim());
            param.put("tel", mBinding.edCompanyNum.getText().toString().trim());//电话
            param.put("landline_telephone", mBinding.edCompanyPhone.getText().toString().trim());//电话
            param.put("email", mBinding.edCompanyEmail.getText().toString().trim());
        }
        mViewModel.EditCard(param);
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

    @Override
    public void OnVmEnentListner(ViewEventResouce viewEventResouce) {
        super.OnVmEnentListner(viewEventResouce);
        if (viewEventResouce.eventType == 205) {
            ToastUtils.showShort("保存成功");
            runOnUiThread(() -> finish());
//            UserInfoVo userInfoVo = CacheUtils.getUser();
//            userInfoVo.isReg = 1;
//            CacheUtils.saveUser(userInfoVo);
            ARouter.getInstance().build(AppRouter.HOME).withString("complete_type", String.valueOf(type)).navigation();
//            ARouter.getInstance().build(AppRouter.Account.ACCOUNT_LOGIN).navigation();
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            if (requestCode == 101) {
                area_code = data.getStringExtra("data");
                mBinding.tvNum.setText(data.getStringExtra("data"));
            }
        }
    }
}
