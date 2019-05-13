package com.example.answer.createRandomExam;

import android.app.ProgressDialog;
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
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.answer.R;


import com.example.answer.myAccount.ManagerAccountActivity;
import com.example.answer.randomExam.RandomExamActivity;
import com.example.answer.util.Contact;
import com.example.answer.util.HttpUtil;
import com.example.answer.util.JsonUtility;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;


public class CreateRandomExamActivity extends AppCompatActivity implements View.OnClickListener{


    public TextView questionText,answerA,answerB,answerC,answerD,showSelect,showCorrect,counterView,description;
    private Button preButton,neButton;
    private static int i=0;
    public static SharedPreferences sharedPreferences;
    private Intent mainActivityIntent;
    private boolean isEnd = false;

    private List<CreateRandomExamBean.MainValueBean> mainValueBeans = new ArrayList<>();

    private ProgressDialog progressDialog;
    private long SPInt = 0;
    private String SPString;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_random_exam);
        Toolbar toolbar = (Toolbar)findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        questionText = (TextView)findViewById(R.id.question_text);
        answerA = (TextView)findViewById(R.id.answer_a);
        answerB = (TextView)findViewById(R.id.answer_b);
        answerC = (TextView)findViewById(R.id.answer_c);
        answerD = (TextView)findViewById(R.id.answer_d);
        showSelect = (TextView)findViewById(R.id.show_select);
        showCorrect = (TextView)findViewById(R.id.show_correct);
        counterView = (TextView)findViewById(R.id.counter_view);
        description = (TextView)findViewById(R.id.description);
        preButton = (Button)findViewById(R.id.pre_button);
        neButton = (Button)findViewById(R.id.ne_button);
        preButton.setOnClickListener(this);
        neButton.setOnClickListener(this);
        answerA.setOnClickListener(this);
        answerB.setOnClickListener(this);
        answerC.setOnClickListener(this);
        answerD.setOnClickListener(this);
        mainActivityIntent = getIntent();
    }

    @Override
    protected void onResume(){
        super.onResume();

        String mainActivityAddress = Contact.createRandomExamUrl + mainActivityIntent.getIntExtra("exam_id_1",0);
        if (mainValueBeans==null||mainValueBeans.size()==0){
            requestAnswerList(mainActivityAddress);
        }else {
            showAnswerList(mainValueBeans,i);
        }




    }
    public void submitAnswer(){
        showProgressDialog();

        //将倒计时设为true，true为不开启倒计时
        sharedPreferences.edit().putBoolean("timer",true).apply();

        sharedPreferences.edit().putBoolean("sure", true).apply();
        answerA.setClickable(false);
        answerB.setClickable(false);
        answerC.setClickable(false);
        answerD.setClickable(false);
        correctTransfer();
        timer.cancel();
        counterView.setText("已提交");
        String []submit = new String[11];
        for (int i=0;i<10;i++){
            submit[i] = String.valueOf(sharedPreferences.getInt(SPString + i,0));
        }
        String url = Contact.submitAddress + SPString + "&userchoice1=" + submit[0] + "&userchoice2=" + submit[1] + "&userchoice3=" + submit[2] + "&userchoice4=" +
                submit[3] + "&userchoice5=" + submit[4] + "&userchoice6=" + submit[5] + "&userchoice7=" + submit[6] + "&userchoice8=" + submit[7] +
                "&userchoice9=" + submit[8] + "&userchoice10=" + submit[9];

        HttpUtil.sendSubmitRequest(CreateRandomExamActivity.this,url,new Callback(){
            @Override
            public void onResponse(@NonNull Call call, @NonNull Response response)throws IOException {
                closeProgressDialog();
                final String submitResponseData = response.body().string();

                SubmitBean submitBean1 = JsonUtility.handleSubmitResponse(submitResponseData);
                if (submitBean1.getCode()==0){
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            getToast("提交成功");
                        }
                    });

                }else {
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            getToast("提交失败");
                        }
                    });

                }
            }
            @Override
            public void onFailure(Call call,IOException e){
                e.printStackTrace();
                closeProgressDialog();
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        getToast("加载失败");
                    }
                });

            }
        });
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
                    getToast("第一题");
                    i++;
                }else {
                    showAnswerList(mainValueBeans,i);
                }
                //前一题是否已答
                if (sharedPreferences.getInt(SPString + i,0) == 0){
                    showSelect.setText("");
                }else {
                    showSelect.setText(String.valueOf(sharedPreferences.getInt(SPString + i,0)));
                }

                break;
            }
            case R.id.ne_button:{
                i++;
                if (i == mainValueBeans.size()){
                    getToast("最后一题");
                    i--;
                }else {
                    showAnswerList(mainValueBeans,i);
                }
                //判断下一题是否已答
                if (sharedPreferences.getInt(SPString + i,0) == 0){
                    showSelect.setText("");
                }else {
                    showSelect.setText(String.valueOf(sharedPreferences.getInt(SPString + i,0)));
                }
                break;
            }
            case R.id.answer_a:{
                showSelect.setText("1");

                sharedPreferences.edit().putInt(SPString + i, 1).apply();

                break;
            }
            case R.id.answer_b:{
                showSelect.setText("2");

                sharedPreferences.edit().putInt(SPString + i, 2).apply();
                break;
            }
            case R.id.answer_c:{
                showSelect.setText("3");

                sharedPreferences.edit().putInt(SPString + i, 3).apply();
                break;
            }
            case R.id.answer_d:{
                showSelect.setText("4");
                sharedPreferences.edit().putInt(SPString + i, 4).apply();
                break;
            }

            default:
                break;
        }
    }
    /**
     * 倒计时器
     */
    private CountDownTimer timer = new CountDownTimer(20000, 1000) {

        @Override
        public void onTick(long millisUntilFinished) {
            counterView.setText("将在"+(millisUntilFinished / 1000) + "秒后自动提交");
        }

        @Override
        public void onFinish() {
            counterView.setEnabled(true);
            AlertDialog.Builder builder = new AlertDialog.Builder(CreateRandomExamActivity.this);
            builder.setTitle("时间结束")
                    .setMessage("已自动提交")
                    .setCancelable(false)
                    .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    submitAnswer();
                }
            }).show();
        }
    };

    private void correctTransfer(){
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                showCorrect.setText(String.valueOf(mainValueBeans.get(i).getCorrectChoice()));
            }
        });


    }

    /**
     * 发起的网络请求
     */
    private CreateRandomExamBean createRandomExamBean;
    public  void requestAnswerList(final String mainActivityAddress){
        showProgressDialog();
        HttpUtil.sendMainActivityRequest(CreateRandomExamActivity.this,mainActivityAddress,new Callback(){
            @Override
            public void onResponse(@NonNull Call call, @NonNull Response response)throws IOException {
                closeProgressDialog();

                final String mainActivityResponseData = response.body().string();
                createRandomExamBean = JsonUtility.handleMainActivityResponse(mainActivityResponseData);
                if (createRandomExamBean != null){
                    mainValueBeans = createRandomExamBean.getValue();
                    showAnswerList(mainValueBeans,0);
                }else {
                    getToast("获取问题信息数据失败");
                }
            }
            @Override
            public void onFailure(Call call,IOException e){
                e.printStackTrace();
                closeProgressDialog();
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        getToast("加载失败");
                    }
                });

            }
        });
    }

    /**
     * 展示界面
     * @param mainValueBeans
     * @param k
     */

    private void showAnswerList(final List<CreateRandomExamBean.MainValueBean> mainValueBeans,final int k){

        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                questionText.setText(mainValueBeans.get(k).getTitle());
                description.setText(mainValueBeans.get(k).getDescription());
                answerA.setText(mainValueBeans.get(k).getAnswer1());
                answerB.setText(mainValueBeans.get(k).getAnswer2());
                answerC.setText(mainValueBeans.get(k).getAnswer3());
                answerD.setText(mainValueBeans.get(k).getAnswer4());

                //获取RandonexamID
                SPInt = mainValueBeans.get(k).getRandonexamID();
                SPString = String.valueOf(SPInt);
                //创建缓存模型
                sharedPreferences =  getSharedPreferences(SPString, 0);

                //true为提交，false为未提交
                if (!sharedPreferences.getBoolean("sure",false)){
                    //false启动倒计时,true不启动倒计时
                    if (!sharedPreferences.getBoolean("timer",false)){
                        timer.start();
                        sharedPreferences.edit().putBoolean("timer",true).apply();
                    }

                }else {
                    answerA.setClickable(false);
                    answerB.setClickable(false);
                    answerC.setClickable(false);
                    answerD.setClickable(false);
                    correctTransfer();
                    if (isEnd){
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                counterView.setText("已结束");
                            }
                        });

                    }else {
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                counterView.setText("已提交");
                            }
                        });

                    }

                }

                //
                if (sharedPreferences.getInt(SPString + i,0) == 0){
                    showSelect.setText("");
                }else {
                    showSelect.setText(String.valueOf(sharedPreferences.getInt(SPString + i,0)));
                }
            }
        });

    }


    /**
     * 返回按钮提交
     */
    @Override
    public void onBackPressed(){
        if (!sharedPreferences.getBoolean("sure",false)) {
            AlertDialog.Builder builder = new AlertDialog.Builder(CreateRandomExamActivity.this);
            builder.setTitle("确定退出吗？")
                    .setMessage("退出后答题自动结束")
                    .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    submitAnswer();
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
     * Display progress dialog;
     */
    private void showProgressDialog(){
        if (progressDialog == null){
            progressDialog = new ProgressDialog(this);
            progressDialog.setMessage("Loading...");
            progressDialog.setCanceledOnTouchOutside(false);
        }
        progressDialog.show();
    }
    /**
     *Close progress dialog;
     */
    private void closeProgressDialog(){
        if (progressDialog != null){
            progressDialog.dismiss();
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
            AlertDialog.Builder builder = new AlertDialog.Builder(CreateRandomExamActivity.this);
            builder.setTitle("确定提交吗？")
                    .setMessage("提交后答题结束")
                    .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    //向服务器提交答题情况
                    submitAnswer();

                }
            });
            builder.setNegativeButton("取消", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                }
            }).show();
        }else {
            getToast("已提交");
        }
    }

    private void getToast(final String s) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                Toast.makeText(CreateRandomExamActivity.this, s, Toast.LENGTH_SHORT).show();
            }
        });
    }

}
