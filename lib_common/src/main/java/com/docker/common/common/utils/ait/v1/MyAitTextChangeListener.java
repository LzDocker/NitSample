package com.docker.common.common.utils.ait.v1;

/**
 * Created by hzchenkang on 2017/7/10.
 */

public interface MyAitTextChangeListener {

    void onTextAdd(String content, int start, int length);

    void onTextDelete(int start, int length);
}
