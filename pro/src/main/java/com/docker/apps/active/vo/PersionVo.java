package com.docker.apps.active.vo;

import com.alibaba.android.arouter.launcher.ARouter;
import com.docker.apps.R;
import com.docker.common.common.model.BaseSampleItem;
import com.docker.common.common.model.OnItemClickListener;
import com.docker.common.common.router.AppRouter;

public class PersionVo extends BaseSampleItem {
    @Override
    public int getItemLayout() {
        return R.layout.pro_active_item_persion;
    }

    @Override
    public OnItemClickListener getOnItemClickListener() {
        return (item, view) -> {
            if (view.getId() == R.id.rl_content) { // 个人详情
                ARouter.getInstance().build(AppRouter.ACTIVE_MANAGER_PERSION_DETAIL).navigation();
            }
            if (view.getId() == R.id.tv_verfi) { // 核销

            }
            if (view.getId() == R.id.tv_argee) { // 同意

            }
            if (view.getId() == R.id.tv_cancel) { //忽略

            }

        };
    }
}
