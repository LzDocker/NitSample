package com.docker.cirlev2.vo.entity;

import java.io.Serializable;
import java.util.ArrayList;

public class TypeVo implements Serializable {


    /*
    * "linkageid": "3408",
        "parentid": "0",
        "name": "闲置物品",
        "img": "",
        "child":
    * */

    public String linkageid;
    public String parentid;
    public String name;
    public String img;
    public String description;
    public ArrayList<TypeVo> child;
    public boolean isChecked;


}
