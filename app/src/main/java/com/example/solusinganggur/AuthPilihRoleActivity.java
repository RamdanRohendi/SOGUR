package com.example.solusinganggur;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;

import androidx.appcompat.app.AppCompatActivity;

public class AuthPilihRoleActivity extends AppCompatActivity implements View.OnClickListener {
    private ImageButton pncrKerja;
    private ImageButton perusahaan;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_auth_pilih_role);

        pncrKerja = findViewById(R.id.pncrKerja);
        pncrKerja.setOnClickListener(this);
        perusahaan = findViewById(R.id.perusahaan);
        perusahaan.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.pncrKerja:
                Intent daftarPencarikerja = new Intent(getApplicationContext(), PencariKerjaDaftarActivity.class);
                daftarPencarikerja.putExtra("role", "pencarikerja");
                startActivity(daftarPencarikerja);
                finish();

                break;
            case R.id.perusahaan:
                Intent daftarPerusahaan = new Intent(getApplicationContext(), PerusahaanDaftarActivity.class);
                daftarPerusahaan.putExtra("role", "perusahaan");
                startActivity(daftarPerusahaan);
                finish();
                break;
        }
    }

    public void kembali(View view) {
        startActivity(new Intent(getApplicationContext(), AuthLoginActivity.class));
        finish();
    }

    @Override
    public void onBackPressed() {
        startActivity(new Intent(getApplicationContext(), AuthLoginActivity.class));
        finish();
    }
}
