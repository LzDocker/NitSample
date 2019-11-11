package com.docker.message.ui.index;

import android.arch.lifecycle.ViewModelProviders;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.View;

import com.alibaba.android.arouter.facade.annotation.Autowired;
import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.android.arouter.launcher.ARouter;
import com.dcbfhd.utilcode.utils.FragmentUtils;
import com.docker.common.common.command.NitContainerCommand;
import com.docker.common.common.router.AppRouter;
import com.docker.common.common.ui.base.NitCommonActivity;
import com.docker.message.R;
import com.docker.message.databinding.MessageActivityBinding;
import com.docker.message.vm.MessageViewModel;
import com.docker.message.vm.SampleListViewModel;
import com.netease.nim.uikit.api.NimUIKit;
import com.netease.nim.uikit.api.model.session.SessionEventListener;
import com.netease.nim.uikit.business.recent.RecentContactsFragment;
import com.netease.nimlib.sdk.msg.model.IMMessage;

@Route(path = AppRouter.MESSAGMAIN)
public class MessageActivity extends NitCommonActivity<MessageViewModel, MessageActivityBinding> {

    @Autowired
    int style;

    @Override
    protected int getLayoutId() {
        return R.layout.message_activity;
    }

    @Override
    public MessageViewModel getmViewModel() {
        return ViewModelProviders.of(this, factory).get(MessageViewModel.class);
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        switch (style) {
            case 0:
                mToolbar.hide();
//                mBinding.messageFrameList.setVisibility(View.GONE);
                FragmentUtils.add(getSupportFragmentManager(),
                        (Fragment) ARouter.getInstance()
                                .build(AppRouter.MESSAGELIST)
                                .withInt("style", 0).navigation(),
                        R.id.message_frame);
//                RecentContactsFragment sessionListFragment = new RecentContactsFragment();
//                SessionEventListener listener = new SessionEventListener() {
//                    @Override
//                    public void onAvatarClicked(Context context, IMMessage message) {
//                    }
//
//                    @Override
//                    public void onAvatarLongClicked(Context context, IMMessage message) {
//                    }
//
//                    @Override
//                    public void onAckMsgClicked(Context context, IMMessage message) {
//                    }
//                };
//                NimUIKit.setSessionListener(listener);
//                FragmentUtils.add(getSupportFragmentManager(), sessionListFragment, R.id.message_frame_list);
                break;
            case 1:
                mToolbar.setTitle("消息中心");
                mToolbar.setTvRight("全部已读", v -> {
                    mViewModel.readAllMsg();
                });
                mBinding.messageCard.setVisibility(View.GONE);
                FragmentUtils.add(getSupportFragmentManager(),
                        (Fragment) ARouter.getInstance().build(AppRouter.MESSAGELIST).withInt("style", 1).navigation(),
                        R.id.message_frame_list);
                break;
        }
    }

    @Override
    public void initView() {

    }

    @Override
    public void initObserver() {
        mViewModel.mRearAllLiveData.observe(this, s -> {
        });
    }

    @Override
    public void initRouter() {
        ARouter.getInstance().inject(this);
    }

    @Override
    public NitContainerCommand providerNitContainerCommand(int flag) {
        return (NitContainerCommand) () -> (SampleListViewModel.class);
    }

}
