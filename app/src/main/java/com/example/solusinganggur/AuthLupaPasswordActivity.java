package com.example.solusinganggur;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Patterns;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.FirebaseAuth;

public class AuthLupaPasswordActivity extends AppCompatActivity {
    private Button btnConfirm;
    private ProgressBar progressBar;
    private FirebaseAuth auth;
    private TextInputLayout inputLayoutEmail;
    private TextInputEditText edtEmail;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_auth_lupapassword);

        progressBar = findViewById(R.id.progressbar);
        auth = FirebaseAuth.getInstance();
        inputLayoutEmail = findViewById(R.id.inputlayout_email);
        edtEmail = findViewById(R.id.edt_email);
        edtEmail.addTextChangedListener(new ValidasiTextWatcher(edtEmail));

        btnConfirm = findViewById(R.id.btnconfirm);
        btnConfirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String email = edtEmail.getText().toString().trim();

                if (edtEmail.getText().toString().trim().isEmpty()) {
                    inputLayoutEmail.setError("Mohon isi email anda !");
                    return;
                }

                progressBar.setVisibility(View.VISIBLE);
                auth.sendPasswordResetEmail(email)
                        .addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                                progressBar.setVisibility(View.GONE);

                                if (task.isSuccessful()) {
                                    startActivity(new Intent(getApplicationContext(), AuthKonfirmasiUbahPasswordActivity.class));
                                    finish();
                                } else {
                                    Toast.makeText(getApplicationContext(), "Gagal mengirim konfirmasi ubah password", Toast.LENGTH_LONG).show();
                                }
                            }
                        });
            }
        });

    }

    private void requestFocus(View view) {
        if (view.requestFocus()) {
            getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_VISIBLE);
        }
    }

    private boolean validasiEmail() {
        if (edtEmail.getText().toString().trim().isEmpty()) {
            inputLayoutEmail.setErrorEnabled(false);
        } else {
            String emailId = edtEmail.getText().toString();
            Boolean isValid = Patterns.EMAIL_ADDRESS.matcher(emailId).matches();
            if (!isValid) {
                inputLayoutEmail.setError("Email tidak valid");
                requestFocus(edtEmail);
                return false;
            } else {
                inputLayoutEmail.setErrorEnabled(false);
            }
        }

        return true;
    }

    private class ValidasiTextWatcher implements TextWatcher {
        private View view;

        private ValidasiTextWatcher(View view) {
            this.view = view;
        }

        @Override
        public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

        }

        @Override
        public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

        }

        @Override
        public void afterTextChanged(Editable editable) {
            switch (view.getId()) {
                case R.id.edt_email:
                    validasiEmail();
                    break;
            }
        }
    }

    public void kembali(View view) {
        finish();
    }
}
