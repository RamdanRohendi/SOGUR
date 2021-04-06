package com.example.solusinganggur;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

public class PerusahaanHomeFragment extends Fragment {

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        View root = inflater.inflate(R.layout.fragment_perusahaan_home, container, false);

//        CardView dashAccept = root.findViewById(R.id.pesan_accept);
//        dashAccept.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                startActivity(new Intent(getActivity(), PencariKerjaDetailAcceptedActivity.class));
//            }
//        });
//
//        CardView dashPending = root.findViewById(R.id.pesan_pending);
//        dashPending.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                startActivity(new Intent(getActivity(), PencariKerjaPendingActivity.class));
//            }
//        });
//
//        CardView dashReject = root.findViewById(R.id.pesan_reject);
//        dashReject.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                startActivity(new Intent(getActivity(), PencariKerjaRejectedActivity.class));
//            }
//        });

//        ImageView notif = root.findViewById(R.id.ic_notif);
//        notif.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                startActivity(new Intent(getActivity(), PerusahaanNotifikasiActivity.class));
//            }
//        });

        return root;
    }
}