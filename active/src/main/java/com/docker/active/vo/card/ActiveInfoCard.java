package com.docker.active.vo.card;

import android.databinding.ObservableField;
import android.view.View;

import com.docker.active.R;
import com.docker.common.common.vo.card.BaseCardVo;

public class ActiveInfoCard extends BaseCardVo {


    public ActiveInfoCard(int style, int position) {
        super(style, position);
    }

    @Override
    public void onItemClick(BaseCardVo item, View view) {

    }

    @Override
    public int getItemLayout() {
        return R.layout.pro_active_info_card;
    }


    public ObservableField<ActiveManagerDetailVo> aMDlVo = new ObservableField<>();

    public ObservableField<ActiveManagerDetailVo> getActiveManagerDetailVo() {
        return aMDlVo;
    }

    public void setActiveManagerDetailVo(ActiveManagerDetailVo activeManagerDetailVo) {
        this.aMDlVo.set(activeManagerDetailVo);
    }
}
