package com.bfhd.account.ui;

import android.arch.lifecycle.ViewModelProvider;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.util.Log;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.android.arouter.launcher.ARouter;
import com.bfhd.account.R;
import com.bfhd.account.BR;
import com.bfhd.account.databinding.AccountActivityMessageListBinding;
import com.bfhd.account.vm.AccountViewModel;
import com.bfhd.account.vo.MessageListVo;
import com.bfhd.circle.base.HivsBaseActivity;
import com.bfhd.circle.base.ViewEventResouce;
import com.docker.common.common.router.AppRouter;
import com.docker.core.util.adapter.SimpleCommonRecyclerAdapter;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

/*
 * 消息列表
 **/
@Route(path = AppRouter.ACCOUNT_MESSAGE_LIST)
public class AccounMessageListtActivity extends HivsBaseActivity<AccountViewModel, AccountActivityMessageListBinding> {


    private List<MessageListVo> messageVoList;
    @Inject
    ViewModelProvider.Factory factory;
    private SimpleCommonRecyclerAdapter simpleCommonRecyclerAdapter;


    @Override
    public AccountViewModel getmViewModel() {
        return ViewModelProviders.of(this, factory).get(AccountViewModel.class);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.account_activity_message_list;
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
        mToolbar.setTitle("消息中心");

        mToolbar.setTvRight("全部已读", v -> {
            mViewModel.readAllMsg();
        });

        mBinding.refresh.setEnableLoadMore(false);
        mBinding.refresh.setOnRefreshListener(refreshLayout -> mViewModel.getMessageList());
        mBinding.empty.setOnretryListener(() -> {
            mViewModel.getMessageList();
        });
        simpleCommonRecyclerAdapter = new SimpleCommonRecyclerAdapter(R.layout.account_item_message, BR.item);
        mBinding.recyclerView.setLayoutManager(new LinearLayoutManager(this));
        mBinding.recyclerView.setAdapter(simpleCommonRecyclerAdapter);


        //
        simpleCommonRecyclerAdapter.setOnItemClickListener((v, p) -> {
            Log.i("myTag", "initView: " + messageVoList.size());
            switch (messageVoList.get(p).getType()) {
                case "1":  // 系统通知
                    simpleCommonRecyclerAdapter.getmObjects().clear();
                    Intent intent = new Intent(AccounMessageListtActivity.this, AccounSystemMessageActivity.class);
                    startActivity(intent);
                    break;
                case "2":  // 评论列表
                    simpleCommonRecyclerAdapter.getmObjects().clear();
                    Intent intent2 = new Intent(AccounMessageListtActivity.this, AccounCommentListActivity.class);
                    startActivity(intent2);

                    break;
                case "3": // 点赞列表
                    simpleCommonRecyclerAdapter.getmObjects().clear();
                    Intent intent3 = new Intent(AccounMessageListtActivity.this, AccounParseListActivity.class);
                    startActivity(intent3);

                    break;
                case "4": // 收藏
                    simpleCommonRecyclerAdapter.getmObjects().clear();
                    Intent intent4 = new Intent(AccounMessageListtActivity.this, AccountCollectionListActivity.class);
                    startActivity(intent4);
                    break;
                case "5": // 关注
                    simpleCommonRecyclerAdapter.getmObjects().clear();
                    Intent intent5 = new Intent(AccounMessageListtActivity.this, AccountCollectionListActivity.class);
                    intent5.putExtra("flag", "atten");
                    startActivity(intent5);
                    break;
            }
        });
    }


    @Override
    protected void onResume() {
        super.onResume();
        if (simpleCommonRecyclerAdapter.getmObjects().size() == 0) {
            mViewModel.getMessageList();
        }

    }

    @Override
    public void OnVmEnentListner(ViewEventResouce viewEventResouce) {
        super.OnVmEnentListner(viewEventResouce);

        switch (viewEventResouce.eventType) {
            case 512:
                mBinding.refresh.finishRefresh();
                mBinding.refresh.finishLoadMore();
                mBinding.empty.hide();
                mBinding.refresh.finishRefresh();
                if (viewEventResouce.data != null) {
                    messageVoList = (ArrayList) viewEventResouce.data;
                } else {
//                    mBinding.empty.showNoData();
                    messageVoList = new ArrayList<>();
                    for (int i = 0; i < 5; i++) {
                        MessageListVo messageListVo = new MessageListVo();
                        messageListVo.setType(i + 1 + "");
                        messageListVo.setContent("暂无数据");
                        messageVoList.add(messageListVo);
                    }
                }
                for (int i = 0; i < messageVoList.size(); i++) {
                    MessageListVo vo = messageVoList.get(i);
                         /*  MessageVo sysMessage = new MessageVo(1, R.mipmap.message_tongzhi_icon, "系统通知");
                            MessageVo commmentMessage = new MessageVo(2, R.mipmap.account_pinglun_icon, "评论");
                            MessageVo praiseMessage = new MessageVo(3, R.mipmap.account_dianzan_icon, "点赞");
                            MessageVo collectionMessage = new MessageVo(4, R.mipmap.account_shoucangg_icon, "收藏");
                            MessageVo attentionMessage = new MessageVo(5, R.mipmap.account_guanzhu_icon, "关注");
*/
                    switch (vo.getType()) {
                        case "1":
                            vo.setIcon(R.mipmap.account_tongzhi_icon);
                            vo.setTitle("系统通知");
                            break;
                        case "2":
                            vo.setIcon(R.mipmap.account_pinglun_icon);
                            vo.setTitle("评论");
                            break;
                        case "3":
                            vo.setIcon(R.mipmap.account_dianzan_icon);
                            vo.setTitle("点赞");
                            break;
                        case "4":
                            vo.setIcon(R.mipmap.account_shoucangg_icon);
                            vo.setTitle("收藏");
                            break;
                        case "5":
                            vo.setIcon(R.mipmap.account_guanzhu_icon);
                            vo.setTitle("关注");
                            break;
                    }
                }

                simpleCommonRecyclerAdapter.clear();
                simpleCommonRecyclerAdapter.getmObjects().addAll(messageVoList);
                simpleCommonRecyclerAdapter.notifyDataSetChanged();
                break;
            case 513:
                mBinding.refresh.finishRefresh();
                mBinding.refresh.finishLoadMore();
                mBinding.empty.showError();
                break;

            case 518:
                mViewModel.getMessageList();
                break;
        }

    }
}
