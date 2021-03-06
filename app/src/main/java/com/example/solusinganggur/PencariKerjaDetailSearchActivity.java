package com.example.solusinganggur;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.solusinganggur.entity.DetailPekerjaan;
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

public class PencariKerjaDetailSearchActivity extends AppCompatActivity implements OnMapReadyCallback {
    private TextView txtNamaPerusahaan;
    private TextView txtDetailAlamat;
    private TextView txtTglLowongan;
    private TextView txtNamaHRD;
    private TextView txtDeskJob;
    private double txtKoordinatX;
    private double txtKoordinatY;
    private Button btnLamar;

    private RelativeLayout mapLokasi;
    private String keyPekerjaan;
    private String namaPerusahaan;
    private String emailPerusahaan;
    private String namaHRD;
    private String detailAlamat;
    private String tglLowongan;
    private String deskJob;
    private String koorX;
    private String koorY;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pencari_kerja_detail_search);
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.mapAcc);
        mapFragment.getMapAsync(this);

        mapLokasi = findViewById(R.id.map_lokasi);
        txtKoordinatX = getIntent().getExtras().getDouble("koorX");
        txtKoordinatY = getIntent().getExtras().getDouble("koorY");
        keyPekerjaan = getIntent().getExtras().getString("keyPekerjaan");
        namaPerusahaan = getIntent().getExtras().getString("namaPerusahaan");
        emailPerusahaan = getIntent().getExtras().getString("emailPerusahaan");
        namaHRD = getIntent().getExtras().getString("namaHRD");
        detailAlamat = getIntent().getExtras().getString("detailAlamat");
        tglLowongan = getIntent().getExtras().getString("tglLowongan");
        deskJob = getIntent().getExtras().getString("deskJob");
        koorX = getIntent().getExtras().getString("koorX");
        koorY = getIntent().getExtras().getString("koorY");

        txtNamaPerusahaan = findViewById(R.id.txt_nama_perusahaan);
        txtDetailAlamat = findViewById(R.id.txt_detail_alamat);
        txtTglLowongan = findViewById(R.id.txt_tgl_lowongan);
        txtNamaHRD = findViewById(R.id.txt_namaHRD);
        txtDeskJob = findViewById(R.id.txt_desc_job);
        btnLamar = findViewById(R.id.btn_lamar);

        txtNamaPerusahaan.setText(namaPerusahaan);
        txtNamaHRD.setText(namaHRD);
        txtDetailAlamat.setText(detailAlamat);
        txtTglLowongan.setText(tglLowongan);
        txtDeskJob.setText(deskJob);
        if (koordinatKosong()) {
            mapLokasi.setVisibility(View.GONE);
        }

        btnLamar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent lamarPekerjaan = new Intent(getApplicationContext(), PencariKerjaAdditionalDataLamaranActvity.class);
                lamarPekerjaan.putExtra("keyPekerjaan", keyPekerjaan);
                lamarPekerjaan.putExtra("namaPerusahaan", namaPerusahaan);
                lamarPekerjaan.putExtra("emailPerusahaan", emailPerusahaan);
                lamarPekerjaan.putExtra("namaHRD", namaHRD);
                lamarPekerjaan.putExtra("detailAlamat", detailAlamat);
                lamarPekerjaan.putExtra("tglLowongan", tglLowongan);
                lamarPekerjaan.putExtra("deskJob", deskJob);
                lamarPekerjaan.putExtra("koorX", txtKoordinatX);
                lamarPekerjaan.putExtra("koorY", txtKoordinatY);
                startActivity(lamarPekerjaan);
                finish();
            }
        });
    }

    private boolean koordinatKosong() {
        return txtKoordinatX == 0 && txtKoordinatY == 0;
    }

    public void kembali(View view) {
        finish();
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        LatLng lokasi = new LatLng(txtKoordinatX, txtKoordinatY);

        googleMap.addMarker(new MarkerOptions()
                .position(lokasi)
                .title(namaPerusahaan));
        googleMap.moveCamera(CameraUpdateFactory.newLatLng(lokasi));
    }
}