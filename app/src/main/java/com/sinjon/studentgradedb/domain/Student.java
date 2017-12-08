package com.sinjon.studentgradedb.domain;

/**
 * 学生bean类
 * 包含班级,名字,成绩等信息
 */
public class Student {
    private long id; //序号
    private String clazz; //班级
    private String name; //名字
    private String grade; //成绩

    public Student(long id, String clazz, String name, String grade) {
        this.id = id;
        this.clazz = clazz;
        this.name = name;
        this.grade = grade;
    }

    public Student(String clazz, String name, String grade) {

        this.clazz = clazz;
        this.name = name;
        this.grade = grade;
    }

    public Student() {

    }

    public long getId() {

        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getClazz() {
        return clazz;
    }

    public void setClazz(String clazz) {
        this.clazz = clazz;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getGrade() {
        return grade;
    }

    public void setGrade(String grade) {
        this.grade = grade;
    }

    @Override
    public String toString() {
        return "[序号: " + id + ", 班级: " + clazz + " 姓名: " + name + " 成绩: " + grade + "]";
    }
}
