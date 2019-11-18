package com.docker.message.ui.index;
import android.arch.lifecycle.ViewModelProviders;
import com.alibaba.android.arouter.facade.annotation.Route;
import com.docker.common.common.command.NitContainerCommand;
import com.docker.common.common.router.AppRouter;
import com.docker.common.common.ui.base.NitCommonListActivity;
import com.docker.message.vm.MessageViewModel;

@Route(path = AppRouter.MESSAGELISTACT)
public class MessageListActivity extends NitCommonListActivity<MessageViewModel> {

    @Override
    public void initView() {
        mViewModel.setServerType(1);
    }

    @Override
    public void initObserver() {

    }

    @Override
    public NitContainerCommand providerNitContainerCommand(int flag) {
        return null;
    }

    @Override
    public MessageViewModel getmViewModel() {
        return ViewModelProviders.of(this, factory).get(MessageViewModel.class);
    }

}
