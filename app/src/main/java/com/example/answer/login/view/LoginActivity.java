package com.example.answer.login.view;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

import com.example.answer.util.BaseActivity;
import com.example.answer.exam.ExamActivity;
import com.example.answer.login.presenter.LoginPresenter;
import com.example.answer.R;


/**
 * Created by W on 2019/2/12.
 */

public class LoginActivity extends BaseActivity implements View.OnClickListener,ILoginView{

    private EditText accountText;
    private EditText passwordText;
    private Button login_btn;
    private CheckBox checkBox;
    private LoginPresenter loginPresenter = new LoginPresenter(this);
    private SharedPreferences sharedPreferences;



    @Override
    protected void onCreate(Bundle savedInstance){
        super.onCreate(savedInstance);
        setContentView(R.layout.activity_login);
        sharedPreferences = getSharedPreferences("CheckLogin",0);
        sharedPreferences.edit().putBoolean("haveLogin",false).apply();
        Toolbar toolbar = (Toolbar)findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        accountText = (EditText)findViewById(R.id.account_text);
        passwordText = (EditText)findViewById(R.id.password_text);
        login_btn = (Button)findViewById(R.id.login);
        login_btn.setOnClickListener(this);
        checkBox = (CheckBox)findViewById(R.id.check_box);
        firstRun();
        //启动时检查是否记住密码

        restoreChecked();

    }
    @Override
    public void onClick(View v){
        switch (v.getId()){
            case R.id.login:{
                loginPresenter.login();
                break;
            }
            default:
                break;
        }
    }

    @Override
    public Activity getContext(){
        return this;
    }


    public void checkChecked(){
        if (checkBox.isChecked()){
            loginPresenter.checkChecked(true);
        }else {
            loginPresenter.checkChecked(false);
        }
    }

    @Override
    public void restoreChecked(){
        loginPresenter.restoreChecked();
    }
    @Override
    public void doRestore(){
        SharedPreferences sharedPreferences = getSharedPreferences("checked",0);
        accountText.setText(sharedPreferences.getString("account",""));
        passwordText.setText(sharedPreferences.getString("password",""));
        accountText.setSelection(accountText.length());
        checkBox.setChecked(true);
    }
    @Override
    public void noRestore(){
        checkBox.setChecked(false);
    }
    @Override
    public void firstRun() {

        loginPresenter.firstRun();

    }
    @Override
    public void loginFailed(){
        getToast("账号或密码错误");
    }
    @Override
    public void loginSuccess(){
        checkChecked();
        SharedPreferences sharedPreferences = getSharedPreferences("CheckLogin",0);
        sharedPreferences.edit().putBoolean("haveLogin",true).apply();
        Intent intent = new Intent(LoginActivity.this, ExamActivity.class);
        startActivity(intent);
        finish();
    }
    @Override
    public void loginEmpty(){
        getToast("账号密码不能为空");
    }
    @Override
    public void networkFailed(){
        getToast("网络错误");
    }
    @Override
    public String getAccount(){
        return accountText.getText().toString();
    }
    @Override
    public String getPassword(){
        return passwordText.getText().toString();
    }
    @Override
    public void firstRunToast(){
        getToast("欢迎");
    }

    private void getToast(final String s) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                Toast.makeText(LoginActivity.this, s, Toast.LENGTH_SHORT).show();
            }
        });
    }

}
