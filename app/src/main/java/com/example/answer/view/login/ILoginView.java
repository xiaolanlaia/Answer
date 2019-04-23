package com.example.answer.view.login;

import android.app.Activity;
import android.widget.CheckBox;
import android.widget.EditText;

public interface ILoginView {

    void loginFailed();
    void loginSuccess();
    void loginEmpty();
    void logonFailed();
    void logonSuccess();
    void logonEmpty();
    void checkChecked();
    void restoreChecked();
    void firstRun();
    String getAccount();
    String getPassword();

    Activity getContext();

    void doRestore();
    void noRestore();


    void firstRunToast();


}
