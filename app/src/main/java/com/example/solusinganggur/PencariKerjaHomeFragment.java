package com.example.solusinganggur;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;

public class PencariKerjaHomeFragment extends Fragment {

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        View root = inflater.inflate(R.layout.fragment_pencarikerja_home, container, false);

        CardView dashAccept = root.findViewById(R.id.pesan_accept);
        dashAccept.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getActivity(), PencariKerjaDetailAcceptedActivity.class));
            }
        });

        CardView dashPending = root.findViewById(R.id.pesan_pending);
        dashPending.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getActivity(), PencariKerjaDetailPendingActivity.class));
            }
        });

        CardView dashReject = root.findViewById(R.id.pesan_reject);
        dashReject.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getActivity(), PencariKerjaDetailRejectedActivity.class));
            }
        });

        ImageView notif = root.findViewById(R.id.ic_notif);
        notif.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getActivity(), PencariKerjaNotifikasiActivity.class));
            }
        });

        return root;
    }
}