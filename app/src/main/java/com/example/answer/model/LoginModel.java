package com.example.answer.model;

import android.content.Context;
import android.content.SharedPreferences;
import android.text.TextUtils;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;

import com.example.answer.db.User;

import org.litepal.LitePal;
import org.litepal.crud.DataSupport;
import java.util.List;
import static com.example.answer.view.answerQuestion.MainActivity.sharedPreferences;

public class LoginModel implements ILoginModel{

    @Override
    public void login(Context context,final String account, final String password, final ILoginListener iLoginListener){

        if (TextUtils.isEmpty(account)||TextUtils.isEmpty(password)){
            iLoginListener.loginEmpty();
        }else {
            List<User> list= DataSupport.findAll(User.class);
            int i = 1;
            for (User user : list){
                if (user.getAccount().equals(account) && user.getPassword().equals(password)){
                    sharedPreferences = context.getSharedPreferences("checked",0);
                    sharedPreferences.edit().putString("account",account).apply();
                    sharedPreferences.edit().putString("password",password).apply();
                    iLoginListener.loginSucceed();
                    break;
                }
                if (list.size() == i){
                    iLoginListener.loginFailed();
                    break;
                }
                i++;
            }
        }
    }
    @Override
    public void logon(final String account,final String password,ILogonListener iLogonListener){
        if (TextUtils.isEmpty(account)||TextUtils.isEmpty(password)){
            iLogonListener.logonEmpty();
        }else {
            List<User> list1= DataSupport.findAll(User.class);
            int i = 1;
            for (User user : list1){
                if (account.equals(user.getAccount())){
                    iLogonListener.logonFailed();
                    break;
                }else if (i >= list1.size()){
                    User user1 = new User();
                    user1.setAccount(account);
                    user1.setPassword(password);
                    user1.save();
                    iLogonListener.logonSucceed();
                    break;
                }
                i++;
            }
        }
    }

    @Override
    public void checkChecked(final Context context, final CheckBox checkBox, final String account, final String password){
        checkBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                sharedPreferences = context.getSharedPreferences("checked",0);
                if (checkBox.isChecked()){
                    sharedPreferences.edit().putString("account",account).apply();
                    sharedPreferences.edit().putString("password",password).apply();
                    sharedPreferences.edit().putBoolean("check",true).apply();
                }else {
                    sharedPreferences.edit().putBoolean("check",false).apply();
                }
            }
        });
    }

    @Override
    public void restoreChecked(Context context, String account, CheckBox checkBox, EditText accountText,EditText passwordText){
        sharedPreferences = context.getSharedPreferences("checked",0);
        boolean checked = sharedPreferences.getBoolean("check",false);
        if (checked){
            accountText.setText(sharedPreferences.getString("account",""));
            passwordText.setText(sharedPreferences.getString("password",""));
            accountText.setSelection(account.length());
            checkBox.setChecked(true);
        }else {
            checkBox.setChecked(false);
        }

    }

    @Override
    public void firstRun(Context context,IFirstRunListener iFirstRunListener){
        SharedPreferences sharedPreferences =  context.getSharedPreferences("FirstRun", 0);
        Boolean firstRun = sharedPreferences.getBoolean("First", true);
        if (firstRun) {
            sharedPreferences.edit().putBoolean("First", false).apply();
            //创建数据库;
            LitePal.getDatabase();
            User user = new User();
            user.setAccount("admin");
            user.setPassword("123456");
            user.save();
            iFirstRunListener.firstRun();
        }
    }
}
