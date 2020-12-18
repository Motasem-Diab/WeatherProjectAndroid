package com.example.weatherproject;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Spinner;


public class AddEditProfileActivity extends AppCompatActivity {
    public static long ID = 1;
    DataBaseHelper dataBaseHelper = new
            DataBaseHelper(AddEditProfileActivity.this, "myDB", null, 1);


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_edit_profile);



        String [] options = {"Celsius","Fahrenheit"};
        final Spinner unitSpinner =(Spinner) findViewById(R.id.unitSpinner);
        ArrayAdapter<String> objUnitArr = new
                ArrayAdapter<>(this,android.R.layout.simple_spinner_dropdown_item, options);
        unitSpinner.setAdapter(objUnitArr);

        final EditText profileNameEditText = (EditText)findViewById(R.id.profileName);
        final EditText cityNameEditText = (EditText)findViewById(R.id.cityName);
        final EditText APIkeyEditText = (EditText)findViewById(R.id.APIkey);
        final CheckBox checkBox = (CheckBox)findViewById(R.id.setAsDefault);
        Button saveProfileButton = (Button) findViewById(R.id.saveProfile);
        Button cancelProfileButton = (Button) findViewById(R.id.cancelProfile);


        saveProfileButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(profileNameEditText.getText().toString().isEmpty() ||
                        cityNameEditText.getText().toString().isEmpty() ||
                        APIkeyEditText.getText().toString().isEmpty() ||
                        unitSpinner.getSelectedItem().toString().isEmpty()){
                    Message.message(getApplicationContext(),"Enter Full Data");
                }
                else{

                    WeatherProfile Wprofile = new WeatherProfile();
                    Wprofile.setID(ID);
                    Wprofile.setProfileName(profileNameEditText.getText().toString());
                    Wprofile.setCityName(cityNameEditText.getText().toString());
                    Wprofile.setAPIkey(APIkeyEditText.getText().toString());
                    Wprofile.setUnit(unitSpinner.getSelectedItem().toString());
                    Wprofile.setDefault(checkBox.isChecked());

                    long ret = dataBaseHelper.insertProfile(Wprofile);
                    if(ret < 0){
                        Message.message(getApplicationContext(),"Unsuccessful ensertion");
                    }
                    else{
                        Message.message(getApplicationContext(),"Successful ensertion");
                        if(checkBox.isChecked()){
                            dataBaseHelper.resetAllDefaultExcept(ID);
                            Message.message(getApplicationContext(),"all default updated");
                        }
                        ID++ ;
                        profileNameEditText.setText("");
                        cityNameEditText.setText("");
                        APIkeyEditText.setText("");
                        checkBox.setChecked(false);
                    }
                }
            }
        });


        cancelProfileButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent=new Intent(AddEditProfileActivity.this,MainActivity.class);
                AddEditProfileActivity.this.startActivity(intent);
                finish();
            }
        });


    }
}