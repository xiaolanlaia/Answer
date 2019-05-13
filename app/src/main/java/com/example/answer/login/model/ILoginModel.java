package com.example.answer.login.model;

import android.content.Context;

public interface ILoginModel {
    void login(Context context,String account, String password, ILoginListener iLoginListener);
    void checkChecked(boolean isChecked,Context context,String account,String password);
    void restoreChecked(Context context,IRestoreCheckListener iRestoreCheckListener);
    void firstRun(Context context,IFirstRunListener iFirstRunListener);
}
