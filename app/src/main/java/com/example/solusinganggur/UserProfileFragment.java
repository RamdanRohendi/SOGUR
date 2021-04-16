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
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.solusinganggur.entity.PencariKerja;
import com.example.solusinganggur.entity.Perusahaan;
import com.example.solusinganggur.entity.User;
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

public class UserProfileFragment extends Fragment {
    private FirebaseAuth auth;
    private FirebaseAuth.AuthStateListener authListener;
    private FirebaseDatabase database;
    private DatabaseReference reference;
    private String getUserID;
    private ImageView icLokasi;
    private TextView txtUsername;
    private TextView txtEmail;
    private TextView txtPerusahaanNama;
    private TextView txtPerusahaanLokasi;
    private String role;

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

        txtPerusahaanNama = root.findViewById(R.id.txtperusahaannama);
        txtPerusahaanLokasi = root.findViewById(R.id.txtperusahaanlokasi);
        icLokasi = root.findViewById(R.id.ic_lokasi);

        reference.child("user").child(getUserID).child("role").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                role = String.valueOf(snapshot.getValue());

                switch (role) {
                    case "pencarikerja":
                        PencariKerja pencariKerja = new PencariKerja();
                        getData(role);
                        break;
                    case "perusahaan":
                        Perusahaan perusahaan = new Perusahaan();
                        getData(role);
                        break;
                }

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
                if (role.equals("pencarikerja")){
                    startActivity(new Intent(getActivity(), PencariKerjaEditProfileActivity.class));
                } else {
                    startActivity(new Intent(getActivity(), Perusahaan_editprofileActivity.class));
                }
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
                        hapusDataAkun();

                        FirebaseUser user = auth.getCurrentUser();
                        if (user != null) {
                            user.delete()
                                    .addOnCompleteListener(new OnCompleteListener<Void>() {
                                        @Override
                                        public void onComplete(@NonNull Task<Void> task) {
                                            if (task.isSuccessful()) {
                                                Log.e("MyData", ": Berhasil Dihapus ^_^");
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

    public void getData(String role) {
        reference.child("user").child(getUserID).child("data").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (role.equals("pencarikerja")) {
                    PencariKerja user = snapshot.getValue(PencariKerja.class);

                    if (user != null) {
                        user.setKey(snapshot.getKey());

                        txtUsername.setVisibility(View.VISIBLE);
                        txtEmail.setVisibility(View.VISIBLE);
                        txtUsername.setText(user.getNamaLengkap());
                        txtEmail.setText(user.getEmail());
                    }

                } else if (role.equals("perusahaan")) {
                    Perusahaan user = snapshot.getValue(Perusahaan.class);

                    if (user != null) {
                        user.setKey(snapshot.getKey());

                        icLokasi.setVisibility(View.VISIBLE);
                        txtPerusahaanNama.setVisibility(View.VISIBLE);
                        txtPerusahaanLokasi.setVisibility(View.VISIBLE);
                        txtPerusahaanNama.setText(user.getNamaPerusahaan());
                        txtPerusahaanLokasi.setText(user.getAlamat());
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
//                Toast.makeText(getActivity(),"Data Gagal Dimuat", Toast.LENGTH_SHORT).show();
                Log.e("MyData", error.getDetails() + " " + error.getMessage());
            }
        });
    }

    public void hapusDataAkun() {
        reference.child("user").child(getUserID).removeValue().addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {
                Log.e("MyData", ": Berhasil dihapus ^_^");
            }
        });
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