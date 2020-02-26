package com.docker.apps.active.vo;

import com.docker.apps.R;
import com.docker.common.common.model.BaseSampleItem;
import com.docker.common.common.model.OnItemClickListener;

public class ActiveSelectVo extends BaseSampleItem {
    @Override
    public int getItemLayout() {
        return R.layout.pro_item_active_select;
    }

    @Override
    public OnItemClickListener getOnItemClickListener() {
        return null;
    }

    public String id;
    public String title;
    public String content;
    public String img;
}
