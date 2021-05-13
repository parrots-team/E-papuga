package com.example.epapuga;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import android.widget.Toast;

import androidx.annotation.Nullable;

public class MyDatabaseHelper extends SQLiteOpenHelper {

    private Context context;
    private static final String DATABASE_NAME = "Papugi.db";
    private static final int DATABASE_VERSION = 2;

    private static final String TABLE_NAME = "papugi";
    private static final String COLUMN_ID = "_id";
    private static final String COLUMN_TITLE = "title";
    private static final String COLUMN_OPIS = "opis";
    private static final String COLUMN_DATA = "data";
    private static final String COLUMN_JEDZENIE = "jedzenie";
    private static final String COLUMN_ZABAWKI = "zabawki";

     MyDatabaseHelper(@Nullable Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        this.context = context;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String query = "CREATE TABLE " + TABLE_NAME +
                " (" + COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                COLUMN_TITLE + " TEXT, " +
                COLUMN_OPIS + " TEXT, " +
                COLUMN_DATA + " TEXT, " +
                COLUMN_JEDZENIE + " TEXT, " +
                COLUMN_ZABAWKI + " TEXT);";
        db.execSQL(query);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i1) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(db);

    }


     void addPapuga(String title, String opis, String data, String jedzenie, String zabawki) {
         SQLiteDatabase db = this.getWritableDatabase();
         ContentValues cv = new ContentValues();

         cv.put(COLUMN_TITLE, title);
         cv.put(COLUMN_OPIS, opis);
         cv.put(COLUMN_DATA, data);
         cv.put(COLUMN_JEDZENIE, jedzenie);
         cv.put(COLUMN_ZABAWKI, zabawki);
         long result = db.insert(TABLE_NAME, null, cv);
         if (result == -1){
             Toast.makeText(context, "Błąd!", Toast.LENGTH_SHORT).show();
         }else {
             Toast.makeText(context, "Dodanie powiodło się!", Toast.LENGTH_SHORT).show();
         }
    }

    Cursor readAllData(){
        String query = "SELECT * FROM " + TABLE_NAME;
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = null;
        if(db != null){
            cursor = db.rawQuery(query, null);
        }
        return cursor;
    }

    void updateData(String row_id, String title, String opis,
                    String data, String jedzenie, String zabawki){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put(COLUMN_TITLE, title);
        cv.put(COLUMN_OPIS, opis);
        cv.put(COLUMN_DATA, data);
        cv.put(COLUMN_JEDZENIE, jedzenie);
        cv.put(COLUMN_ZABAWKI, zabawki);

        long result = db.update(TABLE_NAME, cv, "_id=?", new String[]{row_id});
        if(result == -1){
            Toast.makeText(context, "Failed", Toast.LENGTH_SHORT).show();
        }else {
            Toast.makeText(context, "Updated Successfully!", Toast.LENGTH_SHORT).show();
        }

    }

    void deleteOneRow(String row_id){
        SQLiteDatabase db = this.getWritableDatabase();
        long result = db.delete(TABLE_NAME, "_id=?", new String[]{row_id});
        if(result == -1){
            Toast.makeText(context, "Failed to Delete.", Toast.LENGTH_SHORT).show();
        }else{
            Toast.makeText(context, "Successfully Deleted.", Toast.LENGTH_SHORT).show();
        }
    }

    void deleteAllData(){
        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL("DELETE FROM " + TABLE_NAME);
    }


}

