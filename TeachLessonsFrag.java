package com.example.alex_.splaat;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.renderscript.Sampler;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

import static android.content.ContentValues.TAG;
import static android.content.Context.MODE_PRIVATE;

import android.content.SharedPreferences;
import android.widget.Toast;

import com.google.android.gms.fitness.data.Value;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.Calendar;
import java.util.Map;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link TeachLessonsFrag.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link TeachLessonsFrag#newInstance} factory method to
 * create an instance of this fragment.
 */
public class TeachLessonsFrag extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private TextView tvL1;
    private TextView tvD1;
    private TextView tvT1;
    private TextView tvL2;
    private TextView tvD2;
    private TextView tvT2;
    private TextView tvL3;
    private TextView tvD3;
    private TextView tvT3;
    private TextView tvL4;
    private TextView tvD4;
    private TextView tvT4;
    private EditText etLesson;
    private EditText etDay;
    private EditText etTime;
    private ImageButton addNewLess;
    private int incrementer = 0;

    public int day = Calendar.getInstance().get(Calendar.DAY_OF_WEEK);

    public FirebaseAuth firebaseAuth;
    public DatabaseReference myRef;
    public DatabaseReference lessonRef;
    public FirebaseUser user;

    public int count = 0;

    public String fbLesson;
    public String fbDay;
    public String fbTime;



    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;

    public TeachLessonsFrag() {
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
    public static TeachLessonsFrag newInstance(String param1, String param2) {
        TeachLessonsFrag fragment = new TeachLessonsFrag();
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
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.teacher_lessons_fragment, container, false);

        final String userID = FirebaseAuth.getInstance().getCurrentUser().getUid();

        etDay = (EditText) view.findViewById(R.id.etDay);

        String doW = "";
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

        etDay.setText(doW);

        firebaseAuth = FirebaseAuth.getInstance();
        FirebaseDatabase mFirebaseDatabase = FirebaseDatabase.getInstance();


        final DatabaseReference lessonRef = mFirebaseDatabase.getReference().child("Teachers").child("users").child("uid").child(userID).child("Lessons").child(doW).child("Lesson1");
        lessonRef.keepSynced(true);
        lessonRef.addChildEventListener(new ChildEventListener() {
            @Override

            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                String value = dataSnapshot.getValue(String.class);

                tvL1 = (TextView) view.findViewById(R.id.tvL1);
                tvD1 = (TextView) view.findViewById(R.id.tvD1);
                tvT1 = (TextView) view.findViewById(R.id.tvT1);


                if (value.contains(":")) {
                    tvT1.setText(value);


                } else if ((value.contains("Monday")) || (value.contains("Tuesday")) || (value.contains("Wednesday")) || (value.contains("Thursday"))
                        || (value.contains("Friday"))) {
                    tvD1.setText(value);
                } else if ((value.contains("Art") || (value.contains("DT") || (value.contains("Drama") || (value.contains("English")
                        || (value.contains("Geography") || (value.contains("History") || (value.contains("ICT") || (value.contains("Languages")
                        || (value.contains("Maths") || (value.contains("PE") || (value.contains("RE") || (value.contains("Science")))))))))))))) {
                    tvL1.setText(value);
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
            public void onCancelled(DatabaseError error) {
                // Failed to read value
                Log.w(TAG, "Failed to read value.", error.toException());
            }
        });

        final DatabaseReference lesson2Ref = mFirebaseDatabase.getReference().child("Teachers").child("users").child("uid").child(userID).child("Lessons").child(doW).child("Lesson2");
        lesson2Ref.keepSynced(true);
        lesson2Ref.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                String value2 = dataSnapshot.getValue(String.class);

                tvL2 = (TextView) view.findViewById(R.id.tvL2);
                tvD2 = (TextView) view.findViewById(R.id.tvD2);
                tvT2 = (TextView) view.findViewById(R.id.tvT2);


                if (value2.contains(":")) {
                    tvT2.setText(value2);
                } else if ((value2.contains("Monday")) || (value2.contains("Tuesday")) || (value2.contains("Wednesday")) || (value2.contains("Thursday"))
                        || (value2.contains("Friday"))) {
                    tvD2.setText(value2);
                } else if ((value2.contains("Art") || (value2.contains("DT") || (value2.contains("Drama") || (value2.contains("English")
                        || (value2.contains("Geography") || (value2.contains("History") || (value2.contains("ICT") || (value2.contains("Languages")
                        || (value2.contains("Maths") || (value2.contains("PE") || (value2.contains("RE") || (value2.contains("Science")))))))))))))) {
                    tvL2.setText(value2);
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
            public void onCancelled(DatabaseError error) {
                // Failed to read value
                Log.w(TAG, "Failed to read value.", error.toException());
            }
        });

        final DatabaseReference lesson3Ref = mFirebaseDatabase.getReference().child("Teachers").child("users").child("uid").child(userID).child("Lessons").child(doW).child("Lesson3");
        lesson3Ref.keepSynced(true);
        lesson3Ref.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                String value3 = dataSnapshot.getValue(String.class);

                tvL3 = (TextView) view.findViewById(R.id.tvL3);
                tvD3 = (TextView) view.findViewById(R.id.tvD3);
                tvT3 = (TextView) view.findViewById(R.id.tvT3);



                if (value3.contains(":")) {
                    tvT3.setText(value3);
                } else if ((value3.contains("Monday")) || (value3.contains("Tuesday")) || (value3.contains("Wednesday")) || (value3.contains("Thursday"))
                        || (value3.contains("Friday"))) {
                    tvD3.setText(value3);
                } else if ((value3.contains("Art") || (value3.contains("DT") || (value3.contains("Drama") || (value3.contains("English")
                        || (value3.contains("Geography") || (value3.contains("History") || (value3.contains("ICT") || (value3.contains("Languages")
                        || (value3.contains("Maths") || (value3.contains("PE") || (value3.contains("RE") || (value3.contains("Science")))))))))))))) {
                    tvL3.setText(value3);
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
            public void onCancelled(DatabaseError error) {
                // Failed to read value
                Log.w(TAG, "Failed to read value.", error.toException());
            }
        });

        final DatabaseReference lesson4Ref = mFirebaseDatabase.getReference().child("Teachers").child("users").child("uid").child(userID).child("Lessons").child(doW).child("Lesson4");
        lesson4Ref.keepSynced(true);
        lesson4Ref.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                String value4 = dataSnapshot.getValue(String.class);


                tvL4 = (TextView) view.findViewById(R.id.tvL4);
                tvD4 = (TextView) view.findViewById(R.id.tvD4);
                tvT4 = (TextView) view.findViewById(R.id.tvT4);

                if (value4.contains(":")) {
                    tvT4.setText(value4);
                } else if ((value4.contains("Monday")) || (value4.contains("Tuesday")) || (value4.contains("Wednesday")) || (value4.contains("Thursday"))
                        || (value4.contains("Friday"))) {
                    tvD4.setText(value4);
                } else if ((value4.contains("Art") || (value4.contains("DT") || (value4.contains("Drama") || (value4.contains("English")
                        || (value4.contains("Geography") || (value4.contains("History") || (value4.contains("ICT") || (value4.contains("Languages")
                        || (value4.contains("Maths") || (value4.contains("PE") || (value4.contains("RE") || (value4.contains("Science")))))))))))))) {
                    tvL4.setText(value4);
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
            public void onCancelled(DatabaseError error) {
                // Failed to read value
                Log.w(TAG, "Failed to read value.", error.toException());
            }
        });

        addNewLess = (ImageButton) view.findViewById(R.id.buttonAddLess);

        etLesson = (EditText) view.findViewById(R.id.etLesson);
        etDay = (EditText) view.findViewById(R.id.etDay);
        etTime = (EditText) view.findViewById(R.id.etTime);

        tvL1 = (TextView) view.findViewById(R.id.tvL1);
        tvL2 = (TextView) view.findViewById(R.id.tvL2);
        tvL3 = (TextView) view.findViewById(R.id.tvL3);
        tvL4 = (TextView) view.findViewById(R.id.tvL4);
        tvD1 = (TextView) view.findViewById(R.id.tvD1);
        tvD2 = (TextView) view.findViewById(R.id.tvD2);
        tvD3 = (TextView) view.findViewById(R.id.tvD3);
        tvD4 = (TextView) view.findViewById(R.id.tvD4);
        tvT1 = (TextView) view.findViewById(R.id.tvT1);
        tvT2 = (TextView) view.findViewById(R.id.tvT2);
        tvT3 = (TextView) view.findViewById(R.id.tvT3);
        tvT4 = (TextView) view.findViewById(R.id.tvT4);


        addNewLess.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                StoreInDB();
            }

        });
        return view;
    }

    public void StoreInDB() {
        String lesson1 = etLesson.getText().toString();
        String day1 = etDay.getText().toString();
        String time1 = etTime.getText().toString();

        String userID = FirebaseAuth.getInstance().getCurrentUser().getUid();

        String doW = "";
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

        firebaseAuth = FirebaseAuth.getInstance();
        FirebaseDatabase mFirebaseDatabase = FirebaseDatabase.getInstance();
        final DatabaseReference myRef = mFirebaseDatabase.getReference().child("Teachers").child("users").child("uid").child(userID).child("Lessons").child(doW);

        System.out.println(doW);

        if (tvL4.getText() != null) {
            if (count == 0) {
                if (lesson1 != null && day1 != null && time1 != null) {
                    Toast.makeText(getContext(), "Lesson updated", Toast.LENGTH_SHORT).show();
                    myRef.child("Lesson1").child("Lesson").setValue(lesson1);
                    myRef.child("Lesson1").child("Day").setValue(day1);
                    myRef.child("Lesson1").child("Time").setValue(time1);
                    //tvT1.setText(myRef.child("Lesson1").child("Lesson").setValue(time1).toString());
                    etLesson.setText("");
                    etDay.setText(doW);
                    etTime.setText("");


                } else {
                    Toast.makeText(getContext(), "Field(s) empty, lesson not added", Toast.LENGTH_SHORT).show();
                }
            }
            if (count == 1) {
                if ((tvT1.getText().toString().contains(":")) && lesson1 != null && day1 != null && time1 != null) {
                    Toast.makeText(getContext(), "Lesson updated", Toast.LENGTH_SHORT).show();
                    myRef.child("Lesson2").child("Lesson").setValue(lesson1);
                    myRef.child("Lesson2").child("Day").setValue(day1);
                    myRef.child("Lesson2").child("Time").setValue(time1);
                    etLesson.setText("");
                    etDay.setText(doW);
                    etTime.setText("");

                } else {
                    Toast.makeText(getContext(), "Field(s) empty, lesson not added", Toast.LENGTH_SHORT).show();
                }
            }
            if (count == 2) {
                if ((tvT2.getText().toString().contains(":")) && lesson1 != null && day1 != null && time1 != null) {
                    Toast.makeText(getContext(), "Lesson updated", Toast.LENGTH_SHORT).show();
                    myRef.child("Lesson3").child("Lesson").setValue(lesson1);
                    myRef.child("Lesson3").child("Day").setValue(day1);
                    myRef.child("Lesson3").child("Time").setValue(time1);
                    etLesson.setText("");
                    etDay.setText(doW);
                    etTime.setText("");

                } else {
                    Toast.makeText(getContext(), "Field(s) empty, lesson not added", Toast.LENGTH_SHORT).show();
                }
            }
            if (count == 3) {
                if ((tvT3.getText().toString().contains(":")) && lesson1 != null && day1 != null && time1 != null) {
                    Toast.makeText(getContext(), "Lesson updated", Toast.LENGTH_SHORT).show();
                    myRef.child("Lesson4").child("Lesson").setValue(lesson1);
                    myRef.child("Lesson4").child("Day").setValue(day1);
                    myRef.child("Lesson4").child("Time").setValue(time1);
                    etLesson.setText("");
                    etDay.setText(doW);
                    etTime.setText("");

                } else {
                    Toast.makeText(getContext(), "Field(s) empty, lesson not added", Toast.LENGTH_SHORT).show();
                }
            }

        }
        if (count >= 4) {
            Toast.makeText(getContext(), "Lessons Full", Toast.LENGTH_SHORT).show();
        }
        count = count +1;
    }


    //override with on pause to keep the same lessons after navigating away from the page.

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
