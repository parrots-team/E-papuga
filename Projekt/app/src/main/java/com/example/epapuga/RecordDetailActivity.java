package com.example.epapuga;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.os.Bundle;
import android.text.format.DateFormat;
import android.widget.TextView;

import com.blogspot.atifsoftwares.circularimageview.CircularImageView;

import java.util.Calendar;
import java.util.Locale;

public class RecordDetailActivity extends AppCompatActivity {

    //views
    private CircularImageView profileIv;
    private TextView characterTv, nameTv, dateTv, toyTv, foodTv,
            wordsTv, addedTimeTv, updateTimeTv;

    //actionbar
    private ActionBar actionBar;

    //db helper
    private MyDbHelper dbHelper;

    private String recordID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_record_detail);

        //setting ip actionbar with title and back button
        actionBar = getSupportActionBar();
        actionBar.setTitle("Szczegóły");
        actionBar.setDisplayShowHomeEnabled(true);
        actionBar.setDisplayHomeAsUpEnabled(true);

        //get record id from adapter through intent
        Intent intent = getIntent();
        recordID = intent.getStringExtra("RECORD_ID");

        //init db helper class
        dbHelper = new MyDbHelper(this);


        //init views
        profileIv = findViewById(R.id.profileIv);
        characterTv = findViewById(R.id.characterTv);
        nameTv = findViewById(R.id.nameTv);
        dateTv = findViewById(R.id.dateTv);
        toyTv = findViewById(R.id.toyTv);
        foodTv = findViewById(R.id.foodTv);
        wordsTv = findViewById(R.id.wordsTv);
        addedTimeTv = findViewById(R.id.addedTimeTv);
        updateTimeTv = findViewById(R.id.updateTimeTv);

        showRecordDetails();
    }


    private void showRecordDetails() {
        //get record details

        //query to select record based on record id
        String selectQuery = "SELECT * FROM " + Constants.TABLE_NAME + " WHERE "
                + Constants.C_ID +" =\"" + recordID+"\"";

        SQLiteDatabase db = dbHelper.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        //keep checking in whole db for that record
        if (cursor.moveToFirst()){
            do{
                //get data
                String id = ""+ cursor.getString(cursor.getColumnIndex(Constants.C_ID));
                String name = ""+ cursor.getString(cursor.getColumnIndex(Constants.C_NAME));
                String image = ""+ cursor.getString(cursor.getColumnIndex(Constants.C_IMAGE));
                String date = ""+ cursor.getString(cursor.getColumnIndex(Constants.C_DATE));
                String toy = ""+ cursor.getString(cursor.getColumnIndex(Constants.C_TOY));
                String food = ""+ cursor.getString(cursor.getColumnIndex(Constants.C_FOOD));
                String character = ""+ cursor.getString(cursor.getColumnIndex(Constants.C_CHARACTER));
                String words = ""+ cursor.getString(cursor.getColumnIndex(Constants.C_WORDS));
                String timestampAdded = ""+ cursor.getString(cursor.getColumnIndex(Constants.C_ADDED_TIMESTAMP));
                String timestampUpdate = ""+ cursor.getString(cursor.getColumnIndex(Constants.C_UPDATE_TIMESTAMP));

                //convert timestamp to dd/mm/rrrr gg:mm
                Calendar calendar1 = Calendar.getInstance(Locale.getDefault());
                calendar1.setTimeInMillis(Long.parseLong(timestampAdded));
                String timeAdded = ""+ DateFormat.format("dd/MM/yyyy hh:mm:aa", calendar1);

                Calendar calendar2 = Calendar.getInstance(Locale.getDefault());
                calendar2.setTimeInMillis(Long.parseLong(timestampUpdate));
                String timeUpdate = ""+ DateFormat.format("dd/MM/yyyy hh:mm:aa", calendar2);

                //set data
                nameTv.setText(name);
                characterTv.setText(character);
                dateTv.setText(date);
                toyTv.setText(toy);
                foodTv.setText(food);
                wordsTv.setText(words);
                addedTimeTv.setText(timeAdded);
                updateTimeTv.setText(timeUpdate);
                //if user doesn't attach image then imageUri will be null, so set a default image in that case
                if (image.equals("null")){
                    //no image in record, set default
                    profileIv.setImageResource(R.drawable.ic_person_black);
                }
                else{
                    //have image in record
                    profileIv.setImageURI(Uri.parse(image));
                }

            }while(cursor.moveToNext());
        }

        //close db connection
        db.close();
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return super.onSupportNavigateUp();
    }
}