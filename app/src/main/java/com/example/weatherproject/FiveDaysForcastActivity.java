package com.example.weatherproject;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.concurrent.ExecutionException;

public class FiveDaysForcastActivity extends AppCompatActivity {
    DataBaseHelper dataBaseHelper = new
            DataBaseHelper(FiveDaysForcastActivity.this, "myDB", null, 1);
    int Celsius = 1;
    String cityName = "", API = "", UNIT = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_five_days_forcast);
        final long ID_to_view = getIntent().getLongExtra("ID_to_be_viewd", 1);
        RadioButton rC = (RadioButton) findViewById(R.id.radioCels);
        RadioButton rF = (RadioButton) findViewById(R.id.radioFahren);
        Cursor cursorById = dataBaseHelper.cursorByID(ID_to_view);

        while (cursorById.moveToNext()) {
            cityName = cursorById.getString(2);
            API = cursorById.getString(3);
            UNIT = cursorById.getString(4);
        }
        if (UNIT.equals("Celsius")) {
            UNIT = "metric";
            rC.setChecked(true);
            Celsius = 1;
        } else {
            UNIT = "imperial";
            rF.setChecked(true);
            Celsius = 0;
        }
        TextView cityNameView = (TextView) findViewById(R.id.cityName);
        cityNameView.setText(cityName);
        connectAndView(cityName,API,UNIT);


        //Menu
        Button addnewprofile = (Button) findViewById(R.id.addNewProfileMenu);
        addnewprofile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(FiveDaysForcastActivity.this, AddEditProfileActivity.class);
                FiveDaysForcastActivity.this.startActivity(intent);
                finish();
            }
        });

        Button update = (Button) findViewById(R.id.updateProfile);
        update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                /***needs improvments*/
                Intent intent = new Intent(FiveDaysForcastActivity.this, AddEditProfileActivity.class);
                FiveDaysForcastActivity.this.startActivity(intent);
                finish();
            }
        });
        Button switchprofile = (Button) findViewById(R.id.switchProfile);
        switchprofile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(FiveDaysForcastActivity.this, SelectProfileActivity.class);
                FiveDaysForcastActivity.this.startActivity(intent);
                finish();
            }
        });
        Button currentweather = (Button) findViewById(R.id.showFiveDays);
        currentweather.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(FiveDaysForcastActivity.this, CurrentWeatherActivity.class);
                FiveDaysForcastActivity.this.startActivity(intent);
                finish();
            }
        });

        Button backButton = (Button) findViewById(R.id.backCurrent);
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(FiveDaysForcastActivity.this, MainActivity.class);
                FiveDaysForcastActivity.this.startActivity(intent);
                finish();
            }
        });


    }
    void connectAndView (String cityName,String API,String UNIT){
        ImageView img1 = (ImageView) findViewById(R.id.imageView1);
        ImageView img2 = (ImageView) findViewById(R.id.imageView2);
        ImageView img3 = (ImageView) findViewById(R.id.imageView3);
        ImageView img4 = (ImageView) findViewById(R.id.imageView4);
        ImageView img5 = (ImageView) findViewById(R.id.imageView5);
        TextView temp1 = (TextView) findViewById(R.id.temp1);
        TextView temp2 = (TextView) findViewById(R.id.temp2);
        TextView temp3 = (TextView) findViewById(R.id.temp3);
        TextView temp4 = (TextView) findViewById(R.id.temp4);
        TextView temp5 = (TextView) findViewById(R.id.temp5);

        String url = "http://api.openweathermap.org/data/2.5/forecast?q=" + cityName + "&mode=json&appid=" + API + "&units=" + UNIT;
        ConnectionAsyncTask connectionAsyncTask = new ConnectionAsyncTask();

        try {
            String content = connectionAsyncTask.execute(url).get();
            //check if the data retrieved or not
            Log.i("connect", content);
            for (int i = 0; i < 40; i += 8) {
                JSONObject jsonObject = new JSONObject(content);
                JSONArray array = (JSONArray) jsonObject.get("list");
                JSONObject newObj = (JSONObject) array.get(i);
                String str = newObj.get("weather").toString();
                str = str.substring(1, str.length() - 1);
                Log.i("str:  ", str);
                JSONObject lastobj = new JSONObject(str);
                str = newObj.get("main").toString();
                JSONObject lastobj1 = new JSONObject(str);
                String MaxMin = lastobj1.getString("temp_max") + "/" + lastobj1.getString("temp_min");
                if (lastobj.getString("main").toLowerCase().contains("rain") ) {
                    if (i == 0) {
                        img1.setImageDrawable(getResources().getDrawable(R.drawable.rain));
                        temp1.setText(MaxMin);
                    } else if (i == 8) {
                        img2.setImageDrawable(getResources().getDrawable(R.drawable.rain));
                        temp2.setText(MaxMin);
                    } else if (i == 16) {
                        img3.setImageDrawable(getResources().getDrawable(R.drawable.rain));
                        temp3.setText(MaxMin);
                    } else if (i == 24) {
                        img4.setImageDrawable(getResources().getDrawable(R.drawable.rain));
                        temp4.setText(MaxMin);
                    } else if (i == 32) {
                        img5.setImageDrawable(getResources().getDrawable(R.drawable.rain));
                        temp5.setText(MaxMin);
                    }
                }
                if (lastobj.getString("main").toLowerCase().contains("clear") ) {
                    if (i == 0){
                        img1.setImageDrawable(getResources().getDrawable(R.drawable.sun));
                        temp1.setText(MaxMin);
                    }
                    else if (i == 8){
                        img2.setImageDrawable(getResources().getDrawable(R.drawable.sun));
                        temp2.setText(MaxMin);
                    }
                    else if (i == 16){
                        img3.setImageDrawable(getResources().getDrawable(R.drawable.sun));
                        temp3.setText(MaxMin);
                    }
                    else if (i == 24){
                        img4.setImageDrawable(getResources().getDrawable(R.drawable.sun));
                        temp4.setText(MaxMin);
                    }
                    else if (i == 32){
                        img5.setImageDrawable(getResources().getDrawable(R.drawable.sun));
                        temp5.setText(MaxMin);
                    }
                }
                if (lastobj.getString("main").toLowerCase().contains("clouds") ) {
                    if (i == 0){
                        img1.setImageDrawable(getResources().getDrawable(R.drawable.cloud));
                        temp1.setText(MaxMin);
                    }
                    else if (i == 8){
                        img2.setImageDrawable(getResources().getDrawable(R.drawable.cloud));
                        temp2.setText(MaxMin);
                    }
                    else if (i == 16){
                        img3.setImageDrawable(getResources().getDrawable(R.drawable.cloud));
                        temp3.setText(MaxMin);
                    }
                    else if (i == 24){
                        img4.setImageDrawable(getResources().getDrawable(R.drawable.cloud));
                        temp4.setText(MaxMin);
                    }
                    else if (i == 32){
                        img5.setImageDrawable(getResources().getDrawable(R.drawable.cloud));
                        temp5.setText(MaxMin);
                    }
                }


//                Days.add(lastobj.getString("main"));
                Log.i("DAY", lastobj.getString("main"));
            }
        } catch (Exception e) {
            Log.i("error", "error found");
            e.printStackTrace();
        }

    }
    public void radioButtonClick(View view) {
        boolean checked = ((RadioButton) view).isChecked();

        switch (view.getId()) {
            case R.id.radioCels:
                if (checked) {
                    Message.message(getApplicationContext(), "Celsius selected");
                   connectAndView(cityName,API,"metric");
                }
                break;
            case R.id.radioFahren:
                if (checked) {
                    Message.message(getApplicationContext(), "Fahrenheit selected");
                    connectAndView(cityName,API,"imperial");

                }
                break;
        }

    }
}