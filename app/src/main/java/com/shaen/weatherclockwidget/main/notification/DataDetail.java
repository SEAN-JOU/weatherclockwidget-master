package com.shaen.weatherclockwidget.main.notification;

import java.io.Serializable;

/**
 * Created by jou on 2017/12/25.
 */

public class DataDetail implements Serializable {


    int id;
    private String title, content, video, web, photo,cover;


    public DataDetail() {
    }

    public DataDetail(String title, String content, String video, String web, String photo, String cover) {
        this.title = title;
        this.content = content;
        this.video = video;
        this.web = web;
        this.photo = photo;
        this.cover =cover;
    }


    public int getId() {
        return id;
    }

    public String getContent() {
        return content;
    }

    public String getVideo() {return video;}

    public String getWeb() {
        return web;
    }

    public String getPhoto() {
        return photo;
    }

    public String getTitle() {return title;}

    public String getCover() {return cover;}

    public void setId(int id) {
        this.id = id;
    }

    public void setCover(String cover) {this.cover = cover;}

    public void setContent(String content) {
        this.content = content;
    }

    public void setVideo(String video) {
        this.video = video;
    }

    public void setWeb(String web) {
        this.web = web;
    }

    public void setPhoto(String photo) {
        this.photo = photo;
    }

    public void setTitle(String title) {this.title = title;}

    @Override
    public String toString() {
        return "DataDetail{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", content='" + content + '\'' +
                ", video='" + video + '\'' +
                ", web='" + web + '\'' +
                ", photo='" + photo +'\''+
                ", cover='" + cover +
                '}';
    }


}

