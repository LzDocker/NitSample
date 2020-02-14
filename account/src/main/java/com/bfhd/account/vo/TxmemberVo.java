package com.bfhd.account.vo;

import android.databinding.BaseObservable;

import java.io.Serializable;

public class TxmemberVo extends BaseObservable implements Serializable {

    /*
    * "bankcard": "6222080409003363619",
        "bank": "工商银行",
        "bankman": "张三",
        "account_phone": ""
    * */

    public String bankcard;
    public String bank;
    public String bankman;
    public String account_phone;

}
