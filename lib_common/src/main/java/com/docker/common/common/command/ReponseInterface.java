package com.docker.common.common.command;


import java.io.Serializable;

/**
 * Created by kelin on 15-8-4.
 */

public interface ReponseInterface<T, R> extends Serializable {

    T exectue(R r);

    void next(R r);

}
