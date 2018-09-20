package com.example.alex_.splaat;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.EditText;
import android.widget.Button;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class StudentRegister extends SignInStudent implements View.OnClickListener {

    public static String EncodeString(String string) {
        return string.replace(".", ",");
    }

    public static String DecodeString(String string) {
        return string.replace(",", ".");
    }

    private FirebaseAuth firebaseAuth;
    private FirebaseDatabase mFirebaseDatabase;
    private DatabaseReference myRef;
    private static final String TAG = "StudentRegister";
    private EditText editTextStudName;
    private EditText editTextStudSName;
    private EditText editTextStudEmail;
    private EditText editTextStudYear;
    private Button buttonStudentSubmit;
    private ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student_register);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        editTextStudName = (EditText) findViewById(R.id.editTextStudName);
        editTextStudSName = (EditText) findViewById(R.id.editTextStudSName);
        editTextStudEmail = (EditText) findViewById(R.id.editTextStudEmail);
        editTextStudYear = (EditText) findViewById(R.id.editTextStudYear);
        buttonStudentSubmit = (Button) findViewById(R.id.buttonStudentSubmit);
        buttonStudentSubmit.setOnClickListener(this);
        myRef = FirebaseDatabase.getInstance().getReference();

        firebaseAuth = FirebaseAuth.getInstance();
        FirebaseDatabase mFirebaseDatabase = FirebaseDatabase.getInstance();
        DatabaseReference myRef = mFirebaseDatabase.getReference("Message");

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
    }

    public void StoreInDB () {
        String fName = editTextStudName.getText().toString().trim();
        String sName = editTextStudSName.getText().toString().trim();
        String email = editTextStudEmail.getText().toString().trim();
        String year = editTextStudYear.getText().toString().trim();
        progressDialog = new ProgressDialog(this);

        if (fName != null && sName != null && email != null && year != null) {
            Toast.makeText(this, "Details updated", Toast.LENGTH_SHORT).show();

            FirebaseUser user = firebaseAuth.getCurrentUser();
            String userID = user.getUid();
            myRef.child("Students").child("users").child("uid").child(userID).child("FirstName").setValue(fName + " ");
            myRef.child("Students").child("users").child("uid").child(userID).child("Surname").setValue(sName);
            myRef.child("Students").child("users").child("uid").child(userID).child("Email").setValue(email);
            myRef.child("Students").child("users").child("uid").child(userID).child("Year").setValue(year);
            //GetStudLessonsFirebase.year.endsWith("7");
            finish();
            startActivity(new Intent(StudentRegister.this, StudentHomeScreen.class));

            progressDialog.setMessage("User Registered");
            progressDialog.show();
            progressDialog.dismiss();
            return;

        }

        else {
            Toast.makeText(this, "Please Fill in all fields", Toast.LENGTH_SHORT).show();
            progressDialog.setMessage("Detail submission unsuccessful");
            progressDialog.show();
            progressDialog.dismiss();
        }

        progressDialog.dismiss();
        return;

    }

    public void onClick (View view) {
        if (view == buttonStudentSubmit) {
            StoreInDB();
        }
    }

    @Override
    public void onPointerCaptureChanged(boolean hasCapture) {

    }
}
