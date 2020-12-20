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

    public int getNumberOfRows(){
        SQLiteDatabase sqLiteDatabase = getReadableDatabase();
        Cursor cursor = sqLiteDatabase.rawQuery("SELECT * FROM PROFILE", null);
        return cursor.getCount();
    }

    public long getDefaultProfileID(){
        SQLiteDatabase sqLiteDatabase = getReadableDatabase();
        Cursor cursor = sqLiteDatabase.rawQuery("SELECT * FROM PROFILE WHERE ISDEFAULT = 1", null);
        if(cursor.getCount()>0){
            cursor.moveToNext();
            long x = cursor.getLong(0);
            return x;
        }
        else
            return -1;
    }

    public Cursor getCursorOfProfileID(long ID){
        SQLiteDatabase sqLiteDatabase = getReadableDatabase();
        Cursor cursor = sqLiteDatabase.rawQuery("SELECT * FROM PROFILE WHERE ID ="+ID, null);
        cursor.moveToNext();
        return cursor;
    }

    public long getSingleProfileID(){
        SQLiteDatabase sqLiteDatabase = getReadableDatabase();
        Cursor cursor = sqLiteDatabase.rawQuery("SELECT * FROM PROFILE", null);
        cursor.moveToNext();
        return cursor.getLong(0);
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

    public void updateProfile(long ID, String profile, String city, String api, String unit, boolean isDefault){
        int def =0;
        if(isDefault)
            def=1;
        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL("UPDATE PROFILE SET PROFILE = \'"+profile
                                    +"\' , CITY = \'"+city
                                    +"\' , APIKEY = \'"+api
                                    +"\' , UNIT = \'"+unit
                                    +"\' , ISDEFAULT = "+def
                                    +" WHERE ID="+ID);
//        db.execSQL("UPDATE PROFILE SET PROFILE =\'" +profile+ "\' WHERE ID="+ID);
        db.close();
    }

    //return entire row based on  specific row ID
    public Cursor cursorByID(long newID){
        SQLiteDatabase sqLiteDatabase = getReadableDatabase();
        return sqLiteDatabase.rawQuery("SELECT * FROM PROFILE WHERE ID="+newID, null);
    }
}
