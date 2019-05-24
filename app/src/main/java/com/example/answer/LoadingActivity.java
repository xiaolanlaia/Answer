package com.example.answer;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.example.answer.exam.ExamActivity;
import com.example.answer.login.view.LoginActivity;

public class LoadingActivity extends AppCompatActivity {

    private SharedPreferences sharedPreferences;
    private Intent intent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_loading);
        sharedPreferences = getSharedPreferences("CheckLogin",0);
    }

    @Override
    protected void onResume(){
        super.onResume();
        if (sharedPreferences.getBoolean("haveLogin",false)){
            intent = new Intent(LoadingActivity.this, ExamActivity.class);
            startActivity(intent);
            finish();
        }else {
            intent = new Intent(LoadingActivity.this, LoginActivity.class);
            startActivity(intent);
            finish();
        }
    }
}
