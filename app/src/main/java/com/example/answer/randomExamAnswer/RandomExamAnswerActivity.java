package com.example.answer.randomExamAnswer;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.answer.R;



import com.example.answer.util.Contact;
import com.example.answer.util.HttpUtil;
import com.example.answer.util.JsonUtility;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;


public class RandomExamAnswerActivity extends AppCompatActivity implements View.OnClickListener{


    public TextView questionText,answerA,answerB,answerC,answerD,showSelect,showCorrect,counterView,description;
    private Button preButton,neButton;
    private static int i=0;
    private Intent mainActivityIntent;

    private List<RandomExamAnswerBean.RandomAnswerValueBean> randomAnswerValueBeans = new ArrayList<>();
    private ProgressDialog progressDialog;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_random_exam_answer);
        Toolbar toolbar = (Toolbar)findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        questionText = (TextView)findViewById(R.id.question_text);
        showSelect = (TextView)findViewById(R.id.show_select);
        showCorrect = (TextView)findViewById(R.id.show_correct);
        counterView = (TextView)findViewById(R.id.counter_view);
        description = (TextView)findViewById(R.id.description);
        preButton = (Button)findViewById(R.id.pre_button);
        neButton = (Button)findViewById(R.id.ne_button);
        neButton.setOnClickListener(this);
        preButton.setOnClickListener(this);
        answerA = (TextView) findViewById(R.id.answer_a);
        answerB = (TextView) findViewById(R.id.answer_b);
        answerD = (TextView) findViewById(R.id.answer_c);
        answerC = (TextView) findViewById(R.id.answer_d);

        mainActivityIntent = getIntent();
    }

    @Override
    protected void onResume(){
        super.onResume();
        String mainActivityAddress = Contact.randomExamAnswerUrl + mainActivityIntent.getLongExtra("random_exam_id",0);
        requestAnswerList(mainActivityAddress);

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
                    showRandomExamAnswer(randomAnswerValueBeans,i);
                }
                break;
            }
            case R.id.ne_button:{
                i++;
                if (i == randomAnswerValueBeans.size()){
                    getToast("最后一题");
                    i--;
                }else {
                    showRandomExamAnswer(randomAnswerValueBeans,i);
                }
                break;
            }

            default:
                break;
        }
    }


    private void correctTransfer(){
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                showCorrect.setText(String.valueOf(randomAnswerValueBeans.get(i).getCorrectChoice()));
                showSelect.setText(String.valueOf(randomAnswerValueBeans.get(i).getUserchoice()));
            }
        });
    }

    /**
     * 发起的网络请求
     */
    private RandomExamAnswerBean randomExamAnswerBean;
    public  void requestAnswerList(final String mainActivityAddress){
        showProgressDialog();
        HttpUtil.sendMainActivityRequest(RandomExamAnswerActivity.this,mainActivityAddress,new Callback(){
            @Override
            public void onResponse(@NonNull Call call, @NonNull Response response)throws IOException {
                closeProgressDialog();

                final String randomExamAnswerResponseData = response.body().string();
                randomExamAnswerBean = JsonUtility.handleRandomExamAnswerResponse(randomExamAnswerResponseData);
                if (randomExamAnswerBean != null){
                    randomAnswerValueBeans = randomExamAnswerBean.getValue();
                    showRandomExamAnswer(randomAnswerValueBeans,0);
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
     * @param RandomAnswerValueBeans
     * @param k
     */

    private void showRandomExamAnswer(final List<RandomExamAnswerBean.RandomAnswerValueBean> RandomAnswerValueBeans,final int k){

        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                questionText.setText(RandomAnswerValueBeans.get(k).getTitle());
                description.setText(RandomAnswerValueBeans.get(k).getDescription());
                answerA.setText(RandomAnswerValueBeans.get(k).getAnswer1());
                answerB.setText(RandomAnswerValueBeans.get(k).getAnswer2());
                answerC.setText(RandomAnswerValueBeans.get(k).getAnswer3());
                answerD.setText(RandomAnswerValueBeans.get(k).getAnswer4());
                correctTransfer();
                counterView.setText("已提交");

            }
        });

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
     * @param
     * @return
     */


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
                Toast.makeText(RandomExamAnswerActivity.this, s, Toast.LENGTH_SHORT).show();
            }
        });
    }



}
