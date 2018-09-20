package com.example.alex_.splaat;

import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.sql.Time;
import java.util.Calendar;
import java.util.Date;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link StudAttendFrag.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link StudAttendFrag#newInstance} factory method to
 * create an instance of this fragment.
 */
public class StudAttendFrag extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private DatabaseReference myRef;
    private FirebaseUser firebaseUser;

    private FirebaseDatabase firebaseDatabase;
    private FirebaseAuth firebaseAuth;


    private OnFragmentInteractionListener mListener;

    public StudAttendFrag() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment TeachAlertsFrag.
     */
    // TODO: Rename and change types and number of parameters
    public static StudAttendFrag newInstance(String param1, String param2) {
        StudAttendFrag fragment = new StudAttendFrag();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.student_attendance_fragment, container,false);

        Calendar cal = Calendar.getInstance();
        final int currentDay = cal.get(Calendar.DAY_OF_WEEK);

        firebaseAuth = FirebaseAuth.getInstance();
        firebaseUser = firebaseAuth.getCurrentUser();
        FirebaseUser user = firebaseAuth.getCurrentUser();

        final TextView tvWeeklyVal = (TextView) view.findViewById(R.id.tvWeeklyVal);
        final TextView tvDailyVal = (TextView) view.findViewById(R.id.tvDailyVal);

        String userID = user.getUid();

        final DatabaseReference refAttend = FirebaseDatabase.getInstance().getReference("Attendance").child("users").child("uid").child(userID);

        refAttend.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                final String presentDays = dataSnapshot.getValue().toString();
                System.out.println(presentDays);


                String day1 = dataSnapshot.child("Day1").toString();
                String day2 = dataSnapshot.child("Day2").toString();
                String day3 = dataSnapshot.child("Day3").toString();
                String day4 = dataSnapshot.child("Day4").toString();
                String day5 = dataSnapshot.child("Day5").toString();
                String day0 = dataSnapshot.child("Day0").toString();

                System.out.println("DAY 1 IS " + day1);
                System.out.println("DAY 2 IS " + day2);
                System.out.println("DAY 3 IS " + day3);
                System.out.println("DAY 4 IS " + day4);
                System.out.println("DAY 5 IS " + day5);
                System.out.println("DAY 0 IS " + day0);

                String allWeek = day1.concat(day2.concat(day3.concat(day4.concat(day5))));
                double weeklyAttend = 0;
                double dailyAttend = 0;
                String dayHold = allWeek;

                int weekCounter = 0;
                for( int i=0; i < dayHold.length(); i++ ) {
                    if( dayHold.charAt(i) == 'P' ) {
                        weekCounter++;
                    }
                }

                weeklyAttend = weeklyAttend + weekCounter;
                double weeklyPercentage = (weeklyAttend/24) * 100;

                tvWeeklyVal.setText(weeklyPercentage + "%");

                System.out.println("CURRENT DAY = " + currentDay);
                int day1Counter = 0;
                if (currentDay == 1) {
                    for (int i = 0; i < day1.length(); i++) {
                        if (day1.charAt(i) == 'P') {
                            day1Counter++;
                        }
                    }
                    dailyAttend = dailyAttend + day1Counter;
                    dailyAttend = ((dailyAttend/5) * 100);
                    tvDailyVal.setText(dailyAttend + "%");
                }

                int day2Counter = 0;
                if (currentDay == 2) {
                    for (int i = 0; i < day2.length(); i++) {
                        if (day2.charAt(i) == 'P') {
                            day2Counter++;
                        }
                    }
                    dailyAttend = dailyAttend + day2Counter;
                    dailyAttend = ((dailyAttend/5) * 100);
                    tvDailyVal.setText(dailyAttend + "%");
                }

                int day3Counter = 0;
                if (currentDay == 3) {
                    for (int i = 0; i < day3.length(); i++) {
                        if (day3.charAt(i) == 'P') {
                            day3Counter++;
                        }
                    }
                    dailyAttend = dailyAttend + day3Counter;
                    dailyAttend = ((dailyAttend/5) * 100);
                    tvDailyVal.setText(dailyAttend + "%");
                }

                int day4Counter = 0;
                if (currentDay == 4) {
                    for (int i = 0; i < day4.length(); i++) {
                        if (day4.charAt(i) == 'P') {
                            day4Counter++;
                        }
                    }
                    dailyAttend = dailyAttend + day4Counter;
                    dailyAttend = ((dailyAttend/5) * 100);
                    tvDailyVal.setText(dailyAttend + "%");
                }

                int day5Counter = 0;
                if (currentDay == 5) {
                    for (int i = 0; i < day5.length(); i++) {
                        if (day5.charAt(i) == 'P') {
                            day5Counter++;
                        }
                    }
                    dailyAttend = dailyAttend + day5Counter;
                    dailyAttend = ((dailyAttend/4) * 100);
                    tvDailyVal.setText(dailyAttend + "%");
                }






            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {

            }

            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });



        return view;
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    /**@Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }*/

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }
}
