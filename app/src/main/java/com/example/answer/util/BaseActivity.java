package com.example.answer.util;

import android.app.Activity;
import android.support.v7.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.List;

public class BaseActivity extends AppCompatActivity {
    public static List<Activity> activities = new ArrayList<Activity>();
    //添加活动
    public static void addActivity(Activity activity){
        activities.add(activity);
    }
    //移除当前活动
    public static void removeActivity(Activity activity){
        activities.remove(activity);
    }
    //销毁所有活动
    public static void finishAll(){
        for(Activity activity: activities){
            if(!activity.isFinishing()){
                activity.finish();
            }
        }
    }

}
