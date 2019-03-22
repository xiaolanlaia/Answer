package com.example.answer.model;

import android.content.Context;
import android.widget.CheckBox;
import android.widget.EditText;

public interface ILoginModel {
    void login(Context context,String account, String password, ILoginListener iLoginListener);
    void logon(String account,String password,ILogonListener iLogonListener);
    void checkChecked(Context context,CheckBox checkBox,String account,String password);
    void restoreChecked(Context context, String account, CheckBox checkBox, EditText accountText, EditText passwordText);
    void firstRun(Context context,IFirstRunListener iFirstRunListener);
}
