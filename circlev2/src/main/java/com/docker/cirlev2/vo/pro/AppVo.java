package com.docker.cirlev2.vo.pro;

import com.docker.cirlev2.R;
import com.docker.common.common.model.BaseSampleItem;
import com.docker.common.common.model.OnItemClickListener;
import com.docker.common.common.utils.rxbus.RxBus;
import com.docker.common.common.utils.rxbus.RxEvent;

public class AppVo extends BaseSampleItem {


    public String name;

    public String id;

    public String icon;


    @Override
    public int getItemLayout() {
        return R.layout.circlev2_item_pro;
    }

    @Override
    public OnItemClickListener getOnItemClickListener() {
        return (item, view) -> {
            RxBus.getDefault().post(new RxEvent<>("pro_add", item));
        };
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        AppVo appVo = (AppVo) o;

        return id.equals(appVo.id);
    }

    @Override
    public int hashCode() {
        return id.hashCode();
    }
}
