package com.docker.apps.point.vo;

import com.docker.apps.R;
import com.docker.common.common.model.BaseSampleItem;
import com.docker.common.common.model.OnItemClickListener;

public class PointSortItemVo extends BaseSampleItem {


    @Override
    public int getItemLayout() {
        return R.layout.pro_point_sort_item;
    }

    @Override
    public OnItemClickListener getOnItemClickListener() {
        return null;
    }

    public String nickname;
    public String username;
    public String fullName;
    public String uuid;
    public String totalNum;
    public String avatar;
    public String circlename;


    public String rowNo;

    public String rankType;
}
