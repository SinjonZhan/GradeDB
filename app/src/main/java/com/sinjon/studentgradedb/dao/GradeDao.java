package com.sinjon.studentgradedb.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.sinjon.studentgradedb.db.GradeDB;
import com.sinjon.studentgradedb.domain.GradeTable;
import com.sinjon.studentgradedb.domain.Student;

import java.util.ArrayList;
import java.util.List;

/**
 * dao层 操作数据库
 */
public class GradeDao {

    public List<Student> studentDatas;
    private GradeDB gradeDB; //数据库类

    public GradeDao(Context context) {
        //创建Dao时,创建GradeDB
        gradeDB = new GradeDB(context);
    }

    /**
     * 数据库插入数据
     *
     * @param stu 信息封装
     */
    public void insert(Student stu) {
        SQLiteDatabase db = gradeDB.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(GradeTable.CLASS, stu.getClazz());
        values.put(GradeTable.NAME, stu.getName());
        values.put(GradeTable.GRADE, stu.getGrade());

        long id = db.insert(GradeTable.TABLE, null, values); //返回插入的id号
        stu.setId(id);
        db.close();

    }

    /**
     * 数据库单条信息删除
     *
     * @param id 信息id号
     */
    public void delete(long id) {
        SQLiteDatabase db = gradeDB.getWritableDatabase();
        db.delete(GradeTable.TABLE, "id=?", new String[]{id + ""});
        db.close();
    }

    /**
     * 查询数据库全部信息
     *
     * @return 信息列表
     */
    public List<Student> queryAll() {
        studentDatas = new ArrayList<>();
        SQLiteDatabase db = gradeDB.getReadableDatabase();
        Cursor cursor = db.rawQuery("select * from " + GradeTable.TABLE, null);
        while (cursor.moveToNext()) {
            //封装信息
            Student stu = new Student();
            stu.setId(cursor.getLong(0));
            stu.setClazz(cursor.getString(1));
            stu.setName(cursor.getString(2));
            stu.setGrade("" + cursor.getInt(3));

            //将单个学生信息添加到数据库
            studentDatas.add(stu);
        }

        cursor.close();
        db.close();
        return studentDatas;
    }

    /**
     * 更新数据库
     *
     * @param stu 更新的信息封装类
     */
    public void update(Student stu) {
        SQLiteDatabase db = gradeDB.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(GradeTable.GRADE, stu.getGrade());
        db.update(GradeTable.TABLE, values, "id=?", new String[]{stu.getId() + ""});
        db.close();
    }


}
