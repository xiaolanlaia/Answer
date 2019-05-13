package com.example.answer.myAccount;

import com.example.answer.login.bean.LoginBean;

public class ChangePasswordBean {
    private int code;
    private String message;
    private LoginBean.ValueBean value;

    public static class ValueBean {

    }


    public LoginBean.ValueBean getValue() {
        return value;
    }

    public void setValue(LoginBean.ValueBean value) {
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
