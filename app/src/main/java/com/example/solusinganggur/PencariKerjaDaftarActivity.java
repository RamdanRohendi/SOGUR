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
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class PencariKerjaDaftarActivity extends AppCompatActivity {
    private static final String TAG = "DaftarPerjaActivity";

    private DatabaseReference mDatabase;
    private FirebaseAuth mAuth;
    private EditText txtNamaLengkap;
    private TextInputLayout inputLayoutNama;
    private EditText txtEmail;
    private TextInputLayout inputLayoutEmail;
    private EditText txtPassword;
    private TextInputLayout inputLayoutPassword;
    private EditText txtConfirmPassword;
    private TextInputLayout inputLayoutConfirmPassword;
    private Button btnDaftar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pencarikerja_daftar);

        mDatabase = FirebaseDatabase.getInstance().getReference();
        mAuth = FirebaseAuth.getInstance();

        txtNamaLengkap = findViewById(R.id.isiNama);
        inputLayoutNama = findViewById(R.id.inputnama);
        txtEmail = findViewById(R.id.isiEmail);
        inputLayoutEmail = findViewById(R.id.inputemail);
        txtPassword = findViewById(R.id.isiPassword);
        inputLayoutPassword = findViewById(R.id.inputpassword);
        txtConfirmPassword = findViewById(R.id.isiConfirmPassword);
        inputLayoutConfirmPassword = findViewById(R.id.inputconfirmpassword);

        btnDaftar = findViewById(R.id.daftar_pcrkerja);
        btnDaftar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                daftar();

                if (txtNamaLengkap.getText().toString().trim().isEmpty()) {
                    inputLayoutNama.setError("Nama tidak boleh kosong");
                    return;
                }

                if (txtEmail.getText().toString().trim().isEmpty()) {
                    inputLayoutEmail.setError("Email tidak boleh kosong");
                    return;
                }

                if (txtPassword.getText().toString().trim().isEmpty()) {
                    inputLayoutPassword.setError("Password tidak boleh kosong");
                    return;
                }

                if(TextUtils.isEmpty(txtConfirmPassword.getText().toString()))
                {
                    inputLayoutConfirmPassword.setError("Masukkan Password Konfirmasi");

                    if (!txtConfirmPassword.equals(txtPassword))
                    {
                        Toast.makeText(PencariKerjaDaftarActivity.this, "Password tidak sama", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });
    }

    private void daftar() {
        Log.d(TAG, "daftar");
        if (!validateForm()) {
            return;
        }
        String nama = txtNamaLengkap.getText().toString();
        String email = txtEmail.getText().toString();
        String password = txtPassword.getText().toString();

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
        String username = txtNamaLengkap.getText().toString();

        writeNewPencariKerja(user.getUid(), username, user.getEmail());

        Toast.makeText(PencariKerjaDaftarActivity.this, "Daftar Berhasil !", Toast.LENGTH_SHORT).show();
        startActivity(new Intent(getApplicationContext(), AuthLoginActivity.class));
        finish();
    }

    private boolean validateForm() {
        boolean result = true;

        if (TextUtils.isEmpty(txtNamaLengkap.getText().toString())) {
            result = false;
        } else {
            txtNamaLengkap.setError(null);
        }

        if (TextUtils.isEmpty(txtEmail.getText().toString())) {
            result = false;
        } else {
            txtEmail.setError(null);
        }

        if (TextUtils.isEmpty(txtPassword.getText().toString())) {
            result = false;
        } else {
            txtPassword.setError(null);
        }

        if (TextUtils.isEmpty(txtConfirmPassword.getText().toString())) {
            result = false;
        } else {
            txtConfirmPassword.setError(null);
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
