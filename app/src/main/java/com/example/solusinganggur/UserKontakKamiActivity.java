package com.example.solusinganggur;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class UserKontakKamiActivity extends AppCompatActivity {

    private EditText textEmail, textNama, inputMasukan;
    private Button btnKirim;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_kontakkami);

        textEmail = findViewById(R.id.input_email);
        textNama = findViewById(R.id.input_nama);
        inputMasukan = findViewById(R.id.input_masukan);

        btnKirim = findViewById(R.id.btn_kirim);
        btnKirim.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                if (!validasiInput()){
                    return;
                }

                onButtonTap();

            }
        });

    }


    public void onButtonTap(){
        Intent intent = new Intent(Intent.ACTION_SEND);
        intent.setType("text/plain");
        intent.putExtra(Intent.EXTRA_EMAIL, new String[]{"solusinganggur@gmail.com"});
        intent.putExtra(Intent.EXTRA_CC, new String[]{textEmail.getText().toString()});
        intent.putExtra(Intent.EXTRA_SUBJECT, textNama.getText().toString());
        intent.putExtra(Intent.EXTRA_TEXT, inputMasukan.getText().toString());

        try {
            startActivity(Intent.createChooser(intent, "Ingin mengirim Email ?"));
        } catch (android.content.ActivityNotFoundException ex){

        }
    }

    public boolean validasiInput(){
        boolean result = false;

        if (textEmail.getText().toString().trim().isEmpty()){
            textEmail.setError("Mohon isi email anda !");

        } else if (textNama.getText().toString().trim().isEmpty()){
            textNama.setError("Mohon isi Nama anda !");

        } else {
            result = true;
        }
        return result;
    }

    public void kembali(View view) {
        finish();
    }
}
