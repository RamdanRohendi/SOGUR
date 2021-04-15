package com.example.solusinganggur;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.solusinganggur.entity.PencariKerja;
import com.example.solusinganggur.entity.Perusahaan;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class PerusahaanDaftarActivity extends AppCompatActivity {
    private static final String TAG = "DaftarPeranActivity";

    private DatabaseReference mDatabase;
    private FirebaseAuth mAuth;
    private EditText edtNamaPerusahaan;
    private EditText edtEmail;
    private EditText edtAlamat;
    private EditText edtPassword;
    private EditText edtConfirmPassword;
    private EditText edtTentangPerusahaan;
    private Button btnDaftar;
    private ProgressBar progressBar;
    private String role;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_perusahaan_daftar);

        mDatabase = FirebaseDatabase.getInstance().getReference();
        mAuth = FirebaseAuth.getInstance();

        edtNamaPerusahaan = findViewById(R.id.input_nama_perusahaan);
        edtEmail = findViewById(R.id.input_email_perusahaan);
        edtAlamat = findViewById(R.id.input_alamat_perusahaan);
        edtPassword = findViewById(R.id.input_password);
        edtConfirmPassword = findViewById(R.id.input_confirm_password);
        edtTentangPerusahaan = findViewById(R.id.input_tentang_perusahaan);

        role = getIntent().getExtras().getString("role");

        progressBar = findViewById(R.id.progressbar);

        btnDaftar = findViewById(R.id.daftar_perusahaan);
        btnDaftar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                daftar();
            }
        });
    }

    private void daftar() {
        Log.d(TAG, "daftar");
        if (!validateForm()) {
            return;
        }

        String email = edtEmail.getText().toString();
        String password = edtPassword.getText().toString();

        progressBar.setVisibility(View.VISIBLE);
        mAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        Log.d(TAG, "daftar:onComplete:" + task.isSuccessful());

                        progressBar.setVisibility(View.GONE);

                        if (task.isSuccessful()) {
                            onAuthSuccess(task.getResult().getUser());
                        } else {
                            Toast.makeText(getApplicationContext(), "Daftar Gagal :(", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }

    private void onAuthSuccess(FirebaseUser user) {
        String username = edtNamaPerusahaan.getText().toString();
        String alamat = edtAlamat.getText().toString();
        String tentang = edtTentangPerusahaan.getText().toString();

        writeNewPerusahaan(user.getUid(), role, username, user.getEmail(), alamat, tentang);

        Toast.makeText(getApplicationContext(), "Daftar Berhasil !", Toast.LENGTH_SHORT).show();
        startActivity(new Intent(getApplicationContext(), AuthLoginActivity.class));
        finish();
    }

    private boolean validateForm() {
        boolean result = true;

        if (TextUtils.isEmpty(edtNamaPerusahaan.getText().toString())) {
            edtNamaPerusahaan.setError("Mohon Isi Nama Lengkapnya !");
            result = false;
        } else {
            edtNamaPerusahaan.setError(null);
        }

        if (TextUtils.isEmpty(edtAlamat.getText().toString())) {
            edtAlamat.setError("Mohon Isi Alamat Anda !");
            result = false;
        } else {
            edtAlamat.setError(null);
        }

        if (TextUtils.isEmpty(edtTentangPerusahaan.getText().toString())) {
            edtTentangPerusahaan.setError("Mohon Jelaskan Tentang Perusahaan Anda !");
            result = false;
        } else {
            edtTentangPerusahaan.setError(null);
        }

        if (TextUtils.isEmpty(edtEmail.getText().toString())) {
            edtEmail.setError("Mohon Masukan Alamat Email Anda !");
            result = false;
        } else {
            edtEmail.setError(null);
        }

        if (TextUtils.isEmpty(edtPassword.getText().toString())) {
            edtPassword.setError("Mohon Masukan Password Anda !");
            result = false;
        } else {
            edtEmail.setError(null);
        }

        if (TextUtils.isEmpty(edtConfirmPassword.getText().toString())) {
            edtConfirmPassword.setError("Mohon Masukan Password Anda !");
            result = false;
        } else {
            edtConfirmPassword.setError(null);
        }

        return result;
    }

    private void writeNewPerusahaan(String perjaId, String role, String nama, String email, String alamat, String tentang) {
        Perusahaan perusahaan = new Perusahaan(role, nama, email, alamat, tentang);

        mDatabase.child("user").child(perjaId).setValue(perusahaan);
    }

    public void kembali(View view) {
        startActivity(new Intent(getApplicationContext(), AuthPilihRoleActivity.class));
        finish();
    }

    @Override
    public void onBackPressed() {
        startActivity(new Intent(getApplicationContext(), AuthPilihRoleActivity.class));
        finish();
    }
}
