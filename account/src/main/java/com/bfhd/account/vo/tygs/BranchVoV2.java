package com.bfhd.account.vo.tygs;

import com.bfhd.account.R;
import com.docker.cirlev2.vo.entity.CircleListVo;
import com.docker.common.common.model.OnItemClickListener;
import com.docker.common.common.utils.rxbus.RxBus;
import com.docker.common.common.utils.rxbus.RxEvent;

public class BranchVoV2 extends CircleListVo {
    @Override
    public int getItemLayout() {
        int lay = R.layout.account_tygs_item_branchv2;
        switch (style) {
            case 1:
                lay = R.layout.account_tygs_item_branchv2_style1;
                break;
        }
        return lay;
    }

    @Override
    public OnItemClickListener getOnItemClickListener() {
        if (style == 1) {
            return (item, view) -> {
                if (view.getId() == R.id.ll_coutainer_style1) {
                    RxBus.getDefault().post(new RxEvent<>("select_circle",item));
                }
            };
        } else {
            return super.getOnItemClickListener();
        }
    }


}

