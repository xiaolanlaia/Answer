package com.example.answer.db;

import org.litepal.crud.DataSupport;

/**
 * Created by W on 2019/2/25.
 */

public class User extends DataSupport{

    private String account;
    private String password;

    public String getAccount() {
        return account;
    }

    public void setAccount(String account) {
        this.account = account;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
