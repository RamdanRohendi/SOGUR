package com.example.solusinganggur;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;

public class PencariKerjaRejectedActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pencarikerja_rejected);
    }

    public void kembali(View view) {
        finish();
    }
}
