package com.example.solusinganggur;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.solusinganggur.entity.DetailPekerjaan;
import com.example.solusinganggur.entity.PencariKerja;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class PerusahaanDetailDaftarPelamarActivity extends AppCompatActivity {
    private FirebaseDatabase database;
    private DatabaseReference reference;
    private FirebaseAuth mAuth;
    private String getUserID;

    private TextView txtnamaPelamar;
    private TextView txtalamatPelamar;
    private TextView txtnoTlpPelamar;
    private TextView txtemailPelamar;
    private String keyPelamar;
    private String keyLowongan;
    private Button btnTerima;
    private Button btnTolak;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_perusahaan_detail_daftar_pelamar);

        database = FirebaseDatabase.getInstance();
        reference = database.getReference();

        mAuth = FirebaseAuth.getInstance();
        FirebaseUser user = mAuth.getCurrentUser();
        getUserID = user.getUid();

        txtnamaPelamar = findViewById(R.id.name);
        txtalamatPelamar = findViewById(R.id.alamat);
        txtnoTlpPelamar = findViewById(R.id.nohp);
        txtemailPelamar = findViewById(R.id.email);
        btnTerima = findViewById(R.id.btn_terima);
        btnTolak = findViewById(R.id.btn_tolak);

        keyPelamar = getIntent().getExtras().getString("keyPelamar");

        reference.child("user").child(keyPelamar).child("data").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                PencariKerja pencariKerja = snapshot.getValue(PencariKerja.class);
                if (pencariKerja != null) {
                    pencariKerja.setKey(snapshot.getKey());

                    txtnamaPelamar.setText(pencariKerja.getNamaLengkap());
                    txtalamatPelamar.setText(pencariKerja.getAlamat());
                    txtnoTlpPelamar.setText(pencariKerja.getNomorTelepon());
                    txtemailPelamar.setText(pencariKerja.getEmail());
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
//                    Toast.makeText(getApplicationContext(),"Data Gagal Dimuat", Toast.LENGTH_LONG).show();
                Log.e("MyData", error.getDetails() + " " + error.getMessage());
            }
        });

        reference.child("user").child(getUserID).child("lowongan_pekerjaan").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                DetailPekerjaan pekerjaan = snapshot.getValue(DetailPekerjaan.class);
                if (pekerjaan != null) {
                    keyLowongan = pekerjaan.getKey();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
//                    Toast.makeText(getApplicationContext(),"Data Gagal Dimuat", Toast.LENGTH_LONG).show();
                Log.e("MyData", error.getDetails() + " " + error.getMessage());
            }
        });

        btnTerima.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                reference.child("pekerjaan").child(keyLowongan).child("pelamar").child(keyPelamar).child("status").setValue("accept");
                reference.child("user").child(keyPelamar).child("lowongan_pekerjaan").child(keyLowongan).child("status").setValue("accept").addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        Toast.makeText(getApplicationContext(),"Data Berhasil Diterima", Toast.LENGTH_LONG).show();
                        finish();
                    }
                });
            }
        });

        btnTolak.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                reference.child("pekerjaan").child(keyLowongan).child("pelamar").child(keyPelamar).child("status").setValue("reject");
                reference.child("user").child(keyPelamar).child("lowongan_pekerjaan").child(keyLowongan).child("status").setValue("reject").addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        Toast.makeText(getApplicationContext(),"Data Berhasil Ditolak", Toast.LENGTH_LONG).show();
                        finish();
                    }
                });
            }
        });
    }

    public void kembali(View view) {
        finish();
    }
}