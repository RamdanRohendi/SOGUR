package com.example.solusinganggur;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;

import android.text.TextWatcher;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;


import com.example.solusinganggur.entity.PencariKerja;
import com.example.solusinganggur.entity.User;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class AuthLoginActivity extends AppCompatActivity {
    private static final String TAG = "LoginActivity";

    private FirebaseAuth mAuth;
    private FirebaseDatabase database;
    private DatabaseReference reference;
    private String getUserID;
    private TextView btndaftar;
    private TextView btnforgotpass;
    private Button btnlogin;
    private EditText txtEmail;
    private EditText txtPassword;
    private TextInputLayout inputLayoutEmail;
    private TextInputLayout inputLayoutPassword;
    private ProgressBar progressBar;
    private String role;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_auth_login);

        mAuth = FirebaseAuth.getInstance();

        database = FirebaseDatabase.getInstance();
        reference = database.getReference();

        txtEmail = findViewById(R.id.edt_email);
        inputLayoutEmail = findViewById(R.id.inputemail);
        txtEmail.addTextChangedListener(new ValidasiTextWatcher(txtEmail));
        txtPassword = findViewById(R.id.edt_password);
        inputLayoutPassword = findViewById(R.id.inputpassword);
        progressBar = findViewById(R.id.progressbar);

        btndaftar = findViewById(R.id.daftarsekarang);
        btndaftar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(), AuthPilihRoleActivity.class));
                kosongkanEdt();
                finish();
            }
        });

        btnlogin = findViewById(R.id.signin);
        btnlogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (txtEmail.getText().toString().trim().isEmpty()) {
                    inputLayoutEmail.setError("Mohon isi Email anda !");
                    return;
                }

                if (txtPassword.getText().toString().trim().isEmpty()) {
                    inputLayoutPassword.setError("Mohon isi Password anda !");
                    return;
                }

                login();
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

                        if (task.isSuccessful()) {
                            onAuthSuccess();
                        } else {
                            inputLayoutPassword.setError("Password yang anda masukkan salah");
                            progressBar.setVisibility(View.GONE);
                        }
                    }
                });
    }

    private void onAuthSuccess() {
        FirebaseUser user = mAuth.getCurrentUser();
        if (user == null) {
            return;
        }
        getUserID = user.getUid();

        reference.child("user").child(getUserID).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                progressBar.setVisibility(View.GONE);
                User pengguna = snapshot.getValue(User.class);

                if (pengguna != null) {
                    role = pengguna.getRole();

                    Toast.makeText(getApplicationContext(), "Berhasil Login", Toast.LENGTH_SHORT).show();
                    Intent afterLogin = new Intent(getApplicationContext(), AuthAfterLoginActivity.class);
                    afterLogin.putExtra("role", role);
                    startActivity(afterLogin);
                    finish();
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
//                Toast.makeText(getApplicationContext(),"Data Gagal Dimuat", Toast.LENGTH_LONG).show();
                Log.e("MyData", error.getDetails() + " " + error.getMessage());
            }
        });
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

    private boolean validasiEmail() {
        if (txtEmail.getText().toString().trim().isEmpty()) {
            inputLayoutEmail.setErrorEnabled(false);
        } else {
            String emailId = txtEmail.getText().toString();
            Boolean isValid = Patterns.EMAIL_ADDRESS.matcher(emailId).matches();
            if (!isValid) {
                inputLayoutEmail.setError("Email tidak valid");
                requestFocus(txtEmail);
                return false;
            } else {
                inputLayoutEmail.setErrorEnabled(false);
            }
        }

        return true;
    }

    private void requestFocus(View view) {
        if (view.requestFocus()) {
            getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_VISIBLE);
        }
    }

}