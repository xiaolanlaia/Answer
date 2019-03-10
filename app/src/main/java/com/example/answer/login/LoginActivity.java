package com.example.answer.login;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.answer.answerQuestion.MainActivity;
import com.example.answer.answerQuestionList.QuestionListActivity;
import com.example.answer.R;
import com.example.answer.db.User;

import org.litepal.LitePal;
import org.litepal.crud.DataSupport;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by W on 2019/2/12.
 */

public class LoginActivity extends AppCompatActivity implements View.OnClickListener{

    TextView accountText;
    TextView passwordText;

    Button login;
    Button logon;



    @Override
    protected void onCreate(Bundle savedInstance){
        super.onCreate(savedInstance);
        setContentView(R.layout.login);
        firstRun();

        accountText = (TextView)findViewById(R.id.account_text);
        passwordText = (TextView)findViewById(R.id.password_text);
        login = (Button)findViewById(R.id.login);
        logon = (Button)findViewById(R.id.logon);

        login.setOnClickListener(this);
        logon.setOnClickListener(this);


    }
    @Override
    public void onClick(View v){

        String account = accountText.getText().toString();
        String password = passwordText.getText().toString();
        switch (v.getId()){
            case R.id.logon: {
                if (TextUtils.isEmpty(account)||TextUtils.isEmpty(password)){
                    Toast.makeText(this,"Account or password is not to be empty",Toast.LENGTH_SHORT).show();
                }else {
                    List<User>list1= DataSupport.findAll(User.class);
                    int i = 1;
                    for (User user : list1){
                        if (account.equals(user.getAccount())){
                            Toast.makeText(this,"Account is already exist",Toast.LENGTH_SHORT).show();
                            break;
                        }else if (i >= list1.size()){
                            User user1 = new User();
                            user1.setAccount(account);
                            user1.setPassword(password);
                            user1.save();
                            Toast.makeText(this, "Logon success", Toast.LENGTH_SHORT).show();
                            break;
                        }
                        i++;
                    }
                }

                break;
            }
            case R.id.login:{
                if (TextUtils.isEmpty(account)||TextUtils.isEmpty(password)){
                    Toast.makeText(this,"account or password is not to be empty",Toast.LENGTH_SHORT).show();

                }else {
                    List<User>list2= DataSupport.findAll(User.class);
                    int i = 1;
                    for (User user : list2){
                        if (user.getAccount().equals(account) && user.getPassword().equals(password)){
                            Intent intent1 = new Intent(LoginActivity.this,QuestionListActivity.class);
                            startActivity(intent1);
                            break;
                        }else if (list2.size() == i){
                            Toast.makeText(this,"Account or password error",Toast.LENGTH_SHORT).show();
                            break;
                        }
                        i++;
                    }
                }
                break;
            }
            default:
                break;
        }
    }
    private void firstRun() {
        SharedPreferences sharedPreferences = getSharedPreferences("FirstRun", 0);
        Boolean firstRun = sharedPreferences.getBoolean("First", true);
        if (firstRun) {
            sharedPreferences.edit().putBoolean("First", false).apply();
            //创建数据库;
            LitePal.getDatabase();
            User user = new User();
            user.setAccount("admin");
            user.setPassword("123456");
            user.save();
            Toast.makeText(this,"Welcome !",Toast.LENGTH_SHORT).show();
        }
    }

}
