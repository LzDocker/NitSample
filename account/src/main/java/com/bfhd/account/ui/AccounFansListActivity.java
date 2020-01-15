package com.bfhd.account.ui;

import android.arch.lifecycle.ViewModelProvider;
import android.arch.lifecycle.ViewModelProviders;
import android.os.Bundle;
import android.support.annotation.NonNull;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.android.arouter.launcher.ARouter;
import com.bfhd.account.BR;
import com.bfhd.account.R;
import com.bfhd.account.vm.AccountViewModel;
import com.bfhd.account.vo.FansVo;
import com.bfhd.circle.base.HivsBaseViewModel;
import com.bfhd.circle.base.OpenBaseListActivity;
import com.bfhd.circle.base.ViewEventResouce;
import com.bfhd.circle.base.adapter.HivsSampleAdapter;
import com.bfhd.circle.vo.bean.StaPersionDetail;
import com.docker.common.common.router.AppRouter;
import com.docker.common.common.utils.rxbus.RxBus;
import com.docker.common.common.utils.rxbus.RxEvent;
import com.docker.common.common.widget.empty.EmptyLayout;
import com.docker.common.common.widget.refresh.api.RefreshLayout;
import com.docker.common.common.widget.refresh.listener.OnRefreshListener;

import java.util.List;

import javax.inject.Inject;

import io.reactivex.disposables.Disposable;

/*
 * 粉丝列表
 **/
@Route(path = AppRouter.ACCOUNT_FANS_LISt)
public class AccounFansListActivity extends OpenBaseListActivity<AccountViewModel> {


    @Inject
    ViewModelProvider.Factory factory;

    private HivsSampleAdapter hivsSampleAdapter;
    private List<FansVo> fansVoList;
    private Disposable disposable;

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
        disposable = RxBus.getDefault().toObservable(RxEvent.class).subscribe(rxEvent -> {
            if (rxEvent.getT().equals("refresh_focus")) {
                mViewModel.getFansList();
            }
        });

        //refresh_focus

    }

    @Override
    public HivsBaseViewModel setViewmodel() {
        return null;
    }

    public void initView() {
        mViewModel.getFansList();
        hivsSampleAdapter = new HivsSampleAdapter(R.layout.account_item_fans, BR.item) {
        };
        mBinding.recyclerView.setAdapter(hivsSampleAdapter);
        hivsSampleAdapter.notifyDataSetChanged();


        mBinding.refresh.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(@NonNull RefreshLayout refreshLayout) {
                mViewModel.mPage = 1;
                mViewModel.getFansList();
            }
        });


        mBinding.empty.setOnretryListener(new EmptyLayout.OnretryListener() {
            @Override
            public void onretry() {
                mViewModel.mPage = 1;
                mViewModel.getFansList();
            }
        });

        hivsSampleAdapter.setOnchildViewClickListener(new int[]{R.id.ll_coutainer, R.id.tv_isFocus, R.id.tv_isFocusMe}, (childview, position) -> {
            int i = childview.getId();
            if (i == com.bfhd.account.R.id.tv_isFocus) {
                mViewModel.focusClick(fansVoList.get(position));
            }
            if (i == com.bfhd.account.R.id.tv_isFocusMe) {
                mViewModel.focusClick(fansVoList.get(position));
            }
            if (i == R.id.ll_coutainer) {
                FansVo fvo = fansVoList.get(position);
                StaPersionDetail msta = new StaPersionDetail();
                msta.uuid = fvo.getUuid();
                msta.uid = fvo.getMemberid();
                msta.name = fvo.getNickname();
//                CirclePersonDetailActivity.startMe(AccounFansListActivity.this, msta);
                ARouter.getInstance().build(AppRouter.CIRCLE_persion_detail).withSerializable("mStartParam", msta).navigation();
            }
        });

    }

    @Override
    public void OnVmEnentListner(ViewEventResouce viewEventResouce) {
        super.OnVmEnentListner(viewEventResouce);
        switch (viewEventResouce.eventType) {
            case 112:
                if (viewEventResouce.data != null && ((List<FansVo>) viewEventResouce.data).size() > 0) {
                    mBinding.empty.hide();
                    fansVoList = (List<FansVo>) viewEventResouce.data;
                    if (mViewModel.mPage == 1) {
                        hivsSampleAdapter.clear();
                    }
                    hivsSampleAdapter.getmObjects().addAll(fansVoList);
                    hivsSampleAdapter.notifyDataSetChanged();
                }
                if (hivsSampleAdapter.getmObjects().size() == 0) {
                    mBinding.empty.showNodata();
                }
                mBinding.refresh.finishRefresh();
                mBinding.refresh.finishLoadMore();
                break;
            case 113:
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
