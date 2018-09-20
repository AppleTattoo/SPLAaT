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
import android.util.Log;
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
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.iid.FirebaseInstanceId;

import java.util.Map;

public class SignInTeacher extends AppCompatActivity implements View.OnClickListener {

    private Button ButtonRegister;
    private Button ButtonSignIn;
    private TextView TextViewSignInMessage;
    private EditText EditTextEmailTeach;
    private EditText EditTextPassword;
    private ProgressDialog progressDialog;
    private FirebaseAuth firebaseAuth;
    private FirebaseDatabase mFirebaseDatabase;
    private DatabaseReference myRef;
    private static final String TAG = "SignInTeacher";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in_teacher);
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

        ButtonRegister = (Button)  findViewById(R.id.ButtonRegister);
        ButtonSignIn = (Button) findViewById(R.id.ButtonSignIn);
        EditTextEmailTeach = (EditText) findViewById(R.id.EditTextEmailTeach);
        EditTextPassword = (EditText) findViewById(R.id.EditTextPassword);
        TextViewSignInMessage = (TextView) findViewById(R.id.TextViewSignInMessage);
        firebaseAuth = FirebaseAuth.getInstance();
        progressDialog = new ProgressDialog(this);
        ButtonRegister.setOnClickListener(this);
        ButtonSignIn.setOnClickListener(this);
        mFirebaseDatabase = FirebaseDatabase.getInstance();
        myRef = FirebaseDatabase.getInstance().getReference();

        // Read from the database
        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                // This method is called once with the initial value and again
                // whenever data at this location is updated.
                Map<String, Object> map = (Map<String, Object>) dataSnapshot.getValue();
                Log.d(TAG, "Value is: TESTVALUE");
            }

            @Override
            public void onCancelled(DatabaseError error) {
                // Failed to read value
                Log.w(TAG, "Failed to read value.", error.toException());
            }
        });

    }

    private void RegUser(){
        String email = EditTextEmailTeach.getText().toString().trim();
        String password = EditTextPassword.getText().toString().trim();

        if (TextUtils.isEmpty(email)) {
            Toast.makeText(this, "Enter valid email address", Toast.LENGTH_SHORT).show();
            return;
        }

        if (TextUtils.isEmpty(password)) {
            //no pass entered
            Toast.makeText(this, "Enter a valid password", Toast.LENGTH_SHORT).show();
            return;
        }

        progressDialog.setMessage("User Registering");
        progressDialog.show();

        firebaseAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(task.isSuccessful()) {
                    finish();
                    Toast.makeText(SignInTeacher.this, "Registration successful", Toast.LENGTH_SHORT).show();
                    startActivity(new Intent(SignInTeacher.this, TeacherRegister.class));
                    Log.d(TAG, "onClick: Attempting to add object to Database.");
                    //store the email address in the database and encode the '.' as a ',' to stop errors.
                    String newUser = EditTextEmailTeach.getText().toString();
                    FirebaseUser user = firebaseAuth.getCurrentUser();
                    String userID = user.getUid();
                    myRef.child("users").child("uid").push().setValue(userID);
                    //Decode the '.' and ',' when displaying the email address.
                }
                else {
                    Toast.makeText(SignInTeacher.this, "Registration unsuccessful", Toast.LENGTH_SHORT).show();
                }
                progressDialog.dismiss();
            }
        });

    }

    private void userLogin() {
        String email = EditTextEmailTeach.getText().toString().trim();
        String password = EditTextPassword.getText().toString().trim();

        if (TextUtils.isEmpty(email)) {
            Toast.makeText(this, "Enter valid email address", Toast.LENGTH_SHORT).show();
            return;
        }

        if (TextUtils.isEmpty(password)) {
            //no pass entered
            Toast.makeText(this, "Enter a valid password", Toast.LENGTH_SHORT).show();
            return;
        }

        progressDialog.setMessage("User Registered");
        progressDialog.show();

        firebaseAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(task.isSuccessful()) {
                    finish();
                    startActivity(new Intent(SignInTeacher.this, TeacherHomeScreen.class));
                }
                else {
                    Toast.makeText(SignInTeacher.this, "Login unsuccessful", Toast.LENGTH_SHORT).show();
                }
                progressDialog.dismiss();
            }
        });

    }

    @Override
    public void onClick (View view) {

        if(view == ButtonRegister) {
            RegUser();
        }
        if(view == ButtonSignIn) {
            userLogin();
        }

        if(view == TextViewSignInMessage) {

        }
    }

}
