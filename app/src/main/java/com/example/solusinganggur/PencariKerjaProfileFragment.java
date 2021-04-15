package com.example.solusinganggur;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.solusinganggur.entity.PencariKerja;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class PencariKerjaProfileFragment extends Fragment {
    private FirebaseAuth auth;
    private FirebaseAuth.AuthStateListener authListener;
    private FirebaseDatabase database;
    private DatabaseReference reference;
    private String getUserID;
    private TextView txtUsername;
    private TextView txtEmail;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        View root = inflater.inflate(R.layout.fragment_pencarikerja_profile, container, false);

        auth = FirebaseAuth.getInstance();
        FirebaseUser user = auth.getCurrentUser();
        getUserID = user.getUid();

        database = FirebaseDatabase.getInstance();
        reference = database.getReference();

        txtUsername = root.findViewById(R.id.txtusername);
        txtEmail = root.findViewById(R.id.txtuser);

        reference.child("user").child(getUserID).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                PencariKerja pencariKerja = snapshot.getValue(PencariKerja.class);
                pencariKerja.setKey(snapshot.getKey());

                txtUsername.setText(pencariKerja.getNamaLengkap());
                txtEmail.setText(pencariKerja.getEmail());
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
//                Toast.makeText(getActivity(),"Data Gagal Dimuat", Toast.LENGTH_LONG).show();
                Log.e("MyData", error.getDetails() + " " + error.getMessage());
            }
        });

        RelativeLayout btnEditProfile = root.findViewById(R.id.btn_editprofile);
        btnEditProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getActivity(), PencariKerjaEditProfileActivity.class));
            }
        });

        RelativeLayout btnNotifikasi = root.findViewById(R.id.btn_notifikasi);
        btnNotifikasi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getActivity(), PencariKerjaNotifikasiActivity.class));
            }
        });

        RelativeLayout btnBantuan = root.findViewById(R.id.btn_bantuan);
        btnBantuan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getActivity(), UserBantuanActivity.class));
            }
        });

        authListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser user = firebaseAuth.getCurrentUser();
                if (user == null) {
                    Toast.makeText(getActivity(), "Logout Sukses !", Toast.LENGTH_SHORT).show();
                    startActivity(new Intent(getActivity(), AuthLoginActivity.class));
                    getActivity().finish();
                }
            }
        };

        TextView btnlogout = root.findViewById(R.id.btn_logout);
        btnlogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                builder.setTitle("Apakah Anda yakin untuk Logout ?");
                builder.setMessage("Ketika anda logout maka diperlukan login ulang.");

                builder.setPositiveButton("LOGOUT", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        auth.signOut();
                    }
                });

                builder.setNegativeButton("CANCEL", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                    }
                });

                AlertDialog alertDialog = builder.create();
                alertDialog.show();
            }
        });

        TextView btnhapusakun = root.findViewById(R.id.btn_hapusakun);
        btnhapusakun.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                builder.setTitle("Apakah Anda yakin untuk Hapus Akun ?");
                builder.setMessage("Ketika anda hapus maka akun tidak dapat digunakan kembali.");

                builder.setPositiveButton("HAPUS", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        FirebaseUser user = auth.getCurrentUser();
                        if (user != null) {
                            user.delete()
                                    .addOnCompleteListener(new OnCompleteListener<Void>() {
                                        @Override
                                        public void onComplete(@NonNull Task<Void> task) {
                                            if (task.isSuccessful()) {
                                                Toast.makeText(getActivity(), "Akun Berhasil Dihapus", Toast.LENGTH_SHORT).show();
                                            }
                                        }
                                    });
                        }
                    }
                });

                builder.setNegativeButton("CANCEL", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                    }
                });

                AlertDialog alertDialog = builder.create();
                alertDialog.show();
            }
        });

        return root;
    }

    @Override
    public void onStart() {
        super.onStart();
        auth.addAuthStateListener(authListener);
    }

    @Override
    public void onStop() {
        super.onStop();
        if (authListener != null) {
            auth.removeAuthStateListener(authListener);
        }
    }
}