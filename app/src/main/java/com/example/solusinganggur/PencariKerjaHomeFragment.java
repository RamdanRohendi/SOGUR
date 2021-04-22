package com.example.solusinganggur;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;

import com.example.solusinganggur.entity.ListLamaran;
import com.example.solusinganggur.entity.PencariKerja;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class PencariKerjaHomeFragment extends Fragment {
    private FirebaseAuth mAuth;
    private FirebaseDatabase database;
    private DatabaseReference reference;
    private String getUserID;
    private TextView txtUsername;
    private TextView subHeadlineAccept;
    private TextView subHeadlinePending;
    private TextView subHeadlineReject;

    private TextView noPesanAccept;
    private int jmlAccept = 0;

    private TextView noPesanPending;
    private int jmlPending = 0;

    private TextView noPesanReject;
    private int jmlReject = 0;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        View root = inflater.inflate(R.layout.fragment_pencarikerja_home, container, false);

        mAuth = FirebaseAuth.getInstance();

        FirebaseUser user = mAuth.getCurrentUser();
        getUserID = user.getUid();

        database = FirebaseDatabase.getInstance();
        reference = database.getReference();

        txtUsername = root.findViewById(R.id.txtnama_pengguna);

        noPesanAccept = root.findViewById(R.id.no_pesan_accept);
        noPesanPending = root.findViewById(R.id.no_pesan_pending);
        noPesanReject = root.findViewById(R.id.no_pesan_reject);
        subHeadlineAccept = root.findViewById(R.id.subheadline_accept);
        subHeadlinePending = root.findViewById(R.id.subheadline_pending);
        subHeadlineReject = root.findViewById(R.id.subheadline_reject);

        reference.child("user").child(getUserID).child("data").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                PencariKerja pencariKerja = snapshot.getValue(PencariKerja.class);
                if (pencariKerja != null) {
                    pencariKerja.setKey(snapshot.getKey());

                    txtUsername.setText(pencariKerja.getNamaLengkap());
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
//                Toast.makeText(getActivity(),"Data Gagal Dimuat", Toast.LENGTH_LONG).show();
                Log.e("MyData", error.getDetails() + " " + error.getMessage());
            }
        });

        reference.child("user").child(getUserID).child("lowongan_pekerjaan").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                    ListLamaran lamaran = dataSnapshot.getValue(ListLamaran.class);
                    if (lamaran.getStatus().equals("accept")) {
                        jmlAccept += 1;
                    }
                    if (lamaran.getStatus().equals("pending")) {
                        jmlPending += 1;
                    }
                    if (lamaran.getStatus().equals("reject")) {
                        jmlReject += 1;
                    }
                }
                noPesanAccept.setText(jmlAccept + "");
                subHeadlineAccept.setText("Anda memiliki " + jmlAccept + " lamaran kerja yang dipending");

                noPesanPending.setText(jmlPending + "");
                subHeadlinePending.setText("Anda memiliki " + jmlPending + " lamaran kerja yang dipending");

                noPesanReject.setText(jmlReject + "");
                subHeadlineReject.setText("Anda memiliki " + jmlReject + " lamaran kerja yang dipending");
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
//                Toast.makeText(getActivity(),"Data Gagal Dimuat", Toast.LENGTH_LONG).show();
                Log.e("MyData", error.getDetails() + " " + error.getMessage());
            }
        });

        CardView dashAccept = root.findViewById(R.id.pesan_accept);
        dashAccept.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getActivity(), PencariKerjaAcceptedActivity.class));
            }
        });

        CardView dashPending = root.findViewById(R.id.pesan_pending);
        dashPending.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getActivity(), PencariKerjaPendingActivity.class));
            }
        });

        CardView dashReject = root.findViewById(R.id.pesan_reject);
        dashReject.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getActivity(), PencariKerjaRejectedActivity.class));
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
}