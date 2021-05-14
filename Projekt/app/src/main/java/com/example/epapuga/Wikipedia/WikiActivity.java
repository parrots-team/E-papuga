package com.example.epapuga.Wikipedia;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.epapuga.R;
import com.example.epapuga.Wikipedia.WikiAdapter;

public class WikiActivity extends AppCompatActivity
{

    RecyclerView recyclerView;


    String s1[], s2[], s3[];
    int images[] = {R.drawable.aleksandretta, R.drawable.amazonka, R.drawable.ara, R.drawable.falista,
            R.drawable.kakadu, R.drawable.nimfa, R.drawable.zako};
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.wiki_activity);

        recyclerView = findViewById(R.id.recyclerView);

        s1 = getResources().getStringArray(R.array.Papugedia);
        s2 = getResources().getStringArray(R.array.opis);
        s3 = getResources().getStringArray(R.array.opisW);

        WikiAdapter wikiAdapter = new WikiAdapter(this, s1,s2,s3, images);
        recyclerView.setAdapter(wikiAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

    }
}


