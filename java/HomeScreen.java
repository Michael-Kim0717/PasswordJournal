package com.projects.michaelkim.passwordjournal;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.media.Image;
import android.os.Handler;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.Html;
import android.text.TextWatcher;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class HomeScreen extends AppCompatActivity {

    // Variable declaration.
    ListView passwordList;
    ArrayList<String> accountValues = new ArrayList<>();
    ArrayList<account> accounts = new ArrayList<>();
    ArrayAdapter<String> listAdapter;
    ActionBar actionBar;

    int pinNumber = 1;

    // SQLite Database Variables.
    accountDB accountDB;
    SQLiteDatabase sqLiteDatabase;
    Context context;
    Cursor cursor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_screen);

        actionBar = getSupportActionBar();
        actionBar.setHomeButtonEnabled(true);
        actionBar.setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
        actionBar.setCustomView(R.layout.title_bar_layout);

        context = getApplicationContext();

        // Create an adapter that fills the listview with just the account names.
        passwordList = (ListView) findViewById(R.id.passwordList);
        listAdapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, accountValues);
        passwordList.setAdapter(listAdapter);

        // For showing the information within a listview.
        accountDB = new accountDB(context);
        sqLiteDatabase = accountDB.getReadableDatabase();
        cursor = accountDB.getAccountInformation(sqLiteDatabase);
        if (cursor.moveToFirst()){
            do{
                account accountInDB = new account(cursor.getString(0), cursor.getString(1), cursor.getString(2));
                accounts.add(accountInDB);
                accountValues.add(cursor.getString(0));
            }
            while (cursor.moveToNext());
        }

        passwordList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, final int listViewPosition, long l) {
                SharedPreferences sharedPreferences = getSharedPreferences("PINNUM", Context.MODE_PRIVATE);

                pinNumber = sharedPreferences.getInt("PIN", 0);

                // If the user has no pin entered,
                // Display the selected account information.
                if (pinNumber == 0){
                    showDetails(listViewPosition);
                }
                else {
                    final AlertDialog.Builder enterPin = new AlertDialog.Builder(HomeScreen.this);

                    // Information for the enter pin dialog.
                    View pinDialog = getLayoutInflater().inflate(R.layout.enter_pin, null);

                    final EditText pin1 = (EditText) pinDialog.findViewById(R.id.pinNum1);
                    final EditText pin2 = (EditText) pinDialog.findViewById(R.id.pinNum2);
                    final EditText pin3 = (EditText) pinDialog.findViewById(R.id.pinNum3);
                    final EditText pin4 = (EditText) pinDialog.findViewById(R.id.pinNum4);

                    enterPin.setView(pinDialog).setTitle("Enter Your PIN.");
                    final AlertDialog enterPinDialog = enterPin.show();

                    // Whenever a number is entered into the EditText fields, proceed onto the next EditText field.
                    // When the last field is reached, check if the pin number matches and then show details.
                    pin1.addTextChangedListener(new TextWatcher() {
                        @Override
                        public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                        }

                        @Override
                        public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                            new Handler().postDelayed(new Runnable() {
                                @Override
                                public void run() {
                                    if (pin1.getText().toString().length() == 1){
                                        pin2.requestFocus();
                                    }
                                }
                            },100);
                        }

                        @Override
                        public void afterTextChanged(Editable editable) {

                        }
                    });

                    pin2.addTextChangedListener(new TextWatcher() {
                        @Override
                        public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                        }

                        @Override
                        public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                            new Handler().postDelayed(new Runnable() {
                                @Override
                                public void run() {
                                    if (pin2.getText().toString().length() == 1){
                                        pin3.requestFocus();
                                    }
                                }
                            },100);
                        }

                        @Override
                        public void afterTextChanged(Editable editable) {

                        }
                    });

                    pin3.addTextChangedListener(new TextWatcher() {
                        @Override
                        public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                        }

                        @Override
                        public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                            new Handler().postDelayed(new Runnable() {
                                @Override
                                public void run() {
                                    if (pin3.getText().toString().length() == 1){
                                        pin4.requestFocus();
                                    }
                                }
                            },100);
                        }

                        @Override
                        public void afterTextChanged(Editable editable) {

                        }
                    });

                    pin4.addTextChangedListener(new TextWatcher() {
                        @Override
                        public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                        }

                        @Override
                        public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                            if (pin4.getText().toString().length() == 1){
                                int pinValue = Integer.parseInt(pin1.getText().toString() + pin2.getText().toString() + pin3.getText().toString() + pin4.getText().toString());

                                if (pinValue == pinNumber){
                                    showDetails(listViewPosition);
                                    enterPinDialog.dismiss();
                                }
                                else {
                                    enterPinDialog.dismiss();
                                    Toast.makeText(getBaseContext(), "INVALID PIN.", Toast.LENGTH_SHORT).show();
                                }
                            }
                        }

                        @Override
                        public void afterTextChanged(Editable editable) {

                        }
                    });
                }
            }
        });

        // Upon clicking the '+' button, pull up a dialog that allows the user to add a new account.
        final Button addAccountButton = (Button) findViewById(R.id.addAccount);
        addAccountButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addAccount();
            }
        });

        // Upon clicking the settings button, pull up the settings.
        final ImageButton settingsButton = (ImageButton) findViewById(R.id.settingsButton);
        settingsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getBaseContext(), SettingsScreen.class));
            }
        });
    }

    private void showDetails(final int position) {
        final AlertDialog.Builder show = new AlertDialog.Builder(HomeScreen.this);

        // Information for the show details dialog.
        final View dialogView = getLayoutInflater().inflate(R.layout.show_account_details, null);

        TextView accountInfo = (TextView) dialogView.findViewById(R.id.accountName);
        TextView usernameInfo = (TextView) dialogView.findViewById(R.id.usernameDetails);
        TextView passwordInfo = (TextView) dialogView.findViewById(R.id.passwordDetails);

        Button delete = (Button) dialogView.findViewById(R.id.delete);
        final Button edit = (Button) dialogView.findViewById(R.id.editAccount);

        // Information for the edit details dialog.
        final View editView = getLayoutInflater().inflate(R.layout.add_dialog_layout, null);

        final EditText accountField = (EditText) editView.findViewById(R.id.accountField);
        final EditText usernameField = (EditText) editView.findViewById(R.id.usernameField);
        final EditText passwordField = (EditText) editView.findViewById(R.id.passwordField);

        final Button save = (Button) editView.findViewById(R.id.saveButton);

        // Set the textview to the selected information.
        accountInfo.setText(accounts.get(position).account);
        usernameInfo.setText(accounts.get(position).username);
        passwordInfo.setText(accounts.get(position).password);

        show.setView(dialogView);
        final AlertDialog dialog = show.create();
        dialog.show();

        // Upon clicking the 'Edit' button,
        // Allow the user to edit their currently selected item.
        edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Get rid of current dialog.
                dialog.dismiss();

                // Show the edit view.
                show.setView(editView);
                accountField.setText(accounts.get(position).account);
                usernameField.setText(accounts.get(position).username);
                passwordField.setText(accounts.get(position).password);

                final AlertDialog editDialog = show.create();
                editDialog.show();

                save.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        accountDB.updateAccount(accounts.get(position).account, accountField.getText().toString(), usernameField.getText().toString(), passwordField.getText().toString(), sqLiteDatabase);

                        accounts.get(position).account = accountField.getText().toString();
                        accounts.get(position).username = usernameField.getText().toString();
                        accounts.get(position).password = passwordField.getText().toString();

                        accountValues.set(position, accountField.getText().toString());

                        sqLiteDatabase = accountDB.getWritableDatabase();

                        listAdapter.notifyDataSetChanged();
                        editDialog.dismiss();

                        Toast.makeText(getBaseContext(), "ACCOUNT UPDATED.", Toast.LENGTH_SHORT).show();
                        finish();
                    }
                });
            }
        });

        // Dismiss the information.
        delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                accountDB.deleteAccount(accounts.get(position).account, sqLiteDatabase);

                accounts.remove(position);
                accountValues.remove(position);

                listAdapter.notifyDataSetChanged();
                dialog.dismiss();

                Toast.makeText(getBaseContext(), "ACCOUNT DELETED.", Toast.LENGTH_SHORT).show();
            }
        });

    }

    private void addAccount () {
        AlertDialog.Builder add = new AlertDialog.Builder(HomeScreen.this);

        final View dialogView = getLayoutInflater().inflate(R.layout.add_dialog_layout, null);
        final EditText accountField = (EditText) dialogView.findViewById(R.id.accountField);
        final EditText usernameField = (EditText) dialogView.findViewById(R.id.usernameField);
        final EditText passwordField = (EditText) dialogView.findViewById(R.id.passwordField);
        final Button saveButton = (Button) dialogView.findViewById(R.id.saveButton);

        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // If any of the fields are not entered, throw an error.
                if(accountField.getText().toString().equals("") || usernameField.getText().toString().equals("") || passwordField.getText().toString().equals("")){
                    Toast.makeText(getBaseContext(), "Please Enter in All Fields", Toast.LENGTH_SHORT).show();
                }
                // If the account already exists in the field, throw an error.
                else if (accountValues.contains(accountField.getText().toString())){
                    Toast.makeText(getBaseContext(), "Account Already Exists", Toast.LENGTH_SHORT).show();
                }
                // If all is satisfied, save the account.
                else{
                    accountValues.add(accountField.getText().toString());
                    accounts.add(new account(accountField.getText().toString(), usernameField.getText().toString(), passwordField.getText().toString()));

                    sqLiteDatabase = accountDB.getWritableDatabase();
                    accountDB.addAccount(accountField.getText().toString(), usernameField.getText().toString(), passwordField.getText().toString(), sqLiteDatabase);
                    listAdapter.notifyDataSetChanged();

                    Toast.makeText(getBaseContext(), "Account Saved", Toast.LENGTH_SHORT).show();

                    accountDB.close();
                }
            }
        });

        add.setView(dialogView);
        AlertDialog dialog = add.create();
        dialog.show();
    }
}
