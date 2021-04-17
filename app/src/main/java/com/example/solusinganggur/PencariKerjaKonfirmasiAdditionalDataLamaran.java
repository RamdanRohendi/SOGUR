package com.example.solusinganggur;

import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

public class PencariKerjaKonfirmasiAdditionalDataLamaran extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pencarikerja_konfirmasi_additional_data_lamaran);
    }

    public void kembali(View view) {
        finish();
    }
}
