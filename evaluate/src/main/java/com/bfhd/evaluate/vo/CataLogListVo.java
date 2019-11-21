package com.bfhd.evaluate.vo;

/**
 * kxf -> 2019-09-27
 **/
public class CataLogListVo {
    /**
     * id : 1
     * inputtime : 1569035665
     * name : 英语第一章
     * book_id : 1
     * num : 1
     */

    public String id;
    public String inputtime;
    public String name;
    public String book_id;
    public String num;
    public String download_url;
    public String ksort;


    public  String pass_num;
    public boolean isCheck = false;
    public boolean isShow = false;

    @Override
    public String toString() {
        return "CataLogListVo{" +
                "id='" + id + '\'' +
                ", inputtime='" + inputtime + '\'' +
                ", name='" + name + '\'' +
                ", book_id='" + book_id + '\'' +
                ", num='" + num + '\'' +
                ", download_url='" + download_url + '\'' +
                ", ksort='" + ksort + '\'' +
                ", pass_num='" + pass_num + '\'' +
                ", isCheck=" + isCheck +
                ", isShow=" + isShow +
                '}';
    }
}
