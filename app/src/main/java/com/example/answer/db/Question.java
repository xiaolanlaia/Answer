package com.example.answer.db;

import org.litepal.crud.DataSupport;

public class Question extends DataSupport {

    private String title;
    private String state;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

}
