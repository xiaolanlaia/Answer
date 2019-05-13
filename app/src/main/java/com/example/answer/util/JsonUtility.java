package com.example.answer.util;


import android.content.Context;
import android.os.Build;
import android.provider.Settings;

import com.example.answer.createRandomExam.SubmitBean;
import com.example.answer.myAccount.ChangePasswordBean;
import com.example.answer.createRandomExam.CreateRandomExamBean;
import com.example.answer.login.bean.LoginBean;
import com.example.answer.exam.QuestionListBean;
import com.example.answer.randomExam.RandomExamBean;
import com.example.answer.myAccount.LogoutBean;
import com.example.answer.randomExamAnswer.RandomExamAnswerBean;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;


public class JsonUtility {


    /**
     * 解析LoginActivity
     */
    public static LoginBean handleLoginListResponse(String loginBeanResponseData){

        Gson gson = new Gson();
        java.lang.reflect.Type type = new TypeToken<LoginBean>() {}.getType();
        return gson.fromJson(loginBeanResponseData, type);
    }

    public static ChangePasswordBean handleChangePasswordResponse(String changePasswordResponseData){

        Gson gson = new Gson();
        java.lang.reflect.Type type = new TypeToken<ChangePasswordBean>() {}.getType();
        return gson.fromJson(changePasswordResponseData, type);
    }

    public static LogoutBean handleLogoutResponse(String logoutResponseData){
        Gson gson = new Gson();
        java.lang.reflect.Type type = new TypeToken<LogoutBean>() {}.getType();
        return gson.fromJson(logoutResponseData, type);
    }
    /**
     * 将返回的数据解析成Question实体类
     * @param questionListResponseData
     * @return
     */
    public static QuestionListBean handleQuestionListResponse(String questionListResponseData){

        Gson gson = new Gson();
        java.lang.reflect.Type type = new TypeToken<QuestionListBean>() {}.getType();
        return gson.fromJson(questionListResponseData, type);

    }

    public static RandomExamBean handleRandomExamResponse(String randomExamResponseData){
        Gson gson = new Gson();
        java.lang.reflect.Type type = new TypeToken<RandomExamBean>() {}.getType();
        return gson.fromJson(randomExamResponseData, type);

    }

    public static SubmitBean handleSubmitResponse(String submitResponseData){
        Gson gson = new Gson();
        return gson.fromJson(submitResponseData, SubmitBean.class);

    }

    public static CreateRandomExamBean handleMainActivityResponse(String mainActivityResponse){
        Gson gson = new Gson();
        java.lang.reflect.Type type = new TypeToken<CreateRandomExamBean>() {}.getType();
        return gson.fromJson(mainActivityResponse, type);
    }

    public static RandomExamAnswerBean handleRandomExamAnswerResponse(String randomExamAnswerActivityResponse){
        Gson gson = new Gson();
        java.lang.reflect.Type type = new TypeToken<RandomExamAnswerBean>() {}.getType();
        return gson.fromJson(randomExamAnswerActivityResponse, type);
    }



    //机器码
    public static String getUniqueId2(Context context) {
        String androidID = Settings.Secure.getString(context.getContentResolver(), Settings.Secure.ANDROID_ID);
        String id = androidID + Build.SERIAL;

        try {
            MessageDigest messageDigest = MessageDigest.getInstance("MD5");
            //通过摘要器对字符串的二进制字节数组进行hash计算
            byte[] digest = messageDigest.digest(id.getBytes());
            StringBuilder sb = new StringBuilder();
            for (int i = 0; i < digest.length; i++) {
                //循环每个字符 将计算结果转化为正整数;
                int digestInt = digest[i] & 0xff;
                //将10进制转化为较短的16进制
                String hexString = Integer.toHexString(digestInt);
                //转化结果如果是个位数会省略0,因此判断并补0
                if (hexString.length() < 2) {
                    sb.append(0);
                }
                //将循环结果添加到缓冲区
                sb.append(hexString);
            }
            //返回整个结果
            return sb.toString();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
            return id;
        }
    }

}
