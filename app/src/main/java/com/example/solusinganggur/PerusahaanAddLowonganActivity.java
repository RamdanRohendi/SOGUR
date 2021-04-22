package com.example.solusinganggur;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.solusinganggur.entity.DetailPekerjaan;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class PerusahaanAddLowonganActivity extends AppCompatActivity {
    private EditText edtNmPerusahaan, edtNmHrd, edtAlmPerusahaan, edtEmlPerusahaan, edtWktPerusahaan, edtDeskPekerjaan, edtKoordinatX, edtKoordinatY;
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
        edtDeskPekerjaan = findViewById(R.id.edt_desk_pekerjaan);
        edtKoordinatX = findViewById(R.id.edt_koordinatx);
        edtKoordinatY = findViewById(R.id.edt_koordinaty);

        reference.child("user").child(getUserID).child("lowongan_pekerjaan").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                DetailPekerjaan detailPekerjaan = snapshot.getValue(DetailPekerjaan.class);

                if (detailPekerjaan != null) {
                    detailPekerjaan.setKey(snapshot.getKey());

                    edtNmPerusahaan.setText(detailPekerjaan.getNamaPerusahaan());
                    edtNmHrd.setText(detailPekerjaan.getNamaHRD());
                    edtAlmPerusahaan.setText(detailPekerjaan.getAlamatPerusahaan());
                    edtEmlPerusahaan.setText(detailPekerjaan.getEmailPerusahaan());
                    edtWktPerusahaan.setText(detailPekerjaan.getWaktuLowongan());
                    edtDeskPekerjaan.setText(detailPekerjaan.getDeskripsiPekerjaan());
                    if (!detailPekerjaan.getKoordinatX().equals("0") && !detailPekerjaan.getKoordinatY().equals("0")) {
                        edtKoordinatX.setText(detailPekerjaan.getKoordinatX());
                        edtKoordinatY.setText(detailPekerjaan.getKoordinatY());
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
//                Toast.makeText(getApplicationContext(),"Data Gagal Dimuat", Toast.LENGTH_LONG).show();
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
        String idPerusahaan = getUserID;
        String namaPerusahaan = edtNmPerusahaan.getText().toString().trim();
        String namaHrd = edtNmHrd.getText().toString().trim();
        String alamatPerusahaan = edtAlmPerusahaan.getText().toString().trim();
        String emailPerusahaan = edtEmlPerusahaan.getText().toString().trim();
        String waktuPerusahaan = edtWktPerusahaan.getText().toString().trim();
        String deskPerusahaan = edtDeskPekerjaan.getText().toString().trim();
        String koordinatX = edtKoordinatX.getText().toString().trim();
        String koordinatY = edtKoordinatY.getText().toString().trim();

        if (koordinatX.isEmpty() && koordinatY.isEmpty()) {
            koordinatX = "0";
            koordinatY = "0";
        }

        if (koordinatX == null && koordinatY == null) {
            koordinatX = "0";
            koordinatY = "0";
        }

        DetailPekerjaan detailPekerjaan = new DetailPekerjaan(namaPerusahaan, namaHrd, alamatPerusahaan, emailPerusahaan, waktuPerusahaan, deskPerusahaan, koordinatX, koordinatY);

        reference.child("user").child(idPerusahaan).child("lowongan_pekerjaan").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                DetailPekerjaan dataDetailPekerjaan = snapshot.getValue(DetailPekerjaan.class);

                reference.child("pekerjaan").addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                            DetailPekerjaan detailPekerjaan1 = dataSnapshot.getValue(DetailPekerjaan.class);
                            if (detailPekerjaan1.getKey() != null && dataDetailPekerjaan.getKey() != null) {
                                if (detailPekerjaan1.getKey().equals(dataDetailPekerjaan.getKey())) {
                                    reference.child("pekerjaan").child(detailPekerjaan1.getKey()).child("data").setValue(detailPekerjaan);
                                }
                            } else {
                                return;
                            }
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {
                        Log.e("MyData", error.getDetails() + " " + error.getMessage());
                    }
                });
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Log.e("MyData", error.getDetails() + " " + error.getMessage());
            }
        });


        reference.child("pekerjaan").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                    DetailPekerjaan dataDetailPekerjaan = dataSnapshot.getValue(DetailPekerjaan.class);
                    dataDetailPekerjaan.setKey(dataSnapshot.getKey());

                    if (dataDetailPekerjaan.getIdPerusahaan() == null) {
                        Toast.makeText(getApplicationContext(),"Data Kosong", Toast.LENGTH_SHORT).show();
                        return;
                    }

                    if (dataDetailPekerjaan.getIdPerusahaan().equals(getUserID)) {
                        reference.child("pekerjaan").child(dataDetailPekerjaan.getKey()).child("data").setValue(detailPekerjaan);
                        reference.child("pekerjaan").child(dataDetailPekerjaan.getKey()).child("publish").setValue(false);


                        detailPekerjaan.setKey(dataDetailPekerjaan.getKey());
                        reference.child("user").child(getUserID).child("lowongan_pekerjaan").setValue(detailPekerjaan);
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Log.e("MyData", error.getDetails() + " " + error.getMessage());
            }
        });

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