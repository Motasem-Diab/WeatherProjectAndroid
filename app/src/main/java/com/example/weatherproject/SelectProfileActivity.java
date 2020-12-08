package com.example.weatherproject;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.ScrollView;
import android.widget.TextView;

import java.util.ArrayList;

public class SelectProfileActivity extends AppCompatActivity {

//    String[] mobileArray = {"Android","IPhone","WindowsMobile","Blackberry",
//            "WebOS","Ubuntu","Windows7","Max OS X","motasem","Raed","diab","ahjk","gjhkl"};

    DataBaseHelper dataBaseHelper =new DataBaseHelper(SelectProfileActivity.this,"myDB", null,1);
    ArrayList<String> arrayOfString = new ArrayList<String>();
    ArrayList<Long> arrayOfIDs = new ArrayList<Long>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_profile);

        Button backButton = (Button) findViewById(R.id.backSelectProfile);
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(SelectProfileActivity.this, MainActivity.class);
                SelectProfileActivity.this.startActivity(intent);
                finish();
            }
        });


        makeListOfNames();
        ArrayAdapter adapter = new ArrayAdapter<String>(this,
                R.layout.activity_listview, arrayOfString);
        ListView listview = (ListView) findViewById(R.id.listView1);
       // listview.setOnItemClickListener(this);
        listview.setAdapter(adapter);
        listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Message.message(getApplicationContext(),""+arrayOfString.get(position)+" is loaded now");
                //dataBaseHelper.resetAllDefaultExcept(arrayOfIDs.get(position));
                //go to Current Weather Activity and send the pressed ID
                Intent intent = new Intent(SelectProfileActivity.this, CurrentWeatherActivity.class);
                intent.putExtra("ID_to_be_viewd", arrayOfIDs.get(position));
                SelectProfileActivity.this.startActivity(intent);
                finish();
            }
        });

    }

    public void makeListOfNames(){
        Cursor allProfilesCursor = dataBaseHelper.getAllProfiles();
        while (allProfilesCursor.moveToNext()){
            arrayOfIDs.add(allProfilesCursor.getLong(0));
            arrayOfString.add(allProfilesCursor.getString(1));
        }
    }


}