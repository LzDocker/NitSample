package com.docker.cirlev2.vo.pro;

import com.docker.cirlev2.R;
import com.docker.cirlev2.vo.pro.base.ExtDataBase;

import java.util.ArrayList;

public class News extends ExtDataBase {


    public ArrayList<String> newsImgs;

    public String new_content;

    @Override
    public int getItemLayout() {
        return R.layout.circlev2_item_dynamic_news;
    }
}
