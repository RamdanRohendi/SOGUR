package com.example.solusinganggur;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.RelativeLayout;

public class BantuanActivity extends AppCompatActivity {
    RelativeLayout btnInfoApk;
    RelativeLayout btnKontakKami;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile_bantuan1);

        btnInfoApk = findViewById(R.id.info_aplikasi);
        btnInfoApk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(), InfoAplikasiActivity.class));
            }
        });

        btnKontakKami = findViewById(R.id.kontak_kami);
        btnKontakKami.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(), KontakKamiActivity.class));
            }
        });
    }

    public void kembali(View view){
        finish();
    }
}
