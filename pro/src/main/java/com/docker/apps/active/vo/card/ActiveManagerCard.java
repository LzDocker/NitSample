package com.docker.apps.active.vo.card;

import android.databinding.ObservableArrayList;
import android.databinding.ObservableList;
import android.view.View;

import com.alibaba.android.arouter.launcher.ARouter;
import com.docker.apps.R;
import com.docker.apps.active.vo.ActiveManagerVo;
import com.docker.apps.active.vo.ActiveVo;
import com.docker.cirlev2.BR;
import com.docker.common.common.command.ReplyCommandParam;
import com.docker.common.common.router.AppRouter;
import com.docker.common.common.vo.card.BaseCardVo;

import me.tatarka.bindingcollectionadapter2.ItemBinding;

public class ActiveManagerCard extends BaseCardVo {


    public ActiveManagerCard(int style, int position) {
        super(style, position);
    }

    @Override
    public void onItemClick(BaseCardVo item, View view) {

    }

    public void onChilcItemClick(ActiveManagerVo activeManagerVo, View view) {
        if (mCommand != null) {
            mCommand.exectue(activeManagerVo.id);
        }

    }

    public transient ReplyCommandParam mCommand;

    public void setCommand(ReplyCommandParam replyCommandParam) {
        mCommand = replyCommandParam;
    }

    @Override
    public int getItemLayout() {
        return R.layout.pro_active_manage_card;
    }


    public transient ItemBinding<ActiveManagerVo> itemImgBinding = ItemBinding.<ActiveManagerVo>of(BR.item,
            R.layout.pro_item_active_manager).bindExtra(com.docker.apps.BR.parent, this); // 单一view 有点击事件;

    public final ObservableList<ActiveManagerVo> observableList = new ObservableArrayList<>();


    public ObservableList<ActiveManagerVo> getInnerResource() {

        observableList.add(new ActiveManagerVo(R.mipmap.active_icon_detail, "查看活动页面", 0));
        observableList.add(new ActiveManagerVo(R.mipmap.active_icon_share, "分享活动", 1));
        observableList.add(new ActiveManagerVo(R.mipmap.active_icon_scaner, "下载二维码", 2));
        observableList.add(new ActiveManagerVo(R.mipmap.active_icon_modify, "修改活动", 3));
        observableList.add(new ActiveManagerVo(R.mipmap.active_icon_down, "下架", 4));
        observableList.add(new ActiveManagerVo(R.mipmap.active_icon_del, "删除活动", 5));

        return observableList;
    }

    public ActiveManagerDetailVo aMDlVo;

    public ActiveManagerDetailVo getActiveManagerDetailVo() {
        return aMDlVo;
    }

    public void setActiveManagerDetailVo(ActiveManagerDetailVo activeManagerDetailVo) {
        this.aMDlVo = activeManagerDetailVo;
    }
}
