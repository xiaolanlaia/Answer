package com.example.answer.login.bean;



public class LoginBean {
    private int code;
    private String message;
    private ValueBean value;

    public static class ValueBean {
        private int id;
        private String username;
        private String realname;

    }


    public ValueBean getValue() {
        return value;
    }

    public void setValue(ValueBean value) {
        this.value = value;
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


}
