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

import com.example.solusinganggur.entity.Pekerjaan;
import com.example.solusinganggur.entity.Perusahaan;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class PerusahaanDaftarActivity extends AppCompatActivity {
    private static final String TAG = "DaftarPeranActivity";
    private DatabaseReference mDatabase;
    private FirebaseAuth mAuth;

    private EditText edtNamaPerusahaan;;
    private TextInputLayout inputLayoutNamaPerusahaan;
    private EditText edtEmailPerusahaan;
    private TextInputLayout inputLayoutEmailPerusahaan;
    private EditText edtAlamatPerusahaan;
    private TextInputLayout inputLayoutAlamatPerusahaan;
    private EditText edtPassword;
    private TextInputLayout inputLayoutPassword;
    private EditText edtConfirmPassword;
    private TextInputLayout inputLayoutConfirmPassword;
    private EditText edtTentangPerusahaan;
    private TextInputLayout inputTentangPerusahaan;
    private Button btnDaftar;
    private ProgressBar progressBar;
    private String role;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_perusahaan_daftar);
        mDatabase = FirebaseDatabase.getInstance().getReference();
        mAuth = FirebaseAuth.getInstance();

        edtNamaPerusahaan = findViewById(R.id.isiNamaPerusahaan);
        inputLayoutNamaPerusahaan = findViewById(R.id.inputnamaperusahaan);
        edtEmailPerusahaan = findViewById(R.id.isiEmailPerusahaan);
        inputLayoutEmailPerusahaan = findViewById(R.id.inputemailperusahaan);
        edtAlamatPerusahaan = findViewById(R.id.isiAlamatPerusahaan);
        inputLayoutAlamatPerusahaan = findViewById(R.id.inputalamatperusahaan);
        edtPassword = findViewById(R.id.isiPassword);
        inputLayoutPassword = findViewById(R.id.inputpassword);
        edtConfirmPassword = findViewById(R.id.isiConfirmPassword);
        inputLayoutConfirmPassword = findViewById(R.id.inputconfirmpassword);
        edtTentangPerusahaan = findViewById(R.id.isiTentangPerusahaan);
        inputTentangPerusahaan = findViewById(R.id.inputtentangperusahaan);

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

        String email = edtEmailPerusahaan.getText().toString();
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
        String alamat = edtAlamatPerusahaan.getText().toString();
        String tentang = edtTentangPerusahaan.getText().toString();

        writeNewPerusahaan(user.getUid(), role, username, user.getEmail(), alamat, tentang);

        Toast.makeText(getApplicationContext(), "Daftar Berhasil !", Toast.LENGTH_SHORT).show();
        startActivity(new Intent(getApplicationContext(), AuthLoginActivity.class));
        finish();
    }

    private boolean validateForm() {
        boolean result = true;

        if (TextUtils.isEmpty(edtNamaPerusahaan.getText().toString())) {
            inputLayoutNamaPerusahaan.setError("Nama Perusahaan tidak boleh kosong");
            result = false;
        } else {
            inputLayoutNamaPerusahaan.setError(null);
        }

        if (TextUtils.isEmpty(edtEmailPerusahaan.getText().toString())) {
            inputLayoutEmailPerusahaan.setError("Email Perusahaan tidak boleh kosong");
            result = false;
        } else {
            inputLayoutEmailPerusahaan.setError(null);
        }

        if (TextUtils.isEmpty(edtAlamatPerusahaan.getText().toString())) {
            inputLayoutAlamatPerusahaan.setError("Alamat Perusahaan tidak boleh kosong");
            result = false;
        } else {
            inputLayoutAlamatPerusahaan.setError(null);
        }

        if (TextUtils.isEmpty(edtPassword.getText().toString())) {
            inputLayoutPassword.setError("Password tidak boleh kosong");
            result = false;
        } else {
            inputLayoutPassword.setError(null);
        }

        if (TextUtils.isEmpty(edtConfirmPassword.getText().toString())) {
            inputLayoutConfirmPassword.setError("Masukkan Password Konfirmasi");
            result = false;
        } else {
            inputLayoutConfirmPassword.setError(null);
        }

        if (!edtConfirmPassword.getText().toString().equals(edtPassword.getText().toString())) {
            inputLayoutConfirmPassword.setError("Password tidak sama");
            result = false;
        } else {
            inputLayoutConfirmPassword.setError(null);
        }

        if (TextUtils.isEmpty(edtTentangPerusahaan.getText().toString())) {
            inputTentangPerusahaan.setError("Tentang Perusahaan tidak boleh kosong");
            result = false;
        } else {
            inputTentangPerusahaan.setError(null);
        }

        return result;
    }

    private void writeNewPerusahaan(String perjaId, String role, String nama, String email, String alamat, String tentang) {
        Perusahaan perusahaan = new Perusahaan(nama, email, alamat, tentang);
        Pekerjaan pekerjaan = new Pekerjaan("none", "none");

        mDatabase.child("user").child(perjaId).child("role").setValue(role);
        mDatabase.child("user").child(perjaId).child("data").setValue(perusahaan);
        mDatabase.child("user").child(perjaId).child("lowongan_pekerjaan").setValue(pekerjaan);
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
