package com.example.solusinganggur;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.text.method.PasswordTransformationMethod;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

public class UbahPasswordActivity extends AppCompatActivity {
    private EditText newPass;
    private EditText newPassConf;
    private Button btnConfirm;

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ubah_password);

        newPass = findViewById(R.id.passwordBaru);
        newPassConf = findViewById(R.id.konfirmasiPassBaru);
        btnConfirm = findViewById(R.id.btnKonfirmasi);

        newPass.addTextChangedListener(newPassword);
        newPassConf.addTextChangedListener(newPassword);
    }
    private TextWatcher newPassword = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

        }

        @Override
        public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            String passwordInput = newPass.getText().toString().trim();
            String passwordInputConfirm = newPassConf.getText().toString().trim();

            btnConfirm.setEnabled(!passwordInput.isEmpty() && !passwordInputConfirm.isEmpty());
        }

        @Override
        public void afterTextChanged(Editable editable) {

        }
    };
}
