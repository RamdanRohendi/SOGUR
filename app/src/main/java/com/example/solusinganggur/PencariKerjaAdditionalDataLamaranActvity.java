package com.example.solusinganggur;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

public class PencariKerjaAdditionalDataLamaranActvity extends AppCompatActivity {
    private Button btnSerahkan;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pencarikerja_additional_data_lamaran);

        btnSerahkan = findViewById(R.id.btn_serahkan);
        btnSerahkan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(), PencariKerjaKonfirmasiAdditionalDataLamaran.class));
                finish();
            }
        });
    }

    public void kembali(View view) {
        finish();
    }
}
