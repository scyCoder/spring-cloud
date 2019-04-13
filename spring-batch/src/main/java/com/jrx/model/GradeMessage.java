package com.jrx.model;

import java.io.Serializable;

/**
 * @Author: sunchuanyin
 * @Date: 2019/4/10 14:51
 * @Version 1.0
 */
public class GradeMessage implements Serializable {


    private static final long serialVersionUID = 583201539L;

    /**
     * 学生id
     */
    private Integer studentId;
    /**
     * 考试序列号
     */
    private Integer examId;
    /**
     * 学生总成及
     */
    private Double totalGrade;

    /**
     * 学生平均成绩
     */

    private Double averGrade;

    public Integer getStudentId() {
        return studentId;
    }

    public void setStudentId(Integer studentId) {
        this.studentId = studentId;
    }

    public Integer getExamId() {
        return examId;
    }

    public void setExamId(Integer examId) {
        this.examId = examId;
    }

    public Double getTotalGrade() {
        return totalGrade;
    }

    public void setTotalGrade(Double totalGrade) {
        this.totalGrade = totalGrade;
    }

    public Double getAverGrade() {
        return averGrade;
    }

    public void setAverGrade(Double averGrade) {
        this.averGrade = averGrade;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("GradeMessage{");
        sb.append("studentId=").append(studentId);
        sb.append(", examId=").append(examId);
        sb.append(", totalGrade=").append(totalGrade);
        sb.append(", averGrade=").append(averGrade);
        sb.append('}');
        return sb.toString();
    }

}
