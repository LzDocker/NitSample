package com.docker.nitsample.vo;

import android.os.Environment;

import com.alibaba.android.arouter.launcher.ARouter;
import com.dcbfhd.utilcode.utils.AppUtils;
import com.docker.common.common.model.BaseItemModel;
import com.docker.common.common.model.OnItemClickListener;
import com.docker.common.common.router.AppRouter;
import com.docker.nitsample.R;

import timber.log.Timber;

public class SamplEditVo implements BaseItemModel {


    @Override
    public int getItemLayout() {
        return R.layout.item_edit_sample;
    }

    @Override
    public OnItemClickListener getOnItemClickListener() {
        return (item, view) -> {
            if (view.getId() == R.id.tv_edit_again) { // 重新编辑
                ARouter.getInstance().build(AppRouter.HOME_preview).withString("dbTabid", ((SamplEditVo) item).title).withBoolean("isEdit", true).navigation();
                return;
            }
            if (view.getId() == R.id.tv_preview) { // 预览
                ARouter.getInstance().build(AppRouter.HOME_preview).withString("dbTabid", ((SamplEditVo) item).title).withBoolean("isEdit", false).navigation();
                return;
            }
            if (view.getId() == R.id.ll_card) {// 预览
                ARouter.getInstance().build(AppRouter.HOME_preview).withString("dbTabid", ((SamplEditVo) item).title).withBoolean("isEdit", false).navigation();
                return;
            }
        };
    }

    public String title;

    public String img;

    public String key;

    public SamplEditVo(String title) {
        this.key = title;
        this.title = title.replace("db_tab_", "");
        this.img = Environment.getExternalStorageDirectory() + "/" + AppUtils.getAppName() + "/chache/" + title + ".jpg";
    }
}
