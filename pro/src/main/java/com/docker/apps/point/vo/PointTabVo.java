package com.docker.apps.point.vo;

import java.io.Serializable;
import java.util.ArrayList;

public class PointTabVo implements Serializable {

    public String name;
    public String param;
    public String sort;

    public ArrayList<PointTabVo> rankClass;
}
