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

public class ParentRegister extends SignInParent implements View.OnClickListener {

    public static String EncodeString(String string) {
        return string.replace(".", ",");
    }

    public static String DecodeString(String string) {
        return string.replace(",", ".");
    }

    private FirebaseAuth firebaseAuth;
    private FirebaseDatabase mFirebaseDatabase;
    private DatabaseReference myRef;
    private static final String TAG = "ParentRegister";
    private EditText editTextParentName;
    private EditText editTextParentSName;
    private EditText editTextParentEmail;
    private EditText editTextParentYear;
    private Button buttonParentSubmit;
    private ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_parent_register);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        editTextParentName = (EditText) findViewById(R.id.editTextParentName);
        editTextParentSName = (EditText) findViewById(R.id.editTextParentSName);
        editTextParentEmail = (EditText) findViewById(R.id.editTextParentEmail);
        editTextParentYear = (EditText) findViewById(R.id.editTextParentKnownAs);
        buttonParentSubmit = (Button) findViewById(R.id.buttonParentSubmit);
        buttonParentSubmit.setOnClickListener(this);
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
        String fName = editTextParentName.getText().toString().trim();
        String sName = editTextParentSName.getText().toString().trim();
        String email = editTextParentEmail.getText().toString().trim();
        String studYear = editTextParentYear.getText().toString().trim();
        progressDialog = new ProgressDialog(this);

        if (fName != null && sName != null && email != null && studYear != null) {
            Toast.makeText(this, "Details updated", Toast.LENGTH_SHORT).show();
            FirebaseUser user = firebaseAuth.getCurrentUser();
            String userID = user.getUid();
            myRef.child("Parents").child("users").child("uid").child(userID).child("FirstName").setValue(fName + " ");
            myRef.child("Parents").child("users").child("uid").child(userID).child("Surname").setValue(sName);
            myRef.child("Parents").child("users").child("uid").child(userID).child("Email").setValue(email);
            myRef.child("Parents").child("users").child("uid").child(userID).child("Year").setValue(studYear);

            finish();
            startActivity(new Intent(ParentRegister.this, ParentHomeScreen.class));

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
        if (view == buttonParentSubmit) {
            StoreInDB();
        }
    }

    @Override
    public void onPointerCaptureChanged(boolean hasCapture) {

    }
}
