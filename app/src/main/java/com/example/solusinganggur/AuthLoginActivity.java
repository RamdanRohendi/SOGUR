package com.example.solusinganggur;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class AuthLoginActivity extends AppCompatActivity {
    TextView btndaftar;
    TextView btnforgotpass;
    Button btnlogin;
    EditText txtUsername;
    EditText txtPassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_auth_login);

        txtUsername = findViewById(R.id.username);
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
                String data = txtUsername.getText().toString();
                sendData(data);
                Toast.makeText(AuthLoginActivity.this, "terkirim", Toast.LENGTH_SHORT).show();
                startActivity(new Intent(getApplicationContext(), AuthAfterLoginActivity.class));
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
}
