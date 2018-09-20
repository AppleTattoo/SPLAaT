package com.example.alex_.splaat;

import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.BaseTransientBottomBar;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Adapter;
import android.widget.Button;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import static android.content.ContentValues.TAG;

public class StudentHomeScreen extends AppCompatActivity {

    private static final String TAG = "TeacherHomeScreen";
    private SectionPageAdapter mSectionPageAdapter;
    private ViewPager mViewPager;

    private Button LogoutButtonClickedS;

    private DatabaseReference myRef;
    private FirebaseAuth firebaseAuth;
    private FirebaseUser firebaseUser;
    private FirebaseDatabase firebaseDatabase;

    String fname;
    String sname;
    String email;
    String year;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student_home_screen);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        Log.d(TAG, "onCreate: Starting");

        mSectionPageAdapter = new SectionPageAdapter(getSupportFragmentManager());

        mViewPager = (ViewPager) findViewById(R.id.container);
        setupViewPager(mViewPager);

        TabLayout tabLayout = (TabLayout) findViewById(R.id.studTabs);
        tabLayout.setupWithViewPager(mViewPager);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                    Intent i = new Intent(StudentHomeScreen.this, QRCodeGen.class);
                    startActivity(i);
            }
        });
    }


    public void LogoutButtonClickedS(View v) {
        Intent i = new Intent(StudentHomeScreen.this, SignInStudent.class);
        firebaseAuth.getInstance().signOut();
        finish();
        startActivity(i);
        finish();
    }

    public void onClick(View v) {
        if(v == LogoutButtonClickedS) {
            firebaseAuth.getInstance().signOut();
            finish();
            Intent i = (new Intent(StudentHomeScreen.this, SignInStudent.class));
            startActivity(i);
            finish();
        }
    }

    private void setupViewPager(ViewPager viewPager) {
        SectionPageAdapter adapter = new SectionPageAdapter(getSupportFragmentManager());
        adapter.addFragment(new StudAlertsFrag(), "Alerts");
        adapter.addFragment(new StudAttendFrag(), "Attendance");
        adapter.addFragment(new StudProfFrag(), "Profile");
        adapter.addFragment(new StudLessonsFrag(), "Lessons");
        viewPager.setAdapter(adapter);
    }
}
