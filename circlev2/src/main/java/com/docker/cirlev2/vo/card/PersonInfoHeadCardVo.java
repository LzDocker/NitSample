package com.docker.cirlev2.vo.card;

import android.databinding.Bindable;
import android.databinding.ObservableField;
import android.view.View;

import com.dcbfhd.utilcode.utils.ActivityUtils;
import com.docker.cirlev2.BR;
import com.docker.cirlev2.R;
import com.docker.cirlev2.vm.card.CirclePersonInfoHeadCardVm;
import com.docker.common.common.utils.cache.CacheUtils;
import com.docker.common.common.vo.card.BaseCardVo;
import com.docker.module_im.session.SessionHelper;

public class PersonInfoHeadCardVo extends BaseCardVo {

    public PersonInfoHeadCardVo(int style, int position) {
        super(style, position);
        mVmPath = "com.docker.cirlev2.vm.card.CirclePersonInfoHeadCardVm";
    }

    @Override
    public void onItemClick(BaseCardVo item, View view) {
        if (view.getId() == R.id.tv_Focu || view.getId() == R.id.tv_each_Focus) {
            ((CirclePersonInfoHeadCardVm) mNitcommonCardViewModel).attention(((PersonInfoHeadCardVo) item).datasource.get(), null);
        }
        if (view.getId() == R.id.tv_im) {
            ((CirclePersonInfoHeadCardVm) mNitcommonCardViewModel).toIm(((PersonInfoHeadCardVo) item).datasource.get(), null);
        }
    }

    @Override
    public int getItemLayout() {
        return R.layout.circlev2_activity_circle_person_info_head;
    }

    public transient ObservableField<PersonInfoHeadVo> datasource = new ObservableField<>();

    @Bindable
    public ObservableField<PersonInfoHeadVo> getDatasource() {
        return datasource;
    }

    public void setDatasource(PersonInfoHeadVo datasource) {
        this.datasource.set(datasource);
        if (this.mRepParamMap.get("uuid2").equals(CacheUtils.getUser().uuid)) {
            datasource.setIsSelf(true);
        }
        notifyPropertyChanged(BR.datasource);
    }

    public boolean isSelf = false;
}
