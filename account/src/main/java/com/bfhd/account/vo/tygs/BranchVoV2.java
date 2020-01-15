package com.bfhd.account.vo.tygs;

import com.bfhd.account.R;
import com.docker.cirlev2.vo.entity.CircleListVo;
import com.docker.common.common.model.OnItemClickListener;

public class BranchVoV2 extends CircleListVo {

    @Override
    public int getItemLayout() {
       return R.layout.account_tygs_item_branchv2;
    }

    @Override
    public OnItemClickListener getOnItemClickListener() {
        return super.getOnItemClickListener();
    }


}

