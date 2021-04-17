package com.example.solusinganggur;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.solusinganggur.entity.Pekerjaan;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class PerusahaanStatusLowonganActivity extends AppCompatActivity implements OnMapReadyCallback {
    private TextView txtNamaPerusahaan;
    private TextView txtDetailAlamat;
    private TextView txtTglLowongan;
    private TextView txtNamaHRD;
    private TextView txtDeskJob;
    private double txtKoordinatX;
    private double txtKoordinatY;

    private FirebaseAuth mAuth;
    private FirebaseDatabase database;
    private DatabaseReference reference;
    private String getUserID;

    private RelativeLayout mapLokasi;
    private GoogleMap mMap;
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

        reference.child("user").child(getUserID).child("lowongan_pekerjaan").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                Pekerjaan pekerjaan = snapshot.getValue(Pekerjaan.class);

                if (pekerjaan != null) {
                    pekerjaan.setKey(snapshot.getKey());

                    txtNamaPerusahaan.setText(pekerjaan.getNamaPerusahaan());
                    txtNamaHRD.setText(pekerjaan.getNamaHRD());
                    txtDetailAlamat.setText(pekerjaan.getAlamatPerusahaan());
                    txtTglLowongan.setText(pekerjaan.getWaktuLowongan());
                    txtDeskJob.setText(pekerjaan.getDeskripsiPekerjaan());
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