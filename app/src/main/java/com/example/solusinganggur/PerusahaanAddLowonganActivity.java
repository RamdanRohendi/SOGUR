package com.example.solusinganggur;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.solusinganggur.entity.Pekerjaan;
import com.example.solusinganggur.entity.Perusahaan;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class PerusahaanAddLowonganActivity extends AppCompatActivity {
    private EditText edtNmPerusahaan, edtNmHrd, edtAlmPerusahaan, edtEmlPerusahaan, edtWktPerusahaan, edtDeskPekerjaan;
    private Button btnAdd;

    private FirebaseAuth mAuth;
    private FirebaseDatabase database;
    private DatabaseReference reference;
    private String getUserID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_perusahaan_add_lowongan);

        mAuth = FirebaseAuth.getInstance();

        FirebaseUser user = mAuth.getCurrentUser();
        getUserID = user.getUid();

        database = FirebaseDatabase.getInstance();
        reference = database.getReference();

        edtNmPerusahaan = findViewById(R.id.edt_nm_perusahaan);
        edtNmHrd = findViewById(R.id.edt_nm_hrd);
        edtAlmPerusahaan = findViewById(R.id.edt_alm_perusahaan);
        edtEmlPerusahaan = findViewById(R.id.edt_eml_perusahaan);
        edtWktPerusahaan = findViewById(R.id.edt_wkt_perusahaan);
        edtDeskPekerjaan= findViewById(R.id.edt_desk_pekerjaan);

        reference.child("user").child(getUserID).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                Perusahaan perusahaan = snapshot.getValue(Perusahaan.class);
                perusahaan.setKey(snapshot.getKey());

                edtNmPerusahaan.setText(perusahaan.getNamaPerusahaan());
                edtAlmPerusahaan.setText(perusahaan.getAlamat());
                edtEmlPerusahaan.setText(perusahaan.getEmail());
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(getApplicationContext(),"Data Gagal Dimuat", Toast.LENGTH_LONG).show();
                Log.e("MyData", error.getDetails() + " " + error.getMessage());
            }
        });

        btnAdd= findViewById(R.id.btn_add);
        btnAdd.setOnClickListener(new View.OnClickListener(){
           @Override
           public void onClick(View view){
               if (!validasiInput()) {
                   return;
               }

               addPekerjaan();

               Toast.makeText(getApplicationContext(),"Data Sukses Ditambah ^_^", Toast.LENGTH_SHORT).show();
               finish();
           }
        });
    }

    public void addPekerjaan() {
        String namaPerusahaan = edtNmPerusahaan.getText().toString().trim();
        String namaHrd = edtNmHrd.getText().toString().trim();
        String alamatPerusahaan = edtAlmPerusahaan.getText().toString().trim();
        String emailPerusahaan = edtEmlPerusahaan.getText().toString().trim();
        String waktuPerusahaan = edtWktPerusahaan.getText().toString().trim();
        String deskPerusahaan = edtDeskPekerjaan.getText().toString().trim();

        Pekerjaan pekerjaan = new Pekerjaan(namaPerusahaan, namaHrd, alamatPerusahaan, emailPerusahaan, waktuPerusahaan, deskPerusahaan, "", "");

        reference.child("pekerjaan").push().child(namaPerusahaan + " - " + namaHrd).setValue(pekerjaan);
    }

    public Boolean validasiInput() {
        Boolean result = false;

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

        } else if (edtDeskPekerjaan.getText().toString().trim().isEmpty()){
            edtDeskPekerjaan.setError("Mohon isi Deskripsi Perusahaan anda !");

        } else {
            result = true;
        }

        return result;
    }

    public void kembali(View view) {
        finish();
    }
}