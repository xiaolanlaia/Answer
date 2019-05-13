package com.example.answer.util;

import android.content.Context;

import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;

public class HttpUtil {
    public static void sendLoginRequest(Context context,String account,String password,okhttp3.Callback callback){
        OkHttpClient okHttpClient = new OkHttpClient();
        RequestBody body = new FormBody.Builder()
                .add("username", account)
                .add("password", password)
                .build();

        Request request = new Request.Builder()
                .url(Contact.loginAddress)
                .addHeader("token", JsonUtility.getUniqueId2(context))
                .post(body)
                .build();

        okHttpClient.newCall(request).enqueue(callback);
    }

    public static void sendLogoutRequest(Context context,Callback callback){
        OkHttpClient okHttpClient = new OkHttpClient();
        RequestBody body = new FormBody.Builder().build();

        Request request = new Request.Builder()
                .url(Contact.logoutAddress)
                .addHeader("token", JsonUtility.getUniqueId2(context))
                .post(body)
                .build();

        okHttpClient.newCall(request).enqueue(callback);
    }

    public static void sendChangePassword(Context context,String oldPassword,String newPassword,Callback callback){
        OkHttpClient okHttpClient = new OkHttpClient();
        RequestBody body = new FormBody.Builder()
                .add("oldPassword",oldPassword)
                .add("newPassword",newPassword)
                .build();

        Request request = new Request.Builder()
                .url(Contact.changePasswordAddress)
                .addHeader("token", JsonUtility.getUniqueId2(context))
                .post(body)
                .build();

        okHttpClient.newCall(request).enqueue(callback);
    }

    public static void sendExamActivityRequest(Context context,okhttp3.Callback callback){
        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder().url(Contact.examActivityAddress).addHeader("token", JsonUtility.getUniqueId2(context)).build();
        client.newCall(request).enqueue(callback);
    }

    public static void sendRandomExamRequest(Context context,String address, okhttp3.Callback callback){
        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder().url(address).addHeader("token", JsonUtility.getUniqueId2(context)).build();
        client.newCall(request).enqueue(callback);
    }

    public static void sendMainActivityRequest(Context context,String address, okhttp3.Callback callback){
        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder().url(address).addHeader("token", JsonUtility.getUniqueId2(context)).build();
        client.newCall(request).enqueue(callback);
    }

    public static void sendSubmitRequest(Context context,String address, okhttp3.Callback callback){
        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder().url(address).addHeader("token", JsonUtility.getUniqueId2(context)).build();
        client.newCall(request).enqueue(callback);
    }

}
