package com.example.weatherproject;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import java.util.concurrent.ExecutionException;

public class CurrentWeatherActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_current_weather);

        Button backButton = (Button) findViewById(R.id.backCurrent);
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(CurrentWeatherActivity.this, MainActivity.class);
                CurrentWeatherActivity.this.startActivity(intent);
                finish();
            }
        });



        ConnectionAsyncTask connectionAsyncTask = new ConnectionAsyncTask();//ConnectionAsyncTask connectionAsyncTask = new ConnectionAsyncTask(MainActivity.this);
        try {
            String content = connectionAsyncTask.execute("http://api.openweathermap.org/data/2.5/weather?q=Tulkarm&appid=00eabad0a7551c1a4ee094feb3cf3eab").get();
            //check if the data retrieved or not
            Log.i("connect",content);
        } catch (Exception e) {
            e.printStackTrace();
        }







    }
}