package com.docker.common.common.vo.servervo.vo;

import com.docker.common.common.model.BaseSampleItem;
import com.docker.common.common.model.OnItemClickListener;

import java.util.List;

public class ExtDataBase extends BaseSampleItem {


    public String uuid;
    public String updatetime;
    public String title;

    public List<DynamicResource> resource;

    public DynamicDataBase parent;

    @Override
    public int getItemLayout() {
        return 0;
    }

    @Override
    public OnItemClickListener getOnItemClickListener() {
        return parent.getOnItemClickListener();
    }

    public DynamicDataBase getParent() {
        return parent;
    }

    public void setParent(DynamicDataBase parent) {
        this.parent = parent;
    }
}
