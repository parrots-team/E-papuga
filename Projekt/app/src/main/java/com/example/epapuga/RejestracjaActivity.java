package com.example.epapuga;

import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;

public class RejestracjaActivity extends AppCompatActivity implements View.OnClickListener {

    private ImageView imgLogo;
    private Button btnRejestracja;
    private EditText editTextLogin, editTextPassword, editTextAge, editTextEmailAddress;
    private ProgressBar progressBar;

    private FirebaseAuth mAuth;



    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.rejestracja_activity);

        mAuth = FirebaseAuth.getInstance();

        imgLogo = (ImageView) findViewById(R.id.imgLogo);
        imgLogo.setOnClickListener(this);

        btnRejestracja = (Button) findViewById(R.id.btnRejestracja);
        btnRejestracja.setOnClickListener(this);

        editTextLogin = (EditText) findViewById(R.id.editTextImie);
        editTextPassword = (EditText) findViewById(R.id.editTextPassword);
        editTextAge = (EditText) findViewById(R.id.editTextAge);
        editTextEmailAddress = (EditText) findViewById(R.id.editTextEmailAddress);

        progressBar = (ProgressBar) findViewById(R.id.progressBar);
    }

    @Override
    public void onClick(View v)
    {
        switch (v.getId()){
            case R.id.imgLogo:
                startActivity(new Intent(this, LoginActivity.class));
                break;
            case R.id.btnRejestracja:
                btnRejestracja();
                break;
        }
    }

    private void btnRejestracja()
    {
        String login = editTextLogin.getText().toString().trim();
        String password = editTextPassword.getText().toString().trim();
        String age = editTextAge.getText().toString().trim();
        String email = editTextEmailAddress.getText().toString().trim();

        if(login.isEmpty())
        {
            editTextLogin.setError("Imię jest wymagane!");
            editTextLogin.requestFocus();
            return;
        }

        if(age.isEmpty())
        {
            editTextAge.setError("Wiek jest wymagany!");
            editTextAge.requestFocus();
            return;
        }

        if(email.isEmpty())
        {
            editTextEmailAddress.setError("Email jest wymagany!");
            editTextEmailAddress.requestFocus();
            return;
        }

        if(!Patterns.EMAIL_ADDRESS.matcher(email).matches())
        {
            editTextEmailAddress.setError("Proszę wprowadź poprawny email");
            editTextEmailAddress.requestFocus();
            return;
        }

        if(password.isEmpty())
        {
            editTextPassword.setError("Hasło jest wymagane!");
            editTextPassword.requestFocus();
            return;
        }

        if (password.length() < 6)
        {
            editTextPassword.setError("Hasło musi posiadać minimum 6 znaków");
            editTextPassword.requestFocus();
            return;
        }

        progressBar.setVisibility(View.VISIBLE);
        mAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {

                        if(task.isSuccessful()) {
                            User user = new User(login, age, email);

                            FirebaseDatabase.getInstance().getReference("Users")
                                    .child(FirebaseAuth.getInstance().getCurrentUser().getUid())
                                    .setValue(user).addOnCompleteListener(new OnCompleteListener<Void>() {

                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
                                    if (task.isSuccessful()) {
                                        Toast.makeText(RejestracjaActivity.this, "Rejestracja zakończyła się sukcesem!", Toast.LENGTH_SHORT).show();
                                        progressBar.setVisibility(View.GONE);

                                        startActivity(new Intent(RejestracjaActivity.this, LoginActivity.class));
                                    } else {
                                        Toast.makeText(RejestracjaActivity.this, "Rejestracja zakończyła się niepowodzeniem! Spróbuj ponownie", Toast.LENGTH_SHORT).show();
                                        progressBar.setVisibility(View.GONE);
                                    }
                                }
                            });
                        }else{
                            Toast.makeText(RejestracjaActivity.this, "Rejestracja zakończyła się niepowodzeniem! Spróbuj ponownie", Toast.LENGTH_SHORT).show();
                            progressBar.setVisibility(View.GONE);
                        }
                    }
                });
    }
}
