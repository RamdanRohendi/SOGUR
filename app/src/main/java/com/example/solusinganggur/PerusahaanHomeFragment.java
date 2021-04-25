package com.example.solusinganggur;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.solusinganggur.entity.DetailPekerjaan;
import com.example.solusinganggur.entity.Perusahaan;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class PerusahaanHomeFragment extends Fragment {
    private FirebaseAuth mAuth;
    private FirebaseDatabase database;
    private DatabaseReference reference;
    private String getUserID;
    private TextView txtUsername;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        View root = inflater.inflate(R.layout.fragment_perusahaan_home, container, false);

        mAuth = FirebaseAuth.getInstance();

        FirebaseUser user = mAuth.getCurrentUser();
        getUserID = user.getUid();

        database = FirebaseDatabase.getInstance();
        reference = database.getReference();

        txtUsername = root.findViewById(R.id.txtnama_pengguna);

        reference.child("user").child(getUserID).child("data").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                Perusahaan perusahaan = snapshot.getValue(Perusahaan.class);
                if (perusahaan != null) {
                    perusahaan.setKey(snapshot.getKey());

                    txtUsername.setText(perusahaan.getNamaPerusahaan());

                    if (perusahaan.getNamaHRD() == null || perusahaan.getNamaHRD().isEmpty()) {
                        showAlert();
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
//                Toast.makeText(getActivity(),"Data Gagal Dimuat", Toast.LENGTH_LONG).show();
                Log.e("MyData", error.getDetails() + " " + error.getMessage());
            }
        });

        CardView daftarPelamar = root.findViewById(R.id.cardpelamar);
        daftarPelamar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getActivity(), PerusahaanDaftarPelamarActivity.class));
            }
        });

        CardView statusLowongan = root.findViewById(R.id.cardstatus);
        statusLowongan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                reference.child("user").child(getUserID).child("lowongan_pekerjaan").addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        DetailPekerjaan detailPekerjaan = snapshot.getValue(DetailPekerjaan.class);

                        if (detailPekerjaan != null) {
                            double koorX = Double.parseDouble(detailPekerjaan.getKoordinatX());
                            double koorY = Double.parseDouble(detailPekerjaan.getKoordinatY());
                            String namaPerusahaan = txtUsername.getText().toString();

                            Intent statusLowongan = new Intent(getActivity(), PerusahaanStatusLowonganActivity.class);
                            statusLowongan.putExtra("namaPerusahaan", namaPerusahaan);
                            statusLowongan.putExtra("koorX", koorX);
                            statusLowongan.putExtra("koorY", koorY);
                            startActivity(statusLowongan);
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {
//                Toast.makeText(getApplicationContext(),"Data Gagal Dimuat", Toast.LENGTH_LONG).show();
                        Log.e("MyData", error.getDetails() + " " + error.getMessage());
                    }
                });
            }
        });

        ImageView notif = root.findViewById(R.id.ic_notif);
        notif.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getActivity(), UserNotifikasi5Activity.class));
            }
        });

        return root;
    }

    private void showAlert() {
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        builder.setTitle("Mohon untuk melengkapi data perusahaan terlebih dahulu !");

        builder.setPositiveButton("Lengkapi Data Perusahaan", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                startActivity(new Intent(getActivity(), Perusahaan_editprofileActivity.class));
            }
        });

        AlertDialog alertDialog = builder.create();
        alertDialog.show();
    }
}