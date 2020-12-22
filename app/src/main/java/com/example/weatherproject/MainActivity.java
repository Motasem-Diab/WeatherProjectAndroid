package com.example.weatherproject;



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

    public static int testingMode = 0;//1=true, 0=false

    public static boolean firstRun = true;
    LinearLayout secondLinearLayout;

    DataBaseHelper dataBaseHelper = new
            DataBaseHelper(MainActivity.this, "myDB", null, 1);


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



        if(MainActivity.testingMode == 0){
            int numberOfRows = dataBaseHelper.getNumberOfRows();
            if(numberOfRows == 0){//there is no entries in the DB
                Message.message(getApplicationContext(),"There is no profiles");
                Intent intent = new Intent(MainActivity.this, AddEditProfileActivity.class);
                MainActivity.this.startActivity(intent);
                finish();
            }
            else if(numberOfRows == 1){//only one entry
                Intent intent = new Intent(MainActivity.this, CurrentWeatherActivity.class);
                intent.putExtra("ID_to_be_viewd", dataBaseHelper.getSingleProfileID());
                MainActivity.this.startActivity(intent);
                finish();
            }
            else if(dataBaseHelper.getDefaultProfileID() != -1){//there is a default profile
                Intent intent = new Intent(MainActivity.this, CurrentWeatherActivity.class);
                intent.putExtra("ID_to_be_viewd", dataBaseHelper.getDefaultProfileID());
                MainActivity.this.startActivity(intent);
                finish();
            }
            else{//there is no default profile
                Message.message(getApplicationContext(),"There is no default profile");
                Intent intent = new Intent(MainActivity.this, SelectProfileActivity.class);
                MainActivity.this.startActivity(intent);
                finish();
            }
        }
        else {//testing mode
            Button addProfileButton = (Button) findViewById(R.id.addProfile);
            addProfileButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(MainActivity.this, AddEditProfileActivity.class);
                    intent.putExtra("ID_to_be_edited", "-1");
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
        }

    }


}