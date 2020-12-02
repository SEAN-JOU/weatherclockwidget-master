package com.shaen.weatherclockwidget.main.notification;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;

/**
 * Created by jou on 2017/12/25.
 */

public class DatabasHelper extends SQLiteOpenHelper {

    private  static final  String DB_NAME = "FCMdata.db";
    private  static  final int VERSION = 2;
    private  static  final String CREATE_TABLE_FCMdata =
            "CREATE TABLE FCM(_id  INTEGER PRIMARY KEY AUTOINCREMENT,"+"title TEXT,content TEXT,video TEXT,web TEXT,photo TEXT,cover TEXT)";
    private static final String DROP_TABLE_FCMdata = "DROP TABLE IF EXISTS FCM";



    public DatabasHelper(Context context) {
        super(context,DB_NAME,null,VERSION);}

    @Override
    public void onCreate(SQLiteDatabase db) /*如果沒有數據庫時 執行此方法*/{
        db.execSQL(CREATE_TABLE_FCMdata);/*創建sqlite*/
    }
    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i1) {
       db.execSQL(DROP_TABLE_FCMdata);/*刪除sqlite*/
        db.execSQL(CREATE_TABLE_FCMdata);/*創建sqlite*/
    }




    public ArrayList<DataDetail> findAll(){

        SQLiteDatabase db = getReadableDatabase();
        String [] columns ={DataContext.DataTable._ID,DataContext.DataTable.TIT,DataContext.DataTable.CON,DataContext.DataTable.PHO,DataContext.DataTable.WEB,
                DataContext.DataTable.VID,DataContext.DataTable.COV};
        Cursor c =db.query(true,DataContext.DataTable.FCM,columns,null,null,null,null,null,null);
        DataDetail dataDetail = null;
        ArrayList<DataDetail> dataDetails=new ArrayList<>();
        while (c.moveToNext()){
            dataDetail = new DataDetail();

            dataDetail.setId(c.getInt(c.getColumnIndexOrThrow(DataContext.DataTable._ID)));
            dataDetail.setTitle(c.getString(c.getColumnIndexOrThrow(DataContext.DataTable.TIT)));
            dataDetail.setContent(c.getString(c.getColumnIndexOrThrow(DataContext.DataTable.CON)));
            dataDetail.setPhoto(c.getString(c.getColumnIndexOrThrow(DataContext.DataTable.PHO)));
            dataDetail.setWeb(c.getString(c.getColumnIndexOrThrow(DataContext.DataTable.WEB)));
            dataDetail.setVideo(c.getString(c.getColumnIndexOrThrow(DataContext.DataTable.VID)));
            dataDetail.setCover(c.getString(c.getColumnIndexOrThrow(DataContext.DataTable.COV)));
            dataDetails.add(dataDetail);
        }
        c.close();
        db.close();

        return dataDetails;
    }}
