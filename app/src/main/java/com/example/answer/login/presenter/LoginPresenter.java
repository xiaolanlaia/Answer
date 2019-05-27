package com.example.answer.login.presenter;


import com.example.answer.login.base.BasePresenter;
import com.example.answer.login.model.IFirstRunListener;
import com.example.answer.login.model.ILoginListener;
import com.example.answer.login.model.ILoginModel;
import com.example.answer.login.model.IRestoreCheckListener;
import com.example.answer.login.model.LoginModel;
import com.example.answer.login.view.ILoginView;


public class LoginPresenter extends BasePresenter<ILoginView> {


    private ILoginModel iLoginModel;

    public LoginPresenter(){
        this.iLoginModel = new LoginModel();

    }

    public void login(){
        iLoginModel.login(getView().getContext(),getView().getAccount(), getView().getPassword(), new ILoginListener() {
            @Override
            public void loginSucceed() {
                getView().loginSuccess();
            }

            @Override
            public void loginFailed() {

                getView().loginFailed();
            }

            @Override
            public void loginEmpty() {
                getView().loginEmpty();

            }
            @Override
            public void networkFailed(){
                getView().networkFailed();
            }
        });
    }


    public void checkChecked(boolean isChecked){
        iLoginModel.checkChecked(isChecked,getView().getContext(),getView().getAccount(),getView().getPassword());
    }

    public void restoreChecked(){
        iLoginModel.restoreChecked(getView().getContext(),new IRestoreCheckListener() {
            @Override
            public void doRestore() {
                getView().doRestore();
            }

            @Override
            public void noRestore() {

                getView().noRestore();
            }
        });

    }

    public void firstRun(){
        iLoginModel.firstRun(getView().getContext(), new IFirstRunListener() {
            @Override
            public void firstRun() {
                getView().firstRunToast();
            }
        });

    }
}
