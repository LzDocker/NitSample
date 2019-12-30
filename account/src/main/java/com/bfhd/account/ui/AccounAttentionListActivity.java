package com.bfhd.account.ui;

import android.arch.lifecycle.ViewModelProvider;
import android.arch.lifecycle.ViewModelProviders;
import android.os.Bundle;
import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.android.arouter.launcher.ARouter;
import com.bfhd.account.R;
import com.bfhd.account.BR;
import com.bfhd.account.databinding.AccountActivityAttentionListBinding;
import com.bfhd.account.vm.AccountViewModel;
import com.bfhd.account.vo.FansVo;
import com.bfhd.circle.base.HivsBaseActivity;
import com.bfhd.circle.base.ViewEventResouce;
import com.bfhd.circle.base.adapter.HivsSampleAdapter;
import com.bfhd.circle.vo.bean.StaPersionDetail;
import com.docker.common.common.router.AppRouter;
import java.util.Collection;

import javax.inject.Inject;

/*
 * 关注列表
 **/

//@Route(path = AppRouter.ACCOUNT_ATTEN_LISt)
public class AccounAttentionListActivity extends HivsBaseActivity<AccountViewModel, AccountActivityAttentionListBinding> {


    @Inject
    ViewModelProvider.Factory factory;
    private HivsSampleAdapter mAdapter;


    @Override
    public AccountViewModel getmViewModel() {
        return ViewModelProviders.of(this, factory).get(AccountViewModel.class);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.account_activity_attention_list;
    }

    @Override
    protected void inject() {
        super.inject();
        ARouter.getInstance().inject(this);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public void initView() {
        mViewModel.fechAttentionList();
        mAdapter = new HivsSampleAdapter(R.layout.account_item_attention, BR.item) {
        };
        mAdapter.setOnchildViewClickListener(new int[]{R.id.ll_coutainer, R.id.tv_Focu, R.id.tv_alerFocus}, (childview, position) -> {
            int i = childview.getId();
            if (i == com.bfhd.account.R.id.tv_Focu) {
                mViewModel.focusClick((FansVo) mAdapter.getItem(position));
            }
            if (i == com.bfhd.account.R.id.tv_alerFocus) {
                mViewModel.focusClick((FansVo) mAdapter.getItem(position));
            }
            if (i == R.id.ll_coutainer) {
                FansVo fvo = (FansVo) mAdapter.getItem(position);
                StaPersionDetail msta = new StaPersionDetail();
                msta.uuid = fvo.getUuid();
                msta.uid = fvo.getMemberid();
                msta.name = fvo.getNickname();
//                CirclePersonDetailActivity.startMe(AccounTygsAttentionListActivity.this, msta);
                ARouter.getInstance().build(AppRouter.CIRCLE_persion_detail).withSerializable("mStartParam", msta).navigation();
            }
        });
        mBinding.recyclerView.setAdapter(mAdapter);

        mBinding.refresh.setOnRefreshListener(refreshLayout -> {
            mViewModel.mPage = 1;
            mViewModel.fechAttentionList();
        });
        mBinding.refresh.setEnableLoadMore(false);
    }

    @Override
    public void OnVmEnentListner(ViewEventResouce viewEventResouce) {
        super.OnVmEnentListner(viewEventResouce);
        switch (viewEventResouce.eventType) {
            case 104:
                mBinding.refresh.finishRefresh();
                if (viewEventResouce.data == null || ((Collection) viewEventResouce.data).size() == 0) {
                    mBinding.empty.showNodata();
                    return;
                }
                mBinding.empty.hide();
                mAdapter.getmObjects().clear();
                mAdapter.getmObjects().addAll((Collection) viewEventResouce.data);
                mAdapter.notifyDataSetChanged();
                break;
            case 105:
                mBinding.empty.showError();
                break;
        }
    }
}
