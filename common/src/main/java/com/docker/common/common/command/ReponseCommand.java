package com.docker.common.common.command;


import com.docker.common.di.ServiceModule;

/**
 * Created by kelin on 15-8-4.
 */

@FunctionalInterface
public interface ReponseCommand <T>  {
    T exectue();
}
