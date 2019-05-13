package com.example.answer.exam;

import java.util.List;

public class QuestionListBean {

    int code;
    String message;
    public List<ValueBean>value;
    public static class ValueBean{
        int id;
        String title;
        long createData;
        long startTime;
        long endTime;
        int examType;

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public long getCreateData() {
            return createData;
        }

        public void setCreateData(long createData) {
            this.createData = createData;
        }

        public long getStartTime() {
            return startTime;
        }

        public void setStartTime(long startTime) {
            this.startTime = startTime;
        }

        public long getEndTime() {
            return endTime;
        }

        public void setEndTime(long endTime) {
            this.endTime = endTime;
        }

        public int getExamType() {
            return examType;
        }

        public void setExamType(int examType) {
            this.examType = examType;
        }
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public List<ValueBean> getValue() {
        return value;
    }

    public void setValue(List<ValueBean> value) {
        this.value = value;
    }
}
