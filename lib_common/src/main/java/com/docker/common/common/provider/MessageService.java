package com.docker.common.common.provider;

import com.alibaba.android.arouter.facade.template.IProvider;
import com.docker.common.common.command.ReplyCommandParam;

public interface MessageService extends IProvider {


    /*
     *
     * im  进入聊天界面
     * */
    void enterToTalk(String uid);

    /*
      登录

      0 成功
      1 失败
      2 exception
    */
    void loginIm(String account, String token, ReplyCommandParam<Integer> replyCommandParam);

    /*
     * 是否设置极光
     * */
    void setAlias(boolean isFocus);

    void loginOut();
}
