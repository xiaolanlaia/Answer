package com.example.answer.login.view;

import android.app.Activity;

import com.example.answer.login.base.IBaseView;

public interface ILoginView extends IBaseView {

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
