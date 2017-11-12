package com.projects.michaelkim.passwordjournal;

/**
 * Created by Michael Kim on 11/8/2017.
 */

public class account {

    String account, username, password;

    public account (String a, String u, String p){
        this.account = a;
        this.username = u;
        this.password = p;
    }

    public String getAccount() {
        return account;
    }

    public void setAccount(String account) {
        this.account = account;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
