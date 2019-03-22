package com.example.answer.presenter;

import android.content.Context;
import android.content.SharedPreferences;
import android.widget.CompoundButton;
import android.widget.Toast;

import com.example.answer.db.User;
import com.example.answer.model.IFirstRunListener;
import com.example.answer.model.ILoginListener;
import com.example.answer.model.ILoginModel;
import com.example.answer.model.ILogonListener;
import com.example.answer.model.LoginModel;

import com.example.answer.view.login.ILoginView;
import com.example.answer.view.login.LoginActivity;

import org.litepal.LitePal;

import static com.example.answer.view.answerQuestion.MainActivity.sharedPreferences;


public class LoginPresenter {

    private ILoginView iLoginView;
    private ILoginModel iLoginModel;

    public LoginPresenter(ILoginView iLoginView){
        this.iLoginModel = new LoginModel();
        this.iLoginView = iLoginView;
    }

    public void login(Context context){
        iLoginModel.login(context,iLoginView.getAccount(), iLoginView.getPassword(), new ILoginListener() {
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

    public void checkChecked(final Context context){
        iLoginModel.checkChecked(context,iLoginView.getCheckBox(),iLoginView.getAccount(),iLoginView.getPassword());
    }

    public void restoreChecked(Context context){
        iLoginModel.restoreChecked(context,iLoginView.getAccount(),iLoginView.getCheckBox(),iLoginView.setAccount(),iLoginView.setPassword());

    }

    public void firstRun(Context context){
        iLoginModel.firstRun(context, new IFirstRunListener() {
            @Override
            public void firstRun() {
                iLoginView.firstRunToast();
            }
        });

//        SharedPreferences sharedPreferences =  context.getSharedPreferences("FirstRun", 0);
//        Boolean firstRun = sharedPreferences.getBoolean("First", true);
//        if (firstRun) {
//            sharedPreferences.edit().putBoolean("First", false).apply();
//            //创建数据库;
//            LitePal.getDatabase();
//            User user = new User();
//            user.setAccount("admin");
//            user.setPassword("123456");
//            user.save();
//            Toast.makeText(context,"Welcome !",Toast.LENGTH_SHORT).show();
//        }

    }
}
