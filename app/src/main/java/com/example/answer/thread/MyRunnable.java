package com.example.answer.thread;

import com.example.answer.answerQuestionList.QuestionListActivity;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

/**
 *
 * Created by W on 2019/3/7.
 */



public class MyRunnable implements Runnable {
    @Override
    public void run() {
        try{
            OkHttpClient client = new OkHttpClient();
            Request request = new Request.Builder()
                    .url("http://127.0.0.1/question_list.json")
                    .build();
            Response response = client.newCall(request).execute();
            String responseData = response.body().string();
        }catch (Exception e){
            e.printStackTrace();
        }
    }
}


