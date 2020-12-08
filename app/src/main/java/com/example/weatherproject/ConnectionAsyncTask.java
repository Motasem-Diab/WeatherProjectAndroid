package com.example.weatherproject;

import android.os.AsyncTask;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class ConnectionAsyncTask extends AsyncTask<String, Void, String> { //url as string , void, return data as string

    @Override
    protected String doInBackground(String... address) {

        try {
            URL url = new URL(address[0]);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            //establish connection with address
            connection.connect();

            //retrieve data from URL
            InputStream is = connection.getInputStream();
            InputStreamReader isr = new InputStreamReader(is);

            //retrieve data and return it as string
            int data =  isr.read();
            String content = "";
            char ch;
            while (data != -1){
                ch = (char) data ;
                content = content + ch ;
                data = isr.read();
            }
            return content;

        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return null;
    }

}
