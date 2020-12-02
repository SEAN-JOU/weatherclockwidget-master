package com.shaen.weatherclockwidget.filedata;

import java.io.Serializable;

/**
 * Created by student on 2017/10/2.
 */

public class Coffee implements Serializable {
    private String title;
    private String price;
    private int resource_id;


    public Coffee(String title,String price,int resource_id){
        this.title = title;
        this.price = price;
        this.resource_id= resource_id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public int getResource_id() {
        return resource_id;
    }

    public void setResource_id(int resource_id) {
        this.resource_id = resource_id;
    }

    @Override
    public String toString() {
        return title+price+resource_id;
    }
}
