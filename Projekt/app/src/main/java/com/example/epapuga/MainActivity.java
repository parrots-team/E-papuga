package com.example.epapuga;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import android.os.Bundle;
import android.widget.ImageButton;

import com.google.firebase.auth.FirebaseAuth;

public class MainActivity extends AppCompatActivity {

    private ImageButton imgLogout;

    public void launchProfilActivity(View el)
    {
        Intent intent = new Intent(this, ProfilActivity.class);
        startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        imgLogout = (ImageButton) findViewById(R.id.imgLogout);

        imgLogout.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                FirebaseAuth.getInstance().signOut();
                startActivity(new Intent(MainActivity.this, LoginActivity.class));

            }
        });
    }

}