package com.example.weatherproject;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;



public class DataBaseHelper extends SQLiteOpenHelper {

    public DataBaseHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL("CREATE TABLE PROFILE(ID LONG PRIMARY KEY,PROFILE TEXT, CITY TEXT, " +
                                                    "APIKEY TEXT, UNIT TEXT, ISDEFAULT BOOLEAN )") ;
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
    }

    public long insertProfile(WeatherProfile profile) {
        SQLiteDatabase sqLiteDatabase = getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("ID", profile.getID());
        contentValues.put("PROFILE", profile.getProfileName() );
        contentValues.put("CITY", profile.getCityName() );
        contentValues.put("APIKEY", profile.getAPIkey() );
        contentValues.put("UNIT", profile.getUnit());
        contentValues.put("ISDEFAULT", profile.isDefault() );
        return sqLiteDatabase.insert("PROFILE", null, contentValues);
    }

    public Cursor getAllProfiles() {
        SQLiteDatabase sqLiteDatabase = getReadableDatabase();
        return sqLiteDatabase.rawQuery("SELECT * FROM PROFILE", null);
    }

    public void deleteAll() {
        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL("delete from "+ "PROFILE");
        db.close();
    }

    public void resetAllDefaultExcept(long newID){
        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL("UPDATE PROFILE SET ISDEFAULT = 0 ");
        db.execSQL("UPDATE PROFILE SET ISDEFAULT = 1 WHERE ID="+newID);
        db.close();
    }
}
