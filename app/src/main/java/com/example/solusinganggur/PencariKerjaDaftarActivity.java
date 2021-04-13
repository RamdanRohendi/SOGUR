package com.example.solusinganggur;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.solusinganggur.entity.PencariKerja;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class PencariKerjaDaftarActivity extends AppCompatActivity {
    private static final String TAG = "DaftarPerjaActivity";

    private DatabaseReference mDatabase;
    private FirebaseAuth mAuth;
    private EditText edtNamaLengkap;
    private EditText edtEmail;
    private EditText edtPassword;
    private Button btnDaftar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pencarikerja_daftar);

        mDatabase = FirebaseDatabase.getInstance().getReference();
        mAuth = FirebaseAuth.getInstance();

        edtNamaLengkap = findViewById(R.id.isiNama);
        edtEmail = findViewById(R.id.isiEmail);
        edtPassword = findViewById(R.id.isiPassword);

        btnDaftar = findViewById(R.id.daftar_pcrkerja);
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

        mAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        Log.d(TAG, "daftar:onComplete:" + task.isSuccessful());

                        if (task.isSuccessful()) {
                            onAuthSuccess(task.getResult().getUser());
                        } else {
                            Toast.makeText(PencariKerjaDaftarActivity.this, "Daftar Gagal :(", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }

    private void onAuthSuccess(FirebaseUser user) {
        String username = edtNamaLengkap.getText().toString();

        writeNewPencariKerja(user.getUid(), username, user.getEmail());

        Toast.makeText(PencariKerjaDaftarActivity.this, "Daftar Berhasil !", Toast.LENGTH_SHORT).show();
        startActivity(new Intent(getApplicationContext(), AuthLoginActivity.class));
        finish();
    }

    private boolean validateForm() {
        boolean result = true;

        if (TextUtils.isEmpty(edtNamaLengkap.getText().toString())) {
            edtNamaLengkap.setError("Mohon Isi Nama Lengkapnya !");
            result = false;
        } else {
            edtNamaLengkap.setError(null);
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

        return result;
    }

    private void writeNewPencariKerja(String perjaId, String nama, String email) {
        PencariKerja pencariKerja = new PencariKerja(nama, email);

        mDatabase.child("pencariKerja").child(perjaId).setValue(pencariKerja);
    }

    public void kembali(View view) {
        finish();
    }
}
