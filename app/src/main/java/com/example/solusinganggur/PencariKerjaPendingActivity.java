package com.example.solusinganggur;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.example.solusinganggur.adapter.ItemListPendingAdapter;
import com.example.solusinganggur.entity.DetailPekerjaan;
import com.example.solusinganggur.entity.ListLamaran;
import com.example.solusinganggur.entity.Pekerjaan;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class PencariKerjaPendingActivity extends AppCompatActivity {
    private RecyclerView recyclerView;
    private RecyclerView.Adapter adapter;
    private RecyclerView.LayoutManager layoutManager;

    private DatabaseReference reference;
    private ArrayList<ListLamaran> dataLamaran;

    private FirebaseAuth mAuth;
    private String getUserID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pencarikerja_pending);

        recyclerView = findViewById(R.id.data_pending);
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
                dataLamaran = new ArrayList<>();

                for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                    ListLamaran lamaran = dataSnapshot.getValue(ListLamaran.class);

                    lamaran.setKey(dataSnapshot.getKey());
                    dataLamaran.add(lamaran);
                }

                adapter = new ItemListPendingAdapter(dataLamaran, getApplicationContext());
                recyclerView.setAdapter(adapter);
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
