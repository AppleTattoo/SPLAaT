package com.example.alex_.splaat;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;

import android.widget.ImageView;
import android.widget.Button;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.FirebaseDatabase;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;
import com.journeyapps.barcodescanner.BarcodeEncoder;

import java.util.Calendar;


public class QRCodeGen extends AppCompatActivity {

    MultiFormatWriter multiFormatWriter = new MultiFormatWriter();
    final Context context = this;

    private Button back;
    private ImageView qrCode;

    private FirebaseUser firebaseUser;

    public FirebaseDatabase firebaseDatabase;
    public FirebaseAuth firebaseAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_qrcode_gen);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        Calendar cal = Calendar.getInstance();
        int currentHour = cal.get(Calendar.HOUR_OF_DAY);
        int lesson = 0;
        int day = Calendar.getInstance().get(Calendar.DAY_OF_WEEK);
        System.out.println(day);

        if (((currentHour > 8) && (currentHour < 15))) {
            if (currentHour == 9) {
                lesson = 1;
            } else if (currentHour == 10) {
                lesson = 2;
            } else if (currentHour == 11) {
                lesson = 3;
            } else if (currentHour == 13) {
                lesson = 4;
            } else if (currentHour == 14) {
                lesson = 5;
            }
        } else {
            lesson = 0;
        }

        firebaseAuth = FirebaseAuth.getInstance();
        firebaseUser = firebaseAuth.getCurrentUser();
        FirebaseUser user = firebaseAuth.getCurrentUser();

        String userID = user.getUid();
        String studPresent = "P ";
        System.out.println(currentHour);
        String fullQRPresent = studPresent.concat(userID).concat("/").concat(Integer.toString(lesson).concat(":" + day));

        back = (Button) findViewById(R.id.back);
        qrCode = (ImageView) findViewById(R.id.qrCode);
        Bitmap bitmap1 = getIntent().getParcelableExtra("QR code");
        qrCode.setImageBitmap(bitmap1);

        try {
            BitMatrix bitMatrix = multiFormatWriter.encode(fullQRPresent, BarcodeFormat.QR_CODE, 2500, 2500);
            BarcodeEncoder barcodeEncoder = new BarcodeEncoder();
            Bitmap bitmap = barcodeEncoder.createBitmap(bitMatrix);
            qrCode.setImageBitmap(bitmap);
        } catch (WriterException e) {
            e.printStackTrace();
        }
    }

    public void onClick (View view) {
        if (view == back) {
            startActivity(new Intent(QRCodeGen.this, StudentHomeScreen.class));
        }
    }

}



