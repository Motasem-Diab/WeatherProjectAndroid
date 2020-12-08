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

public class ViewProfilesActivity extends AppCompatActivity {

    LinearLayout secondLinearLayout;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        LinearLayout firstLinearLayout=new LinearLayout(this);
        Button addButton =new Button(this);
        secondLinearLayout=new LinearLayout(this);
        ScrollView scrollView = new ScrollView(this);
        firstLinearLayout.setOrientation(LinearLayout.VERTICAL);
        secondLinearLayout.setOrientation(LinearLayout.VERTICAL);
        addButton.setText("Back");
        addButton.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT));
        firstLinearLayout.addView(addButton);
        scrollView.addView(secondLinearLayout);
        firstLinearLayout.addView(scrollView);
        setContentView(firstLinearLayout);
        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ViewProfilesActivity.this, MainActivity.class);
                ViewProfilesActivity.this.startActivity(intent);
                finish();
            } });

    }


    protected void onResume(){
        super.onResume();
        DataBaseHelper dataBaseHelper =new DataBaseHelper(ViewProfilesActivity.this,"myDB", null,1);
        Cursor allCustomersCursor = dataBaseHelper.getAllProfiles();
        secondLinearLayout.removeAllViews();
        while (allCustomersCursor.moveToNext()){
            TextView textView =new TextView(ViewProfilesActivity.this);
            textView.setText( "profile= "+allCustomersCursor.getString(1) +"\ncity= "+allCustomersCursor.getString(2) +"\nAPI= "+allCustomersCursor.getString(3) +"\nunit= "+allCustomersCursor.getString(4)+"\ndefault= "+allCustomersCursor.getString(5) +"\n\n" );
            secondLinearLayout.addView(textView);
        }
    }
}