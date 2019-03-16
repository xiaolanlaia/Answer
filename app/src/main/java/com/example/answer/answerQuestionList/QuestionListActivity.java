package com.example.answer.answerQuestionList;



import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.annotation.NonNull;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;
import com.example.answer.BaseActivity;
import com.example.answer.R;
import com.example.answer.answerQuestion.MainActivity;
import com.example.answer.db.Question;
import com.example.answer.util.HttpUtil;
import com.example.answer.util.Utility;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;


public class QuestionListActivity extends BaseActivity{

    public  ListView listView;
    public  QuestionListAdapter questionListAdapter;
    public  String questionListActivityAddress = "http://192.168.43.120/question_list.json";
    public  List<Question>dataList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_question_list);
        Toolbar toolbar = (Toolbar)findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        listView = (ListView)findViewById(R.id.list_view);
        questionListAdapter = new QuestionListAdapter(this,R.layout.question_item,dataList);
        listView.setAdapter(questionListAdapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Question question = dataList.get(position);
                if (question.getState().equals("正在进行")){
                    MainActivity.actionStart(QuestionListActivity.this,question.getTitle());
                }else if (question.getState().equals("未开始")){
                    Toast.makeText(QuestionListActivity.this,"未开始",Toast.LENGTH_SHORT).show();
                }else if (question.getState().equals("已结束")){
                    Toast.makeText(QuestionListActivity.this,"已结束",Toast.LENGTH_SHORT).show();
                }else {
                    Toast.makeText(QuestionListActivity.this,"发生错误",Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    @Override
    protected void onResume(){
        super.onResume();
        SharedPreferences questionListPref = PreferenceManager.getDefaultSharedPreferences(this);
        String questionListString = questionListPref.getString("questionList",null);
        if (questionListString != null){
            List<Question> questionList = Utility.handleQuestionListResponse(questionListString);
            showQuestionList(questionList);
        }else {
            requestQuestionList(questionListActivityAddress);
        }
    }

    public boolean onOptionsItemSelected(MenuItem item){
        if(item.getItemId() == android.R.id.home)
        {
            finish();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    /**
     * QuestionListActivity发起的网络请求
     */
    public void requestQuestionList(final String questionListActivityAddress){
        HttpUtil.sendOkHttpRequest(questionListActivityAddress,new Callback(){
            @Override
            public void onResponse(@NonNull Call call, @NonNull Response response)throws IOException {
                final String questionListResponseData = response.body().string();
                final List<Question> questionList = Utility.handleQuestionListResponse(questionListResponseData);
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        if (questionList != null){
                            showQuestionList(questionList);
                        }else {
                            Toast.makeText(QuestionListActivity.this,"获取问题列表数据失败",Toast.LENGTH_SHORT).show();
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
                        Toast.makeText(QuestionListActivity.this,"获取数据失败",Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });
    }

    private void showQuestionList(List<Question> list){
        dataList.clear();
        for (int i=0;i<list.size();i++){
            dataList.add(list.get(i));
        }
        questionListAdapter.notifyDataSetChanged();
        listView.setSelection(0);
    }
}
