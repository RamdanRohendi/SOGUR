package com.example.solusinganggur;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;

import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
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

    private FirebaseAuth mAuth;
    private TextView btndaftar;
    private TextView btnforgotpass;
    private Button btnlogin;
    private EditText txtEmail;
    private EditText txtPassword;
    private ImageView ShowHideBtn;
    private ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_auth_login);

        mAuth = FirebaseAuth.getInstance();
        txtEmail = findViewById(R.id.username);
        txtPassword = findViewById(R.id.password);
        ShowHideBtn = findViewById(R.id.showhide);
        progressBar = findViewById(R.id.progressbar);

        btndaftar = findViewById(R.id.daftarsekarang);
        btndaftar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(), AuthPilihRoleActivity.class));
                kosongkanEdt();
            }
        });

        btnlogin = findViewById(R.id.signin);
        btnlogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String email = txtEmail.getText().toString();
                String password = txtPassword.getText().toString();

                if (email.equals("perusahaan@gmail.com") && password.equals("perusahaan123")) {
                    if (!validateForm()) {
                        return;
                    }
                    startActivity(new Intent(getApplicationContext(), PerusahaanMenuActivity.class));
                    finish();
                } else {
                    login();
                }
            }
        });

        btnforgotpass = findViewById(R.id.fpassword);
        btnforgotpass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(), AuthLupaPasswordActivity.class));
                kosongkanEdt();
            }
        });
    }

    private void login() {
        Log.d(TAG, "login");
        if (!validateForm()) {
            return;
        }

        String email = txtEmail.getText().toString();
        String password = txtPassword.getText().toString();

        progressBar.setVisibility(View.VISIBLE);
        mAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        Log.d(TAG, "login:onComplete:" + task.isSuccessful());

                        progressBar.setVisibility(View.GONE);

                        if (task.isSuccessful()) {
                            onAuthSuccess();
                        } else {
                            Toast.makeText(AuthLoginActivity.this, "Username atau Password salah", Toast.LENGTH_LONG).show();
                        }
                    }
                });
    }

    private void onAuthSuccess() {
        Toast.makeText(AuthLoginActivity.this, "Berhasil Login", Toast.LENGTH_SHORT).show();
        startActivity(new Intent(getApplicationContext(), AuthAfterLoginActivity.class));
        finish();
    }



    public void ShowHidePass(View view){

        if(view.getId()==R.id.showhide){

            if(txtPassword.getTransformationMethod().equals(PasswordTransformationMethod.getInstance())){
                ((ImageView)(view)).setImageResource(R.drawable.ic_hide);

                //Show Password
                txtPassword.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
            } else {
                ((ImageView)(view)).setImageResource(R.drawable.ic_show);

                //Hide Password
                txtPassword.setTransformationMethod(PasswordTransformationMethod.getInstance());

            }
        }
    }

    private boolean validateForm() {
        boolean result = true;

        if (TextUtils.isEmpty(txtEmail.getText().toString())) {
            txtEmail.setError("Email harus diisi");
            result = false;
        } else {
            txtEmail.setError(null);
        }

        if (TextUtils.isEmpty(txtPassword.getText().toString())) {
            txtPassword.setError("Password harus diisi");
            result = false;
        } else {
            txtPassword.setError(null);
        }


        return result;
    }

    private void kosongkanEdt() {
        txtEmail.setText("");
        txtPassword.setText("");
    }

    @Override
    public void onBackPressed() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Apakah anda yakin ingin keluar Aplikasi ?");

        builder.setPositiveButton("Ya", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                finish();
            }
        });

        builder.setNegativeButton("Tidak", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
            }
        });

        AlertDialog alertDialog = builder.create();
        alertDialog.show();
    }
}