package com.example.alex_.splaat;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.google.firebase.auth.FirebaseAuth;

public class TeacherHomeScreen extends AppCompatActivity implements View.OnClickListener {

    private static final String TAG = "TeacherHomeScreen";
    private SectionPageAdapter mSectionPageAdapter;
    private ViewPager mViewPager;

    private Button LogoutButtonClicked;
    private FirebaseAuth firebaseAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_teacher_home_screen);

        Log.d(TAG, "onCreate: Starting");

        mSectionPageAdapter = new SectionPageAdapter(getSupportFragmentManager());

        mViewPager = (ViewPager) findViewById(R.id.container);
        setupViewPager(mViewPager);

        TabLayout tabLayout = (TabLayout) findViewById(R.id.teachTabs);
        tabLayout.setupWithViewPager(mViewPager);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(TeacherHomeScreen.this, TeacherQRScan.class);
                startActivity(i);
            }
        });
    }

    public void LogoutButtonClicked(View v) {
        Intent i = new Intent(TeacherHomeScreen.this, SignInTeacher.class);
        firebaseAuth.getInstance().signOut();
        finish();
        startActivity(i);
        finish();
    }

    @Override
    public void onClick(View v) {
        if(v == LogoutButtonClicked) {
            firebaseAuth.getInstance().signOut();
            finish();
            Intent i = (new Intent(TeacherHomeScreen.this, SignInTeacher.class));
            startActivity(i);
            finish();
        }
    }

    private void setupViewPager(ViewPager viewPager) {
        SectionPageAdapter adapter = new SectionPageAdapter(getSupportFragmentManager());
        adapter.addFragment(new TeachAlertsFrag(), "Alerts");
        adapter.addFragment(new TeachLessonsFrag(), "Lessons");
        adapter.addFragment(new TeachProfileFrag(), "Profile");
        adapter.addFragment(new TeachStudentsFrag(), "Students");
        adapter.addFragment(new TeachTimetableFrag(), "Timetable");
        viewPager.setAdapter(adapter);
    }
}
