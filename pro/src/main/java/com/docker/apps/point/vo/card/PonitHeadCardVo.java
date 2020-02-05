package com.docker.apps.point.vo.card;

import android.databinding.ObservableArrayList;
import android.databinding.ObservableField;
import android.databinding.ObservableList;
import android.view.View;

import com.docker.apps.R;
import com.docker.apps.point.vo.PointSortItemVo;
import com.docker.common.common.vo.card.BaseCardVo;

import java.util.ArrayList;
import java.util.List;

public class PonitHeadCardVo extends BaseCardVo {

    public ObservableList<PointSortItemVo> Obtotals = new ObservableArrayList<>();

    public String rankType;

    public PonitHeadCardVo(int style, int position) {
        super(style, position);
    }

    @Override
    public void onItemClick(BaseCardVo item, View view) {

    }

    @Override
    public int getItemLayout() {
        return R.layout.pro_point_sort_head_card;
    }


    public void setTotals(ArrayList<PointSortItemVo> totals, String rankType) {
        this.rankType = rankType;
        this.Obtotals.addAll(totals);
    }
}
