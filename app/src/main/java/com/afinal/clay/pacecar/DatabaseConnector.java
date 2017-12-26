package com.afinal.clay.pacecar;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.util.Log;

import static android.content.ContentValues.TAG;

/**
 * Created by Clay on 12/3/2017.
 */

public class DatabaseConnector {

    private static final String DATABASE_NAME = "PaceCar";
    private SQLiteDatabase database;
    private DatabaseOpenHelper databaseOpenHelper;

    public DatabaseConnector(Context context){
        databaseOpenHelper = new DatabaseOpenHelper(context, DATABASE_NAME, null, 1);
    }

    public void Open() throws SQLException{
        database = databaseOpenHelper.getWritableDatabase();
    }

    public void Close(){
        if(database != null){
            database.close();
        }
    }

    public void GetLogs(){
        String tableName = "Logs";
        Log.d("DB", "getTableAsString called");
        String tableString = String.format("Table %s:\n", tableName);
        Cursor allRows  = database.rawQuery("SELECT * FROM " + tableName, null);
        if (allRows.moveToFirst() ){
            String[] columnNames = allRows.getColumnNames();
            do {
                for (String name: columnNames) {
                    tableString += String.format("%s: %s\n", name,
                            allRows.getString(allRows.getColumnIndex(name)));
                }
                tableString += "\n";
            } while (allRows.moveToNext());
        }
        Log.d("DB",tableString);
    }

    public void InsertLog(int mileage, String date, int gas, int tank, int oil, int tire,
                          int wiper, int tune, int fluid){
        ContentValues newLog = new ContentValues();
        newLog.put("Mileage", mileage);
        newLog.put("Date", date);
        newLog.put("Gas", gas);
        newLog.put("TankCheck", tank);
        newLog.put("OilCheck", oil);
        newLog.put("TireCheck", tire);
        newLog.put("WiperCheck", wiper);
        newLog.put("TuneCheck", tune);
        newLog.put("FluidCheck",fluid);

        Open();
        database.insert("Logs", null, newLog);
        Close();
    }

    public void InsertCar(String make, String model, String year, int capacity, int push){
        ContentValues newCar = new ContentValues();
        newCar.put("Make",make);
        newCar.put("Model",model);
        newCar.put("Year",year);
        newCar.put("Capacity",capacity);
        newCar.put("PushCheck",push);
        Open();
        database.insert("Cars",null,newCar);
        Close();
    }

    public Cursor getMax(String field){
        return database.rawQuery("SELECT MAX("+field+") FROM LOGS", null);
    }

    public Cursor getLatest(String field, String where){
        return database.rawQuery("SELECT "+field+" FROM LOGS "+ where +" ORDER BY _id DESC LIMIT 1", null);
    }

    public Cursor getLatestCar(String field, String where){
        return database.rawQuery("SELECT "+field+" FROM Cars "+ where +" ORDER BY _id DESC LIMIT 1", null);
    }

    private class DatabaseOpenHelper extends SQLiteOpenHelper{

        public DatabaseOpenHelper(Context context, String name, CursorFactory factory, int version){
            super(context, name, factory, version);
        }

        @Override
        public void onCreate(SQLiteDatabase db){
            String createQuery = "CREATE TABLE Logs" +
                    "(_id integer primary key autoincrement," +
                    "Mileage INTEGER, Date TEXT, Gas INTEGER," +
                    "TankCheck INTEGER, OilCheck INTEGER, TireCheck INTEGER, " +
                    "WiperCheck INTEGER, TuneCheck INTEGER, FluidCheck INTEGER);";
            db.execSQL(createQuery);
            String createQuery2 = "CREATE TABLE Cars" +
                    "(_id integer primary key autoincrement," +
                    "Make TEXT, Model TEXT, Year TEXT," +
                    "Capacity INTEGER, PushCheck INTEGER);";
            db.execSQL(createQuery2);
        }

        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion){

        }
    }
}
