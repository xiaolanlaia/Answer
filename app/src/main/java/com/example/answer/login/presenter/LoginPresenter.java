package com.example.answer.login.presenter;


import com.example.answer.login.model.IFirstRunListener;
import com.example.answer.login.model.ILoginListener;
import com.example.answer.login.model.ILoginModel;
import com.example.answer.login.model.IRestoreCheckListener;
import com.example.answer.login.model.LoginModel;

import com.example.answer.login.view.ILoginView;


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
            @Override
            public void networkFailed(){
                iLoginView.networkFailed();
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
