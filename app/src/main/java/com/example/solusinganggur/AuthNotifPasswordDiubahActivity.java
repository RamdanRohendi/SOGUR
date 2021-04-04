package com.example.solusinganggur;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

public class AuthNotifPasswordDiubahActivity extends AppCompatActivity {
    TextView backLogin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_auth_notif_password_diubah);

        backLogin = findViewById(R.id.backlogin);
        backLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(), AuthLoginActivity.class));
            }
        });
    }
}
