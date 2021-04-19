package com.example.solusinganggur;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.solusinganggur.entity.DetailPekerjaan;
import com.example.solusinganggur.entity.Pekerjaan;
import com.example.solusinganggur.entity.PencariKerja;
import com.example.solusinganggur.entity.Perusahaan;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class Perusahaan_editprofileActivity extends AppCompatActivity {
    private static final String TAG = "PersaEditProfActivity";

    private DatabaseReference reference;
    private FirebaseAuth mAuth;
    private FirebaseUser user;
    private String getUserID;
    private DetailPekerjaan detailPekerjaan;

    private EditText edtNamaPerusahaan;
    private EditText edtNamaHRD;
    private EditText edtEmailPerusahaan;
    private EditText edtNoTelpPerusahaan;
    private EditText edtAlamatPerusahaan;
    private EditText edtTentangPerusahaan;
    private Button btnSimpan;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_perusahaan_editprofile);

        reference = FirebaseDatabase.getInstance().getReference();
        mAuth = FirebaseAuth.getInstance();

        user = mAuth.getCurrentUser();
        getUserID = user.getUid();

        edtNamaPerusahaan = findViewById(R.id.edit_namaperusahaan);
        edtNamaHRD = findViewById(R.id.edit_namahrd);
        edtEmailPerusahaan = findViewById(R.id.edit_emailperusahaan);
        edtNoTelpPerusahaan = findViewById(R.id.edit_notelp);
        edtAlamatPerusahaan = findViewById(R.id.edit_alamatperusahaan);
        edtTentangPerusahaan = findViewById(R.id.edit_tentangperusahaan);
        btnSimpan = findViewById(R.id.btn_simpan);

        reference.child("user").child(getUserID).child("data").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                Perusahaan perusahaan = snapshot.getValue(Perusahaan.class);
                if (perusahaan.getNomorTelepon() != null) {
                    edtNoTelpPerusahaan.setText(perusahaan.getNomorTelepon());
                }
                if (perusahaan.getNamaHRD() != null) {
                    edtNamaHRD.setText(perusahaan.getNamaHRD());
                }
                edtNamaPerusahaan.setText(perusahaan.getNamaPerusahaan());
                edtEmailPerusahaan.setText(perusahaan.getEmail());
                edtAlamatPerusahaan.setText(perusahaan.getAlamat());
                edtTentangPerusahaan.setText(perusahaan.getTentangPerusahaan());
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Log.e("MyData", error.getDetails() + " " + error.getMessage());
            }
        });

        btnSimpan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                editData();

                if (edtNamaPerusahaan.getText().toString().trim().isEmpty()) {
                    edtNamaPerusahaan.setError("Nama tidak boleh kosong");
                    return;
                }

                if (edtNamaHRD.getText().toString().trim().isEmpty()) {
                    edtNamaHRD.setError("Nama tidak boleh kosong");
                    return;
                }

                if (edtEmailPerusahaan.getText().toString().trim().isEmpty()) {
                    edtEmailPerusahaan.setError("Email tidak boleh kosong");
                    return;
                }

                if (edtNoTelpPerusahaan.getText().toString().trim().isEmpty()) {
                    edtNoTelpPerusahaan.setError("Nomor Telepon tidak boleh kosong");
                    return;
                }

                if (edtAlamatPerusahaan.getText().toString().trim().isEmpty()) {
                    edtAlamatPerusahaan.setError("Alamat tidak boleh kosong");
                    return;
                }

                if (edtTentangPerusahaan.getText().toString().trim().isEmpty()) {
                    edtTentangPerusahaan.setError("Tentang tidak boleh kosong");
                    return;
                }
            }
        });
    }

    private void editData() {
        Log.d(TAG, "daftar");
        if (!validateForm()) {
            return;
        }

        String nama = edtNamaPerusahaan.getText().toString().trim();
        String email = edtEmailPerusahaan.getText().toString().trim();
        String notlp = edtNoTelpPerusahaan.getText().toString().trim();
        String alamat = edtAlamatPerusahaan.getText().toString().trim();
        String tentang = edtTentangPerusahaan.getText().toString().trim();
        String hrd = edtNamaHRD.getText().toString().trim();

        Perusahaan perusahaan = new Perusahaan(nama, email, alamat, tentang, notlp, hrd);

        if (!user.getEmail().equals(email)) {
            updateEmail(email);
        }
        reference.child("user").child(getUserID).child("data").setValue(perusahaan).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {
                Toast.makeText(getApplicationContext(), "Data berhasil diubah", Toast.LENGTH_SHORT).show();
            }
        });
        reference.child("pekerjaan").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                    Pekerjaan pekerjaan = dataSnapshot.getValue(Pekerjaan.class);

                    if (pekerjaan != null) {
                        if (pekerjaan.getIdPerusahaan().equals(getUserID)) {
                            detailPekerjaan = pekerjaan.getData();
                            detailPekerjaan.setNamaPerusahaan(nama);
                            detailPekerjaan.setNamaHRD(hrd);
                            detailPekerjaan.setEmailPerusahaan(email);
                            detailPekerjaan.setAlamatPerusahaan(alamat);
                            reference.child("pekerjaan").child(dataSnapshot.getKey()).child("data").setValue(detailPekerjaan);
                            reference.child("user").child(getUserID).child("lowongan_pekerjaan").setValue(detailPekerjaan);
                        }
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Log.e("MyData", error.getDetails() + " " + error.getMessage());
            }
        });

        finish();
    }

    private void updateEmail(String email) {
        user.updateEmail(email).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if (task.isSuccessful()) {
                    Toast.makeText(getApplicationContext(), "Email berhasil diubah", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private boolean validateForm() {
        boolean result = true;

        if (TextUtils.isEmpty(edtNamaPerusahaan.getText().toString())) {
            result = false;
        } else {
            edtNamaPerusahaan.setError(null);
        }

        if (TextUtils.isEmpty(edtEmailPerusahaan.getText().toString())) {
            result = false;
        } else {
            edtEmailPerusahaan.setError(null);
        }

        return result;
    }


    public void kembali(View view) {
        finish();
    }
}