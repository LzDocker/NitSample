package com.bfhd.evaluate.vo;

import java.io.Serializable;

public class DateVo implements Serializable {

    //api.php?m=app.dateList
/*
* "text": "1月",
            "val": "01"*/

    public String text;
    public String val;

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getVal() {
        return val;
    }

    public void setVal(String val) {
        this.val = val;
    }
}
