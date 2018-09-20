package com.example.alex_.splaat;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class SignInStudent extends AppCompatActivity implements View.OnClickListener {

    private Button ButtonRegisterS;
    private Button ButtonSignInS;
    private TextView TextViewSignInMessageS;
    private EditText EditTextEmailS;
    private EditText EditTextPasswordS;
    private ProgressDialog progressDialogS;
    private FirebaseAuth firebaseAuthS;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in_student);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Please sign in with your username and password.", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        ButtonRegisterS = (Button)  findViewById(R.id.ButtonRegisterS);
        ButtonSignInS = (Button) findViewById(R.id.ButtonSignInS);
        EditTextEmailS = (EditText) findViewById(R.id.EditTextEmailS);
        EditTextPasswordS = (EditText) findViewById(R.id.EditTextPasswordS);
        TextViewSignInMessageS = (TextView) findViewById(R.id.TextViewSignInMessageS);
        firebaseAuthS = FirebaseAuth.getInstance();
        progressDialogS = new ProgressDialog(this);
        ButtonRegisterS.setOnClickListener(this);
        ButtonSignInS.setOnClickListener(this);
    }

    private void RegUser(){
        String email = EditTextEmailS.getText().toString().trim();
        String password = EditTextPasswordS.getText().toString().trim();

        if (TextUtils.isEmpty(email)) {
            Toast.makeText(this, "Enter valid email address", Toast.LENGTH_SHORT).show();
            return;
        }

        if (TextUtils.isEmpty(password)) {
            //no pass entered
            Toast.makeText(this, "Enter a valid password", Toast.LENGTH_SHORT).show();
            return;
        }

        progressDialogS.setMessage("User Registered");
        progressDialogS.show();

        firebaseAuthS.createUserWithEmailAndPassword(email, password).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(task.isSuccessful()) {
                    finish();
                    Toast.makeText(SignInStudent.this, "Registration successful", Toast.LENGTH_SHORT).show();
                    startActivity(new Intent(SignInStudent.this, StudentRegister.class));
                }
                else {
                    Toast.makeText(SignInStudent.this, "Registration unsuccessful", Toast.LENGTH_SHORT).show();
                }
                progressDialogS.dismiss();
            }
        });

    }

    private void userLogin() {
        String email = EditTextEmailS.getText().toString().trim();
        String password = EditTextPasswordS.getText().toString().trim();

        if (TextUtils.isEmpty(email)) {
            Toast.makeText(this, "Enter valid email address", Toast.LENGTH_SHORT).show();
            return;
        }

        if (TextUtils.isEmpty(password)) {
            //no pass entered
            Toast.makeText(this, "Enter a valid password", Toast.LENGTH_SHORT).show();
            return;
        }

        progressDialogS.setMessage("User Registered");
        progressDialogS.show();

        firebaseAuthS.signInWithEmailAndPassword(email, password).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(task.isSuccessful()) {
                    finish();
                    startActivity(new Intent(SignInStudent.this, StudentHomeScreen.class));
                }
                else {
                    Toast.makeText(SignInStudent.this, "Login unsuccessful", Toast.LENGTH_SHORT).show();
                }
                progressDialogS.dismiss();
            }
        });

    }

    @Override
    public void onClick (View view) {

        if(view == ButtonRegisterS) {
            RegUser();
        }
        if(view == ButtonSignInS) {
            userLogin();
        }

        if(view == TextViewSignInMessageS) {

        }
    }

}
