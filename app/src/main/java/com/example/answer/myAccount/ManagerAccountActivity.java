package com.example.answer.myAccount;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.answer.R;
import com.example.answer.login.view.LoginActivity;
import com.example.answer.util.BaseActivity;
import com.example.answer.util.HttpUtil;
import com.example.answer.util.JsonUtility;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;

import okhttp3.Response;

public class ManagerAccountActivity extends BaseActivity implements View.OnClickListener{

    private Button change_password;
    private Button logout;
    private EditText old_password_text,new_password_text;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_manager_account);
        addActivity(this);
        Toolbar toolbar = (Toolbar)findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        change_password = (Button)findViewById(R.id.change_password);
        logout = (Button)findViewById(R.id.logout);
        change_password.setOnClickListener(this);
        old_password_text = (EditText)findViewById(R.id.old_password_text) ;
        new_password_text = (EditText)findViewById(R.id.new_password_text) ;
        logout.setOnClickListener(this);
    }

    @Override
    protected void onDestroy(){
        super.onDestroy();
        finishAll();
    }
    @Override
    public void onClick(View view){
        switch (view.getId()){
            case R.id.change_password:{
                changePassword();
                break;
            }
            case R.id.logout:{
                logout();
                break;
            }
            default:
                break;
        }
    }

    private void logout(){

        HttpUtil.sendLogoutRequest(ManagerAccountActivity.this, new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                getToast("网络访问错误");
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                final String logoutResponseData = response.body().string();//string不能调用两次 被调用一次就关闭了，这里调用两次会报异常

                LogoutBean logoutBean = JsonUtility.handleLogoutResponse(logoutResponseData);
                int code = logoutBean.getCode();
                if (code == 0){
                    SharedPreferences sharedPreferences = getSharedPreferences("CheckLogin",0);
                    sharedPreferences.edit().putBoolean("haveLogin",false).apply();
                    Intent intent = new Intent(ManagerAccountActivity.this, LoginActivity.class);
                    startActivity(intent);
                    finish();
                }else {
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            SharedPreferences sharedPreferences = getSharedPreferences("CheckLogin",0);
                            sharedPreferences.edit().putBoolean("haveLogin",false).apply();
                            Intent intent = new Intent(ManagerAccountActivity.this, LoginActivity.class);
                            startActivity(intent);
                            finish();
                        }
                    });
                }
            }
        });

    }


    private ChangePasswordBean changePasswordBean;
    private void changePassword(){
        if (TextUtils.isEmpty(old_password_text.getText().toString())||TextUtils.isEmpty(new_password_text.getText().toString())){
            getToast("账号密码不能为空");
        }else {

            HttpUtil.sendChangePassword(ManagerAccountActivity.this, old_password_text.getText().toString(), new_password_text.getText().toString(), new Callback() {
                @Override
                public void onFailure(Call call, IOException e) {
                    getToast("网络访问错误");
                }
                @Override
                public void onResponse(Call call, Response response) throws IOException {
                    final String changePasswordResponseData = response.body().string();//string不能调用两次 被调用一次就关闭了，这里调用两次会报异常
                    changePasswordBean = JsonUtility.handleChangePasswordResponse(changePasswordResponseData);
                    if (changePasswordBean.getCode() == 0){
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                getToast("修改密码成功，请重新登录");
                            }
                        });
                        Intent intent = new Intent(ManagerAccountActivity.this,LoginActivity.class);
                        startActivity(intent);
                        finish();
                    }else {
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                getToast("账号或密码错误");
                            }
                        });
                    }
                }
            });
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case android.R.id.home:{
                onBackPressed();
            }
            default:
                break;
        }
        return true;
    }

    private void getToast(final String s) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                Toast.makeText(ManagerAccountActivity.this, s, Toast.LENGTH_SHORT).show();
            }
        });
    }




}
