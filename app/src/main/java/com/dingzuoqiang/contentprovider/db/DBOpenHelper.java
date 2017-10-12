package com.dingzuoqiang.contentprovider.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

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
        initTableLocation(db);
    }

    private void initTableLocation(SQLiteDatabase db) {
        String sq0 = "CREATE TABLE " + Constant.TAB_LOCATION + " (id integer primary key autoincrement, city varchar(500), latitude double,longitude double, selected int)";
        db.execSQL(sq0);//执行有更改的sql语句
        String[] strings = {"杭州", "上海黄浦区", "安徽亳州市蒙城县", "温哥华", "宝云岛", "新西敏", "列治文", "北温", "多伦多", "约克", "奥沙瓦", "密西沙加市", "曼哈顿", "布鲁克林", "旧金山", "索诺马县", "洛杉矶县", "橙县", "西雅图市", "斯诺霍米什县"};
        double[] lat = {30.281783, 31.2304324029, 33.2658480732, 49.2719881, 49.3909805, 49.200074, 49.1633969, 49.308448, 43.7332555, 44.1919595, 43.9297023, 43.53724, 40.830722, 40.63531, 37.757466, 38.404639, 34.148815, 33.907004, 47.632817, 47.9874};
        double[] lng = {120.116079, 121.4737919321, 116.5644927969, -123.1589515, -123.3265575, -122.9549538, -123.1311539, -122.948982, -79.5020445, -79.4902784, -78.859426, -79.7061218, -73.946334, -73.969785, -122.429117, -122.825144, -118.441947, -117.918523, -122.345716, -122.1308};

        for (int i = 0; i < strings.length; i++) {
            String sq1 = "INSERT INTO " + Constant.TAB_LOCATION + " VALUES (" + i + ",'" + strings[i] + "'," + lat[i] + "," + lng[i] + ", 0)";
            Log.e("dingzuo",sq1);
            db.execSQL(sq1);
        }
//        String sq2 = "INSERT INTO " + Constant.TAB_LOCATION + " VALUES (2,'杭州',30.290597,120.036009, 0)";
//        db.execSQL(sq1);
//        db.execSQL(sq2);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int arg1, int arg2) {
        for (int i = 0; i < arr.length; i++) {
            db.execSQL("DROP TABLE IF EXISTS " + arr[i]);
        }
        db.execSQL("DROP TABLE IF EXISTS " + Constant.TAB_LOCATION);
        onCreate(db);
    }

}