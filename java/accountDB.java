package com.projects.michaelkim.passwordjournal;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by Michael Kim on 11/13/2017.
 */

public class accountDB extends SQLiteOpenHelper{

    // Database Information.
    private static final String DATABASE_NAME = "ACCOUNT.DB";
    private static final int DATABASE_VERSION = 1;
    private static final String CREATE_QUERY =
            "CREATE TABLE " + account.accountDBManager.TABLE_NAME +
                    "(" + account.accountDBManager.ACCOUNT_NAME + " TEXT," +
                    account.accountDBManager.USER_NAME + " TEXT," +
                    account.accountDBManager.PASSWORD + " TEXT);";

    // Database Constructor.
    public accountDB(Context context){
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    // Create the table.
    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL(CREATE_QUERY);
    }

    // Method to add the information into the database.
    public void addAccount(String accountName, String username, String password, SQLiteDatabase db){

        ContentValues contentValues = new ContentValues();
        contentValues.put(account.accountDBManager.ACCOUNT_NAME, accountName);
        contentValues.put(account.accountDBManager.USER_NAME, username);
        contentValues.put(account.accountDBManager.PASSWORD, password);

        db.insert(account.accountDBManager.TABLE_NAME, null, contentValues);
    }

    // Method to receive information from the database.
    public Cursor getAccountInformation(SQLiteDatabase db){

        Cursor cursor;
        String[] projections = {
                account.accountDBManager.ACCOUNT_NAME,
                account.accountDBManager.USER_NAME,
                account.accountDBManager.PASSWORD
        };
        cursor = db.query(account.accountDBManager.TABLE_NAME, projections, null, null, null, null, null);
        return cursor;

    }

    public void deleteAccount (String accountName, SQLiteDatabase sqLiteDatabase){

        String selection = account.accountDBManager.ACCOUNT_NAME + " LIKE ?";
        String[] selection_args = {accountName};
        sqLiteDatabase.delete(account.accountDBManager.TABLE_NAME, selection, selection_args);

    }

    public int updateAccount(String oldAccount, String newAccount, String newUsername, String newPassword, SQLiteDatabase sqLiteDatabase){

        ContentValues contentValues = new ContentValues();
        contentValues.put(account.accountDBManager.ACCOUNT_NAME, newAccount);
        contentValues.put(account.accountDBManager.USER_NAME, newUsername);
        contentValues.put(account.accountDBManager.PASSWORD, newPassword);
        String selection = account.accountDBManager.ACCOUNT_NAME + " LIKE ?";
        String[] selection_args = {oldAccount};
        int count = sqLiteDatabase.update(account.accountDBManager.TABLE_NAME, contentValues, selection, selection_args);
        return count;

    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }
}
