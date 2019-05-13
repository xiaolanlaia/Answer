package com.example.answer.login.view;

import android.app.Activity;

public interface ILoginView {

    void loginFailed();
    void loginSuccess();
    void loginEmpty();
    void networkFailed();

    void restoreChecked();
    void firstRun();
    String getAccount();
    String getPassword();

    Activity getContext();

    void doRestore();
    void noRestore();

    void firstRunToast();


}
