package com.docker.cirlev2.vo.pro;

import com.docker.cirlev2.R;
import com.docker.common.common.model.OnItemClickListener;
import com.docker.common.common.vo.servervo.vo.ExtDataBase;

public class News extends ExtDataBase {


    @Override
    public int getItemLayout() {
        return R.layout.circlev2_item_dynamic_news;
    }

    @Override
    public OnItemClickListener getOnItemClickListener() {
        return super.getOnItemClickListener();
    }
}
