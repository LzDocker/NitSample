package com.bfhd.account.ui;

import android.arch.lifecycle.ViewModelProvider;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.util.Log;
import android.view.View;
import com.alibaba.android.arouter.launcher.ARouter;
import com.bfhd.account.R;
import com.bfhd.account.adapter.SwipeMenuAdapter;
import com.bfhd.account.databinding.AccountActivityOnekeySosBinding;
import com.bfhd.account.vm.AccountViewModel;
import com.bfhd.account.vo.HelpUserVo;
import com.bfhd.account.vo.ItemVo;
import com.bfhd.circle.base.HivsBaseActivity;
import com.bfhd.circle.base.ViewEventResouce;
import com.docker.common.common.command.ReplyCommandParam;
import com.docker.common.common.utils.rxbus.RxBus;
import com.docker.common.common.utils.rxbus.RxEvent;
import com.docker.common.common.widget.refresh.api.RefreshLayout;
import com.docker.common.common.widget.refresh.listener.OnLoadMoreListener;
import com.docker.common.common.widget.refresh.listener.OnRefreshListener;
import com.docker.core.util.adapter.SimpleCommonRecyclerAdapter;
import com.github.jdsjlzx.ItemDecoration.DividerDecoration;
import com.github.jdsjlzx.recyclerview.LRecyclerViewAdapter;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

/*
 * 一键呼救设置联系人列表
 **/
public class AccountOneKeySosActivity extends HivsBaseActivity<AccountViewModel, AccountActivityOnekeySosBinding> {


    @Inject
    ViewModelProvider.Factory factory;
    private SimpleCommonRecyclerAdapter<String> simpleCommonRecyclerAdapter;
    private SwipeMenuAdapter swipeMenuAdapter;
    private LRecyclerViewAdapter mLRecyclerViewAdapter;
    private int postion;
    private DividerDecoration divider;


    @Override
    public AccountViewModel getmViewModel() {
        return ViewModelProviders.of(this, factory).get(AccountViewModel.class);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.account_activity_onekey_sos;
    }

    @Override
    protected void inject() {
        super.inject();
        ARouter.getInstance().inject(this);
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        mBinding.recycle.setPullRefreshEnabled(false);

//        mBinding.recycle.setLoadMoreEnabled(false);
        mBinding.refresh.setEnableLoadMore(false);
        mBinding.recycle.setLayoutManager(new LinearLayoutManager(this));
        mViewModel.helpUserList();

    }

    @Override
    public void initView() {
        mToolbar.setTitle("一键呼救");
        mToolbar.setTvRight("添加", v -> {
            Intent intent = new Intent(AccountOneKeySosActivity.this, AccountAddConstActivity.class);
            intent.putExtra("data", "0");
            startActivityForResult(intent, 101);
        });

        // 发布求救(经纬度传参有问题)
        mBinding.publishHelp.setOnClickListener(v -> {
//            mViewModel.publishHelp();
            RxBus.getDefault().post(new RxEvent<>("sos", ""));
            finish();
        });

        mBinding.refresh.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(@NonNull RefreshLayout refreshLayout) {
                mViewModel.helpUserList();
            }
        });

        mBinding.refresh.setOnLoadMoreListener(new OnLoadMoreListener() {
            @Override
            public void onLoadMore(@NonNull RefreshLayout refreshLayout) {
//                mViewModel.helpUserList();
            }
        });
        swipeMenuAdapter = new SwipeMenuAdapter(AccountOneKeySosActivity.this, new ReplyCommandParam() {
            @Override
            public void exectue(Object o) {
                HelpUserVo vo = (HelpUserVo) o;
                Intent intent = new Intent(AccountOneKeySosActivity.this, AccountAddConstActivity.class);
                intent.putExtra("data", "1");
                intent.putExtra("id", vo.getId());
                intent.putExtra("concat_name", vo.getConcat_name());
                intent.putExtra("concat_phone", vo.getConcat_phone());
                intent.putExtra("area_code", vo.getConcat_area_code());
                intent.putExtra("remark", vo.getRemark());
                startActivityForResult(intent, 102);
            }
        });
        swipeMenuAdapter.setOnDelListener(new SwipeMenuAdapter.onSwipeListener() {
            @Override
            public void onDel(int pos) {
//                Toast.makeText(AccountOneKeySosActivity.this, "删除:" + pos, Toast.LENGTH_SHORT).show();
                postion = pos;
                //RecyclerView关于notifyItemRemoved的那点小事
                mViewModel.deleteHelpUser(swipeMenuAdapter.getDataList().get(pos).getId());

            }

            @Override
            public void onTop(int pos) {//置顶功能

            }
        });


    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
            case 101:
                mViewModel.helpUserList();
                break;
            case 102:
                mViewModel.helpUserList();
                break;
        }
    }

    @Override
    public void OnVmEnentListner(ViewEventResouce viewEventResouce) {
        super.OnVmEnentListner(viewEventResouce);
        Log.i("myTag", "OnVmEnentListner: " + viewEventResouce.eventType);
        switch (viewEventResouce.eventType) {
            case 524:
                if (viewEventResouce.data != null) {
                    List<HelpUserVo> helpUserVos = (List<HelpUserVo>) viewEventResouce.data;
                    if (helpUserVos.size() == 0) {
                        mBinding.recycle.setVisibility(View.GONE);
                    } else {
                        mBinding.recycle.setVisibility(View.VISIBLE);
                        swipeMenuAdapter.setDataList(helpUserVos);
                        mLRecyclerViewAdapter = new LRecyclerViewAdapter(swipeMenuAdapter);
                        mBinding.recycle.setAdapter(mLRecyclerViewAdapter);
                        mBinding.refresh.finishRefresh();
                    }

                } else {
                    mBinding.recycle.setVisibility(View.GONE);
                    mBinding.refresh.finishRefresh();
                }
                break;
            case 525:
                mBinding.recycle.setVisibility(View.GONE);
                mBinding.refresh.finishRefresh();
                break;
            case 526:
                swipeMenuAdapter.getDataList().remove(postion);
                swipeMenuAdapter.notifyItemRemoved(postion);//推荐用这个
                if (postion != (swipeMenuAdapter.getDataList().size())) {
                    // 如果移除的是最后一个，忽略 注意：这里的mDataAdapter.getDataList()不需要-1，因为上面已经-1了
                    swipeMenuAdapter.notifyItemRangeChanged(postion, swipeMenuAdapter.getDataList().size() - postion);
                }//且如果想让侧滑菜单同时关闭，需要同时调用 ((CstSwipeDelMenu) holder.itemView).quickClose();
                break;
            case 527:

                break;
        }
    }
}
