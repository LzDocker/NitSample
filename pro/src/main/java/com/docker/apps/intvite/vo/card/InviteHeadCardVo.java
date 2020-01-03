package com.docker.apps.intvite.vo.card;

import android.view.View;

import com.alibaba.android.arouter.launcher.ARouter;
import com.docker.apps.R;
import com.docker.cirlev2.Constant;
import com.docker.common.common.router.AppRouter;
import com.docker.common.common.utils.cache.CacheUtils;
import com.docker.common.common.vo.UserInfoVo;
import com.docker.common.common.vo.card.BaseCardVo;

public class InviteHeadCardVo extends BaseCardVo {


    public InviteHeadCardVo(int style, int position) {
        super(style, position);
    }

    @Override
    public void onItemClick(BaseCardVo item, View view) {
        if (view.getId() == R.id.fr_rule) { // 规则
            String weburl = Constant.BaseServeTest + "index.php?m=publish.push_dynamic";
            ARouter.getInstance().build(AppRouter.COMMONH5).withString("weburl", weburl).withString("title", "规则说明").navigation();
            return;
        }
    }

    @Override
    public int getItemLayout() {
        return R.layout.pro_invite_head_card;
    }
}
