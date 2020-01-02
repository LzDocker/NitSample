package com.bfhd.account.vo.card;

import android.view.View;

import com.alibaba.android.arouter.launcher.ARouter;
import com.bfhd.account.R;
import com.docker.common.common.model.OnItemClickListener;
import com.docker.common.common.router.AppRouter;
import com.docker.common.common.vo.card.BaseCardVo;

public class AccountIndexItemVo extends BaseCardVo<String> {

    public AccountIndexItemVo(int style, int position) {
        super(style, position);
        maxSupport = 4;
    }

    @Override
    public int getItemLayout() {
        int lay = R.layout.account_fragment_mine_index_item;
        switch (style) {
            case 0:
                lay = R.layout.account_fragment_mine_index_menu;
                break;
            case 1:
                lay = R.layout.account_fragment_mine_index_item;
                break;
            case 2:
                lay = R.layout.account_fragment_mine_index_item_tygs;
                break;
            case 3:
                lay = R.layout.account_fragment_mine_index_menu_tygs;
                break;
        }
        return lay;
    }

    @Override
    public void onItemClick(BaseCardVo item, View view) {

        if (view.getId() == R.id.tv_isent) {
        }
        if (view.getId() == R.id.tv_iattend) {//我关注的
            ARouter.getInstance().build(AppRouter.ACCOUNT_ATTEN_LISt).withString("type", "attention").navigation();

        }

        if (view.getId() == R.id.tv_fans) {//我的粉丝
            ARouter.getInstance().build(AppRouter.ACCOUNT_ATTEN_LISt).withString("type", "fans").navigation();

        }
        if (view.getId() == R.id.tv_mine_page) {
            ARouter.getInstance().build(AppRouter.CIRCLE_person_info).navigation();
        }
        if (view.getId() == R.id.tv_mine_branch) {
            ARouter.getInstance().build(AppRouter.ACCOUNT_BRANCH_LIST).navigation();
        }

        if (view.getId() == R.id.tv_activie_reg) {
            ARouter.getInstance().build(AppRouter.ACCOUNT_ACT_REGIST_LIST).navigation();
        }
        if (view.getId() == R.id.tv_active_manager) {
            ARouter.getInstance().build(AppRouter.ACCOUNT_ACT_MANAGER_LIST).navigation();
        }
        if (view.getId() == R.id.tv_order) {
            ARouter.getInstance().build(AppRouter.ACCOUNT_ORDER_LIST).navigation();
        }

        if (view.getId() == R.id.tv_ipay) {
        }
        if (view.getId() == R.id.tv_icollec) {
        }
        if (view.getId() == R.id.tv_iattend) {
        }
        if (view.getId() == R.id.tv_imoney_box) {
        }
        if (view.getId() == R.id.tv_ihome) {
        }
        if (view.getId() == R.id.tv_point) {
        }
        if (view.getId() == R.id.tv_help) {
        }
    }

}
