package com.example.answer.view.login;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.Toast;

import com.example.answer.db.User;
import com.example.answer.presenter.LoginPresenter;
import com.example.answer.view.answerQuestionList.QuestionListActivity;
import com.example.answer.R;


import org.litepal.LitePal;


/**
 * Created by W on 2019/2/12.
 */

public class LoginActivity extends AppCompatActivity implements View.OnClickListener,ILoginView{

    private EditText accountText;
    private EditText passwordText;
    private Button login;
    private Button logon;
    private CheckBox checkBox;
    public static String account;
    private SharedPreferences sharedPreferences;
    private LoginPresenter loginPresenter = new LoginPresenter(this);

    @Override
    protected void onCreate(Bundle savedInstance){
        super.onCreate(savedInstance);
        setContentView(R.layout.login);
        Toolbar toolbar = (Toolbar)findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        accountText = (EditText)findViewById(R.id.account_text);
        passwordText = (EditText)findViewById(R.id.password_text);
        login = (Button)findViewById(R.id.login);
        logon = (Button)findViewById(R.id.logon);
        login.setOnClickListener(this);
        logon.setOnClickListener(this);
        checkBox = (CheckBox)findViewById(R.id.check_box);
        firstRun();
        restoreChecked();


    }
    @Override
    protected void onResume(){
        super.onResume();
        checkChecked();
    }
    @Override
    public void onClick(View v){
        switch (v.getId()){
            case R.id.logon: {
                loginPresenter.logon();
                break;
            }
            case R.id.login:{
                loginPresenter.login(LoginActivity.this);
                break;
            }
            default:
                break;
        }
    }



    @Override
    public void checkChecked(){
        loginPresenter.checkChecked(LoginActivity.this);
    }

    @Override
    public void restoreChecked(){
        loginPresenter.restoreChecked(LoginActivity.this);
//        sharedPreferences = getSharedPreferences("checked",0);
//        checked = sharedPreferences.getBoolean("check",false);
//        if (checked){
//            accountText.setText(sharedPreferences.getString("account",""));
//            passwordText.setText(sharedPreferences.getString("password",""));
//            accountText.setSelection(accountText.getText().length());
//            checkBox.setChecked(true);
//        }else {
//            checkBox.setChecked(false);
//        }
    }

    @Override
    public void firstRun() {
        loginPresenter.firstRun(LoginActivity.this);

    }
    @Override
    public void loginFailed(){
        Toast.makeText(this,"账号或密码错误",Toast.LENGTH_SHORT).show();
    }
    @Override
    public void loginSuccess(){
        //account为全局变量，在答题界面中作为SharedPreference缓存名称的一部分;
        account = accountText.getText().toString();
        Intent intent = new Intent(LoginActivity.this,QuestionListActivity.class);
        startActivity(intent);
    }
    @Override
    public void loginEmpty(){
        Toast.makeText(this,"账号密码不能为空",Toast.LENGTH_SHORT).show();
    }
    @Override
    public void logonFailed(){
        Toast.makeText(this,"账号已存在",Toast.LENGTH_SHORT).show();
    }
    @Override
    public void logonSuccess(){
        Toast.makeText(this, "注册成功", Toast.LENGTH_SHORT).show();
    }
    @Override
    public void logonEmpty(){
        Toast.makeText(this,"账号密码不能为空",Toast.LENGTH_SHORT).show();
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
    public CheckBox getCheckBox(){
        return checkBox;
    }
    @Override
    public EditText setAccount(){
        return accountText;
    }
    @Override
    public EditText setPassword(){
        return  passwordText;
    }
    @Override
    public void firstRunToast(){
        Toast.makeText(this,"欢迎",Toast.LENGTH_SHORT).show();
    }

}
