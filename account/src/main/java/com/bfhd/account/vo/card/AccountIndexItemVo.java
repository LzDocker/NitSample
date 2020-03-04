package com.bfhd.account.vo.card;

import android.view.View;

import com.alibaba.android.arouter.launcher.ARouter;
import com.bfhd.account.R;
import com.bfhd.account.utils.AccountConstant;
import com.docker.common.common.model.OnItemClickListener;
import com.docker.common.common.router.AppRouter;
import com.docker.common.common.utils.cache.CacheUtils;
import com.docker.common.common.vo.card.BaseCardVo;

import static com.bfhd.account.utils.AccountConstant.ChenHHR;
import static com.bfhd.account.utils.AccountConstant.InfoDesc;
import static com.bfhd.account.utils.AccountConstant.JfenZhidu;
import static com.bfhd.account.utils.AccountConstant.Tywenhua;

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

        if (view.getId() == R.id.account_lin_tywh) {
            ARouter.getInstance().build(AppRouter.COMMONH5).withString("weburl", Tywenhua).withString("title", "桃源文化").navigation();
            return;
        }
        if (view.getId() == R.id.account_tv_gsjs) {
            ARouter.getInstance().build(AppRouter.COMMONH5).withString("weburl", InfoDesc).withString("title", "公社介绍").navigation();
            return;
        }
        if (view.getId() == R.id.account_tv_jfzd) {
            ARouter.getInstance().build(AppRouter.COMMONH5).withString("weburl", JfenZhidu).withString("title", "积分制度").navigation();
            return;
        }
        if (view.getId() == R.id.account_tv_hhr) { //成为合伙人
            ARouter.getInstance().build(AppRouter.COMMONH5).withString("weburl", ChenHHR).withString("title", "成为合伙人").navigation();
            return;
        }

        if (view.getId() == R.id.account_tv_fwzc) {
            ARouter.getInstance().build(AppRouter.COMMONH5).withString("weburl", AccountConstant.CustomerService).withString("title", "服务支持").navigation();
            return;
        }

        if (CacheUtils.getUser() == null) {
            ARouter.getInstance().build(AppRouter.ACCOUNT_LOGIN).withBoolean("isFoceLogin", true).navigation();
            return;
        }
        if (view.getId() == R.id.account_tv_join_friend) { // 邀请好友
            ARouter.getInstance().build(AppRouter.INVITE_INDEX).navigation();
        }

        if (view.getId() == R.id.tv_isent) {
        }
        if (view.getId() == R.id.tv_iattend) {//我关注的
            ARouter.getInstance().build(AppRouter.ACCOUNT_ATTEN_LISt).withString("type", "attention").navigation();

        }

        if (view.getId() == R.id.tv_fans) {//我的粉丝
            ARouter.getInstance().build(AppRouter.ACCOUNT_FANS_LISt).withString("type", "fans").navigation();

        }
        if (view.getId() == R.id.tv_mine_page) {
            ARouter.getInstance().build(AppRouter.CIRCLE_person_info).navigation();
        }
        if (view.getId() == R.id.tv_mine_branch) {
            ARouter.getInstance().build(AppRouter.ACCOUNT_BRANCH_LIST).navigation();
        }

        if (view.getId() == R.id.tv_activie_reg) {
            ARouter.getInstance().build(AppRouter.ACTIVE_REGIST_LIST).navigation();
        }
        if (view.getId() == R.id.tv_active_manager) {
            ARouter.getInstance().build(AppRouter.ACTIVE_MANAGER_LIST).navigation();
        }
        if (view.getId() == R.id.tv_order) {
            ARouter.getInstance().build(AppRouter.ORDER_LIST).navigation();
        }

        if (view.getId() == R.id.tv_icollec) {
            ARouter.getInstance().build(AppRouter.ACCOUNT_COLLECT_LIST).navigation();
        }

        if (view.getId() == R.id.tv_ipay) {
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
