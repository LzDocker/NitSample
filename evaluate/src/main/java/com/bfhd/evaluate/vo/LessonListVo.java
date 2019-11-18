package com.bfhd.evaluate.vo;

/**
 * kxf -> 2019-09-27
 **/
public class LessonListVo {

    /**
     * id : 1
     * book_id : 1
     * catalog_id : 1
     * name : What is Healthy Sleep? 6 Sleep tips to better sleep
     * read_num :
     */

    private String id;
    private String book_id;
    private String catalog_id;
    private String name;
    private String read_num;
    private String radio;
    private String ksort;

    public String getKsort() {
        return ksort;
    }

    public void setKsort(String ksort) {
        this.ksort = ksort;
    }

    private int fraction;

    public int getFraction() {
        return fraction;
    }

    public void setFraction(int fraction) {
        this.fraction = fraction;
    }

    private String catalog_name;

    public String getCatalog_name() {
        return catalog_name;
    }

    public void setCatalog_name(String catalog_name) {
        this.catalog_name = catalog_name;
    }

    public String getRadio() {
        return radio;
    }

    public void setRadio(String radio) {
        this.radio = radio;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getBook_id() {
        return book_id;
    }

    public void setBook_id(String book_id) {
        this.book_id = book_id;
    }

    public String getCatalog_id() {
        return catalog_id;
    }

    public void setCatalog_id(String catalog_id) {
        this.catalog_id = catalog_id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getRead_num() {
        return read_num;
    }

    public void setRead_num(String read_num) {
        this.read_num = read_num;
    }
}
