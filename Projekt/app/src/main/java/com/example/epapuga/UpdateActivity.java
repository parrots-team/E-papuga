package com.example.epapuga;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class UpdateActivity extends AppCompatActivity {

    EditText title_input, opis_input, jedzenie_input, zabawki_input, data_input;
    Button update_button, delete_button;

    String id_papuga, title, opis, data, jedzenie, zabawki;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update);

        title_input = findViewById(R.id.title_input2);
        opis_input = findViewById(R.id.opis_input2);
        jedzenie_input = findViewById(R.id.jedzenie_input2);
        zabawki_input = findViewById(R.id.zabawki_input2);
        data_input = findViewById(R.id.data_input2);
        update_button = findViewById(R.id.update_button);
        delete_button = findViewById(R.id.delete_button);
        getAndSetIntentData();
        update_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MyDatabaseHelper myDB = new MyDatabaseHelper(UpdateActivity.this);
                title = title_input.getText().toString().trim();
                opis = opis_input.getText().toString().trim();
                data = data_input.getText().toString().trim();
                jedzenie = jedzenie_input.getText().toString().trim();
                zabawki = zabawki_input.getText().toString().trim();
                myDB.updateData(id_papuga, title, opis, data, jedzenie, zabawki);

                startActivity(new Intent(UpdateActivity.this, ProfilActivity.class));
            }
        });


        ActionBar ab = getSupportActionBar();
        if (ab != null) {
            ab.setTitle(title);
        }


        delete_button.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v) {
                confirmDialog();
            }
        });




    }

    void getAndSetIntentData(){
        if(getIntent().hasExtra("id_papuga") && getIntent().hasExtra("title") &&
                getIntent().hasExtra("opis") && getIntent().hasExtra("data")
                && getIntent().hasExtra("jedzenie") && getIntent().hasExtra("zabawki")){
            //Getting Data from Intent
            id_papuga = getIntent().getStringExtra("id_papuga");
            title = getIntent().getStringExtra("title");
            opis = getIntent().getStringExtra("opis");
            data = getIntent().getStringExtra("data");
            jedzenie = getIntent().getStringExtra("jedzenie");
            zabawki = getIntent().getStringExtra("zabawki");

            //Setting Intent Data
            title_input.setText(title);
            opis_input.setText(opis);
            data_input.setText(data);
            jedzenie_input.setText(jedzenie);
            zabawki_input.setText(zabawki);
            Log.d("stev", title+" "+opis+" "+data+" "+jedzenie+" "+zabawki);
        }else{
            Toast.makeText(this, "Brak danych", Toast.LENGTH_SHORT).show();
        }
    }

    void confirmDialog(){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Usunąć " + title + " ?");
        builder.setMessage("Czy na pewno chcesz usunąć " + title + " ?");
        builder.setPositiveButton("Tak", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                MyDatabaseHelper myDB = new MyDatabaseHelper(UpdateActivity.this);
                myDB.deleteOneRow(id_papuga);
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