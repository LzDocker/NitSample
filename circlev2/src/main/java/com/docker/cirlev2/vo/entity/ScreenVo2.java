package com.docker.cirlev2.vo.entity;

import java.util.List;

public class ScreenVo2 {


    /**
     * name : 快捷筛选
     * fieldname : shi
     * type : 1
     * check_val : [["1室","3426"],["2室","3427"],["3室","3428"],["4室","3429"],["5室","3430"],["6室","3431"],["7室","3432"]]
     */

    private String name;
    private String fieldname;
    private String type;
    private List<HotItemVo> check_val;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getFieldname() {
        return fieldname;
    }

    public void setFieldname(String fieldname) {
        this.fieldname = fieldname;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public List<HotItemVo> getCheck_val() {
        return check_val;
    }

    public void setCheck_val(List<HotItemVo> check_val) {
        this.check_val = check_val;
    }
}
