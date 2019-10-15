package com.bfhd.account.ui;

import android.arch.lifecycle.ViewModelProvider;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.text.TextUtils;

import com.alibaba.android.arouter.launcher.ARouter;
import com.bfhd.account.BR;
import com.bfhd.account.R;
import com.bfhd.account.databinding.AccountActivityAddressListBinding;
import com.bfhd.account.vm.AccountViewModel;
import com.bfhd.circle.base.HivsBaseActivity;
import com.bfhd.circle.base.ViewEventResouce;
import com.bfhd.circle.base.adapter.HivsSampleAdapter;
import com.docker.common.common.utils.rxbus.RxBus;
import com.docker.common.common.utils.rxbus.RxEvent;
import com.docker.common.common.vo.AddressVo;
import com.docker.common.common.widget.empty.EmptyLayout;
import com.docker.common.common.widget.refresh.api.RefreshLayout;
import com.docker.common.common.widget.refresh.listener.OnRefreshListener;

import java.util.List;

import javax.inject.Inject;

import io.reactivex.disposables.Disposable;

/*
 * 地址列表
 **/
//@Route(path = AppRouter.ACCOUNT_ADDRESS_LIST)
public class AccounAddressListActivity extends HivsBaseActivity<AccountViewModel, AccountActivityAddressListBinding> {


    @Inject
    ViewModelProvider.Factory factory;

    private HivsSampleAdapter hivsSampleAdapter;
    private List<AddressVo> addressVoList;
    private Disposable disposable;
    private String type;

    @Override
    protected int getLayoutId() {
        return R.layout.account_activity_address_list;
    }

    @Override
    public AccountViewModel getmViewModel() {
        return ViewModelProviders.of(this, factory).get(AccountViewModel.class);
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
        type = intent.getStringExtra("type");
        disposable = RxBus.getDefault().toObservable(RxEvent.class).subscribe(rxEvent -> {
            if (rxEvent.getT().equals("refresh_address_list")) {
                mViewModel.mPage = 1;
                mViewModel.getAdressList();
            }
        });

    }


    public void initView() {
        mViewModel.getAdressList();
        hivsSampleAdapter = new HivsSampleAdapter(R.layout.account_item_address, BR.item) {
        };
        mBinding.recyclerView.setAdapter(hivsSampleAdapter);
        hivsSampleAdapter.notifyDataSetChanged();

        mBinding.tvAddAddress.setOnClickListener(v -> {
            AddressVo addressVo = new AddressVo();
            AccounAddAddressActivity.startMe(AccounAddressListActivity.this, "2", addressVo);
        });


        mBinding.refresh.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(@NonNull RefreshLayout refreshLayout) {
                mViewModel.mPage = 1;
                mViewModel.getAdressList();
            }
        });


        mBinding.empty.setOnretryListener(new EmptyLayout.OnretryListener() {
            @Override
            public void onretry() {
                mViewModel.mPage = 1;
                mViewModel.getAdressList();
            }
        });

        hivsSampleAdapter.setOnchildViewClickListener(new int[]{R.id.lin_choose_address, R.id.tv_edit_address}, (childview, position) -> {
            int i = childview.getId();
            if (i == R.id.tv_edit_address) {//编辑地址
                AddressVo addressVo = (AddressVo) hivsSampleAdapter.getmObjects().get(position);
                AccounAddAddressActivity.startMe(AccounAddressListActivity.this, "1", addressVo);
            }
            if (i == R.id.lin_choose_address) {
                for (int j = 0; j < hivsSampleAdapter.getmObjects().size(); j++) {
                    AddressVo addressVo = (AddressVo) hivsSampleAdapter.getmObjects().get(j);
                    addressVo.setCheck(false);
                }
                AddressVo addressVo = (AddressVo) hivsSampleAdapter.getmObjects().get(position);
                addressVo.setCheck(true);
                hivsSampleAdapter.notifyDataSetChanged();
                if (!TextUtils.isEmpty(type) && "1".equals(type)) {
                    Intent intent = new Intent();
                    intent.putExtra("AddressVo", addressVo);
                    setResult(RESULT_OK, intent);
                    this.finish();
                }

            }
        });

    }

    @Override
    public void OnVmEnentListner(ViewEventResouce viewEventResouce) {
        super.OnVmEnentListner(viewEventResouce);
        switch (viewEventResouce.eventType) {
            case 112:
                hidWaitDialog();
                if (viewEventResouce.data != null) {
                    mBinding.empty.hide();
                    addressVoList = (List<AddressVo>) viewEventResouce.data;
                    if (addressVoList.size() > 0) {
                        if (mViewModel.mPage == 1) {
                            hivsSampleAdapter.clear();
                        }
                        hivsSampleAdapter.getmObjects().addAll(addressVoList);
                        hivsSampleAdapter.notifyDataSetChanged();
                    } else {
                        if (mViewModel.mPage == 1) {
                            mBinding.empty.showNodata();
                        }
                    }


                }
                if (hivsSampleAdapter.getmObjects().size() == 0) {
                    mBinding.empty.showNodata();
                }
                mBinding.refresh.finishRefresh();
                mBinding.refresh.finishLoadMore();
                break;
            case 113:
                hidWaitDialog();
                mBinding.empty.hide();
                mBinding.empty.showError();
                break;
            case 540:

                break;
            case 542:
                break;
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (disposable != null) {
            disposable.dispose();
        }
    }
}
