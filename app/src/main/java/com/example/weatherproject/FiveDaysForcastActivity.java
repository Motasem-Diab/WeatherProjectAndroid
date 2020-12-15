package com.example.weatherproject;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListView;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

public class FiveDaysForcastActivity extends AppCompatActivity {
    DataBaseHelper dataBaseHelper = new
            DataBaseHelper(FiveDaysForcastActivity.this, "myDB", null, 1);
    ArrayList <String> Days = new ArrayList<String>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_five_days_forcast);

        final long ID_to_view = getIntent().getLongExtra("ID_to_be_viewd", 1);
        Cursor cursorById = dataBaseHelper.cursorByID(ID_to_view);
        String cityName = "", API = "", UNIT = "";
        while (cursorById.moveToNext()) {
            cityName = cursorById.getString(2);
            API = cursorById.getString(3);
            UNIT = cursorById.getString(4);
        }
        String url = "api.openweathermap.org/data/2.5/forecast?q="+cityName+"&mode=json&appid="+API;
        ConnectionAsyncTask connectionAsyncTask = new ConnectionAsyncTask();

        try {

            String content = connectionAsyncTask.execute(url).get();
            //check if the data retrieved or not
            Log.i("connect",content);
            JSONObject jsonObject = new JSONObject(content);
            JSONArray array = (JSONArray) jsonObject.get("list");
            for(int i=0 ;i<40 ;i++) {
                JSONObject newObj = (JSONObject) array.get(i);
                String str =newObj.get("weather").toString();
                str = str.substring(1, str.length() - 1);
                JSONObject lastobj = new JSONObject(str);
                Days.add(lastobj.getString("main"));
                Log.i("DAY", lastobj.getString("main"));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        ArrayAdapter adapter = new ArrayAdapter<String>(this,
                R.layout.activity_listview, Days);
        ListView listview = (ListView) findViewById(R.id.listView1);
        // listview.setOnItemClickListener(this);
        listview.setAdapter(adapter);
















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