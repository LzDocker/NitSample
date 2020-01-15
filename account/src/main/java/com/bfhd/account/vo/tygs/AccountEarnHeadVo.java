package com.bfhd.account.vo.tygs;

import android.databinding.Bindable;
import android.view.View;

import com.alibaba.android.arouter.launcher.ARouter;
import com.bfhd.account.BR;
import com.bfhd.account.R;
import com.bfhd.account.vo.MyInfoVo;
import com.dcbfhd.utilcode.utils.ToastUtils;
import com.docker.common.common.router.AppRouter;
import com.docker.common.common.vo.card.BaseCardVo;

import static com.bfhd.account.utils.AccountConstant.ChenHHR;
import static com.bfhd.account.utils.AccountConstant.SYrule;

public class AccountEarnHeadVo extends BaseCardVo<MyInfoVo> {

    private String point;

    @Bindable
    public String getPoint() {
        return point;
    }

    @Bindable
    public void setPoint(String point) {
        this.point = point;
        notifyPropertyChanged(BR.point);
    }

    public AccountEarnHeadVo(int style, int position) {
        super(style, position);


    }

    @Override
    public int getItemLayout() {
        return R.layout.account_mine_earn_head;
    }

    @Override
    public void onItemClick(BaseCardVo item, View view) {
        if (view.getId() == R.id.tv_rule) {//收益规则
            ARouter.getInstance().build(AppRouter.COMMONH5).withString("weburl", SYrule).withString("title", "收益规则").navigation();
        }
        if (view.getId() == R.id.lin_tx) {//提现
            try {
                float money = Float.parseFloat(((AccountEarnHeadVo) item).getPoint());
                if (money > 10) {
                    ARouter.getInstance().build(AppRouter.ACCOUNT_MONEY_HAND).navigation();
                } else {
                    ToastUtils.showShort("余额不足以提现");
                }
            } catch (Exception e) {
                ToastUtils.showShort("请重试");
            }
        }
    }

}
