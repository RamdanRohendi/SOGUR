package com.example.solusinganggur;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;

public class PerusahaanAddLowonganActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_perusahaan_add_lowongan);
    }

    public void kembali(View view) {
        finish();
    }
}