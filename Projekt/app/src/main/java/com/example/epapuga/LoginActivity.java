package com.example.epapuga;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

public class LoginActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login_activity);
    }
    public void launchRejestracjaActivity(View el)
    {
        Intent intent = new Intent(this, RejestracjaActivity.class);
        startActivity(intent);
    }
    public void launchMainActivity(View el)
    {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }
}
