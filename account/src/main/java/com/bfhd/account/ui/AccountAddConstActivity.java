package com.bfhd.account.ui;

import android.arch.lifecycle.ViewModelProvider;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.TextUtils;

import com.alibaba.android.arouter.launcher.ARouter;
import com.bfhd.account.R;
import com.bfhd.account.databinding.AccountActivityAddConstBinding;
import com.bfhd.account.vm.AccountViewModel;
import com.bfhd.circle.base.HivsBaseActivity;
import com.bfhd.circle.base.ViewEventResouce;
import com.dcbfhd.utilcode.utils.ToastUtils;
import com.docker.common.common.router.AppRouter;
import com.docker.core.widget.BottomSheetDialog;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.inject.Inject;

/*
 * 添加联系人
 **/
public class AccountAddConstActivity extends HivsBaseActivity<AccountViewModel, AccountActivityAddConstBinding> {

    private BottomSheetDialog bottomSheetDialog = new BottomSheetDialog();
    @Inject
    ViewModelProvider.Factory factory;
    private String data, id;
    private String concat_name;
    private String concat_phone;
    private String area_code;
    private String remark;


    @Override
    public AccountViewModel getmViewModel() {
        return ViewModelProviders.of(this, factory).get(AccountViewModel.class);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.account_activity_add_const;
    }

    @Override
    protected void inject() {
        super.inject();
        ARouter.getInstance().inject(this);
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Intent intent = getIntent();
        data = intent.getStringExtra("data");
        id = intent.getStringExtra("id");
        concat_name = intent.getStringExtra("concat_name");
        concat_phone = intent.getStringExtra("concat_phone");
        area_code = intent.getStringExtra("area_code");
        remark = intent.getStringExtra("remark");
        mBinding.editName.setText(concat_name);
        mBinding.editPhone.setText(concat_phone);
        mBinding.tvFriendSel.setText(area_code);
        mBinding.editRemark.setText(remark);
    }

    @Override
    public void initView() {
        mToolbar.setTitle("添加联系人");
        // 保存联系人
        mBinding.accountAddContant.setOnClickListener(v -> {
            String name = mBinding.editName.getText().toString().trim();
            String remark = mBinding.editRemark.getText().toString().trim();
            String quNum = mBinding.tvFriendSel.getText().toString().trim();
            String phone = mBinding.editPhone.getText().toString().trim();

            if (TextUtils.isEmpty(name)) {
                ToastUtils.showShort("请输入姓名");
                return;
            }
            if (TextUtils.isEmpty(remark)) {
                ToastUtils.showShort("请输入备注");
                return;
            }
            if (TextUtils.isEmpty(quNum)) {
                ToastUtils.showShort("请选择区号");
                return;
            }
            if (TextUtils.isEmpty(phone)) {
                ToastUtils.showShort("请输入手机号");
                return;
            }

            if ("+86".equals(quNum)) {
                if (!isMobile(phone)) {
                    ToastUtils.showShort("请输入正确的手机号");
                    return;
                }
            }
            if (data.equals("1")) {//修改联系人信息
                mViewModel.editEmergencyContact(id, name, remark, quNum, phone);
            } else {//添加联系人信息
                mViewModel.saveEmergencyContact(name, remark, quNum, phone);
            }

        });

        mBinding.tvFriendSel.setOnClickListener(v -> {
            ARouter.getInstance().build(AppRouter.ACCOUNT_COUNTRY).navigation(AccountAddConstActivity.this, 101);
        });

    }

    /*
     * 正则表达式验证手机号
     * */
    public boolean isMobile(final String str) {
        Pattern p = null;
        Matcher m = null;
        boolean b = false;
        p = Pattern.compile("^[1][3,4,5,7,8][0-9]{9}$");
        m = p.matcher(str);
        b = m.matches();
        return b;
    }

    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            if (requestCode == 101) {// 选择国家
                mBinding.tvFriendSel.setText(data.getStringExtra("data"));
            }
        }
    }

    @Override
    public void OnVmEnentListner(ViewEventResouce viewEventResouce) {
        super.OnVmEnentListner(viewEventResouce);
        switch (viewEventResouce.eventType) {
            case 522:
                AccountAddConstActivity.this.finish();

                break;

            case 523:

                break;
            case 536:

                break;
            case 528:
                AccountAddConstActivity.this.finish();
                break;
            case 529:

                break;


        }
    }
}
