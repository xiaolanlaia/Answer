package com.example.answer.randomExam;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v4.widget.SwipeRefreshLayout;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.example.answer.util.BaseActivity;
import com.example.answer.R;
import com.example.answer.createRandomExam.CreateRandomExamActivity;

import com.example.answer.randomExamAnswer.RandomExamAnswerActivity;

import com.example.answer.util.Contact;
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

public class RandomExamActivity extends BaseActivity {

    public ListView listView;
    public RandomExamAdapter randomExamAdapter;
    public  String randomExamActivityAddress;
    public List<RandomExam> dataList = new ArrayList<>();
    private List<RandomExamBean.RandomExamValue> valueBeans;
    private RandomExamBean randomExamBean;
    public SwipeRefreshLayout swipeRefresh;
    private Intent randomExamIntent;
    private boolean createAble = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_exam);
        addActivity(this);
        Toolbar toolbar = (Toolbar)findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        listView = (ListView)findViewById(R.id.list_view);
        swipeRefresh = (SwipeRefreshLayout)findViewById(R.id.swipe_refresh);
        swipeRefresh.setColorSchemeResources(R.color.colorPrimary);
        randomExamAdapter = new RandomExamAdapter(this,R.layout.item_random_exam,dataList);
        listView.setAdapter(randomExamAdapter);

        randomExamIntent = getIntent();
        createAble = randomExamIntent.getBooleanExtra("create_able",false);
        randomExamActivityAddress = Contact.randomExamUrl + randomExamIntent.getIntExtra("exam_id",0);


        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent1 = new Intent(RandomExamActivity.this, RandomExamAnswerActivity.class);
                intent1.putExtra("exam_id_1",randomExamIntent.getIntExtra("exam_id",0));
                intent1.putExtra("random_exam_id",valueBeans.get(position).getId());
                startActivity(intent1);
            }
        });
        swipeRefresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {

                sendRandomExamRequest(randomExamActivityAddress);
            }
        });

    }
    @Override
    protected void onDestroy(){
        super.onDestroy();
        removeActivity(this);
    }
    @Override
    protected void onResume(){
        super.onResume();
        sendRandomExamRequest(randomExamActivityAddress);

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.create_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.create_item:{
                if (createAble){
                    Intent intent1 = new Intent(RandomExamActivity.this, CreateRandomExamActivity.class);
                    intent1.putExtra("exam_id_1",randomExamIntent.getIntExtra("exam_id",0));
                    startActivity(intent1);
                }else {
                    getToast("答题时间已过");
                }


                break;
            }
            case android.R.id.home:{

                finish();
            }
            default:
                break;
        }
        return true;
    }

    /**
     * RandomExamActivity发起的网络请求
     */
    public void sendRandomExamRequest(final String randomExamActivityAddress){

        HttpUtil.sendRandomExamRequest(RandomExamActivity.this,randomExamActivityAddress,new Callback(){
            @Override
            public void onResponse(@NonNull Call call, @NonNull Response response)throws IOException {
                swipeRefresh.setRefreshing(false);
                final String randomExamResponseData = response.body().string();

                randomExamBean = JsonUtility.handleRandomExamResponse(randomExamResponseData);

                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        if (randomExamBean != null){
                            showRandomExamList(randomExamBean);
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

    private void showRandomExamList(RandomExamBean randomExamBean){
        dataList.clear();
        valueBeans = randomExamBean.getValue();

        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                for (int i=0;i<valueBeans.size();i++) {
                    RandomExam randomExam;
                    Date submitTime = new Date();
                    if (valueBeans.get(i).getMark() != -1){
                        submitTime.setTime(valueBeans.get(i).getSubmitTime());
                        randomExam = new RandomExam("题目编号：" + valueBeans.get(i).getId(),"提交时间：" + new SimpleDateFormat().format(submitTime),"分数：" + valueBeans.get(i).getMark());
                        dataList.add(randomExam);

                    }

                }
            }
        });

        randomExamAdapter.notifyDataSetChanged();
        listView.setSelection(0);
    }
    private void getToast(final String s) {
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    Toast.makeText(RandomExamActivity.this, s, Toast.LENGTH_SHORT).show();
                }
            });
    }
}
