package com.example.answer.answerQuestion;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.CountDownTimer;
import android.support.annotation.NonNull;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.answer.R;


import com.example.answer.login.LoginActivity;
import com.example.answer.util.HttpUtil;
import com.example.answer.util.Utility;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;


public class MainActivity extends AppCompatActivity implements View.OnClickListener{

    private String url = "http://192.168.43.120/";
    public TextView questionText,answerA,answerB,answerC,showSelect,showCorrect,counterView;
    private Button preButton,neButton;
    private int i=0;
    private List<Answer> answerList = new ArrayList<>();
    public static SharedPreferences sharedPreferences;
    private Intent intent;
    private boolean isEnd;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar)findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        questionText = (TextView)findViewById(R.id.question_text);
        answerA = (TextView)findViewById(R.id.answer_a);
        answerB = (TextView)findViewById(R.id.answer_b);
        answerC = (TextView)findViewById(R.id.answer_c);
        showSelect = (TextView)findViewById(R.id.show_select);
        showCorrect = (TextView)findViewById(R.id.show_correct);
        counterView = (TextView)findViewById(R.id.counter_view);
        preButton = (Button)findViewById(R.id.pre_button);
        neButton = (Button)findViewById(R.id.ne_button);
        preButton.setOnClickListener(this);
        neButton.setOnClickListener(this);
        answerA.setOnClickListener(this);
        answerB.setOnClickListener(this);
        answerC.setOnClickListener(this);
    }

    @Override
    protected void onResume(){
        super.onResume();
        intent = getIntent();
        String mainActivityAddress = url + intent.getStringExtra("clickTitle") + ".json";
        requestAnswerList(mainActivityAddress);
        sharedPreferences =  getSharedPreferences(LoginActivity.account + intent.getStringExtra("clickTitle"), 0);
        if (intent.getStringExtra("clickState").equals("已结束")){
            sharedPreferences.edit().putBoolean("sure",true).apply();
            isEnd = true;

        }
    }

    /**
     * 点击事件监听
     * @param v
     */
    @Override
    public void onClick(View v){
        switch (v.getId()){
            case R.id.pre_button:{
                i--;
                if (i<0){
                    Toast.makeText(this,"第一题",Toast.LENGTH_SHORT).show();
                    i++;
                }else {
                    showAnswerList(answerList,i);
                    showSelect.setText(sharedPreferences.getString(answerList.get(i).getTitle(), ""));
                }

                if (sharedPreferences.getBoolean("sure",false)){
                    answerA.setClickable(false);
                    answerB.setClickable(false);
                    answerC.setClickable(false);
                    showCorrect.setText(answerList.get(i).getCorrect());
                }
                break;
            }
            case R.id.ne_button:{
                i++;
                if (i == answerList.size()){
                    Toast.makeText(this,"最后一题",Toast.LENGTH_SHORT).show();
                    i--;
                }else {
                    showAnswerList(answerList,i);
                }
                //判断下一题是否已答
                if (TextUtils.isEmpty(sharedPreferences.getString(answerList.get(i).getTitle(),""))){
                    showSelect.setText("");
                }else {
                    showSelect.setText(sharedPreferences.getString(answerList.get(i).getTitle(), ""));
                }
                if (sharedPreferences.getBoolean("sure",false)){
                    answerA.setClickable(false);
                    answerB.setClickable(false);
                    answerC.setClickable(false);
                    showCorrect.setText(answerList.get(i).getCorrect());
                }
                break;
            }
            case R.id.answer_a:{
                showSelect.setText("A");
                sharedPreferences.edit().putString(answerList.get(i).getTitle(), "A").apply();
                break;
            }
            case R.id.answer_b:{
                showSelect.setText("B");
                sharedPreferences.edit().putString(answerList.get(i).getTitle(), "B").apply();
                break;
            }
            case R.id.answer_c:{
                showSelect.setText("C");
                sharedPreferences.edit().putString(answerList.get(i).getTitle(), "C").apply();
                break;
            }
            default:
                break;
        }
    }

    /**
     * 倒计时器
     */
    private CountDownTimer timer = new CountDownTimer(30000, 1000) {

        @Override
        public void onTick(long millisUntilFinished) {
            counterView.setText("将在"+(millisUntilFinished / 1000) + "秒后自动提交");
        }

        @Override
        public void onFinish() {
            counterView.setEnabled(true);
            sharedPreferences.edit().putBoolean("sure", true).apply();
            counterView.setText("已提交");
            answerA.setClickable(false);
            answerB.setClickable(false);
            answerC.setClickable(false);
            showCorrect.setText(answerList.get(i).getCorrect());

            AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
            builder.setTitle("时间结束")
                    .setMessage("已自动提交")
                    .setCancelable(false)
                    .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    sharedPreferences.edit().putBoolean("sure", true).apply();
                    answerA.setClickable(false);
                    answerB.setClickable(false);
                    answerC.setClickable(false);
                    showCorrect.setText(answerList.get(i).getCorrect());
                }
            }).show();
        }
    };

    /**
     * 发起的网络请求
     */
    public  void requestAnswerList(final String mainActivityAddress){
        HttpUtil.sendOkHttpRequest(mainActivityAddress,new Callback(){
            @Override
            public void onResponse(@NonNull Call call, @NonNull Response response)throws IOException {
                final String mainActivityResponseData = response.body().string();
                answerList = Utility.handleMainActivityResponse(mainActivityResponseData);
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        if (answerList != null){
                            showAnswerList(answerList,0);
                        }else {
                            Toast.makeText(MainActivity.this,"获取问题信息数据失败",Toast.LENGTH_SHORT).show();
                        }
                    }
                });

            }
            @Override
            public void onFailure(Call call,IOException e){
                e.printStackTrace();
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(MainActivity.this,"加载失败",Toast.LENGTH_SHORT).show();
                    }
                });

            }
        });
    }

    /**
     * 展示界面
     * @param answers
     * @param k
     */
    private void showAnswerList(List<Answer>answers,int k){
        Answer answer =  answers.get(k);
        questionText.setText(answer.getTitle());
        answerA.setText(answer.getA());
        answerB.setText(answer.getB());
        answerC.setText(answer.getC());

        if (sharedPreferences!=null){
            showSelect.setText(sharedPreferences.getString(answer.getTitle(),null));
        }
        if (sharedPreferences.getBoolean("sure",false)){
            answerA.setClickable(false);
            answerB.setClickable(false);
            answerC.setClickable(false);
            showCorrect.setText(answerList.get(i).getCorrect());
            if (isEnd){
                counterView.setText("已结束");
            }else {
                counterView.setText("已提交");
            }

        }else {
            timer.start();
        }
    }

    /**
     * 从上一个活动传入标题信息
     * @param context
     * @param clickTitle
     */
    public static void actionStart(Context context,String clickTitle,String clickState){
            Intent intent = new Intent(context,MainActivity.class);
            intent.putExtra("clickTitle",clickTitle);
            intent.putExtra("clickState",clickState);
            context.startActivity(intent);

    }

    /**
     * 返回按钮提交
     */
    @Override
    public void onBackPressed(){
        if (!sharedPreferences.getBoolean("sure",false)) {
            AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
            builder.setTitle("确定退出吗？")
                    .setMessage("退出后答题自动结束")
                    .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    sharedPreferences.edit().putBoolean("sure", true).apply();
                    answerA.setClickable(false);
                    answerB.setClickable(false);
                    answerC.setClickable(false);
                    showCorrect.setText(answerList.get(i).getCorrect());
                    timer.cancel();
                    counterView.setText("已提交");
                }
            });
            builder.setNegativeButton("取消", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                }
            }).show();
        }else {
            finish();
        }
    }

    /**
     * 菜单栏提交
     * @param menu
     * @return
     */
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.commit_item:{
                commit();
                break;
            }
            case android.R.id.home:{
                onBackPressed();
            }
            default:
                break;
        }
        return true;
    }

    private void commit(){
        if (!sharedPreferences.getBoolean("sure",false)) {
            AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
            builder.setTitle("确定提交吗？")
                    .setMessage("提交后答题结束")
                    .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    sharedPreferences.edit().putBoolean("sure", true).apply();
                    answerA.setClickable(false);
                    answerB.setClickable(false);
                    answerC.setClickable(false);
                    showCorrect.setText(answerList.get(i).getCorrect());
                    timer.cancel();
                    counterView.setText("已提交");

                }
            });
            builder.setNegativeButton("取消", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                }
            }).show();
        }else {
            Toast.makeText(MainActivity.this,"已提交",Toast.LENGTH_SHORT).show();
        }
    }

}
