package com.example.alex_.splaat;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.app.Activity;
import android.*;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;


import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.GenericTypeIndicator;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import static android.content.ContentValues.TAG;

public class StudLessonsFrag extends Fragment {
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;

    private DatabaseReference myRef;
    private FirebaseUser firebaseUser;

    private FirebaseDatabase firebaseDatabase;
    private FirebaseAuth firebaseAuth;

    public Calendar cal = Calendar.getInstance();
    public String doW = null;

    public StudLessonsFrag() {
        // Required empty public constructor
    }

    public static StudLessonsFrag newInstance(String param1, String param2) {
        StudLessonsFrag fragment = new StudLessonsFrag();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    public void onClick(View v) {

    }

    @Override
    public void onCreate(Bundle savedInstanceState) {

        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference databaseReference = database.getReference();

        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {


    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.student_lessons_fragment, container, false);

        firebaseAuth = FirebaseAuth.getInstance();
        firebaseUser = firebaseAuth.getCurrentUser();

        final TextView tvL1 = (TextView) view.findViewById(R.id.tvL1);
        final TextView tvD1 = (TextView) view.findViewById(R.id.tvD1);
        final TextView tvT1 = (TextView) view.findViewById(R.id.tvT1);
        final TextView tvL2 = (TextView) view.findViewById(R.id.tvL2);
        final TextView tvD2 = (TextView) view.findViewById(R.id.tvD2);
        final TextView tvT2 = (TextView) view.findViewById(R.id.tvT2);
        final TextView tvL3 = (TextView) view.findViewById(R.id.tvL3);
        final TextView tvD3 = (TextView) view.findViewById(R.id.tvD3);
        final TextView tvT3 = (TextView) view.findViewById(R.id.tvT3);
        final TextView tvL4 = (TextView) view.findViewById(R.id.tvL4);
        final TextView tvD4 = (TextView) view.findViewById(R.id.tvD4);
        final TextView tvT4 = (TextView) view.findViewById(R.id.tvT4);
        final TextView tvL5 = (TextView) view.findViewById(R.id.tvL5);
        final TextView tvD5 = (TextView) view.findViewById(R.id.tvD5);
        final TextView tvT5 = (TextView) view.findViewById(R.id.tvT5);

        int day = Calendar.getInstance().get(Calendar.DAY_OF_WEEK);

        if (day == 2) {
            doW = "Monday";
        } else if (day == 3) {
            doW = "Tuesday";
        } else if (day == 4) {
            doW = "Wednesday";
        } else if (day == 5) {
            doW = "Thursday";
        } else if (day == 6) {
            doW = "Friday";
        }

        final DatabaseReference myRefDays = FirebaseDatabase.getInstance().getReference("Days");
        final DatabaseReference queryRef = FirebaseDatabase.getInstance().getReference();

        myRefDays.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, final String s) {
                final String value1 = dataSnapshot.getValue().toString();
                Log.d(TAG, "Value1 is: " + value1);

                String delimiterEquals = "=";
                String delimiterDT = ", ";

                if (value1.contains("14:00")) {

                    String[] valToSplit1;
                    valToSplit1 = value1.split(delimiterDT);
                    String timeLesson1 = valToSplit1[0];
                    String timeLesson2 = valToSplit1[1];
                    String timeLesson3 = valToSplit1[2];
                    String timeLesson4 = valToSplit1[3];
                    String timeLesson5 = valToSplit1[4];

                    timeLesson1 = timeLesson1.replace("{", "");

                    String[] timeLessonSplit1;
                    timeLessonSplit1 = timeLesson1.split(delimiterEquals);
                    String time1 = timeLessonSplit1[0];
                    String lesson1 = timeLessonSplit1[1];

                    String[] timeLessonSplit2;
                    timeLessonSplit2 = timeLesson2.split(delimiterEquals);
                    String time2 = timeLessonSplit2[0];
                    String lesson2 = timeLessonSplit2[1];

                    String[] timeLessonSplit3;
                    timeLessonSplit3 = timeLesson3.split(delimiterEquals);
                    String time3 = timeLessonSplit3[0];
                    String lesson3 = timeLessonSplit3[1];

                    String[] timeLessonSplit4;
                    timeLessonSplit4 = timeLesson4.split(delimiterEquals);
                    String time4 = timeLessonSplit4[0];
                    String lesson4 = timeLessonSplit4[1];

                    String[] timeLessonSplit5;
                    timeLessonSplit5 = timeLesson5.split(delimiterEquals);
                    String time5 = timeLessonSplit5[0];
                    String lesson5 = timeLessonSplit5[1];

                    if (cal.get(Calendar.DAY_OF_WEEK) == Calendar.MONDAY) {

                        tvD1.setText(doW);
                        tvT1.setText(time5);
                        tvL1.setText(lesson5);

                        tvD2.setText(doW);
                        tvT2.setText(time2);
                        tvL2.setText(lesson2);

                        tvD3.setText(doW);
                        tvT3.setText(time1);
                        tvL3.setText(lesson1);

                        tvD4.setText(doW);
                        tvT4.setText(time3);
                        tvL4.setText(lesson3);

                        tvD5.setText(doW);
                        tvT5.setText(time4);
                        tvL5.setText(lesson4);

                    } else if (cal.get(Calendar.DAY_OF_WEEK) == Calendar.TUESDAY) {

                        tvD1.setText(doW);
                        tvT1.setText(time5);
                        tvL1.setText(lesson5);

                        tvD2.setText(doW);
                        tvT2.setText(time2);
                        tvL2.setText(lesson2);

                        tvD3.setText(doW);
                        tvT3.setText(time1);
                        tvL3.setText(lesson1);

                        tvD4.setText(doW);
                        tvT4.setText(time3);
                        tvL4.setText(lesson3);

                        tvD5.setText(doW);
                        tvT5.setText(time4);
                        tvL5.setText(lesson4);

                    } else if (cal.get(Calendar.DAY_OF_WEEK) == Calendar.WEDNESDAY) {

                        tvD1.setText("Wednesday");
                        tvT1.setText(time5);
                        tvL1.setText(lesson5);

                        tvD2.setText("Wednesday");
                        tvT2.setText(time2);
                        tvL2.setText(lesson2);

                        tvD3.setText("Wednesday");
                        tvT3.setText(time1);
                        tvL3.setText(lesson1);

                        tvD4.setText("Wednesday");
                        tvT4.setText(time3);
                        tvL4.setText(lesson3);

                        tvD5.setText("Wednesday");
                        tvT5.setText(time4);
                        tvL5.setText(lesson4);

                    } else if (cal.get(Calendar.DAY_OF_WEEK) == Calendar.THURSDAY) {

                        tvD1.setText("Thursday");
                        tvT1.setText(time5);
                        tvL1.setText(lesson5);

                        tvD2.setText("Thursday");
                        tvT2.setText(time2);
                        tvL2.setText(lesson2);

                        tvD3.setText("Thursday");
                        tvT3.setText(time1);
                        tvL3.setText(lesson1);

                        tvD4.setText("Thursday");
                        tvT4.setText(time3);
                        tvL4.setText(lesson3);

                        tvD5.setText("Thursday");
                        tvT5.setText(time4);
                        tvL5.setText(lesson4);

                    }
                }/** else {

                    String[] valToSplit1;
                    valToSplit1 = value1.split(delimiterDT);
                    String timeLesson1 = valToSplit1[0];
                    String timeLesson2 = valToSplit1[1];
                    String timeLesson3 = valToSplit1[2];
                    String timeLesson4 = valToSplit1[3];

                    String[] timeLessonSplit1;
                    timeLessonSplit1 = timeLesson1.split(delimiterEquals);
                    String time1 = timeLessonSplit1[0];
                    time1 = time1.replace("{", " ");
                    String lesson1 = timeLessonSplit1[1];
                    lesson1 = lesson1.replace("}", " ");

                    String[] timeLessonSplit2;
                    timeLessonSplit2 = timeLesson2.split(delimiterEquals);
                    String time2 = timeLessonSplit2[0];
                    String lesson2 = timeLessonSplit2[1];

                    String[] timeLessonSplit3;
                    timeLessonSplit3 = timeLesson3.split(delimiterEquals);
                    String time3 = timeLessonSplit3[0];
                    String lesson3 = timeLessonSplit3[1];

                    String[] timeLessonSplit4;
                    timeLessonSplit4 = timeLesson4.split(delimiterEquals);
                    String time4 = timeLessonSplit4[0];
                    String lesson4 = timeLessonSplit4[1];

                    if (cal.get(Calendar.DAY_OF_WEEK) == Calendar.FRIDAY) {

                        tvL1.setVisibility(View.GONE);
                        tvD1.setVisibility(View.GONE);
                        tvT1.setVisibility(View.GONE);

                        tvD2.setText("Friday");
                        tvT2.setText(time2);
                        tvL2.setText(lesson2);

                        tvD3.setText("Friday");
                        tvT3.setText(time1);
                        tvL3.setText(lesson1);

                        tvD4.setText("Friday");
                        tvT4.setText(time3);
                        tvL4.setText(lesson3);

                        tvD5.setText("Friday");
                        tvT5.setText(time4);
                        tvL5.setText(lesson4);
                    }
                }**/
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

    /**
     * @Override public void onAttach(Context context) {
     * super.onAttach(context);
     * if (context instanceof OnFragmentInteractionListener) {
     * mListener = (OnFragmentInteractionListener) context;
     * } else {
     * throw new RuntimeException(context.toString()
     * + " must implement OnFragmentInteractionListener");
     * }
     * }
     */

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
