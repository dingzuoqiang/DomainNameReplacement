package com.dingzuoqiang.contentprovider.db;

import android.content.ContentProvider;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;

/**
 * Created by dingzuoqiang on 2017/6/16.
 * Email: 530858106@qq.com
 */

public class MyContentProvider extends ContentProvider {
    private DBOpenHelper dbOpenHelper;
    //常量UriMatcher.NO_MATCH表示不匹配任何路径的返回码
    private static final UriMatcher MATCHER = new UriMatcher(UriMatcher.NO_MATCH);
    private static final int STUDENTS = 1;
    private static final int STUDENT = 2;
    private static final int COACHS = 3;
    private static final int COACH = 4;
    private static final int HUZHUS = 5;
    private static final int HUZHU = 6;
    private static final int WMCS = 7;
    private static final int WMC = 8;
    private static final int LOCATIONS = 9;
    private static final int LOCATION = 10;

    static {
        //如果match()方法匹配content://com.dingzuoqiang.contentprovider.myprovider/student路径，返回匹配码为1
        MATCHER.addURI("com.dingzuoqiang.contentprovider.myprovider", Constant.TAB_STUDENT, STUDENTS);
        //如果match()方法匹配content://com.dingzuoqiang.contentprovider.myprovider/student/123路径，返回匹配码为2
        MATCHER.addURI("com.dingzuoqiang.contentprovider.myprovider", Constant.TAB_STUDENT + "/#", STUDENT);//#号为通配符
        MATCHER.addURI("com.dingzuoqiang.contentprovider.myprovider", Constant.TAB_COACH, COACHS);
        MATCHER.addURI("com.dingzuoqiang.contentprovider.myprovider", Constant.TAB_COACH + "/#", COACH);//#号为通配符
        MATCHER.addURI("com.dingzuoqiang.contentprovider.myprovider", Constant.TAB_HUZHU, HUZHUS);
        MATCHER.addURI("com.dingzuoqiang.contentprovider.myprovider", Constant.TAB_HUZHU + "/#", HUZHU);//#号为通配符
        MATCHER.addURI("com.dingzuoqiang.contentprovider.myprovider", Constant.TAB_WMC, WMCS);
        MATCHER.addURI("com.dingzuoqiang.contentprovider.myprovider", Constant.TAB_WMC + "/#", WMC);//#号为通配符
        MATCHER.addURI("com.dingzuoqiang.contentprovider.myprovider", Constant.TAB_LOCATION, LOCATIONS);
        MATCHER.addURI("com.dingzuoqiang.contentprovider.myprovider", Constant.TAB_LOCATION + "/#", LOCATION);//#号为通配符
    }

    @Override
    public int delete(Uri uri, String selection, String[] selectionArgs) {
        SQLiteDatabase db = dbOpenHelper.getWritableDatabase();
        int count = 0;
        long id = 0;
        String where = "";
        switch (MATCHER.match(uri)) {
            case STUDENTS:
                count = db.delete(Constant.TAB_STUDENT, selection, selectionArgs);
                return count;
            case STUDENT:
                //ContentUris类用于获取Uri路径后面的ID部分
                id = ContentUris.parseId(uri);
                where = "id = " + id;
                if (selection != null && !"".equals(selection)) {
                    where = selection + " and " + where;
                }
                count = db.delete(Constant.TAB_STUDENT, where, selectionArgs);
                return count;
            case COACHS:
                count = db.delete(Constant.TAB_COACH, selection, selectionArgs);
                return count;
            case COACH:
                //ContentUris类用于获取Uri路径后面的ID部分
                id = ContentUris.parseId(uri);
                where = "id = " + id;
                if (selection != null && !"".equals(selection)) {
                    where = selection + " and " + where;
                }
                count = db.delete(Constant.TAB_COACH, where, selectionArgs);
                return count;

            case HUZHUS:
                count = db.delete(Constant.TAB_HUZHU, selection, selectionArgs);
                return count;
            case HUZHU:
                //ContentUris类用于获取Uri路径后面的ID部分
                id = ContentUris.parseId(uri);
                where = "id = " + id;
                if (selection != null && !"".equals(selection)) {
                    where = selection + " and " + where;
                }
                count = db.delete(Constant.TAB_HUZHU, where, selectionArgs);
                return count;
            case WMCS:
                count = db.delete(Constant.TAB_WMC, selection, selectionArgs);
                return count;
            case WMC:
                //ContentUris类用于获取Uri路径后面的ID部分
                id = ContentUris.parseId(uri);
                where = "id = " + id;
                if (selection != null && !"".equals(selection)) {
                    where = selection + " and " + where;
                }
                count = db.delete(Constant.TAB_WMC, where, selectionArgs);
                return count;
            case LOCATIONS:
                count = db.delete(Constant.TAB_LOCATION, selection, selectionArgs);
                return count;
            case LOCATION:
                //ContentUris类用于获取Uri路径后面的ID部分
                id = ContentUris.parseId(uri);
                where = "id = " + id;
                if (selection != null && !"".equals(selection)) {
                    where = selection + " and " + where;
                }
                count = db.delete(Constant.TAB_LOCATION, where, selectionArgs);
                return count;
            default:
                throw new IllegalArgumentException("Unkwon Uri:" + uri.toString());
        }
    }

    /**
     * 该方法用于返回当前Url所代表数据的MIME类型。
     * 如果操作的数据属于集合类型，那么MIME类型字符串应该以vnd.android.cursor.dir/开头
     * 如果要操作的数据属于非集合类型数据，那么MIME类型字符串应该以vnd.android.cursor.item/开头
     */
    @Override
    public String getType(Uri uri) {
        switch (MATCHER.match(uri)) {
            case STUDENTS:
                return "vnd.android.cursor.dir/" + Constant.TAB_STUDENT;

            case STUDENT:
                return "vnd.android.cursor.item/" + Constant.TAB_STUDENT;
            case COACHS:
                return "vnd.android.cursor.dir/" + Constant.TAB_COACH;

            case COACH:
                return "vnd.android.cursor.item/" + Constant.TAB_COACH;
            case HUZHUS:
                return "vnd.android.cursor.dir/" + Constant.TAB_HUZHU;

            case HUZHU:
                return "vnd.android.cursor.item/" + Constant.TAB_HUZHU;
            case WMCS:
                return "vnd.android.cursor.dir/" + Constant.TAB_WMC;

            case WMC:
                return "vnd.android.cursor.item/" + Constant.TAB_WMC;
            case LOCATIONS:
                return "vnd.android.cursor.dir/" + Constant.TAB_LOCATION;

            case LOCATION:
                return "vnd.android.cursor.item/" + Constant.TAB_LOCATION;

            default:
                throw new IllegalArgumentException("Unkwon Uri:" + uri.toString());
        }
    }

    @Override
    public Uri insert(Uri uri, ContentValues values) {
        SQLiteDatabase db = dbOpenHelper.getWritableDatabase();
        long rowid = 0;
        Uri insertUri = null;//得到代表新增记录的Uri
        switch (MATCHER.match(uri)) {
            case STUDENTS:
                rowid = db.insert(Constant.TAB_STUDENT, "url", values);
                insertUri = ContentUris.withAppendedId(uri, rowid);//得到代表新增记录的Uri
                this.getContext().getContentResolver().notifyChange(uri, null);
                return insertUri;
            case COACHS:
                rowid = db.insert(Constant.TAB_COACH, "url", values);
                insertUri = ContentUris.withAppendedId(uri, rowid);//得到代表新增记录的Uri
                this.getContext().getContentResolver().notifyChange(uri, null);
                return insertUri;
            case HUZHUS:
                rowid = db.insert(Constant.TAB_HUZHU, "url", values);
                insertUri = ContentUris.withAppendedId(uri, rowid);//得到代表新增记录的Uri
                this.getContext().getContentResolver().notifyChange(uri, null);
                return insertUri;
            case WMCS:
                rowid = db.insert(Constant.TAB_WMC, "url", values);
                insertUri = ContentUris.withAppendedId(uri, rowid);//得到代表新增记录的Uri
                this.getContext().getContentResolver().notifyChange(uri, null);
                return insertUri;
            case LOCATIONS:
                rowid = db.insert(Constant.TAB_LOCATION, "url", values);
                insertUri = ContentUris.withAppendedId(uri, rowid);//得到代表新增记录的Uri
                this.getContext().getContentResolver().notifyChange(uri, null);
                return insertUri;
            default:
                throw new IllegalArgumentException("Unkwon Uri:" + uri.toString());
        }
    }

    @Override
    public boolean onCreate() {
        this.dbOpenHelper = new DBOpenHelper(this.getContext());
        return false;
    }

    @Override
    public Cursor query(Uri uri, String[] projection, String selection, String[] selectionArgs,
                        String sortOrder) {
        SQLiteDatabase db = dbOpenHelper.getReadableDatabase();
        long id = 0;
        String where = null;
        switch (MATCHER.match(uri)) {
            case STUDENTS:
                return db.query(Constant.TAB_STUDENT, projection, selection, selectionArgs, null, null, sortOrder);
            case STUDENT:
                id = ContentUris.parseId(uri);
                where = "id = " + id;
                if (selection != null && !"".equals(selection)) {
                    where = selection + " and " + where;
                }
                return db.query(Constant.TAB_STUDENT, projection, where, selectionArgs, null, null, sortOrder);
            case COACHS:
                return db.query(Constant.TAB_COACH, projection, selection, selectionArgs, null, null, sortOrder);
            case COACH:
                id = ContentUris.parseId(uri);
                where = "id = " + id;
                if (selection != null && !"".equals(selection)) {
                    where = selection + " and " + where;
                }
                return db.query(Constant.TAB_COACH, projection, where, selectionArgs, null, null, sortOrder);
            case HUZHUS:
                return db.query(Constant.TAB_HUZHU, projection, selection, selectionArgs, null, null, sortOrder);
            case HUZHU:
                id = ContentUris.parseId(uri);
                where = "id = " + id;
                if (selection != null && !"".equals(selection)) {
                    where = selection + " and " + where;
                }
                return db.query(Constant.TAB_HUZHU, projection, where, selectionArgs, null, null, sortOrder);

            case WMCS:
                return db.query(Constant.TAB_WMC, projection, selection, selectionArgs, null, null, sortOrder);
            case WMC:
                id = ContentUris.parseId(uri);
                where = "id = " + id;
                if (selection != null && !"".equals(selection)) {
                    where = selection + " and " + where;
                }
                return db.query(Constant.TAB_WMC, projection, where, selectionArgs, null, null, sortOrder);
            case LOCATIONS:
                return db.query(Constant.TAB_LOCATION, projection, selection, selectionArgs, null, null, sortOrder);
            case LOCATION:
                id = ContentUris.parseId(uri);
                where = "id = " + id;
                if (selection != null && !"".equals(selection)) {
                    where = selection + " and " + where;
                }
                return db.query(Constant.TAB_LOCATION, projection, where, selectionArgs, null, null, sortOrder);
            default:
                throw new IllegalArgumentException("Unkwon Uri:" + uri.toString());
        }
    }

    @Override
    public int update(Uri uri, ContentValues values, String selection, String[] selectionArgs) {
        SQLiteDatabase db = dbOpenHelper.getWritableDatabase();
        int count = 0;
        long id = 0;
        String where = null;
        switch (MATCHER.match(uri)) {
            case STUDENTS:
                count = db.update(Constant.TAB_STUDENT, values, selection, selectionArgs);
                return count;
            case STUDENT:
                id = ContentUris.parseId(uri);
                where = "id = " + id;
                if (selection != null && !"".equals(selection)) {
                    where = selection + " and " + where;
                }
                count = db.update(Constant.TAB_STUDENT, values, where, selectionArgs);
                return count;
            case COACHS:
                count = db.update(Constant.TAB_COACH, values, selection, selectionArgs);
                return count;
            case COACH:
                id = ContentUris.parseId(uri);
                where = "id = " + id;
                if (selection != null && !"".equals(selection)) {
                    where = selection + " and " + where;
                }
                count = db.update(Constant.TAB_COACH, values, where, selectionArgs);
                return count;
            case HUZHUS:
                count = db.update(Constant.TAB_HUZHU, values, selection, selectionArgs);
                return count;
            case HUZHU:
                id = ContentUris.parseId(uri);
                where = "id = " + id;
                if (selection != null && !"".equals(selection)) {
                    where = selection + " and " + where;
                }
                count = db.update(Constant.TAB_HUZHU, values, where, selectionArgs);
                return count;
            case WMCS:
                count = db.update(Constant.TAB_WMC, values, selection, selectionArgs);
                return count;
            case WMC:
                id = ContentUris.parseId(uri);
                where = "id = " + id;
                if (selection != null && !"".equals(selection)) {
                    where = selection + " and " + where;
                }
                count = db.update(Constant.TAB_WMC, values, where, selectionArgs);
                return count;
            case LOCATIONS:
                count = db.update(Constant.TAB_LOCATION, values, selection, selectionArgs);
                return count;
            case LOCATION:
                id = ContentUris.parseId(uri);
                where = "id = " + id;
                if (selection != null && !"".equals(selection)) {
                    where = selection + " and " + where;
                }
                count = db.update(Constant.TAB_LOCATION, values, where, selectionArgs);
                return count;
            default:
                throw new IllegalArgumentException("Unkwon Uri:" + uri.toString());
        }
    }
}