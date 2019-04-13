package com.jrx.model;

import java.io.Serializable;

/**
 * @Author: sunchuanyin
 * @Date: 2019/4/10 12:31
 * @Version 1.0
 */
public class StudentMessage implements Serializable {


    private static final long serialVersionUID = 583201539L;
    /**
     * 学生id
     */
    private Integer id;
    /**
     * 班级id
     */
    private Integer classId;
    /**
     * 学生姓名
     */
    private String name;
    /**
     * 学生性别
     */
    private String sex;
    /**
     * 学生年龄
     */
    private Integer age;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getClassId() {
        return classId;
    }

    public void setClassId(Integer classId) {
        this.classId = classId;
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

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("StudentMessage{");
        sb.append("id=").append(id);
        sb.append(", classId=").append(classId);
        sb.append(", name='").append(name).append('\'');
        sb.append(", sex='").append(sex).append('\'');
        sb.append(", age=").append(age);
        sb.append('}');
        return sb.toString();
    }
}
