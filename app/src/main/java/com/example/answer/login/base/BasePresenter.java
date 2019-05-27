package com.example.answer.login.base;

public abstract class BasePresenter<V extends IBaseView> {
    protected V mIBaseView;

    public void attachView(V mIBaseView){
        this.mIBaseView = mIBaseView;
    }

    public void detachView(){

        mIBaseView = null;

    }

    public V getView(){
        return mIBaseView;
    }
}
