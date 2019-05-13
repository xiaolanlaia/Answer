package com.example.answer.randomExam;


public class RandomExam {
    private String examID;
    private String submitTime;
    private String mark;

    public RandomExam(String examID, String submitTime, String mark){
        this.examID = examID;
        this.submitTime = submitTime;
        this.mark = mark;
    }

    public String getExamID() {
        return examID;
    }

    public void setExamID(String examID) {
        this.examID = examID;
    }

    public String getSubmitTime() {
        return submitTime;
    }

    public void setSubmitTime(String submitTime) {
        this.submitTime = submitTime;
    }

    public String getMark() {
        return mark;
    }

    public void setMark(String mark) {
        this.mark = mark;
    }
}
