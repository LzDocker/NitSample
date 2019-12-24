package com.docker.message.ui.index;

import android.arch.lifecycle.ViewModelProviders;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.View;

import com.alibaba.android.arouter.facade.annotation.Autowired;
import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.android.arouter.launcher.ARouter;
import com.dcbfhd.utilcode.utils.FragmentUtils;
import com.docker.common.common.config.Constant;
import com.docker.common.common.model.CommonListOptions;
import com.docker.common.common.router.AppRouter;
import com.docker.common.common.ui.base.NitCommonFragment;
import com.docker.common.common.ui.base.NitCommonListFragment;
import com.docker.message.R;
import com.docker.message.databinding.MessageImFragmentBinding;
import com.docker.message.vm.MessageViewModel;
import com.netease.nim.uikit.api.NimUIKit;
import com.netease.nim.uikit.api.model.session.SessionEventListener;
import com.netease.nim.uikit.business.recent.RecentContactsFragment;
import com.netease.nimlib.sdk.msg.model.IMMessage;

@Route(path = AppRouter.MESSAGELIST_IM)
public class MessageImFragment extends NitCommonFragment<MessageViewModel, MessageImFragmentBinding> {

    public static MessageImFragment newInstance() {
        return new MessageImFragment();
    }

    @Override
    protected int getLayoutId() {
        return R.layout.message_im_fragment;
    }

    @Override
    public MessageViewModel getViewModel() {
        return ViewModelProviders.of(this, factory).get(MessageViewModel.class);
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceStates) {
        super.onCreate(savedInstanceStates);
        ARouter.getInstance().inject(this);

    }

    @Override
    protected void initView(View var1) {
        FragmentUtils.add(getChildFragmentManager(),
                (Fragment) ARouter.getInstance()
                        .build(AppRouter.MESSAGELIST)
                        .withInt("style", 0).navigation(),
                R.id.message_frame);

        RecentContactsFragment sessionListFragment = new RecentContactsFragment();
        SessionEventListener listener = new SessionEventListener() {
            @Override
            public void onAvatarClicked(Context context, IMMessage message) {
            }

            @Override
            public void onAvatarLongClicked(Context context, IMMessage message) {
            }

            @Override
            public void onAckMsgClicked(Context context, IMMessage message) {
            }
        };
        NimUIKit.setSessionListener(listener);
        FragmentUtils.add(getChildFragmentManager(), sessionListFragment, R.id.message_frame_list);
    }

    @Override
    public void onVisible() {
        super.onVisible();

    }

    @Override
    public void initImmersionBar() {

    }
}
