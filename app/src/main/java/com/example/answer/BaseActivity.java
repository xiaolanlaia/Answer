package com.example.answer;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.widget.Toast;


import com.example.answer.db.User;


import org.litepal.LitePal;

public class BaseActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        firstRun(getApplicationContext());

    }
    public static void firstRun(Context context) {
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
            Toast.makeText(context.getApplicationContext(),"Welcome !",Toast.LENGTH_SHORT).show();
        }
    }
}
