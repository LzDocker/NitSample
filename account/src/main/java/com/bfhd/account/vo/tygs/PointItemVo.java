package com.bfhd.account.vo.tygs;

import android.databinding.Bindable;
import android.view.View;

import com.bfhd.account.R;
import com.bfhd.account.vo.MyInfoVo;
import com.docker.common.common.vo.card.BaseCardVo;

public class PointItemVo extends BaseCardVo<MyInfoVo> {

    public PointItemVo(int style, int position) {
        super(style, position);
    }

    @Override
    public void onItemClick(BaseCardVo item, View view) {

    }

    @Override
    public int getItemLayout() {
        return R.layout.account_tygs_item_point;
    }
    private  String id;

    private String name;
    private String time;
    private String point;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getPoint() {
        return point;
    }

    public void setPoint(String point) {
        this.point = point;
    }
}
