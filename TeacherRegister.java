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

public class TeacherRegister extends SignInTeacher implements View.OnClickListener {

    public static String EncodeString(String string) {
        return string.replace(".", ",");
    }

    public static String DecodeString(String string) {
        return string.replace(",", ".");
    }

    private FirebaseAuth firebaseAuth;
    private FirebaseDatabase mFirebaseDatabase;
    private DatabaseReference myRef;
    private static final String TAG = "TeacherRegister";
    private EditText editTextTeachName;
    private EditText editTextTeachSName;
    private EditText editTextTeachEmail;
    private EditText editTextTeachKnownAs;
    private Button buttonTeacherSubmit;
    private ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_teacher_register);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        editTextTeachName = (EditText) findViewById(R.id.editTextTeachName);
        editTextTeachSName = (EditText) findViewById(R.id.editTextTeachSName);
        editTextTeachEmail = (EditText) findViewById(R.id.editTextTeachEmail);
        editTextTeachKnownAs = (EditText) findViewById(R.id.editTextTeachKnownAs);
        buttonTeacherSubmit = (Button) findViewById(R.id.buttonTeacherSubmit);
        buttonTeacherSubmit.setOnClickListener(this);
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
        String fName = editTextTeachName.getText().toString().trim();
        String sName = editTextTeachSName.getText().toString().trim();
        String email = editTextTeachEmail.getText().toString().trim();
        String knownAs = editTextTeachKnownAs.getText().toString().trim();
        progressDialog = new ProgressDialog(this);

        if (fName != null && sName != null && email != null && knownAs != null) {
            Toast.makeText(this, "Details updated", Toast.LENGTH_SHORT).show();

            FirebaseUser user = firebaseAuth.getCurrentUser();
            String userID = user.getUid();
            myRef.child("Teachers").child("users").child("uid").child(userID).child("Details").child("FirstName").setValue(fName + " ");
            myRef.child("Teachers").child("users").child("uid").child(userID).child("Details").child("Surname").setValue(sName);
            myRef.child("Teachers").child("users").child("uid").child(userID).child("Details").child("Email").setValue(email);
            myRef.child("Teachers").child("users").child("uid").child(userID).child("Details").child("KnownAs").setValue(knownAs);

            finish();
            startActivity(new Intent(TeacherRegister.this, TeacherHomeScreen.class));

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
        if (view == buttonTeacherSubmit) {
            StoreInDB();
        }
    }

    @Override
    public void onPointerCaptureChanged(boolean hasCapture) {

    }
}
