package com.docker.cirlev2.vo.pro;

import android.databinding.ObservableField;

import com.docker.cirlev2.BR;
import com.docker.cirlev2.R;
import com.docker.cirlev2.vo.pro.base.DynamicResource;
import com.docker.cirlev2.vo.pro.base.ExtDataBase;
import java.util.List;

import me.tatarka.bindingcollectionadapter2.ItemBinding;

public class Dynamic extends ExtDataBase {


    @Override
    public int getItemLayout() {
        return R.layout.circlev2_item_dynamic_active;
    }

    public ObservableField<List<DynamicResource>> getInnerResource() {
        ObservableField<List<DynamicResource>> listObservableField = new ObservableField<>();
        if (this.resource != null && this.resource.size() > 0) {
            listObservableField.set(this.resource);
        }
        return listObservableField;
    }

    public final transient ItemBinding<DynamicResource> itemImgBinding = ItemBinding.<DynamicResource>of(BR.item,
            R.layout.circlev2_item_dynamic_img_innerv2) // 单一view 有点击事件
            .bindExtra(BR.serverdata, this);

    public transient ItemBinding<DynamicResource> itemDetailBinding;

    public ItemBinding<DynamicResource> getItemDetailBinding() {
        if (itemDetailBinding == null) {
            itemDetailBinding = ItemBinding.<DynamicResource>of(BR.item,
                    R.layout.circlev2_item_dynamic_detail_imgv2) // 单一view 有点击事件  //circlev2_item_dynamic_detail_img
                    .bindExtra(BR.serverdata, this);
        }
        return itemDetailBinding;
    }

}
