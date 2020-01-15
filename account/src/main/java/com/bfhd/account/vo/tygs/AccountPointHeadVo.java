package com.bfhd.account.vo.tygs;

import android.databinding.Bindable;
import android.view.View;

import com.alibaba.android.arouter.launcher.ARouter;
import com.bfhd.account.BR;
import com.bfhd.account.R;
import com.bfhd.account.vo.MyInfoVo;
import com.docker.common.common.router.AppRouter;
import com.docker.common.common.vo.card.BaseCardVo;

import static com.bfhd.account.utils.AccountConstant.ChenHHR;
import static com.bfhd.account.utils.AccountConstant.JFrule;

public class AccountPointHeadVo extends BaseCardVo<MyInfoVo> {

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

    public AccountPointHeadVo(int style, int position) {
        super(style, position);


    }

    @Override
    public int getItemLayout() {
        return R.layout.account_mine_point_head;
    }

    @Override
    public void onItemClick(BaseCardVo item, View view) {
        if (view.getId() == R.id.tv_rule) {//积分规则
            ARouter.getInstance().build(AppRouter.COMMONH5).withString("weburl", JFrule).withString("title", "积分规则").navigation();
        }
    }

}
