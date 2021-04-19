package com.example.solusinganggur;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

public class PencariKerjaDetailAcceptedActivity extends FragmentActivity implements OnMapReadyCallback {
    private TextView txtNamaPerusahaan;
    private TextView txtLokasiPerusahaan;
    private TextView txtNamaHRD;
    private TextView txtTglLowongan;
    private Button btnChat;

    private String keyLowongan;
    private String namaPerusahaan;
    private String lokasiPerusahaan;
    private String namaHRD;
    private String tglLowongan;
    private double koorX;
    private double koorY;

    private GoogleMap mMap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pencarikerja_detail_accepted);
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.mapAcc);
        mapFragment.getMapAsync(this);

        txtNamaPerusahaan = findViewById(R.id.nama_perusahaan);
        txtLokasiPerusahaan = findViewById(R.id.loc);
        txtNamaHRD = findViewById(R.id.nama_hrd);
        txtTglLowongan = findViewById(R.id.tgl_lowongan);

        keyLowongan = getIntent().getExtras().getString("keyLowongan");
        namaPerusahaan = getIntent().getExtras().getString("namaPerusahaan");
        lokasiPerusahaan = getIntent().getExtras().getString("lokasiPerusahaan");
        namaHRD = getIntent().getExtras().getString("namaHRD");
        tglLowongan = getIntent().getExtras().getString("tglLowongan");
        koorX = getIntent().getExtras().getDouble("koorX");
        koorY = getIntent().getExtras().getDouble("koorY");

        View fragment = findViewById(R.id.mapAcc);
        if (koordinatKosong()) {
            fragment.setVisibility(View.INVISIBLE);
        }

        txtNamaPerusahaan.setText(namaPerusahaan);
        txtLokasiPerusahaan.setText(lokasiPerusahaan);
        txtNamaHRD.setText(namaHRD);
        txtTglLowongan.setText(tglLowongan);

        btnChat = findViewById(R.id.btn_chat);
        btnChat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(), UserMessageActivity.class));
            }
        });
    }

    private boolean koordinatKosong() {
        return koorX == 0 && koorY == 0;
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        LatLng lokasi = new LatLng(koorX, koorY);

        googleMap.addMarker(new MarkerOptions()
                .position(lokasi)
                .title(namaPerusahaan));
        googleMap.moveCamera(CameraUpdateFactory.newLatLng(lokasi));
    }
}
