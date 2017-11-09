package com.projects.michaelkim.passwordjournal;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;

public class HomeScreen extends AppCompatActivity {

    ListView passwordList;
    ArrayList<String> accountValues = new ArrayList<>();
    ArrayList<String> usernameValues = new ArrayList<>();
    ArrayList<String> passwordValues = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_screen);

        accountValues.add("Chase");
        usernameValues.add("Username 1");
        passwordValues.add("Password 1");
        accountValues.add("Dunkin' Donuts");
        usernameValues.add("Username 2");
        passwordValues.add("sample 2");
        accountValues.add("Hotmail");
        usernameValues.add("Username 3");
        passwordValues.add("sample 3");
        accountValues.add("Starbucks");
        usernameValues.add("abcabc");
        passwordValues.add("ahhhhhh");

        passwordList = (ListView) findViewById(R.id.passwordList);
        ArrayAdapter<String> listAdapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, accountValues);
        passwordList.setAdapter(listAdapter);
    }
}
