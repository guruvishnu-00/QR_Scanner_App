package com.example.resolute;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;

public class MainActivity extends AppCompatActivity {

    Button b;
    TextView qrtext;
    DatabaseReference qrdata;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        b=findViewById(R.id.button);
        qrtext =findViewById(R.id.qrtext);
        qrdata= FirebaseDatabase.getInstance().getReference().child("qrdatabase");


        b.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                IntentIntegrator integrator = new IntentIntegrator(MainActivity.this);
                integrator.setOrientationLocked(true);
                integrator.setPrompt("Scan QR code");
                integrator.setDesiredBarcodeFormats(IntentIntegrator.QR_CODE);
                integrator.initiateScan();

            }
        });
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {


        IntentResult intentResult= IntentIntegrator.parseActivityResult(requestCode,resultCode,data);
        if(intentResult!=null)
        {
            String contents=intentResult.getContents();
            if(contents!=null)
            {
                qrdata.push().setValue(intentResult.getContents());
                qrtext.setText(intentResult.getContents());
                Toast.makeText(MainActivity.this,"Data Inserted",Toast.LENGTH_LONG).show();
            }
        }
        else
        {
            super.onActivityResult(requestCode, resultCode, data);
        }
    }
}