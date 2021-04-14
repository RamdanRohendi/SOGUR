package com.example.solusinganggur;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;

public class AuthLupaPasswordActivity extends AppCompatActivity {
    private Button btnConfirm;
    private EditText txtEmail;
    private ProgressBar progressBar;
    private FirebaseAuth auth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_auth_lupapassword);

        txtEmail = findViewById(R.id.email);
        progressBar = findViewById(R.id.progressbar);
        auth = FirebaseAuth.getInstance();

        btnConfirm = findViewById(R.id.btnconfirm);
        btnConfirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String email = txtEmail.getText().toString();

                if (TextUtils.isEmpty(email)) {
                    txtEmail.setError("Mohon isi email anda !");
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

    public void kembali(View view) {
        finish();
    }
}
