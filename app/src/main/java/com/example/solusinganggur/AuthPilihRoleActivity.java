package com.example.solusinganggur;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

public class AuthPilihRoleActivity extends AppCompatActivity implements View.OnClickListener {
    private Button pncrKerja;
    private Button perusahaan;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_auth_pilih_role);

        pncrKerja = (Button) findViewById(R.id.pncrKerja);
        pncrKerja.setOnClickListener(this);
        perusahaan = (Button) findViewById(R.id.perusahaan);
        perusahaan.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.pncrKerja:
                Intent pncrKerja = new Intent(AuthPilihRoleActivity.this, PencariKerjaDaftarActivity.class);
                startActivity(pncrKerja);
                break;
            case R.id.perusahaan:
                Intent perusahaan = new Intent(AuthPilihRoleActivity.this, PerusahaanDaftarActivity.class);
                startActivity(perusahaan);
                break;
        }
        ;
    }

    public void kembali(View view) {
        finish();
    }
}
