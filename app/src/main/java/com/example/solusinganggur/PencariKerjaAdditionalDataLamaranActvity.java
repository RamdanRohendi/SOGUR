package com.example.solusinganggur;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.solusinganggur.entity.DetailPekerjaan;
import com.example.solusinganggur.entity.ListLamaran;
import com.example.solusinganggur.entity.PencariKerja;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.SimpleDateFormat;
import java.util.Calendar;

public class PencariKerjaAdditionalDataLamaranActvity extends AppCompatActivity {
    private static final String TAG = "AddLamaranActivity";

    private FirebaseAuth mAuth;
    private FirebaseDatabase database;
    private DatabaseReference reference;
    private String getUserID;

    private DetailPekerjaan detailPekerjaan;
    private String keyPekerjaan;
    private String namaPerusahaan;
    private String emailPerusahaan;
    private String namaHRD;
    private String detailAlamat;
    private String tglLowongan;
    private String deskJob;
    private double koorX;
    private double koorY;

    private EditText edtNama;
    private EditText edtAlamat;
    private EditText edtNoTelp;
    private EditText edtEmail;

    private Button btnSerahkan;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pencarikerja_additional_data_lamaran);

        mAuth = FirebaseAuth.getInstance();

        FirebaseUser user = mAuth.getCurrentUser();
        getUserID = user.getUid();
        database = FirebaseDatabase.getInstance();
        reference = database.getReference();

        edtNama = findViewById(R.id.input_nama);
        edtAlamat = findViewById(R.id.input_alamat);
        edtNoTelp = findViewById(R.id.input_nomor_telepon);
        edtEmail = findViewById(R.id.input_email);

        keyPekerjaan = getIntent().getExtras().getString("keyPekerjaan");
        namaPerusahaan = getIntent().getExtras().getString("namaPerusahaan");
        emailPerusahaan = getIntent().getExtras().getString("emailPerusahaan");
        namaHRD = getIntent().getExtras().getString("namaHRD");
        detailAlamat = getIntent().getExtras().getString("detailAlamat");
        tglLowongan = getIntent().getExtras().getString("tglLowongan");
        deskJob = getIntent().getExtras().getString("deskJob");
        koorX = getIntent().getExtras().getDouble("koorX");
        koorY = getIntent().getExtras().getDouble("koorY");
        String SkoorX = String.valueOf(koorX);
        String SkoorY = String.valueOf(koorY);

        detailPekerjaan = new DetailPekerjaan(namaPerusahaan, namaHRD, detailAlamat, emailPerusahaan, tglLowongan, deskJob, SkoorX, SkoorY);
        detailPekerjaan.setKey(keyPekerjaan);

        reference.child("user").child(getUserID).child("data").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                PencariKerja pencariKerja = snapshot.getValue(PencariKerja.class);
                if (pencariKerja != null) {
                    edtNama.setText(pencariKerja.getNamaLengkap());
                    edtEmail.setText(pencariKerja.getEmail());
                    edtAlamat.setText(pencariKerja.getAlamat());
                    edtNoTelp.setText(pencariKerja.getNomorTelepon());
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Log.e("MyData", error.getDetails() + " " + error.getMessage());
            }
        });

        btnSerahkan = findViewById(R.id.btn_serahkan);
        btnSerahkan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                serahkanLamaran();

                if (edtNama.getText().toString().trim().isEmpty()) {
                    edtNama.setError("Nama tidak boleh kosong");
                    return;
                }

                if (edtEmail.getText().toString().trim().isEmpty()) {
                    edtEmail.setError("Email tidak boleh kosong");
                    return;
                }

                if (edtAlamat.getText().toString().trim().isEmpty()) {
                    edtAlamat.setError("Alamat tidak boleh kosong");
                    return;
                }

                if (edtNoTelp.getText().toString().trim().isEmpty()) {
                    edtNoTelp.setError("Nomor Telepon tidak boleh kosong");
                    return;
                }
            }
        });
    }

    private void serahkanLamaran() {
        Log.d(TAG, "daftar");
        if (!validateForm()) {
            return;
        }

        String nama = edtNama.getText().toString().trim();
        String alamat = edtAlamat.getText().toString().trim();
        String email = edtEmail.getText().toString().trim();
        String notlp = edtNoTelp.getText().toString().trim();
        String tgl = new SimpleDateFormat("EEEE, d MMMM yyyy").format(Calendar.getInstance().getTime());

        PencariKerja pencariKerja = new PencariKerja(email, alamat, nama, notlp);
        ListLamaran lamaran = new ListLamaran(detailPekerjaan.getNamaPerusahaan(), detailPekerjaan.getAlamatPerusahaan(), detailPekerjaan.getNamaHRD(), tgl, "pending", detailPekerjaan.getKoordinatX(), detailPekerjaan.getKoordinatY());

        reference.child("user").child(getUserID).child("lowongan_pekerjaan").child(keyPekerjaan).setValue(lamaran);
        reference.child("pekerjaan").child(keyPekerjaan).child("pelamar").child(getUserID).setValue(pencariKerja).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {
                reference.child("pekerjaan").child(keyPekerjaan).child("pelamar").child(getUserID).child("status").setValue("pending");
                startActivity(new Intent(getApplicationContext(), PencariKerjaKonfirmasiAdditionalDataLamaran.class));
                finish();
            }
        });
    }

    private boolean validateForm() {
        boolean result = true;

        if (TextUtils.isEmpty(edtNama.getText().toString())) {
            result = false;
        } else {
            edtNama.setError(null);
        }

        if (TextUtils.isEmpty(edtEmail.getText().toString())) {
            result = false;
        } else {
            edtEmail.setError(null);
        }

        return result;
    }

    public void kembali(View view) {
        finish();
    }
}
