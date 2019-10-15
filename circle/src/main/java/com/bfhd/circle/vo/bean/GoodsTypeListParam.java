package com.bfhd.circle.vo.bean;

import com.bfhd.circle.vo.TypeVo;

import java.io.Serializable;

// 商品类型入参
public class GoodsTypeListParam implements Serializable {

    public TypeVo parent;
    public TypeVo children;
    public GoodsTypeListParam(TypeVo parent, TypeVo children) {
        this.parent = parent;
        this.children = children;
    }
}
