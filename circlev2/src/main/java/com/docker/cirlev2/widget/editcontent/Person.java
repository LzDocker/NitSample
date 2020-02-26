package com.docker.cirlev2.widget.editcontent;

public class Person {

//    lBean.setId(keyId);
//        lBean.setName("@" + nameStr);
//        lBean.setStartIndex(startIndex);
//        lBean.setEndIndex(endIndex);


    public String id;
    public String name;


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getStartIndex() {
        return startIndex;
    }

    public void setStartIndex(int startIndex) {
        this.startIndex = startIndex;
    }

    public int getEndIndex() {
        return endIndex;
    }

    public void setEndIndex(int endIndex) {
        this.endIndex = endIndex;
    }

    public int startIndex;
    public int endIndex;
}
