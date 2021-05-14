package com.example.epapuga.Wikipedia;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.epapuga.R;

public class WikiActivity2 extends AppCompatActivity {

    ImageView mainImageView;
    TextView tytułtxt, opistxt, opistxt2;

    String data1, data2, data3;

    int wikiImgView;
    int myImage;
    int ScrollView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_wiki2);

        mainImageView = findViewById(R.id.mainImageView);
        tytułtxt = findViewById(R.id.tytułtxt);
        opistxt = findViewById(R.id.opistxt);
        opistxt2 = findViewById(R.id.opistxt2);

        getData();
        setData();
    }

    private void getData() {
        if (getIntent().hasExtra("wikiImgView") && getIntent().hasExtra("data1") &&
        getIntent().hasExtra("data2") && getIntent().hasExtra("data3")) {
            data1 = getIntent().getStringExtra("data1");
            data2 = getIntent().getStringExtra("data2");
            data3 = getIntent().getStringExtra("data3");
            wikiImgView = getIntent().getIntExtra("wikiImgView",1 );

        } else {
            Toast.makeText(this, "No data.", Toast.LENGTH_SHORT).show();
        }
    }

        private void setData ()
        {
            tytułtxt.setText(data1);
            opistxt.setText(data2);
            opistxt2.setText(data3);
            mainImageView.setImageResource(wikiImgView);
        }

    }


