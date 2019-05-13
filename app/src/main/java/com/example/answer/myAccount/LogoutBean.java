package com.example.answer.myAccount;

public class LogoutBean {
    private int code;
    private String message;
    private LogoutValue value;
    public static class LogoutValue{

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

    public LogoutValue getValue() {
        return value;
    }

    public void setValue(LogoutValue value) {
        this.value = value;
    }
}
