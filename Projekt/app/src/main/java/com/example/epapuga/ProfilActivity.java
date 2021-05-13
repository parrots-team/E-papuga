package com.example.epapuga;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.Manifest;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import org.jetbrains.annotations.NotNull;

import java.io.IOException;
import java.util.ArrayList;
import java.util.BitSet;

import de.hdodenhof.circleimageview.CircleImageView;

public class ProfilActivity extends AppCompatActivity
{
    RecyclerView recyclerView;
    FloatingActionButton add_button;
    ImageView empty_img;
    TextView no_data;



    MyDatabaseHelper myDB;
    ArrayList<String> id_papuga, title, opis, data, jedzenie, zabawki;
    CustomAdapter customAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.profil_activity);

        recyclerView = findViewById(R.id.recyclerView);
        add_button = findViewById(R.id.add_button);
        empty_img = findViewById(R.id.empty_img);
        no_data = findViewById(R.id.no_data);





        add_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ProfilActivity.this, AddActivity.class);
                startActivity(intent);
            }
        });







        myDB = new MyDatabaseHelper(ProfilActivity.this);
        id_papuga = new ArrayList<>();
        title = new ArrayList<>();
        opis = new ArrayList<>();
        data = new ArrayList<>();
        jedzenie = new ArrayList<>();
        zabawki = new ArrayList<>();

        storeDatainArrays();

        customAdapter = new CustomAdapter(ProfilActivity.this, this, id_papuga, title, opis, data, jedzenie, zabawki);
        recyclerView.setAdapter(customAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(ProfilActivity.this));






    }



    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);


        if (requestCode == 1) {
            recreate();
        }
    }



    void storeDatainArrays() {
         Cursor cursor = myDB.readAllData();
         if(cursor.getCount() == 0){
             empty_img.setVisibility(View.VISIBLE);
             no_data.setVisibility(View.VISIBLE);
         }else{
             while (cursor.moveToNext()){
                 id_papuga.add(cursor.getString(0));
                 title.add(cursor.getString(1));
                 opis.add(cursor.getString(2));
                 data.add(cursor.getString(3));
                 jedzenie.add(cursor.getString(4));
                 zabawki.add(cursor.getString(5));
             }
             empty_img.setVisibility(View.GONE);
             no_data.setVisibility(View.GONE);
         }
     }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.my_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if(item.getItemId() == R.id.delete_all){
            confirmDialog();
        }
        return super.onOptionsItemSelected(item);
    }

    void confirmDialog(){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Usunąć wszystko?");
        builder.setMessage("Czy na pewno chcesz usunąć wszystkie profile?");
        builder.setPositiveButton("Tak", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                MyDatabaseHelper myDB = new MyDatabaseHelper(ProfilActivity.this);
                myDB.deleteAllData();
                Intent intent = new Intent(ProfilActivity.this, ProfilActivity.class);
                startActivity(intent);
                finish();

            }
        });
        builder.setNegativeButton("Nie", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

            }
        });
        builder.create().show();
    }
}
