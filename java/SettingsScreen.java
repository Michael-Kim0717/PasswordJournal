package com.projects.michaelkim.passwordjournal;

import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

/**
 * Created by Micha on 11/10/2017.
 */

public class SettingsScreen extends AppCompatActivity {

    // Variable declaration.
    ListView settingsList;
    ArrayAdapter<String> listAdapter;
    ArrayList<String> settings = new ArrayList<>();
    ActionBar actionBar;

    AlertDialog enterPinDialog;

    // Sample PIN Number.
    int pinNumber = 1;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.settings_screen);

        actionBar = getSupportActionBar();
        actionBar.setHomeButtonEnabled(true);
        actionBar.setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
        actionBar.setCustomView(R.layout.title_bar_layout);

        // Options for the settings.
        settings.add("Set/Edit PIN");

        // Set ListView to show the settings.
        settingsList = (ListView) findViewById(R.id.settingsList);
        listAdapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, settings);
        settingsList.setAdapter(listAdapter);

        // When certain settings are clicked, invoke their specific methods.
        settingsList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                // Set/Edit PIN is selected
                if (position == 0) {
                    SharedPreferences sharedPreferences = getSharedPreferences("PINNUM", Context.MODE_PRIVATE);

                    pinNumber = sharedPreferences.getInt("PIN", 0);

                    // Array to store the values of the initial PIN entered and the confirmed PIN.
                    final int[] pinValues = new int[2];

                    // If no PIN has been set, let the user set their pin now.
                    // The user must confirm the PIN after entering it once.
                    if (pinNumber == 0){
                        // Stores the initial entered PIN followed by its confirmed PIN
                        // Compares them afterwards.
                        setPin(pinValues);
                    }
                    // If a PIN already exists, make the user enter their current pin.
                    // After, make the user enter in a PIN and confirm it.
                    else {
                        // Information for the enter pin dialog.
                        final View pinDialog = getLayoutInflater().inflate(R.layout.enter_pin, null);

                        final EditText pin1 = (EditText) pinDialog.findViewById(R.id.pinNum1);
                        final EditText pin2 = (EditText) pinDialog.findViewById(R.id.pinNum2);
                        final EditText pin3 = (EditText) pinDialog.findViewById(R.id.pinNum3);
                        final EditText pin4 = (EditText) pinDialog.findViewById(R.id.pinNum4);

                        final AlertDialog.Builder enterPin = new AlertDialog.Builder(SettingsScreen.this);

                        enterPin.setView(pinDialog).setTitle("Enter your PIN");
                        enterPinDialog = enterPin.show();

                        // Whenever a number is entered into the EditText fields, proceed onto the next EditText field.
                        // When the last field is reached, check if the entered PIN matches the user's PIN.
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
                                if (pin4.getText().toString().length() == 1) {
                                    // If the entered PIN matches the user's PIN, proceed to creating a new PIN.
                                    pinValues[0] = Integer.parseInt(pin1.getText().toString() + pin2.getText().toString() + pin3.getText().toString() + pin4.getText().toString());
                                    if (pinValues[0] == pinNumber){
                                        enterPinDialog.dismiss();
                                        setPin(pinValues);
                                    }
                                    else{
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
            }
        });
    }

    private void setPin(final int[] pinValues){
        // Information for the enter pin dialog.
        final View pinDialog = getLayoutInflater().inflate(R.layout.enter_pin, null);

        final EditText pin1 = (EditText) pinDialog.findViewById(R.id.pinNum1);
        final EditText pin2 = (EditText) pinDialog.findViewById(R.id.pinNum2);
        final EditText pin3 = (EditText) pinDialog.findViewById(R.id.pinNum3);
        final EditText pin4 = (EditText) pinDialog.findViewById(R.id.pinNum4);

        final AlertDialog.Builder enterPin = new AlertDialog.Builder(SettingsScreen.this);

        enterPin.setView(pinDialog).setTitle("Create a PIN");
        enterPinDialog = enterPin.show();

        // Whenever a number is entered into the EditText fields, proceed onto the next EditText field.
        // When the last field is reached, return the pin number.
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
                if (pin4.getText().toString().length() == 1) {
                    // Set the first array value to whatever was entered.
                    // Proceeded to ask for a confirmation PIN and compare the two.
                    pinValues[0] = Integer.parseInt(pin1.getText().toString() + pin2.getText().toString() + pin3.getText().toString() + pin4.getText().toString());
                    enterPinDialog.dismiss();
                    setSecondPin(pinValues);
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
    }

    private void setSecondPin(final int[] pinValues){
        // Information for the enter pin dialog.
        final View pinDialog = getLayoutInflater().inflate(R.layout.enter_pin, null);

        final EditText pin1 = (EditText) pinDialog.findViewById(R.id.pinNum1);
        final EditText pin2 = (EditText) pinDialog.findViewById(R.id.pinNum2);
        final EditText pin3 = (EditText) pinDialog.findViewById(R.id.pinNum3);
        final EditText pin4 = (EditText) pinDialog.findViewById(R.id.pinNum4);

        final AlertDialog.Builder enterPin = new AlertDialog.Builder(SettingsScreen.this);

        enterPin.setView(pinDialog).setTitle("Confirm your PIN");
        enterPinDialog = enterPin.show();

        // Whenever a number is entered into the EditText fields, proceed onto the next EditText field.
        // When the last field is reached, return the pin number.
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
                if (pin4.getText().toString().length() == 1) {
                    pinValues[1] = Integer.parseInt(pin1.getText().toString() + pin2.getText().toString() + pin3.getText().toString() + pin4.getText().toString());
                    if (pinValues[1] != pinValues[0]){
                        // If the confirmed PIN is different from the original entered PIN,
                        // Dismiss and the user must try again.
                        Toast.makeText(getBaseContext(), "INVALID PIN, TRY AGAIN.", Toast.LENGTH_SHORT).show();
                        enterPinDialog.dismiss();
                    }
                    else{
                        // If the two PIN's matches,
                        // Dismiss and update the user's PIN.
                        enterPinDialog.dismiss();

                        // Using SharedPreferences to save the value of the PIN number.
                        SharedPreferences sharedPreferences = getSharedPreferences("PINNUM", Context.MODE_PRIVATE);

                        SharedPreferences.Editor editor = sharedPreferences.edit();
                        editor.putInt("PIN", pinValues[1]);
                        editor.apply();

                        Toast.makeText(getBaseContext(), "PIN UPDATED.", Toast.LENGTH_SHORT).show();
                    }
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
    }
}
