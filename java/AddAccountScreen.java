package com.projects.michaelkim.passwordjournal;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

/**
 * Created by Micha on 10/27/2017.
 */

public class AddAccountScreen extends AppCompatActivity {

    EditText accountField, usernameField, passwordField;
    Button addAccount;

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_account_screen);

        accountField = (EditText) findViewById(R.id.accountField);
        usernameField = (EditText) findViewById(R.id.usernameField);
        passwordField = (EditText) findViewById(R.id.passwordField);

        addAccount = (Button) findViewById(R.id.addAccountButton);

        addAccount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (accountField.getText().toString().equals("") || usernameField.getText().toString().equals("") || passwordField.getText().toString().equals("")){
                    Toast.makeText(getBaseContext(), "Please Enter in All Fields.", Toast.LENGTH_SHORT).show();
                }
                else{
                    Toast.makeText(getBaseContext(), "Account Added.", Toast.LENGTH_SHORT).show();
                    startActivity(new Intent(getBaseContext(), HomeScreen.class));
                }
            }
        });
    }

}
