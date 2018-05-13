package com.example.zzx.mycalculator;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;


public class CalculateLogs extends SQLiteOpenHelper {
    private static final String _DBNAME = "db_logs.db";

    //table
    private static final String _TABLE = "logs";
    private static final String _ID = "id";
    private static final String _PROCESS = "process";
    private static final String _RESULT = "result";


    private SQLiteDatabase myDB;


    public CalculateLogs(Context context) {
        super(context, _DBNAME, null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        Log.e("数据库", "onCreate方法已经调用！");
        int version = db.getVersion();
        db.execSQL("create TABLE " + _TABLE + "("
                + _ID + " text primary key," + _PROCESS + " text,"
                + _RESULT + " text)");
        db.setVersion(1);
        Log.e("数据库", "创建成功！");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }


    //对表格的一系列操作入口
    public void insert(String process, String result) {
        SQLiteDatabase db = this.getReadableDatabase();
        if (db.isOpen()) {
            Log.e("数据库", "已经打开！");
        }
        Cursor cursor = db.query(_TABLE,
                null, null, null, null, null, null);
        int count = cursor.getCount();
        ContentValues tableLine = new ContentValues();
        tableLine.put(this._ID, String.valueOf(count++));
        tableLine.put(this._PROCESS, process);
        tableLine.put(this._RESULT, result);
        try {
            db.insert(this._TABLE, null, tableLine);
            Log.e("数据库", "插入成功！共" + count + "条数据！");
        } catch (SQLException e) {
            Log.e("数据库错误！", e.getMessage());
        } finally {
            db.close();
        }
    }

    //查询所有的记录用于生成list
    public ArrayList<String> queryAll() {
        ArrayList<String> lines = new ArrayList<String>();
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.query(_TABLE,
                null, null, null, null, null, null);
        if (cursor.getCount() <= 0) {
            return lines;
        } else {
            for (int i = 0; i < cursor.getCount(); i++) {
                lines.add(cursor.getString(i));
            }
            return lines;
        }
    }

    public ArrayList<String> getAllRecords() {
        Log.e("数据库","获取所有记录！");
        SQLiteDatabase db = this.getReadableDatabase();
        ArrayList<String> lines = new ArrayList<String>();
        String temp1 = "";
        String temp2 = "";
        Cursor cursor = db.query(_TABLE,
                null, null, null, null, null, null);
        if (cursor.getCount() <= 0) {
            lines.add("没有记录！");
            cursor.close();
            return lines;
        } else {
            cursor.moveToFirst();
            while (cursor.moveToNext()) {
                temp1 = cursor.getString(cursor.getColumnIndex(_PROCESS));
                temp2 = cursor.getString(cursor.getColumnIndex(_RESULT));
                lines.add(temp1 + "=" + temp2);
            }
            cursor.close();
            return lines;

        }
    }


    public void delOne() {

    }

    public void clearAll() {

    }
}
