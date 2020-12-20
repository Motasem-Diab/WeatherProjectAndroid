package com.example.weatherproject;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.concurrent.ExecutionException;

public class CurrentWeatherActivity extends AppCompatActivity {

    DataBaseHelper dataBaseHelper = new
            DataBaseHelper(CurrentWeatherActivity.this, "myDB", null, 1);

    int Celsius = 0;
    String tempMinC = "", tempMaxC = "", tempMinF = "", tempMaxF = "";
    TextView maxTV, minTV, cityTV;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_current_weather);

        //Define the text views
        cityTV = (TextView) findViewById(R.id.cityTV);
        minTV = (TextView) findViewById(R.id.minTV);
        maxTV = (TextView) findViewById(R.id.maxTV);

        RadioButton rC = (RadioButton) findViewById(R.id.radioCels);
        RadioButton rF = (RadioButton) findViewById(R.id.radioFahren);


        //get the id to work on from SelectProfileActivity
        final long ID_to_view = getIntent().getLongExtra("ID_to_be_viewd", 0);
        Cursor cursorById = dataBaseHelper.cursorByID(ID_to_view);
        //Get the values from DB
        String cityName = "", API = "", UNIT = "";
        while (cursorById.moveToNext()) {
            cityName = cursorById.getString(2);
            API = cursorById.getString(3);
            UNIT = cursorById.getString(4);
        }
        //transform C to metric and F to imperial
        if (UNIT.equals("Celsius")) {
            UNIT = "metric";
            rC.setChecked(true);
            Celsius = 1;
        } else {
            UNIT = "imperial";
            rF.setChecked(true);
            Celsius = 0;
        }


        Button backButton = (Button) findViewById(R.id.backCurrent);
        if(MainActivity.testingMode == 0)
            backButton.setVisibility(View.GONE);
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(CurrentWeatherActivity.this, MainActivity.class);
                CurrentWeatherActivity.this.startActivity(intent);
                finish();
            }
        });


        String url = "http://api.openweathermap.org/data/2.5/weather?q=" + cityName + "&units=" + UNIT + "&appid=" + API;
        ConnectionAsyncTask connectionAsyncTask = new ConnectionAsyncTask();
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
            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject weatherPart = jsonArray.getJSONObject(i);
                weatherMain = weatherPart.getString("main");
            }
            Log.i("main main", weatherMain);


            //for json obj maintemp

            JSONObject mainTempObj = new JSONObject(maintemp);
            if (Celsius == 1) {
                tempMinC = mainTempObj.getString("temp_min");
                tempMinF = celsius_Fah(tempMinC);
                tempMaxC = mainTempObj.getString("temp_max");
                tempMaxF = celsius_Fah(tempMaxC);
                maxTV.setText(tempMaxC);
                minTV.setText(tempMinC);
            } else {
                tempMinF = mainTempObj.getString("temp_min");
                tempMinC = fah_Celsius(tempMinF);
                tempMaxF = mainTempObj.getString("temp_max");
                tempMaxC = fah_Celsius(tempMaxF);
                maxTV.setText(tempMaxF);
                minTV.setText(tempMinF);
            }
            LinearLayout linearLayout = (LinearLayout) findViewById(R.id.mainLinearLayoutCurrentWeather);
            if (weatherMain.toLowerCase().contains("rain"))
                linearLayout.setBackgroundResource(R.drawable.raining);
            else if (weatherMain.toLowerCase().contains("clear"))
                linearLayout.setBackgroundResource(R.drawable.sunny);
            else if(weatherMain.toLowerCase().contains("clouds"))
                linearLayout.setBackgroundResource(R.drawable.cloudy);


            cityTV.setText(cityName);


//

        } catch (Exception e) {
//            e.printStackTrace();
            Message.message(getApplicationContext(), "ERROR in profile,");
            Intent intent = new Intent(CurrentWeatherActivity.this, SelectProfileActivity.class);
            CurrentWeatherActivity.this.startActivity(intent);
            finish();
        }




        Button addnewprofile = (Button) findViewById(R.id.addNewProfileMenu);
        addnewprofile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(CurrentWeatherActivity.this, AddEditProfileActivity.class);
                intent.putExtra("ID_to_be_edited", -1);
                CurrentWeatherActivity.this.startActivity(intent);
                finish();
            }
        });

        Button update = (Button) findViewById(R.id.updateProfile);
        update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                /***needs improvments*/
                Intent intent = new Intent(CurrentWeatherActivity.this, AddEditProfileActivity.class);
                intent.putExtra("ID_to_be_edited", ID_to_view);//to be updated
                CurrentWeatherActivity.this.startActivity(intent);
                finish();
            }
        });
        Button switchprofile = (Button) findViewById(R.id.switchProfile);
        switchprofile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(CurrentWeatherActivity.this, SelectProfileActivity.class);
                CurrentWeatherActivity.this.startActivity(intent);
                finish();
            }
        });
        Button showfiveDays = (Button) findViewById(R.id.showFiveDays);
        showfiveDays.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(CurrentWeatherActivity.this, FiveDaysForcastActivity.class);
                intent.putExtra("ID_to_be_viewd", ID_to_view);
                CurrentWeatherActivity.this.startActivity(intent);
                finish();
            }
        });


    }


    public void radioButtonClick(View view) {
        boolean checked = ((RadioButton) view).isChecked();

        switch (view.getId()) {
            case R.id.radioCels:
                if (checked) {
                    Message.message(getApplicationContext(), "Celsius selected");
                    minTV.setText(tempMinC);
                    maxTV.setText(tempMaxC);
                }
                break;
            case R.id.radioFahren:
                if (checked) {
                    Message.message(getApplicationContext(), "Fahrenheit selected");
                    minTV.setText(tempMinF);
                    maxTV.setText(tempMaxF);
                }
                break;
        }

    }

    @SuppressLint("DefaultLocale")
    public String celsius_Fah(String cels) {
        double cle = Double.parseDouble(cels);
        double fah = cle * (9.0 / 5.0) + 32;
        return String.format("%.1f", fah);
    }

    @SuppressLint("DefaultLocale")
    public String fah_Celsius(String fahr) {
        double fah = Double.parseDouble(fahr);
        double cel = (fah - 32) * (5.0 / 9.0);
        return String.format("%.1f", cel);
    }
}