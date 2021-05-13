package com.example.epapuga;

import androidx.appcompat.app.AppCompatActivity;


import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;


public class AddActivity extends AppCompatActivity {

    EditText title_input, opis_input, jedzenie_input, zabawki_input, data_input;
    Button save_button;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add);

        title_input = findViewById(R.id.title_input);
        opis_input = findViewById(R.id.opis_input);
        data_input = findViewById(R.id.data_input);
        jedzenie_input = findViewById(R.id.jedzenie_input);
        zabawki_input = findViewById(R.id.zabawki_input);
        save_button = findViewById(R.id.save_button);
        save_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                MyDatabaseHelper myDB = new MyDatabaseHelper(AddActivity.this);
                myDB.addPapuga(title_input.getText().toString().trim(),
                        opis_input.getText().toString().trim(),
                        data_input.getText().toString().trim(),
                        jedzenie_input.getText().toString().trim(),
                        zabawki_input.getText().toString().trim());

                startActivity(new Intent(AddActivity.this, ProfilActivity.class));
            }
        });

    }}

