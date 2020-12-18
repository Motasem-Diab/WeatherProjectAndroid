package com.example.weatherproject;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintSet;

import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Shader;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.text.Layout;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

import static com.example.weatherproject.R.drawable.addbackground;

public class FiveDaysForcastActivity extends AppCompatActivity {
    DataBaseHelper dataBaseHelper = new
            DataBaseHelper(FiveDaysForcastActivity.this, "myDB", null, 1);
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_five_days_forcast);
        Log.i("kos em hek sho3'ol","kos emo");

        final long ID_to_view = getIntent().getLongExtra("ID_to_be_viewd", 1);
        Cursor cursorById = dataBaseHelper.cursorByID(ID_to_view);
        String cityName = "", API = "", UNIT = "";
        while (cursorById.moveToNext()) {
            cityName = cursorById.getString(2);
            API = cursorById.getString(3);
            UNIT = cursorById.getString(4);
        }
        TextView cityNameView = (TextView) findViewById(R.id.cityName);
        cityNameView.setText(cityName);
        TextView weather = (TextView) findViewById(R.id.weather);
        TextView tempMin = (TextView) findViewById(R.id.minTemp);
        TextView tempMax = (TextView) findViewById(R.id.maxTemp);

        String url = "api.openweathermap.org/data/2.5/forecast?q=Ramallah&mode=json&appid=15e48b3bb06ba849697c993a7776d8a1";
        ConnectionAsyncTask connectionAsyncTask = new ConnectionAsyncTask();

        try {

            String content = connectionAsyncTask.execute(url).get();
            //check if the data retrieved or not
            Log.i("connect",content);
            JSONObject jsonObject = new JSONObject(content);
            JSONArray array = (JSONArray) jsonObject.get("list");
            JSONObject newObj = (JSONObject) array.get(0);
            String str =newObj.get("weather").toString();
            str = str.substring(1, str.length() - 1);
            Log.i("str:  ", str);
            JSONObject lastobj = new JSONObject(str);
            weather.setText(lastobj.getString("main"));
            str =newObj.get("main").toString();
            lastobj = new JSONObject(str);
            tempMin.setText(lastobj.getString("temp_min"));
            tempMax.setText(lastobj.getString("temp_max"));

//                Days.add(lastobj.getString("main"));
//                Log.i("DAY", lastobj.getString("main"));
        } catch (Exception e) {
            Log.i("error", "error found");
            e.printStackTrace();
        }
        LinearLayout fiveDaysLayout = (LinearLayout)findViewById(R.id.FiveDaysLayout);
//        if (weather.getText().toString().toLowerCase() == "rain")
//            fiveDaysLayout.setBackground(R.drawable.raining);
//        else if (weather.getText().toString().toLowerCase() == "sun")
//            fiveDaysLayout.setBackground(R.drawable.sunny);
//        else if(weather.getText().toString().toLowerCase() == "cloud")
//            fiveDaysLayout.setBackground(R.drawable.cloudy);















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
}