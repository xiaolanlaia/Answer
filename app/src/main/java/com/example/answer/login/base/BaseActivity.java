package com.example.answer.login.base;

import android.app.Activity;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.example.answer.login.view.ILoginView;

import java.util.ArrayList;
import java.util.List;

public abstract class BaseActivity<V extends IBaseView,P extends BasePresenter<V>> extends AppCompatActivity implements ILoginView {

    public P basePresenter;
    protected abstract P createPresenter();

    public P getPresenter(){
        if (basePresenter == null){
            basePresenter = createPresenter();
        }
        return basePresenter;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        basePresenter = createPresenter();
        basePresenter.attachView((V)this);
    }

    @Override
    protected void onDestroy(){
        basePresenter.detachView();
        super.onDestroy();
    }



}
