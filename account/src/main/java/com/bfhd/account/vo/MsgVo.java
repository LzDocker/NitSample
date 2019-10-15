package com.bfhd.account.vo;

public class MsgVo {


    /**
     * errno : 1
     * errmsg : 最多只能添加5人
     */

    private int errno;
    private String errmsg;

    public int getErrno() {
        return errno;
    }

    public void setErrno(int errno) {
        this.errno = errno;
    }

    public String getErrmsg() {
        return errmsg;
    }

    public void setErrmsg(String errmsg) {
        this.errmsg = errmsg;
    }
}
