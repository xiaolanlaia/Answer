package com.example.answer.answerQuestionList;


import android.content.Intent;
import android.content.SharedPreferences;

import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;

import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;


import com.example.answer.R;
import com.example.answer.answerQuestion.MainActivity;
import com.example.answer.db.Question;
import com.example.answer.db.User;
import com.example.answer.util.HttpUtil;
import com.example.answer.util.Utility;

import org.litepal.LitePal;
import org.litepal.crud.DataSupport;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import okhttp3.Call;

import okhttp3.Callback;

import okhttp3.Response;



public class QuestionListActivity extends AppCompatActivity {

    private ListView listView;
    private QuestionListAdapter questionListAdapter;
    private  String address = "http://10.0.2.2/question_list.json";
    private List<Question>dataList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_question_list);


        listView = (ListView)findViewById(R.id.list_view);

        questionListAdapter = new QuestionListAdapter(this,R.layout.question_item,dataList);
        listView.setAdapter(questionListAdapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Question question = dataList.get(position);
                Toast.makeText(getApplicationContext(),""+question.getTitle(),Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(QuestionListActivity.this, MainActivity.class);
                startActivity(intent);
            }
        });

    }

    @Override
    protected void onResume(){
        super.onResume();
        firstRun();
        queryQuestion();

    }

    public void queryQuestion(){
        List<Question>questionList = DataSupport.findAll(Question.class);
            dataList.clear();
            for (Question question:questionList){
                dataList.add(question);
            }
        questionListAdapter.notifyDataSetChanged();
            listView.setSelection(0);
    }
    /**
     * 网络请求
     */
    private void sendRequestWithOkHttp(final String url){
        HttpUtil.sendOkHttpRequest(url,new Callback(){
            @Override
            public void onResponse(@NonNull Call call,@NonNull Response response)throws IOException{
                String responseData = response.body().string();
                Utility.handleQuestionListResponse(responseData);
            }
            @Override
            public void onFailure(Call call,IOException e){
                e.printStackTrace();
            }
        });
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

            sendRequestWithOkHttp(address);

            Toast.makeText(this,"Welcome !",Toast.LENGTH_SHORT).show();
        }
    }

}
