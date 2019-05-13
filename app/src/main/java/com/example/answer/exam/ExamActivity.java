package com.example.answer.exam;




import android.content.Intent;
import android.support.annotation.NonNull;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.example.answer.R;

import com.example.answer.myAccount.ManagerAccountActivity;
import com.example.answer.randomExam.RandomExamActivity;

import com.example.answer.util.HttpUtil;
import com.example.answer.util.JsonUtility;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;


public class ExamActivity extends AppCompatActivity {

    public  ListView listView;
    public ExamAdapter examAdapter;
    public  List<ExamBean>dataList = new ArrayList<>();
    public SwipeRefreshLayout swipeRefresh;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_exam);
        Toolbar toolbar = (Toolbar)findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        listView = (ListView)findViewById(R.id.list_view);
        swipeRefresh = (SwipeRefreshLayout)findViewById(R.id.swipe_refresh);
        swipeRefresh.setColorSchemeResources(R.color.colorPrimary);
        examAdapter = new ExamAdapter(this,R.layout.item_exam,dataList);
        listView.setAdapter(examAdapter);
        requestQuestionList();
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Date date = new Date();
                date.setTime(System.currentTimeMillis());
                new SimpleDateFormat().format(date);
                ExamBean examBean = dataList.get(position);
                if (System.currentTimeMillis()<valueBeans.get(position).getStartTime()){
                    getToast("未开始");
                }else if (System.currentTimeMillis()>valueBeans.get(position).getEndTime()){
                    //暂时测试
                    Intent intent = new Intent(ExamActivity.this, RandomExamActivity.class);
                    intent.putExtra("exam_id", examBean.getId());
                    intent.putExtra("create_able",false);
                    startActivity(intent);
                }else {
                    Intent intent = new Intent(ExamActivity.this, RandomExamActivity.class);
                    intent.putExtra("exam_id", examBean.getId());
                    intent.putExtra("create_able",true);
                    startActivity(intent);
                }
            }
        });
        swipeRefresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                requestQuestionList();
            }
        });
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_logout, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.account_item:{
                Intent intent = new Intent(ExamActivity.this, ManagerAccountActivity.class);
                startActivity(intent);
                break;
            }
            case android.R.id.home:{
                finish();
                break;
            }
            default:
                break;
        }
        return true;
    }

    /**
     * QuestionListActivity发起的网络请求
     */
    private QuestionListBean questionListBean;
    public void requestQuestionList(){

        HttpUtil.sendExamActivityRequest(ExamActivity.this,new Callback(){

            @Override
            public void onResponse(@NonNull Call call, @NonNull Response response)throws IOException {
                swipeRefresh.setRefreshing(false);
                final String questionListResponseData = response.body().string();
                questionListBean = JsonUtility.handleQuestionListResponse(questionListResponseData);
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        if (questionListBean != null){
                            showQuestionList(questionListBean);
                        }else {
                            getToast("获取问题列表数据失败");
                        }

                    }
                });
            }
            @Override
            public void onFailure(@NonNull Call call,@NonNull IOException e){
                e.printStackTrace();
                getToast("加载失败");
                swipeRefresh.setRefreshing(false);
            }
        });

    }

    private List<QuestionListBean.ValueBean> valueBeans;

    private void showQuestionList(QuestionListBean questionListBean){
        dataList.clear();
        valueBeans = questionListBean.getValue();
        for (int i=0;i<valueBeans.size();i++) {
            Date startTime = new Date();
            Date endTime = new Date();
            startTime.setTime(valueBeans.get(i).getStartTime());
            endTime.setTime(valueBeans.get(i).getEndTime());
            ExamBean examBean = new ExamBean(valueBeans.get(i).getTitle(),"开始时间" +
                    new SimpleDateFormat().format(startTime),"截止时间" + new SimpleDateFormat().format(endTime));

            examBean.setId(valueBeans.get(i).getId());
            dataList.add(examBean);
        }

        examAdapter.notifyDataSetChanged();
        listView.setSelection(0);
    }

    private void getToast(final String s) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                Toast.makeText(ExamActivity.this, s, Toast.LENGTH_SHORT).show();
            }
        });
    }
}
