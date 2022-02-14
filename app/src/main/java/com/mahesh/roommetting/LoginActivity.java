package com.mahesh.roommetting;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

import java.util.Objects;

public class LoginActivity extends AppCompatActivity {
    FirebaseAuth auth;
    EditText edtEmail,edtPassword;
    Button btnLogin,btnSignUp;

    ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        setTitle("Login");
        auth = FirebaseAuth.getInstance();

        progressDialog = new ProgressDialog(LoginActivity.this);
        progressDialog.setTitle("Please Wait");
        progressDialog.setMessage("Process is running...");

        edtEmail = findViewById(R.id.edtEmail);
        edtPassword = findViewById(R.id.edtPassword);
        btnLogin = findViewById(R.id.btnLogin);
        btnSignUp = findViewById(R.id.btnSignUp);

        btnSignUp.setOnClickListener(view -> {
            Intent intent = new Intent(getApplicationContext(),
                    SignUpActivity.class);
            startActivity(intent);
        });

        btnLogin.setOnClickListener(view -> {
            progressDialog.show();
            String email,pass;
            if(edtEmail.getText().toString().isEmpty() || edtPassword.getText().toString().isEmpty())
            {
                progressDialog.hide();
                Toast.makeText(LoginActivity.this,"Please Enter all the fields",Toast.LENGTH_SHORT).show();
            }
            else{
                progressDialog.show();
                email = edtEmail.getText().toString();
                    pass  = edtPassword.getText().toString();
                    auth.signInWithEmailAndPassword(email,pass).addOnCompleteListener(task -> {
                        progressDialog.hide();
                        if(task.isSuccessful())
                        {
//                                Toast.makeText(LoginActivity.this,"Signed In",Toast.LENGTH_SHORT).show();
                            Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                            startActivity(intent);
                        }
                        else
                        {
                            Toast.makeText(LoginActivity.this, Objects.requireNonNull(task.getException()).getLocalizedMessage(),Toast.LENGTH_SHORT).show();

                        }
                    });
            }
        });
    }
}