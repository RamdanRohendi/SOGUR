package com.example.solusinganggur;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.solusinganggur.adapter.ItemListSearchAdapter;
import com.example.solusinganggur.entity.DetailPekerjaan;
import com.example.solusinganggur.entity.Pekerjaan;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class PerusahaanStatusLowonganActivity extends AppCompatActivity implements OnMapReadyCallback {
    private TextView txtNamaPerusahaan;
    private TextView txtDetailAlamat;
    private TextView txtTglLowongan;
    private TextView txtNamaHRD;
    private TextView txtDeskJob;
    private double txtKoordinatX;
    private double txtKoordinatY;
    private Button btnPublish;

    private FirebaseAuth mAuth;
    private FirebaseDatabase database;
    private DatabaseReference reference;
    private String getUserID;
    private String getKeyPekerjaan;
    private boolean published;

    private RelativeLayout mapLokasi;
    private String namaPerusahaan;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_perusahaan_status_lowongan);
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.mapAcc);
        mapFragment.getMapAsync(this);

        mAuth = FirebaseAuth.getInstance();

        FirebaseUser user = mAuth.getCurrentUser();
        getUserID = user.getUid();

        database = FirebaseDatabase.getInstance();
        reference = database.getReference();

        mapLokasi = findViewById(R.id.map_lokasi);
        txtKoordinatX = getIntent().getExtras().getDouble("koorX");
        txtKoordinatY = getIntent().getExtras().getDouble("koorY");
        namaPerusahaan = getIntent().getExtras().getString("namaPerusahaan");

        txtNamaPerusahaan = findViewById(R.id.txt_nama_perusahaan);
        txtDetailAlamat = findViewById(R.id.txt_detail_alamat);
        txtTglLowongan = findViewById(R.id.txt_tgl_lowongan);
        txtNamaHRD = findViewById(R.id.txt_namaHRD);
        txtDeskJob = findViewById(R.id.txt_desc_job);
        btnPublish = findViewById(R.id.btn_published);

        reference.child("user").child(getUserID).child("lowongan_pekerjaan").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                DetailPekerjaan detailPekerjaan = snapshot.getValue(DetailPekerjaan.class);

                if (detailPekerjaan != null) {
                    detailPekerjaan.setKey(snapshot.getKey());

                    txtNamaPerusahaan.setText(detailPekerjaan.getNamaPerusahaan());
                    txtNamaHRD.setText(detailPekerjaan.getNamaHRD());
                    txtDetailAlamat.setText(detailPekerjaan.getAlamatPerusahaan());
                    txtTglLowongan.setText(detailPekerjaan.getWaktuLowongan());
                    txtDeskJob.setText(detailPekerjaan.getDeskripsiPekerjaan());
                    if (koordinatKosong()) {
                        mapLokasi.setVisibility(View.GONE);
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
//                Toast.makeText(getApplicationContext(),"Data Gagal Dimuat", Toast.LENGTH_LONG).show();
                Log.e("MyData", error.getDetails() + " " + error.getMessage());
            }
        });

        reference.child("pekerjaan").addValueEventListener(new ValueEventListener() {
            @SuppressLint("ResourceAsColor")
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                ArrayList<DetailPekerjaan> listDetailPekerjaan = new ArrayList<>();

                for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                    Pekerjaan pekerjaan = dataSnapshot.getValue(Pekerjaan.class);
                    if (pekerjaan.getIdPerusahaan().equals(getUserID)) {
                        published = pekerjaan.isPublish();
                        pekerjaan.setKey(dataSnapshot.getKey());
                        getKeyPekerjaan = pekerjaan.getKey();

                        if (pekerjaan.isPublish()) {
                            btnPublish.setText("Unpublish");
                            btnPublish.setBackgroundColor(R.color.grey);
                            btnPublish.setTextColor(R.color.white);
                        }
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Log.e("MyData", error.getDetails() + " " + error.getMessage());
            }
        });

        btnPublish.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                reference.child("pekerjaan").child(getKeyPekerjaan).child("publish").setValue(!published).addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        Toast.makeText(getApplicationContext(),"Status Lowongan Berhasil Diubah", Toast.LENGTH_LONG).show();
                        finish();
                    }
                });
            }
        });
    }

    private boolean koordinatKosong() {
        return txtKoordinatX == 0 && txtKoordinatY == 0;
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        LatLng lokasi = new LatLng(txtKoordinatX, txtKoordinatY);

        googleMap.addMarker(new MarkerOptions()
        .position(lokasi)
        .title(namaPerusahaan));
        googleMap.moveCamera(CameraUpdateFactory.newLatLng(lokasi));
    }

    public void kembali(View view) {
        finish();
    }
}