package com.docker.cirlev2.vo.card;

import android.databinding.ObservableField;
import android.util.Log;
import android.view.View;

import com.docker.cirlev2.R;
import com.docker.common.common.command.ReplyCommandParam;
import com.docker.common.common.vo.card.BaseCardVo;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class PersonInfoHeadVo extends BaseCardVo<String> {



    public PersonInfoHeadVo(int style, int position) {
        super(style, position);

    }

    @Override
    public void onItemClick(BaseCardVo item, View view) {

    }

    @Override
    public int getItemLayout() {
        return R.layout.circlev2_activity_circle_person_info_head;
    }




    private String avatar;
    private String nickName;
    private String fenduo;
    private int isFocus;
    private String content;
    private String favs;
    private String focus;
    private String fans;
    private  String cover;

    public String getCover() {
        return cover;
    }

    public void setCover(String cover) {
        this.cover = cover;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public String getNickName() {
        return nickName;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName;
    }

    public String getFenduo() {
        return fenduo;
    }

    public void setFenduo(String fenduo) {
        this.fenduo = fenduo;
    }

    public int getIsFocus() {
        return isFocus;
    }

    public void setIsFocus(int isFocus) {
        this.isFocus = isFocus;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getFavs() {
        return favs;
    }

    public void setFavs(String favs) {
        this.favs = favs;
    }

    public String getFocus() {
        return focus;
    }

    public void setFocus(String focus) {
        this.focus = focus;
    }

    public String getFans() {
        return fans;
    }

    public void setFans(String fans) {
        this.fans = fans;
    }
}
