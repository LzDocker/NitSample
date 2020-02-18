package com.docker.apps.active.vo;

import com.docker.cirlev2.vo.entity.ServiceDataSuperVo;
import com.docker.common.common.model.OnItemClickListener;

public class ActiveServerDataBean extends ServiceDataSuperVo {


    public ActiveVo extData;

    @Override
    public int getItemLayout() {
        return 0;
    }

    @Override
    public OnItemClickListener getOnItemClickListener() {
        return null;
    }
}
