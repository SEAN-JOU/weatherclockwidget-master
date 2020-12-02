package com.shaen.weatherclockwidget.map;

import java.io.Serializable;


/**
 * Created by NB004 on 2018/7/24.
 */

public class EatData implements Serializable {

    public String Description;
    public String Images;
    public String Pos;
    public String Title;



    public String getPos() {
        return Pos;
    }

    public void setPos(String pos) {
        Pos = pos;
    }

    public String getTitle() {
        return Title;
    }

    public void setTitle(String title) {
        Title = title;
    }

    public String getDescription() {
        return Description;
    }

    public void setDescription(String description) {
        Description = description;
    }

    public String getImages() {
        return Images;
    }

    public void setImages(String images) {
        Images = images;
    }



}