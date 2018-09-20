package com.example.alex_.splaat;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.google.firebase.auth.FirebaseAuth;

public class ParentHomeScreen extends AppCompatActivity {

    private static final String TAG = "TeacherHomeScreen";
    private SectionPageAdapter mSectionPageAdapter;
    private ViewPager mViewPager;

    private Button LogoutButtonClickedP;
    private FirebaseAuth firebaseAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_parent_home_screen);

        Log.d(TAG, "onCreate: Starting");

        mSectionPageAdapter = new SectionPageAdapter(getSupportFragmentManager());

        mViewPager = (ViewPager) findViewById(R.id.container);
        setupViewPager(mViewPager);

        TabLayout tabLayout = (TabLayout) findViewById(R.id.parTabs);
        tabLayout.setupWithViewPager(mViewPager);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
    }

    public void LogoutButtonClickedP(View v) {
        Intent i = new Intent(ParentHomeScreen.this, SignInParent.class);
        firebaseAuth.getInstance().signOut();
        finish();
        startActivity(i);
        finish();
    }

    public void onClick(View v) {
        if(v == LogoutButtonClickedP) {
            firebaseAuth.getInstance().signOut();
            finish();
            Intent i = (new Intent(ParentHomeScreen.this, SignInParent.class));
            startActivity(i);
            finish();
        }
    }

    private void setupViewPager(ViewPager viewPager) {
        SectionPageAdapter adapter = new SectionPageAdapter(getSupportFragmentManager());
        adapter.addFragment(new ParAlertsFrag(), "Alerts");
        adapter.addFragment(new ParProfileFrag(), "Profile");
        adapter.addFragment(new ParStudentsFrag(), "Students");
        adapter.addFragment(new ParDashFrag(), "DashBoard");
        viewPager.setAdapter(adapter);
    }
}
