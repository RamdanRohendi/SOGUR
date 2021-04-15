package com.example.solusinganggur;


import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;



public class PerusahaanDaftarActivity extends AppCompatActivity {
    private DatabaseReference mDatabase;
    private FirebaseAuth mAuth;

    private EditText txtNamaPerusahaan;
    private TextInputLayout inputLayoutNamaPerusahaan;
    private EditText txtEmailPerusahaan;
    private TextInputLayout inputLayoutEmailPerusahaan;
    private EditText txtAlamatPerusahaan;
    private TextInputLayout inputLayoutAlamatPerusahaan;
    private EditText txtPassword;
    private TextInputLayout inputLayoutPassword;
    private EditText txtConfirmPassword;
    private TextInputLayout inputLayoutConfirmPassword;
    private EditText txtTentangPerusahaan;
    private Button btnDaftar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_perusahaan_daftar);
        mDatabase = FirebaseDatabase.getInstance().getReference();
        mAuth = FirebaseAuth.getInstance();

        txtNamaPerusahaan = findViewById(R.id.isiNamaPerusahaan);
        inputLayoutNamaPerusahaan = findViewById(R.id.inputnamaperusahaan);
        txtEmailPerusahaan = findViewById(R.id.isiEmailPerusahaan);
        inputLayoutEmailPerusahaan = findViewById(R.id.inputemailperusahaan);
        txtAlamatPerusahaan = findViewById(R.id.isiAlamatPerusahaan);
        inputLayoutAlamatPerusahaan = findViewById(R.id.inputalamatperusahaan);
        txtPassword = findViewById(R.id.isiPassword);
        inputLayoutPassword = findViewById(R.id.inputpassword);
        txtConfirmPassword = findViewById(R.id.isiConfirmPassword);
        inputLayoutConfirmPassword = findViewById(R.id.inputconfirmpassword);

        btnDaftar = findViewById(R.id.daftar_perusahaan);
        btnDaftar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (txtNamaPerusahaan.getText().toString().trim().isEmpty()) {
                    inputLayoutNamaPerusahaan.setError("Nama Perusahaan tidak boleh kosong");
                    return;
                }

                if (txtEmailPerusahaan.getText().toString().trim().isEmpty()) {
                    inputLayoutEmailPerusahaan.setError("Email Perusahaan tidak boleh kosong");
                    return;
                }

                if (txtPassword.getText().toString().trim().isEmpty()) {
                    inputLayoutPassword.setError("Password tidak boleh kosong");
                    return;
                }

                if (TextUtils.isEmpty(txtConfirmPassword.getText().toString())) {
                    inputLayoutConfirmPassword.setError("Masukkan Password Konfirmasi");

                    if (!txtConfirmPassword.equals(txtPassword)) {
                        inputLayoutConfirmPassword.setError("Password tidak sama");
                    }
                }
            }
        });
    }

    public void kembali(View view) {
        finish();
    }

}
