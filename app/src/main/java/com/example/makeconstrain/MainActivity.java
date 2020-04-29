package com.example.makeconstrain;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationCompat.Builder;
import androidx.core.app.NotificationManagerCompat;

import android.app.Notification;
import android.content.Intent;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.database.DataSnapshot;
import  com.google.firebase.database.DatabaseError;

import android.os.Bundle;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference myRef =database.getReference();

    final DatabaseReference mntr_suhu = myRef.child("Tombol1");

    Button btn_nyala, btn_mati, btn_naik, btn_turun;
    TextView a, b;
    DatabaseReference coba, coba2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btn_mati = (Button) findViewById(R.id.tombol_off);
        btn_nyala = (Button) findViewById(R.id.tombol_on);
        btn_naik = (Button) findViewById(R.id.btn_up);
        btn_turun = (Button) findViewById(R.id.btn_down);
        a = (TextView) findViewById(R.id.suhunya);
        b = (TextView) findViewById(R.id.suhuremote);

        btn_nyala.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mntr_suhu.setValue(1);
            }
        });

        btn_mati.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mntr_suhu.setValue(2);
            }
        });

        btn_naik.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mntr_suhu.setValue(3);
            }
        });

        btn_turun.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mntr_suhu.setValue(4);
            }
        });

        coba = FirebaseDatabase.getInstance().getReference();
        coba.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                String ceksuhu=dataSnapshot.child("Suhu").getValue().toString();
                    a.setText(ceksuhu);
                    int nilai=Integer.parseInt(a.getText().toString());
                     if (nilai>24) {
                        Toast.makeText(getApplicationContext(),
                        "WARNING! Suhu Melebihi Batas Maksimal", Toast.LENGTH_LONG).show();
                         Intent serviceIntent = new Intent(MainActivity.this, ExampleService.class);
                         startService(serviceIntent);
                     }
                     else {
                         Intent serviceIntent = new Intent(MainActivity.this, ExampleService.class);
                         stopService(serviceIntent);
                     }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        coba2 = FirebaseDatabase.getInstance().getReference();
        coba2.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                String ceksuhu2=dataSnapshot.child("SuhuRemote").getValue().toString();
                b.setText(ceksuhu2);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }
}
