package com.example.evgsa.androidgraphics.utils;

import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.example.evgsa.androidgraphics.MainActivity;

public class RecordBaseHelper extends SQLiteOpenHelper
{
    public static final String DATABASE_NAME = "records_base";
    public static final int DATABASE_VERSION = 1;

    public static final String TABLE_NAME = "records_table";
    public static final String KEY_ID = "_id";
    public static final String KEY_RECORD = "record";
    public static final String KEY_TIMESTAMP = "timestamp";

    public RecordBaseHelper()
    {
        super(MainActivity.instance, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db)
    {
        db.execSQL("create table " + TABLE_NAME + "(" +
                                                   KEY_ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
                                                   KEY_RECORD + " integer," +
                                                   KEY_TIMESTAMP  + " TIMEDATE DEFAULT CURRENT_TIMESTAMP)"); //the base has got three columns: id, record itself, and the time it was achieved
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion)
    {
        db.execSQL("drop table if exists " + TABLE_NAME); //deleting existing table
        onCreate(db); //creating a new table
    }
}




















































