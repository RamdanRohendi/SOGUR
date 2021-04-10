package com.example.solusinganggur;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class AuthLoginActivity extends AppCompatActivity {
    private static final String TAG = "LoginActivity";

    private DatabaseReference mDatabase;
    private FirebaseAuth mAuth;
    private TextView btndaftar;
    private TextView btnforgotpass;
    private Button btnlogin;
    private EditText txtEmail;
    private EditText txtPassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_auth_login);

        mDatabase = FirebaseDatabase.getInstance().getReference();
        mAuth = FirebaseAuth.getInstance();
        txtEmail = findViewById(R.id.username);
        txtPassword = findViewById(R.id.password);

        btndaftar = findViewById(R.id.daftarsekarang);
        btndaftar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(), AuthPilihRoleActivity.class));
            }
        });

        btnlogin = findViewById(R.id.signin);
        btnlogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                login();
            }
        });

        btnforgotpass = findViewById(R.id.fpassword);
        btnforgotpass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(), AuthLupaPasswordActivity.class));
            }
        });
    }

    private void sendData(String pesan) {
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference("user");

        myRef.setValue(pesan);
    }

    private void login() {
        Log.d(TAG, "login");
        if (!validateForm()) {
            return;
        }

        String email = txtEmail.getText().toString();
        String password = txtPassword.getText().toString();

        mAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        Log.d(TAG, "login:onComplete:" + task.isSuccessful());

                        if (task.isSuccessful()) {
                            onAuthSuccess();
                        } else {
                            Toast.makeText(AuthLoginActivity.this, "Login Gagal :( \nPastikan alamat email dan Passwordnya benar", Toast.LENGTH_LONG).show();
                        }
                    }
                });
    }

    private void onAuthSuccess() {
        Toast.makeText(AuthLoginActivity.this, "Berhasil Login", Toast.LENGTH_SHORT).show();
        startActivity(new Intent(getApplicationContext(), AuthAfterLoginActivity.class));
        finish();
    }

    private boolean validateForm() {
        boolean result = true;

        if (TextUtils.isEmpty(txtEmail.getText().toString())) {
            txtEmail.setError("Mohon Isi Emailnya !");
            result = false;
        } else {
            txtEmail.setError(null);
        }

        if (TextUtils.isEmpty(txtPassword.getText().toString())) {
            txtPassword.setError("Mohon Masukan Password !");
            result = false;
        } else {
            txtPassword.setError(null);
        }

        return result;
    }

}
