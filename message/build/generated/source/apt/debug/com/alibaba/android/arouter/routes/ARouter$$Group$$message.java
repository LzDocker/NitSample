package com.alibaba.android.arouter.routes;

import com.alibaba.android.arouter.facade.enums.RouteType;
import com.alibaba.android.arouter.facade.model.RouteMeta;
import com.alibaba.android.arouter.facade.template.IRouteGroup;
import com.docker.message.ui.index.MessageSampleActivity;
import java.lang.Override;
import java.lang.String;
import java.util.Map;

/**
 * DO NOT EDIT THIS FILE!!! IT WAS GENERATED BY AROUTER. */
public class ARouter$$Group$$message implements IRouteGroup {
  @Override
  public void loadInto(Map<String, RouteMeta> atlas) {
    atlas.put("/message/index", RouteMeta.build(RouteType.ACTIVITY, MessageSampleActivity.class, "/message/index", "message", null, -1, -2147483648));
  }
}
