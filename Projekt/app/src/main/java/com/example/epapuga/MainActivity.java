package com.example.epapuga;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import android.widget.ImageButton;

import com.example.epapuga.Kalendarz.CalendarActivity;
import com.example.epapuga.LoginRegister.LoginActivity;
import com.example.epapuga.Wikipedia.WikiActivity;
import com.example.epapuga.profil.Profil;
import com.google.firebase.auth.FirebaseAuth;

public class MainActivity extends AppCompatActivity {

    private ImageButton imgLogout;
    private ImageButton imgCalendar;
    private ImageButton imgWiki;
    private ImageButton imgProfil;




    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        imgLogout = (ImageButton) findViewById(R.id.imgLogout);
        imgCalendar = (ImageButton) findViewById(R.id.imgCalendar);
        imgWiki = (ImageButton) findViewById(R.id.imgWiki);
        imgProfil = (ImageButton) findViewById(R.id.imgProfil);


        imgLogout.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                FirebaseAuth.getInstance().signOut();
                startActivity(new Intent(MainActivity.this, LoginActivity.class));

            }
        });

        imgCalendar.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                startActivity(new Intent(MainActivity.this, CalendarActivity.class));

            }
        });

        imgWiki.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                startActivity(new Intent(MainActivity.this, WikiActivity.class));

            }
        });

        imgProfil.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                startActivity(new Intent(MainActivity.this, Profil.class));

            }
        });
    }

}