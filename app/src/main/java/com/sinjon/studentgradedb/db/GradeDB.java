package com.sinjon.studentgradedb.db;


import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;


/**
 * 成绩数据库类
 * 建库建表
 */
public class GradeDB extends SQLiteOpenHelper {
    public GradeDB(Context context) {
        super(context, "grade.db", null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("create table grade(id integer primary key autoincrement," +
                "class varchar(10)," +
                "name varchar(20)," +
                "grade integer)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("drop table grade");
        onCreate(db);
    }
}
