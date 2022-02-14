package com.mahesh.roommetting;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.Objects;

public class SignUpActivity extends AppCompatActivity {
    FirebaseAuth auth;
    FirebaseFirestore database;

    EditText edtName,edtEmail,edtPassword;
    Button btnSignUp,btnLogin;

    ProgressDialog progressDialog;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        auth = FirebaseAuth.getInstance();
        database = FirebaseFirestore.getInstance();

        progressDialog = new ProgressDialog(SignUpActivity.this);
        progressDialog.setTitle("Creating Account");
        progressDialog.setMessage("Account Creation is in Process");


        btnLogin = findViewById(R.id.btnLogin);
        btnSignUp = findViewById(R.id.btnSignup);
        edtName = findViewById(R.id.edtName);
        edtEmail = findViewById(R.id.edtEmail);
        edtPassword = findViewById(R.id.edtPassword);

        btnSignUp.setOnClickListener(view -> {

            if(edtName.getText().toString().isEmpty() || edtEmail.getText().toString().isEmpty() || edtPassword.getText().toString().isEmpty())
            {
                progressDialog.hide();
                Toast.makeText(SignUpActivity.this,"Please Enter all the fields",Toast.LENGTH_SHORT).show();
            }
            else{
                progressDialog.show();
                String email ,pass,name;
                email = edtEmail.getText().toString();
                pass = edtPassword.getText().toString();
                name  = edtName.getText().toString();

                User user = new User(name,email,pass);


                auth.createUserWithEmailAndPassword(email,pass).addOnCompleteListener(task -> {
                    progressDialog.hide();

                    if (task.isSuccessful()){
                        database.collection("Users").document().set(user).addOnSuccessListener(unused -> startActivity(new Intent(SignUpActivity.this,LoginActivity.class)));
                        Toast.makeText(SignUpActivity.this,"Account Is Created...",Toast.LENGTH_SHORT).show();
                    }else {
                        Toast.makeText(SignUpActivity.this, Objects.requireNonNull(task.getException()).getLocalizedMessage(),Toast.LENGTH_SHORT).show();
                    }
                });
            }

        });

        btnLogin.setOnClickListener(view -> {
            Intent intent = new Intent(SignUpActivity.this, LoginActivity.class);
            startActivity(intent);
        });
    }

}