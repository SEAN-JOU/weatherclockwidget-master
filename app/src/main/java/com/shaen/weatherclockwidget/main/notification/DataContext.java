package com.shaen.weatherclockwidget.main.notification;

import android.provider.BaseColumns;

public class DataContext  {
        private DataContext(){}
public static  abstract class DataTable implements BaseColumns{
    public static  final  String FCM= "FCM";
    public static final String TIT="title";
    public static  final  String CON= "content";
    public  static  final String VID= "video";
    public  static  final String WEB ="web";
    public static final String PHO="photo";
    public static final String COV="cover";

}}
