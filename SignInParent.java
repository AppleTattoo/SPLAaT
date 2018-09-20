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

public class SignInParent extends AppCompatActivity implements View.OnClickListener {

    private Button ButtonRegisterP;
    private Button ButtonSignInP;
    private TextView TextViewSignInMessageP;
    private EditText EditTextEmailP;
    private EditText EditTextPasswordP;
    private ProgressDialog progressDialogP;
    private FirebaseAuth firebaseAuthP;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in_parent);
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

        ButtonRegisterP = (Button)  findViewById(R.id.ButtonRegisterP);
        ButtonSignInP = (Button) findViewById(R.id.ButtonSignInP);
        EditTextEmailP = (EditText) findViewById(R.id.EditTextEmailP);
        EditTextPasswordP = (EditText) findViewById(R.id.EditTextPasswordP);
        TextViewSignInMessageP = (TextView) findViewById(R.id.TextViewSignInMessageP);
        firebaseAuthP = FirebaseAuth.getInstance();
        progressDialogP = new ProgressDialog(this);
        ButtonRegisterP.setOnClickListener(this);
        ButtonSignInP.setOnClickListener(this);
    }

    private void RegUser(){
        String email = EditTextEmailP.getText().toString().trim();
        String password = EditTextPasswordP.getText().toString().trim();

        if (TextUtils.isEmpty(email)) {
            Toast.makeText(this, "Enter valid email address", Toast.LENGTH_SHORT).show();
            return;
        }

        if (TextUtils.isEmpty(password)) {
            //no pass entered
            Toast.makeText(this, "Enter a valid password", Toast.LENGTH_SHORT).show();
            return;
        }

        progressDialogP.setMessage("User Registered");
        progressDialogP.show();

        firebaseAuthP.createUserWithEmailAndPassword(email, password).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(task.isSuccessful()) {
                    finish();
                    Toast.makeText(SignInParent.this, "Registration successful", Toast.LENGTH_SHORT).show();
                    startActivity(new Intent(SignInParent.this, ParentRegister.class));
                }
                else {
                    Toast.makeText(SignInParent.this, "Registration unsuccessful", Toast.LENGTH_SHORT).show();
                }
                progressDialogP.dismiss();
            }
        });

    }

    private void userLogin() {
        String email = EditTextEmailP.getText().toString().trim();
        String password = EditTextPasswordP.getText().toString().trim();

        if (TextUtils.isEmpty(email)) {
            Toast.makeText(this, "Enter valid email address", Toast.LENGTH_SHORT).show();
            return;
        }

        if (TextUtils.isEmpty(password)) {
            //no pass entered
            Toast.makeText(this, "Enter a valid password", Toast.LENGTH_SHORT).show();
            return;
        }

        progressDialogP.setMessage("User Registered");
        progressDialogP.show();

        firebaseAuthP.signInWithEmailAndPassword(email, password).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(task.isSuccessful()) {
                    finish();
                    startActivity(new Intent(SignInParent.this, ParentHomeScreen.class));
                }
                else {
                    Toast.makeText(SignInParent.this, "Login unsuccessful", Toast.LENGTH_SHORT).show();
                }
                progressDialogP.dismiss();
            }
        });

    }

    @Override
    public void onClick (View view) {

        if(view == ButtonRegisterP) {
            RegUser();
        }
        if(view == ButtonSignInP) {
            userLogin();
        }

        if(view == TextViewSignInMessageP) {

        }
    }

}
