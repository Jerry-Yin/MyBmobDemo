package com.example.jerryyin.mybobmdemo.model;

import cn.bmob.v3.BmobObject;

/**
 * Created by JerryYin on 10/17/15.
 */
public class Student extends BmobObject {

    private String name;
    private Integer age;
    private String sex;
    private Integer classNum;

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public Integer getClassNum() {
        return classNum;
    }

    public void setClassNum(Integer classNum) {
        this.classNum = classNum;
    }
}
