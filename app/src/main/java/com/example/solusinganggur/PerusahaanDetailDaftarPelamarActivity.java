package com.example.solusinganggur;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.example.solusinganggur.entity.PencariKerja;
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

    private TextView txtnamaPelamar;
    private TextView txtalamatPelamar;
    private TextView txtnoTlpPelamar;
    private TextView txtemailPelamar;
    private String keyPelamar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_perusahaan_detail_daftar_pelamar);

        database = FirebaseDatabase.getInstance();
        reference = database.getReference();

        txtnamaPelamar = findViewById(R.id.name);
        txtalamatPelamar = findViewById(R.id.alamat);
        txtnoTlpPelamar = findViewById(R.id.nohp);
        txtemailPelamar = findViewById(R.id.email);

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
    }

    public void kembali(View view) {
        finish();
    }
}