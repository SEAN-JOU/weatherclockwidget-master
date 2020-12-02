package com.shaen.weatherclockwidget.video;


import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;


/**
 * Created by jou on 2017/11/26.
 */

public class DataHelper extends SQLiteOpenHelper {



    final static String DB_Name = "student.sqlite";
    final static int VERSION = 2;

    public DataHelper(Context context) {
        super(context, DB_Name, null, VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        String createSQL = "CREATE TABLE \"phone\" (\"id\" INTEGER PRIMARY KEY  AUTOINCREMENT  NOT NULL , \"name\" VARCHAR, \"tel\" VARCHAR, \"addr\" VARCHAR, \"email\" VARCHAR)";
        sqLiteDatabase.execSQL(createSQL);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        if (i == 1)
        {
            String upgradeSQL = "ALTER TABLE \"main\".\"phone\" ADD COLUMN \"email\" VARCHAR";
            sqLiteDatabase.execSQL(upgradeSQL);
        }

    }
}
