package com.docker.cirlev2.vo.entity;

import java.io.Serializable;
import java.util.List;

public class NetImgWapperVo implements Serializable {

    private List<NetImgVo> data;

    public List<NetImgVo> getData() {
        return data;
    }

    public void setData(List<NetImgVo> data) {
        this.data = data;
    }

    public String img;

}
