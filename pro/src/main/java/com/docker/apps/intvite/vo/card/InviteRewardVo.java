package com.docker.apps.intvite.vo.card;

import android.databinding.ObservableField;
import android.view.View;

import com.alibaba.android.arouter.launcher.ARouter;
import com.docker.apps.R;
import com.docker.common.common.router.AppRouter;
import com.docker.common.common.vo.card.BaseCardVo;

public class InviteRewardVo extends BaseCardVo {


    public InviteRewardVo(int style, int position) {
        super(style, position);
    }

    @Override
    public void onItemClick(BaseCardVo item, View view) {
        if (view.getId() == R.id.ll_reward) {
            ARouter.getInstance().build(AppRouter.ACCOUNT_reward).navigation();
            return;
        }
    }

    @Override
    public int getItemLayout() {
        return R.layout.pro_invite_reward_card;
    }


    public ObservableField<String> pointsReward = new ObservableField<>();
    public ObservableField<String> incomeReward = new ObservableField<>();
    public ObservableField<String> inviteNum = new ObservableField<>();
}
