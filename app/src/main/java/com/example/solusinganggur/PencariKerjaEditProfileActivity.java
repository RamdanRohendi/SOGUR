package com.example.solusinganggur;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class PencariKerjaEditProfileActivity extends AppCompatActivity {
    private static final String TAG = "PerjaEditProfActivity";

    private DatabaseReference mDatabase;
    private FirebaseAuth mAuth;

    private EditText edtNama;
    private TextInputLayout inputLayoutNama;
    private EditText edtJenkel;
    private TextInputLayout inputLayoutJenkel;
    private EditText edtTtl;
    private TextInputLayout inputLayoutTtl;
    private EditText edtAlamat;
    private TextInputLayout inputLayoutAlamat;
    private EditText edtAgama;
    private TextInputLayout inputLayoutAgama;
    private EditText edtEmail;
    private TextInputLayout inputLayoutEmail;
    private EditText edtNotelp;
    private TextInputLayout inputLayoutNotelp;
    private Button btnSimpan;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pencarikerja_edit_profile);

        mDatabase = FirebaseDatabase.getInstance().getReference();
        mAuth = FirebaseAuth.getInstance();

        edtNama = findViewById(R.id.editNama);
        edtJenkel = findViewById(R.id.editJenkel);
        edtTtl = findViewById(R.id.editTtl);
        edtAlamat = findViewById(R.id.editAlamat);
        edtAgama = findViewById(R.id.editAgama);
        edtEmail = findViewById(R.id.editEmail);
        edtNotelp = findViewById(R.id.editNotelp);

        inputLayoutNama = findViewById(R.id.inputnama);
        inputLayoutJenkel = findViewById(R.id.inputjenkel);
        inputLayoutTtl = findViewById(R.id.inputttl);
        inputLayoutAlamat = findViewById(R.id.inputalamat);
        inputLayoutAgama = findViewById(R.id.inputagama);
        inputLayoutEmail = findViewById(R.id.inputemail);
        inputLayoutNotelp = findViewById(R.id.inputnotelp);

        btnSimpan = findViewById(R.id.simpaneditprofile);
        btnSimpan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                daftar();

                if (edtNama.getText().toString().trim().isEmpty()) {
                    inputLayoutNama.setError("Nama tidak boleh kosong");
                    return;
                }

                if (edtJenkel.getText().toString().trim().isEmpty()) {
                    inputLayoutJenkel.setError("Jenis Kelamin tidak boleh kosong");
                    return;
                }

                if (edtTtl.getText().toString().trim().isEmpty()) {
                    inputLayoutTtl.setError("Tempat Tanggal Lahir tidak boleh kosong");
                    return;
                }

                if (edtAlamat.getText().toString().trim().isEmpty()) {
                    inputLayoutAlamat.setError("Alamat tidak boleh kosong");
                    return;
                }

                if (edtAgama.getText().toString().trim().isEmpty()) {
                    inputLayoutAgama.setError("Agama tidak boleh kosong");
                    return;
                }

                if (edtEmail.getText().toString().trim().isEmpty()) {
                    inputLayoutEmail.setError("Email tidak boleh kosong");
                    return;
                }

                if (edtNotelp.getText().toString().trim().isEmpty()) {
                    inputLayoutNotelp.setError("No Telepon tidak boleh kosong");
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

    }

    private boolean validateForm() {
        boolean result = true;

        if (TextUtils.isEmpty(edtNama.getText().toString())) {
            result = false;
        } else {
            edtNama.setError(null);
        }

        if (TextUtils.isEmpty(edtEmail.getText().toString())) {
            result = false;
        } else {
            edtEmail.setError(null);
        }

        return result;


    }


    public void kembali(View view) {
        finish();
    }
}