package com.example.alex_.splaat;

import android.os.Bundle;
import android.provider.ContactsContract;
import android.util.Log;
import android.view.View;

import java.lang.reflect.Array;
import java.sql.Time;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.*;
import com.google.firebase.*;

/**
 * Created by alex_ on 28/03/2018.
 */

public class GetStudLessonsFirebase extends Object {

    private String lesson;
    private String time;
    private String date;

    public GetStudLessonsFirebase (String lesson, String time, String date) {
        this.lesson = lesson;
        this.time = time;
        this.date = date;
    }
    public GetStudLessonsFirebase() {}

    public String getlesson() {return time;}
    public String getTime() {return lesson;}
    public String getDate() {return date;}
    public void setlesson(String name) {lesson = name;}
    public void setTime(String name) {time = name;}
    public void setDate(String name) {date = name;}
}

