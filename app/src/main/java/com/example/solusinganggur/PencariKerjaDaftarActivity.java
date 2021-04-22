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

import com.example.solusinganggur.entity.DetailPekerjaan;
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

    private EditText edtNamaLengkap;
    private TextInputLayout inputLayoutNama;
    private EditText edtEmail;
    private TextInputLayout inputLayoutEmail;
    private EditText edtPassword;
    private TextInputLayout inputLayoutPassword;
    private EditText edtConfirmPassword;
    private TextInputLayout inputLayoutConfirmPassword;
    private Button btnDaftar;
    private ProgressBar progressBar;
    private String role;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pencarikerja_daftar);

        mDatabase = FirebaseDatabase.getInstance().getReference();
        mAuth = FirebaseAuth.getInstance();

        edtNamaLengkap = findViewById(R.id.isiNama);
        edtEmail = findViewById(R.id.isiEmail);
        edtPassword = findViewById(R.id.isiPassword);
        edtConfirmPassword = findViewById(R.id.isiConfirmPassword);

        role = getIntent().getExtras().getString("role");

        progressBar = findViewById(R.id.progressbar);

        inputLayoutNama = findViewById(R.id.inputnama);
        inputLayoutEmail = findViewById(R.id.inputemail);
        inputLayoutPassword = findViewById(R.id.inputpassword);
        inputLayoutConfirmPassword = findViewById(R.id.inputconfirmpassword);

        btnDaftar = findViewById(R.id.daftar_pcrkerja);
        btnDaftar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                daftar();

                if (edtNamaLengkap.getText().toString().trim().isEmpty()) {
                    inputLayoutNama.setError("Nama tidak boleh kosong");
                    return;
                }

                if (edtEmail.getText().toString().trim().isEmpty()) {
                    inputLayoutEmail.setError("Email tidak boleh kosong");
                    return;
                }

                if (edtPassword.getText().toString().trim().isEmpty()) {
                    inputLayoutPassword.setError("Password tidak boleh kosong");
                    return;
                } String pass = edtPassword.getText().toString();
                if(TextUtils.isEmpty(pass) || pass.length() < 6)
                {
                    inputLayoutPassword.setError("Minimal membutuhkan 6 karakter");
                    return;
                }

                if (edtConfirmPassword.getText().toString().trim().isEmpty()) {
                    inputLayoutConfirmPassword.setError("Masukkan Confirm Password");
                    return;
                }

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
                            Toast.makeText(PencariKerjaDaftarActivity.this, "Daftar Gagal :(", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }

    private void onAuthSuccess(FirebaseUser user) {
        String username = edtNamaLengkap.getText().toString();

        writeNewPencariKerja(user.getUid(), role, username, user.getEmail());

        Toast.makeText(PencariKerjaDaftarActivity.this, "Daftar Berhasil !", Toast.LENGTH_SHORT).show();
        startActivity(new Intent(getApplicationContext(), AuthLoginActivity.class));
        finish();
    }

    private boolean validateForm() {
        boolean result = true;

        if (TextUtils.isEmpty(edtNamaLengkap.getText().toString())) {
            result = false;
        } else {
            edtNamaLengkap.setError(null);
        }

        if (TextUtils.isEmpty(edtEmail.getText().toString())) {
            result = false;
        } else {
            edtEmail.setError(null);
        }

        if (TextUtils.isEmpty(edtPassword.getText().toString())) {
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

        return result;


    }

    private void writeNewPencariKerja(String perjaId, String role, String nama, String email) {
        PencariKerja pencariKerja = new PencariKerja(email, "", nama, "");

        mDatabase.child("user").child(perjaId).child("role").setValue(role);
        mDatabase.child("user").child(perjaId).child("data").setValue(pencariKerja);
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
