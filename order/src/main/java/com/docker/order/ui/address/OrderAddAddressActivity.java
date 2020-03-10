package com.docker.order.ui.address;

import android.arch.lifecycle.ViewModelProviders;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.IBinder;
import android.text.TextUtils;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.android.arouter.launcher.ARouter;
import com.bigkoo.pickerview.builder.OptionsPickerBuilder;
import com.bigkoo.pickerview.listener.OnOptionsSelectListener;
import com.bigkoo.pickerview.view.OptionsPickerView;
import com.dcbfhd.utilcode.utils.ToastUtils;
import com.docker.common.common.router.AppRouter;
import com.docker.common.common.ui.base.NitCommonActivity;
import com.docker.common.common.utils.rxbus.RxBus;
import com.docker.common.common.utils.rxbus.RxEvent;
import com.docker.common.common.widget.dialog.ConfirmDialog;
import com.docker.order.R;
import com.docker.order.databinding.ProOrderAddAddressActivityBinding;
import com.docker.order.vm.OrderAddressViewModel;
import com.docker.order.vo.AddressVo;
import com.docker.order.vo.AllLinkageVo;

import java.util.ArrayList;
import java.util.List;

/*
 * 新增地址/编辑地址
 **/
@Route(path = AppRouter.ORDER_ADDRESS_EDIT)
public class OrderAddAddressActivity extends NitCommonActivity<OrderAddressViewModel, ProOrderAddAddressActivityBinding> {

    private AddressVo addressVo;
    private Intent intent;
    private String type;
    // 多级联动
    private OptionsPickerView pvOptions;
    private String province;
    private String city;
    private String area;
    private String provinceId;
    private String cityId;
    private String districtId;
    private AllLinkageVo linageBean;
    private String id;


    @Override
    protected int getLayoutId() {
        return R.layout.pro_order_add_address_activity;
    }

    @Override
    public OrderAddressViewModel getmViewModel() {
        return ViewModelProviders.of(this, factory).get(OrderAddressViewModel.class);
    }

    @Override
    protected void inject() {
        super.inject();
        ARouter.getInstance().inject(this);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mBinding.empty.hide();
        intent = getIntent();
        Bundle bundle = intent.getExtras();
        type = bundle.getString("type");
        if ("1".equals(type)) {
            mToolbar.setTitle("编辑收货地址");
            addressVo = (AddressVo) bundle.getSerializable("AddressVo");
            if ("1".equals(addressVo.getIs_moren())) {
                mBinding.accountDefaultCheck.setChecked(true);
            } else {
                mBinding.accountDefaultCheck.setChecked(false);
            }
            id = addressVo.getId();
            provinceId = addressVo.getRegion1();
            cityId = addressVo.getRegion2();
            districtId = addressVo.getRegion3();
            mBinding.setItem(addressVo);
            mBinding.tvDeleteAddress.setVisibility(View.VISIBLE);
        } else {
            mBinding.tvDeleteAddress.setVisibility(View.GONE);
            mToolbar.setTitle("新增收货地址");
            addressVo = new AddressVo();
            addressVo.setIs_moren("0");
            mBinding.setItem(addressVo);
        }
    }

    @Override
    public void initView() {
        mBinding.tvComfireAddress.setOnClickListener(v -> {
            checkParams();
        });

        mBinding.linChooseLocation.setOnClickListener(v -> {
            if (linageBean != null) {
                pvOptions.show();
            } else {
                showWaitDialog("加载中...");
                mViewModel.getCityList();
            }
        });

        mBinding.tvDeleteAddress.setOnClickListener(v -> {
            ConfirmDialog.newInstance("提示", "确定删除该地址吗？").setConfimLietener(new ConfirmDialog.ConfimLietener() {
                @Override
                public void onCancle() {
                }

                @Override
                public void onConfim() {
                    // 删除
                    mViewModel.deleteAdress(addressVo);
                }

                @Override
                public void onConfim(String edit) {

                }
            }).setMargin(50).show(getSupportFragmentManager());

        });
        mBinding.accountDefaultCheck.setOnCheckedChangeListener((compoundButton, b) -> {
            if (b) {
                addressVo.setIs_moren("1");
            } else {
                addressVo.setIs_moren("0");
            }
        });
    }

    @Override
    public void initObserver() {

        mViewModel.mCitysLv.observe(this, allLinkageVo -> {
            hidWaitDialog();
            linageBean = allLinkageVo;
            getList(linageBean.getData());
        });
        mViewModel.mDeladdressLv.observe(this, s -> {
            hidWaitDialog();
            RxBus.getDefault().post(new RxEvent<>("refresh_address_list", id));
            OrderAddAddressActivity.this.finish();
        });

        mViewModel.mAddaddressLv.observe(this, s -> {
            hidWaitDialog();
            RxBus.getDefault().post(new RxEvent<>("refresh_address_list", id));
            OrderAddAddressActivity.this.finish();
        });
    }

    @Override
    public void initRouter() {

    }


    private void checkParams() {
        AddressVo addressVo = mBinding.getItem();
        if (TextUtils.isEmpty(addressVo.getName())) {
            ToastUtils.showShort("请输入姓名");
            return;
        }

        if (TextUtils.isEmpty(addressVo.getPhone())) {
            ToastUtils.showShort("请输入联系方式");
            return;
        }

        if (TextUtils.isEmpty(addressVo.getLocation())) {
            ToastUtils.showShort("请选择地址");
            return;
        }

        if (TextUtils.isEmpty(addressVo.getAddress())) {
            ToastUtils.showShort("请输入详细地址");
            return;
        }
        // 保存地址数据
        addressVo.setRegion1(provinceId);
        addressVo.setRegion2(cityId);
        addressVo.setRegion3(districtId);
        mViewModel.addAdress(addressVo);
    }


    /**
     * 解析处理地区数据
     *
     * @param list
     */
    private void getList(final List<AllLinkageVo.DataBean> list) {
        try {
            final ArrayList<String> li = new ArrayList<String>();
            final ArrayList<ArrayList<String>> li1 = new ArrayList<ArrayList<String>>();
            final ArrayList<ArrayList<ArrayList<String>>> li2 = new
                    ArrayList<ArrayList<ArrayList<String>>>();
            for (int j = 0; j < list.size(); j++) {
                li.add(list.get(j).getName());
                ArrayList<String> li11 = new ArrayList<String>();
                ArrayList<ArrayList<String>> li21 = new ArrayList<ArrayList<String>>();
                for (int k = 0; k < list.get(j).getChild().size(); k++) {
                    li11.add(list.get(j).getChild().get(k).getName());
                    ArrayList<String> li22 = new ArrayList<String>();
                    if (list.get(j).getChild().get(k).getChild() != null) {
                        for (int l = 0; l < list.get(j).getChild().get(k).getChild().size(); l++) {
                            li22.add(list.get(j).getChild().get(k).getChild().get(l).getName());
                        }
                    }
                    li21.add(li22);
                }
                li1.add(li11);
                li2.add(li21);
            }

            //条件选择器
            pvOptions = new OptionsPickerBuilder(this, new OnOptionsSelectListener() {
                @Override
                public void onOptionsSelect(int options1, int options2, int options3, View v) {
                    //返回的分别是三个级别的选中位置
                    province = list.get(options1).getName();
                    city = list.get(options1).getChild().get(options2).getName();

                    try {
                        area = options3 == -1 ? "" : list.get(options1).getChild().get
                                (options2).getChild().get(options3).getName();
                    } catch (Exception e) {
                        area = "";
                    }

                    provinceId = list.get(options1).getLinkageid();
                    cityId = list.get(options1).getChild().get(options2).getLinkageid();

                    try {
                        districtId = options3 == -1 ? "" : list.get(options1).getChild().get
                                (options2).getChild().get(options3).getLinkageid();
                    } catch (Exception e) {
                        districtId = "-1";
                    }

                    String tx = province + " " + city + " " + area;
                    mBinding.tvLocation.setText(tx);

                }
            }).setCancelColor(this.getResources().getColor(R.color.open_read))
                    .setSubmitColor(this.getResources().getColor(R.color.open_read))
                    .setSubCalSize(15)
                    .setContentTextSize(16)
                    .isDialog(false)
                    .build();
            pvOptions.setPicker(li, li1, li2);
            pvOptions.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
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
    protected void onDestroy() {
        super.onDestroy();

    }
}
