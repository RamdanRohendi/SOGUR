package com.example.solusinganggur;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

public class PencariKerjaDaftarActivity extends AppCompatActivity {
    Button btnDaftar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pencarikerja_daftar);

        btnDaftar = findViewById(R.id.daftar_pcrkerja);
        btnDaftar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(), AuthLoginActivity.class));
            }
        });
    }

    public void kembali(View view) {
        finish();
    }
}
