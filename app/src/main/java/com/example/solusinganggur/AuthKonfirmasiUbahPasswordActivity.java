package com.example.solusinganggur;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

public class AuthKonfirmasiUbahPasswordActivity extends AppCompatActivity {
    TextView kirimUlang;
    TextView kembaliLogin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_auth_konfirmasi_ubah_password);

        kirimUlang = findViewById(R.id.kirim_ulang);
        kirimUlang.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(), AuthLupaPasswordActivity.class));
                finish();
            }
        });

        kembaliLogin = findViewById(R.id.kembali_login);
        kembaliLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }

    @Override
    public void onBackPressed() {}
}
