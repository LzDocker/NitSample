package com.docker.apps.active.vo;

import android.text.TextUtils;

import com.alibaba.android.arouter.launcher.ARouter;
import com.dcbfhd.utilcode.utils.ToastUtils;
import com.docker.apps.R;
import com.docker.cirlev2.vo.entity.ServiceDataBean;
import com.docker.common.common.model.BaseSampleItem;
import com.docker.common.common.model.OnItemClickListener;
import com.docker.common.common.router.AppRouter;
import com.docker.common.common.utils.cache.CacheUtils;

import java.util.List;

public class ActiveVo extends BaseSampleItem {


    public int scope = 0;

    @Override
    public int getItemLayout() {
        return R.layout.pro_item_active;
    }

    @Override
    public OnItemClickListener getOnItemClickListener() {
        return (item, view) -> {
            if (item == null || TextUtils.isEmpty(((ActiveVo) item).uuid)) {
                ToastUtils.showShort("数据不存在");
                return;
            }
            if (((ActiveVo) item).uuid.equals(CacheUtils.getUser().uuid)) {
                ARouter.getInstance().build(AppRouter.ACTIVE_MANAGER_DETAIL).withSerializable("activeVo", ((ActiveVo) item)).navigation();
            } else {
                ARouter.getInstance().build(AppRouter.ACTIVE_DEATIL_ACTIVITY)
                        .withString("activityid", ((ActiveVo) item).dataid)
                        .withString("activitytitle", ((ActiveVo) item).title)
                        .navigation();
            }
        };
    }

    public String endDate;
    public String cityCode;
    public String sponsorName;
    public String utid;
    public String title;
    public String uuid;
    public String content;
    public List<ServiceDataBean.ResourceBean> banner;

    public String contact;
    public String isDate;
    public String actType;
    public String circleid;
    public String lat;
    public String lng;
    public String limitNum;
    public String address;
    public String signAudit;
    public String situs;
    public String location;
    public String startDate;
    public String memberid;

    public List<ServiceDataBean.ResourceBean> resource;

    public String updatetime;
    public String inputtime;

    public int status;

    public String dataid;

    public String enrollNum;

    public int signStatus; // 0，未报名，1 已报名

    public String point;

    public String actTypeName;
    public String circlename;
}

