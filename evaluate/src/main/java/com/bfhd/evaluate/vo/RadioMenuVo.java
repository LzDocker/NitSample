package com.bfhd.evaluate.vo;

/**
 * kxf -> 2019-09-29
 **/
public class RadioMenuVo {

    /**
     * id : 1
     * inputtime : 1569052223
     * name : 第一册
     * book_name : 新概念英语
     * back_ground : /var/upload/image/2019/09/2019092908455310205_2560x1656.png
     * thumb : /var/upload/image/2019/09/2019092501145448130_380x270.png
     * introduction : <p>新概念英语<br /></p>
     * num : 1
     */

    private String id;
    private String inputtime;
    private String name;
    private String book_name;
    private String back_ground;
    private String thumb;
    private String introduction;
    private String num;
    private String download_url;

    private String pass_num;

    public String getPass_num() {
        return pass_num;
    }

    public void setPass_num(String pass_num) {
        this.pass_num = pass_num;
    }

    public String getDownload_url() {
        return download_url;
    }

    public void setDownload_url(String download_url) {
        this.download_url = download_url;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getInputtime() {
        return inputtime;
    }

    public void setInputtime(String inputtime) {
        this.inputtime = inputtime;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getBook_name() {
        return book_name;
    }

    public void setBook_name(String book_name) {
        this.book_name = book_name;
    }

    public String getBack_ground() {
        return back_ground;
    }

    public void setBack_ground(String back_ground) {
        this.back_ground = back_ground;
    }

    public String getThumb() {
        return thumb;
    }

    public void setThumb(String thumb) {
        this.thumb = thumb;
    }

    public String getIntroduction() {
        return introduction;
    }

    public void setIntroduction(String introduction) {
        this.introduction = introduction;
    }

    public String getNum() {
        return num;
    }

    public void setNum(String num) {
        this.num = num;
    }
}
