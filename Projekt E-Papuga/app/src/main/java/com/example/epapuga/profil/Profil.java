package com.example.epapuga.profil;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.recyclerview.widget.RecyclerView;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.example.epapuga.R;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class Profil extends AppCompatActivity {

    //views
    private FloatingActionButton addRecordBtn;
    private RecyclerView recordsRv;

    //DB helper
    private MyDbHelper dbHelper;

    //action bar
    ActionBar actionBar;

    //sort options
    String orderByNewest = Constants.C_ADDED_TIMESTAMP + " DESC";
    String orderByOldest = Constants.C_ADDED_TIMESTAMP + " ASC";
    String orderByTitleAsc = Constants.C_NAME + " ASC";
    String orderByTitleDesc = Constants.C_NAME + " DESC";

    //for refreshing records, refresh with last chosen sort option
    String currentOrderByStatus = orderByNewest;

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

//        load records (by default newest first)
        loadRecords(orderByNewest);


        //click to start add record activity
        addRecordBtn.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                //start and record activity
                Intent intent = new Intent(Profil.this, AddUpdateRecordActivity.class);
                intent.putExtra("isEditMode", false); //want to add new data, set false
                startActivity(intent);
            }
        });
    }



    private void loadRecords(String orderBy) {
        currentOrderByStatus = orderBy;
        AdapterRecord adapterRecord = new AdapterRecord(Profil.this,
                dbHelper.getAllRecords(orderBy));

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
        loadRecords(currentOrderByStatus); // refresh records list
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
        int id = item.getItemId();
        if (id==R.id.action_sort){
            //show sort options (show in dialog)
            sortOptionDialog();
        }
        else if (id==R.id.action_delete_all){
            //delete all records
            dbHelper.deleteAllData();
            onResume();
        }
        return super.onOptionsItemSelected(item);
    }

    private void sortOptionDialog() {
        //options to display in dialog
        String[] options = {"Tytuły rosnąco", "Tytuły malejąco", "Najnowsze", "Najstarsze"};
        //dialog
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Sortuj według")
                .setItems(options, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        //handle option click
                        if (which == 0){
                            //tytuły rosnąco
                            loadRecords(orderByTitleAsc);
                        }
                        else if (which == 1){
                            //tytuły malejąco
                            loadRecords(orderByTitleDesc);
                        }
                        else if (which == 2){
                            //tytuły najnowsze
                            loadRecords(orderByNewest);
                        }
                        else if (which == 3){
                            //tytuły najstarsze
                            loadRecords(orderByOldest);
                        }
                    }
                })
                .create().show(); //show dialog
    }
}
