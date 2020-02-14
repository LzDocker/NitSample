package com.bfhd.account.vo.tygs;

import android.databinding.Bindable;
import android.view.View;

import com.alibaba.android.arouter.launcher.ARouter;
import com.bfhd.account.BR;
import com.bfhd.account.R;
import com.bfhd.account.vo.MoneyBoxVov2;
import com.docker.common.common.router.AppRouter;
import com.docker.common.common.vo.card.BaseCardVo;

import static com.docker.common.common.router.AppRouter.ACCOUNT_MONEY_HAND;
import static com.docker.common.common.router.AppRouter.ACCOUNT_MONEY_HAND_V2;

public class AccountRewardHeadVo extends BaseCardVo {


    public AccountRewardHeadVo(int style, int position) {
        super(style, position);
        maxSupport = 3;
        mVmPath = "com.bfhd.account.vm.card.AccountRewardHeadCardViewModel";
    }

    @Override
    public int getItemLayout() {
        return R.layout.account_mine_reward_top;
    }

    @Override
    public void onItemClick(BaseCardVo item, View view) {
        if (view.getId() == R.id.tv_txmx) {
            ARouter.getInstance().build(AppRouter.ACCOUNT_MONEY_DETAIL).navigation();
        }
        if (view.getId() == R.id.tv_qbtx) {
            String txmon = "";
            if (((AccountRewardHeadVo) item).moneyBoxVov2 != null) {
                txmon = ((AccountRewardHeadVo) item).moneyBoxVov2.balance;
            }
            ARouter.getInstance().build(ACCOUNT_MONEY_HAND_V2).withString("Txmoney", txmon).navigation();
        }
    }

    public MoneyBoxVov2 moneyBoxVov2;

    @Bindable
    public MoneyBoxVov2 getMoneyBoxVov2() {
        return moneyBoxVov2;
    }

    public void setMoneyBoxVov2(MoneyBoxVov2 moneyBoxVov2) {
        this.moneyBoxVov2 = moneyBoxVov2;
        notifyPropertyChanged(BR.moneyBoxVov2);
    }
}
