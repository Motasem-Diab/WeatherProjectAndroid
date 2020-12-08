package com.example.weatherproject;

//import androidx.appcompat.app.AppCompatActivity;
//
//import android.os.Bundle;
//
//public class MainActivity extends AppCompatActivity {
//
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
////        setContentView(R.layout.activity_main);
//        setContentView(R.layout.activity_add_edit_profile);
//    }
//}



import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;


public class MainActivity extends AppCompatActivity {
    public static boolean firstRun = true;
    LinearLayout secondLinearLayout;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

//        if(firstRun == true){
//            firstRun = false;
//            DataBaseHelper dataBaseHelper =new
//                DataBaseHelper(MainActivity.this,"myDB",null,1);
//            dataBaseHelper.deleteAll();
//        }



        Button addProfileButton = (Button) findViewById(R.id.addProfile);
        addProfileButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, AddEditProfileActivity.class);
                MainActivity.this.startActivity(intent);
                finish();
            }
        });

        Button viewProfileButton = (Button) findViewById(R.id.viewProfile);
        viewProfileButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, ViewProfilesActivity.class);
                MainActivity.this.startActivity(intent);
                finish();
            }
        });

        Button selectProfileButton = (Button) findViewById(R.id.selectProfile);
        selectProfileButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, SelectProfileActivity.class);
                MainActivity.this.startActivity(intent);
                finish();
            }
        });

        Button currentProfileButton = (Button) findViewById(R.id.currentProfile);
        currentProfileButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, CurrentWeatherActivity.class);
                MainActivity.this.startActivity(intent);
                finish();
            }
        });
    }


}