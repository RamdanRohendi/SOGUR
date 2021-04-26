package com.example.solusinganggur;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.solusinganggur.adapter.ItemListChatAdapter;
import com.example.solusinganggur.entity.ChatRoom;
import com.example.solusinganggur.entity.DetailPekerjaan;
import com.example.solusinganggur.entity.ListLamaran;
import com.example.solusinganggur.entity.User;
import com.example.solusinganggur.entity.detailPesan;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class UserMessageFragment extends Fragment {
    private FirebaseAuth mAuth;
    private FirebaseDatabase database;
    private DatabaseReference reference;
    private String getUserID;
    private int jmlChat;
    private String role;
    private TextView txtMessageToolbar;
    private RelativeLayout layEmptyMessage;

    private RecyclerView recyclerView;
    private RecyclerView.Adapter adapter;
    private RecyclerView.LayoutManager layoutManager;
    private ArrayList<detailPesan> listDetailPesan;
    private ArrayList<ListLamaran> pekerjaans;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        View root = inflater.inflate(R.layout.fragment_user_message, container, false);

        mAuth = FirebaseAuth.getInstance();

        FirebaseUser user = mAuth.getCurrentUser();
        getUserID = user.getUid();

        database = FirebaseDatabase.getInstance();
        reference = database.getReference();

        txtMessageToolbar = root.findViewById(R.id.txt_message_toolbar);
        layEmptyMessage = root.findViewById(R.id.layout_empty_message);

        listDetailPesan = new ArrayList<>();
        recyclerView = root.findViewById(R.id.list_message);

        jmlChat = 0;

        getRole();
        setRecyclerView();

        txtMessageToolbar.setText("Messages (" + jmlChat + ")");

        return root;
    }

    private void getLowonganPekerjaan() {
        reference.child("user").child(getUserID).child("lowongan_pekerjaan").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                pekerjaans = new ArrayList<>();
                for(DataSnapshot dataSnapshot : snapshot.getChildren()) {
                    ListLamaran lamaran = dataSnapshot.getValue(ListLamaran.class);

                    if (lamaran != null) {
                        detailPesan pesan = new detailPesan();
                        if (lamaran.getStatus().equals("accept")) {
                            pekerjaans.add(lamaran);
                            pesan.setPengirim(lamaran.getNamaPerusahaan());
                            listDetailPesan.add(pesan);
                            jmlChat++;
                        }
                    }
                }

                if (jmlChat == 0) {
                    layEmptyMessage.setVisibility(View.VISIBLE);
                    recyclerView.setVisibility(View.GONE);
                } else {
                    layEmptyMessage.setVisibility(View.GONE);
                    recyclerView.setVisibility(View.VISIBLE);
                }
                txtMessageToolbar.setText("Messages (" + jmlChat + ")");
                getData();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    private void getRole() {
        reference.child("user").child(getUserID).child("role").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                role = snapshot.getValue(String.class);

                if (role.equals("pencarikerja")) {
                    getLowonganPekerjaan();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    private void getData() {
        adapter = new ItemListChatAdapter(listDetailPesan, getContext());
        recyclerView.setAdapter(adapter);
    }

    private void setRecyclerView() {
        layoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setHasFixedSize(true);
    }
}
