package com.example.solusinganggur;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class PerusahaanAddLowonganActivity extends AppCompatActivity {

    private EditText edtNmPerusahaan, edtNmHrd, edtAlmPerusahaan, edtEmlPerusahaan, edtWktPerusahaan, edtDeskPerusahaan;
    private Button btnAdd;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_perusahaan_add_lowongan);

        edtNmPerusahaan = findViewById(R.id.edt_nm_perusahaan);
        edtNmHrd = findViewById(R.id.edt_nm_hrd);
        edtAlmPerusahaan = findViewById(R.id.edt_alm_perusahaan);
        edtEmlPerusahaan = findViewById(R.id.edt_eml_perusahaan);
        edtWktPerusahaan = findViewById(R.id.edt_wkt_perusahaan);
        edtDeskPerusahaan= findViewById(R.id.edt_desk_perusahaan);

        btnAdd= findViewById(R.id.btn_add);
        btnAdd.setOnClickListener(new View.OnClickListener(){
           @Override
           public void onClick(View view){
               String namaPerusahaan = edtNmPerusahaan.getText().toString().trim();
               String namaHrd = edtNmHrd.getText().toString().trim();
               String alamatPerusahaan = edtAlmPerusahaan.getText().toString().trim();
               String emailPerusahaan = edtEmlPerusahaan.getText().toString().trim();
               String waktuPerusahaan = edtWktPerusahaan.getText().toString().trim();
               String deskPerusahaan = edtDeskPerusahaan.getText().toString().trim();

               if (edtNmPerusahaan.getText().toString().trim().isEmpty()){
                   edtNmPerusahaan.setError("Mohon isi Nama Perusahaan Anda !");

               } else if (edtNmHrd.getText().toString().trim().isEmpty()){
                   edtNmHrd.setError("Mohon isi Nama HRD !");

               } else if (edtAlmPerusahaan.getText().toString().trim().isEmpty()){
                   edtAlmPerusahaan.setError("Mohon isi Alamat Perusahaan Anda !");

               } else if (edtEmlPerusahaan.getText().toString().trim().isEmpty()){
                   edtEmlPerusahaan.setError("Mohon isi Email Perusahaan Anda !");

               } else if (edtWktPerusahaan.getText().toString().trim().isEmpty()){
                   edtWktPerusahaan.setError("Mohon isi Waktu Perushaan anda !");

               } else if (edtDeskPerusahaan.getText().toString().trim().isEmpty()){
                   edtDeskPerusahaan.setError("Mohon isi Deskripsi Perusahaan anda !");
                   return;
               }
           }
        });
    }

    public void kembali(View view) {
        finish();
    }
}