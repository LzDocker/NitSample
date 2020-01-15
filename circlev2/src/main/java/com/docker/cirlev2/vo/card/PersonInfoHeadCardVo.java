package com.docker.cirlev2.vo.card;

import android.databinding.Bindable;
import android.view.View;

import com.docker.cirlev2.BR;
import com.docker.cirlev2.R;
import com.docker.common.common.vo.card.BaseCardVo;

public class PersonInfoHeadCardVo extends BaseCardVo {

    public PersonInfoHeadCardVo(int style, int position) {
        super(style, position);
        mVmPath = "com.docker.cirlev2.vm.card.CirclePersonInfoHeadCardVm";

    }

    @Override
    public void onItemClick(BaseCardVo item, View view) {

    }

    @Override
    public int getItemLayout() {
        return R.layout.circlev2_activity_circle_person_info_head;
    }

    public transient PersonInfoHeadVo datasource;

    @Bindable
    public PersonInfoHeadVo getDatasource() {
        return datasource;
    }

    public void setDatasource(PersonInfoHeadVo datasource) {
        this.datasource = datasource;
        datasource.isSelf = isSelf;
        notifyPropertyChanged(BR.datasource);
    }

    public boolean isSelf = false;
}
