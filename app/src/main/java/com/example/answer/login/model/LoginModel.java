package com.example.answer.login.model;

import android.content.Context;
import android.content.SharedPreferences;
import android.text.TextUtils;

import com.example.answer.login.bean.LoginBean;
import com.example.answer.util.HttpUtil;
import com.example.answer.util.JsonUtility;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;


public class LoginModel implements ILoginModel{

    SharedPreferences sharedPreferences;

    private LoginBean loginBean;
    @Override
    public void login(final Context context, final String account, final String password, final ILoginListener iLoginListener){
        if (TextUtils.isEmpty(account)||TextUtils.isEmpty(password)){
            iLoginListener.loginEmpty();
        }else {
            HttpUtil.sendLoginRequest(context,account,password,new Callback() {
                @Override
                public void onFailure(Call call, IOException e) {
                    iLoginListener.networkFailed();
                }

                @Override
                public void onResponse(Call call, Response response) throws IOException {
                    final String loginBeanResponseData = response.body().string();//string不能调用两次 被调用一次就关闭了，这里调用两次会报异常

                    loginBean = JsonUtility.handleLoginListResponse(loginBeanResponseData);
                    int code = loginBean.getCode();
                    if (code == 0){
                        iLoginListener.loginSucceed();
                    }else {
                        iLoginListener.loginFailed();


                    }
                }
            });

        }
    }

    @Override
    public void checkChecked(boolean isChecked,final Context context, final String account, final String password){
        sharedPreferences = context.getSharedPreferences("checked",0);
        if (isChecked){
            sharedPreferences.edit().putString("account",account).apply();
            sharedPreferences.edit().putString("password",password).apply();
            sharedPreferences.edit().putBoolean("check",true).apply();
        }else {
            sharedPreferences.edit().putBoolean("check",false).apply();
        }
    }

    @Override
    public void restoreChecked(Context context,IRestoreCheckListener iRestoreCheckListener){
        sharedPreferences = context.getSharedPreferences("checked",0);
        boolean checked = sharedPreferences.getBoolean("check",false);
        if (checked){
            iRestoreCheckListener.doRestore();

        }else {
            iRestoreCheckListener.noRestore();

        }

    }

    @Override
    public void firstRun(Context context,IFirstRunListener iFirstRunListener){
        sharedPreferences =  context.getSharedPreferences("FirstRun", 0);
        boolean firstRun = sharedPreferences.getBoolean("First", true);
        if (firstRun) {
            sharedPreferences.edit().putBoolean("First", false).apply();
            iFirstRunListener.firstRun();
        }
    }
}
