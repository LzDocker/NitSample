package com.docker.apps.intvite.vo.card;

import android.view.View;
import com.alibaba.android.arouter.launcher.ARouter;
import com.dcbfhd.utilcode.utils.ActivityUtils;
import com.docker.apps.R;
import com.docker.common.common.router.AppRouter;
import com.docker.common.common.vo.card.BaseCardVo;
import com.docker.common.common.widget.XPopup.FullScreenSharePopView;
import com.lxj.xpopup.XPopup;

public class InviteCardVo extends BaseCardVo {


    public InviteCardVo(int style, int position) {
        super(style, position);
    }

    @Override
    public void onItemClick(BaseCardVo item, View view) {

        if (view.getId() == R.id.ll_invite_img) {
            new XPopup.Builder(ActivityUtils.getTopActivity())
                    .hasStatusBarShadow(true)
                    .autoOpenSoftInput(true)
                    .asCustom(new FullScreenSharePopView(ActivityUtils.getTopActivity()).setStyle(0))
                    .show();
            return;
        }

        if (view.getId() == R.id.ll_invite_link) {
            new XPopup.Builder(ActivityUtils.getTopActivity())
                    .hasStatusBarShadow(true)
                    .autoOpenSoftInput(true)
                    .asCustom(new FullScreenSharePopView(ActivityUtils.getTopActivity()).setStyle(1))
                    .show();
            return;
        }
        if (view.getId() == R.id.ll_invite_scan) {
            ARouter.getInstance().build(AppRouter.INVITE_INDEX_CODE_SCAN).navigation();
            return;
        }
    }

    @Override
    public int getItemLayout() {
        return R.layout.pro_invite_card;
    }
}
