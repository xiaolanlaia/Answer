package com.example.answer.presenter;


import com.example.answer.model.IFirstRunListener;
import com.example.answer.model.ILoginListener;
import com.example.answer.model.ILoginModel;
import com.example.answer.model.ILogonListener;
import com.example.answer.model.IRestoreCheckListener;
import com.example.answer.model.LoginModel;

import com.example.answer.view.login.ILoginView;


public class LoginPresenter {

    private ILoginView iLoginView;
    private ILoginModel iLoginModel;

    public LoginPresenter(ILoginView iLoginView){
        this.iLoginModel = new LoginModel();
        this.iLoginView = iLoginView;
    }

    public void login(){
        iLoginModel.login(iLoginView.getContext(),iLoginView.getAccount(), iLoginView.getPassword(), new ILoginListener() {
            @Override
            public void loginSucceed() {
                iLoginView.loginSuccess();
            }

            @Override
            public void loginFailed() {

                iLoginView.loginFailed();
            }

            @Override
            public void loginEmpty() {
                iLoginView.loginEmpty();

            }
        });
    }

    public void logon(){
        iLoginModel.logon(iLoginView.getAccount(), iLoginView.getPassword(), new ILogonListener() {
            @Override
            public void logonSucceed() {
                iLoginView.logonSuccess();
            }

            @Override
            public void logonFailed() {
                iLoginView.logonFailed();
            }

            @Override
            public void logonEmpty() {
                iLoginView.logonEmpty();

            }
        });

    }

    public void checkChecked(boolean isChecked){
        iLoginModel.checkChecked(isChecked,iLoginView.getContext(),iLoginView.getAccount(),iLoginView.getPassword());
    }

    public void restoreChecked(){
        iLoginModel.restoreChecked(iLoginView.getContext(),new IRestoreCheckListener() {
            @Override
            public void doRestore() {
                iLoginView.doRestore();
            }

            @Override
            public void noRestore() {

                iLoginView.noRestore();
            }
        });

    }

    public void firstRun(){
        iLoginModel.firstRun(iLoginView.getContext(), new IFirstRunListener() {
            @Override
            public void firstRun() {
                iLoginView.firstRunToast();
            }
        });

    }
}
