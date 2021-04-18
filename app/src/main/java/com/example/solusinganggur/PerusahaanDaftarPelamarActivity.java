package com.example.solusinganggur;

import android.os.Bundle;

import com.example.solusinganggur.adapter.ItemListPelamarAdapter;
import com.example.solusinganggur.entity.DetailPekerjaan;
import com.example.solusinganggur.entity.PencariKerja;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.View;

import java.util.ArrayList;

public class PerusahaanDaftarPelamarActivity extends AppCompatActivity {
    private RecyclerView recyclerView;
    private RecyclerView.Adapter adapter;
    private RecyclerView.LayoutManager layoutManager;

    private DatabaseReference reference;
    private ArrayList<PencariKerja> dataPencariKerja;

    private FirebaseAuth mAuth;
    private String getUserID;
    private String keyPekerjaan;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_perusahaan_daftar_pelamar);

        recyclerView = findViewById(R.id.data_pelamar);
        mAuth = FirebaseAuth.getInstance();
        FirebaseUser user = mAuth.getCurrentUser();
        reference = FirebaseDatabase.getInstance().getReference();
        getUserID = user.getUid();

        setRecyclerView();
        getData();
    }

    private void getData() {
        reference.child("user").child(getUserID).child("lowongan_pekerjaan").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                DetailPekerjaan pekerjaan = snapshot.getValue(DetailPekerjaan.class);
                keyPekerjaan = pekerjaan.getKey();

                reference.child("pekerjaan").child(keyPekerjaan).child("pelamar").addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        dataPencariKerja = new ArrayList<>();

                        for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                            PencariKerja pencariKerja = dataSnapshot.getValue(PencariKerja.class);

                            pencariKerja.setKey(dataSnapshot.getKey());
                            dataPencariKerja.add(pencariKerja);
                        }

                        adapter = new ItemListPelamarAdapter(dataPencariKerja, getApplicationContext());
                        recyclerView.setAdapter(adapter);
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
    }

    private void setRecyclerView() {
        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
    }

    public void kembali(View view) {
        finish();
    }
}