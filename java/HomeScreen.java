package com.projects.michaelkim.passwordjournal;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
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

    final int pinNumber = 4444;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_screen);

        // Add samples to show in the listview.
        accountValues.add("Chase");
        accounts.add(new account("Chase", "Username 1", "Password 1"));
        accountValues.add("Dunkin' Donuts");
        accounts.add(new account("Dunkin Donuts", "Username 2", "Password 2"));
        accountValues.add("Hotmail");
        accounts.add(new account("Hotmail", "Username 3", "Password 3"));
        accountValues.add("Starbucks");
        accounts.add(new account("Starbucks", "abcabc", "ahhhhhh"));

        // Create an adapter that fills the listview with just the account names.
        passwordList = (ListView) findViewById(R.id.passwordList);
        listAdapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, accountValues);
        passwordList.setAdapter(listAdapter);

        passwordList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, final int listViewPosition, long l) {
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
                            if (pin1.getText().toString().length() == 1){
                                pin2.requestFocus();
                            }
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
                            if (pin2.getText().toString().length() == 1){
                                pin3.requestFocus();
                            }
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
                            if (pin3.getText().toString().length() == 1){
                                pin4.requestFocus();
                            }
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
                                Toast.makeText(getBaseContext(), Integer.toString(pinValue), Toast.LENGTH_SHORT).show();

                                if (pinValue == pinNumber){
                                    showDetails(listViewPosition);
                                    Toast.makeText(getBaseContext(), "OKAY", Toast.LENGTH_SHORT).show();
                                    enterPinDialog.dismiss();
                                }
                                else {
                                    enterPinDialog.dismiss();
                                    Toast.makeText(getBaseContext(), "NOT OKAY", Toast.LENGTH_SHORT).show();
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
        final Button settingsButton = (Button) findViewById(R.id.settingsButton);
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
        View dialogView = getLayoutInflater().inflate(R.layout.show_account_details, null);

        TextView accountInfo = (TextView) dialogView.findViewById(R.id.accountName);
        TextView usernameInfo = (TextView) dialogView.findViewById(R.id.usernameDetails);
        TextView passwordInfo = (TextView) dialogView.findViewById(R.id.passwordDetails);

        Button dismiss = (Button) dialogView.findViewById(R.id.dismiss);
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
                        accounts.get(position).account = accountField.getText().toString();
                        accounts.get(position).username = usernameField.getText().toString();
                        accounts.get(position).password = passwordField.getText().toString();

                        accountValues.set(position, accountField.getText().toString());

                        listAdapter.notifyDataSetChanged();
                        editDialog.dismiss();

                        Toast.makeText(getBaseContext(), "Account Edited", Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });

        // Dismiss the information.
        dismiss.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
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
                    Toast.makeText(getBaseContext(), "Account Saved", Toast.LENGTH_SHORT).show();
                }
            }
        });

        add.setView(dialogView);
        AlertDialog dialog = add.create();
        dialog.show();
    }
}
