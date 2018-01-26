package com.projects.michaelkim.passwordjournal;

/**
 * Created by Michael Kim on 11/8/2017.
 */

public class account implements Comparable<account> {

    String account, username, password;

    public account (String a, String u, String p){
        this.account = a;
        this.username = u;
        this.password = p;
    }

    public static abstract class accountDBManager {
        public static final String ACCOUNT_NAME = "account";
        public static final String USER_NAME = "username";
        public static final String PASSWORD = "password";
        public static final String TABLE_NAME = "table_info";
    }

    @Override
    public int compareTo(account account){
        int compare = this.account.compareTo(account.account);

        return compare;
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
