package com.example.epapuga;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class Profil extends AppCompatActivity {

    //views
    private FloatingActionButton addRecordBtn;
    private RecyclerView recordsRv;

    //DB helper
    private MyDbHelper dbHelper;

    //action
    ActionBar actionBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profil);

        actionBar = getSupportActionBar();
        actionBar.setTitle("Papugi");

        //init views
        addRecordBtn = findViewById(R.id.addRecordBtn);
        recordsRv = findViewById(R.id.recordsRv);

//        init db helper class
        dbHelper = new MyDbHelper(this);

//        load records
        loadRecords();


        //click to start add record activity
        addRecordBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //start and record activity
                startActivity(new Intent(Profil.this, AddUpdateRecordActivity.class));
            }
        });
    }


    private void loadRecords() {
        AdapterRecord adapterRecord = new AdapterRecord(Profil.this,

                dbHelper.getAllRecords(Constants.C_ADDED_TIMESTAMP + " DESC"));


        recordsRv.setAdapter(adapterRecord);

        //set num of records
        actionBar.setSubtitle("ilość: "+dbHelper.getRecordsCount());
    }

    private void searchRecords(String query){
        AdapterRecord adapterRecord = new AdapterRecord(Profil.this, dbHelper.searchRecords(query));

        recordsRv.setAdapter(adapterRecord);
    }

    @Override
    protected void onResume() {
        super.onResume();
        loadRecords(); // refresh records list
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        //inflate menu
        getMenuInflater().inflate(R.menu.menu_profil, menu);

        //searchView
        MenuItem item = menu.findItem(R.id.action_search);
        SearchView searchView = (SearchView)item.getActionView();
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                //search when search button on keyboard clicked
                searchRecords(query);
                return true;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                //search as you type
                searchRecords(newText);
                return true;
            }
        });
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        //handle menu items
        return super.onOptionsItemSelected(item);
    }
}
