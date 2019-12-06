package com.bfhd.account.vo;

import android.databinding.BaseObservable;
import android.text.TextUtils;

import java.io.Serializable;

/**
 * Created by Administrator on 2018/8/28.
 */

public class MyInfoDispatcherVo extends BaseObservable implements Serializable {

    public MyInfoVo member;

    public ExtData extData;

    public class ExtData extends BaseObservable implements Serializable {
        public String dynamicNum;
        public String dzNum;
        public String plNum;
    }

}
