package com.example.solusinganggur;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.solusinganggur.entity.PencariKerja;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class PencariKerjaEditProfileActivity extends AppCompatActivity {
    private static final String TAG = "PerjaEditProfActivity";

    private DatabaseReference reference;
    private FirebaseAuth mAuth;
    private FirebaseUser user;
    private String getUserID;

    private EditText edtNama;
    private TextInputLayout inputLayoutNama;
    private EditText edtJenkel;
    private TextInputLayout inputLayoutJenkel;
    private EditText edtTtl;
    private TextInputLayout inputLayoutTtl;
    private EditText edtAlamat;
    private TextInputLayout inputLayoutAlamat;
    private EditText edtAgama;
    private TextInputLayout inputLayoutAgama;
    private EditText edtEmail;
    private TextInputLayout inputLayoutEmail;
    private EditText edtNotelp;
    private TextInputLayout inputLayoutNotelp;
    private Button btnSimpan;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pencarikerja_edit_profile);

        reference = FirebaseDatabase.getInstance().getReference();
        mAuth = FirebaseAuth.getInstance();

        user = mAuth.getCurrentUser();
        getUserID = user.getUid();

        edtNama = findViewById(R.id.editNama);
        edtJenkel = findViewById(R.id.editJenkel);
        edtTtl = findViewById(R.id.editTtl);
        edtAlamat = findViewById(R.id.editAlamat);
        edtAgama = findViewById(R.id.editAgama);
        edtEmail = findViewById(R.id.editEmail);
        edtNotelp = findViewById(R.id.editNotelp);

        inputLayoutNama = findViewById(R.id.inputnama);
        inputLayoutJenkel = findViewById(R.id.inputjenkel);
        inputLayoutTtl = findViewById(R.id.inputttl);
        inputLayoutAlamat = findViewById(R.id.inputalamat);
        inputLayoutAgama = findViewById(R.id.inputagama);
        inputLayoutEmail = findViewById(R.id.inputemail);
        inputLayoutNotelp = findViewById(R.id.inputnotelp);

        reference.child("user").child(getUserID).child("data").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                PencariKerja pencariKerja = snapshot.getValue(PencariKerja.class);
                if (pencariKerja.getAlamat() == null) {
                    edtNama.setText(pencariKerja.getNamaLengkap());
                    edtEmail.setText(pencariKerja.getEmail());
                } else {
                    edtNama.setText(pencariKerja.getNamaLengkap());
                    edtEmail.setText(pencariKerja.getEmail());
                    edtTtl.setText(pencariKerja.getTtl());
                    edtJenkel.setText(pencariKerja.getJenisKelamin());
                    edtAlamat.setText(pencariKerja.getAlamat());
                    edtAgama.setText(pencariKerja.getAgama());
                    edtNotelp.setText(pencariKerja.getNomorTelepon());
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Log.e("MyData", error.getDetails() + " " + error.getMessage());
            }
        });

        btnSimpan = findViewById(R.id.simpaneditprofile);
        btnSimpan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                editData();

                if (edtNama.getText().toString().trim().isEmpty()) {
                    inputLayoutNama.setError("Nama tidak boleh kosong");
                    return;
                }

                if (edtJenkel.getText().toString().trim().isEmpty()) {
                    inputLayoutJenkel.setError("Jenis Kelamin tidak boleh kosong");
                    return;
                }

                if (edtTtl.getText().toString().trim().isEmpty()) {
                    inputLayoutTtl.setError("Tempat Tanggal Lahir tidak boleh kosong");
                    return;
                }

                if (edtAlamat.getText().toString().trim().isEmpty()) {
                    inputLayoutAlamat.setError("Alamat tidak boleh kosong");
                    return;
                }

                if (edtAgama.getText().toString().trim().isEmpty()) {
                    inputLayoutAgama.setError("Agama tidak boleh kosong");
                    return;
                }

                if (edtEmail.getText().toString().trim().isEmpty()) {
                    inputLayoutEmail.setError("Email tidak boleh kosong");
                    return;
                }

                if (edtNotelp.getText().toString().trim().isEmpty()) {
                    inputLayoutNotelp.setError("No Telepon tidak boleh kosong");
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

        String nama = edtNama.getText().toString().trim();
        String jenkel = edtJenkel.getText().toString().trim();
        String ttl = edtTtl.getText().toString().trim();
        String alamat = edtAlamat.getText().toString().trim();
        String agama = edtAgama.getText().toString().trim();
        String email = edtEmail.getText().toString().trim();
        String notlp = edtNotelp.getText().toString().trim();

        PencariKerja pencariKerja = new PencariKerja(email, alamat, nama, jenkel, ttl, agama, notlp);

        if (!user.getEmail().equals(email)) {
            updateEmail(email);
        }
        reference.child("user").child(getUserID).child("data").setValue(pencariKerja).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {
                Toast.makeText(getApplicationContext(), "Data berhasil diubah", Toast.LENGTH_SHORT).show();
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