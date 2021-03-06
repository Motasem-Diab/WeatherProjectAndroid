package com.example.weatherproject;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Spinner;


public class AddEditProfileActivity extends AppCompatActivity {

    DataBaseHelper dataBaseHelper = new
            DataBaseHelper(AddEditProfileActivity.this, "myDB", null, 1);
    public static long ID ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_edit_profile);

        ID = dataBaseHelper.getNumberOfRows();
        final long ID_to_edit = getIntent().getLongExtra("ID_to_be_edited", -1);

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

        if(ID_to_edit != -1){//put the data of this profile in the text views
            //get the cursor if ID
            Cursor cursor = dataBaseHelper.getCursorOfProfileID(ID_to_edit);
            //fetch the data and show it in text views
            profileNameEditText.setText(cursor.getString(1));
            cityNameEditText.setText(cursor.getString(2));
            APIkeyEditText.setText(cursor.getString(3));
            if(cursor.getString(4).equals("Fahrenheit"))
                unitSpinner.setSelection(1);
            if(cursor.getString(5).equals("1")){
                checkBox.setChecked(true);
            }
        }

        saveProfileButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(profileNameEditText.getText().toString().isEmpty() ||
                        cityNameEditText.getText().toString().isEmpty() ||
                        APIkeyEditText.getText().toString().isEmpty() ||
                        unitSpinner.getSelectedItem().toString().isEmpty()){
                    Message.message(getApplicationContext(),"Enter Full Data");
                }
                else{   //data valid entered
                    if(ID_to_edit != -1){//edit
                        if(checkBox.isChecked())
                            dataBaseHelper.resetAllDefaultExcept(-1);//set all as non-default
                        dataBaseHelper.updateProfile(ID_to_edit,
                                                    profileNameEditText.getText().toString(),
                                                    cityNameEditText.getText().toString(),
                                                    APIkeyEditText.getText().toString(),
                                                    unitSpinner.getSelectedItem().toString(),
                                                    checkBox.isChecked());
                    }
                    else{//add new
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
                    //go ta main
                    Intent intent = new Intent(AddEditProfileActivity.this, MainActivity.class);
                    AddEditProfileActivity.this.startActivity(intent);
                    finish();
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