package com.docker.nitsample.vo.card;

import android.view.View;

import com.docker.common.common.utils.rxbus.RxBus;
import com.docker.common.common.utils.rxbus.RxEvent;
import com.docker.common.common.vo.card.BaseCardVo;
import com.docker.nitsample.R;

public class SampleCardVo extends BaseCardVo {

    public SampleCardVo(int style, int position) {
        super(style, position);
    }

    @Override
    public void onItemClick(BaseCardVo item, View view) {
        RxBus.getDefault().post(new RxEvent<>("card_edit", item));
    }

    @Override
    public int getItemLayout() {
        return R.layout.item_edit_tool_sample;
    }

    public BaseCardVo mCardData;

    public BaseCardVo getmCardData() {
        return mCardData;
    }

    public void setmCardData(BaseCardVo mCardData) {
        this.mCardData = mCardData;
    }
}
