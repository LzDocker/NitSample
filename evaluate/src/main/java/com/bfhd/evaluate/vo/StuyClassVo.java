package com.bfhd.evaluate.vo;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * kxf -> 2019-09-05
 **/
public class StuyClassVo  implements Parcelable {

    //https://static.frhelper.com/MediaPool/ChannelImg/94889971-56e2-4d60-9a43-a052e4c81278.jpg?stamp=1537912490160
    private String img;
    private String title;
    private String con;
    private String id;
    private String audioUrl;

    protected StuyClassVo(Parcel in) {
        img = in.readString();
        title = in.readString();
        con = in.readString();
        id = in.readString();
        audioUrl = in.readString();
    }

    public StuyClassVo() {
    }

    public static final Creator<StuyClassVo> CREATOR = new Creator<StuyClassVo>() {
        @Override
        public StuyClassVo createFromParcel(Parcel in) {
            return new StuyClassVo(in);
        }

        @Override
        public StuyClassVo[] newArray(int size) {
            return new StuyClassVo[size];
        }
    };

    public String getAudioUrl() {
        return audioUrl;
    }

    public void setAudioUrl(String audioUrl) {
        this.audioUrl = audioUrl;
    }

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getCon() {
        return con;
    }

    public void setCon(String con) {
        this.con = con;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(img);
        parcel.writeString(title);
        parcel.writeString(con);
        parcel.writeString(id);
        parcel.writeString(audioUrl);
    }

    @Override
    public String toString() {
        return "StuyClassVo{" +
                "img='" + img + '\'' +
                ", title='" + title + '\'' +
                ", con='" + con + '\'' +
                ", id='" + id + '\'' +
                ", audioUrl='" + audioUrl + '\'' +
                '}';
    }
}
