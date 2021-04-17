package com.example.solusinganggur;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.solusinganggur.entity.PencariKerja;
import com.example.solusinganggur.entity.Perusahaan;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class AuthAfterLoginActivity extends AppCompatActivity {
    private Button mulai;
    private TextView txtWelcome;
    private FirebaseAuth mAuth;
    private FirebaseDatabase database;
    private DatabaseReference reference;
    private String getUserID;
    private String role;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_auth_after_login);

        mAuth = FirebaseAuth.getInstance();

        FirebaseUser user = mAuth.getCurrentUser();
        getUserID = user.getUid();

        database = FirebaseDatabase.getInstance();
        reference = database.getReference();

        role = getIntent().getExtras().getString("role");

        txtWelcome = findViewById(R.id.welcome);
        mulai = findViewById(R.id.btnstartjob);

        if (role.equals("pencarikerja")) {
            mulai.setText("Mulai Mencari Pekerjaan");
            reference.child("user").child(getUserID).child("data").addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    PencariKerja pencariKerja = snapshot.getValue(PencariKerja.class);
                    if (pencariKerja != null) {
                        pencariKerja.setKey(snapshot.getKey());

                        txtWelcome.setText("Selamat Datang " + pencariKerja.getNamaLengkap());
                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {
//                    Toast.makeText(getApplicationContext(),"Data Gagal Dimuat", Toast.LENGTH_LONG).show();
                    Log.e("MyData", error.getDetails() + " " + error.getMessage());
                }
            });
        }

        if (role.equals("perusahaan")) {
            mulai.setText("Mulai Membuka Lowongan Pekerjaan");
            reference.child("user").child(getUserID).child("data").addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    Perusahaan perusahaan = snapshot.getValue(Perusahaan.class);
                    if (perusahaan != null) {
                        perusahaan.setKey(snapshot.getKey());

                        txtWelcome.setText("Selamat Datang " + perusahaan.getNamaPerusahaan());
                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {
//                    Toast.makeText(getApplicationContext(),"Data Gagal Dimuat", Toast.LENGTH_LONG).show();
                    Log.e("MyData", error.getDetails() + " " + error.getMessage());
                }
            });
        }

        mulai.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (role.equals("pencarikerja")) {
                    startActivity(new Intent(getApplicationContext(), PencariKerjaMenuActivity.class));
                    finish();
                }
                if (role.equals("perusahaan")) {
                    startActivity(new Intent(getApplicationContext(), PerusahaanMenuActivity.class));
                    finish();
                }
            }
        });
    }

    @Override
    public void onBackPressed() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Apakah Anda yakin ingin keluar Aplikasi ?");

        builder.setPositiveButton("Ya", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                finish();
            }
        });

        builder.setNegativeButton("Tidak", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
            }
        });

        AlertDialog alertDialog = builder.create();
        alertDialog.show();
    }
}