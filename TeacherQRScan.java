package com.example.alex_.splaat;

import android.*;
import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.os.Vibrator;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.SparseArray;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.android.gms.vision.CameraSource;
import com.google.android.gms.vision.Detector;
import com.google.android.gms.vision.barcode.Barcode;
import com.google.android.gms.vision.barcode.BarcodeDetector;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.io.DataInputStream;
import java.io.IOException;

import android.content.pm.PackageManager;

import org.w3c.dom.Text;

public class TeacherQRScan extends AppCompatActivity {

    SurfaceView camera;
    TextView tvScan;
    CameraSource cameraSource;
    BarcodeDetector barcodeDetector;
    final int RequestCamPermission = 1001;

    private FirebaseAuth firebaseAuth;
    private FirebaseDatabase mFirebaseDatabase;
    private DatabaseReference myRef;
    private Button back;

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode) {
            case RequestCamPermission: {
                if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    if (ActivityCompat.checkSelfPermission(this, Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
                        return;
                    }
                    try {
                        cameraSource.start(camera.getHolder());
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
            break;
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_teacher_qrscan);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        back = (Button) findViewById(R.id.teachBack);

        camera = (SurfaceView) findViewById(R.id.svCamera);

        barcodeDetector = new BarcodeDetector.Builder(this).setBarcodeFormats(Barcode.QR_CODE).build();
        cameraSource = new CameraSource.Builder(this, barcodeDetector).setAutoFocusEnabled(true).setRequestedPreviewSize(640, 480).build();

        camera.getHolder().addCallback(new SurfaceHolder.Callback() {
            @Override
            public void surfaceCreated(SurfaceHolder holder) {
                if (ActivityCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
                    ActivityCompat.requestPermissions(TeacherQRScan.this,
                            new String[]{Manifest.permission.CAMERA}, RequestCamPermission);

                    return;
                }
                try {
                    cameraSource.start(camera.getHolder());
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {

            }

            @Override
            public void surfaceDestroyed(SurfaceHolder holder) {
                cameraSource.stop();
            }


        });

        tvScan = (TextView) findViewById(R.id.tvCameraText);
        myRef = FirebaseDatabase.getInstance().getReference();

        barcodeDetector.setProcessor(new Detector.Processor<Barcode>() {
            @Override
            public void release() {

            }

            @Override
            public void receiveDetections(Detector.Detections<Barcode> detections) {
                final SparseArray<Barcode> qrcodes = detections.getDetectedItems();
                if (qrcodes.size() != 0) {
                    tvScan.post(new Runnable() {
                        @Override
                        public void run() {
                            Vibrator vibrator = (Vibrator) getApplicationContext().getSystemService(Context.VIBRATOR_SERVICE);
                            vibrator.vibrate(10);//Feedback could be longer?
                            tvScan.setText("Student signed in as present");
                            String qrText = qrcodes.valueAt(0).displayValue;
                            System.out.println("QR CODE VALUE IS " + qrText);
                            String[] parts = qrText.split(" ");
                            final String present = parts[0];
                            final String studAndLess = parts[1];

                            String[] lessonNumTemp = studAndLess.split("/");
                            final String studUID = lessonNumTemp[0];
                            final String presentForLessonTemp = lessonNumTemp[1];

                            String[] dayNumParts = presentForLessonTemp.split(":");
                            final String presentForLesson = dayNumParts[0];
                            final String dayNum = dayNumParts[1];
                            System.out.println(dayNum);
                            if (presentForLesson.equals("0")) {
                                myRef.child("TEST ONLY").child("users").child("uid").child(studUID).child("Present").child("Day" + dayNum).child("Lesson" + presentForLesson).setValue(present);
                            } else {
                                myRef.child("Attendance").child("users").child("uid").child(studUID).child("Present").child("Day" + dayNum).child("Lesson" + presentForLesson).setValue(present);
                            }


                        }
                    });
                }
            }
        });
    }

    public void onClick (View view) {
        if (view == back) {
            startActivity(new Intent(TeacherQRScan.this, TeacherHomeScreen.class));
        }
    }

}

