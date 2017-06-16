package com.dingzuoqiang.contentprovider.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by dingzuoqiang on 2017/6/16.
 * Email: 530858106@qq.com
 */

public class DBOpenHelper extends SQLiteOpenHelper {
    private static final String DBNAME = "myprovider.db"; //数据库名称
    private static final int DBVER = 1;//数据库版本
    String[] arr = {Constant.TAB_STUDENT, Constant.TAB_COACH, Constant.TAB_HUZHU, Constant.TAB_WMC};

    public DBOpenHelper(Context context) {
        super(context, DBNAME, null, DBVER);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        // id 主键id，  url 路径   selected 1 选中，0 未选中
        for (int i = 0; i < arr.length; i++) {
            String tab = arr[i];
            String sq0 = "CREATE TABLE " + tab + " (id integer primary key autoincrement, url varchar(500), selected int)";
            db.execSQL(sq0);//执行有更改的sql语句

            String sq1 = "INSERT INTO " + tab + " VALUES (1,'http://wmc.test.huzhuchinaa.cn/', 0)";
            String sq2 = "INSERT INTO " + tab + " VALUES (2,'https://www.17aixinchou.com/', 0)";
            String sq3 = "INSERT INTO " + tab + " VALUES (3,'http://10.200.15.212:8090/', 0)";
            String sq4 = "INSERT INTO " + tab + " VALUES (4,'http://ybb.s3.natapp.cc/', 1)";
            String sq5 = "INSERT INTO " + tab + " VALUES (5,'http://console.1217.com:8080/jsxy/', 0)";
            String sq6 = "INSERT INTO " + tab + " VALUES (6,'http://appservice.1217.com:8080/1217/', 0)";
            db.execSQL(sq1);
            db.execSQL(sq2);
            db.execSQL(sq3);
            db.execSQL(sq4);
            db.execSQL(sq5);
            db.execSQL(sq6);
        }
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int arg1, int arg2) {
        for (int i = 0; i < arr.length; i++) {
            db.execSQL("DROP TABLE IF EXISTS " + arr[i]);
        }
        onCreate(db);
    }

}