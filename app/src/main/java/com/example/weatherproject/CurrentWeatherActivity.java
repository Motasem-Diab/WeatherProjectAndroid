package com.example.weatherproject;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.concurrent.ExecutionException;

public class CurrentWeatherActivity extends AppCompatActivity {

    DataBaseHelper dataBaseHelper =new
            DataBaseHelper(CurrentWeatherActivity.this,"myDB",null,1);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_current_weather);

        //get the id to work on from SelectProfileActivity
        long ID_to_view = getIntent().getLongExtra("ID_to_be_viewd",1);
        Cursor cursorById = dataBaseHelper.cursorByID(ID_to_view);
        //Set the values
        String cityName="",API="",UNIT="";
        while (cursorById.moveToNext()){
            cityName = cursorById.getString(2);
            API = cursorById.getString(3);
            UNIT = cursorById.getString(4);
        }
        //transform C to metric and F to imperial
        if(UNIT.equals("Celsius"))
            UNIT = "metric";
        else
            UNIT = "imperial";

        //Define the text views
        TextView cityTV = (TextView) findViewById(R.id.cityTV);
        TextView minTV = (TextView) findViewById(R.id.minTV);
        TextView maxTV = (TextView) findViewById(R.id.maxTV);

        Button backButton = (Button) findViewById(R.id.backCurrent);
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(CurrentWeatherActivity.this, MainActivity.class);
                CurrentWeatherActivity.this.startActivity(intent);
                finish();
            }
        });


//        String url = "http://api.openweathermap.org/data/2.5/weather?q="+cityName+"&units="+metric+"&appid="+00eabad0a7551c1a4ee094feb3cf3eab";
        String url = "http://api.openweathermap.org/data/2.5/weather?q="+cityName+"&units="+UNIT+"&appid=" + API;
        ConnectionAsyncTask connectionAsyncTask = new ConnectionAsyncTask();//ConnectionAsyncTask connectionAsyncTask = new ConnectionAsyncTask(MainActivity.this);
        try {
            String content = connectionAsyncTask.execute(url).get();
            //check if the data retrieved or not
            Log.i("connect",content);

            //JSON
            JSONObject jsonObject = new JSONObject(content);
            String weatherData = jsonObject.getString("weather");
            //new for temp
            String maintemp = jsonObject.getString("main"); //json object

            Log.i("weatherData", weatherData);
            //weatherData is an array
            JSONArray jsonArray = new JSONArray(weatherData);
            String weatherMain = "";
            for (int i=0 ; i<jsonArray.length() ; i++){
                JSONObject weatherPart = jsonArray.getJSONObject(i);
                weatherMain = weatherPart.getString("main");
            }
            Log.i("main main",weatherMain);

            //for json obj maintemp
            String tempMin = "", tempMax="" ;
            JSONObject mainTempObj = new JSONObject(maintemp);
            tempMin = mainTempObj.getString("temp_min");
            tempMax = mainTempObj.getString("temp_max");

            cityTV.setText(cityName);
            maxTV.setText(tempMax);
            minTV.setText(tempMin);


        } catch (Exception e) {
            e.printStackTrace();
        }







    }
}