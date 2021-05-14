package com.example.epapuga.LoginRegister;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.epapuga.MainActivity;
import com.example.epapuga.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener
{

    private TextView idNiemaszKonta;
    private EditText idEmail, idHaslo;
    private Button btnLogowanie;

    private FirebaseAuth mAuth;
    private ProgressBar progressBar2;




    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login_activity);

        idNiemaszKonta = (TextView) findViewById(R.id.idNiemaszKonta);
        idNiemaszKonta.setOnClickListener(this);

        btnLogowanie = (Button) findViewById(R.id.btnLogowanie);
        btnLogowanie.setOnClickListener(this);

        idEmail = (EditText) findViewById(R.id.idEmail);
        idHaslo = (EditText) findViewById(R.id.idHaslo);

        progressBar2 = (ProgressBar) findViewById(R.id.progressBar2);

        mAuth = FirebaseAuth.getInstance();

    }

    @Override
    public void onClick(View v)
    {
        switch (v.getId()){
            case R.id.idNiemaszKonta:
                startActivity(new Intent(this, RejestracjaActivity.class));
                break;

            case R.id.btnLogowanie:
                userLogin();

        }


    }
    
    public void onBackPressed() {
        //do nothing
    }

    private void userLogin()
    {
        String email = idEmail.getText().toString().trim();
        String hasło = idHaslo.getText().toString().trim();

        if(email.isEmpty())
        {
            idEmail.setError("Imię jest wymagane!");
            idEmail.requestFocus();
            return;
        }

        if(hasło.isEmpty())
        {
            idHaslo.setError("Hasło jest wymagane!");
            idHaslo.requestFocus();
            return;
        }

        if (hasło.length() < 6)
        {
            idHaslo.setError("Hasło musi posiadać minimum 6 znaków");
            idHaslo.requestFocus();
            return;
        }

        progressBar2.setVisibility(View.VISIBLE);

        mAuth.signInWithEmailAndPassword(email, hasło).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {

                if(task.isSuccessful())
                {
                   startActivity(new Intent(LoginActivity.this, MainActivity.class));
                }else{
                    Toast.makeText(LoginActivity.this, "Logowanie nie powiodło się", Toast.LENGTH_SHORT).show();

                     }

            }
        });

    }
}
