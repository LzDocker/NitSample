package com.docker.common.common.router;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class RoutersServerVo implements Serializable {

    public String mtime;
    public List<Router> routes = new ArrayList<>();
}
