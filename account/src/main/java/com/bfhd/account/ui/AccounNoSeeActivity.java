package com.bfhd.account.ui;

import android.arch.lifecycle.ViewModelProvider;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.ViewGroup;

import com.alibaba.android.arouter.launcher.ARouter;
import com.bfhd.account.R;
import com.bfhd.account.databinding.AccountActivityNoSeeBinding;
import com.bfhd.account.vm.AccountViewModel;
import com.bfhd.account.vo.NoSeeVo;
import com.bfhd.circle.base.HivsBaseActivity;
import com.bfhd.circle.base.ViewEventResouce;
import com.bfhd.circle.base.adapter.HivsSampleAdapter;
import com.dcbfhd.utilcode.utils.ToastUtils;
import com.docker.core.BR;
import com.yanzhenjie.recyclerview.OnItemMenuClickListener;
import com.yanzhenjie.recyclerview.SwipeMenu;
import com.yanzhenjie.recyclerview.SwipeMenuBridge;
import com.yanzhenjie.recyclerview.SwipeMenuCreator;
import com.yanzhenjie.recyclerview.SwipeMenuItem;

import java.util.List;

import javax.inject.Inject;


/*
 *不看他的动态列表
 **/
public class AccounNoSeeActivity extends HivsBaseActivity<AccountViewModel, AccountActivityNoSeeBinding> {


    @Inject
    ViewModelProvider.Factory factory;
    private HivsSampleAdapter hivsSampleAdapter;
    private List<NoSeeVo> noSeeVoList;
    private int pos;
    private String name;


    @Override
    public AccountViewModel getmViewModel() {
        return ViewModelProviders.of(this, factory).get(AccountViewModel.class);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.account_activity_no_see;
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
        mToolbar.setTitle("不看他(她)的动态");
        mViewModel.getPullBlackList();
        hivsSampleAdapter = new HivsSampleAdapter(R.layout.account_no_see_item, BR.item) {
        };

        hivsSampleAdapter.setOnchildViewClickListener(new int[]{}, (childview, position) -> {

        });

        mBinding.refresh.setOnRefreshListener(refreshLayout -> {
            mViewModel.mPage = 1;
            mViewModel.getPullBlackList();
        });

        mBinding.refresh.setOnLoadMoreListener(refreshLayout -> {
            mViewModel.getPullBlackList();
        });

        mBinding.empty.setOnretryListener(() -> {
            mViewModel.mPage = 1;
            mViewModel.getPullBlackList();
        });


// 创建菜单：
        SwipeMenuCreator mSwipeMenuCreator = new SwipeMenuCreator() {
            @Override
            public void onCreateMenu(SwipeMenu leftMenu, SwipeMenu rightMenu, int position) {
                int width = 180;
                int height = ViewGroup.LayoutParams.MATCH_PARENT;
                SwipeMenuItem deleteItem = new SwipeMenuItem(AccounNoSeeActivity.this); // 各种文字和图标属性设置。
                deleteItem.setText("删除").setBackgroundColor(getResources().getColor(R.color.circle_red)).setHeight(height).setWidth(width).setTextColor(Color.WHITE);
                rightMenu.addMenuItem(deleteItem); // 在Item右侧添加一个菜单。
                // 注意：哪边不想要菜单，那么不要添加即可。
            }
        };
        // 设置监听器。
        mBinding.recycle.setSwipeMenuCreator(mSwipeMenuCreator);

        OnItemMenuClickListener mItemMenuClickListener = new OnItemMenuClickListener() {
            @Override
            public void onItemClick(SwipeMenuBridge menuBridge, int position) {
                // 任何操作必须先关闭菜单，否则可能出现Item菜单打开状态错乱。
                menuBridge.closeMenu();
                int direction = menuBridge.getDirection(); // 左侧还是右侧菜单。
                int adapterPosition = menuBridge.getPosition(); // RecyclerView的Item的position。
                int menuPosition = menuBridge.getPosition(); // 菜单在RecyclerView的Item中的Position。
                NoSeeVo noSeeVo = (NoSeeVo) hivsSampleAdapter.getmObjects().get(position);
                pos = position;
                name = noSeeVo.getFullName();
                mViewModel.pullBlack(noSeeVo.getId());
            }
        };
        // 菜单点击监听。
        mBinding.recycle.setOnItemMenuClickListener(mItemMenuClickListener);
        mBinding.recycle.setAdapter(hivsSampleAdapter);
        hivsSampleAdapter.notifyDataSetChanged();

    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            if (requestCode == 10086 && resultCode == RESULT_OK) {

            }
        }
    }

    @Override
    public void OnVmEnentListner(ViewEventResouce viewEventResouce) {
        super.OnVmEnentListner(viewEventResouce);
        switch (viewEventResouce.eventType) {
            case 430:
                hidWaitDialog();
                if (viewEventResouce.data != null) {
                    mBinding.empty.hide();
                    noSeeVoList = (List<NoSeeVo>) viewEventResouce.data;
                    if (noSeeVoList.size() > 0) {
                        if (mViewModel.mPage == 1) {
                            hivsSampleAdapter.clear();
                        }
                        hivsSampleAdapter.getmObjects().addAll(noSeeVoList);
                        hivsSampleAdapter.notifyDataSetChanged();
                    } else {
                        if (mViewModel.mPage == 1) {
                            hivsSampleAdapter.clear();
                        }
                    }
                }
                if (hivsSampleAdapter.getmObjects().size() == 0) {
                    mBinding.empty.showNodata();
                }
                mBinding.refresh.finishRefresh();
                mBinding.refresh.finishLoadMore();
                break;
            case 431:
                mBinding.empty.showError();
                mBinding.refresh.finishRefresh();
                mBinding.refresh.finishLoadMore();
                break;
            case 432:
                hivsSampleAdapter.remove(pos);
                hivsSampleAdapter.notifyDataSetChanged();
                ToastUtils.showShort(name + "解除拉黑成功");
                break;
            case 433:

                break;

        }
    }
}
