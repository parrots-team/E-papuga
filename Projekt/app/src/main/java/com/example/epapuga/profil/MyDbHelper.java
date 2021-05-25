package com.example.epapuga.profil;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import java.util.ArrayList;

public class MyDbHelper  extends SQLiteOpenHelper {
    public MyDbHelper(@Nullable Context context) {
        super(context, Constants.DB_NAME, null, Constants.DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        //create table on that db
        db.execSQL(Constants.CREATE_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        //upgrade database (if there is any structure change the change db version)

        //drop older table if exists
        db.execSQL("DROP TABLE IF EXISTS " + Constants.TABLE_NAME);

        //create table again
        onCreate(db);
    }

    //insert record to db
    public long insertRecord(String name,
                             String image,
                             String date,
                             String food,
                             String toy,
                             String words,
                             String character,
                             String addedTime,
                             String updateTime ){
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();

        //insert data
        values.put(Constants.C_NAME, name);
        values.put(Constants.C_IMAGE, image);
        values.put(Constants.C_DATE, date);
        values.put(Constants.C_FOOD, food);
        values.put(Constants.C_TOY, toy);
        values.put(Constants.C_WORDS, words);
        values.put(Constants.C_CHARACTER, character);
        values.put(Constants.C_ADDED_TIMESTAMP, addedTime);
        values.put(Constants.C_UPDATE_TIMESTAMP, updateTime);

        //insert row, it will return record id of saved record
        long id = db.insert(Constants.TABLE_NAME, null, values);

        //close db connection
        db.close();

        //return id of inserted record
        return id;

    }


    //update existing record to db
    public void updateRecord(String id,
                             String name,
                             String image,
                             String date,
                             String food,
                             String toy,
                             String words,
                             String character,
                             String addedTime,
                             String updateTime ){
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();

        //insert data
        values.put(Constants.C_NAME, name);
        values.put(Constants.C_IMAGE, image);
        values.put(Constants.C_DATE, date);
        values.put(Constants.C_FOOD, food);
        values.put(Constants.C_TOY, toy);
        values.put(Constants.C_WORDS, words);
        values.put(Constants.C_CHARACTER, character);
        values.put(Constants.C_ADDED_TIMESTAMP, addedTime);
        values.put(Constants.C_UPDATE_TIMESTAMP, updateTime);

        //insert row, it will return record id of saved record
        db.update(Constants.TABLE_NAME, values, Constants.C_ID+" =?", new String[]{id});

        //close db connection
        db.close();
    }

    //get all data
    public ArrayList<ModelRecord> getAllRecords(String orderBy){

        ArrayList<ModelRecord> recordsList = new ArrayList<>();
        //query to select records
        String selectQuery = "SELECT * FROM " + Constants.TABLE_NAME + " ORDER BY " + orderBy;

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        //looping through all records and add to list
        if(cursor.moveToFirst()){
            do {
                ModelRecord modelRecord = new ModelRecord(
                        ""+cursor.getInt(cursor.getColumnIndex(Constants.C_ID)),
                        ""+cursor.getString(cursor.getColumnIndex(Constants.C_NAME)),
                        ""+cursor.getString(cursor.getColumnIndex(Constants.C_IMAGE)),
                        ""+cursor.getString(cursor.getColumnIndex(Constants.C_DATE)),
                        ""+cursor.getString(cursor.getColumnIndex(Constants.C_FOOD)),
                        ""+cursor.getString(cursor.getColumnIndex(Constants.C_TOY)),
                        ""+cursor.getString(cursor.getColumnIndex(Constants.C_WORDS)),
                        ""+cursor.getString(cursor.getColumnIndex(Constants.C_CHARACTER)),
                        ""+cursor.getString(cursor.getColumnIndex(Constants.C_ADDED_TIMESTAMP)),
                        ""+cursor.getString(cursor.getColumnIndex(Constants.C_UPDATE_TIMESTAMP))
                );

                //add record to list
                recordsList.add(modelRecord);
            }while (cursor.moveToNext());
        }
        //close db connection
        db.close();

        //return the list
        return recordsList;
    }

    //search  data
    public ArrayList<ModelRecord> searchRecords(String query){

        ArrayList<ModelRecord> recordsList = new ArrayList<>();
        //query to select records
        String selectQuery = "SELECT * FROM " + Constants.TABLE_NAME + " WHERE " + Constants.C_NAME + " LIKE '%" + query + "%'";

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        //looping through all records and add to list
        if(cursor.moveToFirst()){
            do {
                ModelRecord modelRecord = new ModelRecord(
                        ""+cursor.getInt(cursor.getColumnIndex(Constants.C_ID)),
                        ""+cursor.getString(cursor.getColumnIndex(Constants.C_NAME)),
                        ""+cursor.getString(cursor.getColumnIndex(Constants.C_IMAGE)),
                        ""+cursor.getString(cursor.getColumnIndex(Constants.C_DATE)),
                        ""+cursor.getString(cursor.getColumnIndex(Constants.C_FOOD)),
                        ""+cursor.getString(cursor.getColumnIndex(Constants.C_TOY)),
                        ""+cursor.getString(cursor.getColumnIndex(Constants.C_WORDS)),
                        ""+cursor.getString(cursor.getColumnIndex(Constants.C_CHARACTER)),
                        ""+cursor.getString(cursor.getColumnIndex(Constants.C_ADDED_TIMESTAMP)),
                        ""+cursor.getString(cursor.getColumnIndex(Constants.C_UPDATE_TIMESTAMP))
                );

                //add record to list
                recordsList.add(modelRecord);
            }while (cursor.moveToNext());
        }
        //close db connection
        db.close();

        //return the list
        return recordsList;
    }

    //delete data using id
    public void deleteData(String id){
        SQLiteDatabase db = getWritableDatabase();
        db.delete(Constants.TABLE_NAME, Constants.C_ID + " = ?", new String[]{id});
        db.close();
    }

    //delete all data from table
    public void deleteAllData() {
        SQLiteDatabase db = getWritableDatabase();
        db.execSQL("DELETE FROM " + Constants.TABLE_NAME);
        db.close();
    }

    //get number of records
    public int getRecordsCount(){
        String countQuery = "SELECT * FROM " + Constants.TABLE_NAME;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(countQuery, null);

        int count = cursor.getCount();

        cursor.close();

        return count;
    }
}
