package com.jrx.model;

import java.io.Serializable;
import java.util.Date;

/**
 * @Author: sunchuanyin
 * @Date: 2019/4/10 14:19
 * @Version 1.0
 */
public class ExamMessage implements Serializable {


    private static final long serialVersionUID = 583201539L;

    /**
     * 考试序列号
     */
    private Integer examId;
    /**
     * 考试科目
     */
    private String examCourse;
    /**
     * 考试时间
     */
    private Date examTime;
    /**
     * 考试分数
     */
    private Double examScore;
    /**
     * 学生id
     */
    private Integer studentId;

    public Integer getExamId() {
        return examId;
    }

    public void setExamId(Integer examId) {
        this.examId = examId;
    }

    public String getExamCourse() {
        return examCourse;
    }

    public void setExamCourse(String examCourse) {
        this.examCourse = examCourse;
    }

    public Date getExamTime() {
        return examTime;
    }

    public void setExamTime(Date examTime) {
        this.examTime = examTime;
    }

    public Double getExamScore() {
        return examScore;
    }

    public void setExamScore(Double examScore) {
        this.examScore = examScore;
    }

    public Integer getStudentId() {
        return studentId;
    }

    public void setStudentId(Integer studentId) {
        this.studentId = studentId;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("ExamMessage{");
        sb.append("examId=").append(examId);
        sb.append(", examCourse='").append(examCourse).append('\'');
        sb.append(", examTime=").append(examTime);
        sb.append(", examScore=").append(examScore);
        sb.append(", studentId=").append(studentId);
        sb.append('}');
        return sb.toString();
    }

}
