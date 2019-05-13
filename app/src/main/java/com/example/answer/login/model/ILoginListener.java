package com.example.answer.login.model;

public interface ILoginListener {
    void loginSucceed();
    void loginFailed();
    void loginEmpty();
    void networkFailed();
}
